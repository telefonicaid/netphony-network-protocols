package es.tid.pce.pcep.objects.subobjects;

import java.net.*;

/** 
Unnumbered Interface ID Subobject

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |X|  Type = 3   |     Length    |    Reserved   |  Attribute    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        TE Router ID                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        Interface ID                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * @author Oscar Gonzalez de Dios
 *
 */
public class UnnumberIfIDXROSubobject extends XROSubobject{

	public Inet4Address routerID;
	public long interfaceID;//32 bit Interface ID
	
	
	public UnnumberIfIDXROSubobject(){
		super();
		this.setType(XROSubObjectValues.XRO_SUBOBJECT_UNNUMBERED_IF_ID);
	}
	
	public UnnumberIfIDXROSubobject(byte[] bytes, int offset){
		super(bytes, offset);
		decode();		
	}
	
	/**
	 * Encode Unnumbered interface ERO Subobject
	 */
	public void encode(){
		this.erosolength=12;
		this.subobject_bytes=new byte[this.erosolength];
		encodeSoHeader();
		this.subobject_bytes[2]=0x00;
		this.subobject_bytes[3]=(byte) attribute;
		System.arraycopy(routerID.getAddress(), 0, this.subobject_bytes, 4, 4);
		this.subobject_bytes[8]=(byte)(interfaceID >>> 24);
		this.subobject_bytes[9]=(byte)(interfaceID >>> 16 & 0xff);
		this.subobject_bytes[10]=(byte)(interfaceID >>> 8 & 0xff);
		this.subobject_bytes[11]=(byte)(interfaceID & 0xff);	
	}
	
	/**
	 * Decode Unnumbered interface ERO Subobject
	 */
	public void decode(){
		decodeSoHeader();
		byte[] ipadd=new byte[4]; 
		System.arraycopy(this.subobject_bytes,4, ipadd, 0, 4);
		try {
			routerID=(Inet4Address)Inet4Address.getByAddress(ipadd);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		interfaceID=0;
		attribute=this.subobject_bytes[3]&0xFF;
		for (int k = 0; k < 4; k++) {
			interfaceID = (interfaceID << 8) | (this.subobject_bytes[k+8] & 0xff);
		}			
	}
	
	public Inet4Address getRouterID() {
		return routerID;
	}
	public void setRouterID(Inet4Address routerID) {
		this.routerID = routerID;
	}
	public long getInterfaceID() {
		return interfaceID;
	}
	public void setInterfaceID(long interfaceID) {
		this.interfaceID = interfaceID;
	}
	
	public String toString(){
		return routerID.toString()+":"+interfaceID;
	}


}
