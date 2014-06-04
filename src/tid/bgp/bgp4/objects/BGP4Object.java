package tid.bgp.bgp4.objects;

import java.util.logging.Logger;

import tid.bgp.bgp4.BGP4Element;

public abstract class BGP4Object implements BGP4Element {
	
	protected Logger log=Logger.getLogger("BGP4Parser");

	protected byte[] bytes;
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
}
