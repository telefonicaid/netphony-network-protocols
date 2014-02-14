package tid.rsvp.objects;

/*
 * 

-
RFC 2205                          RSVP                    September 1997


   A.10 SENDER_TEMPLATE Class

      SENDER_TEMPLATE class = 11.

      o    IPv4 SENDER_TEMPLATE object: Class = 11, C-Type = 1

           Definition same as IPv4/UDP FILTER_SPEC object.

      o    IPv6 SENDER_TEMPLATE object: Class = 11, C-Type = 2

           Definition same as IPv6/UDP FILTER_SPEC object.

      o    IPv6 Flow-label SENDER_TEMPLATE object: Class = 11, C-Type =
           3



 * 
 */

public class SenderTemplateIPv4 extends SenderTemplate{

	//FIXME: Buscar IPv4/UDP FILTER_SPEC object.
	
	public SenderTemplateIPv4(){
		
		classNum = 11;
		cType = 1;
		length = 4;
		bytes = new byte[length];

		
	}
	
	/**	
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
	public void encode() {
		encodeHeader();
		
	}

	@Override
	public void decode(byte[] bytes, int offset) {
		// FIXME: decodificar cuando se sepa lo que irá dentro
		
	}

	
}
