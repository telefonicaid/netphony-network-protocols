package tid.rsvp.messages;

import java.util.LinkedList;
import java.util.logging.Logger;
import tid.rsvp.RSVPProtocolViolationException;
import tid.rsvp.constructs.FFFlowDescriptor;
import tid.rsvp.constructs.FlowDescriptor;
import tid.rsvp.constructs.SEFlowDescriptor;
import tid.rsvp.constructs.WFFlowDescriptor;
import tid.rsvp.objects.Integrity;
import tid.rsvp.objects.PolicyData;
import tid.rsvp.objects.RSVPHop;
import tid.rsvp.objects.RSVPHopIPv4;
import tid.rsvp.objects.RSVPHopIPv6;
import tid.rsvp.objects.RSVPObject;
import tid.rsvp.objects.ResvConfirm;
import tid.rsvp.objects.ResvConfirmIPv4;
import tid.rsvp.objects.ResvConfirmIPv6;
import tid.rsvp.objects.Scope;
import tid.rsvp.objects.ScopeIPv4;
import tid.rsvp.objects.ScopeIPv6;
import tid.rsvp.objects.Session;
import tid.rsvp.objects.SessionIPv4;
import tid.rsvp.objects.SessionIPv6;
import tid.rsvp.objects.Style;
import tid.rsvp.objects.TimeValues;

/*

RFC 2205                          RSVP                    September 1997

      3.1.4 Resv Messages

         Resv messages carry reservation requests hop-by-hop from
         receivers to senders, along the reverse paths of data flows for
         the session.  The IP destination address of a Resv message is
         the unicast address of a previous-hop node, obtained from the
         path state.  The IP source address is an address of the node
         that sent the message.

         The Resv message format is as follows:

           <Resv Message> ::= <Common Header> [ <INTEGRITY> ]

                                   <SESSION>  <RSVP_HOP>

                                   <TIME_VALUES>

                                   [ <RESV_CONFIRM> ]  [ <SCOPE> ]

                                   [ <POLICY_DATA> ... ]

                                   <STYLE> <flow descriptor list>

           <flow descriptor list> ::=  <empty> |

                            <flow descriptor list> <flow descriptor>


         If the INTEGRITY object is present, it must immediately follow
         the common header.  The STYLE object followed by the flow
         descriptor list must occur at the end of the message, and
         objects within the flow descriptor list must follow the BNF
         given below.  There are no other requirements on transmission
         order, although the above order is recommended.

         The NHOP (i.e., the RSVP_HOP) object contains the IP address of
         the interface through which the Resv message was sent and the
         LIH for the logical interface on which the reservation is
         required.

         The appearance of a RESV_CONFIRM object signals a request for a
         reservation confirmation and carries the IP address of the
         receiver to which the ResvConf should be sent.  Any number of
         POLICY_DATA objects may appear.

         The BNF above defines a flow descriptor list as simply a list
         of flow descriptors.  The following style-dependent rules
         specify in more detail the composition of a valid flow
         descriptor list for each of the reservation styles.

         o    WF Style:

                <flow descriptor list> ::=  <WF flow descriptor>

                <WF flow descriptor> ::= <FLOWSPEC>


         o    FF style:

                <flow descriptor list> ::=

                          <FLOWSPEC>  <FILTER_SPEC>  |

                          <flow descriptor list> <FF flow descriptor>

                <FF flow descriptor> ::=

                          [ <FLOWSPEC> ] <FILTER_SPEC>



              Each elementary FF style request is defined by a single
              (FLOWSPEC, FILTER_SPEC) pair, and multiple such requests
              may be packed into the flow descriptor list of a single
              Resv message.  A FLOWSPEC object can be omitted if it is
              identical to the most recent such object that appeared in
              the list; the first FF flow descriptor must contain a
              FLOWSPEC.

         o    SE style:

                <flow descriptor list> ::= <SE flow descriptor>

                <SE flow descriptor> ::=

                                       <FLOWSPEC> <filter spec list>

                <filter spec list> ::=  <FILTER_SPEC>

                                  |  <filter spec list> <FILTER_SPEC>


         The reservation scope, i.e., the set of senders towards which a
         particular reservation is to be forwarded (after merging), is
         determined as follows:

         o    Explicit sender selection

              The reservation is forwarded to all senders whose
              SENDER_TEMPLATE objects recorded in the path state match a
              FILTER_SPEC object in the reservation.  This match must
              follow the rules of Section 3.2.

         o    Wildcard sender selection

              A request with wildcard sender selection will match all
              senders that route to the given outgoing interface.

              Whenever a Resv message with wildcard sender selection is
              forwarded to more than one previous hop, a SCOPE object
              must be included in the message (see Section 3.4 below);
              in this case, the scope for forwarding the reservation is
              constrained to just the sender IP addresses explicitly
              listed in the SCOPE object.

              A Resv message that is forwarded by a node is generally
              the result of merging a set of incoming Resv messages
              (that are not blockaded; see Section 3.5).  If one of
              these merged messages contains a RESV_CONFIRM object and
              has a FLOWSPEC larger than the FLOWSPECs of the other
              merged reservation requests, then this RESV_CONFIRM object
              is forwarded in the outgoing Resv message.  A RESV_CONFIRM
              object in one of the other merged requests (whose
              flowspecs are equal to, smaller than, or incomparable to,
              the merged flowspec, and which is not blockaded) will
              trigger the generation of an ResvConf message containing
              the RESV_CONFIRM.  A RESV_CONFIRM object in a request that
              is blockaded will be neither forwarded nor returned; it
              will be dropped in the current node.

 */

