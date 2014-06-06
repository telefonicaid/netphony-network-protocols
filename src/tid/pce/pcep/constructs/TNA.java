package tid.pce.pcep.constructs;

import tid.pce.pcep.objects.MalformedPCEPObjectException;
import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import tid.pce.pcep.objects.tlvs.subtlvs.TNAIPv4SubTLV;
import tid.pce.pcep.objects.tlvs.subtlvs.TNAIPv6SubTLV;
import tid.pce.pcep.objects.tlvs.subtlvs.TNANSAPSubTLV;

public class TNA extends PCEPConstruct {
	
	//private 
	
	private TNAIPv4SubTLV TNAIPv4SubTLV;
	
	private TNAIPv6SubTLV TNAIPv6SubTLV;
	
	private TNANSAPSubTLV TNANSAPSubTLV;
	
	public TNA() {
		
	}

	
	public TNA(byte[] bytes, int offset)throws MalformedPCEPObjectException {
		decode(bytes,offset);
	}

	@Override
	public void encode() {
		// TODO Auto-generated method stub
		int length=0;
		
		if (TNAIPv4SubTLV!=null){
			TNAIPv4SubTLV.encode();
			length=length+TNAIPv4SubTLV.getTotalSubTLVLength();
		}
		
		if (TNAIPv6SubTLV!=null){
			TNAIPv6SubTLV.encode();
			length=length+TNAIPv6SubTLV.getTotalSubTLVLength();
		}
		
		if (TNANSAPSubTLV!=null){
			TNANSAPSubTLV.encode();
			length=length+TNANSAPSubTLV.getTotalSubTLVLength();
		}
		
		this.setLength(length);
		//encodeHeader(); //FIXME!!!!!
		this.bytes=new byte[this.getLength()];
		int offset=0;
		
		if (TNAIPv4SubTLV!=null){
			System.arraycopy(TNAIPv4SubTLV.getSubTLV_bytes(),0,this.bytes,offset,TNAIPv4SubTLV.getTotalSubTLVLength());
			offset=offset+TNAIPv4SubTLV.getTotalSubTLVLength();
		}
		
		if (TNAIPv6SubTLV!=null){
			System.arraycopy(TNAIPv6SubTLV.getSubTLV_bytes(),0,this.bytes,offset,TNAIPv6SubTLV.getTotalSubTLVLength());
			offset=offset+TNAIPv6SubTLV.getTotalSubTLVLength();
		}
		
		if (TNANSAPSubTLV!=null){
			System.arraycopy(TNANSAPSubTLV.getSubTLV_bytes(),0,this.bytes,offset,TNANSAPSubTLV.getTotalSubTLVLength());
			offset=offset+TNANSAPSubTLV.getTotalSubTLVLength();
		}
	}
	
	public void decode(byte[] bytes, int offset) {
		
		int subtlvtype=PCEPSubTLV.getType(bytes, offset);
		int subtlvlength=PCEPSubTLV.getTotalSubTLVLength(bytes, offset);
		
		if (subtlvtype==PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_TNA_IPv4){
			TNAIPv4SubTLV=new TNAIPv4SubTLV(bytes, offset);
		}
		
		if (subtlvtype==PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_TNA_IPv6){
			TNAIPv6SubTLV=new TNAIPv6SubTLV(bytes, offset);
		}
		
		if (subtlvtype==PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_TNA_NSAP){
			TNANSAPSubTLV=new TNANSAPSubTLV(bytes, offset);
		}

	}
	
	public TNAIPv4SubTLV getTNAIPv4SubTLV() {
		return TNAIPv4SubTLV;
	}

	public void setTNAIPv4SubTLV(TNAIPv4SubTLV TNAIPv4SubTLV) {
		this.TNAIPv4SubTLV = TNAIPv4SubTLV;
	}
	
	public TNAIPv6SubTLV getTNAIPv6SubTLV() {
		return TNAIPv6SubTLV;
	}

	public void setTNAIPv6SubTLV(TNAIPv6SubTLV TNAIPv6SubTLV) {
		this.TNAIPv6SubTLV = TNAIPv6SubTLV;
	}

	public TNANSAPSubTLV getTNANSAPSubTLV() {
		return TNANSAPSubTLV;
	}

	public void setTNANSAPSubTLV(TNANSAPSubTLV TNANSAPSubTLV) {
		this.TNANSAPSubTLV = TNANSAPSubTLV;
	}

}