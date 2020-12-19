package es.tid.rsvp.objects;

import es.tid.rsvp.RSVPProtocolViolationException;

/*

RFC 2205                          RSVP                    September 1997

   A.7 STYLE Class

      STYLE class = 8.

      o    STYLE object: Class = 8, C-Type = 1

           +-------------+-------------+-------------+-------------+
           |   Flags     |              Option Vector              |
           +-------------+-------------+-------------+-------------+

	  Flags: 8 bits

           (None assigned yet)

      Option Vector: 24 bits

           A set of bit fields giving values for the reservation
           options.  If new options are added in the future,
           corresponding fields in the option vector will be assigned
           from the least-significant end.  If a node does not recognize
           a style ID, it may interpret as much of the option vector as
           it can, ignoring new fields that may have been defined.

           The option vector bits are assigned (from the left) as
           follows:

           19 bits: Reserved

           2 bits: Sharing control

                00b: Reserved

                01b: Distinct reservations

                10b: Shared reservations

                11b: Reserved

           3 bits: Sender selection control

                000b: Reserved

                001b: Wildcard

                010b: Explicit

                011b - 111b: Reserved

      The low order bits of the option vector are determined by the
      style, as follows:

              WF 10001b
              FF 01010b
              SE 10010b
 */

public class Style extends RSVPObject{

	protected int flags;
	protected int optionVector;
	
	public Style(){
		
		classNum = 8;
		cType = 1;
		flags = 0;
		optionVector = 0;
	}
	
public Style(byte[] bytes, int offset) throws RSVPProtocolViolationException{
	super(bytes,offset);
	decode( );
}
	
	public Style(int flags, int optionVector){
		classNum = 8;  
		cType = 1;
		this.flags = flags;
		this.optionVector = optionVector;
	}
	
	/*
	 +-------------+-------------+-------------+-------------+
     |   Flags     |              Option Vector              |
     +-------------+-------------+-------------+-------------+

	 */
	
	@Override
	public void encode() {
		length = 8;
		bytes = new byte[length];
		encodeHeader();
		bytes[4] = (byte) (flags&0XFF);
		bytes[5] = (byte)((optionVector >>16) & 0xFF);
		bytes[6] = (byte)((optionVector >>8) & 0xFF);
		bytes[7] = (byte)(optionVector & 0xFF);
	}

	public void decode() {
		int offset=0;
		optionVector = 0;  
		flags=bytes[4]&0xFF;
		for (int k = 5; k < 8; k++) {
			optionVector = (optionVector << 8) | (bytes[offset+k] & 0xff);
		}
	}

	// Getters & Setters
	
	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getOptionVector() {
		return optionVector;
	}

	public void setOptionVector(int optionVector) {
		this.optionVector = optionVector;
	}
}
