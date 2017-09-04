package service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import bean_interfaces.AgentCenterBeanInt;
import bean_interfaces.AgentJmsServiceBeanInt;
import bean_interfaces.AgentServiceBeanInt;
import bean_interfaces.SendRestKlientBeanInt;
import model.AID;
import model.Agent;
import model.AgentCenter;
import model.AgentType;
import model_accessories.Constants;
import model_accessories.Performative;
import model_messages.ACLMessage;
import model_messages.JmsACLMessage;

@Stateless
@Local(ServiceBeanInt.class)
public class ServiceBean implements ServiceBeanInt {

	@EJB
	private AgentCenterBeanInt agentCenterBean;

	@EJB
	private SendRestKlientBeanInt sendRestKlientBean;

	@EJB
	private AgentJmsServiceBeanInt agentJmsServiceBean;

	@EJB
	private AgentServiceBeanInt agentServiceBean;

	private Agent createAgent(String agentType, String agentName) {

		String port = agentType.split(":")[1];
		String typeName = agentType.split("_" + Constants.MASTER_ADDRESS)[0];
		String address = Constants.MASTER_ADDRESS + ":" + port;

		for (Agent a : agentCenterBean.getActiveAgents()) {
			if (a.getId().getName().equals(agentName) && a.getId().getType().getName().equals(typeName)) {
				return null;
			}
		}

		AgentType type = new AgentType(typeName);
		AgentCenter cen = null;

		for (AgentCenter center : agentCenterBean.getAgentCenters()) {
			if (center.getAddress().equals(address)) {
				cen = center;
				break;
			}
		}

		if (cen == null) {
			return null;
		}

		AID aid = new AID(agentName, cen, type);
		Agent newAgent = new Agent(aid);

		return newAgent;
	}

	@Override
	public HashMap<String, ArrayList<AgentType>> getAgentTypes() {
		// TODO Auto-generated method stub
		return agentCenterBean.getAgentTypes();
	}

	@Override
	public ArrayList<Agent> getActiveAgents() {
		// TODO Auto-generated method stub
		return agentCenterBean.getActiveAgents();
	}

	@Override
	public String runAgent(String agentType, String agentName) {

		Agent newAgent = createAgent(agentType, agentName);

		if (newAgent == null) {
			return "Already exist agent with same name and type!";
		}

		System.out.println("STARTED: " + newAgent.getId().getName() + " agent.");
		agentCenterBean.getActiveAgents().add(newAgent);

		for (AgentCenter center : agentCenterBean.getAgentCenters()) {

			if (!center.getAddress().equals(agentCenterBean.getMyAgentCenter().getAddress())) {
				sendRestKlientBean.addNewAgent(newAgent, center.getAddress());
			}

		}
		return "Agent started!";
	}

	@Override
	public void addActiveAgent(Agent agent) {
		// TODO Auto-generated method stub
		boolean flag = false;

		for (Agent a : agentCenterBean.getActiveAgents()) {
			if (a.getId().getName().equals(agent.getId().getName())
					&& a.getId().getType().getName().equals(agent.getId().getType().getName())) {
				flag = true;
				break;
			}
		}

		if (!flag) {

			System.out.println("STARTED: " + agent.getId().getName() + " agent.");
			agentCenterBean.getActiveAgents().add(agent);
		}
	}

	@Override
	public String stopAgent(AID agentAID) {

		if (!findAndStopAgent(agentAID)) {
			return "Agent not found.";
		}

		for (AgentCenter center : agentCenterBean.getAgentCenters()) {

			if (!center.getAddress().equals(agentCenterBean.getMyAgentCenter().getAddress())) {
				sendRestKlientBean.findAndStopAgent(agentAID, center.getAddress());
			}

		}

		return "Agent stoped!";
	}

	@Override
	public Boolean findAndStopAgent(AID agentAID) {

		for (Agent agent : agentCenterBean.getActiveAgents()) {
			if (agent.getId().getName().equals(agentAID.getName())
					&& agent.getId().getType().getName().equals(agentAID.getType().getName())) {

				agentCenterBean.getActiveAgents().remove(agent);
				return true;
			}

		}

		return false;
	}

	@Override
	public String sendMessage(ACLMessage message) {
		agentServiceBean.sendRestOrJms(message);

		return "Message sent!";
	}

	@Override
	public String sendMessageToAgent(JmsACLMessage message) {
		if (message.getReciver().getHost().getAddress().equals(agentCenterBean.getMyAgentCenter().getAddress())) {
			agentJmsServiceBean.sendMessageToConsumer(message.getAclMessage(), message.getReciver());
		}

		return "Message sent!";
	}

	@Override
	public ArrayList<String> getPerformative() {
		ArrayList<String> performatives = new ArrayList<String>();

		for (Performative per : Performative.values()) {
			performatives.add(per.name());
		}

		return performatives;
	}
}
