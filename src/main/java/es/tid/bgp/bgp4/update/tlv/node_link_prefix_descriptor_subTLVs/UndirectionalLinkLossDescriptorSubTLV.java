package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;



import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 *  
 * [ISIS-TE-METRIC] Internet-Draft        IS-IS Extensions for Traffic Engineering (TE) Metric Extensions    October 2014
 * https://tools.ietf.org/html/draft-ietf-isis-te-metric-extensions-04#section-4.4
 * Section 4.4
 *
 This sub-TLV advertises the loss (as a packet percentage) between two
   directly connected IS-IS neighbors.  The link loss advertised by this
   sub-TLV MUST be the packet loss from the local neighbor to the remote
   one (i.e. the forward path loss).  The format of this sub-TLV is
   shown in the following diagram:

   0                   1                   2                   3
   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Type        |     Length    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |A|  RESERVED   |                  Link Loss                    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   
 * @author victorUceda
 *
 */
public class UndirectionalLinkLossDescriptorSubTLV extends BGP4TLVFormat{
	
	/*
   Link Loss.  This 24-bit field carries link packet loss as a
   percentage of the total traffic sent over a configurable interval.
   The basic unit is 0.000003%, where (2^24 - 2) is 50.331642%. This
   value is the highest packet loss percentage that can be expressed
   (the assumption being that precision is more important on high speed
   links than the ability to advertise loss rates greater than this, and
   that high speed links with over 50% loss are unusable).  Therefore,
   measured values that are larger than the field maximum SHOULD be
   encoded as the maximum value.  When set to a value of all 1s (2^24 -
   1), the link packet loss has not been measured.
	 */
	int linkLoss;
	public UndirectionalLinkLossDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRLINKLOSS_ID);
	}
	
	
	public int getLinkLoss() {
		return linkLoss;
	}


	public void setLinkLoss(int loss) {
		if(loss < 0)loss=0;
		if(loss > Math.pow(2,24))loss=(int)(Math.pow(2,24)-1);
		this.linkLoss = loss;
	}


	public UndirectionalLinkLossDescriptorSubTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		decode();
	}
	@Override
	public void encode() {
		int len = 4;
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		//System.arraycopy(0, 0,  this.tlv_bytes, 0, 1);
		//System.arraycopy(linkLoss,1, this.tlv_bytes, 1, 3);
		int offset=4;
		this.tlv_bytes[offset]=0;
		this.tlv_bytes[offset + 1] = (byte)(linkLoss >> 16 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(linkLoss >> 8 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(linkLoss & 0xff);
	}
	public void decode(){
		if (this.getTLVValueLength()!=8){
			//throw new MalformedPCEPObjectException();
			//FIXME: esta mal formado Que hacer
		}
		//System.arraycopy(this.tlv_bytes,1, linkLoss, 1, 3);
		int offset=4;
		this.linkLoss=0;
		this.linkLoss= (((int)(tlv_bytes[offset+1]<<16)& (int)0xFF0000) |((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF) );
		
	}

	@Override
	public String toString() {
		return "UndirectionalLinkDelay [delay_ms=" + linkLoss + "]";
	}

}
