package tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields;

/**
 * Generic base class for SwitchingCapabilitySpecificInformation.
 * 
 * @author Fernando Muñoz del Nuevo
 * @author Oscar Gonzalez de Dios
 *
 */
public abstract class SwitchingCapabilitySpecificInformation {
	/**
	 * 
	 */
	protected byte[] bytes;
	
	/**
	 * 
	 */
	protected int length;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public abstract void encode();
	
	
}
