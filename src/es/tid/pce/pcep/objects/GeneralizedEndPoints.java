package es.tid.pce.pcep.objects;


import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.AssistedUnicastEndpoints;
import es.tid.pce.pcep.constructs.FullAnycastEndpoints;
import es.tid.pce.pcep.constructs.P2MPEndpoints;
import es.tid.pce.pcep.constructs.P2PEndpoints;

/**
 * <p> Base abstract class for representing Generalized EndPoints Object.</p>
 * <p> It will follow draft-ietf-pce-gmpls-pcep-extensions-01 and GEYSERS
 * network service paradigms</p>
 * <pre>
 *ESTE LO HE REPETIDO PARA CADA SERVICIO DE CONECTIVIDAD GEYSERS  
 * The END-POINTS object is used in a PCReq message to specify the
 * source IP address and the destination IP address of the path for
 * which a path computation is requested.  The P flag of the END-POINTS
 * object MUST be set.  If the END-POINTS object is received with the P
 * flag cleared, the receiving peer MUST send a PCErr message with
 *  Error-Type=10 and Error-value=1.  The corresponding path computation
 *   request MUST be cancelled by the PCE without further notification.
 *   
 *   A new Object Type is defined
 *  to address the following possibilities:
 *
 *  o  Different endpoint types.
 *
 *  o  Label restrictions on the endpoint.
 *
 *  o  Specification of unnumbered endpoints type as seen in GMPLS
 *     networks.
 *
 * The Object is encoded as follow:
 *
 *      0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |      Reserved                                 | endpoint type |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      ~                           TLVs                                ~
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   Reserved bits should be set to 0 when a message is sent and ignored
   when the message is received

   the endpoint type is defined as follow:

   Value   Type                Meaning

   0       Point-to-Point

   1       Point-to-Multipoint New leaves to add

   2                           Old leaves to remove

   3                           Old leaves whose path can be
                               modified/reoptimized

   4                           Old leaves whose path must be left
                               unchanged

 *   
 * In GEYSERS the endpoint type field in the END-POINTS object will 
 * indicate the paradigm type (assisted unicast, restricted anycast,
 * full anycast) while the TLV type could indicate IPv4, IPv6, NSAP,
 * storage description, server description or application description.
 *   
 * </pre>  
 * @author Alejandro Tovar de Due�as (atovar@tid.es)
 *
 */
public class GeneralizedEndPoints extends EndPoints{
	
	private int generalizedendpointType;
	/**
	  * Posible Constructs
	  */
	
	private P2PEndpoints p2pEndpoints;
	
	private P2MPEndpoints p2mpEndpoints;
	
	private AssistedUnicastEndpoints assistedUnicastEndpoints;
	
