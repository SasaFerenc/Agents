package model_accessories;

public class Constants {
	
	public static final String MASTER_ID = "master";
	public static final String MASTER_ADDRESS = "127.0.0.1";
	public static final int MASTER_DEFAULT_PORT = 8080;
	public static final String MASTER_ADDRES_PORT = MASTER_ADDRESS + ":" + MASTER_DEFAULT_PORT;
	
	
	public static final String SLAVE_ID = "slave";
	public static final String OFFSET_ID = "jboss.socket.binding.port-offset";
	public static final String ALIAS_ID = "alias";

	
	public static final String AGENT_TYPE_MODULE = "/AgentsEJB/ejbModule/model/Agent.java";
	
	
	//TIMER SETUP
	public static final int TIMER_CHECK_CENTERS = 15;
	public static final int NUMBER_OF_TRIES = 2;
}
