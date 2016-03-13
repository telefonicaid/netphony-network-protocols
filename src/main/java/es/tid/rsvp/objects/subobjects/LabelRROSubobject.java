package es.tid.rsvp.objects.subobjects;


/**
 <p>RFC 3209		RSVP-TE		RRO Label Object</p>
  
 <p>4.4.1.3. Subobject 3, Label

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |     Type      |     Length    |    Flags      |   C-Type      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |       Contents of Label Object                                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Type

         0x03  Label

      Length

         The Length contains the total length of the subobject in bytes,
         including the Type and Length fields.

      Flags

         0x01 = Global label
           This flag indicates that the label will be understood
           if received on any interface.

      C-Type

         The C-Type of the included Label Object.  Copied from the Label
         Object.

      Contents of Label Object

         The contents of the Label Object.  Copied from the Label Object
</p>
 * @author Fernando Munoz del Nuevo fmn@tid.es
 *
 */


public class LabelRROSubobject extends RROSubobject {

	/**
	 * <p>The contents of the Label Object.  Copied from the Label Object</p>
	 */
	
	private int contentsOfLabelObject;
	
	/**
	 * <p> 0x01 = Global label
           This flag indicates that the label will be understood
           if received on any interface.</p>
	 */
	
	private int flags;
	
	/**
	 * <p> The C-Type of the included Label Object.  Copied from the Label
         Object.</p>
	 */
	
	private int cType;
	
	
	/**
	 * Default constructor used to create a new Label RRO subobject when encoding is needed
	 */
	
	public LabelRROSubobject(){
		
		super();
		this.setType(SubObjectValues.RRO_SUBOBJECT_LABEL);
		flags = 0x01;
		
	}
	
	/**
	 * Constructor used to create a new Label RRO subobject when decoding is needed
	 * @param bytes bytes
	 * @param offset offset
	 */
	
	public LabelRROSubobject(byte [] bytes, int offset){
		
		super(bytes, offset);
		decode(bytes,offset);
		
	}
	
	/**
	 * <p>
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |     Type      |     Length    |    Flags      |   C-Type      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |       Contents of Label Object                                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   </p>
	 */
	
	public void encode(){
		
		this.rrosolength=8;
		this.subobject_bytes=new byte[this.rrosolength];
		
		encodeSoHeader();
		
		this.subobject_bytes[4]=(byte)((contentsOfLabelObject>>24) & 0xFF);
		this.subobject_bytes[5]=(byte)((contentsOfLabelObject>>16) & 0xFF);
		this.subobject_bytes[6]=(byte)((contentsOfLabelObject>>8) & 0xFF);
		this.subobject_bytes[7]=(byte)((contentsOfLabelObject) & 0xFF);
		
	}
	
	@Override
	public void decode() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * <p>
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |     Type      |     Length    |    Flags      |   C-Type      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |       Contents of Label Object                                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   </p>
   	 * @param bytes bytes
	 * @param offset offset
	 */
	
	public void decode(byte [] bytes, int offset){
		
		int currentIndex = offset;
		
		type = (int)bytes[currentIndex];
		flags = (int)bytes[currentIndex+2];
		cType = (int)bytes[currentIndex+3];
		
		currentIndex = currentIndex + 4;
		
		contentsOfLabelObject = bytes[currentIndex] | bytes[currentIndex+1] | bytes[currentIndex+2] | bytes[currentIndex+3];

	}
	// Getters & Setters

	public int getContentsOfLabelObject() {
		return contentsOfLabelObject;
	}

	public void setContentsOfLabelObject(int contentsOfLabelObject) {
		this.contentsOfLabelObject = contentsOfLabelObject;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getcType() {
		return cType;
	}

	public void setcType(int cType) {
		this.cType = cType;
	}


	
	
	

}
