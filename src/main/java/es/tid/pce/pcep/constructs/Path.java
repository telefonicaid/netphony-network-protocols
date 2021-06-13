package es.tid.pce.pcep.constructs;

import java.util.LinkedList;
import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.Bandwidth;
import es.tid.pce.pcep.objects.BandwidthExistingLSP;
import es.tid.pce.pcep.objects.BandwidthExistingLSPGeneralizedBandwidth;
import es.tid.pce.pcep.objects.BandwidthRequested;
import es.tid.pce.pcep.objects.BandwidthRequestedGeneralizedBandwidth;
import es.tid.pce.pcep.objects.BitmapLabelSet;
import es.tid.pce.pcep.objects.ExplicitRouteObject;
import es.tid.pce.pcep.objects.IncludeRouteObject;
import es.tid.pce.pcep.objects.InterLayer;
import es.tid.pce.pcep.objects.LSPA;
import es.tid.pce.pcep.objects.LabelSet;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.Metric;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.ObjectiveFunction;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.ReqAdapCap;
import es.tid.pce.pcep.objects.ServerIndication;
import es.tid.pce.pcep.objects.SuggestedLabel;
import es.tid.pce.pcep.objects.SwitchLayer;

/**
 * Path PCEP Construct. RFC 5440
 * From RFC 5440 Section 6.5
 * {@code 
 *      <path>::= <ERO><attribute-list>

   where:

    <attribute-list>::= [OF]
    					[<LSPA>]
                       [<BANDWIDTH>]
                       [<GENERALIZED-BANDWIDTH>] --ESTADO DRAFT
                       [<metric-list>]
                       [<IRO>]
  						[<INTER-LAYER>]
                            [<SWITCH-LAYER>]
                            [<REQ-ADAP-CAP>]
                            [<SERVER-INDICATION>]
                            
    <metric-list>::=<METRIC>[<metric-list>]}
 * @author ogondio
 *
 */
public class Path extends PCEPConstruct {

	private ExplicitRouteObject ero;
	private ObjectiveFunction of;
	private LSPA lspa;
	private Bandwidth bandwidth;
	private LinkedList<Metric> metricList;
	private IncludeRouteObject iro;
	private InterLayer interLayer;
	private SwitchLayer switchLayer;
	private ReqAdapCap reqAdapCap;
	private ServerIndication serverIndication;
	
	private LabelSet labelSet;
	
	private SuggestedLabel suggestedLabel;
	
	
	public Path(){
		metricList=new LinkedList<Metric>();
	}
	
	public Path(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		metricList=new LinkedList<Metric>();
		decode(bytes,offset);
	}
	
	public void encode() throws PCEPProtocolViolationException {

		//Encoding Request Rule
		int len=0;
		if (ero!=null){
			ero.encode();
			len=len+ero.getLength();
		}
	
		else {
			log.warn("Path must start with ERO object");
			throw new PCEPProtocolViolationException();
		}
		if (of!=null){
			of.encode();
			len=len+of.getLength();
		}
		if (lspa!=null){
			lspa.encode();
			len=len+lspa.getLength();
		}
		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
		}
		for (int i=0;i<metricList.size();++i){
			(metricList.get(i)).encode();
			len=len+(metricList.get(i)).getLength();
		}
		if (iro!=null){
			iro.encode();
			len=len+iro.getLength();
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
		if (labelSet!=null) {
			labelSet.encode();
			len=len+labelSet.getLength();
		}
		if (suggestedLabel!=null) {
			suggestedLabel.encode();
			len=len+suggestedLabel.getLength();
		}
		
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		
		if(ero!=null)
		{
			System.arraycopy(ero.getBytes(), 0, bytes, offset, ero.getLength());
			offset=offset+ero.getLength();
		}
		if (of!=null){
			System.arraycopy(of.getBytes(), 0, bytes, offset, of.getLength());
			offset=offset+of.getLength();
		}	
		if (lspa!=null){
			System.arraycopy(lspa.getBytes(), 0, bytes, offset, lspa.getLength());
			offset=offset+lspa.getLength();
		}
		if (bandwidth!=null){
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset=offset+bandwidth.getLength();
		}
		for (int i=0;i<metricList.size();++i){
			System.arraycopy(metricList.get(i).getBytes(), 0, bytes, offset, metricList.get(i).getLength());
			offset=offset+metricList.get(i).getLength();
		}
		if (iro!=null){
			System.arraycopy(iro.getBytes(), 0, bytes, offset, iro.getLength());
			offset=offset+iro.getLength();
		}
		
		if (interLayer!=null){
			System.arraycopy(interLayer.getBytes(), 0, bytes, offset, interLayer.getLength());
			offset=offset+interLayer.getLength();
		}
		if (switchLayer!=null){
			System.arraycopy(switchLayer.getBytes(), 0, bytes, offset, switchLayer.getLength());
			offset=offset+switchLayer.getLength();
		}
		if (reqAdapCap!=null){
			System.arraycopy(reqAdapCap.getBytes(), 0, bytes, offset, reqAdapCap.getLength());
			offset=offset+reqAdapCap.getLength();
		}
		if (serverIndication!=null){
			System.arraycopy(serverIndication.getBytes(), 0, bytes, offset, serverIndication.getLength());
			offset=offset+serverIndication.getLength();
		}
		if (labelSet!=null) {
			System.arraycopy(labelSet.getBytes(), 0, bytes, offset, labelSet.getLength());
			offset=offset+labelSet.getLength();
		}
		if (suggestedLabel!=null) {
			System.arraycopy(suggestedLabel.getBytes(), 0, bytes, offset, suggestedLabel.getLength());
			offset=offset+suggestedLabel.getLength();
		}
			
	}

