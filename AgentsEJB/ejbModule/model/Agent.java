package model;

import java.io.Serializable;

public class Agent implements Serializable{

	private static final long serialVersionUID = 1L;

	private AID id;
	
	public Agent(){
		
	}
	
	public Agent(AID id){
		super();
		this.id = id;
	}

	public AID getId() {
		return id;
	}

	public void setId(AID id) {
		this.id = id;
	}
	
	
	
}

