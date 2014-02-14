package tid.pce.pcep.constructs;

import java.util.LinkedList;
import java.util.logging.Logger;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.objects.Bandwidth;
import tid.pce.pcep.objects.ExplicitRouteObject;
import tid.pce.pcep.objects.GeneralizedBandwidth;
import tid.pce.pcep.objects.GeneralizedBandwidthSSON;
import tid.pce.pcep.objects.IncludeRouteObject;
import tid.pce.pcep.objects.LSPA;
import tid.pce.pcep.objects.MalformedPCEPObjectException;
import tid.pce.pcep.objects.Metric;
import tid.pce.pcep.objects.ObjectParameters;
import tid.pce.pcep.objects.PCEPObject;

/**
 * Path PCEP Construct. RFC 5440
 * From RFC 5440 Section 6.5
 *      <path>::= <ERO><attribute-list>

   where:

    <attribute-list>::=[<LSPA>]
                       [<BANDWIDTH>]
                       [<GENERALIZED-BANDWIDTH>] --ESTADO DRAFT
                       [<metric-list>]
                       [<IRO>]

    <metric-list>::=<METRIC>[<metric-list>]
 * @author ogondio
 *
 */
public class Path extends PCEPConstruct {

	private ExplicitRouteObject eRO;
	private LSPA lSPA;
	private Bandwidth bandwidth;
	private GeneralizedBandwidth generalizedbandwidth;
	private LinkedList<Metric> metricList;
	private IncludeRouteObject iRO;
	
	public Path(){
		metricList=new LinkedList<Metric>();
	}
	
	public Path(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		metricList=new LinkedList<Metric>();
		decode(bytes,offset);
	}
	
	@Override
	public void encode() throws PCEPProtocolViolationException {
		// TODO Auto-generated method stub
		log.finest("Encoding Request Rule");
		int len=0;
		if (eRO!=null){
			eRO.encode();
			len=len+eRO.getLength();
		}
		else {
			log.warning("Path must start with ERO object");
			throw new PCEPProtocolViolationException();
		}
		if (lSPA!=null){
			lSPA.encode();
			len=len+lSPA.getLength();
		}
		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
		}
		if (generalizedbandwidth!=null){
			generalizedbandwidth.encode();
			len=len+generalizedbandwidth.getLength();
		}
		for (int i=0;i<metricList.size();++i){
			(metricList.get(i)).encode();
			len=len+(metricList.get(i)).getLength();
		}
		if (iRO!=null){
			iRO.encode();
			len=len+iRO.getLength();
		}
		log.finest("Path Length = "+len);
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		System.arraycopy(eRO.getBytes(), 0, bytes, offset, eRO.getLength());
		offset=offset+eRO.getLength();
		if (lSPA!=null){
			System.arraycopy(lSPA.getBytes(), 0, bytes, offset, lSPA.getLength());
			offset=offset+lSPA.getLength();
		}
		if (bandwidth!=null){
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset=offset+bandwidth.getLength();
		}
		if (generalizedbandwidth!=null){
			System.arraycopy(generalizedbandwidth.getBytes(), 0, bytes, offset, generalizedbandwidth.getLength());
			offset=offset+generalizedbandwidth.getLength();
		}
		for (int i=0;i<metricList.size();++i){
			System.arraycopy(metricList.get(i).getBytes(), 0, bytes, offset, metricList.get(i).getLength());
			offset=offset+metricList.get(i).getLength();
		}
		if (iRO!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, iRO.getLength());
			offset=offset+iRO.getLength();
		}
	}

	private void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		log.finest("Decoding Path Rule");
		int len=0;		
		int oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_ERO){
			log.finest("ERO Object found");
			try {
				eRO=new ExplicitRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				throw new PCEPProtocolViolationException();
			}
			offset=offset+eRO.getLength();
			len=len+eRO.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LSPA){
			log.finest("LSPA Object found");
			try {
				lSPA=new LSPA(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed LSPA Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+lSPA.getLength();
			len=len+lSPA.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH){
			log.finest("BANDWIDTH Object found");
			try {
				bandwidth=new Bandwidth(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed BANDWIDTH Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+bandwidth.getLength();
			len=len+bandwidth.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_GENERALIZED_BANDWIDTH){
			log.finest("GENERALIZED BANDWIDTH Object found");
			int ot=PCEPObject.getObjectType(bytes, offset);
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_GB_SSON){
				try {
					generalizedbandwidth=new GeneralizedBandwidthSSON(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed GENERALIZED BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			offset=offset+generalizedbandwidth.getLength();
			len=len+generalizedbandwidth.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_METRIC){
			log.finest("METRIC Object found");
			Metric metric;
			try {
				metric = new Metric(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed METRIC Object found");
				throw new PCEPProtocolViolationException();
			}
			metricList.add(metric);
			offset=offset+metric.getLength();
			len=len+metric.getLength();
			oc=PCEPObject.getObjectClass(bytes, offset);
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_IRO){
			log.finest("IRO Object found");
			try {
				iRO=new IncludeRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed IRO Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+iRO.getLength();
			len=len+iRO.getLength();
		}
		this.setLength(len);
	}
	
	

	public void seteRO(ExplicitRouteObject eRO) {
		this.eRO = eRO;
	}
	
	public void setLog(Logger log) {
		this.log = log;
	}
	
	public void setiRO(IncludeRouteObject iRO) {
		this.iRO = iRO;
	}

	public void setlSPA(LSPA lSPA) {
		this.lSPA = lSPA;
	}
	
	public void setMetricList(LinkedList<Metric> metricList) {
		this.metricList = metricList;
	}
	
	public void setGeneralizedbandwidth(GeneralizedBandwidth generalizedbandwidth) {
		this.generalizedbandwidth = generalizedbandwidth;
	}
	
	public void setBandwidth(Bandwidth bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	public IncludeRouteObject getiRO() {
		return iRO;
	}

	public Bandwidth getBandwidth() {
		return bandwidth;
	}

	public GeneralizedBandwidth getGeneralizedbandwidth() {
		return generalizedbandwidth;
	}

	public LinkedList<Metric> getMetricList() {
		return metricList;
	}
	
	public LSPA getlSPA() {
		return lSPA;
	}

	public Logger getLog() {
		return log;
	}
	
	public ExplicitRouteObject geteRO() {
		return eRO;
	}
	
	public String toString(){
		String ret="PATH={ ";
		if (eRO!=null){
			ret=ret+eRO.toString();
		}
		if (lSPA!=null){
			ret=ret+lSPA.toString();
		}
		if (bandwidth!=null){
			ret=ret+bandwidth.toString();
		}
		if (generalizedbandwidth!=null){
			ret=ret+generalizedbandwidth.toString();
		}
		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				ret=ret+metricList.get(i).toString();			}
		}
		if (iRO!=null){
			ret=ret+iRO.toString();
		}
		ret=ret+" }";

		return ret;
	}
}
