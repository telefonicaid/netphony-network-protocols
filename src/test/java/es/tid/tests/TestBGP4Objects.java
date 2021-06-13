package es.tid.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
//import static org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.tid.bgp.bgp4.objects.BGP4Object;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.protocol.commons.ByteHandler;

@RunWith(org.junit.runners.Parameterized.class)
public class TestBGP4Objects {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
    			//{" es.tid.bgp.bgp4.update.fields.WithdrawnRoutes"},
    			{"es.tid.bgp.bgp4.update.fields.NodeNLRI"},
    			//{"es.tid.bgp.bgp4.update.fields.LinkNLRI"},
    			{"es.tid.bgp.bgp4.update.fields.pathAttributes.AS_Path_Attribute"},
    			//{"es.tid.bgp.bgp4.update.fields.pathAttributes.BGP_LS_MP_Reach_Attribute"},
    			//{"es.tid.bgp.bgp4.update.fields.pathAttributes.LinkStateAttribute"},
    			{"es.tid.bgp.bgp4.update.fields.pathAttributes.LOCAL_PREF_Attribute"},
    			{"es.tid.bgp.bgp4.update.fields.pathAttributes.Generic_MP_Reach_Attribute"},
    			{"es.tid.bgp.bgp4.update.fields.pathAttributes.Generic_MP_Unreach_Attribute"},
    			//{"es.tid.bgp.bgp4.update.fields.pathAttributes.Next_Hop_Attribute"},
    			{"es.tid.bgp.bgp4.update.fields.pathAttributes.OriginAttribute"},    			
				};
		return Arrays.asList(objects);
    }
	
    
    public TestBGP4Objects (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("Testing BGP4 Object "+object);
    	Class objectClass=Class.forName(object);
		BGP4Object object = (BGP4Object)objectClass.newInstance();
		TestCommons.createAllFields(object,0);
		object.encode();
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		BGP4Object object2 = (BGP4Object) ctor.newInstance(object.getBytes(),0);
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));

		//Check if the fields are the same
		assertTrue("testing object "+objectClass,object.equals(object2));
    	} catch(Exception e){
    		e.printStackTrace();
    		assertTrue("Exception in object "+object,false);
    		
    	}
    }
 
    
}
