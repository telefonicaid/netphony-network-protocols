package es.tid.rsvp.objects;

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

	protected int refreshPeriod;
	
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
		bytes = new byte[length];
		refreshPeriod = 0;
		
	}
	
	public TimeValues(int refreshPeriod){
		
		classNum = 5;
		cType = 1;
		length = 8;
		bytes = new byte[length];
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
		// TODO Auto-generated method stub
		encodeHeader();
		
		bytes[4] = (byte)((refreshPeriod>>24) & 0xFF);
		bytes[5] = (byte)((refreshPeriod>>16) & 0xFF);
		bytes[6] = (byte)((refreshPeriod>>8) & 0xFF);
		bytes[7] = (byte)(refreshPeriod & 0xFF);
	}

	public int getRefreshPeriod() {
		return refreshPeriod;
	}

	public void setRefreshPeriod(int refreshPeriod) {
		this.refreshPeriod = refreshPeriod;
	}

	@Override
	public void decode(byte[] bytes, int offset) {
		refreshPeriod  = (int) (((int)bytes[offset+4] << 24)  | ((int)bytes[offset+5] << 16)
	 			  | ((int)bytes[offset+6] << 8)
	 			  | ((int)bytes[offset+7]));		
		//refreshPeriod = (int)(bytes[offset+4] | bytes[offset+5] | bytes[offset+6] | bytes[offset+7]);

	}
}
