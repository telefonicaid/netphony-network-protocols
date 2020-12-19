package es.tid.rsvp.objects;

import es.tid.rsvp.RSVPProtocolViolationException;

public abstract class Session extends RSVPObject{
	
	public Session(){
		super();
	}
	
	public Session(byte[] bytes, int offset) throws RSVPProtocolViolationException{
		super(bytes, offset);
	}

}
