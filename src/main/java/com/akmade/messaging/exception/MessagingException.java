package com.akmade.messaging.exception;

public class MessagingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static MessagingException wrap(Throwable t){
		MessagingException me = new MessagingException();
		me.addSuppressed(t);
		return me;
	}
	
	public static MessagingException wrap(Throwable t, String message){
		MessagingException me = new MessagingException(message);
		me.addSuppressed(t);
		return me;
	}
	
	
	MessagingException(){
		super();
	}
	
	public MessagingException(String msg){
		super(msg);
	}
}
