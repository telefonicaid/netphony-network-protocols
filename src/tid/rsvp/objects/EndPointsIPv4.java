package tid.rsvp.objects;

import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import tid.pce.pcep.objects.MalformedPCEPObjectException;
import tid.pce.pcep.objects.ObjectParameters;
import tid.rsvp.RSVPProtocolViolationException;


public class EndPointsIPv4 extends EndPoints{
	/**
	 * Source IPv4 address
	 */
	private Inet4Address sourceIP;
	/**
	 * Destination IPv4 address
	 */
	private Inet4Address destIP;
		
	public Inet4Address getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(Inet4Address sourceIP) {
		this.sourceIP = sourceIP;
	}

	public Inet4Address getDestIP() {
		return destIP;
	}

	public void setDestIP(Inet4Address destIP) {
		this.destIP = destIP;
	}
		
	public String toString(){
		return "Source IP: "+sourceIP+" Destination IP: "+destIP;
	}

	@Override
	public void decode(byte[] bytes, int offset)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}
	

}
