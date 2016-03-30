package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;



import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 *  
 * [ISIS-TE-METRIC] Internet-Draft        IS-IS Extensions for Traffic Engineering (TE) Metric Extensions    October 2014
 * https://tools.ietf.org/html/draft-ietf-isis-te-metric-extensions-04#section-4.5
 * Section 4.5
 *

   This TLV advertises the residual bandwidth between two directly
   connected IS-IS neighbors.  The residual bandwidth advertised by this
   sub-TLV MUST be the residual bandwidth from the system originating
   the LSA to its neighbor.

   0                   1                   2                   3
   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Type        |     Length    |A|  RESERVED   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                          Residual Bandwidth                   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   
 * @author victorUceda
 *
 */
public class UndirectionalResidualBandwidthDescriptorSubTLV extends BGP4TLVFormat{
	
	
	int residualBw;
	public UndirectionalResidualBandwidthDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRESIDUALBW_ID);
	}
	
	
	public int getResidualBw() {
		return residualBw;
	}


	public void setResidualBw(int residualBw) {
		this.residualBw = residualBw;
	}


	public UndirectionalResidualBandwidthDescriptorSubTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		decode();
	}
	@Override
	public void encode() {
		int len = 5;
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		//System.arraycopy(0, 0,  this.tlv_bytes, 0, 1);
		//System.arraycopy(residualBw,0, this.tlv_bytes, 1, 4);
		int offset=4;
		this.tlv_bytes[offset]=0;
		this.tlv_bytes[offset + 1] = (byte)(residualBw >> 24 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(residualBw >> 16 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(residualBw >> 8 & 0xff);
		this.tlv_bytes[offset + 4] = (byte)(residualBw & 0xff);
	}
	public void decode(){
		if (this.getTLVValueLength()!=9){
			//throw new MalformedPCEPObjectException();
			//FIXME: esta mal formado Que hacer
		}
		//System.arraycopy(this.tlv_bytes,0, residualBw, 0, 4);
		int offset=5;
		this.residualBw=(((int)(this.tlv_bytes[offset]<<24)& (int)0xFF000000) | ((tlv_bytes[offset+1]<<16)& 0xFF0000) |((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF) );
		
	}

	@Override
	public String toString() {
		return "UndirectionalResidualBW [bw_bytes_per_second=" + residualBw + "]";
	}

}
