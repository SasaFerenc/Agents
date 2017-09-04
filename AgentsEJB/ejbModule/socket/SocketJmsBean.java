package socket;

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

import model_messages.JmsACLMessage;
import socket_model.SocketJmsMessage;

@Stateless
@Local(SocketJmsBeanInt.class)
public class SocketJmsBean implements SocketJmsBeanInt{

	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory factory;

	@Resource(mappedName = "java:/jms/queue/socketQueue")
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
	public void sendMessage(SocketJmsMessage message) {
		// TODO Auto-generated method stub
		try {
			
			ObjectMessage objectMessage = session.createObjectMessage(message);
			sender.send(objectMessage);

		} catch (JMSException e) {
			System.out.println("Error sending JMS back to SocketService!");
			e.printStackTrace();
		} 
	}
	
}
