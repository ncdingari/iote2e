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
package com.pzybrick.iote2e.stream.request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.pzybrick.iote2e.common.config.MasterConfig;
import com.pzybrick.iote2e.schema.avro.Iote2eRequest;
import com.pzybrick.iote2e.stream.persist.PooledDataSource;


/**
 * The Class Iote2eRequestRouterHandlerSparkDbImpl.
 */
public class Iote2eRequestRouterHandlerSparkDbImpl implements Iote2eRequestRouterHandler {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(Iote2eRequestRouterHandlerSparkDbImpl.class);
	
	/** The master config. */
	private MasterConfig masterConfig;


	/**
	 * Instantiates a new iote 2 e request router handler spark db impl.
	 *
	 * @throws Exception the exception
	 */
	public Iote2eRequestRouterHandlerSparkDbImpl( ) throws Exception {

	}
	
	
	/* (non-Javadoc)
	 * @see com.pzybrick.iote2e.stream.request.Iote2eRequestRouterHandler#init(com.pzybrick.iote2e.common.config.MasterConfig)
	 */
	public void init(MasterConfig masterConfig) throws Exception {
		try {
			this.masterConfig = masterConfig;
		} catch( Exception e ) {
			logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.pzybrick.iote2e.stream.request.Iote2eRequestRouterHandler#processRequests(java.util.List)
	 */
	public void processRequests( List<Iote2eRequest> iote2eRequests ) throws Exception {
		Connection con = null;
		Map<String,PreparedStatement> cachePrepStmtsByTableName = new HashMap<String,PreparedStatement>();
		try {
			if( iote2eRequests != null && iote2eRequests.size() > 0 ) {
				con = PooledDataSource.getInstance(masterConfig).getConnection();
				insertAllBlocks( iote2eRequests, con, cachePrepStmtsByTableName );
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			try {
				if( con != null ) 
					con.rollback();
			} catch( Exception exRoll ) {
				logger.warn(exRoll.getMessage());
			}
		} finally {
			for( PreparedStatement pstmt : cachePrepStmtsByTableName.values() ) {
				try {
					pstmt.close();
				} catch( Exception e ) {
					logger.warn(e);
				}
			}
			if( con != null ) {
				try {
					con.close();
				} catch(Exception exCon ) {
					logger.warn(exCon.getMessage());
				}
			}
		}
	}
	
	
	/**
	 * Insert all blocks.
	 *
	 * @param iote2eRequests the iote 2 e requests
	 * @param con the con
	 * @param cachePrepStmtsByTableName the cache prep stmts by table name
	 * @throws Exception the exception
	 */
	private void insertAllBlocks( List<Iote2eRequest> iote2eRequests, Connection con, Map<String,PreparedStatement> cachePrepStmtsByTableName ) throws Exception {
		Integer insertBlockSize = masterConfig.getJdbcInsertBlockSize();
		for( int i=0 ;; i+=insertBlockSize ) {
			int startNextBlock = i + insertBlockSize;
			if( startNextBlock > iote2eRequests.size() ) 
				startNextBlock = iote2eRequests.size();
			try {
				insertEachBlock(iote2eRequests.subList(i, startNextBlock), con, cachePrepStmtsByTableName);
			} catch( Exception e ) {
				// At least one failure, reprocess one by one
				for( Iote2eRequest iote2eRequest : iote2eRequests.subList(i, startNextBlock) ) {
					insertEachBlock( Arrays.asList(iote2eRequest), con, cachePrepStmtsByTableName);
				}
				
			}
			if( startNextBlock == iote2eRequests.size() ) break;
		}
	}
	
	
	/**
	 * Insert each block.
	 *
	 * @param iote2eRequests the iote 2 e requests
	 * @param con the con
	 * @param cachePrepStmtsByTableName the cache prep stmts by table name
	 * @throws Exception the exception
	 */
	private void insertEachBlock( List<Iote2eRequest> iote2eRequests, Connection con, Map<String,PreparedStatement> cachePrepStmtsByTableName ) throws Exception {
		final DateTimeFormatter dtfmt = ISODateTimeFormat.dateTime();
		String tableName = null;
		String request_uuid = null;
		PreparedStatement pstmt = null;
		try {
			int cntToCommit = 0;
			for( Iote2eRequest iote2eRequest : iote2eRequests) {
				tableName = iote2eRequest.getSourceType().toString();
				pstmt = getPreparedStatement( tableName, con, cachePrepStmtsByTableName );
				if( pstmt != null ) {
					cntToCommit++;
					request_uuid = iote2eRequest.getRequestUuid().toString();
					int offset = 1;
					// First set of values are the same on every table
					pstmt.setString(offset++, request_uuid );
					pstmt.setString(offset++, iote2eRequest.getLoginName().toString());
					pstmt.setString(offset++, iote2eRequest.getSourceName().toString());
					Timestamp timestamp = new Timestamp(dtfmt.parseDateTime(iote2eRequest.getRequestTimestamp().toString()).getMillis());
					pstmt.setTimestamp(offset++, timestamp);
					// Next value(s)/types are specific to the table
					// For this simple example, assume one value passed as string
					String value = iote2eRequest.getPairs().values().iterator().next().toString();
					if( "temperature".compareToIgnoreCase(tableName) == 0) {
						// temp_f
						pstmt.setFloat(offset++, new Float(value));
					}else if( "humidity".compareToIgnoreCase(tableName) == 0) {
						// pct_humidity
						pstmt.setFloat(offset++, new Float(value));
					}else if( "switch".compareToIgnoreCase(tableName) == 0) {
						// switch_state
						pstmt.setInt(offset++, Integer.parseInt(value));
					}else if( "heartbeat".compareToIgnoreCase(tableName) == 0) {
						// heartbeat_state
						pstmt.setInt(offset++, Integer.parseInt(value));
					}
					pstmt.execute();
				}
			}
			if( cntToCommit > 0 ) con.commit();
		} catch( SQLException sqlEx ) {
			con.rollback();
			// Suppress duplicate rows, assume the are the same and were sent over Kafka > 1 time
			if( iote2eRequests.size() == 1 ) {
				if( sqlEx.getSQLState() != null && sqlEx.getSQLState().startsWith("23") )
					logger.debug("Skipping duplicate row, table={}, request_uuid={}", tableName, request_uuid);
				else {
					logger.error("Error on insert for pstmt: {}", pstmt.toString());
					throw sqlEx;
				}
			} else {
				throw sqlEx;
			}
		} catch( Exception e2 ) {
			con.rollback();
			throw e2;
		}
	}
	
	
	/**
	 * Gets the prepared statement.
	 *
	 * @param tableName the table name
	 * @param con the con
	 * @param cachePrepStmtsByTableName the cache prep stmts by table name
	 * @return the prepared statement
	 * @throws Exception the exception
	 */
	/*
	 * A bit of a hack
	 */
	private PreparedStatement getPreparedStatement( String tableName, Connection con, Map<String,PreparedStatement> cachePrepStmtsByTableName ) throws Exception {
		if( cachePrepStmtsByTableName.containsKey(tableName) ) return cachePrepStmtsByTableName.get(tableName);
		final String sqlTemperature = "INSERT INTO temperature (request_uuid,login_name,source_name,request_timestamp,degrees_c) VALUES (?,?,?,?,?)";
		final String sqlHumidity = "INSERT INTO humidity (request_uuid,login_name,source_name,request_timestamp,pct_humidity) VALUES (?,?,?,?,?)";
		final String sqlSwitch = "INSERT INTO switch (request_uuid,login_name,source_name,request_timestamp,switch_state) VALUES (?,?,?,?,?)";
		final String sqlHeartbeat = "INSERT INTO heartbeat (request_uuid,login_name,source_name,request_timestamp,heartbeat_state) VALUES (?,?,?,?,?)";
		
		String sql = null;
		if( "temperature".compareToIgnoreCase(tableName) == 0) sql = sqlTemperature;
		else if( "humidity".compareToIgnoreCase(tableName) == 0) sql = sqlHumidity;
		else if( "switch".compareToIgnoreCase(tableName) == 0) sql = sqlSwitch;
		else if( "heartbeat".compareToIgnoreCase(tableName) == 0) sql = sqlHeartbeat;
		else if( "pill_dispenser".compareToIgnoreCase(tableName) == 0) sql = null;
		else throw new Exception("Invalid table name: " + tableName);

		PreparedStatement pstmt = null;
		if( sql != null ) { 
			logger.debug("Creating pstmt for {}",  tableName);
			pstmt = con.prepareStatement(sql);
			cachePrepStmtsByTableName.put(tableName, pstmt );
		} else logger.debug("NOT Creating pstmt for {}", tableName);
		
		return pstmt;
	}

}
		
