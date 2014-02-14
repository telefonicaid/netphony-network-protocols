package tid.rsvp.objects;

import tid.rsvp.RSVPProtocolViolationException;

/*

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

4.2.2. Label Request with ATM Label Range

   Class = 19, C_Type = 2

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |           Reserved            |             L3PID             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |M| Res |    Minimum VPI        |      Minimum VCI              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |  Res  |    Maximum VPI        |      Maximum VCI              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Reserved (Res)

         This field is reserved.  It MUST be set to zero on transmission
         and MUST be ignored on receipt.

      L3PID

         an identifier of the layer 3 protocol using this path.
         Standard Ethertype values are used.

      M

         Setting this bit to one indicates that the node is capable of
         merging in the data plane

      Minimum VPI (12 bits)

         This 12 bit field specifies the lower bound of a block of
         Virtual Path Identifiers that is supported on the originating
         switch.  If the VPI is less than 12-bits it MUST be right
         justified in this field and preceding bits MUST be set to zero.

      Minimum VCI (16 bits)

         This 16 bit field specifies the lower bound of a block of
         Virtual Connection Identifiers that is supported on the
         originating switch.  If the VCI is less than 16-bits it MUST be
         right justified in this field and preceding bits MUST be set to
         zero.

      Maximum VPI (12 bits)

         This 12 bit field specifies the upper bound of a block of
         Virtual Path Identifiers that is supported on the originating
         switch.  If the VPI is less than 12-bits it MUST be right
         justified in this field and preceding bits MUST be set to zero.

      Maximum VCI (16 bits)

         This 16 bit field specifies the upper bound of a block of
         Virtual Connection Identifiers that is supported on the
         originating switch.  If the VCI is less than 16-bits it MUST be
         right justified in this field and preceding bits MUST be set to
         zero.

4.2.3. Label Request with Frame Relay Label Range

   Class = 19, C_Type = 3

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |           Reserved            |             L3PID             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | Reserved    |DLI|                     Minimum DLCI            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | Reserved        |                     Maximum DLCI            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Reserved

         This field is reserved.  It MUST be set to zero on transmission
         and ignored on receipt.

      L3PID

         an identifier of the layer 3 protocol using this path.
         Standard Ethertype values are used.

      DLI

         DLCI Length Indicator.  The number of bits in the DLCI.  The
         following values are supported:

                   Len    DLCI bits

                    0        10
                    2        23

      Minimum DLCI

         This 23-bit field specifies the lower bound of a block of Data
         Link Connection Identifiers (DLCIs) that is supported on the
         originating switch.  The DLCI MUST be right justified in this
         field and unused bits MUST be set to 0.

      Maximum DLCI

         This 23-bit field specifies the upper bound of a block of Data
         Link Connection Identifiers (DLCIs) that is supported on the
         originating switch.  The DLCI MUST be right justified in this
         field and unused bits MUST be set to 0.

4.2.4. Handling of LABEL_REQUEST

   To establish an LSP tunnel the sender creates a Path message with a
   LABEL_REQUEST object.  The LABEL_REQUEST object indicates that a
   label binding for this path is requested and provides an indication
   of the network layer protocol that is to be carried over this path.
   This permits non-IP network layer protocols to be sent down an LSP.
   This information can also be useful in actual label allocation,
   because some reserved labels are protocol specific, see [5].

   The LABEL_REQUEST SHOULD be stored in the Path State Block, so that
   Path refresh messages will also contain the LABEL_REQUEST object.
   When the Path message reaches the receiver, the presence of the
   LABEL_REQUEST object triggers the receiver to allocate a label and to
   place the label in the LABEL object for the corresponding Resv
   message.  If a label range was specified, the label MUST be allocated
   from that range.  A receiver that accepts a LABEL_REQUEST object MUST
   include a LABEL object in Resv messages pertaining to that Path
   message.  If a LABEL_REQUEST object was not present in the Path
   message, a node MUST NOT include a LABEL object in a Resv message for
   that Path message's session and PHOP.

   A node that sends a LABEL_REQUEST object MUST be ready to accept and
   correctly process a LABEL object in the corresponding Resv messages.

   A node that recognizes a LABEL_REQUEST object, but that is unable to
   support it (possibly because of a failure to allocate labels) SHOULD
   send a PathErr with the error code "Routing problem" and the error
   value "MPLS label allocation failure."  This includes the case where
   a label range has been specified and a label cannot be allocated from
   that range.

   A node which receives and forwards a Path message each with a
   LABEL_REQUEST object, MUST copy the L3PID from the received
   LABEL_REQUEST object to the forwarded LABEL_REQUEST object.

   If the receiver cannot support the protocol L3PID, it SHOULD send a
   PathErr with the error code "Routing problem" and the error value
   "Unsupported L3PID."  This causes the RSVP session to fail.

4.2.5. Non-support of the Label Request Object

   An RSVP router that does not recognize the LABEL_REQUEST object sends
   a PathErr with the error code "Unknown object class" toward the
   sender.  An RSVP router that recognizes the LABEL_REQUEST object but
   does not recognize the C_Type sends a PathErr with the error code
   "Unknown object C_Type" toward the sender.  This causes the path
   setup to fail.  The sender should notify management that a LSP cannot
   be established and possibly take action to continue the reservation
   without the LABEL_REQUEST.

   RSVP is designed to cope gracefully with non-RSVP routers anywhere
   between senders and receivers.  However, obviously, non-RSVP routers
   cannot convey labels via RSVP.  This means that if a router has a
   neighbor that is known to not be RSVP capable, the router MUST NOT
   advertise the LABEL_REQUEST object when sending messages that pass
   through the non-RSVP routers.  The router SHOULD send a PathErr back
   to the sender, with the error code "Routing problem" and the error
   value "MPLS being negotiated, but a non-RSVP capable router stands in
   the path."  This same message SHOULD be sent, if a router receives a
   LABEL_REQUEST object in a message from a non-RSVP capable router.
   See [1] for a description of how a downstream router can determine
   the presence of non-RSVP routers.




 */

public class LabelRequest extends RSVPObject{

	/**
	 * Common LabelRequest field;
	 */
	
	protected int reserved;
	
	/**
	 * Common L3PID field
	 */
	
	protected int l3PID;
	
	@Override
	public void encode() throws RSVPProtocolViolationException {
		
	}

	@Override
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public int getL3PID() {
		return l3PID;
	}

	public void setL3PID(int l3pid) {
		l3PID = l3pid;
	}



}
