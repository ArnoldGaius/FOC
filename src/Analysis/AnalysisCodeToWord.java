package Analysis;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JTextArea;
public class AnalysisCodeToWord {
	static char[] strbuf = new char[1500];//����һ������,���Դ�Ŵ��ļ���ȡ�����ַ���
	public String errors = "";
	public int errors_num = 0;
	public String tokenString = "";
	public ArrayList<tokenNode> tokenMap = new ArrayList<tokenNode>(); //�ʷ�������õ�token���ַ�/����/�ؼ���/����...
	public String sigMap = "";
	ArrayList<String> sigList = new ArrayList<String>();
    int keywordIndex;  //ȡ�ؼ��ֵ����
    String[] keyWord = {"program","var","integer","bool","real","char","const", 	       
    		"begin","if","then","else","while","do","for",
    		"to","end","read","write","true","flase","not","and","or"
    		};
    public static void main(String[] args) throws IOException {
		AnalysisCodeToWord wa = new AnalysisCodeToWord();
		wa.readFile("C_TEST.txt");
		System.out.println("--------------------token����Ϣ...--------------------"+"\n");
		wa.run();
		System.out.println("\n"+"--------------------���--------------------");
//		System.out.println(errors);
	}	
    
    /**
     * ��ȡ��ߴ�������ڴ������<code>char</code>����
     * ��ʼ��������Ϣ���������
     * ��ʼ��token���е���Ϣ
     **/
    
    public void readFromArea(JTextArea codeArea) {
		String str = codeArea.getText();
		strbuf = str.toCharArray();
		errors="";
		errors_num = 0;
		tokenString = "";
	}
    
