package com.pzybrick.iote2e.ruleproc.svc;

public class RuleEvalResult {
	private boolean ruleActuatorHit;
	private String actuatorTargetValue;
	private SourceSensorActuator sourceSensorActuator;

	public RuleEvalResult() {
		this.ruleActuatorHit = false;
		this.sourceSensorActuator = null;
		this.actuatorTargetValue = null;
	}

	public RuleEvalResult(boolean ruleActuatorHit, SourceSensorActuator sourceSensorActuator) {
		this.ruleActuatorHit = ruleActuatorHit;
		this.sourceSensorActuator = sourceSensorActuator;
		this.actuatorTargetValue = null;
	}

	public boolean isRuleActuatorHit() {
		return ruleActuatorHit;
	}

	public SourceSensorActuator getSourceSensorActuator() {
		return sourceSensorActuator;
	}

	public RuleEvalResult setRuleActuatorHit(boolean ruleActuatorHit) {
		this.ruleActuatorHit = ruleActuatorHit;
		return this;
	}

	public RuleEvalResult setSourceSensorActuator(SourceSensorActuator sourceSensorActuator) {
		this.sourceSensorActuator = sourceSensorActuator;
		return this;
	}

	@Override
	public String toString() {
		return "RuleEvalResult [ruleActuatorHit=" + ruleActuatorHit + ", actuatorTargetValue=" + actuatorTargetValue
				+ ", sourceSensorActuator=" + sourceSensorActuator + "]";
	}

	public String getActuatorTargetValue() {
		return actuatorTargetValue;
	}

	public RuleEvalResult setActuatorTargetValue(String actuatorTargetValue) {
		this.actuatorTargetValue = actuatorTargetValue;
		return this;
	}

}