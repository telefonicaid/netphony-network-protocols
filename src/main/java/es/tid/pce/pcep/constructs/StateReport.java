package es.tid.pce.pcep.constructs;

import java.util.LinkedList;
import java.util.Objects;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.Association;
import es.tid.pce.pcep.objects.AssociationIPv4;
import es.tid.pce.pcep.objects.AssociationIPv6;
import es.tid.pce.pcep.objects.LSP;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.SRP;

/**
 * State Report Construct.
 * 
 * {@code
 *  <state-report-list> ::= <state-report>[<state-report-list>]

   <state-report> ::= [<SRP>]
                      <LSP>
                      <path>
 Where:
   <path>::= <ERO><attribute-list>[<RRO>]

Where:
   <attribute-list> is defined in [RFC5440] and extended by PCEP extensions.
}
   The SRP object (see Section 7.2) is optional.  If the PCRpt message
   is not in response to a PCupd message, the SRP object MAY be omitted.
   When the PCC does not include the SRP object, the PCE treats this as
   an SRP object with an SRP-ID-number equal to the reserved value
   0x00000000.  The reserved value 0x00000000 indicates that the state
   reported is not as a result of processing a PCUpd message.

   If the PCRpt message is in response to a PCUpd message, the SRP
   object MUST be included and the value of the SRP-ID-number in the SRP
   Object MUST be the same as that sent in the PCUpd message that
   triggered the state that is reported.  If the PCC compressed several
   PCUpd messages for the same LSP by only processing the latest one,
   then it should use the SRP-ID-number of that request.  No state
   compression is allowed for state reporting, e.g.  PCRpt messages MUST
   NOT be pruned from the PCC's egress queue even if subsequent
   operations on the same LSP have been completed before the PCRpt
   message has been sent to the TCP stack.  The PCC MUST explicitly
   report state changes (including removal) for paths it manages.

   The LSP object (see Section 7.3) is mandatory, and it MUST be
   included in each LSP State Report on the PCRpt message.  If the LSP
   object is missing, the receiving PCE MUST send a PCErr message with
   Error-type=6 (Mandatory Object missing) and Error-value=[TBD] (LSP
   object missing).
 * 
 * @author ogondio
 *
 */

public class StateReport extends PCEPConstruct
{
	/**
	 * Optional SRP
	 */
	SRP srp;
	
	/**
	 * Compulsory LSP object
	 */
	LSP lsp;
	
	/**
	 * Compulsory path
	 */
	Path path;
	
	private LinkedList<Association> associationList;
	
	public StateReport(){
		
		super();
		associationList=new LinkedList<Association>();
	}
	
	public StateReport(byte []bytes, int offset)throws PCEPProtocolViolationException {
		associationList=new LinkedList<Association>();
		decode(bytes,offset);
		
	}

	public void encode() throws PCEPProtocolViolationException {
		//Encoding State Report
		int length=0;
		if (srp!=null){
			srp.encode();
			length=length+srp.getLength();
		}
		if (lsp!=null){
			lsp.encode();
			length=length+lsp.getLength();
		}else {
			log.warn("LSP Object compulsory");
			throw new PCEPProtocolViolationException();
		}
		if (associationList != null) {
			for (int i = 0; i < associationList.size(); ++i) {
				(associationList.get(i)).encode();
				length = length + (associationList.get(i)).getLength();
			}
		}
		if (path!=null){
			path.encode();
			length=length+path.getLength();
		}else {
			log.warn("PATH Construct compulsory");
			throw new PCEPProtocolViolationException();
		}
		
		
		this.setLength(length);
		this.bytes = new byte[length];
		int offset = 0;
		if (srp!=null){
			System.arraycopy(srp.getBytes(), 0, this.getBytes(), offset,srp.getLength());
			offset += srp.getLength();
		}
		if (lsp!=null){
			System.arraycopy(lsp.getBytes(), 0, this.getBytes(), offset,lsp.getLength());		
			offset += lsp.getLength();
		}
		for (int i = 0; i < associationList.size(); ++i) {
			System.arraycopy(associationList.get(i).getBytes(), 0, bytes, offset, associationList.get(i).getLength());
			offset = offset + associationList.get(i).getLength();
		}
		if (path!=null){
			System.arraycopy(path.getBytes(), 0, bytes, offset, path.getLength());
			offset = offset+path.getLength();
		}
		
	}
	
