package es.tid.rsvp.objects;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.RSVPProtocolViolationException;

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
	protected long logicalInterfaceHandle;
	
	public RSVPHopIPv4(){
		
		classNum = 3;
		cType = 1;
		
		logicalInterfaceHandle = 0;
		
	}
	
public RSVPHopIPv4(byte[] bytes, int offset) throws RSVPProtocolViolationException{
	super(bytes, offset);
	this.decode();
}
	
	public RSVPHopIPv4(Inet4Address next_previousHopAddress, int logicalInterfaceHandle){
		
		classNum = 3;
		cType = 1;
		
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
	public void encode() {
		length = 12;
		bytes = new byte[length];
		
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


	
	
	
	public void decode() throws RSVPProtocolViolationException {
		try{
		int offset=0;
		byte[] receivedAddress = new byte[4];
		System.arraycopy(bytes,offset+4,receivedAddress,0,4);
		
			next_previousHopAddress = (Inet4Address) Inet4Address.getByAddress(receivedAddress);
		
		logicalInterfaceHandle = ByteHandler.decode4bytesLong(bytes,offset+8);
		}catch(Exception e){
			throw new RSVPProtocolViolationException();
		}	
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

	public void setLogicalInterfaceHandle(long logicalInterfaceHandle) {
		this.logicalInterfaceHandle = logicalInterfaceHandle;
	}
	
}
