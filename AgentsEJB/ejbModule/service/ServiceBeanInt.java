package service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Local;

import model.AID;
import model.Agent;
import model.AgentType;
import model_messages.ACLMessage;
import model_messages.JmsACLMessage;

@Local
public interface ServiceBeanInt {

	public HashMap<String, ArrayList<AgentType>> getAgentTypes();

	public ArrayList<Agent> getActiveAgents();
	public String runAgent(String agentType,String agentName);
	public void addActiveAgent(Agent agent);
	public String stopAgent(AID agentAID);
	public Boolean findAndStopAgent(AID agentAID);
	public String sendMessage(ACLMessage message);
	public String sendMessageToAgent(JmsACLMessage message);
	public ArrayList<String> getPerformative();
		
}
