package model_messages;

import java.io.Serializable;

import model.AID;

public class JmsACLMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	private ACLMessage aclMessage;
	private AID reciver;
	
	public JmsACLMessage(){
		
	}
	
	public JmsACLMessage(ACLMessage aclMessage, AID reciver) {
		super();
		this.aclMessage = aclMessage;
		this.reciver = reciver;
	}
	public ACLMessage getAclMessage() {
		return aclMessage;
	}
	public void setAclMessage(ACLMessage aclMessage) {
		this.aclMessage = aclMessage;
	}
	public AID getReciver() {
		return reciver;
	}
	public void setReciver(AID reciver) {
		this.reciver = reciver;
	}
	
	
	
}
