package es.tid.rsvp;

/**
 * <p>Interface class for all RSVP Elements, which are RSVP Messages, RSVP Constructs and RSVP Objects.</p>
 * 
 * @author Fernando Munoz del Nuevo
 *
 */

public interface RSVPElement {
	
	/**
	 * Generic method to encode an RSVP element
	 * @throws RSVPProtocolViolationException Exception is thrown when there is a problem encoding the RSVP Message
	 */
	
	public void encode() throws RSVPProtocolViolationException;
	
	/**
	 * Generic method to get the byte array from an encoded RSVP element
	 * @return The byte array with the encoded RSVP element
	 */
	public byte[] getBytes();
	
	/**
	 * Generic method to get length an encoded RSVP element byte array
	 * @return The length of with the encoded RSVP element
	 */
	
	public int getLength();
	
}
