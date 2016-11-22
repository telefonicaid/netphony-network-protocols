package es.tid.bgp.bgp4.update.fields;

import es.tid.bgp.bgp4.update.tlv.LocalNodeDescriptorsTLV;
import es.tid.bgp.bgp4.update.tlv.RoutingUniverseIdentifierTypes;

/**
 *  Node NLRI Format (RFC 4271). 
 * 
 * <a href="http://www.ietf.org/rfc/rfc4271.txt">RFC 4271</a>.
 * 
  The 'NLRI Type' field can contain one of the following values:

      Type = 1: Node NLRI

      Type = 2: Link NLRI

      Type = 3: IPv4 Topology Prefix NLRI

      Type = 4: IPv6 Topology Prefix NLRI
      
      Type = 5: IT Node

   The Node NLRI (NLRI Type = 1) is shown in the following figure.

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+
   |  Protocol-ID  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                           Identifier                          |
   |                            (64 bits)                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   //                Local Node Descriptors (variable)            //
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                      Figure 7: The Node NLRI format

 * @author pac
 *
 */
public class ITNodeNLRI extends LinkStateNLRI {
	private String nodeId;
	private String controllerIT;
	private String cpu;
	private String mem;
	private String storage;
			
	public ITNodeNLRI(){
		this.setNLRIType(NLRITypes.IT_Node_NLRI);
		
	}

	public ITNodeNLRI(byte[] bytes, int offset) {
		super(bytes,offset);
		decode();
	}
	@Override
	public void encode() {
		int len=4;// The four bytes of the header plus the 4 first bytes)
//		if (localNodeDescriptors!=null){
//			localNodeDescriptors.encode();
//			len=len+localNodeDescriptors.getTotalTLVLength();		
//		}
		
		//len+=5;
		byte bytesStringNodeId[]=nodeId.getBytes();
		len = len + 4 +bytesStringNodeId.length;
		
		byte bytesStringControllerIT[]=controllerIT.getBytes();
		len = len + 4 +bytesStringControllerIT.length;
		
		byte bytesStringCpu[]=cpu.getBytes();
		len+=4+bytesStringCpu.length;
		
		byte bytesStringMem[]=mem.getBytes();
		len+=4+bytesStringMem.length;
		
		byte bytesStringStorage[]=storage.getBytes();
		len+=4+bytesStringStorage.length;
		
		
		this.setTotalNLRILength(len); 
		
		this.setLength(len);
		
		
		this.bytes=new byte[len];
		this.encodeHeader();
				
		
		int offset=4;//Header
		offset = encodeHeaderSubTLV(1, bytesStringNodeId.length,offset);
		for(int i=0;i<bytesStringNodeId.length;i++){
			this.bytes[offset]=bytesStringNodeId[i];
			offset++;
		}
		
		offset = encodeHeaderSubTLV(2, bytesStringControllerIT.length,offset);
		for(int i=0;i<bytesStringControllerIT.length;i++){
			this.bytes[offset]=bytesStringControllerIT[i];
			offset++;
		}
		
		offset = encodeHeaderSubTLV(3, bytesStringCpu.length,offset);
		for(int i=0;i<bytesStringCpu.length;i++){
			this.bytes[offset]=bytesStringCpu[i];
			offset++;
		}
		
		offset = encodeHeaderSubTLV(4, bytesStringMem.length,offset);
		for(int i=0;i<bytesStringMem.length;i++){
			this.bytes[offset]=bytesStringMem[i];
			offset++;
		}
		
		offset = encodeHeaderSubTLV(5, bytesStringStorage.length,offset);
		for(int i=0;i<bytesStringStorage.length;i++){
			this.bytes[offset]=bytesStringStorage[i];
			offset++;
		}		
	}
	public void decode(){
		//Decoding ITNodeNL
		//Header 2(type)+2(length)
		int offset = 2;
		
		byte[] lengthITNodeNLRIBytes = new byte[2];
		System.arraycopy(this.bytes,offset, lengthITNodeNLRIBytes, 0, 2);
		int lengthITNodeNLRI = ((lengthITNodeNLRIBytes[0] << 8) & 0xFF00) | ((lengthITNodeNLRIBytes[1]) & 0xFF);
		offset+=2;
				
		int lengthResourcesgeted = 0;
		while (lengthResourcesgeted<lengthITNodeNLRI){
			//int typeResource = null;
			
			byte[] typeResourceBytes = new byte[2];
			System.arraycopy(this.bytes,offset, typeResourceBytes, 0, 2);
			int typeResource = ((typeResourceBytes[0] << 8) & 0xFF00) | ((typeResourceBytes[1]) & 0xFF);
			offset+=2;
			
			byte[] lengthResourceBytes = new byte[2];
			System.arraycopy(this.bytes,offset, lengthResourceBytes, 0, 2);
			int lengthResource = ((lengthResourceBytes[0] << 8) & 0xFF00) | ((lengthResourceBytes[1]) & 0xFF);
			offset+=2;
			
			byte[] valueResourceBytes = new byte[lengthResource];
			System.arraycopy(this.bytes,offset, valueResourceBytes, 0, lengthResource);
			String valueResource=new String(valueResourceBytes);
			offset+=lengthResource;
			
			switch (typeResource) {
				case 1:
					this.nodeId = valueResource;
					break;
				case 2:
					this.controllerIT = valueResource;
					break;
				case 3:
					this.cpu = valueResource;
					break;
				case 4:
					this.mem = valueResource;
					break;
				case 5:
					this.storage = valueResource;
					break;
			}
			
			lengthResourcesgeted+=4+lengthResource;
		}
	}
	protected int encodeHeaderSubTLV(int type, int valueLength,int byteStart){
		this.bytes[byteStart]=(byte)(type>>>8 & 0xFF);
		this.bytes[byteStart+1]=(byte)(type & 0xFF);
		this.bytes[byteStart+2]=(byte)(valueLength>>>8 & 0xFF);
		this.bytes[byteStart+3]=(byte)(valueLength & 0xFF);
		return byteStart+4;
	}
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}
	
	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	@Override
	public String toString() {
		return "ITNodeNLRI [nodeID=" + nodeId + "controllerIT=" + controllerIT + ", cpu="
				+ cpu + ", mem="
				+ mem+  ", storage="+ storage+"]";
	}

	public String getControllerIT() {
		return controllerIT;
	}

	public void setControllerIT(String controllerIT) {
		this.controllerIT = controllerIT;
	}

}
