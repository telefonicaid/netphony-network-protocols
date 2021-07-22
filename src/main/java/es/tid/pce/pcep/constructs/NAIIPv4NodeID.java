package es.tid.pce.pcep.constructs;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;

public class NAIIPv4NodeID extends NAI {
	
	private Inet4Address nodeID;

	public NAIIPv4NodeID() {
		this.setNaiType(1);
	}
	
	public NAIIPv4NodeID(byte[] bytes, int offset)throws MalformedPCEPObjectException {
		this.setNaiType(1);
		decode(bytes,offset);
	}

	@Override
	public void encode() throws PCEPProtocolViolationException {
		int len=4;
		int offset=0;
		this.setLength(len);
		bytes=new byte[len];
		
		if (nodeID!=null){
			System.arraycopy(nodeID.getAddress(),0, this.bytes , offset, 4);
		}

	}
	
	public void decode(byte[] bytes, int offset) throws MalformedPCEPObjectException {
		byte[] ip=new byte[4]; 
		System.arraycopy(bytes,offset, ip, 0, 4);
		try {
			nodeID=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Inet4Address getNodeID() {
		return nodeID;
	}

	public void setNodeID(Inet4Address nodeID) {
		this.nodeID = nodeID;
	}

	@Override
	public String toString() {
		return "NAIIPv4NodeID [nodeID=" + nodeID + "]";
	}
	
	

}
