/**
 *    Copyright 2016, 2017 Peter Zybrick and others.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * 
 * @author  Pete Zybrick
 * @version 1.0.0, 2017-09
 * 
 */
package com.pzybrick.iote2e.stream.spark;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import com.pzybrick.iote2e.common.config.MasterConfig;

import consumer.kafka.Config;
import consumer.kafka.MessageAndMetadata;
import consumer.kafka.ReceiverLauncher;


/**
 * The Class Iote2eRequestSparkConsumer.
 */
public class Iote2eRequestSparkConsumer {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(Iote2eRequestSparkConsumer.class.getName());
    
    /** The conf. */
    private SparkConf conf;
    
    /** The ssc. */
    private JavaStreamingContext ssc;
    
    /** The started. */
    private boolean started = false;
	
	
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
    	Iote2eRequestSparkConsumer iote2eRequestSparkConsumer = new Iote2eRequestSparkConsumer();
    	MasterConfig masterConfig = MasterConfig.getInstance( args[0], args[1], args[2] );
    	iote2eRequestSparkConsumer.process( masterConfig );
//    	RunProcess runProcess = new RunProcess( iote2eRequestSparkConsumer);
//    	runProcess.start();
//    	try {
//    		Thread.sleep(5000);
//    	} catch( Exception e ) {}
//    	iote2eRequestSparkConsumer.stop();
//    	runProcess.join();

    }
    
    /**
     * The Class RunProcess.
     */
    private static class RunProcess extends Thread {
    	
	    /** The iote 2 e request spark consumer. */
	    private Iote2eRequestSparkConsumer iote2eRequestSparkConsumer;
    	
	    /** The master config. */
	    private MasterConfig masterConfig;
    	
    	/**
	     * Instantiates a new run process.
	     *
	     * @param iote2eRequestSparkConsumer the iote 2 e request spark consumer
	     * @param masterConfig the master config
	     */
	    public RunProcess( Iote2eRequestSparkConsumer iote2eRequestSparkConsumer, MasterConfig masterConfig ) {
    		this.iote2eRequestSparkConsumer = iote2eRequestSparkConsumer;
    		this.masterConfig = masterConfig;
    	}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			try {
	    		iote2eRequestSparkConsumer.process( masterConfig );
			} catch( Exception e ) {
				logger.error(e.getMessage(), e);
			}
		}
    }
    	
    /**
     * Process.
     *
     * @param masterConfig the master config
     * @throws Exception the exception
     */
    public void process(MasterConfig masterConfig) throws Exception {
    	logger.info(masterConfig.toString());
    	String sparkAppName = masterConfig.getSparkAppName();
    	String sparkMaster = masterConfig.getSparkMaster();
    	Integer kafkaConsumerNumThreads = masterConfig.getKafkaConsumerNumThreads();
    	Integer sparkStreamDurationMs = masterConfig.getSparkStreamDurationMs();
    	String kafkaGroup = masterConfig.getKafkaGroup();
    	String kafkaTopic = masterConfig.getKafkaTopic();
    	String kafkaZookeeperHosts = masterConfig.getKafkaZookeeperHosts();
    	Integer kafkaZookeeperPort = masterConfig.getKafkaZookeeperPort();
    	String kafkaZookeeperBrokerPath = masterConfig.getKafkaZookeeperBrokerPath();
    	String kafkaConsumerId = masterConfig.getKafkaConsumerId();
    	String kafkaZookeeperConsumerConnection = masterConfig.getKafkaZookeeperConsumerConnection();
    	String kafkaZookeeperConsumerPath = masterConfig.getKafkaZookeeperConsumerPath();

        conf = new SparkConf()
                .setAppName(sparkAppName);
        if( sparkMaster != null && sparkMaster.length() > 0 ) conf.setMaster( sparkMaster );
        ssc = new JavaStreamingContext(conf, new Duration(sparkStreamDurationMs));

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(kafkaTopic, new Integer(kafkaConsumerNumThreads));
        Properties kafkaProps = new Properties();
        kafkaProps.put("group.id", kafkaGroup);
        // Spark Kafka Consumer https://github.com/dibbhatt/kafka-spark-consumer
        kafkaProps.put("zookeeper.hosts", kafkaZookeeperHosts);
        kafkaProps.put("zookeeper.port", String.valueOf(kafkaZookeeperPort) );
        kafkaProps.put("zookeeper.broker.path", kafkaZookeeperBrokerPath );
        kafkaProps.put("kafka.topic", kafkaTopic);
        kafkaProps.put("kafka.consumer.id", kafkaConsumerId );
        kafkaProps.put("zookeeper.consumer.connection", kafkaZookeeperConsumerConnection);
        kafkaProps.put("zookeeper.consumer.path", kafkaZookeeperConsumerPath);
        // consumer optional 
        kafkaProps.put("consumer.forcefromstart", "false");
        kafkaProps.put("consumer.fetchsizebytes", "1048576");
        kafkaProps.put("consumer.fillfreqms", "200" );
        // kafkaProps.put("consumer.fillfreqms", String.valueOf(sparkStreamDurationMs) );
        kafkaProps.put("consumer.backpressure.enabled", "true");
        //kafkaProps.put("consumer.num_fetch_to_buffer", "10");
                
        kafkaProps.put( Config.KAFKA_PARTITIONS_NUMBER, 4 );
        
        kafkaProps.put("zookeeper.session.timeout.ms", "400");
        kafkaProps.put("zookeeper.sync.time.ms", "200");
        kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");

        Iote2eRequestSparkProcessor streamProcessor = new Iote2eRequestSparkProcessor(masterConfig);
        
        int numberOfReceivers = 6;	
        
		try {
			JavaDStream<MessageAndMetadata> unionStreams = ReceiverLauncher.launch(
					ssc, kafkaProps, numberOfReceivers, StorageLevel.MEMORY_ONLY());		
			unionStreams.foreachRDD(streamProcessor::processIote2eRequestRDD);
			logger.info("Starting Iote2eRequestSparkConsumer");
			ssc.start();
		} catch( Exception e ) {
			logger.error(e.getMessage(),e);
			System.exit(8);
		}

		try {
			logger.info("Started Iote2eRequestSparkConsumer");
			started = true;
			ssc.awaitTermination();
	    	logger.info("Stopped Spark");
		} catch( InterruptedException e1 ) {
			logger.warn(e1.getMessage());
		} catch( Exception e2 ) {
			logger.error(e2.getMessage(),e2);
			System.exit(8);
		}
		
    }
    
    /**
     * Stop.
     *
     * @throws Exception the exception
     */
    public void stop() throws Exception {
    	logger.info("Stopping Spark...");
    	ssc.stop(true);
    }

	/**
	 * Checks if is started.
	 *
	 * @return true, if is started
	 */
	public boolean isStarted() {
		return started;
	}

}
