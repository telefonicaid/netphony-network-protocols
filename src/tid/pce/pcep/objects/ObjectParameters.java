/** PCEP header fields values for protocol messages according to RFC 5440 and extensions
 * See http://www.iana.org/assignments/pcep/pcep.xml for updated values
 * @author Carlos Garcia Argos (cgarcia@novanotio.es)
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 * Last update: Jun 2011
 */

package tid.pce.pcep.objects;

public class ObjectParameters {
	
	/*
	 * PCEP Object Classes from RFC 5440
	 */
	public static final int PCEP_OBJECT_CLASS_OPEN = 1;
	public static final int PCEP_OBJECT_CLASS_RP = 2;
	public static final int PCEP_OBJECT_CLASS_NOPATH = 3;
	public static final int PCEP_OBJECT_CLASS_ENDPOINTS = 4;
	public static final int PCEP_OBJECT_CLASS_BANDWIDTH = 5;
	public static final int PCEP_OBJECT_CLASS_METRIC = 6;
	public static final int PCEP_OBJECT_CLASS_ERO = 7;
	public static final int PCEP_OBJECT_CLASS_RRO = 8;
	public static final int PCEP_OBJECT_CLASS_LSPA = 9;
	public static final int PCEP_OBJECT_CLASS_IRO = 10;
	public static final int PCEP_OBJECT_CLASS_SVEC = 11;
	public static final int PCEP_OBJECT_CLASS_NOTIFICATION = 12;
	public static final int PCEP_OBJECT_CLASS_PCEPERROR = 13;
	public static final int PCEP_OBJECT_CLASS_LOADBALANCING = 14;
	public static final int PCEP_OBJECT_CLASS_CLOSE = 15;
	//PCEP Object classes from RFC 5520 NOT SUPPORTED YET!!!!!
	public static final int PCEP_OBJECT_CLASS_PATH_KEY=16;
	//PCEP Object classes from RFC 5521 NOT SUPPORTED YET!!!!!
	public static final int PCEP_OBJECT_CLASS_XRO = 17;
	//PCEP Object classes from 	RFC 5886 NOT SUPPORTED YET!!!
	public static final int PCEP_OBJECT_CLASS_MONITORING=19;
	public static final int PCEP_OBJECT_CLASS_PCC_REQ_ID=20;
	// PCEP Object classes from RFC 5541 NOT SUPPORTED YET!!!!!
	public static final int PCEP_OBJECT_CLASS_OBJECTIVE_FUNCTION = 21;
	// PCEP Object clasess from RFC 5455 NOT SUPPORTED YET!!!
	public static final int PCEP_OBJECT_CLASS_CLASSTYPE=22;
	// PCEP Object classes from RFC 5557 NOT SUPPORTED YET!!!
	public static final int PCEP_OBJECT_CLASS_GLOBAL_CONSTRAINTS=24;
	// PCEP Object classes from RFC 5886 NOT SUPPORTED YET!!
	public static final int PCEP_OBJECT_CLASS_PCE_ID=25;
	public static final int PCEP_OBJECT_CLASS_PROC_TIME=26;
	public static final int PCEP_OBJECT_CLASS_OVERLOAD=27;
	// PCEP Object classes from RFC 6006 NOT SUPPORTED YET!!!
	public static final int PCEP_OBJECT_CLASS_UNREACH_DESTINATION=28;
	public static final int PCEP_OBJECT_CLASS_SERO=29;
	public static final int PCEP_OBJECT_CLASS_SRRO=30;

	//PCEP Object classes from draft-ietf-pce-inter-layer-ext-04.txt NOT SUPPORTED YET!!!
	// NUMBERS FOR INTERNAL USE ONLY THEY DONT FOLLOW THE DRAFT as they use already existing numbers!!!!
	public static final int PCEP_OBJECT_CLASS_INTER_LAYER=118;
	public static final int PCEP_OBJECT_CLASS_SWITCH_LAYER=119;
	// PCEP Obect classes from draft-lee-pce-wson-rwa-ext-03
	// NUMBERS FOR INTERNAL USE ONLY!!!!
	public static final int PCEP_OBJECT_CLASS_WAVELENGTH_ASSIGNEMENT=150;
	// PCEP Obect classes from draft-gonzalezdedios-pce-reservation-state-00
	// NUMBERS FOR INTERNAL USE ONLY!!!!
	public static final int PCEP_OBJECT_CLASS_RESERVATION=160;
	public static final int PCEP_OBJECT_CLASS_RESERVATION_CONF=161;
	//PCEP Object classes from GEYSERS!!!
	
