package es.tid.rsvp.objects;

import org.slf4j.Logger;

import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.RSVPProtocolViolationException;
import org.slf4j.LoggerFactory;

/*

RFC 3209     RSVP-TE

4.1. Label Object

   Labels MAY be carried in Resv messages.  For the FF and SE styles, a
   label is associated with each sender.  The label for a sender MUST
   immediately follow the FILTER_SPEC for that sender in the Resv
   message.

   The LABEL object has the following format:

   LABEL class = 16, C_Type = 1

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                           (top label)                         |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The contents of a LABEL is a single label, encoded in 4 octets.  Each
   generic MPLS label is an unsigned integer in the range 0 through
   1048575.  Generic MPLS labels and FR labels are encoded right aligned
   in 4 octets.  ATM labels are encoded with the VPI right justified in
   bits 0-15 and the VCI right justified in bits 16-31.

4.1.1. Handling Label Objects in Resv messages

   In MPLS a node may support multiple label spaces, perhaps associating
   a unique space with each incoming interface.  For the purposes of the
   following discussion, the term "same label" means the identical label
   value drawn from the identical label space.  Further, the following
   applies only to unicast sessions.

   Labels received in Resv messages on different interfaces are always
   considered to be different even if the label value is the same.

4.1.1.1. Downstream

   The downstream node selects a label to represent the flow.  If a
   label range has been specified in the label request, the label MUST
   be drawn from that range.  If no label is available the node sends a
   PathErr message with an error code of "routing problem" and an error
   value of "label allocation failure".

   If a node receives a Resv message that has assigned the same label
   value to multiple senders, then that node MAY also assign a single
   value to those same senders or to any subset of those senders.  Note

   that if a node intends to police individual senders to a session, it
   MUST assign unique labels to those senders.

   In the case of ATM, one further condition applies.  Some ATM nodes
   are not capable of merging streams.  These nodes MAY indicate this by
   setting a bit in the label request to zero.  The M-bit in the
   LABEL_REQUEST object of C-Type 2, label request with ATM label range,
   serves this purpose.  The M-bit SHOULD be set by nodes which are
   merge capable.  If for any senders the M-bit is not set, the
   downstream node MUST assign unique labels to those senders.

   Once a label is allocated, the node formats a new LABEL object.  The
   node then sends the new LABEL object as part of the Resv message to
   the previous hop.  The node SHOULD be prepared to forward packets
   carrying the assigned label prior to sending the Resv message.  The
   LABEL object SHOULD be kept in the Reservation State Block.  It is
   then used in the next Resv refresh event for formatting the Resv
   message.

   A node is expected to send a Resv message before its refresh timers
   expire if the contents of the LABEL object change.

4.1.1.2. Upstream

   A node uses the label carried in the LABEL object as the outgoing
   label associated with the sender.  The router allocates a new label
   and binds it to the incoming interface of this session/sender.  This
   is the same interface that the router uses to forward Resv messages
   to the previous hops.

   Several circumstance can lead to an unacceptable label.

      1. the node is a merge incapable ATM switch but the downstream
         node has assigned the same label to two senders

      2. The implicit null label was assigned, but the node is not
         capable of doing a penultimate pop for the associated L3PID

      3. The assigned label is outside the requested label range

   In any of these events the node send a ResvErr message with an error
   code of "routing problem" and an error value of "unacceptable label
   value".

4.1.2. Non-support of the Label Object

   Under normal circumstances, a node should never receive a LABEL
   object in a Resv message unless it had included a LABEL_REQUEST
   object in the corresponding Path message.  However, an RSVP router
   that does not recognize the LABEL object sends a ResvErr with the
   error code "Unknown object class" toward the receiver.  This causes
   the reservation to fail.



 */

public class Label extends RSVPObject{

	/**
	 * Label 4 bytes length
	 */
	
	protected long label;
	
	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Constructor to be used when a new Label Object wanted to be attached to a new message
	 * @param label The label number requested
	 */
	
	
	
	public Label(int label){
		
		classNum = 16;
		cType = 1;
		length = 4;
		this.label = label;
		
		log.debug("Label Object Created");

	}
	
	public Label() {
		super();
		classNum = 16;
		cType = 1;
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor to be used when a new ERO Object wanted to be decoded from a received
	 * message.
	 * @param bytes bytes
	 * @param offset offset
	 * @throws RSVPProtocolViolationException RSVP Protocol Violation Exception
	 */
	
	public Label(byte[] bytes, int offset) throws RSVPProtocolViolationException{
		super(bytes,offset);
		decode();
		log.debug("Label Object Created");
		
	}
	
	public void encode() throws RSVPProtocolViolationException{
		
		// Encontramos la longitud del objeto Label
		
		this.length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;	// Cabecera 
		this.length = this.length + 4;	// Longitud de la etiqueta
		this.bytes = new byte[length];
		encodeHeader();
		
		int currentIndex = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		bytes[currentIndex] = (byte)((label>>24) & 0xFF);
		bytes[currentIndex+1] = (byte)((label>>16) & 0xFF);
		bytes[currentIndex+2] = (byte)((label>>8) & 0xFF);
		bytes[currentIndex+3] = (byte)((label) & 0xFF);
		
	}
	
	public void decode() throws RSVPProtocolViolationException{
		int offset = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		label = ByteHandler.decode4bytesLong(bytes,offset);
		
	}
	
	// Getters & Setters

	public long getLabel() {
		return label;
	}

	public void setLabel(long label) {
		this.label = label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (label ^ (label >>> 32));
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
		Label other = (Label) obj;
		if (label != other.label)
			return false;
		return true;
	}


}
