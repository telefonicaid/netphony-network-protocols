package tid.pce.pcep.objects;

import java.util.LinkedList;
import tid.protocol.commons.ByteHandler;

/**
 * 
 * This class is a copy of P2MPEndPointsIPv4. The only difference is that instead
 * of Ipv4 we have database ID (of 8 bytes). Asi que merits al farruk
 * 
 *      0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                          Leaf type                            |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                Source DataPath Id (8 bytes)                  |
    |                                                               |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |              Destination DataPath Id (8 bytes)                |
    |                                                               |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    ~                           ...                                 ~
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |              Destination DataPath Id (8 bytes)                |
    |                                                               |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * 
 * @author jaume
 *
 */

public class P2MPEndPointsDataPathID extends EndPoints
{

	/*
	 * Leaf type
	 */
	private int leafType;
	
	
	/**
	 * Source IPv4 address
	 */
	private String sourceDatapathID;




	
	/**
	 * Destination IPv4 addresses
	 */
	private LinkedList <String> destDatapathIDList;


	/**
	 * Constructs a new PCEP END-POINTS object IPv4 Type from scratch.
	 */
	public P2MPEndPointsDataPathID() 
	{
		super();
		destDatapathIDList=new LinkedList<String> ();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_P2MP_ENDPOINTS_DATAPATHID);
	}

	/**
	 * Constructs a new PCEP END-POINTS object IPv4 Type from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public P2MPEndPointsDataPathID(byte []bytes, int offset)throws MalformedPCEPObjectException 
	{
		super(bytes,offset);
		destDatapathIDList=new LinkedList<String> ();
		decode();
	}

	/**
	 * Encode the IPv4 address
	 */
	public void encode() 
	{
		this.ObjectLength= 4 + 4 + 8 + 8 * destDatapathIDList.size();



		this.object_bytes=new byte[ObjectLength];
		encode_header();
		
		//leafType
		this.object_bytes[4]=(byte)(leafType >>> 24);
		this.object_bytes[5]=(byte)(leafType >>> 16 & 0xff);
		this.object_bytes[6]=(byte)(leafType >>> 8 & 0xff);
		this.object_bytes[7]=(byte)(leafType & 0xff);	
		
		
		
		
		
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(sourceDatapathID),0, this.object_bytes, 8, 8);
		
		// TODO what if its size is 0?
		for (int i = 0; i < destDatapathIDList.size(); i++)
		{
			System.arraycopy(ByteHandler.MACFormatStringtoByteArray(destDatapathIDList.get(i)),0, this.object_bytes, 16 + 8 * i, 8);			
		}
	}

	/**
	 * Decode the IPv4 address
	 */
	public void decode() throws MalformedPCEPObjectException 
	{
		System.out.println("Decoding P2MPEndPointsDataPathID");
		if (this.ObjectLength < 24 || this.ObjectLength % 4 != 0)
		{
			log.info("object size doesnt match");
			throw new MalformedPCEPObjectException();
		}
		
		leafType=0;
		for (int k = 0; k < 4; k++) 
		{
			leafType = (leafType << 8) | (this.object_bytes[k+4] & 0xff);
		}	
		log.info("leafType: "+leafType);
		
		
		int numDestId = (int)((this.ObjectLength - 4 - 4 - 8)/8);
		log.info("num of Dest Ids: "+numDestId);

		byte[] datapath_id = new byte[8]; 
		System.arraycopy(this.object_bytes,8, datapath_id, 0, 8);
		sourceDatapathID = ByteHandler.ByteMACToString(datapath_id);

		for (int i = 0; i < numDestId; i++)
		{

			System.arraycopy(this.object_bytes, 16 + 8*i, datapath_id, 0, 8);

			destDatapathIDList.add(ByteHandler.ByteMACToString(datapath_id)); 
		}
	}

	
	//GETTERS SETTERS
	
	public int getLeafType()
	{
		return leafType;
	}

	public void setLeafType(int leafType)
	{
		this.leafType = leafType;
	}

	public String getSourceDatapathID() 
	{
		return sourceDatapathID;
	}

	public void setSourceDatapathID(String sourceDatapathID)
	{
		this.sourceDatapathID = sourceDatapathID;
	}

	public LinkedList<String> getDestDatapathIDList() 
	{
		return destDatapathIDList;
	}

	public void setDestDatapathIDList(LinkedList<String> destDatapathIDList)
	{
		this.destDatapathIDList = destDatapathIDList;
	}

	@Override
	public String toString()
	{
		return "P2MPEndPointsDataPathID [leafType=" + leafType
				+ ", sourceDatapathID=" + sourceDatapathID
				+ ", destDatapathIDList=" + destDatapathIDList + "]";
	}

	
	
	


}
