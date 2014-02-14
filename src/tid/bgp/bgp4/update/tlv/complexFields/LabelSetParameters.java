package tid.bgp.bgp4.update.tlv.complexFields;
/**
 * Label Set Parameters.
 * 
 * These parameters are used for the Action filed of Label Set Field. They are explained in 
 * Internet-Draft  General Network Element Constraint Encoding    May 2011    
 * 
 * The values are:
 * 	0 - Inclusive List
 *  1 - Exclusive List
 *  2 - Inclusive Range
 *  3 - Exclusive Range
 *  4 - Bitmap Set
 *  
 * @author mcs
 *
 */
public class LabelSetParameters {
	public static final int InclusiveLabelLists=0;
	public static final int ExclusiveLabelLists=1;
	public static final int InclusiveLabelRanges=2;
	public static final int ExclusiveLabelRanges=3;
	public static final int BitmapLabelSet=4;
}
