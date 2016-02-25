package es.tid.pce.pcep.objects;

/**
 * 3.1. INTER-LAYER Object

   The INTER-LAYER object is optional and can be used in PCReq and PCRep
   messages.

   In a PCReq message, the INTER-LAYER object indicates whether inter-
   layer path computation is allowed, the type of path to be computed,
   and whether triggered signaling (hierarchical LSPs per [RFC4206] or
   stitched LSPs per [RFC5150] depending on physical network


E. Oki                  Expires December 2011                 [Page 4]
 
draft-ietf-pce-inter-layer-ext-05.txt                        June 2011


   technologies) is allowed. When the INTER-LAYER object is absent from
   a PCReq message, the receiving PCE MUST process as though inter-layer
   path computation had been explicitly disallowed (I-bit set to zero -
   see below).

   In a PCRep message, the INTER-LAYER object indicates whether inter-
   layer path computation has been performed, the type of path that has
   been computed, and whether triggered signaling is used.

   When a PCReq message includes more than one request, an INTER-LAYER
   object is used per request. When a PCRep message includes more than
   one path per request that is responded to, an INTER-LAYER object is
   used per path.

   INTER-LAYER Object-Class is to be assigned by IANA (recommended
   value=18)

   INTER-LAYER Object-Type is to be assigned by IANA (recommended
   value=1)

   The format of the INTER-LAYER object body is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |    Reserved                                             |T|M|I|
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   I flag (1 bit): The I flag is used by a PCC in a PCReq message to
   indicate to a PCE whether an inter-layer path is allowed. When the I
   flag is set (one), the PCE MAY perform inter-layer path computation
   and return an inter-layer path. When the flag is clear (zero), the
   path that is returned MUST NOT be an inter-layer path.

   The I flag is used by a PCE in a PCRep message to indicate to a PCC
   whether the path returned is an inter-layer path. When the I flag is
   set (one), the path is an inter-layer path. When it is clear (zero),
   the path is contained within a single layer either because inter-
   layer path computation was not performed or because a mono-layer path
   (without any virtual TE link and without any loose hop that spans the
   lower-layer network) was found notwithstanding the use of inter-layer
   path computation.

   M flag (1 bit): The M flag is used by a PCC in a PCReq message to
   indicate to a PCE whether mono-layer path or multi-layer path is



E. Oki                  Expires December 2011                 [Page 5]
 
draft-ietf-pce-inter-layer-ext-05.txt                        June 2011


   requested. When the M flag is set (one), multi-layer path is
   requested. When it is clear (zero), mono-layer path is requested.

   The M flag is used by a PCE in a PCRep message to indicate to a PCC
   whether mono-layer path or multi-layer path is returned. When M flag
   is set (one), multi-layer path is returned. When M flag is set (zero),
   mono-layer path is returned.

   If the I flag is clear (zero), the M flag has no meaning and MUST be
   ignored.

   [PCE-INTER-LAYER-REQ] describes two sub-options for mono-layer path.

   - A mono-layer path that is specified by strict hops. The path may
   include virtual TE links.

   - A mono-layer path that includes loose hops that span the lower-
   layer network.

   The choice of this sub-option can be specified by the use of O flag
   in the RP object specified in [RFC5440].

   T flag (1 bit): The T flag is used by a PCC in a PCReq message to
   indicate to a PCE whether triggered signaling is allowed. When the T
   flag is set (one), triggered signaling is allowed. When it is clear
   (zero), triggered signaling is not allowed.

   The T flag is used by a PCE in a PCRep message to indicate to a PCC
   whether triggered signaling is required to support the returned path.
   When the T flag is set (one), triggered signaling is required. When
   it is clear (zero), triggered signaling is not required.

   Note that triggered signaling is used to support hierarchical
   [RFC4206] or stitched [RFC5150] LSPs according to the physical
   attributes of the network layers.

   If the I flag is clear (zero), the T flag has no meaning and MUST be
   ignored.

   Note that the I flag and M flag differ in the following ways. - When
   the I flag is clear (zero), virtual TE links must not be used in path
   computation. In addition, loose hops that span the lower-layer
   network must not be specified. Only regular TE links from the same
   layer may be used.

   - When the I flag is set (one), the M flag is clear (zero), and the T
   flag is set (one), virtual TE links are allowed in path computation.


E. Oki                  Expires December 2011                 [Page 6]
 
draft-ietf-pce-inter-layer-ext-05.txt                        June 2011


   In addition, when the O flag of the RP object is set, loose hops that
   span the lower-layer network may be specified. This will initiate
   lower-layer LSP setup, thus inter-layer path is setup even though the
   path computation result from a PCE to a PCC include hops from the
   same layer only.

   - However, when the I flag is set (one), the M flag is clear (zero),
   and the T flag is clear (zero), since triggered signaling is not
   allowed, virtual TE links must not be used in path computation. In
   addition, loose hops that span the lower-layer network must not be
   specified. Therefore, this is equivalent to the I flag being clear
   (zero).

   Reserved bits of the INTER-LAYER object SHOULD be transmitted as zero
   and SHOULD be ignored on receipt. A PCE that forwards a path
   computation request to other PCEs SHOULD preserve the settings of
   reserved bits in the PCReq messages it sends and in the PCRep
   messages it forwards to PCCs.

 * 
 * @author ogondio
 *
 */

