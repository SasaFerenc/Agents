package rest;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bean_interfaces.AgentCenterBeanInt;
import bean_interfaces.SendRestRequestBeanInt;
import model.AgentCenter;
import model.AgentType;
import model_messages.HandshakeRequestMessage;
import model_messages.HandshakeResponseMessage;

@Stateless
@Path("/handshake")
public class HandshakeRestService {

	@EJB
	private AgentCenterBeanInt agentCenterBean;

	@EJB
	private SendRestRequestBeanInt sendRestRequestBean;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registerNewNode")
	public HandshakeResponseMessage registerNewNode(HandshakeRequestMessage message){
		System.out.println("---------------------------------REQUEST------------------");
		
		if(alreadyInList(message.getAgentCenter().getAddress())){
			return new HandshakeResponseMessage(agentCenterBean.getAgentCenters(), agentCenterBean.getActiveAgents(),
					   agentCenterBean.getAgentTypes());
		}
		
		agentCenterBean.getAgentCenters().add(message.getAgentCenter());
		
		if(!agentCenterBean.getAgentTypes().containsKey(message.getAgentCenter().getAddress())){
			agentCenterBean.getAgentTypes().put(message.getAgentCenter().getAddress(), message.getMyAgentTypes());
		}
		
		if(agentCenterBean.isMaster()){
			
			for(AgentCenter center : agentCenterBean.getAgentCenters()){
				if(!center.getAddress().equals(agentCenterBean.getMyAgentCenter().getAddress()) &&
						!center.getAddress().equals(message.getAgentCenter().getAddress())){
					
					try{
						sendRestRequestBean.sendNodeRegisterRequest(message, center.getAddress());
					}catch (Exception e) {
						System.out.println("Nije uspeo da se registruje. Probaj ponovo.");
						try{
							sendRestRequestBean.sendNodeRegisterRequest(message, center.getAddress());
						}catch (Exception e1) {
							System.out.println("Ponovo nije uspeo. BRISI!");
							
							for(AgentCenter c : agentCenterBean.getAgentCenters()){
								if(!c.getAddress().equals(agentCenterBean.getMyAgentCenter().getAddress()) &&
										!c.getAddress().equals(message.getAgentCenter().getAddress()) &&
										c.getAddress().equals(center.getAddress())){
									
									sendRestRequestBean.sendBadRegisterRequest(message, c.getAddress());
								}
							}
						}
					}
				}
			}
			
			
		}
		
		return new HandshakeResponseMessage(agentCenterBean.getAgentCenters(), agentCenterBean.getActiveAgents(),
				   agentCenterBean.getAgentTypes());
	}

	private boolean alreadyInList(String address) {
		for(AgentCenter center : agentCenterBean.getAgentCenters()){
			if(center.getAddress().equals(address)){
				return true;
			}
		}
		return false;
	}

	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/checkCenters")
	public Boolean checkCenters(){
	//	System.out.println("BOOLEAN");
		return true;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteNode")
	public void deleteNode(ArrayList<AgentCenter> removeCenters){
		System.out.println("DELETING");
		agentCenterBean.deleteCenterInfo(removeCenters);
		/*
		for(AgentCenter removeCenter : removeCenters){
			for(AgentCenter center : agentCenterBean.getAgentCenters()){
				if(removeCenter.getAddress().equals(center.getAddress())){
					ArrayList<Agent> newActiveAgentsList = agentCenterBean.getActiveAgents();
					agentCenterBean.getAgentCenters().remove(center);
					agentCenterBean.getAgentTypes().remove(center.getAddress());
					
					for(Agent agent : agentCenterBean.getActiveAgents()){
						if(agent.getId().getHost().getAddress().equals(center.getAddress())){
							newActiveAgentsList.remove(agent);
						}
					}
					agentCenterBean.setActiveAgents(newActiveAgentsList);
					
					break;
				}
				
			}
		}*/
		
	}
	
	
	@GET
	@Path("/test")
	public void test(){
		System.out.println("TEST");
		HandshakeRequestMessage hrm = new HandshakeRequestMessage(new AgentCenter("alias", "120.0.0.1:8080"),new ArrayList<AgentType>());
		try {
			sendRestRequestBean.sendNodeRegisterRequest(hrm, "127.0.0.1:8080");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public void deleteNode(AgentCenter removeCenter){
		System.out.println("DELETING FROM BAD REQUEST");
		agentCenterBean.delete(removeCenter);
	}
	
}