	public void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException {
		//Decoding State Report Construct
		int len=0;		
		int max_offset=bytes.length;
		if (offset>=max_offset)
		{			
			throw new PCEPProtocolViolationException();
		}
		
		int oc=PCEPObject.getObjectClass(bytes, offset);
		int ot;
		log.debug("Voy  ver el SRP oc "+oc+" offset "+offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SRP)
		{
				try {
					srp = new SRP(bytes,offset);
					offset=offset+srp.getLength();
					len=len+srp.getLength();
				} catch (MalformedPCEPObjectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		log.debug("Voy a ver el LSP, oc "+oc+" offset "+offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LSP)
		{
			try 
			{
				lsp = new LSP(bytes,offset);
				offset=offset+lsp.getLength();
				len=len+lsp.getLength();
				if (offset>=bytes.length){
					this.setLength(len);
					return;
				}
				
			} 
			catch (MalformedPCEPObjectException e) 
			{
				log.warn("Malformed LSP Object found");
				throw new PCEPProtocolViolationException();
			}
			
		}
		else
		{
			log.warn("Malformed Report Message. There must be at least one LSP object. Exception will be throwed");
			throw new PCEPProtocolViolationException();
		}
		
		//Association
			
				oc = PCEPObject.getObjectClass(bytes, offset);
				ot = PCEPObject.getObjectType(bytes, offset);
				log.debug("Voy a ver el ASSOCIATION, oc "+oc+" offset "+offset +"OT: "+ ot);
				while (oc == ObjectParameters.PCEP_OBJECT_CLASS_ASSOCIATION) {
					Association aso=null;
					try {
						if(ot == ObjectParameters.PCEP_OBJECT_TYPE__ASSOCIATION_IPV4) {
							aso = new AssociationIPv4(bytes, offset);
						}else if(ot == ObjectParameters.PCEP_OBJECT_TYPE__ASSOCIATION_IPV6) {
							aso = new AssociationIPv6(bytes, offset);
						}
						
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed ASSOCIATION Object found");
						throw new PCEPProtocolViolationException();
					}
					associationList.add(aso);
					offset = offset + aso.getLength();
					len = len + aso.getLength();
					if (offset >= bytes.length) {
						this.setLength(len);
						return;
					}
					
					oc = PCEPObject.getObjectClass(bytes, offset);
					ot = PCEPObject.getObjectType(bytes, offset);
				}
				log.debug("Voy a ver el ERO, oc "+oc+" offset "+offset +"OT: "+ ot);
		if (PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_ERO)
		{
			path=new Path(bytes,offset);
			offset=offset+path.getLength();
			len=len+path.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
		}
		else
		{
			log.warn("Malformed Report Message. There must be at least one ERO or SRERO message!");
			//throw new PCEPProtocolViolationException();
		}
		if (PCEPObject.getObjectClass(bytes, offset)==34) {
			offset+=PCEPObject.getObjectLength(bytes, offset);
			len +=PCEPObject.getObjectLength(bytes, offset);
			if (offset>=bytes.length){
				this.setLength(len);
				return;
				}
		}
		
		
		this.setLength(len);
	}

	public SRP getSrp() {
		return srp;
	}

	public void setSrp(SRP srp) {
		this.srp = srp;
	}
	
	public LSP getLsp() {
		return lsp;
	}

	public void setLsp(LSP lsp) {
		this.lsp = lsp;
	}
	
	
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public LinkedList<Association> getAssociationList() {
		return associationList;
	}

	public void setAssociationList(LinkedList<Association> associationList) {
		this.associationList = associationList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(associationList, lsp, path, srp);
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
		StateReport other = (StateReport) obj;
		return Objects.equals(associationList, other.associationList) && Objects.equals(lsp, other.lsp)
				&& Objects.equals(path, other.path) && Objects.equals(srp, other.srp);
	}

	@Override
	public String toString() {
		return "StateReport [srp=" + srp + ", lsp=" + lsp + ", path=" + path + ", associationList=" + associationList
				+ "]";
	}
	
	

	
	
}