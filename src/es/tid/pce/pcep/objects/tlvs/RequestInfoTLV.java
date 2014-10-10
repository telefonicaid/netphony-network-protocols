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
	private int requestId;
	
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
		System.arraycopy(requestId, 0, this.tlv_bytes, 4, 4);

	}

	public void decode() throws MalformedPCEPObjectException {
		log.fine("Decoding RequestInfo TLV");
	
		byte[] ip=new byte[4];
		System.arraycopy(this.tlv_bytes, 4, ip, 0, 4);
		try {
			this.IPv4Address=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.arraycopy(this.tlv_bytes, 4, requestId, 0, 4);
		
	}

	public Inet4Address getIPv4Address() {
		return IPv4Address;
	}
	public void setIPv4Address(Inet4Address iPv4Address) {
		IPv4Address = iPv4Address;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	
}
