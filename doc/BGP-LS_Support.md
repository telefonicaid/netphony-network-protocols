Detailed BGP-4 & BGP-LS Implementation Support (v1.0.1)
=======================================================

The BGP-4 and BGP-LS elements shown below are implemented in current version, unless **Not Implemented** is mentioned.  

BGP-4 Messages
--------------
* BGP KeepAlive Message [RFC 4271](http://tools.ietf.org/html/rfc4271#section-4.4 "RFC 4271: A Border Gateway Protocol 4 (BGP-4) Keepalive Message")
* BGP Notification **Not Implemented**
* BGP Open Message [RFC 4271](http://tools.ietf.org/html/rfc4271#section-4.2 "RFC 4271: A Border Gateway Protocol 4 (BGP-4) Open Message")
* BGP Update Message [RFC 4271](http://tools.ietf.org/html/rfc4271#section-4.3 "RFC 4271: A Border Gateway Protocol 4 (BGP-4) Update Message")

Optional Parameters in OPEN
---------------------------
* Capabilities Optional Parameter (Parameter Type 2) [RFC 3392](http://tools.ietf.org/html/rfc3392#section-4 "RFC 3392 Capabilities Advertisement with BGP-4")

### Capabilities
* Multiprotocol Extensions capability (Capability Code 1) [RFC 4760](http://tools.ietf.org/html/rfc4760#section-8 "RFC 4760 Multiprotocol Extensions for BGP-4")

Fields in UPDATE Message
------------------------
* Withdrawn Routes **Not Implemented**
* Path Attribute
* Network Layer Reachability Information **Not Implemented**

### Path Attributes
* Origin Attribute (Type Code 1): rfc 4271
* AS Path Attribute (Type Code 2): rfc 4271
* Next Hop (Type Code 3): rfc 4271  **Not Implemented**
* Multi Exit Disc (Type Code 4) **Not Implemented**
* Local Pref (Type Code 5) **Not Implemented**
* Atomic Aggregate (Type Code 6) **Not Implemented**
* Aggregator (Type Code 7) **Not Implemented**
* MP Reach (Type Code 14)
* MP Unreach (Type Code 15)

TLVs
----
* Local Node Descriptors TLV (Type 256)
* Remote Node Descriptors TLV (Type 257)

 ### Node Descriptor Sub-TLVs
 * Autonomous System
 * BGP-LS Identifier 
 * OSPF Area-ID   
 * IGP Router-ID

Link Descriptors TLVs
----------------------


Link State Attribute TLVs
-------------------------
* Administrative Group
* Link Local/Remote 
* IPv4 interface
* IPv4 neighbor


