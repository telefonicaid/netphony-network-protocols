package es.tid.rsvp.objects.subobjects;

/**
 * AS Number ERO Subobject. RFC 3209
 *
 * Section 4.3.3.4. Subobject 32:  Autonomous System Number
 *
 * The contents of an Autonomous System (AS) number subobject are a 2-
 * octet AS number.  The abstract node represented by this subobject is
 * the set of nodes belonging to the autonomous system.
 *
 * The length of the AS number subobject is 4 octets.
 * 
 * @author Oscar Gonzalez de Dios
 * @version 0.1
 */
public class ASNumberEROSubobject extends EROSubobject{
	
	private long ASNumber;//AS Number is a 32bit unsigned number. We set it to long to avoid problems.
	
	public ASNumberEROSubobject(){
		super();
		this.type=SubObjectValues.ERO_SUBOBJECT_ASNUMBER;
	}
	public ASNumberEROSubobject(byte []bytes, int offset){
		super(bytes, offset);
		decode();		
	}
	
	/**
	 * Decode ASNumber ERO Subobject
	 */
	public void decode(){
		int ASNumber = 0;		
		for (int k = 0; k < 4; k++) {
			ASNumber = (ASNumber << 8) | (subobject_bytes[k+4] & 0xff);
		}		
	}

	/**
	 * Decode Encode ERO Subobject
	 */
	public void encode(){
		this.subobject_bytes[4]=(byte)(ASNumber >>> 24);
		this.subobject_bytes[5]=(byte)(ASNumber >>> 16 & 0xff);
		this.subobject_bytes[6]=(byte)(ASNumber >>> 8 & 0xff);
		this.subobject_bytes[7]=(byte)(ASNumber & 0xff);	
	}
	
	public long getASNumber() {
		return ASNumber;
	}
	
	public void setASNumber(long aSNumber) {
		ASNumber = aSNumber;
	}
	
	@Override
	public String toString() {

		return String.valueOf(ASNumber);
	}
}
