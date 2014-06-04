package tid.rsvp.objects;

import java.util.logging.Level;
import java.util.logging.Logger;

import tid.rsvp.RSVPProtocolViolationException;

/*

3.4. Suggested Label

   The Suggested Label is used to provide a downstream node with the
   upstream node's label preference.  This permits the upstream node to
   start configuring its hardware with the proposed label before the
   label is communicated by the downstream node.  Such early
   configuration is valuable to systems that take non-trivial time to
   establish a label in hardware.  Such early configuration can reduce




Berger                      Standards Track                    [Page 13]

RFC 3471        GMPLS Signaling Functional Description


   setup latency, and may be important for restoration purposes where
   alternate LSPs may need to be rapidly established as a result of
   network failures.

   The use of Suggested Label is only an optimization.  If a downstream
   node passes a different label upstream, an upstream LSR reconfigures
   itself so that it uses the label specified by the downstream node,
   thereby maintaining the downstream control of a label.  Note, the
   transmission of a suggested label does not imply that the suggested
   label is available for use.  In particular, an ingress node should
   not transmit data traffic on a suggested label until the downstream
   node passes a label upstream.

   The information carried in a suggested label is identical to a
   generalized label.  Note, values used in the label field of a
   suggested label are from the object/TLV sender's perspective.



 */

public class SuggestedLabel extends Label{

	/**
	 * Log
	 */
		
	private Logger log;
	
	/**
	 * Constructor to be used when a new Label Object wanted to be attached to a new message
	 * @param label The label number requested
	 */
	
	public SuggestedLabel(int label){
		
		super(label);
		
		this.classNum = 129;
		this.cType = 2;
		this.length = 8;
		this.label = label;
		
		this.bytes = new byte[length];
		
		log = Logger.getLogger("ROADM");

		log.finest("Label Object Created");

	}
	
	/**
	 * Constructor to be used when a new ERO Object wanted to be decoded from a received
	 * message.
	 * @param bytes
	 * @param offset
	 */
	
	public SuggestedLabel(byte[] bytes, int offset){
		
		super(bytes, offset);
		
		this.decodeHeader(bytes,offset);
		this.bytes = new byte[this.getLength()];
		
		log = Logger.getLogger("ROADM");

		log.finest("Label Object Created");
		
	}
	
	public void encode() throws RSVPProtocolViolationException{
		
		// Encontramos la longitud del objeto Label
		
		encodeHeader();
		
		int currentIndex = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		bytes[currentIndex] = (byte)((this.label >> 24) & 0xFF);
		bytes[currentIndex+1] = (byte)((this.label >> 16) & 0xFF);
		bytes[currentIndex+2] = (byte)((this.label >> 8) & 0xFF);
		bytes[currentIndex+3] = (byte)((this.label) & 0xFF);
		
	}
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException{

		int currentIndex = offset + RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		super.setLabel((int)(bytes[currentIndex] | bytes[currentIndex+1] | bytes[currentIndex+2] | bytes[currentIndex+3]));
		
	}
	
	// Getters & Setters

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}




}
