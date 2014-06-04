package tid.pce.pcep.constructs;

import tid.pce.pcep.PCEPProtocolViolationException;

/**
 * 
 *  <state-report-list>
   Where:

      <state-report-list> ::= <state-report>[<state-report-list>]

      <state-report> ::= <SRP>
                         <LSP>
                         <path>

   Where:
        <path> is defined in [RFC5440] and extended by PCEP extensions.

   The SRP object (see Section 7.2) is mandatory, and it MUST be
   included for each LSP State Report in the PCRpt message.  The value
   of the SRP-ID-number in the SRP Object MUST be the same as that sent
   in the PCUpd message that triggered the state that is reported, or
   the reserved value 0x00000000 if the state is not as a result of
   processing a PCUpd message.  If the PCC compressed several PCUpd
   messages for the same LSP by only processing the latest one, then it
   should use the SRP-ID-number of that request.  If the SRP object is
   missing, the receiving PCE MUST send a PCErr message with Error-
   type=6 (Mandatory Object missing) and Error-value=[TBD] (SRP object
   missing).  No state compression is allowed for state reporting.  The
   PCC MUST explicitly report state changes (including removal) for
   paths it manages.

   The LSP object (see Section 7.3) is mandatory, and it MUST be
   included in each LSP State Report on the PCRpt message.  If the LSP
   object is missing, the receiving PCE MUST send a PCErr message with
   Error-type=6 (Mandatory Object missing) and Error-value=[TBD] (LSP
   object missing).


   If the LSP transitioned to non-operational state, the PCC SHOULD
   include the LSP-ERROR-TLV (Section 7.3.3) with the relevant LSP Error
   Code to report the error to the PCE.
 * 
 * @author jaume
 *
 */

public class StateReport extends LSPplusPath
{
	public StateReport(){
		super();
		
	}
	
	public StateReport(byte []bytes, int offset)throws PCEPProtocolViolationException {
		super(bytes,offset);
	}
}