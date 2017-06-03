package Analysis;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class grammaticalAnalysis {
	public int bracket_left=0;
	public int bracket_right=0;
	public int num_err = 0;
	public String errors="";
	public HashMap<String,String> var = new HashMap<String, String>(); 
	public ArrayList<tokenNode> tokenMap = new ArrayList<tokenNode>();
	public ArrayList<String> unusedIdentifier_ArrayList = new ArrayList<String>();
	public ArrayList<String> isIdentifier_ArrayList = new ArrayList<String>();
//	public ArrayList<tokenNode> testMap = new ArrayList<tokenNode>(
//			Arrays.asList("program","example",";","const",
//					"k","=","2",",","l","=","3",
//					";","var","a",",","b",",",
//					"c",":","integer",";","x",":",
//					"char",";","begin","if","(","(","a",
//					"+","c",")","*","3",">","b",")","and",
//					"(","b",">","3",")","then","c",":=",
//					"3",";","x",":=","2","+","(","3",
//					"*","a",")","-","b","*","c","*",
//					"8",";","if","(","2","+","3",">","a",
//					")","and","(","b",">","3",")","and",
//					"(","a",">","c",")","then","c",":=",
//					"3",";","for","x",":=","1","+","2",
//					"to","3","do","b",":=","100",";","while",
//					"a",">","b","do","c",":=","5",";","for",
//					"x",":=","1","+","a","to","3","do","b",
//					":=","15",";","while","b",">","b","do",
//					"c",":=","5",";","repeat","a",":=","10",
//					";","until","a",">","b",";","end","."));
	public ArrayList<String> keyWord = new ArrayList<String>(
			Arrays.asList(
			"program","var","integer","bool","real","char","const", 	       
    		"begin","if","then","else","while","do","for",
    		"to","end","read","write","true","flase","not","and","or")); //�ʷ�������õ�token���ַ�/����/�ؼ���/����...
	private String token = null;
	private int index_tokenMap = 0;
	
	public String getNextToken (){
		if(index_tokenMap<tokenMap.size()){
			return tokenMap.get(index_tokenMap++).getWord();
		}
		return null;
	}	
	/**
	 * �ж��Ƿ��Ǳ���
	 * @param str
	 * @return
	 */
	private boolean isVariable(String str) {
		if(str==null)
			return false;
		if(str.equals("integer")||str.equals("char")||str.equals("real")||str.equals("bool"))
			return true;
		return false;
	}
	/**
	 * �ж��Ƿ����ַ�
	 * @param ch
	 * @return
	 */
	private boolean isLetter(char ch) {
		if('a'<=ch&ch<='z'||'A'<=ch&ch<='Z')
			return true;
		else    return false;
	}
	/**
	 * �ж��Ƿ��ǳ���
	 * @param str
	 * @return true/false
	 */
	public boolean isConstantWords(String str){
         Pattern pattern = Pattern.compile("[0-9]*");
         Matcher isNum = pattern.matcher(str);
         if(!isNum.matches() ){
             return false;
         }
         return true;
	}
	/**
	 * �ж��Ƿ��Ǳ�ʶ��
	 * @param str
	 * @return true/false
	 */
	public boolean isIdentifier(String str){
		if(keyWord.contains(str))
			return false;
		if(str!=null){
			if(isLetter(str.charAt(0))||str.charAt(0)=='_'){
				return true;
			}
			return false;
		}
		return false;
	}
	/**
	 * �ж��Ƿ��������
	 * @param str
	 * @return true/false
	 */
	//�����
	public boolean isOperator(String str){
		if(str==null)
			return false;
		if(str.equals("+")||str.equals("-")||str.equals("*")||str.equals("/"))
			return true;
		return false;
	}
	/**
	 * �ж��Ƿ��ǹ�ϵ��
	 * @param str
	 * @return true/false
	 */
	//��ϵ��
	public boolean isRelationSig(String str){
		if(str==null)
			return false;
		if(str.equals(">")||str.equals("<")||str.equals(">=")||str.equals("<=")||str.equals("<>")||str.equals("="))
			return true;
		return false;
	}
	/**
	 * const�������
	 */
	public void const_st(){
		if (!isIdentifier(token)) {
			error("const ��ȱ�ٳ�������");
		}else {
			unusedIdentifier_ArrayList.add(token); //����ĳ�������δʹ����
			isIdentifier_ArrayList.add(token); //����ĳ������붨���ַ���
			token = getNextToken();
			if (!"=".equals(token)){
				error("ȱ��=");
			}else {
				token = getNextToken();
				if (!isConstantWords(token)) {
					error("ȱ����ֵ");
				} else {
					token = getNextToken();
					if (!";".equals(token)){
						if(!",".equals(token)){
							error("ȱ��;");
						}
						else{
							token=getNextToken();
							const_st();
						}	
					} else {
						token = getNextToken();
						if ("var".equals(token)||"begin".equals(token)) {
							return;
							// var_st(iterator);
						} else {
							const_st();
							return;
						}
					}
				}
			}
		}
	}
	/**
	 * �����¼
	 * @param Warningstr
	 */
	public void warning(String Warningstr){
		errors+=Warningstr;
	}
	/**
	 * �����¼ 
	 * <p>
	 * �ڿ���̨���
	 * @param errStr
	 */
	public void error(String errStr){
		num_err++;
		String err=null;
		if(index_tokenMap-1>-1&&index_tokenMap<tokenMap.size())
			err=tokenMap.get(index_tokenMap-1).getRow()+"\t"+errStr+"\n";
		else if(index_tokenMap-1<0){
			err=tokenMap.get(0).getRow()+"\t"+errStr+"\n";
		}else{
			err=tokenMap.get(index_tokenMap-2).getRow()+"\t"+errStr+"\n";
		}
		if(!errors.contains(err))
			errors+=err;
		System.out.println(err);
	}
	/**
	 * ����������
	 */
	void varst() /*�������˵��������var�����ù���*/
	{
		ArrayList<String> identfiers = new ArrayList<String>();
	    while(true) {
		    if (!isIdentifier(token))
		    		error("ȱ�ٱ�ʶ��");
		    else{
		    	unusedIdentifier_ArrayList.add(token); //����ı�������δʹ����
				isIdentifier_ArrayList.add(token); //����ı������붨���ַ���
				identfiers.add(token);
		    	token= getNextToken(); /*ȡ��һ��token��*/
		    }
		    if (",".equals(token)) 
		    	token= getNextToken(); /*ȡ��һ��token��*/
		    else if (":".equals(token)) 
		    	break;
		    else{
		    	error("���󣺱�������ֻ�ܳ���:��,");
		    	break;
		    }
	    }
	    token = getNextToken(); /*ȡ����һ��token�֣�����type��*/
	    if (!isVariable(token)) 
	    	error("������������");
	    else{
	    	if(!identfiers.isEmpty()){
	    		for (String iden: identfiers){
	    			if(var.get(iden)==null)
	    				var.put(iden, token);
	    		}
	    		identfiers.clear();
	    	}
	    	token= getNextToken(); /*ȡ����һ��token��*/
	    }
	    if (!";".equals(token))  //�������; �ֺ�
	    	error("ȱ�� ; ");/*������һ�б���˵��*/
	    else
	    	token= getNextToken(); /*ȡ����һ��token��*/
	    if (isIdentifier(token))
	    	varst(); 
	    	/* ��������Ǳ�ʶ���������������˵��*/
	    else if ("begin".equals(token))
	    	return ; /* �����������Ĵ���,ת�봦���ִ�г��򲿷�*/
	    else 
	    	error("�﷨����ȱ��BEGIN�������˵������");
	}
	/**
	 * �������ʽ
	 * @return true/false
	 */
	//�������ʽ
	boolean bexp()  /* �߼����ʽ�Ĵ���*/
	{
		  bt();   /* ��������*/
		  if ("or".equals(token))  {   /*���ȴ���or*/
			  token = getNextToken();                
			 bexp();  /* �ݹ���ã�or ����Ӧ������һ���������ʽ*/
			 return true;
		  }
		  else  
			  return false;  /*�������ʽ��������*/
	}
	/**
	 * ������
	 * @return true/false
	 */
	//������
	boolean bt()  /*����and*/
	{
		token = getNextToken();
		bf();      /*��������*/
		if (bracket_left != bracket_right){ /*�����еı��ʽ������ϣ����������*/
			error("��ƥ�������");
		}
		bracket_left = 0;
		bracket_right = 0;
		if ("and".equals(token)) {
 		   bt(); /*�ݹ���ã�and����Ӧ������һ��������*/
 		   return true;
      }
      else  
    	  return false;  /*�������ʽ��������*/
	}
	/**
	 * ��������
	 */
	//�����
	public void bds_arithmeticWords(){
		if(!isOperator(token)){
			if(isRelationSig(token)){
				return;
			}
			if(";".equals(token)){
				token = getNextToken();
				return;
			}else if("to".equals(token) ||"do".equals(token)||"until".equals(token)||"and".equals(token)||"or".equals(token)||"then".equals(token)){
				return;
			}else if(")".equals(token)){
				if(this.bracket_left<this.bracket_right){
					error("����� )");
				}else{
					bracket_right++;
					token = getNextToken();
					bds_arithmeticWords();
				}
			}else if("if".equals(token)|| "for".equals(token)||"while".equals(token)||"repeat".equals(token)){
				error("ȱ�� ;");
			}else{
				error("ȱ�������");
			}
		}else{
			token = getNextToken();
			if((!isIdentifier(token)) && (!isConstantWords(token)) &&"until".equals(token) && "(".equals(token)){
				error("ȱ�ٱ�ʶ��");
			}else{
				aexpr();
			}
		}
	}
	/**
	 * �������ʽ
	 */
	//�������ʽ
	void aexpr(){
		if(";".equals(token) ){
			token = getNextToken();
			return;
		}
		else if("to".equals(token)|| "do".equals(token)){
			
		}else if("(".equals(token)){
			token = getNextToken();
			bracket_left++;
			aexpr(); /*��������Ȼ�Ǳ��ʽ*/
		}
		else if((!isIdentifier(token)) && (!isConstantWords(token))){
			error("ȱ�ٱ�ʶ��");
			bds_arithmeticWords();
		}else{
			token = getNextToken();
			bds_arithmeticWords();
		}
	}
	 /**
	  * ������
	  */
	//������
	void bf() /*����not*/
	{
	       if ("not".equals(token)) {
	    	   token = getNextToken();
	    	   bf(); /*�ݹ���ã�not����Ӧ������һ��������*/
	    	   }
	       else{
	    	   if ("(".equals(token)) {/*�������ţ�*/
	    		   bracket_left++;
	    		   bexp(); /*��������Ȼ�Ǳ��ʽ*/
	    	   }else {    /* �����ϵ���� */
	    		   aexpr(); /* �����������ʽ����*/
	    		   if (isRelationSig(token)){ /*��ϵ�����*/
						token=getNextToken();
						aexpr(); /*��ϵ�����֮�����������ʽ*/
	    		   }
	    	   }
	       }
	}

	/**
	 * if�����
	 */
	void ifs(){
	    bexp();  /*���������ʽ*/
	    if(!"then".equals(token))
	    	error("ȱ��then");
	    else{
		    token = getNextToken();
		    ST_SORT();/*���ú�������then��Ŀ�ִ�����*/
	    }
//	    if(token!= "else") 
//	    	error("ȱ��else");
//	    else{
//	    	token = getNextToken();
//	    	ST_SORT();/*����else��Ŀ�ִ�����*/
//	    }
	}
	/**
	 * while�����
	 */
	void whiles(){
		bexp();  /*���������ʽ*/
	    if(!"do".equals(token))
	    	error("ȱ��do");
	    else{
		    token = getNextToken();
		    ST_SORT();/*���ú�������do��Ŀ�ִ�����*/
	    }
	}
	/**
	 * repeat�����
	 */
	void repeats(){
		token=getNextToken();
		ST_SORT();
		if(!"until".equals(token))
			error("ȱ�� until");
		bexp();
	}
	/**
	 * for�����
	 */
	void fors(){
		token=getNextToken();
		if((!isIdentifier(token)) && (!isConstantWords(token))){
			error("ȱ�ٱ�ʶ��");
		}
		token=getNextToken();
		if(!":=".equals(token)){
			error("ȱ�� := ��ֵ����");
		}
		token=getNextToken();
		aexpr();
		if("to".equals(token)||"downto".equals(token)){
			token=getNextToken();
			aexpr();
			if(!"do".equals(token))
				error("ȱ�� do");
			else
				token=getNextToken();
			ST_SORT();
		}
	}
	/**
	 * ִ�������
	 */
	void assign(){
		if((!isIdentifier(token)) && (!isConstantWords(token))){
			error("ȱ�ٱ�ʶ��");
		}
		if(!isIdentifier_ArrayList.contains(token)){
			error("��ʶ��δ����");
		}
		if(unusedIdentifier_ArrayList.contains(token))
			unusedIdentifier_ArrayList.remove(token);
		token=getNextToken();
		if(!":=".equals(token)){
			error("ȱ�� := ��ֵ����");
		}
		token=getNextToken();
		aexpr();
	}
	/**
	 * ���ദ��
	 * <p>
	 * ��Ϊif��while��for��repeat��ִ�����
	 */
	void ST_SORT() /* ��ִ�������ദ��ģ��������*/
	{
		if ("if".equals(token))  
			ifs(); /*����if������ģ��*/
		else if ("while".equals(token)) 
			whiles();/*����while������ģ��*/
		else if ("repeat".equals(token)) 
			repeats();/*����repeat������ģ��*/
		else if ("for".equals(token)) 
			fors();/*����for������ģ��*/
		else assign();/*���������ʾ�Ǹ�ֵ��䣬ֱ�ӵ��ø�ֵ���ķ���*/
	}
	/**
	 * ���г���
	 */
	public void run(){
		while(!"program".equals(token)&&index_tokenMap<tokenMap.size()){
			token = getNextToken();
		}
		if("program".equals(token)){
			token=getNextToken();
			isIdentifier(token);
		}
		token = getNextToken();
		if(";".equals(token)){
			token=getNextToken();
		}
		if ("const".equals(token))  {  /*���������ǳ���˵�����*/
			token = getNextToken();
	        const_st();/*���÷�������˵���ĺ���*/
	    }
		if ("var".equals(token))  {  /*����������˵�����*/
			token = getNextToken();
	        varst();/*���÷�������˵���ĺ���*/
	    }
		if ("begin".equals(token)) {
			token = getNextToken();
			while(token!=null&&!"end".equals(token)){
				ST_SORT();  /*������ֿ�ִ�����*/
			}
		}
		if("end".equals(token)){
			token=getNextToken();
		    if (!".".equals(token)) {/*����������*/
		    	error("ȱ�� .");
		    }
		}else{
			error("ȱ�ٽ�������");
		}
		for(String unused_token:unusedIdentifier_ArrayList)
			warning("δʹ�õı���\t\""+unused_token+"\"\n");
	    System.out.println("�﷨�������");
	    System.out.println("error(s)\t("+num_err+")");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		grammaticalAnalysis ga = new grammaticalAnalysis();
//		ga.tokenMap=ga.testMap;
		ga.run();
	}	

}
