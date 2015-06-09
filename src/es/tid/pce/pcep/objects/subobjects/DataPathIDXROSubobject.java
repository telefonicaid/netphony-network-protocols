package es.tid.pce.pcep.objects.subobjects;

import java.io.UnsupportedEncodingException;

/** 
 * @author b.mvas
 *
 */
public class DataPathIDXROSubobject extends XROSubobject{

	public String routerID;
	public int routerLength;
	
	public DataPathIDXROSubobject(){
		super();
		this.setType(XROSubObjectValues.XRO_SUBOBJECT_DATAPATH_ID);
	}
	
	public DataPathIDXROSubobject(byte[] bytes, int offset){
		super(bytes, offset);
		decode();		
	}
	
	/**
	 * Encode Unnumbered interface ERO Subobject
	 */
	public void encode(){
		this.erosolength=30;
		this.subobject_bytes=new byte[this.erosolength];
		encodeSoHeader();
		this.subobject_bytes[2]=0x00;
		this.subobject_bytes[3]=(byte) attribute;
		System.arraycopy(routerID.getBytes(), 0, this.subobject_bytes,4, 23);

	}
	
	/**
	 * Decode Unnumbered interface ERO Subobject
	 */
	public void decode(){
		decodeSoHeader();
		byte[] dpid=new byte[23]; 
		System.arraycopy(this.subobject_bytes,4, dpid, 0, 23);
		try {
			routerID=new String(dpid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		attribute=this.subobject_bytes[3]&0xFF;
	
	}
	
	public String getRouterID() {
		return routerID;
	}
	public void setRouterID(String dpidXRO) {
		this.routerID = dpidXRO;
	}
	
	public String toString(){
		return routerID.toString();
	}


}
