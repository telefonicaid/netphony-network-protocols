package tid.ospf.ospfv2.lsa.tlv.subtlv;

public class LinkProtectionType extends OSPFSubTLV {

	public LinkProtectionType(){
		
	}
	
	public LinkProtectionType(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		decode();
	}	
		
	@Override
	public void encode() {
		// TODO Auto-generated method stub

	}
	
	protected void decode()throws MalformedOSPFSubTLVException{
		
	}

}
