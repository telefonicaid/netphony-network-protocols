package es.tid.pce.pcep.constructs;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;



public class AssistedUnicastEndpoints extends PCEPConstruct {

	private EndPoint sourceEndpoint;
	private LinkedList <EndPoint> sourceEndpointsList;
	
	private EndPoint destinationEndpoint;
	private LinkedList <EndPoint> destinationEndpointsList;
	
	public AssistedUnicastEndpoints() {
		sourceEndpointsList=new LinkedList<EndPoint> ();
		destinationEndpointsList=new LinkedList<EndPoint> ();
	}
	
	public AssistedUnicastEndpoints(byte[] bytes, int offset) throws PCEPProtocolViolationException {
		sourceEndpointsList=new LinkedList<EndPoint> ();
		destinationEndpointsList=new LinkedList<EndPoint> ();
		decode(bytes,offset);
	}


	@Override
	public void encode() throws PCEPProtocolViolationException {
		int len=0;
		if (sourceEndpointsList.size()>0){
			for (int i=0;i<sourceEndpointsList.size();++i){
				(sourceEndpointsList.get(i)).encode();
				len=len+(sourceEndpointsList.get(i)).getLength();
			}
		}
		if (destinationEndpointsList.size()>0){
			for (int i=0;i<destinationEndpointsList.size();++i){
				(destinationEndpointsList.get(i)).encode();
				len=len+(destinationEndpointsList.get(i)).getLength();
			}
		}

		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		
		if ((sourceEndpointsList!=null) && (destinationEndpointsList!=null)){
			for (int i=0;i<sourceEndpointsList.size();++i){
				System.arraycopy(sourceEndpointsList.get(i).getBytes(), 0, bytes, offset, sourceEndpointsList.get(i).getLength());
				offset=offset+sourceEndpointsList.get(i).getLength();
				System.arraycopy(destinationEndpointsList.get(i).getBytes(), 0, bytes, offset, destinationEndpointsList.get(i).getLength());
				offset=offset+destinationEndpointsList.get(i).getLength();
			}
		}
		
	}

	private void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException {

		int max_offset=bytes.length;
		if (offset>=max_offset){
			log.warn("Empty AssistedUnicastEndpoints construct!!!");
			throw new PCEPProtocolViolationException();
		}
		while (offset<max_offset){
			try {
				sourceEndpoint = new EndPoint(bytes, offset);
			} catch (MalformedPCEPObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sourceEndpointsList.add(sourceEndpoint);
			offset = offset + sourceEndpoint.getLength();
			try {
				destinationEndpoint = new EndPoint(bytes, offset);
			} catch (MalformedPCEPObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			destinationEndpointsList.add(destinationEndpoint);
			offset = offset + destinationEndpoint.getLength();
		}
	}
	
	public EndPoint getSourceEndPoint() {
		return sourceEndpoint;
	}

	public void setSourceEndPoint(EndPoint sourceEndPoint) {
		this.sourceEndpoint = sourceEndPoint;
	}
	
	public EndPoint getDestinationEndPoint() {
		return destinationEndpoint;
	}

	public void setEndPoint(EndPoint sourceEndPoint) {
		this.destinationEndpoint = sourceEndPoint;
	}

	public LinkedList <EndPoint> getSourceEndPointsList() {
		return sourceEndpointsList;
	}

	public void setSourceEndPointsList(LinkedList <EndPoint> sourceEndPointsList) {
		this.sourceEndpointsList = sourceEndPointsList;
	}
	
	public LinkedList <EndPoint> getDestinationEndPointsList() {
		return destinationEndpointsList;
	}

	public void setDestinationEndPointsList(LinkedList <EndPoint> destinationEndPointsList) {
		this.destinationEndpointsList = destinationEndPointsList;
	}
}
