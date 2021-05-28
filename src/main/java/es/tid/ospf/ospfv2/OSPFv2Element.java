package es.tid.ospf.ospfv2;

/**
 * Interface class for all OSPFv2 Elements
 * 
 * @author ogondio
 *
 */
public interface OSPFv2Element {
	
	
	public void encode();
	
	
	
	public byte[] getBytes();
	
	public int getLength();

}
