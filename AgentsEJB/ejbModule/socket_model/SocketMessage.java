package socket_model;

import java.io.Serializable;

import model.AID;
import model_messages.ACLMessage;

public class SocketMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AID stopAgentAID;
	private ActiveAgent activeAgent;
	private ACLMessage aclMessage;
	private SocketMessageTypes type;
	
	
	public SocketMessage() {
	}
	
	public SocketMessage(SocketMessageTypes type){
		this.type = type;
	}
	
	

	public SocketMessage(AID stopAgentAID, SocketMessageTypes type) {
		super();
		this.stopAgentAID = stopAgentAID;
		this.type = type;
	}

	public SocketMessage(ActiveAgent activeAgent, SocketMessageTypes type) {
		super();
		this.activeAgent = activeAgent;
		this.type = type;
	}

	public SocketMessage(ACLMessage aclMessage, SocketMessageTypes type) {
		super();
		this.aclMessage = aclMessage;
		this.type = type;
	}


	public ACLMessage getAclMessage() {
		return aclMessage;
	}
	
	
	public void setAclMessage(ACLMessage aclMessage) {
		this.aclMessage = aclMessage;
	}
	public SocketMessageTypes getType() {
		return type;
	}
	public void setType(SocketMessageTypes type) {
		this.type = type;
	}

	public ActiveAgent getActiveAgent() {
		return activeAgent;
	}

	public void setActiveAgent(ActiveAgent activeAgent) {
		this.activeAgent = activeAgent;
	}

	public AID getStopAgentAID() {
		return stopAgentAID;
	}

	public void setStopAgentAID(AID stopAgentAID) {
		this.stopAgentAID = stopAgentAID;
	}
	
	
	
}
