package com.akmade.messaging.api.queue;
import java.util.HashMap;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.akmade.messaging.api.CallbackQueue;
import com.akmade.messaging.api.Sender;
import com.akmade.messaging.exception.MessagingException;

import com.akmade.messaging.dto.MessagingDTO.BusMessage;

public abstract class QueueSender extends Sender {
	
	protected QueueSender(String queueName) throws JMSException{
		super(queueName, true);
		mDestination = mSession.createQueue(queueName);
		mProducer = mSession.createProducer(mDestination);
        mProducer.setDeliveryMode(DeliveryMode.PERSISTENT); // do not log message to stable storage
	}
	
	
	@Override
	public void onMessage(Message message) {
		if(message instanceof TextMessage){
			try {
				message.acknowledge();
			} catch (JMSException e) {
				MessagingException.wrap(e, "Error Acknowledging exception");
			}	
			TextMessage txtMsg = (TextMessage)message;
			BusMessage bm = RESPONSE_BUS_MESSAGE_PROTOCOL.apply(txtMsg);
			if(bm.getStatus().getUndelivered()){
				log.warn("Handling undelivered message");
				handleUndeliveredMessage(bm);
			}else{
				log.debug("Recieved reply for message: " + bm.getStatus().getProcess() + "-" + bm.getStatus().getGuid() +
						" - just completed step: " + bm.getStatus().getStep());
				CallbackQueue q = getCallbackQueues().get(bm.getStatus().getProcess()+bm.getStatus().getGuid());
				if(q != null)
					q.doNext.accept(bm);
			}
		}
	}
	
	
	protected Session getSession(){
		return mSession;
	}
	

	protected abstract HashMap<String,CallbackQueue> getCallbackQueues();
	protected abstract void handleUndeliveredMessage(BusMessage bm);
	
}
