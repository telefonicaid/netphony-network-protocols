package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;



import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 *  
 * [ISIS-TE-METRIC] Internet-Draft        IS-IS Extensions for Traffic Engineering (TE) Metric Extensions    October 2014
 * https://tools.ietf.org/html/draft-ietf-isis-te-metric-extensions-04#section-4.6
 * Section 4.6
 *

      This Sub-TLV advertises the available bandwidth between two directly
   connected IS-IS neighbors.  The available bandwidth advertised by
   this sub-TLV MUST be the available bandwidth from the system
   originating this Sub-TLV.  The format of this Sub-TLV is shown in the
   following diagram:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Type        |     Length    |A|  RESERVED   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                      Available Bandwidth                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   
 * @author victorUceda
 *
 */
public class UndirectionalAvailableBandwidthDescriptorSubTLV extends BGP4TLVFormat{
	
	
	int availableBw;
	public UndirectionalAvailableBandwidthDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRAVAILABLEBW_ID);
	}
	
	
	public int getAvailableBw() {
		return availableBw;
	}


	public void setAvailableBw(int availableBw) {
		this.availableBw = availableBw;
	}


	public UndirectionalAvailableBandwidthDescriptorSubTLV(byte []bytes, int offset) {		
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
		//System.arraycopy(availableBw,0, this.tlv_bytes, 1, 4);
		int offset=4;
		this.tlv_bytes[offset]=0;
		this.tlv_bytes[offset + 1] = (byte)(availableBw >> 24 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(availableBw >> 16 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(availableBw >> 8 & 0xff);
		this.tlv_bytes[offset + 4] = (byte)(availableBw & 0xff);
	}
	public void decode(){
		if (this.getTLVValueLength()!=9){
			//throw new MalformedPCEPObjectException();
			//FIXME: esta mal formado Que hacer
		}
		//System.arraycopy(this.tlv_bytes,0, availableBw, 0, 4);
		int offset=5;
		this.availableBw=(((int)(this.tlv_bytes[offset]<<24)& (int)0xFF000000) | ((tlv_bytes[offset+1]<<16)& 0xFF0000) |((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF) );
		
	}

	@Override
	public String toString() {
		return "UndirectionalAvailableBW [bw_bytes_per_second=" + availableBw + "]";
	}

}
