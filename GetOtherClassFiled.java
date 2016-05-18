import java.lang.reflect.Constructor;
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
      //获取方法 
        Method[] m=demo.getMethods();
        for(int i=0;i<m.length;i++){
        	System.out.println(m[i].getName());
        }
        
        
      //获取接口类  
        Class<?> interfaces[] = demo.getInterfaces();  
       //获取父类  
        Class<?> parents = demo.getSuperclass();  
        //获取所有的构造函数  
        Constructor<?> constructors[] = demo.getConstructors();  
        for (int i = 0; i < interfaces.length; i++) {  
            System.out.println("实现了哪些接口类:" + interfaces[i].getName());  
        }  
        for (int i = 0; i < constructors.length; i++) {  
            System.out.println("类有哪些构造函数:" + constructors[i]);  
        }  
        System.out.println("继承的父类:" + parents.getName());  
        for (int i = 0; i < constructors.length; i++) {  
          Class<?> paramenter[] = constructors[i].getParameterTypes();  
          int mo = constructors[i].getModifiers();  
          System.out.println(Modifier.toString(mo) + " " + constructors[i].getName());  
          for(int j=0;j<paramenter.length;j++){  
              System.out.println(paramenter[j].getName());  
         }  
         }
     }
}
