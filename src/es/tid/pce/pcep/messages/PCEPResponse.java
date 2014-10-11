package es.tid.pce.pcep.messages;

import java.util.LinkedList;
import java.util.logging.Logger;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.Response;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;

/**
 * PCEP Response Message (RFC 5440).
 * 
 *6.5. Path Computation Reply (PCRep) Message


   The PCEP Path Computation Reply message (also referred to as a PCRep
   message) is a PCEP message sent by a PCE to a requesting PCC in
   response to a previously received PCReq message.  The Message-Type
   field of the PCEP common header for the PCRep message is set to 4.

   The bundling of multiple replies to a set of path computation
   requests within a single PCRep message is supported by PCEP.  If a
   PCE receives non-synchronized path computation requests by means of
   one or more PCReq messages from a requesting PCC, it MAY decide to
   bundle the computed paths within a single PCRep message so as to
   reduce the control plane load.  Note that the counter side of such an
   approach is the introduction of additional delays for some path
   computation requests of the set.  Conversely, a PCE that receives
   multiple requests within the same PCReq message MAY decide to provide
   each computed path in separate PCRep messages or within the same
   PCRep message.  A PCRep message may contain positive and negative
   replies.

   A PCRep message may contain a set of computed paths corresponding to
   either a single path computation request with load-balancing (see
   Section 7.16) or multiple path computation requests originated by a
   requesting PCC.  The PCRep message may also contain multiple
   acceptable paths corresponding to the same request.

   The PCRep message MUST contain at least one RP object.  For each
   reply that is bundled into a single PCReq message, an RP object MUST
   be included that contains a Request-ID-number identical to the one
   specified in the RP object carried in the corresponding PCReq message
   (see Section 7.4 for the definition of the RP object).

   If the path computation request can be satisfied (i.e., the PCE finds
   a set of paths that satisfy the set of constraints), the set of
   computed paths specified by means of Explicit Route Objects (EROs) is
   inserted in the PCRep message.  The ERO is defined in Section 7.9.
   The situation where multiple computed paths are provided in a PCRep
   message is discussed in detail in Section 7.13.  Furthermore, when a
   PCC requests the computation of a set of paths for a total amount of
   bandwidth by means of a LOAD-BALANCING object carried within a PCReq
   message, the ERO of each computed path may be followed by a BANDWIDTH
   object as discussed in section Section 7.16.

   If the path computation request cannot be satisfied, the PCRep
   message MUST include a NO-PATH object.  The NO-PATH object (described
   in Section 7.5) may also contain other information (e.g, reasons for
   the path computation failure).

   The format of a PCRep message is as follows:


   <PCRep Message> ::= <Common Header>
                       <response-list>

   where:

      <response-list>::=<response>[<response-list>]

      <response>::=<RP>
                  [<NO-PATH>]
                  [<attribute-list>]
                  [<path-list>]

      <path-list>::=<path>[<path-list>]

      <path>::= <ERO><attribute-list>

   where:

    <attribute-list>::=[<LSPA>]
                       [<BANDWIDTH>]
                       [<GENERALIZED-BANDWIDTH>] --ESTADO DRAFT
                       [<metric-list>]
                       [<IRO>]

    <metric-list>::=<METRIC>[<metric-list>]

 * 
 * 
* @author Oscar Gonzalez de Dios
**/

public class PCEPResponse extends PCEPMessage {

	public LinkedList<Response> ResponseList;
	private Logger log=Logger.getLogger("PCEPParser");

	/**
	 * Construct new PCEP Request from scratch
	 */
	public PCEPResponse(){
		super();
		ResponseList=new LinkedList<Response>();
		this.setMessageType(PCEPMessageTypes.MESSAGE_PCREP);
	}

	public PCEPResponse(byte [] bytes)  throws PCEPProtocolViolationException
	{
		super(bytes);
		ResponseList = new LinkedList<Response>();
		decode();

	}

	public void addResponse(Response response){		
		this.ResponseList.add(response);
	}
	public Response getResponse(int index){
		return this.ResponseList.get(index);
	}

	public LinkedList<Response> getResponseList() {
		return ResponseList;
	}

	public void setResponsetList(LinkedList<Response> responseList) {
		ResponseList = responseList;
	}

	/**
	 * Encodes the PCEP Response Message
	 */
	public void encode() throws PCEPProtocolViolationException {
		log.finest("Encoding PCEP Response Message");
		if (ResponseList.size()==0){
			log.warning("There should be at least one request in a PCEP Response message");
			throw new PCEPProtocolViolationException();
		}
		int len=4;
		log.finest("Ending "+ResponseList.size()+" different responses");
		for (int i=0;i<ResponseList.size();++i){
			ResponseList.get(i).encode();
			len=len+ResponseList.get(i).getLength();
		}
		log.finest("Total length of the Response Message"+len);
		this.setMessageLength(len);
		messageBytes=new byte[len];
		encodeHeader();
		int offset=4;
		for (int i=0;i<ResponseList.size();++i){
			System.arraycopy(ResponseList.get(i).getBytes(), 0, messageBytes, offset, ResponseList.get(i).getLength());
			offset=offset+ResponseList.get(i).getLength();
		}
	}

	/**
	 * Decode the PCEP Response message
	 */
	public void decode() throws PCEPProtocolViolationException {
		log.finest("Decoding PCEP Response Message");
		byte[] bytes=this.getBytes();
		int offset=4;//We start after the object header
		while (PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_RP){
			Response res=new Response();
			res.decode(bytes, offset);
			ResponseList.add(res);
			offset=offset+res.getLength();
			if (offset>=bytes.length){
				return;
			}
		}

		if (ResponseList.size()==0){
			log.warning("No Responses in the PCEP Response message");
			throw new PCEPProtocolViolationException();
		}
	}

	public String toString(){
		StringBuffer sb=new StringBuffer(ResponseList.size()*100);
		sb.append("RESP: ");
		for (int i=0;i<ResponseList.size();++i){
			sb.append(ResponseList.get(i).toString());
		}
		return sb.toString();
	}
}