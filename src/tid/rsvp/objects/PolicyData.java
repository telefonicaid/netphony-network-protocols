package tid.rsvp.objects;

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
		length = 4;
		bytes = new byte[length];
		
	}
	

	@Override
	public void encode() {
		// TODO Auto-generated method stub
		encodeHeader();
	}


	@Override
	public void decode(byte[] bytes, int offset){
		// TODO Auto-generated method stub
		
	}

}
