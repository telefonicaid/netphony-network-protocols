package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.ObjectParameters;

/**
 *  The NO-PATH-VECTOR TLV is compliant with the PCEP TLV format defined
   in Section 7.1 and is comprised of 2 bytes for the type, 2 bytes
   specifying the TLV length (length of the value portion in bytes)
   followed by a fixed-length 32-bit flags field.

   Type:   1
   Length: 4 bytes
   Value:  32-bit flags field

   IANA manages the space of flags carried in the NO-PATH-VECTOR TLV
   (see Section 9).

   The following flags are currently defined:

   o  Bit number: 31 - PCE currently unavailable

   o  Bit number: 30 - Unknown destination

   o  Bit number: 29 - Unknown source
 * 
 * 
 * @author ogondio
 *
 */
public class NoPathTLV extends PCEPTLV {
	
	/**
	 * Bit number: 31 -PCE currently unavailable
	 */
	private boolean PCEunavailable=false;
	/**
	 * Bit number: 30 - Unknown destination
	 */
	private boolean unknownDestination=false;
	/**
	 * Bit number: 29 - Unknown source
	 */
	private boolean unknownSource=false;
	
	public NoPathTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_NO_PATH_VECTOR);
		
	}
	
	public NoPathTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode No Path TLV
	 */
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		this.tlv_bytes[4]=0x00;
		this.tlv_bytes[5]=0x00;
		this.tlv_bytes[6]=0x00;				
		this.tlv_bytes[7]=(byte)( ( ((unknownSource?1:0) <<2) & 0x04) | ( ((unknownDestination?1:0) <<1) & 0x02) |   ((PCEunavailable?1:0)& 0x01) );
	}

	
	public void decode() {
		this.unknownSource=(this.tlv_bytes[7]&0x04)==0x04;
		this.unknownDestination=(this.tlv_bytes[7]&0x02)==0x02;
		this.PCEunavailable=(this.tlv_bytes[7]&0x01)==0x01;
		
		
	}

	public boolean isPCEunavailable() {
		return PCEunavailable;
	}

	public void setPCEunavailable(boolean pCEunavailable) {
		PCEunavailable = pCEunavailable;
	}

	public boolean isUnknownDestination() {
		return unknownDestination;
	}

	public void setUnknownDestination(boolean unknownDestination) {
		this.unknownDestination = unknownDestination;
	}

	public boolean isUnknownSource() {
		return unknownSource;
	}

	public void setUnknownSource(boolean unknownSource) {
		this.unknownSource = unknownSource;
	}
	
	public String toString(){
		String st="unknownSource "+unknownSource+"unknownDestination "+unknownDestination+"PCEunavailable "+PCEunavailable;
		return st;
	}
	
	

}
