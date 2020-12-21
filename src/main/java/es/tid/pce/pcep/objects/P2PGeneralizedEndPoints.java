package es.tid.pce.pcep.objects;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.EndPoint;
import es.tid.pce.pcep.constructs.P2PEndpoints;
import es.tid.pce.pcep.objects.tlvs.EndPointIPv4TLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.pce.pcep.objects.tlvs.UnnumberedEndpointTLV;

/**
 * GeneralizedEndPoints Object of Type P2P End Points
 * @author ogondio
 *
 */
public class P2PGeneralizedEndPoints extends GeneralizedEndPoints {
	
	private EndPoint sourceEndpoint;
	//private LinkedList <EndpointRestriction> sourceEndpointRestrictionList;

	private EndPoint destinationEndpoint;
	//private LinkedList <EndpointRestriction> destinationEndpointRestrictionList;

	public P2PGeneralizedEndPoints() {
		super();
		this.setGeneralizedEndPointsType(1);		
	}

	public P2PGeneralizedEndPoints(byte[] bytes, int offset)
			throws MalformedPCEPObjectException, PCEPProtocolViolationException {
		super(bytes, offset);
		// TODO Auto-generated constructor stub
	}
	
	public void encode() {
		try {
			int len=8;//The four bytes of the header plus the reserved and Endpoint type	
			sourceEndpoint.encode();
			len+=sourceEndpoint.getLength();
			destinationEndpoint.encode();
			len+=destinationEndpoint.getLength();
			this.setObjectLength(len);
			this.object_bytes=new byte[this.getLength()];
			encode_header();
			encodeEndpointHeader();
			int offset=8;
			System.arraycopy(sourceEndpoint.getBytes(),0, this.object_bytes, offset, sourceEndpoint.getLength());
			System.arraycopy(destinationEndpoint.getBytes(),0, this.object_bytes, offset, destinationEndpoint.getLength());			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EndPoint getSourceEndpoint() {
		return sourceEndpoint;
	}

	public void setSourceEndpoint(EndPoint sourceEndpoint) {
		this.sourceEndpoint = sourceEndpoint;
	}

	public EndPoint getDestinationEndpoint() {
		return destinationEndpoint;
	}

	public void setDestinationEndpoint(EndPoint destinationEndpoint) {
		this.destinationEndpoint = destinationEndpoint;
	}

	@Override
	public void decode() throws MalformedPCEPObjectException {
		int offset=8;
		int tlvtype=PCEPTLV.getType(this.getBytes(), offset);
		int tlvlength=PCEPTLV.getTotalTLVLength(this.getBytes(), offset);
//		
//		if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_IPV4_ADDRESS){
//			endPointIPv4=new EndPointIPv4TLV(bytes, offset);
//		}
//		else if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_UNNUMBERED_ENDPOINT){
//			unnumberedEndpoint=new UnnumberedEndpointTLV(bytes, offset);
//		}
		
	}
	
	

}
