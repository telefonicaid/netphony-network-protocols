package es.tid.rsvp.objects;

/* FIXME: TODO
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

public class SenderTemplateIPv6 extends SenderTemplate{

	//FIXME: Buscar IPv6/UDP FILTER_SPEC object.
	
	public SenderTemplateIPv6(){
		super();
		cType = 2;
		
		
	}
	
	public SenderTemplateIPv6(byte[] bytes, int offset){
		
		super(bytes,offset);
		decode();
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
	public void encode() {
		length = 4;
		bytes = new byte[length];
		encodeHeader();
	}


	@Override
	public void decode() {
		// FIXME: modificar cuando se mire la rfc
		
	}
	
}
