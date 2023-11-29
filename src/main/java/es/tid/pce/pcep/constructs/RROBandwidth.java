package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.Bandwidth;
import es.tid.pce.pcep.objects.BandwidthExistingLSP;
import es.tid.pce.pcep.objects.BandwidthExistingLSPGeneralizedBandwidth;
import es.tid.pce.pcep.objects.BandwidthRequested;
import es.tid.pce.pcep.objects.BandwidthRequestedGeneralizedBandwidth;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.ReportedRouteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code
 * [<RRO>[<BANDWIDTH>]]}
 * @author ogondio
 *
 */
public class RROBandwidth extends PCEPConstruct{

	private ReportedRouteObject rRO; //Compulsory
	private Bandwidth bandwidth;//Optional

	private static final Logger log= LoggerFactory.getLogger("PCEPParser");

	public RROBandwidth(){
		super();
	};

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
		int ot = PCEPObject.getObjectType(bytes, offset);
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

		// Bandwidth
		try {
			oc = PCEPObject.getObjectClass(bytes, offset);
			ot = PCEPObject.getObjectType(bytes, offset);
			if (oc == ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH) {
				if (ot == ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_REQUEST) {
					bandwidth = new BandwidthRequested(bytes, offset);
				} else if (ot == ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_EXISTING_TE_LSP) {
					bandwidth = new BandwidthExistingLSP(bytes, offset);
				} else if (ot == ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_REQUEST) {
					bandwidth = new BandwidthRequestedGeneralizedBandwidth(bytes, offset);
				} else if (ot == ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_EXISTING_TE_LSP) {
					bandwidth = new BandwidthExistingLSPGeneralizedBandwidth(bytes, offset);
				} else {
					log.warn("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}

				offset = offset + bandwidth.getLength();
				len = len + bandwidth.getLength();
				if (offset >= bytes.length) {
					this.setLength(len);
					return;
				}
			}
		} catch (MalformedPCEPObjectException e) {
			log.warn("Malformed BANDWIDTH Object found");
			throw new PCEPProtocolViolationException();
		}
		this.setLength(len);

	}



	public ReportedRouteObject getRRO() {
		return rRO;
	}



	public void setRRO(ReportedRouteObject rRO) {
		this.rRO = rRO;
	}


	public Bandwidth getBandwidth() {
		return bandwidth;
	}



	public void setBandwidth(Bandwidth bandwidth) {
		this.bandwidth = bandwidth;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((bandwidth == null) ? 0 : bandwidth.hashCode());
		result = prime * result + ((rRO == null) ? 0 : rRO.hashCode());
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
		RROBandwidth other = (RROBandwidth) obj;
		if (bandwidth == null) {
			if (other.bandwidth != null)
				return false;
		} else if (!bandwidth.equals(other.bandwidth))
			return false;
		if (rRO == null) {
			if (other.rRO != null)
				return false;
		} else if (!rRO.equals(other.rRO))
			return false;
		return true;
	}



}
