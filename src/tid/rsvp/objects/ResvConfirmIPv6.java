package tid.rsvp.objects;

import java.net.Inet6Address;
import java.net.UnknownHostException;

/*
 * 

RFC 2205                          RSVP                    September 1997


   A.14 Resv_CONFIRM Class

      RESV_CONFIRM class = 15.

      o    IPv4 RESV_CONFIRM object: Class = 15, C-Type = 1

           +-------------+-------------+-------------+-------------+
           |            IPv4 Receiver Address (4 bytes)            |
           +-------------+-------------+-------------+-------------+


      o    IPv6 RESV_CONFIRM object: Class = 15, C-Type = 2

           +-------------+-------------+-------------+-------------+
           |                                                       |
           +                                                       +
           |                                                       |
           +            IPv6 Receiver Address (16 bytes)           +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+


 * 
 */

public class ResvConfirmIPv6 extends ResvConfirm{

	protected Inet6Address receiverAddress;
	
	public ResvConfirmIPv6(){
		
		classNum = 15;
		cType = 2;
		length = 20;
		bytes = new byte[length];
		try{
			receiverAddress = (Inet6Address) Inet6Address.getLocalHost();
		}catch(UnknownHostException e){
			
		}
		
	}
	
	public ResvConfirmIPv6(Inet6Address receiverAddress){
		
		classNum = 15;
		cType = 2;
		length = 20;
		bytes = new byte[length];
		this.receiverAddress = receiverAddress;
		
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
           +            IPv6 Receiver Address (16 bytes)           +
           |                                                       |
           +                                                       +
           |                                                       |
           +-------------+-------------+-------------+-------------+
	 */
	

	public void encode() {

		encodeHeader();
		
		byte[] addr = receiverAddress.getAddress();
		
		System.arraycopy(addr,0, getBytes(), 4, addr.length);
	}


	public void decodeHeader() {
		
	}

	/**
	 * 
	 */
	
	public void decode(byte[] bytes, int offset) {
		byte[] receivedAddress = new byte[16];
		System.arraycopy(bytes,offset+4,receivedAddress,0,16);
		try{
			receiverAddress = (Inet6Address) Inet6Address.getByAddress(receivedAddress);
		}catch(UnknownHostException e){
			// FIXME: Poner logs con respecto a excepcion
		}
		
	}
	
	
	// Getters & Setters

	public Inet6Address getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(Inet6Address receiverAddress) {
		this.receiverAddress = receiverAddress;
	}



	
	
}
