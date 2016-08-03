import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.HashBag;



public class Test {
	private static double FLAG = 0.5;

	public static void main(String[] args) {
		/*String user1 = "a";
		String user2 = "b";
		String user3 = "c";
		String user4 = "d";
		Map<String, int[]> map = new HashMap<>();
		map.put(user1, new int[] { 1, 2, 3 });
		map.put(user2, new int[] { 2, 3, 4, 5 });
		map.put(user3, new int[] { 4, 5, 6, 7 });
		map.put(user4, new int[] { 1, 2, 3, 4, 5, 6, 7 });
		//Map<String, List> recommendMap = computeRecommend(map);
		//printData(recommendMap);
		// expect:
		// a -> (4,5)
		// b -> (1,6,7)
		// c -> (2,3)
		js(map);*/
		
		//downloadFile("http://10.16.8.22:8080/smc2/disp/feeOrdr/getAllCharge?flag=123456","D:\\var\\smc\\img\\user\\file.txt");
		//pushBackInputStream();
		/*SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		System.out.println(sdf.format(new Date()));
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, +1);
		System.out.println(new Date().getMonth());
		calendar.add(Calendar.MONTH, -(new Date().getMonth()));
		date = calendar.getTime();
		for(int i=1;i<25;i++){
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, -1);
			date = calendar.getTime();
			System.out.println(sdf.format(date));
		}*/
		beanToXML();
		XMLStringToBean();
	}
	    
	    public static void beanToXML() {  
	        Classroom classroom = new Classroom(1, "软件工程", 4);  
	        Student student = new Student(101, "张三", 22, classroom);  
	  
	        try {  
	            JAXBContext context = JAXBContext.newInstance(Student.class);  
	            Marshaller marshaller = context.createMarshaller();  
	            marshaller.marshal(student, System.out);  
	        } catch (JAXBException e) {  
	            e.printStackTrace();  
	        }  
	  
	    }  
	      
	     
	    public static void XMLStringToBean(){  
	        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><student><age>22</age><classroom><grade>4</grade><id>1</id><name>软件工程</name></classroom><id>101</id><name>张三</name></student>";  
	        try {  
	            JAXBContext context = JAXBContext.newInstance(Student.class);  
	            Unmarshaller unmarshaller = context.createUnmarshaller();  
	            Student student = (Student)unmarshaller.unmarshal(new StringReader(xmlStr));  
	            System.out.println("\n"+student.getAge());  
	            System.out.println(student.getClassroom().getName());  
	        } catch (JAXBException e) {  
	            e.printStackTrace();  
	        }  
	          
	    }  
	public static void pushBackInputStream(){
		 String str = "hello,rollenholt";  
		    PushbackInputStream push = null; // 声明回退流对象  
		    ByteArrayInputStream bat = null; // 声明字节数组流对象  
		    bat = new ByteArrayInputStream(str.getBytes());  
		    push = new PushbackInputStream(bat); // 创建回退流对象，将拆解的字节数组流传入  
		    int temp = 0;  
		    try {
				while ((temp = push.read()) != -1) { // push.read()逐字节读取存放在temp中，如果读取完成返回-1  
				   if (temp == ',') { // 判断读取的是否是逗号  
				      push.unread(temp); //回到temp的位置  
				      temp = push.read(); //接着读取字节  
				      System.out.print("(回退" + (char) temp + ") "); // 输出回退的字符  
				   } else {  
				      System.out.print((char) temp); // 否则输出字符  
				   }  
				}
			} catch (IOException e) {
				e.printStackTrace();
			}  
	}
	 public static void downloadFile(String remoteFilePath, String localFilePath)
	    {
	        URL urlfile = null;
	        HttpURLConnection httpUrl = null;
	        BufferedInputStream bis = null;
	        BufferedOutputStream bos = null;
	        File f = new File(localFilePath);
	        try
	        {
	            urlfile = new URL(remoteFilePath);
	            httpUrl = (HttpURLConnection)urlfile.openConnection();
	            httpUrl.connect();
	            bis = new BufferedInputStream(httpUrl.getInputStream());
	            System.out.println(httpUrl.getInputStream().available());
	            bos = new BufferedOutputStream(new FileOutputStream(f));
	            int len = 0;
	            byte[] b = new byte[1024*4];
	            while ((len = bis.read(b)) != -1)
	            {
	                bos.write(b, 0, len);
	            }
	            bos.flush();
	            bis.close();
	            httpUrl.disconnect();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
	            try
	            {
	                bis.close();
	                bos.close();
	            }
	            catch (IOException e)
	            {
	                e.printStackTrace();
	            }
	        }
	    }
	
	public static void js(Map<String, int[]> data){
		Set<String> cname=data.keySet();
		List<String> clist=new ArrayList<String>();
		
		Map<String,List<Integer>> map=new HashMap<String,List<Integer>>();
		for(String c:cname){
			clist.add(c);
			map.put(c, new ArrayList<Integer>());
		}
		for(int i=0;i<clist.size();i++){
			int[] a=data.get(clist.get(i));
			List<Integer> al=new ArrayList<Integer>();
			for(int l=0;l<clist.size();l++){
				System.out.println("\n"+clist.get(i)+"********"+clist.get(l));
				if(clist.get(i).equals(clist.get(l))){
					continue;
				}
				List<Integer> bl=map.get(clist.get(l));
				int[] b=data.get(clist.get(l));
				int n=0;
				int f=0;
				for(int j=0;j<a.length;j++){
					for(int k=0;k<b.length;k++){
						if(a[j]==b[k]){
							n++;
							f=1;
							break;
						}
					}
					for(Integer r:bl){
						if(a[j]==r){
							f=1;
						}
					}
					if(f!=1){
						System.out.println(clist.get(i)+"********"+clist.get(l)+"********"+a[j]);
						al.add(a[j]);
					}else{
						f=0;
					}
				}
				if(n<2){
					al=null;
				}else{
					System.out.println(clist.get(l)+"****&&&****"+al);
					bl.addAll(al);
				}
				al=new ArrayList<Integer>();
				map.put(clist.get(l), bl);
			}
		}
		for(int i=0;i<clist.size();i++){
			System.out.print(clist.get(i)+" -> (");
			List<Integer> bl=map.get(clist.get(i));
			for(int n=0;n<bl.size();n++){
				System.out.print(bl.get(n));
				if(n!=bl.size()-1){
					System.out.print(",");
				}
			}
			System.out.println(")");
		}
	}
	
	
	
