package es.tid.bgp.bgp4.messages;

/**
 * BGP KeepAlive Message (RFC 4271).
 * <p>From RFC 4271, Section 4.4</p>
 * <a href="https://tools.ietf.org/html/rfc4271">RFC 4271</a>.
 * 
 * KEEPALIVE Message Format 

   BGP does not use any TCP-based, keep-alive mechanism to determine if
   peers are reachable.  Instead, KEEPALIVE messages are exchanged
   between peers often enough not to cause the Hold Timer to expire.  A
   reasonable maximum time between KEEPALIVE messages would be one third
   of the Hold Time interval.  KEEPALIVE messages MUST NOT be sent more
   frequently than one per second.  An implementation MAY adjust the
   rate at which it sends KEEPALIVE messages as a function of the Hold
   Time interval.

   If the negotiated Hold Time interval is zero, then periodic KEEPALIVE
   messages MUST NOT be sent.

   A KEEPALIVE message consists of only the message header and has a
   length of 19 octets.
   
 * @author mcs
 *
 */
public class BGP4Keepalive extends BGP4Message{

	/**
	 * Create new Keepalive
	 */
	public BGP4Keepalive() {
		this.setMessageType(BGP4MessageTypes.MESSAGE_KEEPALIVE);
	}
	
	public BGP4Keepalive(byte[] bytes){
		super(bytes);
	}
	
	public void encode(){
		this.setMessageLength(BGPHeaderLength);
		this.messageBytes=new byte[this.getLength()];
		encodeHeader();
	}


}
