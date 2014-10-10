package es.tid.rsvp.objects;

import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.util.logging.Logger;



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
	
	protected int extendedTunnelId;
	
	/**
	 * Log
	 */
		
	private Logger log;
	
	/**
	 * Constructor to be used when a new Session LSP Tunnel IPv6 Object wanted to be
	 * attached to a new message
	 */	
	
	public SessionLSPTunnelIPv6(){
		
		classNum = 1;
		cType = 8;
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 36;
		bytes = new byte[length];

		log = Logger.getLogger("ROADM");

		log.finest("Session LSP Tunnel IPv6 Object Created");
		
	}
	
	/**
	 * Constructor to be used when a new Session LSP Tunnel IPv6 Object wanted to be
	 * attached to a new message
	 * @param egressNodeAddress
	 * @param extendedTunnelId
	 */	
	
	public SessionLSPTunnelIPv6(Inet6Address egressNodeAddress, int tunnelId, int extendedTunnelId){
		
		classNum = 1;
		cType = 8;
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 36;
		bytes = new byte[length];

		this.egressNodeAddress = egressNodeAddress;
		this.tunnelId = tunnelId;
		this.extendedTunnelId = extendedTunnelId;
		
		log = Logger.getLogger("ROADM");

		log.finest("Session LSP Tunnel IPv6 Object Created");
		
	}
	
	/**
	 * Constructor to be used when a new Session LSP Tunnel IPv6 Object wanted to be decoded from a received
	 * message.
	 * @param bytes
	 * @param offset
	 */
	
	public SessionLSPTunnelIPv6(byte[] bytes, int offset){
		
		this.decodeHeader(bytes,offset);
		this.bytes = new byte[this.getLength()];
		
		log = Logger.getLogger("ROADM");

		log.finest("Session LSP Tunnel IPv6 Object Created");
		
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
		
		encodeHeader();
		
		byte[] addr = egressNodeAddress.getAddress();
		
		int currentIndex =  RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		
		System.arraycopy(addr,0, getBytes(), currentIndex, addr.length);
		currentIndex = currentIndex + addr.length;
		
		bytes[currentIndex] = (byte) 0;
		bytes[currentIndex+1] = (byte) 0;
		bytes[currentIndex+2] = (byte)((tunnelId>>8) & 0xFF);
		bytes[currentIndex+3] = (byte)(tunnelId & 0xFF);
		bytes[currentIndex+4] = (byte)((extendedTunnelId>>24) & 0xFF);
		bytes[currentIndex+5] = (byte)((extendedTunnelId>>16) & 0xFF);
		bytes[currentIndex+6] = (byte)((extendedTunnelId>>8) & 0xFF);
		bytes[currentIndex+7] = (byte)(extendedTunnelId & 0xFF);

	}


	public void decode(byte[] bytes, int offset){
		
		byte[] receivedAddress = new byte[16];
		System.arraycopy(bytes,offset+4,receivedAddress,0,16);
		try{
			egressNodeAddress = (Inet6Address) Inet6Address.getByAddress(receivedAddress);
		}catch(UnknownHostException e){
			// FIXME: Poner logs con respecto a excepcion
		}
		offset = offset + 16;
		tunnelId = (int)(bytes[offset+2] | bytes[offset+3]);
		offset = offset + 4;
		extendedTunnelId = (int)(bytes[offset] | bytes[offset+1] | bytes[offset+2] | bytes[offset+3] | bytes[offset+4] | bytes[offset+5] | bytes[offset+6] | bytes[offset+7] | bytes[offset+8] | bytes[offset+9] | bytes[offset+10] | bytes[offset+11] | bytes[offset+12] | bytes[offset+13] | bytes[offset+14] | bytes[offset+15]);

		

	}






	
	
	
}
