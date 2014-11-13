Implemented PCEP messages syntax
====================================

This file shows the implemented syntax.
Beware that it may not represent or be fully updated to the latest version of a draft

Syntax generated with jPCEPGrammar

Open Message
------------
```
Open Message::=
<Common Header><OPEN>
```

KeepAlive Message
-----------------
```
Keepalive Message::=
<Common Header>
```

PCReq Message
-------------
```PCReq Message::= <Common Header>[<svec-tuple-list>]<request-list>  ```

```
PCReq Message::=
				<Common Header>[<svec-tuple-list>]<request-list>
<svec-tuple-list>::=
					<svec-tuple>[<svec-tuple-list>]
<svec-tuple>::=
				<SVEC>[<OF>][<metric-list>][<GC>][<XRO>]
<metric-list>::=
<				METRIC>[<metric-list>]
<request-list>::=
				<request>[<request-list>]
<request>::=
			<RP><segment-computation>|<path-key-expansion>
<segment-computation>::=
						<END-POINTS>[<CLASSTYPE>][<LSPA>][<BANDWIDTH>][<metric-list>][<OF>][<rro-bw-pair>][<IRO>][<LOAD-BALANCING>][<XRO>]
<rro-bw-pair>::=
				<RRO>[<BANDWIDTH>]
<path-key-expansion>::=
						<PATH-KEY>
     
```
PCRep Message
-------------

PCNtf Message
-------------

PCErr Message
-------------

Close Message
-------------

* PCMonReq Message
* PCMonRep Message
* PCRpt Message
* PCUpd Message
* PCInitiate Message

### Experimental propierary messages

* PCEPTELinkSuggestion
* PCEPTELinkConfirmation
* PCEPTELinkTearDownSuggestion



