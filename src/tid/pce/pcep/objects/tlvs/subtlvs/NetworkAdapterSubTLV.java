package tid.pce.pcep.objects.tlvs.subtlvs;

import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
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

Network Adapter (Figure 3.21) includes two field, Adapter Type and FullDuplex. 
The following values are defined for the adapter type:

•	0x01: Ethernet
•	0x02: Fibre
•	0x03: Infiniband
•	0x04: TokenRing

The FullDuplex field indicate if the connection is full duplex. The following values 
are defined:

•	0x01: full duplex
•	0x02: half duplex
•	0x03: undefined


         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Type (TBD)          |           Length              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |      Adapter Type             |  FullDuplex   |   Reserved    |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+



 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class NetworkAdapterSubTLV extends PCEPSubTLV {
	
	private int Adapter_Type, FullDupplex;
	
	public NetworkAdapterSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_NETWORK_ADAPTER);
		
	}
	
	public NetworkAdapterSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode Network Adapter SubTLV
	 */
	public void encode() {
		this.setSubTLVValueLength(4);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		System.arraycopy(Adapter_Type, 0, this.subtlv_bytes, 4, 2);
		this.subtlv_bytes[4]=(byte)(Adapter_Type>>8 & 0xFF);
		this.subtlv_bytes[5]=(byte)(Adapter_Type & 0xFF);
		this.subtlv_bytes[6]=(byte)(FullDupplex & 0xFF);
	}

	
	public void decode() {
		Adapter_Type = (int) ((this.subtlv_bytes[4]<<8 & 0xFF00) | (this.subtlv_bytes[5] & 0xFF));
		FullDupplex = (int)(this.subtlv_bytes[6] & 0xFF);
	}


	public void setAdapter_Type(int Adapter_Type) {
		this.Adapter_Type = Adapter_Type;
	}
	
	public int getAdapter_Type() {
		return Adapter_Type;
	}
	
	
	public void setFullDupplex(int FullDupplex) {
		this.FullDupplex= FullDupplex;
	}
	
	public int getFullDupplex() {
		return FullDupplex;
	}

}