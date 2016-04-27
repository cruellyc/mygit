/*

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seed.common.Conf;

*//**
 * App升级
 * 文件复制 压缩
 * @author 吴Sir
 *
 *//*
@Controller
@RequestMapping("/app")
public class AppCtl {

	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private Conf conf;
	
	*//**
	 * 获取更新数据包
	 * 
	 * @param version App数据包版本号
	 *//*
	@ResponseBody
	@RequestMapping("/getUpdateData")
	public void getUpdateData(@RequestParam(value = "version", required = false) String version,
			HttpServletResponse response) {
		String appUpdateDir = conf.getStr("AppUpdateDir");
		String path;
		if (version == null || "".equals(version)) {//下载完整包
			path = appUpdateDir + File.separator + "full.zip";
		} else {
			path = appUpdateDir + File.separator + "patch.zip";
			try {
				List<String> vList = new ArrayList<String>();
				int intVersion = Integer.valueOf(version);
				File dir1 = new File(appUpdateDir + File.separator + "version");
				File[] files1 = dir1.listFiles();
				for (File file : files1) {
					if (file.isDirectory()) {
						try {
							int intS = Integer.valueOf(file.getName());
							if (intS > intVersion) {//比App版本号高的，是要添加到更新包里的版本
								vList.add(file.getName());
								logger.debug("****name="+file.getName());
							}
						} catch (NumberFormatException e) {
							// TODO: handle exception
							logger.error(e.getMessage());
						}
						
					}
				}
				// 复制文件
				for (String patch : vList) {
					String baseDir = appUpdateDir;
					String patchDir ="version"+ File.separator+ patch;
					File dir = new File(baseDir + File.separator + patchDir);
					File[] files = dir.listFiles();
					for (File file : files) {
						if (file.isDirectory()) {
							copyDir(file, baseDir, patchDir);
						} else {
							copyFile(file, baseDir, patchDir);
						}
					}
				}
				String baseDir = appUpdateDir + File.separator +"dyUpdate";
				File dyUpdateDir = new File(baseDir);
				if (!dyUpdateDir.exists()) {
					dyUpdateDir.mkdirs();
				}
				FileCopyUtils.copy(new File(appUpdateDir + File.separator + "v"), new File(dyUpdateDir, "v"));
				//System.out.println(appUpdateDir + File.separator + "v"+"***"+new File(dyUpdateDir, "v").getPath());
				// 添加压缩
				ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(path));
				File[] files = dyUpdateDir.listFiles();
				for (File file : files) {
					if (file.isDirectory()) {
						System.out.println("put dir " + file);
						putZipDir(zos, baseDir, file);
					} else {
						System.out.println("put file " + file);
						putZipEntry(zos, baseDir, file);
					}
				}
				zos.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}
		try {
			logger.error(path);
			Resource res = new FileSystemResource(path);
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ new String(res.getFilename().getBytes(), "UTF-8"));
			response.setHeader("Content-Length",
					new String(String.valueOf(res.contentLength()).getBytes(),
							"UTF-8"));
			logger.error("length "+new String(String.valueOf(res.contentLength()).getBytes(),
							"UTF-8"));
			ServletOutputStream os = response.getOutputStream();

			FileCopyUtils.copy(res.getInputStream(), os);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	private void copyDir(File dir, String baseDir, String patchDir) {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				copyDir(file, baseDir, patchDir);
			} else {
				copyFile(file, baseDir, patchDir);
			}
		}
	}

	private void copyFile(File file, String baseDir, String patchDir) {
		String posPath = file.getPath();
		posPath = posPath.substring((baseDir + File.separator+patchDir).length());
		String targetFilePath = baseDir + File.separator+"dyUpdate"+posPath;
		File targetFile = new File(targetFilePath);
		File targetFileParent = targetFile.getParentFile();
		if (!targetFileParent.exists()) {
			targetFileParent.mkdirs();
		}
		try {
			FileCopyUtils.copy(file, new File(targetFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}

	private void putZipEntry(ZipOutputStream zos, String baseDir, File file) throws IOException {
//		System.out.println("put file " + file);
		String entryName = file.getPath();
	//	System.out.println("entryName= " + entryName);
		entryName = entryName.substring(baseDir.length()+1);
	//	System.out.println("***entryName= " + entryName);
		ZipEntry zipEntry = new ZipEntry(entryName);
		zos.putNextEntry(zipEntry);
		InputStream is=new FileInputStream(file);  
        byte content[]=new byte[1024];
        int len = 0;
        while((len = is.read(content))!=-1){  
            zos.write(content, 0, len);  
        }  
        is.close();
        //添加到zip后删除
        file.delete();
	}

	private void putZipDir(ZipOutputStream zos, String baseDir, File dir) throws IOException {
//		System.out.println("put dir " + dir);
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				putZipDir(zos, baseDir, file);
			} else {
				putZipEntry(zos, baseDir, file);
			}
		}
	}

}
*/