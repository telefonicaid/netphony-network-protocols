package es.tid.pce.pcep.objects;

/**
 * <p>Represents a PCEP METRIC Object as defined in RFC 5440 </p>
 * <p> From RFC 5440 Section 7.8. METRIC Object </p>
<pre>
   The METRIC object is optional and can be used for several purposes.

   In a PCReq message, a PCC MAY insert one or more METRIC objects:

   o  To indicate the metric that MUST be optimized by the path
      computation algorithm (IGP metric, TE metric, hop counts).
      Currently, three metrics are defined: the IGP cost, the TE metric
      (see [RFC3785]), and the number of hops traversed by a TE LSP.

   o  To indicate a bound on the path cost that MUST NOT be exceeded for
      the path to be considered as acceptable by the PCC.

   In a PCRep message, the METRIC object MAY be inserted so as to
   provide the cost for the computed path.  It MAY also be inserted
   within a PCRep with the NO-PATH object to indicate that the metric
   constraint could not be satisfied.

   The path computation algorithmic aspects used by the PCE to optimize
   a path with respect to a specific metric are outside the scope of
   this document.

   It must be understood that such path metrics are only meaningful if
   used consistently: for instance, if the delay of a computed path
   segment is exchanged between two PCEs residing in different domains,
   consistent ways of defining the delay must be used.

   The absence of the METRIC object MUST be interpreted by the PCE as a
   path computation request for which no constraints need be applied to
   any of the metrics.

   METRIC Object-Class is 6.

   METRIC Object-Type is 1.

   The format of the METRIC object body is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |          Reserved             |    Flags  |C|B|       T       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                          metric-value                         |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                   Figure 15: METRIC Object Body Format

   The METRIC object body has a fixed length of 8 bytes.

   Reserved (16 bits):  This field MUST be set to zero on transmission
      and MUST be ignored on receipt.

   T (Type - 8 bits):  Specifies the metric type.

      Three values are currently defined:
      *  T=1: IGP metric
      *  T=2: TE metric
      *  T=3: Hop Counts

   Flags (8 bits):  Two flags are currently defined:

      *  B (Bound - 1 bit): When set in a PCReq message, the metric-
         value indicates a bound (a maximum) for the path metric that
         must not be exceeded for the PCC to consider the computed path
         as acceptable.  The path metric must be less than or equal to
         the value specified in the metric-value field.  When the B flag
         is cleared, the metric-value field is not used to reflect a
         bound constraint.

      *  C (Computed Metric - 1 bit): When set in a PCReq message, this
         indicates that the PCE MUST provide the computed path metric
         value (should a path satisfying the constraints be found) in
         the PCRep message for the corresponding metric.

      Unassigned flags MUST be set to zero on transmission and MUST be
      ignored on receipt.

   Metric-value (32 bits):  metric value encoded in 32 bits in IEEE
      floating point format (see [IEEE.754.1985]).

   Multiple METRIC objects MAY be inserted in a PCRep or a PCReq message
   for a given request (i.e., for a given RP).  For a given request,
   there MUST be at most one instance of the METRIC object for each
   metric type with the same B flag value.  If, for a given request, two
   or more instances of a METRIC object with the same B flag value are
   present for a metric type, only the first instance MUST be considered
   and other instances MUST be ignored.

   For a given request, the presence of two METRIC objects of the same
   type with a different value of the B flag is allowed.  Furthermore,
   it is also allowed to insert, for a given request, two METRIC objects
   with different types that have both their B flag cleared: in this
   case, an objective function must be used by the PCE to solve a multi-
   parameter optimization problem.

   A METRIC object used to indicate the metric to optimize during the
   path computation MUST have the B flag cleared and the C flag set to
   the appropriate value.  When the path computation relates to the
   reoptimization of an exiting TE LSP (in which case, the R flag of the
   RP object is set), an implementation MAY decide to set the metric-
   value field to the computed value of the metric of the TE LSP to be
   reoptimized with regards to a specific metric type.

   A METRIC object used to reflect a bound MUST have the B flag set, and
   the C flag and metric-value field set to the appropriate values.

   In a PCRep message, unless not allowed by PCE policy, at least one
   METRIC object MUST be present that reports the computed path metric
   if the C flag of the METRIC object was set in the corresponding path
   computation request (the B flag MUST be cleared).  The C flag has no
   meaning in a PCRep message.  Optionally, the PCRep message MAY
   contain additional METRIC objects that correspond to bound
   constraints; in which case, the metric-value MUST be equal to the
   corresponding computed path metric (the B flag MUST be set).  If no
   path satisfying the constraints could be found by the PCE, the METRIC
   objects MAY also be present in the PCRep message with the NO-PATH
   object to indicate the constraint metric that could be satisfied.

   Example: if a PCC sends a path computation request to a PCE where the
   metric to optimize is the IGP metric and the TE metric must not
   exceed the value of M, two METRIC objects are inserted in the PCReq
   message:

   o  First METRIC object with B=0, T=1, C=1, metric-value=0x0000

   o  Second METRIC object with B=1, T=2, metric-value=M

   If a path satisfying the set of constraints can be found by the PCE
   and there is no policy that prevents the return of the computed
   metric, the PCE inserts one METRIC object with B=0, T=1, metric-
   value= computed IGP path cost.  Additionally, the PCE may insert a
   second METRIC object with B=1, T=2, metric-value= computed TE path
   cost.
 * </pre>
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 * 
 */
