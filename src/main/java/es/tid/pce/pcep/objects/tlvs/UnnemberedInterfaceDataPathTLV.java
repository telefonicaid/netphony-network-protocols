package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * 
 * @author b.mvas
 *
 */
public class UnnemberedInterfaceDataPathTLV extends PCEPTLV{
	private String switchID;
	private int port;


	public UnnemberedInterfaceDataPathTLV()
	{
		this.setTLVType(ObjectParameters.PCEP_GENERALIZED_END_POINTS_TYPE_P2P);

	}

	public UnnemberedInterfaceDataPathTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() 
	{
		log.debug("Encoding UnnemberedInterfaceDataPathID EndPoint TLV");
		int length = 8 + 6 + 4;

		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset = 4;

		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(switchID),0, this.tlv_bytes, offset, 8);

		offset += 8;
		ByteHandler.IntToBuffer(0,offset*8, 32, port,this.tlv_bytes);

		log.debug("switchID after encoding :::: "+switchID);
		log.debug("port after encoding :::: "+port);

	}

	public void decode() throws MalformedPCEPObjectException
	{
		log.debug("Decoding UnnemberedInterfaceDataPathID EndPoint TLV");

		int offset = 4;
		if (this.getTLVValueLength()==0){
			throw new MalformedPCEPObjectException();
		}
		log.debug("TLV Length:" + this.getTLVValueLength());


		byte[] mac=new byte[8]; 
		System.arraycopy(this.tlv_bytes, offset, mac, 0, 8);
		switchID=ByteHandler.ByteMACToString(mac);

		offset += 8;
		port = ByteHandler.easyCopy(0,31,tlv_bytes[offset],tlv_bytes[offset + 1],tlv_bytes[offset + 2],tlv_bytes[offset + 3]);

		log.debug("switchID after decoding :::: "+switchID);
		log.debug("port after decoding :::: "+port);

	}


	/*
	 * GETTERS AND SETTERS
	 */
	public String getSwitchID() {
		return switchID;
	}

	public void setSwitchID(String switchID) {
		this.switchID = switchID;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
