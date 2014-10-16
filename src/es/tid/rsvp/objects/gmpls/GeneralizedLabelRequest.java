package es.tid.rsvp.objects.gmpls;

import java.util.logging.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.objects.LabelRequest;
import es.tid.rsvp.objects.RSVPObjectParameters;

/**
 * @author Fernando Mu�oz del Nuevo
 * 
 * 	RFC 3473
 * 
 * 	2.1. Generalized Label Request Object

		A Path message SHOULD contain as specific an LSP (Label Switched
		Path) Encoding Type as possible to allow the maximum flexibility in
		switching by transit LSRs.  A Generalized Label Request object is set
		by the ingress node, transparently passed by transit nodes, and used
		by the egress node.  The Switching Type field may also be updated
		hop-by-hop.
		
		The format of a Generalized Label Request object is:
		
		 0                   1                   2                   3
		 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
		+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
		|            Length             | Class-Num (19)|  C-Type (4)   |
		+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
		| LSP Enc. Type |Switching Type |             G-PID             |
		+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
		
		See [RFC3471] for a description of parameters.



	RFC 3471
	
	
	
	3. Label Related Formats
	
	   To deal with the widening scope of MPLS into the optical and time
	   domain, several new forms of "label" are required.  These new forms
	   of label are collectively referred to as a "generalized label".  A
	   generalized label contains enough information to allow the receiving
	   node to program its cross connect, regardless of the type of this
	   cross connect, such that the ingress segments of the path are
	   properly joined.  This section defines a generalized label request, a
	   generalized label, support for waveband switching, suggested label
	   and label sets.
	
	   Note that since the nodes sending and receiving the new form of label
	   know what kinds of link they are using, the generalized label does
	   not contain a type field, instead the nodes are expected to know from
	   context what type of label to expect.
	
	3.1. Generalized Label Request
	
	
	   The Generalized Label Request supports communication of
	   characteristics required to support the LSP being requested.  These
	   characteristics include LSP encoding and LSP payload.  Note that
	   these characteristics may be used by transit nodes, e.g., to support
	   penultimate hop popping.
	
	   The Generalized Label Request carries an LSP encoding parameter,
	   called LSP Encoding Type.  This parameter indicates the encoding
	   type, e.g., SONET/SDH/GigE etc., that will be used with the data
	   associated with the LSP.  The LSP Encoding Type represents the nature
	   of the LSP, and not the nature of the links that the LSP traverses.
	   A link may support a set of encoding formats, where support means
	   that a link is able to carry and switch a signal of one or more of
	   these encoding formats depending on the resource availability and
	   capacity of the link.  For example, consider an LSP signaled with
	   "lambda" encoding.   It is expected that such an LSP would be
	   supported with no electrical conversion and no knowledge of the
	   modulation and speed by the transit nodes.  Other formats normally
	   require framing knowledge, and field parameters are broken into the
	   framing type and speed as shown below.
	
	   The Generalized Label Request also indicates the type of switching
	   that is being requested on a link.  This field normally is consistent
	   across all links of an LSP.
	
	3.1.1. Required Information
	
	
	   The information carried in a Generalized Label Request is:
	
	    0                   1                   2                   3
	    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	   | LSP Enc. Type |Switching Type |             G-PID             |
	   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	
	      LSP Encoding Type: 8 bits
	
	         Indicates the encoding of the LSP being requested.  The
	         following shows permitted values and their meaning:
	
	   Value       Type
	   -----       ----
	     1         Packet
	     2         Ethernet
	     3         ANSI/ETSI PDH
	     4         Reserved
	     5         SDH ITU-T G.707 / SONET ANSI T1.105
	     6         Reserved
	     7         Digital Wrapper
	     8         Lambda (photonic)
	     9         Fiber
	    10         Reserved
	    11         FiberChannel
//	    12		   Spectrum
	
	         The ANSI PDH and ETSI PDH types designate these respective
	         networking technologies.  DS1 and DS3 are examples of ANSI PDH
	         LSPs.  An E1 LSP would be ETSI PDH.  The Lambda encoding type
	         refers to an LSP that encompasses a whole wavelengths.  The
	         Fiber encoding type refers to an LSP that encompasses a whole
	         fiber port.
	
	      Switching Type: 8 bits
	
	         Indicates the type of switching that should be performed on a
	         particular link.  This field is needed for links that advertise
	         more than one type of switching capability.  This field should
	         map to one of the values advertised for the corresponding link
	         in the routing Switching Capability Descriptor, see [GMPLS-
	         RTG].
	
	         The following are currently defined values:
	
	   Value       Type
	   -----       ----
	     1         Packet-Switch Capable-1 (PSC-1)
	     2         Packet-Switch Capable-2 (PSC-2)
	     3         Packet-Switch Capable-3 (PSC-3)
	     4         Packet-Switch Capable-4 (PSC-4)
	     51        Layer-2 Switch Capable  (L2SC)
	     100       Time-Division-Multiplex Capable (TDM)
	     150       Lambda-Switch Capable   (LSC)
	     200       Fiber-Switch Capable    (FSC)
	
	      Generalized PID (G-PID): 16 bits
	
	         An identifier of the payload carried by an LSP, i.e., an
	         identifier of the client layer of that LSP.  This is used by
	         the nodes at the endpoints of the LSP, and in some cases by the
	         penultimate hop.  Standard Ethertype values are used for packet
	         and Ethernet LSPs; other values are:
	
	   Value   Type                                   Technology
	   -----   ----                                   ----------
	     0     Unknown                                All
	     1     Reserved
	     2     Reserved
	     3     Reserved
	     4     Reserved
	     5     Asynchronous mapping of E4             SDH
	     6     Asynchronous mapping of DS3/T3         SDH
	     7     Asynchronous mapping of E3             SDH
	     8     Bit synchronous mapping of E3          SDH
	     9     Byte synchronous mapping of E3         SDH
	    10     Asynchronous mapping of DS2/T2         SDH
	    11     Bit synchronous mapping of DS2/T2      SDH
	    12     Reserved
	    13     Asynchronous mapping of E1             SDH
	    14     Byte synchronous mapping of E1         SDH
	    15     Byte synchronous mapping of 31 * DS0   SDH
	    16     Asynchronous mapping of DS1/T1         SDH
	    17     Bit synchronous mapping of DS1/T1      SDH
	    18     Byte synchronous mapping of DS1/T1     SDH
	    19     VC-11 in VC-12                         SDH
	    20     Reserved
	    21     Reserved
	    22     DS1 SF Asynchronous                    SONET
	    23     DS1 ESF Asynchronous                   SONET
	    24     DS3 M23 Asynchronous                   SONET
	    25     DS3 C-Bit Parity Asynchronous          SONET
	    26     VT/LOVC                                SDH
	    27     STS SPE/HOVC                           SDH
	    28     POS - No Scrambling, 16 bit CRC        SDH
	    29     POS - No Scrambling, 32 bit CRC        SDH
	    30     POS - Scrambling, 16 bit CRC           SDH
	    31     POS - Scrambling, 32 bit CRC           SDH
	    32     ATM mapping                            SDH
	    33     Ethernet                               SDH, Lambda, Fiber
	    34     SONET/SDH                              Lambda, Fiber
	    35     Reserved (SONET deprecated)            Lambda, Fiber
	    36     Digital Wrapper                        Lambda, Fiber
	    37     Lambda                                 Fiber
	    38     ANSI/ETSI PDH                          SDH
	    39     Reserved                               SDH
	    40     Link Access Protocol SDH               SDH
	           (LAPS - X.85 and X.86)
	    41     FDDI                                   SDH, Lambda, Fiber
	    42     DQDB (ETSI ETS 300 216)                SDH
	    43     FiberChannel-3 (Services)              FiberChannel
	    44     HDLC                                   SDH
	    45     Ethernet V2/DIX (only)                 SDH, Lambda, Fiber
	    46     Ethernet 802.3 (only)                  SDH, Lambda, Fiber
	
*/


