package tid.rsvp.objects;

import java.net.Inet6Address;
import java.net.UnknownHostException;

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

public class FilterSpecIPv6 extends FilterSpec{

	protected Inet6Address srcAddress;
	protected int srcPort;
	
	public FilterSpecIPv6(){
		
		classNum = 10;
		cType = 2;
		length = 24;
		bytes = new byte[length];
		try{
			srcAddress = (Inet6Address) Inet6Address.getLocalHost();
		}catch(UnknownHostException e){
			
		}
		srcPort = 0;
		
	}
	
	public FilterSpecIPv6(Inet6Address srcAddress, int srcPort){
		
		classNum = 10;
		cType = 2;
		length = 24;
		bytes = new byte[length];
		this.srcAddress = srcAddress;
		this.srcPort = srcPort;
		
	}
	
	
	/*
	 
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

	*/

	@Override
	public void encode() {

		encodeHeader();
		
		byte[] addr = srcAddress.getAddress();
		
		System.arraycopy(addr,0, getBytes(), 4, addr.length);

		bytes[22] = (byte)((srcPort>>8) & 0xFF);
		bytes[23] = (byte)(srcPort & 0xFF);
	}


	public void decode(byte[] bytes, int offset) {
		
		length = (int)(bytes[offset]|bytes[offset+1]);
		int headerSize = 4;
		int currentIndex = offset + headerSize;
				
		byte[] readAddress = new byte[16];
		System.arraycopy(bytes,currentIndex,readAddress,0,16);
		try{
			srcAddress = (Inet6Address) Inet6Address.getByAddress(readAddress);
			currentIndex = currentIndex + 16;
		}catch(UnknownHostException e){
			// FIXME: Poner logs con respecto a excepcion
		}
		srcPort = (int)(bytes[currentIndex+2]|bytes[currentIndex+3]);
	}
	
	
	// Getters & Setters
		
	public Inet6Address getSrcAddress() {
		return srcAddress;
	}

	public void setSrcAddress(Inet6Address srcAddress) {
		this.srcAddress = srcAddress;
	}

	public int getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}


	
}
