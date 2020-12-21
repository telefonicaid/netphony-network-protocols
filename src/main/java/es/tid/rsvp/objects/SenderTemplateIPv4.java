package es.tid.rsvp.objects;

import es.tid.protocol.commons.ByteHandler;

/*
 * FIXME: TODO


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
		super();
		cType = 1;
	}
	
public SenderTemplateIPv4(byte[] bytes, int offset){
	super(bytes, offset);
	this.decode();
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
	//TODO
	@Override
	public void encode() {
		this.length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		bytes = new byte[length];
		encodeHeader();
		
	}
	//TODO
	@Override
	public void decode() {
		
	}

	
}
