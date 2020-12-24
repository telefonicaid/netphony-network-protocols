package es.tid.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
//import static org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.tid.bgp.bgp4.messages.BGP4Message;
import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.messages.PCEPMessage;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.protocol.commons.ByteHandler;

@RunWith(org.junit.runners.Parameterized.class)
public class TestBGP4Messages {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
    			{"es.tid.bgp.bgp4.messages.BGP4Keepalive"},
    			{"es.tid.bgp.bgp4.messages.BGP4Open"},
    			{"es.tid.bgp.bgp4.messages.BGP4Update"},
    			//{"es.tid.bgp.bgp4.messages.BGP4Notification"},
				};
		return Arrays.asList(objects);
    }
	
    
    public TestBGP4Messages (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("oscar Testing BGP4 Message "+object);
    	Class objectClass=Class.forName(object);
    	BGP4Message object = (BGP4Message)objectClass.newInstance();
		TestCommons.createAllFields(object,true);
		object.encode();
		Constructor ctor = objectClass.getConstructor(byte[].class);
		BGP4Message object2 = (BGP4Message) ctor.newInstance(object.getBytes());
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));

		//Check if the fields are the same
		assertTrue("testing message "+objectClass,object.equals(object2));
    	} catch(Exception e){
    		e.printStackTrace();
    		assertTrue("Exception in message "+object,false);
    		
    	}
    }
    
  
    
}
