package es.tid.pce.pcep.constructs;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;


public class P2MPEndpoints extends PCEPConstruct {
	
	
	private EndPointAndRestrictions EndpointAndRestrictions;
	private LinkedList <EndPointAndRestrictions> EndpointAndRestrictionsList;
	
	
	public P2MPEndpoints(){
		EndpointAndRestrictionsList=new LinkedList<EndPointAndRestrictions> ();	
	}

	public P2MPEndpoints(byte[] object_bytes, int offset) throws PCEPProtocolViolationException, MalformedPCEPObjectException {
		EndpointAndRestrictionsList=new LinkedList<EndPointAndRestrictions> ();
		this.bytes = object_bytes;
		decode(bytes,offset);
	}

	@Override
	public void encode() throws PCEPProtocolViolationException {
		//Encoding P2MPEndpoints Construct
		
		int len=0;
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
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		
		
		System.arraycopy(EndpointAndRestrictions.getBytes(), 0, bytes, offset, EndpointAndRestrictions.getLength());
		offset=offset+EndpointAndRestrictions.getLength();
		
		if (EndpointAndRestrictionsList!=null)
		{
			for (int i=0;i<EndpointAndRestrictionsList.size();++i)
			{
				System.arraycopy(EndpointAndRestrictionsList.get(i).getBytes(), 0, bytes, offset, EndpointAndRestrictionsList.get(i).getLength());
				offset=offset+EndpointAndRestrictionsList.get(i).getLength();
			}
		}
	}
	
	private void decode(byte[] bytes, int offset)
	throws PCEPProtocolViolationException, MalformedPCEPObjectException {
		
		int max_offset = bytes.length;
		if (offset>=max_offset){
			log.warn("Empty P2MPEndpoints construct!!!");
			throw new PCEPProtocolViolationException();
		}
		
		EndpointAndRestrictions = new EndPointAndRestrictions(bytes, offset);
		offset = offset + EndpointAndRestrictions.getLength();
		
		while (offset < max_offset)
		{
			EndPointAndRestrictions EndpointAndRestrictions = new EndPointAndRestrictions(bytes, offset);
			EndpointAndRestrictionsList.add(EndpointAndRestrictions);
			offset = offset + EndpointAndRestrictions.getLength();
		}
	}
	
	public EndPointAndRestrictions getEndPointAndRestrictions() {
		return EndpointAndRestrictions;
	}

	public void setEndPointAndRestrictions(EndPointAndRestrictions EndPointAndRestrictions) {
		this.EndpointAndRestrictions = EndPointAndRestrictions;
	}

	public LinkedList <EndPointAndRestrictions> getEndPointAndRestrictionsList() {
		return EndpointAndRestrictionsList;
	}

	public void setEndPointAndRestrictionsList(LinkedList <EndPointAndRestrictions> EndPointAndRestrictionsList) {
		this.EndpointAndRestrictionsList = EndPointAndRestrictionsList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((EndpointAndRestrictions == null) ? 0
						: EndpointAndRestrictions.hashCode());
		result = prime
				* result
				+ ((EndpointAndRestrictionsList == null) ? 0
						: EndpointAndRestrictionsList.hashCode());
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
		P2MPEndpoints other = (P2MPEndpoints) obj;
		if (EndpointAndRestrictions == null) {
			if (other.EndpointAndRestrictions != null)
				return false;
		} else if (!EndpointAndRestrictions
				.equals(other.EndpointAndRestrictions))
			return false;
		if (EndpointAndRestrictionsList == null) {
			if (other.EndpointAndRestrictionsList != null)
				return false;
		} else if (!EndpointAndRestrictionsList
				.equals(other.EndpointAndRestrictionsList))
			return false;
		return true;
	}
	
	

}
