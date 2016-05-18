import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
/**
 * 获取类属性方法
 * @author liyc
 *
 */
public class GetOtherClassFiled {
	public static void main(String[] args) throws ClassNotFoundException {  
        Class<?> demo = Class.forName("ExcelUtil");  
        System.out.println("获取当前类的所有属性:=========================");  
        Field field[] = demo.getDeclaredFields();  
        for(int i=0;i<field.length;i++){  
            int mo = field[i].getModifiers();  
            //修饰符  
            String prev = Modifier.toString(mo);  
            //属性类型  
            Class<?> type = field[i].getType();  
            System.out.println(prev + " " + type.getName() + " " + field[i].getName());  
        }  
        System.out.println("实现的父类接口属性:============================");  
        Field field2[] = demo.getFields();  
        for(int j=0;j<field2.length;j++){  
            int mo = field2[j].getModifiers();  
            String prev = Modifier.toString(mo);  
            Class<?> type = field2[j].getType();  
            System.out.println(prev + " " + type.getName() + " " + field2[j].getName());  
        }
        Method[] m=demo.getMethods();
        for(int i=0;i<m.length;i++){
        	System.out.println(m[i].getName());
        }
     }
}