public class RSVPResvMessage extends RSVPMessage {

	/*
	 *   RSVP Common Header

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

              1 = Path

              2 = Resv

              3 = PathErr

              4 = ResvErr

              5 = PathTear

              6 = ResvTear

              7 = ResvConf

         RSVP Checksum: 16 bits

              The one's complement of the one's complement sum of the
              message, with the checksum field replaced by zero for the
              purpose of computing the checksum.  An all-zero value
              means that no checksum was transmitted.

         Send_TTL: 8 bits

              The IP TTL value with which the message was sent.  See
              Section 3.8.

         RSVP Length: 16 bits

              The total length of this RSVP message in bytes, including
              the common header and the variable-length objects that
              follow.
         
         The Resv message format is as follows:

           <Resv Message> ::= <Common Header> [ <INTEGRITY> ]

                                   <SESSION>  <RSVP_HOP>

                                   <TIME_VALUES>

                                   [ <RESV_CONFIRM> ]  [ <SCOPE> ]

                                   [ <POLICY_DATA> ... ]

                                   <STYLE> <flow descriptor list>

           <flow descriptor list> ::=  <empty> |

                            <flow descriptor list> <flow descriptor>
         
	 */
	
	protected Integrity integrity;
	protected Session session;
	protected RSVPHop rsvpHop;
	protected TimeValues timeValues;
	protected ResvConfirm resvConfirm;
	protected Scope scope;
	protected LinkedList<PolicyData> policyData;
	protected Style style;
	protected LinkedList<FlowDescriptor> flowDescriptors;

	/**
	 * Log
	 */
		
	private Logger log;
	
	/**
	 * 
	 */
	
	public RSVPResvMessage(){
		
		vers = 0x01;
		flags = 0x00;
		msgType = RSVPMessageTypes.MESSAGE_RESV;
		rsvpChecksum = 0xFF;
		sendTTL = 0x00;
		reserved = 0x00;
		length = tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		
		policyData = new LinkedList<PolicyData>();
		flowDescriptors = new LinkedList<FlowDescriptor>();
		
		log = Logger.getLogger("ROADM");

		log.finest("RSVP Resv Message Created");
	}
	
	/**
	 * 
	 * @param bytes
	 * @param length
	 */
	
