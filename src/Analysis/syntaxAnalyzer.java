package Analysis;

import java.io.FileReader;
import java.io.IOException;

public class syntaxAnalyzer {
	static char[] strbuf = new char[1500];//����һ������,���Դ�Ŵ��ļ���ȡ�����ַ���
//	public String token = "";
	
	//���ļ��а��ַ�����ȡ��һ���ַ�������
	public void readFile(String url) throws IOException{
		int ch,i=0;		
		FileReader fr = new FileReader(url);
		while( (ch=fr.read())!=-1){
			strbuf[i++]=(char)ch;
		}
		fr.close();
	}	
	public void scan(){
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
