package es.tid.tests;

import es.tid.bgp.bgp4.update.fields.LinkStateNLRI;
import es.tid.bgp.bgp4.update.fields.NodeNLRI;
import es.tid.bgp.bgp4.update.fields.pathAttributes.BGP_LS_MP_Reach_Attribute;
import es.tid.bgp.bgp4.update.fields.pathAttributes.Generic_MP_Reach_Attribute;
import es.tid.bgp.bgp4.update.fields.pathAttributes.MP_Reach_Attribute;
import es.tid.bgp.bgp4.update.tlv.LocalNodeDescriptorsTLV;
import es.tid.bgp.bgp4.update.tlv.ProtocolIDCodes;
import es.tid.bgp.bgp4.update.tlv.RoutingUniverseIdentifierTypes;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.IGPRouterIDNodeDescriptorSubTLV;
import org.junit.Assert;
import org.junit.Test;

import java.net.Inet4Address;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

	@Test
	public void testBGPLSMPReachAttribute()
	{
		try
		{
			BGP_LS_MP_Reach_Attribute bgplsmpr1 = new BGP_LS_MP_Reach_Attribute();
			List<LinkStateNLRI> lsNLRIList = new LinkedList<LinkStateNLRI>();

			Inet4Address nodeIPAddress = (Inet4Address) Inet4Address.getByName("10.0.0.1");
			IGPRouterIDNodeDescriptorSubTLV igpRouterIDNodeDescriptorSubTLV = new IGPRouterIDNodeDescriptorSubTLV();
			igpRouterIDNodeDescriptorSubTLV.setIpv4AddressOSPF(nodeIPAddress);
			igpRouterIDNodeDescriptorSubTLV.setIGP_router_id_type(IGPRouterIDNodeDescriptorSubTLV.IGP_ROUTER_ID_TYPE_OSPF_NON_PSEUDO);
			LocalNodeDescriptorsTLV lnd = new LocalNodeDescriptorsTLV();
			lnd.setIGPRouterID(igpRouterIDNodeDescriptorSubTLV);

			NodeNLRI nlri1 = new NodeNLRI();
			nlri1.setProtocolID(ProtocolIDCodes.OSPF_Protocol_ID);
			nlri1.setLocalNodeDescriptors(lnd);
			nlri1.setRoutingUniverseIdentifier(RoutingUniverseIdentifierTypes.Level3Identifier);
			nlri1.encode();

			lsNLRIList.add(nlri1);

			NodeNLRI nlri2 = new NodeNLRI(nlri1.getBytes(), 0);
			nlri2.encode();
			lsNLRIList.add(nlri2);

			bgplsmpr1.setLsNLRIList(lsNLRIList);
			bgplsmpr1.encode();

			BGP_LS_MP_Reach_Attribute bgplsmpr2 = new BGP_LS_MP_Reach_Attribute(bgplsmpr1.getBytes(), 0);

			Assert.assertEquals("Both object should be equal", bgplsmpr1, bgplsmpr2);
			Assert.assertArrayEquals("Bytes from both objetcs should be the same", bgplsmpr1.getBytes(), bgplsmpr2.getBytes());
		}catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail();
		}
	}

}
