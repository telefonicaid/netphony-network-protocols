package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.PceId;
import es.tid.pce.pcep.objects.PceIdIPv4;
import es.tid.pce.pcep.objects.ProcTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  <metric-pce>::=<PCE-ID>
                  [<PROC-TIME>]
                  [<OVERLOAD>]
 * @author ogondio
 *
 */

public class MetricPCE extends PCEPConstruct{


	private PceId pceId; //Compulsory
	private ProcTime procTime;//Optional

	private static final Logger log= LoggerFactory.getLogger("PCEPParser");

	public MetricPCE (){

	}
	public MetricPCE (byte[] bytes, int offset) throws PCEPProtocolViolationException{
		decode(bytes,offset);
	}

	/**
	 * 
	 */
	public void encode() throws PCEPProtocolViolationException{
		//Encoding MetricPCE Rule"
		int len=0;
		if (pceId!=null){
			pceId.encode();
			len=len+pceId.getLength();
		}
		else {
			log.warn("Metric PCE Rule must start with PCE ID object");
			throw new PCEPProtocolViolationException();
		}
		if (procTime!=null){
			procTime.encode();
			len=len+procTime.getLength();
		}		
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		System.arraycopy(pceId.getBytes(), 0, bytes, offset, pceId.getLength());
		offset=offset+pceId.getLength();
		if (procTime!=null){
			System.arraycopy(procTime.getBytes(), 0, bytes, offset, procTime.getLength());
			offset=offset+procTime.getLength();

		}
	}



	/**
	 * Decoding RRO - Bandwidth Rule
	 */
	private void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		int len=0;		
		int oc=PCEPObject.getObjectClass(bytes, offset);
		int ot=PCEPObject.getObjectType(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_PCE_ID){
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_PCE_ID_IPV4){
				try {
					pceId=new PceIdIPv4(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed pceId Object found");
					throw new PCEPProtocolViolationException();
				}
				offset=offset+pceId.getLength();
				len=len+pceId.getLength();
			}
		}
		else {
			throw new PCEPProtocolViolationException();
		}		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_PROC_TIME){
			try {
				procTime=new ProcTime(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed ProcTime Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+procTime.getLength();
			len=len+procTime.getLength();
		}
		this.setLength(len);

	}

	public PceId getPceId() {
		return pceId;
	}
	public void setPceId(PceId pceId) {
		this.pceId = pceId;
	}
	public ProcTime getProcTime() {
		return procTime;
	}
	public void setProcTime(ProcTime procTime) {
		this.procTime = procTime;
	}
	public String toString(){
		String ret="";
		if (pceId!=null){
			ret=ret+pceId.toString();
		}
		if (procTime!=null){
			ret=ret+procTime.toString();
		}
		return ret;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((pceId == null) ? 0 : pceId.hashCode());
		result = prime * result
				+ ((procTime == null) ? 0 : procTime.hashCode());
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
		MetricPCE other = (MetricPCE) obj;
		if (pceId == null) {
			if (other.pceId != null)
				return false;
		} else if (!pceId.equals(other.pceId))
			return false;
		if (procTime == null) {
			if (other.procTime != null)
				return false;
		} else if (!procTime.equals(other.procTime))
			return false;
		return true;
	}
	
	

}
