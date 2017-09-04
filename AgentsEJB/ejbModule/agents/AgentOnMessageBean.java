package agents;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import bean_interfaces.AgentServiceBeanInt;
import model.AID;
import model_accessories.Performative;
import model_messages.ACLMessage;
import model_messages.JmsACLMessage;

@Stateless
@Local(AgentOnMessageBeanInt.class)
public class AgentOnMessageBean implements AgentOnMessageBeanInt{

	@EJB
	private AgentServiceBeanInt agentServiceBean;
	
	@Override
	public void ping(JmsACLMessage message) {
		// TODO Auto-generated method stub
		
		switch (message.getAclMessage().getPerformative()) {
		case REQUEST:
			
			System.out.println(message.getReciver().getName() + " (PING_AGENT) GOT REQUEST FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			
			ACLMessage newMessage = new ACLMessage();
			
			newMessage.setSender(message.getReciver());
			newMessage.setContent(message.getReciver().getName() + ": I got your message. Thanks!");
			newMessage.setPerformative(Performative.INFORM);
			newMessage.setReplyTo(null);			
			
			ArrayList<AID> resivers = new ArrayList<AID>();
			resivers.add(message.getAclMessage().getReplyTo());
			newMessage.setReceivers(resivers);
			
			agentServiceBean.sendRestOrJms(newMessage);
			
			break;

		case INFORM:
			
			System.out.println(message.getReciver().getName() + " (PING_AGENT) GOT INFORM FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			
			break;
			
		default:
			System.out.println("Performative is not define!");
		}
		
		
	}

	@Override
	public void pong(JmsACLMessage message) {
		// TODO Auto-generated method stub
		switch (message.getAclMessage().getPerformative()) {
		case REQUEST:
			
			System.out.println(message.getReciver().getName() + " (PONG_AGENT) GOT REQUEST FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			
			ACLMessage newMessage = new ACLMessage();
			
			newMessage.setSender(message.getReciver());
			newMessage.setContent(message.getReciver().getName() + ": I got your message. Thanks!");
			newMessage.setPerformative(Performative.INFORM);
			newMessage.setReplyTo(null);			
			
			ArrayList<AID> resivers = new ArrayList<AID>();
			resivers.add(message.getAclMessage().getReplyTo());
			newMessage.setReceivers(resivers);
			
			agentServiceBean.sendRestOrJms(newMessage);
			break;

		case INFORM:
			
			System.out.println(message.getReciver().getName() + " (PONG_AGENT) GOT INFORM FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			break;
			
		default:
			System.out.println("Performative is not define!");
		}
	}

	@Override
	public void kgb(JmsACLMessage message) {
		// TODO Auto-generated method stub
		switch (message.getAclMessage().getPerformative()) {
		case REQUEST:
			System.out.println(message.getReciver().getName() + " (KGB_AGENT) GOT REQUEST FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			
			ACLMessage newMessage = new ACLMessage();
			
			newMessage.setSender(message.getReciver());
			newMessage.setContent(message.getReciver().getName() + ": I got your message. Thanks!");
			newMessage.setPerformative(Performative.INFORM);
			newMessage.setReplyTo(null);			
			
			ArrayList<AID> resivers = new ArrayList<AID>();
			resivers.add(message.getAclMessage().getReplyTo());
			newMessage.setReceivers(resivers);
			
			agentServiceBean.sendRestOrJms(newMessage);
			break;

		case INFORM:
			System.out.println(message.getReciver().getName() + " (KGB_AGENT) GOT INFORM FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			break;
			
		default:
			System.out.println("Performative is not define!");
		}
	}

	@Override
	public void cia(JmsACLMessage message) {
		// TODO Auto-generated method stub
		switch (message.getAclMessage().getPerformative()) {
		case REQUEST:
			System.out.println(message.getReciver().getName() + " (CIA_AGENT) GOT REQUEST FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			
			ACLMessage newMessage = new ACLMessage();
			
			newMessage.setSender(message.getReciver());
			newMessage.setContent(message.getReciver().getName() + ": I got your message. Thanks!");
			newMessage.setPerformative(Performative.INFORM);
			newMessage.setReplyTo(null);			
			
			ArrayList<AID> resivers = new ArrayList<AID>();
			resivers.add(message.getAclMessage().getReplyTo());
			newMessage.setReceivers(resivers);
			
			agentServiceBean.sendRestOrJms(newMessage);
			break;

		case INFORM:
			System.out.println(message.getReciver().getName() + " (CIA_AGENT) GOT INFORM FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			break;
			
		default:
			System.out.println("Performative is not define!");
		}
	}

	@Override
	public void fbi(JmsACLMessage message) {
		// TODO Auto-generated method stub
		switch (message.getAclMessage().getPerformative()) {
		case REQUEST:
			System.out.println(message.getReciver().getName() + " (FBI_AGENT) GOT REQUEST FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			
			ACLMessage newMessage = new ACLMessage();
			
			newMessage.setSender(message.getReciver());
			newMessage.setContent(message.getReciver().getName() + ": I got your message. Thanks!");
			newMessage.setPerformative(Performative.INFORM);
			newMessage.setReplyTo(null);			
			
			ArrayList<AID> resivers = new ArrayList<AID>();
			resivers.add(message.getAclMessage().getReplyTo());
			newMessage.setReceivers(resivers);
			
			agentServiceBean.sendRestOrJms(newMessage);
			break;

		case INFORM:
			System.out.println(message.getReciver().getName() + " (FBI_AGENT) GOT INFORM FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			break;
			
		default:
			System.out.println("Performative is not define!");
		}
	}

	@Override
	public void izzy(JmsACLMessage message) {
		// TODO Auto-generated method stub
		switch (message.getAclMessage().getPerformative()) {
		case REQUEST:
			System.out.println(message.getReciver().getName() + " (IZZY_AGENT) GOT REQUEST FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			
			ACLMessage newMessage = new ACLMessage();
			
			newMessage.setSender(message.getReciver());
			newMessage.setContent(message.getReciver().getName() + ": I got your message. Thanks!");
			newMessage.setPerformative(Performative.INFORM);
			newMessage.setReplyTo(null);			
			
			ArrayList<AID> resivers = new ArrayList<AID>();
			resivers.add(message.getAclMessage().getReplyTo());
			newMessage.setReceivers(resivers);
			
			agentServiceBean.sendRestOrJms(newMessage);
			break;

		case INFORM:
			System.out.println(message.getReciver().getName() + " (IZZY_AGENT) GOT INFORM FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			break;
			
		default:
			System.out.println("Performative is not define!");
		}
	}

	@Override
	public void tajni(JmsACLMessage message) {
		// TODO Auto-generated method stub
		switch (message.getAclMessage().getPerformative()) {
		case REQUEST:
			System.out.println(message.getReciver().getName() + " (TAJNI_AGENT) GOT REQUEST FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			
			ACLMessage newMessage = new ACLMessage();
			
			newMessage.setSender(message.getReciver());
			newMessage.setContent(message.getReciver().getName() + ": I got your message. Thanks!");
			newMessage.setPerformative(Performative.INFORM);
			newMessage.setReplyTo(null);			
			
			ArrayList<AID> resivers = new ArrayList<AID>();
			resivers.add(message.getAclMessage().getReplyTo());
			newMessage.setReceivers(resivers);
			
			agentServiceBean.sendRestOrJms(newMessage);
			break;

		case INFORM:
			System.out.println(message.getReciver().getName() + " (TAJNI_AGENT) GOT INFORM FROM: " 
					+ message.getAclMessage().getSender().getName()
					+ " (" + message.getAclMessage().getSender().getType().getName() + ")");
			 
			System.out.println("Message content: " + message.getAclMessage().getContent());
			break;
			
		default:
			System.out.println("Performative is not define!");
		}
	}

	
	
}
