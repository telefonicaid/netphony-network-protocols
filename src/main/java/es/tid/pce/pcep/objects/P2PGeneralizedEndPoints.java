package es.tid.pce.pcep.objects;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.EndPoint;
import es.tid.pce.pcep.constructs.EndPointAndRestrictions;
import es.tid.pce.pcep.constructs.EndpointRestriction;
import es.tid.pce.pcep.constructs.IPv4AddressEndPoint;
import es.tid.pce.pcep.constructs.UnnumIfEndPoint;
import es.tid.pce.pcep.objects.tlvs.EndPointIPv4TLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.pce.pcep.objects.tlvs.UnnumberedEndpointTLV;

/**
 * GeneralizedEndPoints Object of Type P2P End Points
 * @author ogondio
 *
 */
public class P2PGeneralizedEndPoints extends GeneralizedEndPoints {
	
	private EndPointAndRestrictions sourceEndpoint;
	
	private EndPointAndRestrictions destinationEndpoint;
	

	public P2PGeneralizedEndPoints() {
		super();
		this.setGeneralizedEndPointsType(1);		
	}

	public P2PGeneralizedEndPoints(byte[] bytes, int offset)
			throws MalformedPCEPObjectException, PCEPProtocolViolationException {
		super(bytes, offset);
		decode();
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
			offset=offset+sourceEndpoint.getLength();	
			System.arraycopy(destinationEndpoint.getBytes(),0, this.object_bytes, offset, destinationEndpoint.getLength());			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	public EndPointAndRestrictions getSourceEndpoint() {
		return sourceEndpoint;
	}

	public void setSourceEndpoint(EndPointAndRestrictions sourceEndpoint) {
		this.sourceEndpoint = sourceEndpoint;
	}

	public EndPointAndRestrictions getDestinationEndpoint() {
		return destinationEndpoint;
	}

	public void setDestinationEndpoint(EndPointAndRestrictions destinationEndpoint) {
		this.destinationEndpoint = destinationEndpoint;
	}

	@Override
	public void decode() throws MalformedPCEPObjectException {

			int offset=8;
			sourceEndpoint=new EndPointAndRestrictions(this.getBytes(), offset);
			offset+=sourceEndpoint.getLength();
			destinationEndpoint=new EndPointAndRestrictions(this.getBytes(), offset);
	
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer(100);
		sb.append("<GEP_P2P src = ");
		sb.append(sourceEndpoint.toString());
		sb.append(" dst = ");
		sb.append(destinationEndpoint.toString());
		sb.append(">");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((destinationEndpoint == null) ? 0 : destinationEndpoint.hashCode());
		result = prime * result + ((sourceEndpoint == null) ? 0 : sourceEndpoint.hashCode());
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
		P2PGeneralizedEndPoints other = (P2PGeneralizedEndPoints) obj;
		if (destinationEndpoint == null) {
			if (other.destinationEndpoint != null)
				return false;
		} else if (!destinationEndpoint.equals(other.destinationEndpoint))
			return false;
		if (sourceEndpoint == null) {
			if (other.sourceEndpoint != null)
				return false;
		} else if (!sourceEndpoint.equals(other.sourceEndpoint))
			return false;
		return true;
	}


}
