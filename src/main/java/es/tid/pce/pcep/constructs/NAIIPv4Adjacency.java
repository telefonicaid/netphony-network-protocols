package es.tid.pce.pcep.constructs;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;

public class NAIIPv4Adjacency extends NAI {
	
	private Inet4Address localNodeAddress;
	private Inet4Address remoteNodeAddress;

	public NAIIPv4Adjacency() {
		this.setNaiType(3);
	}
	
	public NAIIPv4Adjacency(byte[] bytes, int offset)throws MalformedPCEPObjectException {
		this.setNaiType(3);
		decode(bytes,offset);
	}

	@Override
	public void encode() throws PCEPProtocolViolationException {
		int len=8;
		int offset=0;
		this.setLength(len);
		this.bytes=new byte[len];
		
		if (localNodeAddress!=null){
			System.arraycopy(localNodeAddress.getAddress(),0, this.bytes , offset, 4);
		}
		offset+=4;
		if (remoteNodeAddress!=null){
			System.arraycopy(remoteNodeAddress.getAddress(),0, this.bytes , offset, 4);
		}

	}
	
	public void decode(byte[] bytes, int offset) throws MalformedPCEPObjectException {
		byte[] ip=new byte[4]; 
		System.arraycopy(bytes,offset, ip, 0, 4);
		try {
			localNodeAddress=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		offset+=4;
		System.arraycopy(bytes,offset, ip, 0, 4);
		try {
			remoteNodeAddress=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Inet4Address getLocalNodeAddress() {
		return localNodeAddress;
	}

	public void setLocalNodeAddress(Inet4Address localNodeAddress) {
		this.localNodeAddress = localNodeAddress;
	}

	public Inet4Address getRemoteNodeAddress() {
		return remoteNodeAddress;
	}

	public void setRemoteNodeAddress(Inet4Address remoteNodeAddress) {
		this.remoteNodeAddress = remoteNodeAddress;
	}

	@Override
	public String toString() {
		return "NAIIPv4Adjacency [localNodeAddress=" + localNodeAddress + ", remoteNodeAddress=" + remoteNodeAddress
				+ "]";
	}


	

}
