package com.akmade.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.akmade.messaging.exception.MessagingException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

import com.akmade.messaging.dto.MessagingDTO.BusMessage;
import com.akmade.messaging.dto.MessagingDTO.BusMessage.BusMessageStatus;



public class Utility {
	
	private static Logger log = Logger.getLogger(Utility.class);
	
	private Utility(){}
	private static Random random = new Random(System.currentTimeMillis());
	public static final String createRandomString() {
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }

	public static BusMessageStatus getStartingBusMessageStatus(String processName){
		return BusMessageStatus.newBuilder()
				.setProcess(processName)
				.setGuid(createRandomString())
				.build();
	}
	
	public static BusMessage createInitialBusMessage(String processName) {
		return BusMessage.newBuilder()
						.setStatus(getStartingBusMessageStatus(processName))
						.build();
	}
	
	public static <T extends Message> BusMessageStatus makeStatus(BusMessageStatus bms, Either<T,List<String>> ep) {
		return BusMessageStatus.newBuilder(bms)
					.setValid(bms.getValid() && ep.isLeft())
					.addAllMessages(ep.rightOrElseGet(() -> new ArrayList<>()))
					.build();
	}
	
	
	
	private static <T extends Message.Builder> T readMessage(T b, String json){
		try{
			JsonFormat.parser().merge(json, b);
		}catch(InvalidProtocolBufferException e){
			String s = "Error deserializing protobuf JSON: " + e.getMessage();
			log.error(s);
			MessagingException.wrap(e, s);
		}
		return b;
	}

	public static BusMessage.Builder readBusMessage(String json){
		return readMessage(BusMessage.newBuilder(), json);
	}
	
	public static <T extends Message> String print(T t){
		try{
			return JsonFormat.printer().print(t);
		}catch(InvalidProtocolBufferException e){
			String s = "Error serializing to Json: " + e.getMessage();
			log.error(s);
			MessagingException.wrap(e, s);
			return null;
		}
	}
	
	public static BusMessage invalidateMessage(BusMessage m, String s){
		return BusMessage.newBuilder(m)
						.setStatus(BusMessageStatus.newBuilder(m.getStatus())
								.setValid(false)
								.addMessages(s)
								.build())
						.build();
	}
	
	public static BusMessage invalidateMessage(BusMessage m, Throwable t, String s){
		return BusMessage.newBuilder(m)
						.setStatus(BusMessageStatus.newBuilder(m.getStatus())
								.setValid(false)
								.addMessages(s + " caused by: " + t.getClass().getName() + " - " + t.getMessage())
								.build())
						.build();
	}
	
	public static String readStringProperty(TextMessage msg, String property){
		try {
			return msg.getStringProperty(property);
		} catch (JMSException e) {
			String s = "Error extracting string propery: " + property + " from TextMessage: " + msg.toString();
			log.error(s);;
			MessagingException.wrap(e, s);
			return "";
		}
	}
}
