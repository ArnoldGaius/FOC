package LL1;

/*�������ս����first����follow������
 * ���з��ս�� ���ַ���name,���Ӧ�ļ������ַ�������ʾ
 * 
 * */

public class FF {
	private String ff;
	private String name;
	public FF(String name,FF f){
		//��һ�����ս����first/follow�����������µ�һ����
		this.name=name;
		this.ff=f.ff;		
	}
	public FF(String name){//�����µ�First����
		this.name=name;
		this.ff="";
	}
	public FF(String name,String ff){//�ֱ�ֵ�ķ�ʽ��������
		this.name=name;
		this.ff=ff;
	}
	public String getName(){//��ȡnameԪ�أ������ս��
		return this.name;
	}
	public String getCollect(){//��ȡ�����firstԪ��
		return this.ff;
	}
	public void addf(char str){//�ڼ����������Ԫ��
		int length=ff.length();
		if(length==0)
			ff=ff+str;
		else{
			for(int i=0;i<length;i++){
				if(str==ff.charAt(i))
					return;
			}
				ff=ff+str;
		}
	}
	public FF exceptZore(){//��ǰ�������'��'֮���firt����
		int length=this.ff.length();
		String emp="";
		if(length>0){
			for(int i=0;i<length;i++){
				if(this.ff.charAt(i)!='��')
					emp=emp+this.ff.charAt(i);
				//else emp=emp;
			}				
		}
		return new FF(this.name,emp);
	}
	public boolean conteinZero(){//�жϵ�ǰ�����firstԪ�����Ƿ���'��'
		int length=this.ff.length();
		if(length>0){
			for(int i=0;i<length;i++){
				if(this.ff.charAt(i)=='��')
					return true;
				}
		}
		return false;
	}
	public boolean conteinChar(char c){//�жϵ�ǰ����first���Ƿ����ַ�c
		int length=this.ff.length();
		if(length>0){
			for(int i=0;i<length;i++){
				if(this.ff.charAt(i)==c)
					return true;
				}
		}
		return false;
	}
	public void addf(FF fc){//��ĳһ����� �����е�Ԫ�ؼ��뵽��ǰԪ�صļ�����
		if(fc.ff.length()>0){
			for(int i=0;i<fc.ff.length();i++)
				this.addf(fc.ff.charAt(i));
		}
	}
	public void addf(String str){//����õ��¼��ϼ��뵳��ǰ����ļ�����
		if(str.length()>0){
			for(int i=0;i<str.length();i++)
				this.addf(str.charAt(i));
		}
	}
	
	 public String print(){//��ӡ��ǰ���� ��Ԫ�أ��Լ��Ϸ�ʽ��ӡ����first�е�Ԫ��
		 String str = new String();
		 str+=name+"�� {";
		 System.out.print(name+"�� {");
//		 for(int i=0;i<ff.length()-1;i++)
//			 System.out.print(ff.charAt(i)+",");
//		 if(ff.length()>0)
//			 System.out.print(ff.charAt(ff.length()-1));
		 for(int i=0;i<ff.length();i++){
			 str+=ff.charAt(i);
			 System.out.print(ff.charAt(i));
			 	if(i!=ff.length()-1){
			 		str+=",";
			 		System.out.print(",");
			 	}
		 }
		 str+="}\n";
		 System.out.println("}");
		 return str;
	 }
	 
	 
	public void reMove(char c) {//�ӵ�ǰԪ����ȥ��ĳһ�ַ�c
		int length=ff.length();		
		if(length>0){
			String emp="";
			for(int i=0;i<length;i++){
				if(c!=ff.charAt(i))
					emp=emp+ff.charAt(i);
					
			}
				ff=emp;
		}
		
	}
}

