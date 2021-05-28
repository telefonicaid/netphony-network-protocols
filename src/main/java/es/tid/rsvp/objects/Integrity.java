package es.tid.rsvp.objects;

import es.tid.rsvp.RSVPProtocolViolationException;

/* RFC 2205                          RSVP                    September 1997
 *    A.3 INTEGRITY Class

      INTEGRITY class = 4.

      See [Baker96].

 */

public class Integrity extends RSVPObject{

	public Integrity(){
		
		classNum = 4;
		cType = 1;	
	}
	
	public Integrity(byte[] bytes, int offset) throws RSVPProtocolViolationException{
		super(bytes,offset);
		decode( );
	}
	/*
	 *    A.3 INTEGRITY Class

      INTEGRITY class = 4.

      See [Baker96].

	 */
	
	@Override
	public void encode() {
		length = 4;
		bytes = new byte[length];
		// TODO Auto-generated method stub
		encodeHeader();
	}

	
	public void decode() {
		// TODO Auto-generated method stub
		
	}

}
