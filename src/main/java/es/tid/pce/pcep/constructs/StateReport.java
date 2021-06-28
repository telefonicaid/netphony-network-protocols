package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;
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
	
	public StateReport(){
		super();
		
	}
	
	public StateReport(byte []bytes, int offset)throws PCEPProtocolViolationException {
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
		else if (PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_SR_ERO)
		{
			//SRERO Object found, New Path Construct found
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
	
	public String toString(){
		StringBuffer sb=new StringBuffer();
		if (srp!=null){
			sb.append(srp.toString());
		}
		if (lsp!=null){
			sb.append(lsp.toString());
		}
		if (path!=null){
			sb.append(path.toString());
		}	
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((lsp == null) ? 0 : lsp.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((srp == null) ? 0 : srp.hashCode());
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
		if (lsp == null) {
			if (other.lsp != null)
				return false;
		} else if (!lsp.equals(other.lsp))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (srp == null) {
			if (other.srp != null)
				return false;
		} else if (!srp.equals(other.srp))
			return false;
		return true;
	}
	
	
}