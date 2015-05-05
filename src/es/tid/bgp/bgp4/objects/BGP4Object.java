package es.tid.bgp.bgp4.objects;

import es.tid.bgp.bgp4.BGP4Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BGP4Object implements BGP4Element {
	
	protected static final Logger log= LoggerFactory.getLogger("BGP4Parser");

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