public class InterLayer extends PCEPObject {
	
	/**
	 * I flag (1 bit): The I flag is used by a PCC in a PCReq message to
   indicate to a PCE whether an inter-layer path is allowed. When the I
   flag is set (one), the PCE MAY perform inter-layer path computation
   and return an inter-layer path. When the flag is clear (zero), the
   path that is returned MUST NOT be an inter-layer path.

   The I flag is used by a PCE in a PCRep message to indicate to a PCC
   whether the path returned is an inter-layer path. When the I flag is
   set (one), the path is an inter-layer path. When it is clear (zero),
   the path is contained within a single layer either because inter-
   layer path computation was not performed or because a mono-layer path
   (without any virtual TE link and without any loose hop that spans the
   lower-layer network) was found notwithstanding the use of inter-layer
   path computation.
	 */
	private boolean IFlag;//true --> Interlayer false --> MonoLayer
	
	// The M flag is used by a PCE in a PCRep message to indicate to a PCC
	//   whether mono-layer path or multi-layer path is returned. When M flag
	//   is set (one), multi-layer path is returned. When M flag is set (zero),
	//   mono-layer path is returned.
	private boolean MFlag;//1 (true)-> Multilayer PATH, 0(false)--> Monolayer PATH 
	
	/*
	 * T flag (1 bit): The T flag is used by a PCC in a PCReq message to
   indicate to a PCE whether triggered signaling is allowed. When the T
   flag is set (one), triggered signaling is allowed. When it is clear
   (zero), triggered signaling is not allowed.

   The T flag is used by a PCE in a PCRep message to indicate to a PCC
   whether triggered signaling is required to support the returned path.
   When the T flag is set (one), triggered signaling is required. When
   it is clear (zero), triggered signaling is not required.
	 */
	
	private boolean TFlag;//true--> triggering allowed
	
	public InterLayer(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_INTER_LAYER);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_INTER_LAYER);

	}
	
	public InterLayer(byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
		decode();
	}
	
	public boolean isIFlag() {
		return IFlag;
	}

	public void setIFlag(boolean iFlag) {
		IFlag = iFlag;
	}

	public boolean isMFlag() {
		return MFlag;
	}

	public void setMFlag(boolean mFlag) {
		MFlag = mFlag;
	}

	public boolean isTFlag() {
		return TFlag;
	}

	public void setTFlag(boolean tFlag) {
		TFlag = tFlag;
	}

	public void encode() {
		this.setObjectLength(8);
		this.object_bytes=new byte[this.getLength()];
		encode_header();
		object_bytes[4]=0x00;
		object_bytes[5]=0x00;
		object_bytes[6]=0x00;
		object_bytes[7]=(byte)( ( ((TFlag?1:0) <<2) & 0x04) | ( ( (MFlag?1:0) <<1) & 0x02) | ((IFlag?1:0)&0x01) ) ;
	}

	
	public void decode() throws MalformedPCEPObjectException {
		IFlag=(object_bytes[7]&0x01)==0x01;
		MFlag =(object_bytes[7]&0x02)==0x02;
		TFlag=(object_bytes[7]&0x04)==0x04;


	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (IFlag ? 1231 : 1237);
		result = prime * result + (MFlag ? 1231 : 1237);
		result = prime * result + (TFlag ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterLayer other = (InterLayer) obj;
		if (IFlag != other.IFlag)
			return false;
		if (MFlag != other.MFlag)
			return false;
		if (TFlag != other.TFlag)
			return false;
		return true;
	}
	
	

}
