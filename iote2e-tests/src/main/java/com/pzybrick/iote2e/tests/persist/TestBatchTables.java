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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pzybrick.iote2e.common.config.MasterConfig;
import com.pzybrick.iote2e.common.utils.Iote2eUtils;
import com.pzybrick.iote2e.stream.persist.PooledDataSource;
import com.pzybrick.iote2e.stream.request.Iote2eRequestRouterHandlerSparkDbImpl;
import com.pzybrick.iote2e.schema.avro.Iote2eRequest;
import com.pzybrick.iote2e.schema.avro.OPERATION;


/**
 * The Class TestBatchTables.
 */
public class TestBatchTables {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(TestBatchTables.class);
	
	/** The Constant TEST_LOGIN_NAME. */
	private static final String TEST_LOGIN_NAME = "pzybricktest@gmail.com";
	
	/** The master config. */
	private static MasterConfig masterConfig;
	
	/** The iote 2 e request router handler spark db impl. */
	private Iote2eRequestRouterHandlerSparkDbImpl iote2eRequestRouterHandlerSparkDbImpl;

	/**
	 * Before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void beforeClass() throws Exception {
		TestBatchTables.masterConfig = MasterConfig.getInstance(System.getenv("MASTER_CONFIG_JSON_KEY"), System.getenv("CASSANDRA_CONTACT_POINT"), System.getenv("CASSANDRA_KEYSPACE_NAME") );
	}

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		iote2eRequestRouterHandlerSparkDbImpl = new Iote2eRequestRouterHandlerSparkDbImpl();
		iote2eRequestRouterHandlerSparkDbImpl.init(masterConfig);
		truncateTables();
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
		truncateTables();
	}

	/**
	 * Test single temp.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSingleTemp() throws Exception {
		List<Iote2eRequest> iote2eRequests = new ArrayList<Iote2eRequest>();
		iote2eRequests.add( createIote2eRequest( "rpi999", "temperature", "temp1", "12.3") );
		iote2eRequestRouterHandlerSparkDbImpl.processRequests(iote2eRequests);
		Map<String,Integer> countsByTable = tableCounts();
		Assert.assertEquals("temperature count", new Integer(1), countsByTable.get("temperature"));
		Assert.assertEquals("humidity count", new Integer(0), countsByTable.get("humidity"));
		Assert.assertEquals("switch count", new Integer(0), countsByTable.get("switch"));
		Assert.assertEquals("heartbeat count", new Integer(0), countsByTable.get("heartbeat"));
	}
	
	/**
	 * Test one of each.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testOneOfEach() throws Exception {
		long before = System.currentTimeMillis();
		List<Iote2eRequest> iote2eRequests = new ArrayList<Iote2eRequest>();
		iote2eRequests.add( createIote2eRequest( "rpi999", "temperature", "temp1", "12.3") );
		iote2eRequests.add( createIote2eRequest( "rpi999", "humidity", "humidity1", "45.67") );
		iote2eRequests.add( createIote2eRequest( "rpi999", "switch", "switch1", "1") );
		iote2eRequests.add( createIote2eRequest( "rpi999", "heartbeat", "heartbeat1", "1") );
		iote2eRequestRouterHandlerSparkDbImpl.processRequests(iote2eRequests);
		logger.info("Elapsed {} ",  (System.currentTimeMillis()-before));
		Map<String,Integer> countsByTable = tableCounts();
		Assert.assertEquals("temperature count", new Integer(1), countsByTable.get("temperature"));
		Assert.assertEquals("humidity count", new Integer(1), countsByTable.get("humidity"));
		Assert.assertEquals("switch count", new Integer(1), countsByTable.get("switch"));
		Assert.assertEquals("heartbeat count", new Integer(1), countsByTable.get("heartbeat"));
	}
	
	/**
	 * Test volume.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testVolume() throws Exception {
		final Integer numTestRequests = 2567;
		List<Iote2eRequest> iote2eRequests = new ArrayList<Iote2eRequest>();
		double temperature = .01;
		double humdity = .02;
		for( int i=0 ; i<numTestRequests ; i++ ) {
			iote2eRequests.add( createIote2eRequest( "rpi999", "temperature", "temp1", String.valueOf(temperature)) );
			iote2eRequests.add( createIote2eRequest( "rpi999", "humidity", "humidity1",  String.valueOf(humdity)) );
			iote2eRequests.add( createIote2eRequest( "rpi999", "switch", "switch1", String.valueOf(i%2)) );
			iote2eRequests.add( createIote2eRequest( "rpi999", "heartbeat", "heartbeat1", String.valueOf(i%2)) );
			temperature += .01;
			humdity += .01;
		}
		long before = System.currentTimeMillis();
		iote2eRequestRouterHandlerSparkDbImpl.processRequests(iote2eRequests);
		logger.info("Elapsed {} ",  (System.currentTimeMillis()-before));
		Map<String,Integer> countsByTable = tableCounts();
		Assert.assertEquals("temperature count", numTestRequests, countsByTable.get("temperature"));
		Assert.assertEquals("humidity count", numTestRequests, countsByTable.get("humidity"));
		Assert.assertEquals("switch count", numTestRequests, countsByTable.get("switch"));
		Assert.assertEquals("heartbeat count", numTestRequests, countsByTable.get("heartbeat"));

	}
	
	/**
	 * Creates the iote 2 e request.
	 *
	 * @param sourceName the source name
	 * @param sourceType the source type
	 * @param sensorName the sensor name
	 * @param sensorValue the sensor value
	 * @return the iote 2 e request
	 */
	private Iote2eRequest createIote2eRequest( String sourceName, String sourceType, String sensorName, String sensorValue ) {
		Map<CharSequence, CharSequence> pairs = new HashMap<CharSequence, CharSequence>();
		pairs.put(sensorName, sensorValue);
		Iote2eRequest iote2eRequest = Iote2eRequest.newBuilder().setLoginName(TEST_LOGIN_NAME).setSourceName(sourceName)
				.setSourceType(sourceType).setRequestUuid(UUID.randomUUID().toString())
				.setRequestTimestamp(Iote2eUtils.getDateNowUtc8601()).setOperation(OPERATION.SENSORS_VALUES)
				.setPairs(pairs).build();
		return iote2eRequest;
	}
	
