package tid.rsvp.messages;

import tid.rsvp.*;


/*	RSVP Message. From RFC 2205
 * 	
 * 	RFC 2205:
 * 
 * 3. RSVP Functional Specification

   3.1 RSVP Message Formats

      An RSVP message consists of a common header, followed by a body
      consisting of a variable number of variable-length, typed
      "objects".  The following subsections define the formats of the
      common header, the standard object header, and each of the RSVP
      message types.

      For each RSVP message type, there is a set of rules for the
      permissible choice of object types.  These rules are specified
      using Backus-Naur Form (BNF) augmented with square brackets
      surrounding optional sub-sequences.  The BNF implies an order for
      the objects in a message.  However, in many (but not all) cases,
      object order makes no logical difference.  An implementation
      should create messages with the objects in the order shown here,
      but accept the objects in any permissible order.

      3.1.1 Common Header

                0             1              2             3
         +-------------+-------------+-------------+-------------+
         | Vers | Flags|  Msg Type   |       RSVP Checksum       |
         +-------------+-------------+-------------+-------------+
         |  Send_TTL   | (Reserved)  |        RSVP Length        |
         +-------------+-------------+-------------+-------------+



         The fields in the common header are as follows:

         Vers: 4 bits

              Protocol version number.  This is version 1.

         Flags: 4 bits

              0x01-0x08: Reserved

                   No flag bits are defined yet.

         Msg Type: 8 bits

              1 = Path

              2 = Resv

              3 = PathErr

              4 = ResvErr

              5 = PathTear

              6 = ResvTear

              7 = ResvConf

         RSVP Checksum: 16 bits

              The one's complement of the one's complement sum of the
              message, with the checksum field replaced by zero for the
              purpose of computing the checksum.  An all-zero value
              means that no checksum was transmitted.

         Send_TTL: 8 bits

              The IP TTL value with which the message was sent.  See
              Section 3.8.

         RSVP Length: 16 bits

              The total length of this RSVP message in bytes, including
              the common header and the variable-length objects that
              follow.

 */

public abstract class RSVPMessage implements RSVPElement{
	
	
//FIXME: Poner Campos de la cabecera
	protected int vers;
	protected int flags;
	protected int msgType;
	protected int rsvpChecksum;
	protected int sendTTL;
	protected int reserved;
	protected int length;
		
	protected byte bytes[];//The bytes of the message 
	
	/*
	 *   RSVP Common Header

                0             1              2             3
         +-------------+-------------+-------------+-------------+
         | Vers | Flags|  Msg Type   |       RSVP Checksum       |
         +-------------+-------------+-------------+-------------+
         |  Send_TTL   | (Reserved)  |        RSVP Length        |
         +-------------+-------------+-------------+-------------+
         
	 */
	
	public RSVPMessage(){
		//FIXME: Poner valores por defecto
		vers = 0x01;
		flags = 0x00;
		msgType = RSVPMessageTypes.MESSAGE_PATH;
		rsvpChecksum = 0x00;
		sendTTL = 0x00;
		reserved = 0x00;
		length = 0x00;

	}
	

	public RSVPMessage(byte[] bytes){
		//
		this.bytes=bytes;
		//FIXME: DECODIFICAR CABECERA
		//FIXME: SACAR Y CREAR OBJETOS
	}

	//FIXME: Crear metodo para codificar cabecera
	
	/**
	 * 
	 */
	
	public abstract void encodeHeader();
	

	/**
	 * 
	 * @throws RSVPProtocolViolationException
	 */
	
	public abstract void encode() throws RSVPProtocolViolationException;
	
	/**
	 *
	 *   RSVP Common Header
	 *   
     *           0             1              2             3
     *    +-------------+-------------+-------------+-------------+
     *    | Vers | Flags|  Msg Type   |       RSVP Checksum       |
     *    +-------------+-------------+-------------+-------------+
     *    |  Send_TTL   | (Reserved)  |        RSVP Length        |
     *    +-------------+-------------+-------------+-------------+
     *    
	 *
	 * @throws RSVPProtocolViolationException
	 */
	
	public void decodeHeader() throws RSVPProtocolViolationException{
		
		vers = (bytes[0] >> 4) & 0x0F; 
		flags = bytes[0] & 0x0F;
		msgType = bytes[1];
		rsvpChecksum = bytes[2] | bytes[3];
		sendTTL = bytes[4];
		reserved = bytes[5];
		length = (((int)((bytes[6]&0xFF)<<8)& 0xFF00)|  ((int)bytes[7] & 0xFF));
	}
	
	
	/**
	 * 
	 * @throws RSVPProtocolViolationException
	 */
	
	public abstract void decode() throws RSVPProtocolViolationException;
	
	
	public void calculateChecksum(){
		
		byte var[] = new byte[this.length];
		
		for(int i = 0; i < this.length; i++){
			int m = ~ (int)(this.bytes[i]);
			m = m & 0xFF;
			var[i] = (byte)(m);
		}
		
		int sum = 0;
		
		for(int j = 0; j < this.length; j++){
			sum = sum + (int) var[j];
		}
		
		this.rsvpChecksum = (~sum) & 0xFF;
		
		bytes[2]= (byte)((rsvpChecksum>>8) & 0xFF);
		bytes[3]= (byte)(rsvpChecksum & 0xFF);
		
	}
	
	// GETTERS & SETTERS
	
	/**
	 * 
	 * @return vers
	 */	
	public int getVers() {
		return vers;
	}

	public void setVers(int vers) {
		this.vers = vers;
	}

	public int getFlags() {
		return flags;
	}


	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}


	public int getRsvpChecksum() {
		return rsvpChecksum;
	}


	public void setRsvpChecksum(int rsvpChecksum) {
		this.rsvpChecksum = rsvpChecksum;
	}


	public int getSendTTL() {
		return sendTTL;
	}


	public void setSendTTL(int sendTTL) {
		this.sendTTL = sendTTL;
	}


	public int getReserved() {
		return reserved;
	}


	public void setReserved(int reserved) {
		this.reserved = reserved;
	}


	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}


	public byte[] getBytes() {
		return bytes;
	}


	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public static int getMsgType(byte[] bytes) {
		return bytes[1];
	}

	public static int getMsgLength(byte[] bytes) {
		return //(int)(((bytes[6]&0xFF)<<8)& 0xFF00) | (bytes[7] & 0x00FF));
		(((int)((bytes[6]&0xFF)<<8)& 0xFF00)|  ((int)bytes[7] & 0xFF));
	}
		
}
