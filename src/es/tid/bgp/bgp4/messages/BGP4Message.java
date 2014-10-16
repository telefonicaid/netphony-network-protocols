package es.tid.bgp.bgp4.messages;

import java.util.logging.Logger;

import es.tid.bgp.bgp4.BGP4Element;

/**
 *  
 * <h1>BGP Message Header Format (RFC 4271). <h1>
 * <p>From RFC 4271, Section 4.1</p>
 * <a href="https://tools.ietf.org/html/rfc4271">RFC 4271</a>.
 * 
 * From RFC 4271, Section 4.1
 * 
 * 4.1. Message Header Format
 * 

   Each message has a fixed-size header.  There may or may not be a data
   portion following the header, depending on the message type.  The
   layout of these fields is shown below:
 * <pre>
      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      +                                                               +
      |                                                               |
      +                                                               +
      |                           Marker                              |
      +                                                               +
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |          Length               |      Type     |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * </pre>
      Marker:

         This 16-octet field is included for compatibility; it MUST be
         set to all ones.

      Length:

         This 2-octet unsigned integer indicates the total length of the
         message, including the header in octets.  Thus, it allows one
         to locate the (Marker field of the) next message in the TCP
         stream.  The value of the Length field MUST always be at least
         19 and no greater than 4096, and MAY be further constrained,
         depending on the message type.  "padding" of extra data after
         the message is not allowed.  Therefore, the Length field MUST
         have the smallest value required, given the rest of the
         message.

      Type:

         This 1-octet unsigned integer indicates the type code of the
         message.  This document defines the following type codes:

                              1 - OPEN
                              2 - UPDATE
                              3 - NOTIFICATION
                              4 - KEEPALIVE


 * @author mcs
 *
 */
public abstract class BGP4Message  implements BGP4Element {
	
	/**
	 * The bytes of the message
	 */
	protected byte messageBytes[]; 
	
	/**
	 *  MessageType
	 */
	private int messageType;
	
	/**
	 * Message Length
	 */
	private int messageLength;
	
	/**
	 * Logger
	 */
	protected Logger log;
	

	public static int BGPHeaderLength = 19;
	public static int BGPMarkerLength = 16;
	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	/** Default constructor
	 * 
	 */
	public BGP4Message(){
		log=Logger.getLogger("BGP4Parser");
		
	}
	 
	/**
	 * Creates a PCEP message from a byte array. 
	 * Decodes the message header 
	 * @param bytes
	 * @throws PCEPProtocolViolationException
	 */
	public BGP4Message(byte []bytes){
		log=Logger.getLogger("BGP4Parser");

		messageLength=(bytes[16] & 0xFF)* 256 + (bytes[17]& 0xFF);
		if (bytes.length!=this.getLength()){
			log.warning("Bytes and length in BGP header do not match");			
		}
		this.messageBytes=new byte[messageLength];
		System.arraycopy(bytes, 0, messageBytes, 0, messageLength);
		messageType=messageBytes[18]&0xFF;		
	}
	

	/**
	 * Get the message Bytes
	 */
	public byte[] getBytes() {
		return messageBytes;
	}

/**
	Message-Type (8 bits):  The following message types are currently
      defined:

         Value    Meaning
           1        Open
           2        Update
           3        Notification
           4        Keepalive
    
*/
	public int getMessageType() {
		return messageType;	
	}
	public static int getMessageType(byte[] bytes){
		int mt;
		mt= bytes[18];//Type is in 18 byte
		return mt;
	}
	

	public int getLength() {
		return messageLength;
	}

	
	//public abstract void decode(byte[] bytes) throws PCEPProtocolViolationException;
	
	protected void encodeHeader() { 
		for (int i = 0;i<16;i++)
		messageBytes[i]=(byte)0xFF;
		messageBytes[16]=(byte)((messageLength>>8) & 0xFF);
		messageBytes[17]=(byte)(messageLength & 0xFF);
		messageBytes[18]=(byte)messageType;
	}

	public static int getBGPMarkerLength() {
		return BGPMarkerLength;
	}

	public static int getBGPHeaderLength() {
		return BGPHeaderLength;
	}

	
	
	
}