    /**
     * ���ļ��а��ַ�����ȡ��һ���ַ�������
     **/
	public void readFile(String url) throws IOException{
		int ch,i=0;		
		FileReader fr = new FileReader(url);
		while( (ch=fr.read())!=-1){
			strbuf[i++]=(char)ch;
		}
		fr.close();
	}	
	/**
	 * �ж��Ƿ����ַ�
	 **/
	private boolean isLetter(char ch) {
		if('a'<=ch&ch<='z'||'A'<=ch&ch<='Z')
			return true;
		else    return false;
	}	
	/**
	 * �ж��Ƿ�������
	 **/
	private boolean isDigit(char ch) {
		if('0'<= ch&&ch<='9')
			return true;
		else    return false;
	}	
	/**
	 * ���г���
	 **/
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
						tokenString+=str + "\n";
						tokenMap.add(new tokenNode(buf.toString(), keywordIndex, j));
						break;
					}
				}
				if(k>=keyWord.length){//�ǹؼ���
					String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" +34;
					System.out.println(str);	
					tokenMap.add(new tokenNode(buf.toString(), 34, j));
					tokenString+=str + "\n";
					if(!sigList.contains(buf.toString())){
						sigMap += buf + "\t" + buf.length() + "\t" + 34 + "\t��ʶ��" + "\n";
						sigList.add(buf.toString());
					}
				}
				i--;
			} 
			else if(i<strbuf.length&&isDigit(strbuf[i])) {                                                    
			     buf.delete(0, buf.length());
			     while(i<strbuf.length&&isDigit(strbuf[i])){
			    	      buf.append(strbuf[i++]);
				  }
			     if(i<strbuf.length&&isLetter(strbuf[i])){
			    	 if(strbuf[i]=='e'||strbuf[i]=='E'){
			    		 buf.append(strbuf[i++]);
		    			 if(i<strbuf.length){
		    				 if(strbuf[i]=='-'||strbuf[i]=='+'){
		    					 while(i<strbuf.length&&isDigit(strbuf[i]))
		    			    		 buf.append(strbuf[i++]);
		    				 }else if(isDigit(strbuf[i])){
		    					 while(i<strbuf.length&&isDigit(strbuf[i]))
		    			    		 buf.append(strbuf[i++]);
		    				 }
		    			 }
			    	 }else{
				    	 while(i<strbuf.length&&isLetter(strbuf[i]))
				    		 buf.append(strbuf[i++]);
				    	 errors += "line:" + String.valueOf(j) + "\t�޷�ʶ��ı�ʶ��" + buf.toString() + "\n";
				    	 errors_num++;
				    	 i--;
				    	 continue;
			    	 }
			     }
			     if(i<strbuf.length&&strbuf[i]=='.'){
			    	 buf.append(strbuf[i++]);
			    	 while(i<strbuf.length&&isDigit(strbuf[i]))
			    		 buf.append(strbuf[i++]);
			    	 if(i<strbuf.length&&isLetter(strbuf[i])){
			    		 if(strbuf[i]!='e'&&strbuf[i]!='E'){
			    			 while(i<strbuf.length&&(isLetter(strbuf[i])||isDigit(strbuf[i])))	
			    				 buf.append(strbuf[i++]);
			    			 errors += "line:" + String.valueOf(j) + "\t������������" + buf.toString() + "\n";
			    			 errors_num++;
			    			 i--;
			    			 continue;
			    		 }else if(i<strbuf.length&&strbuf[i]=='e'||strbuf[i]=='E'){
			    			 buf.append(strbuf[i++]);
			    			 if(i<strbuf.length){
			    				 if(strbuf[i]=='-'||strbuf[i]=='+'){
			    					 buf.append(strbuf[i++]);
			    					 while(i<strbuf.length&&isDigit(strbuf[i]))
			    			    		 buf.append(strbuf[i++]);
			    				 }else if(i<strbuf.length&&isDigit(strbuf[i])){
			    					 while(i<strbuf.length&&isDigit(strbuf[i]))
			    			    		 buf.append(strbuf[i++]);
			    				 }
			    			 }
			    		 }
			    	 }
			     }
			     i--;
		     if(!sigList.contains(buf.toString())){
					sigMap += buf + "\t" + buf.length() + "\t" + 35 + "\t�򵥱���" + "\n";
					sigList.add(buf.toString());
		     }
			 String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" +35;
			 System.out.println(str);
			 tokenMap.add(new tokenNode(buf.toString(), 35, j));
			 tokenString+=str + "\n";
	  	    }
			else if(i<strbuf.length)
				switch((char)strbuf[i]){
					case',':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 41;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 41, j));
						tokenString+=str + "\n";
						};break;
					case';':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 40;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 40, j));
						tokenString+=str + "\n";
					}break;
					case'.':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 48;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 48, j));
						tokenString+=str + "\n";
					}break;
					case'(':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 46;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 46, j));
						tokenString+=str + "\n";
					}break;
					case')':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 47;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 47, j));
						tokenString+=str + "\n";
					}break;
					case'[':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 29;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 29, j));
						tokenString+=str + "\n";
					}break;
					case']':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 30;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 30, j));
						tokenString+=str + "\n";
					}break;
					case'+':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 24;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 24, j));
						tokenString+=str + "\n";
					}break;
					case'-':{
						String str = "line:" + String.valueOf(j) + "\t" + strbuf[i] + "\t\t" + 25;
						System.out.println(str);
						tokenMap.add(new tokenNode(String.valueOf(strbuf[i]), 25, j));
						tokenString+=str + "\n";
					}break;
					case'*':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(i<strbuf.length){
							switch(strbuf[i]){
								case '/':{
									buf.append('/');
									String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 44;
									System.out.println(str);
									tokenMap.add(new tokenNode(buf.toString(), 44, j));
									tokenString+=str + "\n";
								};break;
								default:{
									i--;
									String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 26;
									System.out.println(str);
									tokenMap.add(new tokenNode(buf.toString(), 26, j));
									tokenString+=str + "\n";									
								};break;
							}
						}
					}break;
					case'/':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(i<strbuf.length){
							switch(strbuf[i]){
								case '/':{
									buf.append(strbuf[i]);
									while(i<strbuf.length&&strbuf[i]!='\n')
										i++;
									String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 37;
									System.out.println(str);	
									tokenMap.add(new tokenNode(buf.toString(), 37, j));
									tokenString+=str + "\n";
								};break;
								case '*':{
									buf.append(strbuf[i]);
									String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 43;
									System.out.println(str);	
									tokenMap.add(new tokenNode(buf.toString(), 43, j));
									tokenString+=str + "\n";
									while(i<strbuf.length&&strbuf[i]!='*')
										i++;
									while(i<strbuf.length&&strbuf[i]!='/')
										i++;
									if(i<strbuf.length&&strbuf[i]=='/'){
										buf.delete(0, buf.length());
										buf.append("*/");
										str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 36;
										System.out.println(str);
										tokenMap.add(new tokenNode(buf.toString(), 36, j));
										tokenString+=str + "\n";
									}
								};break;
								default:{
									i--;
									String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 27;
									System.out.println(str);
									tokenMap.add(new tokenNode(buf.toString(), 27, j));
									tokenString+=str + "\n";
									};break;
							}
						}
					}break;
					case'=':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(i<strbuf.length){
							if(strbuf[i]=='='){
								buf.append(strbuf[i]);
								String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 32;
								System.out.println(str);
								tokenMap.add(new tokenNode(buf.toString(), 32, j));
								tokenString+=str + "\n";
							}else{
								String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 39;
								System.out.println(str);
								tokenMap.add(new tokenNode(buf.toString(), 39, j));
								tokenString+=str + "\n";
								i--;
							}
						}
						
					}break;	
					case':':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(i<strbuf.length&&strbuf[i]=='='){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 54;
							System.out.println(str);
							tokenMap.add(new tokenNode(buf.toString(), 54, j));
							tokenString+=str + "\n";
						} else{
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 45;
							System.out.println(str); 
							tokenMap.add(new tokenNode(buf.toString(), 45, j));
							tokenString+=str + "\n";
							i--;
						}					
					};break;
					case'>':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(strbuf[i]=='='){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 31;
							System.out.println(str);
							tokenMap.add(new tokenNode(buf.toString(), 31, j));
							tokenString+=str + "\n";
						} 
						else{
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 29;
							System.out.println(str); 
							tokenMap.add(new tokenNode(buf.toString(), 29, j));
							tokenString+=str + "\n";
							i--;
						}	
					};break;
					
					case'<':{
						buf.delete(0, buf.length());
						buf.append(strbuf[i]);
						i++;
						if(strbuf[i]=='='){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 30;
							System.out.println(str); 
							tokenMap.add(new tokenNode(buf.toString(), 30, j));
							tokenString+=str + "\n";
							i++;
						} else if(strbuf[i]=='>'){
							buf.append(strbuf[i]);
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 33;
							System.out.println(str); 
							tokenMap.add(new tokenNode(buf.toString(), 33, j));
							tokenString+=str + "\n";
							i++;
						} else{
							String str = "line:" + String.valueOf(j) + "\t" + buf + "\t\t" + 28;
							System.out.println(str); 
							tokenMap.add(new tokenNode(buf.toString(), 28, j));
							tokenString+=str + "\n";
							i++;
						}	
					};break;
					case '~':  
			        case '@':  
			        case '$':  
			        case '%':  
			        case '&':  
			        case '"':
			        case '\'':
			        {
			        	String vs = "";  
			        	while (true) {  
			                vs += strbuf[i];  
			                i++; 
			                if(i>=strbuf.length||isLetter(strbuf[i])||isDigit(strbuf[i])||
			                		strbuf[i] == ' ' || strbuf[i] == '\t'||strbuf[i] == '\r'||strbuf[i] == '\n'){  
			                    break;  
			                }  
			            }
			        	String str = "line:" + String.valueOf(j) + "\t�޷�ʶ����ַ�" + vs;
			        	System.out.println(str);
			        	errors += str + "\n";
			        	errors_num++;
			        	i--;
			        }break;
			        default:{
			        	String str ="line:" + String.valueOf(j) + "\t���ش��󣺱��벻ͬ���ַ�";
			        	System.out.println(str);
			        	errors += str + "\n";
			        	errors_num++;
			        }
				}//switch����
			}
	}                                                                                             
}
