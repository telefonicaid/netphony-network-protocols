package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.EndPointDataPathTLV;
import es.tid.pce.pcep.objects.tlvs.EndPointUnnumberedDataPathTLV;
import es.tid.pce.pcep.objects.tlvs.EndPointIPv4TLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.pce.pcep.objects.tlvs.UnnumberedEndpointTLV;
import es.tid.pce.pcep.objects.tlvs.XifiEndPointTLV;

public abstract class EndPoint extends PCEPConstruct {

	public EndPoint() {

	}
}