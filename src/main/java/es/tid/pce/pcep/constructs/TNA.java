package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import es.tid.pce.pcep.objects.tlvs.subtlvs.TNAIPv4SubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.TNAIPv6SubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.TNANSAPSubTLV;

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
		System.out.println("leeen "+length);
		this.setLength(length);
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
		if (subtlvtype==PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_TNA_IPv4){
			TNAIPv4SubTLV=new TNAIPv4SubTLV(bytes, offset);
			offset+=TNAIPv4SubTLV.getTotalSubTLVLength();
			if (offset>=bytes.length){
				return;
			}else {
				subtlvtype=PCEPSubTLV.getType(bytes, offset);

			}
		}
		
		if (subtlvtype==PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_TNA_IPv6){
			TNAIPv6SubTLV=new TNAIPv6SubTLV(bytes, offset);
			offset+=TNAIPv6SubTLV.getTotalSubTLVLength();

			if (offset>=bytes.length){
				return;
			}else {
				subtlvtype=PCEPSubTLV.getType(bytes, offset);

			}
		}

		if (subtlvtype==PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_TNA_NSAP){
			TNANSAPSubTLV=new TNANSAPSubTLV(bytes, offset);
			offset+=TNANSAPSubTLV.getTotalSubTLVLength();
			if (offset>=bytes.length){
				return;
			}
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((TNAIPv4SubTLV == null) ? 0 : TNAIPv4SubTLV.hashCode());
		result = prime * result
				+ ((TNAIPv6SubTLV == null) ? 0 : TNAIPv6SubTLV.hashCode());
		result = prime * result
				+ ((TNANSAPSubTLV == null) ? 0 : TNANSAPSubTLV.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
				return false;
		if (getClass() != obj.getClass())
			return false;
		TNA other = (TNA) obj;
		if (TNAIPv4SubTLV == null) {
			if (other.TNAIPv4SubTLV != null)
				return false;
		} else if (!TNAIPv4SubTLV.equals(other.TNAIPv4SubTLV))
			return false;

		if (TNAIPv6SubTLV == null) {
			if (other.TNAIPv6SubTLV != null)
				return false;
		} else if (!TNAIPv6SubTLV.equals(other.TNAIPv6SubTLV))
			return false;

		if (TNANSAPSubTLV == null) {
			if (other.TNANSAPSubTLV != null)
				return false;
		} else if (!TNANSAPSubTLV.equals(other.TNANSAPSubTLV))
			return false;

		return true;
	}
	
	

}