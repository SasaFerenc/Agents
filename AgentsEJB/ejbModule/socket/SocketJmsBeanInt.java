package socket;

import javax.ejb.Local;

import socket_model.SocketJmsMessage;

@Local
public interface SocketJmsBeanInt {

	public void sendMessage(SocketJmsMessage message);
	
}