	/**
	 * Truncate tables.
	 *
	 * @throws Exception the exception
	 */
	private void truncateTables() throws Exception {
		Connection conn = null;
		try {
			conn = PooledDataSource.getInstance(masterConfig).getConnection();
			String[] tables = {"heartbeat","humidity","switch","temperature"};
			for( String table : tables ) {
				Statement stmnt = conn.createStatement();
				stmnt.execute("TRUNCATE " + table );
			}
			conn.commit();
		} catch( Exception e ) {
			conn.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch( Exception e ) {
				logger.warn(e.getMessage());
			}
		}
	}
		
	/**
	 * Table counts.
	 *
	 * @return the map
	 * @throws Exception the exception
	 */
	private Map<String,Integer> tableCounts() throws Exception {
		Map<String,Integer> countsByTable = new HashMap<String,Integer>();
		Connection conn = null;
		try {
			conn = PooledDataSource.getInstance(masterConfig).getConnection();
			String[] tables = {"heartbeat","humidity","switch","temperature"};
			for( String table : tables ) {
				Statement stmnt = conn.createStatement();
				ResultSet rs = stmnt.executeQuery("SELECT COUNT(*) FROM " + table );
				rs.next();
				countsByTable.put(table, rs.getInt(1));
			}
			//conn.commit();
		} catch( Exception e ) {
			conn.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch( Exception e ) {
				logger.warn(e.getMessage());
			}
		}
		return countsByTable;
	}

}
