package es.tid.pce.pcep.objects;

import java.util.Objects;

import es.tid.protocol.commons.ByteHandler;

/**
 * BU Object
 * @author Luis Cepeda Martínez
 *
 */

public class BandwidthUtilization extends PCEPObject{
	
	/**
	 * The format of the BU object body is as follows:

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |              Reserved                         |    Type       |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                      Bandwidth Utilization                    |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                           BU Object Body Format
	 */
	private int type;
	private float bandwidthUtilization = 0;
	
	public BandwidthUtilization(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_BU);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_BU);
	}

	public BandwidthUtilization(byte[] bytes, int offset) throws MalformedPCEPObjectException{

		super(bytes,offset);
		decode();
	}
	
	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public float getBandwidthUtilization() {
		return bandwidthUtilization;
	}

	public void setBandwidthUtilization(float bandwidthUtilization) {
		this.bandwidthUtilization = bandwidthUtilization;
	}

	@Override
	public void encode() {
		ObjectLength=12;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		
		object_bytes[4]=0;
		object_bytes[5]=0;
		object_bytes[6]=0;
		
		ByteHandler.encode1byteInteger(type, object_bytes, 7);
		
		ByteHandler.encode1Float(bandwidthUtilization, object_bytes, 8);
	
	}

	
	@Override
	public void decode() throws MalformedPCEPObjectException {
		if (ObjectLength!=12){
			throw new MalformedPCEPObjectException();
		}
		int offset=7;
		type = ByteHandler.decode1byteInteger(object_bytes, offset);
		offset += 1;
		bandwidthUtilization = ByteHandler.decode1Float(object_bytes, offset);
		
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(bandwidthUtilization, type);
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
		BandwidthUtilization other = (BandwidthUtilization) obj;
		return Float.floatToIntBits(bandwidthUtilization) == Float.floatToIntBits(other.bandwidthUtilization)
				&& type == other.type;
	}

	@Override
	public String toString() {
		return "BandwidthUtilization [type=" + type + ", bandwidthUtilization=" + bandwidthUtilization + "]";
	}

	
	

}
