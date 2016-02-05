package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

/**
 * 3.2.1.3.  Node Descriptor Sub-TLVs

   The Node Descriptor Sub-TLV type codepoints and lengths are listed in
   the following table:

                   +------+-------------------+--------+
                   | Type | Description       | Length |
                   +------+-------------------+--------+
                   |  258 | Autonomous System |      4 |
                   |  259 | Member-AS         |      4 |
                   |  260 | IPv4 Router-ID    |      5 |
                   |  261 | IPv6 Router-ID    |     17 |
                   |  262 | ISO Node-ID       |      7 |
                   +------+-------------------+--------+

                     Table 1: Node Descriptor Sub-TLVs

   The TLV values in Node Descriptor Sub-TLVs are defined as follows:

   Autonomous System:  opaque value (32 Bit AS ID)

   Member-AS:  opaque value (32 Bit AS ID); only included if the node is
      in an AS confederation.

   IPv4 Router ID:  opaque value (can be an IPv4 address or an 32 Bit
      router ID) followed by a LAN-ID octet in case LAN "Pseudonode"
      information gets advertised.  The PSN octet must be zero for non-
      LAN "Pseudonodes".

   IPv6 Router ID:  opaque value (can be an IPv6 address or 128 Bit
      router ID) followed by a LAN-ID octet in case LAN "Pseudonode"
      information gets advertised.  The PSN octet must be zero for non-
      LAN "Pseudonodes".

   ISO Node ID:  ISO node-ID (6 octets ISO system-ID) followed by a PSN
      octet in case LAN "Pseudonode" information gets advertised.  The
      PSN octet must be zero for non-LAN "Pseudonodes".
      
 * @author mcs
 *
 */
public abstract class NodeDescriptorsSubTLV extends BGP4SubTLV{
	public NodeDescriptorsSubTLV(){
		super();
	}
	
	
	public NodeDescriptorsSubTLV(byte []bytes, int offset) {
		super(bytes,offset);
	}
	
}
