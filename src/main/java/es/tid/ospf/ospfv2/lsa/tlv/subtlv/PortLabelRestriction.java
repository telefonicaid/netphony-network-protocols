package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

/**
 * 
http://tools.ietf.org/html/draft-ietf-ccamp-general-constraint-encode-05#section-2.6

Bernstein and Lee     Expires November 25, 2011               [Page 12]
 
Internet-Draft  General Network Element Constraint Encoding    May 2011

2.6. Port Label Restriction sub-TLV


   Port Label Restriction tells us what labels may or may not be used on
   a link.

   The port label restriction of section 1.2.  can be encoded as a sub-
   TLV as follows. More than one of these sub-TLVs may be needed to
   fully specify a complex port constraint. When more than one of these
   sub-TLVs are present the resulting restriction is the intersection of
   the restrictions expressed in each sub-TLV. To indicate that a
   restriction applies to the port in general and not to a specific
   connectivity matrix use the reserved value of 0xFF for the MatrixID.


      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |   MatrixID    |  RestrictionType |      Reserved/Parameter    |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |     Additional Restriction Parameters per RestrictionType    |
     :                                                               :
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   Where:

   MatrixID: either is the value in the corresponding Connectivity
   Matrix sub-TLV or takes the value OxFF to indicate the restriction
   applies to the port regardless of any Connectivity Matrix.

   RestrictionType can take the following values and meanings:

         0: SIMPLE_LABEL  (Simple label selective restriction)

         1: CHANNEL_COUNT (Channel count restriction)

         2: LABEL_RANGE1 (Label range device with a movable center label
         and width)

         3: SIMPLE_LABEL and CHANNEL_COUNT (Combination of SIMPLE_LABEL
         and CHANNEL_COUNT restriction. The accompanying label set and
         channel count indicate labels permitted on the port and the
         maximum number of channels that can be simultaneously used on
         the port)

         4: LINK_LABEL_EXCLUSIVITY (A label may be used at most once
         amongst a set of specified ports)
         
 2.6.1. SIMPLE_LABEL

   In the case of the SIMPLE_LABEL the GeneralPortRestrictions (or
   MatrixSpecificRestrictions) format is given by:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     | MatrixID      | RstType = 0   |             Reserved          |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                        Label Set Field                  |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   In this case the accompanying label set indicates the labels
   permitted on the port.


   2.6.2. CHANNEL_COUNT

   In the case of the CHANNEL_COUNT the format is given by:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     | MatrixID      | RstType = 1   |        MaxNumChannels         |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   In this case the accompanying MaxNumChannels indicates the maximum
   number of channels (labels) that can be simultaneously used on the
   port/matrix.


   2.6.3. LABEL_RANGE1

   In the case of the LABEL_RANGE1 the GeneralPortRestrictions (or
   MatrixSpecificRestrictions) format is given by:


      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     | MatrixID      | RstType = 2   |     MaxLabelRange             |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                        Label Set Field                        |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   In this case the accompanying MaxLabelRange indicates the maximum
   range of the labels. The corresponding label set is used to indicate
   the overall label range. Specific center label information can be
   obtained from dynamic label in use information. It is assumed that
   both center label and range tuning can be done without causing faults
   to existing signals.


   2.6.4. SIMPLE_LABEL and CHANNEL_COUNT

   In the case of the SIMPLE_LABEL and CHANNEL_COUNT the format is given
   by:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     | MatrixID      | RstType = 3   |        MaxNumChannels         |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                     Label Set Field                     |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   In this case the accompanying label set and MaxNumChannels indicate
   labels permitted on the port and the maximum number of labels that
   can be simultaneously used on the port.

   2.6.5. Link Label Exclusivity

   In the case of the SIMPLE_LABEL and CHANNEL_COUNT the format is given
   by:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     | MatrixID      | RstType = 4   |        Reserved               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                     Link Set Field                            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   In this case the accompanying port set indicate that a label may be
   used at most once among the ports in the link set field.

         
 * @author ogondio
 * @author Fernando Munoz del Nuevo
 *
 */
