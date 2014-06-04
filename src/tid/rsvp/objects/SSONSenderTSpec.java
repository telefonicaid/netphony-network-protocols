package tid.rsvp.objects;

import tid.rsvp.objects.SenderTSpec;



/**
 *
   A.11 SENDER_TSPEC Class

      SENDER_TSPEC class = 12.
 * 
 *
 *Traffic Spec encoding.
 * 
 *  The SSON traffic parameters are organized as follows: 
 *  0                   1                   2                   3 
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 
 *   0              1               2               3
    +---------------+---------------+---------------+---------------+
    |       Length (bytes)          |   Class-Num   |     C-Type    |
    +---------------+---------------+---------------+---------------+
    |                                                               |
    //                     (Object contents)                        //
    |                                                               |
 *  +---------------+---------------+---------------+---------------+
 *  
 *  
 *  SSON Traffic Spec
 *  
 *   o    SSON SENDER_TSPEC object: Class = 12, C-Type = to be assigned by IANA, preferred 8
 *  
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ 
 *  |     m         |                      Reserved                 |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  
 **/


public class SSONSenderTSpec extends SenderTSpec {


	public int m;
	
	public SSONSenderTSpec(){
		cType = 8;
		length = 8;
		bytes=new byte[length];
	}
	
	
	@Override
	public void encode(){
		encodeHeader();
		// TODO Auto-generated method stub	
		this.bytes[4]=(byte)(m);
		this.bytes[5]=0x00;
		this.bytes[6]=0x00;
		this.bytes[7]=0x00;
	}


	@Override
	public void decode(byte[] bytes, int offset){
		decodeHeader(bytes, offset);
		offset=offset+4;
		m=bytes[offset]&0xFF;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer(4);
		sb.append("M (slots)= ");
		sb.append(m);
		return sb.toString();
	}

}
