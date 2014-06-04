package tid.pce.pcep.objects;

/**
 * Represents a PCE-ID Object (defined in RFC 5886).
 * 
 * 4.3.  PCE-ID Object

   The PCE-ID object is used to specify a PCE's IP address.  The PCE-ID
   object can either be used to specify the list of PCEs for which
   monitoring data is requested and to specify the IP address of the
   requesting PCC.

   A set of PCE-ID objects may be inserted within a PCReq or a PCMonReq
   message to specify the PCE for which PCE state metrics are requested
   and in a PCMonRep or a PCRep message to record the IP address of the
   PCE reporting PCE state metrics or that was involved in the path
   computation chain.

   Two PCE-ID objects (for IPv4 and IPv6) are defined.  PCE-ID Object-
   Class (25) has been assigned by IANA.  PCE-ID Object-Type (1 for IPv4
   and 2 for IPv6) has been assigned by IANA.

   The format of the PCE-ID object body for IPv4 and IPv6 are as
   follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                           IPv4 Address                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   |                           IPv6 Address                        |
   |                                                               |
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The PCE-ID object body has a fixed length of 4 octets for IPv4 and 16
   octets for IPv6.

   When a dynamic discovery mechanism is used for PCE discovery, a PCE
   advertises its PCE address in the PCE-ADDRESS sub-TLV defined in
   [RFC5088] and [RFC5089].  A PCC MUST use this address in PCReq and
   PCMonReq messages and a PCE MUST also use this address in PCRep and
   PCMonRep messages.
 * @author ogondio
 *
 */
public abstract class PceId extends PCEPObject {
	
	public PceId(){
		super();
	}
	public  PceId(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);			
		decode();
		
	}

	
}
