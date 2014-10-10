package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.ExplicitRouteObject;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;


/**
 *       0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Type                  |  Length                       |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |            				   time                    	 	 	 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     | 				Not used							    	  |b |     
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     | 							    ERO						    	 |
     | 							   							    	 |    
     | 							   							    	 |     
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * @author mcs
 *
 */

public class PathReservationTLV extends PCEPTLV {

	private ExplicitRouteObject eRO;
	private long time;
	private boolean bidirectional;

	public PathReservationTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_TYPE_PATH_RESERVATION;
	}

	public PathReservationTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);		
		decode();
	}
	 
	@Override
	public void encode() {
		log.fine("Encoding PathReservation TLV");
		int len=0;
		if (eRO!=null){
			eRO.encode();
			len=len+eRO.getLength();
		}
		else {
			log.severe("There are not notificationSubTLV");
			//throw new MalformedPCEPObjectException();
			return;//FIXME: ARREGLAR PARA LANZAR EXCEPCIONES
		}
		
		//Lenght TLV
		this.setTLVValueLength(len+4+4);//(ERO)+long+boolean
	
		this.tlv_bytes=new byte[this.TotalTLVLength];
		int offset=4;

		//Time
		this.tlv_bytes[offset]=(byte)(time >>> 24);
		this.tlv_bytes[offset+1]=(byte)(time >> 16 & 0xFF);
		this.tlv_bytes[offset+2]=(byte)(time >> 8  & 0xFF);
		this.tlv_bytes[offset+3]=(byte)(time & 0xFF);
		
		//Bidirectional
		offset=offset+4;
		this.tlv_bytes[offset+4]=(byte)((bidirectional?1:0));
		offset=offset+4;
		encodeHeader();
		
		//ERO
		System.arraycopy(eRO.getBytes(), 0, this.tlv_bytes, offset, eRO.getLength());
				
	}

	public void decode() throws MalformedPCEPObjectException{
		log.info("Decoding PathReservation TLV");
		int offset=4;
		//Time
		for (int k = 0; k < 4; k++) {
			time = (time << 8) | (this.tlv_bytes[k+offset] & 0xff);
		}
		offset=offset+4;
		//Bidirectional
		bidirectional=(this.tlv_bytes[offset]&0x01)==0x01;
		offset=offset+4;
		//ERO
		int oc=PCEPObject.getObjectClass(this.tlv_bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_ERO){
			log.finest("ERO Object found");
			try {
				eRO=new ExplicitRouteObject(this.tlv_bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				throw new MalformedPCEPObjectException();
			}
			offset=offset+eRO.getLength();
		}

	}

	public long getTime() {
		return time;
	}

	public ExplicitRouteObject geteRO() {
		return eRO;
	}

	public void seteRO(ExplicitRouteObject eRO) {
		this.eRO = eRO;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}



}
