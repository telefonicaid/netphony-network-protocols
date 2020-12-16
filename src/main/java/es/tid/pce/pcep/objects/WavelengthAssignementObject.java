package es.tid.pce.pcep.objects;

/**
 * Wavelength Assignement Object.
 * 
 * http://tools.ietf.org/html/draft-lee-pce-wson-rwa-ext-03
 * 
 * And additions..
 * 
 *  The format of the Wavelength Assignment (WA) object body is as
   follows:

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                           Flags                       |  O  |M|
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      //                      Optional TLVs                          //
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                            Figure 3 WA Object

   o  Flags (32 bits)

   The following new flags SHOULD be set

     . M (Mode - 1 bit): M bit is used to indicate the mode of
        wavelength assignment. When M bit is set to 1, this indicates
        that the label assigned by the PCE must be explicit. That is,
        the selected way to convey the allocated wavelength is by means
        of Explicit Label Control (ELC) [RFC4003] for each hop of a
        computed LSP. Otherwise, the label assigned by the PCE needs
        not be explicit (i.e., it can be suggested in the form of label
        set objects in the corresponding response, to allow distributed
        WA. In such case, the PCE MUST return a Label_Set object in the
        response if the path is found.

   (Ed note: When the distributed WA is applied, some specific
   wavelength range and/or the maximum number of wavelengths to be
   returned in the Label Set might be additionally indicated. The
   optional TLV field will be used for conveying this additional
   request. The details of this encoding will be provided in a later
   revision.)

     . O (Order - 3 bits): O bit is used to indicate the wavelength
        assignment constraint in regard to the order of wavelength
        assignment to be returned by the PCE. This case is only applied
        when M bit is set to "explicit." The following indicators
        should be defined:

         000 - Reserved

         001 - Random Assignment

         010 - First Fit (FF) in descending Order

         011 - First Fit (FF) in ascending Order

         100 - Last Fit (LF) in ascending Order

         101 - Last Fit (LF) in descending Order

         110 - Vendor Specific/Private

         111 - Reserved

   When the Order bit is set for "Vendor Specific/Private", the
   optional TLV field will be used to indicate specifics of the order
   algorithm applied by the PCE.
 * @author ogondio
 *
 */
public class WavelengthAssignementObject extends PCEPObject{
	
	//private boolean mode;
	
	//private int order;
	
	private boolean retry;

	public WavelengthAssignementObject(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_WAVELENGTH_ASSIGNEMENT);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_WAVELENGTH_ASSIGNEMENT);
	}

	public WavelengthAssignementObject(byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	public void encode() {
		ObjectLength=8;/* 4 bytes de la cabecera + 4 del cuerpo */
		object_bytes=new byte[ObjectLength];
		encode_header();
		this.object_bytes[4]=0;
		this.object_bytes[5]=0;
		this.object_bytes[6]=0;
		this.object_bytes[7]=(byte)((retry==true)?0x10:0);			
	}

	@Override
	public void decode() throws MalformedPCEPObjectException {
		retry=(this.object_bytes[7]&0x10)==0x10;	
	}

	public boolean isRetry() {
		return retry;
	}

	public void setRetry(boolean retry) {
		this.retry = retry;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (retry ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		WavelengthAssignementObject other = (WavelengthAssignementObject) obj;
		if (retry != other.retry)
			return false;
		return true;
	}
	
	
	

}
