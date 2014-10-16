package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;


/**
 * http://tools.ietf.org/html/draft-crabbe-pce-stateful-pce-mpls-te-00
 * 
 * <update-request-list> ::= <update-request>[<update-request-list>]

      <update-request> ::= <LSP>
                           [<path-list>]
 * Where:

      <path-list>::=<path>[<path-list>]

   For MPLS-TE LSPs, the encoding of path descriptor is defined as
   follows:

      <path>::=<ERO><attribute-list>

   Where:
      <path>::=<ERO><attribute-list>

   Where:

      <attribute-list> ::= [<LSPA>]
                           [<BANDWIDTH>]
                           [<metric-list>]

      <metric-list> ::= <METRIC>[<metric-list>]
 * 
 * @author Fernando Mu�oz del Nuevo
 *
 */

public class UpdateRequest extends LSPplusPath
{
	public UpdateRequest(){
		super();
		
	}
	
	public UpdateRequest(byte []bytes, int offset)throws PCEPProtocolViolationException {
		super(bytes,offset);
	}
}
