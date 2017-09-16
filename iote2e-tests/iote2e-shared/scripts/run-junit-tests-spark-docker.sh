#!/bin/bash
export MASTER_CONFIG_JSON_KEY="master_spark_unit_test_docker_config"
./run-junit-tests-common.sh TestCommonDocker.properties \
../jars/iote2e-tests-1.0.0.jar \
"com.pzybrick.iote2e.tests.spark.TestSparkHandlerTempToFan"

./run-junit-tests-common.sh TestCommonDocker.properties \
../jars/iote2e-tests-1.0.0.jar \
"com.pzybrick.iote2e.tests.spark.TestSparkHandlerHumidityToMister"

./run-junit-tests-common.sh TestCommonDocker.properties \
../jars/iote2e-tests-1.0.0.jar \
"com.pzybrick.iote2e.tests.spark.TestSparkHandlerLed"
