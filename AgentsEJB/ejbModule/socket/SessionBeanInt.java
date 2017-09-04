package socket;

import java.util.HashMap;

import javax.ejb.Local;
import javax.websocket.Session;

@Local
public interface SessionBeanInt {

	public HashMap<String, Session> getSessions();

	public void setSessions(HashMap<String, Session> sessions);
	
	
}
