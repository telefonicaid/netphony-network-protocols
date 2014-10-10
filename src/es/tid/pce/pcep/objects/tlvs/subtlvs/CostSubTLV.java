package es.tid.pce.pcep.objects.tlvs.subtlvs;

import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;


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
Cost includes two fields, unitary price on 32 bits and usage unit on 16 bit. The 
following values are defined for usage units:

•	0x01: GB per day.
•	0x02: GB per week.
•	0x03: GB per month.
•	0x04: usage time – per minute.
•	0x05: usage time – per hour.
•	0x06: usage time – per week.
•	0x07: usage time – per month.

         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Type (TBD)          |           Length              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |                         Unitary Price                         |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |   Usage Unit  |                    Reserved                   |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class CostSubTLV extends PCEPSubTLV {
	
	private byte[] unitaryPrice;
	private byte[] usageUnit;
	
	public CostSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_COST);
		
	}
	
	public CostSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedStorageSize TLV
	 */
	public void encode() {
		this.setSubTLVValueLength(8);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		System.arraycopy(unitaryPrice, 0, this.subtlv_bytes, 4, 4);
		System.arraycopy(usageUnit, 0, this.subtlv_bytes, 8, 2);
		
		log.info("Encoding UnitaryPrice: "+(unitaryPrice).toString());
	}

	
	public void decode() {
		this.unitaryPrice=new byte[4];
		this.usageUnit=new byte[2];
		log.info("Tamaño del SubTLV:"+String.valueOf(this.getSubTLVValueLength()));
//		System.arraycopy(this.subtlv_bytes, 4, prueba, 0, 4);
//		this.usageUnit= prueba;
		System.arraycopy(this.subtlv_bytes, 4, this.unitaryPrice, 0, 4);
//		this.unitaryPrice[0]=this.subtlv_bytes[4];
//		this.unitaryPrice[1]=this.subtlv_bytes[5];
//		this.unitaryPrice[2]=this.subtlv_bytes[6];
//		this.unitaryPrice[3]=this.subtlv_bytes[7];
		//this.usageUnit[0]=this.subtlv_bytes[8];
		System.arraycopy(this.subtlv_bytes, 8, this.usageUnit, 0, 2);
		log.info("Decoding UnitaryPrice"+(this.unitaryPrice).toString());
	}


	public void setUnitaryPrice(byte[] UnitaryPrice) {
		this.unitaryPrice = UnitaryPrice;
	}
	
	public byte[] getUnitaryPrice() {
		return unitaryPrice;
	}
	
	
	public void setUsageUnit(byte[] UsageUnit) {
		this.usageUnit = UsageUnit;
	}
	
	public byte[] getUsageUnit() {
		return usageUnit;
	}

}