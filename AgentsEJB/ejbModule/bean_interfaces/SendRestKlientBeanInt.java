package bean_interfaces;

import javax.ejb.Local;

import model.AID;
import model.Agent;
import model_messages.ACLMessage;
import socket_model.SocketJmsMessage;

@Local
public interface SendRestKlientBeanInt {
	public void addNewAgent(Agent agent, String addressToSend);

	public void findAndStopAgent(AID agentAID, String address);

	public void sendACLMessage(ACLMessage message, AID address);
	
	public void updateSessions(SocketJmsMessage jmsMessage, String address);
}
