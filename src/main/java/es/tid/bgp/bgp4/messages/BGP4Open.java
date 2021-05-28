package es.tid.bgp.bgp4.messages;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

import es.tid.bgp.bgp4.open.BGP4CapabilitiesOptionalParameter;
import es.tid.bgp.bgp4.open.BGP4OptionalParameter;
import es.tid.bgp.bgp4.open.BGP4OptionalParametersTypes;


/**
 * BGP Open Message.
 * <p>
 * BGP Open Message Format (RFC 4271). From 
 * {link <a href="https://tools.ietf.org/html/rfc4271">RFC 4271</a>}
 * Section 4.2
 * 4.2. OPEN Message Format
 *    
 *   After a TCP connection is established, the first message sent by each
   side is an OPEN message.  If the OPEN message is acceptable, a
   KEEPALIVE message confirming the OPEN is sent back.

   In addition to the fixed-size BGP header, the OPEN message contains
   the following fields:
<pre>
       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
       +-+-+-+-+-+-+-+-+
       |    Version    |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |     My Autonomous System      |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |           Hold Time           |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                         BGP Identifier                        |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       | Opt Parm Len  |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                                                               |
       |             Optional Parameters (variable)                    |
       |                                                               |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
</pre>
      Version:

         This 1-octet unsigned integer indicates the protocol version
         number of the message.  The current BGP version number is 4.

      My Autonomous System:

         This 2-octet unsigned integer indicates the Autonomous System
         number of the sender.

      Hold Time:

         This 2-octet unsigned integer indicates the number of seconds
         the sender proposes for the value of the Hold Timer.  Upon
         receipt of an OPEN message, a BGP speaker MUST calculate the
         value of the Hold Timer by using the smaller of its configured
         Hold Time and the Hold Time received in the OPEN message.  The
         Hold Time MUST be either zero or at least three seconds.  An
         implementation MAY reject connections on the basis of the Hold


         Time.  The calculated value indicates the maximum number of
         seconds that may elapse between the receipt of successive
         KEEPALIVE and/or UPDATE messages from the sender.

      BGP Identifier:

         This 4-octet unsigned integer indicates the BGP Identifier of
         the sender.  A given BGP speaker sets the value of its BGP
         Identifier to an IP address that is assigned to that BGP
         speaker.  The value of the BGP Identifier is determined upon
         startup and is the same for every local interface and BGP peer.

      Optional Parameters Length:

         This 1-octet unsigned integer indicates the total length of the
         Optional Parameters field in octets.  If the value of this
         field is zero, no Optional Parameters are present.

      Optional Parameters:

         This field contains a list of optional parameters, in which
         each parameter is encoded as a Parameter Type, Parameter
         Length, Parameter Value triplet.
<pre>
         0                   1
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5
         +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-...
         |  Parm. Type   | Parm. Length  |  Parameter Value (variable)
         +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-...
</pre>
         Parameter Type is a one octet field that unambiguously
         identifies individual parameters.  Parameter Length is a one
         octet field that contains the length of the Parameter Value
         field in octets.  Parameter Value is a variable length field
         that is interpreted according to the value of the Parameter
         Type field.

         [RFC3392] defines the Capabilities Optional Parameter.

   The minimum length of the OPEN message is 29 octets (including the
   message header).
 * @author mcs
 *
 */
public class BGP4Open extends BGP4Message {
	/**
	 * version indicates the protocol version number of the message (current 4)
	 */
	private int version=0x04;//Default value
	
	/**
	 * myAutonomousSystem indicates the Autonomous System number of the sender
	 */
	private int myAutonomousSystem;
	/**
	 * hold Time the number of seconds the sender proposes for the value of the Hold Timer.
	 * The calculated value indicates the maximum number of seconds that may elapse between the receipt of successive
     * KEEPALIVE and/or UPDATE messages from the sender.
	 */
	private int holdTime=0x03;/** By default 3s */	
	/**
	 * BGPIdentifier indicates the BGP Identifier of the sender
	 * IP address that is assigned to that BGP speaker
	 */
	private Inet4Address BGPIdentifier;
	
	/**
	 * optional parameters
	 */
	private LinkedList<BGP4OptionalParameter> parametersList;
	
	/**
	 * Construct new PCEP Open message from scratch
	 */
	public BGP4Open () {
		super();
		this.setMessageType(BGP4MessageTypes.MESSAGE_OPEN);
		parametersList = new LinkedList<BGP4OptionalParameter>();
	}

	/**
	 * Construct new BGP4 Open message from scratch
	 * @param bytes bytes of the  BGP4 Open message to decode
	 */
	public BGP4Open (byte[] bytes) {//throws PCEPProtocolViolationException {
		super(bytes);		
		parametersList = new LinkedList<BGP4OptionalParameter>();
		decode();
		
	}

