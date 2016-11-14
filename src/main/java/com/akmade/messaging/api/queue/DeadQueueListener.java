package com.akmade.messaging.api.queue;

import javax.jms.JMSException;

public class DeadQueueListener extends QueueListener {

	public DeadQueueListener(String queueName, MessageProtocol protocol) throws JMSException {
		super("DLQ." + queueName + "?consumer.priority=20", protocol);
		// TODO Auto-generated constructor stub
	}

}
