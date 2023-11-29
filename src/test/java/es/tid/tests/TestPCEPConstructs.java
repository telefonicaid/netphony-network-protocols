package es.tid.tests;

import static org.junit.Assert.assertTrue;

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
import org.junit.runners.Parameterized.Parameters;

import es.tid.pce.pcep.constructs.PCEPConstruct;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.protocol.commons.ByteHandler;

@RunWith(org.junit.runners.Parameterized.class)
public class TestPCEPConstructs {
	
	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
//    			{"es.tid.pce.pcep.constructs.AssistedUnicastEndpoints"},
//    			{"es.tid.pce.pcep.constructs.EndPoint"},
//    			{"es.tid.pce.pcep.constructs.EndPointAndRestrictions"},
//    			{"es.tid.pce.pcep.constructs.EndpointRestriction"},
//    			{"es.tid.pce.pcep.constructs.ErrorConstruct"},
//    			{"es.tid.pce.pcep.constructs.FullAnycastEndpoints"},
    			{"es.tid.pce.pcep.constructs.GeneralizedBandwidthSSON"},
    			{"es.tid.pce.pcep.constructs.LSPInstantationRequest"},
    			{"es.tid.pce.pcep.constructs.MetricPCE"},
    			{"es.tid.pce.pcep.constructs.NCF"},
//    			{"es.tid.pce.pcep.constructs.Notify"},
//    			{"es.tid.pce.pcep.constructs.P2MPEndpoints"},
//    			{"es.tid.pce.pcep.constructs.P2PEndpoints"},
    			{"es.tid.pce.pcep.constructs.PCEPIntiatedLSP"},
    			{"es.tid.pce.pcep.constructs.Path"},
    			{"es.tid.pce.pcep.constructs.RROBandwidth"},
    			{"es.tid.pce.pcep.constructs.Request"},
    			{"es.tid.pce.pcep.constructs.Response"},
    			{"es.tid.pce.pcep.constructs.StateReport"},
  			    {"es.tid.pce.pcep.constructs.SVECConstruct"},
    			{"es.tid.pce.pcep.constructs.SwitchEncodingType"},
    			{"es.tid.pce.pcep.constructs.UpdateRequest"}
    			
    	};
    	return Arrays.asList(objects);
    	
	}
	
    public TestPCEPConstructs (String object) {
    	this.object=object;
    }
    
	
	  @Test
	    public void test (){
	    	try {
	    	System.out.println("Testing PCEP Construct "+object+ " (combination 0)");
	    	Class objectClass=Class.forName(object);
	    	PCEPConstruct object1 = (PCEPConstruct)objectClass.newInstance();
			TestCommons.createAllFields(object1,0);
			object1.encode();
			Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
			PCEPConstruct object2 = (PCEPConstruct) ctor.newInstance(object1.getBytes(),0);
			object2.encode();
			System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
			System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
			//Check all the gets
			TestCommons.testGets(object1);
			//Check if the fields are the same
			assertTrue("testing Construct "+object.getClass().getName() + " original:  "+object1.toString()+" copy: "+object2.toString(),object1.equals(object2));
	    	System.out.println("Testing PCEP Construct "+object+ " (combination 1)");
	    	object1 = (PCEPConstruct)objectClass.newInstance();
			TestCommons.createAllFields(object1,1);
			object1.encode();
			object2 = (PCEPConstruct) ctor.newInstance(object1.getBytes(),0);
			object2.encode();
			System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
			System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
			assertTrue("(1) testing Construct "+object.getClass().getName() + " original:  "+object1.toString()+" copy: "+object2.toString(),object1.equals(object2));

			System.out.println("Testing PCEP Construct "+object+ " (combination 2)");;
			object1 = (PCEPConstruct)objectClass.newInstance();
			TestCommons.createAllFields(object1,2);
			object1.encode();
			object2 = (PCEPConstruct) ctor.newInstance(object1.getBytes(),0);
			object2.encode();
			System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
			System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
			assertTrue("(2) testing Construct "+object.getClass().getName() + " original:  "+object1.toString()+" copy: "+object2.toString(),object1.equals(object2));
			
			System.out.println("Testing PCEP Construct "+object+ " (combination 3)");;
			object1 = (PCEPConstruct)objectClass.newInstance();
			TestCommons.createAllFields(object1,3);
			object1.encode();
			object2 = (PCEPConstruct) ctor.newInstance(object1.getBytes(),0);
			object2.encode();
			System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
			System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
			assertTrue("(3) testing Construct "+object.getClass().getName() + " original:  "+object1.toString()+" copy: "+object2.toString(),object1.equals(object2));
			
			
			//Test hashcode
			object1.hashCode();
			//Test equals
			testEquals(object1,object2);
	    	} catch(Exception e){
	    		e.printStackTrace();
	    		assertTrue("Exception in construct "+object,false);
	    		
	    	}
	    }
	    	
	  
	   public void testEquals(PCEPConstruct object1, PCEPConstruct object2){
	    	//Test against different class object
	    	Integer test1=new Integer(2);
	    	boolean res;
			res=object1.equals(test1);
			assertTrue("testing equals ",!res);
	    	//Test same object
	    	object1.equals(object1);
	    	//Test null
	    	object1.equals(null);	    
	    	object1.equals(object2);
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
