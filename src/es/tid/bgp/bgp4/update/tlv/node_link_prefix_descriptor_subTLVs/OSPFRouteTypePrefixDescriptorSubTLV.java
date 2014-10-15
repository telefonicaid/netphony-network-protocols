package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class OSPFRouteTypePrefixDescriptorSubTLV extends BGP4TLVFormat{
	
	public static final int INTRA_AREA=0x1;
	public static final int INTER_AREA = 0X2;
	public static final int EXTERNAL_1 = 0x3;
	public static final int EXTERNAL_2 = 0x4;
	public static final int NSSA_1=0x5;
	public static final int NSSA_2=0x6;
	
	private int route_type;

	public OSPFRouteTypePrefixDescriptorSubTLV() {
		super();
		this.setTLVType(PrefixDescriptorSubTLVTypes.PREFIX_DESCRIPTOR_SUB_TLV_TYPE_OSPF_ROUTE_TYPE);
		this.setTLVValueLength(1);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset = 4;
			(this.tlv_bytes[offset])|=(byte) this.getRoute_type();
	}

	
	public OSPFRouteTypePrefixDescriptorSubTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		decode();
	}
	
	@Override
	public void encode() {
		this.setTLVValueLength(1);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		int offset = 4;
		this.tlv_bytes[offset] = (byte)route_type;
		}
		
		
		// TODO Auto-generated method stub
		
	
	public void decode(){
		int offset =4;
		int type = tlv_bytes[offset];
		switch(type){
			case 0x1:
				this.setRoute_type(INTRA_AREA);
			case 0x2:
				this.setRoute_type(INTER_AREA);
			case 0X3:
				this.setRoute_type(EXTERNAL_1);
			case 0x4:
				this.setRoute_type(EXTERNAL_2);
			case 0x5:
				this.setRoute_type(NSSA_1);
			case 0x6:
				this.setRoute_type(NSSA_2);
			default:
				log.finest("No such OSPF route type");
		}
		
	}

	public int getRoute_type() {
		return route_type;
	}

	public void setRoute_type(int route_type) {
		this.route_type = route_type;
	}
	
	public String toString(){
		return "Route Type [route_type=" + route_type + "]";

	}

	

}
