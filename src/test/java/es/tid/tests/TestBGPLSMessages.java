package es.tid.tests;

import es.tid.bgp.bgp4.update.fields.pathAttributes.Generic_MP_Reach_Attribute;
import es.tid.bgp.bgp4.update.fields.pathAttributes.MP_Reach_Attribute;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestBGPLSMessages
{

	@org.junit.Test
	public void testBGPLSOpen()
	{

	}

	@Test
	public void testMPReach()
	{
		MP_Reach_Attribute mp1 = new Generic_MP_Reach_Attribute();
		mp1.encode();
		byte[] bytesMp1 = mp1.getBytes();

		MP_Reach_Attribute mp2 = new Generic_MP_Reach_Attribute(bytesMp1, 0);
		byte[] bytesMp2 = mp2.getBytes();

		Assert.assertTrue("Bytes from both messages should be equal", Arrays.equals(bytesMp1, bytesMp2));
		Assert.assertEquals("Both MP_Reach_Attribute objects should be equal", mp1, mp2);
	}

}
