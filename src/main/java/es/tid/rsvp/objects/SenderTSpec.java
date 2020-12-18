package es.tid.rsvp.objects;
/**
 *    A.11 SENDER_TSPEC Class

      SENDER_TSPEC class = 12.
           
 * @author mcs
 *
 */
public abstract class SenderTSpec extends RSVPObject{

	public SenderTSpec(){
		//dar valores de clase 12 y ctype 2
		classNum = 12;
	}
	public SenderTSpec(byte[] bytes, int offset){
		super(bytes,offset);
	}
	
}
