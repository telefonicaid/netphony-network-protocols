package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;

/**
 * From draft-ietf-pce-inter-layer-ext-04.txt
 *   
 * 
 *    0                   1                   2                   3 
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ 
    | LSP Enc. Type |Switching Type | Reserved                    |I| 
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ 
 * 
 * LSP Encoding Type (8 bits): see [RFC3471] for a description of 
    parameters. 
     
    Switching Type (8 bits): see [RFC3471] for a description of 
    parameters. 
     
    I flag (1 bit): the I flag indicates whether a layer with the 
    specified switching type and encoding type must or must not be used 
    by the computed path. When the I flag is set (one), the computed 
    path MUST traverse a layer with the specified switching type and 
    encoding type. When the I flag is clear (zero), the computed path
 *
 *@author ogondio
 */
public class SwitchEncodingType extends PCEPConstruct {

	private int LSPEncodingType;
	
	private int SwitchingType;
	
	private boolean Iflag;
	
	public SwitchEncodingType(){
		
	}
	
	public SwitchEncodingType(byte[] bytes, int offset)
	throws PCEPProtocolViolationException {
		
		decode (bytes, offset);
		
	}
	
	
	
	public boolean isIflag() {
		return Iflag;
	}

	public void setIflag(boolean iflag) {
		Iflag = iflag;
	}

	public int getLSPEncodingType() {
		return LSPEncodingType;
	}

	public void setLSPEncodingType(int lSPEncodingType) {
		LSPEncodingType = lSPEncodingType;
	}

	public int getSwitchingType() {
		return SwitchingType;
	}

	public void setSwitchingType(int switchingType) {
		SwitchingType = switchingType;
	}

	public void encode() throws PCEPProtocolViolationException {
		this.setLength(4);
		bytes=new byte[this.length];
		this.bytes[0]=(byte)(LSPEncodingType  & 0xff);;
		this.bytes[1]=(byte)(SwitchingType  & 0xff);
		this.bytes[2]=(byte)0x00;
		this.bytes[3]=(byte)(Iflag?1:0);

	}

	
	private void decode(byte[] bytes, int offset)
			throws PCEPProtocolViolationException {
		this.setLength(4);
		LSPEncodingType = (this.bytes[0+offset]&0xFF);
		SwitchingType = (this.bytes[1+offset]&0xFF);	
		Iflag =(this.bytes[3+offset]&0x01)==0x01;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (Iflag ? 1231 : 1237);
		result = prime * result + LSPEncodingType;
		result = prime * result + SwitchingType;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SwitchEncodingType other = (SwitchEncodingType) obj;
		if (Iflag != other.Iflag)
			return false;
		if (LSPEncodingType != other.LSPEncodingType)
			return false;
		if (SwitchingType != other.SwitchingType)
			return false;
		return true;
	}
	
	
	

}
