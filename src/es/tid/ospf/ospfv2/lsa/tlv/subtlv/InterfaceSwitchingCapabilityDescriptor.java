package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import es.tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields.*;


/**
 * Interface Switching Capability Descriptor, 
 * see <a href="http://www.ietf.org/rfc/rfc4203.txt">RFC 4203</a> (OSPF Extensions for GMPLS).

 1.4. Interface Switching Capability Descriptor

   <p>The Interface Switching Capability Descriptor is a sub-TLV (of type
   15) of the Link TLV.  The length is the length of value field in
   octets.  The format of the value field is as shown below</p>
   <pre>
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | Switching Cap |   Encoding    |           Reserved            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Max LSP Bandwidth at priority 0              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Max LSP Bandwidth at priority 1              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Max LSP Bandwidth at priority 2              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Max LSP Bandwidth at priority 3              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Max LSP Bandwidth at priority 4              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Max LSP Bandwidth at priority 5              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Max LSP Bandwidth at priority 6              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Max LSP Bandwidth at priority 7              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |        Switching Capability-specific information              |
   |                  (variable)                                   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The Switching Capability (Switching Cap) field contains one of the
   following values:

      1     Packet-Switch Capable-1 (PSC-1)
      2     Packet-Switch Capable-2 (PSC-2)
      3     Packet-Switch Capable-3 (PSC-3)
      4     Packet-Switch Capable-4 (PSC-4)
      51    Layer-2 Switch Capable  (L2SC)
      100   Time-Division-Multiplex Capable (TDM)
      150   Lambda-Switch Capable   (LSC)
      200   Fiber-Switch Capable    (FSC)

   The Encoding field contains one of the values specified in Section
   3.1.1 of [GMPLS-SIG].

   Maximum LSP Bandwidth is encoded as a list of eight 4 octet fields in
   the IEEE floating point format [IEEE], with priority 0 first and
   priority 7 last.  The units are bytes (not bits!) per second.

   The content of the Switching Capability specific information field
   depends on the value of the Switching Capability field.

   When the Switching Capability field is PSC-1, PSC-2, PSC-3, or PSC-4,
   the Switching Capability specific information field includes Minimum
   LSP Bandwidth, Interface MTU, and padding.
<pre>
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Minimum LSP Bandwidth                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |           Interface MTU       |            Padding            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
</pre>
   The Minimum LSP Bandwidth is encoded in a 4 octets field in the IEEE
   floating point format.  The units are bytes (not bits!) per second.
   The Interface MTU is encoded as a 2 octets integer.  The padding is 2
   octets, and is used to make the Interface Switching Capability
   Descriptor sub-TLV 32-bits aligned.  It SHOULD be set to zero by the
   sender and SHOULD be ignored by the receiver.

   When the Switching Capability field is L2SC, there is no Switching
   Capability specific information field present.

   When the Switching Capability field is TDM, the Switching Capability
   specific information field includes Minimum LSP Bandwidth, an
   indication whether the interface supports Standard or Arbitrary
   SONET/SDH, and padding.
<pre>
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Minimum LSP Bandwidth                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Indication  |                 Padding                       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
</pre>
   The Minimum LSP Bandwidth is encoded in a 4 octets field in the IEEE
   floating point format.  The units are bytes (not bits!) per second.
   The indication whether the interface supports Standard or Arbitrary
   SONET/SDH is encoded as 1 octet.  The value of this octet is 0 if the
   interface supports Standard SONET/SDH, and 1 if the interface
   supports Arbitrary SONET/SDH.  The padding is 3 octets, and is used
   to make the Interface Switching Capability Descriptor sub-TLV 32-bits
   aligned.  It SHOULD be set to zero by the sender and SHOULD be
   ignored by the receiver.

   When the Switching Capability field is LSC, there is no Switching
   Capability specific information field present.

   To support interfaces that have more than one Interface Switching
   Capability Descriptor (see Section "Interface Switching Capability
   Descriptor" of [GMPLS-ROUTING]) the Interface Switching Capability
   Descriptor sub-TLV may occur more than once within the Link TLV.
</pre>
 */

public class InterfaceSwitchingCapabilityDescriptor extends OSPFSubTLV {

	/**
	 * Switching Capability
	 */
	private int switchingCap;
	
	/**
	 * Encoding Type
	 */
	private int encoding;
	
	/**
	 * Max LSP Bandwidth at priority 0-7 
	 */
	private long[] max_LSP_BW=new long[8];
		
	/**
	 * Switching Capability-specific information
	 */
	private SwitchingCapabilitySpecificInformation switchingCapabilitySpecificInformation;
	
	/**
	 * Default Constructor			
	 */
	public InterfaceSwitchingCapabilityDescriptor(){
		this.setTLVType(OSPFSubTLVTypes.InterfaceSwitchingCapabilityDescriptor);
	}
		
