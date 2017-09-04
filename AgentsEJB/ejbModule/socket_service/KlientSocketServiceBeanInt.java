package socket_service;

import javax.ejb.Local;
import javax.websocket.Session;

import socket_model.SocketMessage;

@Local
public interface KlientSocketServiceBeanInt {

	public void sendACLMessage(SocketMessage message, Session session);
	public void activeAgent(SocketMessage message);
	public void getAgents(Session session);	
	public void getPerformative(Session session);
	public void getTypes(Session session);
	public void stopAgent(SocketMessage message);
}
