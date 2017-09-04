package rest;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.AID;
import model.Agent;
import model.AgentType;
import model_messages.ACLMessage;
import model_messages.JmsACLMessage;
import service.ServiceBeanInt;
import socket.SocketJmsBeanInt;
import socket_model.SocketJmsMessage;

@Stateless
@Path("/klient")
public class KlientRestService {

	@EJB
	private ServiceBeanInt serviceBean;
	
	@EJB
	private SocketJmsBeanInt socketJmsBean;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/agentTypes")
	public HashMap<String, ArrayList<AgentType>> getAgentTypes(){
		return serviceBean.getAgentTypes();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/activeAgents")
	public ArrayList<Agent> getActiveAgents(){
		return serviceBean.getActiveAgents();
	}
	
	
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/run/{type}/{name}")
	public String runAgent(@PathParam("type") String agentType, @PathParam("name") String agentName){
		return serviceBean.runAgent(agentType, agentName);
	}
	

	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addActiveAgent")
	public void addActiveAgent(Agent agent){
		serviceBean.addActiveAgent(agent);
	}
	
	
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/stop")
	public String stopAgent(AID agentAID){
		return serviceBean.stopAgent(agentAID);
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/stopAgent")
	public Boolean findAndStopAgent(AID agentAID) {
		return serviceBean.findAndStopAgent(agentAID);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/send")
	public String sendMessage(ACLMessage message){
		return serviceBean.sendMessage(message);		
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/sendAclMessage")
	public String sendMessageToAgent(JmsACLMessage message){
		
		return serviceBean.sendMessageToAgent(message);
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/performative")
	public ArrayList<String> getPerformative(){
		return serviceBean.getPerformative();	
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateSessions")
	public void updateSessions(SocketJmsMessage jmsMessage){
		socketJmsBean.sendMessage(jmsMessage);
	}
	
	
	
}
