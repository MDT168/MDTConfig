package com.mdt168.configapi.datatypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;

public class DurationDataType implements ConfigDataType<ConfigDuration> {
    DurationDataType() {}

    @Override
    public @NotNull Object serialize(@NotNull ConfigDuration value) {
        return value.durationString();
    }

    @Override
    public @Nullable ConfigDuration deserialize(@NotNull Object object) {
        if (object instanceof ConfigDuration dur) return dur;
        String s = String.valueOf(object);
        Duration duration = parseDuration(s);
        if (duration == null) return null;
        return new ConfigDuration(s, duration);
    }

    @Override
    public @NotNull String getName() {
        return "Duration (e.g., 10s, 3m, 5h, 2d)";
    }

    /**
     * Parses the duration string (e.g., 5m) to Duration Object
     * @param input The duration in text
     * @return The Duration representation of the String
     */
    public static @Nullable Duration parseDuration(String input) {
        try {
            long value = Long.parseLong(input.substring(0, input.length() - 1));
            char unit = input.charAt(input.length() - 1);

            return switch (unit) {
                case 's' -> Duration.ofSeconds(value);
                case 'm' -> Duration.ofMinutes(value);
                case 'h' -> Duration.ofHours(value);
                case 'd' -> Duration.ofDays(value);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }
}
