package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.SidLabelNodeAttribTLV;
import java.util.Objects;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SR Capabilities TLV (Type 1034) conforme a RFC 9085.
 * * Esta TLV se utiliza para anunciar las capacidades de Segment Routing de un nodo,
 * incluyendo el rango de etiquetas (SRGB) y sub-TLVs adicionales como SID/Label.
 * * Estructura:
 * - Tipo: 1034 (2 bytes)
 * - Longitud: Variable (2 bytes)
 * - Flags: 1 byte
 * - Reserved: 1 byte
 * - Range Size: 3 bytes
 * - Sub-TLVs: Variable (ej. SID/Label Sub-TLV 1161)
 * * @author pac & isdr
 */
public class SRCapabilitiesTLV extends BGP4TLVFormat {

    private static final Logger log = LoggerFactory.getLogger("BGP4Parser");

    private int rangeSize;
    private int flags;
    private int reserved;
    private SidLabelNodeAttribTLV sidLabelSubTLV;

    public SRCapabilitiesTLV() {
        super();
        this.setTLVType(
                LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_SR_CAPABILITIES
        );
    }

    public SRCapabilitiesTLV(byte[] bytes, int offset) {
        super(bytes, offset);
        decode();
    }

    @Override
    public void encode() {
        int subLen = 0;
        if (sidLabelSubTLV != null) {
            sidLabelSubTLV.encode();
            subLen = sidLabelSubTLV.getTotalTLVLength();
            if (subLen == 0) {
                sidLabelSubTLV = null;
            }
        }
        
        this.setTLVValueLength(5 + subLen);
        this.tlv_bytes = new byte[this.getTotalTLVLength()];
        encodeHeader();
        
        int offset = 4;
        this.tlv_bytes[offset] = (byte) flags;
        offset++;
        this.tlv_bytes[offset] = (byte) reserved;
        offset++;
        
        this.tlv_bytes[offset] = (byte) ((rangeSize >>> 16) & 0xFF);
        this.tlv_bytes[offset + 1] = (byte) ((rangeSize >>> 8) & 0xFF);
        this.tlv_bytes[offset + 2] = (byte) (rangeSize & 0xFF);
        offset += 3;
        
        if (sidLabelSubTLV != null && subLen > 0) {
            System.arraycopy(
                sidLabelSubTLV.getTlv_bytes(),
                0,
                this.tlv_bytes,
                offset,
                subLen
            );
        }
        log.debug("SRCapabilitiesTLV codificada: RangeSize={}, Flags={}, SubLen={}", rangeSize, flags, subLen);
    }

    protected void decode() {
        int offset = 4;
        int valueLength = this.getTLVValueLength();

        log.debug("SRCapabilitiesTLV.decode() - INICIO. ValueLength: {}", valueLength);

        if (valueLength < 5) {
            log.warn("SRCapabilitiesTLV: Longitud insuficiente ({} < 5), abortando decode", valueLength);
            return;
        }

        this.flags = this.tlv_bytes[offset] & 0xFF;
        this.reserved = this.tlv_bytes[offset + 1] & 0xFF;
        offset += 2;

        this.rangeSize =
                ((this.tlv_bytes[offset] & 0xFF) << 16) |
                ((this.tlv_bytes[offset + 1] & 0xFF) << 8) |
                (this.tlv_bytes[offset + 2] & 0xFF);

        offset += 3;

        int remaining = valueLength - 5;
        log.debug("Flags: {}, Reserved: {}, RangeSize: {}, Bytes restantes: {}", flags, reserved, rangeSize, remaining);

        if (remaining > 0) {
            log.debug("Intentando decodificar Sub-TLV en offset: {}", offset);
            this.sidLabelSubTLV = new SidLabelNodeAttribTLV(this.tlv_bytes, offset);
            if (this.sidLabelSubTLV != null) {
                log.debug("Sub-TLV decodificada correctamente: SID={}", this.sidLabelSubTLV.getSid());
            }
        } else {
            this.sidLabelSubTLV = null;
        }
        log.debug("SRCapabilitiesTLV.decode() - FIN");
    }

    public int getRangeSize() { return rangeSize; }
    public void setRangeSize(int rangeSize) { this.rangeSize = rangeSize; }

    public int getFlags() { return flags; }
    public void setFlags(int flags) { this.flags = flags; }

    public int getReserved() { return reserved; }
    public void setReserved(int reserved) { this.reserved = reserved; }

    public SidLabelNodeAttribTLV getSidLabelSubTLV() { return sidLabelSubTLV; }
    public void setSidLabelSubTLV(SidLabelNodeAttribTLV sidLabelSubTLV) {
        if (sidLabelSubTLV != null) {
            sidLabelSubTLV.encode();
            if (sidLabelSubTLV.getTotalTLVLength() <= 4) { 
                this.sidLabelSubTLV = null;
            } else {
                this.sidLabelSubTLV = sidLabelSubTLV;
            }
        } else {
            this.sidLabelSubTLV = null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        if (!super.equals(obj)) {
            log.debug("SRCapabilitiesTLV: super.equals() falló. Tipos o longitudes de cabecera no coinciden.");
            return false;
        }
        
        SRCapabilitiesTLV other = (SRCapabilitiesTLV) obj;
        
        if (rangeSize != other.rangeSize || flags != other.flags || reserved != other.reserved) {
            log.debug("SRCapabilitiesTLV: Diferencia en campos básicos (Range/Flags/Reserved)");
            return false;
        }
        
        if (!Objects.equals(sidLabelSubTLV, other.sidLabelSubTLV)) {
            log.debug("SRCapabilitiesTLV: Diferencia en sidLabelSubTLV");
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getTLVType(),
                rangeSize,
                sidLabelSubTLV,
                flags,
                reserved
        );
    }

    @Override
    public String toString() {
        return "SRCapabilities [RangeSize=" + rangeSize + ", Flags=" + flags + ", SubTLV=" + sidLabelSubTLV + "]";
    }
}