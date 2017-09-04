package beans;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import bean_interfaces.SendRestKlientBeanInt;
import model.AID;
import model.Agent;
import model_accessories.RestURLConstants;
import model_messages.ACLMessage;
import model_messages.JmsACLMessage;
import socket_model.SocketJmsMessage;

@Stateless
@Local(SendRestKlientBeanInt.class)
public class SendRestKlientBean implements SendRestKlientBeanInt {

	@Override
	public void addNewAgent(Agent agent, String addressToSend) {
		
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://" + addressToSend + RestURLConstants.ADD_ACTIVE_AGENT);
		Response response = target.request().post(Entity.entity(agent, MediaType.APPLICATION_JSON));

		response.close();
		
	}

	@Override
	public void findAndStopAgent(AID agentAID, String address) {
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://" + address + RestURLConstants.FIND_AND_STOP_AGENT);
		Response response = target.request(MediaType.TEXT_PLAIN).post(Entity.entity(agentAID, MediaType.APPLICATION_JSON));

		response.close();
	}

	@Override
	public void sendACLMessage(ACLMessage message, AID reciver) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		JmsACLMessage aclMessage = new JmsACLMessage(message,reciver);
		
		ResteasyWebTarget target = client.target("http://" + reciver.getHost().getAddress() + RestURLConstants.SEND_ACLMESSAGE);
		Response response = target.request(MediaType.TEXT_PLAIN).post(Entity.entity(aclMessage, MediaType.APPLICATION_JSON));

		response.close();
	}

	@Override
	public void updateSessions(SocketJmsMessage jmsMessage, String address) {
		// TODO Auto-generated method stub
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://" + address + RestURLConstants.UPDATE_SESSIONS);
		Response response = target.request().post(Entity.entity(jmsMessage, MediaType.APPLICATION_JSON));

		response.close();
	}
	
	

}
