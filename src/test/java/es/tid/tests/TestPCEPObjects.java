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
    			{"es.tid.pce.pcep.objects.OPEN"}, // ObjectClass 1	OPEN	Object-Type 1: Open	[RFC5440]
    			{"es.tid.pce.pcep.objects.RequestParameters"},	// ObjectClass 2	RP Object-Type  Request Parameters	[RFC5440]
    			{"es.tid.pce.pcep.objects.NoPath"}, // ObjectClass NO-PATH	Object-Type 1: No Path	[RFC5440]
    			{"es.tid.pce.pcep.objects.EndPointsIPv4"}, // ObjectClass 4	END-POINTS Object-Type 1: IPv4 addresses	[RFC5440]
    			{"es.tid.pce.pcep.objects.EndPointsIPv6"}, // ObjectClass 4	END-POINTS Object-Type 2: IPv6 addresses	[RFC5440]
    			{"es.tid.pce.pcep.objects.EndPointsUnnumberedIntf"}, // FIXME: PROPRIETARY MOVE TO GENERALIZED END POINTS!!!!
    			{"es.tid.pce.pcep.objects.P2MPEndPointsIPv4"},// ObjectClass 4	END-POINTS Object-Type 3: P2MP IPv4	[RFC8306]
    			// FIME NOT IMPLEMENTED ObjectClass 4	END-POINTS Object-Type 4: P2MP IPv6 [RFC8306]
    			{"es.tid.pce.pcep.objects.P2MPGeneralizedEndPoints"},// ObjectClass 4	END-POINTS Object-Type 5:  Generalized Endpoint Point to Multipoint.
    			{"es.tid.pce.pcep.objects.P2PGeneralizedEndPoints"},// ObjectClass 4	END-POINTS Object-Type 5:  Generalized Endpoint Point to Multipoint.
    			{"es.tid.pce.pcep.objects.BandwidthRequested"},	// ObjectClass 	5	BANDWIDTH  Object-Type 1: Requested bandwidth	[RFC5440]
    			{"es.tid.pce.pcep.objects.BandwidthExistingLSP"},    // ObjectClass 	5	BANDWIDTH  Object-Type	2: Bandwidth of an existing TE LSP for which a reoptimization is requested	[RFC5440]		
//3: Generalized bandwidth	[RFC8779, Section 2.3]
    			{"es.tid.pce.pcep.objects.BandwidthRequestedGeneralizedBandwidth"},
//4: Generalized bandwidth of an existing TE-LSP for which a reoptimization is requested	[RFC8779, Section 2.3]
    			{"es.tid.pce.pcep.objects.BandwidthExistingLSPGeneralizedBandwidth"},
    			{"es.tid.pce.pcep.objects.Metric"},// ObjectClass 6	METRIC Object-Type 1: Metric	[RFC5440]	
    			{"es.tid.pce.pcep.objects.ExplicitRouteObject"},   // ObjectClass 7	ERO Object-Type 1: Explicit Route	[RFC5440]
    			{"es.tid.pce.pcep.objects.ReportedRouteObject"}, // ObjectClass 8	RRO	Object-Type 1: Recorded Route	[RFC5440] 
    			{"es.tid.pce.pcep.objects.LSPA"},
    			{"es.tid.pce.pcep.objects.BandwidthUtilization"},
//9	LSPA	0: Reserved	[RFC5440][RFC Errata 4956]
//1: LSP Attributes	[RFC5440]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.IncludeRouteObject"},			
//10	IRO	0: Reserved	[RFC5440][RFC Errata 4956]
//1: Include Route	[RFC5440]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.Svec"},		
//11	SVEC	0: Reserved	[RFC5440][RFC Errata 4956]
//1: Synchronization Vector	[RFC5440]
//2-15: Unassigned	
    			//{"es.tid.pce.pcep.objects.Notification"},
//12	NOTIFICATION	0: Reserved	[RFC5440][RFC Errata 4956]
//1: Notification	[RFC5440]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.PCEPErrorObject"},
//13	PCEP-ERROR	0: Reserved	[RFC5440][RFC Errata 4956]
//1: PCEP Error	[RFC5440]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.LoadBalancing"},	
//14	LOAD-BALANCING	0: Reserved	[RFC5440][RFC Errata 4956]
//1: Load Balancing	[RFC5440]
//2: Generalized Load Balancing	[RFC8779, Section 2.4]
//3-15: Unassigned	
    			{"es.tid.pce.pcep.objects.Close"},	
//15	CLOSE	0: Reserved	[RFC5440][RFC Errata 4956]
//1: Close	[RFC5440]
//2-15: Unassigned	
//    			//FIXME: TBD
//16	PATH-KEY	0: Reserved	[RFC5520][RFC Errata 4956]
//1: Path Key	[RFC5520]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.ExcludeRouteObject"},
//17	XRO	0: Reserved	[RFC5521][RFC Errata 4956]
//1: Route exclusion	[RFC5521]
//2-15: Unassigned	
//18	Unassigned	0: Reserved	[RFC Errata 4956]
//1-15: Unassigned	
    			{"es.tid.pce.pcep.objects.Monitoring"},	
//19	MONITORING	0: Reserved	[RFC5886][RFC Errata 4956]
//1: Monitoring	[RFC5886]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.PccReqId"},
//20	PCC-REQ-ID	0: Reserved	[RFC5886][RFC Errata 4956]
//1: IPV4 Addresses	[RFC5886]
//2: IPV6 Addresses	[RFC5886]
//3-15: Unassigned	
    			{"es.tid.pce.pcep.objects.ObjectiveFunction"},
