package tid.pce.pcep.objects;

import tid.pce.pcep.objects.tlvs.DomainIDTLV;
import tid.pce.pcep.objects.tlvs.GMPLSCapabilityTLV;
import tid.pce.pcep.objects.tlvs.LSPDatabaseVersionTLV;
import tid.pce.pcep.objects.tlvs.OF_LIST_TLV;
import tid.pce.pcep.objects.tlvs.PCEPTLV;
import tid.pce.pcep.objects.tlvs.PCE_ID_TLV;
import tid.pce.pcep.objects.tlvs.PCE_Redundancy_Group_Identifier_TLV;
import tid.pce.pcep.objects.tlvs.SRCapabilityTLV;
import tid.pce.pcep.objects.tlvs.StatefulCapabilityTLV;

/** 
 * <p>Represents a PCEP OPEN Object, as described in RFC 5440.</p>
 * <p>No optional TLVs in current version</p>
 * <p>From RFC 5440, Section 7.3. OPEN Object</p>
<pre>
   The OPEN object MUST be present in each Open message and MAY be
   present in a PCErr message.  There MUST be only one OPEN object per
   Open or PCErr message.

   The OPEN object contains a set of fields used to specify the PCEP
   version, Keepalive frequency, DeadTimer, and PCEP session ID, along
   with various flags.  The OPEN object may also contain a set of TLVs
   used to convey various session characteristics such as the detailed
   PCE capabilities, policy rules, and so on.  No TLVs are currently
   defined.

   OPEN Object-Class is 1.

   OPEN Object-Type is 1.

   The format of the OPEN object body is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | Ver |   Flags |   Keepalive   |  DeadTimer    |      SID      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                       Optional TLVs                         //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                    Figure 9: OPEN Object Format

   Ver (3 bits):  PCEP version.  Current version is 1.

   Flags (5 bits):  No flags are currently defined.  Unassigned bits are
      considered as reserved.  They MUST be set to zero on transmission
      and MUST be ignored on receipt.

   Keepalive (8 bits):  maximum period of time (in seconds) between two
      consecutive PCEP messages sent by the sender of this message.  The
      minimum value for the Keepalive is 1 second.  When set to 0, once
      the session is established, no further Keepalive messages are sent
      to the remote peer.  A RECOMMENDED value for the keepalive
      frequency is 30 seconds.

   DeadTimer (8 bits):  specifies the amount of time after the
      expiration of which the PCEP peer can declare the session with the
      sender of the Open message to be down if no PCEP message has been
      received.  The DeadTimer SHOULD be set to 0 and MUST be ignored if
      the Keepalive is set to 0.  A RECOMMENDED value for the DeadTimer
      is 4 times the value of the Keepalive.

   Example:

   A sends an Open message to B with Keepalive=10 seconds and
   DeadTimer=40 seconds.  This means that A sends Keepalive messages (or
   any other PCEP message) to B every 10 seconds and B can declare the
   PCEP session with A down if no PCEP message has been received from A
   within any 40-second period.

   SID (PCEP session ID - 8 bits):  unsigned PCEP session number that
      identifies the current session.  The SID MUST be incremented each
      time a new PCEP session is established.  It is used for logging
      and troubleshooting purposes.  Each increment SHOULD have a value
      of 1 and may cause a wrap back to zero.

      The SID is used to disambiguate instances of sessions to the same
      peer.  A PCEP implementation could use a single source of SIDs
      across all peers, or one source for each peer.  The former might
      constrain the implementation to only 256 concurrent sessions.  The
      latter potentially requires more states.  There is one SID number
      in each direction.

   Optional TLVs may be included within the OPEN object body to specify
   PCC or PCE characteristics.  The specification of such TLVs is
   outside the scope of this document.

   When present in an Open message, the OPEN object specifies the
   proposed PCEP session characteristics.  Upon receiving unacceptable
   PCEP session characteristics during the PCEP session initialization
   phase, the receiving PCEP peer (PCE) MAY include an OPEN object
   within the PCErr message so as to propose alternative acceptable
   session characteristic values.
</pre>
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 */
public class OPEN extends PCEPObject{

