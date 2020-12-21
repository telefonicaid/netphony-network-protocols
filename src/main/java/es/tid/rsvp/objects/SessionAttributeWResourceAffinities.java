package es.tid.rsvp.objects;

import org.slf4j.Logger;

import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.RSVPProtocolViolationException;
import org.slf4j.LoggerFactory;

/**

<p>RFC 3209 RSVP-TE		Session Attribute with resource affinities Object</p>

<p>The Session Attribute Class is 207.  Two C_Types are defined,
   LSP_TUNNEL, C-Type = 7 and LSP_TUNNEL_RA, C-Type = 1.  The
   LSP_TUNNEL_RA C-Type includes all the same fields as the LSP_TUNNEL
   C-Type.  Additionally it carries resource affinity information.  The
   formats are as follows:

Format with resource affinities

    SESSION_ATTRIBUTE class = 207, LSP_TUNNEL_RA C-Type = 1

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Exclude-any                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Include-any                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Include-all                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Setup Prio  | Holding Prio  |     Flags     |  Name Length  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //          Session Name      (NULL padded display string)      //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Exclude-any

         A 32-bit vector representing a set of attribute filters
         associated with a tunnel any of which renders a link
         unacceptable.

      Include-any

         A 32-bit vector representing a set of attribute filters
         associated with a tunnel any of which renders a link acceptable
         (with respect to this test).  A null set (all bits set to zero)
         automatically passes.

      Include-all

         A 32-bit vector representing a set of attribute filters
         associated with a tunnel all of which must be present for a
         link to be acceptable (with respect to this test).  A null set
         (all bits set to zero) automatically passes.

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

public class SessionAttributeWResourceAffinities extends SessionAttribute{

	
	/**
	 *<p>A 32-bit vector representing a set of attribute filters
         associated with a tunnel any of which renders a link
         unacceptable.</p>
         
     */
	
	private long excludeAny;
	
	/**
	  <p>A 32-bit vector representing a set of attribute filters
         associated with a tunnel any of which renders a link acceptable
         (with respect to this test).  A null set (all bits set to zero)
         automatically passes.</p>
         
     */
	
	private long includeAny;
	
	/**
	 *<p>A 32-bit vector representing a set of attribute filters
         associated with a tunnel all of which must be present for a
         link to be acceptable (with respect to this test).  A null set
         (all bits set to zero) automatically passes. </p>
	 */
	
	private long includeAll;
	
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

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
  public SessionAttributeWResourceAffinities() {
	  super();
		cType = 1;
		
  }
  
	/**
	 * Constructor to be used when a new Session Attribute With Resource Affinities Object 
	 * wanted to be attached to a new message.
	 * @param excludeAny excludeAny
	 * @param includeAny includeAny
	 * @param includeAll includeAll
	 * @param setupPriority setupPriority
	 * @param holdingPriority holdingPriority
	 * @param flags flags
	 * @param sessionName sessionName
	 */
	
	public SessionAttributeWResourceAffinities(int excludeAny, int includeAny, int includeAll, int setupPriority, int holdingPriority, int flags, String sessionName){
		
		classNum = 207;
		cType = 1;
		
		this.excludeAny = excludeAny;
		this.includeAny = includeAny;
		this.includeAll = includeAll;
		this.setupPriority = setupPriority;
		this.holdingPriority = holdingPriority;
		this.flags = flags;
		this.sessionName = sessionName;
		
		
		
		log.debug("Session Attribute With Resource Affinities Object Created");

	}
	
	/**
	 * Constructor to be used when a new Session Attribute With Resource Affinities
	 * Object wanted to be decoded from a received message.
	 * @param bytes bytes
	 * @param offset offset
	 * @throws RSVPProtocolViolationException Exception when decoding the message
	 */
	
	public SessionAttributeWResourceAffinities(byte[] bytes, int offset) throws RSVPProtocolViolationException{
		super(bytes,offset);
		decode();
		log.debug("Session Attribute With Resource Affinities Object Created");
		
	}
	
	/**
	<p>
	0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Exclude-any                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Include-any                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Include-all                           |
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
		
		log.debug("Starting Session Attribute With Resource Affinities encode");
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 16;
		
		double sessionNameLength = sessionName.getBytes().length;
		
		this.nameLength = (int) sessionNameLength;
		
		int sessionName4BytesAlignLength = ((int) Math.ceil(sessionNameLength/4))*4; // Redondeo a 4 bytes
		length = length + sessionName4BytesAlignLength;
		this.bytes = new byte[this.getLength()];
		encodeHeader();
		int currentIndex = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		
		bytes[currentIndex] = (byte) ((excludeAny>>24) & 0xFF);
		bytes[currentIndex+1] = (byte) ((excludeAny>>16) & 0xFF);
		bytes[currentIndex+2] = (byte) ((excludeAny>>8) & 0xFF);
		bytes[currentIndex+3] = (byte) (excludeAny & 0xFF);

		currentIndex = currentIndex + 4;
		
		bytes[currentIndex] = (byte) ((includeAny>>24) & 0xFF);
		bytes[currentIndex+1] = (byte) ((includeAny>>16) & 0xFF);
		bytes[currentIndex+2] = (byte) ((includeAny>>8) & 0xFF);
		bytes[currentIndex+3] = (byte) (includeAny & 0xFF);
		
		currentIndex = currentIndex + 4;
		
		bytes[currentIndex] = (byte) ((includeAll>>24) & 0xFF);
		bytes[currentIndex+1] = (byte) ((includeAll>>16) & 0xFF);
		bytes[currentIndex+2] = (byte) ((includeAll>>8) & 0xFF);
		bytes[currentIndex+3] = (byte) (includeAll & 0xFF);

		currentIndex = currentIndex + 4;
		
		bytes[currentIndex] = (byte) setupPriority;
		bytes[currentIndex+1] = (byte) holdingPriority;
		bytes[currentIndex+2] = (byte) flags;
		bytes[currentIndex+3] = (byte) nameLength;
		
		currentIndex = currentIndex + 4;
		
		System.arraycopy(sessionName.getBytes(), 0, bytes, currentIndex, sessionName.getBytes().length);
		
		currentIndex = currentIndex + nameLength;
		
		byte[] padding = new byte[this.length-currentIndex];
		System.arraycopy(padding, 0, bytes, currentIndex, this.length-currentIndex);
		
		log.debug("Encoding Session Attribute With Resource Affinities accomplished");
		
		
	}
	
	/**
	<p>
	0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Exclude-any                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Include-any                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Include-all                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Setup Prio  | Holding Prio  |     Flags     |  Name Length  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //          Session Name      (NULL padded display string)      //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+</p>
	 */
	
	public void decode() throws RSVPProtocolViolationException{
		int offset=0;
		log.debug("Starting Session Attribute With Resource Affinities decode");

		int currentIndex = offset + RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		
		excludeAny = ByteHandler.decode4bytesLong(bytes, currentIndex);
				

		currentIndex = currentIndex + 4;
		
		includeAny = ByteHandler.decode4bytesLong(bytes, currentIndex);

		currentIndex = currentIndex + 4;
		
		includeAll = ByteHandler.decode4bytesLong(bytes, currentIndex);

		currentIndex = currentIndex + 4;
		
		setupPriority =  bytes[currentIndex]&0xFF;
		holdingPriority =  bytes[currentIndex+1]&0xFF;
		flags =  bytes[currentIndex+2]&0xFF;
		nameLength =  bytes[currentIndex+3]&0xFF;
		
		currentIndex = currentIndex + 4;
		
		byte[] sessionNameBytes = new byte[nameLength];
		System.arraycopy(bytes, currentIndex, sessionNameBytes, 0, nameLength);
		sessionName = new String(sessionNameBytes);
		
		log.debug("Decoding Session Attribute With Resource Affinities accomplished");
		
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

	public long getExcludeAny() {
		return excludeAny;
	}

	public void setExcludeAny(long excludeAny) {
		this.excludeAny = excludeAny;
	}

	public long getIncludeAny() {
		return includeAny;
	}

	public void setIncludeAny(long includeAny) {
		this.includeAny = includeAny;
	}

	public long getIncludeAll() {
		return includeAll;
	}

	public void setIncludeAll(long includeAll) {
		this.includeAll = includeAll;
	}


}
