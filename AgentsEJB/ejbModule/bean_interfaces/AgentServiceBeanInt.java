package bean_interfaces;

import javax.ejb.Local;

import model_messages.ACLMessage;

@Local
public interface AgentServiceBeanInt {

	public void sendRestOrJms(ACLMessage message);
}
