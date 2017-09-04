package service;

import java.io.IOException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import socket.SessionBeanInt;
import socket_model.SocketJmsMessage;
import socket_model.SocketMessage;
import socket_service.KlientSocketServiceBeanInt;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/socketQueue") })
@ServerEndpoint("/socketService")
public class SocketService implements MessageListener{

	@EJB
	private SessionBeanInt sessionBean;
	
	@EJB
	private KlientSocketServiceBeanInt klientSocketServiceBean;
	
	
	@OnOpen
	public void open(Session session) {
		
		if(!sessionBean.getSessions().containsKey(session.getId())){
			sessionBean.getSessions().put(session.getId(), session);
		}
	}

	@OnClose
	public void close(Session session) {
		if(sessionBean.getSessions().containsKey(session.getId())){
			sessionBean.getSessions().remove(session.getId());
		}	
	}

	@OnError
	public void onError(Throwable error) {
	}

	@OnMessage
	public void handleMessage(String message, Session session) {
		
		ObjectMapper mapper = new ObjectMapper();
		SocketMessage socketMessage;
		
		try {
			socketMessage = mapper.readValue(message,  SocketMessage.class);
			
			
			switch (socketMessage.getType()) {
			case SEND_ACLMESSAGE:
				klientSocketServiceBean.sendACLMessage(socketMessage, session);
				break;
			case ACTIVE_AGENT:
				klientSocketServiceBean.activeAgent(socketMessage);				
				break;
			case GET_AGENTS:
				klientSocketServiceBean.getAgents(session);
				break;
			case GET_PERFROMATIVE:
				klientSocketServiceBean.getPerformative(session);
				break;
			case GET_TYPES:
				klientSocketServiceBean.getTypes(session);
				break;
			case STOP_AGENT:
				klientSocketServiceBean.stopAgent(socketMessage);
				break;
				
			default:
				System.out.println("Socket message type is not define!");
				break;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	
	//jms
	@Override
	public void onMessage(Message message) {
		
		if(!(message instanceof ObjectMessage)){
			return;
		}
		
		try{
			SocketJmsMessage jmsMessage = (SocketJmsMessage) ((ObjectMessage) message).getObject();
			ObjectMapper mapper = new ObjectMapper();
			String out = mapper.writeValueAsString(jmsMessage);
			
			switch (jmsMessage.getType()) {
			case ACTIVE_AGENT:
				sendToAllSessions(jmsMessage,out);				
				break;
			case STOP_AGENT:
				sendToAllSessions(jmsMessage, out);
				break;
			default:
				if(sessionBean.getSessions().containsKey(jmsMessage.getSession())){
					sessionBean.getSessions().get(jmsMessage.getSession()).getBasicRemote().sendText(out);
				}
				break;
			}
			
			
		}catch (Exception e) {
			System.out.println("Error handling message in SocketService!");
			e.printStackTrace();
		}
	
		
	}

	private void sendToAllSessions(SocketJmsMessage jmsMessage, String out) {
		for(Session s : sessionBean.getSessions().values()){
			try {
				s.getBasicRemote().sendText(out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
	
	
	
	
	
	
	
	
}
