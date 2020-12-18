package es.tid.rsvp.objects;

import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.RSVPProtocolViolationException;

/*RFC 2205                          RSVP                    September 1997
 *    A.4 TIME_VALUES Class

      TIME_VALUES class = 5.

      o    TIME_VALUES Object: Class = 5, C-Type = 1


           +-------------+-------------+-------------+-------------+
           |                   Refresh Period R                    |
           +-------------+-------------+-------------+-------------+



      Refresh Period

           The refresh timeout period R used to generate this message;
           in milliseconds.

 */


public class TimeValues extends RSVPObject{

	protected long refreshPeriod;
	
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
	
	public TimeValues(){
		
		classNum = 5;
		cType = 1;
		length = 8;
		
		
	}
	
	public TimeValues(byte[] bytes, int offset) throws RSVPProtocolViolationException{
		super(bytes,offset);
		decode( );
	}
	
	public TimeValues(long refreshPeriod){
		
		classNum = 5;
		cType = 1;
		length = 8;
		
		this.refreshPeriod = refreshPeriod;
	}
	
	/*  A.4 TIME_VALUES Class

    TIME_VALUES class = 5.

    o    TIME_VALUES Object: Class = 5, C-Type = 1


         +-------------+-------------+-------------+-------------+
         |                   Refresh Period R                    |
         +-------------+-------------+-------------+-------------+

	Refresh Period

         The refresh timeout period R used to generate this message;
         in milliseconds.

*/
	
	@Override
	public void encode() {
		bytes = new byte[length];
		refreshPeriod = 0;
		// TODO Auto-generated method stub
		encodeHeader();
		
		bytes[4] = (byte)((refreshPeriod>>24) & 0xFF);
		bytes[5] = (byte)((refreshPeriod>>16) & 0xFF);
		bytes[6] = (byte)((refreshPeriod>>8) & 0xFF);
		bytes[7] = (byte)(refreshPeriod & 0xFF);
	}

	public long getRefreshPeriod() {
		return refreshPeriod;
	}

	public void setRefreshPeriod(long refreshPeriod) {
		this.refreshPeriod = refreshPeriod;
	}

	
	public void decode() {
		int offset = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		refreshPeriod  = ByteHandler.decode4bytesLong(this.getBytes(),offset);	

	}
}
