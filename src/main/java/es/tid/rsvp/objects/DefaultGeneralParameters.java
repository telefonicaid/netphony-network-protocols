package es.tid.rsvp.objects;

/**
 * 
 * @author Fernando Munoz del Nuevo
 * 
 * Default General Characterisation Parameters Data Fragment for ADSPEC Objects

All RSVP ADSPEC objects must contain the general characterisation parameters defined in [SW96b].

31            24 23           16 15            8 7             0
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|    1  (c)     |x| reserved    |           8 (d)               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|    4 (e)      |    (f)        |           1 (g)               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|       IS hop count (32-bit unsigned integer)                  |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|    6 (h)      |    (i)        |           1 (j)               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|  Path b/w estimate  (32-bit IEEE floating point number)       |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     8 (k)     |    (l)        |           1 (m)               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|        Minimum path latency (32-bit integer)                  |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     10 (n)    |      (o)      |           1 (p)               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|      Composed MTU (32-bit unsigned integer)                   |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

(c)
    Per-Service header, service number 1 (Default General Parameters). 
(d)
    Global Break bit ([SW96b], Parameter 2) (marked x) and length of General Parameters data block. 
(e)
    Parameter ID, parameter 4 (Number-of-IS-hops parameter). 
(f)
    Parameter 4 flag byte. 
(g)
    Parameter 4 length, 1 word not including the header. 
(h)
    Parameter ID, parameter 6 (Path-BW parameter). 
(i)
    Parameter 6 flag byte. 
(j)
    Parameter 6 length, 1 word not including the header. 
(k)
    Parameter ID, parameter 8 (minimum path latency). 
(l)
    Parameter 8 flag byte. 
(m)
    Parameter 8 length, 1 word not including the header. 
(n)
    Parameter ID, parameter 10 (composed path MTU). 
(o)
    Parameter 10 flag byte. 
(p)
    Parameter 10 length, 1 word not including the header.
    
 *
 */


public class DefaultGeneralParameters{
	
	private int perServiceHeader;
	
	private int globalBreakBit;
	
	private int parametersGlobalLength;
	
	private int parameter1ID;
	
	private int parameter1Flag;
	
	private int parameter1length;
	
	private long isHopCount;
	
	private int parameter2ID;
	
	private int parameter2Flag;
	
	private int parameter2length;
	
	private float pathBWEstimate;
	
	private int parameter3ID;
	
	private int parameter3Flag;
	
	private int parameter3length;
	
	private double minimumPathLatency;
	
	private int parameter4ID;
	
	private int parameter4Flag;
	
	private int parameter4length;
	
	private long composedMTU;
	
	private byte[] bytes;
	
	private int length;
	
	
	public DefaultGeneralParameters(long isHopCount, float pathBWEstimate, double minimumPathLatency, long composedMTU){
		
		this.perServiceHeader = 1;
		this.globalBreakBit = 0;
		this.parametersGlobalLength = 8;
		
		this.parameter1ID = 4;
		this.parameter1Flag = 0;
		this.parameter1length = 1;
		
		this.isHopCount = isHopCount;
		
		this.parameter2ID = 6;
		this.parameter2Flag = 0;
		this.parameter2length = 1;
		
		this.pathBWEstimate = pathBWEstimate;
		
		this.parameter3ID = 8;
		this.parameter3Flag = 0;
		this.parameter3length = 1;
		
		this.minimumPathLatency = minimumPathLatency;
		
		this.parameter4ID = 10;
		this.parameter4Flag = 0;
		this.parameter4length = 1;
		
		this.composedMTU = composedMTU;
		
		this.length = 36;
		this.bytes = new byte[this.length];
		
	}
	