	private void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		//Decoding Path Rule
		int len=0;		
		int oc=PCEPObject.getObjectClass(bytes, offset);
		int ot=PCEPObject.getObjectType(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_ERO){
			try {
				ero=new ExplicitRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				throw new PCEPProtocolViolationException();
			}
			offset=offset+ero.getLength();
			len=len+ero.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_OBJECTIVE_FUNCTION){
			try {
				of=new ObjectiveFunction(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed OF Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+of.getLength();
			len=len+of.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LSPA){
			try {
				lspa=new LSPA(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed LSPA Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+lspa.getLength();
			len=len+lspa.getLength();
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

		//Metric
		oc=PCEPObject.getObjectClass(bytes, offset);
		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_METRIC){
			Metric metric;
			try {
				metric = new Metric(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed METRIC Object found");
				throw new PCEPProtocolViolationException();
			}
			metricList.add(metric);
			offset=offset+metric.getLength();
			len=len+metric.getLength();
			oc=PCEPObject.getObjectClass(bytes, offset);
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_IRO){
			try {
				iro=new IncludeRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed IRO Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+iro.getLength();
			len=len+iro.getLength();
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_INTER_LAYER){
			try {
				interLayer=new InterLayer(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed interLayer Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+interLayer.getLength();
			len=len+interLayer.getLength();
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SWITCH_LAYER){
			try {
				switchLayer=new SwitchLayer(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed switchLayer Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+switchLayer.getLength();
			len=len+switchLayer.getLength();
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_REQ_ADAP_CAP){
			try {
				reqAdapCap=new ReqAdapCap(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed reqAdapCap Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+reqAdapCap.getLength();
			len=len+reqAdapCap.getLength();
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SERVER_INDICATION){
			try {
				serverIndication=new ServerIndication(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed serverIndication Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+serverIndication.getLength();
			len=len+serverIndication.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);	
		ot=PCEPObject.getObjectType(bytes, offset);
	
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LABEL_SET){
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_LABEL_SET_BITMAP){
				try {
					labelSet=new BitmapLabelSet(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed Suggested Label Object found");
					throw new PCEPProtocolViolationException();
				}
				offset=offset+labelSet.getLength();
				len=len+labelSet.getLength();
			}
			
		}
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SUGGESTED_LABEL){
			try {
				suggestedLabel=new SuggestedLabel(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed Suggested Label Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+suggestedLabel.getLength();
			len=len+suggestedLabel.getLength();
		}
		
		this.setLength(len);
	}
	
	
	public void setEro(ExplicitRouteObject eRO) {
		this.ero = eRO;
	}
	
	public void setIro(IncludeRouteObject iRO) {
		this.iro = iRO;
	}

	public void setLspa(LSPA lSPA) {
		this.lspa = lSPA;
	}
	
	public void setMetricList(LinkedList<Metric> metricList) {
		this.metricList = metricList;
	}
	
	
	public void setBandwidth(Bandwidth bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	public IncludeRouteObject getIro() {
		return iro;
	}

	public Bandwidth getBandwidth() {
		return bandwidth;
	}

	public LinkedList<Metric> getMetricList() {
		return metricList;
	}
	
	public LSPA getLspa() {
		return lspa;
	}

	public ExplicitRouteObject getEro() {
		return ero;
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

	public LabelSet getLabelSet() {
		return labelSet;
	}

	public void setLabelSet(LabelSet labelSet) {
		this.labelSet = labelSet;
	}

	public SuggestedLabel getSuggestedLabel() {
		return suggestedLabel;
	}

	public void setSuggestedLabel(SuggestedLabel suggestedLabel) {
		this.suggestedLabel = suggestedLabel;
	}
	
	

	
	public ObjectiveFunction getOf() {
		return of;
	}

	public void setOf(ObjectiveFunction of) {
		this.of = of;
	}

	public String toString(){
		String ret="PATH={ ";
		if (ero!=null){
			ret=ret+ero.toString();
		}
		if (of!=null){
			ret=ret+of.toString();
		}
		if (lspa!=null){
			ret=ret+lspa.toString();
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
		if (iro!=null){
			ret=ret+iro.toString();
		}
		if (labelSet!=null) {
			ret=ret+labelSet.toString();
		}
		if (suggestedLabel!=null) {
			ret=ret+suggestedLabel.toString();
		}
		ret=ret+" }";

		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((bandwidth == null) ? 0 : bandwidth.hashCode());
		result = prime * result + ((ero == null) ? 0 : ero.hashCode());
		result = prime * result + ((iro == null) ? 0 : iro.hashCode());
		result = prime * result
				+ ((interLayer == null) ? 0 : interLayer.hashCode());
		result = prime * result + ((lspa == null) ? 0 : lspa.hashCode());
		result = prime * result
				+ ((labelSet == null) ? 0 : labelSet.hashCode());
		result = prime * result
				+ ((metricList == null) ? 0 : metricList.hashCode());
		result = prime * result + ((of == null) ? 0 : of.hashCode());
		result = prime * result
				+ ((reqAdapCap == null) ? 0 : reqAdapCap.hashCode());
		result = prime
				* result
				+ ((serverIndication == null) ? 0 : serverIndication.hashCode());
		result = prime * result
				+ ((suggestedLabel == null) ? 0 : suggestedLabel.hashCode());
		result = prime * result
				+ ((switchLayer == null) ? 0 : switchLayer.hashCode());
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
		Path other = (Path) obj;
		if (bandwidth == null) {
			if (other.bandwidth != null)
				return false;
		} else if (!bandwidth.equals(other.bandwidth))
			return false;
		if (ero == null) {
			if (other.ero != null)
				return false;
		} else if (!ero.equals(other.ero))
			return false;
		if (iro == null) {
			if (other.iro != null)
				return false;
		} else if (!iro.equals(other.iro))
			return false;
		if (interLayer == null) {
			if (other.interLayer != null)
				return false;
		} else if (!interLayer.equals(other.interLayer))
			return false;
		if (lspa == null) {
			if (other.lspa != null)
				return false;
		} else if (!lspa.equals(other.lspa))
			return false;
		if (labelSet == null) {
			if (other.labelSet != null)
				return false;
		} else if (!labelSet.equals(other.labelSet))
			return false;
		if (metricList == null) {
			if (other.metricList != null)
				return false;
		} else if (!metricList.equals(other.metricList))
			return false;
		if (of == null) {
			if (other.of != null)
				return false;
		} else if (!of.equals(other.of))
			return false;
		if (reqAdapCap == null) {
			if (other.reqAdapCap != null)
				return false;
		} else if (!reqAdapCap.equals(other.reqAdapCap))
			return false;
		if (serverIndication == null) {
			if (other.serverIndication != null)
				return false;
		} else if (!serverIndication.equals(other.serverIndication))
			return false;
		if (suggestedLabel == null) {
			if (other.suggestedLabel != null)
				return false;
		} else if (!suggestedLabel.equals(other.suggestedLabel))
			return false;
		if (switchLayer == null) {
			if (other.switchLayer != null)
				return false;
		} else if (!switchLayer.equals(other.switchLayer))
			return false;
		return true;
	}
	
	
}
