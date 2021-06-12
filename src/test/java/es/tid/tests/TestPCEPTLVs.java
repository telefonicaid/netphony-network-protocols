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
    			{"es.tid.pce.pcep.objects.tlvs.OverloadDurationTLV"},//Type 2 OVERLOAD-DURATION-TLV
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
    			{"es.tid.pce.pcep.objects.tlvs.SymbolicPathNameTLV"},//Type 17: 17	SYMBOLIC-PATH-NAME	[RFC8231]
    			{"es.tid.pce.pcep.objects.tlvs.IPv4LSPIdentifiersTLV"},//Type 18:	IPV4-LSP-IDENTIFIERS	[RFC8231] FIXME: Draft version implemented RECHECK!!
    			// 19	IPV6-LSP-IDENTIFIERS	[RFC8231] FIXME: Not implemented
    			{"es.tid.pce.pcep.objects.tlvs.LSPErrorCodeTLV"}, //20	LSP-ERROR-CODE	[RFC8231]  FIXME: Draft version implemented RECHECK!!
    			{"es.tid.pce.pcep.objects.tlvs.RSVPErrorSpecTLV" }, //	21	RSVP-ERROR-SPEC	[RFC8231]  FIXME: Draft version implemented RECHECK!!
    			//	22	Unassigned	
    			{"es.tid.pce.pcep.objects.tlvs.LSPDatabaseVersionTLV" },//   23	LSP-DB-VERSION	[RFC8232] FIXME: Draft version implemented RECHECK!!
    			//{"es.tid.pce.pcep.objects.tlvs.SpeakerEntityIdentifierTLV"},// 	24	SPEAKER-ENTITY-ID	[RFC8232] FIXME: Add suport for byte[] in fields
    			//	25	Unassigned	
    			{"es.tid.pce.pcep.objects.tlvs.SRCapabilityTLV"},//26	SR-PCE-CAPABILITY (deprecated)	[RFC8664]
    			//	27	Unassigned	
    			{"es.tid.pce.pcep.objects.tlvs.PathSetupTLV"},//   28	PATH-SETUP-TYPE	[RFC8408] FIXME: Draft version implemented RECHECK!!
    			{"es.tid.pce.pcep.objects.tlvs.OpConfAssocRangeTLV"},//  	29	Operator-configured Association Range	[RFC8697]
    			{"es.tid.pce.pcep.objects.tlvs.GlobalAssociationSourceTLV"}, //	30	Global Association Source	[RFC8697]
//    					31	Extended Association ID	[RFC8697]
//    					32	P2MP-IPV4-LSP-IDENTIFIERS	[RFC8623]
//    					33	P2MP-IPV6-LSP-IDENTIFIERS	[RFC8623]
//    					34	PATH-SETUP-TYPE-CAPABILITY	[RFC8408]
//    					35	ASSOC-Type-List	[RFC8697]
//    					36	AUTO-BANDWIDTH-CAPABILITY	[RFC8733]
//    					37	AUTO-BANDWIDTH-ATTRIBUTES	[RFC8733]
//    					38	Path Protection Association Group TLV	[RFC8745]
//    					39	IPV4-ADDRESS	[RFC8779, Section 2.5.2.1]
//    					40	IPV6-ADDRESS	[RFC8779, Section 2.5.2.2]
//    					41	UNNUMBERED-ENDPOINT	[RFC8779, Section 2.5.2.3]
    			{"es.tid.pce.pcep.objects.tlvs.LabelRequestTLV"}, //	42	LABEL-REQUEST	[RFC8779, Section 2.5.2.4]    			
//    					43	LABEL-SET	[RFC8779, Section 2.5.2.5]
//    					44	PROTECTION-ATTRIBUTE	[RFC8779, Section 2.8]
//    					45	GMPLS-CAPABILITY	[RFC8779, Section 2.1.2]
//    					46	DISJOINTNESS-CONFIGURATION	[RFC8800]
//    					47	DISJOINTNESS-STATUS	[RFC8800]
//    					48	POLICY-PARAMETERS-TLV (TEMPORARY - registered 2020-05-15, expires 2021-05-15)	[draft-ietf-pce-association-policy-09]
//    					49	SCHED-LSP-ATTRIBUTE	[RFC8934]
//    					50	SCHED-PD-LSP-ATTRIBUTE	[RFC8934]
//    					51	PCE-FLOWSPEC-CAPABILITY TLV	[RFC-ietf-pce-pcep-flowspec-12]
//    					52	FLOW FILTER TLV	[RFC-ietf-pce-pcep-flowspec-12]
//    					53	L2 FLOW FILTER TLV	[RFC-ietf-pce-pcep-flowspec-12]
    			{"es.tid.pce.pcep.objects.tlvs.ASSOCTypeListTLV"},//Type 35

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
