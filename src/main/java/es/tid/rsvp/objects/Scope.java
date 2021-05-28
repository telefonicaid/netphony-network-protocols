package es.tid.rsvp.objects;

import es.tid.rsvp.RSVPProtocolViolationException;

public abstract class Scope extends RSVPObject{

	public Scope () {
		super();
	}
	
	public Scope (byte[] bytes, int offset) throws RSVPProtocolViolationException{
		super(bytes, offset);
	}
	
	public abstract void encode();




}
