package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class AreaIDNodeDescriptorSubTLV extends NodeDescriptorsSubTLV{

	private Inet4Address AREA_ID;

	public AreaIDNodeDescriptorSubTLV() {
		super();
		this.setSubTLVType(NodeDescriptorsSubTLVTypes.NODE_DESCRIPTORS_SUBTLV_TYPE_AREA_ID);
		// TODO Auto-generated constructor stub
	}

	public AreaIDNodeDescriptorSubTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		decode();
		// TODO Auto-generated constructor stub
	}

	private void decode() {
		// TODO Auto-generated method stub
		log.finest("Decoding AREA ID");		
		byte[] ip=new byte[4]; 
		System.arraycopy(this.subtlv_bytes,4, ip, 0, 4);
		try {
			AREA_ID=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}

	@Override
	public void encode() {
		log.finest("Encoding AreaIdentifier Sub-TLV");
		this.setSubTLVValueLength(4);//AS_ID		
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		encodeHeader();
		int offset = 4;
		System.arraycopy(AREA_ID.getAddress(), 0, this.subtlv_bytes, offset, 4);
	}

	public Inet4Address getAREA_ID() {
		return AREA_ID;
	}

	public void setAREA_ID(Inet4Address AREA_ID) {
		this.AREA_ID = AREA_ID;
	}
	
	public String toString() {
		return "AreaID [Area_ID=" + AREA_ID + "]";
	}

}

