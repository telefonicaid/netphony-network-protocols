package es.tid.rsvp.objects;

import java.net.Inet4Address;
import java.net.UnknownHostException;
/*
 *

RFC 2205                          RSVP                    September 1997

   A.1 SESSION Class

      SESSION Class = 1.

      o    IPv4/UDP SESSION object: Class = 1, C-Type = 1

           +-------------+-------------+-------------+-------------+
           |             IPv4 DestAddress (4 bytes)                |
           +-------------+-------------+-------------+-------------+
           | Protocol Id |    Flags    |          DstPort          |
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

public class SessionIPv4 extends Session{

	protected Inet4Address destAddress;
	protected int protocolId;
	protected int flags;
	protected int destPort;
	
	/**
	 * 
	 */
	
	public SessionIPv4(){
		
		
		classNum = 1;
		cType = 1;
		length = 12;
		bytes = new byte[length];
		try{
			destAddress = (Inet4Address) Inet4Address.getLocalHost();
		}catch(UnknownHostException e){
			
		}
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
	
	public SessionIPv4(Inet4Address destAddress, int protocolId, int flags, int destPort){
		
		classNum = 1;
		cType = 1;
		length = 12;
		bytes = new byte[length];
		this.destAddress = destAddress;
		this.protocolId = protocolId;
		this.flags = flags;
		this.destPort = destPort;
		
	}
/*	
    0             1              2             3
    +-------------+-------------+-------------+-------------+
    |       Length (bytes)      |  Class-Num  |   C-Type    |
    +-------------+-------------+-------------+-------------+
    |                                                       |
    //                  (Object contents)                   //
    |                                                       |
    +-------------+-------------+-------------+-------------+	
    
    */
	
	public void encodeHeader(){
		
		bytes[0] = (byte)((length>>8) & 0xFF);
		bytes[1] = (byte)(length & 0xFF);
		bytes[2] = (byte) classNum;
		bytes[3] = (byte) cType;

		
	}
	
	/*
	 *     +-------------+-------------+-------------+-------------+
           |             IPv4 DestAddress (4 bytes)                |
           +-------------+-------------+-------------+-------------+
           | Protocol Id |    Flags    |          DstPort          |
           +-------------+-------------+-------------+-------------+
	 */
	
	public void encode(){
		
		encodeHeader();
		
		byte[] addr = destAddress.getAddress();
		
		System.arraycopy(addr,0, getBytes(), 4, addr.length);
		
		bytes[8] = (byte) protocolId;
		bytes[9] = (byte) flags;
		bytes[10] = (byte)((destPort>>8) & 0xFF);
		bytes[11] = (byte)(destPort & 0xFF);
		

	}

	@Override
	public void decode(byte[] bytes, int offset){
		
		byte[] receivedAddress = new byte[4];
		System.arraycopy(bytes,offset+4,receivedAddress,0,4);
		try{
			destAddress = (Inet4Address) Inet4Address.getByAddress(receivedAddress);
		}catch(UnknownHostException e){
			// FIXME: Poner logs con respecto a excepcion
		}
		protocolId = bytes[offset+8];
		flags = bytes[offset+9];
		destPort = bytes[offset+10] | bytes[offset+11];
		
		
	}
	
	
	// Getters & Setters
	
	public Inet4Address getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(Inet4Address destAddress) {
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
