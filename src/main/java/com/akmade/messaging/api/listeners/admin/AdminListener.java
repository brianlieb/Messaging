package com.akmade.messaging.api.listeners.admin;

import javax.jms.JMSException;

import com.akmade.messaging.api.topic.TopicListener;

public abstract class AdminListener extends TopicListener {

	protected final MessageProtocol protocol =
		m -> {
			if(m.getCommand().equals("reload"))
				reloadData();
			else if(m.getCommand().equals("persist"))
				persistData();
			else if(m.getCommand().equals("powerdown"))
				prepareForPowerdown();
			else
				log.error("Error! Unrecognized command recieved on Admin Topic");
		};
	
	protected AdminListener() throws JMSException {
		super(AdminQueues.ADMIN_QUEUE);
	}
	
	@Override 
	protected MessageProtocol getMessageProtocol() {
		return protocol;
	}

	public abstract void reloadData();
	public abstract void persistData();
	public abstract void prepareForPowerdown();

}
