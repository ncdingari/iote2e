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
package com.pzybrick.iote2e.tests.persist;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.pzybrick.iote2e.stream.persist.ActuatorStateDao;
import com.pzybrick.iote2e.stream.svc.ActuatorState;




/**
 * The Class TestActuatorStateDao.
 */
public class TestActuatorStateDao {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(TestActuatorStateDao.class);
	
	/** The Constant TEST_KEYSPACE_NAME. */
	private static final String TEST_KEYSPACE_NAME = "iote2e";
	
	/** The Constant TEST_KEYSPACE_REPLICATION_STRATEGY. */
	private static final String TEST_KEYSPACE_REPLICATION_STRATEGY = "SimpleStrategy";
	
	/** The Constant TEST_KEYSPACE_REPLICATION_FACTOR. */
	private static final int TEST_KEYSPACE_REPLICATION_FACTOR = 3;


	/**
	 * Test sequence.
	 */
	@Test
	public void testSequence() {
		try {
			ActuatorStateDao.dropKeyspace(TEST_KEYSPACE_NAME);
			ActuatorStateDao.createKeyspace(TEST_KEYSPACE_NAME, TEST_KEYSPACE_REPLICATION_STRATEGY, TEST_KEYSPACE_REPLICATION_FACTOR);
			ActuatorStateDao.useKeyspace(TEST_KEYSPACE_NAME);
			
			
			ActuatorStateDao.dropTable();
			ActuatorStateDao.isTableExists(TEST_KEYSPACE_NAME);
			
			
			ActuatorStateDao.createTable();

			ActuatorStateDao.insertActuatorState( createActuatorStateSingle());
			ActuatorStateDao.insertActuatorStateBatch( createActuatorStateBatch());
			
			String pk = "lo2|lo2_so2|lo2_so2_se2|";
			ActuatorState actuatorState = ActuatorStateDao.findActuatorState(pk);
			logger.info(actuatorState.toString());
			Assert.assertEquals("actuatorState login", "lo2", actuatorState.getLoginName());
			Assert.assertEquals("actuatorState source", "lo2_so2", actuatorState.getSourceName());
			Assert.assertEquals("actuatorState sensor", "lo2_so2_se2", actuatorState.getSensorName());
			Assert.assertEquals("actuatorState name", "ledGreen", actuatorState.getActuatorName());
			Assert.assertEquals("actuatorState actuatorValue", "off", actuatorState.getActuatorValue());
			Assert.assertEquals("actuatorState desc", "Green LED", actuatorState.getActuatorDesc());
			
			String value = ActuatorStateDao.findActuatorValue(pk);
			logger.info("value - before update {}", value );
			ActuatorStateDao.updateActuatorValue(pk,"on");
			value = ActuatorStateDao.findActuatorValue(pk);
			logger.info("value - after update {}", value );
			Assert.assertEquals("value after update", "on", value);
			
			ActuatorStateDao.deleteRow(pk);
			actuatorState = ActuatorStateDao.findActuatorState(pk);
			logger.info("actuatorState after delete: {}",actuatorState);
			Assert.assertNull("actuatorState after delete", actuatorState);
			
			long cntBefore = ActuatorStateDao.count();
			logger.info("cntBefore {}", cntBefore);
			Assert.assertEquals("cnt before truncate", 3, cntBefore );
			ActuatorStateDao.truncate();
			long cntAfter = ActuatorStateDao.count();
			logger.info("cntAfter {}", cntAfter);
			Assert.assertEquals("cnt after truncate", 0, cntAfter );
			
		} catch( Exception e ) {
			logger.error(e.getLocalizedMessage());			
		} finally {
			ActuatorStateDao.disconnect();
		}
	}
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * Creates the actuator state single.
	 *
	 * @return the actuator state
	 */
	private static ActuatorState createActuatorStateSingle() {
		return new ActuatorState().setLoginName("lo1").setSourceName("lo1_so1").setSensorName("lo1_so1_se1")
				.setActuatorName("fan1").setActuatorValue("off").setActuatorDesc("fan in greenhouse");
	}
	
	/**
	 * Creates the actuator state batch.
	 *
	 * @return the list
	 */
	private static List<ActuatorState> createActuatorStateBatch() {
		List<ActuatorState> actuatorStates = new ArrayList<ActuatorState>();
		actuatorStates.add( new ActuatorState().setLoginName("lo2").setSourceName("lo2_so2").setSensorName("lo2_so2_se2")
				.setActuatorName("ledGreen").setActuatorValue("off").setActuatorDesc("Green LED") );
		actuatorStates.add( new ActuatorState().setLoginName("lo3").setSourceName("lo3_so3").setSensorName("lo3_so3_se3")
				.setActuatorName("ledYellow").setActuatorValue("off").setActuatorDesc("Yellow LED") );
		actuatorStates.add( new ActuatorState().setLoginName("lo4").setSourceName("lo4_so4").setSensorName("lo4_so4_se4")
				.setActuatorName("ledRed").setActuatorValue("off").setActuatorDesc("Red LED") );
		return actuatorStates;
	}

}