	/** Ver (3 bits):  PCEP version.  Current version is 1.
	*/
    private int Ver;
	/**  No flags are currently defined. 
	*/
	//private byte Flags;
	/** Keepalive (8 bits):  maximum period of time (in seconds) between two
    *  consecutive PCEP messages sent by the sender of this message.  The
    *  minimum value for the Keepalive is 1 second.  When set to 0, once
    *  the session is established, no further Keepalive messages are sent
    *  to the remote peer.  A RECOMMENDED value for the keepalive
    *  frequency is 30 seconds.
	*/
    /**
     * FROM http://www.ietf.org/id/draft-zhang-pce-hierarchy-extensions-00.txt
     *  Parent PCE request bit (to be assigned by IANA, recommended bit 0):
     * if set it means the child PCE wishes to use the peer PCE as a 
     * parent PCE. 
    */
    private boolean parentPCERequestBit;
    
    /**
     * FROM http://www.ietf.org/id/draft-zhang-pce-hierarchy-extensions-00.txt
     * Parent PCE indication bit (to be assigned by IANA, recommended bit
     * 1): if set it means the PCE can be used as a parent PCE by the peer
     * PCE.       
    */
    private boolean parentPCEIndicationBit;
    
	private int Keepalive;
	/**	 DeadTimer (8 bits):  specifies the amount of time after the
      expiration of which the PCEP peer can declare the session with the
      sender of the Open message to be down if no PCEP message has been
      received.  The DeadTimer SHOULD be set to 0 and MUST be ignored if
      the Keepalive is set to 0.  A RECOMMENDED value for the DeadTimer
      is 4 times the value of the Keepalive.
	*/
	private int Deadtimer;
	/**
	 * SID: Session ID.
	 */
	private int SID;
	
	//private LinkedList<PCEPTLV> tLVList;  
	
	/**
	 * Optional OF_LIST_TLV
	 */
	private OF_LIST_TLV of_list_tlv;
	/**
	 * Optional DOMAIN ID TLV
	 */
	private DomainIDTLV domain_id_tlv;
	/**
	 * Optional PCE ID TLV
	 */
	private PCE_ID_TLV pce_id_tlv;
	
	/**
	 * Optional GMPLS CAPABILITY TLV
	 */
	
	private GMPLSCapabilityTLV gmplsCapabilityTLV;
	
	/**
	 * Optional Stateful PCE Capability TLV. It indicates wether PCE or PCC supports
	 * stateful operations
	 */
	private StatefulCapabilityTLV stateful_capability_tlv = null;

	/**
	 * Optional SR PCE Capability TLV. It indicates wether PCE or PCC supports
	 * segment routing paths
	 */
	private SRCapabilityTLV SR_capability_tlv = null;	
	
	/**
	 * Optional LSP database version TLV. It indicates the database version of the LSP. It's useful
	 * for the synchronization phase in order to skip it if both the PCE and PCC have the same version
	 */
	private LSPDatabaseVersionTLV lsp_database_version_tlv = null;
	
	/**
	 * Optional Redundancy Group Indentifier TLV. Used for database synchronization
	 */
	private PCE_Redundancy_Group_Identifier_TLV redundancy_indetifier_tlv = null;
	
	
			
	//Constructors
	
