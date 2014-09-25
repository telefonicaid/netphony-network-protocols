package tid.pce.pcep.constructs;

import java.util.LinkedList;
import java.util.logging.Logger;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.objects.BandwidthExistingLSP;
import tid.pce.pcep.objects.BandwidthRequested;
import tid.pce.pcep.objects.ExplicitRouteObject;
import tid.pce.pcep.objects.BandwidthRequestedGeneralizedBandwidth;
import tid.pce.pcep.objects.Bandwidth;
import tid.pce.pcep.objects.IncludeRouteObject;
import tid.pce.pcep.objects.InterLayer;
import tid.pce.pcep.objects.SwitchLayer;
import tid.pce.pcep.objects.ReqAdapCap;
import tid.pce.pcep.objects.LSPA;
import tid.pce.pcep.objects.ServerIndication;
import tid.pce.pcep.objects.MalformedPCEPObjectException;
import tid.pce.pcep.objects.Metric;
import tid.pce.pcep.objects.ObjectParameters;
import tid.pce.pcep.objects.PCEPObject;
import tid.pce.pcep.objects.SRERO;

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
  						[<INTER-LAYER>]
                            [<SWITCH-LAYER>]
                            [<REQ-ADAP-CAP>]
                            [<SERVER-INDICATION>]
    <metric-list>::=<METRIC>[<metric-list>]
 * @author ogondio
 *
 */
public class Path extends PCEPConstruct {

	private ExplicitRouteObject eRO;
	private SRERO SRERO;
	
	private LSPA lSPA;
	private Bandwidth bandwidth;
	private LinkedList<Metric> metricList;
	private IncludeRouteObject iRO;
	private InterLayer interLayer;
	private SwitchLayer switchLayer;
	private ReqAdapCap reqAdapCap;
	private ServerIndication serverIndication;
	
	
	public Path(){
		metricList=new LinkedList<Metric>();
	}
	
	public Path(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		metricList=new LinkedList<Metric>();
		decode(bytes,offset);
	}
	
	public void encode() throws PCEPProtocolViolationException {

		log.finest("Encoding Request Rule");
		int len=0;
		if (eRO!=null){
			eRO.encode();
			len=len+eRO.getLength();
		}
		else if (SRERO!=null){
			SRERO.encode();
			len+=SRERO.getLength();
		}
		else {
			log.warning("Path must start with ERO or SRERO object");
			throw new PCEPProtocolViolationException();
		}
		//log.info("Path encoding "+z);z++;
		if (lSPA!=null){
			lSPA.encode();
			len=len+lSPA.getLength();
		}
		//log.info("Path encoding "+z);z++;
		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
		}
		//log.info("Path encoding "+z);z++;
		for (int i=0;i<metricList.size();++i){
			(metricList.get(i)).encode();
			len=len+(metricList.get(i)).getLength();
		}
		//log.info("Path encoding "+z);z++;
		if (iRO!=null){
			iRO.encode();
			len=len+iRO.getLength();
		}
		if (interLayer!=null){
			interLayer.encode();
			len=len+interLayer.getLength();
		}
		if (switchLayer!=null){
			switchLayer.encode();
			len=len+switchLayer.getLength();
		}
		if (reqAdapCap!=null){
			reqAdapCap.encode();
			len=len+reqAdapCap.getLength();
		}
		if (serverIndication!=null){
			serverIndication.encode();
			len=len+serverIndication.getLength();
		}
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		
		if(eRO!=null)
		{
			System.arraycopy(eRO.getBytes(), 0, bytes, offset, eRO.getLength());
			offset=offset+eRO.getLength();
		}
		else if(SRERO!=null)
		{
			System.arraycopy(SRERO.getBytes(), 0, bytes, offset, SRERO.getLength());
			offset=offset+SRERO.getLength();			
		}	
			
