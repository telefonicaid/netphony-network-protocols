package es.tid.rsvp.objects.subobjects;

import es.tid.protocol.commons.ByteHandler;

/**
 * AS Number ERO Subobject. RFC 3209
 *
 * Section 4.3.3.4. Subobject 32:  Autonomous System Number
 *
 * The contents of an Autonomous System (AS) number subobject are a 2-
 * octet AS number.  The abstract node represented by this subobject is
 * the set of nodes belonging to the autonomous system.
 *
 * The length of the AS number subobject is 4 octets.
 * 
 * @author Oscar Gonzalez de Dios
 * @version 0.1
 */
public class ASNumberEROSubobject extends EROSubobject{
	
	private int ASNumber;//AS Number is a 16bit unsigned number. 
	
	public ASNumberEROSubobject(){
		super();
		erosolength=4;
		this.setType(SubObjectValues.ERO_SUBOBJECT_ASNUMBER);
	}
	public ASNumberEROSubobject(byte []bytes, int offset){
		super(bytes, offset);
		decode();		
	}
	
	/**
	 * Decode ASNumber ERO Subobject
	 */
	public void decode(){
		int offset=2;
		ASNumber=ByteHandler.decode2bytesInteger(this.getSubobject_bytes(), offset);
	}

	/**
	 * Decode Encode ERO Subobject
	 */
	public void encode(){
		this.subobject_bytes=new byte[erosolength];
		encodeSoHeader();
		int offset=2;
		ByteHandler.encode2byteInteger(ASNumber,this.getSubobject_bytes(),offset);
	}
	
	public int getASNumber() {
		return ASNumber;
	}
	public void setASNumber(int aSNumber) {
		ASNumber = aSNumber;
	}
	@Override
	public String toString() {
		return "ASNumberEROSubobject [ASNumber=" + ASNumber + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ASNumber;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ASNumberEROSubobject other = (ASNumberEROSubobject) obj;
		if (ASNumber != other.ASNumber)
			return false;
		return true;
	}
	


}
