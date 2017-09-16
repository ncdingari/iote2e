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
package com.pzybrick.iote2e.schema.avro;

import org.apache.avro.specific.SpecificData;


/**
 * The Class Iote2eResult.
 */
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Iote2eResult extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 5133708781356754708L;
  
  /** The Constant SCHEMA$. */
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Iote2eResult\",\"namespace\":\"com.pzybrick.iote2e.schema.avro\",\"fields\":[{\"name\":\"login_name\",\"type\":\"string\"},{\"name\":\"source_name\",\"type\":\"string\"},{\"name\":\"source_type\",\"type\":\"string\"},{\"name\":\"metadata\",\"type\":{\"type\":\"map\",\"values\":\"string\"},\"default\":\"null\"},{\"name\":\"request_uuid\",\"type\":\"string\"},{\"name\":\"request_timestamp\",\"type\":\"string\"},{\"name\":\"result_code\",\"type\":\"int\",\"default\":\"0\"},{\"name\":\"result_error_message\",\"type\":\"string\",\"default\":\"null\"},{\"name\":\"result_uuid\",\"type\":\"string\"},{\"name\":\"result_timestamp\",\"type\":\"string\"},{\"name\":\"pairs\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"operation\",\"type\":{\"type\":\"enum\",\"name\":\"OPERATION\",\"symbols\":[\"SENSORS_VALUES\",\"ACTUATOR_VALUES\",\"ACTUATOR_CONFIRM\",\"LOOPBACK\"]}}]}");
  
  /**
   * Gets the class schema.
   *
   * @return the class schema
   */
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  
  /** The login name. */
  @Deprecated public java.lang.CharSequence login_name;
  
  /** The source name. */
  @Deprecated public java.lang.CharSequence source_name;
  
  /** The source type. */
  @Deprecated public java.lang.CharSequence source_type;
  
  /** The metadata. */
  @Deprecated public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> metadata;
  
  /** The request uuid. */
  @Deprecated public java.lang.CharSequence request_uuid;
  
  /** The request timestamp. */
  @Deprecated public java.lang.CharSequence request_timestamp;
  
  /** The result code. */
  @Deprecated public int result_code;
  
  /** The result error message. */
  @Deprecated public java.lang.CharSequence result_error_message;
  
  /** The result uuid. */
  @Deprecated public java.lang.CharSequence result_uuid;
  
  /** The result timestamp. */
  @Deprecated public java.lang.CharSequence result_timestamp;
  
  /** The pairs. */
  @Deprecated public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> pairs;
  
  /** The operation. */
  @Deprecated public com.pzybrick.iote2e.schema.avro.OPERATION operation;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Iote2eResult() {}

  /**
   * All-args constructor.
   * @param login_name The new value for login_name
   * @param source_name The new value for source_name
   * @param source_type The new value for source_type
   * @param metadata The new value for metadata
   * @param request_uuid The new value for request_uuid
   * @param request_timestamp The new value for request_timestamp
   * @param result_code The new value for result_code
   * @param result_error_message The new value for result_error_message
   * @param result_uuid The new value for result_uuid
   * @param result_timestamp The new value for result_timestamp
   * @param pairs The new value for pairs
   * @param operation The new value for operation
   */
  public Iote2eResult(java.lang.CharSequence login_name, java.lang.CharSequence source_name, java.lang.CharSequence source_type, java.util.Map<java.lang.CharSequence,java.lang.CharSequence> metadata, java.lang.CharSequence request_uuid, java.lang.CharSequence request_timestamp, java.lang.Integer result_code, java.lang.CharSequence result_error_message, java.lang.CharSequence result_uuid, java.lang.CharSequence result_timestamp, java.util.Map<java.lang.CharSequence,java.lang.CharSequence> pairs, com.pzybrick.iote2e.schema.avro.OPERATION operation) {
    this.login_name = login_name;
    this.source_name = source_name;
    this.source_type = source_type;
    this.metadata = metadata;
    this.request_uuid = request_uuid;
    this.request_timestamp = request_timestamp;
    this.result_code = result_code;
    this.result_error_message = result_error_message;
    this.result_uuid = result_uuid;
    this.result_timestamp = result_timestamp;
    this.pairs = pairs;
    this.operation = operation;
  }

  /* (non-Javadoc)
   * @see org.apache.avro.specific.SpecificRecordBase#getSchema()
   */
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  
  /* (non-Javadoc)
   * @see org.apache.avro.specific.SpecificRecordBase#get(int)
   */
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return login_name;
    case 1: return source_name;
    case 2: return source_type;
    case 3: return metadata;
    case 4: return request_uuid;
    case 5: return request_timestamp;
    case 6: return result_code;
    case 7: return result_error_message;
    case 8: return result_uuid;
    case 9: return result_timestamp;
    case 10: return pairs;
    case 11: return operation;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /* (non-Javadoc)
   * @see org.apache.avro.specific.SpecificRecordBase#put(int, java.lang.Object)
   */
  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: login_name = (java.lang.CharSequence)value$; break;
    case 1: source_name = (java.lang.CharSequence)value$; break;
    case 2: source_type = (java.lang.CharSequence)value$; break;
    case 3: metadata = (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>)value$; break;
    case 4: request_uuid = (java.lang.CharSequence)value$; break;
    case 5: request_timestamp = (java.lang.CharSequence)value$; break;
    case 6: result_code = (java.lang.Integer)value$; break;
    case 7: result_error_message = (java.lang.CharSequence)value$; break;
    case 8: result_uuid = (java.lang.CharSequence)value$; break;
    case 9: result_timestamp = (java.lang.CharSequence)value$; break;
    case 10: pairs = (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>)value$; break;
    case 11: operation = (com.pzybrick.iote2e.schema.avro.OPERATION)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'login_name' field.
   * @return The value of the 'login_name' field.
   */
  public java.lang.CharSequence getLoginName() {
    return login_name;
  }

  /**
   * Sets the value of the 'login_name' field.
   * @param value the value to set.
   */
  public void setLoginName(java.lang.CharSequence value) {
    this.login_name = value;
  }

  /**
   * Gets the value of the 'source_name' field.
   * @return The value of the 'source_name' field.
   */
  public java.lang.CharSequence getSourceName() {
    return source_name;
  }

  /**
   * Sets the value of the 'source_name' field.
   * @param value the value to set.
   */
  public void setSourceName(java.lang.CharSequence value) {
    this.source_name = value;
  }

  /**
   * Gets the value of the 'source_type' field.
   * @return The value of the 'source_type' field.
   */
  public java.lang.CharSequence getSourceType() {
    return source_type;
  }

  /**
   * Sets the value of the 'source_type' field.
   * @param value the value to set.
   */
  public void setSourceType(java.lang.CharSequence value) {
    this.source_type = value;
  }

  /**
   * Gets the value of the 'metadata' field.
   * @return The value of the 'metadata' field.
   */
  public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> getMetadata() {
    return metadata;
  }

  /**
   * Sets the value of the 'metadata' field.
   * @param value the value to set.
   */
  public void setMetadata(java.util.Map<java.lang.CharSequence,java.lang.CharSequence> value) {
    this.metadata = value;
  }

  /**
   * Gets the value of the 'request_uuid' field.
   * @return The value of the 'request_uuid' field.
   */
  public java.lang.CharSequence getRequestUuid() {
    return request_uuid;
  }

  /**
   * Sets the value of the 'request_uuid' field.
   * @param value the value to set.
   */
  public void setRequestUuid(java.lang.CharSequence value) {
    this.request_uuid = value;
  }

  /**
   * Gets the value of the 'request_timestamp' field.
   * @return The value of the 'request_timestamp' field.
   */
  public java.lang.CharSequence getRequestTimestamp() {
    return request_timestamp;
  }

  /**
   * Sets the value of the 'request_timestamp' field.
   * @param value the value to set.
   */
  public void setRequestTimestamp(java.lang.CharSequence value) {
    this.request_timestamp = value;
  }

  /**
   * Gets the value of the 'result_code' field.
   * @return The value of the 'result_code' field.
   */
  public java.lang.Integer getResultCode() {
    return result_code;
  }

  /**
   * Sets the value of the 'result_code' field.
   * @param value the value to set.
   */
  public void setResultCode(java.lang.Integer value) {
    this.result_code = value;
  }

  /**
   * Gets the value of the 'result_error_message' field.
   * @return The value of the 'result_error_message' field.
   */
  public java.lang.CharSequence getResultErrorMessage() {
    return result_error_message;
  }

  /**
   * Sets the value of the 'result_error_message' field.
   * @param value the value to set.
   */
  public void setResultErrorMessage(java.lang.CharSequence value) {
    this.result_error_message = value;
  }

  /**
   * Gets the value of the 'result_uuid' field.
   * @return The value of the 'result_uuid' field.
   */
  public java.lang.CharSequence getResultUuid() {
    return result_uuid;
  }

  /**
   * Sets the value of the 'result_uuid' field.
   * @param value the value to set.
   */
  public void setResultUuid(java.lang.CharSequence value) {
    this.result_uuid = value;
  }

  /**
   * Gets the value of the 'result_timestamp' field.
   * @return The value of the 'result_timestamp' field.
   */
  public java.lang.CharSequence getResultTimestamp() {
    return result_timestamp;
  }

  /**
   * Sets the value of the 'result_timestamp' field.
   * @param value the value to set.
   */
  public void setResultTimestamp(java.lang.CharSequence value) {
    this.result_timestamp = value;
  }

  /**
   * Gets the value of the 'pairs' field.
   * @return The value of the 'pairs' field.
   */
  public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> getPairs() {
    return pairs;
  }

  /**
   * Sets the value of the 'pairs' field.
   * @param value the value to set.
   */
  public void setPairs(java.util.Map<java.lang.CharSequence,java.lang.CharSequence> value) {
    this.pairs = value;
  }

  /**
   * Gets the value of the 'operation' field.
   * @return The value of the 'operation' field.
   */
  public com.pzybrick.iote2e.schema.avro.OPERATION getOperation() {
    return operation;
  }

  /**
   * Sets the value of the 'operation' field.
   * @param value the value to set.
   */
  public void setOperation(com.pzybrick.iote2e.schema.avro.OPERATION value) {
    this.operation = value;
  }

  /**
   * Creates a new Iote2eResult RecordBuilder.
   * @return A new Iote2eResult RecordBuilder
   */
  public static com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder newBuilder() {
    return new com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder();
  }

  /**
   * Creates a new Iote2eResult RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Iote2eResult RecordBuilder
   */
  public static com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder newBuilder(com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder other) {
    return new com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder(other);
  }

  /**
   * Creates a new Iote2eResult RecordBuilder by copying an existing Iote2eResult instance.
   * @param other The existing instance to copy.
   * @return A new Iote2eResult RecordBuilder
   */
  public static com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder newBuilder(com.pzybrick.iote2e.schema.avro.Iote2eResult other) {
    return new com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder(other);
  }

  /**
   * RecordBuilder for Iote2eResult instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Iote2eResult>
    implements org.apache.avro.data.RecordBuilder<Iote2eResult> {

    /** The login name. */
    private java.lang.CharSequence login_name;
    
    /** The source name. */
    private java.lang.CharSequence source_name;
    
    /** The source type. */
    private java.lang.CharSequence source_type;
    
    /** The metadata. */
    private java.util.Map<java.lang.CharSequence,java.lang.CharSequence> metadata;
    
    /** The request uuid. */
    private java.lang.CharSequence request_uuid;
    
    /** The request timestamp. */
    private java.lang.CharSequence request_timestamp;
    
    /** The result code. */
    private int result_code;
    
    /** The result error message. */
    private java.lang.CharSequence result_error_message;
    
    /** The result uuid. */
    private java.lang.CharSequence result_uuid;
    
    /** The result timestamp. */
    private java.lang.CharSequence result_timestamp;
    
    /** The pairs. */
    private java.util.Map<java.lang.CharSequence,java.lang.CharSequence> pairs;
    
    /** The operation. */
    private com.pzybrick.iote2e.schema.avro.OPERATION operation;

    /**
     *  Creates a new Builder.
     */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.login_name)) {
        this.login_name = data().deepCopy(fields()[0].schema(), other.login_name);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.source_name)) {
        this.source_name = data().deepCopy(fields()[1].schema(), other.source_name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.source_type)) {
        this.source_type = data().deepCopy(fields()[2].schema(), other.source_type);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.metadata)) {
        this.metadata = data().deepCopy(fields()[3].schema(), other.metadata);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.request_uuid)) {
        this.request_uuid = data().deepCopy(fields()[4].schema(), other.request_uuid);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.request_timestamp)) {
        this.request_timestamp = data().deepCopy(fields()[5].schema(), other.request_timestamp);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.result_code)) {
        this.result_code = data().deepCopy(fields()[6].schema(), other.result_code);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.result_error_message)) {
        this.result_error_message = data().deepCopy(fields()[7].schema(), other.result_error_message);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.result_uuid)) {
        this.result_uuid = data().deepCopy(fields()[8].schema(), other.result_uuid);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.result_timestamp)) {
        this.result_timestamp = data().deepCopy(fields()[9].schema(), other.result_timestamp);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.pairs)) {
        this.pairs = data().deepCopy(fields()[10].schema(), other.pairs);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.operation)) {
        this.operation = data().deepCopy(fields()[11].schema(), other.operation);
        fieldSetFlags()[11] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Iote2eResult instance.
     *
     * @param other The existing instance to copy.
     */
    private Builder(com.pzybrick.iote2e.schema.avro.Iote2eResult other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.login_name)) {
        this.login_name = data().deepCopy(fields()[0].schema(), other.login_name);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.source_name)) {
        this.source_name = data().deepCopy(fields()[1].schema(), other.source_name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.source_type)) {
        this.source_type = data().deepCopy(fields()[2].schema(), other.source_type);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.metadata)) {
        this.metadata = data().deepCopy(fields()[3].schema(), other.metadata);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.request_uuid)) {
        this.request_uuid = data().deepCopy(fields()[4].schema(), other.request_uuid);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.request_timestamp)) {
        this.request_timestamp = data().deepCopy(fields()[5].schema(), other.request_timestamp);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.result_code)) {
        this.result_code = data().deepCopy(fields()[6].schema(), other.result_code);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.result_error_message)) {
        this.result_error_message = data().deepCopy(fields()[7].schema(), other.result_error_message);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.result_uuid)) {
        this.result_uuid = data().deepCopy(fields()[8].schema(), other.result_uuid);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.result_timestamp)) {
        this.result_timestamp = data().deepCopy(fields()[9].schema(), other.result_timestamp);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.pairs)) {
        this.pairs = data().deepCopy(fields()[10].schema(), other.pairs);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.operation)) {
        this.operation = data().deepCopy(fields()[11].schema(), other.operation);
        fieldSetFlags()[11] = true;
      }
    }

    /**
      * Gets the value of the 'login_name' field.
      * @return The value.
      */
    public java.lang.CharSequence getLoginName() {
      return login_name;
    }

    /**
      * Sets the value of the 'login_name' field.
      * @param value The value of 'login_name'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setLoginName(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.login_name = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'login_name' field has been set.
      * @return True if the 'login_name' field has been set, false otherwise.
      */
    public boolean hasLoginName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'login_name' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearLoginName() {
      login_name = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'source_name' field.
      * @return The value.
      */
    public java.lang.CharSequence getSourceName() {
      return source_name;
    }

    /**
      * Sets the value of the 'source_name' field.
      * @param value The value of 'source_name'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setSourceName(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.source_name = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'source_name' field has been set.
      * @return True if the 'source_name' field has been set, false otherwise.
      */
    public boolean hasSourceName() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'source_name' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearSourceName() {
      source_name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'source_type' field.
      * @return The value.
      */
    public java.lang.CharSequence getSourceType() {
      return source_type;
    }

    /**
      * Sets the value of the 'source_type' field.
      * @param value The value of 'source_type'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setSourceType(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.source_type = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'source_type' field has been set.
      * @return True if the 'source_type' field has been set, false otherwise.
      */
    public boolean hasSourceType() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'source_type' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearSourceType() {
      source_type = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'metadata' field.
      * @return The value.
      */
    public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> getMetadata() {
      return metadata;
    }

    /**
      * Sets the value of the 'metadata' field.
      * @param value The value of 'metadata'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setMetadata(java.util.Map<java.lang.CharSequence,java.lang.CharSequence> value) {
      validate(fields()[3], value);
      this.metadata = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'metadata' field has been set.
      * @return True if the 'metadata' field has been set, false otherwise.
      */
    public boolean hasMetadata() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'metadata' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearMetadata() {
      metadata = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'request_uuid' field.
      * @return The value.
      */
    public java.lang.CharSequence getRequestUuid() {
      return request_uuid;
    }

    /**
      * Sets the value of the 'request_uuid' field.
      * @param value The value of 'request_uuid'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setRequestUuid(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.request_uuid = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'request_uuid' field has been set.
      * @return True if the 'request_uuid' field has been set, false otherwise.
      */
    public boolean hasRequestUuid() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'request_uuid' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearRequestUuid() {
      request_uuid = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'request_timestamp' field.
      * @return The value.
      */
    public java.lang.CharSequence getRequestTimestamp() {
      return request_timestamp;
    }

    /**
      * Sets the value of the 'request_timestamp' field.
      * @param value The value of 'request_timestamp'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setRequestTimestamp(java.lang.CharSequence value) {
      validate(fields()[5], value);
      this.request_timestamp = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'request_timestamp' field has been set.
      * @return True if the 'request_timestamp' field has been set, false otherwise.
      */
    public boolean hasRequestTimestamp() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'request_timestamp' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearRequestTimestamp() {
      request_timestamp = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'result_code' field.
      * @return The value.
      */
    public java.lang.Integer getResultCode() {
      return result_code;
    }

    /**
      * Sets the value of the 'result_code' field.
      * @param value The value of 'result_code'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setResultCode(int value) {
      validate(fields()[6], value);
      this.result_code = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'result_code' field has been set.
      * @return True if the 'result_code' field has been set, false otherwise.
      */
    public boolean hasResultCode() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'result_code' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearResultCode() {
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'result_error_message' field.
      * @return The value.
      */
    public java.lang.CharSequence getResultErrorMessage() {
      return result_error_message;
    }

    /**
      * Sets the value of the 'result_error_message' field.
      * @param value The value of 'result_error_message'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setResultErrorMessage(java.lang.CharSequence value) {
      validate(fields()[7], value);
      this.result_error_message = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'result_error_message' field has been set.
      * @return True if the 'result_error_message' field has been set, false otherwise.
      */
    public boolean hasResultErrorMessage() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'result_error_message' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearResultErrorMessage() {
      result_error_message = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'result_uuid' field.
      * @return The value.
      */
    public java.lang.CharSequence getResultUuid() {
      return result_uuid;
    }

    /**
      * Sets the value of the 'result_uuid' field.
      * @param value The value of 'result_uuid'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setResultUuid(java.lang.CharSequence value) {
      validate(fields()[8], value);
      this.result_uuid = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'result_uuid' field has been set.
      * @return True if the 'result_uuid' field has been set, false otherwise.
      */
    public boolean hasResultUuid() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'result_uuid' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearResultUuid() {
      result_uuid = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /**
      * Gets the value of the 'result_timestamp' field.
      * @return The value.
      */
    public java.lang.CharSequence getResultTimestamp() {
      return result_timestamp;
    }

    /**
      * Sets the value of the 'result_timestamp' field.
      * @param value The value of 'result_timestamp'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setResultTimestamp(java.lang.CharSequence value) {
      validate(fields()[9], value);
      this.result_timestamp = value;
      fieldSetFlags()[9] = true;
      return this;
    }

    /**
      * Checks whether the 'result_timestamp' field has been set.
      * @return True if the 'result_timestamp' field has been set, false otherwise.
      */
    public boolean hasResultTimestamp() {
      return fieldSetFlags()[9];
    }


    /**
      * Clears the value of the 'result_timestamp' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearResultTimestamp() {
      result_timestamp = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /**
      * Gets the value of the 'pairs' field.
      * @return The value.
      */
    public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> getPairs() {
      return pairs;
    }

    /**
      * Sets the value of the 'pairs' field.
      * @param value The value of 'pairs'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setPairs(java.util.Map<java.lang.CharSequence,java.lang.CharSequence> value) {
      validate(fields()[10], value);
      this.pairs = value;
      fieldSetFlags()[10] = true;
      return this;
    }

    /**
      * Checks whether the 'pairs' field has been set.
      * @return True if the 'pairs' field has been set, false otherwise.
      */
    public boolean hasPairs() {
      return fieldSetFlags()[10];
    }


    /**
      * Clears the value of the 'pairs' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearPairs() {
      pairs = null;
      fieldSetFlags()[10] = false;
      return this;
    }

    /**
      * Gets the value of the 'operation' field.
      * @return The value.
      */
    public com.pzybrick.iote2e.schema.avro.OPERATION getOperation() {
      return operation;
    }

    /**
      * Sets the value of the 'operation' field.
      * @param value The value of 'operation'.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder setOperation(com.pzybrick.iote2e.schema.avro.OPERATION value) {
      validate(fields()[11], value);
      this.operation = value;
      fieldSetFlags()[11] = true;
      return this;
    }

    /**
      * Checks whether the 'operation' field has been set.
      * @return True if the 'operation' field has been set, false otherwise.
      */
    public boolean hasOperation() {
      return fieldSetFlags()[11];
    }


    /**
      * Clears the value of the 'operation' field.
      * @return This builder.
      */
    public com.pzybrick.iote2e.schema.avro.Iote2eResult.Builder clearOperation() {
      operation = null;
      fieldSetFlags()[11] = false;
      return this;
    }

    /* (non-Javadoc)
     * @see org.apache.avro.data.RecordBuilder#build()
     */
    @Override
    public Iote2eResult build() {
      try {
        Iote2eResult record = new Iote2eResult();
        record.login_name = fieldSetFlags()[0] ? this.login_name : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.source_name = fieldSetFlags()[1] ? this.source_name : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.source_type = fieldSetFlags()[2] ? this.source_type : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.metadata = fieldSetFlags()[3] ? this.metadata : (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>) defaultValue(fields()[3]);
        record.request_uuid = fieldSetFlags()[4] ? this.request_uuid : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.request_timestamp = fieldSetFlags()[5] ? this.request_timestamp : (java.lang.CharSequence) defaultValue(fields()[5]);
        record.result_code = fieldSetFlags()[6] ? this.result_code : (java.lang.Integer) defaultValue(fields()[6]);
        record.result_error_message = fieldSetFlags()[7] ? this.result_error_message : (java.lang.CharSequence) defaultValue(fields()[7]);
        record.result_uuid = fieldSetFlags()[8] ? this.result_uuid : (java.lang.CharSequence) defaultValue(fields()[8]);
        record.result_timestamp = fieldSetFlags()[9] ? this.result_timestamp : (java.lang.CharSequence) defaultValue(fields()[9]);
        record.pairs = fieldSetFlags()[10] ? this.pairs : (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>) defaultValue(fields()[10]);
        record.operation = fieldSetFlags()[11] ? this.operation : (com.pzybrick.iote2e.schema.avro.OPERATION) defaultValue(fields()[11]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  /** The Constant WRITER$. */
  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  /* (non-Javadoc)
   * @see org.apache.avro.specific.SpecificRecordBase#writeExternal(java.io.ObjectOutput)
   */
  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  /** The Constant READER$. */
  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  /* (non-Javadoc)
   * @see org.apache.avro.specific.SpecificRecordBase#readExternal(java.io.ObjectInput)
   */
  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
