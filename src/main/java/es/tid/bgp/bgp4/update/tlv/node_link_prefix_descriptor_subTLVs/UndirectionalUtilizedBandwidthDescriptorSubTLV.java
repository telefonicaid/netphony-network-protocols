package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;



import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 *  
 * [ISIS-TE-METRIC] Internet-Draft        IS-IS Extensions for Traffic Engineering (TE) Metric Extensions    October 2014
 * https://tools.ietf.org/html/draft-ietf-isis-te-metric-extensions-04#section-4.7
 * Section 4.7
 *

    This Sub-TLV advertises the bandwidth utilization between two
   directly connected IS-IS neighbors.  The bandwidth utilization
   advertised by this sub-TLV MUST be the bandwidth from the system
   originating this Sub-TLV.  The format of this Sub-TLV is shown in the
   following diagram:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Type        |     Length    |A|  RESERVED   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       Bandwidth Utilization                   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   
 * @author victorUceda
 *
 */
public class UndirectionalUtilizedBandwidthDescriptorSubTLV extends BGP4TLVFormat{
	
	
	int utilizedBw;
	public UndirectionalUtilizedBandwidthDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRLUTILIZEDBW_ID);
	}
	
	
	public int getUtilizedBw() {
		return utilizedBw;
	}


	public void setUtilizedBw(int availableBw) {
		this.utilizedBw = availableBw;
	}


	public UndirectionalUtilizedBandwidthDescriptorSubTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		decode();
	}
	@Override
	public void encode() {
		int len = 5;
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		int offset=4;
		this.tlv_bytes[offset]=0;
		this.tlv_bytes[offset + 1] = (byte)(utilizedBw >> 24 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(utilizedBw >> 16 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(utilizedBw >> 8 & 0xff);
		this.tlv_bytes[offset + 4] = (byte)(utilizedBw & 0xff);
	}
	public void decode(){
		if (this.getTLVValueLength()!=9){
			//throw new MalformedPCEPObjectException();
			//FIXME: esta mal formado Que hacer
		}
		//System.arraycopy(this.tlv_bytes,0, utilizedBw, 0, 4);
		int offset=5;
		this.utilizedBw=(((int)(this.tlv_bytes[offset]<<24)& (int)0xFF000000) | ((tlv_bytes[offset+1]<<16)& 0xFF0000) |((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF) );
		
	}

	@Override
	public String toString() {
		return "UndirectionalAvailableBW [bw_bytes_per_second=" + utilizedBw + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + utilizedBw;
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
		UndirectionalUtilizedBandwidthDescriptorSubTLV other = (UndirectionalUtilizedBandwidthDescriptorSubTLV) obj;
		if (utilizedBw != other.utilizedBw)
			return false;
		return true;
	}

}
