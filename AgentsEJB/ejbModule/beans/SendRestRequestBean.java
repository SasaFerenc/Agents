package beans;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import bean_interfaces.AgentCenterBeanInt;
import bean_interfaces.SendRestRequestBeanInt;
import model.AgentCenter;
import model_accessories.Constants;
import model_accessories.RestURLConstants;
import model_messages.HandshakeRequestMessage;
import model_messages.HandshakeResponseMessage;

@Stateless
@Local(SendRestRequestBeanInt.class)
public class SendRestRequestBean implements SendRestRequestBeanInt {

	@EJB
	private AgentCenterBeanInt agentCenterBean;

	@Override
	public void sendNodeRegisterRequest(HandshakeRequestMessage handshakeRequestMessage, String sendToAddress)
			throws Exception {

		ResteasyClient client = new ResteasyClientBuilder().establishConnectionTimeout(2, TimeUnit.SECONDS)
				.socketTimeout(2, TimeUnit.SECONDS).build();

		ResteasyWebTarget target = client.target("http://" + sendToAddress + RestURLConstants.REGISTER_NODE);
		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(handshakeRequestMessage, MediaType.APPLICATION_JSON));

		HandshakeResponseMessage responseMessage = response.readEntity(new GenericType<HandshakeResponseMessage>() {
		});

		response.close();

		if (responseMessage == null) {
			throw new NullPointerException();
		}

		agentCenterBean.setActiveAgents(responseMessage.getActiveAgents());
		agentCenterBean.setAgentCenters(responseMessage.getAgentCenters());
		agentCenterBean.setAgentTypes(responseMessage.getAgentTypes());

	}

	@Override
	public Boolean sendCheckCenters(String sendToAddress) {

		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target("http://" + sendToAddress + RestURLConstants.CHECK_CENTERS);
		Builder request = resource.request();
		request.accept(MediaType.TEXT_PLAIN);
		boolean responseMessage = false;

		for (int i = 0; i < Constants.NUMBER_OF_TRIES; i++) {
			try {
				Response response = request.get();
				responseMessage = response.readEntity(new GenericType<Boolean>() {
				});
				response.close();
				break;
			} catch (Exception e) {

			}
		}

		return responseMessage;
	}

	@Override
	public void sendNodeDeleteRequest(ArrayList<AgentCenter> removeCenter, String sendToAddress) {
		System.out.println("SENDING REQUESTS TO DELTE");

		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target("http://" + sendToAddress + RestURLConstants.DELETE_NODE);
		Builder request = resource.request();
		Response response = request.post(Entity.entity(removeCenter, MediaType.APPLICATION_JSON));

		response.close();
	}

	@Override
	public void sendBadRegisterRequest(HandshakeRequestMessage message, String sendToAddress) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://" + sendToAddress + RestURLConstants.DELETE_BAD_REQUEST);
		Response response = target.request().post(Entity.entity(message.getAgentCenter(), MediaType.APPLICATION_JSON));

		response.close();

	}

}