	public void encode(){// throws PCEPProtocolViolationException {
		//BGPOpenMessageMandatoryFileds=10;
		int len=BGPHeaderLength+10;		
		int optionalParameterLength = 0;
		int num_parameters = parametersList.size();
		for (int i=0;i<num_parameters;++i){
			BGP4OptionalParameter bgp4OptionalParameter = parametersList.get(i); 
			bgp4OptionalParameter.encode();
			len=len+parametersList.get(i).getLength();
			optionalParameterLength = optionalParameterLength + parametersList.get(i).getLength();
		}
		this.setMessageLength(len);
		messageBytes=new byte[len];
		encodeHeader();
		int offset = BGPHeaderLength;
		messageBytes[offset]=(byte) ((version) & 0xFF);// |( ((parentPCEIndicationBit?1:0)<<3 ) &0x08 ) | ((parentPCERequestBit?1:0<<4) &0x10 ) );
		offset++;
		messageBytes[offset]=(byte)(myAutonomousSystem>>>8 & 0xFF);
		messageBytes[offset+1]=(byte)(myAutonomousSystem & 0xFF);
		offset=offset+2;
		messageBytes[offset]=(byte)(holdTime>>>8 & 0xFF);
		messageBytes[offset+1]=(byte)(holdTime & 0xFF);
		offset=offset+2;
		System.arraycopy(BGPIdentifier.getAddress(),0, messageBytes, offset, 4);	
		offset=offset+4;
		messageBytes[offset] = (byte)(optionalParameterLength & 0xff);
		offset++;
		for (int i=0;i<parametersList.size();++i){
			System.arraycopy(parametersList.get(i).getBytes(), 0, messageBytes, offset, parametersList.get(i).getLength());
			offset=offset+parametersList.get(i).getLength();
		}
	
	}
	
	
	public void decode() {
		int offset = BGPHeaderLength;
		int optionalParameterLength = 0;
		version = (int)((messageBytes[offset] & 0xFF));		
		offset=offset+1;
		this.myAutonomousSystem=((  ((int)messageBytes[offset]) <<8)& 0xFF00) |  ((int)messageBytes[offset+1] & 0xFF);		
		offset=offset+2;
		this.holdTime=((  ((int)messageBytes[offset])   <<8)& 0xFF00) |  ((int)messageBytes[offset+1] & 0xFF);
		offset=offset+2;
		byte[] ip=new byte[4]; 
		System.arraycopy(this.messageBytes,offset, ip, 0, 4);
		try {
			BGPIdentifier=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		offset=offset+4;
		optionalParameterLength = (int)(messageBytes[offset] & 0xFF);
		offset++;
		if (optionalParameterLength != 0){
			
			
			int len= 0;
			while (len < optionalParameterLength){
				
				int optionalParameterType = BGP4OptionalParameter.getType(messageBytes, offset);
				int parameterLength = BGP4OptionalParameter.getLength(messageBytes, offset);				
				if (optionalParameterType == BGP4OptionalParametersTypes.CAPABILITY_OPTIONAL_PARAMETER){
					BGP4CapabilitiesOptionalParameter cop = new BGP4CapabilitiesOptionalParameter(this.getBytes(),offset);
					parametersList.add(cop);
				}
				offset = offset + parameterLength;
				len =  len +parameterLength;
			
			}

			
		}
		if (offset> this.getLength()){
			log.warn("Empty BGP OPEN message");
			//throw new PCEPProtocolViolationException();
		}
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getMyAutonomousSystem() {
		return myAutonomousSystem;
	}

	public void setMyAutonomousSystem(int myAutonomousSystem) {
		this.myAutonomousSystem = myAutonomousSystem;
	}

	public int getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(int holdTime) {
		this.holdTime = holdTime;
	}

	public Inet4Address getBGPIdentifier() {
		return BGPIdentifier;
	}

	public void setBGPIdentifier(Inet4Address bGPIdentifier) {
		BGPIdentifier = bGPIdentifier;
	}

	public LinkedList<BGP4OptionalParameter> getParametersList() {
		return parametersList;
	}

	public void setParametersList(LinkedList<BGP4OptionalParameter> parametersList) {
		this.parametersList = parametersList;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((BGPIdentifier == null) ? 0 : BGPIdentifier.hashCode());
		result = prime * result + holdTime;
		result = prime * result + myAutonomousSystem;
		result = prime * result + ((parametersList == null) ? 0 : parametersList.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BGP4Open other = (BGP4Open) obj;
		if (BGPIdentifier == null) {
			if (other.BGPIdentifier != null)
				return false;
		} else if (!BGPIdentifier.equals(other.BGPIdentifier))
			return false;
		if (holdTime != other.holdTime)
			return false;
		if (myAutonomousSystem != other.myAutonomousSystem)
			return false;
		if (parametersList == null) {
			if (other.parametersList != null)
				return false;
		} else if (!parametersList.equals(other.parametersList))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer((20)*400);
		sb.append("BGP Open Message: \n");
		sb.append("> Version: "+ version+"\n");
		sb.append("> MyAutonomousSystem: "+ myAutonomousSystem +"\n");
		sb.append("> HoldTime: "+ holdTime+"\n");
		sb.append("> BGPIdentifier: "+ BGPIdentifier+"\n");
		if (parametersList != null){
		for (int i=0;i<parametersList.size();++i){
			sb.append("> "+ parametersList.get(i).toString()+"\n");
		}
		}
		return sb.toString();
	}
	
}
