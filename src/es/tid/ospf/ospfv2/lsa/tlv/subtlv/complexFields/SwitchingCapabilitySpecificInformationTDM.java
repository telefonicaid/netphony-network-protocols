package es.tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields;

/**
 * Switching Capability SpecificInformation for TDM.
 * 
Kompella & Rekhter          Standards Track                     [Page 4]

 
	RFC 4203                OSPF Extensions in MPLS             October 2005
 
   When the Switching Capability field is TDM, the Switching Capability
   specific information field includes Minimum LSP Bandwidth, an
   indication whether the interface supports Standard or Arbitrary
   SONET/SDH, and padding.

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Minimum LSP Bandwidth                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Indication  |                 Padding                       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The Minimum LSP Bandwidth is encoded in a 4 octets field in the IEEE
   floating point format.  The units are bytes (not bits!) per second.
   The indication whether the interface supports Standard or Arbitrary
   SONET/SDH is encoded as 1 octet.  The value of this octet is 0 if the
   interface supports Standard SONET/SDH, and 1 if the interface
   supports Arbitrary SONET/SDH.  The padding is 3 octets, and is used
   to make the Interface Switching Capability Descriptor sub-TLV 32-bits
   aligned.  It SHOULD be set to zero by the sender and SHOULD be
   ignored by the receiver.
 * 
 */

public class SwitchingCapabilitySpecificInformationTDM extends SwitchingCapabilitySpecificInformation {
	
	/**
	 * Minimum LSP Bandwidth
	 */
	private long minimumLSPBandwidth;
	
	/**
	 * 
	 */
	private int indication;
	
	/**
	 * 
	 */
	public SwitchingCapabilitySpecificInformationTDM(){
		
	}

	/**
	 * 
	 * @param bytes
	 * @param offset
	 */
	public SwitchingCapabilitySpecificInformationTDM(byte[] bytes, int offset) {
		this.length=8;
		this.bytes=new byte[this.length];
		System.arraycopy(bytes, offset, this.bytes, 0, this.length);
		decode();
	}
	
	/**
	 * 
	 */
	public void encode(){
		
		int offset = 0;
		
		this.bytes[offset]=(byte)(minimumLSPBandwidth >>> 24);
		this.bytes[offset+1]=(byte)(minimumLSPBandwidth >> 16 & 0xff);
		this.bytes[offset+2]=(byte)(minimumLSPBandwidth >> 8 & 0xff);
		this.bytes[offset+3]=(byte)(minimumLSPBandwidth & 0xff);
		
		offset = offset + 4;
		
		this.bytes[offset]=(byte)(indication & 0xff);
		
	}
	
	/**
	 * 
	 */
	public void decode(){
		this.minimumLSPBandwidth = ( (long)(this.bytes[0]&0xFF) << 24) | ( (long)(bytes[1]&0xFF)<<16) | ((long)(bytes[2]&0xFF)<<8) |  (long)(bytes[3] & 0xFF);
		this.indication = bytes[4] & 0xFF;
	}

}
