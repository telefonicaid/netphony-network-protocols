package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.LinkStateAttributeTLVTypes;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SID/Label Sub-TLV (Type 1161) conforme a RFC 9085. https://www.rfc-editor.org/rfc/rfc9085 
 * * Esta Sub-TLV se utiliza en Segment Routing para anunciar el Segment Identifier (SID) 
 * o etiqueta MPLS asociada a un nodo o enlace.
 * * Estructura:
 * - Tipo: 1161 (2 bytes)
 * - Longitud: 3 o 4 bytes (2 bytes)
 * - Valor: Etiqueta de 20 bits (3 bytes) o SID Index de 32 bits (4 bytes).
 * * @author isdr
 */
public class SidLabelNodeAttribTLV extends BGP4TLVFormat {
    private static final Logger log = LoggerFactory.getLogger("BGP4Parser");
    private int sid;

    public SidLabelNodeAttribTLV() {
        super();
        this.setTLVType(LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_SID_LABEL);
    }

    public SidLabelNodeAttribTLV(byte[] bytes, int offset) {
        super(bytes, offset);
        decode();
    }

    @Override
    public void encode() {
        int len = (sid > 0xFFFFFF || sid < 0) ? 4 : 3;
        this.setTLVValueLength(len);
        this.tlv_bytes = new byte[this.getTotalTLVLength()]; 
        encodeHeader();
        
        int offset = 4;
        if (len == 4) {
            this.tlv_bytes[offset]     = (byte)(sid >>> 24 & 0xff);
            this.tlv_bytes[offset + 1] = (byte)(sid >>> 16 & 0xff);
            this.tlv_bytes[offset + 2] = (byte)(sid >>> 8 & 0xff);
            this.tlv_bytes[offset + 3] = (byte)(sid & 0xff);
        } else {
            this.tlv_bytes[offset]     = (byte)(sid >>> 16 & 0xff);
            this.tlv_bytes[offset + 1] = (byte)(sid >>> 8 & 0xff);
            this.tlv_bytes[offset + 2] = (byte)(sid & 0xff);
        }
        log.debug("SidLabelNodeAttribTLV codificado con SID: {}", this.sid);
    }

    protected void decode() {
        int offset = 4;
        int len = this.getTLVValueLength();
        this.sid = 0;
        
        if (len == 3) {
            this.sid = ((this.tlv_bytes[offset] & 0xFF) << 16) | 
                       ((this.tlv_bytes[offset + 1] & 0xFF) << 8) | 
                       (this.tlv_bytes[offset + 2] & 0xFF);
        } else if (len == 4) {
            for (int k = 0; k < 4; k++) {
                this.sid = (this.sid << 8) | (this.tlv_bytes[offset + k] & 0xff);
            }
        } else {
            log.warn("Longitud inesperada en SidLabelNodeAttribTLV (Type 1161): {}", len);
        }
        log.debug("SidLabelNodeAttribTLV decodificado. SID encontrado: {}", this.sid);
    }

    public int getSid() { return sid; }
    public void setSid(int sid) { this.sid = sid; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        if (!super.equals(obj)) {
            return false;
        }
        
        SidLabelNodeAttribTLV other = (SidLabelNodeAttribTLV) obj;
        return sid == other.sid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTLVType(), sid);
    }

    @Override
    public String toString() {
        return "SID/Label [SID=" + sid + "]";
    }
}