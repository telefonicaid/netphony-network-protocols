package es.tid.rsvp.objects.gmpls;

import org.slf4j.Logger;

import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.objects.Label;
import es.tid.rsvp.objects.RSVPObjectParameters;
import org.slf4j.LoggerFactory;

/**
 * @author Fernando Munoz del Nuevo
 * 
 * 
		0                   1                   2                   3
	    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	   |            Length             | Class-Num (16)|   C-Type (2)  |
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	   |                             Label                             |
	   |                              ...                              |
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
		
		See [RFC3471] for a description of parameters.

	RFC 3473
	
	*/


public class GeneralizedLabel extends Label {
	
	private long label;
	
	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
  public GeneralizedLabel(){
	  super();
		classNum = 16;
		cType = 2;
  }
	
	public GeneralizedLabel(long label){
		super();
		classNum = 16;
		cType = 2;

		this.label = label;

		
		
		log.debug("Generalized Label Request Object Created");
		
		
	}
	
	/**
	 * Constructor to be used when a new Label Request With ATM Label Range Object wanted to be decoded from a received
	 * message.
	 * @param bytes bytes
	 * @param offset offset
	 * @throws RSVPProtocolViolationException RSVP Protocol Violation Exception
	 */
	
	public GeneralizedLabel(byte[] bytes, int offset) throws RSVPProtocolViolationException{
		super();
		decode();
		log.debug("Generalized Label Request Object Created");
		
	}	
	
	public void encode() throws RSVPProtocolViolationException{
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 4;

		bytes = new byte[this.length];
		encodeHeader();
		
		bytes[4] = (byte)((label >> 24) & 0xFF);
		bytes[5] = (byte)((label >> 16) & 0xFF);
		bytes[6] = (byte)((label >> 8) & 0xFF);
		bytes[7] = (byte)((label) & 0xFF);
	
	}
	
	public void decode() throws RSVPProtocolViolationException{
		int offset=4;
		label  = ByteHandler.decode4bytesLong(bytes,offset);
	}
	
	// Getters & Setters
	
	public long getLabel() {
		return label;
	}

	public void setLabel(long label) {
		this.label = label;
	}

}
