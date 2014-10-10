package es.tid.pce.pcep.constructs;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.EndPoint;
import es.tid.pce.pcep.constructs.EndpointRestriction;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;

/**
 *      <fullAnycast-endpoints> ::=
              <endpoint>
              [<endpoint-restrictions>]
              <endpoint>
              [<endpoint-restrictions>]
              
        
        For <endpoint> only Storage, Server or Application TLVs are allowed
 **/

public class FullAnycastEndpoints extends PCEPConstruct {
	
//	private EndpointAndRestriction sourceEndpoint; 
//	private EndpointAndRestriction destinationEndpoint;
	private EndPoint sourceEndpoint;
	private LinkedList <EndpointRestriction> sourceEndpointRestrictionList;

	private EndPoint destinationEndpoint;
	private LinkedList <EndpointRestriction> destinationEndpointRestrictionList;
	
	
	public FullAnycastEndpoints(){
		sourceEndpointRestrictionList=new LinkedList<EndpointRestriction> ();
		destinationEndpointRestrictionList=new LinkedList<EndpointRestriction>();	
	}
	
	public FullAnycastEndpoints(byte[] bytes, int offset) throws PCEPProtocolViolationException, MalformedPCEPObjectException{
		sourceEndpointRestrictionList=new LinkedList<EndpointRestriction> ();
		destinationEndpointRestrictionList=new LinkedList<EndpointRestriction>();
		decode(bytes,offset);
	}

	@Override
	public void encode() throws PCEPProtocolViolationException {
		// TODO Auto-generated method stub
		log.finest("Encoding FullAnycastEndpoints Construct");
		
		int len=0;
		
		sourceEndpoint.encode();
		len=len+sourceEndpoint.getLength();
		if (sourceEndpointRestrictionList.size()>0){
			for (int i=0;i<sourceEndpointRestrictionList.size();++i){
				(sourceEndpointRestrictionList.get(i)).encode();
				len=len+(sourceEndpointRestrictionList.get(i)).getLength();
			}
		}
		
		destinationEndpoint.encode();
		len=len+destinationEndpoint.getLength();
		if (destinationEndpointRestrictionList.size()>0){
			for (int i=0;i<destinationEndpointRestrictionList.size();++i){
				(destinationEndpointRestrictionList.get(i)).encode();
				len=len+(destinationEndpointRestrictionList.get(i)).getLength();
			}
		}

		log.info("Length = "+len);
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		
		System.arraycopy(sourceEndpoint.getBytes(), 0, bytes, offset, sourceEndpoint.getLength());
		offset=offset+sourceEndpoint.getLength();
		
		if (sourceEndpointRestrictionList!=null){
			for (int i=0;i<sourceEndpointRestrictionList.size();++i){
				System.arraycopy(sourceEndpointRestrictionList.get(i).getBytes(), 0, bytes, offset, sourceEndpointRestrictionList.get(i).getLength());
				offset=offset+sourceEndpointRestrictionList.get(i).getLength();
			}
		}
		
		System.arraycopy(destinationEndpoint.getBytes(), 0, bytes, offset, destinationEndpoint.getLength());
		offset=offset+destinationEndpoint.getLength();
		
		if (destinationEndpointRestrictionList!=null){
			for (int i=0;i<destinationEndpointRestrictionList.size();++i){
				System.arraycopy(destinationEndpointRestrictionList.get(i).getBytes(), 0, bytes, offset, destinationEndpointRestrictionList.get(i).getLength());
				offset=offset+destinationEndpointRestrictionList.get(i).getLength();
			}
		}

	}
	
	private void decode(byte[] bytes, int offset)
	throws PCEPProtocolViolationException, MalformedPCEPObjectException {
		log.info("Decoding FullAnycastEndpoints Construct");
		int max_offset=bytes.length;
		if (offset>=max_offset){
			log.warning("Empty FullAnycastEndpoints construct!!!");
			throw new PCEPProtocolViolationException();
		}
		
		sourceEndpoint = new EndPoint(bytes, offset);
		offset = offset + sourceEndpoint.getLength();
		
		while (PCEPTLV.getType(bytes, offset)==ObjectParameters.PCEP_TLV_TYPE_LABEL_REQUEST){
			EndpointRestriction sourceEndpointRestriction = new EndpointRestriction(bytes, offset);
			sourceEndpointRestrictionList.add(sourceEndpointRestriction);
			offset = offset + sourceEndpointRestriction.getLength();
		}
		
		destinationEndpoint = new EndPoint(bytes, offset);
		offset = offset + destinationEndpoint.getLength();
		
		while (PCEPTLV.getType(bytes, offset)==ObjectParameters.PCEP_TLV_TYPE_LABEL_REQUEST){
			EndpointRestriction destinationEndpointRestriction = new EndpointRestriction(bytes, offset);
			destinationEndpointRestrictionList.add(destinationEndpointRestriction);
			offset = offset + destinationEndpointRestriction.getLength();
		}
		
		
	}
	
	public EndPoint getSourceEndPoint() {
		return sourceEndpoint;
	}

	public void setSourceEndPoints(EndPoint sourceEndPoint) {
		this.sourceEndpoint = sourceEndPoint;
	}

	public LinkedList <EndpointRestriction> getSourceEndPointRestrictionList() {
		return sourceEndpointRestrictionList;
	}

	public void setSourceEndPointRestrictionList(LinkedList <EndpointRestriction> sourceEndPointRestrictionList) {
		this.sourceEndpointRestrictionList = sourceEndPointRestrictionList;
	}
	
	public EndPoint getDestinationEndPoint() {
		return destinationEndpoint;
	}

	public void setDestinationEndPoints(EndPoint destinationEndPoint) {
		this.destinationEndpoint = destinationEndPoint;
	}
	
	public LinkedList <EndpointRestriction> getDestinationEndPointRestrictionList() {
		return destinationEndpointRestrictionList;
	}

	public void setDestinationEndPointRestrictionList(LinkedList <EndpointRestriction> sourceEndPointRestrictionList) {
		this.destinationEndpointRestrictionList = sourceEndPointRestrictionList;
	}
}