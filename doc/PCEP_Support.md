Detailed PCEP Implementation Support (v1.4.0)
=============================================

Messages
--------

### Standard Messages

|Number | Message | Reference | Implemented |
| ------ | ------  | ------    | ------ |
| 1 | Open | [RFC5440] | YES |
| 2 | Keepalive | [RFC5440] | YES |
| 3 | Path Computation Request | [RFC5440] | YES |
| 4 | Path Computation Reply | [RFC5440] | YES |
| 5 | Notification | [RFC5440] | YES |
| 6 | Error | [RFC5440] | YES |
| 7 | Close | [RFC5440] | YES |
| 8 | Path Computation Monitoring Request (PCMonReq) | [RFC5886] | YES |
| 9 | Path Computation Monitoring Reply (PCMonRep) | [RFC5886] | YES |
| 10 | Report | [RFC8231] | YES |
| 11 | Update | [RFC8231] | YES |
| 12 | LSP Initiate Request | [RFC8281] | YES |
| 13 | StartTLS | [RFC8253] | NO |

Objects
-------

### Standard (RFC & IETF draft) Objects

|Object-Class Value | Name | Object-Type | Reference |  Implemented |
| ------ | ------  | ------    | ------ | ------ |
|1 | OPEN | 1: Open | [RFC5440] | YES |
|2 | RP | 1: Request Parameters | [RFC5440] | YES | 
|3 | NO-PATH | 1: No Path | [RFC5440]| YES | 
|4 | END-POINTS | 1: IPv4 addresses | [RFC5440] | YES | 
| |  | 2: IPv6 addresses | [RFC5440] | YES | 
| |  | 3: IPv4 | [RFC8306] | NO | 
| |  | 4: IPv6 | [RFC8306] | NO | 
| |  | 5: Generalized Endpoint | "[RFC8779 Section 2.5]" | YES | 
|5 | BANDWIDTH | 1: Requested bandwidth | [RFC5440] | YES | 
| |  | 2: Bandwidth of an existing TE LSP for which a reoptimization is requested | [RFC5440] | YES | 
| |  | 3: Generalized bandwidth | "[RFC8779]" | YES | 
| |  | 4: Generalized bandwidth of an existing TE-LSP for which a reoptimization is requested | [RFC8779] | YES | 
|6 | METRIC | 1: Metric | [RFC5440] | YES |  
|7 | ERO | 1: Explicit Route | [RFC5440] | YES |  
|8 | RRO | 1: Recorded Route | [RFC5440] | YES |
|9 | LSPA |1: LSP Attributes | [RFC5440] | YES |
|10 | IRO | 1: Include Route | [RFC5440] | YES |
|11 | SVEC | 1: Synchronization Vector | [RFC5440] | YES |
|12 | NOTIFICATION | 1: Notification | [RFC5440] | YES |
|13 | PCEP-ERROR | 1: PCEP Error | [RFC5440] | YES |
|14 | LOAD-BALANCING |1: Load Balancing | [RFC5440] | YES |
| |  | 2: Generalized Load Balancing | [RFC8779] | YES |
|15 | CLOSE |  1: Close | [RFC5440] | YES |
|16 | PATH-KEY |  1: Path Key | [RFC5520] | NO |
|17 | XRO |  1: Route exclusion | [RFC5521] | YES |
|19 | MONITORING | 1: Monitoring | [RFC5886] | YES |
|20 | PCC-REQ-ID |  1: IPV4 Addresses | [RFC5886] | YES |
| |  | 2: IPV6 Addresses | [RFC5886] | NO |
|21 | OF | 1: Objective Function | [RFC5541] | YES |
|22 | CLASSTYPE |1: Class-Type | [RFC5455] | NO |
|24 | GLOBAL-CONSTRAINTS |  1: Global Constraints | [RFC5557] | NO |
|25 | PCE-ID |1: IPV4 Addresses | [RFC5886] | YES |
| |  | 2: IPV6 Addresses | [RFC5886] | NO |
|26 | PROC-TIME | 1: PROC-TIME | [RFC5886] | YES |
|27 | OVERLOAD | 1: overload | [RFC5886] | NO |
|28 | UNREACH-DESTINATION |1: IPv4 | [RFC8306] | NO |
| |  | 2: IPv6 | [RFC8306] | NO |
|29 | SERO |  1: SERO | [RFC8306] | NO |
|30 | SRRO | 1: SRRO | [RFC8306] | NO |
|31 | BNC |  1: Branch node list | [RFC8306] | NO |
| |  | 2: Non-branch node list | [RFC8306] | NO |
|32 | LSP |  1: LSP | [RFC8231] | YES |
|33 | SRP |  1: SRP | [RFC8231] | YES |
|34 | VENDOR-INFORMATION |  1: Vendor-Specific Constraints | [RFC7470] | NO |
|35 | BU |  1: BU | [RFC8233] | NO |
|36 | INTER-LAYER | 1: Inter-layer | [RFC8282] | YES |
|37 | SWITCH-LAYER | 1: Switch-layer | [RFC8282] | YES |
|38 | REQ-ADAP-CAP |1: Req-Adap-Cap | [RFC8282] | YES |
|39 | SERVER-INDICATION |  1: Server-indication | [RFC8282] | YES |
|40 | ASSOCIATION | 1: IPv4 | [RFC8697] | NO |
| |  | 2: IPv6 | [RFC8697] | NO |
|41 | S2LS | 1: S2LS | [RFC8623] | NO |
|42 | WA | 1: Wavelength Assignment | [RFC8780] | YES |
|43 | FLOWSPEC | 1: Flow Specification | [RFC-ietf-pce-pcep-flowspec-12] | NO |

