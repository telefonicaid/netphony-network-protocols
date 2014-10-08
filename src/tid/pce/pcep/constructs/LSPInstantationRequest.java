package tid.pce.pcep.constructs;

import java.util.LinkedList;
import java.util.logging.Logger;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.objects.*;

/**
 <lsp-instantiation-request> ::= <END-POINTS>
                                   <LSPA>
                                   [<ERO>]
                                   [<BANDWIDTH>]
                                   [<metric-list>]
 * </p>
 * @author ogondio
 *
 */
public class LSPInstantationRequest extends PCEPConstruct{

	private EndPoints endPoints;//COMPULSORY!!!
	private LSPA lSPA;//COMPULSORY!!
	private ExplicitRouteObject eRO;
	private BandwidthRequested bandwidth;
	private LinkedList<Metric> metricList;
	
	private Logger log=Logger.getLogger("PCEPParser");
	
	/**
	 * Default constructor. 
	 * Use this method to create a new Request from scratch
	 */
	public LSPInstantationRequest(){
		this.metricList=new LinkedList<Metric>();
	}
	
	/**
	 * 
	 * Use this method to create a new Request from a sequence of bytes	 
	 * @param bytes
	 * @param offset
	 * @throws PCEPProtocolViolationException
	 */
	public LSPInstantationRequest(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		this.metricList=new LinkedList<Metric>();
		decode(bytes,offset);
	}

	public void encode() throws PCEPProtocolViolationException{
		log.finest("Encoding Request Construct");
		int len=0;
		
		if (endPoints!=null){
			endPoints.encode();
			len=len+endPoints.getLength();
		}
		else {
			log.warning("EndPoints not found! They are compulsory");
			throw new PCEPProtocolViolationException();
		}
		if (lSPA!=null){
			lSPA.encode();
			len=len+lSPA.getLength();
		}
		else{
			log.warning("lSPA not found! It is compulsory");
			throw new PCEPProtocolViolationException();
		}
		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
		}
		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				(metricList.get(i)).encode();
				len=len+(metricList.get(i)).getLength();
			}
		}
		
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;

		System.arraycopy(endPoints.getBytes(), 0, bytes, offset, endPoints.getLength());
		offset=offset+endPoints.getLength();

		System.arraycopy(lSPA.getBytes(), 0, bytes, offset, lSPA.getLength());
		offset=offset+lSPA.getLength();

		if (bandwidth!=null){
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset=offset+bandwidth.getLength();
		}
		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				System.arraycopy(metricList.get(i).getBytes(), 0, bytes, offset, metricList.get(i).getLength());
				offset=offset+metricList.get(i).getLength();
			}
		}
	}

	/**
	 * Decode a Request rule;
	 */
	private void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		log.finest("Decoding Request Rule");
		int len=0;		
		int max_offset=bytes.length;
		if (offset>=max_offset){
			log.warning("Empty Request construct!!!");
			throw new PCEPProtocolViolationException();
		}
		// END-POINTS
		int oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS){
			int ot=PCEPObject.getObjectType(bytes, offset);
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV4){
				try {
					endPoints=new EndPointsIPv4(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed ENDPOINTS IPV4 Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV6){
				try {
					endPoints=new EndPointsIPv6(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed ENDPOINTSIPV6 Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_GENERALIZED_ENDPOINTS){
				try {
					endPoints=new GeneralizedEndPoints(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed GENERALIZED END POINTS Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			else {
				log.warning("ENDPOINTS TYPE NOT SUPPORTED");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+endPoints.getLength();
			len=len+endPoints.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		else {
			log.warning("LSPInstantationRequest must start with ENDPOINTS");
			throw new PCEPProtocolViolationException();
		}
		// LSPA
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
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}else{
			log.warning("LSPInstantationRequest follow with an LSPA after the ENDPOINTS");
		}
		// ERO
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_ERO){
			log.finest("ERO object Found");
			try {
				eRO=new ExplicitRouteObject(bytes, offset);
			}catch (MalformedPCEPObjectException e){
				log.warning("Malformed ERO Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+eRO.getLength();
			len=len+eRO.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		
		// Bandwidth
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH){
			log.finest("BANDWIDTH Object found");
			try {
				bandwidth=new BandwidthRequested(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed BANDWIDTH Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+bandwidth.getLength();
			len=len+bandwidth.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		// Metric List
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
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
			oc=PCEPObject.getObjectClass(bytes, offset);
		}
		this.setLength(len);
	}
	
	public EndPoints getEndPoints() {
		return endPoints;
	}

	public void setEndPoints(EndPoints endPoints) {
		this.endPoints = endPoints;
	}

	public LSPA getlSPA() {
		return lSPA;
	}

	public void setlSPA(LSPA lSPA) {
		this.lSPA = lSPA;
	}
	
	public ExplicitRouteObject geteRO() {
		return eRO;
	}

	public void seteRO(ExplicitRouteObject eRO) {
		this.eRO = eRO;
	}

	public BandwidthRequested getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(BandwidthRequested bandwidth) {
		this.bandwidth = bandwidth;
	}

	public LinkedList<Metric> getMetricList() {
		return metricList;
	}

	public void setMetricList(LinkedList<Metric> metricList) {
		this.metricList = metricList;
	}

	public String toString(){
		StringBuffer sb=new StringBuffer();
		
		if (endPoints!=null){
			sb.append(endPoints);
		}
		if (lSPA!=null){
			sb.append(lSPA);
		}
		if (eRO!=null){
			sb.append(eRO.toString());
		}
		if (bandwidth!=null){
			sb.append(bandwidth.toString());
		}
		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				sb.append(metricList.get(i).toString());
			}
		}
		return sb.toString();
	}
	
	public LSPInstantationRequest duplicate(){
		LSPInstantationRequest req=new LSPInstantationRequest();
		req.setEndPoints(this.endPoints);
		req.setlSPA(this.lSPA);
		req.seteRO(this.eRO);
		req.setBandwidth(this.bandwidth);
		req.setMetricList(this.metricList);
		return req;
	}
}