	//PCEP extensions for GMPLS draft-ietf-pce-gmpls-pcep-extensions-04
	// NUMBER FOR INTERNAL USE ONLY!!!!
	public static final int PCEP_OBJECT_CLASS_GENERALIZED_BANDWIDTH=155;
	
	
	public static final int PCEP_OBJECT_CLASS_ADVANCE_RESERVATION=100;
	public static final int PCEP_OBJECT_CLASS_NET_QUOTATION=101;

	
	//TODO: Aun por determinar los valores finales 
	public static final int PCEP_OBJECT_CLASS_SR_ERO = 31;
	public static final int PCEP_OBJECT_TYPE_SR_ERO = 2;
	public static final int PCEP_SUBOBJECT_TYPE_SR_ERO = 5; 
	
	
	/*
	 * Object types from RFC 5440
	 */
	public static final int PCEP_OBJECT_TYPE_OPEN = 1;
	public static final int PCEP_OBJECT_TYPE_RP = 1;
	public static final int PCEP_OBJECT_TYPE_NOPATH = 1;
	public static final int PCEP_OBJECT_TYPE_ENDPOINTS = 1;
	public static final int PCEP_OBJECT_TYPE_BANDWIDTH_REQUEST = 1;
	public static final int PCEP_OBJECT_TYPE_BANDWIDTH_REOPT = 2;
	public static final int PCEP_OBJECT_TYPE_ENDPOINTS_IPV4 = 1;
	public static final int PCEP_OBJECT_TYPE_ENDPOINTS_IPV6 = 2;
	public static final int PCEP_OBJECT_TYPE_ENDPOINTS_MAC = 3;
	public static final int PCEP_OBJECT_TYPE_ENDPOINTS_MAC_NOT_UNICAST = 10;
	
	/*
	 * From Strauss project
	 */
	public static final int PCEP_OBJECT_TYPE_ENDPOINTS_DATAPATH_ID = 14;
	
	
	
	public static final int PCEP_OBJECT_TYPE_ENDPOINTS_UNNUMBERED = 4;
	public static final int PCEP_OBJECT_TYPE_METRIC = 1;
	public static final int PCEP_OBJECT_TYPE_ERO = 1;
	public static final int PCEP_OBJECT_TYPE_RRO = 1;
	public static final int PCEP_OBJECT_TYPE_LSPA = 1;
	public static final int PCEP_OBJECT_TYPE_IRO = 1;
	public static final int PCEP_OBJECT_TYPE_SVEC = 1;
	public static final int PCEP_OBJECT_TYPE_NOTIFICATION = 1;
	public static final int PCEP_OBJECT_TYPE_PCEPERROR = 1;
	public static final int PCEP_OBJECT_TYPE_LOADBALANCING = 1;
	public static final int PCEP_OBJECT_TYPE_CLOSE = 1;
	//PCEP Object classes from RFC 5521 NOT SUPPORTED YET!!!!!
	public static final int PCEP_OBJECT_TYPE_XRO = 1;
	//PCEP Object types from RFC 5886
	public static final int PCEP_OBJECT_TYPE_MONITORING=1;
	public static final int PCEP_OBJECT_TYPE_PCC_REQ_ID=1;
	public static final int PCEP_OBJECT_TYPE_PCE_ID_IPV4=1;
	public static final int PCEP_OBJECT_TYPE_PCE_ID_IPV6=2;
	public static final int PCEP_OBJECT_TYPE_PROC_TIME=1;
	public static final int PCEP_OBJECT_TYPE_OVERLOAD=1;
	
	
	
	//PCEP extensions for GMPLS draft-ietf-pce-gmpls-pcep-extensions-04
	////NUMBERS FOR INTERNAL USE ONLY!!!!
	public static final int PCEP_OBJECT_TYPE_GB_INTERSERV=2;
	public static final int PCEP_OBJECT_TYPE_GB_SONET_SDH=4;
	public static final int PCEP_OBJECT_TYPE_GB_G709=5;
	public static final int PCEP_OBJECT_TYPE_GB_ETHERNET=6;
	public static final int PCEP_OBJECT_TYPE_GB_SSON=7;
	
