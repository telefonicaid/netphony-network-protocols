package es.tid.rsvp.objects;

import java.net.Inet6Address;
import java.net.UnknownHostException;

import es.tid.protocol.commons.ByteHandler;

/*
*

RFC 2205                          RSVP                    September 1997

  A.1 SESSION Class

     SESSION Class = 1.

      o    IPv6/UDP SESSION object: Class = 1, C-Type = 2

           +-------------+-------------+-------------+-------------+
           |                                                       |
           +                                                       +
           |                                                       |
           +               IPv6 DestAddress (16 bytes)             +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+
           | Protocol Id |     Flags   |          DstPort          |
           +-------------+-------------+-------------+-------------+



     DestAddress

          The IP unicast or multicast destination address of the
          session.  This field must be non-zero.

     Protocol Id

          The IP Protocol Identifier for the data flow.  This field
          must be non-zero.

     Flags

          0x01 = E_Police flag

               The E_Police flag is used in Path messages to determine
               the effective "edge" of the network, to control traffic
               policing.  If the sender host is not itself capable of
               traffic policing, it will set this bit on in Path
               messages it sends.  The first node whose RSVP is capable
               of traffic policing will do so (if appropriate to the
               service) and turn the flag off.

     DstPort

          The UDP/TCP destination port for the session.  Zero may be
          used to indicate `none'.

          Other SESSION C-Types could be defined in the future to
          support other demultiplexing conventions in the transport-
          layer or application layer.


*/


public class SessionIPv6 extends Session{

	protected Inet6Address destAddress;
	protected int protocolId;
	protected int flags;
	protected int destPort;
	
	/**
	 * 
	 */
	
	public SessionIPv6(){
		
		
		classNum = 1;
		cType = 2;

		protocolId = 0;
		flags = 0;
		destPort = 0;
		
	}
	
	/**
	 * 
	 * @param destAddress destAddress
	 * @param protocolId protocolId
	 * @param flags flags
	 * @param destPort destPort
	 */
	
	public SessionIPv6(Inet6Address destAddress, int protocolId, int flags, int destPort){
		
		this.destAddress = destAddress;
		this.protocolId = protocolId;
		this.flags = flags;
		this.destPort = destPort;
		
	}
	
	public SessionIPv6(byte[] bytes, int offset){
		super(bytes, offset);
		this.decode();
	}
	

	/*
	 *     +-------------+-------------+-------------+-------------+
           |                                                       |
           +                                                       +
           |                                                       |
           +               IPv6 DestAddress (16 bytes)             +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+
           | Protocol Id |     Flags   |          DstPort          |
           +-------------+-------------+-------------+-------------+
           
	 */
	
	@Override
	public void encode() {
		length = 24;
		bytes = new byte[length];
		
		// TODO Auto-generated method stub
		encodeHeader();
		int offset = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		if (destAddress!=null)  {
			byte[] addr = destAddress.getAddress();
			System.arraycopy(getBytes(),4, addr, 0, addr.length);
		}else {
			bytes[offset]=0;
			bytes[offset+1]=0;
			bytes[offset+2]=0;
			bytes[offset+3]=0;
			bytes[offset+4]=0;
			bytes[offset+5]=0;
			bytes[offset+6]=0;
			bytes[offset+7]=0;
			bytes[offset+8]=0;
			bytes[offset+9]=0;
			bytes[offset+10]=0;
			bytes[offset+11]=0;
			bytes[offset+12]=0;
			bytes[offset+13]=0;
			bytes[offset+14]=0;
			bytes[offset+15]=0;
		}
		offset+=16;
		bytes[offset] = (byte) protocolId;
		bytes[offset+1] = (byte) flags;
		bytes[offset+2] = (byte)((destPort>>8) & 0xFF);
		bytes[offset+3] = (byte)(destPort & 0xFF);
				
	}

	
	public void decode() {
		
		byte[] receivedAddress = new byte[16];
		
		int offset= RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		
		System.arraycopy(bytes,offset,receivedAddress,0,16);
		try{
			destAddress = (Inet6Address) Inet6Address.getByAddress(receivedAddress);
		}catch(UnknownHostException e){
			// FIXME: Poner logs con respecto a excepcion
		}
		offset = offset + 16;
		protocolId = bytes[offset];
		flags = bytes[offset+1]&0xFF;
		destPort = ByteHandler.decode2bytesInteger(bytes,offset+2);
		
	}
	
	
	// Getters & Setters
	
	
	public Inet6Address getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(Inet6Address destAddress) {
		this.destAddress = destAddress;
	}

	public int getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(int protocolId) {
		this.protocolId = protocolId;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getDestPort() {
		return destPort;
	}

	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}



}
