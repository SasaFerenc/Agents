package beans;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import bean_interfaces.AgentCenterBeanInt;
import bean_interfaces.SendRestRequestBeanInt;
import model.Agent;
import model.AgentCenter;
import model.AgentType;
import model_accessories.AgentTypes_8080;
import model_accessories.AgentTypes_8090;
import model_accessories.AgentTypes_8100;
import model_accessories.Constants;

@Singleton
@Local(AgentCenterBeanInt.class)
public class AgentCenterBean implements AgentCenterBeanInt {

	private AgentCenter myAgentCenter;
	private boolean master;
	private ArrayList<AgentType> myAgentTypes;
	private HashMap<String, ArrayList<AgentType>> agentTypes;

	private ArrayList<AgentCenter> agentCenters;
	private ArrayList<Agent> activeAgents;

	@EJB
	private SendRestRequestBeanInt sendRestRequestBean;

	@Override
	@PostConstruct
	public void initialise() {
		// TODO: podesiti arej liste.. voditi racuna za MYAGETNS kako ce
		// uzimati..
		this.agentCenters = new ArrayList<AgentCenter>();
		this.myAgentTypes = new ArrayList<AgentType>();
		this.agentTypes = new HashMap<String, ArrayList<AgentType>>();
		this.activeAgents = new ArrayList<Agent>();
	}

	@Schedule(hour = "*", minute = "*", second = "*/"
			+ Constants.TIMER_CHECK_CENTERS, persistent = false, info = "every " + Constants.TIMER_CHECK_CENTERS
					+ " secound")
	public void checkCenters() {
		// System.out.println("PROVERAVAM");
		ArrayList<AgentCenter> removeCenter = new ArrayList<AgentCenter>();

		for (AgentCenter center : agentCenters) {
			if (!center.getAddress().equals(myAgentCenter.getAddress())) {
				if (!sendRestRequestBean.sendCheckCenters(center.getAddress())) {
					// TODO Ubiti center
				//	 System.out.println("NE ODGOVARA");
					removeCenter.add(center);
				} else {
					// TODO NISTA
				//	 System.out.println("ODGOVARA");
				}
			}
		}

		if (!removeCenter.isEmpty()) {
			deleteCenterInfo(removeCenter);

			for (AgentCenter center : agentCenters) {
				if (!center.getAddress().equals(myAgentCenter.getAddress())) {
					// sendRestRequestBean.sendNodeDeleteRequest(removeCenter,
					// center.getAddress());
				}
			}

		}

	}

	@Override
	public void deleteCenterInfo(ArrayList<AgentCenter> removeCenters) {
		System.out.println("DELETE CENTER INFO");

		ArrayList<AgentCenter> newAgentCenterList = agentCenters;
		ArrayList<Agent> newActiveAgentsList = activeAgents;
		HashMap<String, ArrayList<AgentType>> newAgentTypesList = agentTypes;

		for (AgentCenter removeMe : removeCenters) {

			for (AgentCenter center : agentCenters) {
				if (center.getAddress().equals(removeMe.getAddress())) {
					newAgentCenterList.remove(center);
					break;
				}
			}

			for (Agent agent : activeAgents) {
				if (agent.getId().getHost().getAddress().equals(removeMe.getAddress())) {
					newActiveAgentsList.remove(agent);
				}
			}

			if (newAgentTypesList.containsKey(removeMe.getAddress())) {
				newAgentTypesList.remove(removeMe.getAddress());
			}

		}

		agentCenters = newAgentCenterList;
		activeAgents = newActiveAgentsList;
		agentTypes = newAgentTypesList;

	}

	@Override
	public void delete(AgentCenter removeCenter) {
		// TODO Auto-generated method stub
		for (AgentCenter center : agentCenters) {
			if (removeCenter.equals(center.getAddress())) {
				agentCenters.remove(center);
				break;
			}
		}

		if (agentTypes.containsKey(removeCenter.getAddress())) {
			agentTypes.remove(removeCenter.getAddress());
		}

	}

	@Override
	public void setMyTypes() {

		switch (myAgentCenter.getAddress().split(":")[1]) {
		case "8080":
			for (AgentTypes_8080 myType : AgentTypes_8080.values()) {
				myAgentTypes.add(new AgentType(myType.name()));
			}
			break;
		case "8090":
			for (AgentTypes_8090 myType : AgentTypes_8090.values()) {
				myAgentTypes.add(new AgentType(myType.name()));
			}
			break;
		case "8100":
			for (AgentTypes_8100 myType : AgentTypes_8100.values()) {
				myAgentTypes.add(new AgentType(myType.name()));
			}
			break;
		default:
			System.out.println("NIJE PODRZAN PORT ZA TIPOVE!");
		}

	}

	@Override
	public AgentCenter getMyAgentCenter() {
		return myAgentCenter;
	}

	@Override
	public void setMyAgentCenter(AgentCenter myAgentCenter) {
		this.myAgentCenter = myAgentCenter;
	}

	@Override
	public boolean isMaster() {
		return master;
	}

	@Override
	public void setMaster(boolean master) {
		this.master = master;
	}

	@Override
	public ArrayList<AgentType> getMyAgentTypes() {
		return myAgentTypes;
	}

	@Override
	public void setMyAgentTypes(ArrayList<AgentType> myAgentTypes) {
		this.myAgentTypes = myAgentTypes;
	}

	@Override
	public HashMap<String, ArrayList<AgentType>> getAgentTypes() {
		return agentTypes;
	}

	@Override
	public void setAgentTypes(HashMap<String, ArrayList<AgentType>> agentTypes) {
		this.agentTypes = agentTypes;
	}

	@Override
	public ArrayList<AgentCenter> getAgentCenters() {
		return agentCenters;
	}

	@Override
	public void setAgentCenters(ArrayList<AgentCenter> agentCenters) {
		this.agentCenters = agentCenters;
	}

	@Override
	public ArrayList<Agent> getActiveAgents() {
		return activeAgents;
	}

	@Override
	public void setActiveAgents(ArrayList<Agent> activeAgents) {
		this.activeAgents = activeAgents;
	}

}
