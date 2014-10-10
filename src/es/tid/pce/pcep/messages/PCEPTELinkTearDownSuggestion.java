package es.tid.pce.pcep.messages;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.EndPoints;
import es.tid.pce.pcep.objects.EndPointsIPv4;
import es.tid.pce.pcep.objects.EndPointsIPv6;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;

/**
 
 * PCEP TE Link Suggestion.
 * @author ogondio
 * 
**/

public class PCEPTELinkTearDownSuggestion extends PCEPMessage {
	
	private EndPoints endPoints;
	private int LSPid;

	public int getLSPid() {
		return LSPid;
	}

	public void setLSPid(int lSPid) {
		LSPid = lSPid;
	}

	/**
	 * Construct new PCEP CLOSE Message
	 */
	public PCEPTELinkTearDownSuggestion () {
		super();
		this.setMessageType(PCEPMessageTypes.MESSAGE_TE_LINK_TEAR_DOWN_SUGGESTION);
		
	}
	
	/**
	 * Contructs and decodes new PCEP TELinkSuggestion Message from a byte array
	 * @param bytes Bytes of the message
	 * @throws PCEPProtocolViolationException
	 */
	public PCEPTELinkTearDownSuggestion(byte[] bytes) throws PCEPProtocolViolationException {
		super(bytes);
		this.decode();
	}

	
	/**
	 * Encode the PCEP Message
	 */
	public void encode() throws PCEPProtocolViolationException {
		if (endPoints==null){
			throw new PCEPProtocolViolationException();
		}
		endPoints.encode();
		this.setMessageLength(4+endPoints.getLength());
		this.messageBytes=new byte[this.getLength()];
		encodeHeader();
		System.arraycopy(endPoints.getBytes(), 0, this.messageBytes, 4, endPoints.getLength());				
	}
	
	/**
	 * Decode a PCEP Message from a byte array. 
	 * The byte array is copied in messageBytes
	 */
	private void decode()  throws PCEPProtocolViolationException {
		log.finest("Decoding PCEP TE Link Suggestion Message");
		int offset=4;//We start after the object header
		int oc=PCEPObject.getObjectClass(this.messageBytes, offset);
		oc=PCEPObject.getObjectClass(this.messageBytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS){
			int ot=PCEPObject.getObjectType(this.messageBytes, offset);
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV4){
				try {
					endPoints=new EndPointsIPv4(this.messageBytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed ENDPOINTS IPV4 Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV6){
				try {
					endPoints=new EndPointsIPv6(this.messageBytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed ENDPOINTSIPV6 Object found");
					throw new PCEPProtocolViolationException();
				}
			}
		
		}

	}

	public EndPoints getEndPoints() {
		return endPoints;
	}

	public void setEndPoints(EndPoints endPoints) {
		this.endPoints = endPoints;
	}	
	
	
}