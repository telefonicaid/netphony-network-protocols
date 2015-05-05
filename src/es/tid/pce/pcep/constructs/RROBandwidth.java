package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.BandwidthRequested;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.ReportedRouteObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [<RRO>[<BANDWIDTH>]]
 * @author ogondio
 *
 */
public class RROBandwidth extends PCEPConstruct{

	private ReportedRouteObject rRO; //Compulsory
	private BandwidthRequested bandwidth;//Optional
	
	private static final Logger log= LoggerFactory.getLogger("PCEPParser");
	
	public RROBandwidth (byte[] bytes, int offset) throws PCEPProtocolViolationException{
		decode(bytes,offset);
	}
	
	/**
	 * 
	 */
	public void encode() throws PCEPProtocolViolationException{
		//Encoding RRO/BANDWIDTH Rule");
		int len=0;
		if (rRO!=null){
			rRO.encode();
			len=len+rRO.getLength();
		}
		else {
			log.warn("RRO/BANDWIDTH Rule must start with RRO object");
				throw new PCEPProtocolViolationException();
		}
		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
		}		
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
		//Decoding RRO-Bandwidth Rule
		int len=0;		
		int oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_RRO){
			try {
				rRO=new ReportedRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed ReportedRouteObject Object found");
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
			try {
				rRO=new ReportedRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed BANDWIDTH Object found");
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
