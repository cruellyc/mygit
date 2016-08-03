package netty;

import java.util.Scanner;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * @author liyc
 * @date 2016年8月3日 上午9:16:35
 * @version 2.0
 */
public class TimeClientHandler extends SimpleChannelUpstreamHandler {
	//当绑定到服务端的时候触发
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		System.out.print("输入：");
		@SuppressWarnings("resource")
		Scanner in=new Scanner(System.in);
		String input=in.nextLine();
		e.getChannel().write(input);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		System.out.println("客服端接收：" + e.getMessage());
		e.getChannel().close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}
}
