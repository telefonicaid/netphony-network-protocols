package es.tid.bgp.bgp4.update.tlv;

import java.util.ArrayList;
import java.util.logging.Logger;

import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.AreaIDNodeDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.AutonomousSystemNodeDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.BGP4SubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.BGPLSIdentifierNodeDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.IGPRouterIDNodeDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.NodeDescriptorsSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.NodeDescriptorsSubTLVTypes;

/**
 *    The Remote Node Descriptors TLV (Type 257) contains Node Descriptors
   for the node anchoring the remote end of the link.  The length of
   this TLV is variable.  The value contains one or more Node Descriptor
   Sub-TLVs defined in Section 3.2.1.3.

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |              Type             |             Length            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                                                               |
     |               Node Descriptor Sub-TLVs (variable)             |
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

               Figure 10: Remote Node Descriptors TLV format
               
 * @author mcs
 *
 */
public class RemoteNodeDescriptorsTLV extends BGP4TLVFormat {

private ArrayList<NodeDescriptorsSubTLV> nodeDescriptorsSubTLVList;
public static final int Remote_Node_Descriptors_TLV = 257;
	
	public RemoteNodeDescriptorsTLV(){
		super();
		this.setTLVType(RemoteNodeDescriptorsTLV.Remote_Node_Descriptors_TLV);
		//nodeDescriptorsSubTLVList=new ArrayList<NodeDescriptorsSubTLV>();
	}
	
	
	public RemoteNodeDescriptorsTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		log=Logger.getLogger("BGP4Parser");	
		nodeDescriptorsSubTLVList=new ArrayList<NodeDescriptorsSubTLV>();
		decode();
	}
	
	public void encode(){		
		log.info("Encode RemoteNodeDescriptorsTLV");
		if ((nodeDescriptorsSubTLVList.size() == 0))
			log.warning("RemoteNodeDescriptorsTLV sub TLV with 0 elements");
		
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
		log.info("Decoding RemoteNodeDescriptorsTLV");
		boolean fin=false;
		int offset=4;
		while (!fin) {
			int subtlvType=BGP4SubTLV.getType(tlv_bytes, offset);
			int subtlvLength=BGP4SubTLV.getTotalSubTLVLength(tlv_bytes, offset);
			switch(subtlvType) {
				case NodeDescriptorsSubTLVTypes.NODE_DESCRIPTORS_SUBTLV_TYPE_AUTONOMOUS_SYSTEM:
					AutonomousSystemNodeDescriptorSubTLV autonomousSystemSubTLV=new AutonomousSystemNodeDescriptorSubTLV(this.tlv_bytes, offset);
					this.addNodeDescriptorsSubTLV(autonomousSystemSubTLV);
					break;		
				
				case NodeDescriptorsSubTLVTypes.NODE_DESCRIPTORS_SUBTLV_TYPE_BGP_LS_IDENTIFIER:
					BGPLSIdentifierNodeDescriptorSubTLV BGPLSIDSubTLV=new BGPLSIdentifierNodeDescriptorSubTLV(this.tlv_bytes, offset);
					addNodeDescriptorsSubTLV(BGPLSIDSubTLV);
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
					log.info("Remote Node Descriptor Unknown");
					//FIXME What do we do??
					break;
			}
			
			offset=offset+subtlvLength;
			if (offset>=this.TLVValueLength){
				fin=true;
			}
			else{
				log.info("sigo leyendo NodeDescriptorsSubTLV ");
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
			sb.append("\n\t> "+nodeDescriptorsSubTLVList.get(i).toString()+"\n");
		}
		return sb.toString();
	}
	
}
