package es.tid.rsvp.objects.subobjects.subtlvs;

public class SubTransponderTLVFEC extends SubTLV {

	private int standardizedFormat;
	private int input; 
	private int FEC_id;
	
	
	public SubTransponderTLVFEC(){
		super();
		this.setTLVType(SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_FEC);
	}
	
	public SubTransponderTLVFEC(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	
	@Override
	public void encode() {
		this.setTotalTLVLength(8);			
		int offset =4;
		
		tlv_bytes[offset]=(byte)((standardizedFormat<<7)|(input<<6)|(FEC_id>>>8));
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(FEC_id&0xFF);
		
			  
	}
	
	protected void decode(){
		
		int offset = 4;
		
		standardizedFormat=(tlv_bytes[offset]&0xE0)>>>7;
		input=(tlv_bytes[offset]&0x1E)>>>6;
		FEC_id=((tlv_bytes[offset]&0x01)<<8)|(tlv_bytes[offset+1]&0xFF);
				
		log.info("Standardized Format : " + standardizedFormat + ".");
		log.info("Input : " + input + ".");
		log.info("FEC ID : " + FEC_id + ".");
				
	}
	
	public String toString(){
		String str =  "[STFEC " + " SF: " + standardizedFormat + "I: " + input + "FEC ID: " + FEC_id; 
		str+="]";
		return str;
	}

	public int getStandardizedFormat() {
		return standardizedFormat;
	}

	public void setStandardizedFormat(int standardizedFormat) {
		this.standardizedFormat = standardizedFormat;
	}

	public int getInput() {
		return input;
	}

	public void setInput(int input) {
		this.input = input;
	}

	public int getFEC_id() {
		return FEC_id;
	}

	public void setFEC_id(int fEC_id) {
		FEC_id = fEC_id;
	}
	
	
	
	
}
