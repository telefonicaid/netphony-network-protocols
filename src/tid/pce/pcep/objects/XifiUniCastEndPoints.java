package tid.pce.pcep.objects;


import tid.protocol.commons.ByteHandler;

/**
 * 
 *   0                   1                   2                   3
 *       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                     Source Switch Id                          |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                              |Destination Switch Id           |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                                                               |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                     Source port                               |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                     Destination port                          |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                     Source mac                                |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |       (continuation)         |     Destination mac            |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |       (continuation)         |                                |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * 
 * 
 * @author jaume
 *
 */


public class XifiUniCastEndPoints extends EndPoints
{
	/**
	 * Source switch ID
	 */
	private String sourceSwitchID;
	/**
	 * Destination switch ID
	 */
	private String destSwitchID;
	
	/**
	 * Source port
	 */
	private int source_port;
	
	/**
	 * Destination port
	 */
	private int destination_port;
	
	/**
	 * SourceMAC
	 */
	private String SourceMAC;
	
	/**
	 * Destination mac
	 */
	private String DestinationMAC;
	
	/**
	 * Constructs a new PCEP END-POINTS object IPv4 Type from scratch.
	 */
	public XifiUniCastEndPoints() 
	{
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_MAC);
	}
	
	/**
	 * Constructs a new PCEP END-POINTS object IPv4 Type from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public XifiUniCastEndPoints(byte []bytes, int offset)throws MalformedPCEPObjectException 
	{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode the IPv4 address
	 */
	public void encode() 
	{
		this.ObjectLength=44;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(sourceSwitchID),0, this.object_bytes, 4, 8);
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(destSwitchID),0, this.object_bytes, 12, 8);
		
		int offset = 4 + 8 + 8;
		ByteHandler.IntToBuffer(0,offset*8, 32,source_port,this.object_bytes);
		
		offset += 4;
		ByteHandler.IntToBuffer(0,offset*8, 32,destination_port,this.object_bytes);
		
		offset += 4;
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(SourceMAC),0, this.object_bytes, offset, 6);
		
		offset += 6;
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(DestinationMAC),0, this.object_bytes, offset, 6);
	
	}

	/**
	 * Decode the IPv4 address
	 */
	public void decode() throws MalformedPCEPObjectException 
	{
		if (this.ObjectLength!=44)
		{
			throw new MalformedPCEPObjectException();
		}
		byte[] mac=new byte[8]; 
		System.arraycopy(this.object_bytes,4, mac, 0, 8);
		sourceSwitchID=ByteHandler.ByteMACToString(mac);
		
		System.arraycopy(this.object_bytes,12, mac, 0, 8);
		destSwitchID=ByteHandler.ByteMACToString(mac);
		
		int offset = 4 + 8 + 8;
		
		source_port = ByteHandler.easyCopy(0,31,object_bytes[offset],object_bytes[offset + 1],object_bytes[offset + 2],object_bytes[offset + 3]);
		
		offset += 4;
		destination_port = ByteHandler.easyCopy(0,31,object_bytes[offset],object_bytes[offset + 1],object_bytes[offset + 2],object_bytes[offset + 3]);
		
		offset += 4;
		System.arraycopy(this.object_bytes,offset, mac, 0, 6);
		SourceMAC=ByteHandler.ByteMACToString(mac);
		
		offset += 6;
		System.arraycopy(this.object_bytes,offset, mac, 0, 6);
		DestinationMAC=ByteHandler.ByteMACToString(mac);
		
	}
		
	public String getSwitchSourceID() 
	{
		return sourceSwitchID;
	}

	public void setSwitchSourceID(String sourceMAC) 
	{
		this.sourceSwitchID = sourceMAC;
	}

	public String getSwitchDestinationID()
	{
		return destSwitchID;
	}

	public void setSwitchDestinationID(String destMAC)
	{
		this.destSwitchID = destMAC;
	}

	public String toString()
	{
		return "Source MAC: "+sourceSwitchID+" Destination MAC: "+destSwitchID;
	}

	public int getSource_port() {
		return source_port;
	}

	public void setSource_port(int source_port) {
		this.source_port = source_port;
	}

	public int getDestination_port() 
	{
		return destination_port;
	}

	public void setDestination_port(int destination_port)
	{
		this.destination_port = destination_port;
	}

	public String getSourceMAC() {
		return SourceMAC;
	}

	public void setSourceMAC(String sourceMAC) 
	{
		SourceMAC = sourceMAC;
	}

	public String getDestinationMAC() 
	{
		return DestinationMAC;
	}

	public void setDestinationMAC(String destinationMAC) 
	{
		DestinationMAC = destinationMAC;
	}
	
	

}