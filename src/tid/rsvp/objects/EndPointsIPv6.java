package tid.rsvp.objects;

import java.net.*;

import tid.rsvp.RSVPProtocolViolationException;

/**
 * 
 * @author Oscar Gonzalez de Dios
 * @version 0.1 
 */
public class EndPointsIPv6 extends EndPoints{
	
	public Inet6Address sourceIP;//Source IPv6 address
	public Inet6Address destIP;//Destination IPv6 address
	
	@Override
	public void decode(byte[] bytes, int offset) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void encode() {
		// TODO Auto-generated method stub
	}
	
	
	

}
