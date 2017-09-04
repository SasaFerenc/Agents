package model_messages;

import java.io.Serializable;
import java.util.ArrayList;

import model.AgentCenter;
import model.AgentType;

public class HandshakeRequestMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private AgentCenter agentCenter;
	private ArrayList<AgentType> myAgentTypes;
	
	public HandshakeRequestMessage(){
		
	}

	public HandshakeRequestMessage(AgentCenter agentCenter, ArrayList<AgentType> myAgentTypes) {
		super();
		this.agentCenter = agentCenter;
		this.myAgentTypes = myAgentTypes;
	}

	public AgentCenter getAgentCenter() {
		return agentCenter;
	}

	public void setAgentCenter(AgentCenter agentCenter) {
		this.agentCenter = agentCenter;
	}

	public ArrayList<AgentType> getMyAgentTypes() {
		return myAgentTypes;
	}

	public void setMyAgentTypes(ArrayList<AgentType> myAgentTypes) {
		this.myAgentTypes = myAgentTypes;
	}
	
	
	
}
