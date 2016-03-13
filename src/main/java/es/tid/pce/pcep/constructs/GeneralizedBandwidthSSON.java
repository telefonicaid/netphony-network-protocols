package es.tid.pce.pcep.constructs;



import java.lang.String;

import es.tid.pce.pcep.objects.ObjectParameters;

/**
 * Generalized Bandwidth of SSON Network.
 *  The SSON traffic parameters carried in both objects have the same
   format as shown in Figure 1.

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |              m                |            Reserved           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                   Figure 1: The SSON Traffic Parameters

   m (16 bits): the slot width is specified by m*12.5 GHz.
 *@author ogondio
 */


public class GeneralizedBandwidthSSON extends GeneralizedBandwidth {
	  
    private int m;

	
	
	/**
	 * Constructs a GeneralizedBandwidth object
	 */
	public GeneralizedBandwidthSSON (){
		super();
		this.setBwSpecType(ObjectParameters.PCEP_GMPLS_GEN_BANDWIDTH_SSON);
	}
	
	public GeneralizedBandwidthSSON (byte[] bytes, int offset){
		super(bytes, offset);
		this.setBwSpecType(ObjectParameters.PCEP_GMPLS_GEN_BANDWIDTH_SSON);
		decode(bytes, offset);
	}
	
	public void encode() {
		int length=4;
		this.setLength(length);
		this.bytes=new byte[length];
		int offset=0;
		this.bytes[offset]=(byte)((m>>8)&0xFF);
		this.bytes[offset+1]=(byte)(m&0xFF);
	}

	
	public void decode(byte[] bytes, int offset) {
		m=(((bytes[offset]&0xFF)<<8)& 0xFF00) |  (bytes[offset+1] & 0xFF);;	
		}

	

	
	public String toString(){
		StringBuffer sb=new StringBuffer(100);
		sb.append("GenBW SSON m: ");
		sb.append(m);
		return sb.toString();
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + m;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeneralizedBandwidthSSON other = (GeneralizedBandwidthSSON) obj;
		if (m != other.m)
			return false;
		return true;
	}
	
	

}
