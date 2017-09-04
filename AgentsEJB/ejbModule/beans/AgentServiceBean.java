package beans;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import bean_interfaces.AgentCenterBeanInt;
import bean_interfaces.AgentJmsServiceBeanInt;
import bean_interfaces.AgentServiceBeanInt;
import bean_interfaces.SendRestKlientBeanInt;
import model.AID;
import model_messages.ACLMessage;

@Singleton
@Local(AgentServiceBeanInt.class)
public class AgentServiceBean implements AgentServiceBeanInt{

	@EJB
	private AgentCenterBeanInt agentCenterBean;
	
	@EJB
	private SendRestKlientBeanInt sendRestKlientBean;
	
	@EJB
	private AgentJmsServiceBeanInt agentJmsServiceBean;
	
	@Override
	public void sendRestOrJms(ACLMessage message) {
		for(AID aid : message.getReceivers()){
			if(aid.getHost().getAddress().equals(agentCenterBean.getMyAgentCenter().getAddress())){
				agentJmsServiceBean.sendMessageToConsumer(message, aid);
			}else{
				sendRestKlientBean.sendACLMessage(message, aid);
			}
		}
	}

}
