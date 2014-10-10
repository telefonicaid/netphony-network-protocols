package es.tid.pce.pcep.constructs;

import java.util.logging.Logger;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.BandwidthRequested;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.ReportedRouteObject;

/**
 * [<RRO>[<BANDWIDTH>]]
 * @author ogondio
 *
 */
public class RROBandwidth extends PCEPConstruct{

	private ReportedRouteObject rRO; //Compulsory
	private BandwidthRequested bandwidth;//Optional
	
	private Logger log=Logger.getLogger("PCEPParser");
	
	public RROBandwidth (byte[] bytes, int offset) throws PCEPProtocolViolationException{
		decode(bytes,offset);
	}
	
	/**
	 * 
	 */
	public void encode() throws PCEPProtocolViolationException{
		log.finest("Encoding RRO/BANDWIDTH Rule");
		int len=0;
		if (rRO!=null){
			rRO.encode();
			len=len+rRO.getLength();
		}
		else {
			log.warning("RRO/BANDWIDTH Rule must start with RRO object");			
				throw new PCEPProtocolViolationException();
		}
		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
		}		
		log.info("RRO/Bandwidth Length = "+len);
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		System.arraycopy(rRO.getBytes(), 0, bytes, offset, rRO.getLength());
		offset=offset+rRO.getLength();
		if (bandwidth!=null){
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset=offset+bandwidth.getLength();
		}
	}
	


	/**
	 * Decoding RRO - Bandwidth Rule
	 */
	private void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		log.finest("Decoding RRO-Bandwidth Rule");
		int len=0;		
		int oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_RRO){
			log.finest("RRO Object found");
			try {
				rRO=new ReportedRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed ReportedRouteObject Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+rRO.getLength();
			len=len+rRO.getLength();
		}
		else {
			throw new PCEPProtocolViolationException();
		}		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH){
			log.finest("BANDWDITH Object found");
			try {
				rRO=new ReportedRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed BANDWIDTH Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+rRO.getLength();
			len=len+rRO.getLength();
		}
		this.setLength(len);
		
	}



	public ReportedRouteObject getrRO() {
		return rRO;
	}



	public void setrRO(ReportedRouteObject rRO) {
		this.rRO = rRO;
	}



	public BandwidthRequested getBandwidth() {
		return bandwidth;
	}



	public void setBandwidth(BandwidthRequested bandwidth) {
		this.bandwidth = bandwidth;
	}

}
