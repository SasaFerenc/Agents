package model;

import java.io.Serializable;

import model_accessories.Constants;

public class AgentType implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String module;
	
	public AgentType(){
		this.module = Constants.AGENT_TYPE_MODULE;
	}

	public AgentType(String name) {
		super();
		this.name = name;
		this.module = Constants.AGENT_TYPE_MODULE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	
}
