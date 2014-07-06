/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.thrift.ton.gen.nonStrictSubenum;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Struktura implements org.apache.thrift.TBase<Struktura, Struktura._Fields>, java.io.Serializable, Cloneable, Comparable<Struktura> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Struktura");

  private static final org.apache.thrift.protocol.TField SUB_FIELD_DESC = new org.apache.thrift.protocol.TField("sub", org.apache.thrift.protocol.TType.STRUCT, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StrukturaStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StrukturaTupleSchemeFactory());
  }

  public SubStruct sub; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUB((short)1, "sub");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // SUB
          return SUB;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUB, new org.apache.thrift.meta_data.FieldMetaData("sub", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SubStruct.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Struktura.class, metaDataMap);
  }

  public Struktura() {
  }

  public Struktura(
    SubStruct sub)
  {
    this();
    this.sub = sub;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Struktura(Struktura other) {
    if (other.isSetSub()) {
      this.sub = new SubStruct(other.sub);
    }
  }

  public Struktura deepCopy() {
    return new Struktura(this);
  }

  @Override
  public void clear() {
    this.sub = null;
  }

  public SubStruct getSub() {
    return this.sub;
  }

  public Struktura setSub(SubStruct sub) {
    this.sub = sub;
    return this;
  }

  public void unsetSub() {
    this.sub = null;
  }

  /** Returns true if field sub is set (has been assigned a value) and false otherwise */
  public boolean isSetSub() {
    return this.sub != null;
  }

  public void setSubIsSet(boolean value) {
    if (!value) {
      this.sub = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SUB:
      if (value == null) {
        unsetSub();
      } else {
        setSub((SubStruct)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUB:
      return getSub();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SUB:
      return isSetSub();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Struktura)
      return this.equals((Struktura)that);
    return false;
  }

  public boolean equals(Struktura that) {
    if (that == null)
      return false;

    boolean this_present_sub = true && this.isSetSub();
    boolean that_present_sub = true && that.isSetSub();
    if (this_present_sub || that_present_sub) {
      if (!(this_present_sub && that_present_sub))
        return false;
      if (!this.sub.equals(that.sub))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(Struktura other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSub()).compareTo(other.isSetSub());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSub()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sub, other.sub);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Struktura(");
    boolean first = true;

    sb.append("sub:");
    if (this.sub == null) {
      sb.append("null");
    } else {
      sb.append(this.sub);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (sub != null) {
      sub.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class StrukturaStandardSchemeFactory implements SchemeFactory {
    public StrukturaStandardScheme getScheme() {
      return new StrukturaStandardScheme();
    }
  }

  private static class StrukturaStandardScheme extends StandardScheme<Struktura> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Struktura struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SUB
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.sub = new SubStruct();
              struct.sub.read(iprot);
              struct.setSubIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Struktura struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.sub != null) {
        oprot.writeFieldBegin(SUB_FIELD_DESC);
        struct.sub.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StrukturaTupleSchemeFactory implements SchemeFactory {
    public StrukturaTupleScheme getScheme() {
      return new StrukturaTupleScheme();
    }
  }

  private static class StrukturaTupleScheme extends TupleScheme<Struktura> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Struktura struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSub()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetSub()) {
        struct.sub.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Struktura struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.sub = new SubStruct();
        struct.sub.read(iprot);
        struct.setSubIsSet(true);
      }
    }
  }

}
