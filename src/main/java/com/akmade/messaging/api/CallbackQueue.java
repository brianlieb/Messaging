package com.akmade.messaging.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;


import org.apache.log4j.Logger;

import com.akmade.messaging.OneOf3;
import com.akmade.messaging.Pair;
import com.akmade.messaging.api.queue.QueueListener;
import com.akmade.messaging.exception.MessagingException;
import com.google.protobuf.Message;

import com.akmade.messaging.dto.MessagingDTO.BusMessage;
import com.akmade.messaging.dto.MessagingDTO.BusMessage.BusMessageStatus;

public class CallbackQueue {

	private static final Logger log = Logger.getLogger(CallbackQueue.class);
	
	public static Builder newBuilder(String process, String guid){
		return new Builder(process,guid);
	}
	
	public static Builder newBuilder(BusMessageStatus bms){
		return new Builder(bms.getProcess(), bms.getGuid());
	}
	
	private final String process;
	private final String queueGuid;
	private Map<Integer,Consumer<BusMessage>> actionQueue;
	protected final Consumer<BusMessage> starter;
	protected final OneOf3<Consumer<BusMessage>, Function<BusMessage,BusMessage>, QueueListener> finisher;
	protected final BusMessageStatus returnStatus;
	private BusMessage endMessage = null;
	
	private CallbackQueue(String process, String guid, Map<Integer, Consumer<BusMessage>> queue,
							Consumer<BusMessage> starter, 
							OneOf3<Consumer<BusMessage>, Function<BusMessage,BusMessage>, QueueListener> finisher, 
							BusMessageStatus returnStatus) {
		this.process = process;
		this.queueGuid = guid;
		this.actionQueue = Collections.unmodifiableMap(queue);
		this.starter = starter;
		this.finisher = finisher;
		this.returnStatus = returnStatus;
	}
	

	
	private Function<BusMessage,BusMessage> incrementStepAndGet =
			bm -> BusMessage.newBuilder(bm)
						.setStatus(BusMessageStatus.newBuilder(bm.getStatus())
												.setStep(bm.getStatus().getStep()+1)
												.build())
						.build();

	public final Consumer<BusMessage> doNext = 
			msg -> { 
				if (actionQueue.get(msg.getStatus().getStep()+1) != null && msg.getStatus().getValid()){
					actionQueue.get(msg.getStatus().getStep()+1).accept(incrementStepAndGet.apply(msg));
				}else {
					finish(msg);
				}
			};
	
	private final BinaryOperator<BusMessageStatus> combine =
			(returnStatus, currentStatus) -> (currentStatus.getValid())
												? returnStatus
												: BusMessageStatus.newBuilder(returnStatus)
																.setValid(currentStatus.getValid())
																.addAllMessages(currentStatus.getMessagesList().subList(0, currentStatus.getMessagesCount()-1))
																.build();

	
	private void finish(BusMessage bm){
		log.debug("Finishing messaging queue for: " + process+"-"+queueGuid);
		if(finisher.isFirst())
			finisher.getFirst().accept(bm); 
		if(finisher.isSecond())
			endMessage = finisher.getSecond().apply(bm);
		finisher.getThird().sendReply(BusMessage.newBuilder(bm)
										.setStatus(combine.apply(returnStatus, bm.getStatus()))
										.build());
	}
	
	public Pair<String, CallbackQueue> start(BusMessage y){
		log.debug("Starting message queue for: " + process+"-"+queueGuid);
		starter.accept(BusMessage.newBuilder(y)
						.setStatus(BusMessageStatus.newBuilder(y.getStatus())
								.setStep(1)
								.setValid(true)
								.build())
						.build());
		return Pair.of(process+queueGuid, this);
	}
	
	public BusMessage receive(long millis) throws InterruptedException{
		long startMillis = System.currentTimeMillis();
		while(endMessage == null){
			if(System.currentTimeMillis() - startMillis > millis)
				throw new MessagingException ("Result did not return in allowed time");
			Thread.sleep(100l);
		}
		return endMessage;
	}
	
	
	
	public static class Builder {
		private Integer step = 2;
		private String b_process;
		private String b_queueGuid;
		private Map<Integer,Consumer<BusMessage>> b_actionQueue;
		protected Consumer<BusMessage> b_starter;
		protected OneOf3<Consumer<BusMessage>, Function<BusMessage,BusMessage>, QueueListener>  b_finisher;
		protected BusMessageStatus b_returnStatus;
		
		public Builder (String process, String guid){
			this.b_process = process;
			this.b_queueGuid = guid;
			this.b_actionQueue = new HashMap<>();
		}
		
		
		private <T extends Message> void addAction(Consumer<BusMessage> send) {
			b_actionQueue.put((step++), send);
		}
		
		public Builder startWith(Consumer<BusMessage> starter) {
			this.b_starter = starter;
			return this;
		}
		
		public <T extends Message> Builder thenDo(Function<BusMessage, T> messageExtract, Consumer<T> action,Function<T, BusMessage> messageBundle, Consumer<BusMessage> send) {
			return thenDo(messageExtract.andThen(m -> { action.accept(m); return m; }).andThen(messageBundle), send);
		}
		
		public <T extends Message> Builder thenDo(Function<BusMessage, T> messageExtract, Function<T,T> action,Function<T, BusMessage> messageBundle, Consumer<BusMessage> send) {
			return thenDo(messageExtract.andThen(action).andThen(messageBundle), send);
		}
		
		public Builder thenDo(Function<BusMessage, BusMessage> action, Consumer<BusMessage> send) {
			return thenDo(m -> send.accept(action.apply(m)));
		}
		
		public Builder thenDo(Consumer<BusMessage> send){
			addAction(send);
			return this;
		}
		
		public Builder finishWith(Function<BusMessage,BusMessage> finisher){
			this.b_finisher = OneOf3.second(finisher);
			return this;
		}
		
		public Builder finishWith(Consumer<BusMessage> finisher) {
			this.b_finisher = OneOf3.first(finisher);
			return this;
		}
		
		public Builder finishWith(QueueListener finisher, BusMessageStatus returnStatus) {
			this.b_finisher = OneOf3.third(finisher);
			this.b_returnStatus = returnStatus;
			return this;
		}
		
		
		private boolean valid(){
			return b_finisher != null && b_starter != null;
		}
		
		public CallbackQueue build(){
			if(!valid())
				throw new MessagingException("Cannot build a Message Queue without a starter, finisher, and finalizer");
			return new CallbackQueue(b_process, b_queueGuid, b_actionQueue, b_starter, b_finisher, b_returnStatus);
		}
		
	}
}
