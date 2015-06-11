netphony-network-protocols v1.0.1
=================================

Java Library of Networking Protocols: PCEP, RSVP, OSPF, BGP-LS

Provides encoding and decoding of the mentioned protocols.

## Usage:

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

