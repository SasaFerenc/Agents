package socket;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.websocket.Session;

@Singleton
@Local(SessionBeanInt.class)
public class SessionBean implements SessionBeanInt{

	private HashMap<String, Session> sessions;
	
	@PostConstruct
	public void initialise(){
		this.setSessions(new HashMap<String,Session>());
	}

	@Override
	public HashMap<String, Session> getSessions() {
		return sessions;
	}

	@Override
	public void setSessions(HashMap<String, Session> sessions) {
		this.sessions = sessions;
	}
	
	
	
	
}
