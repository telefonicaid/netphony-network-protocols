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
		// TODO Auto-generated method stub
		log.info("Encoding P2MPEndpoints Construct");
		
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

		log.info("P2MPEndpoints Construct Length = "+len);
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
		
		log.info("Decoding P2MPEndpoints Construct");
		log.info("bytes.length::"+bytes.length);
		int max_offset = bytes.length;
		if (offset>=max_offset){
			log.warning("Empty P2MPEndpoints construct!!!");
			throw new PCEPProtocolViolationException();
		}
		log.info("Offset::"+offset);
		
		EndpointAndRestrictions = new EndPointAndRestrictions(bytes, offset);
		offset = offset + EndpointAndRestrictions.getLength();
		
		while (offset < max_offset)
		{
			EndPointAndRestrictions EndpointAndRestrictions = new EndPointAndRestrictions(bytes, offset);
			EndpointAndRestrictionsList.add(EndpointAndRestrictions);
			offset = offset + EndpointAndRestrictions.getLength();
			log.info("EndpointAndRestrictions.getLength():"+EndpointAndRestrictions.getLength());
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

}
