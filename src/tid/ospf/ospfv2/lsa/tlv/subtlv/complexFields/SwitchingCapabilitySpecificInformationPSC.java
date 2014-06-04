package tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields;

/**
 * Switching Capability SpecificInformation for PSC.
 * 
Kompella & Rekhter          Standards Track                     [Page 4]

 
	RFC 4203                OSPF Extensions in MPLS             October 2005
 
 	   When the Switching Capability field is PSC-1, PSC-2, PSC-3, or PSC-4,
	   the Switching Capability specific information field includes Minimum
	   LSP Bandwidth, Interface MTU, and padding.

	    0                   1                   2                   3
	    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	   |                  Minimum LSP Bandwidth                        |
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	   |           Interface MTU       |            Padding            |
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * 
 *   @author Fernando
 *   @author Oscar Gonzalez de Dios
 */

public class SwitchingCapabilitySpecificInformationPSC extends SwitchingCapabilitySpecificInformation {
	
	/**
	 * Minimum LSP Bandwidth
	 */
	private long minimumLSPBandwidth;
	
	/**
	 * Interface MTU
	 */
	private int interfaceMTU;

	public SwitchingCapabilitySpecificInformationPSC(byte[] bytes,int offset) {
		this.length=8;
		this.bytes=new byte[this.length];
		System.arraycopy(bytes, offset, this.bytes, 0, this.length);
		decode();
	}
	
	/**
	 * 
	 */
	public void encode(){		
		this.setLength(8);
		this.bytes=new byte[this.length];
		this.bytes[0]=(byte)(minimumLSPBandwidth >>> 24);
		this.bytes[1]=(byte)(minimumLSPBandwidth >> 16 & 0xff);
		this.bytes[2]=(byte)(minimumLSPBandwidth >> 8 & 0xff);
		this.bytes[3]=(byte)(minimumLSPBandwidth & 0xff);
		this.bytes[4]=(byte)(interfaceMTU >> 8 & 0xff);
		this.bytes[5]=(byte)(interfaceMTU & 0xff);
		this.bytes[6]=0;
		this.bytes[7]=0;
		
	}
	
	/**
	 * 
	 */
	private void decode(){
		this.minimumLSPBandwidth = ( (long)(this.bytes[0]&0xFF) << 24) | ( (long)(bytes[1]&0xFF)<<16) | ((long)(bytes[2]&0xFF)<<8) |  (long)(bytes[3] & 0xFF);
		this.interfaceMTU = ( (bytes[4]&0xFF)<<8) |  (bytes[5] & 0xFF);
	}

	public long getMinimumLSPBandwidth() {
		return minimumLSPBandwidth;
	}

	public void setMinimumLSPBandwidth(long minimumLSPBandwidth) {
		this.minimumLSPBandwidth = minimumLSPBandwidth;
	}

	public int getInterfaceMTU() {
		return interfaceMTU;
	}

	public void setInterfaceMTU(int interfaceMTU) {
		this.interfaceMTU = interfaceMTU;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

}
