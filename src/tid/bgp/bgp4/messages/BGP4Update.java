package tid.bgp.bgp4.messages;

import java.util.ArrayList;

import tid.bgp.bgp4.update.fields.LinkNLRI;
import tid.bgp.bgp4.update.fields.LinkStateNLRI;
import tid.bgp.bgp4.update.fields.NLRI;
import tid.bgp.bgp4.update.fields.NLRITypes;
import tid.bgp.bgp4.update.fields.NodeNLRI;
import tid.bgp.bgp4.update.fields.PathAttribute;
import tid.bgp.bgp4.update.fields.WithdrawnRoutes;
import tid.bgp.bgp4.update.fields.pathAttributes.AFICodes;
import tid.bgp.bgp4.update.fields.pathAttributes.AS_Path_Attribute;
import tid.bgp.bgp4.update.fields.pathAttributes.BGP_LS_MP_Reach_Attribute;
import tid.bgp.bgp4.update.fields.pathAttributes.Generic_MP_Reach_Attribute;
import tid.bgp.bgp4.update.fields.pathAttributes.LinkStateAttribute;
import tid.bgp.bgp4.update.fields.pathAttributes.MP_Reach_Attribute;
import tid.bgp.bgp4.update.fields.pathAttributes.OriginAttribute;
import tid.bgp.bgp4.update.fields.pathAttributes.PathAttributesTypeCode;


/**
 * <h1> BGP4 Update Message (RFC 4271). </h1> 
 * <p> From RFC 4271 Section 4.3. </p>
 * <a href="https://tools.ietf.org/html/rfc4271">RFC 4271</a>.
 * 4.3.  UPDATE Message Format
 * 
 * UPDATE messages are used to transfer routing information between BGP
   peers.  The information in the UPDATE message can be used to
   construct a graph that describes the relationships of the various
   Autonomous Systems.  By applying rules to be discussed, routing
   information loops and some other anomalies may be detected and
   removed from inter-AS routing.

   An UPDATE message is used to advertise feasible routes that share
   common path attributes to a peer, or to withdraw multiple unfeasible
   routes from service (see 3.1).  An UPDATE message MAY simultaneously
   advertise a feasible route and withdraw multiple unfeasible routes
   from service.  The UPDATE message always includes the fixed-size BGP
   header, and also includes the other fields, as shown below (note,
   some of the shown fields may not be present in every UPDATE message):
<pre>
      +-----------------------------------------------------+
      |   Withdrawn Routes Length (2 octets)                |
      +-----------------------------------------------------+
      |   Withdrawn Routes (variable)                       |
      +-----------------------------------------------------+
      |   Total Path Attribute Length (2 octets)            |
      +-----------------------------------------------------+
      |   Path Attributes (variable)                        |
      +-----------------------------------------------------+
      |   Network Layer Reachability Information (variable) |
      +-----------------------------------------------------+
</pre>
 * @author mcs,pac
 *
 */
public class BGP4Update extends BGP4Message {
	
	/** Used to check the origin of update message */
	private String learntFrom;
	
	/**
	 * Total length of the Withdrawn Routes field in octets.  
	 */
	private int withdrawnRoutesLength;
	/**
	 * List of IP address prefixes for the routes that are being withdrawn from service.
	 */
	private WithdrawnRoutes withdrawnRoutes;
	/**
	 * Total length of the Path Attributes field in octets
	 */
	private int totalPathAttibuteLength;

	/**
	 * List of path attributes
	 */
	private ArrayList<PathAttribute> pathAttributes;
	/**
	 *  Network Layer Reachability Information
	 */
	private NLRI nlri;
	/**
	 * Construct for encoding
	 */
	public BGP4Update () {
		super();
		this.setMessageType(BGP4MessageTypes.MESSAGE_UPDATE);
		pathAttributes = new ArrayList<PathAttribute>();
	}

