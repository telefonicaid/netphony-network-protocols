package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import java.nio.charset.StandardCharsets;

public class NodeNameNodeAttribTLV extends BGP4TLVFormat {
    
    private byte[] name;

    public NodeNameNodeAttribTLV() {
        super();
        this.setTLVType(LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_NODE_NAME);
    }
    
    public NodeNameNodeAttribTLV(byte[] bytes, int offset) {     
        super(bytes, offset);        
        this.decode();
    }

    public void encode() {
        if (this.name == null) {
            this.name = new byte[0];
        }
        this.setTLVValueLength(this.name.length);
        this.tlv_bytes = new byte[this.getTotalTLVLength()];
        this.encodeHeader();
        System.arraycopy(this.name, 0, this.tlv_bytes, 4, this.name.length);
    }
    
   
    public void decode() {
        int length = this.getTLVValueLength();
        this.name = new byte[length];
        try {
            System.arraycopy(this.tlv_bytes, 4, this.name, 0, length);
        } catch (Exception e) {
            
        }
    }
    
 
    public void setName(byte[] name) {
        this.name = name;
    }

    public byte[] getName() {
        return name;
    }

    @Override
    public String toString() {
        String strName = (name != null) ? new String(name, StandardCharsets.US_ASCII) : "null";
        return "NODE NAME [name=" + strName + "]";
    }
}