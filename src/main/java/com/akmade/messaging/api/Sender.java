package com.akmade.messaging.api;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.akmade.messaging.Utility;

public abstract class Sender extends Messenger {

	protected MessageProducer mProducer;
	protected final Destination replyQueue;
	protected final Boolean receiveReplies;
	
	
	protected Sender(String queueName, Boolean receiveReplies) throws JMSException{
		super();
		this.receiveReplies = receiveReplies;
        replyQueue = mSession.createTemporaryQueue();
        MessageConsumer responseConsumer = mSession.createConsumer(replyQueue);
        //This class will handle the messages to the reply queue as well
        responseConsumer.setMessageListener(this); 
	}
	
	protected final String sendMessage(TextMessage txtMessage) throws JMSException{
		
        //Set the reply to field to the temp queue you created above, this is the queue the server
        //will respond to 
		if(receiveReplies)
			txtMessage.setJMSReplyTo(replyQueue);
        //Set a correlation ID so when you get a response you know which sent message the response is for
        //If there is never more than one outstanding message to the server then the
        //same correlation ID can be used for all the messages...if there is more than one outstanding
        //message to the server you would presumably want to associate the correlation ID with this
        //message somehow...a Map works good
        String correlationId = Utility.createRandomString();
        txtMessage.setJMSCorrelationID(correlationId);
        log.debug("Sending message " + txtMessage.toString());
        txtMessage.setJMSExpiration(20000l);
        mProducer.setTimeToLive(20000l);
        this.mProducer.send(txtMessage);
        return correlationId;
	}
	
	@Override
	protected Session getSession() {
		return mSession;
	}


	
}
