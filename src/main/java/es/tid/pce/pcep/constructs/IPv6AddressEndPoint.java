package es.tid.pce.pcep.constructs;

import java.net.Inet4Address;
import java.net.Inet6Address;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;

public class IPv6AddressEndPoint extends EndPoint {
	
	/**
	 *  IPv6 address
	 */
	private Inet6Address endPointAddress;

	public IPv6AddressEndPoint() {
		// TODO Auto-generated constructor stub
	}

	public IPv6AddressEndPoint(byte[] bytes, int offset) throws MalformedPCEPObjectException {
			// TODO Auto-generated constructor stub
	}

	@Override
	public void encode() throws PCEPProtocolViolationException {
		// TODO Auto-generated method stub
		
	}

}
