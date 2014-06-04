package tid.pce.pcep.messages;

import java.util.LinkedList;
import java.util.logging.Logger;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.constructs.StateReport;
import tid.pce.pcep.objects.ObjectParameters;
import tid.pce.pcep.objects.PCEPObject;

/*
 *  A Path Computation LSP State Report message (also referred to as
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

   <state-report> ::= <LSP>
                      [<path-list>]

Where:

   <path-list>::=<path>[<path-list>]

Where:
     <path-list> is defined in [RFC5440] and extended by PCEP extensions.

   The LSP object (see Section 7.2) is mandatory, and it MUST be
   included in each LSP State Report on the PCRpt message.  If the LSP
   object is missing, the receiving PCE MUST send a PCErr message with
   Error-type=6 (Mandatory Object missing) and Error-value=[TBD] (LSP
   object missing).

Crabbe, et al.          Expires November 9, 2013               [Page 32]
Internet-Draft      PCEP Extensions for Stateful PCE            May 2013

   The path descriptor is described in separate technology-specific
   documents according to the LSP type.

 */

public class PCEPReport extends PCEPMessage
{
	private Logger log = Logger.getLogger("PCEPParser");
	protected LinkedList<StateReport> stateReportList;
	
	public PCEPReport()
	{
		this.setMessageType(PCEPMessageTypes.MESSAGE_REPORT);
		stateReportList = new LinkedList<StateReport>();
	}
	public PCEPReport(byte [] bytes)  throws PCEPProtocolViolationException
	{
		super(bytes);
		System.out.println("PCEPReport finishing");
		System.out.println("Decoding PCEP Report 2");
		stateReportList = new LinkedList<StateReport>();
		System.out.println("PCEPReport finishing 2");
		decode();
		
	}
	
	@Override
	public void encode() throws PCEPProtocolViolationException 
	{
		System.out.println("Empezando enconde");
		int len = 4;
		int index = 0;
		
        while(index < stateReportList.size())
        {
        	stateReportList.get(index).encode();		
			len+=stateReportList.get(index).getLength();
			index++;			
        }
        
        System.out.println("CCCC");
		if (stateReportList.size()==0)
		{
			log.warning("There should be at least one update request in a PCEP update Request message");
			//throw new PCEPProtocolViolationException();
		}
		
		System.out.println("DDDD "+len);
		this.setMessageLength(len);
		messageBytes=new byte[len];
		System.out.println("Vamos a por el encodeHeaer");
		this.encodeHeader();
		int offset = 4;		//Header
		index=0;
		System.out.println("a por array copy");
		
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
		boolean atLeastOne = false;
		StateReport sr;
		System.out.println("PCEPReport finishing 2");
		System.out.println("Decoding PCEP Report!!");
		
		//No LSP object. Malformed Update Request. PCERR mesage should be sent!
		System.out.println("Object Type::"+PCEPObject.getObjectClass(this.getBytes(), offset));
		if(PCEPObject.getObjectClass(this.getBytes(), offset)!=ObjectParameters.PCEP_OBJECT_CLASS_RSP)
		{
			System.out.println("There should be at least one RSP Object");
			//throw new PCEPProtocolViolationException();
		}
		//It has to be at least one!
		while (PCEPObject.getObjectClass(this.getBytes(), offset)==ObjectParameters.PCEP_OBJECT_CLASS_RSP)
		{
			sr = new StateReport(this.getBytes(),offset);
			try
			{
				sr = new StateReport(this.getBytes(),offset);
			}
			catch(PCEPProtocolViolationException e)
			{
				log.warning("Malformed UpdateRequest Construct");
				//throw new PCEPProtocolViolationException();
			}
			offset=offset+sr.getLength();
			stateReportList.add(sr);
			atLeastOne = true;
		}
		
		if (!atLeastOne)
		{
			//log.warning("Malformed Report Message. There must be at least one state-report object. Exception will be throwed");
			//throw new PCEPProtocolViolationException();
		}
		System.out.println("PCEP Report decoded!!");
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
}
