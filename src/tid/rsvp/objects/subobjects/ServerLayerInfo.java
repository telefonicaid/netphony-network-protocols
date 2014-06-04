package tid.rsvp.objects.subobjects;



/**
 * Server Layer Infor. ERO sub-object. 
 *  from <a href="link http://tools.ietf.org/html/draft-ietf-pce-inter-layer-ext-05#section-3.5"> </a>. 
 * 
 *  This sub-object can be used for specifying the detail
   server layer information of a server layer path, e.g. the switching
   capability and encoding of the server layer path.

     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |L|    Type     |     Length    |           Reserved            |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | Switching Cap |   Encoding    |           Reserved            |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    ~                       Optional TLVs                           ~
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   The type of SERVER_LAYER_INFO sub-object is to be assigned by IANA.

   L: The L bit SHOULD NOT be set, so that the subobject represents a
   strict hop in the explicit route.

   Switching Capability (8 bits): see [RFC4203] for a description of
   parameters.
	From RFC 4230:
	The Switching Capability (Switching Cap) field contains one of the
   following values:
      1     Packet-Switch Capable-1 (PSC-1)
      2     Packet-Switch Capable-2 (PSC-2)
      3     Packet-Switch Capable-3 (PSC-3)
      4     Packet-Switch Capable-4 (PSC-4)
      51    Layer-2 Switch Capable  (L2SC)
      100   Time-Division-Multiplex Capable (TDM)
      150   Lambda-Switch Capable   (LSC)
      200   Fiber-Switch Capable    (FSC)
      



   Encoding (8 bits): see [RFC3471] for a description of parameters.
   

   Optional TLVs: Optional TLVs may be included within the object to
   specify more specific server layer path information (e.g., traffic
   parameters).

   This sub-object MAY be added immediately behind the node or link
   address sub-objects of the two edge nodes of a service path.
 * @author mcs
 *
 */
public class ServerLayerInfo extends EROSubobject{

	
	public int switchingCap;
	public int encoding;

	
	public ServerLayerInfo(){
		super();
		this.setType(SubObjectValues.ERO_SUBOBJECT_LAYER_INFO);
	}
	public ServerLayerInfo(byte [] bytes, int offset){
		super(bytes, offset);
		decode();
	}
	
	
	@Override
	public void encode() {
			this.erosolength=8;
			this.subobject_bytes=new byte[this.erosolength];
			encodeSoHeader();

			this.subobject_bytes[4]=(byte)switchingCap;
			this.subobject_bytes[5]=(byte)encoding;

	}

	@Override
	public void decode() {
		switchingCap=this.subobject_bytes[4]&0xFF;
		encoding=this.subobject_bytes[5]&0xFF;
		
	}
	public int getSwitchingCap() {
		return switchingCap;
	}
	public void setSwitchingCap(int switchingCap) {
		this.switchingCap = switchingCap;
	}
	public int getEncoding() {
		return encoding;
	}
	public void setEncoding(int encoding) {
		this.encoding = encoding;
	}

	public String toString(){
		String ret="/SERVER_LAYER_INFO";
		return ret;
	}

}
