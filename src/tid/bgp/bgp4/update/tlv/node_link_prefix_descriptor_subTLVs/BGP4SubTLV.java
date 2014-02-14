package tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import java.util.logging.Logger;
/**
 * 
 * TLV Header

 The format of each TLV is:

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


 * @author mcs
 *
 */
public abstract class BGP4SubTLV {
	/**
	 * Type of the SubTLV
	 */
	protected int SubTLVType;
	
	/**
	 * Length of the VALUE ONLY!!!
	 */
	private int SubTLVValueLength;
	
	/**
	 * Total length of the SubTLV (including type, length and padding)
	 */
	protected int TotalSubTLVLength;
	
	/**
	 * Bytes of the SubTLV
	 */
	protected byte[] subtlv_bytes;
	
	/**
	 * Logger
	 */
	protected Logger log;
		
		public BGP4SubTLV(){
			log=Logger.getLogger("BGP4Parser");
		}
		
		
		public BGP4SubTLV(byte []bytes, int offset) {
			log=Logger.getLogger("BGP4Parser");	
			this.SubTLVType=((  ((int)bytes[offset]&0xFF)   <<8)& 0xFF00) |  ((int)bytes[offset+1] & 0xFF);
			this.SubTLVValueLength=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
			this.TotalSubTLVLength=SubTLVValueLength+4;
			
			if ((this.TotalSubTLVLength%4)!=0){
				//Padding must be done!!
				this.TotalSubTLVLength=this.TotalSubTLVLength+4-(this.TotalSubTLVLength%4);
				
			}	
			this.subtlv_bytes=new byte[TotalSubTLVLength];
			System.arraycopy(bytes, offset, subtlv_bytes, 0, TotalSubTLVLength);
		}
		
		protected void encodeHeader(){
			this.subtlv_bytes[0]=(byte)(SubTLVType>>>8 & 0xFF);
			this.subtlv_bytes[1]=(byte)(SubTLVType & 0xFF);
			this.subtlv_bytes[2]=(byte)(SubTLVValueLength>>>8 & 0xFF);
			this.subtlv_bytes[3]=(byte)(SubTLVValueLength & 0xFF);
		}
		
		public int getSubTLVValueLength(){
			return SubTLVValueLength;
		}
		public int getTotalSubTLVLength(){
			return TotalSubTLVLength;
		}

		public static int getTotalSubTLVLength(byte []bytes, int offset) {
			int len=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF)+4;		
			return len;
		}
		
		public static int getSubTLVLength(byte []bytes, int offset) {
			int len=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
			return len;
		}
		
		
		public static int getType(byte []bytes, int offset) {
		
			int typ=((  ((int)bytes[offset]&0xFF)   <<8)& 0xFF00) |  ((int)bytes[offset+1] & 0xFF);
			return typ;
		}
		
		
		
		public int getSubTLVType() {
			return SubTLVType;
		}


		protected void setSubTLVType(int subTLVType) {
			SubTLVType = subTLVType;
		}


		public byte[] getSubTLV_bytes() {
			return subtlv_bytes;
		}


		protected void setSubTLV_bytes(byte[] subtlv_bytes) {
			this.subtlv_bytes = subtlv_bytes;
		}


		/**
		 * Sets the lenght of the VALUE of the TLV. The total length is computed!!!
		 * @param SubTLVValueLength
		 */
		protected void setSubTLVValueLength(int SubTLVValueLength) {
			this.SubTLVValueLength = SubTLVValueLength;
			this.TotalSubTLVLength=SubTLVValueLength+ 4;
			if ((this.TotalSubTLVLength%4)!=0){
				//Padding must be done!!
				this.TotalSubTLVLength=this.TotalSubTLVLength+4-(this.TotalSubTLVLength%4);
				
			}	
			
		}
		


		public abstract void encode();
		

	}


