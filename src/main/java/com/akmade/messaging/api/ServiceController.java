package com.akmade.messaging.api;

import java.util.HashMap;
import org.apache.log4j.Logger;

import com.akmade.messaging.Pair;

public abstract class ServiceController {

	private static Logger log = Logger.getLogger(ServiceController.class);
	
	protected HashMap<String,CallbackQueue> mCallbackQueues = new HashMap<>();
	
	public void addCallback(Pair<String,CallbackQueue> queue){
		mCallbackQueues.put(queue.left, queue.right);
	}
	
}
