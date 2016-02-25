package es.tid.pce.pcep.objects;

/**
 *  REQ-ADAP-CAP Object

   The REQ-ADAP-CAP object is optional and is used to specify a
   requested adaptation capability for both ends of the lower layer LSP.
   The REQ-ADAP-CAP object is used in a PCReq message for inter-PCE
   communication, where the PCE that is responsible for computing higher
   layer paths acts as a PCC to request a path computation from a PCE
   that is responsible for computing lower layer paths.


E. Oki                    Expires July 2013                  [Page 8]

 
draft-ietf-pce-inter-layer-ext-08.txt                     January 2014


   The REQ-ADAP-CAP object is used in a PCRep message in case of
   unsuccessful path computation (in this case, the PCRep message also
   contains a NO-PATH object, and the REQ-ADAP-CAP object is used to
   indicate the set of constraints that could not be satisfied).

   The REQ-ADAP-CAP object MAY be used in a PCReq message in a mono-
   layer network to specify a requested adaptation capability for both
   ends of the LSP. In this case, it MAY be carried without INTER-LAYER
   Object.

   REQ-ADAP-CAP Object-Class is to be assigned by IANA (recommended
   value=20)

   REQ-ADAP-CAP Object-Type is to be assigned by IANA (recommended
   value=1)

   The format of the REQ-ADAP-CAP object body is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | Switching Cap |   Encoding    | Reserved                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   The format is based on [RFC6001] and has equivalent semantics as the
   IACD Upper SC and Lower SC.

   Switching Capability (8 bits): see [RFC4203] for a description of
   parameters.

   Encoding (8 bits): see [RFC3471] for a description of parameters.

   A PCC may want to specify a Switching Capability, but not an Encoding.
   In this case, the Encoding MUST be set zero.
 * @author ogondio
 *
 */
public class ReqAdapCap extends PCEPObject {
	
	private int switchingCap;
	private int encoding;

	public ReqAdapCap() {
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_REQ_ADAP_CAP);
		
	}
	
	public ReqAdapCap (byte [] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
		decode();
	}
	
	public void encode() {
		this.setObjectLength(8);
		this.setObject_bytes(new byte[this.getLength()]);
		this.encode_header();
		this.object_bytes[4]=(byte)(switchingCap&0xFF);
		this.object_bytes[5]=(byte)(encoding&0xFF);

	}
	
	
	public void decode() throws MalformedPCEPObjectException {
		this.switchingCap=this.object_bytes[4]&0XFF;
		this.encoding=this.object_bytes[5]&0XFF;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + encoding;
		result = prime * result + switchingCap;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReqAdapCap other = (ReqAdapCap) obj;
		if (encoding != other.encoding)
			return false;
		if (switchingCap != other.switchingCap)
			return false;
		return true;
	}

	public int getSwitchingCap() {
		return switchingCap;
	}

	public void setSwitchingCap(int switchingCap) {
		this.switchingCap = switchingCap;
	}

	public int getEncoding() {
		return encoding;
	}

	public void setEncoding(int encoding) {
		this.encoding = encoding;
	}
	


}
