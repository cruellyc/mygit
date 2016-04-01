import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Boolean;
import jxl.write.DateFormats;
import jxl.write.Number;
import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtil {
	//创建excel
	public void craeteExcel(OutputStream os) throws IOException, WriteException, WriteException{
		//创建工作簿
		WritableWorkbook workbook=Workbook.createWorkbook(os);
		
		//创建新的一页
		WritableSheet sheet=workbook.createSheet("First Sheet", 0);
		//创建要显示的内容，创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
		Label xuexiao=new Label(0, 0, "学校");
		sheet.addCell(xuexiao);
		Label zhuanye = new Label(1,0,"专业");
		sheet.addCell(zhuanye);
		Label jingzhengli = new Label(2,0,"专业竞争力");
		sheet.addCell(jingzhengli);
		
		Label qinghua = new Label(0,1,"清华大学");
		sheet.addCell(qinghua);
		Label jisuanji = new Label(1,1,"计算机专业");
		sheet.addCell(jisuanji);
		Label gao = new Label(2,1,"高");
		sheet.addCell(gao);
		
		Label beida = new Label(0,2,"北京大学");
		sheet.addCell(beida);
		Label falv = new Label(1,2,"法律专业");
		sheet.addCell(falv);
		Label zhong = new Label(2,2,"中");
		sheet.addCell(zhong);
		
		Label ligong = new Label(0,3,"北京理工大学");
		sheet.addCell(ligong);
		Label hangkong = new Label(1,3,"航空专业");
		sheet.addCell(hangkong);
		Label di = new Label(2,3,"低");
		sheet.addCell(di);
		
		//把创建的内容写入到输出流中，并关闭输出了
		workbook.write();
		workbook.close();
		os.close();
	}
	public void createExcel2(OutputStream os) throws WriteException,IOException {
        //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet", 0);
        //创建要显示的具体内容
        Label formate = new Label(0,0,"数据格式");
        sheet.addCell(formate);
        Label floats = new Label(1,0,"浮点型");
        sheet.addCell(floats);
        Label integers = new Label(2,0,"整型");
        sheet.addCell(integers);
        Label booleans = new Label(3,0,"布尔型");
        sheet.addCell(booleans);
        Label dates = new Label(4,0,"日期格式");
        sheet.addCell(dates);
        
        Label example = new Label(0,1,"数据示例");
        sheet.addCell(example);
        //浮点数据
        Number number = new Number(1,1,3.1415926535);
        sheet.addCell(number);
        //整形数据
        Number ints = new Number(2,1,15042699);
        sheet.addCell(ints);
        Boolean bools = new Boolean(3,1,true);
        sheet.addCell(bools);
        //日期型数据
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        WritableCellFormat cf1 = new WritableCellFormat(DateFormats.FORMAT1);
        DateTime dt = new DateTime(4,1,date,cf1);
        sheet.addCell(dt);
        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();
        
    }
	//对比字段是否相同
	public static boolean equalGetMethod(Method m, String name) {
		String s = m.getName().substring(3);
		if (s.equalsIgnoreCase(name))
			return true;
		else
			return false;
	}
	@SuppressWarnings("rawtypes")
	public static void excelExport(HttpServletResponse response,String fname,Map<String,String> map,List list) throws IOException, WriteException{
		OutputStream os=response.getOutputStream();
		response.reset();
		response.setCharacterEncoding("utf-8");
		fname=URLEncoder.encode(fname, "utf-8");
		response.setHeader("CONtent-Disposition", "attachment;filename="+new String(fname.getBytes("utf-8"),"gbk")+".xls");
		response.setContentType("application/msexcel");//定义输出类型
		//创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet", 0);
        
        WritableFont font1 = new WritableFont(WritableFont.TIMES, 10,
				WritableFont.NO_BOLD);
		WritableCellFormat format1 = new WritableCellFormat(font1);
		int n=0;
		List<String> columnList=new ArrayList<String>();
		//添加列名
		for(String key:map.keySet()){
			sheet.addCell(new Label(n, 0, map.get(key),format1));
			columnList.add(key);
			n++;
		}
		List<Method> methodList = new ArrayList<Method>();
        //创建要显示的具体内容
        if(list!=null){
        	for(int i=0;i<list.size();i++){
        		//获取方法名
        		if(i==0){
        			Object obj=list.get(0);
        			Method[] methods=obj.getClass().getMethods();
        			for(Method method:methods){
        				if(method.getName().startsWith("get")&&!method.getName().startsWith("getClass")){
        					methodList.add(method);
        				}
        			}
        		}
        		int row=i+1;
        		int k=0;
        		for(String c:columnList){
        			for(Method m:methodList){
        				if(equalGetMethod(m,c)){
        					try {
								Object o=m.invoke(list.get(i));
								if(o==null){
									sheet.addCell(new Label(k++, row, "", format1));
								}else{
									sheet.addCell(new Label(k++, row, o+"", format1));
								}
								break;
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
        				}
        			}
        		}
        		
    		}
        }
        workbook.write();
		workbook.close();
	}
	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(xls)$");
	}

	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(xlsx)$");
	}

	public static String getString(Cell cell) {
		String str = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			str = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			str = String.format("%.0f", cell.getNumericCellValue());
			break;
		}
		return str;
	}

	public static Integer getInteger(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			return (int) cell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			String str = cell.getStringCellValue();
			if (str.equals(""))
				return null;
			return Integer.parseInt(str);
		}
		return null;
	}

	public static Double getDouble(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			String str = cell.getStringCellValue();
			if (str.equals(""))
				return null;
			return Double.parseDouble(str);
		}
		return null;
	}
	/**
	 * 获取当前日期，返回字符串格式
	 * 
	 * @return
	 */
	public static String getCurrtDoneTimeString() {
		String d="";
		try {
			Date date = new Date();
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
			d=dateFm.format(date);
			return d;
		} catch (Exception e) {
			return d;
		}

	}
	/**
	 * Excel导出
	 * 
	 * @param propertiesName
	 *            配置文件名，例"seedreport.properties"
	 * @param list
	 *            导出所需数据
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static void exportData(HttpServletRequest req,
			HttpServletResponse resp, String propertiesName, List list,
			String fileName) {
		OrderProperties properties = null;
		try {
			// Resource resource = new
			// ClassPathResource("export\\"+propertiesName);
			// properties = PropertiesLoaderUtils.loadProperties(resource);
			properties = new OrderProperties();
			properties.load(new InputStreamReader(ExcelUtil.class
					.getResourceAsStream(propertiesName), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		List<String> title = new ArrayList<String>();
		List<String> columnList = new ArrayList<String>();
		for (Object t : properties.keySet()) {
			title.add(properties.getProperty(t.toString()));
			columnList.add(t.toString());
		}
		ServletOutputStream out = null;
		try {
			out = resp.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/octet-stream");
		String str = null;
		try {
			str = new String(fileName.getBytes("gbk"), "ISO8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return;
		}
		resp.setHeader("Content-disposition", "attachment; filename=" + str
				+ ".xls");
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(out);

			WritableSheet sheet = workbook.createSheet("sheet", 0);

			WritableFont font1 = new WritableFont(WritableFont.TIMES, 10,
					WritableFont.NO_BOLD);
			WritableCellFormat format1 = new WritableCellFormat(font1);
			List<Method> methods = new ArrayList<Method>();
			for (int i = 0; i < title.size(); i++) {
				sheet.addCell(new Label(i, 0, title.get(i), format1));
			}
			if (list == null) {
				workbook.write();
				workbook.close();
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				int row = i + 1;
				if (i == 0) {
					Object obj = list.get(i);
					Method[] methods2 = obj.getClass().getMethods();
					for (Method m : methods2) {
						if (m.getName().startsWith("get")
								&& !m.getName().equals("getClass")) {
							methods.add(m);
						}
					}
				}
				int k = 0;
				for (String c : columnList) {
					for (Method m : methods) {
						if (equalGetMethod(m, c)) {
							Object o = null;
							;
							try {

								o = m.invoke(list.get(i));
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
							if (o == null)
								sheet.addCell(new Label(k++, row, "", format1));
							else
								sheet.addCell(new Label(k++, row, "" + o,
										format1));
							break;
						}
					}
				}
			}
			workbook.write();
			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return;
	}
}
