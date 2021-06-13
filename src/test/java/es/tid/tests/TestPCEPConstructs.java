package es.tid.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

import es.tid.pce.pcep.constructs.PCEPConstruct;
import es.tid.pce.pcep.objects.PCEPObject;
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
	    	System.out.println("Testing PCEP Construct "+object);
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
	    	} catch(Exception e){
	    		e.printStackTrace();
	    		assertTrue("Exception in construct "+object,false);
	    		
	    	}
	    }
	    	

}
