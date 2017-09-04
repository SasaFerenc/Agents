package agents;

import javax.ejb.Local;

import model_messages.JmsACLMessage;

@Local
public interface AgentOnMessageBeanInt {

	public void ping(JmsACLMessage message);
	public void pong(JmsACLMessage message);
	public void kgb(JmsACLMessage message);
	public void cia(JmsACLMessage message);
	public void fbi(JmsACLMessage message);
	public void izzy(JmsACLMessage message);
	public void tajni(JmsACLMessage message);
	
}
