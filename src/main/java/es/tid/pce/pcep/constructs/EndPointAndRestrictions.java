package es.tid.pce.pcep.constructs;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;

public class EndPointAndRestrictions extends PCEPConstruct{

	private EndPoint endPoint;
	private LinkedList <EndpointRestriction> EndpointRestrictionList;
	
	
	public EndPointAndRestrictions()
	{
	}
	
	public EndPointAndRestrictions(byte[] bytes, int offset)throws MalformedPCEPObjectException {
		EndpointRestrictionList=new LinkedList<EndpointRestriction> ();
		decode(bytes,offset);
	}

	public void encode() throws PCEPProtocolViolationException {
		
		int len=0;
	
		endPoint.encode();
		len=len+endPoint.getLength();
		if ((EndpointRestrictionList!=null)&&(EndpointRestrictionList.size()>0)){
			for (int i=0;i<EndpointRestrictionList.size();++i){
				(EndpointRestrictionList.get(i)).encode();
				len=len+(EndpointRestrictionList.get(i)).getLength();
			}
		}
		
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		
		System.arraycopy(endPoint.getBytes(), 0, bytes, offset, endPoint.getLength());
		offset=offset+endPoint.getLength();
		
		if (EndpointRestrictionList!=null){
			for (int i=0;i<EndpointRestrictionList.size();++i){
				System.arraycopy(EndpointRestrictionList.get(i).getBytes(), 0, bytes, offset, EndpointRestrictionList.get(i).getLength());
				offset=offset+EndpointRestrictionList.get(i).getLength();
			}
		}
	}
	
	public void decode(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		int len=0;
		endPoint = new EndPoint(bytes, offset);
		offset = offset + endPoint.getLength();
		len += endPoint.getLength();
		
		
		while ((offset < bytes.length) && (PCEPTLV.getType(bytes, offset)==ObjectParameters.PCEP_TLV_TYPE_LABEL_REQUEST))
		{
			EndpointRestriction EndpointRestriction = new EndpointRestriction(bytes, offset);
			EndpointRestrictionList.add(EndpointRestriction);
			offset = offset + EndpointRestriction.getLength();
			len += EndpointRestriction.getLength();
		}
		
		this.setLength(len);
		
	}

	public EndPoint getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(EndPoint EndPoint) {
		this.endPoint = EndPoint;
	}

	public LinkedList <EndpointRestriction> getEndPointRestrictionList() {
		return EndpointRestrictionList;
	}

	public void setEndPointRestrictionList(LinkedList <EndpointRestriction> EndPointRestrictionList) {
		this.EndpointRestrictionList = EndPointRestrictionList;
	}

	public LinkedList<EndpointRestriction> getEndpointRestrictionList() {
		return EndpointRestrictionList;
	}

	public void setEndpointRestrictionList(
			LinkedList<EndpointRestriction> endpointRestrictionList) {
		EndpointRestrictionList = endpointRestrictionList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((EndpointRestrictionList == null) ? 0
						: EndpointRestrictionList.hashCode());
		result = prime * result
				+ ((endPoint == null) ? 0 : endPoint.hashCode());
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
		EndPointAndRestrictions other = (EndPointAndRestrictions) obj;
		if (EndpointRestrictionList == null) {
			if (other.EndpointRestrictionList != null)
				return false;
		} else if (!EndpointRestrictionList
				.equals(other.EndpointRestrictionList))
			return false;
		if (endPoint == null) {
			if (other.endPoint != null)
				return false;
		} else if (!endPoint.equals(other.endPoint))
			return false;
		return true;
	}
	
	
}