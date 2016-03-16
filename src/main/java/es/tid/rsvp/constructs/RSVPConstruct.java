package es.tid.rsvp.constructs;

import es.tid.rsvp.*;

/**
 * 
 * <p> A RSVP Construct is a rule that joins RSVP Objects and Constructions. 
 * Each specific construct will have its elements </p>
 * @author Fernando Munoz del Nuevo
 *
 */

public abstract class RSVPConstruct implements RSVPElement{

	protected byte[] bytes;
	protected int length;	
	
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Decode the Element based on the bytes passed, starting in the offset position
	 * @param bytes Bytes of the RSVP Construct 
	 * @param offset offset in bytes where the construct starts
	 * @throws RSVPProtocolViolationException Exception thrown when the bytes decoded do not lead to a valid RSVP Message
	 */
	public abstract void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException;

	
	
}
