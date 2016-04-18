/*package cn.seedtec.smc.foreign.queryfee;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seedtec.smc.Conf;
import cn.seedtec.util.XmlUtils;

@Controller
@RequestMapping("repairLand")
public class RepairLand {
	private Logger logger = Logger.getLogger(RepairLand.class);
	private Conf conf;
	@Autowired
	public void setConf(Conf conf) {
		this.conf = conf;
	}
	@RequestMapping("create")
	@ResponseBody
	public String create(@RequestParam(value = "transactionID", required = true) String transactionID,
			@RequestParam(value = "regionName", required = true) String regionName ,
			@RequestParam(value = "applyNo", required = true) String applyNo,
			@RequestParam(value = "applyType", required = true) String applyType,
			@RequestParam(value = "custID", required = true) String custID,
			@RequestParam(value = "roomNo", required = true) String roomNo,
			@RequestParam(value = "reneName", required = true) String reneName,
			@RequestParam(value = "telephone", required = true) String telephone,
			@RequestParam(value = "applyDate", required = true) String applyDate,
			@RequestParam(value = "scheduleDate", required = false) String scheduleDate,
			@RequestParam(value = "applyLevel", required = true) String applyLevel,
			@RequestParam(value = "itemName", required = true) String itemName,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "remark", required = true) String remark){
		String ret = "";
		if(scheduleDate==null) scheduleDate="";
		regionName=escCn(regionName);
		reneName=escCn(reneName);
		applyType=escCn(applyType);
		itemName=escCn(itemName);
		content=escCn(content);
		remark=escCn(remark);
		applyLevel=escCn(applyLevel);
		if(scheduleDate!=null) scheduleDate=escCn(scheduleDate);
		logger.debug("scheduleDate:"+scheduleDate);
		String pd =
			"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sky=\"www.skyland.com\">" +
				"<soapenv:Header/>" +
				"<soapenv:Body>" +
				"<sky:createRepair soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
					"<xmlData xsi:type=\"xsd:string\"><![CDATA[" +
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
					"<SKYLAND>" +
					"<WsUsername>SKYLAND</WsUsername>" +
					"<WsPassword>skyland!#($</WsPassword>" +
					"<DATAS>" +
					"<TransactionID>"+transactionID+"</TransactionID>" +
					"<RegionName>"+regionName+"</RegionName>" +
					"<ApplyNo>"+applyNo+"</ApplyNo>" +
					"<ApplyType>"+applyType+"</ApplyType>" +
					"<CustID>"+custID+"</CustID>" +
					"<RoomNo>"+roomNo+"</RoomNo>" +
					"<ReneName>"+reneName+"</ReneName>" +
					"<Telephone>"+telephone+"</Telephone>" +
					"<ApplyDate>"+applyDate+"</ApplyDate>" +
					"<ScheduleDate>"+scheduleDate+"</ScheduleDate>" +
					"<ApplyLevel>"+applyLevel+"</ApplyLevel>" +
					"<ItemName>"+itemName+"</ItemName>" +
					"<Content>"+content+"</Content>" +
					"<Remark>"+remark+"</Remark>" +
					"</DATAS>" +
					"</SKYLAND>" +
					"]]></xmlData>" +
					"</sky:createRepair>" +
					"</soapenv:Body>" +
					"</soapenv:Envelope>";
		String sUrl = conf.getStr("LoadRepair");
		logger.debug(pd);
		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		client.getHttpConnectionManager().getParams().setSoTimeout(5000);
		PostMethod postMethod = new PostMethod(sUrl);
		postMethod.setRequestHeader("SOAPAction", "");
		postMethod.setRequestBody(pd);
		try {
			int sc = client.executeMethod(postMethod);
			if(sc == HttpStatus.SC_OK){
				InputStream responseBody = postMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody,"utf-8"));
				String line = reader.readLine();
				while(line != null){
					ret = ret + line;
					logger.debug(new String(line.getBytes()));
					line = reader.readLine();
				}
				Document doc = XmlUtils.genDoc(ret);
				if(doc!=null){
					Element eRet = (Element) XPath.selectSingleNode(doc.getRootElement(), "//createRepairReturn");
					logger.debug(eRet.getText());
					Document d2 = XmlUtils.genDoc(eRet.getText());
					logger.debug("RESULT=" + d2.getRootElement().getChildText("RESULT"));
					logger.debug("MESSAGE=" + d2.getRootElement().getChildText("MESSAGE"));
					ret = d2.getRootElement().getChildText("MESSAGE");
					String rc=d2.getRootElement().getChildText("RESULT");
					logger.debug("rc=" + rc);
					if(rc.equals("0")) return "S";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "F";
		}
		return ret;
	}
	@RequestMapping("delete")
	@ResponseBody
	public String delete(@RequestParam(value = "transactionID", required = true) String transactionID){
		String ret = "";
		String pd =
			"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sky=\"www.skyland.com\">" +
				"<soapenv:Header/>" +
				"<soapenv:Body>" +
				"<sky:deleteRepair soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
				"<xmlData xsi:type=\"xsd:string\"><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<SKYLAND>" +
				"<WsUsername>SKYLAND</WsUsername>" +
				"<WsPassword>skyland!#($</WsPassword>" +
				"<DATAS>" +
					"<TransactionID>"+transactionID+"</TransactionID>" +
				"</DATAS>" +
				"</SKYLAND>]]></xmlData>" +
				"</sky:deleteRepair>" +
				"</soapenv:Body>" +
			"</soapenv:Envelope>";
		String sUrl = conf.getStr("LoadRepair");
		logger.debug(pd);
		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
		client.getHttpConnectionManager().getParams().setSoTimeout(10000);
		PostMethod postMethod = new PostMethod(sUrl);
		postMethod.setRequestHeader("SOAPAction", "");
		postMethod.setRequestBody(pd);
		try {
			int sc = client.executeMethod(postMethod);
			if(sc == HttpStatus.SC_OK){
				InputStream responseBody = postMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody,"utf-8"));
				String line = reader.readLine();
				while(line != null){
					ret = ret + line;
					logger.debug(new String(line.getBytes()));
					line = reader.readLine();
				}
				Document doc = XmlUtils.genDoc(ret);
				if(doc!=null){
					Element eRet = (Element) XPath.selectSingleNode(doc.getRootElement(), "//deleteRepairReturn");
					logger.debug(eRet.getText());
					Document d2 = XmlUtils.genDoc(eRet.getText());
					logger.debug("RESULT=" + d2.getRootElement().getChildText("RESULT"));
					logger.debug("MESSAGE=" + d2.getRootElement().getChildText("MESSAGE"));
					ret = d2.getRootElement().getChildText("MESSAGE");
					String rc=d2.getRootElement().getChildText("RESULT");
					logger.debug("rc=" + rc);
					if(rc.equals("0")||rc.equals("14")) return "S";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "F";
		}
		return ret;
	}
	@RequestMapping("update")
	@ResponseBody
	public String update(@RequestParam(value = "transactionID", required = true) String transactionID,
			@RequestParam(value = "regionName", required = true) String regionName ,
			@RequestParam(value = "applyNo", required = true) String applyNo,
			@RequestParam(value = "applyType", required = true) String applyType,
			@RequestParam(value = "custID", required = true) String custID,
			@RequestParam(value = "roomNo", required = true) String roomNo,
			@RequestParam(value = "reneName", required = true) String reneName,
			@RequestParam(value = "telephone", required = true) String telephone,
			@RequestParam(value = "applyDate", required = true) String applyDate,
			@RequestParam(value = "scheduleDate", required = true) String scheduleDate,
			@RequestParam(value = "applyLevel", required = true) String applyLevel,
			@RequestParam(value = "itemName", required = true) String itemName,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "remark", required = true) String remark){
		String ret = "";
		if(scheduleDate==null) scheduleDate="";
		regionName=escCn(regionName);
		reneName=escCn(reneName);
		applyType=escCn(applyType);
		itemName=escCn(itemName);
		content=escCn(content);
		remark=escCn(remark);
		applyLevel=escCn(applyLevel);
		if(scheduleDate!=null) scheduleDate=escCn(scheduleDate);
		String pd =
			"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sky=\"www.skyland.com\">" +
				"<soapenv:Header/>" +
				"<soapenv:Body>" +
				"<sky:updateRepair soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
					"<xmlData xsi:type=\"xsd:string\"><![CDATA[" +
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
					"<SKYLAND>" +
					"<WsUsername>SKYLAND</WsUsername>" +
					"<WsPassword>skyland!#($</WsPassword>" +
					"<DATAS>" +
					"<TransactionID>"+transactionID+"</TransactionID>" +
					"<RegionName>"+regionName+"</RegionName>" +
					"<ApplyNo>"+applyNo+"</ApplyNo>" +
					"<ApplyType>"+applyType+"</ApplyType>" +
					"<CustID>"+custID+"</CustID>" +
					"<RoomNo>"+roomNo+"</RoomNo>" +
					"<ReneName>"+reneName+"</ReneName>" +
					"<Telephone>"+telephone+"</Telephone>" +
					"<ApplyDate>"+applyDate+"</ApplyDate>" +
					"<ScheduleDate>"+scheduleDate+"</ScheduleDate>" +
					"<ApplyLevel>"+applyLevel+"</ApplyLevel>" +
					"<ItemName>"+itemName+"</ItemName>" +
					"<Content>"+content+"</Content>" +
					"<Remark>"+remark+"</Remark>" +
					"</DATAS>" +
					"</SKYLAND>" +
					"]]></xmlData>" +
					"</sky:updateRepair>" +
					"</soapenv:Body>" +
					"</soapenv:Envelope>";
		String sUrl = conf.getStr("LoadRepair");
		logger.debug(pd);
		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		client.getHttpConnectionManager().getParams().setSoTimeout(5000);
		PostMethod postMethod = new PostMethod(sUrl);
		postMethod.setRequestHeader("SOAPAction", "");
		postMethod.setRequestBody(pd);
		try {
			int sc = client.executeMethod(postMethod);
			if(sc == HttpStatus.SC_OK){
				InputStream responseBody = postMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody,"utf-8"));
				String line = reader.readLine();
				while(line != null){
					ret = ret + line;
					logger.debug(new String(line.getBytes()));
					line = reader.readLine();
				}
				Document doc = XmlUtils.genDoc(ret);
				if(doc!=null){
					Element eRet = (Element) XPath.selectSingleNode(doc.getRootElement(), "//updateRepairReturn");
					logger.debug(eRet.getText());
					Document d2 = XmlUtils.genDoc(eRet.getText());
					logger.debug("RESULT=" + d2.getRootElement().getChildText("RESULT"));
					logger.debug("MESSAGE=" + d2.getRootElement().getChildText("MESSAGE"));
					ret = d2.getRootElement().getChildText("MESSAGE");
					String rc=d2.getRootElement().getChildText("RESULT");
					logger.debug("rc=" + rc);
					if(rc.equals("0")) return "S";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "F";
		}
		return ret;
	}
	@RequestMapping("query")
	@ResponseBody
	public String query(@RequestParam(value = "transactionID", required = true) String transactionID){
		String ret = "";
		String pd =
			"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sky=\"www.skyland.com\">" +
				"<soapenv:Header/>" +
				"<soapenv:Body>" +
				"<sky:queryRepair soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
				"<xmlData xsi:type=\"xsd:string\"><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<SKYLAND>" +
				"<WsUsername>SKYLAND</WsUsername>" +
				"<WsPassword>skyland!#($</WsPassword>" +
				"<DATAS>" +
					"<TransactionID>"+transactionID+"</TransactionID>" +
				"</DATAS>" +
				"</SKYLAND>]]></xmlData>" +
				"</sky:queryRepair>" +
				"</soapenv:Body>" +
			"</soapenv:Envelope>";
		String sUrl = conf.getStr("LoadRepair");
		logger.debug(pd);
		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		client.getHttpConnectionManager().getParams().setSoTimeout(5000);
		PostMethod postMethod = new PostMethod(sUrl);
		postMethod.setRequestHeader("SOAPAction", "");
		postMethod.setRequestBody(pd);
		try {
			int sc = client.executeMethod(postMethod);
			if(sc == HttpStatus.SC_OK){
				InputStream responseBody = postMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody,"utf-8"));
				String line = reader.readLine();
				while(line != null){
					ret = ret + line;
					logger.debug(new String(line.getBytes()));
					line = reader.readLine();
				}
				Document doc = XmlUtils.genDoc(ret);
				if(doc!=null){
					Element eRet = (Element) XPath.selectSingleNode(doc.getRootElement(), "//queryRepairReturn");
					logger.debug(eRet.getText());
					Document d2 = XmlUtils.genDoc(eRet.getText());
					logger.debug("RESULT=" + d2.getRootElement().getChildText("RESULT"));
					logger.debug("MESSAGE=" + d2.getRootElement().getChildText("MESSAGE"));
					ret = d2.getRootElement().getChildText("MESSAGE");
					String rc=d2.getRootElement().getChildText("RESULT");
					logger.debug("rc=" + rc);
					if(rc.equals("0")){
						String sta = d2.getRootElement().getChild("DATAS").getChildText("Status");
						logger.debug(sta);
						if(sta.trim().equals("处理中")||sta.trim().equals("已派工")){
							return "2";
						}else if(sta.trim().equals("已处理")){
							return "3";
						}else{
							return "1";
						}
					}else{
						return "F";
					}
				}else{
					return "F";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "F";
		}
		return "F";
	}
	*//**
	 * 中文编码
	 * @param s
	 * @return
	 *//*
	private String escCn(String s){
		String r = "";
		for(int i=0; i<s.length(); i++){
			int str = s.codePointAt(i);
			r = r + "&#" + str + ";";
		}
		return r;
	}
}
*/