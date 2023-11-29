package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.objects.RSVPObjectParameters;

/**
 * LABEL-REQUEST TLV (Type 42). 
 *
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
	
	/**
	 * Lsp Encoding Type (8 bits)
	 * 
	 * Indicates the encoding of the LSP being requested.  The
	 * following shows permitted values and their meaning:
	 */
	
	private int lspEncodingType;
	
	/**
	 * Switching Type (8 bits)
	 * 
	 * 
	 * Indicates the type of switching that should be performed on a
	 * particular link.  This field is needed for links that advertise
	 * more than one type of switching capability.  This field should
	 * map to one of the values advertised for the corresponding link
	 * in the routing Switching Capability Descriptor, see [GMPLS-RTG].
	 */
	
	private int switchingType;
	
	/**
	 * GPID (16 bits)

		An identifier of the payload carried by an LSP, i.e., an
		identifier of the client layer of that LSP.  This is used by
		the nodes at the endpoints of the LSP, and in some cases by the
		penultimate hop.  Standard Ethertype values are used for packet
		and Ethernet LSPs; other values are:
		
	 */
	
	private int gpid;

	/*
	 *   The information carried in a Generalized Label Request is:
	
	    0                   1                   2                   3
	    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	   | LSP Enc. Type |Switching Type |             G-PID             |
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	 */

	public LabelRequestTLV() {
		this.setTLVType(ObjectParameters.PCEP_TLV_LABEL_REQUEST);
	}
	
	/**
	 * Label Request TLV constructor
	 * @param bytes the bytes where the object is present
	 * @param offset offset starting position of the object (in bytes)
	 */
	public LabelRequestTLV(byte[] bytes, int offset) {
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode the TLV
	 */
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();

		int currentIndex = 4;
		this.tlv_bytes[currentIndex] = (byte) ((lspEncodingType) & 0xFF);
		this.tlv_bytes[currentIndex+1] = (byte) ((switchingType) & 0xFF);
		this.tlv_bytes[currentIndex+2] = (byte)((gpid >> 8) & 0xFF);
		this.tlv_bytes[currentIndex+3] = (byte)((gpid) & 0xFF);
		
	}
	
	public void decode() {
		int offset=4;//start decoding after 4 bytes header
		this.lspEncodingType=ByteHandler.decode1byteInteger(this.tlv_bytes,offset);
		this.switchingType=ByteHandler.decode1byteInteger(this.tlv_bytes,offset+1);
		this.gpid=ByteHandler.decode2bytesInteger(this.tlv_bytes,offset+2);
	}

	public int getLspEncodingType() {
		return lspEncodingType;
	}

	public void setLspEncodingType(int lspEncodingType) {
		this.lspEncodingType = lspEncodingType;
	}

	public int getSwitchingType() {
		return switchingType;
	}

	public void setSwitchingType(int switchingType) {
		this.switchingType = switchingType;
	}

	public int getGpid() {
		return gpid;
	}

	public void setGpid(int gpid) {
		this.gpid = gpid;
	}

	@Override
	public String toString() {
		return "LabelRequestTLV [lspEncodingType=" + lspEncodingType + ", switchingType=" + switchingType + ", gpid="
				+ gpid + "]";
	}
	
	

}
