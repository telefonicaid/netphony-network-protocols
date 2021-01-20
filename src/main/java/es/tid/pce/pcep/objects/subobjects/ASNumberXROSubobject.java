package es.tid.pce.pcep.objects.subobjects;

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
		this.ASNumber = 0;		
		for (int k = 0; k < 2; k++) {
			ASNumber = (ASNumber << 8) | (subobject_bytes[k+2] & 0xff);
		}		
	}

	/**
	 * Decode Encode ERO Subobject
	 */
	public void encode(){
		this.erosolength=4;
		this.subobject_bytes=new byte[this.erosolength];
		encodeSoHeader();
		this.subobject_bytes[2]=(byte)(ASNumber >>> 8 & 0xff);
		this.subobject_bytes[3]=(byte)(ASNumber & 0xff);	
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
