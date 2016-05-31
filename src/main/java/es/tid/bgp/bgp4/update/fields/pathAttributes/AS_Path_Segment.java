package es.tid.bgp.bgp4.update.fields.pathAttributes;

import es.tid.bgp.bgp4.BGP4Element;
import es.tid.bgp.bgp4.update.MalformedBGP4ElementException;

import java.util.Arrays;

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
 * 1 AS_SET: unordered set of ASes a route in the
 * UPDATE message has traversed
 * <p>
 * 2 AS_SEQUENCE: ordered set of ASes a route in
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
 * @author Jose-Juan Pedreno-Manresa
 */
public class AS_Path_Segment implements BGP4Element
{
	private int  type;
	private int  numberOfSegments;
	private int  segments[];
	private byte bytes[];
	private int  length;

	public AS_Path_Segment()
	{
		type = PathAttributesTypeCode.PATH_ATTRIBUTE_ASPATH_AS_SEQUENCE;
	} //Type and number, and nothing else;

	public AS_Path_Segment(byte[] bytes, int offset) throws MalformedBGP4ElementException
	{
		
		this.type = (int) bytes[offset] & 0xFF;
		if(this.type != PathAttributesTypeCode.PATH_ATTRIBUTE_ASPATH_AS_SEQUENCE && this.type != PathAttributesTypeCode.PATH_ATTRIBUTE_ASPATH_AS_SET)
			throw new MalformedBGP4ElementException();

		this.numberOfSegments = bytes[offset + 1] & 0xFF;
		this.segments = new int[this.numberOfSegments];

		for(int i = 0; i < this.numberOfSegments; i++)
		{
			this.segments[i] = (int) (((bytes[offset + 2 + (i * 2)] & 0xFF) << 8) & 0xFF00 | bytes[offset + 2 + (i * 2) + 1] & 0xFF);
		}

		/*
		 * Type 1 octet
		 * Number of AS segments: 1 octet
		 * Segments: 2 octets each one
		 * Total length = 2 + numberOfSegments*2 octets
		 */
		this.length = 2 + this.numberOfSegments * 2;
	}

	@Override
	public void encode()
	{
		this.length = 2 + numberOfSegments * 2;
		
		int offset = 0;
		bytes = new byte[this.length];
		bytes[offset++] = (byte) (type & 0xFF); //1 octet, TYPE of AS_PATH_SEGMENT
		bytes[offset++] = (byte) (numberOfSegments & 0xFF); //1 octet, NUMBER of ASes

		for(int i = 0; i < numberOfSegments; i++)
		{
			bytes[offset++] = (byte) ((segments[i] & 0xFF00 >> 8) & 0xFF);
			bytes[offset++] = (byte) (segments[i] & 0xFF);
		}

	}

	@Override
	public byte[] getBytes()
	{
		if(bytes == null) encode();
		return bytes;
	}

	public int getLength()
	{
		return length;
	}

	public int getType()
	{
		return type;
	}

	public int getNumberOfSegments()
	{
		return numberOfSegments;
	}

	public int[] getSegments(){ return segments; }

	public void setSegments(int[] segments)
	{
		if(segments == null) this.segments = new int[0];
		else
		{
			this.segments = segments;
			numberOfSegments = this.segments.length;
		}
	}

	@Override
	public String toString(){ return "AS_PATH_SEGMENT [Type=" + type + " NumberOfSegments=" + numberOfSegments + " Total Length=" + length+"]"; }

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AS_Path_Segment other = (AS_Path_Segment) obj;
		if (! Arrays.equals(bytes, other.getBytes()))
			return false;
		if (length != other.getLength())
			return false;
		return true;
	}
}
