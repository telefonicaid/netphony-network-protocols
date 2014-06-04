package tid.pce.pcep.constructs;

import java.util.LinkedList;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.objects.MalformedPCEPObjectException;
import tid.pce.pcep.objects.ObjectParameters;
import tid.pce.pcep.objects.tlvs.PCEPTLV;

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
		log.finest("Encoding EndpointAndRestrictions Construct");
		
		int len=0;
	
		endPoint.encode();
		len=len+endPoint.getLength();
		if ((EndpointRestrictionList!=null)&&(EndpointRestrictionList.size()>0)){
			for (int i=0;i<EndpointRestrictionList.size();++i){
				(EndpointRestrictionList.get(i)).encode();
				len=len+(EndpointRestrictionList.get(i)).getLength();
			}
		}
		
		log.info("EndPointAndRestriction Length = "+len);
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
		
		log.info("Decoding EndPointAndRestrictions");
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
}