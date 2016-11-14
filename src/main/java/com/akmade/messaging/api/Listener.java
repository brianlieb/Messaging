package com.akmade.messaging.api;

import static com.akmade.messaging.Utility.print;
import static com.akmade.messaging.api.Messenger.MessageProtocol.responseMessage;

import java.util.HashMap;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.akmade.messaging.exception.MessagingException;

import com.akmade.messaging.dto.MessagingDTO.BusMessage;

public abstract class Listener extends Messenger {

	protected MessageProducer replyProducer;
	private HashMap<String,Destination> mReplyTos = new HashMap<>();
	protected MessageProtocol mProtocol;
	
	protected Listener() throws JMSException{
		super();
		
        //Setup a message producer to respond to messages from clients, we will get the destination
        //to send to from the JMSReplyTo header field from a Message
        this.replyProducer = mSession.createProducer(null);
        this.replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
 	}
	
	@Override
	public void onMessage(Message message) {
		try {
            TextMessage txtMsg = null;
            if (message instanceof TextMessage)
                txtMsg = (TextMessage) message;
            try {
				message.acknowledge();
			} catch (JMSException e) {
				MessagingException.wrap(e, "Error Acknowledging exception");
			}	 
            BusMessage bm = REQUEST_BUS_MESSAGE_PROTOCOL.apply(txtMsg);
            if(message.getJMSReplyTo() != null) {
	            mReplyTos.put(bm.getStatus().getGuid(), message.getJMSReplyTo());
            }
            
            log.debug("Handling Message: " + bm.getStatus().getProcess() + "-" + bm.getStatus().getGuid());
            getMessageProtocol().handle(bm);
            
            
        } catch (JMSException e) {
        	String em = "Error handling Message: " + message.toString();
            log.error(em);
            MessagingException.wrap(e, em);
        }
	}
	
	public void sendReply(BusMessage bm){
		try{
			log.debug("Responding to message: " + bm.getStatus().getProcess() + "-" + bm.getStatus().getGuid());
	        TextMessage response = responseMessage(getSession(), "response", bm);
	        //Set the correlation ID from the received message to be the correlation id of the response message
	        //this lets the client identify which message this is a response to if it has more than
	        //one outstanding message to the server
	        response.setJMSCorrelationID(bm.getStatus().getProcess() + "-" + bm.getStatus().getGuid());
	
	        //Send the response to the Destination specified by the JMSReplyTo field of the received message,
	        //this is presumably a temporary queue created by the client
	        replyProducer.send(mReplyTos.remove(bm.getStatus().getGuid()), response);
		}catch(JMSException e) {
			String em = "Error sending response message: " + print(bm);
            log.error(em,e);
            MessagingException.wrap(e, em);
		}
	}
	
	protected abstract MessageProtocol getMessageProtocol();
	
	@Override
	protected Session getSession(){
		return mSession;
	}
	
}
