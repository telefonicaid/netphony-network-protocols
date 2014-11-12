package es.tid.pce.pcep.objects.tlvs;

/**
 * LABEL-REQUEST TLV draft-ietf-pce-gmpls-pcep-extensions-10 TBD. 
 * Encoding: TBD
 * TLV Type: TBD
 * 
 * 2.5.2.4. LABEL-REQUEST TLV


   The LABEL-REQUEST TLV indicates the switching capability and encoding
   type of the following label restriction list for the endpoint.  Its
   format and encoding is the same as described in [RFC3471] Section 3.1
   Generalized label request.  The LABEL-REQUEST TLV use TLV-Type=TBA.
   The Encoding Type indicates the encoding type, e.g., SONET/SDH/GigE
   etc., of the LSP with which the data is associated.  The Switching
   type indicates the type of switching that is being requested on the
   endpoint.  G-PID identifies the payload.  This TLV and the following
   one are introduced to satisfy requirement 13 for the endpoint.  It is
   not directly related to the TE-LSP label request, which is expressed
   by the SWITCH-LAYER object.

   On the path calculation request only the Tspec and switch layer need
   to be coherent, the endpoint labels could be different (supporting a
   different Tspec).  Hence the label restrictions include a Generalized
   label request in order to interpret the labels.  This TLV MAY be
   ignored, in which case a PCRep with NO-PATH should be responded, as
   described in Section 2.5.1.
   
 * @author ogondio
 *
 */

public class LabelRequestTLV extends PCEPTLV {

	public LabelRequestTLV(byte[] object_bytes, int offset) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}

}
