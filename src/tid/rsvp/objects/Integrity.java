package tid.rsvp.objects;

/* RFC 2205                          RSVP                    September 1997
 *    A.3 INTEGRITY Class

      INTEGRITY class = 4.

      See [Baker96].

 */

public class Integrity extends RSVPObject{

	public Integrity(){
		
		classNum = 4;
		cType = 0;
		length = 4;
		bytes = new byte[length];
		
	}
	
	/*
	 *    A.3 INTEGRITY Class

      INTEGRITY class = 4.

      See [Baker96].

	 */
	
	@Override
	public void encode() {
		// TODO Auto-generated method stub
		encodeHeader();
	}

	@Override
	public void decode(byte[] bytes, int offset) {
		// TODO Auto-generated method stub
		
	}

}
