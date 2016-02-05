package es.tid.pce.pcep.objects;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class EndPointsUnnumberedIntf extends EndPoints{
	/**
	 * Source IPv4 address
	 */
	private Inet4Address sourceIP;
	
	private long sourceIF;
	/**
	 * Destination IPv4 address
	 */
	private Inet4Address destIP;
	
	private long destIF;
	
	
	public long getSourceIF() {
		return sourceIF;
	}


	public void setSourceIF(long sourceIF) {
		this.sourceIF = sourceIF;
	}


	public long getDestIF() {
		return destIF;
	}


	public void setDestIF(long destIF) {
		this.destIF = destIF;
	}

	public void encode() {
		this.ObjectLength=20;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		System.arraycopy(sourceIP.getAddress(),0, this.object_bytes, 4, 4);
		this.object_bytes[8]=(byte)(this.sourceIF >>> 24);
		this.object_bytes[9]=(byte)(this.sourceIF >>> 16 & 0xff);
		this.object_bytes[10]=(byte)(this.sourceIF >>> 8 & 0xff);
		this.object_bytes[11]=(byte)(this.sourceIF & 0xff);
		System.arraycopy(destIP.getAddress(),0, this.object_bytes, 12, 4);
		this.object_bytes[16]=(byte)(this.destIF >>> 24);
		this.object_bytes[17]=(byte)(this.destIF >>> 16 & 0xff);
		this.object_bytes[18]=(byte)(this.destIF >>> 8 & 0xff);
		this.object_bytes[19]=(byte)(this.destIF & 0xff);
	}

	/**
	 * Decode the IPv4 address
	 */
	public void decode() throws MalformedPCEPObjectException {
		if (this.ObjectLength!=20){
			throw new MalformedPCEPObjectException();
		}
		byte[] ip=new byte[4]; 
		System.arraycopy(this.object_bytes,4, ip, 0, 4);
		try {
			sourceIP=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int k = 0; k < 4; k++) {
			this.sourceIF = (this.sourceIF << 8) | (this.object_bytes[k+8] & 0xff);
		}
		System.arraycopy(this.object_bytes,12, ip, 0, 4);
		try {
			destIP=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		for (int k = 0; k < 4; k++) {
			this.destIF = (this.destIF << 8) | (this.object_bytes[k+16] & 0xff);
		}
		
	}
	/**
	 * Constructs a new PCEP END-POINTS object IPv4 Type from scratch.
	 */
	public EndPointsUnnumberedIntf() {
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_UNNUMBERED);
	}
	
	public EndPointsUnnumberedIntf(byte []bytes, int offset)throws MalformedPCEPObjectException {
		super(bytes,offset);
		decode();
	}
	
	public Inet4Address getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(Inet4Address sourceIP) {
		this.sourceIP = sourceIP;
	}

	public Inet4Address getDestIP() {
		return destIP;
	}

	public void setDestIP(Inet4Address destIP) {
		this.destIP = destIP;
	}
		
	public String toString(){
		return "Source IP: "+sourceIP+"Source If: "+sourceIF+" Destination IP: "+destIP+" Destination If: "+destIF;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (destIF ^ (destIF >>> 32));
		result = prime * result + ((destIP == null) ? 0 : destIP.hashCode());
		result = prime * result + (int) (sourceIF ^ (sourceIF >>> 32));
		result = prime * result
				+ ((sourceIP == null) ? 0 : sourceIP.hashCode());
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
		EndPointsUnnumberedIntf other = (EndPointsUnnumberedIntf) obj;
		if (destIF != other.destIF)
			return false;
		if (destIP == null) {
			if (other.destIP != null)
				return false;
		} else if (!destIP.equals(other.destIP))
			return false;
		if (sourceIF != other.sourceIF)
			return false;
		if (sourceIP == null) {
			if (other.sourceIP != null)
				return false;
		} else if (!sourceIP.equals(other.sourceIP))
			return false;
		return true;
	}
	
	
}