	// PCEP Extensions for Inter-Layer MPLS and GMPLS Traffic Engineering
    //draft-ietf-pce-inter-layer-ext-05
	public static final int PCEP_OBJECT_TYPE_INTER_LAYER=1;
	public static final int PCEP_OBJECT_TYPE_SWITCH_LAYER=1;
	
	// PCEP Object types from RFC 5541
	public static final int PCEP_OBJECT_TYPE_OBJECTIVE_FUNCTION = 1;
	
	// PCEP Obect classes from draft-lee-pce-wson-rwa-ext-03
	// NUMBERS FOR INTERNAL USE ONLY!!!!
	public static final int PCEP_OBJECT_TYPE_WAVELENGTH_ASSIGNEMENT=1;
	
	// PCEP Object types from GEYSERS!!!
	
	
	public static final int PCEP_OBJECT_TYPE_ADVANCE_RESERVATION = 1;
	public static final int PCEP_OBJECT_TYPE_NET_QUOTATION_ENDPOINTS_IP4 = 1;
	public static final int PCEP_OBJECT_TYPE_NET_QUOTATION_ENDPOINTS_IP6 = 2;
	public static final int PCEP_OBJECT_TYPE_NET_QUOTATION_ENDPOINTS_NSAP = 3;
	//public static final int PCEP_OBJECT_TYPE_ENDPOINTS_ASSISTEDUNICAST_IPV4 = 3;
	//public static final int PCEP_OBJECT_TYPE_ENDPOINTS_ASSISTEDUNICAST_IPV6 = 4;
	//public static final int PCEP_OBJECT_TYPE_ENDPOINTS_ASSISTEDUNICAST_NSAP = 5;
	public static final int PCEP_GENERALIZED_END_POINTS_TYPE_ASSISTED_UNICAST = 245;
	public static final int PCEP_GENERALIZED_END_POINTS_TYPE_FULL_ANYCAST = 247;
	
	
	//PCEP Object types from draft-ietf-pce-gmpls-pcep-extensions-02
	public static final int PCEP_OBJECT_TYPE_GENERALIZED_ENDPOINTS = 5;
	
	public static final int PCEP_OBJECT_TYPE_RESERVATION =1;
	public static final int PCEP_OBJECT_TYPE_RESERVATION_CONF=1;
	
	
	public static final int PCEP_GENERALIZED_END_POINTS_TYPE_P2P=1;
	public static final int PCEP_GENERALIZED_END_POINTS_TYPE_P2MP_NEW_LEAVES=2;
	public static final int PCEP_GENERALIZED_END_POINTS_TYPE_P2MP_OLD_LEAVES_MODIFIED=3;
	public static final int PCEP_GENERALIZED_END_POINTS_TYPE_P2MP_OLD_LEAVES_UNCHANGED=4;
	
	public static final int PCEP_TLV_TYPE_LABEL_REQUEST=2000;
	
	public static final int PCEP_TLV_TYPE_MAX_REQ_TIME=3000;
	
	/*
	 * CLOSE reasons
	 */
	public static final int REASON_NOEXPLANATION = 1;
	public static final int REASON_DEADTIMER = 2;
	public static final int REASON_MALFORMED = 3;
	public static final int REASON_UNKNOWN = 4;
	public static final int REASON_UNRECOGNIZED = 5;
	
	/*
	 * ERROR types
	 */
	public static final int ERROR_ESTABLISHMENT = 1;
	public static final int ERROR_CAPABILITY = 2;
	public static final int ERROR_UNKNOWNOBJECT = 3;
	public static final int ERROR_UNSUPPORTEDOBJECT = 4;
	public static final int ERROR_POLICY = 5;
	public static final int ERROR_MISSINGOBJECT = 6;
	public static final int ERROR_MISSINGREQUEST = 7;
	public static final int ERROR_UNKNOWNREQUEST = 8;
	public static final int ERROR_SECONDSESSION = 9;
	public static final int ERROR_INVALIDOBJECT = 10;
	
	
	public static final int ERROR_INVALID_OPERATION = 19;
	public static final int ERROR_LSP_SYNC = 20;
	
	/*
	 * ERROR values
	 */
	//ESTABLISHMENT ERRORS
	
