package tid.pce.pcep.constructs;


/**
 * Generalized Bandwidth Construct.
 * 
 * 	<p>Represents a PCEP GENERALIZED-BANDWIDTH as defined in PCEP extensions for GMPLS              
 *	draft-ietf-pce-gmpls-pcep-extensions-04</p>
 *
 *	<p>From PCEP extensions for GMPLS draft-ietf-pce-gmpls-pcep-extensions-04
 *	Section 2.2. Traffic Parameters encoding, GENERALIZED-BANDWIDTH Object:</p>
 *	
 *	<p>The PCEP BANDWIDTH does not describe the details of the signal (for
 *  example NVC, multiplier), hence the bandwidth information should be
 *  extended to use the RSVP Tspec object encoding.  The PCEP BANDWIDTH
 *  object defines two types: 1 and 2.  C-Type 2 is representing the
 *  existing bandwidth in case of re-optimization.</p>
 *
 *  <p>The following possibilities cannot be represented in the BANDWIDTH
 *  object:</p>
 *	<pre>
 *  o  Asymmetric bandwidth (different bandwidth in forward and reverse
 *     direction), as described in [RFC6387]
 *
 *  o  GMPLS (SDH/SONET, G.709, ATM, MEF etc) parameters are not
 *     supported.
 *  </pre>
 *  <p>This correspond to requirement 3,4,5 and 10 of
 *  [I-D.ietf-pce-gmpls-aps-req].</p>
 *
 *  <p>According to [RFC5440] the BANDWIDTH object has no TLV and has a
 *  fixed size of 4 bytes.  This definition does not allow extending it
 *  with the required information.  To express this information, a new
 *  object named GENERALIZED-BANDWIDTH having the following format is
 *  defined:</p>
 *  
 *  <pre>
 *  
 *    0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |    Bandwidth Spec Length      | Rev. Bandwidth Spec Length    |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      | Bw Spec Type  |   Reserved                                    |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      ~                     generalized bandwidth                     ~
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      ~          Optional : reverse generalized bandwidth             ~
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      ~                       Optional TLVs                           ~
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  
 *  
 *      0                   1                   2                   3
 *       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |   Traffic Spec Length         | TSpec Type    | Reserved  |R|O|
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      ~                       Traffic Spec                            ~
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      ~                       Optional TLVs                           ~
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 *  The GENERALIZED-BANDWIDTH has a variable length.  The Traffic spec
 *  length field indicates the length of the Traffic spec field.  The
 *  bits R and O have the following meaning:
 *
 *     O bit : when set the value refers to the previous bandwidth in
 *     case of re-optimization
 *
 *     R bit : when set the value refers to the bandwidth of the reverse
 *     direction
 *
 *  The Object type determines which type of bandwidth is represented by
 *  the object.  The following object types are defined:
 *
 *  1.  Intserv
 *
 *  2.  SONET/SDH
 *
 *  3.  G.709
 *
 *  4.  Ethernet
 *  
 *  5.	SSON
 *
 *  The encoding of the field Traffic Spec is the same as in RSVP-TE, it
 *  can be found in the following references.
 *  
 *                     Object Type Name      Reference
 *
 *                     0           Reserved
 *
 *                     1           Reserved
 *
 *                     2           Intserv   [RFC2210]
 *
 *                     3           Reserved
 *
 *                     4           SONET/SDH [RFC4606]
 *
 *                     5           G.709     [RFC4328]
 *
 *                     6           Ethernet  [RFC6003]
 *
 *                       Traffic Spec field encoding
 * </pre>
 * 
 *  <p>The GENERALIZED-BANDWIDTH MAY appear more than once in a PCReq
 *  message.  If more than one GENERALIZED-BANDWIDTH have the same Object
 *  Type, Reserved, R and O values, only the first one is processed, the
 *  others are ignored.</p>
 *
 *  <p>a PCE MAY ignore GENERALIZED-BANDWIDTH objects, a PCC that requires a
 *  GENERALIZED-BANDWIDTH to be used can set the P (Processing) bit in
 *  the object header.</p>
 *
 *  <p>When a PCC needs to get a bi-directional path with asymmetric
 *  bandwidth, it SHOULD specify the different bandwidth in forward and
 *  reverse directions through two separate GENERALIZED-BANDWIDTH
 *  objects.  If the PCC set the P bit on both object the PCE MUST
 *  compute a path that satisfies the asymmetric bandwidth constraint and
 *  return the path to PCC if the path computation is successful.  If the
 *  P bit on the reverse GENERALIZED-BANDWIDTH object the PCE MAY ignore
 *  this constraint.</p>
 *
 *  <p>a PCE MAY include the GENERALIZED-BANDWIDTH objects in the response
 *  to indicate the GENERALIZED-BANDWIDTH of the path</p>
 *
 *  <p>Optional TLVs may be included within the object body to specify more
 *  specific bandwidth requirements.  The specification of such TLVs is
 *  outside the scope of this document.</p>
 *
 *	</pre>
 * 
 *  GENERALIZED_BANDWIDTH Object Class = 155
 * 
 * @author amll
 *
 */


public abstract class GeneralizedBandwidth extends PCEPConstruct {
	
	private int bwSpecType;
  
	/**
	 * Constructs a GeneralizedBandwidth object
	 */
	public GeneralizedBandwidth (){
		super();
	}

	public int getBwSpecType() {
		return bwSpecType;
	}

	public void setBwSpecType(int bwSpecType) {
		this.bwSpecType = bwSpecType;
	}

	public void decode(byte[] bytes, int offset) {
		// TODO Auto-generated method stub
		
	}

}
