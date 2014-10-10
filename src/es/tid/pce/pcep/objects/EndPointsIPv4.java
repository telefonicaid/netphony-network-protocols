package es.tid.pce.pcep.objects;

import java.net.*;

/**
 * <p> Represents a PCEP END-POINTS object IPv4 Type, as defined in RFC 5440</p>
 * <p>From RFC 5440 Section 7.6. END-POINTS Object</p>
 * <pre>
 * The END-POINTS object is used in a PCReq message to specify the
 * source IP address and the destination IP address of the path for
 * which a path computation is requested.  The P flag of the END-POINTS
 * object MUST be set.  If the END-POINTS object is received with the P
 * flag cleared, the receiving peer MUST send a PCErr message with
 *  Error-Type=10 and Error-value=1.  The corresponding path computation
 *   request MUST be cancelled by the PCE without further notification.
 *
 *   Note that the source and destination addresses specified in the END-
 *   POINTS object may correspond to the source and destination IP address
 *   of the TE LSP or to those of a path segment.  Two END-POINTS objects
 *   (for IPv4 and IPv6) are defined.
 *
 *   END-POINTS Object-Class is 4.
 *
 *   END-POINTS Object-Type is 1 for IPv4 and 2 for IPv6.
 *
 *   The format of the END-POINTS object body for IPv4 (Object-Type=1) is
 *   as follows:
 *
 *       0                   1                   2                   3
 *       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                     Source IPv4 address                       |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                  Destination IPv4 address                     |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * </pre>
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 * 
 */
public class EndPointsIPv4 extends EndPoints{
	/**
	 * Source IPv4 address
	 */
	private Inet4Address sourceIP;
	/**
	 * Destination IPv4 address
	 */
	private Inet4Address destIP;
	
	/**
	 * Constructs a new PCEP END-POINTS object IPv4 Type from scratch.
	 */
	public EndPointsIPv4() {
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV4);
	}
	
	/**
	 * Constructs a new PCEP END-POINTS object IPv4 Type from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public EndPointsIPv4(byte []bytes, int offset)throws MalformedPCEPObjectException {
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode the IPv4 address
	 */
	public void encode() {
		this.ObjectLength=12;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		System.arraycopy(sourceIP.getAddress(),0, this.object_bytes, 4, 4);
		System.arraycopy(destIP.getAddress(),0, this.object_bytes, 8, 4);
	}

	/**
	 * Decode the IPv4 address
	 */
	public void decode() throws MalformedPCEPObjectException {
		if (this.ObjectLength!=12){
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
		System.arraycopy(this.object_bytes,8, ip, 0, 4);
		try {
			destIP=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	 
		
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
		return "Source IP: "+sourceIP+" Destination IP: "+destIP;
	}
	

}
