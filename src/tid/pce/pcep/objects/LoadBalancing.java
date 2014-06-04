package tid.pce.pcep.objects;

/**
 * <p>Represents a PCEP Load Balancing Object. From RFC 5440</p>
 * <p>From RFC 5440 7.16. LOAD-BALANCING Object</p>
<pre>
   There are situations where no TE LSP with a bandwidth of X could be
   found by a PCE although such a bandwidth requirement could be
   satisfied by a set of TE LSPs such that the sum of their bandwidths
   is equal to X.  Thus, it might be useful for a PCC to request a set
   of TE LSPs so that the sum of their bandwidth is equal to X Mbit/s,
   with potentially some constraints on the number of TE LSPs and the
   minimum bandwidth of each of these TE LSPs.  Such a request is made
   by inserting a LOAD-BALANCING object in a PCReq message sent to a
   PCE.

   The LOAD-BALANCING object is optional.

   LOAD-BALANCING Object-Class is 14.

   LOAD-BALANCING Object-Type is 1.

   The format of the LOAD-BALANCING object body is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |           Reserved            |     Flags     |     Max-LSP   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        Min-Bandwidth                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                Figure 21: LOAD-BALANCING Object Body Format

   Reserved (16 bits):  This field MUST be set to zero on transmission
      and MUST be ignored on receipt.

   Flags (8 bits):  No flag is currently defined.  The Flags field MUST
      be set to zero on transmission and MUST be ignored on receipt.

   Max-LSP (8 bits):  maximum number of TE LSPs in the set.

   Min-Bandwidth (32 bits):  Specifies the minimum bandwidth of each
      element of the set of TE LSPs.  The bandwidth is encoded in 32
      bits in IEEE floating point format (see [IEEE.754.1985]),
      expressed in bytes per second.

   The LOAD-BALANCING object body has a fixed length of 8 bytes.

   If a PCC requests the computation of a set of TE LSPs so that the sum
   of their bandwidth is X, the maximum number of TE LSPs is N, and each
   TE LSP must at least have a bandwidth of B, it inserts a BANDWIDTH
   object specifying X as the required bandwidth and a LOAD-BALANCING
   object with the Max-LSP and Min-Bandwidth fields set to N and B,
   respectively.
</pre>
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 * 
*/


public class LoadBalancing extends PCEPObject{

	/**
	 * maximum number of TE LSPs in the set.
	 */
	private int MaxLSP;
	/**
	 *  Specifies the minimum bandwidth of each element of the set of TE LSPs. The bandwidth is encoded in 32
     * bits in IEEE floating point format (see [IEEE.754.1985]),
     * expressed in bytes per second.   
	 */
	private float MinBandwidth;
	
	//Constructors
	
	public LoadBalancing(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_LOADBALANCING);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_LOADBALANCING);
	}
	
	/**
	 * Constructs a new LOAD-BALANCING Object from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public LoadBalancing(byte[] bytes, int offset )throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	//Encode and decode

	/**
	 * Encode the LOAD BALANCING Object
	 */
	public void encode() {
		ObjectLength=12;/* 4 bytes de la cabecera + 4 del cuerpo */
		object_bytes=new byte[ObjectLength];
		encode_header();
		int bwi=Float.floatToIntBits(MinBandwidth);
		this.object_bytes[4]=0x00;
		this.object_bytes[5]=0x00;
		this.object_bytes[6]=0x00;
		this.object_bytes[7]=(byte)(MaxLSP& 0xff);
		this.object_bytes[8]=(byte)(bwi >>> 24);
		this.object_bytes[9]=(byte)(bwi >> 16 & 0xff);
		this.object_bytes[10]=(byte)(bwi >> 8 & 0xff);
		this.object_bytes[11]=(byte)(bwi & 0xff);	
	}

	/**
	 * Decode the LOAD BALANCING Object
	 */
	public void decode() throws MalformedPCEPObjectException {	
		if (ObjectLength!=12){
			throw new MalformedPCEPObjectException();
		}
		MaxLSP=object_bytes[7];
		int bwi = 0;		
		for (int k = 0; k < 4; k++) {
			bwi = (bwi << 8) | (object_bytes[k+8] & 0xff);
		}
		MinBandwidth=Float.intBitsToFloat(bwi);		
	}
	
	//Getters and Setters
		
	public int getMaxLSP() {
		return MaxLSP;
	}

	public void setMaxLSP(int maxLSP) {
		MaxLSP = maxLSP;
	}

	public float getMinBandwidth() {
		return MinBandwidth;
	}

	public void setMinBandwidth(float minBandwidth) {
		MinBandwidth = minBandwidth;
	}
	
}
