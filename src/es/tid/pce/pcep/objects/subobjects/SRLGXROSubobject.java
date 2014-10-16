package es.tid.pce.pcep.objects.subobjects;

/**
 * SRLG XRO Subobject.
 * 
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |X|  Type = 5   |     Length    |       SRLG Id (4 bytes)       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |      SRLG Id (continued)      |    Reserved   |  Attribute    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * @author ogondio
 *
 */
public class SRLGXROSubobject extends XROSubobject {

	private long SRLGID;
	
	
	public SRLGXROSubobject(){
		super();
		this.type=XROSubObjectValues.XRO_SUBOBJECT_SRLG;
		attribute=2;
	}
	public SRLGXROSubobject(byte []bytes, int offset){
		super(bytes, offset);
		decode();		
	}
	
	
	public void encode() {
		this.erosolength=8;
		this.subobject_bytes=new byte[this.erosolength];
		encodeSoHeader();
		this.subobject_bytes[6]=(byte)((SRLGID>>24) & 0xFF);
		this.subobject_bytes[7]=(byte)((SRLGID>>16) & 0xFF);
		this.subobject_bytes[8]=(byte)((SRLGID>>8) & 0xFF);
		this.subobject_bytes[9]=(byte)(SRLGID & 0xFF);
		this.subobject_bytes[10]=(byte)attribute;
		this.subobject_bytes[11]=(byte)attribute;
	}

	
	public void decode() {
		SRLGID=( (((long)this.subobject_bytes[6]&(long)0xFF)<<24) | (((long)this.subobject_bytes[7]&(long)0xFF)<<16) |( ((long)this.subobject_bytes[8]&(long)0xFF)<<8) |  ((long)this.subobject_bytes[9]& (long)0xFF) );
		attribute=subobject_bytes[11]&0xFF;
	}

}
