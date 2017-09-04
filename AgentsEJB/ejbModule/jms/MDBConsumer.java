package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import agents.AgentOnMessageBeanInt;
import model_messages.ACLMessage;
import model_messages.JmsACLMessage;
import sun.management.resources.agent;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/agentsQueue") })
public class MDBConsumer implements MessageListener{

	@EJB
	private AgentOnMessageBeanInt agentOnMessageBean;
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if(!(message instanceof ObjectMessage)){
			return;
		}
		
		try{
			
			JmsACLMessage jmsMessage = (JmsACLMessage) ((ObjectMessage) message).getObject();
			
			switch (jmsMessage.getReciver().getType().getName()) {
			case "PING_AGENT":
					agentOnMessageBean.ping(jmsMessage);
				break;
			
			case "PONG_AGENT":
					agentOnMessageBean.pong(jmsMessage);
				break;
			
			case "CIA_AGENT":
					agentOnMessageBean.cia(jmsMessage);
				break;
				
			case "TAJNI_AGENT":
					agentOnMessageBean.tajni(jmsMessage);
				break;
			
			case "KGB_AGENT":
					agentOnMessageBean.kgb(jmsMessage);
				break;
			
			case "FBI_AGENT":
					agentOnMessageBean.fbi(jmsMessage);
				break;
				
			case "IZZY_AGENT":
					agentOnMessageBean.izzy(jmsMessage);
				break;
			
			default:
				System.out.println("Agent type is not define!");
				return;
			}
				
			
		}catch (Exception e) {
			System.out.println("Error handling message in MDBConsumer!");
		}
	
		
	}

}
