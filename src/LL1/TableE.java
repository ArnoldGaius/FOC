package LL1;
/**********************************************
 * ��������������Ϊ���ս��������Ϊ�ս������������Ϊ�ķ����ʽ
 * */
public class TableE {
	private String nT; //���ս��
	private char yT; //�ս��
	private String exp;  //����ʽ
	public TableE(String nt,char c,String exp){
		this.yT=c;
		this.nT=nt;
		this.exp=exp;
	}
	public boolean isEquals(TableE t){//�ж��������Ԫ���Ƿ���ȫ��ͬ
		if(t.nT.equals(this.nT)&&t.yT==this.yT&&t.exp.equals(this.exp))
			return true;
		return false;
	}
	public boolean cmpTnT(String nT,char yT){//�鿴���Ԫ���Ƿ��Ѿ�������
		if(this.yT==yT&&this.nT.equals(nT))
			return true;
		else 
			return false;
	}
	public void print(){//��ӡ��ǰ�ı�����
		System.out.println("["+this.nT+"] ["+this.yT+"] ("+this.exp+ ") ");
	}
	
	public String getExp(){//��ȡ�����������,�ķ����ʽ��
		return this.exp;
	}
	public String getNT() {//��÷��ս����
		
		return this.nT;
	}
	public String getYT(){
		return String.valueOf(this.yT);
	}
}
