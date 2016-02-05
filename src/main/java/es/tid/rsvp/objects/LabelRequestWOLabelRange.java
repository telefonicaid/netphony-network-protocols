package es.tid.rsvp.objects;

import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import org.slf4j.LoggerFactory;

/*

RFC 3209     RSVP-TE

RFC 3209     RSVP-TE

4.2. Label Request Object

   The Label Request Class is 19.  Currently there are three possible
   C_Types.  Type 1 is a Label Request without label range.  Type 2 is a
   label request with an ATM label range.  Type 3 is a label request
   with a Frame Relay label range.  The LABEL_REQUEST object formats are
   shown below.

4.2.1. Label Request without Label Range

   Class = 19, C_Type = 1

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |           Reserved            |             L3PID             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Reserved

         This field is reserved.  It MUST be set to zero on transmission
         and MUST be ignored on receipt.

      L3PID

         an identifier of the layer 3 protocol using this path.
         Standard Ethertype values are used.



 */

public class LabelRequestWOLabelRange extends LabelRequest{

	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Constructor to be used when a new Label Request Without Label Range Object 
	 * wanted to be attached to a new message
	 * @param l3PID The identifier of the layer 3 protocol using this path. Standard Ethertype values are used.
	 */
	
	public LabelRequestWOLabelRange(int l3PID){
		
		classNum = 19;
		cType = 1;
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 4;
		
		reserved = 0;
		this.l3PID = l3PID;
		
		log.debug("Label Request Without Label Range Object Created");

	}
	
	/**
	 * Constructor to be used when a new Label Request Without Label Range Object wanted to be decoded from a received
	 * message.
	 * @param bytes
	 * @param offset
	 */
	
	public LabelRequestWOLabelRange(byte[] bytes, int offset){
		
		this.decodeHeader(bytes,offset);
		this.bytes = new byte[RSVPObject.getLength(bytes, offset)];
		
		log.debug("Label Request Without Label Range Object Created");
		
	}
	
	public void encode() throws RSVPProtocolViolationException{
		
		// Encontramos la longitud del objeto Label Request Without Label Range Object
		
		this.length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;	// Cabecera 
		this.length = this.length + 4;	// Longitud de la etiqueta
		
		this.bytes = new byte[this.length];
		encodeHeader();
		
		int currentIndex = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		bytes[currentIndex] = (byte)((reserved >> 8) & 0xFF);	// Reserved a 0
		bytes[currentIndex+1] = (byte)(reserved);	// Reserved a 0
		bytes[currentIndex+2] = (byte)((l3PID >> 8) & 0xFF);
		bytes[currentIndex+3] = (byte)((l3PID) & 0xFF);
		
	}
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException{

		log.debug("Starting Label Request Without Label Range Object decoding");
		
		int currentIndex = offset + RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		l3PID = (int)(bytes[currentIndex+2] | bytes[currentIndex+3]);
		
		log.debug("Label Request Without Label Range Object decoded");
		
	}
	
	// Getters & Setters

}
