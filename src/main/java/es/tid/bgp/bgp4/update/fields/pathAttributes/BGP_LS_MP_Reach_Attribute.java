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
		super();
		this.setAddressFamilyIdentifier(AFICodes.AFI_BGP_LS);
		this.setSubsequentAddressFamilyIdentifier(SAFICodes.SAFI_BGP_LS);
	}
	
	public BGP_LS_MP_Reach_Attribute(byte [] bytes, int offset) {
		super(bytes, offset);
		int offset2=offset+this.mandatoryLength+5+this.getLengthofNextHopNetworkAddress();
		int type= LinkStateNLRI.getType(bytes, offset2);
		if (type==NLRITypes.Link_NLRI){
			lsNLRI=new LinkNLRI(bytes, offset2);	
		}else if (type==NLRITypes.Node_NLRI){
			lsNLRI=new NodeNLRI(bytes, offset2);	
		}
		else if (type == NLRITypes.Prefix_v4_NLRI){
			lsNLRI = new PrefixNLRI(bytes, offset2);
		}
		else{
			log.warn("UNKNOWN_NLRI: "+type);
		}
		
	}
	
	
	public void encode() {
		//Encoding BGP_LS_MP_Reach_Attribute
		
		if (lsNLRI!=null){
			lsNLRI.encode();
		}
		this.
		pathAttributeLength = 5+lsNLRI.getLength()+this.getLengthofNextHopNetworkAddress();
		this.setPathAttributeLength(pathAttributeLength);
		this.bytes=new byte[this.getLength()];
		encodeHeader();	
		encodeMP_Reach_Header();
		int offset = this.getMandatoryLength()+5+this.getLengthofNextHopNetworkAddress();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((lsNLRI == null) ? 0 : lsNLRI.hashCode());
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
		BGP_LS_MP_Reach_Attribute other = (BGP_LS_MP_Reach_Attribute) obj;
		if (lsNLRI == null) {
			if (other.lsNLRI != null)
				return false;
		} else if (!lsNLRI.equals(other.lsNLRI))
			return false;
		return true;
	}
	
	
	
	

}
