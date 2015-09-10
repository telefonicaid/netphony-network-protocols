package es.tid.rsvp.objects.subobjects.subtlvs;

public class SubTransponderTLVFS extends SubTLV {

	private int grid;
	private int channelSpacing;
	private int identifier;
	private int n;
	private int m;
	
	public SubTransponderTLVFS(){
		super();
		this.setTLVType(SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_FS);
	}
	
	public SubTransponderTLVFS(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	@Override
	public void encode() {
		
		int offset =4;
				
		tlv_bytes[offset]=(byte)((grid<<5)|(channelSpacing<<1)|(identifier>>>8));
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(identifier&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((n>>8)&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(n&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((m>>8)&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(m&0xFF);
		
		  
	}
	
	protected void decode(){
		
		int offset = 4;
		
		log.info("******************* Decodificando SubTransponderTLVFS *****************");
					
		grid=(tlv_bytes[offset]&0xE0)>>>5;
		channelSpacing=(tlv_bytes[offset]&0x1E)>>>1;
		identifier=((tlv_bytes[offset]&0x01)<<8)|(tlv_bytes[offset+1]&0xFF);
		n=((tlv_bytes[offset+2]&0xFF)<<8)|(tlv_bytes[offset+3]&0xFF);
		m=(((tlv_bytes[offset+4]&0xFF)<<8)|(tlv_bytes[offset+5]&0xFF));	
		
		log.info("Grid : " + grid + ".");
		log.info("Channel Spacing : " + channelSpacing + ".");
		log.info("Identifier : " + identifier + ".");
		log.info("n : " + n + ".");
		log.info("m : " + m + ".");
		
		log.info("***************** FIN Decodificando SubTransponderTLVFS ***************");
				
	}
	
	public String toString(){
		String str =  "<SubTransponderTLVID" + " Grid: " + grid + "\n Channel Spacing: " + channelSpacing + "\n Identifier: " + identifier + "\n n: " + n + "\n m: " + m;
		str+=">";
		return str;
	}

	public int getGrid() {
		return grid;
	}

	public void setGrid(int grid) {
		this.grid = grid;
	}

	public int getChannelSpacing() {
		return channelSpacing;
	}

	public void setChannelSpacing(int channelSpacing) {
		this.channelSpacing = channelSpacing;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}
	
}
