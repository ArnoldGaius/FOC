package Analysis;
import java.io.*;

import javax.swing.JTextArea;
public class AnalysisCodeToWord {
	static char[] strbuf = new char[1500];//����һ������,���Դ�Ŵ��ļ���ȡ�����ַ���
	public String errors = "";
	public int errors_num = 0;
	public String token = "";
    int keywordIndex;  //ȡ�ؼ��ֵ����
    String[] keyWord = {"and","begin","const","div","do","else","end", 	       
    		"function","if","integer","not","or","procedure","program",
    		"read","real","then","type","var","while","write"
    		};
    public static void main(String[] args) throws IOException {
		AnalysisCodeToWord wa = new AnalysisCodeToWord();
		wa.readFile("C_for.TXT");
		System.out.println("--------------------token����Ϣ...--------------------"+"\n");
		wa.run();
		System.out.println("\n"+"--------------------���--------------------");
//		System.out.println(errors);
	}	
    public void readFromArea(JTextArea codeArea) {
		String str = codeArea.getText();
		strbuf = str.toCharArray();
		errors="";
		errors_num = 0;
		token = "";
	}
	//���ļ��а��ַ�����ȡ��һ���ַ�������
	public void readFile(String url) throws IOException{
		int ch,i=0;		
		FileReader fr = new FileReader(url);
		while( (ch=fr.read())!=-1){
			strbuf[i++]=(char)ch;
		}
		fr.close();
	}	
	private boolean isLetter(char ch) {
		if('a'<=ch&ch<='z'||'A'<=ch&ch<='Z')
			return true;
		else    return false;
	}	
	private boolean isDigit(char ch) {
		if('0'<= ch&&ch<='9')
			return true;
		else    return false;
	}	

	public void run(){                             //��������strbuf����ַ���
		StringBuffer buf = new StringBuffer();  //����һ��������
		int j=1;
		for(int i=0; i<strbuf.length;i++){      //i����ѭ������,Ҳ���ļ�ָ��
			//����ͷ����space\enter\line��ʱ��,����!
			if(strbuf[i]=='\n')
				j++;
			else if(strbuf[i]==' '||strbuf[i]=='\t'||strbuf[i]=='\n'){
//				i++;
				continue;
			}
			else if(i<strbuf.length&&isLetter(strbuf[i])) {
				int k;
				buf.delete(0, buf.length()); //���ջ�����
				while(i<strbuf.length&&(isLetter(strbuf[i])||isDigit(strbuf[i]))) {
				    buf.append(strbuf[i]);
				    i++;
				}
				//����buf������ַ����Ƿ�Ϊ�ؼ���
				for(k =0; k<keyWord.length; k++){
					if(new String(buf).equals(keyWord[k])){
						keywordIndex = k;
						String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" +keywordIndex;
						System.out.println(str);	
						token+=str + "\n";
						break;
					}
				}
				if(k>20){
					String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" +21;
					System.out.println(str);	
					token+=str + "\n";
				}
				i--;
			} 
			else if(i<strbuf.length&&isDigit(strbuf[i])) {                                                       
			     buf.delete(0, buf.length());
			     while(isDigit(strbuf[i])){
			    	      buf.append(strbuf[i]);
					  i++;
				  }
			     if(i<strbuf.length&&isLetter(strbuf[i])){
			    	 while(i<strbuf.length&&isLetter(strbuf[i]))
			    		 buf.append(strbuf[i++]);
			    	 errors += "line:" + String.valueOf(j) + "\t" + buf.toString() +"\t�޷�ʶ��ı�ʶ��\n";
			    	 errors_num++;
			    	 i--;
			    	 continue;
			     }
			     if(i<strbuf.length&&strbuf[i]=='.'){
			    	 buf.append(strbuf[i++]);
			    	 while(i<strbuf.length&&isDigit(strbuf[i]))
			    		 buf.append(strbuf[i++]);
			    	 if(i<strbuf.length&&!isDigit(strbuf[i])){
			    		 if((isLetter(strbuf[i]))){
			    			 while(i<strbuf.length&&(isLetter(strbuf[i])||isDigit(strbuf[i])))	
			    				 buf.append(strbuf[i++]);
			    			 errors += "line:" + String.valueOf(j) + "\t" + buf.toString() +"\t������������\n";
			    			 errors_num++;
			    			 i--;
			    			 continue;
			    		 }
			    	 }
			     }
			     i--;
			  String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" +22;
			  System.out.println(str);
			  token+=str + "\n";
	  	    }
			else if(i<strbuf.length)
				switch((char)strbuf[i]){
					case',':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 23;
						System.out.println(str);
						token+=str + "\n";
						};break;
					case';':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 24;
						System.out.println(str);
						token+=str + "\n";
					}break;
					case'.':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 26;
						System.out.println(str);
						token+=str + "\n";
					}break;
					case'(':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 27;
						System.out.println(str);
						token+=str + "\n";
					}break;
					case')':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 28;
						System.out.println(str);
						token+=str + "\n";
					}break;
					case'[':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 29;
						System.out.println(str);
						token+=str + "\n";
					}break;
					case']':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 30;
						System.out.println(str);
						token+=str + "\n";
					}break;
					case'+':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 34;
						System.out.println(str);
						token+=str + "\n";
					}break;
					case'-':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 35;
						System.out.println(str);
						token+=str + "\n";
					}break;
					case'*':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 36;
						System.out.println(str);
						token+=str + "\n";
					}break;
					case'/':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 37;
						System.out.println(str);
						token+=str + "\n";
					}break;
					case'=':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 38;
						System.out.println(str);	
						token+=str + "\n";
					}break;	
					case':':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(strbuf[i]=='='){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 44;
							System.out.println(str);
							token+=str + "\n";
						} else{
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 25;
							System.out.println(str); 
							token+=str + "\n";
							i--;
						}					
					};break;
					case'>':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(strbuf[i]=='='){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 43;
							System.out.println(str);
							token+=str + "\n";
						} else{
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 40;
							System.out.println(str); 
							token+=str + "\n";
							i--;
						}	
					};break;
					
					case'<':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(strbuf[i]=='='){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 42;
							System.out.println(str); 
							token+=str + "\n";
							i++;
						} else if(strbuf[i]=='>'){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 41;
							System.out.println(str); 
							token+=str + "\n";
							i++;
						} else{
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 39;
							System.out.println(str); 
							token+=str + "\n";
							i++;
						}	
					};break;
					case '~':  
			        case '@':  
			        case '$':  
			        case '%':  
			        case '&':  
			        {
			        	String vs = "";  
			        	while (true) {  
			                vs += strbuf[i];  
			                i++; 
			                if(i>=strbuf.length||isLetter(strbuf[i])||isDigit(strbuf[i])||strbuf[i] == ' ' || strbuf[i] == '\t'){  
			                    break;  
			                }  
			            }
			        	errors += "line:" + String.valueOf(j) + "\t" + vs +"\t�޷�ʶ����ַ�\n";
			        	errors_num++;
			        	i--;
			        }break;
			        default:{
			        	errors+="line:" + String.valueOf(j) + "\t"  +"\t���ش��󣺱��벻ͬ���ַ�\n";
			        	errors_num++;
			        }
				}//switch����
			}
	}                                                                                             
}
