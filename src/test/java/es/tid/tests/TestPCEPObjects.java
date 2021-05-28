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
import es.tid.protocol.commons.ByteHandler;

@RunWith(org.junit.runners.Parameterized.class)
public class TestPCEPObjects {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
    			{"es.tid.pce.pcep.objects.AssociationIPv4"},
    			{"es.tid.pce.pcep.objects.AssociationIPv6"},
    			{"es.tid.pce.pcep.objects.BandwidthExistingLSP"},
    			{"es.tid.pce.pcep.objects.BandwidthExistingLSPGeneralizedBandwidth"},
    			{"es.tid.pce.pcep.objects.BandwidthRequested"},
    			{"es.tid.pce.pcep.objects.BandwidthRequestedGeneralizedBandwidth"},
    			{"es.tid.pce.pcep.objects.BitmapLabelSet"},
    			{"es.tid.pce.pcep.objects.Close"},
    			{"es.tid.pce.pcep.objects.EndPointDataPathID"},
    			{"es.tid.pce.pcep.objects.EndPointsIPv4"},
    			{"es.tid.pce.pcep.objects.EndPointsIPv6"},
    			{"es.tid.pce.pcep.objects.EndPointsUnnumberedIntf"},
    			{"es.tid.pce.pcep.objects.ExcludeRouteObject"},
    			{"es.tid.pce.pcep.objects.ExplicitRouteObject"},    			
    			{"es.tid.pce.pcep.objects.IncludeRouteObject"},
    			{"es.tid.pce.pcep.objects.InterLayer"},
    			{"es.tid.pce.pcep.objects.LSP"},
    			{"es.tid.pce.pcep.objects.LSPA"},
    			{"es.tid.pce.pcep.objects.LabelSetInclusiveList"},
    			{"es.tid.pce.pcep.objects.LoadBalancing"},
    			{"es.tid.pce.pcep.objects.Metric"},
    			{"es.tid.pce.pcep.objects.Monitoring"},
    			{"es.tid.pce.pcep.objects.NoPath"},
    			//{"es.tid.pce.pcep.objects.Notification"},
    			{"es.tid.pce.pcep.objects.OPEN"},
    			{"es.tid.pce.pcep.objects.ObjectiveFunction"},
    			{"es.tid.pce.pcep.objects.P2MPEndPointsIPv4"},
    			{"es.tid.pce.pcep.objects.P2MPGeneralizedEndPoints"},
    			{"es.tid.pce.pcep.objects.P2PGeneralizedEndPoints"},
    			{"es.tid.pce.pcep.objects.PCEPErrorObject"},
    			{"es.tid.pce.pcep.objects.PccReqId"},
    			{"es.tid.pce.pcep.objects.PceIdIPv4"},
    			{"es.tid.pce.pcep.objects.ProcTime"},
    			{"es.tid.pce.pcep.objects.ReportedRouteObject"},
    			{"es.tid.pce.pcep.objects.ReqAdapCap"},
    			{"es.tid.pce.pcep.objects.RequestParameters"},
    			{"es.tid.pce.pcep.objects.Reservation"},
				{"es.tid.pce.pcep.objects.ReservationConf"},
    			{"es.tid.pce.pcep.objects.SRP"},
    			{"es.tid.pce.pcep.objects.ServerIndication"},
    			{"es.tid.pce.pcep.objects.SuggestedLabel"},
    			{"es.tid.pce.pcep.objects.Svec"},
    			{"es.tid.pce.pcep.objects.SwitchLayer"},
    			{"es.tid.pce.pcep.objects.WavelengthAssignementObject"},
    			{"es.tid.pce.pcep.objects.UnknownObject"},
				};
		return Arrays.asList(objects);
    }
	
    
    public TestPCEPObjects (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("Testing PCEP Object "+object);
    	Class objectClass=Class.forName(object);
		PCEPObject object1 = (PCEPObject)objectClass.newInstance();
		TestCommons.createAllFields(object1,true);
		object1.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		PCEPObject object2 = (PCEPObject) ctor.newInstance(object1.getBytes(),0);
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
		//Check toString output
		object1.toString();
		//Check all the gets
		TestCommons.testGets(object1);
		//Check if the fields are the same
		assertTrue("testing PCEP object 1 "+objectClass,object1.equals(object2));
		
		//Check equals 
		testEquals(object1, object2);
		//Check hashcode
		object1.hashCode();
		//Test with boolean false
		TestCommons.createAllFields(object1,false);
		object1.encode();
		object2 = (PCEPObject) ctor.newInstance(object1.getBytes(),0);
		object2.encode();
		assertTrue("testing PCEP object 2"+objectClass,object1.equals(object2));
		//Check equals with false boolean
		testEquals(object1, object2);
		//Test Bad object
		//Check hashcode
		object1.hashCode();
		try {
			byte[] bitos = new byte[4];
			bitos[3]=0x04;
			PCEPObject object3 = (PCEPObject) ctor.newInstance(bitos,0);
			
		} catch (Exception e) {
			System.out.println("Testing malformed object ok");
		}
			
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
					//System.out.println("XXXXXXXXXXXXXXXXXClass name: "+c.getName()); 
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
