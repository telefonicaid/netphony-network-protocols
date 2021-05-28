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
import es.tid.ospf.ospfv2.OSPFv2Packet;
import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.messages.PCEPMessage;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.protocol.commons.ByteHandler;

@RunWith(org.junit.runners.Parameterized.class)
public class TestOSPFv2Packets {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
    			{"es.tid.ospf.ospfv2.OSPFv2HelloPacket"},
    			{"es.tid.ospf.ospfv2.OSPFv2LinkStateUpdatePacket"},
				};
		return Arrays.asList(objects);
    }
	
    
    public TestOSPFv2Packets (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("oscar Testing OSPFv2 Packet "+object);
    	Class objectClass=Class.forName(object);
    	OSPFv2Packet object1 = (OSPFv2Packet)objectClass.newInstance();
		TestCommons.createAllFields(object1,true);
		object1.encode();
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		OSPFv2Packet object2 = (OSPFv2Packet) ctor.newInstance(object1.getBytes(),0);
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
		System.out.println(object1.toString());
		System.out.println(object2.toString());
		//Check if the fields are the same
		assertTrue("testing OSPFv2 packet "+objectClass,object1.equals(object2));
		System.out.println("OSPF Packet check 1 ok");
		//Testing changing values
		OSPFv2Packet object3 = (OSPFv2Packet)objectClass.newInstance();
		TestCommons.createAllFields(object3,false);
		object3.encode();
		OSPFv2Packet object4 = (OSPFv2Packet) ctor.newInstance(object3.getBytes(),0);
		object4.encode();
		System.out.println(ByteHandler.ByteMACToString(object3.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object4.getBytes()));
		System.out.println(object3.toString());
		System.out.println(object4.toString());
		assertTrue("testing OSPFv2 packet check 2 "+objectClass,object4.equals(object4));
		System.out.println("OSPF Packet check 2 ok");

    	} catch(Exception e){
    		e.printStackTrace();
    		assertTrue("Exception in message "+object,false);
    		
    	}
    }
    
  
    
}