	public static final int ERROR_LSP_OBJECT_MISSING=8;
	public static final int ERROR_LSP_DB_VERSION_MISSING=12;
	public static final int ERROR_STATEFUL_CAPABILITY_NOT_SUPPORTED=555;
	public static final int ERROR_DELEGATION_NOT_NEGOTIATED=556;
	
	
	public static final int ERROR_ESTABLISHMENT_INVALID_OPEN_MESSAGE=0x01;
	public static final int ERROR_ESTABLISHMENT_NO_OPEN_MESSAGE=0x02;
	public static final int ERROR_ESTABLISHMENT_UNACCEPTABLE_NON_NEGOTIABLE_SESSION_CHARACTERISTICS=0x03;
	public static final int ERROR_ESTABLISHMENT_UNACCEPTABLE_NEGOTIABLE_SESSION_CHARACTERISTICS=0x04;
	public static final int ERROR_ESTABLISHMENT_SECOND_OPEN_MESSAGE_UNACCEPTABLE_SESSION_CHARACTERISTICS=0x05;
	public static final int ERROR_ESTABLISHMENT_PCERR_UNACCEPTABLE_SESSION_CHARACTERISTICS=0x06;
	/* Error-value=7: No Keepalive or PCErr message received
                       before the expiration of the KeepWait timer
	 */
	public static final int ERROR_ESTABLISHMENT_NO_KA_OR_ERROR_KEEPWAIT_TIMER=0x07;
	//UNKNOWN OBJECT
	public static final int ERROR_UNKNOWNOBJECT_UNRECOGNIZED_OBJECT_CLASS=0x01;
	public static final int ERROR_UNKNOWNOBJECT_UNRECOGNIZED_OBJECT_TYPE=0x02;
	//NOT SUPPORTED OBJECT
	public static final int ERROR_UNSUPPORTEDOBJECT_NOT_SUPPORTED_OBJECT_CLASS=0x01;
	public static final int ERROR_UNSUPPORTEDOBJECT_NOT_SUPPORTED_OBJECT_TYPE=0x02;
	/**FROM RFC 5441
	 Error-Type     Meaning and error values                 Reference
     4           Not supported object

                 Error-value=4: Unsupported parameter     This document
*/
	public static final int ERROR_UNSUPPORTEDOBJECT_UNSUPPORTED_PARAMETER=0x04;
	/*Policy violation
     Error-value=1: C bit of the METRIC object set (request rejected)
     Error-value=2: O bit of the RP object set (request rejected)
     */ 
	public static final int ERROR_POLICY_C_BIT_METRIC_SET_REQ_REJ=0x01;
	public static final int ERROR_POLICY_O_BIT_RP_SET_REQ_REJ=0x02;
    /*  Mandatory Object missing
     Error-value=1: RP object missing
     Error-value=2: RRO object missing for a reoptimization
                    request (R bit of the RP object set)
                    when bandwidth is not equal to 0.
     Error-value=3: END-POINTS object missing
	 */
	public static final int ERROR_MISSINGOBJECT_RP=0x01;
	public static final int ERROR_MISSINGOBJECT_RR0=0x02;
	public static final int ERROR_MISSINGOBJECT_END_POINTS=0x03;
	/*
	*   10         Reception of an invalid object
                  Error-value=1: reception of an object with P flag not
                  set although the P flag must be set according to this
                  specification.
	 */
	public static final int ERROR_INVALIDOBJECT_P_FLAG_NOT_SET=0x01;
	
	/** 
	 * From RFC 5541
	 * Two new Error-values are defined for the Error-type "policy
   violation" (type 5):

      Error-type      Meaning and error values                 Reference
      ------------------------------------------------------------------
         5            Policy violation

                      Error-value=3: objective function not     RFC 5541
                      allowed (request rejected)

                      Error-value=4: OF bit of the RP object    RFC 5541
                      set (request rejected)
	 */
	public static final int ERROR_POLICY_VIOLATION_OF_NOT_ALLOWED=3;
	public static final int ERROR_POLICY_VIOLATION_OF_BIT_SET=4;

	
	/*
	 * Routing Object types (ERO, RRO, IRO)
	 */
	public static final int RO_IPV4 = 1;
	public static final int RO_IPV6 = 2;
	public static final int RO_UNNUMBERED = 4;
	public static final int RO_AS = 32;
	
