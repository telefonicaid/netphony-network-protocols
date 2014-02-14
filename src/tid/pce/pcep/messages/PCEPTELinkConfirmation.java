package tid.pce.pcep.messages;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.constructs.Path;
import tid.pce.pcep.objects.ObjectParameters;
import tid.pce.pcep.objects.PCEPObject;

/**
 * PCEP TE Link Suggestion.
 * @author ogondio
 * 
**/

public class PCEPTELinkConfirmation extends PCEPMessage {
	
	private Path path;
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
	public PCEPTELinkConfirmation () {
		super();
		this.setMessageType(PCEPMessageTypes.MESSAGE_TE_LINK_SUGGESTION_CONFIRMATION);
	}
	
	/**
	 * Contructs and decodes new PCEP TELinkSuggestion Message from a byte array
	 * @param bytes Bytes of the message
	 * @throws PCEPProtocolViolationException
	 */
	public PCEPTELinkConfirmation(byte[] bytes) throws PCEPProtocolViolationException {
		super(bytes);
		this.decode();
	}
	
	/**
	 * Encode the PCEP Message
	 */
	public void encode() throws PCEPProtocolViolationException {
		if (path==null && (LSPid!=-1)){
			throw new PCEPProtocolViolationException();
		}
		path.encode();
		this.setMessageLength(4+path.getLength());
		this.messageBytes=new byte[this.getLength()];
		encodeHeader();
		System.arraycopy(path.getBytes(), 0, this.messageBytes, 4, path.getLength());
	}
	
	/**
	 * Decode a PCEP Message from a byte array. 
	 * The byte array is copied in messageBytes
	 */
	private void decode()  throws PCEPProtocolViolationException {
		log.finest("Decoding PCEP TE Link Suggestion Message");
		int offset=4;//We start after the object header
		int oc=PCEPObject.getObjectClass(this.messageBytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_ERO){
			path=new Path(this.messageBytes, offset);
			offset=offset+path.getLength();
		}
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}
}