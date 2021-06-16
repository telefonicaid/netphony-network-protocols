package es.tid.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
//import static org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.protocol.commons.ByteHandler;

@RunWith(org.junit.runners.Parameterized.class)
public class TestBGP_LS_TLVs {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
    			//https://www.iana.org/assignments/bgp-ls-parameters/bgp-ls-parameters.xhtml#node-descriptor-link-descriptor-prefix-descriptor-attribute-tlv    			
    			{"es.tid.bgp.bgp4.update.tlv.LocalNodeDescriptorsTLV"},//Type 256 Local Node Descriptors
    			{"es.tid.bgp.bgp4.update.tlv.RemoteNodeDescriptorsTLV"},// Type 257 Remote Node Descriptors
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.NodeFlagBitsNodeAttribTLV"},// 1024	Node Flag Bits		[RFC7752, Section 3.3.1.1]
//    			258	Link Local/Remote Identifiers	22/4	[RFC5307, Section 1.1]
//    					259	IPv4 interface address	22/6	[RFC5305, Section 3.2]
//    					260	IPv4 neighbor address	22/8	[RFC5305, Section 3.3]
//    					261	IPv6 interface address	22/12	[RFC6119, Section 4.2]
//    					262	IPv6 neighbor address	22/13	[RFC6119, Section 4.3]
//    					263	Multi-Topology ID		[RFC7752, Section 3.2.1.5]
//    					264	OSPF Route Type		[RFC7752, Section 3.2.3]
//    					265	IP Reachability Information		[RFC7752, Section 3.2.3]
//    					266	Node MSD	242/23	[RFC8814]
//    					267	Link MSD	(22,23,25,141,222,223)/15	[RFC8814]
//    					268-511	Unassigned		
//    					512	Autonomous System		[RFC7752, Section 3.2.1.4]
//    					513	BGP-LS Identifier		[RFC7752, Section 3.2.1.4]
//    					514	OSPF Area-ID		[RFC7752, Section 3.2.1.4]
//    					515	IGP Router-ID		[RFC7752, Section 3.2.1.4]
//    					516	BGP Router-ID		[RFC-ietf-idr-bgpls-segment-routing-epe-19]
//    					517	BGP Confederation Member		[RFC-ietf-idr-bgpls-segment-routing-epe-19]
//    					518	SRv6 SID Information TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgpls-srv6-ext]
//    					519-549	Unassigned		
//    					550	Tunnel ID TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    					551	LSP ID TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    					552	IPv4/6 Tunnel Head-end address TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    					553	IPv4/6 Tunnel Tail-end address TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    					554	SR Policy CP Descriptor TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    					555	MPLS Local Cross Connect TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    					556	MPLS Cross Connect Interface TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    					557	MPLS Cross Connect FEC TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]    			
    			//1025	Opaque Node Attribute		[RFC7752, Section 3.3.1.5]
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.NodeNameNodeAttribTLV"},//	1026	Node Name	variable	[RFC7752, Section 3.3.1.3]
    			//		1027	IS-IS Area Identifier	variable	[RFC7752, Section 3.3.1.2]
    			//		1028	IPv4 Router-ID of Local Node	134/---	[RFC5305, Section 4.3]
    			//		1029	IPv6 Router-ID of Local Node	140/---	[RFC6119, Section 4.1]
    			//		1030	IPv4 Router-ID of Remote Node	134/---	[RFC5305, Section 4.3]
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IS_IS_AreaIdentifierNodeAttribTLV"},//Type 1027 IS-IS Area Identifier [RFC7752, Section 3.3.1.2]
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IPv4RouterIDLocalNodeLinkAttribTLV"}, //Type 1028	IPv4 Router-ID of Local Node	[RFC5305, Section 4.3]
    			//1029	IPv6 Router-ID of Local Node	140/---	[RFC6119, Section 4.1] //TODO: Not implemented
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IPv4RouterIDRemoteNodeLinkAttribTLV"}, //Type 1030 IPv4 Router-ID of Remote Node	[RFC5305, Section 4.3]	[RFC5305, Section 4.3]
    			//1031	IPv6 Router-ID of Remote Node	140/---	[RFC6119, Section 4.1] //TODO: Not implmented
    			//1032	S-BFD Discriminators TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-30, expires 2021-08-06)		[draft-ietf-idr-bgp-ls-sbfd-extensions] //TODO: Not implemented
    			//1034	SR Capabilities		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.1.2]
    			//	1035	SR Algorithm		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.1.3]
    			//	1036	SR Local Block		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.1.4]
    			//	1037	SRMS Preference		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.1.5]
    			//		1038	SRv6 Capabilities TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgpls-srv6-ext] //TODO: Not implemented
    			//		1039	Flex Algorithm Definition TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgp-ls-flex-algo] //TODO: Not implemented
    			//		1040	Flex Algo Exclude Any Affinity sub-TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgp-ls-flex-algo] //TODO: Not implemented
    			//		1041	Flex Algo Include Any Affinity sub-TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgp-ls-flex-algo] //TODO: Not implemented
    			//		1042	Flex Algo Include All Affinity sub-TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgp-ls-flex-algo] //TODO: Not implemented
    			//		1043	Flex Algo Definition Flags sub-TLV (TEMPORARY - registered 2019-08-19, extension registered 2020-06-25, expires 2021-08-19)		[draft-ietf-idr-bgp-ls-flex-algo] //TODO: Not implemented
    			//		1044	Flex Algorithm Prefix Metric TLV (TEMPORARY - registered 2019-08-19, extension registered 2020-06-25, expires 2021-08-19)		[draft-ietf-idr-bgp-ls-flex-algo] //TODO: Not implemented    			
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.AdministrativeGroupLinkAttribTLV"},// Type 1088	Administrative group (color)	22/3	[RFC5305, Section 3.1]
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.MaximumLinkBandwidthLinkAttribTLV"},// Type 1089	Maximum link bandwidth	[RFC5305, Section 3.4]
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.MaxReservableBandwidthLinkAttribTLV"},// Type 1090	Max. reservable link bandwidth	22/10	[RFC5305, Section 3.5]
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.UnreservedBandwidthLinkAttribTLV"},// Type 1091	Unreserved bandwidth	[RFC5305, Section 3.6]//FIXME: Rellenar el array
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.DefaultTEMetricLinkAttribTLV"},// Type 1092	TE Default Metric	22/18	[RFC7752, Section 3.3.2.3]
     			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.LinkProtectionTypeLinkAttribTLV"}, //1093	Link Protection Type	22/20	[RFC5307, Section 1.2] //TODO: Not implemented
//    					1094	MPLS Protocol Mask		[RFC7752, Section 3.3.2.2] //TODO: Not implemented
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.MetricLinkAttribTLV"},// 1095	IGP Metric		[RFC7752, Section 3.3.2.4] //TODO: Encode Not implemented
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.SharedRiskLinkGroupAttribTLV"},// Type 1096	Shared Risk Link Group		[RFC7752, Section 3.3.2.5]
//    					1097	Opaque Link Attribute		[RFC7752, Section 3.3.2.6]
//    					1098	Link Name		[RFC7752, Section 3.3.2.7]
//    					1099	Adjacency SID		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.2.1]
//    					1100	LAN Adjacency SID		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.2.2]
//    					1101	PeerNode SID		[RFC-ietf-idr-bgpls-segment-routing-epe-19]
//    					1102	PeerAdj SID		[RFC-ietf-idr-bgpls-segment-routing-epe-19]
//    					1103	PeerSet SID		[RFC-ietf-idr-bgpls-segment-routing-epe-19]
//    			1104	Unassigned		
//    			1105	RTM Capability	22/40	[RFC8169]
//    			1106	SRv6 End.X SID TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgpls-srv6-ext]
//    			1107	IS-IS SRv6 LAN End.X SID TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgpls-srv6-ext]
//    			1108	OSPFv3 SRv6 LAN End.X SID TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgpls-srv6-ext]
//    			1109-1113	Unassigned		
//    			1114	Unidirectional Link Delay		[RFC8571]
//    			1115	Min/Max Unidirectional Link Delay		[RFC8571]
//    			1116	Unidirectional Delay Variation		[RFC8571]
//    			1117	Unidirectional Link Loss		[RFC8571]
//    			1118	Unidirectional Residual Bandwidth		[RFC8571]
//    			1119	Unidirectional Available Bandwidth		[RFC8571]
//    			1120	Unidirectional Utilized Bandwidth		[RFC8571]
//    			1121	Graceful-Link-Shutdown TLV		[RFC8379]
//    			1122	Application Specific Link Attributes TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgp-ls-app-specific-attr]
//    			1123-1151	Unassigned		
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IGPFlagBitsPrefixAttribTLV"},//    			1152	IGP Flags		[RFC7752, Section 3.3.3.1]
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.RouteTagPrefixAttribTLV"},//    			1153	IGP Route Tag		[RFC5130]
//    			1154	IGP Extended Route Tag		[RFC5130]
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.PrefixMetricPrefixAttribTLV"},//    			1155	Prefix Metric		[RFC5305]
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.OSPFForwardingAddressPrefixAttribTLV"},//    			1156	OSPF Forwarding Address		[RFC2328]
//    			1157	Opaque Prefix Attribute		[RFC7752, Section 3.3.3.6]
//    			1158	Prefix SID		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.3.1]
//    			1159	Range		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.3.4]
//    			1160	Unassigned		
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.SidLabelNodeAttribTLV"},//    			1161	SID/Label		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.1.1]
    			//    			1162	SRv6 Locator TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgpls-srv6-ext]
//    			1163-1169	Unassigned		
//    			1170	Prefix Attributes Flags		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.3.2]
//    			1171	Source Router-ID		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.3.3]
//    			1172	L2 Bundle Member Attributes		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.2.3]
//    			1173	Extended Administrative Group (TEMPORARY - registered 2018-04-09, extension registered 2021-02-19, expires 2022-04-09)	22/14	[draft-ietf-idr-eag-distribution][RFC7308]
//    			1174-1199	Unassigned		
//    			1200	MPLS-TE Policy State TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1201	SR BSID TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1202	SR CP State TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1203	SR CP Name TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1204	SR CP Constraints TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1205	SR Segment List TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1206	SR Segment sub-TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1207	SR Segment List Metric sub-TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1208	SR Affinity Constraint sub-TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1209	SR SRLG Constraint sub-TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1210	SR Bandwidth Constraint sub-TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1211	SR Disjoint Group Constraint sub-TLV (TEMPORARY - registered 2019-09-17, extension registered 2020-11-18, expires 2021-09-17)		[draft-ietf-idr-te-lsp-distribution-14]
//    			1212-1249	Unassigned		
//    			1250	SRv6 Endpoint Function TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgpls-srv6-ext]
//    			1251	SRv6 BGP Peer Node SID TLV (TEMPORARY - registered 2019-08-06, extension registered 2020-06-25, expires 2021-08-06)		[draft-ietf-idr-bgpls-srv6-ext]
//    			1252	SRv6 SID Structure TLV (TEMPORARY - registered 2019-08-19, extension registered 2020-06-25, expires 2021-08-19)		[draft-ietf-idr-bgpls-srv6-ext]
    			
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.TransceiverClassAndAppAttribTLV"},//PROPRIETARY
				};
		return Arrays.asList(objects);
    }
	
    
    public TestBGP_LS_TLVs (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("Testing BGP-LS TLV "+object);
    	Class objectClass=Class.forName(object);
    	BGP4TLVFormat object1 = (BGP4TLVFormat)objectClass.newInstance();
		TestCommons.createAllFields(object1,0);
		object1.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getTlv_bytes()));
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		BGP4TLVFormat object2 = (BGP4TLVFormat) ctor.newInstance(object1.getTlv_bytes(),0);
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getTlv_bytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getTlv_bytes()));
		//Check toString output
		System.out.println(object1.toString());
		//Check all the gets
		TestCommons.testGets(object1);
		//Check if the fields are the same
		assertTrue("testing BGP-LS TLV  "+objectClass,object1.equals(object2));
		
		//Check equals 
		//testEquals(object1, object2);
		//Check hashcode
