package es.tid.pce.pcep.objects;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.protocol.commons.ByteHandler;


/**
 * Class made to allow OpenFlow ID in PCEP Requests. Used in Strauss Project
 * 
 * 
 *  *   0                   1                   2                   3
 *       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                     Source Switch Id                          |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                 Source Switch Id(continuation)                |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                  Destination Switch Id                        |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |              Destination Switch Id(continuation)              |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * 
 * @author jaume
 *
 */

public class EndPointDataPathID extends EndPoints
{
	/**
	 * Source switch ID
	 */
	private String sourceSwitchID;
	/**
	 * Destination switch ID
	 */
	private String destSwitchID;
	
	
	public EndPointDataPathID()
	{
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_DATAPATH_ID);		
	}
	public EndPointDataPathID(byte[] bytes, int offset) throws MalformedPCEPObjectException, PCEPProtocolViolationException{
		super(bytes, offset);
		decode();
	}
	@Override
	public void encode() 
	{
		this.ObjectLength=20;
		this.object_bytes=new byte[ObjectLength];
		
		encode_header();
		
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(sourceSwitchID),0, this.object_bytes, 4, 8);
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(destSwitchID),0, this.object_bytes, 12, 8);
	}

	@Override
	public void decode() throws MalformedPCEPObjectException 
	{
		if (this.ObjectLength!=20)
		{
			throw new MalformedPCEPObjectException();
		}
		byte[] mac=new byte[8]; 
		System.arraycopy(this.object_bytes,4, mac, 0, 8);
		sourceSwitchID=ByteHandler.ByteMACToString(mac);
		log.info("EndPointDataPathID decode sourceSwitchID:: "+sourceSwitchID);
		
		System.arraycopy(this.object_bytes,12, mac, 0, 8);
		destSwitchID=ByteHandler.ByteMACToString(mac);
		log.info("EndPointDataPathID decode destSwitchID:: "+destSwitchID);
	}

	public String getSourceSwitchID() 
	{
		return sourceSwitchID;
	}

	public void setSourceSwitchID(String sourceSwitchID) 
	{
		this.sourceSwitchID = sourceSwitchID;
	}

	public String getDestSwitchID() 
	{
		return destSwitchID;
	}

	public void setDestSwitchID(String destSwitchID) 
	{
		this.destSwitchID = destSwitchID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((destSwitchID == null) ? 0 : destSwitchID.hashCode());
		result = prime * result
				+ ((sourceSwitchID == null) ? 0 : sourceSwitchID.hashCode());
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
		EndPointDataPathID other = (EndPointDataPathID) obj;
		if (destSwitchID == null) {
			if (other.destSwitchID != null)
				return false;
		} else if (!destSwitchID.equals(other.destSwitchID))
			return false;
		if (sourceSwitchID == null) {
			if (other.sourceSwitchID != null)
				return false;
		} else if (!sourceSwitchID.equals(other.sourceSwitchID))
			return false;
		return true;
	}
	
	
	
	
}