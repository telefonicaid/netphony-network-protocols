package tid.pce.pcep.messages;

/**
 * PCEP Message as defined in RFC 5440
 * <p> Derive all PCEP Messages from this base class <p>
 * @author Oscar Gonzalez de Dios
**/


//import java.util.logging.Logger;

import java.util.logging.Logger;

import tid.pce.pcep.PCEPElement;
import tid.pce.pcep.PCEPProtocolViolationException;

/** Base class for PCEP Messages.
 * 
 * Cabecera comun de los mensajes PCEP
0                   1                   2                   3
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Ver |  Flags  |  Message-Type |       Message-Length          |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
*/
public abstract class PCEPMessage implements PCEPElement {

	protected byte messageBytes[];//The bytes of the message 
	
	private int messageType; //MessageType
	private int Ver;//PCEP Version (by default=0x01
	private int Flags;//By default to 0x00
	private int messageLength;
		
	protected Logger log;
	


	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	/** Default constructor
	 * 
	 */
	public PCEPMessage(){
		Ver=0x01;
		Flags=0x00;
		messageLength=0;
	}
	 
	/**
	 * Creates a PCEP message from a byte array. 
	 * Decodes the message header 
	 * @param bytes
	 * @throws PCEPProtocolViolationException
	 */
	public PCEPMessage(byte []bytes) throws PCEPProtocolViolationException{
		log=Logger.getLogger("PCEPParser");
		System.out.println("Repooooooting");
		messageLength=(bytes[2] & 0xFF)* 256 + (bytes[3]& 0xFF);
		System.out.println("Repooooooting 2");
		if (bytes.length!=this.getLength()){
			log.warning("Bytes and length in header do not match");
			throw new PCEPProtocolViolationException();
		}
		System.out.println("Repooooooting 3");
		this.messageBytes=new byte[messageLength];
		System.arraycopy(bytes, 0, messageBytes, 0, messageLength);
		messageType=messageBytes[1]&0xFF;
		Ver= (messageBytes[0] & 0xE0)>>>5;
		System.out.println("Repooooooting finishing");
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
           2        Keepalive
           3        Path Computation Request
           4        Path Computation Reply
           5        Notification
           6        Error
           7        Close
           8 		PCMonReq
           9		PCMonRep
          TBD  		PCUpdate
          TBD		PCReport
*/
	public int getMessageType() {
		return messageType;	
	}
	public static int getMessageType(byte[] bytes){
		int mt;
		mt= bytes[1];
		return mt;
	}
	
	public int getVer() {
		return Ver;	
	}

	public int getLength() {
		return messageLength;
	}

	
	//public abstract void decode(byte[] bytes) throws PCEPProtocolViolationException;
	
	protected void encodeHeader() { 
		messageBytes[0]= (byte)(((Ver<<5) &0xE0) | (Flags & 0x1F));
		messageBytes[1]=(byte)messageType;
		messageBytes[2]=(byte)((messageLength>>8) & 0xFF);
		messageBytes[3]=(byte)(messageLength & 0xFF);
	}	
	
//	protected void decodeHeader(){
//		messageType=messageBytes[1];
//		Ver= (messageBytes[0] & 0x224)/32;
//		//Flags=
//		messageLength=(messageBytes[2] & 0xFF)* 256 + (messageBytes[3]& 0xFF);	
//	}
	
}