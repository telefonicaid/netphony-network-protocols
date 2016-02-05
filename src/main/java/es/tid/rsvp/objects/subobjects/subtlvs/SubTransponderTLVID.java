package es.tid.rsvp.objects.subobjects.subtlvs;

/**
 * 
 *  0                   1                   2                   3
 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|        Type = 5005            |           Length = 8          |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                       SubTransponder ID                       |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 *
 */
public class SubTransponderTLVID extends SubTLV {

	private long id;
	
	public SubTransponderTLVID(){
		super();
		this.setTLVType(SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_ID);
	}
	
	public SubTransponderTLVID(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	@Override
	public void encode() {
		this.setTotalTLVLength(8);					
		int offset = 4;
		
		tlv_bytes[offset]=(byte)((id>>24) & 0xFF);
	    tlv_bytes[offset+1]=(byte)((id>>16) & 0xFF);
		tlv_bytes[offset+2]=(byte)((id>>8) & 0xFF);
		tlv_bytes[offset+3]=(byte)(id & 0xFF);	
		
	}
	
	protected void decode(){
		
		int offset = 4;
		id = 0;
		
		for (int k = 0; k < 4; k++) {
			id = (id << 8) | ((long)tlv_bytes[k+offset] & (long)0xff);
		}
		
	}
	
	public String toString(){
		String str =  "[STID: " + id +"]";
		return str;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
