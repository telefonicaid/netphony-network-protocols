package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

public class ExtendedAssociationIDTLVProv extends PCEPTLV{

	/*
	 * 

   The Extended Association ID TLV is an optional TLV for use in the
   ASSOCIATION object.  The meaning and usage of the Extended
   Association ID TLV are as per Section 4 of [RFC6780].

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |           Type = 31           |       Length = 8 or 20        |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                             Color                             |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      ~                           Endpoint                            ~
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

              Figure 6: The Extended Association ID TLV Format

   Type:  31

   Length:  Variable.
   
   Color: SR Policy color value.

   Endpoint: can be either IPv4 or IPv6,
	 */
	
	protected int color;
	protected int endpoint;
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(int endpoint) {
		this.endpoint = endpoint;
	}
	
	public ExtendedAssociationIDTLVProv()
	{
		this.setTLVType(ObjectParameters.PCEP_TLV_EXTENDED_ASSOCIATION_ID);
		
	}
	
	public ExtendedAssociationIDTLVProv(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		super(bytes,offset);
		decode();
	}
	
	private void decode() {
		log.debug("Decoding SymbolicPathName TLV");
		int offset=4;//Position of the next subobject
		//Color
		for (int k = 0; k < 4; k++) {
			color = (color << 8) | (this.tlv_bytes[k+offset] & 0xff);
		}
		offset=offset+4;
		//Endpoint
		for (int k = 0; k < 4; k++) {
			endpoint = (endpoint << 8) | (this.tlv_bytes[k+offset] & 0xff);
		}
	}

	@Override
	public void encode() {
		log.debug("Encoding ExtendedAssociationIDTLV TLV");
		
		int offset=0;
		
		//COLOR
		this.tlv_bytes[offset]=(byte)(color >>> 24);
		this.tlv_bytes[offset+1]=(byte)(color >> 16 & 0xFF);
		this.tlv_bytes[offset+2]=(byte)(color >> 8  & 0xFF);
		this.tlv_bytes[offset+3]=(byte)(color & 0xFF);
		
		//ENDPOINT
		offset=offset+4;
		this.tlv_bytes[offset]=(byte)(endpoint >>> 24);
		this.tlv_bytes[offset+1]=(byte)(endpoint >> 16 & 0xFF);
		this.tlv_bytes[offset+2]=(byte)(endpoint >> 8  & 0xFF);
		this.tlv_bytes[offset+3]=(byte)(endpoint & 0xFF);
		offset=offset+4;
		encodeHeader();
		
	}

	
}
