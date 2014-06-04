package tid.pce.pcep.objects.tlvs.subtlvs;


import java.util.LinkedList;

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

In GEYSERS, when the End-point describes a CPU, it includes
 the description of the characteristics of the requested storage in a
 set of TLVs.
 Requested CPU includes two 8-bit fields indicating the minimum and
 maximum number of CPUs required, a 8-bit field indicating the required
 type of CPU architecture and a variable number of pair of 32-bit
 fields describing the minimum and maximum speed requested for each CPU.
      0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |           Type (TBD)          |           Length              |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |  Elem No Min  |   Elem No Max |     Type      |   Reserved    |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                       Min Speed CPU 1                         |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                       Max Speed CPU 1                         |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                              ...                              |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                              ...                              |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                       Min Speed CPU N                         |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                       Max Speed CPU N                         |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 
 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class RequestedCPUsSubTLV extends PCEPSubTLV {
	
	private byte minCPUs, maxCPUs, processorType;
	byte[] minSpeedCPU, maxSpeedCPU;
	private LinkedList<byte[]> minSpeedCPUList;
	private LinkedList<byte[]> maxSpeedCPUList;
	
	public RequestedCPUsSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_REQUESTED_CPU);
		
	}
	
	public RequestedCPUsSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedCPUs TLV
	 */
	public void encode() {
		int len=4;//The four fixed bytes-including the reserved bites
		for (int k=0; k<minSpeedCPUList.size();k=k+1){
			len=len+8;
		}
		this.setSubTLVValueLength(len);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		this.subtlv_bytes[4]=minCPUs;
		this.subtlv_bytes[5]=maxCPUs;
		this.subtlv_bytes[6]=processorType;
		int pos=8;
		for (int k=0 ; k<minSpeedCPUList.size(); k=k+1) {					
			System.arraycopy(minSpeedCPUList.get(k),0, this.subtlv_bytes, pos, 4);
			pos=pos+4;
			System.arraycopy(maxSpeedCPUList.get(k),0, this.subtlv_bytes, pos, 4);
			pos=pos+4;
		}
	}
	public void decode() {
		minCPUs=this.subtlv_bytes[4];
		maxCPUs=this.subtlv_bytes[5];
		processorType=this.subtlv_bytes[6];
		int offset=8;//Position of the next Speed element
		boolean fin=false;
		minSpeedCPU=new byte[4];
		maxSpeedCPU=new byte[4];
		while (!fin) {
			System.arraycopy(this.subtlv_bytes,offset, minSpeedCPU, 0, 4);
			offset=offset+4;
			System.arraycopy(this.subtlv_bytes,offset, maxSpeedCPU, 0, 4);
			offset=offset+4;
			minSpeedCPUList.add(minSpeedCPU);
			maxSpeedCPUList.add(maxSpeedCPU);
			if (offset>=this.getTotalSubTLVLength()){
				System.out.println("No more Requested CPUs");
				fin=true;
			}
		}
	}
	
	public void setminCPUs(byte minCPUs) {
		this.minCPUs = minCPUs;
	}
	
	public byte isminCPUs() {
		return minCPUs;
	}

	public void setmasCPUs(byte maxCPUs) {
		this.maxCPUs = maxCPUs;
	}
	
	public byte ismaxCPUs() {
		return maxCPUs;
	}
	
	public void setprocessorType(byte processorType) {
		this.processorType = processorType;
	}
	
	public byte isprocessorType() {
		return processorType;
	}
	
	public void setminSpeedCPUList(LinkedList<byte[]> minSpeedCPUList) {
		this.minSpeedCPUList = minSpeedCPUList;
	}
	
	public LinkedList<byte []> isminSpeedCPUList() {
		return minSpeedCPUList;
	}
	
	public void setmaxSpeedCPUList(LinkedList<byte[]> maxSpeedCPUList) {
		this.maxSpeedCPUList = maxSpeedCPUList;
	}
	
	public LinkedList<byte []> ismaxSpeedCPUList() {
		return maxSpeedCPUList;
	}
}