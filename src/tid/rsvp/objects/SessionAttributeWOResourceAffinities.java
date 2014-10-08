package tid.rsvp.objects;

import java.util.logging.Logger;

import tid.rsvp.RSVPProtocolViolationException;

/**

<p>RFC 3209 RSVP-TE		Session Attribute without resource affinities Object</p>

<p>The Session Attribute Class is 207.  Two C_Types are defined,
   LSP_TUNNEL, C-Type = 7 and LSP_TUNNEL_RA, C-Type = 1.  The
   LSP_TUNNEL_RA C-Type includes all the same fields as the LSP_TUNNEL
   C-Type.  Additionally it carries resource affinity information.  The
   formats are as follows:

4.7.1. Format without resource affinities

   SESSION_ATTRIBUTE class = 207, LSP_TUNNEL C-Type = 7

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Setup Prio  | Holding Prio  |     Flags     |  Name Length  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //          Session Name      (NULL padded display string)      //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Setup Priority

         The priority of the session with respect to taking resources,
         in the range of 0 to 7.  The value 0 is the highest priority.
         The Setup Priority is used in deciding whether this session can
         preempt another session.

      Holding Priority

         The priority of the session with respect to holding resources,
         in the range of 0 to 7.  The value 0 is the highest priority.
         Holding Priority is used in deciding whether this session can
         be preempted by another session.

      Flags

         0x01  Local protection desired

               This flag permits transit routers to use a local repair
               mechanism which may result in violation of the explicit
               route object.  When a fault is detected on an adjacent
               downstream link or node, a transit router can reroute
               traffic for fast service restoration.

         0x02  Label recording desired

               This flag indicates that label information should be
               included when doing a route record.

         0x04  SE Style desired

               This flag indicates that the tunnel ingress node may
               choose to reroute this tunnel without tearing it down.
               A tunnel egress node SHOULD use the SE Style when
               responding with a Resv message.

      Name Length

         The length of the display string before padding, in bytes.

      Session Name

         A null padded string of characters.
	</p>

 */

public class SessionAttributeWOResourceAffinities extends SessionAttribute{

	/**
	 *<p>The priority of the session with respect to taking resources,
         in the range of 0 to 7.  The value 0 is the highest priority.
         The Setup Priority is used in deciding whether this session can
         preempt another session.</p>
	 */
	
	private int setupPriority;
	
	/**
	 *<p>The priority of the session with respect to holding resources,
         in the range of 0 to 7.  The value 0 is the highest priority.
         Holding Priority is used in deciding whether this session can
         be preempted by another session.</p>
	 */
		
	private int holdingPriority;
	
	/**
	 *<p>0x01  Local protection desired

               This flag permits transit routers to use a local repair
               mechanism which may result in violation of the explicit
               route object.  When a fault is detected on an adjacent
               downstream link or node, a transit router can reroute
               traffic for fast service restoration.

         0x02  Label recording desired

               This flag indicates that label information should be
               included when doing a route record.

         0x04  SE Style desired

               This flag indicates that the tunnel ingress node may
               choose to reroute this tunnel without tearing it down.
               A tunnel egress node SHOULD use the SE Style when
               responding with a Resv message.</p>
	 */
	
	private int flags;
	
	/**
	 * <p>The length of the display string before padding, in bytes.</p>
	 */
	
	private int nameLength;
	
	/**
	 * <p>A null padded string of characters.</p>
	 */
	
	private String sessionName;
	
	/**
	 * Log
	 */
		
	private Logger log;
	
	/**
	 * Constructor to be used when a new Session Attribute Without Resource Affinities Object 
	 * wanted to be attached to a new message.
	 * @param setupPriority
	 * @param holdingPriority
	 * @param flags
	 * @param sessionName
	 */
	
	public SessionAttributeWOResourceAffinities(int setupPriority, int holdingPriority, int flags, String sessionName){
		
		classNum = 207;
		cType = 7;
		
		this.setupPriority = setupPriority;
		this.holdingPriority = holdingPriority;
		this.flags = flags;
		this.sessionName = sessionName;
		
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 4;
		
		double sessionNameLength = sessionName.getBytes().length;
		
		this.nameLength = (int) sessionNameLength;
		
		int sessionName4BytesAlignLength = ((int) Math.ceil(sessionNameLength/4))*4; // Redondeo a 4 bytes
		length = length + sessionName4BytesAlignLength;
		
		log = Logger.getLogger("ROADM");

		log.finest("Session Attribute Without Resource Affinities Object Created");

	}
	
	/**
	 * Constructor to be used when a new Session Attribute Without Resource Affinities
	 * Object wanted to be decoded from a received message.
	 * @param bytes
	 * @param offset
	 */
	
	public SessionAttributeWOResourceAffinities(byte[] bytes, int offset){
		
		this.decodeHeader(bytes,offset);
		this.bytes = new byte[this.getLength()];
		
		log = Logger.getLogger("ROADM");

		log.finest("Session Attribute Without Resource Affinities Object Created");
		
	}
	
	/**
	<p> 0                   1                   2                   3
	    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	   |   Setup Prio  | Holding Prio  |     Flags     |  Name Length  |
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	   |                                                               |
	   //          Session Name      (NULL padded display string)      //
	   |                                                               |
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	</p>
	 */
	
	public void encode() throws RSVPProtocolViolationException{
		
		log.finest("Starting Session Attribute Without Resource Affinities encode");
		
		this.bytes = new byte[this.length];
		
		encodeHeader();
		int currentIndex = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		
		bytes[currentIndex] = (byte) setupPriority;
		bytes[currentIndex+1] = (byte) holdingPriority;
		bytes[currentIndex+2] = (byte) flags;
		bytes[currentIndex+3] = (byte) nameLength;
		
		currentIndex = currentIndex + 4;
		
		System.arraycopy(sessionName.getBytes(), 0, bytes, currentIndex, sessionName.getBytes().length);
		
		currentIndex = currentIndex + nameLength;
		
		byte[] padding = new byte[this.length-currentIndex];
		System.arraycopy(padding, 0, bytes, currentIndex, this.length-currentIndex);
		
		log.finest("Encoding Session Attribute Without Resource Affinities accomplished");
		
		
	}
	
	/**
	<p> 0                   1                   2                   3
	    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	   |   Setup Prio  | Holding Prio  |     Flags     |  Name Length  |
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	   |                                                               |
	   //          Session Name      (NULL padded display string)      //
	   |                                                               |
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	</p>
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException{

		log.finest("Starting Session Attribute Without Resource Affinities decode");

		int currentIndex = offset + RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		
		setupPriority = (int) bytes[currentIndex];
		holdingPriority = (int) bytes[currentIndex+1];
		flags = (int) bytes[currentIndex+2];
		nameLength = (int) bytes[currentIndex+3];
		
		currentIndex = currentIndex + 4;
		
		byte[] sessionNameBytes = new byte[nameLength];
		System.arraycopy(bytes, currentIndex, sessionNameBytes, 0, nameLength);
		sessionName = new String(sessionNameBytes);
		
		log.finest("Decoding Session Attribute Without Resource Affinities accomplished");
		
	}
	
	// Getters & Setters

	public int getSetupPriority() {
		return setupPriority;
	}

	public void setSetupPriority(int setupPriority) {
		this.setupPriority = setupPriority;
	}

	public int getHoldingPriority() {
		return holdingPriority;
	}

	public void setHoldingPriority(int holdingPriority) {
		this.holdingPriority = holdingPriority;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getNameLength() {
		return nameLength;
	}

	public void setNameLength(int nameLength) {
		this.nameLength = nameLength;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	


}
