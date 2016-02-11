package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

/**
 * GMPLS-CAPABILITY TLV, see draft-ietf-pce-gmpls-pcep-extensions-10.
 * Encoding: standard
 * TLV Type: non-standard (14, as per draft-ietf-pce-gmpls-pcep-extensions-10 )
 * 
 * In addition to the IGP advertisement, a PCEP speaker should be able
   to discover the other peer GMPLS capabilities during the Open message
   exchange.  This capability is also useful to avoid misconfigurations.
   This document defines a new optional GMPLS-CAPABILITY TLV for use in
   the OPEN object to negotiate the GMPLS capability.  The inclusion of
   this TLV in the OPEN message indicates that the PCC/PCE support the
   PCEP extensions defined in the document.  A PCE that is able to
   support the GMPLS extensions defined in this document SHOULD include
   the GMPLS-CAPABILITY TLV on the OPEN message.  If the PCE does not
   include the GMPLS-CAPABILITY TLV in the OPEN message and PCC does
   include the TLV, it is RECOMMENDED that the PCC indicates a mismatch
   of capabilities.  Moreover , in case that the PCC does not receive
   the GMPLS-CAPABILITY TLV it is RECOMMENDED that the PCC does not make
   use of the objects and TLVs defined in this document.

   IANA has allocated value 14 from the "PCEP TLV Type Indicators" sub-
   registry, as documented in Section Section 5.3 ("New PCEP TLVs").
   The description is "GMPLS capable".  Its format is shown in the
   following figure.

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |               Type=14       |             Length              |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                             Flags                             |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   No Flags are defined in this document, they are reserved for future
   use.
 * 
 * @author ogondio
 *
 */
public class GMPLSCapabilityTLV extends PCEPTLV {

	
	public GMPLSCapabilityTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_GMPLS_CAPABILITY);
		
	}
	
	public GMPLSCapabilityTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	public void decode(){
		//No flags defined so far
	}

	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		this.tlv_bytes[4]=0x00;
		this.tlv_bytes[5]=0x00;
		this.tlv_bytes[6]=0x00;
		this.tlv_bytes[7]=0x00;
	
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}	

}
