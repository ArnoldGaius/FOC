package Analysis;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.util.Properties;
@SuppressWarnings("serial")
public class FileChooser extends JFrame implements ActionListener{
 private JButton open;
 private JFileChooser chooser;
 private String  latestPath ;
 private Profile profile;
 public FileChooser() {
  profile = new Profile();//ÿ�����г���ʱ���������ļ�Profile����
  latestPath = (profile.read()?profile.latestPath:"C:/");//��ȡ�����ļ���Ĳ���Profile����ֵ��latestPath
  open = new JButton("open");
  this.add(open);
  this.setSize(200,150);
  this.setLocationRelativeTo(null);//���ھ���
  this.setVisible(true);
  open.addActionListener(this);
  this.addWindowListener(new WindowAdapter(){
   public void windowClosing(WindowEvent we){
    profile.write(latestPath);//ÿ���˳�����ʱ�����һ�δ򿪵�Ŀ¼д�뵽�����ļ�
   }
  });
 } 
 public static void main(String[] args) {
  new FileChooser();
 } 
 public void actionPerformed(ActionEvent arg0) {
  if(!new File(latestPath).exists()){
   latestPath = "C:/";
  }else{
   chooser = new JFileChooser(latestPath);
   int returnVal = chooser.showOpenDialog(this);
      if(returnVal == JFileChooser.APPROVE_OPTION) {
       File file = chooser.getSelectedFile();
       //��������...
       latestPath = file.getParent();//ÿ���˳��ļ�ѡ���������Ŀ¼Properties
      }     
  } 
 }
}
class Profile{
 String latestPath = "C:/";
 File file = new File("set.ini"); 
 public Profile(){}
 boolean create(){
  boolean b = true;  
  if(file!=null){
   File directory = file.getParentFile();//����ļ��ĸ�Ŀ¼
   if(!directory.exists()){//Ŀ¼������ʱ
    b = directory.mkdirs();//����Ŀ¼
   }else{//����Ŀ¼
    if(!file.exists()){//�����ļ�������ʱ
     try {
      b = file.createNewFile();//���������ļ�
     } catch (IOException e) {
      b = false;
     }
    }
   }
  }  return b;
}
 
 boolean read(){
  Properties pro;//���Լ�
  FileInputStream is = null;
  boolean b = true;
  if(!file.exists()){//�����ļ�������ʱ
   b = create();//����һ��
   if(b)//�����ɹ���
   b = write(latestPath);//��ʼ��
   else//����ʧ�ܼ������������ļ�ʱ�����Ի�����ʾ����
    JOptionPane.showConfirmDialog(null, "�Բ��𣬲����������ļ���", "����", 
     JOptionPane.YES_NO_OPTION, 
     JOptionPane.ERROR_MESSAGE);
  }else{
   try {
    is = new FileInputStream(file);
    pro = new Properties();
    pro.load(is);//��ȡ����
    latestPath = pro.getProperty("latestPath");//��ȡ���ò���latestPath��ֵ
    is.close();
   }
   catch (IOException ex) {
    ex.printStackTrace(); 
    b =  false;
   }
  }
  return b;
 }
 boolean write(String latestPath){  
  this.latestPath = latestPath;
  Properties pro = null;
  FileOutputStream os = null;
  boolean b = true;
  try {
   os = new FileOutputStream(file);
   pro = new Properties();
   
   pro.setProperty("latestPath",latestPath); 
     
   pro.store(os,null); //������д��  
   os.flush();
   os.close();
   
   System.out.println("latestPath=" + latestPath);
 
  }
  catch (IOException ioe) {
   b = false;
   ioe.printStackTrace();
  }
  return b;
 }
}