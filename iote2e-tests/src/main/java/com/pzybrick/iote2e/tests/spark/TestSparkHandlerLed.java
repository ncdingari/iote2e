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
package com.pzybrick.iote2e.tests.spark;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.pzybrick.iote2e.stream.persist.ActuatorStateDao;
import com.pzybrick.iote2e.schema.avro.Iote2eResult;
import com.pzybrick.iote2e.schema.util.Iote2eSchemaConstants;


/**
 * The Class TestSparkHandlerLed.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSparkHandlerLed extends TestSparkHandlerBase {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(TestSparkHandlerLed.class);
	
	/** The Constant pkActuatorStateGreen. */
	private static final String pkActuatorStateGreen = testLedLoginName + "|" + testLedSourceName + "|" + testLedSensorNameGreen;
	
	/** The Constant pkActuatorStateYellow. */
	private static final String pkActuatorStateYellow = testLedLoginName + "|" + testLedSourceName + "|" + testLedSensorNameYellow;
	
	/** The Constant pkActuatorStateRed. */
	private static final String pkActuatorStateRed = testLedLoginName + "|" + testLedSourceName + "|" + testLedSensorNameRed;

	/**
	 * Instantiates a new test spark handler led.
	 *
	 * @throws Exception the exception
	 */
	public TestSparkHandlerLed() throws Exception {
		super();
	}
	
	/**
	 * Test led led green on.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedLedGreenOn() throws Exception {
		logger.info("begins");
		String testLedValue = "1";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameGreen, testLedValue, testLedGreenFilterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 10000, queueIote2eResults );
		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults PAIRNAME_SENSOR_NAME", testLedSensorNameGreen, iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_SENSOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_NAME", "ledGreen1", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_VALUE", "green", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_VALUE).toString());
		Assert.assertEquals("Cassandra actuator_state Green LED value=green", "green", ActuatorStateDao.findActuatorValue(pkActuatorStateGreen));
	}
	
	/**
	 * Test led led green off.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedLedGreenOff() throws Exception {
		logger.info("begins");
		String testLedValue = "0";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameGreen, testLedValue, testLedGreenFilterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 10000, queueIote2eResults );
		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults PAIRNAME_SENSOR_NAME", testLedSensorNameGreen, iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_SENSOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_NAME", "ledGreen1", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_VALUE", "off", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_VALUE).toString());
		Assert.assertEquals("Cassandra actuator_state Green LED value=off", "off", ActuatorStateDao.findActuatorValue(pkActuatorStateGreen));
	}
	
	/**
	 * Test led led red on.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedLedRedOn() throws Exception {
		logger.info("begins");
		String testLedValue = "1";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameRed, testLedValue, testLedRedFilterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 10000, queueIote2eResults );
		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults PAIRNAME_SENSOR_NAME", testLedSensorNameRed, iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_SENSOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_NAME", "ledRed1", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_VALUE", "red", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_VALUE).toString());
		Assert.assertEquals("Cassandra actuator_state Red LED value=red", "red", ActuatorStateDao.findActuatorValue(pkActuatorStateRed));
	}
	
	/**
	 * Test led led red off.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedLedRedOff() throws Exception {
		logger.info("begins");
		String testLedValue = "0";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameRed, testLedValue, testLedRedFilterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 10000, queueIote2eResults );
		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults PAIRNAME_SENSOR_NAME", testLedSensorNameRed, iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_SENSOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_NAME", "ledRed1", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_VALUE", "off", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_VALUE).toString());
		Assert.assertEquals("Cassandra actuator_state Red LED value=off", "off", ActuatorStateDao.findActuatorValue(pkActuatorStateRed));
	}
	
	/**
	 * Test led led yellow on.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedLedYellowOn() throws Exception {
		logger.info("begins");
		String testLedValue = "1";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameYellow, testLedValue, testLedYellowFilterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 10000, queueIote2eResults );
		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults PAIRNAME_SENSOR_NAME", testLedSensorNameYellow, iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_SENSOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_NAME", "ledYellow1", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_VALUE", "yellow", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_VALUE).toString());
		Assert.assertEquals("Cassandra actuator_state Yellow LED value=yellow", "yellow", ActuatorStateDao.findActuatorValue(pkActuatorStateYellow));
	}
	
	/**
	 * Test led led yellow off.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLedLedYellowOff() throws Exception {
		logger.info("begins");
		String testLedValue = "0";
		commonRun( testLedLoginName, testLedSourceName, testLedSourceType, testLedSensorNameYellow, testLedValue, testLedYellowFilterKey);
		List<Iote2eResult> iote2eResults = commonThreadSubscribeGetIote2eResults( 10000, queueIote2eResults );
		Assert.assertNotNull("iote2eResults is null", iote2eResults == null );
		Assert.assertEquals("iote2eResults must have size=1", 1, iote2eResults.size() );
		Assert.assertEquals("iote2eResults PAIRNAME_SENSOR_NAME", testLedSensorNameYellow, iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_SENSOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_NAME", "ledYellow1", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_NAME).toString());
		Assert.assertEquals("iote2eResults PAIRNAME_ACTUATOR_VALUE", "off", iote2eResults.get(0).getPairs().get(Iote2eSchemaConstants.PAIRNAME_ACTUATOR_VALUE).toString());
		Assert.assertEquals("Cassandra actuator_state Yellow LED value=off", "off", ActuatorStateDao.findActuatorValue(pkActuatorStateYellow));
	}
	
}
