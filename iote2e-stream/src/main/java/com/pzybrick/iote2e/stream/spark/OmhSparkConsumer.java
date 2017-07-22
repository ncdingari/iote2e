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

public class OmhSparkConsumer {
	private static final Logger logger = LogManager.getLogger(OmhSparkConsumer.class.getName());
    private SparkConf conf;
    private JavaStreamingContext ssc;
    private boolean started = false;
	
	
    public static void main(String[] args) throws Exception {
    	OmhSparkConsumer omhSparkConsumer = new OmhSparkConsumer();
    	MasterConfig masterConfig = MasterConfig.getInstance( args[0], args[1], args[2] );
    	omhSparkConsumer.process( masterConfig );
//    	RunProcess runProcess = new RunProcess( omhSparkConsumer);
//    	runProcess.start();
//    	try {
//    		Thread.sleep(5000);
//    	} catch( Exception e ) {}
//    	omhSparkConsumer.stop();
//    	runProcess.join();

    }
    
    // local testing
    private static class RunProcess extends Thread {
    	private OmhSparkConsumer omhSparkConsumer;
    	private MasterConfig masterConfig;
    	
    	public RunProcess( OmhSparkConsumer omhSparkConsumer, MasterConfig masterConfig ) {
    		this.omhSparkConsumer = omhSparkConsumer;
    		this.masterConfig = masterConfig;
    	}
		@Override
		public void run() {
			try {
	    		omhSparkConsumer.process( masterConfig );
			} catch( Exception e ) {
				logger.error(e.getMessage(), e);
			}
		}
    }
    	
    public void process(MasterConfig masterConfig) throws Exception {
    	logger.info(masterConfig.toString());
    	String sparkAppNameOmh = masterConfig.getSparkAppNameOmh();
    	String sparkMaster = masterConfig.getSparkMaster();
    	Integer kafkaConsumerNumThreads = masterConfig.getKafkaConsumerNumThreads();
    	Integer sparkStreamDurationMs = masterConfig.getSparkStreamDurationMs();
    	String kafkaGroupOmh = masterConfig.getKafkaGroupOmh();
    	String kafkaTopicOmh = masterConfig.getKafkaTopicOmh();
    	String kafkaZookeeperHosts = masterConfig.getKafkaZookeeperHosts();
    	Integer kafkaZookeeperPort = masterConfig.getKafkaZookeeperPort();
    	String kafkaZookeeperBrokerPath = masterConfig.getKafkaZookeeperBrokerPath();
    	String kafkaConsumerId = masterConfig.getKafkaConsumerId();
    	String kafkaZookeeperConsumerConnection = masterConfig.getKafkaZookeeperConsumerConnection();
    	String kafkaZookeeperConsumerPath = masterConfig.getKafkaZookeeperConsumerPath();

        conf = new SparkConf()
                .setAppName(sparkAppNameOmh);
        if( sparkMaster != null && sparkMaster.length() > 0 ) conf.setMaster( sparkMaster );
        ssc = new JavaStreamingContext(conf, new Duration(sparkStreamDurationMs));

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(kafkaTopicOmh, new Integer(kafkaConsumerNumThreads));
        Properties kafkaProps = new Properties();
        kafkaProps.put("group.id", kafkaGroupOmh);
        // Spark Kafka Consumer https://github.com/dibbhatt/kafka-spark-consumer
        kafkaProps.put("zookeeper.hosts", kafkaZookeeperHosts);
        kafkaProps.put("zookeeper.port", String.valueOf(kafkaZookeeperPort) );
        kafkaProps.put("zookeeper.broker.path", kafkaZookeeperBrokerPath );
        kafkaProps.put("kafka.topic", kafkaTopicOmh);
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

        OmhSparkProcessor streamProcessor = new OmhSparkProcessor(masterConfig);
        
        int numberOfReceivers = 6;	
        
		try {
			JavaDStream<MessageAndMetadata> unionStreams = ReceiverLauncher.launch(
					ssc, kafkaProps, numberOfReceivers, StorageLevel.MEMORY_ONLY());		
			unionStreams.foreachRDD(streamProcessor::processOmhRDD);
			logger.info("Starting OmhSparkConsumer");
			ssc.start();
		} catch( Exception e ) {
			logger.error(e.getMessage(),e);
			System.exit(8);
		}

		try {
			logger.info("Started OmhSparkConsumer");
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
    
    public void stop() throws Exception {
    	logger.info("Stopping Spark...");
    	ssc.stop(true);
    }

	public boolean isStarted() {
		return started;
	}

}
