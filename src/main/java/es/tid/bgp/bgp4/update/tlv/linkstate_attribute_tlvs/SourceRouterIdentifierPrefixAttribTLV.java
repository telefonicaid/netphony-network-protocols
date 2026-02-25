package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Source Router Identifier TLV según RFC 7794 / RFC 9084.https://datatracker.ietf.org/doc/rfc7794/ 
 */
public class SourceRouterIdentifierPrefixAttribTLV extends BGP4TLVFormat {

    private static final Logger log = LoggerFactory.getLogger(SourceRouterIdentifierPrefixAttribTLV.class);
    private InetAddress sourceRouterID;

    public SourceRouterIdentifierPrefixAttribTLV() {
        super();
        this.setTLVType(LinkStateAttributeTLVTypes.PREFIX_ATTRIBUTE_TLV_TYPE_SOURCE_ROUTER_ID);
    }

    public SourceRouterIdentifierPrefixAttribTLV(byte[] bytes, int offset) {
        super(bytes, offset);
        decode();
    }

    @Override
    public void encode() {
        if (sourceRouterID == null) return;
        
        byte[] addr = sourceRouterID.getAddress();
        this.setTLVValueLength(addr.length);
        this.tlv_bytes = new byte[this.getTotalTLVLength()];
        encodeHeader();
        System.arraycopy(addr, 0, this.tlv_bytes, 4, addr.length);
    }

    protected void decode() {
        if (this.tlv_bytes == null) return;

        log.info("SOURCE ROUTER ID BYTES RAW: " + es.tid.protocol.commons.ByteHandler.ByteMACToString(this.tlv_bytes));

        int valueLen = this.getTLVValueLength();
        byte[] ipBytes;

        try {
            // Caso 1: Longitud exacta IPv4 
            if (valueLen == 4) {
                ipBytes = new byte[4];
                System.arraycopy(this.tlv_bytes, 4, ipBytes, 0, 4);
                this.sourceRouterID = InetAddress.getByAddress(ipBytes);
            } 
            // Caso 2: Longitud con Padding 
            else if (valueLen > 4 && valueLen < 16) {
                ipBytes = new byte[4];
                boolean found = false;
                // Escaneamos buscando los primeros 4 bytes que NO sean 0.0.0.0
                for (int i = 4; i <= (this.tlv_bytes.length - 4); i++) {
                    if (tlv_bytes[i] != 0 || tlv_bytes[i+1] != 0 || tlv_bytes[i+2] != 0 || tlv_bytes[i+3] != 0) {
                        System.arraycopy(this.tlv_bytes, i, ipBytes, 0, 4);
                        this.sourceRouterID = InetAddress.getByAddress(ipBytes);
                        found = true;
                        break;
                    }
                }
                if (!found) log.warn("No se encontró IP en TLV con padding");
            }
            // Caso 3: IPv6
            else if (valueLen >= 16) {
                ipBytes = new byte[16];
                // Copiamos los últimos 16 por si hay padding inicial
                int lastPos = 4 + valueLen - 1;
                System.arraycopy(this.tlv_bytes, lastPos - 15, ipBytes, 0, 16);
                this.sourceRouterID = InetAddress.getByAddress(ipBytes);
            }

            if (this.sourceRouterID != null) {
                log.info("Source Router ID Decodificado: {}", sourceRouterID.getHostAddress());
            }

        } catch (Exception e) {
            log.error("Error decodificando Source Router ID: " + e.getMessage());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SourceRouterIdentifierPrefixAttribTLV other = (SourceRouterIdentifierPrefixAttribTLV) obj;
        
        if (this.sourceRouterID == null && other.sourceRouterID == null) return true;
        if (this.sourceRouterID == null || other.sourceRouterID == null) return false;
        
        return Objects.equals(this.sourceRouterID.getHostAddress(), other.sourceRouterID.getHostAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTLVType(), (sourceRouterID != null ? sourceRouterID.getHostAddress() : 0));
    }

    public InetAddress getSourceRouterID() { return sourceRouterID; }
    public void setSourceRouterID(InetAddress sourceRouterID) { this.sourceRouterID = sourceRouterID; }

    @Override
    public String toString() {
        return "SourceRouterID [" + (sourceRouterID != null ? sourceRouterID.getHostAddress() : "null") + "]";
    }
}