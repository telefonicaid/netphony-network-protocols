package es.tid.bgp.bgp4.update.fields.pathAttributes;

public class Generic_MP_Unreach_Attribute extends MP_Unreach_Attribute
{
	public Generic_MP_Unreach_Attribute(){}

	public Generic_MP_Unreach_Attribute(byte[] bytes, int offset){
		super(bytes, offset);
	}

	@Override
	public void encode(){
		/**
		 * Attribute length:
		 * 	Address Family Identifier (2 octets)
		 * 	Subsequent Address Family Identifier (1 octet)
		 *  Total: 3 octets
		 * @see RFC 4760
		 * @see es.tid.bgp.bgp4.update.fields.pathAttributes.MP_Unreach_Attribute
		 */
		pathAttributeLength = 3;
		this.setPathAttributeLength(pathAttributeLength);
		this.bytes=new byte[this.getLength()];
		encodeHeader();
		encodeMP_Unreach_Header();
	}
}
