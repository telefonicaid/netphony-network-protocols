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
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IS_IS_AreaIdentifierNodeAttribTLV"},//Type 1027 IS-IS Area Identifier [RFC7752, Section 3.3.1.2]
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IPv4RouterIDLocalNodeLinkAttribTLV"}, //Type 1028	IPv4 Router-ID of Local Node	[RFC5305, Section 4.3]
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IPv4RouterIDLocalNodeNodeAttribTLV"}, //FIXME: Now it is duplicated for node and link
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
//    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.UnreservedBandwidthLinkAttribTLV"},// Type 1091	Unreserved bandwidth	[RFC5305, Section 3.6]//FIXME: Rellenar el array
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.DefaultTEMetricLinkAttribTLV"},// Type 1092	TE Default Metric	22/18	[RFC7752, Section 3.3.2.3]
//     			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.LinkProtectionTypeLinkAttribTLV"}, 1093	Link Protection Type	22/20	[RFC5307, Section 1.2] //TODO: Not implemented
//    					1094	MPLS Protocol Mask		[RFC7752, Section 3.3.2.2] //TODO: Not implemented
//    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.MetricLinkAttribTLV"},// 1095	IGP Metric		[RFC7752, Section 3.3.2.4] //TODO: Encode Not implemented
    			{"es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.SharedRiskLinkGroupAttribTLV"},// Type 1096	Shared Risk Link Group		[RFC7752, Section 3.3.2.5]
//    					1097	Opaque Link Attribute		[RFC7752, Section 3.3.2.6]
//    					1098	Link Name		[RFC7752, Section 3.3.2.7]
//    					1099	Adjacency SID		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.2.1]
//    					1100	LAN Adjacency SID		[RFC-ietf-idr-bgp-ls-segment-routing-ext-16, Section 2.2.2]
//    					1101	PeerNode SID		[RFC-ietf-idr-bgpls-segment-routing-epe-19]
//    					1102	PeerAdj SID		[RFC-ietf-idr-bgpls-segment-routing-epe-19]
//    					1103	PeerSet SID		[RFC-ietf-idr-bgpls-segment-routing-epe-19]
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
		TestCommons.createAllFields(object1,true);
		object1.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getTlv_bytes()));
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		BGP4TLVFormat object2 = (BGP4TLVFormat) ctor.newInstance(object1.getTlv_bytes(),0);
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getTlv_bytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getTlv_bytes()));
		//Check toString output
		object1.toString();
		//Check all the gets
		TestCommons.testGets(object1);
		//Check if the fields are the same
		assertTrue("testing BGP-LS TLV  "+objectClass,object1.equals(object2));
		
		//Check equals 
		//testEquals(object1, object2);
		//Check hashcode
//		object1.hashCode();
		//Test with boolean false
		TestCommons.createAllFields(object1,false);
		object1.encode();
		object2 = (BGP4TLVFormat) ctor.newInstance(object1.getTlv_bytes(),0);
		object2.encode();
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
