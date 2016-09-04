package com.pzybrick.iote2e.ws.igniteavro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pzybrick.learnjetty.jsr.EventServerSocket;
import com.pzybrick.learnjetty.jsr.WaveItem;

public class EventServerAvro {
	public static final Map<String, EventServerSocketAvro> eventServerSocketAvros = new ConcurrentHashMap<String, EventServerSocketAvro>();
	public static final ConcurrentLinkedQueue<String> messagesToSend = new ConcurrentLinkedQueue<String>();


	public static void main(String[] args) {
		EventServerAvro eventServerAvro = new EventServerAvro();
		eventServerAvro.process();
	}

	
	public void process() {
		SubAvroWaveThread subAvroWaveThread = null;
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(8080);
		server.addConnector(connector);

		// Setup the basic application "context" for this application at "/"
		// This is also known as the handler tree (in jetty speak)
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		try {
			subAvroWaveThread = new SubAvroWaveThread( messagesToSend );
			subAvroWaveThread.start();
			
			// Initialize javax.websocket layer
			ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);

			// Add WebSocket endpoint to javax.websocket layer
			wscontainer.addEndpoint(EventServerSocketAvro.class);

			server.start();
			//server.dump(System.err);
			SendMessagesToClients sendMessagesToClients = new SendMessagesToClients();
			sendMessagesToClients.start();
			//new PollForMessages(sendMessagesToClients).start();
			server.join();
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		} finally {
			if( subAvroWaveThread != null ) subAvroWaveThread.shutdown();
		}
	}

	
	public static class SendMessagesToClients extends Thread {
		@Override
		public void run() {
			List<String> messages = new ArrayList<String>();
			try {
				while (true) {
					while (!messagesToSend.isEmpty()) {
						messages.add(messagesToSend.poll());
					}
					//System.out.println("found messages: " + messages.size());
					if (!eventServerSocketAvros.isEmpty()) {
						System.out.println("Sending " + messages.size() + " to clients");
						for (Map.Entry<String, EventServerSocketAvro> entry : eventServerSocketAvros.entrySet()) {
							// TODO: convert the list into a JSON array
							for (String message : messages) {
								entry.getValue().getSession().getBasicRemote().sendText(message);
							}
						}
					}
					messages.clear();
					try {
						sleep(500L);
					} catch (InterruptedException e) {
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	public static class PollForMessages extends Thread {
		private static final int NUM_TEST_SENSORS = 7;
		private SendMessagesToClients sendMessagesToClients;
	    private Gson gson;
	    private List<String> testUuids;

		public PollForMessages(SendMessagesToClients sendMessagesToClients) {
			super();
			this.sendMessagesToClients = sendMessagesToClients;
			this.gson = new GsonBuilder().create();
			createTestUuids();
		}

		@Override
		public void run() {
			runTriangleWave();
			//runHeartbeat();
		}
		
		private void runTriangleWave() {
			try {
				WaveItem waveItem = new WaveItem();
				boolean isUp = true;
				double waveValue = roundDblTwoDec(0);
				while (true) {
					if( isUp ) {
						waveValue = roundDblTwoDec(waveValue + .1);
						if(waveValue == 2.0 ) isUp = false;
					} else {
						waveValue = roundDblTwoDec(waveValue - .1);
						if(waveValue == 0 ) isUp = true;
					}
					waveItem.setTimeMillis(System.currentTimeMillis());
					waveItem.setWaveValue(waveValue);
					
					for( int i=0 ; i<NUM_TEST_SENSORS ; i++ ) {
						waveItem.setSensorId(i);
						waveItem.setSensorUuid(testUuids.get(i));
						waveItem.setWaveValue( waveItem.getWaveValue()+i);
						String testMessage = gson.toJson(waveItem);
						messagesToSend.add(testMessage);
					}
					sendMessagesToClients.interrupt();
					sleep(250L);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		public void runHeartbeat() {
			try {
				WaveItem waveItem = new WaveItem();
				while (true) {
					waveItem.setTimeMillis(System.currentTimeMillis());
					waveItem.setWaveValue(1);
					
					for( int i=0 ; i<NUM_TEST_SENSORS ; i++ ) {
						waveItem.setSensorId(i);
						waveItem.setSensorUuid(testUuids.get(i));
						waveItem.setWaveValue( waveItem.getWaveValue()+i);
						String testMessage = gson.toJson(waveItem);
						messagesToSend.add(testMessage);
					}
					sendMessagesToClients.interrupt();
					sleep(500L);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		private void createTestUuids() {
			for( int i=0 ; i<NUM_TEST_SENSORS ; i++) {
				testUuids.add( "00-00-00-" + String.format("%02d", i));
			}
		}
	}

	
	
	public static double roundDblTwoDec( double dbl ) {
		dbl = dbl*100;
		dbl = Math.round(dbl);
		dbl = dbl /100;
		return dbl;
	}
}