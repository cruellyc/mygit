/*

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.seed.common.Conf;
import com.seed.entity.SelOrdr;


*//**
 * 发送邮件工具类
 * @author luob
 *
 *//*
public class EmailUtils {
	
	*//**
	 * 发送支付成功后的邮件
	 * @param conf
	 *//*
	public static boolean sendOrdrEmail(SelOrdr ordr, String suprEmail, Conf conf) {
		boolean emailSendSuccess = true;
		String FROM = conf.getStr("FROM");
        Session session = getSession(FROM, conf);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSubject("云聚到家订单,订单号" + ordr.getOrdrSn());
            String content = generyContent(ordr, conf);
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = new InternetAddress[] {
            	new InternetAddress(suprEmail),
            	new InternetAddress(FROM)
            };
            message.setRecipients(RecipientType.TO, address);
            message.setContent(content, "text/html;charset=utf-8");
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            emailSendSuccess = false;
        }
        return emailSendSuccess;
    }
	
	*//**
	 * 发送每日邮件
	 * @param conf
	 *//*
	public static boolean sendSummaryEmail(String suprEmail, Conf conf, String fileName) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startTime = format.format(new Date()) + " 00:00:00";
		String endTime = format.format(new Date()) + " 23:59:59";
		boolean emailSendSuccess = true;
		String FROM = conf.getStr("FROM");
        Session session = getSession(FROM, conf);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        try {
        	message.setSubject("今日订单核对（" + startTime + "至" + endTime + "）");
        	StringBuilder content = new StringBuilder();
        	content.append("<body><div style='width:600px;font-family: 微软雅黑;font-size:14px'>");
        	content.append("<span>您好，附件为今日云聚社区新增的订单，请核对。如有错误，请及时与我司联系。</span><br /><br />");
        	content.append("<span>客服电话：" + conf.getStr("phone") + "</span><br />");
        	content.append("<div style='float:right'>云聚社区</div><br />");
            content.append("<div style='float:right'>" + endTime + "</div>");
        	content.append("</div></body>");
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = new InternetAddress[] {
            	new InternetAddress(suprEmail),
            	new InternetAddress(FROM)
            };
            message.setRecipients(RecipientType.TO, address);
            Multipart multipart = new MimeMultipart();         
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content.toString(),"text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);
            BodyPart messageBodyPart= new MimeBodyPart();
            DataSource source = new FileDataSource(conf.getStr("ImgPath")+"exp/" + fileName);
            messageBodyPart.setDataHandler(new DataHandler(source));
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            messageBodyPart.setFileName("=?UTF-8?B?"+enc.encode(fileName.getBytes())+"?=");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            emailSendSuccess = false;
        }
        return emailSendSuccess;
    }
	
	public static Session getSession(final String FROM, final Conf conf) {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", conf.getStr("mail.transport.protocol"));
        props.setProperty("mail.smtp.host", conf.getStr("mail.smtp.host")); 
        props.setProperty("mail.smtp.port", conf.getStr("mail.smtp.port"));
        props.setProperty("mail.smtp.auth", conf.getStr("mail.smtp.auth"));
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String password = conf.getStr("password");
                return new PasswordAuthentication(FROM, password);
            }
            
        });
        return session;
    }
	
	public static String generyContent(SelOrdr ordr, Conf conf) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder builder = new StringBuilder();
		String tableStyle = "border-top:1px solid #ccc;border-left:1px solid #ccc;";
		String tdStyle = "padding:12px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;font-family: 微软雅黑;font-size:14px;";
		String thStyle = "padding:12px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;background:#eee;";
		builder.append("<body style='font-family: 微软雅黑;font-size:14px'>");
		builder.append("<span>尊敬的供应商：</span><br /><br />");
		builder.append("<span>又有新的订单咯~</span><br /><br />");
		builder.append("<table style='" + tableStyle + "' width='800' border='0' cellspacing='0' cellpadding='0'>");
		builder.append("  <tr>");
		builder.append("    <td style='" + tdStyle + "' colspan='6'>订单号：" + ordr.getOrdrSn() + "</td>");
		builder.append("  </tr>");
		builder.append("  <tr>");
		builder.append("    <td style='" + tdStyle + "' colspan='4'>下单人：" + ordr.getUserName() + "</td>");
		builder.append("    <td style='" + tdStyle + "' colspan='2'>地址：" + ordr.getAddress() + "</td>");
		builder.append("  </tr>");
		builder.append("  <tr>");
		builder.append("    <td style='" + tdStyle + "' colspan='4'>联系电话：" + ordr.getMobile() + "</td>");
		builder.append("    <td style='" + tdStyle + "' colspan='2'>下单时间：" + ordr.getPayTime() + "</td>");
		builder.append("  </tr>");
		builder.append("  <tr>");
		builder.append("    <th style='" + thStyle + "'>商品名称</th>");
		//builder.append("    <th style='" + thStyle + "'>数量(件)</th>");
		//builder.append("    <th style='" + thStyle + "'>价格(元)</th>");
		builder.append("    <th style='" + thStyle + "'>付款情况</th>");
		builder.append("    <th style='" + thStyle + "'>预约服务时间</th>");
		builder.append("    <th style='" + thStyle + "'>备注</th>");
		builder.append("  </tr>");
		builder.append("  <tr>");
		builder.append("    <td style='" + tdStyle + "'>" + ordr.getAddress() + "</td>");
		//builder.append("    <td style='" + tdStyle + "'>" + ordr.getNum() + "</td>");
		//builder.append("    <td style='" + tdStyle + "'>" + ordr.getAmount() + "</td>");
		builder.append("    <td style='" + tdStyle + "'>已付款</td>");
		builder.append("    <td style='" + tdStyle + "'>" + ordr.getPayTime() + "</td>");
		builder.append("    <td style='" + tdStyle + "'>" + ordr.getMessage() + "</td>");
		builder.append("  </tr>");
		builder.append("</table>");
		builder.append("<br /><span>FROM：云聚社区</span><br /><br />");
		builder.append("<span>客服电话：" + conf.getStr("phone") + "</span><br /><br />");
		builder.append("<span>" + format.format(new Date()) + "</span><br />");
		builder.append("</body>");
		return builder.toString();
	}

}
*/