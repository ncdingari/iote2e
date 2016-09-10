package com.pzybrick.test.iote2e.ruleproc.ignite;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.pzybrick.iote2e.ruleproc.svc.SourceSensorActuator;

import junit.framework.Assert;

public class TestIgniteSourceSensorHandlerHumidityToMister extends TestIgniteSourceSensorHandlerBase {
	private static final Log log = LogFactory.getLog(TestIgniteSourceSensorHandlerHumidityToMister.class);
	private static String testSourceUuid = "8043c648-a45d-4352-b024-1b4dd72fe9bc";
	private static String testSensorUuid = "fb0440cd-5933-47c2-b7f2-a60b99fa0ba8";
	private String filterKey;


	public TestIgniteSourceSensorHandlerHumidityToMister() {
		super();
		filterKey = testSourceUuid + "|" + testSensorUuid + "|";
	}
	
	@Test
	public void testHumidityToMisterRuleFireFanOff() {
		log.info("begins");

		String testValue = "50";
		commonRun( testSourceUuid, testSensorUuid, testValue, filterKey);
		List<String> subscribeResults = commonThreadSubscribeResults( 2000 );
		Assert.assertNotNull("subscribeResults must not be null", subscribeResults );
		Assert.assertEquals("subscribeResults must have size=1", 1, subscribeResults.size() );
		SourceSensorActuator sourceSensorActuator = gson.fromJson(subscribeResults.get(0), SourceSensorActuator.class);
		Assert.assertEquals("subscribeResults getActuatorTargetValue", "on", sourceSensorActuator.getActuatorValue() );
	}
	
	@Test
	public void testHumidityToMisterRuleFireFanOn() {
		log.info("begins");
		String testValue = "100";
		commonRun( testSourceUuid, testSensorUuid, testValue, filterKey);
		List<String> subscribeResults = commonThreadSubscribeResults( 2000 );
		Assert.assertNotNull("subscribeResults must not be null", subscribeResults );
		Assert.assertEquals("subscribeResults must have size=1", subscribeResults.size(), 1 );
		SourceSensorActuator sourceSensorActuator = gson.fromJson(subscribeResults.get(0), SourceSensorActuator.class);
		Assert.assertEquals("subscribeResults getActuatorTargetValue", "off", sourceSensorActuator.getActuatorValue() );
	}
	
	@Test
	public void testHumidityToMisterRuleNotFire() {
		log.info("begins");
		String testValue = "87";
		commonRun( testSourceUuid, testSensorUuid, testValue, filterKey);
		List<String> subscribeResults = commonThreadSubscribeResults( 2000 );
		Assert.assertEquals("subscribeResults must be empty", 0, subscribeResults.size() );
	}
}
