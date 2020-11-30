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

* Bandwidth
* Close
* EndPoints
* ExcludeRouteObject
* ExplicitRouteObject
* IncludeRouteObject
* InterLayer
* LoadBalancing
* LSP
* LSPA
* Metric
* Monitoring
* NoPath
* Notification
* ObjectiveFunction
* ObjectParameters
* OPEN
* PccReqId
* PceId
* PCEPError Object
* ProcTime
* ReportedRouteObject
* ReqAdapCap
* RequestParameters
* ServerIndication
* SRP
* Svec
* SwitchLayer
* WavelengthAssignementObject

### Experimental propietary object

* AdvanceReservation Object
* BitmapLabelSet Object
* NetQuotationIPv4
* NetQuotationIPv6
* NetQuotationNSAP
* Reservation
* ReservationConf
* SuggestedLabel

TLVs
----

### Standard (RFC & IETF draft) TLVs

* DomainID TLV (with non-standard encoding)
* EndPointIPv4TLV
* GMPLSCapabilityTLV
* LabelRequestTLV (encoding TBD)
* LSPDatabaseVersion TLV
* LSPErrorCodeTLV
* LSPIdentifiersTLV
* NoPathTLV
* OF_LIST_TLV
* OverloadedDurationTLV
* PathReservationTLV
* PathSetupTLV
* PCE_ID_TLV
* PCE_Redundancy_Group_Identifier_TLV
* ReqMissingTLV
* RequestInfoTLV
* RSVPErrorSpecTLV
* SRCapabilityTLV
* StatefulCapabilityTLV
* SymbolicPathNameTLV
* TunnelIDTLV
* UnnumberedEndpointTLV

### Experimental propietary messages

* PCEPTELinkSuggestion
* PCEPTELinkConfirmation
* PCEPTELinkTearDownSuggestion


### Experimental propietary TLVs

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