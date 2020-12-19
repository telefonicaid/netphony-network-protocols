package es.tid.rsvp.objects;

public abstract class FilterSpec extends RSVPObject{

	public FilterSpec(byte[] bytes, int offset){
		super(bytes, offset);
	}
	
	public FilterSpec(){
		super();
	}
	
	public abstract void encode();

	public abstract void decode();

}
