package es.tid.pce.pcep.objects.tlvs;


import java.util.Objects;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

public class PathProtectionAssociationTLV extends PCEPTLV{
	/*
	 0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |         Type = 39             |            Length = 4         |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                              Data                             |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

             Figure 1: Path Protection Association TLV Format
             */

	private long data;

	
	public PathProtectionAssociationTLV() {
		this.setTLVType(ObjectParameters.PCEP_TLV_PATH_PROT_ASSOCIATION_GROUP);
	}
	
	public PathProtectionAssociationTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		super(bytes,offset);
		decode();
	}

	public long getData() {
		return data;
	}

	public void setData(long data) {
		this.data = data;
	}

	@Override
	public void encode() {
		log.debug("Encoding PathProtectionAssociation TLV");
		this.setTLVValueLength(4);
		
		this.tlv_bytes = new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset = 4;
		
		ByteHandler.encode4bytesLong(data, this.tlv_bytes, offset);
		
		
	}
	
	public void decode() throws MalformedPCEPObjectException {
		log.debug("Decoding SymbolicPathName TLV");
		int offset=4;//Position of the next subobject
		try {
			this.data = ByteHandler.decode4bytesLong(this.tlv_bytes, offset);
		}catch (Exception e)
		{
			log.error("Exception occurred, Possibly TLV size is not what expected");
			throw new MalformedPCEPObjectException();			
		}
		
		
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(data);
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
		PathProtectionAssociationTLV other = (PathProtectionAssociationTLV) obj;
		return data == other.data;
	}

	@Override
	public String toString() {
		return "PathProtectionAssociationTLV [data=" + data + "]";
	}
	
	

}
