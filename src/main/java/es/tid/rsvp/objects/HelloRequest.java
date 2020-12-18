package es.tid.rsvp.objects;

import org.slf4j.Logger;

import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.RSVPProtocolViolationException;
import org.slf4j.LoggerFactory;

/**

<p>RFC 3209 RSVP-TE		Hello Request Object</p>

<p>5.2. HELLO Object formats

   The HELLO Class is 22.  There are two C_Types defined.

5.2.1. HELLO REQUEST object

   Class = HELLO Class, C_Type = 1

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Src_Instance                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Dst_Instance                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Src_Instance: 32 bits

      a 32 bit value that represents the sender's instance.  The
      advertiser maintains a per neighbor representation/value.  This
      value MUST change when the sender is reset, when the node reboots,
      or when communication is lost to the neighboring node and
      otherwise remains the same.  This field MUST NOT be set to zero
      (0).

      Dst_Instance: 32 bits

      The most recently received Src_Instance value received from the
      neighbor.  This field MUST be set to zero (0) when no value has
      ever been seen from the neighbor.


	</p>

 */

public class HelloRequest extends Hello{

	/**
	 *<p>A 32 bit value that represents the sender's instance.  The
      advertiser maintains a per neighbor representation/value.  This
      value MUST change when the sender is reset, when the node reboots,
      or when communication is lost to the neighboring node and
      otherwise remains the same.  This field MUST NOT be set to zero
      (0).</p>
	 */
	
	private long srcInstance;
	
	/**
	 *<p>The most recently received Src_Instance value received from the
      neighbor.  This field MUST be set to zero (0) when no value has
      ever been seen from the neighbor.</p>
	 */
		
	private long dstInstance;
	
	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
  public HelloRequest() {
	  classNum = 22;
		cType = 1;
  }
  
	/**
	 * Constructor to be used when a new Hello Request Object wanted to be attached 
	 * to a new message.
	 * @param srcInstance source instance
	 * @param dstInstance destination instance
	 */
	
	public HelloRequest(long srcInstance, long dstInstance){
		
		classNum = 22;
		cType = 1;
		
		this.srcInstance = srcInstance;
		this.dstInstance = dstInstance;

		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 8;

		log.debug("Hello Request Object Created");

	}
	
	/**
	 * Constructor to be used when a new Hello Request Object wanted to be decoded 
	 * from a received message.
	 * @param bytes bytes
	 * @param offset offset
	 * @throws RSVPProtocolViolationException Exception when decoding the message
	 */
	public HelloRequest(byte[] bytes, int offset) throws RSVPProtocolViolationException{
		super(bytes,offset);
		this.decodeHeader(bytes,offset);
		this.decode(bytes,offset);		
		log.debug("Hello Request Object Created");
		
	}
	
	/**
	<p>
	0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Src_Instance                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Dst_Instance                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	</p>
	 */
	
	public void encode() throws RSVPProtocolViolationException{
		
		log.debug("Starting Hello Request encode");
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 8;
		this.bytes = new byte[this.getLength()];
		encodeHeader();
		int currentIndex = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		
		bytes[currentIndex] = (byte)((srcInstance>>24) & 0xFF);
		bytes[currentIndex+1] = (byte)((srcInstance>>16) & 0xFF);
		bytes[currentIndex+2] = (byte)((srcInstance>>8) & 0xFF);
		bytes[currentIndex+3] = (byte)(srcInstance & 0xFF);
		
		currentIndex = currentIndex + 4;
		
		bytes[currentIndex] = (byte)((dstInstance>>24) & 0xFF);
		bytes[currentIndex+1] = (byte)((dstInstance>>16) & 0xFF);
		bytes[currentIndex+2] = (byte)((dstInstance>>8) & 0xFF);
		bytes[currentIndex+3] = (byte)(dstInstance & 0xFF);
		
		log.debug("Encoding Hello Request accomplished");
		
		
	}
	
	/**
	<p>
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Src_Instance                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Dst_Instance                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	</p>
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException{

		log.debug("Starting Hello Request decode");

		int currentIndex = offset + RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		
		srcInstance = ByteHandler.decode4bytesLong(bytes,currentIndex);
		
		currentIndex = currentIndex + 4;
		
		dstInstance = ByteHandler.decode4bytesLong(bytes,currentIndex);
		
		log.debug("Decoding Hello Request accomplished");
		
	}
	
	// Getters & Setters

	public long getSrcInstance() {
		return srcInstance;
	}

	public void setSrcInstance(long srcInstance) {
		this.srcInstance = srcInstance;
	}

	public long getDstInstance() {
		return dstInstance;
	}

	public void setDstInstance(long dstInstance) {
		this.dstInstance = dstInstance;
	}
	
	

}
