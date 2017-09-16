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
package com.pzybrick.iote2e.tests.ignite;

import java.util.List;

import org.apache.avro.util.Utf8;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.pzybrick.iote2e.schema.avro.Iote2eResult;


/**
 * The Class TestIgniteHandlerLed.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestIgniteHandlerLed extends TestIgniteHandlerBase {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(TestIgniteHandlerLed.class);

	/**
	 * Instantiates a new test ignite handler led.
	 *
	 * @throws Exception the exception
	 */
	public TestIgniteHandlerLed() throws Exception {
		super();
	}
	
	/**
	 * Test led green on.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedGreenOn() throws Exception {
		logger.info("begins");
		String filterKey = testLedLoginName + "|" + testLedSourceName + "|" + testLedSensorNameGreen + "|";
		String testLedValue = "1";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameGreen, testLedValue, filterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 3000, queueIote2eResults );
		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults getActuatorTargetValue", "green", 
				iote2eResults.get(0).getPairs().get(new Utf8("actuatorValue")).toString()  );
	}
	
	/**
	 * Test led green off.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedGreenOff() throws Exception {
		logger.info("begins");
		String filterKey = testLedLoginName + "|" + testLedSourceName + "|" + testLedSensorNameGreen + "|";
		String testLedValue = "0";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameGreen, testLedValue, filterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 3000, queueIote2eResults );

		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults getActuatorTargetValue", "off", 
				iote2eResults.get(0).getPairs().get(new Utf8("actuatorValue")).toString()  );
	}
	
	/**
	 * Test led red on.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedRedOn() throws Exception {
		logger.info("begins");
		String filterKey = testLedLoginName + "|" + testLedSourceName + "|" + testLedSensorNameRed + "|";
		String testLedValue = "1";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameRed, testLedValue, filterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 3000, queueIote2eResults );

		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults getActuatorTargetValue", "red", 
				iote2eResults.get(0).getPairs().get(new Utf8("actuatorValue")).toString()  );
	}
	
	/**
	 * Test led red off.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedRedOff() throws Exception {
		logger.info("begins");
		String filterKey = testLedLoginName + "|" + testLedSourceName + "|" + testLedSensorNameRed + "|";
		String testLedValue = "0";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameRed, testLedValue, filterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 3000, queueIote2eResults );
		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults getActuatorTargetValue", "off", 
				iote2eResults.get(0).getPairs().get(new Utf8("actuatorValue")).toString()  );
	}
	
	/**
	 * Test led yellow on.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedYellowOn() throws Exception {
		logger.info("begins");
		String filterKey = testLedLoginName + "|" + testLedSourceName + "|" + testLedSensorNameYellow + "|";
		String testLedValue = "1";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameYellow, testLedValue, filterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 3000, queueIote2eResults );

		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults getActuatorTargetValue", "yellow", 
				iote2eResults.get(0).getPairs().get(new Utf8("actuatorValue")).toString()  );
	}
	
	/**
	 * Test led yellow off.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedYellowOff() throws Exception {
		logger.info("begins");
		String filterKey = testLedLoginName + "|" + testLedSourceName + "|" + testLedSensorNameYellow + "|";
		String testLedValue = "0";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameYellow, testLedValue, filterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 3000, queueIote2eResults );

		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults getActuatorTargetValue", "off", 
				iote2eResults.get(0).getPairs().get(new Utf8("actuatorValue")).toString()  );
	}
	
}
