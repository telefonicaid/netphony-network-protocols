package es.tid.pce.pcep.objects.tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

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

GEYSERS IT Advertisement TLV

    0                   1                   2                   3
   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |           Type (TBD)          |           Length              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |							Adv ID							   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+								
   |					   Virtual IT Site ID					   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |    Adv Type  |  Adv Trigger  |			  reserved			   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

* 
* 
* @author Alejandro Tovar de Dueñas
*
*/

public class ITAdvertisementTLV extends PCEPTLV {
	
	private Inet4Address Adv_ID;
	private Inet4Address Virtual_IT_Site_ID;	// Unilateralmente estoy usando direcciones IP como identificadores
	private int Adv_Type, Adv_Trigger;
	
	
	public ITAdvertisementTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_TYPE_IT_ADV;		
	}

	public ITAdvertisementTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	public void encode() {
		this.setTLVValueLength(12);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		System.arraycopy(Adv_ID.getAddress(), 0, this.tlv_bytes, 4, 4);
		System.arraycopy(Virtual_IT_Site_ID.getAddress(), 0, this.tlv_bytes, 8, 4);
		this.tlv_bytes[12]=(byte)( (Adv_Type) & 0xFF );
		this.tlv_bytes[13]=(byte)( (Adv_Trigger) & 0xFF );
	}

	public void decode() throws MalformedPCEPObjectException {
		log.finest("Decoding IT Advertisement TLV");//FIXME: Cambiar a fine
		//Adv_ID=new byte[4];
		byte[] ip=new byte[4];
		System.arraycopy(this.tlv_bytes, 4, ip, 0, 4);
		try {
			this.Adv_ID=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] ip2=new byte[4];
		System.arraycopy(this.tlv_bytes, 8, ip2, 0, 4);
		try {
			this.Virtual_IT_Site_ID=(Inet4Address)Inet4Address.getByAddress(ip2);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		byte [] byteVirtual_IT_Site_ID=new byte[4];
//		System.arraycopy(this.tlv_bytes, 4, byteVirtual_IT_Site_ID, 0, 4);
//		try {
//			Virtual_IT_Site_ID= (Inet4Address)Inet4Address.getByAddress(byteVirtual_IT_Site_ID);
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Adv_Type=this.tlv_bytes[12];
		Adv_Trigger=this.tlv_bytes[13];
		
	}

	public void setAdv_ID(Inet4Address inet4Address) {
		this.Adv_ID = inet4Address;
	}
	
	public Inet4Address getAdv_ID() {
		return Adv_ID;
	}

	
	public void setVirtual_IT_Site_ID(Inet4Address Virtual_IT_Site_ID) {
		this.Virtual_IT_Site_ID = Virtual_IT_Site_ID;
	}
	
	public Inet4Address getVirtual_IT_Site_ID() {
		return Virtual_IT_Site_ID;
	}
		
	
	public void setAdv_Type(int Adv_Type) {
		this.Adv_Type = Adv_Type;
	}
	
	public int getAdv_Type() {
		return Adv_Type;
	}

	
	public void setAdv_Trigger(int Adv_Trigger) {
		this.Adv_Trigger = Adv_Trigger;
	}
	
	public int getAdv_Trigger() {
		return Adv_Trigger;
	}


}