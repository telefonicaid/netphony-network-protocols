package es.tid.rsvp.objects;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

/*
 * RFC 2205                          RSVP                    September 1997


   A.6 SCOPE Class

      SCOPE class = 7.

      This object contains a list of IP addresses, used for routing
      messages with wildcard scope without loops.  The addresses must be
      listed in ascending numerical order.

      o    IPv4 SCOPE List object: Class = 7, C-Type = 1


           +-------------+-------------+-------------+-------------+
           |                IPv4 Src Address (4 bytes)             |
           +-------------+-------------+-------------+-------------+
           //                                                      //
           +-------------+-------------+-------------+-------------+
           |                IPv4 Src Address (4 bytes)             |
           +-------------+-------------+-------------+-------------+


 */

public class ScopeIPv4 extends Scope{

	protected LinkedList<Inet4Address> sourceIpAddresses;
	
	public ScopeIPv4(){
		
		classNum = 7;
		cType = 1;
		length = 4;
		sourceIpAddresses = new LinkedList<Inet4Address>();
		
	}
	
	
	
	public void addSourceIpAddress(Inet4Address addr){
		
		sourceIpAddresses.add(addr);
		length = length + 4;
		
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
     |                IPv4 Src Address (4 bytes)             |
     +-------------+-------------+-------------+-------------+
     //                                                      //
     +-------------+-------------+-------------+-------------+
     |                IPv4 Src Address (4 bytes)             |
     +-------------+-------------+-------------+-------------+


	 */
	
	public void encode() {
		bytes = new byte[length];
		encodeHeader();
		
		int size = sourceIpAddresses.size();
		int currentIndex = 4;
		
		for(int i = 0; i < size; i++){
			
			byte[] addr = (sourceIpAddresses.get(i)).getAddress();
			System.arraycopy(addr,0, getBytes(), currentIndex, addr.length);
			currentIndex = currentIndex + 4;
			
		}
		
	}


	public void decodeHeader() {

			
	}

	public void decode(byte[] bytes, int offset) {

		length = (int)(bytes[offset]|bytes[offset+1]);
		int headerSize = 4;
		int unprocessedBytes = length - headerSize;
		int currentIndex = offset+headerSize;
		
		while(unprocessedBytes > 0){
			
			byte[] readAddress = new byte[4];
			System.arraycopy(bytes,currentIndex,readAddress,0,4);
			try{
				Inet4Address newAddress = (Inet4Address) Inet4Address.getByAddress(readAddress);
				addSourceIpAddress(newAddress);
				currentIndex = currentIndex + 4;
				unprocessedBytes = unprocessedBytes - 4;				
			}catch(UnknownHostException e){
				// FIXME: Poner logs con respecto a excepcion
			}
			
		}
		
	}
	
	// Getters & Setters

	public LinkedList<Inet4Address> getSourceIpAddresses() {
		return sourceIpAddresses;
	}

	public void setSourceIpAddresses(LinkedList<Inet4Address> sourceIpAddresses) {
		this.sourceIpAddresses = sourceIpAddresses;
	}





	
	
}
