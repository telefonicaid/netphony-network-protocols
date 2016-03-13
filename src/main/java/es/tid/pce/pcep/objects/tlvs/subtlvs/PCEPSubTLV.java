package es.tid.pce.pcep.objects.tlvs.subtlvs;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 TLV Header

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

   The Length field defines the length of the value portion in octets
   (thus a TLV with no value portion would have a length of zero).  The
   TLV is padded to four-octet alignment; padding is not included in the
   length field (so a three octet value would have a length of three,
   but the total size of the TLV would be eight octets).  Nested TLVs
   are also 32-bit aligned.  Unrecognized types are ignored.

   This memo defines Types 1 and 2.  See the IANA Considerations section
   for allocation of new Types.
 * @author Alejandro Tovar de Due�as
 *
 */

public abstract class PCEPSubTLV {

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
		protected static final Logger log = LoggerFactory.getLogger("PCEPParser");
		
		public PCEPSubTLV(){
		}
		
		
		public PCEPSubTLV(byte []bytes, int offset) {
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
			if ((len%4)!=0){
				//Padding must be done!!
				len=len+4-(len%4);
			}		
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
		 * @param SubTLVValueLength length of the SUBTLV Value
		 */
		protected void setSubTLVValueLength(int SubTLVValueLength) {
			this.SubTLVValueLength = SubTLVValueLength;
			this.TotalSubTLVLength=SubTLVValueLength+4;
			if ((this.TotalSubTLVLength%4)!=0){
				//Padding must be done!!
				this.TotalSubTLVLength=this.TotalSubTLVLength+4-(this.TotalSubTLVLength%4);
				
			}	
			
		}
		


		public abstract void encode();


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + SubTLVType;
			result = prime * result + SubTLVValueLength;
			result = prime * result + TotalSubTLVLength;
			result = prime * result + Arrays.hashCode(subtlv_bytes);
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
			PCEPSubTLV other = (PCEPSubTLV) obj;
			if (SubTLVType != other.SubTLVType)
				return false;
			if (SubTLVValueLength != other.SubTLVValueLength)
				return false;
			if (TotalSubTLVLength != other.TotalSubTLVLength)
				return false;
			if (!Arrays.equals(subtlv_bytes, other.subtlv_bytes))
				return false;
			return true;
		}
		
		
		

	}
