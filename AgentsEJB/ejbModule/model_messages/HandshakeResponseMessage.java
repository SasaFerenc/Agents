package model_messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import model.Agent;
import model.AgentCenter;
import model.AgentType;
import sun.management.resources.agent;

public class HandshakeResponseMessage implements Serializable{

	private static final long serialVersionUID = 1L;

	private ArrayList<AgentCenter> agentCenters;
	private ArrayList<Agent> activeAgents;
	private HashMap<String, ArrayList<AgentType>> agentTypes;
	
	public HandshakeResponseMessage(){
		this.activeAgents= new ArrayList<Agent>();
		this.agentCenters = new ArrayList<AgentCenter>();
		this.agentTypes = new HashMap<String, ArrayList<AgentType>>();
	}

	public HandshakeResponseMessage(ArrayList<AgentCenter> agentCenters, ArrayList<Agent> activeAgents,
			HashMap<String, ArrayList<AgentType>> agentTypes) {
		super();
		this.agentCenters = agentCenters;
		this.activeAgents = activeAgents;
		this.agentTypes = agentTypes;
	}

	public ArrayList<AgentCenter> getAgentCenters() {
		return agentCenters;
	}

	public void setAgentCenters(ArrayList<AgentCenter> agentCenters) {
		this.agentCenters = agentCenters;
	}

	public ArrayList<Agent> getActiveAgents() {
		return activeAgents;
	}

	public void setActiveAgents(ArrayList<Agent> activeAgents) {
		this.activeAgents = activeAgents;
	}

	public HashMap<String, ArrayList<AgentType>> getAgentTypes() {
		return agentTypes;
	}

	public void setAgentTypes(HashMap<String, ArrayList<AgentType>> agentTypes) {
		this.agentTypes = agentTypes;
	}

	
	
}

