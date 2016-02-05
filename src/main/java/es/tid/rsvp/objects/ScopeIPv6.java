package es.tid.rsvp.objects;

import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

/*
 * RFC 2205                          RSVP                    September 1997


   A.6 SCOPE Class

      SCOPE class = 7.

      This object contains a list of IP addresses, used for routing
      messages with wildcard scope without loops.  The addresses must be
      listed in ascending numerical order.

      o    IPv6  SCOPE list object: Class = 7, C-Type = 2


           +-------------+-------------+-------------+-------------+
           |                                                       |
           +                                                       +
           |                                                       |
           +                IPv6 Src Address (16 bytes)            +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+
           //                                                      //
           +-------------+-------------+-------------+-------------+
           |                                                       |
           +                                                       +
           |                                                       |
           +                IPv6 Src Address (16 bytes)            +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+



 */

public class ScopeIPv6 extends Scope{

	protected LinkedList<Inet6Address> sourceIpAddresses;
	
	public ScopeIPv6(){
		
		classNum = 7;
		cType = 2;
		length = 4;
		sourceIpAddresses = new LinkedList<Inet6Address>();
		
	}
	
	public void addSourceIpAddress(Inet6Address addr){
		
		sourceIpAddresses.add(addr);
		length = length + 16;
		
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

           +-------------+-------------+-------------+-------------+
           |                                                       |
           +                                                       +
           |                                                       |
           +                IPv6 Src Address (16 bytes)            +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+
           //                                                      //
           +-------------+-------------+-------------+-------------+
           |                                                       |
           +                                                       +
           |                                                       |
           +                IPv6 Src Address (16 bytes)            +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+

	 */
	@Override
	public void encode() {

		bytes = new byte[length];
		encodeHeader();
		
		int size = sourceIpAddresses.size();
		int currentIndex = 4;
		
		for(int i = 0; i < size; i++){
			
			byte[] addr = (sourceIpAddresses.get(i)).getAddress();
			System.arraycopy(addr,0, getBytes(), currentIndex, addr.length);
			currentIndex = currentIndex + 16;
			
		}
	}

	@Override
	public void decodeHeader() {
		
	}

	@Override
	public void decode(byte[] bytes, int offset) {
		length = (int)(bytes[offset]|bytes[offset+1]);
		int headerSize = 4;
		int unprocessedBytes = length - headerSize;
		int currentIndex = offset+headerSize;
		
		while(unprocessedBytes > 0){
			
			byte[] readAddress = new byte[16];
			System.arraycopy(bytes,currentIndex,readAddress,0,16);
			try{
				Inet6Address newAddress = (Inet6Address) Inet6Address.getByAddress(readAddress);
				addSourceIpAddress(newAddress);
				currentIndex = currentIndex + 16;
				unprocessedBytes = unprocessedBytes - 16;				
			}catch(UnknownHostException e){
				// FIXME: Poner logs con respecto a excepcion
			}
			
		}
		
	}

	// Getters & Setters
	
	public LinkedList<Inet6Address> getSourceIpAddresses() {
		return sourceIpAddresses;
	}

	public void setSourceIpAddresses(LinkedList<Inet6Address> sourceIpAddresses) {
		this.sourceIpAddresses = sourceIpAddresses;
	}


	
	
}
