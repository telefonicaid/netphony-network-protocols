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
		System.out.println(" --- ---------------------------Codificando ITNodeNLRI");
		int len=4;// The four bytes of the header plus the 4 first bytes)
//		if (localNodeDescriptors!=null){
//			localNodeDescriptors.encode();
//			len=len+localNodeDescriptors.getTotalTLVLength();		
//		}
		
		//len+=5;
		byte bytesStringNodeId[]=nodeId.getBytes();
		len = len + 4 +bytesStringNodeId.length;
		
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
		
		
		System.out.println(" --- ---------------------------len: "+len);
		
		
		
		int offset=4;//Header
		offset = encodeHeaderSubTLV(0, bytesStringNodeId.length,offset);
		for(int i=0;i<bytesStringNodeId.length;i++){
			this.bytes[offset]=bytesStringNodeId[i];
			offset++;
		}
		
		offset = encodeHeaderSubTLV(1, bytesStringCpu.length,offset);
		for(int i=0;i<bytesStringCpu.length;i++){
			this.bytes[offset]=bytesStringCpu[i];
			offset++;
		}
		
		offset = encodeHeaderSubTLV(2, bytesStringMem.length,offset);
		for(int i=0;i<bytesStringMem.length;i++){
			this.bytes[offset]=bytesStringMem[i];
			offset++;
		}
		
		offset = encodeHeaderSubTLV(3, bytesStringStorage.length,offset);
		for(int i=0;i<bytesStringStorage.length;i++){
			this.bytes[offset]=bytesStringStorage[i];
			offset++;
		}
		
		System.out.println(" --- ---------------------------After for");
//		this.bytes[4]=(byte)protocolID;
//		this.bytes[5]=(byte)(routingUniverseIdentifier>>>56 & 0xFF);
//		this.bytes[6]=(byte)(routingUniverseIdentifier>>>48 & 0xFF);
//		this.bytes[7]=(byte)(routingUniverseIdentifier >>> 40 & 0xFF);
//		this.bytes[8]=(byte)(routingUniverseIdentifier>>>32 & 0xFF);
//		this.bytes[9]=(byte)(routingUniverseIdentifier>>>24 & 0xFF);
//		this.bytes[10]=(byte)(routingUniverseIdentifier >>> 16 & 0xFF);
//		this.bytes[11]=(byte)(routingUniverseIdentifier >>>8 & 0xFF);
//		this.bytes[12]=(byte)(routingUniverseIdentifier & 0xFF);
//		
//		int offset=13;
//		
//		if (localNodeDescriptors!=null){
//			System.arraycopy(localNodeDescriptors.getTlv_bytes(), 0, this.bytes, offset,localNodeDescriptors.getTotalTLVLength());
//			offset=offset+localNodeDescriptors.getTotalTLVLength();
//		}
		
		
	}
	public void decode(){
		System.out.println("Decodificnado ITNodeNLRI");
//		//Decoding NodeNLRI
//		int offset = 4; //Cabecera del LinkState NLRI
//		protocolID = this.bytes[offset];
//		offset=offset +1; //identifier
//		
//		byte[] ip=new byte[8]; 
//		System.arraycopy(this.bytes,offset, ip, 0, 8);
//		
//		long routingUniverseIdentifieraux1 = ((  ((long)bytes[offset]&0xFF)   <<24)& 0xFF000000) |  (((long)bytes[offset+1]<<16) & 0xFF0000) | (((long)bytes[offset+2]<<8) & 0xFF00) |(((long)bytes[offset+3]) & 0xFF);
//		long routingUniverseIdentifieraux2 = ((  ((long)bytes[offset+4]&0xFF)   <<24)& 0xFF000000) |  (((long)bytes[offset+5]<<16) & 0xFF0000) | (((long)bytes[offset+6]<<8) & 0xFF00) |(((long)bytes[offset+7]) & 0xFF);
//		//this.setRoutingUniverseIdentifier((2^32)*routingUniverseIdentifieraux1+routingUniverseIdentifieraux2);
//		this.setRoutingUniverseIdentifier((routingUniverseIdentifieraux1 <<32)&0xFFFFFFFF00000000L | routingUniverseIdentifieraux2);
//		offset = offset +8;
//		this.localNodeDescriptors=new LocalNodeDescriptorsTLV(this.bytes, offset);
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
		return "ITNodeNLRI [nodeID=" + nodeId + ", cpu="
				+ cpu + ", mem="
				+ mem+  ", storage="+ storage+"]";
	}

}
