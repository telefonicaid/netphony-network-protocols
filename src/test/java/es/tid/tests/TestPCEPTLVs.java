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

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.protocol.commons.ByteHandler;

@RunWith(org.junit.runners.Parameterized.class)
public class TestPCEPTLVs {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
    			{"es.tid.pce.pcep.objects.tlvs.NoPathTLV"},//Type 1
    			//{"es.tid.pce.pcep.objects.tlvs.OverloadedDurationTLV"},//Type 2: FIXME: Not Implemented
    			{"es.tid.pce.pcep.objects.tlvs.ReqMissingTLV"},//Type 3: REQ-MISSING TLV
    			{"es.tid.pce.pcep.objects.tlvs.OF_LIST_TLV"}, //Type 4: OF-LIST TLV
    			//{"es.tid.pce.pcep.objects.tlvs.Order TLV"}, Type 5: Order TLV FIXME: Not implemented
    			//{"es.tid.pce.pcep.objects.tlvs.P2MPCapable"}, //Type 6: P2MP capable FIXME: Not implemented
    			//{"es.tid.pce.pcep.objects.tlvs.VendorInformationTLV"}, //Type 7: VENDOR-INFORMATION-TLV FIXME: Not implemented
    			//{"es.tid.pce.pcep.objects.tlvs.WavelengthSelectionTLV	"},//Type 8: Wavelength Selection TLV FIXME: Not implemented
    			//Type 9: Wavelength Restriction FIXME: Not implemented
    			//Type 10: Wavelength Allocation FIXME: Not implemented
    			//Type 11: Optical Interface Class List FIXME: Not implemented
    			//Type 12: Client Signal Information FIXME: Not implemented
    			//Type 13: H-PCE Capability FIXME: Not implemented
    			{"es.tid.pce.pcep.objects.tlvs.DomainIDTLV"},//Type 14: Domain ID TLV FIXME: Draft version implemented RECHECK!!
    			//Type 15: H-PCE-FLAG FIXME: Not implemented
    			{"es.tid.pce.pcep.objects.tlvs.StatefulCapabilityTLV"},//Type 16: STATEFUL-PCE-CAPABILITY FIXME: IMPLEMENT MORE FLAGS
    			{"es.tid.pce.pcep.objects.tlvs.ASSOCTypeListTLV"},//Type 35
//    			{"es.tid.pce.pcep.objects.BandwidthExistingLSP"},
//    			{"es.tid.pce.pcep.objects.BandwidthExistingLSPGeneralizedBandwidth"},
//    			{"es.tid.pce.pcep.objects.BandwidthRequested"},
//    			{"es.tid.pce.pcep.objects.BandwidthRequestedGeneralizedBandwidth"},
//    			{"es.tid.pce.pcep.objects.BitmapLabelSet"},
//    			{"es.tid.pce.pcep.objects.Close"},
//    			{"es.tid.pce.pcep.objects.EndPointDataPathID"},
//    			{"es.tid.pce.pcep.objects.EndPointsIPv4"},
//    			{"es.tid.pce.pcep.objects.EndPointsIPv6"},
//    			{"es.tid.pce.pcep.objects.EndPointsUnnumberedIntf"},
//    			{"es.tid.pce.pcep.objects.ExcludeRouteObject"},
//    			{"es.tid.pce.pcep.objects.ExplicitRouteObject"},
//				//"es.tid.pce.pcep.objects.GeneralizedEndPoints",
//    			{"es.tid.pce.pcep.objects.IncludeRouteObject"},
//    			{"es.tid.pce.pcep.objects.InterLayer"},
//    			{"es.tid.pce.pcep.objects.LSP"},
//    			{"es.tid.pce.pcep.objects.LSPA"},
//    			{"es.tid.pce.pcep.objects.LabelSetInclusiveList"},
//    			{"es.tid.pce.pcep.objects.LoadBalancing"},
//    			{"es.tid.pce.pcep.objects.Metric"},
//    			{"es.tid.pce.pcep.objects.Monitoring"},
//    			{"es.tid.pce.pcep.objects.NoPath"},
//    			//{"es.tid.pce.pcep.objects.Notification"},
//    			{"es.tid.pce.pcep.objects.OPEN"},
//    			{"es.tid.pce.pcep.objects.ObjectiveFunction"},
//    			{"es.tid.pce.pcep.objects.P2MPEndPointsIPv4"},
//    			{"es.tid.pce.pcep.objects.PCEPErrorObject"},
//    			{"es.tid.pce.pcep.objects.PccReqId"},
//    			{"es.tid.pce.pcep.objects.PceIdIPv4"},
//    			{"es.tid.pce.pcep.objects.ProcTime"},
//    			{"es.tid.pce.pcep.objects.ReportedRouteObject"},
//    			{"es.tid.pce.pcep.objects.ReqAdapCap"},
//    			{"es.tid.pce.pcep.objects.RequestParameters"},
//    			{"es.tid.pce.pcep.objects.Reservation"},
//				{"es.tid.pce.pcep.objects.ReservationConf"},
//    			{"es.tid.pce.pcep.objects.SRP"},
//    			{"es.tid.pce.pcep.objects.ServerIndication"},
//    			{"es.tid.pce.pcep.objects.SuggestedLabel"},
//    			{"es.tid.pce.pcep.objects.Svec"},
//    			{"es.tid.pce.pcep.objects.SwitchLayer"},
//    			{"es.tid.pce.pcep.objects.WavelengthAssignementObject"},
//    			{"es.tid.pce.pcep.objects.UnknownObject"},
				};
		return Arrays.asList(objects);
    }
	
    
    public TestPCEPTLVs (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("Testing PCEP TLV "+object);
    	Class objectClass=Class.forName(object);
    	PCEPTLV object1 = (PCEPTLV)objectClass.newInstance();
		TestCommons.createAllFields(object1,true);
		object1.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getTlv_bytes()));
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		PCEPTLV object2 = (PCEPTLV) ctor.newInstance(object1.getTlv_bytes(),0);
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
		object2 = (PCEPTLV) ctor.newInstance(object1.getTlv_bytes(),0);
		object2.encode();
		assertTrue("testing PCEP TLV changing values"+objectClass,object1.equals(object2));
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
