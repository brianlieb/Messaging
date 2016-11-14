// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: any.proto

package com.google.protobuf;

/**
 * <pre>
 * `Any` contains an arbitrary serialized protocol buffer message along with a
 * URL that describes the type of the serialized message.
 * Protobuf library provides support to pack/unpack Any values in the form
 * of utility functions or additional generated methods of the Any type.
 * Example 1: Pack and unpack a message in C++.
 *     Foo foo = ...;
 *     Any any;
 *     any.PackFrom(foo);
 *     ...
 *     if (any.UnpackTo(&amp;foo)) {
 *       ...
 *     }
 * Example 2: Pack and unpack a message in Java.
 *     Foo foo = ...;
 *     Any any = Any.pack(foo);
 *     ...
 *     if (any.is(Foo.class)) {
 *       foo = any.unpack(Foo.class);
 *     }
 *  Example 3: Pack and unpack a message in Python.
 *     foo = Foo(...)
 *     any = Any()
 *     any.Pack(foo)
 *     ...
 *     if any.Is(Foo.DESCRIPTOR):
 *       any.Unpack(foo)
 *       ...
 * The pack methods provided by protobuf library will by default use
 * 'type.googleapis.com/full.type.name' as the type URL and the unpack
 * methods only use the fully qualified type name after the last '/'
 * in the type URL, for example "foo.bar.com/x/y.z" will yield type
 * name "y.z".
 * JSON
 * ====
 * The JSON representation of an `Any` value uses the regular
 * representation of the deserialized, embedded message, with an
 * additional field `&#64;type` which contains the type URL. Example:
 *     package google.profile;
 *     message Person {
 *       string first_name = 1;
 *       string last_name = 2;
 *     }
 *     {
 *       "&#64;type": "type.googleapis.com/google.profile.Person",
 *       "firstName": &lt;string&gt;,
 *       "lastName": &lt;string&gt;
 *     }
 * If the embedded message type is well-known and has a custom JSON
 * representation, that representation will be embedded adding a field
 * `value` which holds the custom JSON in addition to the `&#64;type`
 * field. Example (for message [google.protobuf.Duration][]):
 *     {
 *       "&#64;type": "type.googleapis.com/google.protobuf.Duration",
 *       "value": "1.212s"
 *     }
 * </pre>
 *
 * Protobuf type {@code google.protobuf.Any}
 */