public class PortLabelRestriction extends OSPFSubTLV {
	
	private int matrixID;
	private int restrictionType;
	private int reservedOrParameter;
	private long additionalRestrictionParameters;
	
	public PortLabelRestriction(){
		this.setTLVType(OSPFSubTLVTypes.PortLabelRestriction);
		
	}
	
	public PortLabelRestriction(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		decode();
	}
	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
		if(restrictionType == 1){	//    2.6.2. CHANNEL_COUNT
			
			this.setTLVValueLength(4);
			
		}else{
			
			this.setTLVValueLength(8);
			
		}
		
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		
		int offset=4;
		
		this.tlv_bytes[offset] = (byte)(matrixID & 0xff);
		this.tlv_bytes[offset + 1] = (byte)(restrictionType & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(reservedOrParameter >> 8 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(reservedOrParameter & 0xff);
		
		if(restrictionType != 1){	 //    2.6.1. SIMPLE_LABEL, 2.6.3. LABEL_RANGE1, 2.6.4. SIMPLE_LABEL & CHANNEL_COUNT, 2.6.5. Link Label Exclusivity
		
			offset = offset + 4;
			
			this.tlv_bytes[offset] = (byte)(additionalRestrictionParameters >> 24 & 0xff);
			this.tlv_bytes[offset + 1] = (byte)(additionalRestrictionParameters >> 16 & 0xff);
			this.tlv_bytes[offset + 2] = (byte)(additionalRestrictionParameters >> 8 & 0xff);
			this.tlv_bytes[offset + 3] = (byte)(additionalRestrictionParameters & 0xff);
			
		}
	}
	
	public void decode()throws MalformedOSPFSubTLVException{
		
		int offset=4;
		this.matrixID = (int) (this.tlv_bytes[offset]);
		this.restrictionType = (int) (this.tlv_bytes[offset+1]);
		this.reservedOrParameter = (int) (((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF));
			
		if ((this.getTLVValueLength() != 4) && (this.restrictionType == 1)){
			
			throw new MalformedOSPFSubTLVException();
		
		}else if((this.getTLVValueLength() != 8)){
			
			throw new MalformedOSPFSubTLVException();
			
		}
		
		if(this.restrictionType != 1){ //    2.6.2. CHANNEL_COUNT, 2.6.3. LABEL_RANGE1, 2.6.4. SIMPLE_LABEL & CHANNEL_COUNT, 2.6.5. Link Label Exclusivity
			
			offset = offset + 4;
			this.additionalRestrictionParameters = (long) (((this.tlv_bytes[offset] << 24) & 0xFF000000) | ((tlv_bytes[offset+1]<<16)& 0xFF0000) | ((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF));
			
		}

	}

	public int getMatrixID() {
		return matrixID;
	}

	public void setMatrixID(int matrixID) {
		this.matrixID = matrixID;
	}

	public int getRestrictionType() {
		return restrictionType;
	}

	public void setRestrictionType(int restrictionType) {
		this.restrictionType = restrictionType;
	}

	public int getReservedOrParameter() {
		return reservedOrParameter;
	}

	public void setReservedOrParameter(int reservedOrParameter) {
		this.reservedOrParameter = reservedOrParameter;
	}

	public long getAdditionalRestrictionParameters() {
		return additionalRestrictionParameters;
	}

	public void setAdditionalRestrictionParameters(
			long additionalRestrictionParameters) {
		this.additionalRestrictionParameters = additionalRestrictionParameters;
	}

	@Override
	public String toString() {
		return "PortLabelRestriction [matrixID=" + matrixID
				+ ", restrictionType=" + restrictionType
				+ ", reservedOrParameter=" + reservedOrParameter
				+ ", additionalRestrictionParameters="
				+ additionalRestrictionParameters + "]";
	}
	
	
}
