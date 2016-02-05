package es.tid.pce.pcep.messages;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.StateReport;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 *  Path Computation State Report (PCRpt) Message.
 *  
 * A Path Computation LSP State Report message (also referred to as
   PCRpt message) is a PCEP message sent by a PCC to a PCE to report the
   current state of an LSP.  A PCRpt message can carry more than one LSP
   State Reports.  A PCC can send an LSP State Report either in response
   to an LSP Update Request from a PCE, or asynchronously when the state
   of an LSP changes.  The Message-Type field of the PCEP common header
   for the PCRpt message is set to [TBD].

   The format of the PCRpt message is as follows:
   
  <PCRpt Message> ::= <Common Header>
                       <state-report-list>
Where:

   <state-report-list> ::= <state-report>[<state-report-list>]

   <state-report> ::= [<SRP>]
                      <LSP>
                      <path>
 Where:
   <path>::= <ERO><attribute-list>[<RRO>]

Where:
   <attribute-list> is defined in [RFC5440] and extended by PCEP extensions.

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

   If the LSP transitioned to non-operational state, the PCC SHOULD
   include the LSP-ERROR-TLV (Section 7.3.3) with the relevant LSP Error
   Code to report the error to the PCE.

   The RRO SHOULD be included by the PCC when the path is up, but MAY be
   omitted if the path is down due to a signaling error or another
   failure.
   
   A PCE may choose to implement a limit on the resources a single PCC
   can occupy.  If a PCRpt is received that causes the PCE to exceed
   this limit, it MUST send a PCErr message with error-type 19 (invalid
   operation) and error-value 4 (indicating resource limit exceeded) in
   response to the PCRpt message triggering this condition and MAY
   terminate the session.


 */

public class PCEPReport extends PCEPMessage
{
	private static final Logger log = LoggerFactory.getLogger("PCEPParser");
	protected LinkedList<StateReport> stateReportList;
	
	public PCEPReport()
	{
		this.setMessageType(PCEPMessageTypes.MESSAGE_REPORT);
		stateReportList = new LinkedList<StateReport>();
	}
	
	public PCEPReport(byte [] bytes)  throws PCEPProtocolViolationException
	{
		super(bytes);
		stateReportList = new LinkedList<StateReport>();
		decode();
		
	}
	
	public void encode() throws PCEPProtocolViolationException 
	{
		int len = 4;
		int index = 0;
		
        while(index < stateReportList.size())
        {
        	stateReportList.get(index).encode();		
			len+=stateReportList.get(index).getLength();
			index++;			
        }
        
		if (stateReportList.size()==0)
		{
			log.warn("There should be at least one state Report");
			throw new PCEPProtocolViolationException();
		}
		
		this.setMessageLength(len);
		messageBytes=new byte[len];
		this.encodeHeader();
		int offset = 4;		//Header
		index=0;
		
		while(index < stateReportList.size())
		{
			System.arraycopy(stateReportList.get(index).getBytes(), 0, this.messageBytes, offset, stateReportList.get(index).getLength());
			offset = offset + stateReportList.get(index).getLength();
			index++;						
		}	
	}
	
	
	public void decode() throws PCEPProtocolViolationException
	{
		//Current implementation is strict, does not accept unknown objects 
		int offset=4;//We start after the object header
		StateReport sr;
		//Decoding PCEP Report
		
		int oc=PCEPObject.getObjectClass(this.getBytes(), offset); // Get the object class
		if ((oc!=ObjectParameters.PCEP_OBJECT_CLASS_SRP)&&(oc!=ObjectParameters.PCEP_OBJECT_CLASS_LSP))
		{
			log.warn("At least one state-report is needed");
			throw new PCEPProtocolViolationException();
		}
		
		while ((oc==ObjectParameters.PCEP_OBJECT_CLASS_SRP)||(oc==ObjectParameters.PCEP_OBJECT_CLASS_LSP))
		{
			try
			{
				sr = new StateReport(this.getBytes(),offset);
				offset=offset+sr.getLength();
				stateReportList.add(sr);
				if (offset>=this.messageBytes.length){
					return;
				}
			}
			catch(PCEPProtocolViolationException e)
			{
				log.warn("Malformed PCEP Report");
				throw new PCEPProtocolViolationException();
			}
		
		}
		
	}
	
	public LinkedList<StateReport> getStateReportList() 
	{
		return stateReportList;
	}

	public void setStateReportList(LinkedList<StateReport> updateRequestList) 
	{
		this.stateReportList = updateRequestList;
	}
	
	public void addStateReport(StateReport stReport)
	{
		stateReportList.add(stReport);
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer(stateReportList.size()*100);
		sb.append("PCRpt: ");
		for (int i=0;i<stateReportList.size();++i){
			sb.append(stateReportList.get(i).toString());
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((stateReportList == null) ? 0 : stateReportList.hashCode());
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
		PCEPReport other = (PCEPReport) obj;
		if (stateReportList == null) {
			if (other.stateReportList != null)
				return false;
		} else if (!stateReportList.equals(other.stateReportList))
			return false;
		return true;
	}
	
	
}
