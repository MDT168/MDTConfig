package com.mdt168.configapi.datatypes;

import java.util.*;

public class ConfigDataTypes {
    public static final IntegerDataType INTEGER = new IntegerDataType();
    public static final DoubleDataType DOUBLE = new DoubleDataType();
    public static final LongDataType LONG = new LongDataType();
    public static final BooleanDataType BOOLEAN = new BooleanDataType();
    public static final StringDataType STRING = new StringDataType();
    public static final UUIDDataType UUID = new UUIDDataType();
    public static final FloatDataType FLOAT = new FloatDataType();
    public static final ShortDataType SHORT = new ShortDataType();
    public static final ByteDataType BYTE = new ByteDataType();
    public static final DurationDataType DURATION = new DurationDataType();
    public static final HexDataType HEX_COLOR = new HexDataType();
    public static final BigIntegerDataType BIG_INTEGER = new BigIntegerDataType();
    public static final BigDecimalDataType BIG_DECIMAL = new BigDecimalDataType();
    public static final URIDataType URI = new URIDataType();
    public static final URLDataType URL = new URLDataType();
    public static final PathDataType PATH = new PathDataType();
    public static final FileDataType FILE = new FileDataType();
    public static final NameToWorldDataType WORLD_FROM_NAME = new NameToWorldDataType();
    public static final UUIDToWorldDataType WORLD_FROM_UUID = new UUIDToWorldDataType();
    public static final PotionEffectTypeDataType POTION_EFFECT_TYPE = new PotionEffectTypeDataType();
    public static final ObjectDataType OBJECT = new ObjectDataType();

    public static <T> ConfigDataType<Optional<T>> optionalOf(ConfigDataType<T> type) {
        return new OptionalDataType<>(type);
    }

    public static <T extends Enum<T>> ConfigDataType<T> enumType(Class<T> enumClass) {
        return new EnumDataType<>(enumClass);
    }

    public static <T> ConfigDataType<List<T>> listOf(ConfigDataType<T> dataType) {
        return new ListDataType<>(dataType);
    }

    public static <T> ConfigDataType<Queue<T>> queueOf(ConfigDataType<T> dataType) {
        return new QueueDataType<>(dataType);
    }

    public static <T> ConfigDataType<Set<T>> setOf(ConfigDataType<T> dataType) {
        return new SetDataType<>(dataType);
    }

    public static <V> ConfigDataType<Map<String, V>> mapOf(ConfigDataType<V> dataType) {
        return new MapDataType<>(dataType);
    }

    private ConfigDataTypes() {}
}
