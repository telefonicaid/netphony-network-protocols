package es.tid.rsvp.objects;

import java.net.Inet6Address;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 

  <p>RFC 3209  RSVP-TE	LSP_TUNNEL_IPv6 Sender Template Object</p>

  <p> Class = SENDER_TEMPLATE, LSP_TUNNEL_IPv6 C_Type = 8

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   +                                                               +
   |                   IPv6 tunnel sender address                  |
   +                                                               +
   |                            (16 bytes)                         |
   +                                                               +
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |  MUST be zero                 |            LSP ID             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      IPv6 tunnel sender address

         IPv6 address for a sender node

      LSP ID

         A 16-bit identifier used in the SENDER_TEMPLATE and the
         FILTER_SPEC that can be changed to allow a sender to share
         resources with itself.</p>


@author Fernando Munoz del Nuevo	fmn@tid.es

 */

public class SenderTemplateLSPTunnelIPv6 extends SenderTemplate{

	/**
	 * <p>IPv6 address of the sender node</p>
	 */
	
	private Inet6Address senderNodeAddress;
	
	
	/**
	 *   <p> A 16-bit identifier used in the SENDER_TEMPLATE and the
         FILTER_SPEC that can be changed to allow a sender to share
         resources with itself. </p>

	 */
	
	private int LSPId;
	
	/**
	 * <p>Log
	 */
	private static final Logger log = LoggerFactory.getLogger("ROADM");
	/**
	 * <p>Parameters constructor to encode this kind of object. </p>
	 * @param senderNodeAddress The sender IPv6 Sender node address
	 * @param LSPId	The LSP identifier
	 */
	
	public SenderTemplateLSPTunnelIPv6(Inet6Address senderNodeAddress, int LSPId){
		
		classNum = 11;
		cType = 8;
		
		this.senderNodeAddress = senderNodeAddress;
		this.LSPId = LSPId;
		
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 20;
		bytes = new byte[length];
		
		log.debug("Sender Template LSP Tunnel IPv6 Object Created");
			
	}
	
	/**
	 * <p>Constructor to be used when a new Sender Template LSP Tunnel IPv6 Object wanted 
	 * to be decoded from a received message.</p>
	 * @param bytes bytes
	 * @param offset offset
	 */
	
	public SenderTemplateLSPTunnelIPv6(byte[] bytes, int offset){
		
		this.decodeHeader(bytes,offset);
		this.bytes = new byte[this.getLength()];
		
		log.debug("Sender Template LSP Tunnel IPv6 Object Created");
		
	}

	
	/**
	<p>
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   +                                                               +
   |                   IPv6 tunnel sender address                  |
   +                                                               +
   |                            (16 bytes)                         |
   +                                                               +
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |  MUST be zero                 |            LSP ID             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   </p>
	 */

	public void encode() {
		
		encodeHeader();
		
		byte[] addr = senderNodeAddress.getAddress();
		
		int currentIndex =  RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;

		System.arraycopy(addr,0, getBytes(), currentIndex, addr.length);
		currentIndex = currentIndex + addr.length;
		
		bytes[currentIndex] = (byte) 0;
		bytes[currentIndex+1] = (byte) 0;
		bytes[currentIndex+2] = (byte)((LSPId>>8) & 0xFF);
		bytes[currentIndex+3] = (byte)(LSPId & 0xFF);
		log.debug("Sender Template LSP Tunnel IPv6 Object Encoded");
		
	}

	/**
	<p>
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   +                                                               +
   |                   IPv6 tunnel sender address                  |
   +                                                               +
   |                            (16 bytes)                         |
   +                                                               +
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |  MUST be zero                 |            LSP ID             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   </p>
	 */
	
	public void decode(byte[] bytes, int offset) {

		byte[] receivedAddress = new byte[16];
		
		offset = offset + RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		
		System.arraycopy(bytes,offset,receivedAddress,0,16);
		try{
			senderNodeAddress = (Inet6Address) Inet6Address.getByAddress(receivedAddress);
		}catch(UnknownHostException e){
			log.error("Unknown Host received on Sender Template LSP IPv6 Object");
		}
		offset = offset + receivedAddress.length;
		LSPId = (int)(bytes[offset+2] | bytes[offset+3]);
		log.debug("Sender Template LSP Tunnel IPv6 Object Decoded");
		
	}
	
	// Getters & Setters

	public Inet6Address getSenderNodeAddress() {
		return senderNodeAddress;
	}

	public void setSenderNodeAddress(Inet6Address senderNodeAddress) {
		this.senderNodeAddress = senderNodeAddress;
	}

	public int getLSPId() {
		return LSPId;
	}

	public void setLSPId(int lSPId) {
		LSPId = lSPId;
	}

}
