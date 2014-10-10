package es.tid.pce.pcep.messages;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.*;

/**
 * PCEP OPEN Message (RFC 5440).
 * 
 * From RFC 5440 Section 6.2: 
 * 6.2. Open Message
 * 
 * The Open message is a PCEP message sent by a PCC to a PCE and by a
 * PCE to a PCC in order to establish a PCEP session.  The Message-Type
 * field of the PCEP common header for the Open message is set to 1.
 * 
 *   Once the TCP connection has been successfully established, the first
 *   message sent by the PCC to the PCE or by the PCE to the PCC MUST be
 *   an Open message as specified in Appendix A.
 *
 *   Any message received prior to an Open message MUST trigger a protocol
 *   error condition causing a PCErr message to be sent with Error-Type
 *   "PCEP session establishment failure" and Error-value "reception of an
 *   invalid Open message or a non Open message" and the PCEP session
 *   establishment attempt MUST be terminated by closing the TCP
 *   connection.
 *   
 *   The Open message is used to establish a PCEP session between the PCEP
 *   peers.  During the establishment phase, the PCEP peers exchange
 *   several session characteristics.  If both parties agree on such
 *   characteristics, the PCEP session is successfully established.
 *
 *   The format of an Open message is as follows:
 *
 *   <Open Message>::= <Common Header>
 *                     <OPEN>
 *
 *   The Open message MUST contain exactly one OPEN object (see
 *   Section 7.3).
 *
 *   Various session characteristics are specified within the OPEN object.
 *   Once the TCP connection has been successfully established, the sender
 *   MUST start an initialization timer called OpenWait after the
 *   expiration of which, if no Open message has been received, it sends a
 *   PCErr message and releases the TCP connection (see Appendix A for
 *   details).
 *
 *   Once an Open message has been sent to a PCEP peer, the sender MUST
 *   start an initialization timer called KeepWait after the expiration of
 *   which, if neither a Keepalive message has been received nor a PCErr
 *   message in case of disagreement of the session characteristics, a
 *   PCErr message MUST be sent and the TCP connection MUST be released
 *   (see Appendix A for details).
 *
 *   The OpenWait and KeepWait timers have a fixed value of 1 minute.
 *
 *   Upon the receipt of an Open message, the receiving PCEP peer MUST
 *   determine whether the suggested PCEP session characteristics are
 *   acceptable.  If at least one of the characteristics is not acceptable
 *   to the receiving peer, it MUST send an Error message.  The Error
 *   message SHOULD also contain the related OPEN object and, for each
 *   unacceptable session parameter, an acceptable parameter value SHOULD
 *   be proposed in the appropriate field of the OPEN object in place of
 *   the originally proposed value.  The PCEP peer MAY decide to resend an
 *   Open message with different session characteristics.  If a second
 *   Open message is received with the same set of parameters or with
 *   parameters that are still unacceptable, the receiving peer MUST send
 *   an Error message and it MUST immediately close the TCP connection.
 *   Details about error messages can be found in Section 7.15.
 *   Successive retries are permitted, but an implementation SHOULD make
 *   use of an exponential back-off session establishment retry procedure.
 *
 *   If the PCEP session characteristics are acceptable, the receiving
 *   PCEP peer MUST send a Keepalive message (defined in Section 6.3) that
 *   serves as an acknowledgment.
 *
 *   The PCEP session is considered as established once both PCEP peers
 *   have received a Keepalive message from their peer.
 *   
 * @author Oscar Gonzalez de Dios
 * @version 0.1
**/

//FIXME: TLVS????
public class PCEPOpen extends PCEPMessage {

	/**
	 * OPEN Object of the OPEN Messsage
	 */
	private OPEN open;
	
	/**
	 * Construct new PCEP Open message from scratch
	 */
	public PCEPOpen () {
		super();
		this.setMessageType(PCEPMessageTypes.MESSAGE_OPEN);
		open=new OPEN();
	}
	
	/**
	 * Construct new PCEP Open message from scratch
	 */
	public PCEPOpen (byte[] bytes)throws PCEPProtocolViolationException {
		super(bytes);
		decode();
		
	}

	/**
	 * 	
	 * @param session_id
	 */
	public void setSID(int session_id) {
		open.setSID(session_id);
	}
	public int getSID(){
		return open.getSID();
	}
	/**
	 * 
	 * @return Keepalive
	 */
	public int getKeepalive(){
		return open.getKeepalive();
	}
	
	/**
	 * 
	 * @param ka
	 */
	public void setKeepalive(int ka){
		 open.setKeeealive(ka);
	}
	
	/**
	 * 
	 * @return DeadTimer
	 */
	public int getDeadTimer(){
		return open.getDeadtimer();
		
	}
	
	public void setDeadTimer(int dt){
		open.setDeadtimer(dt);
		
	}
	
	
	/**
	 * 
	 */
	public String toString () {
		return open.toString();
	}

	@Override
	public void encode() throws PCEPProtocolViolationException {
		this.open.encode();
		this.setMessageLength(4+open.getLength());
		this.messageBytes=new byte[this.getLength()];
		encodeHeader();
		System.arraycopy(open.getBytes(), 0, messageBytes, 4, open.getLength());
	}
	
	
	public void decode()  throws PCEPProtocolViolationException {
		log.finest("Decoding PCEP OPEN Message");
		int offset=4;//We start after the object header
		if (offset>=this.getLength()){
			log.warning("Empty OPEN message");
			throw new PCEPProtocolViolationException();
		}
		if (PCEPObject.getObjectClass(this.messageBytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_OPEN){
			try {
				open=new OPEN(this.messageBytes,offset);
			} catch (MalformedPCEPObjectException e) {
				throw new PCEPProtocolViolationException();
			}			
		}
		else {
			throw new PCEPProtocolViolationException();
		}
	}


	
	public OPEN getOpen() {
		return open;
	}

	public void setOpen(OPEN open) {
		this.open = open;
	}
	
	
}