package beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import bean_interfaces.AgentCenterBeanInt;
import bean_interfaces.NodeInitialiseBeanInt;
import bean_interfaces.SendRestRequestBeanInt;
import model.AgentCenter;
import model_accessories.Constants;
import model_messages.HandshakeRequestMessage;

@Startup
@Singleton
@Local(NodeInitialiseBeanInt.class)
public class NodeInitialiseBean implements NodeInitialiseBeanInt{

	@EJB
	private AgentCenterBeanInt agentCenterBean;
	
	@EJB
	private SendRestRequestBeanInt sendRestRequestBean;
	
	@Override
	@PostConstruct
	public void initialise() {
		if(System.getProperty(Constants.SLAVE_ID) == null || 
				System.getProperty(Constants.SLAVE_ID).equals("")){
			AgentCenter agentCenter = new AgentCenter(Constants.ALIAS_ID, Constants.MASTER_ADDRES_PORT);
			agentCenterBean.setMyAgentCenter(agentCenter);
			agentCenterBean.setMaster(true);
			agentCenterBean.setMyTypes();
			agentCenterBean.getAgentTypes().put(agentCenterBean.getMyAgentCenter().getAddress(), agentCenterBean.getMyAgentTypes());
			
			agentCenterBean.getAgentCenters().add(agentCenter);
			System.out.println("Master initialise!");
			
		}else{
			AgentCenter slaveAgentCenter = createSlaveAgentCenter();
			
			if(slaveAgentCenter == null){
				return;
			}
			
			agentCenterBean.setMyAgentCenter(slaveAgentCenter);
			agentCenterBean.setMaster(false);
			agentCenterBean.setMyTypes();
			agentCenterBean.getAgentTypes().put(agentCenterBean.getMyAgentCenter().getAddress(), agentCenterBean.getMyAgentTypes());
			
			
			try {
				sendRestRequestBean.sendNodeRegisterRequest(new HandshakeRequestMessage(slaveAgentCenter, agentCenterBean.getMyAgentTypes()),
																Constants.MASTER_ADDRES_PORT);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("NISAM PRONASAO MASTERA!");
			}
			
			System.out.println("Slave initialise");
			
		}
	
	}



	private AgentCenter createSlaveAgentCenter() {
		String myAddress = System.getProperty(Constants.SLAVE_ID);
		String myAlias = System.getProperty(Constants.ALIAS_ID);
		int offset = 0;
		
		
		if(System.getProperty(Constants.OFFSET_ID) != null && !System.getProperty(Constants.OFFSET_ID).equals("")){
			offset = Integer.parseInt(System.getProperty(Constants.OFFSET_ID)) + Constants.MASTER_DEFAULT_PORT;
		}
		
		if(myAddress != null && !myAddress.equals("")
				&& myAlias != null && !myAlias.equals("")
				&& offset != Constants.MASTER_DEFAULT_PORT){
			
			AgentCenter agentCenter = new AgentCenter(myAlias, myAddress + ':' + offset);	
			return agentCenter;
		}
		
		return null;
	}

	
	
	
	
}
