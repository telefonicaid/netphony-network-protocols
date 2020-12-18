package es.tid.rsvp.objects;

import java.net.Inet6Address;
import java.net.UnknownHostException;

/* RFC 2205                          RSVP                    September 1997


A.5 ERROR_SPEC Class

   ERROR_SPEC class = 6.

   o    IPv6 ERROR_SPEC object: Class = 6, C-Type = 2


        +-------------+-------------+-------------+-------------+
        |                                                       |
        +                                                       +
        |                                                       |
        +           IPv6 Error Node Address (16 bytes)          +
        |                                                       |
        +                                                       +
        |                                                       |
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

public class ErrorSpecIPv6 extends ErrorSpec{

	protected Inet6Address errorNodeAddress;
	protected int flags;
	protected int errorCode;
	protected int errorValue;
	
	public ErrorSpecIPv6(){
		
		classNum = 6;
		cType = 2;
		length = 24;

		flags = 0;
		errorCode = 0;
		errorValue = 0;
		
	}
	
	public ErrorSpecIPv6(Inet6Address errorNodeAddress, int flags, int errorCode, int errorValue){
		
		classNum = 6;
		cType = 2;

		this.errorNodeAddress = errorNodeAddress;
		this.flags = flags;
		this.errorCode = errorCode;
		this.errorValue = errorValue;
		
	}
	
	public ErrorSpecIPv6(byte[] bytes, int offset){
		super(bytes, offset);
		this.decode(bytes,offset);
	}
	
	

	/*

        +-------------+-------------+-------------+-------------+
        |                                                       |
        +                                                       +
        |                                                       |
        +           IPv6 Error Node Address (16 bytes)          +
        |                                                       |
        +                                                       +
        |                                                       |
        +-------------+-------------+-------------+-------------+
        |    Flags    |  Error Code |        Error Value        |
        +-------------+-------------+-------------+-------------+
 
	 */
	
	@Override
	public void encode() {
		length = 24;
		bytes = new byte[length];
		encodeHeader();
		
		byte[] addr = errorNodeAddress.getAddress();
		
		System.arraycopy(addr,0, getBytes(), 4, addr.length);
		
		bytes[20] = (byte)(flags & 0xFF);
		bytes[21] = (byte)(errorCode & 0xFF);
		bytes[22] = (byte)((errorValue >>8) & 0xFF);
		bytes[23] = (byte)(errorValue & 0xFF);
	}


	@Override
	public void decode(byte[] bytes, int offset) {

		length = (int)(bytes[offset]|bytes[offset+1]);
		int headerSize = 4;
		int unprocessedBytes = length - headerSize;
		int currentIndex = offset+headerSize;
		
		if(unprocessedBytes > 0){
			
			byte[] readAddress = new byte[16];
			System.arraycopy(bytes,currentIndex,readAddress,0,16);
			try{
				errorNodeAddress = (Inet6Address) Inet6Address.getByAddress(readAddress);
				currentIndex = currentIndex + 16;
				unprocessedBytes = unprocessedBytes - 16;				
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

	public Inet6Address getErrorNodeAddress() {
		return errorNodeAddress;
	}

	public void setErrorNodeAddress(Inet6Address errorNodeAddress) {
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