	/**
	 * Construct for decoding
	 */
	public BGP4Update (byte[] bytes) {//throws PCEPProtocolViolationException {
		super(bytes);		
		log.info("Decoding BGP4 Update Message");
		this.messageBytes=new byte[bytes.length];		
		System.arraycopy(bytes, 0, this.messageBytes, 0, bytes.length);
		decode();		
	}
	@Override
	public void encode() {
		log.info("Encode BGP4 Update");
		if ((withdrawnRoutes == null)&&(pathAttributes.size() == 0))
			log.warning("There should be withdrawnRoutes or path Attributes");
		if (withdrawnRoutes == null)
			withdrawnRoutesLength = 0;
		int len = BGPHeaderLength;
		//Add 4 octects for the lengths
		len = len +4; //withdrawnRoutesLength + totalPathAttibuteLength
		if (withdrawnRoutes!=null){
			withdrawnRoutes.encode();
			len=len+withdrawnRoutes.getLength();
			withdrawnRoutesLength = withdrawnRoutes.getLength();
		}
		totalPathAttibuteLength =0;
		for (int i=0;i<pathAttributes.size();++i){
			pathAttributes.get(i).encode();
			len=len+pathAttributes.get(i).getLength();
			totalPathAttibuteLength = totalPathAttibuteLength +pathAttributes.get(i).getLength();
		}
		if (nlri != null){
			log.info("NLRI encoding...");
			nlri.encode();
			len=len+nlri.getLength();
		}
		
		this.setMessageLength(len);		
		messageBytes=new byte[len];
		encodeHeader();
		int offset=BGPHeaderLength;
		//Add withdrawnRoutesLength
		this.messageBytes[offset]=(byte)(withdrawnRoutesLength>>>8 & 0xFF);
		this.messageBytes[offset+1]=(byte)(withdrawnRoutesLength & 0xFF);
		offset = offset+2;
		//Add withdrawnRoutes
		if (withdrawnRoutes!=null){
			System.arraycopy(withdrawnRoutes.getBytes(),0,messageBytes,offset,withdrawnRoutes.getLength());
			offset=offset+withdrawnRoutes.getLength();
		}
		//Add totalPathAttibuteLength
		this.messageBytes[offset]=(byte)(totalPathAttibuteLength>>>8 & 0xFF);
		this.messageBytes[offset+1]=(byte)(totalPathAttibuteLength & 0xFF);
		offset = offset+2;
		
		//Add Path Attributes
		for (int i=0;i<pathAttributes.size();++i){
			log.info("Path attribute to be encoded::::" + pathAttributes.get(i).getTypeCode());
			System.arraycopy(pathAttributes.get(i).getBytes(),0,messageBytes,offset,pathAttributes.get(i).getLength());	
			offset=offset+pathAttributes.get(i).getLength();
		}
		//Add NLRI
		if (nlri!=null){
			System.arraycopy(nlri.getBytes(),0,messageBytes,offset,nlri.getBytes().length);
			offset=offset+nlri.getLength();
		}
		
	}
	
	
	public void decode() {
		int offset = BGPHeaderLength;
		//Withdrawn Routes length
		withdrawnRoutesLength = ((  ((int)messageBytes[offset]) <<8)& 0xFF00) |  ((int)messageBytes[offset+1] & 0xFF);
		offset = offset +2;
		//Withdrawn Routes
		if (withdrawnRoutesLength != 0){
			//Decodificar withdrawnRoutes

		}
		//Path Attributes Length
		totalPathAttibuteLength = ((  ((int)messageBytes[offset]) <<8)& 0xFF00) |  ((int)messageBytes[offset+1] & 0xFF);
		log.info("total path attribute length "+ totalPathAttibuteLength);
		offset = offset +2;
		if (totalPathAttibuteLength != 0){
			pathAttributes = new ArrayList<PathAttribute>();
			int len = 0;
			int attribute_typeCode = PathAttribute.getAttibuteTypeCode(messageBytes, offset);
			int attribute_length = PathAttribute.getAttributeLength(messageBytes, offset);
			while (len < totalPathAttibuteLength){
				//Path Attributes
				attribute_typeCode = PathAttribute.getAttibuteTypeCode(messageBytes, offset);
				attribute_length = PathAttribute.getAttributeLength(messageBytes, offset);

				log.info("Attribute Length: "+attribute_length);
				log.info("New Attribute, type code: "+attribute_typeCode);
				
				if (attribute_typeCode == PathAttributesTypeCode.PATH_ATTRIBUTE_TYPECODE_ORIGIN){
					log.info("Tiene atributo ORIGIN");
					OriginAttribute origin = new OriginAttribute(messageBytes,offset);
					pathAttributes.add(origin);
					
				}
				else if (attribute_typeCode == PathAttributesTypeCode.PATH_ATTRIBUTE_TYPECODE_ASPATH){
					log.info("Tiene atributo ASPATH");
					AS_Path_Attribute as_Path_Attribute = new AS_Path_Attribute(messageBytes,offset);
					pathAttributes.add(as_Path_Attribute);
				}
				else if (attribute_typeCode == PathAttributesTypeCode.PATH_ATTRIBUTE_TYPECODE_LINKSTATE){
					log.info("Tiene atributo LINK_STATE");
					LinkStateAttribute linkState_Attribute = new LinkStateAttribute(messageBytes,offset);
					if(linkState_Attribute.getMandatoryLength() == 4){
						offset++;//extended length
						len++;//length tiene 2 bytes con extended length
					}
					pathAttributes.add(linkState_Attribute);
				}	
				else if (attribute_typeCode == PathAttributesTypeCode.PATH_ATTRIBUTE_ASPATH_AS_SEQUENCE){
					//log.info("Tiene atributo ASPATH_AS_SEQUENCE");
					log.severe("Not implemented yet");
				}
				else if (attribute_typeCode == PathAttributesTypeCode.PATH_ATTRIBUTE_ORIGIN_EGP){
					log.info("Tiene atributo ORIGIN_EGP");
					log.severe("Not implemented yet");
				}
				else if (attribute_typeCode == PathAttributesTypeCode.PATH_ATTRIBUTE_TYPECODE_MP_REACH_NLRI){
					log.info("Tiene atributo PATH_ATTRIBUTE_TYPECODE_MP_REACH_NLRI");
					int afi = MP_Reach_Attribute.getAFI(messageBytes, offset);
					if (afi == AFICodes.AFI_BGP_LS ){
						BGP_LS_MP_Reach_Attribute blsra= new BGP_LS_MP_Reach_Attribute(messageBytes,offset);
						if(blsra.getMandatoryLength()==4){
							offset++;//extended length
							len++;//length tiene 2 bytes
						}
						pathAttributes.add(blsra);
					}else {
						Generic_MP_Reach_Attribute gblsra= new Generic_MP_Reach_Attribute(messageBytes,offset);
						if(gblsra.getMandatoryLength()==4){
							offset++;
							len++;
						}
						pathAttributes.add(gblsra);
					}
					//nlri con extended length tiene mandatory length 4 no tres
				}
				
				offset = offset + attribute_length + 3;
				len = len + attribute_length +3;
				log.info("offset2: "+offset);
				/**attribute_typeCode = messageBytes[offset];
				attribute_length = messageBytes[offset+1];*/
			}
			//NLRI
			//if (nlri != null){
			int nlri_type = LinkStateNLRI.getNLRIType(messageBytes, offset);
			if (nlri_type == NLRITypes.Link_NLRI){
				log.info("Link_NLRI");
				nlri = new LinkNLRI(messageBytes,offset);
				offset=offset+ nlri.getLength();
			}
			if (nlri_type == NLRITypes.Node_NLRI){
				log.info("Node_NLRI");
				nlri = new NodeNLRI(messageBytes,offset);
				//UPDATE message Length - 23 - Total Path Attributes Length
	            //   - Withdrawn Routes Length
				offset=offset+nlri.getLength();
			}
			if ((nlri == null)&&(withdrawnRoutesLength == 0)){
				log.warning("BGP4 Update without NRLI and without Withdrawn Routes");
				
			}
		}
	}

