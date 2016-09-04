/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pzybrick.iote2e.ws.ignite;

import java.util.List;

import javax.cache.Cache;
import javax.cache.configuration.Factory;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryUpdatedListener;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.ContinuousQuery;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;

/**
 * This examples demonstrates continuous query API.
 * <p>
 * Remote nodes should always be started with special configuration file which
 * enables P2P class loading: {@code 'ignite.{sh|bat} examples/config/example-ignite.xml'}.
 * <p>
 * Alternatively you can run {@link ExampleNodeStartup} in another JVM which will
 * start node with {@code examples/config/example-ignite.xml} configuration.
 */
public class ClientContinuousQuery {
    /** Cache name. */
    private static final String CACHE_NAME = ClientContinuousQuery.class.getSimpleName();

    /**
     * Executes example.
     *
     * @param args Command line arguments, none required.
     * @throws Exception If example execution failed.
     */
    public static void main(String[] args) throws Exception {
//    	try {
//    		//Object obj = Ignition.loadSpringBean("config/example-ignite.xml", "cfgpz");
//    		IgniteConfiguration igniteConfiguration = Ignition.loadSpringBean("config/example-ignite.xml", "cfgpz");
//    		List<Ignite> grids = Ignition.allGrids();
//    		System.out.println(grids);
//    		if( System.currentTimeMillis()>0 ) System.exit(0);
//    		
//    	} catch( Exception e) {
//    		System.out.println(e);
//    		e.printStackTrace();
//    		System.exit(0);
//    	}
//    	
    	Ignite ignite = null;
        try {
        	IgniteConfiguration igniteConfiguration = Ignition.loadSpringBean("config/example-ignite.xml", "ignite.cfg");
        	ignite = Ignition.getOrStart(igniteConfiguration);
        	System.out.println(ignite.toString());
            System.out.println();
            System.out.println(">>> Cache continuous query example started.");

            // Auto-close cache at the end of the example.
            try (IgniteCache<Integer, String> cache = ignite.getOrCreateCache(CACHE_NAME)) {
                int keyCnt = 20;

                // These entries will be queried by initial predicate.
                for (int i = 0; i < keyCnt; i++)
                    cache.put(i, Integer.toString(i));

                // Create new continuous query.
                ContinuousQuery<Integer, String> qry = new ContinuousQuery<>();

                qry.setInitialQuery(new ScanQuery<>(new IgniteBiPredicate<Integer, String>() {
                    @Override public boolean apply(Integer key, String val) {
                        return key > 10;
                    }
                }));

                // Callback that is called locally when update notifications are received.
                qry.setLocalListener(new CacheEntryUpdatedListener<Integer, String>() {
                    @Override public void onUpdated(Iterable<CacheEntryEvent<? extends Integer, ? extends String>> evts) {
                        for (CacheEntryEvent<? extends Integer, ? extends String> e : evts)
                            System.out.println("Updated entry [key=" + e.getKey() + ", val=" + e.getValue() + ']');
                    }
                });

                // This filter will be evaluated remotely on all nodes.
                // Entry that pass this filter will be sent to the caller.
                qry.setRemoteFilterFactory(new Factory<CacheEntryEventFilter<Integer, String>>() {
                    @Override public CacheEntryEventFilter<Integer, String> create() {
                        return new CacheEntryEventFilter<Integer, String>() {
                            @Override public boolean evaluate(CacheEntryEvent<? extends Integer, ? extends String> e) {
                                return e.getKey() > 10;
                            }
                        };
                    }
                });

                // Execute query.
                try (QueryCursor<Cache.Entry<Integer, String>> cur = cache.query(qry)) {
                    // Iterate through existing data.
                    for (Cache.Entry<Integer, String> e : cur)
                        System.out.println("Queried existing entry [key=" + e.getKey() + ", val=" + e.getValue() + ']');

                    // Add a few more keys and watch more query notifications.
                    for (int i = keyCnt; i < keyCnt + 10; i++)
                        cache.put(i, Integer.toString(i));

                    // Wait for a while while callback is notified about remaining puts.
                    Thread.sleep(2000);
                }
            }
            finally {
                // Distributed cache could be removed from cluster only by #destroyCache() call.
                ignite.destroyCache(CACHE_NAME);
            }
        } catch( Exception e ) {
        	throw e;
        } finally {
        	System.out.println("in finally");
        	System.out.println(ignite.cluster());
        	ignite.close();
        }
    }
}