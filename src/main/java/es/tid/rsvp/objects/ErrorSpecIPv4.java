package es.tid.rsvp.objects;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/* RFC 2205                          RSVP                    September 1997


A.5 ERROR_SPEC Class

   ERROR_SPEC class = 6.

   o    IPv4 ERROR_SPEC object: Class = 6, C-Type = 1


        +-------------+-------------+-------------+-------------+
        |            IPv4 Error Node Address (4 bytes)          |
        +-------------+-------------+-------------+-------------+
        |    Flags    |  Error Code |        Error Value        |
        +-------------+-------------+-------------+-------------+


   Error Node Address

        The IP address of the node in which the error was detected.

   Flags

        0x01 = InPlace

             This flag is used only for an ERROR_SPEC object in a
             ResvErr message.  If it on, this flag indicates that
             there was, and still is, a reservation in place at the
             failure point.

        0x02 = NotGuilty

             This flag is used only for an ERROR_SPEC object in a
             ResvErr message, and it is only set in the interface to
             the receiver application.  If it on, this flag indicates
             that the FLOWSPEC that failed was strictly greater than
             the FLOWSPEC requested by this receiver.

   Error Code

        A one-octet error description.

   Error Value

        A two-octet field containing additional information about the
             error.  Its contents depend upon the Error Type.

   The values for Error Code and Error Value are defined in Appendix
   B.

 */

public class ErrorSpecIPv4 extends ErrorSpec{

	protected Inet4Address errorNodeAddress;
	protected int flags;
	protected int errorCode;
	protected int errorValue;
	
	public static final int ERROR_SPEC_FLAG_IN_PLACE = 0x01;
	public static final int ERROR_SPEC_FLAG_GUILTY = 0x02;
	
	public ErrorSpecIPv4(){
		
		classNum = 6;
		cType = 1;
		length = 12;
		bytes = new byte[length];
		try{
			errorNodeAddress = (Inet4Address) Inet4Address.getLocalHost();
		}catch(UnknownHostException e){
			
		}
		flags = 0;
		errorCode = 0;
		errorValue = 0;
		
	}
	
	public ErrorSpecIPv4(Inet4Address errorNodeAddress, int flags, int errorCode, int errorValue){
		
		classNum = 6;
		cType = 1;
		length = 12;
		bytes = new byte[length];
		this.errorNodeAddress = errorNodeAddress;
		this.flags = flags;
		this.errorCode = errorCode;
		this.errorValue = errorValue;
		
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
        |            IPv4 Error Node Address (4 bytes)          |
        +-------------+-------------+-------------+-------------+
        |    Flags    |  Error Code |        Error Value        |
        +-------------+-------------+-------------+-------------+
	 
	 */
	
	public void encode() {

		encodeHeader();
		
		byte[] addr = errorNodeAddress.getAddress();
		
		System.arraycopy(addr,0, getBytes(), 4, addr.length);
		
		bytes[8] = (byte)(flags & 0xFF);
		bytes[9] = (byte)(errorCode & 0xFF);
		bytes[10] = (byte)((errorValue >>8) & 0xFF);
		bytes[11] = (byte)(errorValue & 0xFF);
		
	}

	public void decodeHeader() {
		
	}
	
	public void decode(byte[] bytes, int offset) {

		length = (int)(bytes[offset]|bytes[offset+1]);
		int headerSize = 4;
		int unprocessedBytes = length - headerSize;
		int currentIndex = offset+headerSize;
		
		if(unprocessedBytes > 0){
			
			byte[] readAddress = new byte[4];
			System.arraycopy(bytes,currentIndex,readAddress,0,4);
			try{
				errorNodeAddress = (Inet4Address) Inet4Address.getByAddress(readAddress);
				currentIndex = currentIndex + 4;
				unprocessedBytes = unprocessedBytes - 4;				
			}catch(UnknownHostException e){
				// FIXME: Poner logs con respecto a excepcion
			}
			if(unprocessedBytes > 0){
				
				flags = (int)(bytes[currentIndex]);
				currentIndex = currentIndex + 1;
				unprocessedBytes = unprocessedBytes - 1;
				if(unprocessedBytes > 0){
					
					errorCode = (int)(bytes[currentIndex]);
					currentIndex = currentIndex + 1;
					unprocessedBytes = unprocessedBytes - 1;
				
					if(unprocessedBytes > 0){
						
						errorValue = (int)(bytes[currentIndex]|bytes[currentIndex+1]);
						currentIndex = currentIndex + 2;
						unprocessedBytes = unprocessedBytes - 2;
						
					}else{
						// Malformed Object
					}
				}else{
					// Malformed Object					
				}
			}else{
				// Malformed Object
			}
		}// Malformed Object
	
		
	}

	
	// Getters & Setters
	

	public Inet4Address getErrorNodeAddress() {
		return errorNodeAddress;
	}

	public void setErrorNodeAddress(Inet4Address errorNodeAddress) {
		this.errorNodeAddress = errorNodeAddress;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}


	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorValue() {
		return errorValue;
	}

	public void setErrorValue(int errorValue) {
		this.errorValue = errorValue;
	}



	
	
}
