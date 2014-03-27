package tid.pce.pcep.objects;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

/**
 * <p> Represents a PCEP P2MPEND-POINTS object IPv4 Type, as defined in RFC 6006</p>
 * <p>From RFC 6006 Section 3.3.2 END-POINTS Object</p>
 * <pre>
 The END-POINTS object is used in a PCReq message to specify the
   source IP address and the destination IP address of the path for
   which a path computation is requested.  To represent the end points
   for a P2MP path efficiently, we define two new types of END-POINTS
   objects for the P2MP path:

   o  Old leaves whose path can be modified/reoptimized;

   o  Old leaves whose path must be left unchanged.

   With the new END-POINTS object, the PCE path computation request
   message is expanded in a way that allows a single request message to
   list multiple destinations.

   In total, there are now 4 possible types of leaves in a P2MP request:

   o  New leaves to add (leaf type = 1)

   o  Old leaves to remove (leaf type = 2)

   o  Old leaves whose path can be modified/reoptimized (leaf type = 3)

   o  Old leaves whose path must be left unchanged (leaf type = 4)

   A given END-POINTS object gathers the leaves of a given type.  The
   type of leaf in a given END-POINTS object is identified by the END-
   POINTS object leaf type field.

   Using the new END-POINTS object, the END-POINTS portion of a request
   message for the multiple destinations can be reduced by up to 50% for
   a P2MP path where a single source address has a very large number of
   destinations.
 *      
 *           0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                          Leaf type                            |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                     Source IPv4 address                       |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                  Destination IPv4 address                     |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    ~                           ...                                 ~
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                  Destination IPv4 address                     |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 *      
 *      
 * </pre>
 * @author Ayk
 * 
 * Venga si Messi.
 * 
 */
public class P2MPEndPointsIPv4 extends EndPoints{

	/*
	 * Leaf type
	 */
	private int leafType;
	
	
	/**
	 * Source IPv4 address
	 */
	private Inet4Address sourceIP;




	
	/**
	 * Destination IPv4 addresses
	 */
	private LinkedList <Inet4Address> destIPList;


	/**
	 * Constructs a new PCEP END-POINTS object IPv4 Type from scratch.
	 */
	public P2MPEndPointsIPv4() {
		super();
		destIPList=new LinkedList<Inet4Address> ();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_P2MP_ENDPOINTS_IPV4);
	}

	/**
	 * Constructs a new PCEP END-POINTS object IPv4 Type from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public P2MPEndPointsIPv4(byte []bytes, int offset)throws MalformedPCEPObjectException {
		super(bytes,offset);
		destIPList=new LinkedList<Inet4Address> ();
		decode();
	}

	/**
	 * Encode the IPv4 address
	 */
	public void encode() {
		this.ObjectLength=4+4+4+4*destIPList.size();



		this.object_bytes=new byte[ObjectLength];
		encode_header();
		
		//leafType
		this.object_bytes[4]=(byte)(leafType >>> 24);
		this.object_bytes[5]=(byte)(leafType >>> 16 & 0xff);
		this.object_bytes[6]=(byte)(leafType >>> 8 & 0xff);
		this.object_bytes[7]=(byte)(leafType & 0xff);	
		
		
		
		
		
		
		System.arraycopy(sourceIP.getAddress(),0, this.object_bytes, 8, 4);
		
		// TODO what if its size is 0?
		for (int i = 0; i < destIPList.size(); i++) {
			System.arraycopy(destIPList.get(i).getAddress(),0, this.object_bytes, 12+4*i, 4);			
		}
	}

	/**
	 * Decode the IPv4 address
	 */
	public void decode() throws MalformedPCEPObjectException {
		if (this.ObjectLength<16 || this.ObjectLength%4 != 0){
			log.info("object size doesnt match");
			throw new MalformedPCEPObjectException();
		}
		
		leafType=0;
		for (int k = 0; k < 4; k++) {
			leafType = (leafType << 8) | (this.object_bytes[k+4] & 0xff);
		}	
		log.info("leafType: "+leafType);
		
		
		int numDestId = (int)((this.ObjectLength-4-4-4)/4);
		log.info("num of Dest Ids: "+numDestId);

		byte[] ip=new byte[4]; 
		System.arraycopy(this.object_bytes,8, ip, 0, 4);
		try {
			sourceIP=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < numDestId; i++) {

			System.arraycopy(this.object_bytes,12+4*i, ip, 0, 4);
			try {
				destIPList.add((Inet4Address)Inet4Address.getByAddress(ip));
			} catch (UnknownHostException e) {

				e.printStackTrace();
			} 	 
		}
	}

	public Inet4Address getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(Inet4Address sourceIP) {
		this.sourceIP = sourceIP;
	}

	public Inet4Address getDestIP(int index) {
		return destIPList.get(index);
	}

	public void setDestIP(Inet4Address destIP) {
		this.destIPList.add(destIP);
	}

	public int getLeafType() {
		return leafType;
	}

	public void setLeafType(int leafType) {
		this.leafType = leafType;
	}

	public String toString(){
		StringBuffer sb=new StringBuffer(destIPList.size()*100);
		sb.append("<Leaf type: "+leafType);
		sb.append(" Source IP: "+sourceIP);
		for (int i=0;i<destIPList.size();++i){
			sb.append(" Destination IP"+i+": "+destIPList.get(i).toString());
		}
		sb.append(">");
		return sb.toString();
	}


}
