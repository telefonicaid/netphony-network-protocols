package es.tid.pce.pcep.objects.tlvs.subtlvs;

import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;


/**
 All PCEP SubTLVs have the following format:

   Type:   2 bytes
   Length: 2 bytes
   Value:  variable

 Processor (Figure 3.25) includes four fields: Element Number, Type, Speed, 
 and Load. The Element Number field (8 bit) indicates the number of available 
 cores in the processor. The Speed and Load fields, both on 32 bits, indicate 
 the available speed for the processor (in GHz) and the current overall load. 
 The Type field (8 bit) indicates the CPU architecture. The following values 
 are defined:
 
	0x01: SPARC.
	0x02: POWERPC.
	0x03: X86.
	0x04: X86_32.
	0x05: X86_64.
	0x06: PARISC.
	0x07: MIPS.
	0x08: IA64.
	0x09: ARM.
	0xFF: other.

         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Type (TBD)          |           Length              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | Element No    |    Type       |          Reserved             |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |                             Load                              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |                             Speed                             |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * 
 * 
 * @author Alejandro Tovar de Due�as
 *
 */
public class ProcessorSubTLV extends PCEPSubTLV {
	
	private int elementNo, type, load, speed;
	
	public ProcessorSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_PROCESSOR);
		
	}
	
	public ProcessorSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedStorageSize TLV
	 */
	public void encode() {
		this.setSubTLVValueLength(12);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		this.subtlv_bytes[4]=(byte) (elementNo& 0xFF);
		this.subtlv_bytes[5]=(byte) (type& 0xFF);
		this.subtlv_bytes[8]=(byte) (load>>24 & 0xFF);
		this.subtlv_bytes[9]=(byte) (load>>16 & 0xFF);
		this.subtlv_bytes[10]=(byte) (load>>8 & 0xFF);
		this.subtlv_bytes[11]=(byte) (load & 0xFF);
		this.subtlv_bytes[12]=(byte) (speed>>24 & 0xFF);
		this.subtlv_bytes[13]=(byte) (speed>>16 & 0xFF);
		this.subtlv_bytes[14]=(byte) (speed>>8 & 0xFF);
		this.subtlv_bytes[15]=(byte) (speed & 0xFF);
	}

	
	public void decode() {
		elementNo = (int)(this.subtlv_bytes[4] & 0xFF);
		type = (int)(this.subtlv_bytes[5] & 0xFF);
		load = (int)((this.subtlv_bytes[8] & 0xFF000000) | (this.subtlv_bytes[9] & 0x00FF0000) | (this.subtlv_bytes[10] & 0x0000FF00) | (this.subtlv_bytes[11] & 0x000000FF));
		speed = (int)((this.subtlv_bytes[12] & 0xFF000000) | (this.subtlv_bytes[13] & 0x00FF0000) | (this.subtlv_bytes[14] & 0x0000FF00) | (this.subtlv_bytes[15] & 0x000000FF));
	}

	
	public void setElementNo(int ElementNo) {
		this.elementNo = ElementNo;
	}
	
	public int getElementNo() {
		return elementNo;
	}

	public void setLoad(int Load) {
		this.load = Load;
	}
	
	public int getLoad() {
		return load;
	}
	
	public void setSpeed(int Speed) {
		this.speed = Speed;
	}
	
	public int getSpeed() {
		return speed;
	}

	
}