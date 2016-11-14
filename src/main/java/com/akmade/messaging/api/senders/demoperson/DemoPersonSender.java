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

public abstract class DemoPersonSender extends QueueSender {

	
	
	protected DemoPersonSender() throws JMSException {
		super(SecurityQueues.SECURITY_SERVICE);
	}

	public final String getPeople(BusMessageStatus status) throws MessagingException{
		log.debug("Requesting return of all People");
	       try{
	    	   TextMessage txtMessage = mSession.createTextMessage();
	    	   txtMessage.setStringProperty("request", 
	    			   print(BusMessage.newBuilder()
	    					   .set
	    					   .setStatus(status)
	    					   .setCommand("get-people")
	    					   .build()));
	    	   return super.sendMessage(txtMessage);
	       }catch(JMSException e){
	    	   String s = "Error sending message to find people";
	    	   log.error(s);
	    	   throw MessagingException.wrap(e, s);
	       }
		}
		
		
		public final String saveNewPerson(BusMessageStatus status, Person newPerson) throws MessagingException{
			log.debug("Requesting save of new person: \n" + print(newPerson));
			try{
		        TextMessage txtMessage = mSession.createTextMessage();
		        txtMessage.setStringProperty("request", 
		    			   print(BusMessage.newBuilder()
		    					   .setStatus(status)
		    					   .setCommand("save-person")
		    					   .setPerson(newPerson)
		    					   .build()));
		        return super.sendMessage(txtMessage);
			}catch(JMSException e){
				String s = "Error sending message to update a person";
				log.error(s);
		 	    throw MessagingException.wrap(e, s);
		    }
		}
		
		public final String changeFirstName(BusMessageStatus status, Person person, String newName){
			log.debug("Requesting person: \n" + print(person) + "\nchange name to:\n" + newName);
			try{
		        TextMessage txtMessage = mSession.createTextMessage();
		        txtMessage.setStringProperty("request", 
		    			   print(BusMessage.newBuilder()
		    					   .setStatus(status)
		    					   .setCommand("firstname-change")
		    					   .setPerson(person)
		    					   .addKeyValueCommands(BusMessageKeyValueCommand.newBuilder()
		    							   .setKey("firstname")
		    							   .setValue(newName)
		    							   .build())
		    					   .build()));
		        return super.sendMessage(txtMessage);
			}catch(JMSException e){
				String s = "Error sending message to update a person";
				log.error(s);
		 	    throw MessagingException.wrap(e, s);
		    }
		}

}
