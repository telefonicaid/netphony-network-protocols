package es.tid.pce.pcep.objects;

/**
 * <p>Represents a SERVER-INDICATION Object, as defined in http://tools.ietf.org/id/draft-ietf-pce-inter-layer-ext-04.txt</p>
 * <p> From draft-ietf-pce-inter-layer-ext-08 </p>
 * The SERVER-INDICATION is optional and is used to indicate that path
   information included in the ERO is server layer information and
   specify the characteristics of the server layer, e.g. the switching
   capability and encoding of the server layer path.

     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | Switching Cap |   Encoding    |           Reserved            |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    ~                       Optional TLVs                           ~
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   The type of SERVER-INDICATION   object is to be assigned by IANA.

   Switching Capability (8 bits): see [RFC4203] for a description of
   parameters.

   Encoding (8 bits): see [RFC3471] for a description of parameters.

   Optional TLVs: Optional TLVs may be included within the object to
   specify more specific server layer path information (e.g., traffic
   parameters).

 * @author ogondio
 *
 */
public class ServerIndication extends PCEPObject {
	
	private int encoding;
	
	private int switchingCap;

	public ServerIndication(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_SERVER_INDICATION);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_SERVER_INDICATION);
	}

	public ServerIndication(byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
		decode();
	}
	
	/**
	 * Encode ServerIndication PCEP Object
	 */
	public void encode() {
		int len=8;//The four bytes of the header + 4 bytes (no tlvs so far)
		this.setObjectLength(len);
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		int pos=4;	
		this.getBytes()[pos]=(byte)(switchingCap & 0xff);
		this.getBytes()[pos+1]=(byte)(encoding & 0xff);
		this.getBytes()[pos+2]=0x00;
		this.getBytes()[pos+3]=0x00;
						
	}
	
	
	/**
	 * Decode SwitchLayer PCEP Object
	 */
	public void decode() throws MalformedPCEPObjectException {
		int offset=4;//Position of the next subobject
		if (ObjectLength<8){
			throw new MalformedPCEPObjectException();
		}
		switchingCap = ( (this.getBytes()[0+offset]&0xFF)<<8) | (this.getBytes()[1+offset]&0xFF) ; 
		encoding = ( (this.getBytes()[2+offset]&0xFF)<<8) | (this.getBytes()[3+offset]&0xFF) ; 
	

	}

	public int getEncoding() {
		return encoding;
	}

	public void setEncoding(int encoding) {
		this.encoding = encoding;
	}

	public int getSwitchingCap() {
		return switchingCap;
	}

	public void setSwitchingCap(int switchingCap) {
		this.switchingCap = switchingCap;
	}


	

}
