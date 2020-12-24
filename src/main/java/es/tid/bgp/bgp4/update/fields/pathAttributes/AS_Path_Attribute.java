package es.tid.bgp.bgp4.update.fields.pathAttributes;

import es.tid.bgp.bgp4.update.MalformedBGP4ElementException;
import es.tid.bgp.bgp4.update.fields.PathAttribute;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * AS_PATH (Type Code 2):
 * <p>
 * AS_PATH is a well-known mandatory attribute that is composed
 * of a sequence of AS path segments.  Each AS path segment is
 * represented by a triple{@code <path segment type, path segment
 * length, path segment value>}.
 * <p>
 * The path segment type is a 1-octet length field with the
 * following values defined:
 * <p>
 * Value      Segment Type
 * <p>
 * 1         AS_SET: unordered set of ASes a route in the
 * UPDATE message has traversed
 * <p>
 * 2         AS_SEQUENCE: ordered set of ASes a route in
 * the UPDATE message has traversed
 * <p>
 * The path segment length is a 1-octet length field,
 * containing the number of ASes (not the number of octets) in
 * the path segment value field.
 * <p>
 * The path segment value field contains one or more AS
 * numbers, each encoded as a 2-octet length field.
 * <p>
 * Usage of this attribute is defined in 5.1.2.
 *
 * @author mcs
 */
public class AS_Path_Attribute extends PathAttribute
{
	private LinkedList<AS_Path_Segment> asPathSegments;

	public AS_Path_Attribute()
	{
		super();
		//Poner los flags. 
		this.typeCode = PathAttributesTypeCode.PATH_ATTRIBUTE_TYPECODE_ASPATH;
		asPathSegments = new LinkedList<AS_Path_Segment>();
	}

	public AS_Path_Attribute(byte[] bytes, int offset) throws MalformedBGP4ElementException
	{
		super(bytes, offset);
		asPathSegments = new LinkedList<AS_Path_Segment>();
		decode();
	}

	@Override
	public void encode()
	{
		int path_attribute_length=0;
		
		for(AS_Path_Segment asPathSegment : asPathSegments)
		{
			asPathSegment.encode();
			path_attribute_length+=asPathSegment.getLength();
		}
		this.setPathAttributeLength(path_attribute_length);
		bytes = new byte[length];
		encodeHeader();
		int offset = this.mandatoryLength; //After the header encoding
		int num_segs=asPathSegments.size();
		for(int i=0;i<num_segs;++i)
		{
			System.arraycopy(asPathSegments.get(i).getBytes(), 0, bytes, offset, asPathSegments.get(i).getLength());
			offset += asPathSegments.get(i).getLength();
		}

	}

	public void decode() throws MalformedBGP4ElementException
	{
		if(typeCode != PathAttributesTypeCode.PATH_ATTRIBUTE_TYPECODE_ASPATH)
			throw new MalformedBGP4ElementException();
		
		int offset = this.mandatoryLength; 
		while(offset < length)
		{
			AS_Path_Segment asPathSegment = new AS_Path_Segment(bytes, offset);
			asPathSegments.add(asPathSegment);
			offset += asPathSegment.getLength();
		}
	}

	public int getType()
	{
		return typeCode;
	}

	public List<AS_Path_Segment> getAsPathSegments(){ return asPathSegments; }

	public void setAsPathSegments(LinkedList<AS_Path_Segment> asPathSegments)
	{
		this.asPathSegments = asPathSegments;
	}

	@Override
	public String toString()
	{
		String ret="";
		ret+="AS_PATH [Type=" + typeCode + " Length=" + length + " NumberOfAsPathSegments=" + asPathSegments.size() + "]";
		for (int i=0; i<asPathSegments.size();++i){
			ret+=asPathSegments.toString();
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		AS_Path_Attribute other = (AS_Path_Attribute) obj;
		if(! Arrays.equals(bytes, other.getBytes()))
			return false;
		if(length != other.getLength())
			return false;
		return true;

	}

}
