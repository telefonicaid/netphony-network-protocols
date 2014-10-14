package es.tid.bgp.bgp4.update.tlv;

import java.util.ArrayList;

import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.AreaIDNodeDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.AutonomousSystemNodeDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.BGP4SubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.BGPLSIdentifierNodeDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.IGPRouterIDNodeDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.NodeDescriptorsSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.NodeDescriptorsSubTLVTypes;


/**
 * 3.2.1.1.  Local Node Descriptors

   The Local Node Descriptors TLV (Type 256) contains Node Descriptors
   for the node anchoring the local end of the link.  The length of this
   TLV is variable.  The value contains one or more Node Descriptor Sub-
   TLVs defined in Section 3.2.1.3.

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |              Type             |             Length            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                                                               |
     |               Node Descriptor Sub-TLVs (variable)             |
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                Figure 9: Local Node Descriptors TLV format


 * @author pac porque madre mia mcs...
 *
 */
public class LocalNodeDescriptorsTLV extends BGP4TLVFormat{
	
	private ArrayList<NodeDescriptorsSubTLV> nodeDescriptorsSubTLVList;
	public static final int Local_Node_Descriptors_TLV = 256;
	
	public LocalNodeDescriptorsTLV(){
		super();
		this.setTLVType(LocalNodeDescriptorsTLV.Local_Node_Descriptors_TLV);
		//nodeDescriptorsSubTLVList=new ArrayList<NodeDescriptorsSubTLV>();
	}
	
	
	public LocalNodeDescriptorsTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		nodeDescriptorsSubTLVList=new ArrayList<NodeDescriptorsSubTLV>();
		decode();
	}
	
	public void encode(){		
		log.finest("Encode LocalNodeDescriptorsTLV");
		if ((nodeDescriptorsSubTLVList.size() == 0))
			log.warning("LocalNodeDescriptorsTLV sub TLV with 0 elements");
		
		int len = 0;//Header TLV

		for (int i=0;i<nodeDescriptorsSubTLVList.size();++i){
			nodeDescriptorsSubTLVList.get(i).encode();
			len=len+nodeDescriptorsSubTLVList.get(i).getTotalSubTLVLength();			
		}
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);
		encodeHeader();
		int offset=4;//Header TLV
	
		for (int i=0;i<nodeDescriptorsSubTLVList.size();++i){
			System.arraycopy(nodeDescriptorsSubTLVList.get(i).getSubTLV_bytes(),0,this.tlv_bytes,offset,nodeDescriptorsSubTLVList.get(i).getTotalSubTLVLength());			
			offset=offset+nodeDescriptorsSubTLVList.get(i).getTotalSubTLVLength();
			
		}
	
	}
	public void decode(){
		log.finest("Decoding LocalNodeDescriptorsTLV");
		boolean fin=false;
		int offset=4;//Position of the next subobject
//		if (ObjectLength==4){
//			fin=true;
//		}

		while (!fin) {
			int subtlvType=BGP4SubTLV.getType(tlv_bytes, offset);
			int subtlvLength=BGP4SubTLV.getTotalSubTLVLength(tlv_bytes, offset);
			switch(subtlvType) {
				case NodeDescriptorsSubTLVTypes.NODE_DESCRIPTORS_SUBTLV_TYPE_AUTONOMOUS_SYSTEM:
					AutonomousSystemNodeDescriptorSubTLV autonomousSystemSubTLV=new AutonomousSystemNodeDescriptorSubTLV(this.tlv_bytes, offset);
					this.addNodeDescriptorsSubTLV(autonomousSystemSubTLV);
					break;		
				
				case NodeDescriptorsSubTLVTypes.NODE_DESCRIPTORS_SUBTLV_TYPE_BGP_LS_IDENTIFIER:
					BGPLSIdentifierNodeDescriptorSubTLV bgplsidentifierSubTLV = new  BGPLSIdentifierNodeDescriptorSubTLV(this.tlv_bytes, offset);
					addNodeDescriptorsSubTLV(bgplsidentifierSubTLV);
					break;
				
				case NodeDescriptorsSubTLVTypes.NODE_DESCRIPTORS_SUBTLV_TYPE_AREA_ID:
					AreaIDNodeDescriptorSubTLV AreaID = new AreaIDNodeDescriptorSubTLV(this.tlv_bytes, offset);
					addNodeDescriptorsSubTLV(AreaID);
					break;
				
				case NodeDescriptorsSubTLVTypes.NODE_DESCRIPTORS_SUBTLV_TYPE_IGP_ROUTER_ID:
					IGPRouterIDNodeDescriptorSubTLV IGPRouterID = new IGPRouterIDNodeDescriptorSubTLV(this.tlv_bytes, offset);
					addNodeDescriptorsSubTLV(IGPRouterID);
					break;
					
				default:
					log.finest("TYPE: " +subtlvType);
					log.finest("Local Node Descriptor Unknown");
					//FIXME What do we do??
					break;
			}
			offset=offset+subtlvLength;
			if (offset>=this.TLVValueLength){
				fin=true;
			}
			else{
				log.finest("sigo leyendo NodeDescriptorsSubTLV ");
			}
		}
	}

	public ArrayList<NodeDescriptorsSubTLV> getNodeDescriptorsSubTLVList() {
		return nodeDescriptorsSubTLVList;
	}

	public void setNodeDescriptorsSubTLVList(
			ArrayList<NodeDescriptorsSubTLV> nodeDescriptorsSubTLVList) {
		this.nodeDescriptorsSubTLVList = nodeDescriptorsSubTLVList;
	}
	public void addNodeDescriptorsSubTLV(NodeDescriptorsSubTLV nodeDescriptorsSubTLV){
		nodeDescriptorsSubTLVList.add(nodeDescriptorsSubTLV);
	}
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer(1000);
		
		for (int i=0;i<nodeDescriptorsSubTLVList.size();++i){			
			sb.append("\n\t> "+nodeDescriptorsSubTLVList.get(i).toString() + "\n");
		}
		return sb.toString();
	}
	
	
}
