/*import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.seed.common.RespMsg;
import com.seed.entity.Goods;
import com.seed.entity.GoodsSupr;
import com.seed.entity.Goods_shelves_label;
import com.seed.entity.Goods_shelves_spec;
import com.seed.entity.Supr;

public class ZipXml {
	*//**上传zip*//*
	public RespMsg<Goods> uploadZip(HttpServletRequest request) {
		logger.info("uploadZip() start");
		RespMsg<Goods> msg = new RespMsg<Goods>("上传成功");
		//创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver =
				new CommonsMultipartResolver(request.getSession().getServletContext());
		//判断 request 是否有文件上传,即多部分请求
		if(multipartResolver.isMultipart(request)){
			//转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			//取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while(iter.hasNext()){
				MultipartFile file = multiRequest.getFile(iter.next());
				
				Pattern fileR = Pattern.compile("^.*\\.(?i)(zip)$");
				if(file!=null){
					//取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					
					String[] arr = myFileName.split("\\.");
					String picFormat = arr[(arr.length-1)];//后缀名

					logger.debug("--------------------后缀名------------------------"+picFormat);
					Matcher mat = fileR.matcher(myFileName);
					if(!mat.find()){
						msg.setSuccess(false);
						msg.setDesc("文件格式不正确");
						return msg;
					}
					logger.info("--------------------file.size------------------------"+file.getSize());
					String path = conf.getStr("goodsPath");
					File pathDir = new File(path);
					if (!pathDir.exists()) {
						pathDir.mkdirs();
					}
					
					CommonsMultipartFile cf= (CommonsMultipartFile)file; 
					DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
					File f = fi.getStoreLocation();
					//保存
					try {
						@SuppressWarnings("resource")
						FileInputStream fin=new FileInputStream(f);
						FileOutputStream fout=new FileOutputStream(new File(path+myFileName));
						//文件缓存区
						byte[] b = new byte[1024];
						while(fin.read(b)!=-1){
							fout.write(b);
							fout.flush();
						}
						fout.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//解压
					try {
						unzip(f,path);
					} catch (Exception e) {
						e.printStackTrace();
					}
					//读取文件
					String filename=myFileName.substring(0, myFileName.length()-picFormat.length()-1);
					Goods goods=readXml(path+filename+"/"+filename+".xml");
					if(goods!=null){
						List<String> imgList=imgList(path+filename+"/img");
						goods.setImgList(imgList);
						msg.setCont(goods);
					}else{
						return new RespMsg<Goods>("上传失败", false);
					}
				}
			}
		}
		
		logger.info("uploadZip() end");
		return msg;
	}
	*//**图片读取*//*
	private List<String> imgList(String imgpath) {
		List<String> imgList=new ArrayList<String>();
		File dir1=new File(imgpath);
		File[] files1 = dir1.listFiles();
		for(File file:files1){
			String img=imgpath+"/"+file.getName();
			logger.info(img.indexOf("thumb"));
			if(img.indexOf("thumb")==-1){
				imgList.add(img);
			}
		}
		return imgList;
	}
	*//**解压zip*//*
	private String unzip(File zipFile, String unzipDir) throws Exception {
		logger.info("unzip() start");
		@SuppressWarnings("resource")
		ZipFile zf = new ZipFile(zipFile);
		Enumeration enu = zf.entries();
		String result = "";
		
		while (enu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) enu.nextElement();
			String name = entry.getName();
			// 如果解压entry是目录,直接生成目录即可,不用写入,如果是文件,要讲文件写入
			String path = unzipDir + name;
			result = result + path + "<br/>";
			File file = new File(path);
			if (entry.isDirectory()) {
				if(file.exists()){
					file.delete();
				}
				file.mkdirs();
			} else {
				// 建议使用如下方式创建 流,和读取字节,不然会有乱码(当然要根据具体环境来定),具体原因请听
				InputStream is = zf.getInputStream(entry);
				byte[] buf1 = new byte[1024];
				int len;
				if (!file.exists()) {
					file.getParentFile().mkdirs();
					file.createNewFile();
				}
				OutputStream out = new FileOutputStream(file);
				while ((len = is.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				out.close();
			}
		}
		result = "文件解压成功,解压文件:" + result;
		logger.info("----------------unzip msg = " + result);
		logger.info("unzip() end");
		return result;
	}
	private Goods readXml(String filePath){
		Goods goods=null;
		File file=new File(filePath);
		Element element=null;
		DocumentBuilder db=null;
		DocumentBuilderFactory dbf=null;
		dbf=DocumentBuilderFactory.newInstance();
		try {
			goods=new Goods();
			List<Goods_shelves_label> glblist=new ArrayList<Goods_shelves_label>();
			List<Goods_shelves_spec> gspeclist=new ArrayList<Goods_shelves_spec>();
			List<GoodsSupr> gsuprlist=new ArrayList<GoodsSupr>();
			db=dbf.newDocumentBuilder();
			Document dt=db.parse(file);
			element=dt.getDocumentElement();
			logger.info("element.getNodeName:"+element.getNodeName());
			NodeList childNodes=element.getChildNodes();
			for(int i=0;i<childNodes.getLength();i++){
				Node node1=childNodes.item(i);
				logger.info(node1.getNodeName()+":"+node1.getTextContent());
				if("goodsNo".equals(node1.getNodeName())){
					goods.setGoodsNo(node1.getTextContent());
				}
				if("barcode".equals(node1.getNodeName())){
					goods.setBarcode(node1.getTextContent());
				}
				if("name".equals(node1.getNodeName())){
					goods.setName(node1.getTextContent());
				}
				if("goodsType".equals(node1.getNodeName())){
					String typeId=mallTypeDao.getMallTypeId(node1.getTextContent());
					if(typeId!=null&&!typeId.equals("null")){
						goods.setGoodsTypeId(typeId);
						goods.setTypeName(node1.getNodeName());
					}
				}
				if("price".equals(node1.getNodeName())){
					goods.setPrice(Float.parseFloat(node1.getTextContent()));
				}
				if("mallPrice".equals(node1.getNodeName())){
					goods.setMallPrice(Float.parseFloat(node1.getTextContent()));
				}
				if("jsPrice".equals(node1.getNodeName())){
					goods.setJsPrice(Float.parseFloat(node1.getTextContent()));
				}
				if("state".equals(node1.getNodeName())){
					goods.setState(Integer.parseInt(node1.getTextContent()));
				}
				if("describes".equals(node1.getNodeName())){
					goods.setDescribes(node1.getTextContent());
				}
				if("remark".equals(node1.getNodeName())){
					goods.setRemark(node1.getTextContent());
				}
				if("lable".equals(node1.getNodeName())){
					Goods_shelves_label glb=new Goods_shelves_label();
					NodeList childNodes2=node1.getChildNodes();
					for(int j=0;j<childNodes2.getLength();j++){
						Node node2=childNodes2.item(j);
						logger.info(node2.getNodeName()+":"+node2.getTextContent());
						if("name".equals(node2.getNodeName())){
							glb.setName(node2.getTextContent());
						}
					}
					glblist.add(glb);
				}
				if("supr".equals(node1.getNodeName())){
					GoodsSupr gsupr=new GoodsSupr();
					NodeList childNodes2=node1.getChildNodes();
					for(int j=0;j<childNodes2.getLength();j++){
						Node node2=childNodes2.item(j);
						logger.info(node2.getNodeName()+":"+node2.getTextContent());
						if("name".equals(node2.getNodeName())){
							Supr supr=suprDao.getSuprByName(node2.getTextContent());
							gsupr.setSupr(supr);
							gsupr.setSuprId(supr!=null?supr.getId():null);
						}
					}
					gsuprlist.add(gsupr);
				}
				if("spec".equals(node1.getNodeName())){
					Goods_shelves_spec gspec=new Goods_shelves_spec();
					NodeList childNodes2=node1.getChildNodes();
					for(int j=0;j<childNodes2.getLength();j++){
						Node node2=childNodes2.item(j);
						logger.info(node2.getNodeName()+":"+node2.getTextContent());
						if("specName".equals(node2.getNodeName())){
							gspec.setSpec(node2.getTextContent());
						}
						if("specType".equals(node2.getNodeName())){
							gspec.setSpecType(Integer.parseInt(node2.getTextContent()));
						}
						if("item".equals(node2.getNodeName())){
							gspec.setItem(node2.getTextContent());
						}
					}
					gspeclist.add(gspec);
				}
			}
			goods.setLabelList(glblist);
			goods.setSpecList(gspeclist);
			goods.setSuprList(gsuprlist);
			goods.setId(UUID.randomUUID().toString());
			goods.setSelNum(0);
			goods.setSelPer(0);
			goods.setValid(true);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return goods;
	}
}
*/