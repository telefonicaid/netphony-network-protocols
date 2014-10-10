package es.tid.rsvp.objects;

import java.util.logging.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;

/*

RFC 3209     RSVP-TE

RFC 3209     RSVP-TE

4.2. Label Request Object

   The Label Request Class is 19.  Currently there are three possible
   C_Types.  Type 1 is a Label Request without label range.  Type 2 is a
   label request with an ATM label range.  Type 3 is a label request
   with a Frame Relay label range.  The LABEL_REQUEST object formats are
   shown below.

Label Request with ATM Label Range

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



 */

public class LabelRequestWATMLabelRange extends LabelRequest{

	/**
	 * M
	 * 
	 * Setting this bit to one indicates that the node is capable of
       merging in the data plane
	 */
	
	private int m;
	
	/**
	 * Minimum VPI (12 bits)
	 * 
	 * This 12 bit field specifies the lower bound of a block of
       Virtual Path Identifiers that is supported on the originating
       switch.  If the VPI is less than 12-bits it MUST be right
       justified in this field and preceding bits MUST be set to zero.
	 */
	
	private int minVPI;
	
	/**
	 * Minimum VCI (16 bits)

       This 16 bit field specifies the lower bound of a block of
       Virtual Connection Identifiers that is supported on the
       originating switch.  If the VCI is less than 16-bits it MUST be
       right justified in this field and preceding bits MUST be set to
       zero.

	 */
	
	private int minVCI;
	
	/**
	 * Reserved field set to 0
	 */
	
	private int res1;
	
	/**
	 * Reserved field set to 0
	 */
	
	private int res2;
	
	/**
	 * Maximum VPI (12 bits)

         This 12 bit field specifies the upper bound of a block of
         Virtual Path Identifiers that is supported on the originating
         switch.  If the VPI is less than 12-bits it MUST be right
         justified in this field and preceding bits MUST be set to zero.
	 */
	
	private int maxVPI;
	
	/**
	 * Maximum VCI (16 bits)

         This 16 bit field specifies the upper bound of a block of
         Virtual Connection Identifiers that is supported on the
         originating switch.  If the VCI is less than 16-bits it MUST be
         right justified in this field and preceding bits MUST be set to
         zero.
	 */
	
	private int maxVCI;
	
	
	/**
	 * Log
	 */
		
	private Logger log;
	
	/**
	 * Constructor to be used when a new Label Request With ATM Label Range Object 
	 * wanted to be attached to a new message
	 * @param m	Setting this bit to one indicates that the node is capable of
         merging in the data plane.
	 * @param minVPI	This 12 bit field specifies the lower bound of a block of
         Virtual Path Identifiers that is supported on the originating
         switch.  If the VPI is less than 12-bits it MUST be right
         justified in this field and preceding bits MUST be set to zero.
	 * @param minVCI	This 16 bit field specifies the lower bound of a block of
         Virtual Connection Identifiers that is supported on the
         originating switch.  If the VCI is less than 16-bits it MUST be
         right justified in this field and preceding bits MUST be set to
         zero.
	 * @param maxVPI	This 12 bit field specifies the upper bound of a block of
         Virtual Path Identifiers that is supported on the originating
         switch.  If the VPI is less than 12-bits it MUST be right
         justified in this field and preceding bits MUST be set to zero.
	 * @param maxVCI	This 16 bit field specifies the upper bound of a block of
         Virtual Connection Identifiers that is supported on the
         originating switch.  If the VCI is less than 16-bits it MUST be
         right justified in this field and preceding bits MUST be set to
         zero.
	 * @param l3PID The identifier of the layer 3 protocol using this path. Standard Ethertype values are used.
	 */
	
	public LabelRequestWATMLabelRange(int m, int minVPI, int minVCI, int maxVPI, int maxVCI, int l3PID){
		
		classNum = 19;
		cType = 2;

		this.m = m;
		this.minVPI = minVPI;
		this.minVCI = minVCI;
		this.maxVPI = maxVPI;
		this.maxVCI = maxVCI;
		this.l3PID = l3PID;
		
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 12;
		
		log = Logger.getLogger("ROADM");

		log.finest("Label Request With ATM Label Range Object Created");
		
		
	}
	
	/**
	 * Constructor to be used when a new Label Request With ATM Label Range Object wanted to be decoded from a received
	 * message.
	 * @param bytes
	 * @param offset
	 */
	
	public LabelRequestWATMLabelRange(byte[] bytes, int offset){
		
		this.decodeHeader(bytes,offset);
		this.bytes = new byte[this.getLength()];
		
		log = Logger.getLogger("ROADM");

		log.finest("Label Request With ATM Label Range Object Created");
		
	}
	
	
	
	public void encode() throws RSVPProtocolViolationException{
		
		encodeHeader();
		int currentIndex = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		bytes[currentIndex] = (byte) ((reserved >> 8) & 0xFF);
		bytes[currentIndex+1] = (byte) ((reserved) & 0xFF);
		bytes[currentIndex+2] = (byte)((l3PID >> 8) & 0xFF);
		bytes[currentIndex+3] = (byte)((l3PID) & 0xFF);
		currentIndex = currentIndex + 4;
		bytes[currentIndex] = (byte)(((m << 7) & 0x80) | (res1 << 4) | (minVPI << 8) & 0x0F);
		bytes[currentIndex+1] = (byte) ((minVPI) & 0xFF);
		bytes[currentIndex+2] = (byte)((minVCI >> 8) & 0xFF);
		bytes[currentIndex+3] = (byte)((minVCI) & 0xFF);
		currentIndex = currentIndex + 4;
		bytes[currentIndex] = (byte)((res2 << 4) | (maxVPI << 8) & 0x0F);
		bytes[currentIndex+1] = (byte) ((maxVPI) & 0xFF);
		bytes[currentIndex+2] = (byte)((maxVCI >> 8) & 0xFF);
		bytes[currentIndex+3] = (byte)((maxVCI) & 0xFF);
		
		
	}
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException{


		
	}
	
	// Getters & Setters

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}


}