	public static Map<String, List> computeRecommend(Map<String, int[]> data) {

		Map<String, List> recommed = new HashMap<>();
		String[] nameList = initRecommendResult(data, recommed);

		for (int i = 0; i < nameList.length; i++) {

			for (int j = i + 1; j < nameList.length; j++) {

				Map<String, Object> repeatResult = getRepeatCount(data.get(nameList[i]), data.get(nameList[j]));

				Set repeatSet = (Set) repeatResult.get("repeatSet");
				if (repeatSet.size() > 0) {

					adviseByPercentage(recommed, nameList[i], nameList[j], repeatResult);

				}
			}
		}

		return recommed;
	}
	/*@ResponseBody
	@RequestMapping(value = "/getAllCharge")
	public void getAllCharge(@RequestParam(value = "callback", required = false) String callback, 
			@RequestParam(value = "flag", required = true) String flag,HttpServletResponse response,HttpServletRequest request) {
		if (flag.equals("123456")) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
			Calendar calendar = Calendar.getInstance();
			Date date = new Date(System.currentTimeMillis());
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, -1);
			date = calendar.getTime();
			logger.debug(sdf.format(date)+"**************"+sdf.format(new Date()));
			BaseLst<FeeChrgDto> lst=feeOrdrService.loadChrgList("1", 1, 10);

			
			try {
				FileOutputStream fop = null;
				File file;
				String path = conf.getStr("ImgPath");
				file = new File(path+"feeTxt"+sdf.format(date)+"-"+sdf.format(new Date())+".txt");
				fop = new FileOutputStream(file);
				
				if (!file.exists()) {
					file.createNewFile();
				}
				String json=JSON.toJSONString(lst);
				fop.write(json.getBytes());
				fop.flush();
				fop.close();
				@SuppressWarnings("resource")
				BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
				ByteArrayOutputStream out=new ByteArrayOutputStream();
		        byte[] buffer=new byte[1024*4];
		        int n=0;
		        while ( (n=bufferedInputStream.read(buffer)) !=-1) {
		            out.write(buffer,0,n);
		        }
				byte[] contentInBytes=out.toByteArray();
				out.close();
				response.setHeader("Content-Type", "txt");
				response.setHeader("Content-Length", String.valueOf(contentInBytes.length));
				logger.error("length "+ String.valueOf(contentInBytes.length));
				ServletOutputStream os = response.getOutputStream();
				FileCopyUtils.copy(contentInBytes, os);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	public static Map<String, Object> getRepeatCount(int[] M, int[] N) {

		Map<String, Object> result = new HashMap<>();

		if (M.length == 0 || N.length == 0) {
			return result;
		}

		Set<Integer> setM = getUniqueSet(M);
		Set<Integer> setN = getUniqueSet(N);
		HashBag bag = new HashBag();
		bag.addAll(setM);
		bag.addAll(setN);

		Set set = bag.uniqueSet();

		Set repeatSet = new HashSet();

		for (Object o : set) {
			if (bag.getCount(o) >= 2) {
				repeatSet.add(o);
			}
		}

		result.put("repeatSet", repeatSet);

		Map<String, Object> resultM = new HashMap<>();
		Map<String, Object> resultN = new HashMap<>();
		setM.removeAll(repeatSet);
		setN.removeAll(repeatSet);

		resultM.put("percentage", repeatSet.size() / (double) setM.size());
		resultM.put("unRepeatSet", setM);

		resultN.put("percentage", repeatSet.size() / (double) setN.size());
		resultN.put("unRepeatSet", setN);

		result.put("M", resultM);
		result.put("N", resultN);
		return result;
	}

	private static Set<Integer> getUniqueSet(int[] M) {
		Set<Integer> setM = new HashSet<Integer>();
		for (int m : M)
			setM.add(m);
		return setM;
	}

	private static void adviseByPercentage(Map<String, List> recommed, String i, String j,
			Map<String, Object> repeatResult) {
		HashMap m = (HashMap) repeatResult.get("M");
		HashMap n = (HashMap) repeatResult.get("N");

		if ((double) m.get("percentage") >= FLAG) {
			recommed.get(i).addAll((Set) n.get("unRepeatSet"));
		}
		if ((double) n.get("percentage") >= FLAG) {
			recommed.get(j).addAll((Set) m.get("unRepeatSet"));
		}
	}

	private static String[] initRecommendResult(Map<String, int[]> data, Map<String, List> recommed) {
		Set<String> names = data.keySet();
		String[] nameList = new String[names.size()];

		int index = 0;
		for (String s : names) {
			recommed.put(s, new ArrayList());
			nameList[index] = s;
			index++;
		}
		return nameList;
	}

	public static void printData(Map<String, List> data) {
		if (data == null) {
			System.out.println("null");
			return;
		}
		for (Map.Entry<String, List> entry : data.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}
	}
}
