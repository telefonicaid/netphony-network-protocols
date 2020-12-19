package es.tid.rsvp.objects;

public abstract class ResvConfirm extends RSVPObject{


	public ResvConfirm(){
		super();
	}
	
	public ResvConfirm(byte[] bytes, int offset){
		super(bytes, offset);
	}

	public abstract void encode();



	public abstract void decode();

}
