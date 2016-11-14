package com.akmade.messaging.api.topic;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import com.akmade.messaging.api.Listener;

public abstract class TopicListener extends Listener {

	protected TopicListener(String queueName) throws JMSException{
		super();
		mDestination = mSession.createTopic(queueName);
 
        //Set up a consumer to consume messages off of the admin queue
        MessageConsumer consumer = mSession.createConsumer(mDestination);
        consumer.setMessageListener(this);
	}
	
	@Override
	protected Session getSession() {
		return mSession;
	}

}
