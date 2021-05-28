package es.tid.rsvp.objects;

public abstract class SenderTemplate extends RSVPObject{
	
	public SenderTemplate(){
		super();
		classNum = 11;
		}

	
	public SenderTemplate(byte[] bytes, int offset){
		super(bytes,offset);
				}

	public abstract void encode();

	public abstract void decode();

}
