package tid.pce.pcep.messages;

/**
 * PCEP message type codes.
 * 
 * Carlos Garcia Argos (cgarcia@novanotio.es)
 * Feb. 11 2010
 * Oscar (Nov. 7 1010): Renombro clase a PCEPMessageTypes 
 */

public class PCEPMessageTypes {

	public static final int MESSAGE_OPEN = 1;
	public static final int MESSAGE_KEEPALIVE = 2;
	public static final int MESSAGE_PCREQ = 3;
	public static final int MESSAGE_PCREP = 4;
	public static final int MESSAGE_NOTIFY = 5;
	public static final int MESSAGE_ERROR = 6;
	public static final int MESSAGE_CLOSE = 7;
	//Path Computation Monitoring Request Message
	public static final int MESSAGE_PCMONREQ = 8;
	//Path Computation Monitoring Reply Message
	public static final int MESSAGE_PCMONREP = 9;
	
	/**
	 * This value is actually 12, the reason it's not changed it's because
	 * MESSAGE_TE_LINK_TEAR_DOWN_SUGGESTION has also 12 value
	 */
	public static final int MESSAGE_INTIATE = 12;
	
	//EXPERIMENTAL: TO TALK TO VNTM: 
	public static final int MESSAGE_TE_LINK_SUGGESTION=55;
	public static final int MESSAGE_TE_LINK_SUGGESTION_CONFIRMATION=56;
	public static final int MESSAGE_TE_LINK_TEAR_DOWN_SUGGESTION=57;

	//Not used
	public static final int MESSAGE_MPLS_SUGGESTION=15;
	public static final int MESSAGE_MPLS_SUGGESTION_CONFIRMATION=14;
	
	//for testing TM from VNTM
	public static final int MESSAGE_FULL_TOPOLOGY=16;
	
	// Path Computation LSP Create Message
	public static final int MESSAGE_CREATEREQ=13;
	
	
	//Experimental, http://tools.ietf.org/html/draft-crabbe-pce-stateful-pce-mpls-te-00#page-4
	/*
	 * The Message-Type field of the PCEP common header for
   	 * the PCUpd message is set to [TBD].
	 */
		
	
	/*************************************************************/
	//TO BE DONE!!!!!!!
	public static final int MESSAGE_UPDATE = 11;
	public static final int MESSAGE_REPORT = 10;
	
	/*************************************************************/
	
	
}
