package es.tid.rsvp.objects;

public abstract class FilterSpec extends RSVPObject{


	public abstract void encode();

	public abstract void decode(byte[] bytes, int offset);

}
