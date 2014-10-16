package es.tid.pce.pcep;

/**
 * Interface class for all PCEP Elements, which are PCEP Messages, PCEP Constructs and PCEP Objects
 * 
 * @author ogondio
 *
 */
public interface PCEPElement {
	
	
	public void encode() throws PCEPProtocolViolationException;
	
	
	
	public byte[] getBytes();
	
	public int getLength();

}
