package LL1;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
 
public class split_test extends JFrame {
 /**
  * 
  */
 private static final long serialVersionUID = 7965028125490718782L;
  
  
 public split_test(){
  super("JTable����");
  this.setSize(600, 400);
   
  // �б����Լ�����
  String[] columnNames = {"col1", "col2"};
  // �����Լ���̬�������ݡ�
  Object[][] cellData = {{"row1-col1", "row1-col2"},{"row2-col1", "row2-col2"}};
   
  JTable table = new JTable(cellData, columnNames);

  // һ��Ҫ��JTable�ŵ�JScrollPane������ʾ������
  JScrollPane contentPane = new JScrollPane();
  contentPane.setViewportView(table);
  this.add(contentPane);
   
  this.setVisible(true);
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
  
 public static void main(String[] args){
	 LinkedList<String> a = new LinkedList<String>();
	 a.add("a");
	 a.add("b");
	 Iterator<String> it = a.iterator();
	 while(it.hasNext()){
		 System.out.println(it.next());
	 }
     Stack<Integer> s = new Stack<Integer>(); 
     for (int i = 0; i < 10; i++) { 
             s.push(i); 
     } 
     for (Integer x : s) {
         System.out.println(x); 
 } 
     String str = "asfhjksdf";
     System.out.println(new StringBuffer(str).reverse().toString());
 }
}