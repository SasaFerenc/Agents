package socket_service;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.websocket.Session;

import bean_interfaces.AgentCenterBeanInt;
import bean_interfaces.SendRestKlientBeanInt;
import model.AgentCenter;
import service.ServiceBeanInt;
import socket.SocketJmsBeanInt;
import socket_model.SocketJmsMessage;
import socket_model.SocketMessage;
import socket_model.SocketMessageTypes;

@Stateless
@Local(KlientSocketServiceBeanInt.class)
public class KlientSocketServiceBean implements KlientSocketServiceBeanInt{

	@EJB
	private AgentCenterBeanInt agentCenterBean;
	
	@EJB
	private ServiceBeanInt serviceBean;
	
	@EJB
	private SocketJmsBeanInt socketJmsBean;
	
	@EJB
	private SendRestKlientBeanInt sendRestKlientBean;
	
	@Override
	public void activeAgent(SocketMessage message) {
		SocketJmsMessage jmsMessage = new SocketJmsMessage();
		
		jmsMessage.setInfo(serviceBean.runAgent(message.getActiveAgent().getType(), message.getActiveAgent().getName()));
		jmsMessage.setType(SocketMessageTypes.ACTIVE_AGENT);
		
			
		socketJmsBean.sendMessage(jmsMessage);
		
		updateEveryCenter(jmsMessage);
	}

	@Override
	public void sendACLMessage(SocketMessage message, Session session) {
		SocketJmsMessage jmsMessage = new SocketJmsMessage();
		
		jmsMessage.setInfo(serviceBean.sendMessage(message.getAclMessage()));
		jmsMessage.setType(SocketMessageTypes.SEND_ACLMESSAGE);
		jmsMessage.setSession(session.getId());
		
		socketJmsBean.sendMessage(jmsMessage);
	}

	@Override
	public void getAgents( Session session) {
		SocketJmsMessage jmsMessage = new SocketJmsMessage();
		
		jmsMessage.setActiveAgents(serviceBean.getActiveAgents());
		jmsMessage.setType(SocketMessageTypes.GET_AGENTS);
		jmsMessage.setSession(session.getId());
		
		socketJmsBean.sendMessage(jmsMessage);
	}

	@Override
	public void getPerformative( Session session) {
		SocketJmsMessage jmsMessage = new SocketJmsMessage();
		
		jmsMessage.setPerfomatives(serviceBean.getPerformative());
		jmsMessage.setType(SocketMessageTypes.GET_PERFROMATIVE);
		jmsMessage.setSession(session.getId());
		
		socketJmsBean.sendMessage(jmsMessage);		
	}

	@Override
	public void getTypes( Session session) {
		SocketJmsMessage jmsMessage = new SocketJmsMessage();
		
		jmsMessage.setAgentTypes(serviceBean.getAgentTypes());
		jmsMessage.setType(SocketMessageTypes.GET_TYPES);
		jmsMessage.setSession(session.getId());
		
		socketJmsBean.sendMessage(jmsMessage);	
	}

	@Override
	public void stopAgent(SocketMessage message) {
		SocketJmsMessage jmsMessage = new SocketJmsMessage();
		
		jmsMessage.setInfo(serviceBean.stopAgent(message.getStopAgentAID()));
		jmsMessage.setType(SocketMessageTypes.STOP_AGENT);
		
		socketJmsBean.sendMessage(jmsMessage);	
		
		updateEveryCenter(jmsMessage);
	}

	
	private void updateEveryCenter(SocketJmsMessage jmsMessage){
		
		for(AgentCenter center : agentCenterBean.getAgentCenters()){
			if(!center.getAddress().equals(agentCenterBean.getMyAgentCenter().getAddress())){
				sendRestKlientBean.updateSessions(jmsMessage, center.getAddress());
			}
		}
		
		
	}
	
	
}