//21	OF	0: Reserved	[RFC5541][RFC Errata 4956]
//1: Objective Function	[RFC5541]
//2-15: Unassigned	
    			//FIXME: TBD
//22	CLASSTYPE	0: Reserved	[RFC5455][RFC Errata 4956]
//1: Class-Type	[RFC5455]
//2-15: Unassigned	
//23	Unassigned	0: Reserved	[RFC Errata 4956]
//1-15: Unassigned	
    			//FIXME: TBD
//24	GLOBAL-CONSTRAINTS	0: Reserved	[RFC5557][RFC Errata 4956]
//1: Global Constraints	[RFC5557]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.PceIdIPv4"},
//25	PCE-ID	0: Reserved	[RFC5886][RFC Errata 4956]
//1: IPV4 Addresses	[RFC5886]
//2: IPV6 Addresses	[RFC5886]
//3-15: Unassigned	
    			{"es.tid.pce.pcep.objects.ProcTime"},
//26	PROC-TIME	0: Reserved	[RFC5886][RFC Errata 4956]
//1: PROC-TIME	[RFC5886]
//2-15: Unassigned	
    			//FIXME: TBD
//27	OVERLOAD	0: Reserved	[RFC5886][RFC Errata 4956]
//1: overload	[RFC5886]
//2-15: Unassigned	
    			//FIXME: TBD
//28	UNREACH-DESTINATION	0: Reserved	[RFC8306]
//1: IPv4	[RFC8306]
//2: IPv6	[RFC8306]
    			
//3-15: Unassigned	
    			//FIXME: TBD
//29	SERO	0: Reserved	[RFC8306]
//1: SERO	[RFC8306]
//2-15: Unassigned
    			//FIXME: TBD
//30	SRRO	0: Reserved	[RFC8306]
//1: SRRO	[RFC8306]
//2-15: Unassigned	
    			//FIXME: TBD
//31	BNC	0: Reserved	[RFC8306]
//1: Branch node list	[RFC8306]
//2: Non-branch node list	[RFC8306]
//3-15: Unassigned	
    			{"es.tid.pce.pcep.objects.LSP"},
//32	LSP	0: Reserved	[RFC8231]
//1: LSP	[RFC8231]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.SRP"},	
//33	SRP	0: Reserved	[RFC8231]
//1: SRP	[RFC8231]
//2-15: Unassigned	
    			//FIXME: TBD
//34	VENDOR-INFORMATION	0: Reserved	[RFC7470][RFC Errata 4956]
//1: Vendor-Specific Constraints	[RFC7470]
//2-15: Unassigned	
    			//FIXME: TBD

//35	BU	0: Reserved	[RFC8233][RFC Errata 4956]
//1: BU	[RFC8233]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.InterLayer"},
//36	INTER-LAYER	0: Reserved	[RFC8282]
//1: Inter-layer	[RFC8282]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.SwitchLayer"},

//37	SWITCH-LAYER	0: Reserved	[RFC8282]
//1: Switch-layer	[RFC8282]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.ReqAdapCap"},

//38	REQ-ADAP-CAP	0: Reserved	[RFC8282]
//1: Req-Adap-Cap	[RFC8282]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.ServerIndication"},
	
//39	SERVER-INDICATION	0: Reserved	[RFC8282]
//1: Server-indication	[RFC8282]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.AssociationIPv4"},
    			{"es.tid.pce.pcep.objects.AssociationIPv6"},

//40	ASSOCIATION	0: Reserved	[RFC8697]
//1: IPv4	[RFC8697]
//2: IPv6	[RFC8697]
//3-15: Unassigned	
    			//FIXME: TBD

//41	S2LS	0: Reserved	[RFC8623]
//1: S2LS	[RFC8623]
//2-15: Unassigned	
    			{"es.tid.pce.pcep.objects.WavelengthAssignementObject"},
//42	WA	0: Reserved	[RFC8780]
//1: Wavelength Assignment	[RFC8780]
//2-15: Unassigned	
    			//FIXME: TBD

//43	FLOWSPEC	0: Reserved	[RFC-ietf-pce-pcep-flowspec-12]
//1: Flow Specification	[RFC-ietf-pce-pcep-flowspec-12]
//2-15: Unassigned	
    			//FIXME: TBD

//44	CCI Object-Type	0: Reserved	[RFC9050]
//1: MPLS Label	[RFC9050]


//EXPERIMENTAL
    			{"es.tid.pce.pcep.objects.BitmapLabelSet"},
    			{"es.tid.pce.pcep.objects.EndPointDataPathID"},
    			{"es.tid.pce.pcep.objects.LabelSetInclusiveList"},
    			{"es.tid.pce.pcep.objects.Reservation"},
				{"es.tid.pce.pcep.objects.ReservationConf"},
    			{"es.tid.pce.pcep.objects.SuggestedLabel"},
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
		TestCommons.createAllFields(object1,0);
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
		TestCommons.createAllFields(object1,1);
		object1.encode();
		object2 = (PCEPObject) ctor.newInstance(object1.getBytes(),0);
		object2.encode();
		assertTrue("testing PCEP object 2"+objectClass,object1.equals(object2));
		System.out.println("Testing String output: "+object1.toString());
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
