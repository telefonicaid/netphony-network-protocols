package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;



import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 *  
 * [ISIS-TE-METRIC] Internet-Draft        IS-IS Extensions for Traffic Engineering (TE) Metric Extensions    October 2014
 * https://tools.ietf.org/html/draft-ietf-isis-te-metric-extensions-04#section-4.1
 * Section 4.1
 *
 This sub-TLV advertises the average link delay between two directly
   connected IS-IS neighbors.  The delay advertised by this sub-TLV MUST
   be the delay from the local neighbor to the remote one (i.e. the
   forward path latency).  The format of this sub-TLV is shown in the
   following diagram:

   0                   1                   2                   3
   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Type        |     Length    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |A|   RESERVED  |                   Delay                       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   
 * @author victorUceda
 *
 */
public class UndirectionalLinkDelayDescriptorSubTLV extends BGP4TLVFormat{
	int delay;
	public UndirectionalLinkDelayDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRLINKDELAY_ID);
	}
	
	
	public int getDelay() {
		return delay;
	}


	public void setDelay(int delay) {
		if(delay < 0)delay=0;
		if(delay > 16777215)delay=16777215;
		this.delay = delay;
	}


	public UndirectionalLinkDelayDescriptorSubTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		decode();
	}
	@Override
	public void encode() {
		int len = 4;
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		System.arraycopy(0, 0,  this.tlv_bytes, 0, 1);
		System.arraycopy(delay,1, this.tlv_bytes, 1, 3);
	}
	public void decode(){
		if (this.getTLVValueLength()!=8){
			//throw new MalformedPCEPObjectException();
			//FIXME: esta mal formado Que hacer
		}
		System.arraycopy(this.tlv_bytes,1, delay, 1, 3);
		
	}

	@Override
	public String toString() {
		return "UndirectionalLinkDelay [delay_ms=" + delay + "]";
	}

}