public class Metric extends PCEPObject{
	
	/**
	 *  C (Computed Metric - 1 bit): When set in a PCReq message, this
         indicates that the PCE MUST provide the computed path metric
         value (should a path satisfying the constraints be found) in
         the PCRep message for the corresponding metric.
	 */
	private boolean computedMetricBit;
	/**
	 * B (Bound - 1 bit): When set in a PCReq message, the metric-
         value indicates a bound (a maximum) for the path metric that
         must not be exceeded for the PCC to consider the computed path
         as acceptable.  The path metric must be less than or equal to
         the value specified in the metric-value field.  When the B flag
         is cleared, the metric-value field is not used to reflect a
         bound constraint.
	 */
	private boolean boundBit;
	/**
	 * Specifies the metric type.

      Three values are currently defined:
      *  T=1: IGP metric
      *  T=2: TE metric
      *  T=3: Hop Counts
	 */
	private int metricType;
	/**
	 * Metric-value (32 bits):  metric value encoded in 32 bits in IEEE
      floating point format (see [IEEE.754.1985]).
	 */
	private float metricValue;
	
	//Constructors

	public Metric(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_METRIC);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_METRIC);
	}
	
	/**
	 * Constructs a new Metric Object from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public Metric(byte[] bytes,int offset ) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	//Encode and Decode
	
	/**
	 * Encode Metric Object
	 */
	public void encode() {
		ObjectLength=12;/* 4 bytes de la cabecera + 8 del cuerpo */
		object_bytes=new byte[ObjectLength];
		encode_header();
		this.object_bytes[4]=0x00;
		this.object_bytes[5]=0x00;
		this.object_bytes[6]=  (byte)(  (this.boundBit?1:0)|( ((this.computedMetricBit?1:0)<<1 )&0x02) );
		this.object_bytes[7]=(byte)(metricType& 0xff);
		int metricValuei=Float.floatToIntBits(metricValue);
		this.object_bytes[8]=(byte)(metricValuei >>> 24);
		this.object_bytes[9]=(byte)(metricValuei >> 16 & 0xff);
		this.object_bytes[10]=(byte)(metricValuei >> 8 & 0xff);
		this.object_bytes[11]=(byte)(metricValuei & 0xff);			
	}

	/**
	 * Decode Metric Object
	 */
	public void decode() throws MalformedPCEPObjectException{
		if (ObjectLength!=12){
			throw new MalformedPCEPObjectException();
		}		
		this.boundBit=(this.object_bytes[6]&0x01)==0x01;
		this.computedMetricBit=(this.object_bytes[6]&0x02)==0x02;
		this.metricType=this.object_bytes[7];
		int metricValuei=0;
		for (int k = 0; k < 4; k++) {
			metricValuei = (metricValuei << 8) | (object_bytes[k+8] & 0xff);
		}
		metricValue=Float.intBitsToFloat(metricValuei);
				
	}

	//Getters and Setters

	

	public void setComputedMetricBit(boolean computedMetricBit) {
		this.computedMetricBit = computedMetricBit;
	}

	public void setBoundBit(boolean boundBit) {
		this.boundBit = boundBit;
	}
	
	public void setMetricType(int metricType) {
		this.metricType = metricType;
	}

	public void setMetricValue(float metricValue) {
		this.metricValue = metricValue;
	}

	public boolean isBoundBit() {
		return boundBit;
	}
	
	public int getMetricType() {
		return metricType;
	}
	
	public float getMetricValue() {
		return metricValue;
	}
	
	public boolean isComputedMetricBit() {
		return computedMetricBit;
	}
	
	public String toString(){
		if (metricType==1){
			return "<METRIC type=1(IGP) val:"+metricValue+" Bound: "+(this.boundBit?1:0)+"CompMet: "+(this.computedMetricBit?1:0+">");
		}
		else if (metricType==2){
			return "<METRIC type=2(TE) val:"+metricValue+" Bound: "+(this.boundBit?1:0)+"CompMet: "+(this.computedMetricBit?1:0+">");
		} 
		else if (metricType==3){
			return "<METRIC type=3(HOP COUNT) val:"+metricValue+" Bound: "+(this.boundBit?1:0)+"CompMet: "+(this.computedMetricBit?1:0+">");			
		}else if (metricType==ObjectParameters.PCEP_METRIC_TYPE_PATH_DELAY_METRIC){
			return "<METRIC type="+ObjectParameters.PCEP_METRIC_TYPE_PATH_DELAY_METRIC+"(DELAY) val:"+metricValue+" Bound: "+(this.boundBit?1:0)+"CompMet: "+(this.computedMetricBit?1:0+">");			
		}
		else {
			return "<METRIC type="+metricType+"(UNK) val:"+metricValue+" Bound: "+(this.boundBit?1:0)+"CompMet: "+(this.computedMetricBit?1:0+">");			
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (boundBit ? 1231 : 1237);
		result = prime * result + (computedMetricBit ? 1231 : 1237);
		result = prime * result + metricType;
		result = prime * result + Float.floatToIntBits(metricValue);
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
		Metric other = (Metric) obj;
		if (boundBit != other.boundBit)
			return false;
		if (computedMetricBit != other.computedMetricBit)
			return false;
		if (metricType != other.metricType)
			return false;
		if (Float.floatToIntBits(metricValue) != Float
				.floatToIntBits(other.metricValue))
			return false;
		return true;
	}
	
	
	

}
