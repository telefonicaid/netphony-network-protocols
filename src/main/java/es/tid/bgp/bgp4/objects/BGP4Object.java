package es.tid.bgp.bgp4.objects;

import es.tid.bgp.bgp4.BGP4Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public abstract class BGP4Object implements BGP4Element {
	
	protected static final Logger log= LoggerFactory.getLogger("BGP4Parser");

	protected byte[] bytes;
	protected int length;
	
	
	public byte[] getBytes() {
		return bytes;
	}
	protected void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public int getLength() {
		return length;
	}
	protected void setLength(int length) {
		this.length = length;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bytes);
		result = prime * result + length;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BGP4Object other = (BGP4Object) obj;
		if (!Arrays.equals(bytes, other.bytes))
			return false;
		if (length != other.length)
			return false;
		return true;
	}
	
	
}
