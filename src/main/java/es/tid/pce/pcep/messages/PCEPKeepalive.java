package es.tid.pce.pcep.messages;

import es.tid.pce.pcep.PCEPProtocolViolationException;

/**
 * PCEP Keepalive Message (RFC 5440).
 * 
 * From RFC 5440, Section 6.3
 * 6.3. Keepalive Message
 * 
 *  A Keepalive message is a PCEP message sent by a PCC or a PCE in order
 *  to keep the session in active state.  The Keepalive message is also
 *  used in response to an Open message to acknowledge that an Open
 *  message has been received and that the PCEP session characteristics
 *  are acceptable.  The Message-Type field of the PCEP common header for
 *  the Keepalive message is set to 2.  The Keepalive message does not
 *  contain any object.
 * 
 *  PCEP has its own keepalive mechanism used to ensure the liveness of
 *  the PCEP session.  This requires the determination of the frequency
 *  at which each PCEP peer sends Keepalive messages.  Asymmetric values
 *  may be chosen; thus, there is no constraint mandating the use of
 *  identical keepalive frequencies by both PCEP peers.  The DeadTimer is
 *  defined as the period of time after the expiration of which a PCEP
 *  peer declares the session down if no PCEP message has been received
 *  (Keepalive or any other PCEP message); thus, any PCEP message acts as
 *  a Keepalive message.  Similarly, there are no constraints mandating
 *  the use of identical DeadTimers by both PCEP peers.  The minimum
 *  Keepalive timer value is 1 second.  Deployments SHOULD consider
 *  carefully the impact of using low values for the Keepalive timer as
 *  these might not give rise to the expected results in periods of
 *  temporary network instability.
 *
 *  Keepalive messages are sent at the frequency specified in the OPEN
 *  object carried within an Open message according to the rules
 *  specified in Section 7.3.  Because any PCEP message may serve as
 *  Keepalive, an implementation may either decide to send Keepalive
 *  messages at fixed intervals regardless of whether other PCEP messages
 *  might have been sent since the last sent Keepalive message, or may
 *  decide to differ the sending of the next Keepalive message based on
 *  the time at which the last PCEP message (other than Keepalive) was
 *  sent.
 *
 *  Note that sending Keepalive messages to keep the session alive is
 *  optional, and PCEP peers may decide not to send Keepalive messages
 *  once the PCEP session is established; in which case, the peer that
 *  does not receive Keepalive messages does not expect to receive them
 *  and MUST NOT declare the session as inactive.
 *
 *  The format of a Keepalive message is as follows:
 *{@code
 *  <Keepalive Message>::= <Common Header>}
 *
 * @author Oscar Gonzalez de Dios
 * @version 0.1
 * 
**/
public class PCEPKeepalive extends PCEPMessage {
	//No elements inside
	
	/**
	 * Create new Keepalive
	 */
	public PCEPKeepalive() {
		this.setMessageType(PCEPMessageTypes.MESSAGE_KEEPALIVE);
	}
	
	public PCEPKeepalive(byte[] bytes) throws PCEPProtocolViolationException {
		super(bytes);
	}

	public void encode() throws PCEPProtocolViolationException {
		this.setMessageLength(4);
		this.messageBytes=new byte[this.getLength()];
		encodeHeader();
	}
	
	

	
}
