package bean_interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Local;

import model.Agent;
import model.AgentCenter;
import model.AgentType;

@Local
public interface AgentCenterBeanInt {
	
	public void initialise();
	public AgentCenter getMyAgentCenter();
	public void setMyAgentCenter(AgentCenter myAgentCenter);
	public boolean isMaster();
	public void setMaster(boolean master);
	public ArrayList<AgentType> getMyAgentTypes();
	public void setMyAgentTypes(ArrayList<AgentType> myAgentTypes);
	public HashMap<String, ArrayList<AgentType>> getAgentTypes();
	public void setAgentTypes(HashMap<String, ArrayList<AgentType>> agentTypes);
	public ArrayList<AgentCenter> getAgentCenters();
	public void setAgentCenters(ArrayList<AgentCenter> agentCenters); 
	public ArrayList<Agent> getActiveAgents();
	public void setActiveAgents(ArrayList<Agent> activeAgents); 
	public void deleteCenterInfo(ArrayList<AgentCenter> removeCenters);
	public void delete(AgentCenter removeCenter);
	public void setMyTypes();
}