	/** 
	 * Default constructor. Use to create new OPEN objet with default parameters.
	*/	
	public OPEN() {
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_OPEN);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_OPEN);
		/*Default Values*/
		Ver=0x01;
		//Flags=0x00;
		Keepalive=0x1E;/** By default 30s */
		Deadtimer=0x78;/** By default 120s */
		SID=0x01;
		parentPCERequestBit=false;
		parentPCEIndicationBit=false;
	}

	/**
	 * Constructs a new OPEN Object from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public OPEN(byte []bytes, int offset)throws MalformedPCEPObjectException {
		super(bytes, offset);
		decode();
	}
	
	/**
	 * Encode the OPEN object
	 */
	public void encode() {
		this.ObjectLength=8;//The four bytes of the header + 4 of the body
		if (of_list_tlv!=null){
			of_list_tlv.encode();
			ObjectLength=ObjectLength+of_list_tlv.getTotalTLVLength();
		}
		if (domain_id_tlv!=null){
			domain_id_tlv.encode();
			ObjectLength=ObjectLength+domain_id_tlv.getTotalTLVLength();
		}
		if (pce_id_tlv!=null){
			pce_id_tlv.encode();
			ObjectLength=ObjectLength+pce_id_tlv.getTotalTLVLength();
		}
		if (gmplsCapabilityTLV!=null){
			gmplsCapabilityTLV.encode();
			ObjectLength=ObjectLength+gmplsCapabilityTLV.getTotalTLVLength();
		}	
		if (stateful_capability_tlv!=null){
			stateful_capability_tlv.encode();
			ObjectLength=ObjectLength+stateful_capability_tlv.getTotalTLVLength();
		}
		if (SR_capability_tlv!=null){
			SR_capability_tlv.encode();
			ObjectLength=ObjectLength+SR_capability_tlv.getTotalTLVLength();
		}		
		if (lsp_database_version_tlv!=null){
			lsp_database_version_tlv.encode();
			ObjectLength=ObjectLength+lsp_database_version_tlv.getTotalTLVLength();
		}
		if (redundancy_indetifier_tlv!=null){
			redundancy_indetifier_tlv.encode();
			ObjectLength=ObjectLength+redundancy_indetifier_tlv.getTotalTLVLength();
		}

		object_bytes=new byte[ObjectLength];
		
		log.info("OPEN: Header size: "+ObjectLength);
		
		encode_header();
		object_bytes[4]=(byte)( ((Ver<<5) & 0xE0) |( ((parentPCEIndicationBit?1:0)<<3 ) &0x08 ) | ((parentPCERequestBit?1:0<<4) &0x10 ) );
		object_bytes[5]=(byte)Keepalive;
		object_bytes[6]=(byte)Deadtimer;
		object_bytes[7]=(byte)(SID& 0xFF);
		int offset=8;
		if (of_list_tlv!=null){
			System.arraycopy(of_list_tlv.getTlv_bytes(),0,this.object_bytes,offset,of_list_tlv.getTotalTLVLength());
			offset=offset+of_list_tlv.getTotalTLVLength();
			
		}
		if (domain_id_tlv!=null){
			System.arraycopy(domain_id_tlv.getTlv_bytes(),0,this.object_bytes,offset,domain_id_tlv.getTotalTLVLength());
			offset=offset+domain_id_tlv.getTotalTLVLength();
		}
		if (pce_id_tlv!=null){
			System.arraycopy(pce_id_tlv.getTlv_bytes(),0,this.object_bytes,offset,pce_id_tlv.getTotalTLVLength());
			offset=offset+pce_id_tlv.getTotalTLVLength();
		}
		if (gmplsCapabilityTLV!=null){
			System.arraycopy(gmplsCapabilityTLV.getTlv_bytes(),0,this.object_bytes,offset,gmplsCapabilityTLV.getTotalTLVLength());
			offset=offset+gmplsCapabilityTLV.getTotalTLVLength();
		}
		if (stateful_capability_tlv!=null){
			System.arraycopy(stateful_capability_tlv.getTlv_bytes(),0,this.object_bytes,offset,stateful_capability_tlv.getTotalTLVLength());
			offset=offset+stateful_capability_tlv.getTotalTLVLength();
		}
		if (SR_capability_tlv!=null){
			System.arraycopy(SR_capability_tlv.getTlv_bytes(),0,this.object_bytes,offset,SR_capability_tlv.getTotalTLVLength());
			offset=offset+SR_capability_tlv.getTotalTLVLength();
		}		
		if (lsp_database_version_tlv!=null){
			System.arraycopy(lsp_database_version_tlv.getTlv_bytes(),0,this.object_bytes,offset,lsp_database_version_tlv.getTotalTLVLength());
			offset=offset+lsp_database_version_tlv.getTotalTLVLength();
		}
		if (redundancy_indetifier_tlv!=null){
			System.arraycopy(redundancy_indetifier_tlv.getTlv_bytes(),0,this.object_bytes,offset,redundancy_indetifier_tlv.getTotalTLVLength());
			offset=offset+redundancy_indetifier_tlv.getTotalTLVLength();
		}
	}
	
	/**
	 * Decodes the OPEN object
	 */
	public void decode() throws MalformedPCEPObjectException {
		log.info("Beginning decoding of OPEN object");
		redundancy_indetifier_tlv = null;
		lsp_database_version_tlv = null;
		stateful_capability_tlv = null;
		SR_capability_tlv = null;		
		
		Ver=(object_bytes[4]>>>5) & 0x07;
		parentPCEIndicationBit=(object_bytes[4]&0x08)==0x08;
		parentPCERequestBit=(object_bytes[4]&0x10)==0x10;
		Keepalive=object_bytes[5] & 0xFF;
		Deadtimer=object_bytes[6] & 0xFF;
		SID=object_bytes[7]& 0xFF;	
		boolean fin=false;
		int offset=8;
		if (ObjectLength==8){
			log.info("Prematurely ending. OPEN message too short");
			fin=true;
		}
		while (!fin) {
			log.info("Beginning TLV decoding");
			int tlvtype=PCEPTLV.getType(this.getObject_bytes(), offset);
			int tlvlength=PCEPTLV.getTotalTLVLength(this.getObject_bytes(), offset);
			log.info("TLV type "+tlvtype+" TLV length "+tlvlength);//FIXME: Cambiar a log.fine cuando est� estable!!!
			switch (tlvtype){
			case ObjectParameters.PCEP_TLV_OF_LIST_TLV:
				log.info("OF_LIST_TLV found");
				of_list_tlv=new OF_LIST_TLV(this.getObject_bytes(), offset);
				log.fine(of_list_tlv.toString());
				break;
			case ObjectParameters.PCEP_TLV_DOMAIN_ID_TLV:
				log.fine("DOMAIN_ID TLV found");
				domain_id_tlv=new DomainIDTLV(this.getObject_bytes(), offset);
				log.fine(domain_id_tlv.toString());				
				break;
			case ObjectParameters.PCEP_TLV_PCE_ID_TLV:
				log.fine("PCEP_TLV_PCE_ID found");
				pce_id_tlv=new PCE_ID_TLV(this.getObject_bytes(), offset);
				log.fine(pce_id_tlv.toString());
				break;
			case ObjectParameters.PCEP_TLV_TYPE_GMPLS_CAPABILITY:
				log.info("PCEP_TLV_GMPLS_CAPABILITY found");
				gmplsCapabilityTLV=new GMPLSCapabilityTLV(this.getObject_bytes(), offset);
				log.info(gmplsCapabilityTLV.toString());
				break;
			case ObjectParameters.PCEP_TLV_TYPE_STATEFUL_CAPABILITY:
				log.fine("PCEP_TLV_TYPE_STATEFUL_CAPABILITY found");
				stateful_capability_tlv=new StatefulCapabilityTLV(this.getObject_bytes(), offset);
				log.fine(stateful_capability_tlv.toString());
				break;
			case ObjectParameters.PCEP_TLV_TYPE_SR_CAPABILITY:
				log.fine("PCEP_TLV_TYPE_SR_CAPABILITY found");
				SR_capability_tlv=new SRCapabilityTLV(this.getObject_bytes(), offset);
				log.fine(SR_capability_tlv.toString());
				break;					
			case ObjectParameters.PCEP_TLV_TYPE_LSP_DATABASE_VERSION:
				log.fine("PCEP_TLV_TYPE_LSP_DATABASE_VERSION found");
				lsp_database_version_tlv=new LSPDatabaseVersionTLV(this.getObject_bytes(), offset);
				log.fine(lsp_database_version_tlv.toString());
				break;
			case ObjectParameters.PCEP_TLV_TYPE_PCE_REDUNDANCY_GROUP_INDENTIFIER:
				log.fine("PCEP_TLV_TYPE_PCE_REDUNDANCY_GROUP_INDENTIFIER found");
				redundancy_indetifier_tlv=new PCE_Redundancy_Group_Identifier_TLV(this.getObject_bytes(), offset);
				break;
		
			default:
				log.info("UNKNOWN TLV found");
				//UnknownTLV unknownTLV = new UnknownTLV();			
				//FIXME: Que hacemos con los desconocidos????
				break;
			}
			offset=offset+tlvlength;
			if (offset>=ObjectLength){
				fin=true;
			}
		}
	}

	/**
	 * gets the Deadtimer
	 * @return Deadtimer
	 */
	public int getDeadtimer() {
		return Deadtimer;
	}

	/**
	 * 
	 * @return Keepalive timer (int)
	 */
	public int getKeepalive() {
		return Keepalive;
	}

	/**
	 * 
	 * @return Session ID
	 */
	public int getSID() {
		return SID;
	}
		

	/**
	 * 
	 * @return PCEP Version
	 */
	public int getVer() {
	    return Ver;
	}
	

	/**
	 * 
	 * @param dt Deadtimer
	 */
	public void setDeadtimer (int dt) {
	    Deadtimer=(byte)dt;
	}

	/**
	 * 
	 * @param ka Keepalive timer
	 */
	public void setKeeealive(int ka){
		Keepalive=(byte)ka;
	}
	
	/**
	 * 
	 * @param sd Session ID
	 */
	public void setSID (int sd) {
		SID=sd;
	}
	
	/**
	 * 
	 * @param version PCEP Version
	 */
	public void setVer(int version) {
		if ((version>7) || (version<0)) {
	        //throw new InvalidVerException();
			//throw new Exception();
			}
		else
			Ver=(byte)version;
		}
	
	

	public boolean isParentPCERequestBit() {
		return parentPCERequestBit;
	}

	public void setParentPCERequestBit(boolean parentPCERequestBit) {
		this.parentPCERequestBit = parentPCERequestBit;
	}

	public boolean isParentPCEIndicationBit() {
		return parentPCEIndicationBit;
	}

	public void setParentPCEIndicationBit(boolean parentPCEIndicationBit) {
		this.parentPCEIndicationBit = parentPCEIndicationBit;
	}
	
	

	public OF_LIST_TLV getOf_list_tlv() {
		return of_list_tlv;
	}

	public void setOf_list_tlv(OF_LIST_TLV of_list_tlv) {
		this.of_list_tlv = of_list_tlv;
	}

	public DomainIDTLV getDomain_id_tlv() {
		return domain_id_tlv;
	}

	public void setDomain_id_tlv(DomainIDTLV domain_id_tlv) {
		this.domain_id_tlv = domain_id_tlv;
	}

	public PCE_ID_TLV getPce_id_tlv() {
		return pce_id_tlv;
	}

	public void setPce_id_tlv(PCE_ID_TLV pce_id_tlv) {
		this.pce_id_tlv = pce_id_tlv;
	}

	public StatefulCapabilityTLV getStateful_capability_tlv() {
		return stateful_capability_tlv;
	}

	public void setStateful_capability_tlv(
			StatefulCapabilityTLV stateful_capability_tlv) {
		this.stateful_capability_tlv = stateful_capability_tlv;
	}
	public SRCapabilityTLV getSR_capability_tlv() {
		return SR_capability_tlv;
	}

	public void setSR_capability_tlv(
			SRCapabilityTLV SR_capability_tlv) {
		this.SR_capability_tlv = SR_capability_tlv;
	}		

	public LSPDatabaseVersionTLV getLsp_database_version_tlv() {
		return lsp_database_version_tlv;
	}

	public void setLsp_database_version_tlv(
			LSPDatabaseVersionTLV lsp_database_version_tlv) {
		this.lsp_database_version_tlv = lsp_database_version_tlv;
	}

	public PCE_Redundancy_Group_Identifier_TLV getRedundancy_indetifier_tlv() {
		return redundancy_indetifier_tlv;
	}

	public void setRedundancy_indetifier_tlv(
			PCE_Redundancy_Group_Identifier_TLV redundancy_indetifier_tlv) {
		this.redundancy_indetifier_tlv = redundancy_indetifier_tlv;
	}

	public GMPLSCapabilityTLV getGmplsCapabilityTLV() {
		return gmplsCapabilityTLV;
	}

	public void setGmplsCapabilityTLV(GMPLSCapabilityTLV gmplsCapabilityTLV) {
		this.gmplsCapabilityTLV = gmplsCapabilityTLV;
	}

	public String toString() {
		return "Ver: "+Ver+" Flags: "+"Parent PCE Indication Bit: "+parentPCEIndicationBit+"Parent PCE Request Bit: "+parentPCERequestBit+" Keepalive"+Keepalive+" Deadtimer: "+Deadtimer+" SID: "+SID;
	}
}