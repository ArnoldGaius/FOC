package Analysis;

/** 
 * ������Ϣ 
 * @author Gaius 
 * 
 */  
public class Error {  
    //�к�  
    private Integer hh;  
    //������Ϣ  
    private String msg;  
    public Error(Integer hh, String msg) {  
        this.hh = hh;  
        this.msg = msg;  
    }  
    public Integer getHh() {  
        return hh;  
    }  
    public void setHh(Integer hh) {  
        this.hh = hh;  
    }  
    public String getMsg() {  
        return msg;  
    }  
    public void setMsg(String msg) {  
        this.msg = msg;  
    }  
}  