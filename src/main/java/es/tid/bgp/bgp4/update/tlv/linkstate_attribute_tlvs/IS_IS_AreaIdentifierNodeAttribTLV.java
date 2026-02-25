package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import java.util.Arrays;
import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 * IS-IS Area Identifier TLV (Type 1027) [RFC7752, Section 3.3.1.2]
 * * Basado en RFC 7752: "The IS-IS Area Identifier TLV may be present 
 * in the BGP-LS attribute only when advertised in the Link-State Node NLRI.
 * The value contains the variable-length Area Identifier."
 * * @author pac
 * @author isdr
 */
public class IS_IS_AreaIdentifierNodeAttribTLV extends BGP4TLVFormat {

    /* El Area ID en IS-IS es binario y de longitud variable */
    private byte[] areaID;

    public IS_IS_AreaIdentifierNodeAttribTLV() {
        super();
        this.setTLVType(LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_IS_IS_AREA_ID);
    }

    public IS_IS_AreaIdentifierNodeAttribTLV(byte[] bytes, int offset) {
        super(bytes, offset);
        decode();
    }

    @Override
    public void encode() {
        if (areaID == null) {
            areaID = new byte[0];
        }
        this.setTLVValueLength(areaID.length);
        this.setTlv_bytes(new byte[this.getTotalTLVLength()]);
        encodeHeader();
        System.arraycopy(areaID, 0, this.tlv_bytes, 4, areaID.length);
    }

    public void decode() {
        int len = this.getTLVValueLength();
        if (len > 0) {
            this.areaID = new byte[len];
            System.arraycopy(this.tlv_bytes, 4, this.areaID, 0, len);
        }
    }

    public byte[] getAreaID() {
        return areaID;
    }

    public void setAreaID(byte[] areaID) {
        this.areaID = areaID;
    }

    @Override
    public String toString() {
        if (areaID == null || areaID.length == 0) {
            return "ISIS AREA [NONE]";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < areaID.length; i++) {
            sb.append(String.format("%02x", areaID[i]));
            if ((i + 1) % 2 == 1 && i != areaID.length - 1 && areaID.length > 3) {
            }
        }
        return sb.toString().toUpperCase();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        IS_IS_AreaIdentifierNodeAttribTLV other = (IS_IS_AreaIdentifierNodeAttribTLV) obj;
        return Arrays.equals(areaID, other.areaID);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(areaID);
        return result;
    }
}