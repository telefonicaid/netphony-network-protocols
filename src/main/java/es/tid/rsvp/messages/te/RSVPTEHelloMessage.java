package es.tid.rsvp.messages.te;

import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.messages.RSVPMessage;
import es.tid.rsvp.messages.RSVPMessageTypes;
import es.tid.rsvp.objects.Hello;
import es.tid.rsvp.objects.HelloACK;
import es.tid.rsvp.objects.HelloRequest;
import es.tid.rsvp.objects.Integrity;
import es.tid.rsvp.objects.RSVPObject;
import es.tid.rsvp.objects.RSVPObjectParameters;
import org.slf4j.LoggerFactory;

/**
 * Hello Message.
<p>
   The RSVP Hello extension enables RSVP nodes to detect when a
   neighboring node is not reachable.  The mechanism provides node to
   node failure detection.  When such a failure is detected it is
   handled much the same as a link layer communication failure.  This
   mechanism is intended to be used when notification of link layer
   failures is not available and unnumbered links are not used, or when
   the failure detection mechanisms provided by the link layer are not
   sufficient for timely node failure detection.

   It should be noted that node failure detection is not the same as a
   link failure detection mechanism, particularly in the case of
   multiple parallel unnumbered links.

   The Hello extension is specifically designed so that one side can use
   the mechanism while the other side does not.  Neighbor failure
   detection may be initiated at any time.  This includes when neighbors
   first learn about each other, or just when neighbors are sharing Resv
   or Path state.

   The Hello extension is composed of a Hello message, a HELLO REQUEST
   object and a HELLO ACK object.  Hello processing between two
   neighbors supports independent selection of, typically configured,
   failure detection intervals.  Each neighbor can autonomously issue
   HELLO REQUEST objects.  Each request is answered by an
   acknowledgment.  Hello Messages also contain enough information so
   that one neighbor can suppress issuing hello requests and still
   perform neighbor failure detection.  A Hello message may be included
   as a sub-message within a bundle message.

   Neighbor failure detection is accomplished by collecting and storing
   a neighbor's "instance" value.  If a change in value is seen or if
   the neighbor is not properly reporting the locally advertised value,
   then the neighbor is presumed to have reset.  When a neighbor's value
   is seen to change or when communication is lost with a neighbor, then
   the instance value advertised to that neighbor is also changed.  The
   HELLO objects provide a mechanism for polling for and providing an
   instance value.  A poll request also includes the sender's instance
   value.  This allows the receiver of a poll to optionally treat the
   poll as an implicit poll response.  This optional handling is an
   optimization that can reduce the total number of polls and responses
   processed by a pair of neighbors.  In all cases, when both sides
   support the optimization the result will be only one set of polls and
   responses per failure detection interval.  Depending on selected
   intervals, the same benefit can occur even when only one neighbor
   supports the optimization.

5.1. Hello Message Format

   Hello Messages are always sent between two RSVP neighbors.  The IP
   source address is the IP address of the sending node.  The IP
   destination address is the IP address of the neighbor node.

   The HELLO mechanism is intended for use between immediate neighbors.
   When HELLO messages are being the exchanged between immediate
   neighbors, the IP TTL field of all outgoing HELLO messages SHOULD be
   set to 1.

   The Hello message has a Msg Type of 20.  The Hello message format is
   as follows:
{@code
      <Hello Message> ::= <Common Header> [ <INTEGRITY> ]
                              <HELLO>
  }
</p>
 * @author Fernando Munoz del Nuevo fmn@tid.es
 *
 */

public class RSVPTEHelloMessage extends RSVPMessage{

	private Integrity integrity;
	
	private Hello hello;
	
	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Constructor to be used when a new RSVP-TE Hello Message wanted to be sent
	 */
	
	public RSVPTEHelloMessage(){
		
		vers = 0x01;
		flags = 0x00;
		msgType = RSVPMessageTypes.MESSAGE_HELLO;
		rsvpChecksum = 0xFF;
		sendTTL = 0x00;
		reserved = 0x00;
	
		
		log.debug("RSVP-TE Hello Message Created");
	}
	
