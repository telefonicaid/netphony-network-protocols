package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import org.slf4j.Logger;         
import org.slf4j.LoggerFactory;
import java.util.Objects;

/**
 * Prefix-SID Sub-TLV según RFC 8667. https://datatracker.ietf.org/doc/rfc8667/  
 * Flags: |R|N|P|E|V|L| - | - |
 * V-Flag: Value bit.
 * L-Flag: Local bit.
 */
public class PrefixSIDPrefixAttribTLV extends BGP4TLVFormat {

    private static final Logger log = LoggerFactory.getLogger(PrefixSIDPrefixAttribTLV.class);
    private int flags;
    private int algorithm;
    private long sidIndex = -1;
    private int label = -1;

    public PrefixSIDPrefixAttribTLV() {
        super();
        this.setTLVType(LinkStateAttributeTLVTypes.PREFIX_ATTRIBUTE_TLV_TYPE_PREFIX_SID); 
    }

    public PrefixSIDPrefixAttribTLV(byte[] bytes, int offset) {
        super(bytes, offset);
        decode();
    }

    @Override
    public void encode() {
        // Determinamos si es Label o Index según las FLAGS (Bits V y L) -> VyL=1: 3-octet local label / VyL=0: 4-octet index
        // L-flag es el bit 0x04, V-flag es el bit 0x08 (en el octeto de flags)
        boolean isLabel = ((flags & 0x04) != 0) || ((flags & 0x08) != 0);
        
        int valueLength = isLabel ? 5 : 6; // Label=3 bytes valor + 2 header | Index=4 bytes valor + 2 header
        this.setTLVValueLength(valueLength);
        this.tlv_bytes = new byte[this.getTotalTLVLength()];
        encodeHeader();
        
        int pos = 4; 
        this.tlv_bytes[pos++] = (byte) (flags & 0xFF);
        this.tlv_bytes[pos++] = (byte) (algorithm & 0xFF);

        if (isLabel) {
            // Encode Label (20 bits en 3 octetos)
            this.tlv_bytes[pos++] = (byte) ((label >> 12) & 0xFF);
            this.tlv_bytes[pos++] = (byte) ((label >> 4) & 0xFF);
            this.tlv_bytes[pos]   = (byte) ((label << 4) & 0xFF);
        } else {
            // Encode Index (32 bits en 4 octetos)
            this.tlv_bytes[pos++] = (byte) ((sidIndex >>> 24) & 0xFF);
            this.tlv_bytes[pos++] = (byte) ((sidIndex >>> 16) & 0xFF);
            this.tlv_bytes[pos++] = (byte) ((sidIndex >>> 8) & 0xFF);
            this.tlv_bytes[pos]   = (byte) (sidIndex & 0xFF);
        }
    }

    protected void decode() {
        if (this.tlv_bytes == null) return;

        this.flags = this.tlv_bytes[4] & 0xFF;
        this.algorithm = this.tlv_bytes[5] & 0xFF;

        // Validamos flags V o L para saber qué leer
        boolean isLabel = ((flags & 0x04) != 0) || ((flags & 0x08) != 0);
        int valueLength = this.getTLVValueLength();

        log.debug("Decoding Prefix-SID: isLabel={}, algorithm={}, length={}", isLabel, algorithm, valueLength);

        if (isLabel && valueLength == 5) {
            this.label = ((tlv_bytes[6] & 0xFF) << 12) |
                         ((tlv_bytes[7] & 0xFF) << 4) |
                         ((tlv_bytes[8] & 0xFF) >> 4);
            this.sidIndex = -1; // Limpiamos para el equals
        } else if (!isLabel && valueLength == 6) {
            this.sidIndex = ((long)(tlv_bytes[6] & 0xFF) << 24) |
                            ((long)(tlv_bytes[7] & 0xFF) << 16) |
                            ((long)(tlv_bytes[8] & 0xFF) << 8) |
                            ((long)(tlv_bytes[9] & 0xFF));
            this.label = -1; // Limpiamos para el equals
        } else {
            log.warn("Invalid Prefix-SID format: Flags indicate isLabel={}, but valueLength is {}", isLabel, valueLength);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PrefixSIDPrefixAttribTLV other = (PrefixSIDPrefixAttribTLV) obj;
        
        // Comparación estricta de Flags y Algoritmo
        if (this.flags != other.flags || this.algorithm != other.algorithm) return false;

        // Comparamos según lo que digan las flags
        boolean isLabel = ((flags & 0x04) != 0) || ((flags & 0x08) != 0);
        if (isLabel) {
            return this.label == other.label;
        } else {
            return this.sidIndex == other.sidIndex;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTLVType(), flags, algorithm, sidIndex, label);
    }

    // Getters y Setters
    public long getSidIndex() { return sidIndex; }
    public void setSidIndex(long sidIndex) { this.sidIndex = sidIndex; }
    public int getLabel() { return label; }
    public void setLabel(int label) { this.label = label; }
    public int getFlags() { return flags; }
    public void setFlags(int flags) { this.flags = flags; }
    public int getAlgorithm() { return algorithm; }
    public void setAlgorithm(int algorithm) { this.algorithm = algorithm; }

    @Override
    public String toString() {
        boolean isLabel = ((flags & 0x04) != 0) || ((flags & 0x08) != 0);
        return "PrefixSID [" + (isLabel ? "LABEL=" + label : "INDEX=" + sidIndex) + 
               ", flags=" + Integer.toBinaryString(flags) + ", algorit=" + algorithm + "]";
    }
}