package com.socket;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class AIOTest {
	@Test
	public void testServer() throws IOException, InterruptedException {
		SimpleServer server = new SimpleServer(7788);
		System.out.println("server");
		Thread.sleep(10000);
	}

	@Test
	public void testClient() throws IOException, InterruptedException, ExecutionException {
		SimpleClient client = new SimpleClient("localhost", 7788);
		client.write((byte) 11);
	}
}
