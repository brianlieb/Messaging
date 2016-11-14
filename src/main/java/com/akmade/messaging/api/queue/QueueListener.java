package com.akmade.messaging.api.queue;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import javax.jms.Session;

import com.akmade.messaging.api.Listener;
import com.akmade.messaging.api.Messenger;
import com.akmade.messaging.api.Messenger.MessageProtocol;

public class QueueListener extends Listener {
	
	private MessageProtocol mProtocol;
	
	public QueueListener(String queueName, MessageProtocol protocol) throws JMSException{
		super();
		mDestination = mSession.createQueue(queueName);
		mProtocol = protocol;
		
        //Set up a consumer to consume messages off of the admin queue
        MessageConsumer consumer = mSession.createConsumer(mDestination);
        consumer.setMessageListener(this);
	}
	
	@Override
	protected MessageProtocol getMessageProtocol() {
		return mProtocol;
	}
	
	@Override
	protected Session getSession(){
		return mSession;
	}
	

}
