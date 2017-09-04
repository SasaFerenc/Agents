package socket_model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.websocket.Session;

import model.Agent;
import model.AgentType;

public class SocketJmsMessage implements Serializable{

	private static final long serialVersionUID = 1L;

	private String session;
	private HashMap<String, ArrayList<AgentType>> agentTypes;
	private ArrayList<Agent> activeAgents;
	private boolean findAndStopAgent;
	private ArrayList<String> perfomatives;
	private String info;
	private SocketMessageTypes type;
	
	
	public SocketJmsMessage(){
		
	}

	
	
	
	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public HashMap<String, ArrayList<AgentType>> getAgentTypes() {
		return agentTypes;
	}

	public void setAgentTypes(HashMap<String, ArrayList<AgentType>> agentTypes) {
		this.agentTypes = agentTypes;
	}

	public ArrayList<Agent> getActiveAgents() {
		return activeAgents;
	}

	public void setActiveAgents(ArrayList<Agent> activeAgents) {
		this.activeAgents = activeAgents;
	}

	public boolean isFindAndStopAgent() {
		return findAndStopAgent;
	}

	public void setFindAndStopAgent(boolean findAndStopAgent) {
		this.findAndStopAgent = findAndStopAgent;
	}

	public ArrayList<String> getPerfomatives() {
		return perfomatives;
	}

	public void setPerfomatives(ArrayList<String> perfomatives) {
		this.perfomatives = perfomatives;
	}




	public String getInfo() {
		return info;
	}




	public void setInfo(String info) {
		this.info = info;
	}




	public SocketMessageTypes getType() {
		return type;
	}




	public void setType(SocketMessageTypes type) {
		this.type = type;
	}
	
	
	
}
