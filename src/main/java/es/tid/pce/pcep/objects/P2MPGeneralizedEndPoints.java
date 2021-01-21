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
public class P2MPGeneralizedEndPoints extends GeneralizedEndPoints {
	
	private EndPointAndRestrictions EndpointAndRestrictions;
	private LinkedList <EndPointAndRestrictions> EndpointAndRestrictionsList;

	public P2MPGeneralizedEndPoints() {
		super();
		this.setGeneralizedEndPointsType(1);		
		EndpointAndRestrictionsList= new LinkedList <EndPointAndRestrictions>();
	}

	public P2MPGeneralizedEndPoints(byte[] bytes, int offset)
			throws MalformedPCEPObjectException, PCEPProtocolViolationException {
		super(bytes, offset);
		EndpointAndRestrictionsList= new LinkedList <EndPointAndRestrictions>();
		decode();
	}
	
	public void encode() {
		try {
			int len=8;
			if (EndpointAndRestrictionsList.size()>0){
				for (int i=0;i<EndpointAndRestrictionsList.size();++i){
					(EndpointAndRestrictionsList.get(i)).encode();
					len=len+(EndpointAndRestrictionsList.get(i)).getLength();
				}
			}
			if (EndpointAndRestrictions != null)
			{
				EndpointAndRestrictions.encode();
				len=len+EndpointAndRestrictions.getLength();
			}
			this.setObjectLength(len);
			this.object_bytes=new byte[this.getLength()];
			encode_header();
			encodeEndpointHeader();
			int offset=8;
			
			
			System.arraycopy(EndpointAndRestrictions.getBytes(), 0, this.object_bytes, offset, EndpointAndRestrictions.getLength());
			offset=offset+EndpointAndRestrictions.getLength();
			
			if (EndpointAndRestrictionsList!=null)
			{
				for (int i=0;i<EndpointAndRestrictionsList.size();++i)
				{
					System.arraycopy(EndpointAndRestrictionsList.get(i).getBytes(), 0, this.object_bytes, offset, EndpointAndRestrictionsList.get(i).getLength());
					offset=offset+EndpointAndRestrictionsList.get(i).getLength();
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	@Override
	public void decode() throws MalformedPCEPObjectException {
		try {
			int offset=8;
			int max_offset = this.getLength();
			if (offset>=max_offset){
				log.warn("Empty P2MPEndpoints !!!");
				throw new PCEPProtocolViolationException();
			}
			
			EndpointAndRestrictions = new EndPointAndRestrictions(this.object_bytes, offset);
			offset = offset + EndpointAndRestrictions.getLength();
			
			while (offset < max_offset)
			{
				EndPointAndRestrictions EndpointAndRestrictions = new EndPointAndRestrictions(this.object_bytes, offset);
				EndpointAndRestrictionsList.add(EndpointAndRestrictions);
				offset = offset + EndpointAndRestrictions.getLength();
			}
		} catch (PCEPProtocolViolationException e) {
			// TODO Auto-generated catch block
			throw new MalformedPCEPObjectException();
		}
		
	}

	public EndPointAndRestrictions getEndpointAndRestrictions() {
		return EndpointAndRestrictions;
	}

	public void setEndpointAndRestrictions(EndPointAndRestrictions endpointAndRestrictions) {
		EndpointAndRestrictions = endpointAndRestrictions;
	}

	public LinkedList<EndPointAndRestrictions> getEndpointAndRestrictionsList() {
		return EndpointAndRestrictionsList;
	}

	public void setEndpointAndRestrictionsList(LinkedList<EndPointAndRestrictions> endpointAndRestrictionsList) {
		EndpointAndRestrictionsList = endpointAndRestrictionsList;
	}
	



}
