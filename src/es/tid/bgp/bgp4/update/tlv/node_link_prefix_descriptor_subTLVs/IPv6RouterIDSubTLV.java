package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Campo del Node Descriptor Sub TLV
 *  
 *  IPv6 Router ID:  opaque value (can be an IPv6 address or 128 Bit
      router ID) followed by a LAN-ID octet in case LAN "Pseudonode"
      information gets advertised.  The PSN octet must be zero for non-
      LAN "Pseudonodes".
      
 * @author mcs
 *
 */
public class IPv6RouterIDSubTLV extends NodeDescriptorsSubTLV{

  private static Logger log = LoggerFactory.getLogger("BGP4Parser");

  public IPv6RouterIDSubTLV(){
		super();
		//this.setSubTLVType(NodeDescriptorsSubTLVTypes.BGP4_SUBTLV_TYPE_IPV6);
	}
	public IPv6RouterIDSubTLV(byte [] bytes, int offset){
		//super(bytes, offset);
		decode();
	}
	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}
	public void decode(){
		
	}
}
