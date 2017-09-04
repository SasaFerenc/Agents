package beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

import bean_interfaces.AgentJmsServiceBeanInt;
import model.AID;
import model_messages.ACLMessage;
import model_messages.JmsACLMessage;

@Stateless
@Local(AgentJmsServiceBeanInt.class)
public class AgentJmsServiceBean implements AgentJmsServiceBeanInt {


	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory factory;

	@Resource(mappedName = "java:/jms/queue/agentsQueue")
	private Queue agentQueue;

	private Connection connection;
	private QueueSender sender;
	private QueueSession session;

	@PostConstruct
	public void initialise() {
		try {
			this.connection = factory.createConnection();
			connection.start();
			this.session = (QueueSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			this.sender = session.createSender(agentQueue);
		} catch (JMSException e) {
			return;
		}

	}

	@PreDestroy
	public void destroy() {
		try {
			connection.stop();
			sender.close();
		} catch (JMSException e) {
		}
	}
	
	
	@Override
	public void sendMessageToConsumer(ACLMessage message, AID reciver) {
		// TODO Auto-generated method stub
		
		try {
			JmsACLMessage acl = new JmsACLMessage(message, reciver);
			
			ObjectMessage objectMessage = session.createObjectMessage(acl);
			sender.send(objectMessage);

		} catch (JMSException e) {
			System.out.println("Error sending JMS to MDBConsumer!");
			// e.printStackTrace();
		} 
	}
	
	
}
