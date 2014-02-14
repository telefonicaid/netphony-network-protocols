package tid.pce.pcep.messages;

import java.util.LinkedList;
import java.util.logging.Logger;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.constructs.Request;
import tid.pce.pcep.constructs.SVECConstruct;
import tid.pce.pcep.constructs.StateReport;
import tid.pce.pcep.constructs.UpdateRequest;
import tid.pce.pcep.objects.MalformedPCEPObjectException;
import tid.pce.pcep.objects.Monitoring;
import tid.pce.pcep.objects.ObjectParameters;
import tid.pce.pcep.objects.PCEPObject;
import tid.pce.pcep.objects.PccReqId;

/**
 * 
 * http://tools.ietf.org/html/draft-crabbe-pce-stateful-pce-mpls-te-00
 * 
 * 4.2. MPLS-TE specific descriptors for the PCUpd Message


   A Path Computation LSP Update Request message (also referred to as
   PCUpd message) is a PCEP message sent by a PCE to a PCC to update
   attributes of an LSP.  A PCUpd message can carry more than one LSP
   Update Request.  The Message-Type field of the PCEP common header for
   the PCUpd message is set to [TBD].

   The format of the PCUpd message is defined in
   [I-D.ietf-pce-stateful-pce] and included here for easy reference:


      <PCUpd Message> ::= <Common Header>
                          <udpate-request-list>
   Where:

      <update-request-list> ::= <update-request>[<update-request-list>]

      <update-request> ::= <LSP>
                           [<path-list>]

   Where:

      <path-list>::=<path>[<path-list>]

   For MPLS-TE LSPs, the endoding of path descriptor is defined as
   follows:

      <path>::=<ERO><attribute-list>

   Where:
      <path>::=<ERO><attribute-list>

   Where:

      <attribute-list> ::= [<LSPA>]
                           [<BANDWIDTH>]
                           [<metric-list>]

      <metric-list> ::= <METRIC>[<metric-list>]

   There is one mandatory object that MUST be included within each LSP
   Update Request in the PCUpd message: the LSP object (see
   [I-D.ietf-pce-stateful-pce]).  If the LSP object is missing, the
   receiving PCE MUST send a PCErr message with Error-type=6 (Mandatory
   Object missing) and Error-value=[TBD] (LSP object missing).

   The LSP Update Request MUST contain a path descriptor for the primary
   path, and MAY contain one or more path descriptors for backup paths.
   A path descriptor MUST contain an ERO object.  A path descriptor MAY
   further contain the BANDWIDTH, IRO, and METRIC objects.  The ERO,
   LSPA, BANDWIDTH, METRIC, and IRO objects are defined in [RFC5440].

   Each LSP Update Request results in a separate LSP setup operation at
   a PCC.  An LSP Update Request MUST contain all LSP parameters that a
   PCC wishes to set for the LSP.  A PCC MAY set missing parameters from
   locally configured defaults.  If the LSP specified the Update Request
   is already up, it will be re-signaled.  The PCC will use make-before-
   break whenever possible in the re-signaling operation.

   A PCC MUST respond with an LSP State Report to each LSP Update
   Request to indicate the resulting state of the LSP in the network.  A
   PCC MAY respond with multiple LSP State Reports to report LSP setup
   progress of a single LSP.

   If the rate of PCUpd messages sent to a PCC for the same target LSP
   exceeds the rate at which the PCC can signal LSPs into the network,
   the PCC MAY perform state compression and only re-signal the last
   modification in its queue.

   Note that a PCC MUST process all LSP Update Requests - for example,
   an LSP Update Request is sent when a PCE returns delegation or puts
   an LSP into non-operational state.  The protocol relies on TCP for
   message-level flow control.

   Note also that it's up to the PCE to handle inter-LSP dependencies;
   for example, if ordering of LSP set-ups is required, the PCE has to
   wait for an LSP State Report for a previous LSP before triggering the
   LSP setup of a next LSP.
   
 * @author Fernando Mu�oz del Nuevo
 * 
 *
 */


public class PCEPUpdate extends PCEPMessage{
	private Logger log = Logger.getLogger("PCEPParser");
	protected LinkedList<UpdateRequest> updateRequestList;
	
	public PCEPUpdate(){
		this.setMessageType(PCEPMessageTypes.MESSAGE_UPDATE);
		updateRequestList = new LinkedList<UpdateRequest>();
	}
	public PCEPUpdate(byte [] bytes)  throws PCEPProtocolViolationException{
		super(bytes);
		updateRequestList = new LinkedList<UpdateRequest>();
		decode();
		
	}
	
	@Override
	public void encode() throws PCEPProtocolViolationException {
		// TODO Auto-generated method stub
		log.info("Empezando enconde");
		int len = 4;
		int index = 0;
        while(index < updateRequestList.size()){
			updateRequestList.get(index).encode();		
			len+=updateRequestList.get(index).getLength();
			index++;			
        }
        log.info("CCCC");
		if (updateRequestList.size()==0){
			log.warning("There should be at least one update request in a PCEP update Request message");
			throw new PCEPProtocolViolationException();
		}
		log.info("DDDD "+len);
		this.setMessageLength(len);
		messageBytes=new byte[len];
		log.info("Vamos a por el encodeHeaer");
		this.encodeHeader();
		int offset = 4;		//Header
		index=0;
		log.info("a por array copy");
		while(index < updateRequestList.size()){
			System.arraycopy(updateRequestList.get(index).getBytes(), 0, this.messageBytes, offset, updateRequestList.get(index).getLength());
			offset = offset + updateRequestList.get(index).getLength();
			index++;						
		}	
	}
	
	
	public void decode() throws PCEPProtocolViolationException{
		//Current implementation is strict, does not accept unknown objects 
		int offset=4;//We start after the object header
		boolean atLeastOne = false;
		
		UpdateRequest ur;
		
		//No LSP object. Malformed Update Request. PCERR mesage should be sent!
		if(PCEPObject.getObjectClass(this.getBytes(), offset)!=ObjectParameters.PCEP_OBJECT_CLASS_RSP)
		{
			throw new PCEPProtocolViolationException();
		}
		
		while (PCEPObject.getObjectClass(this.getBytes(), offset)==ObjectParameters.PCEP_OBJECT_CLASS_RSP){
			try
			{
				ur = new UpdateRequest(this.getBytes(),offset);
			}
			catch(PCEPProtocolViolationException e)
			{
				log.warning("Malformed UpdateRequest Construct");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+ur.getLength();
			updateRequestList.add(ur);
			atLeastOne = true;
		}
		
		if (!atLeastOne)
		{
			log.warning("Malformed Report Message. There must be at least one update-list object. Exception will be throwed");
			throw new PCEPProtocolViolationException();
		}
	}

	public LinkedList<UpdateRequest> getUpdateRequestList() {
		return updateRequestList;
	}

	public void setUpdateRequestList(LinkedList<UpdateRequest> updateRequestList) {
		this.updateRequestList = updateRequestList;
	}
	
	public void addStateReport(UpdateRequest upRequest)
	{
		updateRequestList.add(upRequest);
	}
}
