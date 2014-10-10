package es.tid.pce.pcep.objects;

import es.tid.protocol.commons.ByteHandler;

public class XifiEndPoints extends EndPoints
{
  private String sourceSwitchID;
  private String destSwitchID;
  private int source_port;
  private int destination_port;
  private String SourceMAC;
  private String DestinationMAC;

  public XifiEndPoints()
  {
    setObjectClass(4);
    setOT(ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_MAC_NOT_UNICAST);
  }

  public XifiEndPoints(byte[] bytes, int offset)
    throws MalformedPCEPObjectException
  {
    super(bytes, offset);
    decode();
  }

  public void encode()
  {
    this.ObjectLength = 44;
    this.object_bytes = new byte[this.ObjectLength];
    encode_header();
    System.arraycopy(ByteHandler.MACFormatStringtoByteArray(this.sourceSwitchID), 0, this.object_bytes, 4, 8);
    System.arraycopy(ByteHandler.MACFormatStringtoByteArray(this.destSwitchID), 0, this.object_bytes, 12, 8);

    int offset = 20;
    ByteHandler.IntToBuffer(0, offset * 8, 32, this.source_port, this.object_bytes);

    offset += 4;
    ByteHandler.IntToBuffer(0, offset * 8, 32, this.destination_port, this.object_bytes);

    offset += 4;
    System.arraycopy(ByteHandler.MACFormatStringtoByteArray(this.SourceMAC), 0, this.object_bytes, offset, 6);

    offset += 6;
    System.arraycopy(ByteHandler.MACFormatStringtoByteArray(this.DestinationMAC), 0, this.object_bytes, offset, 6);
  }

  public void decode()
    throws MalformedPCEPObjectException
  {
    if (this.ObjectLength != 44) {
      throw new MalformedPCEPObjectException();
    }
    byte[] mac = new byte[8];
    System.arraycopy(this.object_bytes, 4, mac, 0, 8);
    this.sourceSwitchID = ByteHandler.ByteMACToString(mac);

    System.arraycopy(this.object_bytes, 12, mac, 0, 8);
    this.destSwitchID = ByteHandler.ByteMACToString(mac);

    int offset = 20;

    this.source_port = ByteHandler.easyCopy(0, 31, new byte[] { this.object_bytes[offset], this.object_bytes[(offset + 1)], this.object_bytes[(offset + 2)], this.object_bytes[(offset + 3)] });

    offset += 4;
    this.destination_port = ByteHandler.easyCopy(0, 31, new byte[] { this.object_bytes[offset], this.object_bytes[(offset + 1)], this.object_bytes[(offset + 2)], this.object_bytes[(offset + 3)] });

    offset += 4;
    System.arraycopy(this.object_bytes, offset, mac, 0, 6);
    this.SourceMAC = ByteHandler.ByteMACToString(mac);

    offset += 6;
    System.arraycopy(this.object_bytes, offset, mac, 0, 6);
    this.DestinationMAC = ByteHandler.ByteMACToString(mac);
  }

  public String getSwitchSourceID()
  {
    return this.sourceSwitchID;
  }

  public void setSwitchSourceID(String sourceMAC)
  {
    this.sourceSwitchID = sourceMAC;
  }

  public String getSwitchDestinationID()
  {
    return this.destSwitchID;
  }

  public void setSwitchDestinationID(String destMAC)
  {
    this.destSwitchID = destMAC;
  }

  public String toString()
  {
    return "Source MAC: " + this.sourceSwitchID + " Destination MAC: " + this.destSwitchID;
  }

  public int getSource_port() {
    return this.source_port;
  }

  public void setSource_port(int source_port) {
    this.source_port = source_port;
  }

  public int getDestination_port()
  {
    return this.destination_port;
  }

  public void setDestination_port(int destination_port)
  {
    this.destination_port = destination_port;
  }

  public String getSourceMAC() {
    return this.SourceMAC;
  }

  public void setSourceMAC(String sourceMAC)
  {
    this.SourceMAC = sourceMAC;
  }

  public String getDestinationMAC()
  {
    return this.DestinationMAC;
  }

  public void setDestinationMAC(String destinationMAC)
  {
    this.DestinationMAC = destinationMAC;
  }
}