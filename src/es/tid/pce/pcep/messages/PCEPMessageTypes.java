package es.tid.pce.pcep.messages;

/**
 * PCEP message type codes. 
 * Messages from:
 * - RFC 5440
 * - RFC 5886,
 * - draft-ietf-pce-stateful-pce-09
 * - draft-ietf-pce-pce-initiated-lsp-01
 * 
 * @author Carlos Garcia Argos (cgarcia@novanotio.es) Feb. 11 2010
 * @author Oscar (Nov. 7 1010)
 */

public class PCEPMessageTypes {

	public static final int MESSAGE_OPEN = 1; // From RFC5440
	public static final int MESSAGE_KEEPALIVE = 2; // From RFC5440
	public static final int MESSAGE_PCREQ = 3; // From RFC5440
	public static final int MESSAGE_PCREP = 4; // From RFC5440
	public static final int MESSAGE_NOTIFY = 5; // From RFC5440
	public static final int MESSAGE_ERROR = 6; // From RFC5440
	public static final int MESSAGE_CLOSE = 7; // From RFC5440
	//Path Computation Monitoring Request Message ( RFC5886 )
	public static final int MESSAGE_PCMONREQ = 8; 
	//Path Computation Monitoring Reply Message ( RFC5886 )
	public static final int MESSAGE_PCMONREP = 9;
	//http://tools.ietf.org/html/draft-ietf-pce-stateful-pce-09
	public static final int MESSAGE_REPORT = 10;
	//http://tools.ietf.org/html/draft-ietf-pce-stateful-pce-09
	public static final int MESSAGE_UPDATE = 11;
	//http://tools.ietf.org/html/draft-ietf-pce-pce-initiated-lsp-01
	public static final int MESSAGE_INITIATE = 12;
	
	//EXPERIMENTAL: TO TALK TO VNTM: 
	public static final int MESSAGE_TE_LINK_SUGGESTION=55;
	public static final int MESSAGE_TE_LINK_SUGGESTION_CONFIRMATION=56;
	public static final int MESSAGE_TE_LINK_TEAR_DOWN_SUGGESTION=57;
	//for testing TM from VNTM
	public static final int MESSAGE_FULL_TOPOLOGY=16;
	
	
}
