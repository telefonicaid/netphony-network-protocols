package es.tid.pce.pcep.constructs;

import java.util.Arrays;

import es.tid.pce.pcep.PCEPElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * A PCEP Construct is a rule that joins PCEP Objects and Constructions
 * Each specific construct will have its elements
 */
public abstract class PCEPConstruct implements PCEPElement{
	
	
	protected static final Logger log= LoggerFactory.getLogger("PCEPParser");

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
		PCEPConstruct other = (PCEPConstruct) obj;
		if (!Arrays.equals(bytes, other.bytes))
			return false;
		if (length != other.length)
			return false;
		return true;
	}
	
	
		
}
