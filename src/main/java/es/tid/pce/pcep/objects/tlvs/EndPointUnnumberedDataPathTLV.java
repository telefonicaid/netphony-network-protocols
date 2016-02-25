package es.tid.pce.pcep.objects.tlvs;

import es.tid.of.DataPathID;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * UNNUMBERED-Datapath-ENDPOINT TLV.
 * 

   This TLV represent an unnumbered datapath.  This TLV has the same
   semantic as in [RFC3477] The TLV value is encoded as follow (TLV-
   Type=TBA)

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |              Datapath ID (8 bytes)                            |
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                       Interface ID (32 bits)                  |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   This TLV MAY be ignored, in which case a PCRep with NO-PATH should be
   responded, as described in Section 2.5.1.
 * 
 * @author b.jmgj
 *
 */
public class EndPointUnnumberedDataPathTLV extends PCEPTLV{
	public DataPathID switchID;
	public long IfID;

	public EndPointUnnumberedDataPathTLV()
	{
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_UNNUMBERED_ENDPOINT_DATAPATHID);
		this.switchID=new DataPathID();
	}

	public EndPointUnnumberedDataPathTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		super(bytes,offset);
		this.switchID=new DataPathID();		
		decode();
	}

	@Override
	public void encode() 
	{
		log.info("Encoding UnnemberedInterfaceDataPathID EndPoint TLV");
		int length = 8 + 4;

		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset = 4;

		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(switchID.getDataPathID()),0, this.tlv_bytes, offset, 8);

		offset += 8;
		
		this.tlv_bytes[offset]=(byte)(IfID >>> 24);
		this.tlv_bytes[offset+1]=(byte)(IfID >>> 16 & 0xff);
		this.tlv_bytes[offset+2]=(byte)(IfID >>> 8 & 0xff);
		this.tlv_bytes[offset+3]=(byte)(IfID & 0xff);
		

		log.info("switchID after encoding :::: "+switchID.getDataPathID());
		log.info("port after encoding :::: "+IfID);

	}

	public void decode() throws MalformedPCEPObjectException
	{
		log.info("Decoding UnnemberedInterfaceDataPathID EndPoint TLV"); 

		int offset = 4;
		if (this.getTLVValueLength()==0){
			throw new MalformedPCEPObjectException();
		}
		log.debug("TLV Length:" + this.getTLVValueLength());


		byte[] dpid=new byte[8]; 
		System.arraycopy(this.tlv_bytes, offset, dpid, 0, 8);
		switchID.setDataPathID(ByteHandler.ByteMACToString(dpid));
		log.info("jm ver decode switchID: "+switchID.getDataPathID());

		offset += 8;
		
		IfID=0;
		for (int k = 0; k < 4; k++) {
			IfID = (IfID << 8) | (this.tlv_bytes[k+offset] & 0xff);
		}
		
		log.info("switchID after decoding :::: "+switchID.getDataPathID());
		log.info("port after decoding :::: "+IfID);

	}


	/*
	 * GETTERS AND SETTERS
	 */
	public DataPathID getDataPathID() {
		return switchID;
	}
	
	public String getSwitchID() {
		return switchID.getDataPathID();
	}

	public void setSwitchID(String switchID) {
		this.switchID.setDataPathID(switchID);
	}

	public long getIfID() {
		return IfID;
	}

	public void setIfID(long IfID) {
		this.IfID = IfID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (IfID ^ (IfID >>> 32));
		result = prime * result
				+ ((switchID == null) ? 0 : switchID.hashCode());
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
		EndPointUnnumberedDataPathTLV other = (EndPointUnnumberedDataPathTLV) obj;
		if (IfID != other.IfID)
			return false;
		if (switchID == null) {
			if (other.switchID != null)
				return false;
		} else if (!switchID.equals(other.switchID))
			return false;
		return true;
	}

	public void setSwitchID(DataPathID switchID) {
		this.switchID = switchID;
	}
	
	



}
