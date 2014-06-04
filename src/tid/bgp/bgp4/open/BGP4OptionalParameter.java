package tid.bgp.bgp4.open;

import java.util.logging.Logger;

import tid.bgp.bgp4.objects.BGP4Object;
import tid.protocol.commons.ByteHandler;

/**
 * BGP Open Message Format (RFC 4271). 
 * From RFC 4271, Section 4.2.  OPEN Message Format
 * <a href="http://www.ietf.org/rfc/rfc4271.txt">RFC 4271</a>.
 * Optional Parameters:
 *  	
 *  	This field contains a list of optional parameters, in which
         each parameter is encoded as a <Parameter Type, Parameter
         Length, Parameter Value> triplet.
 * <pre>
         0                   1
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5
         +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-...
         |  Parm. Type   | Parm. Length  |  Parameter Value (variable)
         +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-...

         Parameter Type is a one octet field that unambiguously
         identifies individual parameters.  Parameter Length is a one
         octet field that contains the length of the Parameter Value
         field in octets.  Parameter Value is a variable length field
         that is interpreted according to the value of the Parameter
         Type field.
  * </pre>        
 * @author mcs
 *
 */
public abstract class BGP4OptionalParameter extends BGP4Object{
	
	protected int type;
	
	protected int parameterLength;
		
	public BGP4OptionalParameter(){
		log=Logger.getLogger("BGP4Parser");	
	}
	
	
	public BGP4OptionalParameter(byte []bytes, int offset) {		
		this.type =((int)bytes[offset] & 0xFF);
		this.parameterLength= ((int)bytes[offset+1] & 0xFF);		
		this.length= parameterLength+2;
		this.bytes=new byte[length];
		System.arraycopy(bytes, offset, this.bytes, 0, length);
	
	}
	
	protected void encodeOptionalParameterHeader(){	
		this.bytes[0]=(byte)(type & 0xFF);
		this.bytes[1]=(byte)((parameterLength) & 0xFF);
	}

	/**
	 * Get the object Length. Generic for all objects
	 * @return Object Length
	 */
	public int getLength() {
		return length;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public static int getParameterLength(byte []bytes, int offset) {
		int len=((int)bytes[offset+1] & 0xFF);
		return len;
	}
	
	public static int getLength(byte []bytes, int offset) {
		int len=((int)bytes[offset+1] & 0xFF)+2;
		return len;
	}
	
	
	public static int getType(byte []bytes, int offset) {
		int typ=((int)bytes[offset] & 0xFF);
		return typ;
	}


	public int getParameterLength() {
		return parameterLength;
	}


	public void setParameterLength(int parameterLength) {
		this.parameterLength = parameterLength;
	}
	
	
	
}