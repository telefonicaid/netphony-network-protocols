package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.EndPointDataPathTLV;
import es.tid.pce.pcep.objects.tlvs.EndPointIPv4TLV;
import es.tid.pce.pcep.objects.tlvs.EndPointUnnumberedDataPathTLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.pce.pcep.objects.tlvs.UnnumberedEndpointTLV;
import es.tid.pce.pcep.objects.tlvs.XifiEndPointTLV;

public class UnnumIfEndPoint extends EndPoint {
	
	private UnnumberedEndpointTLV unnumberedEndpoint;

	public UnnumIfEndPoint() {
		
	}

	public UnnumIfEndPoint(byte[] bytes, int offset) throws PCEPProtocolViolationException {
		decode(bytes, offset);
	}
	
	public void encode() {
		// TODO Auto-generated method stub
		int length=0;

	    if (unnumberedEndpoint!=null){
			unnumberedEndpoint.encode();
			length=length+unnumberedEndpoint.getTotalTLVLength();
		}


		this.setLength(length);
		this.bytes=new byte[this.getLength()];
		int offset=0;

	   if (unnumberedEndpoint!=null){
			System.arraycopy(unnumberedEndpoint.getTlv_bytes(),0,this.bytes,offset,unnumberedEndpoint.getTotalTLVLength());
			offset=offset+unnumberedEndpoint.getTotalTLVLength();
		}


	}
	
	public void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		int tlvtype=PCEPTLV.getType(bytes, offset);
		int tlvlength=PCEPTLV.getTotalTLVLength(bytes, offset);
		this.setLength(tlvlength);

		if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_UNNUMBERED_ENDPOINT){
			unnumberedEndpoint=new UnnumberedEndpointTLV(bytes, offset);
		}

	}

	public UnnumberedEndpointTLV getUnnumberedEndpoint() {
		return unnumberedEndpoint;
	}

	public void setUnnumberedEndpoint(UnnumberedEndpointTLV unnumberedEndpoint) {
		this.unnumberedEndpoint = unnumberedEndpoint;
	}
	
	
	

}
