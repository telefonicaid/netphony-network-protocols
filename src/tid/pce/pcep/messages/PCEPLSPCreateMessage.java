package tid.pce.pcep.messages;

import java.util.LinkedList;
import java.util.logging.Logger;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.constructs.LSPInstantationRequest;
import tid.pce.pcep.constructs.Request;
import tid.pce.pcep.constructs.SVECConstruct;
import tid.pce.pcep.objects.*;


/**
 *
 * 
   
   A Path Computation LSP Create message (also referred to as PCCreate
   message) is a PCEP message sent by a PCE to a PCC to trigger an LSP
   instantiation.  The Message-Type field of the PCEP common header for
   the PCCreate message is set to [TBD].

   The PCCreate message MUST include the END-POINTS and the LSPA
   objects.  In the LSPA object, it MUST include the SYMBOLIC-PATH-NAME
   TLV for the LSP.  The PCCreate message MAY include other attributes
   for the LSP.  If specified, the PCC MUST use them for the LSP
   instantiation, otherwise it MUST use its locally configured values.
   The error messages will be specified in a future version of this
   document.
   
 *  The format of a PCCreate message is as follows:


   <PCCreate Message> ::= <Common Header>
                       <lsp-instantiation-list>
Where:

   <lsp-instantiation-list> ::= <lsp-instantiation-request>[<lsp-instantiation-list>]

   <lsp-instantiation-request> ::= <END-POINTS>
                                   <LSPA>
                                   [<ERO>]
                                   [<BANDWIDTH>]
                                   [<metric-list>]


Where:

   <metric-list>::=<METRIC>[<metric-list>]
 
 *
 * @author Oscar Gonzalez de Dios
 * @version 0.1
 * 
**/
public class PCEPLSPCreateMessage extends PCEPMessage {
	
	/**
	 * List of LSPInstantationRequest. At least one LSPInstantationRequest is compulsory!!
	 */
	private LinkedList<LSPInstantationRequest> LSPInstantationRequestList;
	
	
	/**
	 * Logger
	 */
	private Logger log=Logger.getLogger("PCEPParser");
	
	/**
	 * Construct new PCEP Request from scratch
	 */
	public PCEPLSPCreateMessage(){		
		LSPInstantationRequestList=new LinkedList<LSPInstantationRequest>();
		this.setMessageType(PCEPMessageTypes.MESSAGE_CREATEREQ);
	}
	
	/**
	 * Encodes the PCEP Request message
	 */
	public void encode() throws PCEPProtocolViolationException {
		log.finest("Encoding PCEP Request Message");
		if (LSPInstantationRequestList.size()==0){
			log.warning("There should be at least one request in a PCEP Request message");
			throw new PCEPProtocolViolationException();
		}
		int len=4;
		for (int i=0;i<LSPInstantationRequestList.size();++i){
			LSPInstantationRequestList.get(i).encode();
			len=len+LSPInstantationRequestList.get(i).getLength();
		}
		this.setMessageLength(len);		
		messageBytes=new byte[len];
		encodeHeader();
		int offset=4;
		
		for (int i=0;i<LSPInstantationRequestList.size();++i){
			System.arraycopy(LSPInstantationRequestList.get(i).getBytes(), 0, messageBytes, offset, LSPInstantationRequestList.get(i).getLength());
			offset=offset+LSPInstantationRequestList.get(i).getLength();		
		}
	}
	
	/**
	 * Decodes a PCEP Request following RFC 5440, RFC 5541, RFC 5886 and RFC 5521 
	 */
	public void decode(byte[] bytes) throws PCEPProtocolViolationException{
		//Current implementation is strict, does not accept unknown objects 
		log.finest("Decoding PCEP LSP CREATE MESSAGE");
		log.finest("Length in bytes: "+bytes.length);
		this.messageBytes=new byte[bytes.length];
		System.arraycopy(bytes, 0, this.messageBytes, 0, bytes.length);
		int offset=4;//We start after the object header
		int oc = PCEPObject.getObjectClass(bytes, offset);
		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_RP){
			LSPInstantationRequest lspInstantationRequest= new LSPInstantationRequest(bytes, offset);
			LSPInstantationRequestList.add(lspInstantationRequest);
			offset=offset+lspInstantationRequest.getLength();
			if (offset>=bytes.length){
				return;
			}
		}
		if (LSPInstantationRequestList.size()==0){
			throw new PCEPProtocolViolationException();
		}
	}
	
	
	public void addRequest(LSPInstantationRequest lspInstantationRequest){		
		this.LSPInstantationRequestList.add(lspInstantationRequest);		
	}
	
	public LSPInstantationRequest getRequest(int index){
		return this.LSPInstantationRequestList.get(index);	
	}

	public LinkedList<LSPInstantationRequest> getLSPInstantationRequestList() {
		return LSPInstantationRequestList;
	}

	public void setLSPInstantationRequestList(LinkedList<LSPInstantationRequest> lspInstantationRequestList) {
		LSPInstantationRequestList = lspInstantationRequestList;
	}

	public String toString(){
		StringBuffer sb=new StringBuffer(LSPInstantationRequestList.size()*100);
		sb.append("LSP Create MESSAGE: ");
		for (int i=0;i<LSPInstantationRequestList.size();++i){
			sb.append(LSPInstantationRequestList.get(i).toString());
		}
		return sb.toString();
	}

}