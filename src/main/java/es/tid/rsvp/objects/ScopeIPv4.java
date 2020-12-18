package es.tid.rsvp.objects;

import java.net.Inet4Address;
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
		sourceIpAddresses = new LinkedList<Inet4Address>();
		
	}
	
	public ScopeIPv4(byte[] bytes, int offset) throws RSVPProtocolViolationException{
		super(bytes, offset);
		this.decode();
	}
	
	
	
	public void addSourceIpAddress(Inet4Address addr){
		
		sourceIpAddresses.add(addr);
		
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
		int size = sourceIpAddresses.size();
		length=4+size*4;
		bytes = new byte[length];
		encodeHeader();
		
		
		int currentIndex = 4;
		
		for(int i = 0; i < size; i++){
			
			byte[] addr = (sourceIpAddresses.get(i)).getAddress();
			System.arraycopy(addr,0, getBytes(), currentIndex, addr.length);
			currentIndex = currentIndex + 4;
			
		}
		
	}


	public void decode() {
	    sourceIpAddresses = new LinkedList<Inet4Address>();
       int offset=0;
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
