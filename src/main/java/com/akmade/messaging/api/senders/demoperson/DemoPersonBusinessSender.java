package com.akmade.messaging.api.senders.demoperson;

import static com.akmade.messaging.Utility.*;

import javax.jms.*;

import com.akmade.messaging.api.queue.QueueSender;
import com.akmade.messaging.exception.MessagingException;

import gov.noaa.alaskafisheries.messaging.dto.MessagingDTO.BusMessage.BusMessageKeyValueCommand;
import gov.noaa.alaskafisheries.messaging.dto.MessagingDTO.BusMessage.BusMessageStatus;
import gov.noaa.alaskafisheries.messaging.dto.MessagingDTO.BusMessage;
import gov.noaa.alaskafisheries.messaging.dto.MessagingDTO.Person;

public abstract class DemoPersonBusinessSender extends QueueSender {
	
	public DemoPersonBusinessSender() throws JMSException{
		super(SecurityQueues.SECURITY_BUSINESS_QUEUE);
	}
	
	
	
	public final String firstNameChange(BusMessageStatus status, Person person, String newName) throws MessagingException{
		log.debug("First Name Change request: " + print(person) + "\nChange Name to: " + newName);
		try{
			TextMessage txtMessage = mSession.createTextMessage();
	        txtMessage.setStringProperty("request", 
    			   	print(BusMessage.newBuilder()
    			   			.setStatus(status)
    			   			.setCommand("firstname-change")
    			   			.addKeyValueCommands(
    			   					BusMessageKeyValueCommand.newBuilder()
    			   						.setKey("firstname")
    			   						.setValue(newName))
    			   			.setPerson(person)
    			   			.build()));
	        return super.sendMessage(txtMessage);
		}catch(JMSException e){
			String s = "Error sending message to change a persons first name";
			log.error(s);
	 	    throw MessagingException.wrap(e, s);
	    }
	}
	
	
	public final String haveBirthday(BusMessageStatus status, Person person) throws MessagingException{
		log.debug("Birthday request! Happy Birthday " + person.getFirstName() + " " + person.getLastName());
	    try{
			TextMessage txtMessage = mSession.createTextMessage();
	        txtMessage.setStringProperty("request", 
    			   	print(BusMessage.newBuilder()
    			   			.setStatus(status)
    			   			.setCommand("have-birthday")
    			   			.setPerson(person)
    			   			.build()));
	        return super.sendMessage(txtMessage);
		}catch(JMSException e){
			String s = "Error sending message to have a birthday";
			log.error(s);
	 	    throw MessagingException.wrap(e, s);
	    }
	
	}
}