	public void encode(){
		
		int offset = 0;
		
		this.bytes[offset] = (byte)(this.perServiceHeader & 0xFF);
		this.bytes[offset+1] = (byte)(this.globalBreakBit >> 7 & 0xff);
		this.bytes[offset+2] = (byte)(this.parametersGlobalLength >> 8 & 0xFF);
		this.bytes[offset+3] = (byte)(this.parametersGlobalLength & 0xFF);
		
		offset = offset + 4;
		
		this.bytes[offset] = (byte)(this.parameter1ID & 0xFF);
		this.bytes[offset+1] = (byte)(this.parameter1Flag & 0xff);
		this.bytes[offset+2] = (byte)(this.parameter1length >> 8 & 0xFF);
		this.bytes[offset+3] = (byte)(this.parameter1length & 0xFF);
		
		offset = offset + 4;
		
		this.bytes[offset] = (byte)(this.isHopCount >>> 24);
		this.bytes[offset+1] = (byte)(this.isHopCount >> 16 & 0xff);
		this.bytes[offset+2] = (byte)(this.isHopCount >> 8 & 0xff);
		this.bytes[offset+3] = (byte)(this.isHopCount & 0xff);
		
		offset = offset + 4;
		
		this.bytes[offset] = (byte)(this.parameter2ID & 0xFF);
		this.bytes[offset+1] = (byte)(this.parameter2Flag & 0xff);
		this.bytes[offset+2] = (byte)(this.parameter2length >> 8 & 0xFF);
		this.bytes[offset+3] = (byte)(this.parameter2length & 0xFF);
		
		offset = offset + 4;
		
		int pbwe = Float.floatToIntBits(this.pathBWEstimate);
		
		this.bytes[offset] = (byte)(pbwe >>> 24);
		this.bytes[offset+1] = (byte)(pbwe >> 16 & 0xff);
		this.bytes[offset+2] = (byte)(pbwe >> 8 & 0xff);
		this.bytes[offset+3] = (byte)(pbwe & 0xff);
		
		offset = offset + 4;
		
		this.bytes[offset] = (byte)(this.parameter3ID & 0xFF);
		this.bytes[offset+1] = (byte)(this.parameter3Flag & 0xff);
		this.bytes[offset+2] = (byte)(this.parameter3length >> 8 & 0xFF);
		this.bytes[offset+3] = (byte)(this.parameter3length & 0xFF);

		offset = offset + 4;
		
		this.bytes[offset] = (byte)(((long)this.minimumPathLatency & 0xFF000000L) >> 24);
		this.bytes[offset+1] = (byte)(((long)this.minimumPathLatency & 0x00FF0000L) >> 16);
		this.bytes[offset+2] = (byte)(((long)this.minimumPathLatency & 0x0000FF00L) >> 8);
		this.bytes[offset+3] = (byte)((long)this.minimumPathLatency & 0x000000FFL);
		
		offset = offset + 4;
		
		this.bytes[offset] = (byte)(this.parameter4ID & 0xFF);
		this.bytes[offset+1] = (byte)(this.parameter4Flag & 0xff);
		this.bytes[offset+2] = (byte)(this.parameter4length >> 8 & 0xFF);
		this.bytes[offset+3] = (byte)(this.parameter4length & 0xFF);

		offset = offset + 4;
				
		this.bytes[offset] = (byte)(this.composedMTU >>> 24);
		this.bytes[offset+1] = (byte)(this.composedMTU >> 16 & 0xff);
		this.bytes[offset+2] = (byte)(this.composedMTU >> 8 & 0xff);
		this.bytes[offset+3] = (byte)(this.composedMTU & 0xff);
				
	}

	public int getPerServiceHeader() {
		return perServiceHeader;
	}

	public void setPerServiceHeader(int perServiceHeader) {
		this.perServiceHeader = perServiceHeader;
	}

	public int getGlobalBreakBit() {
		return globalBreakBit;
	}

	public void setGlobalBreakBit(int globalBreakBit) {
		this.globalBreakBit = globalBreakBit;
	}

	public int getParameter1ID() {
		return parameter1ID;
	}

	public void setParameter1ID(int parameter1id) {
		parameter1ID = parameter1id;
	}

	public int getParameter1Flag() {
		return parameter1Flag;
	}

	public void setParameter1Flag(int parameter1Flag) {
		this.parameter1Flag = parameter1Flag;
	}

	public int getParameter1length() {
		return parameter1length;
	}

	public void setParameter1length(int parameter1length) {
		this.parameter1length = parameter1length;
	}

	public long getIsHopCount() {
		return isHopCount;
	}

	public void setIsHopCount(long isHopCount) {
		this.isHopCount = isHopCount;
	}

	public int getParameter2ID() {
		return parameter2ID;
	}

	public void setParameter2ID(int parameter2id) {
		parameter2ID = parameter2id;
	}

	public int getParameter2Flag() {
		return parameter2Flag;
	}

	public void setParameter2Flag(int parameter2Flag) {
		this.parameter2Flag = parameter2Flag;
	}

	public int getParameter2length() {
		return parameter2length;
	}

	public void setParameter2length(int parameter2length) {
		this.parameter2length = parameter2length;
	}

	public float getPathBWEstimate() {
		return pathBWEstimate;
	}

	public void setPathBWEstimate(float pathBWEstimate) {
		this.pathBWEstimate = pathBWEstimate;
	}

	public int getParameter3ID() {
		return parameter3ID;
	}

	public void setParameter3ID(int parameter3id) {
		parameter3ID = parameter3id;
	}

	public int getParameter3Flag() {
		return parameter3Flag;
	}

	public void setParameter3Flag(int parameter3Flag) {
		this.parameter3Flag = parameter3Flag;
	}

	public int getParameter3length() {
		return parameter3length;
	}

	public void setParameter3length(int parameter3length) {
		this.parameter3length = parameter3length;
	}

	public double getMinimumPathLatency() {
		return minimumPathLatency;
	}

	public void setMinimumPathLatency(double minimumPathLatency) {
		this.minimumPathLatency = minimumPathLatency;
	}

	public int getParameter4ID() {
		return parameter4ID;
	}

	public void setParameter4ID(int parameter4id) {
		parameter4ID = parameter4id;
	}

	public int getParameter4Flag() {
		return parameter4Flag;
	}

	public void setParameter4Flag(int parameter4Flag) {
		this.parameter4Flag = parameter4Flag;
	}

	public int getParameter4length() {
		return parameter4length;
	}

	public void setParameter4length(int parameter4length) {
		this.parameter4length = parameter4length;
	}

	public long getComposedMTU() {
		return composedMTU;
	}

	public void setComposedMTU(long composedMTU) {
		this.composedMTU = composedMTU;
	}

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
