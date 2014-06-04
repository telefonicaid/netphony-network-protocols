package tid.ospf.ospfv2.lsa.tlv;

import java.util.logging.Logger;

/**
 * Base class for OSPF TLVs,
 * from <a href="link http://tools.ietf.org/html/rfc5392"> RFC 3630 </a>. 
 * 
 * RFC 3630  TE Extensions to OSPF Version 2 

2.3.2. TLV Header

   The LSA payload consists of one or more nested Type/Length/Value
   (TLV) triplets for extensibility.  The format of each TLV is:

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |              Type             |             Length            |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                            Value...                           |
      .                                                               .
      .                                                               .
      .                                                               .
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The Length field defines the length of the value portion in octets
   (thus a TLV with no value portion would have a length of zero).  The
   TLV is padded to four-octet alignment; padding is not included in the
   length field (so a three octet value would have a length of three,
   but the total size of the TLV would be eight octets).  Nested TLVs
   are also 32-bit aligned.  Unrecognized types are ignored.

   This memo defines Types 1 and 2.  See the IANA Considerations section
   for allocation of new Types.
 * @author ogondio
 *
 */
public abstract class OSPFTLV {

	/**
	 * Type of the OSPF TLV
	 */
	protected int TLVType;
	
	/**
	 * Length of the VALUE ONLY!!!
	 */
	private int TLVValueLength;
	
	/**
	 * Total length of the TLV (including type, length and padding)
	 */
	protected int TotalTLVLength;
	
	/**
	 * Bytes of the TLV
	 */
	protected byte[] tlv_bytes;
	
	/**
	 * Logger
	 */
	protected Logger log;
	
	public OSPFTLV(){
		log=Logger.getLogger("PCEPParser");
	}
	
	/**
	 * 
	 * @param bytes
	 * @param offset
	 */
	public OSPFTLV(byte []bytes, int offset) {
		log=Logger.getLogger("OSPFParser");		
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
	
	/**
	 * 
	 */
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
		int len=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
		len+=4;
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


	protected void setTLVType(int tLVType) {
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
	 * @param TLVValueLength
	 */
	protected void setTLVValueLength(int TLVValueLength) {
		this.TLVValueLength = TLVValueLength;
		this.TotalTLVLength=TLVValueLength+4;
		if ((this.TotalTLVLength%4)!=0){
			//Padding must be done!!
			this.TotalTLVLength=this.TotalTLVLength+(this.TotalTLVLength%4);
		}	
		
	}
	


	public abstract void encode();
	

}