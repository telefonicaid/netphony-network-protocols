package es.tid.pce.pcep.objects.tlvs;


import es.tid.pce.pcep.objects.ObjectParameters;

/**
 * EndPointsNSAP TLV, Experimental and Propietary from GEYSERS.
 * Encoding: propietary from GEYSERS
 * TLV Type: 999 (non-standard)
 * 
 All PCEP TLVs have the following format:

   Type:   2 bytes
   Length: 2 bytes
   Value:  variable

   A PCEP object TLV is comprised of 2 bytes for the type, 2 bytes
   specifying the TLV length, and a value field.

   The Length field defines the length of the value portion in bytes.
   The TLV is padded to 4-bytes alignment; padding is not included in
   the Length field (so a 3-byte value would have a length of 3, but the
   total size of the TLV would be 8 bytes).

   Unrecognized TLVs MUST be ignored.

   IANA management of the PCEP Object TLV type identifier codespace is
   described in Section 9.

In GEYSERS,

 * PCEP END-POINTS-ASSISTEDUNICAST-IPv4 object Assisted unicast from GEYSERS
 * 
The path computation request for the enhanced connection paradigms 
defined in D4.1 section 4.2.2 are supported extending the END-POINTS 
object (Object-Class=4). The following new Object-Types are defined 
for this object:

	Object-Type: TBD  Assisted unicast IPv4
	Object-Type: TBD  Assisted unicast IPv6
	Object-Type: TBD  Assisted unicast NSAP

The format of the END-POINTS-ASSISTEDUNICAST-IPv4 object body for assisted NSAP is the 
following:
 

  0                   1                   2                   3
  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
  |                          						              |
  //                     End-point pair 1                        //
  |                     							              |
  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
  |                          						              |
  //                          ...                                //
  |                     							              |
  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
  |                          						              |
  //                     End-point pair N                        //
  |                     							              |
  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

where a single End-point pair block has the following format:

    0                   1                   2                   3
   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     Source IPv4 address                       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Destination IPv4 address                     |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
				END-POINT pair block Format for IPv4

 * 
 * 
 * @author Alejandro Tovar de Duenas
 *
 */
public class EndPointsNSAPTLV extends PCEPTLV {
	
	/**
	 * Source IPv6 address
	 */
	public byte[] sourceNSAP;
	/**
	 * Destination IPv6 address
	 */
	public byte[] destNSAP;
	
	
	public EndPointsNSAPTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_ENDPOINTS_NSAP);
		
	}
	
	public EndPointsNSAPTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedStorageSize TLV
	 */
	public void encode() {
		this.setTLVValueLength(40);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		System.arraycopy(sourceNSAP,0, this.tlv_bytes, 4, 20);
		System.arraycopy(destNSAP,0, this.tlv_bytes, 24, 20);
	}

	
	public void decode(){
		log.debug("Decoding NSAP Addreess");
		System.arraycopy(this.tlv_bytes,4, sourceNSAP, 0, 20);
		System.arraycopy(this.tlv_bytes,24, destNSAP, 0, 20); 	 
	}

	public byte[] getSourceIP() {
		return sourceNSAP;
	}

	public void setSourceNSAP(byte[] sourceNSAP) {
		this.sourceNSAP = sourceNSAP;
	}

	public byte[] getDestNSAP() {
		return destNSAP;
	}

	public void setDestNSAP(byte[] destNSAP) {
		this.destNSAP = destNSAP;
	}
		
	public String toString(){
		return "Source NSAP: "+sourceNSAP+" Destination NSAP: "+destNSAP;
	}

}
