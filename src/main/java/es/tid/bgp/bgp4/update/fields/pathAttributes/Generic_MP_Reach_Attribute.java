package es.tid.bgp.bgp4.update.fields.pathAttributes;

public class Generic_MP_Reach_Attribute extends MP_Reach_Attribute {
	public Generic_MP_Reach_Attribute(){
	}
	
	public Generic_MP_Reach_Attribute(byte [] bytes, int offset) {
		super(bytes, offset);
	}
	public void encode() {
		//Encoding  Generic MP_Reach_Attribute
		//FIXME: SUPONEMOS lengthofNextHopNetworkAddress cero

		/**
		 * Attribute length:
		 * 	Address Family Identifier (2 octets)
		 * 	Subsequent Address Family Identifier (1 octet)
		 * 	Length of Next Hop Network Address (1 octet)
		 * 	Network Address of Next Hop (variable), assuming IPv4, 4 octets //FIXME: change this in case of IPv6
		 * 	Reserved (1 octet)
		 * 	Total: 9 octets
		 *
		 * @see RFC 4760
		 * @see es.tid.bgp.bgp4.update.fields.pathAttributes.MP_Reach_Attribute
		 */

		pathAttributeLength = 9;
		this.setPathAttributeLength(pathAttributeLength);
		this.bytes=new byte[this.getLength()];
		encodeHeader();	
		encodeMP_Reach_Header();
	}
}
