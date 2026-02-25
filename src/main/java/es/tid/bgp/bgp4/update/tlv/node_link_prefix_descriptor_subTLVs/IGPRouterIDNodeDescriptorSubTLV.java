package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IGP Router-ID Sub-TLV (Type 515) conforme a RFC 9552.
 * 
 * @author isdr 
 */
public class IGPRouterIDNodeDescriptorSubTLV extends NodeDescriptorsSubTLV {

    private static final Logger log = LoggerFactory.getLogger("BGP4Server");

    public static final int IGP_ROUTER_ID_TYPE_GENERIC = 0;
    public static final int IGP_ROUTER_ID_TYPE_IS_IS_NON_PSEUDO = 1;
    public static final int IGP_ROUTER_ID_TYPE_IS_IS_PSEUDO = 2;
    public static final int IGP_ROUTER_ID_TYPE_OSPF_NON_PSEUDO = 3;
    public static final int IGP_ROUTER_ID_TYPE_OSPF_PSEUDO = 4;

    private int igp_router_id_type;
    private Inet4Address ipv4Address_ospf = null;
    private Inet4Address ipv4Address_ospf_dr_address = null;
    private int ISIS_ISO_NODE_ID; 
    private byte[] address = null;
    private int PSN_IDENT;
    private String normalized_router_id = null; 

    public IGPRouterIDNodeDescriptorSubTLV() {
        super();
        this.setSubTLVType(NodeDescriptorsSubTLVTypes.NODE_DESCRIPTORS_SUBTLV_TYPE_IGP_ROUTER_ID);
    }
    
    public IGPRouterIDNodeDescriptorSubTLV(byte[] bytes, int offset) {
        super(bytes, offset);
        decode();
    }

    private void decode() {
        int length = this.getSubTLVValueLength();
        int offset = 4; 
        
        this.address = new byte[length];
        System.arraycopy(this.subtlv_bytes, offset, this.address, 0, length);

        this.normalized_router_id = formatToRFC9552(this.address);

        switch(length) {
            case 4:
                setIGP_router_id_type(IGP_ROUTER_ID_TYPE_OSPF_NON_PSEUDO);
                try {
                    ipv4Address_ospf = (Inet4Address) Inet4Address.getByAddress(address);
                } catch (UnknownHostException e) { log.error("Error decode OSPF ID"); }
                break;
            case 6:
                setIGP_router_id_type(IGP_ROUTER_ID_TYPE_IS_IS_NON_PSEUDO);
                processISIS(offset);
                break;
            case 7:
                setIGP_router_id_type(IGP_ROUTER_ID_TYPE_IS_IS_PSEUDO);
                processISIS(offset);
                this.PSN_IDENT = this.subtlv_bytes[offset+6] & 0xFF;
                break;
            case 8:
                setIGP_router_id_type(IGP_ROUTER_ID_TYPE_OSPF_PSEUDO);
                try {
                    byte[] temp = new byte[4];
                    System.arraycopy(address, 0, temp, 0, 4);
                    ipv4Address_ospf = (Inet4Address) Inet4Address.getByAddress(temp);
                    System.arraycopy(address, 4, temp, 0, 4);
                    ipv4Address_ospf_dr_address = (Inet4Address) Inet4Address.getByAddress(temp);
                } catch (UnknownHostException e) { log.error("Error decode OSPF Pseudo ID"); }
                break;
            default:
                setIGP_router_id_type(IGP_ROUTER_ID_TYPE_GENERIC);
        }
    }

    private void processISIS(int offset) {
        // Realizamos el cálculo en long para evitar errores de desbordamiento intermedio y hacemos cast a int al final
        long tempId = (((long)(this.subtlv_bytes[offset] & 0xFF) << 40) | 
                       ((long)(this.subtlv_bytes[offset+1] & 0xFF) << 32) | 
                       ((long)(this.subtlv_bytes[offset+2] & 0xFF) << 24) | 
                       ((long)(this.subtlv_bytes[offset+3] & 0xFF) << 16) | 
                       ((long)(this.subtlv_bytes[offset+4] & 0xFF) << 8) | 
                       ((long)(this.subtlv_bytes[offset+5] & 0xFF)));
        this.ISIS_ISO_NODE_ID = (int) (tempId & 0xFFFFFFFFL);
    }

    @Override
    public void encode() {
        if (this.address == null && this.ipv4Address_ospf != null) {
            this.address = this.ipv4Address_ospf.getAddress();
        }
        if (this.address == null) this.address = new byte[4]; 

        this.setSubTLVValueLength(this.address.length);
        this.subtlv_bytes = new byte[this.getTotalSubTLVLength()];
        encodeHeader();
        System.arraycopy(this.address, 0, this.subtlv_bytes, 4, this.address.length);
    }

    private String formatToRFC9552(byte[] bytes) {
        if (bytes == null) return "null";
        StringBuilder sb = new StringBuilder();
        
        int start = 0;
        while (start < bytes.length && bytes[start] == 0) {
            start++;
        }
   
        if (start == bytes.length) return "000";

        for (int i = start; i < bytes.length; i++) {
            sb.append(String.format("%03d", bytes[i] & 0xFF));
            if (i < bytes.length - 1) sb.append(" "); 
        }
        return sb.toString();
    }

    // --- GETTERS / SETTERS 
    public int getISIS_ISO_NODE_ID() { return ISIS_ISO_NODE_ID; }
    public void setISIS_ISO_NODE_ID(int iSIS_ISO_NODE_ID) { this.ISIS_ISO_NODE_ID = iSIS_ISO_NODE_ID; }
    
    public int getPSN_IDENT() { return PSN_IDENT; }
    public void setPSN_IDENT(int pSN_IDENT) { this.PSN_IDENT = pSN_IDENT; }
    
    public Inet4Address getIpv4AddressOSPF() { return ipv4Address_ospf; }
    public void setIpv4AddressOSPF(Inet4Address ipv4Address) { 
        this.ipv4Address_ospf = ipv4Address; 
        if (ipv4Address != null) {
            this.address = ipv4Address.getAddress();
            this.normalized_router_id = formatToRFC9552(this.address);
        }
    }
    public Inet4Address getIpv4Address_ospf_dr_address() { return ipv4Address_ospf_dr_address; }
    public void setIpv4Address_ospf_dr_address(Inet4Address addr) { this.ipv4Address_ospf_dr_address = addr; }

    public byte[] getAddress() { return address; }
    public void setAddress(byte[] addr) { 
        this.address = addr; 
        if (addr != null) this.normalized_router_id = formatToRFC9552(addr);
    }
    
    public int getIGP_router_id_type() { return igp_router_id_type; }
    public void setIGP_router_id_type(int type) { this.igp_router_id_type = type; }
    public int getIgp_router_id_type() { return igp_router_id_type; }
    public void setIgp_router_id_type(int type) { this.igp_router_id_type = type; }
    
    public Inet4Address getIpv4Address_ospf() { return ipv4Address_ospf; }
    public void setIpv4Address_ospf(Inet4Address addr) { this.setIpv4AddressOSPF(addr); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IGPRouterIDNodeDescriptorSubTLV)) return false;
        IGPRouterIDNodeDescriptorSubTLV other = (IGPRouterIDNodeDescriptorSubTLV) o;
        return Arrays.equals(address, other.address);
    }

    @Override
    public int hashCode() { return Objects.hash(super.hashCode(), Arrays.hashCode(address)); }

    @Override
    public String toString() {
        if (normalized_router_id == null && address != null) normalized_router_id = formatToRFC9552(address);
        return "IGP_ROUTER_ID [RFC9552_Format=" + normalized_router_id + "]";
    }
}