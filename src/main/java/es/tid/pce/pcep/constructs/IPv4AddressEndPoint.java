package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.EndPointIPv4TLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;

public class IPv4AddressEndPoint extends EndPoint {
	
	/**
	 * Source IPv4 address
	 */
	private EndPointIPv4TLV endPointIPv4;

	public IPv4AddressEndPoint() {
		
	}

	public IPv4AddressEndPoint(byte[] bytes, int offset) throws PCEPProtocolViolationException {
		decode(bytes, offset);
	}
	
	public void encode() {
		// TODO Auto-generated method stub
		int length=0;

		if (endPointIPv4!=null){
			endPointIPv4.encode();
			length=length+endPointIPv4.getTotalTLVLength();
		}

		this.setLength(length);
		this.bytes=new byte[this.getLength()];
		int offset=0;

		if (endPointIPv4!=null){
			System.arraycopy(endPointIPv4.getTlv_bytes(),0,this.bytes,offset,endPointIPv4.getTotalTLVLength());
			offset=offset+endPointIPv4.getTotalTLVLength();
		}		
		
	}
	
	public void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		int tlvtype=PCEPTLV.getType(bytes, offset);
		int tlvlength=PCEPTLV.getTotalTLVLength(bytes, offset);
		this.setLength(tlvlength);

		if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_IPV4_ADDRESS){
			endPointIPv4=new EndPointIPv4TLV(bytes, offset);
		}
		
	}

	public EndPointIPv4TLV getEndPointIPv4() {
		return endPointIPv4;
	}

	public void setEndPointIPv4(EndPointIPv4TLV endPointIPv4) {
		this.endPointIPv4 = endPointIPv4;
	}
	
	

}
