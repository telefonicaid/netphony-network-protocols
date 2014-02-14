package tid.pce.pcep.constructs;

import java.util.LinkedList;
import java.util.logging.Logger;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.objects.MalformedPCEPObjectException;
import tid.pce.pcep.objects.Metric;
import tid.pce.pcep.objects.ObjectParameters;
import tid.pce.pcep.objects.ObjectiveFunction;
import tid.pce.pcep.objects.PCEPObject;
import tid.pce.pcep.objects.Svec;

/**
 * From RFC 5440 y 5541
 * <svec-list> ::= <SVEC>
                         [<OF>]
                         [<metric-list>]
                         [<svec-list>]

 * @author ogondio
 *
 */
public class SVECConstruct extends PCEPConstruct{
	
	private Svec svec;
	private ObjectiveFunction objectiveFunction;
	private LinkedList<Metric> metricList;
	private Logger log=Logger.getLogger("PCEPParser");
	
	public SVECConstruct(){
		metricList=new LinkedList<Metric>(); 
	}

	public  SVECConstruct(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		decode(bytes, offset);
		metricList=new LinkedList<Metric>();
	}
	
	@Override
	public void encode() throws PCEPProtocolViolationException {
		log.finest("Encoding SVEC Construct");		
		int len=0;		
		if (svec!=null){
			svec.encode();
			len=len+svec.getLength();
		}
		else {
			log.warning("svec  not found!  compulsory");
			throw new PCEPProtocolViolationException();
		}
		if (objectiveFunction!=null){
			objectiveFunction.encode();
			len=len+objectiveFunction.getLength();
		}
		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				(metricList.get(i)).encode();
				len=len+(metricList.get(i)).getLength();
			}
		}
		
		log.info("SVEC Construct Length = "+len);
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		System.arraycopy(svec.getBytes(), 0, bytes, offset, svec.getLength());
		offset=offset+svec.getLength();
		if (objectiveFunction!=null){
			System.arraycopy(objectiveFunction.getBytes(), 0, bytes, offset, objectiveFunction.getLength());
			offset=offset+objectiveFunction.getLength();
		}
		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				System.arraycopy(metricList.get(i).getBytes(), 0, bytes, offset, metricList.get(i).getLength());
				offset=offset+metricList.get(i).getLength();
			}
		}
		
	}

	
	public void decode(byte[] bytes, int offset)
			throws PCEPProtocolViolationException {
		log.finest("Decoding SVEC Construct ");
		int len=0;
		int oc=PCEPObject.getObjectClass(bytes, offset);
		//EMPEZAMOS HACIENDO IMPLEMENTACION ESTRICTA
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SVEC){
			try {
				svec=new Svec(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed RP Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+svec.getLength();
			len=len+svec.getLength();
		}
		else {
			log.warning("SVEC Construct must start with SVEC object");
			throw new PCEPProtocolViolationException();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_OBJECTIVE_FUNCTION){
			log.finest("OBJECTIVE FUNCTION Object found");
			try {
				objectiveFunction=new ObjectiveFunction(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed OBJECTIVE FUNCTION Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+objectiveFunction.getLength();
			len=len+objectiveFunction.getLength();
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
		this.setLength(len);

	}

	public Svec getSvec() {
		return svec;
	}

	public void setSvec(Svec svec) {
		this.svec = svec;
	}

	public ObjectiveFunction getObjectiveFunction() {
		return objectiveFunction;
	}

	public void setObjectiveFunction(ObjectiveFunction objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}

	public LinkedList<Metric> getMetricList() {
		return metricList;
	}

	public void setMetricList(LinkedList<Metric> metricList) {
		this.metricList = metricList;
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer();
		if (svec!=null){
			sb.append(""+svec.toString());
		}
		return sb.toString();
	}
	
}
