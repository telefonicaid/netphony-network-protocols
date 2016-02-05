package es.tid.pce.pcep.objects.tlvs.subtlvs;

import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;


/**
 All PCEP SubTLVs have the following format:

   Type:   2 bytes
   Length: 2 bytes
   Value:  variable

   Power Info includes three fields, Power Source, Power Class and Regeneration. 
   The following values are defined for the Power Source field (16 bit):
   
•	0x01: Battery.
•	0x02: Solar Cells.
•	0x03: Electrical.
•	0x04: Fuel.

The Power Class field (8 bit) indicates the level of energy efficiency. The following
 values are defined:
 
•	0x01: A.
•	0x02: B.
•	0x03: C.
•	0x04: D.

The Regeneration field (8 bit) indicates if the it is possible to regenerate the
 power resource. The following values are defined:
 
•	0x01: renewable.
•	0x02: not renewable.
•	0x03: undefined.


         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Type (TBD)          |           Length              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |      Power Source             | Power Class   | Regeneration  |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class PowerInfoSubTLV extends PCEPSubTLV {
	
	private int PowerSource, PowerClass, Regeneration;
	
	public PowerInfoSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_POWER_INFO);
		
	}
	
	public PowerInfoSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedStorageSize TLV
	 */
	public void encode() {
		this.setSubTLVValueLength(4);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		this.subtlv_bytes[4]=(byte) (PowerSource>>8 & 0xFF);
		this.subtlv_bytes[5]=(byte) (PowerSource & 0xFF);
		this.subtlv_bytes[6]=(byte) (PowerClass>>8 & 0xFF);
		this.subtlv_bytes[7]=(byte) (Regeneration & 0xFF);
	}

	
	public void decode() {
		PowerSource = (int)((this.subtlv_bytes[4]<<8 & 0xFF00) | (this.subtlv_bytes[5] & 0x00FF));
		PowerClass = (int) this.subtlv_bytes[6] & 0xFF;
		Regeneration = (int) this.subtlv_bytes[7] & 0xFF;
	}

	
	public void setPowerSource(int powerSource) {
		this.PowerSource = powerSource;
	}
	
	public int getPowerSource() {
		return PowerSource;
	}
	
	public void setPowerClass(int powerClass) {
		this.PowerClass = powerClass;
	}
	
	public int getPowerClass() {
		return PowerClass;
	}
	
	public void setRegeneration(int regeneration) {
		this.Regeneration = regeneration;
	}
	
	public int getRegeneration() {
		return Regeneration;
	}
	
}