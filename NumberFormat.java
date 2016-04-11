import java.text.DecimalFormat;
/**
 * 格式化数字
 * */
public class NumberFormat {
	
	public static String intToString(int n, int l) {  
        DecimalFormat decimalFormat = new DecimalFormat(initString(  
                '0', l));  
        return decimalFormat.format(n);  
    }  
      
    public static String initString(char ch, int length) {  
        if (length < 0)  
            return "";  
        char chars[] = new char[length];  
        for (int i = 0; i < length; i++)  
            chars[i] = ch;  
        return new String(chars);  
    }
    
    public static void main(String[] args) {  
        System.out.println(initString('0', 5));  
                //生成五位的字符串  
        System.out.println(intToString(45, 5));  
                //个数化的数字为45  
    } 
}
