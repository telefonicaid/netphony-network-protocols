package es.tid.bgp.bgp4.update.fields.pathAttributes;

public class Generic_MP_Reach_Attribute extends MP_Reach_Attribute {
	public Generic_MP_Reach_Attribute(){
	}
	
	public Generic_MP_Reach_Attribute(byte [] bytes, int offset) {
		super(bytes, offset);
	}
	public void encode() {
		log.info("Encoding  Generic MP_Reach_Attribute");
		
		
		//FIXME: SUPONEMOS lengthofNextHopNetworkAddress cero
		pathAttributeLength = 5;
		this.setPathAttributeLength(pathAttributeLength);
		this.bytes=new byte[this.getLength()];
		encodeHeader();	
		encodeMP_Reach_Header();
	}
}
