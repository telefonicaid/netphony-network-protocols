package es.tid.pce.pcep.objects;

import java.net.*;

/**
 * PCEP END-POINTS object IPv6 Type
 * 
 * From RFC 5440 Section 7.6. END-POINTS Object
 * 
 * The END-POINTS object is used in a PCReq message to specify the
 * source IP address and the destination IP address of the path for
 * which a path computation is requested.  The P flag of the END-POINTS
 * object MUST be set.  If the END-POINTS object is received with the P
 * flag cleared, the receiving peer MUST send a PCErr message with
 * Error-Type=10 and Error-value=1.  The corresponding path computation
 * request MUST be cancelled by the PCE without further notification.
 * 
 * Note that the source and destination addresses specified in the END-
 * POINTS object may correspond to the source and destination IP address
 * of the TE LSP or to those of a path segment.  Two END-POINTS objects
 * (for IPv4 and IPv6) are defined.
 * 
 * END-POINTS Object-Class is 4.
 * 
 * END-POINTS Object-Type is 1 for IPv4 and 2 for IPv6.
 * 
 * The format of the END-POINTS object for IPv6 (Object-Type=2) is as
 * follows:

 *     0                   1                   2                   3
 *     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *    |                                                               |
 *    |                Source IPv6 address (16 bytes)                 |
 *    |                                                               |
 *    |                                                               |
 *    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *    |                                                               |
 *    |              Destination IPv6 address (16 bytes)              |
 *    |                                                               |
 *    |                                                               |
 *    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * 
 *               Figure 13: END-POINTS Object Body Format for IPv6
 * 
 * The END-POINTS object body has a fixed length of 8 bytes for IPv4 and
 * 32 bytes for IPv6.
 * 
 * If more than one END-POINTS object is present, the first MUST be
 * processed and subsequent objects ignored.
 * 
 * @author Oscar Gonzalez de Dios
 * @version 0.1 
 */
public class EndPointsIPv6 extends EndPoints{
	
	public Inet6Address sourceIP;//Source IPv6 address
	public Inet6Address destIP;//Destination IPv6 address
	
	/**
	 * 
	 */
	public EndPointsIPv6() {
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV6);
	}
	
	/**
	 * Constructs a new PCEP END-POINTS object IPv6 Type from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public EndPointsIPv6(byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode the EndPoints IPv6 objects
	 */
	public void encode() {
		this.ObjectLength=36;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		System.arraycopy(sourceIP.getAddress(),0, this.object_bytes, 4, 16);
		System.arraycopy(destIP.getAddress(),0, this.object_bytes, 20, 16);
	}

	/**
	 * Decode the ENDPOINTS IPv6 object
	 */
	public void decode() throws MalformedPCEPObjectException{
		if (ObjectLength!=36){
			throw new MalformedPCEPObjectException();
		}
		byte[] ip=new byte[16]; 
		System.arraycopy(this.object_bytes,4, ip, 0, 16);
		try {
			sourceIP=(Inet6Address)Inet6Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.arraycopy(this.object_bytes,20, ip, 0, 16);
		try {
			destIP=(Inet6Address)Inet6Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	 
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((destIP == null) ? 0 : destIP.hashCode());
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
		EndPointsIPv6 other = (EndPointsIPv6) obj;
		if (destIP == null) {
			if (other.destIP != null)
				return false;
		} else if (!destIP.equals(other.destIP))
			return false;
		if (sourceIP == null) {
			if (other.sourceIP != null)
				return false;
		} else if (!sourceIP.equals(other.sourceIP))
			return false;
		return true;
	}
		
	
	

}
