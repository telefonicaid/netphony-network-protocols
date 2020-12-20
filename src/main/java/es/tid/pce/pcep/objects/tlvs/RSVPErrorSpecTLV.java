package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.rsvp.objects.ErrorSpecIPv4;
import es.tid.rsvp.objects.RSVPObject;

/**
 * RSVP-ERROR-SPEC TLV (Type 21) [RFC8231] 
 * 
 * 
 * The RSVP-ERROR-SPEC TLV is an optional TLV for use in the LSP object
 *  to carry RSVP error information.  It includes the RSVP ERROR_SPEC or
 *  USER_ERROR_SPEC object ([RFC2205] and [RFC5284]), which were returned
 *  to the PCC from a downstream node.  If the setup of an LSP fails at a
 *  downstream node that returned an ERROR_SPEC to the PCC, the PCC
 *  SHOULD include in the PCRpt for this LSP the LSP-ERROR-CODE TLV with
 *  LSP Error Code = "RSVP signaling error" and the RSVP-ERROR-SPEC TLV
 *  with the relevant RSVP ERROR-SPEC or USER_ERROR_SPEC object.
 *
 *  TODO: UPDATE to include RFC5284
 *  
 *  @see <a href="https://tools.ietf.org/html/rfc8231#page-41">RFC 8231 page 41</a>
 *  
 *  @author jaume
 */

public class RSVPErrorSpecTLV extends PCEPTLV
{
	
	/*
 * The RSVP-ERROR-SPEC TLV is an optional TLV for use in the LSP object
   to carry RSVP error information.  It includes the RSVP ERROR_SPEC or
   USER_ERROR_SPEC object ([RFC2205] and [RFC5284]), which were returned
   to the PCC from a downstream node.  If the setup of an LSP fails at a
   downstream node that returned an ERROR_SPEC to the PCC, the PCC
   SHOULD include in the PCRpt for this LSP the LSP-ERROR-CODE TLV with
   LSP Error Code = "RSVP signaling error" and the RSVP-ERROR-SPEC TLV
   with the relevant RSVP ERROR-SPEC or USER_ERROR_SPEC object.

   The format of the RSVP-ERROR-SPEC TLV is shown in the following
   figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |           Type=21             |            Length (variable)  |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                                                               |
     +                RSVP ERROR_SPEC or USER_ERROR_SPEC Object      +
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                   Figure 16: RSVP-ERROR-SPEC TLV Format

   Type (16 bits): the type is 21.

   Length (16 bits): indicates the total length of the TLV in octets.
   The TLV MUST be zero-padded so that the TLV is 4-octet aligned.

   Value (variable): contains the RSVP ERROR_SPEC or USER_ERROR_SPEC
   object, as specified in [RFC2205] and [RFC5284], including the object
   header.
	 */
	
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
		log.debug("Encoding RSVPErrorSpecTLV TLV");
		errorSpecObject4.encode();
		int length = errorSpecObject4.getLength();
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.TotalTLVLength];
		this.encodeHeader();

		int offset=4;
		
		
		
		System.arraycopy(errorSpecObject4.getBytes(), 0, tlv_bytes, offset, errorSpecObject4.getLength());
		
	}
	
	public void decode() throws MalformedPCEPObjectException
	{
		log.debug("Decoding RSVPErrorSpecTLV TLV");
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

	public ErrorSpecIPv4 getErrorSpecObject4() {
		return errorSpecObject4;
	}

	public void setErrorSpecObject4(ErrorSpecIPv4 errorSpecObject4) {
		this.errorSpecObject4 = errorSpecObject4;
	}
	
	

}
