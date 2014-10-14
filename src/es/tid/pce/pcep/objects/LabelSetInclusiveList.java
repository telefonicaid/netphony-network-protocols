package es.tid.pce.pcep.objects;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.NCF;



/**

¥	Attaching a new LABEL_SET object (class 130, type 1) as an attribute of the computed path, that encodes the free NCF as an inclusive list. Each ncf-number is of the form grid-ch.spacing-identier-n. We assume identifier is zero.

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | Object-Class  |   OT  |Res|P|I|   Object Length (bytes)       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |     resv      |                 flags                         |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       ncf-number 1                            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       ncf-number 2                            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       ncf-number ..                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       ncf-number M                            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * @author Oscar Gonzalez de Dios
 */

public  class LabelSetInclusiveList extends LabelSet{

	private LinkedList<NCF> ncfList;
	
	//Constructors
	
	public LabelSetInclusiveList(){
		super();
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_LABEL_SET_INCLUSIVE);
		this.ncfList=new LinkedList<NCF>();
	}

	public LabelSetInclusiveList(byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() {
		int length=4+4;//4 bytes header, 4 resv+flags
		for (int i=0;i<ncfList.size();++i){
			try {
				ncfList.get(i).encode();
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			length =length+ncfList.get(i).getLength();
		}
		this.setObjectLength(length);
		this.object_bytes=new byte[length];
		int offset=4;
		this.getBytes()[offset]=0;
		this.getBytes()[offset+1]=0;
		this.getBytes()[offset+2]=0;
		this.getBytes()[offset+3]=0;
		offset=offset+4;
		for (int i=0;i<ncfList.size();++i){
			System.arraycopy(ncfList.get(i).getBytes(),0, this.object_bytes, offset, ncfList.get(i).getLength());
			offset=offset+ncfList.get(i).getLength();			
		}
		
		
	}
	


	@Override
	public void decode() throws MalformedPCEPObjectException {
		log.finest("VAMOS A CREAR EL NCFList");
		this.ncfList=new LinkedList<NCF>();
		log.finest("LENGTH vale "+this.getLength());

		int offset=8;
		if (offset>=this.getLength()){
			return;
		}
		boolean fin=false;
		while (!fin) {
			NCF ncf= new NCF(this.getBytes(),offset);
			
			ncfList.add(ncf);
			log.finest("EN NCF mide "+ncf.getLength());
			offset=offset+ncf.getLength();
			if (offset>=this.getLength()){
				fin=true;
			}
			
		}
		
	}

	public LinkedList<NCF> getNCFList() {
		return ncfList;
	}

	public void setNCFList(LinkedList<NCF> nCFList) {
		ncfList = nCFList;
	}

	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("<ILS ");
		for (int i=0;i< ncfList.size();++i){
			sb.append(ncfList.get(i).toString());
		}
		sb.append(">");
		return sb.toString();
	}
		

	

}