	public RSVPResvMessage(byte[] bytes, int length){
		
		this.bytes = bytes;
		this.length = length;
		policyData = new LinkedList<PolicyData>();
		flowDescriptors = new LinkedList<FlowDescriptor>();
		
		log = Logger.getLogger("ROADM");

		log.finest("RSVP Resv Message Created");
	}
	
	@Override
	public void encodeHeader() {

		bytes[0]= (byte)(((vers<<4) &0xF0) | (flags & 0x0F));
		bytes[1]= (byte) msgType;
		bytes[2]= (byte)((rsvpChecksum>>8) & 0xFF);
		bytes[3]= (byte)(rsvpChecksum & 0xFF);
		bytes[4]= (byte) sendTTL;
		bytes[5]= (byte) reserved;
		bytes[6]= (byte)((length>>8) & 0xFF);
		bytes[7]= (byte)(length & 0xFF);
		
	}

	@Override
	public void encode() throws RSVPProtocolViolationException {

		log.finest("Starting RSVP Resv Message encode");
		
		// Obtengo el tama�o de la cabecera comun
		int commonHeaderSize = tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		
		// Obtencion del tama�o completo del mensaje
		
		if(integrity != null){
			
			length = length + integrity.getLength();
			log.finest("Integrity RSVP Object found");
			
		}
		if(session != null){
			
			length = length + session.getLength();
			log.finest("Session RSVP Object found");
			
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.severe("Session RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
			
		}
		if(rsvpHop != null){
			
			length = length + rsvpHop.getLength();
			log.finest("Hop RSVP Object found");
			
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.severe("Hop RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
			
		}
		if(timeValues != null){
			
			length = length + timeValues.getLength();
			log.finest("Time Values RSVP Object found");
			
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.severe("Time Values RSVP Object NOT found");
			throw new RSVPProtocolViolationException();			
			
		}
		if(resvConfirm != null){
			
			length = length + resvConfirm.getLength();
			log.finest("ResvConfirm RSVP Object found");
			
		}
		if(scope != null){
			
			length = length + scope.getLength();
			log.finest("Scope RSVP Object found");
			
		}
		
		int pdSize = policyData.size();
				
		for(int i = 0; i < pdSize; i++){
				
			PolicyData pd = (PolicyData) policyData.get(i);
			length = length + pd.getLength();
			log.finest("Policy Data RSVP Object found");
				
		}					
		
		if(style != null){
			
			length = length + style.getLength();
			log.finest("Style RSVP Object found");
			
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.severe("Style RSVP Object NOT found");
			throw new RSVPProtocolViolationException();			
			
		}
		
		int fdSize = flowDescriptors.size();

		for(int i = 0; i < fdSize; i++){
			
			FlowDescriptor fd = (FlowDescriptor) flowDescriptors.get(i);
			length = length + fd.getLength();
			log.finest("Sender Descriptor RSVP Construct found");
			
		}
		
		bytes = new byte[length];
		encodeHeader();
		int currentIndex = commonHeaderSize;
		
		if(integrity != null){
			//Campo Opcional
			integrity.encode();
			System.arraycopy(integrity.getBytes(), 0, bytes, currentIndex, integrity.getLength());
			currentIndex = currentIndex + integrity.getLength();
			
		}
		
		// Campo Obligatorio
		session.encode();
		System.arraycopy(session.getBytes(), 0, bytes, currentIndex, session.getLength());
		currentIndex = currentIndex + session.getLength();
		// Campo Obligatorio
		rsvpHop.encode();
		System.arraycopy(rsvpHop.getBytes(), 0, bytes, currentIndex, rsvpHop.getLength());
		currentIndex = currentIndex + rsvpHop.getLength();
		// Campo Obligatorio
		timeValues.encode();
		System.arraycopy(timeValues.getBytes(), 0, bytes, currentIndex, timeValues.getLength());
		currentIndex = currentIndex + timeValues.getLength();
		
		if(resvConfirm != null){
			
			//Campo Opcional
			resvConfirm.encode();
			System.arraycopy(resvConfirm.getBytes(), 0, bytes, currentIndex, resvConfirm.getLength());
			currentIndex = currentIndex + resvConfirm.getLength();
			
		}
		if(scope != null){
			
			//Campo Opcional
			scope.encode();
			System.arraycopy(scope.getBytes(), 0, bytes, currentIndex, scope.getLength());
			currentIndex = currentIndex + scope.getLength();
			
		}
		// Campos Opcionales
		for(int i = 0; i < pdSize; i++){
				
			PolicyData pd = (PolicyData) policyData.get(i);
			pd.encode();
			System.arraycopy(pd.getBytes(), 0, bytes, currentIndex, pd.getLength());
			currentIndex = currentIndex + pd.getLength();
							
		}
	
		// Campo Obligatorio
		style.encode();
		System.arraycopy(style.getBytes(), 0, bytes, currentIndex, style.getLength());
		currentIndex = currentIndex + style.getLength();
		
		// Lista de Flow Descriptors
		for(int i = 0; i < fdSize; i++){
			
			FlowDescriptor fd = (FlowDescriptor) flowDescriptors.get(i);
			try{
				fd.encode();
				System.arraycopy(fd.getBytes(), 0, bytes, currentIndex, fd.getLength());
				currentIndex = currentIndex + fd.getLength();

			}catch(RSVPProtocolViolationException e){
				
				log.severe("Errors during Flow Descriptor number "+i+" encoding");
				
			}
		}
		log.finest("RSVP Resv Message encoding accomplished");
	}

	@Override
	public void decode() throws RSVPProtocolViolationException {
	
		decodeHeader();
		
		int offset = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		while(offset < length){		// Mientras quede mensaje
			
			int classNum = RSVPObject.getClassNum(bytes,offset);
			if(classNum == 1){
				
				// Session Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					// Session IPv4
					session = new SessionIPv4();
					session.decode(bytes, offset);
					offset = offset + session.getLength();
					
				}else if(cType == 2){
					
					// Session IPv6
					session = new SessionIPv6();
					session.decode(bytes, offset);
					offset = offset + session.getLength();
				}else{
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}
			}
			else if(classNum == 3){
				
				// RSVPHop Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					// RSVPHop IPv4
					rsvpHop = new RSVPHopIPv4();
					rsvpHop.decode(bytes, offset);
					offset = offset + rsvpHop.getLength();
					
				}else if(cType == 2){
					// RSVPHop IPv6
					rsvpHop = new RSVPHopIPv6();
					rsvpHop.decode(bytes, offset);
					offset = offset + rsvpHop.getLength();
					
				}else{
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}				
			}
			else if(classNum == 4){
				
				// Integrity Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					integrity = new Integrity();
					integrity.decode(bytes, offset);
					offset = offset + integrity.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}
			}
			else if(classNum == 5){
				
				// TimeValues Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					timeValues = new TimeValues();
					timeValues.decode(bytes, offset);
					offset = offset + timeValues.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}				
			}else if(classNum == 15){
				
				// Resv Confirm Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					// ResvConfirm IPv4
					resvConfirm = new ResvConfirmIPv4();
					resvConfirm.decode(bytes, offset);
					offset = offset + resvConfirm.getLength();
					
				}else if(cType == 2){
					
					// ResvConfirm IPv6
					resvConfirm = new ResvConfirmIPv6();
					resvConfirm.decode(bytes, offset);
					offset = offset + resvConfirm.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}
			}else if(classNum == 7){
				
				// Scope Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					// Scope IPv4
					scope = new ScopeIPv4();
					scope.decode(bytes, offset);
					offset = offset + scope.getLength();
					
				}else if(cType == 2){
					
					// Scope IPv6
					scope = new ScopeIPv6();
					scope.decode(bytes, offset);
					offset = offset + scope.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}
			}else if(classNum == 13){
				
				// Policy Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					PolicyData pd = new PolicyData();
					pd.decode(bytes, offset);
					offset = offset + pd.getLength();
					policyData.add(pd);
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}				
			}
			else if(classNum == 8){
				
				// Style Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					style = new Style();
					style.decode(bytes, offset);
					offset = offset + style.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}				
			}else if(classNum == 9){
				// Flow Descriptor Construct
				if(style != null){	// Style debe haberse decodificado porque en caso
									// contrario el mensaje no se habra construido bien
					if(style.getOptionVector()==tid.rsvp.objects.RSVPObjectParameters.RSVP_STYLE_OPTION_VECTOR_FF_STYLE){
						
						// Decodifico el primero que es distinto en su formacion.
						FFFlowDescriptor fffd = new FFFlowDescriptor(true);
						fffd.decode(bytes, offset);
						offset = offset + fffd.getLength();
						flowDescriptors.add(fffd);
						while(offset < length){		// Mientras quede mensaje
						
							// Decodifico los siguientes
							FFFlowDescriptor fffd2 = new FFFlowDescriptor(false);
							fffd2.decode(bytes, offset);
							offset = offset + fffd2.getLength();
							flowDescriptors.add(fffd2);
						}
					}else if(style.getOptionVector()==tid.rsvp.objects.RSVPObjectParameters.RSVP_STYLE_OPTION_VECTOR_WF_STYLE){

						// Los Flow Descriptor WF son todos iguales
						while(offset < length){		// Mientras quede mensaje
							WFFlowDescriptor wffd = new WFFlowDescriptor();
							wffd.decode(bytes, offset);
							offset = offset + wffd.getLength();
							flowDescriptors.add(wffd);
						}
					}else if(style.getOptionVector()==tid.rsvp.objects.RSVPObjectParameters.RSVP_STYLE_OPTION_VECTOR_SE_STYLE){

						// Los Flow Descriptor SE son todos iguales
						while(offset < length){		// Mientras quede mensaje
							SEFlowDescriptor sefd = new SEFlowDescriptor();
							sefd.decode(bytes, offset);
							offset = offset + sefd.getLength();
							flowDescriptors.add(sefd);
						}
					}
				}else{
					
					// Malformed Resv Message
					log.severe("Malformed RSVP Resv Message, Style Object not Found");
					throw new RSVPProtocolViolationException();
					
				}
			}	
			else{
				
				// Fallo en classNum
				log.severe("Malformed RSVP Resv Message, Object classNum incorrect");
				throw new RSVPProtocolViolationException();
				
			}
		}
	}
	
	public void addFlowDescriptor(FFFlowDescriptor flowDescriptor){
		
		flowDescriptors.add(flowDescriptor);

	}

	public Integrity getIntegrity() {
		return integrity;
	}

	public void setIntegrity(Integrity integrity) {
		this.integrity = integrity;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public RSVPHop getRsvpHop() {
		return rsvpHop;
	}

	public void setRsvpHop(RSVPHop rsvpHop) {
		this.rsvpHop = rsvpHop;
	}

	public TimeValues getTimeValues() {
		return timeValues;
	}

	public void setTimeValues(TimeValues timeValues) {
		this.timeValues = timeValues;
	}

	public ResvConfirm getResvConfirm() {
		return resvConfirm;
	}

	public void setResvConfirm(ResvConfirm resvConfirm) {
		this.resvConfirm = resvConfirm;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public LinkedList<PolicyData> getPolicyData() {
		return policyData;
	}

	public void setPolicyData(LinkedList<PolicyData> policyData) {
		this.policyData = policyData;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public LinkedList<FlowDescriptor> getFlowDescriptors() {
		return flowDescriptors;
	}

	public void setFlowDescriptors(LinkedList<FlowDescriptor> flowDescriptors) {
		this.flowDescriptors = flowDescriptors;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
	

}