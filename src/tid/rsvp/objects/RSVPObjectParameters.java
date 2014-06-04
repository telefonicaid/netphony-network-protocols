package tid.rsvp.objects;

/**
 * 
 * @author fmn
 *
 */

public class RSVPObjectParameters {

	/*
	 * RSVP Common Header Size
	 */
	
	public static final int RSVP_OBJECT_COMMON_HEADER_SIZE = 4;
	
	/*
	 * RSVP Object Classes
	 */
	
	public static final int RSVP_OBJECT_CLASS_SESSION = 1;
	public static final int RSVP_OBJECT_CLASS_RSVP_HOP = 3;
	public static final int RSVP_OBJECT_CLASS_INTEGRITY = 4;
	public static final int RSVP_OBJECT_CLASS_TIME_VALUES = 5;
	public static final int RSVP_OBJECT_CLASS_ERROR_SPEC = 6;
	public static final int RSVP_OBJECT_CLASS_SCOPE = 7;
	public static final int RSVP_OBJECT_CLASS_STYLE = 8;
	public static final int RSVP_OBJECT_CLASS_FLOW_SPEC = 9;
	public static final int RSVP_OBJECT_CLASS_FILTER_SPEC = 10;
	public static final int RSVP_OBJECT_CLASS_SENDER_TEMPLATE = 11;
	public static final int RSVP_OBJECT_CLASS_SENDER_TSPEC = 12;
	public static final int RSVP_OBJECT_CLASS_ADSPEC = 13;
	public static final int RSVP_OBJECT_CLASS_POLICY_DATA = 14;
	public static final int RSVP_OBJECT_CLASS_RESV_CONFIRM = 15;
	
	public static final int RSVP_OBJECT_CLASS_LABEL = 16;
	public static final int RSVP_OBJECT_CLASS_RRO = 21;
	public static final int RSVP_OBJECT_CLASS_HELLO = 22;
	
	
	/*
	 * RSVP Error Codes 
	 */
	
