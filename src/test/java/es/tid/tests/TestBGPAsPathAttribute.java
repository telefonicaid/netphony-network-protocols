package es.tid.tests;

import es.tid.bgp.bgp4.update.fields.pathAttributes.AS_Path_Attribute;
import es.tid.bgp.bgp4.update.fields.pathAttributes.AS_Path_Segment;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class TestBGPAsPathAttribute
{

	@Test
	public void testAsPathSegment()
	{
		try
		{
			AS_Path_Segment as1 = new AS_Path_Segment();
			as1.encode();
			byte[] as1Bytes = as1.getBytes();

			AS_Path_Segment as2 = new AS_Path_Segment(as1Bytes, 0);

			Assert.assertEquals("1-Both objects should be equal", as1, as2);
			Assert.assertArrayEquals("1-Bytes from both objects should be the same", as1.getBytes(), as2.getBytes());

			int segments[] = {1, 2, 3};

			as1.setSegments(segments);
			as1.encode();

			as2 = new AS_Path_Segment(as1.getBytes(), 0);

			Assert.assertEquals("2-Both objects should be equal", as1, as2);
			Assert.assertArrayEquals("2-Bytes from both objects should be the same", as1.getBytes(), as2.getBytes());

		}catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail("Exception is thrown");
		}
	}

	@Test
	public void testAsPathAttribute()
	{
		AS_Path_Attribute ap1 = new AS_Path_Attribute();
		AS_Path_Segment as1 = new AS_Path_Segment();
		int segments[] = {1, 2, 3};
		as1.setSegments(segments);
		LinkedList<AS_Path_Segment> asList = new LinkedList<AS_Path_Segment>();
		asList.add(as1);
		ap1.setAsPathSegments(asList);
		ap1.encode();

		AS_Path_Attribute ap2 = new AS_Path_Attribute(ap1.getBytes(), 0);
		ap2.encode();
		System.out.println("AP1 "+ap1.toString());
		System.out.println("AP2 "+ap2.toString());

		Assert.assertEquals("Both PathAttribute_AS_PATH should be equal", ap1, ap2);
		Assert.assertArrayEquals("Bytes from both PathAttribute_AS_PATH objects should be the same", ap1.getBytes(), ap2.getBytes());

	}
}
