package es.tid.pce.pcep.constructs;

import java.util.logging.Logger;

import es.tid.pce.pcep.PCEPElement;

/*
 * A PCEP Construct is a rule that joins PCEP Objects and Constructions
 * Each specific construct will have its elements
 */
public abstract class PCEPConstruct implements PCEPElement{
	
	
	protected Logger log=Logger.getLogger("PCEPParser");

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