TLVs
----

### Standard (RFC & IETF draft) TLVs

| Value | Description | Reference | Implemented |
| ------ | ------  | ------    | ------ |
|1 | NO-PATH-VECTOR TLV | [RFC5440] | YES |
|2 | OVERLOAD-DURATION TLV | [RFC5440] | NO |
|3 | REQ-MISSING TLV | [RFC5440] | YES |
|4 | OF-List | [RFC5541] | YES |
|5 | Order TLV | [RFC5557] | NO |
|6 | P2MP capable | [RFC8306] | NO |
|7 | VENDOR-INFORMATION-TLV | [RFC7470] | NO |
|8 | Wavelength Selection | [RFC8780] | NO |
|9 | Wavelength Restriction | [RFC8780] | NO |
|10 | Wavelength Allocation | [RFC8780] | NO |
|11 | Optical Interface Class List | [RFC8780] | NO |
|12 | Client Signal Information | [RFC8780] | NO |
|13 | H-PCE-CAPABILITY | [RFC8685] | NO |
|14 | Domain-ID | [RFC8685] | YES |
|15 | H-PCE-FLAG | [RFC8685] | NO |
|16 | STATEFUL-PCE-CAPABILITY | [RFC8231] | YES |
|17 | SYMBOLIC-PATH-NAME | [RFC8231] | YES |
|18 | IPV4-LSP-IDENTIFIERS | [RFC8231] | YES |
|19 | IPV6-LSP-IDENTIFIERS | [RFC8231] | NO |
|20 | LSP-ERROR-CODE | [RFC8231] | YES |
|21 | RSVP-ERROR-SPEC | [RFC8231] | YES |
|23 | LSP-DB-VERSION | [RFC8232] | YES |
|24 | SPEAKER-ENTITY-ID | [RFC8232] | YES |
|26 | SR-PCE-CAPABILITY (deprecated) | [RFC8664] | YES |
|28 | PATH-SETUP-TYPE | [RFC8408] | YES | 
|29 | Operator-configured Association Range | [RFC8697] | NO |
|30 | Global Association Source | [RFC8697] | NO |
|31 | Extended Association ID | [RFC8697] | NO |
|32 | P2MP-IPV4-LSP-IDENTIFIERS | [RFC8623] | NO |
|33 | P2MP-IPV6-LSP-IDENTIFIERS | [RFC8623] | NO |
|34 | PATH-SETUP-TYPE-CAPABILITY | [RFC8408] | NO |
|35 | ASSOC-Type-List | [RFC8697] | NO |
|36 | AUTO-BANDWIDTH-CAPABILITY | [RFC8733] | NO |
|37 | AUTO-BANDWIDTH-ATTRIBUTES | [RFC8733] | NO |
|38 | Path Protection Association Group TLV | [RFC8745] | NO |
|39 | IPV4-ADDRESS | [RFC8779 Section 2.5.2.1]  | YES |
|40 | IPV6-ADDRESS | [RFC8779 Section 2.5.2.2]  | NO |
|41 | UNNUMBERED-ENDPOINT | [RFC8779 Section 2.5.2.3]  | YES |
|42 | LABEL-REQUEST | [RFC8779]  | YES | 
|43 | LABEL-SET | [RFC8779 Section 2.5.2.5] | NO |
|44 | PROTECTION-ATTRIBUTE | [RFC8779 Section 2.8] | NO |
|45 | GMPLS-CAPABILITY | [RFC8779 Section 2.1.2] | YES | 
|46 | DISJOINTNESS-CONFIGURATION | [RFC8800] | NO |
|47 | DISJOINTNESS-STATUS | [RFC8800] | NO |
|48 | "POLICY-PARAMETERS-TLV  | [draft-ietf-pce-association-policy-09] | NO |
|49 | SCHED-LSP-ATTRIBUTE | [RFC8934] | NO |
|50 | SCHED-PD-LSP-ATTRIBUTE | [RFC8934] | NO |
|51 | PCE-FLOWSPEC-CAPABILITY TLV | [RFC-ietf-pce-pcep-flowspec-12] | NO |
|52 | FLOW FILTER TLV | [RFC-ietf-pce-pcep-flowspec-12] | NO |
|53 | L2 FLOW FILTER TLV | [RFC-ietf-pce-pcep-flowspec-12] | NO |

EXPERIMENTAL
----

### Experimental proprietary messages

* PCEPTELinkSuggestion
* PCEPTELinkConfirmation
* PCEPTELinkTearDownSuggestion

### Experimental proprietary objects

* AdvanceReservation Object
* BitmapLabelSet Object
* NetQuotationIPv4
* NetQuotationIPv6
* NetQuotationNSAP
* Reservation
* ReservationConf
* SuggestedLabel

### Experimental proprietary TLVs

* BandwidthTLV
* EndPointApplicationTLV
* EndPointServerTLV
* EndPointsNSAPTLV
* EndPointStorageTLV
* ITAdvertisementTLV
* MaxRequestTimeTLV
* OSPFTE_LSA_TLV
* ReachabilityTLV
* ReservationIDTLV
* ServerTLV
* StorageTLV
* XifiEndPointTLV