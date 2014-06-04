package tid.pce.pcep.objects.tlvs.subtlvs;

import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;


/**
 All PCEP TLVs have the following format:

   Type:   2 bytes
   Length: 2 bytes
   Value:  variable

   A PCEP object TLV is comprised of 2 bytes for the type, 2 bytes
   specifying the TLV length, and a value field.

   The Length field defines the length of the value portion in bytes.
   The TLV is padded to 4-bytes alignment; padding is not included in
   the Length field (so a 3-byte value would have a length of 3, but the
   total size of the TLV would be 8 bytes).

   Unrecognized TLVs MUST be ignored.

   IANA management of the PCEP Object TLV type identifier codespace is
   described in Section 9.

In GEYSERS, when the End-point describes a OperativeSystem, it includes
 the description of the characteristics of the OS in a set of TLVs.
 OperativeSystem is expressed two fields: OSType and Version. The format
 of the version field includes a major version number on 4 bits, a minor
 version number on 6 bits and a build/fix version number on 6 bits.
 
     0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |           Type (TBD)          |           Length              |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |          OS Type              | mj-rv |  mn-rv    | build/fix |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class OperativeSystemSubTLV extends PCEPSubTLV {
	
	private byte[] OSType;
	private int mj_rv, mn_rv, build_fix;
	
		
	public OperativeSystemSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_OS);
		
	}
	
	public OperativeSystemSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedStorageSize SubTLV
	 */
	public void encode() {
		this.setSubTLVValueLength(4);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		System.arraycopy(OSType, 0, this.subtlv_bytes, 4, 2);
		this.subtlv_bytes[6]=(byte)( ( (mj_rv<<4) & 0xF0) | ( (mn_rv>>2) & 0x0F));
		this.subtlv_bytes[7]=(byte)( ( (mn_rv<<6) & 0xC0) | ( (build_fix) & 0x3F));
	}

	
	public void decode() {
		OSType=new byte[2];
		System.arraycopy(this.subtlv_bytes, 4, this.OSType, 0, 2);
		mj_rv=(this.subtlv_bytes[6]>>4&0x0F);
		mn_rv=( ( (this.subtlv_bytes[6]<<2) & 0x3C) | ( (this.subtlv_bytes[7]>>6) & 0x03));
		build_fix=( (this.subtlv_bytes[7]) & 0x3F);
	}


	public void setOSType(byte[] OSType) {
		this.OSType = OSType;
	}
	
	public byte[] isOSType() {
		return OSType;
	}
	
	public void setmj_rv(int mj_rv) {
		this.mj_rv = mj_rv;
	}
	
	public int ismj_rv() {
		return mj_rv;
	}
	
	public void setmn_rv(int mn_rv) {
		this.mn_rv = mn_rv;
	}
	
	public int ismn_rv() {
		return mn_rv;
	}
	
	public void setbuild_fix(int build_fix) {
		this.build_fix = build_fix;
	}
	
	public int isbuild_fix() {
		return build_fix;
	}

}