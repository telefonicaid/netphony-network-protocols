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

import es.tid.ospf.ospfv2.lsa.tlv.subtlv.OSPFSubTLV;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.protocol.commons.ByteHandler;

@RunWith(org.junit.runners.Parameterized.class)
public class TestOSPFv2SubTLVs {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
    			// Open Shortest Path First (OSPF) Traffic Engineering TLVs
    			//https://www.iana.org/assignments/ospf-traffic-eng-tlvs/ospf-traffic-eng-tlvs.xhtml
    			//Types for sub-TLVs of TE Link TLV (Value 2)
    			{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.LinkType"},//Type 1 	Link Type (4 octets)	[RFC3630]
    			{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.LinkID"},//Type 2 	Link ID (4 octets)	[RFC3630]
    			{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.LocalInterfaceIPAddress"},//Type 3 	Local interface IP address (4 octets) (4 octets)	[RFC3630]
    			{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.RemoteInterfaceIPAddress"},//Type 4 Remote interface IP address (4 octets) (4 octets)	[RFC3630]
    			{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.TrafficEngineeringMetric"},//5	Traffic engineering metric (4 octets)	[RFC3630]
    			{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.MaximumBandwidth"},//6	Maximum bandwidth (4 octets)	[RFC3630]
    			{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.MaximumReservableBandwidth"},//7	Maximum reservable bandwidth (4 octets)	[RFC3630]
    			//{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.UnreservedBandwidth"},//8	Unreserved bandwidth (32 octets)	[RFC3630]
    			//FIXME: Need to add test with float[]
    			//LinkProtectionType FIXME: NOT implemented
    			{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.AdministrativeGroup"},//Type 9 Administrative group (4 octets)	[RFC3630]
    			{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.SharedRiskLinkGroup"},//Type 16	Shared Risk Link Group (variable)	[RFC4203]
    			//{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.InterfaceSwitchingCapabilityDescriptor"},//Type 15	Interface Switching Capability Descriptor (variable)	[RFC4203]
    			//FIXME: Need to add test with max_LSP_BW
    			{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.RemoteASNumber"},// Type 21	Remote AS Number sub-TLV	[RFC5392]
    			{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.IPv4RemoteASBRID"},//Type 22	IPv4 Remote ASBR ID sub-TLV	[RFC5392]    			
    			//Types for sub-TLVs of WSON-LSC SCSI (Switching Capability Specific Information)
    			//{"es.tid.ospf.ospfv2.lsa.tlv.subtlv.AvailableLabels"},//Type 1200 FIXME: Non-standard value used   FIXME: Add specific test			

				};
		return Arrays.asList(objects);
    }
	
    
    public TestOSPFv2SubTLVs (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("Testing OSPF LSA SubTLV  "+object);
    	Class objectClass=Class.forName(object);
    	OSPFSubTLV object1 = (OSPFSubTLV)objectClass.newInstance();
		TestCommons.createAllFields(object1,true);
		object1.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getTlv_bytes()));
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		OSPFSubTLV object2 = (OSPFSubTLV) ctor.newInstance(object1.getTlv_bytes(),0);
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getTlv_bytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getTlv_bytes()));
		//Check toString output
		object1.toString();
		//Check all the gets
		TestCommons.testGets(object1);
		//Check if the fields are the same
		assertTrue("testing PCEP TLV  "+objectClass,object1.equals(object2));
		
		//Check equals 
		//testEquals(object1, object2);
		//Check hashcode
//		object1.hashCode();
		//Test with boolean false
		TestCommons.createAllFields(object1,false);
		object1.encode();
		object2 = (OSPFSubTLV) ctor.newInstance(object1.getTlv_bytes(),0);
		object2.encode();
		assertTrue("testing OSPF SubTLV changing values"+objectClass,object1.equals(object2));
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
