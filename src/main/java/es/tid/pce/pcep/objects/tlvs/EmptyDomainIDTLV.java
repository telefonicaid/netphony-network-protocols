package es.tid.pce.pcep.objects.tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import es.tid.protocol.commons.ByteHandler;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

/**
 * Domain ID TLV (Type 14) Domain Type 0 
 * (Type 0 non standard, included for compatibility with BAD PCEP speakers
 * 
  The Domain-ID TLV, when used in the OPEN object, identifies the
   domains served by the PCE.  The child PCE uses this mechanism to
   provide the domain information to the parent PCE.
   
 * @author ogondio
 *
 */
public class EmptyDomainIDTLV extends DomainIDTLV {
	
	/*
	 *  The Domain-ID TLV is defined below:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |               Type=14         |            Length             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | Domain Type   |                  Reserved                     |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                          Domain ID                          //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                       Figure 2: Domain-ID TLV Format

   The type of the TLV is 14, and it has a variable Length of the value
   portion.  The value part comprises the following:

      Domain Type (8 bits):  Indicates the domain type.  Four types of
         domains are currently defined:

         Type=1:   The Domain ID field carries a 2-byte AS number.
                   Padded with trailing zeros to a 4-byte boundary.

         Type=2:   The Domain ID field carries a 4-byte AS number.

         Type=3:   The Domain ID field carries a 4-byte OSPF area ID.

         Type=4:   The Domain ID field carries a 2-byte Area-Len and a
                   variable-length IS-IS area ID.  Padded with trailing
                   zeros to a 4-byte boundary.

      Reserved:  Zero at transmission; ignored on receipt.

      Domain ID (variable):  Indicates an IGP area ID or AS number as
         per the Domain Type field.  It can be 2 bytes, 4 bytes, or
         variable length, depending on the domain identifier used.  It
         is padded with trailing zeros to a 4-byte boundary.  In the
         case of IS-IS, it includes the Area-Len as well.

   In the case where a PCE serves more than one domain, multiple Domain-
   ID TLVs are included for each domain it serves.
	 */
	
	public EmptyDomainIDTLV(){		
		this.setDomainType(0);
	}
	
	public EmptyDomainIDTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	/**
	 * Encodes the Domain Id TLV
	 */
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.TotalTLVLength];
		encodeHeader();
		this.encodeType();
	}

	
	
	public void decode() throws MalformedPCEPObjectException{
	
	}
	
	
	
	

	public String toString() {
		return "DomainIDTLV [domainType=" + domainType ;
	}
	

	


}
