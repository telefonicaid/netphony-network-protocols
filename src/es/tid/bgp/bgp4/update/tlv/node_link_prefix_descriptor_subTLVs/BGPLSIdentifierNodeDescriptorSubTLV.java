package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class BGPLSIdentifierNodeDescriptorSubTLV extends NodeDescriptorsSubTLV{

	private Inet4Address BGPLS_ID;

	public BGPLSIdentifierNodeDescriptorSubTLV() {
		super();
		this.setSubTLVType(NodeDescriptorsSubTLVTypes.NODE_DESCRIPTORS_SUBTLV_TYPE_BGP_LS_IDENTIFIER);
		// TODO Auto-generated constructor stub
	}

	public BGPLSIdentifierNodeDescriptorSubTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		decode();
		// TODO Auto-generated constructor stub
	}

	private void decode() {
		log.finest("Decoding BGPLS_ID_TLV");		
		byte[] ip=new byte[4]; 
		System.arraycopy(this.subtlv_bytes,4, ip, 0, 4);
		try {
			BGPLS_ID=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
		// TODO Auto-generated method stub
	

	@Override
	public void encode() {
		log.finest("Encoding BGPLSIdentifier Sub-TLV");
		this.setSubTLVValueLength(4);//AS_ID		
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		encodeHeader();
		int offset = 4;
		System.arraycopy(BGPLS_ID.getAddress(), 0, this.subtlv_bytes, offset, 4);
		
	}

	public Inet4Address getBGPLS_ID() {
		return BGPLS_ID;
	}

	public void setBGPLS_ID(Inet4Address bGPLS_ID) {
		BGPLS_ID = bGPLS_ID;
	}
	
	public String toString() {
		return "BGPLS_IDENT [BGPLS_ID=" + BGPLS_ID + "]";
	}

}
