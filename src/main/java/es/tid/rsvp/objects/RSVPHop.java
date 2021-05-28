package es.tid.rsvp.objects;

import es.tid.rsvp.RSVPProtocolViolationException;

public abstract class RSVPHop extends RSVPObject{

	public RSVPHop () {
		super();
	}
	
	public RSVPHop (byte[] bytes, int offset) throws RSVPProtocolViolationException{
		super(bytes, offset);
	}
	public abstract void encode();



	public abstract void decode() throws RSVPProtocolViolationException;
	

}