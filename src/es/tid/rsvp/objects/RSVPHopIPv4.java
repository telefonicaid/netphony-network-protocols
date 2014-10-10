package es.tid.rsvp.objects;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/*
 *

RFC 2205                          RSVP                    September 1997

   A.2 RSVP_HOP Class

      RSVP_HOP class = 3.

      o    IPv4 RSVP_HOP object: Class = 3, C-Type = 1

           +-------------+-------------+-------------+-------------+
           |             IPv4 Next/Previous Hop Address            |
           +-------------+-------------+-------------+-------------+
           |                 Logical Interface Handle              |
           +-------------+-------------+-------------+-------------+

      This object carries the IP address of the interface through which
      the last RSVP-knowledgeable hop forwarded this message.  The
      Logical Interface Handle (LIH) is used to distinguish logical
      outgoing interfaces, as discussed in Sections 3.3 and 3.9.  A node
      receiving an LIH in a Path message saves its value and returns it
      in the HOP objects of subsequent Resv messages sent to the node
      that originated the LIH.  The LIH should be identically zero if
      there is no logical interface handle.

 */

public class RSVPHopIPv4 extends RSVPHop{

	protected Inet4Address next_previousHopAddress;
	protected double logicalInterfaceHandle;
	
	public RSVPHopIPv4(){
		
		classNum = 3;
		cType = 1;
		length = 12;
		bytes = new byte[length];
		try{
			next_previousHopAddress = (Inet4Address) Inet4Address.getLocalHost();
		}catch(UnknownHostException e){
			
		}
		logicalInterfaceHandle = 0;
		
	}
	
	public RSVPHopIPv4(Inet4Address next_previousHopAddress, int logicalInterfaceHandle){
		
		classNum = 3;
		cType = 1;
		length = 12;
		bytes = new byte[length];
		this.next_previousHopAddress = next_previousHopAddress;
		this.logicalInterfaceHandle = logicalInterfaceHandle;
		
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
	
	@Override
	public void encodeHeader() {
		// TODO Auto-generated method stub
		bytes[0] = (byte)((length>>8) & 0xFF);
		bytes[1] = (byte)(length & 0xFF);
		bytes[2] = (byte) classNum;
		bytes[3] = (byte) cType;
	}

	/*
	 *       o    IPv4 RSVP_HOP object: Class = 3, C-Type = 1

           +-------------+-------------+-------------+-------------+
           |             IPv4 Next/Previous Hop Address            |
           +-------------+-------------+-------------+-------------+
           |                 Logical Interface Handle              |
           +-------------+-------------+-------------+-------------+
	 */
	
	@Override
	public void encode() {
		// TODO Auto-generated method stub
		encodeHeader();
		
		byte[] addr = next_previousHopAddress.getAddress();
		
		System.arraycopy(addr,0, getBytes(), 4, addr.length);
		
		int offset = 8;
		
		this.bytes[offset] = (byte)(((long)this.logicalInterfaceHandle & 0xFF000000L) >> 24);
		this.bytes[offset+1] = (byte)(((long)this.logicalInterfaceHandle & 0x00FF0000L) >> 16);
		this.bytes[offset+2] = (byte)(((long)this.logicalInterfaceHandle & 0x0000FF00L) >> 8);
		this.bytes[offset+3] = (byte)((long)this.logicalInterfaceHandle & 0x000000FFL);
	
	}

	@Override
	public void decodeHeader() {
		
	}

	@Override
	public void decode() {
		
	}

	/**
	 * 
	 */
	
	@Override
	public void decode(byte[] bytes, int offset) {

		byte[] receivedAddress = new byte[4];
		System.arraycopy(bytes,offset+4,receivedAddress,0,4);
		try{
			next_previousHopAddress = (Inet4Address) Inet4Address.getByAddress(receivedAddress);
		}catch(UnknownHostException e){
			// FIXME: Poner logs con respecto a excepcion
		}
		logicalInterfaceHandle = (double)(bytes[offset+8]|bytes[offset+9]|bytes[offset+10]|bytes[offset+11]);
			
	}
	
	
	
	// Getters & Setters
		
	public Inet4Address getNext_previousHopAddress() {
		return next_previousHopAddress;
	}

	public void setNext_previousHopAddress(Inet4Address next_previousHopAddress) {
		this.next_previousHopAddress = next_previousHopAddress;
	}

	public double getLogicalInterfaceHandle() {
		return logicalInterfaceHandle;
	}

	public void setLogicalInterfaceHandle(double logicalInterfaceHandle) {
		this.logicalInterfaceHandle = logicalInterfaceHandle;
	}
	
}
