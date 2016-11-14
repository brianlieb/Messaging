package com.akmade.messaging.api;

import static com.akmade.messaging.Utility.print;
import static com.akmade.messaging.Utility.readBusMessage;
import static com.akmade.messaging.Utility.readStringProperty;

import java.util.function.Function;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;

import com.akmade.messaging.exception.MessagingException;

import com.akmade.messaging.dto.MessagingDTO.BusMessage;


public abstract class Messenger implements AutoCloseable, MessageListener {
	
	public static final Function<TextMessage,BusMessage> RESPONSE_BUS_MESSAGE_PROTOCOL =
			m -> readBusMessage(readStringProperty(m, "response")).build();

	public static final Function<TextMessage,BusMessage> REQUEST_BUS_MESSAGE_PROTOCOL =
			m -> readBusMessage(readStringProperty(m, "request")).build();
			
	public static final Function<TextMessage,BusMessage> PUBLISH_BUS_MESSAGE_PROTOCOL =
			m -> readBusMessage(readStringProperty(m, "publish")).build();	
	
	public static final Logger log = Logger.getLogger(Messenger.class);

	protected static final String BROKER_URL = "tcp://localhost:61616";
	
	private static final int ackMode = Session.CLIENT_ACKNOWLEDGE;
	private boolean transacted = false;
	protected Connection mConnection;
	protected Session mSession;
	protected Destination mDestination;
	
    
	
	private static BrokerService broker;
	
	private static void startBrokerService() {
		try{
		if(broker != null && !broker.isStarted()){
			BrokerService broker = new BrokerService();
	     	broker.setPersistent(false);
	     	broker.setUseJmx(false);
	     	broker.addConnector(BROKER_URL);
	     	broker.start();
		}
		}catch(Exception e){
			log.error("Could Not start Broker Service");
			MessagingException.wrap(e, "Unable to start Broker Service: " + e.getMessage());
		}
	}

    protected Messenger() throws MessagingException{
    	startBrokerService();
    	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
    	try{
    		RedeliveryPolicy p = connectionFactory.getRedeliveryPolicy();
    		p.setMaximumRedeliveries(1);
    		p.setMaximumRedeliveryDelay(100l);
    		p.setInitialRedeliveryDelay(50l);
    		mConnection = connectionFactory.createConnection();
    		mConnection.start();
    		mSession = mConnection.createSession(transacted, ackMode);
    	}catch(JMSException e){
    		log.error("Could not connect to Active MQ");
			throw MessagingException.wrap(e, "Error connection to Active MQ");
		}
		
    }
    
    
	@Override
	public void close() throws Exception {
		try {
			mConnection.close();
			mConnection = null;
		} catch (JMSException e) {
			log.error("Error closing the connection to Active MQ");
			MessagingException.wrap(e, "Error closing the connection to Active MQ");
		}
	}
	
	
	
	protected abstract Session getSession();
	
	
	
	@FunctionalInterface
	public interface MessageProtocol {

		static  TextMessage responseMessage(Session session, String jmsType, BusMessage msg){
			TextMessage response = null;
			try {
				response = session.createTextMessage();
				response.setJMSType(jmsType);
				response.setStringProperty("response", print(msg));
			} catch (JMSException e) {
				String m = "Error creating a resonse with Protobuf: " + print(msg);
				log.debug(m);
				MessagingException.wrap(e, m);
			}
			return response;
			
		}
		
		public void handle(BusMessage bm);
	}	
	
	@FunctionalInterface
	public interface MessageAction extends Function<BusMessage,BusMessage>{}
	

}
