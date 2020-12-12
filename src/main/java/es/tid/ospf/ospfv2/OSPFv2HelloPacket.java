package es.tid.ospf.ospfv2;

public class OSPFv2HelloPacket extends OSPFv2Packet{
	
	public OSPFv2HelloPacket(){
		super();
		this.setType(OSPFPacketTypes.OSPFv2_HELLO_PACKET);
	}
	public OSPFv2HelloPacket(byte[] bytes, int offset){
		super(bytes,offset);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Hello packet";
	}

	@Override
	public void encode() {
		int len=24;//Header bytes
		this.setLength(len);
		this.bytes=new byte[this.getLength()];
		this.encodeOSPFV2PacketHeader();
		
	}
}
