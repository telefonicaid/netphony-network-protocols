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
import es.tid.rsvp.messages.RSVPMessage;

@RunWith(org.junit.runners.Parameterized.class)
public class TestRSVPTEMessages {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
    			//Traffic Engineering
    			{"es.tid.rsvp.messages.te.RSVPTEPathMessage"},
    			{"es.tid.rsvp.messages.te.RSVPTEHelloMessage"},
    			
				};
		return Arrays.asList(objects);
    }
	
    
    public TestRSVPTEMessages (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("Testing RSVP TE Message "+object+ "combination 0");
    	Class objectClass=Class.forName(object);
    	RSVPMessage object1 = (RSVPMessage)objectClass.newInstance();
    	TestCommons.createAllFields(object1,0,false,true);
    	//Fill parent's fields
		TestCommons.createAllFields(object1,1,true,true);
		object1.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		RSVPMessage object2 = (RSVPMessage) ctor.newInstance(object1.getBytes(),object1.getBytes().length);
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
		//System.out.println(object2.toString());
		//Check if the fields are the same
		assertTrue("asserting RSVP message combination  0: "+objectClass,object1.equals(object2));
		
		//Check with other options
		System.out.println("Testing RSVP TE Message "+object+ "combination 1");
		object1 = (RSVPMessage)objectClass.newInstance();
    	TestCommons.createAllFields(object1,1,false,true);
    	//Fill parent's fields
		TestCommons.createAllFields(object1,0,true,true);
		object1.encode();
		//System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
		object2 = (RSVPMessage) ctor.newInstance(object1.getBytes(),object1.getBytes().length);
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
		//System.out.println(object2.toString());
		//Check if the fields are the same
		assertTrue("asserting RSVP message "+objectClass,object1.equals(object2));
		
    	} catch(Exception e){
    		e.printStackTrace();
    		assertTrue("Exception in message "+object,false);
    		
    	}
    }
    
  
    
}
