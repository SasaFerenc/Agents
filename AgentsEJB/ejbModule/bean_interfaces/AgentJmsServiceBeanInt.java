package bean_interfaces;

import javax.ejb.Local;

import model.AID;
import model_messages.ACLMessage;

@Local
public interface AgentJmsServiceBeanInt {

	public void sendMessageToConsumer(ACLMessage message, AID reciver);
	
}
