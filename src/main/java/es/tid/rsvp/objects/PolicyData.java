package es.tid.rsvp.objects;

import es.tid.rsvp.RSVPProtocolViolationException;

/*
 * 

   A.13 POLICY_DATA Class

      POLICY_DATA class = 14.

      o    Type 1 POLICY_DATA object: Class = 14, C-Type = 1

           The contents of this object are for further study.


 * 
 */

public class PolicyData extends RSVPObject{

	public PolicyData(){
		
		classNum = 14;
		cType = 1;
		
		
	}
	
public PolicyData(byte[] bytes, int offset) throws RSVPProtocolViolationException{
	super(bytes,offset);
	decode( );
}
	
	

	@Override
	public void encode() {
		length = 4;
		bytes = new byte[length];
		// TODO Auto-generated method stub
		encodeHeader();
	}


	
	public void decode(){
		// TODO Auto-generated method stub
		
	}

}
