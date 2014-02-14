package tid.pce.pcep.objects.tlvs;

import tid.pce.pcep.objects.MalformedPCEPObjectException;
import tid.pce.pcep.objects.ObjectParameters;
import tid.rsvp.RSVPProtocolViolationException;
import tid.rsvp.objects.ErrorSpecIPv4;
import tid.rsvp.objects.ErrorSpecIPv6;
import tid.rsvp.objects.RSVPObject;

/*
 * If the set up of an LSP failed at a downstream node which returned an
   ERROR_SPEC to the PCC, the ERROR_SPEC MUST be included in the LSP
   State Report.  Depending on whether RSVP signaling was performed over
   IPv4 or IPv6, the LSP Object will contain an IPV4-ERROR_SPEC TLV or
   an IPV6-ERROR_SPEC TLV.

   The format of the IPV4-RSVP-ERROR-SPEC TLV is shown in the following
   figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |           Type=[TBD]          |            Length=8           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                                                               |
     +                IPv4 ERROR_SPEC object (rfc2205)               +
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                Figure 22: IPV4-RSVP-ERROR-SPEC TLV format

   The type of the TLV is [TBD] and it has a fixed length of 8 octets.
   The value contains the RSVP IPv4 ERROR_SPEC object defined in
   [RFC2205].  Error codes allowed in the ERROR_SPEC object are defined
   in [RFC2205], [RFC3209] and [RFC3473]..
   
   @author jaume
 */

public class RSVPErrorSpecTLV extends PCEPTLV
{
	protected ErrorSpecIPv4 errorSpecObject4;
	
	public RSVPErrorSpecTLV()
	{
		errorSpecObject4 = null;
		this.TLVType=ObjectParameters.PCEP_TLV_TYPE_RSVP_ERROR_SPEC;
	}

	public RSVPErrorSpecTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException
	{		
		super(bytes,offset);
		errorSpecObject4 = null;
		decode();
	}

	@Override
	public void encode() 
	{
		log.info("Encoding RSVPErrorSpecTLV TLV");
		
		int length = errorSpecObject4.getLength();
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.TotalTLVLength];
		
		int offset=4;
		
		errorSpecObject4.encode();
		
		System.arraycopy(errorSpecObject4.getBytes(), 0, tlv_bytes, offset, errorSpecObject4.getLength());
		
	}
	
	public void decode() throws MalformedPCEPObjectException
	{
		log.info("Decoding RSVPErrorSpecTLV TLV");
		int offset = 4;
		
		int classNum = RSVPObject.getClassNum(tlv_bytes,offset);
		if(classNum == 6)
		{
			int cType = RSVPObject.getcType(tlv_bytes,offset);
			if(cType == 1){
				
				// Error Spec IPv4
				errorSpecObject4 = new ErrorSpecIPv4();
				errorSpecObject4.decode(tlv_bytes, offset);
				
			}else{
				// Fallo en cType
				throw new MalformedPCEPObjectException();
				//throw new RSVPProtocolViolationException(); ?????
			}
		}
		else
		{
			throw new MalformedPCEPObjectException();
		}
		
	}

}