public class GeneralizedLabelRequest extends LabelRequest {
	
	/**
	 * Lsp Encoding Type (8 bits)
	 * 
	 * Indicates the encoding of the LSP being requested.  The
	 * following shows permitted values and their meaning:
	 */
	
	private int lspEncodingType;
	
	/**
	 * Switching Type (8 bits)
	 * 
	 * 
	 * Indicates the type of switching that should be performed on a
	 * particular link.  This field is needed for links that advertise
	 * more than one type of switching capability.  This field should
	 * map to one of the values advertised for the corresponding link
	 * in the routing Switching Capability Descriptor, see [GMPLS-RTG].
	 */
	
	private int switchingType;
	
	/**
	 * GPID (16 bits)

		An identifier of the payload carried by an LSP, i.e., an
		identifier of the client layer of that LSP.  This is used by
		the nodes at the endpoints of the LSP, and in some cases by the
		penultimate hop.  Standard Ethertype values are used for packet
		and Ethernet LSPs; other values are:
		
	 */
	
	private int gpid;
	
	/**
	 * Log
	 */
		
	private Logger log;
	
	public GeneralizedLabelRequest(int lspEncodingType, int switchingType, int gpid){
		
		classNum = 19;
		cType = 4;

		this.lspEncodingType = lspEncodingType;
		this.switchingType = switchingType;
		this.gpid = gpid;
		
		length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE + 4;
		
		log = Logger.getLogger("ROADM");

		log.finest("Generalized Label Request Object Created");
		
		
	}
	
	/**
	 * Constructor to be used when a new Label Request With ATM Label Range Object wanted to be decoded from a received
	 * message.
	 * @param bytes
	 * @param offset
	 */
	
	public GeneralizedLabelRequest(byte[] bytes, int offset){
		
		this.decodeHeader(bytes,offset);
		this.bytes = new byte[this.getLength()];
		
		log = Logger.getLogger("ROADM");

		log.finest("Generalized Label Request Object Created");
		
	}	
	
	public void encode() throws RSVPProtocolViolationException{
		
		bytes = new byte[this.length];
		encodeHeader();
		int currentIndex = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		bytes[currentIndex] = (byte) ((lspEncodingType) & 0xFF);
		bytes[currentIndex+1] = (byte) ((switchingType) & 0xFF);
		bytes[currentIndex+2] = (byte)((gpid >> 8) & 0xFF);
		bytes[currentIndex+3] = (byte)((gpid) & 0xFF);
	
	}
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException{


		
	}
	
	// Getters & Setters

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}	
	

}
