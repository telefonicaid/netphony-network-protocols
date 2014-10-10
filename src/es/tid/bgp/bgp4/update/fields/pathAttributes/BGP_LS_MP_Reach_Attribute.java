package es.tid.bgp.bgp4.update.fields.pathAttributes;

import es.tid.bgp.bgp4.update.fields.LinkNLRI;
import es.tid.bgp.bgp4.update.fields.LinkStateNLRI;
import es.tid.bgp.bgp4.update.fields.NLRITypes;
import es.tid.bgp.bgp4.update.fields.NodeNLRI;
import es.tid.bgp.bgp4.update.fields.PrefixNLRI;

/**
 * 
 *  @author Telefonica I+D
 *
 */

public class BGP_LS_MP_Reach_Attribute extends MP_Reach_Attribute {
	
	private LinkStateNLRI lsNLRI;
	
	public BGP_LS_MP_Reach_Attribute(){
		this.setAddressFamilyIdentifier(AFICodes.AFI_BGP_LS);
		this.setSubsequentAddressFamilyIdentifier(SAFICodes.SAFI_BGP_LS);
	}
	
	public BGP_LS_MP_Reach_Attribute(byte [] bytes, int offset) {
		super(bytes, offset);
		log.info("mandatory length:"+this.mandatoryLength);
		int offset2=offset+this.mandatoryLength+5+this.getLengthofNextHopNetworkAddress();
		int type= LinkStateNLRI.getType(bytes, offset2);
		log.info("nlri del tipo:"+type);
		if (type==NLRITypes.Link_NLRI){
			log.info(">>Link_NLRI");
			lsNLRI=new LinkNLRI(bytes, offset2);	
		}else if (type==NLRITypes.Node_NLRI){
			log.info(">>Node_NLRI");
			lsNLRI=new NodeNLRI(bytes, offset2);	
		}
		else if (type == NLRITypes.Prefix_v4_NLRI){
			log.info(">>Prefix_NLRI");
			lsNLRI = new PrefixNLRI(bytes, offset2);
		}
		else{
			log.info(">>UNKNOWN_NLRI!!!");
		}
		
	}
	
	
	public void encode() {
		log.info("Encoding BGP_LS_MP_Reach_Attribute");
		
		if (lsNLRI!=null){
			lsNLRI.encode();
		}
		//FIXME: SUPONEMOS lengthofNextHopNetworkAddress cero
		pathAttributeLength = 5+lsNLRI.getLength();
		this.setPathAttributeLength(pathAttributeLength);
		this.bytes=new byte[this.getLength()];
		encodeHeader();	
		encodeMP_Reach_Header();
		//FIXME: SUPONEMOS lengthofNextHopNetworkAddress cero
		int offset = this.getMandatoryLength()+5;
		System.arraycopy(lsNLRI.getBytes(), 0, this.getBytes(), offset, lsNLRI.getLength());
	}

	public LinkStateNLRI getLsNLRI() {
		return lsNLRI;
	}

	public void setLsNLRI(LinkStateNLRI lsNLRI) {
		this.lsNLRI = lsNLRI;
	}
	
	public String toString(){
		String text;
		if (lsNLRI!=null){
			text="[BPG_LS_MP_REACH "+ lsNLRI.toString()+" ]";
		}else {
			text ="";	
		}
		
		
		return text;
	}
	

}
