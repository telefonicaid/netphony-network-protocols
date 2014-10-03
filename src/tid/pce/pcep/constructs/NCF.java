package tid.pce.pcep.constructs;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.objects.MalformedPCEPObjectException;

public class NCF extends PCEPConstruct {
	
	/**
	 * 
   The value for Grid is set to 1 for the ITU-T DWDM grid as defined in [G.694.1].
	 */
	private int grid;
	
	/**
	 * Channel spacing
	 */
	private int channelSpacing;
	
	/**
	 * n is a two's-complement integer to take either a positive, negative,
   or zero value.  This value is used to compute the frequency 
	 */
	private int n;
	
	
	
	public NCF(){
		super();
	}
	
	public NCF(byte[] bytes,int offset ) throws MalformedPCEPObjectException{
		decode(bytes, offset);
	}
	

	@Override
	public void encode() throws PCEPProtocolViolationException {
		this.setLength(4);
		this.bytes=new byte[this.getLength()];
		this.bytes[0]=(byte)((grid<<5)|(channelSpacing<<1));
		this.bytes[1]=0;
		this.bytes[2]=(byte)((n>>8)&0xFF);
		this.bytes[3]=(byte)(n&0xFF);

	}
	
	public void decode(byte[] bytes, int offset){
		
		grid=(bytes[offset]&0xE0)>>>5;
		channelSpacing=(bytes[offset]&0x1E)>>>1;
		n=((bytes[offset+2])<<8)|(bytes[offset+3]&0xFF);
		this.setLength(4);
	}
	
	@Override
	public String toString() {
		String ret=" n: "+Integer.toString(n);
		return ret;
	}

	public int getGrid() {
		return grid;
	}

	public void setGrid(int grid) {
		this.grid = grid;
	}

	public int getChannelSpacing() {
		return channelSpacing;
	}

	public void setChannelSpacing(int channelSpacing) {
		this.channelSpacing = channelSpacing;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
	
	

}
