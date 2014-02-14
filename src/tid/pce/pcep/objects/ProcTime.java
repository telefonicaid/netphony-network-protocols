package tid.pce.pcep.objects;

/**
 *  0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |       Reserved                |           Flags             |E|
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     Current-processing-time                   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        Min-processing-time                    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        Max-processing-time                    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       Average-processing time                 |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       Variance-processing-time                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * @author ogondio
 *
 */
public class ProcTime extends PCEPObject {
	
	private long currentProcessingTime;
	
	private long minProcessingTime;
	
	private long maxProcessingTime;
	
	private long averageProcessingTime;
	
	private long varianceProcessingTime;
	
	

	public ProcTime(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_PROC_TIME);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_PROC_TIME);
	}
	public ProcTime(byte []bytes, int offset)throws MalformedPCEPObjectException {
		super(bytes, offset);
		decode();
	}
	
	public void encode() {
		ObjectLength=28;/* 4 bytes de la cabecera + 24 del cuerpo */
		object_bytes=new byte[ObjectLength];
		encode_header();
		this.object_bytes[8]=(byte)(currentProcessingTime >>> 24);
		this.object_bytes[9]=(byte)(currentProcessingTime >> 16 & 0xff);
		this.object_bytes[10]=(byte)(currentProcessingTime >> 8 & 0xff);
		this.object_bytes[11]=(byte)(currentProcessingTime & 0xff);
		this.object_bytes[12]=(byte)(minProcessingTime >>> 24);
		this.object_bytes[13]=(byte)(minProcessingTime >> 16 & 0xff);
		this.object_bytes[14]=(byte)(minProcessingTime >> 8 & 0xff);
		this.object_bytes[15]=(byte)(minProcessingTime & 0xff);
		this.object_bytes[16]=(byte)(maxProcessingTime >>> 24);
		this.object_bytes[17]=(byte)(maxProcessingTime >> 16 & 0xff);
		this.object_bytes[18]=(byte)(maxProcessingTime >> 8 & 0xff);
		this.object_bytes[19]=(byte)(maxProcessingTime & 0xff);
		this.object_bytes[20]=(byte)(averageProcessingTime >>> 24);
		this.object_bytes[21]=(byte)(averageProcessingTime >> 16 & 0xff);
		this.object_bytes[22]=(byte)(averageProcessingTime >> 8 & 0xff);
		this.object_bytes[23]=(byte)(averageProcessingTime & 0xff);
		this.object_bytes[24]=(byte)(varianceProcessingTime >>> 24);
		this.object_bytes[25]=(byte)(varianceProcessingTime >> 16 & 0xff);
		this.object_bytes[26]=(byte)(varianceProcessingTime >> 8 & 0xff);
		this.object_bytes[27]=(byte)(varianceProcessingTime & 0xff);

	}

	
	public void decode() throws MalformedPCEPObjectException {
		int k;
		currentProcessingTime = 0;		
		for (k = 0; k < 4; k++) {
			currentProcessingTime = (currentProcessingTime << 8) | (object_bytes[k+8] & 0xff);
		}
		minProcessingTime = 0;		
		for (k = 0; k < 4; k++) {
			minProcessingTime = (minProcessingTime << 8) | (object_bytes[k+12] & 0xff);
		}
		maxProcessingTime = 0;		
		for (k = 0; k < 4; k++) {
			maxProcessingTime = (maxProcessingTime << 8) | (object_bytes[k+16] & 0xff);
		}
		averageProcessingTime = 0;		
		for (k = 0; k < 4; k++) {
			averageProcessingTime = (averageProcessingTime << 8) | (object_bytes[k+20] & 0xff);
		}
		varianceProcessingTime = 0;		
		for (k = 0; k < 4; k++) {
			varianceProcessingTime = (varianceProcessingTime << 8) | (object_bytes[k+24] & 0xff);
		}
	}
	public long getCurrentProcessingTime() {
		return currentProcessingTime;
	}
	public void setCurrentProcessingTime(long currentProcessingTime) {
		this.currentProcessingTime = currentProcessingTime;
	}
	public long getMinProcessingTime() {
		return minProcessingTime;
	}
	public void setMinProcessingTime(long minProcessingTime) {
		this.minProcessingTime = minProcessingTime;
	}
	public long getMaxProcessingTime() {
		return maxProcessingTime;
	}
	public void setMaxProcessingTime(long maxProcessingTime) {
		this.maxProcessingTime = maxProcessingTime;
	}
	public long getAverageProcessingTime() {
		return averageProcessingTime;
	}
	public void setAverageProcessingTime(long averageProcessingTime) {
		this.averageProcessingTime = averageProcessingTime;
	}
	public long getVarianceProcessingTime() {
		return varianceProcessingTime;
	}
	public void setVarianceProcessingTime(long varianceProcessingTime) {
		this.varianceProcessingTime = varianceProcessingTime;
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("<ProcTime currPT: ");
		sb.append(this.currentProcessingTime);
		sb.append(">");
		return sb.toString();
		
	}
	

}
