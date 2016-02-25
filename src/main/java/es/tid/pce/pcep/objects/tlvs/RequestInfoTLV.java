package es.tid.pce.pcep.objects.tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

/**
 * RequestInfo TLV format:
 * Type: TBD: Proposed value: 0x9001
 * Value Length: 8 bytes
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       IPv4 address                            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       Request Id                              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * IPv4 address - the address of the requesting PCC
 * Request Id - the request Id

 * @author mcs
 *
 */
public class RequestInfoTLV extends PCEPTLV {

	private Inet4Address IPv4Address;
	private long requestId;
	
	public RequestInfoTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_REQUEST_INFO;		
	}

	public RequestInfoTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	public void encode() {
		this.setTLVValueLength(8);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		System.arraycopy(IPv4Address.getAddress(), 0, this.tlv_bytes, 4, 4);
		//System.arraycopy(requestId, 0, this.tlv_bytes, 4, 4);
		this.tlv_bytes[8]=(byte)((requestId>>24) & 0xFF);
		this.tlv_bytes[9]=(byte)((requestId>>16) & 0xFF);
		this.tlv_bytes[10]=(byte)((requestId>>8) & 0xFF);
		this.tlv_bytes[11]=(byte)(requestId & 0xFF);

	}

	public void decode() throws MalformedPCEPObjectException {
		log.debug("Decoding RequestInfo TLV");
	
		byte[] ip=new byte[4];
		System.arraycopy(this.tlv_bytes, 4, ip, 0, 4);
		try {
			this.IPv4Address=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		requestId=( (((long)this.tlv_bytes[8]&(long)0xFF)<<24) | (((long)this.tlv_bytes[9]&(long)0xFF)<<16) |( ((long)this.tlv_bytes[10]&(long)0xFF)<<8) |  ((long)this.tlv_bytes[11]& (long)0xFF) );
		
	}

	public Inet4Address getIPv4Address() {
		return IPv4Address;
	}
	public void setIPv4Address(Inet4Address iPv4Address) {
		IPv4Address = iPv4Address;
	}
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((IPv4Address == null) ? 0 : IPv4Address.hashCode());
		result = prime * result + (int) (requestId ^ (requestId >>> 32));
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
		RequestInfoTLV other = (RequestInfoTLV) obj;
		if (IPv4Address == null) {
			if (other.IPv4Address != null)
				return false;
		} else if (!IPv4Address.equals(other.IPv4Address))
			return false;
		if (requestId != other.requestId)
			return false;
		return true;
	}
	
	
	
}
