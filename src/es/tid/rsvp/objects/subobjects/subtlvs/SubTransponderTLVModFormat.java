package es.tid.rsvp.objects.subobjects.subtlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class SubTransponderTLVModFormat extends SubTLV {

	
	private int standardizedFormat;
	private int input; 
	private int modulationID;
	private long symbolRate;	
	private int numCarriers;
	private int bitSymbol;
	
	public SubTransponderTLVModFormat(){
		super();
		this.setTLVType(SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_MOD_FORMAT);
	}
	
	public SubTransponderTLVModFormat(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	@Override
	public void encode() {
		
		int offset =4;
		
		tlv_bytes[offset]=(byte)((standardizedFormat<<7)|(input<<6)|(modulationID>>>8));
		
		offset = offset + 1;
		
		//tlv_bytes[offset]=(byte)(modulationID&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((symbolRate>>24) & 0xFF);
		
		offset = offset + 1;
		
	    tlv_bytes[offset]=(byte)((symbolRate>>16) & 0xFF);
	    
	    offset = offset + 1;
	    
		tlv_bytes[offset]=(byte)((symbolRate>>8) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(symbolRate & 0xFF);
		
		offset = offset + 1;
				
		tlv_bytes[offset]=(byte)((numCarriers>>8)&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(numCarriers&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((bitSymbol>>8)&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(bitSymbol&0xFF);
		
		  
	}
	
	protected void decode(){
		
		
		int offset = 4;
		
		log.info("******************* Decodificando TCAA *****************");
		
		//Comprobar y revisar 
		standardizedFormat=(tlv_bytes[offset]&0xE0)>>>7;
		input=(tlv_bytes[offset]&0x1E)>>>6;
		modulationID=((tlv_bytes[offset]&0x01)<<8)|(tlv_bytes[offset+1]&0xFF);
		symbolRate=((tlv_bytes[offset+2]&0xFF)<<8)|(tlv_bytes[offset+3]&0xFF);
		numCarriers=(((tlv_bytes[offset+4]&0xFF)<<8)|(tlv_bytes[offset+5]&0xFF));
		bitSymbol=(((tlv_bytes[offset+4]&0xFF)<<8)|(tlv_bytes[offset+5]&0xFF));
		
		
		log.info("Standardized Format : " + standardizedFormat + ".");
		log.info("Input : " + input + ".");
		log.info("ModulationID : " + modulationID + ".");
		log.info("Symbol Rate : " + symbolRate + ".");
		log.info("Number Carriers : " + numCarriers + ".");
		log.info("Bits/Symbol : " + bitSymbol + ".");
		
		log.info("***************** FIN Decodificando SubTransponderTLVModFormat ***************");
	}
	
	public String toString(){
		String str =  "<SubTransponderTLVModFormat" + " Standardized Format: " + standardizedFormat + "\n Input: " + input + "\n ModulationID: " + modulationID + "\n Symbol Rate: " + symbolRate + "\n Number Carriers: " + numCarriers + "\n Bits/Symbol: " + bitSymbol;
		str+=">";
		return str;
	}	
	

	public int getstandardizedFormat() {
		return standardizedFormat;
	}

	public void setstandardizedFormat(int standardizedFormat) {
		this.standardizedFormat = standardizedFormat;
	}

	public int getInput() {
		return input;
	}

	public void setInput(int input) {
		this.input = input;
	}

	public int getModulationID() {
		return modulationID;
	}

	public void setModulationID(int modulationID) {
		this.modulationID = modulationID;
	}

	public long getSymbolRate() {
		return symbolRate;
	}

	public void setSymbolRate(long symbolRate) {
		this.symbolRate = symbolRate;
	}

	public int getNumCarriers() {
		return numCarriers;
	}

	public void setNumCarriers(int numCarriers) {
		this.numCarriers = numCarriers;
	}

	public int getBitSymbol() {
		return bitSymbol;
	}

	public void setBitSymbol(int bitSymbol) {
		this.bitSymbol = bitSymbol;
	}
	
	
	
	
}
