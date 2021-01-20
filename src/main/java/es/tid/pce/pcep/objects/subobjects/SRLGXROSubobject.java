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
		int offset=2;
		this.subobject_bytes[offset]=(byte)((SRLGID>>24) & 0xFF);
		this.subobject_bytes[offset+1]=(byte)((SRLGID>>16) & 0xFF);
		this.subobject_bytes[offset+2]=(byte)((SRLGID>>8) & 0xFF);
		this.subobject_bytes[offset+3]=(byte)(SRLGID & 0xFF);
		this.subobject_bytes[offset+4]=0;
		this.subobject_bytes[offset+5]=(byte)attribute;
	}

	
	public void decode() {
		int offset=2;
		SRLGID=( (((long)this.subobject_bytes[offset]&(long)0xFF)<<24) | (((long)this.subobject_bytes[offset+1]&(long)0xFF)<<16) |( ((long)this.subobject_bytes[offset+2]&(long)0xFF)<<8) |  ((long)this.subobject_bytes[offset+3]& (long)0xFF) );
		attribute=subobject_bytes[offset+5]&0xFF;
	}
	public long getSRLGID() {
		return SRLGID;
	}
	public void setSRLGID(long sRLGID) {
		SRLGID = sRLGID;
	}

	
}
