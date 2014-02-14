package tid.pce.pcep.objects.subobjects;

/**
 * AS Number ERO Subobject. RFC 3209
 *
 * Autonomous System Number Subobject

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |X|  Type = 4   |     Length    |      2-Octet AS Number        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * 
 * @author Oscar Gonzalez de Dios
 * @version 0.1
 */
public class ASNumberXROSubobject extends XROSubobject{
	
	private long ASNumber;//AS Number is a 32bit unsigned number. We set it to long to avoid problems.
	
	public ASNumberXROSubobject(){
		super();
		this.type=XROSubObjectValues.XRO_SUBOBJECT_ASNUMBER;
	}
	public ASNumberXROSubobject(byte []bytes, int offset){
		super(bytes, offset);
		decode();		
	}
	
	/**
	 * Decode ASNumber XRO Subobject
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
