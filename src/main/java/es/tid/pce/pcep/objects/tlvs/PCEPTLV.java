package es.tid.pce.pcep.objects.tlvs;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for PCEP TLVs
 * 
 * 7.1. PCEP TLV Format


   A PCEP object may include a set of one or more optional TLVs.

   All PCEP TLVs have the following format:

   Type:   2 bytes
   Length: 2 bytes
   Value:  variable

   A PCEP object TLV is comprised of 2 bytes for the type, 2 bytes
   specifying the TLV length, and a value field.

   The Length field defines the length of the value portion in bytes.
   The TLV is padded to 4-bytes alignment; padding is not included in
   the Length field (so a 3-byte value would have a length of 3, but the
   total size of the TLV would be 8 bytes).

   Unrecognized TLVs MUST be ignored.

   IANA management of the PCEP Object TLV type identifier codespace is
   described in Section 9.

 * 
 * @author ogondio
 *
 */
public abstract class PCEPTLV {
	
	
	
	protected int TLVType;
	/**
	 * Length of the VALUE ONLY!!!
	 */
	protected int TLVValueLength;
	protected int TotalTLVLength;
	
	protected byte[] tlv_bytes;
	
	protected static final Logger log = LoggerFactory.getLogger("PCEPParser");
	
	public PCEPTLV(){
	}
	
	
	public PCEPTLV(byte []bytes, int offset) {
		this.TLVType=((  ((int)bytes[offset]&0xFF)   <<8)& 0xFF00) |  ((int)bytes[offset+1] & 0xFF);
		this.TLVValueLength=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
		this.TotalTLVLength=TLVValueLength+4;
		if ((this.TotalTLVLength%4)!=0){
			//Padding must be done!!
			this.TotalTLVLength=this.TotalTLVLength+4-(this.TotalTLVLength%4);
		}	
		this.tlv_bytes=new byte[TotalTLVLength];
		System.arraycopy(bytes, offset, tlv_bytes, 0, TotalTLVLength);
	}
	
	protected void encodeHeader(){
		this.tlv_bytes[0]=(byte)(TLVType>>>8 & 0xFF);
		this.tlv_bytes[1]=(byte)(TLVType & 0xFF);
		this.tlv_bytes[2]=(byte)(TLVValueLength>>>8 & 0xFF);
		this.tlv_bytes[3]=(byte)(TLVValueLength & 0xFF);
	}
	
	public int getTLVValueLength(){
		return TLVValueLength;
	}
	public int getTotalTLVLength(){
		return TotalTLVLength;
	}
	
	public static int getTotalTLVLength(byte []bytes, int offset) {
		int len=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF)+4;
		if ((len%4)!=0){
			//Padding must be done!!
			len=len+4-(len%4);
		}		
		return len;
	}
	
	public static int getTLVLength(byte []bytes, int offset) {
		int len=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
		return len;
	}
	
	
	public static int getType(byte []bytes, int offset) {
		int typ=((  ((int)bytes[offset]&0xFF)   <<8)& 0xFF00) |  ((int)bytes[offset+1] & 0xFF);
		return typ;
	}
	
	
	
	public int getTLVType() {
		return TLVType;
	}


	public void setTLVType(int tLVType) {
		TLVType = tLVType;
	}


	public byte[] getTlv_bytes() {
		return tlv_bytes;
	}


	protected void setTlv_bytes(byte[] tlv_bytes) {
		this.tlv_bytes = tlv_bytes;
	}


	/**
	 * Sets the lenght of the VALUE of the TLV. The total length is computed!!!
	 * @param TLVValueLength Length of the TLV Value
	 */
	protected void setTLVValueLength(int TLVValueLength) {
		this.TLVValueLength = TLVValueLength;
		this.TotalTLVLength=TLVValueLength+4;
		if ((this.TotalTLVLength%4)!=0){
			//Padding must be done!!
			this.TotalTLVLength=this.TotalTLVLength+4-(this.TotalTLVLength%4);
		}	
		
	}
	


	public abstract void encode();


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + TLVType;
		result = prime * result + TLVValueLength;
		result = prime * result + TotalTLVLength;
		result = prime * result + Arrays.hashCode(tlv_bytes);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PCEPTLV other = (PCEPTLV) obj;
		if (TLVType != other.TLVType)
			return false;
		if (TLVValueLength != other.TLVValueLength)
			return false;
		if (TotalTLVLength != other.TotalTLVLength)
			return false;
		if (!Arrays.equals(tlv_bytes, other.tlv_bytes))
			return false;
		return true;
	}
	
	
	
}
