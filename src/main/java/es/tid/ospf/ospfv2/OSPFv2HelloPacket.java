package es.tid.ospf.ospfv2;

public class OSPFv2HelloPacket extends OSPFv2Packet{
	public OSPFv2HelloPacket(byte[] bytes, int offset){
		super(bytes,offset);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Hello packet";
	}
}