	/*
	 * 
RFC 2205                          RSVP                    September 1997


APPENDIX B. Error Codes and Values

   The following Error Codes may appear in ERROR_SPEC objects and be
   passed to end systems.  Except where noted, these Error Codes may
   appear only in ResvErr messages.

   o    Error Code = 00: Confirmation

        This code is reserved for use in the ERROR_SPEC object of a
        ResvConf message.  The Error Value will also be zero.

   o    Error Code = 01: Admission Control failure

        Reservation request was rejected by Admission Control due to
        unavailable resources.

        For this Error Code, the 16 bits of the Error Value field are:

           ssur cccc cccc cccc

        where the bits are:




        ss = 00: Low order 12 bits contain a globally-defined sub-code
             (values listed below).


        ss = 10: Low order 12 bits contain a organization-specific sub-
             code.  RSVP is not expected to be able to interpret this
             except as a numeric value.


        ss = 11: Low order 12 bits contain a service-specific sub-code.
             RSVP is not expected to be able to interpret this except as
             a numeric value.

             Since the traffic control mechanism might substitute a
             different service, this encoding may include some
             representation of the service in use.

             u = 0: RSVP rejects the message without updating local
             state.


        u = 1: RSVP may use message to update local state and forward
             the message.  This means that the message is informational.

        r: Reserved bit, should be zero.


        cccc cccc cccc: 12 bit code.

        The following globally-defined sub-codes may appear in the low-
        order 12 bits when ssur = 0000:

        -    Sub-code = 1: Delay bound cannot be met

        -    Sub-code = 2: Requested bandwidth unavailable

        -    Sub-code = 3: MTU in flowspec larger than interface MTU.

   o    Error Code = 02: Policy Control failure

        Reservation or path message has been rejected for administrative
        reasons, for example, required credentials not submitted,
        insufficient quota or balance, or administrative preemption.
        This Error Code may appear in a PathErr or ResvErr message.

        Contents of the Error Value field are to be determined in the
        future.

   o    Error Code = 03: No path information for this Resv message.

        No path state for this session.  Resv message cannot be
        forwarded.

   o    Error Code = 04: No sender information for this Resv message.

        There is path state for this session, but it does not include
        the sender matching some flow descriptor contained in the Resv
        message.  Resv message cannot be forwarded.

   o    Error Code = 05: Conflicting reservation style

        Reservation style conflicts with style(s) of existing
        reservation state.  The Error Value field contains the low-order
        16 bits of the Option Vector of the existing style with which
        the conflict occurred.  This Resv message cannot be forwarded.

   o    Error Code = 06: Unknown reservation style

        Reservation style is unknown.  This Resv message cannot be
        forwarded.

   o    Error Code = 07: Conflicting dest ports

        Sessions for same destination address and protocol have appeared
        with both zero and non-zero dest port fields.  This Error Code
        may appear in a PathErr or ResvErr message.

   o    Error Code = 08: Conflicting sender ports

        Sender port is both zero and non-zero in Path messages for the
        same session.  This Error Code may appear only in a PathErr
        message.

   o    Error Code = 09, 10, 11: (reserved)

   o    Error Code = 12: Service preempted

        The service request defined by the STYLE object and the flow
        descriptor has been administratively preempted.

        For this Error Code, the 16 bits of the Error Value field are:


           ssur cccc cccc cccc

        Here the high-order bits ssur are as defined under Error Code
        01.  The globally-defined sub-codes that may appear in the low-
        order 12 bits when ssur = 0000 are to be defined in the future.

   o    Error Code = 13: Unknown object class

        Error Value contains 16-bit value composed of (Class-Num, C-
        Type) of unknown object.  This error should be sent only if RSVP
        is going to reject the message, as determined by the high-order
        bits of the Class-Num.  This Error Code may appear in a PathErr
        or ResvErr message.

   o    Error Code = 14: Unknown object C-Type

        Error Value contains 16-bit value composed of (Class-Num, C-
        Type) of object.

   o    Error Code = 15-19: (reserved)

   o    Error Code = 20: Reserved for API

        Error Value field contains an API error code, for an API error
        that was detected asynchronously and must be reported via an
        upcall.

   o    Error Code = 21: Traffic Control Error

        Traffic Control call failed due to the format or contents of the
        parameters to the request.  The Resv or Path message that caused
        the call cannot be forwarded, and repeating the call would be
        futile.

        For this Error Code, the 16 bits of the Error Value field are:


           ss00 cccc cccc cccc

        Here the high-order bits ss are as defined under Error Code 01.

        The following globally-defined sub-codes may appear in the low
        order 12 bits (cccc cccc cccc) when ss = 00:

        -    Sub-code = 01: Service conflict

             Trying to merge two incompatible service requests.

        -    Sub-code = 02: Service unsupported

             Traffic control can provide neither the requested service
             nor an acceptable replacement.

        -    Sub-code = 03: Bad Flowspec value

             Malformed or unreasonable request.

        -    Sub-code = 04: Bad Tspec value

             Malformed or unreasonable request.

        -    Sub-code = 05: Bad Adspec value

             Malformed or unreasonable request.

   o    Error Code = 22: Traffic Control System error

        A system error was detected and reported by the traffic control
        modules.  The Error Value will contain a system-specific value
        giving more information about the error.  RSVP is not expected
        to be able to interpret this value.

   o    Error Code = 23: RSVP System error

        The Error Value field will provide implementation-dependent
        information on the error.  RSVP is not expected to be able to
        interpret this value.

   In general, every RSVP message is rebuilt at each hop, and the node
   that creates an RSVP message is responsible for its correct
   construction.  Similarly, each node is required to verify the correct
   construction of each RSVP message it receives.  Should a programming
   error allow an RSVP to create a malformed message, the error is not
   generally reported to end systems in an ERROR_SPEC object; instead,
   the error is simply logged locally, and perhaps reported through
   network management mechanisms.

   The only message formatting errors that are reported to end systems
   are those that may reflect version mismatches, and which the end
   system might be able to circumvent, e.g., by falling back to a
   previous CType for an object; see code 13 and 14 above.

   The choice of message formatting errors that an RSVP may detect and
   log locally is implementation-specific, but it will typically include
   the following:

   o    Wrong-length message: RSVP Length field does not match message
        length.

   o    Unknown or unsupported RSVP version.

   o    Bad RSVP checksum

   o    INTEGRITY failure

   o    Illegal RSVP message Type

   o    Illegal object length: not a multiple of 4, or less than 4.

   o    Next hop/Previous hop address in HOP object is illegal.

   o    Bad source port: Source port is non-zero in a filter spec or
        sender template for a session with destination port zero.

   o    Required object class (specify) missing

   o    Illegal object class (specify) in this message type.

   o    Violation of required object order

   o    Flow descriptor count wrong for style or message type

   o    Logical Interface Handle invalid

   o    Unknown object Class-Num.

   o    Destination address of ResvConf message does not match Receiver
        Address in the RESV_CONFIRM object it contains.

	 */
	