	/*
	 * NO PATH Reasons
	 */
	public static final int NOPATH_NOPATH_SAT_CONSTRAINTS=0x00;
	public static final int NOPATH_PCE_CHAIN_BROKEN=0x01;
	
	/**
	 * TLVs
	 */
	public static final int PCEP_TLV_TYPE_NO_PATH_VECTOR=60; 
	public static final int PCEP_TLV_OVERLOADED_DURATION=0x02;
	public static final int PCEP_TLV_REQ_MISSING_TLV=0x03;
	public static final int PCEP_TLV_OF_LIST_TLV=4;
	public static final int PCEP_TLV_ORDER_TLV=5;
	public static final int PCEP_TLV_P2MP_CAPABLE=6;
	public static final int PCEP_TLV_REQUEST_INFO=7;
	public static final int PCEP_TLV_TYPE_XIFI=500;
	
	public static final int PCEP_TLV_PATH_SETUP=666;	

	
	// TLV types from GEYSERS!!!

	public static final int PCEP_TLV_TYPE_ENDPOINT_IPV4=33033;
	public static final int PCEP_TLV_TYPE_ENDPOINTS_IPV4=33035;
	public static final int PCEP_TLV_TYPE_ENDPOINTS_IPV6=33034;
	public static final int PCEP_TLV_TYPE_ENDPOINTS_NSAP=999;
	public static final int PCEP_TLV_TYPE_ENDPOINTS_STORAGE=1000;
	public static final int PCEP_TLV_TYPE_ENDPOINTS_SERVER=1001;
	public static final int PCEP_TLV_TYPE_ENDPOINTS_APPLICATION=1002;
	public static final int PCEP_TLV_TYPE_REQUESTED_STORAGE_SIZE=1003;
	public static final int PCEP_TLV_TYPE_REQUESTED_VOLUME_SIZE=1004;
	public static final int PCEP_TLV_TYPE_REQUESTED_CPUs=1005;
	public static final int PCEP_TLV_TYPE_REQUESTED_MEMORY=1006;
	public static final int PCEP_TLV_TYPE_REQUESTED_DISK_SPACE=1007;
	public static final int PCEP_TLV_TYPE_OPERATIVE_SYSTEM=1008;
	public static final int PCEP_TLV_TYPE_APPLICATION=1009;
	
	public static final int PCEP_TLV_TYPE_IT_ADV=1010;
	public static final int PCEP_TLV_TYPE_STORAGE=1011;
	public static final int PCEP_TLV_TYPE_SERVER=1012;
	
	// draft-gonzalezdedios-pce-reservation-state
	public static final int PCEP_TLV_TYPE_RESERVATION_ID=20000;
	
	//TLV STRONGEST: collaborative PCEs
	public static final int PCEP_TLV_TYPE_PATH_RESERVATION=30003;
	
	// FOR STRONGEST USE ONLY!!!!!
	public static final int PCEP_TLV_DOMAIN_ID_TLV=32771;
	// FOR STRONGEST USE ONLY!!!!!
	public static final int PCEP_TLV_REACHABILITY_TLV=32777;
	//FOR STRONGEST USE ONLY!!!!!
	public static final int PCEP_TLV_OSPFTE_LSU_TLV=32778;
	//FOR STRONGEST USE ONLY!!!!!
	public static final int PCEP_TLV_OSPFTE_LSA_TLV=32779;
	//FOR STRONGEST USE ONLY!!!!!
	public static final int PCEP_TLV_PCE_ID_TLV=32769;
	
	/*
	 * Notification types
	 */
	public static final int PCEP_NOTIFICATION_TYPE_PENDING_REQUEST_CANCELLED=0x01;
	public static final int PCEP_NOTIFICATION_TYPE_OVERLOADED_PCE=0x02;
	
	//From STRONGEST
	public static final int PCEP_NOTIFICATION_TYPE_REACHABILITY=100;
	public static final int  PCEP_NOTIFICATION_TYPE_TOPOLOGY=101;
	public static final int  PCEP_NOTIFICATION_TYPE_IT_RESOURCE_INFORMATION=0x04;

	//For PRIVATE USE ONLY: draft-gonzalezdedios-pce-reservation-state-00
	public static final int PCEP_NOTIFICATION_TYPE_CANCEL_RESERVATION=120;
	
