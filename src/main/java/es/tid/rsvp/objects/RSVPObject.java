package es.tid.rsvp.objects;

import java.util.Arrays;

import org.slf4j.Logger;

import es.tid.rsvp.*;
import org.slf4j.LoggerFactory;

/*	RSVP Object. From RFC 2205
 * 
 * RFC 2205:
 * 
 *       3.1.2 Object Formats

         Every object consists of one or more 32-bit words with a one-
         word header, with the following format:

                0             1              2             3
         +-------------+-------------+-------------+-------------+
         |       Length (bytes)      |  Class-Num  |   C-Type    |
         +-------------+-------------+-------------+-------------+
         |                                                       |
         //                  (Object contents)                   //
         |                                                       |
         +-------------+-------------+-------------+-------------+


         An object header has the following fields:

         Length

              A 16-bit field containing the total object length in
              bytes.  Must always be a multiple of 4, and at least 4.

         Class-Num

              Identifies the object class; values of this field are
              defined in Appendix A.  Each object class has a name,
              which is always capitalized in this document.  An RSVP
              implementation must recognize the following classes:

              NULL

                   A NULL object has a Class-Num of zero, and its C-Type
                   is ignored.  Its length must be at least 4, but can
                   be any multiple of 4.  A NULL object may appear
                   anywhere in a sequence of objects, and its contents
                   will be ignored by the receiver.

              SESSION

                   Contains the IP destination address (DestAddress),
                   the IP protocol id, and some form of generalized
                   destination port, to define a specific session for
                   the other objects that follow.  Required in every
                   RSVP message.

              RSVP_HOP

                   Carries the IP address of the RSVP-capable node that
                   sent this message and a logical outgoing interface
                   handle (LIH; see Section 3.3).  This document refers
                   to a RSVP_HOP object as a PHOP ("previous hop")
                   object for downstream messages or as a NHOP (" next
                   hop") object for upstream messages.

              TIME_VALUES

                   Contains the value for the refresh period R used by
                   the creator of the message; see Section 3.7.
                   Required in every Path and Resv message.

              STYLE

                   Defines the reservation style plus style-specific
                   information that is not in FLOWSPEC or FILTER_SPEC
                   objects.  Required in every Resv message.

              FLOWSPEC

                   Defines a desired QoS, in a Resv message.

              FILTER_SPEC

                   Defines a subset of session data packets that should
                   receive the desired QoS (specified by a FLOWSPEC
                   object), in a Resv message.

              SENDER_TEMPLATE

                   Contains a sender IP address and perhaps some
                   additional demultiplexing information to identify a
                   sender.  Required in a Path message.

              SENDER_TSPEC

                   Defines the traffic characteristics of a sender's
                   data flow.  Required in a Path message.

              ADSPEC

                   Carries OPWA data, in a Path message.

              ERROR_SPEC

                   Specifies an error in a PathErr, ResvErr, or a
                   confirmation in a ResvConf message.

              POLICY_DATA

                   Carries information that will allow a local policy
                   module to decide whether an associated reservation is
                   administratively permitted.  May appear in Path,
                   Resv, PathErr, or ResvErr message.

                   The use of POLICY_DATA objects is not fully specified
                   at this time; a future document will fill this gap.


              INTEGRITY

                   Carries cryptographic data to authenticate the
                   originating node and to verify the contents of this
                   RSVP message.  The use of the INTEGRITY object is
                   described in [Baker96].

              SCOPE

                   Carries an explicit list of sender hosts towards
                   which the information in the message is to be
                   forwarded.  May appear in a Resv, ResvErr, or
                   ResvTear message.  See Section 3.4.

              RESV_CONFIRM

                   Carries the IP address of a receiver that requested a
                   confirmation.  May appear in a Resv or ResvConf
                   message.

         C-Type

              Object type, unique within Class-Num.  Values are defined
              in Appendix A.

         The maximum object content length is 65528 bytes.  The Class-
         Num and C-Type fields may be used together as a 16-bit number
         to define a unique type for each object.

         The high-order two bits of the Class-Num is used to determine
         what action a node should take if it does not recognize the
         Class-Num of an object; see Section 3.10.


 */

public abstract class RSVPObject implements RSVPElement{
	
	protected int length;	// The object length
	protected int classNum;//Identifies the object class
	protected int cType;//Object type, unique within Class-Num
	protected byte[] bytes; // Byte Object representation
  private static final Logger log = LoggerFactory.getLogger("RSVPParser");
	
	public RSVPObject(byte[] bytes, int offset){
		this.length=((bytes[offset]<<8)& 0xFF00) |  (bytes[offset+1] & 0xFF);
		this.bytes=new byte[this.length];
		System.arraycopy(bytes, offset, this.bytes, 0, this.length);
		classNum = (int) bytes[offset+2];
		cType = (int) bytes[offset+3];
	}
	
	public RSVPObject(){
	}
	
	/**
	 * Common RSVP Objects Header encoding method
	 */
	
	public void encodeHeader(){
	
		bytes[0] = (byte)((length>>8) & 0xFF);
		bytes[1] = (byte)(length & 0xFF);
		bytes[2] = (byte) classNum;
		bytes[3] = (byte) cType;
		
	}
	
	/**
	 * Common RSVP Objects Header decoding method
	 * @param bytes bytes 
	 * @param offset offset
	 */
	
	public void decodeHeader(byte[] bytes, int offset){
		
		length = ((int)((bytes[offset] << 8) & 0xFF00)) | ((int)(bytes[offset+1] & 0x00FF));
		classNum = (int) (bytes[offset+2]&0xFF);
		cType = (int) bytes[offset+3];
		
	}
	
	public abstract void encode() throws RSVPProtocolViolationException;
	
	public abstract void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException;

	// Getters & Setters
	
	public int getLength() {
		return length;
	}

	protected void setLength(int length) {
		this.length = length;
	}

	public int getClassNum() {
		return classNum;
	}

	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}

	public int getcType() {
		return cType;
	}

	public void setcType(int cType) {
		this.cType = cType;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}	

	public static int getClassNum(byte[] bytes, int offset) {
		return (int) bytes[offset+2];
	}
	
	public static int getcType(byte[] bytes, int offset){
		return (int) bytes[offset+3];		
	}
	
	public static int getLength(byte[] bytes, int offset){
		
		return ((int)((bytes[offset] << 8) & 0xFF00)) | ((int)(bytes[offset+1] & 0x00FF));
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bytes);
		result = prime * result + cType;
		result = prime * result + classNum;
		result = prime * result + length;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RSVPObject other = (RSVPObject) obj;
		if (!Arrays.equals(bytes, other.bytes))
			return false;
		if (cType != other.cType)
			return false;
		if (classNum != other.classNum)
			return false;
		if (length != other.length)
			return false;
		return true;
	}
	
	
}
