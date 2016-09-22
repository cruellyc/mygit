package netty;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * @author liyc
 * @date 2016年8月3日 上午9:13:46
 * @version 2.0
 */
public class DiscardServerHandler extends SimpleChannelUpstreamHandler {
	//当有客户端绑定到服务端的时候触发
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		System.out.println("有客户端绑定到服务端");
	};
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		System.out.println("服务器接收：" + e.getMessage());

	/*	ChannelBuffer buffer = (ChannelBuffer) e.getMessage(); // 五位读取 
		while(buffer.readableBytes()>= 5) {
			ChannelBuffer tempBuffer = buffer.readBytes(5);
			System.out.println(tempBuffer.toString(Charset.defaultCharset()));
		}
		// 读取剩下的信息
		System.out.println(buffer.toString(Charset.defaultCharset()));*/
		e.getChannel().write("ok");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		Channel ch = e.getChannel();
		ch.close();
	}
}
