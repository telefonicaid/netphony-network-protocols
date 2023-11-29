package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import es.tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields.*;

/**
 * 
http://tools.ietf.org/html/draft-ietf-ccamp-general-constraint-encode-05#section-2.3

Bernstein and Lee     Expires November 25, 2011               [Page 12]
 
Internet-Draft  General Network Element Constraint Encoding    May 2011

2.3. Available Labels Sub-TLV


   To indicate the labels available for use on a link the Available
   Labels sub-TLV consists of a single variable length label set field
   as follows:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                           Label Set Field                     |
     :                                                               :
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   Note that Label Set Field is defined in Section 3.2.


 * @author ogondio
 * @author Fernando Munoz del Nuevo
 *
 */
public class AvailableLabels extends OSPFSubTLV {
	
	private LabelSetField labelSet;
	
	public AvailableLabels(){
		this.setTLVType(OSPFSubTLVTypes.AvailableLabels);
		
	}
	
	public AvailableLabels(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{		
		super(bytes,offset);		
		decode();
	}
	@Override
	public void encode()throws MalformedOSPFSubTLVException{
		// TODO Auto-generated method stub
		//log.debug("Inicio encode labelset");
		if (labelSet==null){
			log.warn("Label Set de Availables Labels is null");
			throw new MalformedOSPFSubTLVException();
			
		}
		else{
			
			//log.debug("BAISSSH: "+labelSet.toString());
			labelSet.encode();
			//log.debug("Encode correcto");
			
		}
		this.setTLVValueLength(labelSet.getLength());
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		System.arraycopy(labelSet.getBytes(), 0, this.tlv_bytes, 4, labelSet.getLength());

	}

	public void decode()throws MalformedOSPFSubTLVException{		
		int offset=4; //cabecera de OSPFSubTLV
		int type = (int) (((this.getTlv_bytes()[offset]) & 0xF0)>>4);
		
		if (type == LabelSetParameters.InclusiveLabelLists)
			labelSet = new LabelListField(this.getTlv_bytes(),offset);
		else if (type == LabelSetParameters.ExclusiveLabelLists)
			labelSet = new LabelListField(this.getTlv_bytes(),offset);
		else if (type == LabelSetParameters.InclusiveLabelRanges)
			labelSet = new LabelRangeField(this.getTlv_bytes(),offset);
		else if (type == LabelSetParameters.ExclusiveLabelRanges)
			labelSet = new LabelRangeField(this.getTlv_bytes(),offset);
		else if (type == LabelSetParameters.BitmapLabelSet){
			labelSet = new BitmapLabelSet(this.getTlv_bytes(),offset);
			
		}
		
	}

	public LabelSetField getLabelSet() {
		return labelSet;
	}

	public void setLabelSet(LabelSetField labelSet) {
		this.labelSet = labelSet;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((labelSet == null) ? 0 : labelSet.hashCode());
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
		AvailableLabels other = (AvailableLabels) obj;
		if (labelSet == null) {
			if (other.labelSet != null)
				return false;
		} else if (!labelSet.equals(other.labelSet))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return labelSet.toString();
	}
	
	public AvailableLabels dublicate(){
		AvailableLabels al = new AvailableLabels();
		if (this.getLabelSet()!=null) {
			al.setLabelSet(this.getLabelSet().duplicate());
		}	
		return al;
	}

	
}
