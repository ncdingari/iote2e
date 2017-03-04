package com.pzybrick.iote2e.ruleproc.spark;

import java.io.Serializable;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;

import com.pzybrick.iote2e.common.config.MasterConfig;
import com.pzybrick.iote2e.ruleproc.router.Router;
import com.pzybrick.iote2e.ruleproc.router.RouterIote2eRequestImpl;
import com.pzybrick.iote2e.schema.avro.Iote2eRequest;
import com.pzybrick.iote2e.schema.util.Iote2eRequestReuseItem;

import consumer.kafka.MessageAndMetadata;

public class Iote2eRequestSparkProcessor implements Serializable {
	private static final long serialVersionUID = 2989374902129650863L;
	private static final Logger logger = LogManager.getLogger(Iote2eRequestSparkProcessor.class);
	private Iote2eRequestReuseItem iote2eRequestReuseItem;
	private MasterConfig masterConfig;

	
	public Iote2eRequestSparkProcessor(MasterConfig masterConfig) {
		this.iote2eRequestReuseItem = new Iote2eRequestReuseItem();
		this.masterConfig = masterConfig;
	}

    public void processIote2eRequestRDD(JavaRDD<MessageAndMetadata> rdd) {
        logger.debug("Processing Iote2eRequestRDD {}, rdd={}", Thread.currentThread().getId(), rdd.toString() );
        rdd.foreachPartition(partition -> processPartition(partition));
    }

    public void processPartition(Iterator<MessageAndMetadata> partition) throws Exception {
        logger.debug("Starting to process partition");
        try (Router router = new RouterIote2eRequestImpl(masterConfig); ) {
            partition.forEachRemaining(messageAndMetadata -> processIote2eRequestRecord(messageAndMetadata, router));
        } catch(Exception e ) {
            logger.error(e.getMessage(),e);
        }
        logger.debug("Finished processing partition");
    }

    public void processIote2eRequestRecord(MessageAndMetadata messageAndMetadata, Router router) {
        try {
        	logger.debug("Processing message with key: {}", new String(messageAndMetadata.getKey()) );
        	Iote2eRequest iote2eRequest = iote2eRequestReuseItem.fromByteArray(messageAndMetadata.getPayload() );
            router.add(iote2eRequest);
        } catch (Exception e) {
            logger.error("Error processing record : " + e.getMessage(), e);
        }
    }


}
