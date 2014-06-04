package tid.pce.pcep.objects.tlvs.subtlvs;

import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;

/**
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
Location: as per IETF RFC3825, latitude and longitude can be represented in 
fixed-point 2s-complement binary degrees. The integer part of the fields
is 9 bits long, while the fractional part is 25 bits long. The length of each
field is 40 bits, including 6 bits of resolution.
    
         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Type (TBD)          |           Length              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |   LaRes   |               Latitude                            |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | Lat. (cont’d) |   Reserved    |  LoRes    |  Longitude        |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Longitude (cont’d)                  |   Reserved    |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


* 
* 
* @author Alejandro Tovar de Dueñas
*
*/
public class LocationSubTLV extends PCEPSubTLV {
	
	private int LaRes, LoRes;
	private byte[] Latitude, Longitude;
	
		
	public LocationSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_LOCATION);
		
	}
	
	public LocationSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode Application TLV
	 */
	public void encode() {
		this.setSubTLVValueLength(12);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		this.subtlv_bytes[4]=(byte)( ( (LaRes<<2) & 0xFC) | ( (Latitude[0]>>6) & 0x03));
		this.subtlv_bytes[5]=(byte)( ( (Latitude[0]<<2) & 0xFC) | ( (Latitude[1]>>6) & 0x03));
		this.subtlv_bytes[6]=(byte)( ( (Latitude[1]<<2) & 0xFC) | ( (Latitude[2]>>6) & 0x03));
		this.subtlv_bytes[7]=(byte)( ( (Latitude[2]<<2) & 0xFC) | ( (Latitude[3]>>6) & 0x03));
		this.subtlv_bytes[8]=(byte)( ( (Latitude[3]<<2) & 0xFC) | ( (Latitude[4]>>6) & 0x03));
		this.subtlv_bytes[9]=(byte) 0xFF;
		this.subtlv_bytes[10]=(byte)( ( (LoRes<<2) & 0xFC) | ( (Longitude[0]>>6) & 0x03));
		this.subtlv_bytes[11]=(byte)( ( (Longitude[0]<<2) & 0xFC) | ( (Longitude[1]>>6) & 0x03));
		this.subtlv_bytes[12]=(byte)( ( (Longitude[1]<<2) & 0xFC) | ( (Longitude[2]>>6) & 0x03));
		this.subtlv_bytes[13]=(byte)( ( (Longitude[2]<<2) & 0xFC) | ( (Longitude[3]>>6) & 0x03));
		this.subtlv_bytes[14]=(byte)( ( (Longitude[3]<<2) & 0xFC) | ( (Longitude[2]>>6) & 0x03));
		this.subtlv_bytes[15]=(byte) 0xFF;
		
	}

	
	public void decode() {
		LaRes= (int)( ( this.subtlv_bytes[4]>>2) & 0x3F);
		Latitude[0]= (byte)( ( (this.subtlv_bytes[4]<<6) & 0xC0) | (this.subtlv_bytes[5]>>2) & 0x3F);
		Latitude[1]= (byte)( ( (this.subtlv_bytes[5]<<6) & 0xC0) | (this.subtlv_bytes[6]>>2) & 0x3F);
		Latitude[2]= (byte)( ( (this.subtlv_bytes[6]<<6) & 0xC0) | (this.subtlv_bytes[7]>>2) & 0x3F);
		Latitude[3]= (byte)( ( (this.subtlv_bytes[7]<<6) & 0xC0) | (this.subtlv_bytes[8]>>2) & 0x3F);
		Latitude[4]= (byte)( (this.subtlv_bytes[8]<<6) & 0xC0);
		
		LoRes= (int)( ( this.subtlv_bytes[10]>>2) & 0x3F);
		Latitude[0]= (byte)( ( (this.subtlv_bytes[10]<<6) & 0xC0) | (this.subtlv_bytes[11]>>2) & 0x3F);
		Latitude[1]= (byte)( ( (this.subtlv_bytes[11]<<6) & 0xC0) | (this.subtlv_bytes[12]>>2) & 0x3F);
		Latitude[2]= (byte)( ( (this.subtlv_bytes[12]<<6) & 0xC0) | (this.subtlv_bytes[13]>>2) & 0x3F);
		Latitude[3]= (byte)( ( (this.subtlv_bytes[13]<<6) & 0xC0) | (this.subtlv_bytes[14]>>2) & 0x3F);
		Latitude[4]= (byte)( (this.subtlv_bytes[14]<<6) & 0xC0);
		
	}


	public void setLaRes(int LaRes) {
		this.LaRes = LaRes;
	}
	
	public int getLaRes() {
		return LaRes;
	}
	
	
	public void setLoRes(int LoRes) {
		this.LoRes = LoRes;
	}
	
	public int getLoRes() {
		return LoRes;
	}
	
	
	public void setLatitude(byte[] lat) { //QUIZAS haya q cuadrar desplazamiento de Bits!
		this.Latitude = lat;
	}
	
	public byte[] getLatitude() { //QUIZAS haya q cuadrar desplazamiento de Bits!
		return Latitude;
	}
	
	
	public void setLongitude(byte[] lon) { //QUIZAS haya q cuadrar desplazamiento de Bits!
		this.Longitude = lon;
	}
	
	public byte[] getLongitude() {  //QUIZAS haya q cuadrar desplazamiento de Bits!
		return Longitude;
	}
	
}