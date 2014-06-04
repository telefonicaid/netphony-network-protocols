package tid.rsvp.objects;

import java.net.Inet4Address;
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

public class ResvConfirmIPv4 extends ResvConfirm{

	protected Inet4Address receiverAddress;
	
	public ResvConfirmIPv4(){
		
		classNum = 15;
		cType = 1;
		length = 8;
		bytes = new byte[length];
		try{
			receiverAddress = (Inet4Address) Inet4Address.getLocalHost();
		}catch(UnknownHostException e){
			
		}

		
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
           |            IPv4 Receiver Address (4 bytes)            |
           +-------------+-------------+-------------+-------------+
	 */
	
	
	@Override
	public void encode() {
		encodeHeader();
		
		byte[] addr = receiverAddress.getAddress();
		
		System.arraycopy(addr,0, getBytes(), 4, addr.length);
	}

	@Override
	public void decodeHeader() {
		
	}
	
	@Override
	public void decode(byte[] bytes, int offset) {
		
		byte[] receivedAddress = new byte[4];
		System.arraycopy(bytes,offset+4,receivedAddress,0,4);
		try{
			receiverAddress = (Inet4Address) Inet4Address.getByAddress(receivedAddress);
		}catch(UnknownHostException e){
			// FIXME: Poner logs con respecto a excepcion
		}
		
	}


	public Inet4Address getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(Inet4Address receiverAddress) {
		this.receiverAddress = receiverAddress;
	}


	
	
}