	private FullAnycastEndpoints fullAnycastEndpoints;
	
	
	public GeneralizedEndPoints(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_GENERALIZED_ENDPOINTS);
	}
	public GeneralizedEndPoints(byte[] bytes, int offset) throws MalformedPCEPObjectException, PCEPProtocolViolationException{
		super(bytes, offset);
		decode();
	}
	/**
	 * Encode Generalized EndPoint Object
	 */
	public void encode() {
		int len=4+4;//The four bytes of the header plus the reserved and Endpoint type
		
		switch (generalizedendpointType){
		case ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_P2P:
			try {
				p2pEndpoints.encode();
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			len=len+p2pEndpoints.getLength();
			break;
		case ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_P2MP_NEW_LEAVES:
			try {
				p2mpEndpoints.encode();
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			len=len+p2mpEndpoints.getLength();
			break;
		case ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_ASSISTED_UNICAST:
			try {
				assistedUnicastEndpoints.encode();
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			len=len+assistedUnicastEndpoints.getLength();
			break;
		case ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_FULL_ANYCAST:
			try {
				fullAnycastEndpoints.encode();
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			len=len+fullAnycastEndpoints.getLength();
			break;
		}
		
		
		
		ObjectLength=len;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		this.object_bytes[4]=0x00;
		this.object_bytes[5]=0x00;
		this.object_bytes[6]=(byte)((generalizedendpointType>>8)&0xFF);
		this.object_bytes[7]=(byte)((generalizedendpointType)&0xFF);
		log.warning("Generalized End Points TYPE:"+generalizedendpointType);
		log.warning("Generalized End Points LENGTH:"+len);
		//System.arraycopy((byte)generalizedendpointType,0, this.object_bytes, 6, 2);
		int pos=8;
		if (generalizedendpointType==ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_P2P){
			System.arraycopy(p2pEndpoints.getBytes(),0, this.object_bytes, pos, p2pEndpoints.getLength());
			pos=pos+p2pEndpoints.getLength();
		}
		
		if (generalizedendpointType==ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_P2MP_NEW_LEAVES){
			System.arraycopy(p2mpEndpoints.getBytes(),0, this.object_bytes, pos, p2mpEndpoints.getLength());
			pos=pos+p2mpEndpoints.getLength();
		}
		
		if (generalizedendpointType==ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_ASSISTED_UNICAST){
			System.arraycopy(assistedUnicastEndpoints.getBytes(),0, this.object_bytes, pos, assistedUnicastEndpoints.getLength());
			pos=pos+assistedUnicastEndpoints.getLength();
		}
		
		if (generalizedendpointType==ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_FULL_ANYCAST){
			System.arraycopy(fullAnycastEndpoints.getBytes(),0, this.object_bytes, pos, fullAnycastEndpoints.getLength());
			pos=pos+fullAnycastEndpoints.getLength();
		}
		
	}
	
	/**
	 * Decode Generalized EndPoint object
	 */
	public void decode() throws MalformedPCEPObjectException {
		if (ObjectLength<=8){
			log.warning("Generalized End Points does not have TLVs");
			throw new MalformedPCEPObjectException();
		}
		int offset=4;
		generalizedendpointType=this.object_bytes[offset+3]&0xFF;
		offset=8;
		
		if (generalizedendpointType==ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_P2P){		
			try {
				p2pEndpoints= new P2PEndpoints(this.object_bytes,offset); // no deber�a adem�s rellenar la tLVList??
				offset= offset + p2pEndpoints.getLength();
			} catch (PCEPProtocolViolationException e) {
				throw new MalformedPCEPObjectException();
			}
		}
		
		if (generalizedendpointType==ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_P2MP_NEW_LEAVES){		
			try {
				p2mpEndpoints= new P2MPEndpoints(this.object_bytes,offset);
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // no deber�a adem�s rellenar la tLVList??
			offset= offset + p2mpEndpoints.getLength();
		}
		
		if (generalizedendpointType==ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_ASSISTED_UNICAST){		
			try {
				assistedUnicastEndpoints= new AssistedUnicastEndpoints(this.object_bytes,offset);
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // no deber�a adem�s rellenar la tLVList?? 
			offset= offset + assistedUnicastEndpoints.getLength();
		}
		
		if (generalizedendpointType==ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_FULL_ANYCAST){		
			try {
				fullAnycastEndpoints= new FullAnycastEndpoints(this.object_bytes,offset);
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // no deber�a adem�s rellenar la tLVList?? 
			offset= offset + fullAnycastEndpoints.getLength();
		}
		
	}

	//Getters and Setters

	public int getGeneralizedEndPointsType() {
		return generalizedendpointType;
	}

	public void setGeneralizedEndPointsType(int generalizedendpointType) {
		this.generalizedendpointType = generalizedendpointType;
	}

	public P2PEndpoints getP2PEndpoints() {
		return p2pEndpoints;
	}

	public void setP2PEndpoints(P2PEndpoints P2PEndpoints) {
		this.p2pEndpoints = P2PEndpoints;
		this.generalizedendpointType = ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_P2P ;
	}
	
	public P2MPEndpoints getP2MPEndpoints() {
		return p2mpEndpoints;
	}

	//FIXME
	public void setP2MPEndpoints(P2MPEndpoints P2PMEndpoints) {
		this.p2mpEndpoints = P2PMEndpoints;
		this.generalizedendpointType = ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_P2MP_NEW_LEAVES;
	}
	
	public AssistedUnicastEndpoints getAssistedUnicastEndpoints() {
		return assistedUnicastEndpoints;
	}

	public void setAssistedUnicastEndpoints(AssistedUnicastEndpoints AssistedUnicastEndpoints) {
		this.assistedUnicastEndpoints = AssistedUnicastEndpoints;
		this.generalizedendpointType = 3;
	}
	
	public String toString(){
		if (p2pEndpoints!=null){
			return p2pEndpoints.toString();
		}
		else {
			return "<GEP unknown>";
		}
	}
	
}