		if (lSPA!=null){
			System.arraycopy(lSPA.getBytes(), 0, bytes, offset, lSPA.getLength());
			offset=offset+lSPA.getLength();
		}
		if (bandwidth!=null){
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset=offset+bandwidth.getLength();
		}
		//log.info("Path encoding "+z);z++;
		for (int i=0;i<metricList.size();++i){
			System.arraycopy(metricList.get(i).getBytes(), 0, bytes, offset, metricList.get(i).getLength());
			offset=offset+metricList.get(i).getLength();
		}
		//log.info("Path encoding "+z);z++;
		if (iRO!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, iRO.getLength());
			offset=offset+iRO.getLength();
		}
		
		if (interLayer!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, interLayer.getLength());
			offset=offset+interLayer.getLength();
		}
		if (switchLayer!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, switchLayer.getLength());
			offset=offset+switchLayer.getLength();
		}
		if (reqAdapCap!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, reqAdapCap.getLength());
			offset=offset+reqAdapCap.getLength();
		}
		if (serverIndication!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, serverIndication.getLength());
			offset=offset+serverIndication.getLength();
		}
		
	}

	private void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		log.finest("Decoding Path Rule");
		int len=0;		
		int oc=PCEPObject.getObjectClass(bytes, offset);
		int ot=PCEPObject.getObjectType(bytes, offset);
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
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SR_ERO){
			log.finest("SRERO Object found");
			try {
				SRERO=new SRERO(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				throw new PCEPProtocolViolationException();
			}
			offset=offset+SRERO.getLength();
			len=len+SRERO.getLength();
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
		ot=PCEPObject.getObjectType(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH){
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_REQUEST){
				log.finest("BANDWIDTH Request Object found");
				try {
					bandwidth=new BandwidthRequested(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}			
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_EXISTING_TE_LSP){
				log.finest("BANDWIDTH Existing TE LSP Object found");
				try {
					bandwidth=new BandwidthExistingLSP(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_REQUEST){
				log.finest("BANDWIDTH GENERALIZED BANDWIDTH Request Object found");
				try {
					bandwidth=new BandwidthRequestedGeneralizedBandwidth(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_EXISTING_TE_LSP){
				log.finest("BANDWIDTH GENERALIZED BANDWIDTH Existing TE LSP Object found");
				try {
					bandwidth=new BandwidthRequested(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else {
				log.warning("Malformed BANDWIDTH Object found");
				throw new PCEPProtocolViolationException();
			}
			
			offset=offset+bandwidth.getLength();
			len=len+bandwidth.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
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
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_INTER_LAYER){
			log.finest("interLayer Object found");
			try {
				interLayer=new InterLayer(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed interLayer Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+interLayer.getLength();
			len=len+interLayer.getLength();
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SWITCH_LAYER){
			log.finest("switchLayer Object found");
			try {
				switchLayer=new SwitchLayer(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed switchLayer Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+switchLayer.getLength();
			len=len+switchLayer.getLength();
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_REQ_ADAP_CAP){
			log.finest("reqAdapCap Object found");
			try {
				reqAdapCap=new ReqAdapCap(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed reqAdapCap Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+reqAdapCap.getLength();
			len=len+reqAdapCap.getLength();
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SERVER_INDICATION){
			log.finest("serverIndication Object found");
			try {
				serverIndication=new ServerIndication(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed serverIndication Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+serverIndication.getLength();
			len=len+serverIndication.getLength();
		}
		
		this.setLength(len);
	}
	
	
	public void setSRERO(SRERO srero)
	{
		this.SRERO = srero;
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
	
	
	public void setBandwidth(Bandwidth bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	public IncludeRouteObject getiRO() {
		return iRO;
	}

	public Bandwidth getBandwidth() {
		return bandwidth;
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
	
	public SRERO getSRERO() {
		return this.SRERO;
	}
	
	public InterLayer getInterLayer() {
		return interLayer;
	}

	public void setInterLayer(InterLayer interLayer) {
		this.interLayer = interLayer;
	}

	public SwitchLayer getSwitchLayer() {
		return switchLayer;
	}

	public void setSwitchLayer(SwitchLayer switchLayer) {
		this.switchLayer = switchLayer;
	}

	public ReqAdapCap getReqAdapCap() {
		return reqAdapCap;
	}

	public void setReqAdapCap(ReqAdapCap reqAdapCap) {
		this.reqAdapCap = reqAdapCap;
	}

	public ServerIndication getServerIndication() {
		return serverIndication;
	}

	public void setServerIndication(ServerIndication serverIndication) {
		this.serverIndication = serverIndication;
	}

	public String toString(){
		String ret="PATH={ ";
		if (SRERO!=null){
			ret+=SRERO.toString();
		}
		if (eRO!=null){
			ret=ret+eRO.toString();
		}
		if (lSPA!=null){
			ret=ret+lSPA.toString();
		}
		if (bandwidth!=null){
			ret=ret+bandwidth.toString();
		}
		if (serverIndication!=null){
			ret=ret+serverIndication.toString();
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
