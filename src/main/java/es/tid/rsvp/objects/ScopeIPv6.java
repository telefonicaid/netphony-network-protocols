package es.tid.rsvp.objects;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

import es.tid.rsvp.RSVPProtocolViolationException;

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
		sourceIpAddresses = new LinkedList<Inet6Address>();
		
	}
	
	public ScopeIPv6(byte[] bytes, int offset) throws RSVPProtocolViolationException{
		super(bytes, offset);
		this.decode();
	}
	
	public void addSourceIpAddress(Inet6Address addr){
		
		sourceIpAddresses.add(addr);
		
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
		int size = sourceIpAddresses.size();
		length=4+size*16;
		bytes = new byte[length];
		encodeHeader();
		
		int currentIndex = 4;
		
		for(int i = 0; i < size; i++){
			
			byte[] addr = (sourceIpAddresses.get(i)).getAddress();
			System.arraycopy(addr,0, getBytes(), currentIndex, addr.length);
			currentIndex = currentIndex + 16;
			
		}
	}



	
	public void decode() {
		 sourceIpAddresses = new LinkedList<Inet6Address>();
		int offset=0;
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
