package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class PrefixSIDPrefixAttribTLV extends BGP4TLVFormat {
    private int sidIndex;

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
        this.setTLVValueLength(8); // Longitud típica de 8 bytes para Prefix SID
        this.tlv_bytes = new byte[this.getTotalTLVLength()];
        encodeHeader();
        
        // Offset 4-7: Flags y reservados (0)
        this.tlv_bytes[4] = 0;
        this.tlv_bytes[5] = 0;
        this.tlv_bytes[6] = 0;
        this.tlv_bytes[7] = 0;

        // Offset 8-11: SID Index
        this.tlv_bytes[8] = (byte) (sidIndex >>> 24);
        this.tlv_bytes[9] = (byte) (sidIndex >> 16 & 0xff);
        this.tlv_bytes[10] = (byte) (sidIndex >> 8 & 0xff);
        this.tlv_bytes[11] = (byte) (sidIndex & 0xff);
    }

    protected void decode() {
        int bwi = 0;
        for (int k = 0; k < 4; k++) {
            // Saltamos los primeros 4 bytes de cabecera y 4 de flags
            bwi = (bwi << 8) | (this.tlv_bytes[k + 8] & 0xff);
        }
        this.sidIndex = bwi;
    }

    public int getSidIndex() { return sidIndex; }
    public void setSidIndex(int sidIndex) { this.sidIndex = sidIndex; }

    @Override
    public String toString() {
        return " \t> Prefix SID Index: " + Integer.toString(sidIndex);
    }
}