public  final class Any extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:google.protobuf.Any)
    AnyOrBuilder {
  // Use Any.newBuilder() to construct.
  private Any(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Any() {
    typeUrl_ = "";
    value_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private Any(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            typeUrl_ = s;
            break;
          }
          case 18: {

            value_ = input.readBytes();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.google.protobuf.AnyProto.internal_static_google_protobuf_Any_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.protobuf.AnyProto.internal_static_google_protobuf_Any_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.protobuf.Any.class, com.google.protobuf.Any.Builder.class);
  }

  private static String getTypeUrl(
      java.lang.String typeUrlPrefix,
      com.google.protobuf.Descriptors.Descriptor descriptor) {
    return typeUrlPrefix.endsWith("/")
        ? typeUrlPrefix + descriptor.getFullName()
        : typeUrlPrefix + "/" + descriptor.getFullName();
  }

  private static String getTypeNameFromTypeUrl(
      java.lang.String typeUrl) {
    int pos = typeUrl.lastIndexOf('/');
    return pos == -1 ? "" : typeUrl.substring(pos + 1);
  }

  public static <T extends com.google.protobuf.Message> Any pack(
      T message) {
    return Any.newBuilder()
        .setTypeUrl(getTypeUrl("type.googleapis.com",
                               message.getDescriptorForType()))
        .setValue(message.toByteString())
        .build();
  }

  /**
   * Packs a message uisng the given type URL prefix. The type URL will
   * be constructed by concatenating the message type's full name to the
   * prefix with an optional "/" separator if the prefix doesn't end
   * with "/" already.
   */
  public static <T extends com.google.protobuf.Message> Any pack(
      T message, java.lang.String typeUrlPrefix) {
    return Any.newBuilder()
        .setTypeUrl(getTypeUrl(typeUrlPrefix,
                               message.getDescriptorForType()))
        .setValue(message.toByteString())
        .build();
  }

  public <T extends com.google.protobuf.Message> boolean is(
      java.lang.Class<T> clazz) {
    T defaultInstance =
        com.google.protobuf.Internal.getDefaultInstance(clazz);
    return getTypeNameFromTypeUrl(getTypeUrl()).equals(
        defaultInstance.getDescriptorForType().getFullName());
  }

  private volatile com.google.protobuf.Message cachedUnpackValue;

  public <T extends com.google.protobuf.Message> T unpack(
      java.lang.Class<T> clazz)
      throws com.google.protobuf.InvalidProtocolBufferException {
    if (!is(clazz)) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          "Type of the Any message does not match the given class.");
    }
    if (cachedUnpackValue != null) {
      return (T) cachedUnpackValue;
    }
    T defaultInstance =
        com.google.protobuf.Internal.getDefaultInstance(clazz);
    T result = (T) defaultInstance.getParserForType()
        .parseFrom(getValue());
    cachedUnpackValue = result;
    return result;
  }
  public static final int TYPE_URL_FIELD_NUMBER = 1;
  private volatile java.lang.Object typeUrl_;
  /**
   * <pre>
   * A URL/resource name whose content describes the type of the
   * serialized protocol buffer message.
   * For URLs which use the scheme `http`, `https`, or no scheme, the
   * following restrictions and interpretations apply:
   * * If no scheme is provided, `https` is assumed.
   * * The last segment of the URL's path must represent the fully
   *   qualified name of the type (as in `path/google.protobuf.Duration`).
   *   The name should be in a canonical form (e.g., leading "." is
   *   not accepted).
   * * An HTTP GET on the URL must yield a [google.protobuf.Type][]
   *   value in binary format, or produce an error.
   * * Applications are allowed to cache lookup results based on the
   *   URL, or have them precompiled into a binary to avoid any
   *   lookup. Therefore, binary compatibility needs to be preserved
   *   on changes to types. (Use versioned type names to manage
   *   breaking changes.)
   * Schemes other than `http`, `https` (or the empty scheme) might be
   * used with implementation specific semantics.
   * </pre>
   *
   * <code>optional string type_url = 1;</code>
   */
  public java.lang.String getTypeUrl() {
    java.lang.Object ref = typeUrl_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      typeUrl_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * A URL/resource name whose content describes the type of the
   * serialized protocol buffer message.
   * For URLs which use the scheme `http`, `https`, or no scheme, the
   * following restrictions and interpretations apply:
   * * If no scheme is provided, `https` is assumed.
   * * The last segment of the URL's path must represent the fully
   *   qualified name of the type (as in `path/google.protobuf.Duration`).
   *   The name should be in a canonical form (e.g., leading "." is
   *   not accepted).
   * * An HTTP GET on the URL must yield a [google.protobuf.Type][]
   *   value in binary format, or produce an error.
   * * Applications are allowed to cache lookup results based on the
   *   URL, or have them precompiled into a binary to avoid any
   *   lookup. Therefore, binary compatibility needs to be preserved
   *   on changes to types. (Use versioned type names to manage
   *   breaking changes.)
   * Schemes other than `http`, `https` (or the empty scheme) might be
   * used with implementation specific semantics.
   * </pre>
   *
   * <code>optional string type_url = 1;</code>
   */
  public com.google.protobuf.ByteString
      getTypeUrlBytes() {
    java.lang.Object ref = typeUrl_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      typeUrl_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int VALUE_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString value_;
  /**
   * <pre>
   * Must be a valid serialized protocol buffer of the above specified type.
   * </pre>
   *
   * <code>optional bytes value = 2;</code>
   */
  public com.google.protobuf.ByteString getValue() {
    return value_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getTypeUrlBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, typeUrl_);
    }
    if (!value_.isEmpty()) {
      output.writeBytes(2, value_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getTypeUrlBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, typeUrl_);
    }
    if (!value_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, value_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.google.protobuf.Any)) {
      return super.equals(obj);
    }
    com.google.protobuf.Any other = (com.google.protobuf.Any) obj;

    boolean result = true;
    result = result && getTypeUrl()
        .equals(other.getTypeUrl());
    result = result && getValue()
        .equals(other.getValue());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + TYPE_URL_FIELD_NUMBER;
    hash = (53 * hash) + getTypeUrl().hashCode();
    hash = (37 * hash) + VALUE_FIELD_NUMBER;
    hash = (53 * hash) + getValue().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.protobuf.Any parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.protobuf.Any parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.protobuf.Any parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.protobuf.Any parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.protobuf.Any parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.protobuf.Any parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.protobuf.Any parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.google.protobuf.Any parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.protobuf.Any parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.protobuf.Any parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.google.protobuf.Any prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * `Any` contains an arbitrary serialized protocol buffer message along with a
   * URL that describes the type of the serialized message.
   * Protobuf library provides support to pack/unpack Any values in the form
   * of utility functions or additional generated methods of the Any type.
   * Example 1: Pack and unpack a message in C++.
   *     Foo foo = ...;
   *     Any any;
   *     any.PackFrom(foo);
   *     ...
   *     if (any.UnpackTo(&amp;foo)) {
   *       ...
   *     }
   * Example 2: Pack and unpack a message in Java.
   *     Foo foo = ...;
   *     Any any = Any.pack(foo);
   *     ...
   *     if (any.is(Foo.class)) {
   *       foo = any.unpack(Foo.class);
   *     }
   *  Example 3: Pack and unpack a message in Python.
   *     foo = Foo(...)
   *     any = Any()
   *     any.Pack(foo)
   *     ...
   *     if any.Is(Foo.DESCRIPTOR):
   *       any.Unpack(foo)
   *       ...
   * The pack methods provided by protobuf library will by default use
   * 'type.googleapis.com/full.type.name' as the type URL and the unpack
   * methods only use the fully qualified type name after the last '/'
   * in the type URL, for example "foo.bar.com/x/y.z" will yield type
   * name "y.z".
   * JSON
   * ====
   * The JSON representation of an `Any` value uses the regular
   * representation of the deserialized, embedded message, with an
   * additional field `&#64;type` which contains the type URL. Example:
   *     package google.profile;
   *     message Person {
   *       string first_name = 1;
   *       string last_name = 2;
   *     }
   *     {
   *       "&#64;type": "type.googleapis.com/google.profile.Person",
   *       "firstName": &lt;string&gt;,
   *       "lastName": &lt;string&gt;
   *     }
   * If the embedded message type is well-known and has a custom JSON
   * representation, that representation will be embedded adding a field
   * `value` which holds the custom JSON in addition to the `&#64;type`
   * field. Example (for message [google.protobuf.Duration][]):
   *     {
   *       "&#64;type": "type.googleapis.com/google.protobuf.Duration",
   *       "value": "1.212s"
   *     }
   * </pre>
   *
   * Protobuf type {@code google.protobuf.Any}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.protobuf.Any)
      com.google.protobuf.AnyOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.google.protobuf.AnyProto.internal_static_google_protobuf_Any_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.protobuf.AnyProto.internal_static_google_protobuf_Any_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.protobuf.Any.class, com.google.protobuf.Any.Builder.class);
    }

    // Construct using com.google.protobuf.Any.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      typeUrl_ = "";

      value_ = com.google.protobuf.ByteString.EMPTY;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.google.protobuf.AnyProto.internal_static_google_protobuf_Any_descriptor;
    }

    public com.google.protobuf.Any getDefaultInstanceForType() {
      return com.google.protobuf.Any.getDefaultInstance();
    }

    public com.google.protobuf.Any build() {
      com.google.protobuf.Any result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.google.protobuf.Any buildPartial() {
      com.google.protobuf.Any result = new com.google.protobuf.Any(this);
      result.typeUrl_ = typeUrl_;
      result.value_ = value_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.google.protobuf.Any) {
        return mergeFrom((com.google.protobuf.Any)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.protobuf.Any other) {
      if (other == com.google.protobuf.Any.getDefaultInstance()) return this;
      if (!other.getTypeUrl().isEmpty()) {
        typeUrl_ = other.typeUrl_;
        onChanged();
      }
      if (other.getValue() != com.google.protobuf.ByteString.EMPTY) {
        setValue(other.getValue());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.google.protobuf.Any parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.google.protobuf.Any) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object typeUrl_ = "";
    /**
     * <pre>
     * A URL/resource name whose content describes the type of the
     * serialized protocol buffer message.
     * For URLs which use the scheme `http`, `https`, or no scheme, the
     * following restrictions and interpretations apply:
     * * If no scheme is provided, `https` is assumed.
     * * The last segment of the URL's path must represent the fully
     *   qualified name of the type (as in `path/google.protobuf.Duration`).
     *   The name should be in a canonical form (e.g., leading "." is
     *   not accepted).
     * * An HTTP GET on the URL must yield a [google.protobuf.Type][]
     *   value in binary format, or produce an error.
     * * Applications are allowed to cache lookup results based on the
     *   URL, or have them precompiled into a binary to avoid any
     *   lookup. Therefore, binary compatibility needs to be preserved
     *   on changes to types. (Use versioned type names to manage
     *   breaking changes.)
     * Schemes other than `http`, `https` (or the empty scheme) might be
     * used with implementation specific semantics.
     * </pre>
     *
     * <code>optional string type_url = 1;</code>
     */
    public java.lang.String getTypeUrl() {
      java.lang.Object ref = typeUrl_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        typeUrl_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * A URL/resource name whose content describes the type of the
     * serialized protocol buffer message.
     * For URLs which use the scheme `http`, `https`, or no scheme, the
     * following restrictions and interpretations apply:
     * * If no scheme is provided, `https` is assumed.
     * * The last segment of the URL's path must represent the fully
     *   qualified name of the type (as in `path/google.protobuf.Duration`).
     *   The name should be in a canonical form (e.g., leading "." is
     *   not accepted).
     * * An HTTP GET on the URL must yield a [google.protobuf.Type][]
     *   value in binary format, or produce an error.
     * * Applications are allowed to cache lookup results based on the
     *   URL, or have them precompiled into a binary to avoid any
     *   lookup. Therefore, binary compatibility needs to be preserved
     *   on changes to types. (Use versioned type names to manage
     *   breaking changes.)
     * Schemes other than `http`, `https` (or the empty scheme) might be
     * used with implementation specific semantics.
     * </pre>
     *
     * <code>optional string type_url = 1;</code>
     */
    public com.google.protobuf.ByteString
        getTypeUrlBytes() {
      java.lang.Object ref = typeUrl_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        typeUrl_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * A URL/resource name whose content describes the type of the
     * serialized protocol buffer message.
     * For URLs which use the scheme `http`, `https`, or no scheme, the
     * following restrictions and interpretations apply:
     * * If no scheme is provided, `https` is assumed.
     * * The last segment of the URL's path must represent the fully
     *   qualified name of the type (as in `path/google.protobuf.Duration`).
     *   The name should be in a canonical form (e.g., leading "." is
     *   not accepted).
     * * An HTTP GET on the URL must yield a [google.protobuf.Type][]
     *   value in binary format, or produce an error.
     * * Applications are allowed to cache lookup results based on the
     *   URL, or have them precompiled into a binary to avoid any
     *   lookup. Therefore, binary compatibility needs to be preserved
     *   on changes to types. (Use versioned type names to manage
     *   breaking changes.)
     * Schemes other than `http`, `https` (or the empty scheme) might be
     * used with implementation specific semantics.
     * </pre>
     *
     * <code>optional string type_url = 1;</code>
     */
    public Builder setTypeUrl(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      typeUrl_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * A URL/resource name whose content describes the type of the
     * serialized protocol buffer message.
     * For URLs which use the scheme `http`, `https`, or no scheme, the
     * following restrictions and interpretations apply:
     * * If no scheme is provided, `https` is assumed.
     * * The last segment of the URL's path must represent the fully
     *   qualified name of the type (as in `path/google.protobuf.Duration`).
     *   The name should be in a canonical form (e.g., leading "." is
     *   not accepted).
     * * An HTTP GET on the URL must yield a [google.protobuf.Type][]
     *   value in binary format, or produce an error.
     * * Applications are allowed to cache lookup results based on the
     *   URL, or have them precompiled into a binary to avoid any
     *   lookup. Therefore, binary compatibility needs to be preserved
     *   on changes to types. (Use versioned type names to manage
     *   breaking changes.)
     * Schemes other than `http`, `https` (or the empty scheme) might be
     * used with implementation specific semantics.
     * </pre>
     *
     * <code>optional string type_url = 1;</code>
     */
    public Builder clearTypeUrl() {
      
      typeUrl_ = getDefaultInstance().getTypeUrl();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * A URL/resource name whose content describes the type of the
     * serialized protocol buffer message.
     * For URLs which use the scheme `http`, `https`, or no scheme, the
     * following restrictions and interpretations apply:
     * * If no scheme is provided, `https` is assumed.
     * * The last segment of the URL's path must represent the fully
     *   qualified name of the type (as in `path/google.protobuf.Duration`).
     *   The name should be in a canonical form (e.g., leading "." is
     *   not accepted).
     * * An HTTP GET on the URL must yield a [google.protobuf.Type][]
     *   value in binary format, or produce an error.
     * * Applications are allowed to cache lookup results based on the
     *   URL, or have them precompiled into a binary to avoid any
     *   lookup. Therefore, binary compatibility needs to be preserved
     *   on changes to types. (Use versioned type names to manage
     *   breaking changes.)
     * Schemes other than `http`, `https` (or the empty scheme) might be
     * used with implementation specific semantics.
     * </pre>
     *
     * <code>optional string type_url = 1;</code>
     */
    public Builder setTypeUrlBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      typeUrl_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString value_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * Must be a valid serialized protocol buffer of the above specified type.
     * </pre>
     *
     * <code>optional bytes value = 2;</code>
     */
    public com.google.protobuf.ByteString getValue() {
      return value_;
    }
    /**
     * <pre>
     * Must be a valid serialized protocol buffer of the above specified type.
     * </pre>
     *
     * <code>optional bytes value = 2;</code>
     */
    public Builder setValue(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      value_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Must be a valid serialized protocol buffer of the above specified type.
     * </pre>
     *
     * <code>optional bytes value = 2;</code>
     */
    public Builder clearValue() {
      
      value_ = getDefaultInstance().getValue();
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:google.protobuf.Any)
  }

  // @@protoc_insertion_point(class_scope:google.protobuf.Any)
  private static final com.google.protobuf.Any DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.google.protobuf.Any();
  }

  public static com.google.protobuf.Any getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Any>
      PARSER = new com.google.protobuf.AbstractParser<Any>() {
    public Any parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new Any(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Any> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Any> getParserForType() {
    return PARSER;
  }

  public com.google.protobuf.Any getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

