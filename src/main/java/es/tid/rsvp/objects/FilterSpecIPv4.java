package es.tid.rsvp.objects;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.protocol.commons.ByteHandler;

/*

RFC 2205                          RSVP                    September 1997

   A.9 FILTER_SPEC Class

      FILTER_SPEC class = 10.

      o    IPv4 FILTER_SPEC object: Class = 10, C-Type = 1

           +-------------+-------------+-------------+-------------+
           |               IPv4 SrcAddress (4 bytes)               |
           +-------------+-------------+-------------+-------------+
           |    //////   |    //////   |          SrcPort          |
           +-------------+-------------+-------------+-------------+


      o    IPv6 FILTER_SPEC object: Class = 10, C-Type = 2

           +-------------+-------------+-------------+-------------+
           |                                                       |
           +                                                       +
           |                                                       |
           +               IPv6 SrcAddress (16 bytes)              +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+
           |    //////   |    //////   |          SrcPort          |
           +-------------+-------------+-------------+-------------+


      o    IPv6 Flow-label FILTER_SPEC object: Class = 10, C-Type = 3

           +-------------+-------------+-------------+-------------+
           |                                                       |
           +                                                       +
           |                                                       |
           +               IPv6 SrcAddress (16 bytes)              +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+
           |   ///////   |         Flow Label (24 bits)            |
           +-------------+-------------+-------------+-------------+



      SrcAddress

           The IP source address for a sender host.  Must be non-zero.


      SrcPort

           The UDP/TCP source port for a sender, or zero to indicate
           `none'.

      Flow Label

           A 24-bit Flow Label, defined in IPv6.  This value may be used
           by the packet classifier to efficiently identify the packets
           belonging to a particular (sender->destination) data flow.
           

 */

public class FilterSpecIPv4 extends FilterSpec{

	protected Inet4Address srcAddress;
	protected int srcPort;
	
	public FilterSpecIPv4(){
		
		classNum = 10;
		cType = 1;

		try{
			srcAddress = (Inet4Address) Inet4Address.getLocalHost();
		}catch(UnknownHostException e){
			
		}
		srcPort = 0;
		
	}
	
	public FilterSpecIPv4(Inet4Address srcAddress, int srcPort){
		
		classNum = 10;
		cType = 1;
		this.srcAddress = srcAddress;
		this.srcPort = srcPort;
		
	}

	public FilterSpecIPv4(byte[] bytes, int offset){
		super(bytes, offset);
		this.decode();
	}
	/*

           +-------------+-------------+-------------+-------------+
           |               IPv4 SrcAddress (4 bytes)               |
           +-------------+-------------+-------------+-------------+
           |    //////   |    //////   |          SrcPort          |
           +-------------+-------------+-------------+-------------+
           
	 */

	public void encode() {
		length = 12;
		bytes = new byte[length];
		encodeHeader();
		
		byte[] addr = srcAddress.getAddress();
		
		System.arraycopy(addr,0, getBytes(), 4, addr.length);

		bytes[10] = (byte)((srcPort>>8) & 0xFF);
		bytes[11] = (byte)(srcPort & 0xFF);
		
	}

	/**
	 * 
	 */
	
	public void decode() {

		
		int headerSize = 4;
		int currentIndex = headerSize;
				
		byte[] readAddress = new byte[4];
		System.arraycopy(bytes,currentIndex,readAddress,0,4);
		try{
			srcAddress = (Inet4Address) Inet4Address.getByAddress(readAddress);
			currentIndex = currentIndex + 4;
		}catch(UnknownHostException e){
			// FIXME: Poner logs con respecto a excepcion
		}
		srcPort = ByteHandler.decode2bytesInteger(bytes,currentIndex+2);
		
	}
	
	
	// Getters & Setters
	
	public Inet4Address getSrcAddress() {
		return srcAddress;
	}

	public void setSrcAddress(Inet4Address srcAddress) {
		this.srcAddress = srcAddress;
	}

	public int getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}



	
	
}
