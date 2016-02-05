package es.tid.pce.pcep.messages;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.Path;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;

/**
 
 * PCEP TE Link Suggestion.
 * @author ogondio
 * 
**/

public class PCEPTELinkSuggestion extends PCEPMessage {
	
	private Path path;

	/**
	 * Construct new PCEP CLOSE Message
	 */
	public PCEPTELinkSuggestion () {
		super();
		this.setMessageType(PCEPMessageTypes.MESSAGE_INITIATE);
		
	}
	
	/**
	 * Contructs and decodes new PCEP TELinkSuggestion Message from a byte array
	 * @param bytes Bytes of the message
	 * @throws PCEPProtocolViolationException
	 */
	public PCEPTELinkSuggestion(byte[] bytes) throws PCEPProtocolViolationException {
		super(bytes);
		this.decode();
	}
	/**
	 * Encode the PCEP Message
	 */
	public void encode() throws PCEPProtocolViolationException {
		if (path==null){
			throw new PCEPProtocolViolationException();
		}
		path.encode();
		this.setMessageLength(4+path.getLength());
		this.messageBytes=new byte[this.getLength()];
		encodeHeader();
		System.arraycopy(path.getBytes(), 0, this.messageBytes, 4, path.getLength());	
		//FuncionesUtiles.printByte(path.getBytes(), "Bytes que codificamos");
	}
	
	/**
	 * Decode a PCEP Message from a byte array. 
	 * The byte array is copied in messageBytes
	 */
	private void decode()  throws PCEPProtocolViolationException {
		//Decoding PCEP TE Link Suggestion Message
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		PCEPTELinkSuggestion other = (PCEPTELinkSuggestion) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	
}