//		object1.hashCode();
		//Test with boolean false
		TestCommons.createAllFields(object1,1);
		object1.encode();
		object2 = (BGP4TLVFormat) ctor.newInstance(object1.getTlv_bytes(),0);
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getTlv_bytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getTlv_bytes()));
		System.out.println(object1.toString());
		assertTrue("testing BGP-LS TLV changing values"+objectClass,object1.equals(object2));
//		//Check equals with false boolean
//		testEquals(object1, object2);
		//Test Bad object
		//Check hashcode
//		object1.hashCode();
//		try {
//			byte[] bitos = new byte[4];
//			bitos[3]=0x04;
//			PCEPObject object3 = (PCEPObject) ctor.newInstance(bitos,0);
//			
//		} catch (Exception e) {
//			System.out.println("Testing malformed object ok");
//		}
//			
    	} catch(Exception e){
    		e.printStackTrace();
    		assertTrue("Exception in object "+object,false);
    		
    	}
    }
    
    public void testEquals(PCEPObject object1, PCEPObject object2) {
    	//Test against different class object
    	Integer test1=new Integer(2);
		object1.equals(test1);
    	//Test same object
    	object1.equals(object1);
    	//Test change in parent
    	object2.setObjectClass(object1.getObjectClass()+1);
    	object1.equals(object2);
    	object2.setObjectClass(object1.getObjectClass());
    	//Test changes in fields
		List<Field> fieldListNS = new ArrayList<Field>();
		List<Field> fieldList= Arrays.asList(object1.getClass().getDeclaredFields());
		for (Field field : fieldList) {
			fieldListNS.add(field);
			Type ty=field.getGenericType();
			if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
				if (ty instanceof Class){
					Class c =(Class)ty;
					System.out.println("XXXXXXXXXXXXXXXXXClass name: "+c.getName()); 
					Method method;
					Method methods;
					if (c.isPrimitive()){
					try {
						if (c.getName().equals("boolean")) {
							method = object1.getClass().getMethod("is"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()));
							methods = object2.getClass().getMethod("set"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()),boolean.class);
							if (((boolean)method.invoke(object1,null))==true) {
								methods.invoke(object2,false);
			
							}else {
								methods.invoke(object2,true);
							}
							object1.equals(object2);
							methods.invoke(object2,(boolean)method.invoke(object1,null));
							
						}
						else {
							method = object1.getClass().getMethod("get"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()));
							methods = object2.getClass().getMethod("set"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()),c);
							methods.invoke(object2,77);
							object1.equals(object2);
							methods.invoke(object2,method.invoke(object1,null));
						}
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				}
			}
		}
    	
    }
    
    
}