	/**
	 * Constructor to be used when an RSVP-TE Hello Message wanted to be decoded
	 * @param bytes bytes 
	 * @param length length 
	 * @throws RSVPProtocolViolationException RSVPProtocolViolationException
	 */ 
	
	public RSVPTEHelloMessage(byte[] bytes, int length) throws RSVPProtocolViolationException{		
		super(bytes);
		decode();		
		log.debug("RSVP-TE Hello Message Created");
	}
	
	
	
	/*
	 *<p> RSVP Common Header

                0             1              2             3
         +-------------+-------------+-------------+-------------+
         | Vers | Flags|  Msg Type   |       RSVP Checksum       |
         +-------------+-------------+-------------+-------------+
         |  Send_TTL   | (Reserved)  |        RSVP Length        |
         +-------------+-------------+-------------+-------------+
         
         The fields in the common header are as follows:

         Vers: 4 bits

              Protocol version number.  This is version 1.

         Flags: 4 bits

              0x01-0x08: Reserved

                   No flag bits are defined yet.

         Msg Type: 8 bits

              20 = Hello

 
         RSVP Checksum: 16 bits

              The one's complement of the one's complement sum of the
              message, with the checksum field replaced by zero for the
              purpose of computing the checksum.  An all-zero value
              means that no checksum was transmitted.

         Send_TTL: 8 bits

              The IP TTL value with which the message was sent.

         RSVP Length: 16 bits

              The total length of this RSVP message in bytes, including
              the common header and the variable-length objects that
              follow.
       </p>
     
	 */
	



	public void encode() throws RSVPProtocolViolationException {
		length = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		log.debug("RSVP-TE Hello Message Header encoding started");
		
		// Calculamos la longitud
		
		if(integrity != null){
			integrity.encode();
			length = length + integrity.getLength();
			log.debug("Integrity RSVP Object found");
			
		}if(hello != null){
			hello.encode();
			length = length + hello.getLength();
			log.debug("Hello RSVP Object found");
			
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.error("Hello RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
			
		}
		
		bytes = new byte[length];
		encodeHeader();
		int currentIndex = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
				
		if(integrity != null){
			
			//Campo Opcional
			
			System.arraycopy(integrity.getBytes(), 0, bytes, currentIndex, integrity.getLength());
			currentIndex = currentIndex + integrity.getLength();
			
		}
		
		// Campo Obligatorio
		
		System.arraycopy(hello.getBytes(), 0, bytes, currentIndex, hello.getLength());
		currentIndex = currentIndex + hello.getLength();
		
		log.debug("RSVP-TE Hello Message encoding accomplished");
		
	}
	
	public void decode() throws RSVPProtocolViolationException {
		
		decodeHeader();
		
		int offset = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		while(offset < length){		// Mientras quede mensaje
			
			int classNum = RSVPObject.getClassNum(bytes,offset);
			if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_INTEGRITY){
				
				// Integrity Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					integrity = new Integrity(bytes, offset);
					offset = offset + integrity.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}
				
			}else if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_HELLO){
				
				// Hello Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					hello = new HelloRequest(bytes,offset);
					hello.decode(bytes, offset);
					offset = offset + hello.getLength();
					
				}else if(cType == 2){
					
					hello = new HelloACK(bytes,offset);
					hello.decode(bytes, offset);
					offset = offset + hello.getLength();
					
				}
				else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}
				
			}else{
				
				// Fallo en classNum
				throw new RSVPProtocolViolationException();
				
			}
			
			
		}
		
	}

	public Integrity getIntegrity() {
		return integrity;
	}

	public void setIntegrity(Integrity integrity) {
		this.integrity = integrity;
	}

	public Hello getHello() {
		return hello;
	}

	public void setHello(Hello hello) {
		this.hello = hello;
	}

	
	
}
