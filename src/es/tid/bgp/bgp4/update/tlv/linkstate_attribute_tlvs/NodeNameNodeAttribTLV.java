package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class NodeNameNodeAttribTLV extends BGP4TLVFormat{
	
	private byte[] name;

	public NodeNameNodeAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_NODE_NAME);
	}
	
	public NodeNameNodeAttribTLV(byte[] bytes, int offset){		
		super(bytes,offset);		
		decode();
	}
	
	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}
	
	public void decode(){
		int length = this.getTLVValueLength();
		int offset = 4;
		System.arraycopy(this.tlv_bytes,offset, name, 0, length);
	}
	
	public void setName(String name)
	{
		char[] c = name.toCharArray();
	    byte[] b = new byte[c.length];
	    for (int i = 0; i < c.length; i++)
	      b[i] = (byte)(c[i] & 0x007F);

	    System.arraycopy(b,0, name, 0, b.length);
	}
	
	public byte[] getName(){
		return name;
	}
	
	public String toString(){
		return "NODE NAME [name=" +this.getName().toString() + "]";
	}
}
