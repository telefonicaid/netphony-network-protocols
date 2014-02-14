package tid.pce.pcep.objects;

/**
 * <p> Base abstract class for representing EndPoints Object.</p>
 * <p> There are currently two implemented classes, IPv4 and IPv6, as defined in RFC 5440</p>
 * <pre>
 * From RFC 5440 Section 7.6. END-POINTS Object
 * 
 * The END-POINTS object is used in a PCReq message to specify the
 * source IP address and the destination IP address of the path for
 * which a path computation is requested.  The P flag of the END-POINTS
 * object MUST be set.  If the END-POINTS object is received with the P
 * flag cleared, the receiving peer MUST send a PCErr message with
 *  Error-Type=10 and Error-value=1.  The corresponding path computation
 *   request MUST be cancelled by the PCE without further notification.
 * </pre>  
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 *
 */
public abstract class EndPoints extends PCEPObject{
	
	public EndPoints(){
		super();
	}
	public EndPoints(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
	}
}