	public int getWithdrawnRoutesLength() {
		return withdrawnRoutesLength;
	}

	public void setWithdrawnRoutesLength(int withdrawnRoutesLength) {
		this.withdrawnRoutesLength = withdrawnRoutesLength;
	}

	public WithdrawnRoutes getWithdrawnRoutes() {
		return withdrawnRoutes;
	}

	public void setWithdrawnRoutes(WithdrawnRoutes withdrawnRoutes) {
		this.withdrawnRoutes = withdrawnRoutes;
	}

	public int getTotalPathAttibuteLength() {
		return totalPathAttibuteLength;
	}

	public void setTotalPathAttibuteLength(int totalPathAttibuteLength) {
		this.totalPathAttibuteLength = totalPathAttibuteLength;
	}

	public ArrayList<PathAttribute> getPathAttributes() {
		return pathAttributes;
	}

	public void setPathAttributes(ArrayList<PathAttribute> pathAttribute) {
		this.pathAttributes = pathAttribute;
	}

	public NLRI getNlri() {
		return nlri;
	}

	public void setNlri(NLRI nlri) {
		this.nlri = nlri;
	}
	
	public String getLearntFrom() {
		return learntFrom;
	}

	public void setLearntFrom(String learntFrom) {
		this.learntFrom = learntFrom;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer((withdrawnRoutesLength+totalPathAttibuteLength)*800);
		sb.append("BGP4Update Msg: ");
		if (withdrawnRoutesLength != 0){
			sb.append("> Withdrawn Routes: \n");
			sb.append(withdrawnRoutes.toString());
		}
		if (pathAttributes.size() != 0){
			sb.append("> Path Attibutes: \n ");
			for (int i=0;i<pathAttributes.size();++i){
				sb.append("> "+ pathAttributes.get(i).toString()+"\n");
			}
		}
		if (nlri != null)
			sb.append(nlri.toString());
		
		return sb.toString();
	}

}