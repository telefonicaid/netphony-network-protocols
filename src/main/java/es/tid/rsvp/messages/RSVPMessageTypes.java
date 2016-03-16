package es.tid.rsvp.messages;

public class RSVPMessageTypes {

	/**
	 * 
	 * Message Types from	RFC 2205
	 *      
	 *      Msg Type: 8 bits

              1 = Path
              2 = Resv
              3 = PathErr
              4 = ResvErr
              5 = PathTear
              6 = ResvTear
              7 = ResvConf

	 * 
	 */
	
	public static final int MESSAGE_PATH = 1;
	public static final int MESSAGE_RESV = 2;
	public static final int MESSAGE_PATHERR = 3;
	public static final int MESSAGE_RESVERR = 4;
	public static final int MESSAGE_PATHTEAR = 5;
	public static final int MESSAGE_RESVTEAR = 6;
	public static final int MESSAGE_RESVCONF = 7;
	
	public static final int MESSAGE_HELLO = 20;

	public static final int RSVP_MESSAGE_HEADER_LENGTH = 8;
	
}