	//STRONGEST:collaborative PCEs --- PRIVATE USE ONLY
	public static final int PCEP_NOTIFICATION_TYPE_PRERESERVE=121;
	
	/*
	 * Notification values
	 */
	//From GEYSERS
	public static final int  PCEP_NOTIFICATION_VALUE_QUERY=0x00;
	public static final int  PCEP_NOTIFICATION_VALUE_UPDATE=0x01;
	public static final int  PCEP_NOTIFICATION_VALUE_DELETE=0x02;
	
	//For PRIVATE USE ONLY: draft-gonzalezdedios-pce-reservation-state-00
	public static final int  PCEP_NOTIFICATION_VALUE_CANCEL_RESERVATION=1;
	public static final int  PCEP_NOTIFICATION_VALUE_CANCEL_ALL_RESERVATIONS=2;
	
	//Strongest
	public static final int  PCEP_NOTIFICATION_VALUE_PATH_RESERVATION=1;
	//Metric Types
	//Standard Metric Types (RFC 5440)
	//T=1: IGP metric
    public static final int  PCEP_METRIC_TYPE_IGP_METRIC=1;
    //  T=2: TE metric
    public static final int  PCEP_METRIC_TYPE_TE_METRIC=2;
    //  T=3: Hop Counts
    public static final int  PCEP_METRIC_TYPE_HOP_COUNT=3;
    //  T=3: BW    
    public static final int  PCEP_METRIC_TYPE_BW=4;
    
    //From draft-dhody-pce-pcep-service-aware-02
    //  T=13(IANA): Latency metric
    public static final int  PCEP_METRIC_TYPE_LATENCY_METRIC=13;
    
    // Nuevos Emuladr PCEP Session Create LSP
    /*private static final int PCEP_OBJECT_CLASS*/
   
    //Inventados RSVP
    public static final int  RSVP_OBJECT_CLASS_ENDPOINTS=222;
    public static final int  RSVP_OBJECT_TYPE_ENDPOINTS_IPV4=223;
    
    
    
    /*******************************************************************/
    //TO BE DONE!!!!!
    
    //Stateful PCE Capability TLV type
    public static final int PCEP_TLV_TYPE_STATEFUL_CAPABILITY=5555;
    
    //LSP database version TLV type
    public static final int PCEP_TLV_TYPE_LSP_DATABASE_VERSION=5556;
    
    //Redundancy group identifier TLV type
    public static final int PCEP_TLV_TYPE_PCE_REDUNDANCY_GROUP_INDENTIFIER=5557;
    
    //LSP Identifiers TLV
    public static final int PCEP_TLV_TYPE_LSP_IDENTIFIERS=5558;
    
    //LSP Error Code TLV
    public static final int PCEP_TLV_TYPE_LSP_ERROR_CODE=5559;
    
    //RSVP Error Spec TLV
    public static final int PCEP_TLV_TYPE_RSVP_ERROR_SPEC=5560;
    
    //Tunnel ID TLV
    public static final int PCEP_TLV_TYPE_TUNNEL_ID=5561;
        
	//Symbolic Path Name
    public static final int PCEP_TLV_TYPE_SYMBOLIC_PATH_NAME=1013;  
    
    //Segment Routing PCE Capability TLV type. Value 26 according to draft-sivabalan-pce-segment-routing-02
    public static final int PCEP_TLV_TYPE_SR_CAPABILITY=26;       
    
    //ESTO NO VA A AQUI. CAMBIARLO!!!
    //FIXME
    public static final boolean amIstateFull=true;  
    public static final byte[] redundancyID= new byte[]{1,2,3,4}; 
    
    
    // From Stateful PCEP objects
    //LSP class
    public static final int PCEP_OBJECT_CLASS_LSP = 32;
    
    //RSP class
    public static final int PCEP_OBJECT_CLASS_RSP = 33;
    
    /*******************************************************************/
    
    public static final int LSP_OPERATIONAL_DOWN = 0;
    public static final int LSP_OPERATIONAL_UP = 1;
    public static final int LSP_OPERATIONAL_ACTIVE = 2;
    public static final int LSP_OPERATIONAL_GOING_DOWN = 3;
    public static final int LSP_OPERATIONAL_GOING_UP = 4;
    
}