package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutonomousSystemNodeDescriptorSubTLV extends NodeDescriptorsSubTLV{	
	private Inet4Address AS_ID;

  private static final Logger log = LoggerFactory.getLogger("BGP4Parser");

	public AutonomousSystemNodeDescriptorSubTLV(){
		super();
		this.setSubTLVType(NodeDescriptorsSubTLVTypes.NODE_DESCRIPTORS_SUBTLV_TYPE_AUTONOMOUS_SYSTEM);
	}
	public AutonomousSystemNodeDescriptorSubTLV(byte [] bytes, int offset){
		super(bytes, offset);
		decode();
	}
	@Override
	public void encode() {
		//Encode AutonomousSystemSubTLV
		this.setSubTLVValueLength(4);//AS_ID		
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		encodeHeader();
		int offset = 4;
		System.arraycopy(AS_ID.getAddress(), 0, this.subtlv_bytes, offset, 4);

	}
	public void decode(){
		//Decoding AutonomousSystemSubTLV		
		byte[] ip=new byte[4]; 
		System.arraycopy(this.subtlv_bytes,4, ip, 0, 4);
		try {
			AS_ID=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
	
	public Inet4Address getAS_ID() {
		return AS_ID;
	}
	public void setAS_ID(Inet4Address aS_ID) {
		AS_ID = aS_ID;
	}
	@Override
	public String toString() {
		return "AutonomousSystemSubTLV [AS_ID=" + AS_ID + "]";
	}
	
}