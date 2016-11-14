import static com.akmade.messaging.Utility.*;

import java.util.HashMap;
import java.util.function.Consumer;

import javax.jms.JMSException;

import com.akmade.messaging.Utility;
import com.akmade.messaging.api.CallbackQueue;
import com.akmade.messaging.api.ServiceController;
import com.akmade.messaging.api.senders.demoperson.DemoPersonSender;

import gov.noaa.alaskafisheries.messaging.dto.MessagingDTO.BusMessage;
import gov.noaa.alaskafisheries.messaging.dto.MessagingDTO.BusMessage.BusMessageStatus;

public class Test extends ServiceController{

	DemoPersonSender p;
	
	BusMessageStatus bms = BusMessageStatus.newBuilder()
			.setGuid("11111")
			.setProcess("test")
			.setValid(true)
			.build();
	BusMessage busMessage = BusMessage.newBuilder()
				.setStatus(bms)
				.build();
	Consumer<BusMessage> gp = bm -> p.getPeople(bm.getStatus());
	Consumer<BusMessage> printReply = bm -> System.out.println("Print reply: " + Utility.print(bm));
	Consumer<BusMessage> findJohnChangeToJenny =
			bm -> p.changeFirstName(bm.getStatus(), 
				bm.getPersonList().getPersonList()
					.stream()
					.filter(x -> x.getFirstName().equalsIgnoreCase("John"))
					.findFirst()
					.get(),
					"Jenny");
	
	private void getAllPeople(){
		CallbackQueue q = CallbackQueue.newBuilder("test", "11111")
				.startWith(gp)
				.finishWith(printReply)
				.build();
		addCallback(q.start(busMessage));
	}
	
	private void changeJohntoJenny(){
		CallbackQueue q = CallbackQueue.newBuilder("test", "11111")
				.startWith(gp)
				.thenDo(findJohnChangeToJenny)
				.thenDo(gp)
				.finishWith(printReply)
				.build();
		addCallback(q.start(busMessage));
	}
	
	public void test() throws JMSException {
		p = new DemoPersonSender() {
			
			@Override
			protected HashMap<String, CallbackQueue> getCallbackQueues() {
				return mCallbackQueues;
			}

			@Override
			protected void handleUndeliveredMessage(BusMessage bm) {
				log.error("Message never delivered to the Api Gateway." + print(bm));
			}
		};
	
		changeJohntoJenny();
		
	
	}
	
	public static void main (String[] args) throws JMSException{
		new Test().test();
	}

}