	public static final int RSVP_ERROR_CODE_CONFIRMATION = 0;
	public static final int RSVP_ERROR_VALUE_CONFIRMATION = 0x00;

	public static final int RSVP_ERROR_CODE_ADMISSION_CONTROL_FAILURE = 1;
	public static final int RSVP_ERROR_VALUE_ADMISSION_CONTROL_FAILURE_DELAYBOUND = 0x01;
	public static final int RSVP_ERROR_VALUE_ADMISSION_CONTROL_FAILURE_REQUESTED_BANDWIDTH_UNAVAILABLE = 0x02;
	public static final int RSVP_ERROR_VALUE_ADMISSION_CONTROL_FAILURE_MTU_LARGER_THAN_INTERFACE_MTU = 0x03;

	public static final int RSVP_ERROR_CODE_POLICY_CONTROL_FAILURE = 2;
	
	public static final int RSVP_ERROR_CODE_NO_PATH_INFORMATION = 3;
	
	public static final int RSVP_ERROR_CODE_NO_SENDER_INFORMATION = 4;
	
	public static final int RSVP_ERROR_CODE_CONFLICTING_RESERVATION_STYLE = 5;
	
	public static final int RSVP_ERROR_CODE_UKNOWN_RESERVATION_STYLE = 6;
	
	public static final int RSVP_ERROR_CODE_CONFLICTING_DEST_PORTS = 7;
	
	public static final int RSVP_ERROR_CODE_CONFLICTING_SENDER_PORTS = 8;
	
	public static final int RSVP_ERROR_CODE_SERVICE_PREEMPTED = 12;
	
	public static final int RSVP_ERROR_CODE_UNKNOWN_OBJECT_CLASS = 13;
	
	public static final int RSVP_ERROR_CODE_UNKNOWN_OBJECT_CTYPE = 14;
	
	public static final int RSVP_ERROR_CODE_TRAFFIC_CONTROL = 21;
	public static final int RSVP_ERROR_VALUE_SERVICE_CONFLICT = 0x01;
	public static final int RSVP_ERROR_VALUE_SERVICE_UNSUPPORTED = 0x02;
	public static final int RSVP_ERROR_VALUE_BAD_FLOWSPEC_VALUE = 0x03;
	public static final int RSVP_ERROR_VALUE_BAD_TSPEC_VALUE = 0x04;
	public static final int RSVP_ERROR_VALUE_BAD_ADSPEC_VALUE = 0x05;
	
	public static final int RSVP_ERROR_CODE_TRAFFIC_CONTROL_SYSTEM = 22;
	
	
	/*

	RFC 2205                          RSVP                    September 1997

	   A.7 STYLE Class

	      STYLE class = 8.

	      o    STYLE object: Class = 8, C-Type = 1

	           +-------------+-------------+-------------+-------------+
	           |   Flags     |              Option Vector              |
	           +-------------+-------------+-------------+-------------+



	      Flags: 8 bits

	           (None assigned yet)

	      Option Vector: 24 bits

	           A set of bit fields giving values for the reservation
	           options.  If new options are added in the future,
	           corresponding fields in the option vector will be assigned
	           from the least-significant end.  If a node does not recognize
	           a style ID, it may interpret as much of the option vector as
	           it can, ignoring new fields that may have been defined.

	           The option vector bits are assigned (from the left) as
	           follows:

	           19 bits: Reserved

	           2 bits: Sharing control

	                00b: Reserved

	                01b: Distinct reservations

	                10b: Shared reservations

	                11b: Reserved

	           3 bits: Sender selection control

	                000b: Reserved

	                001b: Wildcard

	                010b: Explicit

	                011b - 111b: Reserved

	      The low order bits of the option vector are determined by the
	      style, as follows:

	              WF 10001b
	              FF 01010b
	              SE 10010b

	 */
	
	
	public static final int RSVP_STYLE_OPTION_VECTOR_WF_STYLE = 17;
	public static final int RSVP_STYLE_OPTION_VECTOR_FF_STYLE = 10;
	public static final int RSVP_STYLE_OPTION_VECTOR_SE_STYLE = 18;
	
}
