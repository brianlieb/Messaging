package com.akmade.messaging.api.senders.demoperson;

import static com.akmade.messaging.Utility.*;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import com.akmade.messaging.api.queue.QueueSender;
import com.akmade.messaging.exception.MessagingException;

import gov.noaa.alaskafisheries.messaging.dto.MessagingDTO.BusMessage;
import gov.noaa.alaskafisheries.messaging.dto.MessagingDTO.BusMessage.BusMessageKeyValueCommand;
import gov.noaa.alaskafisheries.messaging.dto.MessagingDTO.BusMessage.BusMessageStatus;
import gov.noaa.alaskafisheries.messaging.dto.MessagingDTO.Person;

public abstract class DemoPersonDataSender extends QueueSender {
	
	public DemoPersonDataSender() throws JMSException{
		super(SecurityQueues.PERSON_DATA_QUEUE);
	}
	
	
	public final String findPeople(BusMessageStatus status, String property, String value) throws MessagingException{
		log.debug((property.equals("")) 
				? "Requesting return of all people"
				: "Requesting Search for people matching: " + property + "=" + value);
       try{
    	   TextMessage txtMessage = mSession.createTextMessage();
    	   txtMessage.setStringProperty("request", 
    			   	print(BusMessage.newBuilder()
    			   			.setStatus(status)
    			   			.setCommand("find-people")
    			   			.addKeyValueCommands(
    			   					BusMessageKeyValueCommand.newBuilder()
    			   						.setKey(property)
    			   						.setValue(value))
    			   			.build()));
    	   return super.sendMessage(txtMessage);
       }catch(JMSException e){
    	   String s = "Error sending message to find people";
    	   log.error(s);
    	   throw MessagingException.wrap(e, s);
       }
	}
	
	
	public final String updatePerson(BusMessageStatus status, Person person) throws MessagingException{
		log.debug("Requesting update of:\n" + print(person));
		try{
	        TextMessage txtMessage = mSession.createTextMessage();
	        txtMessage.setStringProperty("request", 
	        		print(BusMessage.newBuilder()
	        				.setStatus(status)
	        				.setCommand("update")
	        				.setPerson(person)
	        				.build()));
	        return super.sendMessage(txtMessage);
		}catch(JMSException e){
			String s = "Error sending message to update a person";
			log.error(s);
	 	   	throw MessagingException.wrap(e, s);
	    }
	}
	
	public final String saveNewPerson(BusMessageStatus status, Person person){
		log.debug("Requesting save of new person:\n" + print(person));
		try{
	        TextMessage txtMessage = mSession.createTextMessage();
	        txtMessage.setStringProperty("request", 
	        		print(BusMessage.newBuilder()
	        				.setStatus(status)
	        				.setCommand("save-new-person")
	        				.setPerson(person)
	        				.build()));
	        return super.sendMessage(txtMessage);
		}catch(JMSException e){
			String s = "Error sending message to save a new person";
			log.error(s);
	 	   	throw MessagingException.wrap(e, s);
	    }
	}
	
	public final String getAllPeople(BusMessageStatus status){
		return findPeople(status, "", "");
	}
	

	
	
}
