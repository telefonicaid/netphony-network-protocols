package tid.bgp.bgp4.update.fields.pathAttributes;

import tid.bgp.bgp4.update.fields.PathAttribute;

/**
 *  c) NEXT_HOP (Type Code 3):

            This is a well-known mandatory attribute that defines the
            (unicast) IP address of the router that SHOULD be used as
            the next hop to the destinations listed in the Network Layer
            Reachability Information field of the UPDATE message.

            Usage of this attribute is defined in 5.1.3.
            
 * @author mcs
 *
 */
public class Next_Hop_Attribute extends PathAttribute{
	
	

	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}
	
	public Next_Hop_Attribute(){		
		super();
		//Poner los flags. 
		this.typeCode = PathAttributesTypeCode.PATH_ATTRIBUTE_TYPECODE_NEXTHOP;
		

	}
	public Next_Hop_Attribute(byte []bytes, int offset){
		super(bytes, offset);
		decode();		
	}
	
	
	private void decode(){
		
	}

}
