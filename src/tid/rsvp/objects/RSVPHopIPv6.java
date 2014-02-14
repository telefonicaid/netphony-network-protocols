package tid.rsvp.objects;

import java.net.Inet6Address;
import java.net.UnknownHostException;

/*
*

RFC 2205                          RSVP                    September 1997

  A.2 RSVP_HOP Class

     RSVP_HOP class = 3.

      o    IPv6 RSVP_HOP object: Class = 3, C-Type = 2

           +-------------+-------------+-------------+-------------+
           |                                                       |
           +                                                       +
           |                                                       |
           +             IPv6 Next/Previous Hop Address            +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+
           |                Logical Interface Handle               |
           +-------------+-------------+-------------+-------------+


*/

public class RSVPHopIPv6 extends RSVPHop{

	protected Inet6Address next_previousHopAddress;
	protected int logicalInterfaceHandle;
	
	public RSVPHopIPv6(){
		
		classNum = 3;
		cType = 2;
		length = 24;
		bytes = new byte[length];
		try{
			next_previousHopAddress = (Inet6Address) Inet6Address.getLocalHost();
		}catch(UnknownHostException e){
			
		}
		logicalInterfaceHandle = 0;
		
		
	}
	
	public RSVPHopIPv6(Inet6Address next_previousHopAddress, int logicalInterfaceHandle){
		
		classNum = 3;
		cType = 2;
		length = 24;
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
		bytes[0] = (byte)((length>>8) & 0xFF);
		bytes[1] = (byte)(length & 0xFF);
		bytes[2] = (byte) classNum;
		bytes[3] = (byte) cType;
		
	}

	/*
	*
      o    IPv6 RSVP_HOP object: Class = 3, C-Type = 2

           +-------------+-------------+-------------+-------------+
           |                                                       |
           +                                                       +
           |                                                       |
           +             IPv6 Next/Previous Hop Address            +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+
           |                Logical Interface Handle               |
           +-------------+-------------+-------------+-------------+



	*/
	
	@Override
	public void encode() {
		encodeHeader();
		
		byte[] addr = next_previousHopAddress.getAddress();
		
		System.arraycopy(addr,0, getBytes(), 4, addr.length);
		
		bytes[20] = (byte)((logicalInterfaceHandle>>24) & 0xFF);
		bytes[21] = (byte)((logicalInterfaceHandle>>16) & 0xFF);
		bytes[22] = (byte)((logicalInterfaceHandle>>8) & 0xFF);
		bytes[23] = (byte)(logicalInterfaceHandle & 0xFF);
	}

	@Override
	public void decodeHeader() {
		
	}

	@Override
	public void decode() {

		
	}
	
	public void decode(byte[] bytes, int offset) {
		byte[] receivedAddress = new byte[16];
		System.arraycopy(bytes,offset+4,receivedAddress,0,16);
		try{
			next_previousHopAddress = (Inet6Address) Inet6Address.getByAddress(receivedAddress);
		}catch(UnknownHostException e){
			// FIXME: Poner logs con respecto a excepcion
		}
		logicalInterfaceHandle = (int)(bytes[offset+20]|bytes[offset+21]|bytes[offset+22]|bytes[offset+23]);
	}
	
	
	
	// Getters & Setters
	

	public Inet6Address getNext_previousHopAddress() {
		return next_previousHopAddress;
	}

	public void setNext_previousHopAddress(Inet6Address next_previousHopAddress) {
		this.next_previousHopAddress = next_previousHopAddress;
	}

	public int getLogicalInterfaceHandle() {
		return logicalInterfaceHandle;
	}

	public void setLogicalInterfaceHandle(int logicalInterfaceHandle) {
		this.logicalInterfaceHandle = logicalInterfaceHandle;
	}



	
	
}