	/**
	 * Contruct from a byte array and a given offset
	 * @param bytes
	 * @param offset
	 * @throws MalformedOSPFSubTLVException
	 */
	public InterfaceSwitchingCapabilityDescriptor(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		decode();
	}
	
	/**
	 * Encode a InterfaceSwitchingCapabilityDescriptor
	 */
	public void encode() {
		int length=36;
		if (switchingCapabilitySpecificInformation!=null){
			switchingCapabilitySpecificInformation.encode();
			length=length+switchingCapabilitySpecificInformation.getLength();
		}
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset = 4;
		//Encode switchingCap
		this.tlv_bytes[offset] = (byte)(switchingCap & 0xFF);
		//Encode Encoding Type
		this.tlv_bytes[offset+1] =( byte)(encoding & 0xFF);
		offset = offset + 4;
		for(int i=0;i<8;++i){
			this.tlv_bytes[offset] = (byte)(max_LSP_BW[i] >> 24 & 0xff);
			this.tlv_bytes[offset + 1] = (byte)(max_LSP_BW[i] >> 16 & 0xff);
			this.tlv_bytes[offset + 2] = (byte)(max_LSP_BW[i] >> 8 & 0xff);
			this.tlv_bytes[offset + 3] = (byte)(max_LSP_BW[i] & 0xff);
			offset = offset + 4;
		}
		if (this.switchingCapabilitySpecificInformation!=null){
			System.arraycopy(this.tlv_bytes, offset , switchingCapabilitySpecificInformation.getBytes(), 0, switchingCapabilitySpecificInformation.getLength());
		}		
	}
	
	/**
	 * Decode the Interface Switching Capability Descriptor
	 * @throws MalformedOSPFSubTLVException
	 */
	protected void decode() throws MalformedOSPFSubTLVException{
		int offset=4;
		this.switchingCap = this.tlv_bytes[offset]&0xFF;
		this.encoding = this.tlv_bytes[offset+1]&0xFF;
		offset = offset + 4;
		for (int i=0;i<7;++i){
			this.max_LSP_BW[i] = (long) ((((this.tlv_bytes[offset]&0xFF) << 24) & 0xFF000000) | (((tlv_bytes[offset+1]&0xFF)<<16)& 0xFF0000) | (((tlv_bytes[offset+2]&0xFF)<<8)& 0xFF00) |  (tlv_bytes[offset+3]&0xFF) );
			offset = offset + 4;
		}

		if((this.switchingCap == SwitchingCapabilityType.PACKET_SWITCH_CAPABLE_1) || (this.switchingCap == SwitchingCapabilityType.PACKET_SWITCH_CAPABLE_2) || (this.switchingCap == SwitchingCapabilityType.PACKET_SWITCH_CAPABLE_3) || (this.switchingCap == SwitchingCapabilityType.PACKET_SWITCH_CAPABLE_4)){		// PSC 1,2,3 or 4
			this.switchingCapabilitySpecificInformation = new SwitchingCapabilitySpecificInformationPSC(this.tlv_bytes,offset);		
		}
		else if(this.switchingCap == SwitchingCapabilityType.TIME_DIVISION_SWITCH_CAPABLE){	// TDM
			this.switchingCapabilitySpecificInformation = new SwitchingCapabilitySpecificInformationTDM(this.tlv_bytes,offset);
		}

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

	public long[] getMax_LSP_BW() {
		return max_LSP_BW;
	}

	public void setMax_LSP_BW(long []max_LSP_BW) {
		this.max_LSP_BW = max_LSP_BW;
	}

	public long getMax_LSP_BW(int i) {
		return max_LSP_BW[i];
	}

	public void setMax_LSP_BW(int i, long maxLSPBW) {
		this.max_LSP_BW[i] = maxLSPBW;
	}

	public SwitchingCapabilitySpecificInformation getSwitchingCapabilitySpecificInformation() {
		return switchingCapabilitySpecificInformation;
	}

	public void setSwitchingCapabilitySpecificInformation(
			SwitchingCapabilitySpecificInformation switchingCapabilitySpecificInformation) {
		this.switchingCapabilitySpecificInformation = switchingCapabilitySpecificInformation;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (switchingCap != ((InterfaceSwitchingCapabilityDescriptor)obj).getSwitchingCap())
			return false;
		else if (encoding != ((InterfaceSwitchingCapabilityDescriptor)obj).getEncoding())
			return false;
		
		return switchingCapabilitySpecificInformation.equals(((InterfaceSwitchingCapabilityDescriptor)obj).getSwitchingCapabilitySpecificInformation());
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer(100);
		sb.append("ISCD: swCap: ");
		if (switchingCap==150){
			sb.append("150 (LAMBDA)");
		}else {
			sb.append(switchingCap);
		}
		return sb.toString();
	}
	
}
