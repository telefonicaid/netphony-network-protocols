package es.tid.rsvp.objects;

import java.net.Inet6Address;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.RSVPProtocolViolationException;



/*
 *

RFC 3209  RSVP-TE

 4.6.1.1. LSP_TUNNEL_IPv6 Session Object

   Class = SESSION, LSP_TUNNEL_IPv6 C_Type = 8

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   +                                                               +
   |                   IPv6 tunnel end point address               |
   +                                                               +
   |                            (16 bytes)                         |
   +                                                               +
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |  MUST be zero                 |      Tunnel ID                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   +                                                               +
   |                       Extended Tunnel ID                      |
   +                                                               +
   |                            (16 bytes)                         |
   +                                                               +
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      IPv6 tunnel end point address

         IPv6 address of the egress node for the tunnel.

      Tunnel ID

         A 16-bit identifier used in the SESSION that remains constant
         over the life of the tunnel.

      Extended Tunnel ID

         A 16-byte identifier used in the SESSION that remains constant
         over the life of the tunnel.  Normally set to all zeros.
         Ingress nodes that wish to narrow the scope of a SESSION to the
         ingress-egress pair may place their IPv6 address here as a
         globally unique identifier.



 */





public class SessionLSPTunnelIPv6 extends Session{

	/**
	 * IPv6 address of the egress node for the tunnel.
	 */
	
	protected Inet6Address egressNodeAddress;
	
	/**
	 *   A 16-bit identifier used in the SESSION that remains constant
         over the life of the tunnel.
	 */
	
	protected int tunnelId;
	
	/**
	 *   A 16-byte identifier used in the SESSION that remains constant
         over the life of the tunnel.  Normally set to all zeros.
         Ingress nodes that wish to narrow the scope of a SESSION to the
         ingress-egress pair may place their IPv6 address here as a
         globally unique identifier.
	 */
	
	protected Inet6Address extendedTunnelId;
	
	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Constructor to be used when a new Session LSP Tunnel IPv6 Object wanted to be
	 * attached to a new message
	 */	
	
	public SessionLSPTunnelIPv6(){
		
		classNum = 1;
		cType = 8;
		

		log.debug("Session LSP Tunnel IPv6 Object Created");
		
	}
	
	/**
	 * Constructor to be used when a new Session LSP Tunnel IPv6 Object wanted to be
	 * attached to a new message
	 * @param egressNodeAddress egressNodeAddress
	 * @param extendedTunnelId extendedTunnelId
	 * @param tunnelId tunnelID
	 */	
	
	public SessionLSPTunnelIPv6(Inet6Address egressNodeAddress, int tunnelId, Inet6Address extendedTunnelId){
		
		classNum = 1;
		cType = 8;
		

		this.egressNodeAddress = egressNodeAddress;
		this.tunnelId = tunnelId;
		this.extendedTunnelId = extendedTunnelId;
		
		log.debug("Session LSP Tunnel IPv6 Object Created");
		
	}
	
	/**
	 * Constructor to be used when a new Session LSP Tunnel IPv6 Object wanted to be decoded from a received
	 * message.
	 * @param bytes bytes
	 * @param offset offset
	 * @throws RSVPProtocolViolationException RSVP Protocol Violation Exception
	 */
	
	public SessionLSPTunnelIPv6(byte[] bytes, int offset) throws RSVPProtocolViolationException{
		
		super(bytes, offset);
		decode();
		
		log.debug("Session LSP Tunnel IPv6 Object Created");
	}

	/**
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   +                                                               +
   |                   IPv6 tunnel end point address               |
   +                                                               +
   |                            (16 bytes)                         |
   +                                                               +
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |  MUST be zero                 |      Tunnel ID                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   +                                                               +
   |                       Extended Tunnel ID                      |
   +                                                               +
   |                            (16 bytes)                         |
   +                                                               +
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	 */
	
	public void encode(){
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 36;
		bytes = new byte[length];
		encodeHeader();
		
		byte[] addr = egressNodeAddress.getAddress();
		
		int currentIndex =  RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		
		System.arraycopy(addr,0, getBytes(), currentIndex, addr.length);
		currentIndex = currentIndex + addr.length;
		
		bytes[currentIndex] = (byte) 0;
		bytes[currentIndex+1] = (byte) 0;
		bytes[currentIndex+2] = (byte)((tunnelId>>8) & 0xFF);
		bytes[currentIndex+3] = (byte)(tunnelId & 0xFF);
		currentIndex = currentIndex + 4;
		addr = extendedTunnelId.getAddress();
		System.arraycopy(addr,0, getBytes(), currentIndex, addr.length);
	}


	public void decode() throws RSVPProtocolViolationException{
		try{
		int offset=0;
		byte[] receivedAddress = new byte[16];
		System.arraycopy(bytes,offset+4,receivedAddress,0,16);
		
			egressNodeAddress = (Inet6Address) Inet6Address.getByAddress(receivedAddress);
		
		offset = offset + 20;
		tunnelId = ByteHandler.decode2bytesInteger(bytes,offset+2);
		offset = offset + 4;
		System.arraycopy(bytes,offset,receivedAddress,0,16);
		extendedTunnelId = (Inet6Address) Inet6Address.getByAddress(receivedAddress);
		}catch(Exception e){
			throw new RSVPProtocolViolationException();
		}
		

	}

	public Inet6Address getEgressNodeAddress() {
		return egressNodeAddress;
	}

	public void setEgressNodeAddress(Inet6Address egressNodeAddress) {
		this.egressNodeAddress = egressNodeAddress;
	}

	public int getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(int tunnelId) {
		this.tunnelId = tunnelId;
	}

	public Inet6Address getExtendedTunnelId() {
		return extendedTunnelId;
	}

	public void setExtendedTunnelId(Inet6Address extendedTunnelId) {
		this.extendedTunnelId = extendedTunnelId;
	}






	
	
	
}
