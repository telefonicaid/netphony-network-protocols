package es.tid.pce.pcep.objects.tlvs;


import java.util.Arrays;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;


/**
 *  Extended Association ID TLV
 * @author Oscar Gonzalez de Dios
 *
 */
public class ExtendedAssociationIDTLV extends PCEPTLV 
{
	/*
	 * 

   The Extended Association ID TLV is an optional TLV for use in the
   ASSOCIATION object.  The meaning and usage of the Extended
   Association ID TLV are as per Section 4 of [RFC6780].

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Type                  |            Length             |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     //                Extended Association ID                      //
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

              Figure 6: The Extended Association ID TLV Format

   Type:  31

   Length:  Variable.

   Extended Association ID:  As defined in Section 4 of [RFC6780].
	 */
	
	private byte[] extendedAssociationID;
	
	
	public ExtendedAssociationIDTLV()
	{
		this.setTLVType(ObjectParameters.PCEP_TLV_EXTENDED_ASSOCIATION_ID);
		
	}
	
	public ExtendedAssociationIDTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode
	 */
	public void encode() 
	{
		log.debug("Encoding ExtendedAssociationIDTLV TLV");
		this.setTLVValueLength(extendedAssociationID.length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		System.arraycopy(extendedAssociationID, 0, this.tlv_bytes, offset, extendedAssociationID.length);
	}

	
	public void decode() throws MalformedPCEPObjectException
	{
		log.debug("Decoding SymbolicPathName TLV");
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0)
		{
			throw new MalformedPCEPObjectException();
		}
		
		extendedAssociationID = new byte[getTLVValueLength()];
		try
		{
			System.arraycopy(this.tlv_bytes, offset, extendedAssociationID, 0, getTLVValueLength());
		}
		catch (Exception e)
		{
			log.error("Exception occurred, Possibly TLV size is not what expected");
			throw new MalformedPCEPObjectException();			
		}
			

	}

	public byte[] getExtendedAssociationID() {
		return extendedAssociationID;
	}

	public void setExtendedAssociationID(byte[] extendedAssociationID) {
		this.extendedAssociationID = extendedAssociationID;
	}

	@Override
	public String toString() {
		return "ExtendedAssociationIDTLV [extendedAssociationID=" + Arrays.toString(extendedAssociationID) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(extendedAssociationID);
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
		ExtendedAssociationIDTLV other = (ExtendedAssociationIDTLV) obj;
		if (!Arrays.equals(extendedAssociationID, other.extendedAssociationID))
			return false;
		return true;
	}

	

	
}