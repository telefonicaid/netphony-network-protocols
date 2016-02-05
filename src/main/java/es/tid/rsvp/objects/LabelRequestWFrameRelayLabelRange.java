package es.tid.rsvp.objects;

import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import org.slf4j.LoggerFactory;

/*

RFC 3209     RSVP-TE

4.2. Label Request Object

   The Label Request Class is 19.  Currently there are three possible
   C_Types.  Type 1 is a Label Request without label range.  Type 2 is a
   label request with an ATM label range.  Type 3 is a label request
   with a Frame Relay label range.  The LABEL_REQUEST object formats are
   shown below.

Label Request with Frame Relay Label Range

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




 */

public class LabelRequestWFrameRelayLabelRange extends LabelRequest{

	/**
      DLI

         DLCI Length Indicator.  The number of bits in the DLCI.  The
         following values are supported:

                   Len    DLCI bits

                    0        10
                    2        23
	 */
	
	private int DLI;
	
	/**
      Minimum DLCI

         This 23-bit field specifies the lower bound of a block of Data
         Link Connection Identifiers (DLCIs) that is supported on the
         originating switch.  The DLCI MUST be right justified in this
         field and unused bits MUST be set to 0.

	 */
	
	private int minDLCI;
	
	/**
	 * Reserved field set to 0
	 */
	
	private int res1;
	
	/**
	 * Reserved field set to 0
	 */
	
	private int res2;
	
	
	/**
      Maximum DLCI

         This 23-bit field specifies the upper bound of a block of Data
         Link Connection Identifiers (DLCIs) that is supported on the
         originating switch.  The DLCI MUST be right justified in this
         field and unused bits MUST be set to 0.
	 */
	
	private int maxDLCI;
	
	
	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Constructor to be used when a new Label Request With ATM Label Range Object 
	 * wanted to be attached to a new message
	 * @param DLI

         DLCI Length Indicator.  The number of bits in the DLCI.  The
         following values are supported:

                   Len    DLCI bits

                    0        10
                    2        23
	 * @param minDLCI

         This 23-bit field specifies the lower bound of a block of Data
         Link Connection Identifiers (DLCIs) that is supported on the
         originating switch.  The DLCI MUST be right justified in this
         field and unused bits MUST be set to 0.
         zero.
	 * @param maxDLCI

         This 23-bit field specifies the upper bound of a block of Data
         Link Connection Identifiers (DLCIs) that is supported on the
         originating switch.  The DLCI MUST be right justified in this
         field and unused bits MUST be set to 0.
	 * @param l3PID The identifier of the layer 3 protocol using this path. Standard Ethertype values are used.
	 */
	
	public LabelRequestWFrameRelayLabelRange(int DLI, int minDLCI, int maxDLCI, int l3PID){		
		
		classNum = 19;
		cType = 3;

		this.DLI = DLI;
		this.minDLCI = minDLCI;
		this.maxDLCI = maxDLCI;
		this.l3PID = l3PID;
		
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 12;
		
		log.debug("Label Request With Frame Relay Label Range Object Created");
		
		
	}
	
	/**
	 * Constructor to be used when a new Label Request With ATM Label Range Object wanted to be decoded from a received
	 * message.
	 * @param bytes
	 * @param offset
	 */
	
	public LabelRequestWFrameRelayLabelRange(byte[] bytes, int offset){
		
		this.decodeHeader(bytes,offset);
		this.bytes = new byte[this.getLength()];
		
		log.debug("Label Request With Frame Relay Label Range Object Created");
		
	}
	
	
	
	public void encode() throws RSVPProtocolViolationException{
		
		encodeHeader();
		
		
	}
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException{


		
	}
	
	// Getters & Setters

	public int getDLI() {
		return DLI;
	}

	public void setDLI(int dLI) {
		DLI = dLI;
	}

	public int getMinDLCI() {
		return minDLCI;
	}

	public void setMinDLCI(int minDLCI) {
		this.minDLCI = minDLCI;
	}

	public int getRes1() {
		return res1;
	}

	public void setRes1(int res1) {
		this.res1 = res1;
	}

	public int getRes2() {
		return res2;
	}

	public void setRes2(int res2) {
		this.res2 = res2;
	}

	public int getMaxDLCI() {
		return maxDLCI;
	}

	public void setMaxDLCI(int maxDLCI) {
		this.maxDLCI = maxDLCI;
	}


}
