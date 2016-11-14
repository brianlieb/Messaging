package com.akmade.messaging.api.topic;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Session;

import com.akmade.messaging.api.Sender;

public abstract class TopicSender extends Sender {


	protected TopicSender(String topicName, boolean receiveReplies) throws JMSException{
		super(topicName, receiveReplies);
		mDestination = mSession.createTopic(topicName);
		mProducer = mSession.createProducer(mDestination);
        mProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT); // do not log message to stable storage
		
	}
	

	@Override
	protected Session getSession() {
		return mSession;
	}

}
