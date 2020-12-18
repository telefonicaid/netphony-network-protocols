package es.tid.rsvp.objects;

public abstract class Session extends RSVPObject{
	
	public Session(){
		super();
	}
	public Session(byte[] bytes, int offset){
		super(bytes, offset);
	}

}
