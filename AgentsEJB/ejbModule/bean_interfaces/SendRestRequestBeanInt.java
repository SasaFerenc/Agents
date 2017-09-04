package bean_interfaces;

import java.util.ArrayList;

import javax.ejb.Local;

import model.AgentCenter;
import model_messages.HandshakeRequestMessage;

@Local
public interface SendRestRequestBeanInt {

	public void sendNodeRegisterRequest(HandshakeRequestMessage handshakeRequestMessage, String sendToAddress) throws Exception;

	public Boolean sendCheckCenters(String sendToAddress);

	public void sendNodeDeleteRequest(ArrayList<AgentCenter> removeCenter, String sendToAddress);

	public void sendBadRegisterRequest(HandshakeRequestMessage message, String sendToAddress);
}
