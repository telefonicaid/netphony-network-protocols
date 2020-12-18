package es.tid.rsvp.objects;

public abstract class ErrorSpec extends RSVPObject{

	public ErrorSpec(byte[] bytes, int offset){
		super(bytes, offset);
		this.decode(bytes,offset);
	}
	
	public ErrorSpec(){
		super();
	}
	
	public abstract void encode();

	public abstract void decode(byte[] bytes, int offset);

}
