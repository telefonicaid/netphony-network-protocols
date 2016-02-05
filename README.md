netphony-network-protocols v1.2
=================================
The netphony-network-protocols library is a set of implementations of the main networking protocols stacks that enable control and management functions: 
* PCEP protocol
* RVP-TE protocol
* OSPF-TE protocol
* BGP-LS protocol

Any software requiring the connection with a device/software that supports such protocols can easily integrate the protocol library. The library provides the encoding and decoding from java objects to bit-level. Note that, the state machine and set up of sessions is provided by other components, also available in github. 

The library is maintained to be up-to-date to the latest version of the internet-drafts/RFCs. Contributions are highly welcomed.

## Compilation and use

The library can be built using the maven tool. There is a set of junit tests included that tests the enconding/decoding process. Contributions on expanding the test suite are welcomed.
To build the .jar file and run the tests, simply clone the repository, go to the main directory and run
 ```bash
    cd netphony-network-protocols
    mvn package
 ```
 
 To use the library in your application, simply add the dependency in your pom.xml file:
  ```xml
    <dependency>
      <groupId>es.tid.netphony</groupId>
      <artifactId>network-protocols</artifactId>
      <version>1.2</version>
    </dependency>
 ```
 Authors keep also a copy of the artifact in maven central to facilitate the deployment.

## How to use the code:

### ENCODING

1-> Create a new instance of the desired message
 ```java
     PCEPRequest message = new PCEPRequest();
 ```
2-> Create instances of the desired constructs or objects and add them to the message
 ```java
     Request req = new Request();
     //RequestParameters
     RequestParameters rp= new RequestParameters();
     rp.setPbit(true);				
     rp.setRequestID(123);		
     rp.setPrio(1);		
     rp.setReopt(false);	
     rp.setBidirect(false);
     rp.setLoose(false);
     req.setRequestParameters(rp);
     //EndPoints
     EndPointsIPv4 ep=new EndPointsIPv4();				
     req.setEndPoints(ep);
     Inet4Address ipp = (Inet4Address)Inet4Address.getByName("172.16.101.101");
     ep.setSourceIP(ipp);
	.....
    message.addRequest(req); 	
 ```
3-> Call encode()
```java
   message.encode();
```
4-> Get bytes and send them!
```java
   out.write(message.getBytes());
   out.flush();
```
## PCEP Support

Detailed PCEP Support [click here](doc/PCEP_Support.md)

* RFC 5440: Full compliance
* RFC 5521: Path-key not supported
* RFC 5886: Full compliance
* RFC 6006: Only P2MP END-POINTS Object for IPv4
* draft-ietf-pce-gmpls-pcep-extensions-10 (partial)
* draft-ietf-pce-inter-layer-ext-05 (partial)
* draft-ietf-pce-hierarchy-extensions-02
* draft-ietf-pce-stateful-pce-05
* draft-ietf-pce-pcep-stateful-pce-gmpls-00
* draft-ietf-pce-pce-initiated-lsp-00:
 
## RSVP-TE Support

Detailed RSVP-TE Support [click here](doc/RSVP-TE_Support.md)


## OSPF-TE Support

Detailed OSPF-TE Support [click here](doc/OSPF-TE_Support.md)

OSPF-TE v2 LSA from RFC3630
Inter-AS-TE-v2 LSA from RFC5392 http://tools.ietf.org/html/rfc5392

# BGP-LS Support

Detailed BGP4 & BGP-LS Support [click here](doc/BGP-LS_Support.md)


http://tools.ietf.org/html/draft-ietf-idr-ls-distribution-03

