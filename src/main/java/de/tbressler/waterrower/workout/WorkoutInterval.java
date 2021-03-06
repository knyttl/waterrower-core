package de.tbressler.waterrower.workout;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

/**
 * A workout interval (a part of a workout).
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class WorkoutInterval {

    /* The rest interval. */
    private final int restInterval;

    /* The distance or duration. */
    private final int distance;

    /* The unit of the workout distance/duration. */
    private final WorkoutUnit unit;


    /**
     * A workout interval.
     *
     * @param distance The distance (in meters/strokes) or duration (in seconds) of the workout. When unit = METERS,
     *                 MILES or KMS: this value is in Meters, the display value for miles is a conversion and valid
     *                 values are 0x0001 to 0xFA00. When unit = STROKES this value is the number of strokes and valid
     *                 values are 0x0001 to 0x1388. When unit = SECONDS this value is in seconds. Valid values are 0x0001
     *                 to 0x4650. This value is limited to 5 Hours, which is 18,000 seconds.
     * @param unit The unit of the workout distance/duration, must not be null.
     */
    public WorkoutInterval(int distance, WorkoutUnit unit) {
        this(0, distance, unit);
    }


    /**
     * A workout interval.
     *
     * @param restInterval The rest interval (in seconds) or 0 if no rest interval must be
     *                     set. Usually for single workouts or the first interval of an interval
     *                     workout. Valid values are 0x0000 to 0x0E10.
     * @param distance The distance (in meters/strokes) or duration (in seconds) of the workout. When unit = METERS,
     *                 MILES or KMS: this value is in Meters, the display value for miles is a conversion and valid
     *                 values are 0x0001 to 0xFA00. When unit = STROKES this value is the number of strokes and valid
     *                 values are 0x0001 to 0x1388. When unit = SECONDS this value is in seconds. Valid values are 0x0001
     *                 to 0x4650. This value is limited to 5 Hours, which is 18,000 seconds.
     * @param unit The unit of the workout distance/duration, must not be null.
     */
    public WorkoutInterval(int restInterval, int distance, WorkoutUnit unit) {
        this.restInterval = checkRestInterval(restInterval);
        this.unit = requireNonNull(unit);
        this.distance = checkDistance(distance, unit);
    }

    /* Checks if the rest interval is in range. */
    private int checkRestInterval(int restInterval) {
        if ((restInterval < 0x0000) || (restInterval > 0x0E10))
            throw new IllegalArgumentException("The rest interval must be between 0x0000 and 0x0E10!");
        return restInterval;
    }

    // TODO Duplicate code!
    /* Checks if distance is in range. */
    private int checkDistance(int distance, WorkoutUnit unit) {
        switch(unit) {
            case METERS:
            case MILES:
            case KMS:
                // When unit = METERS, MILES or KMS: this value is in Meters, the display value for
                // miles is a conversion and valid values are 0x0001 to 0xFA00.
                if ((distance < 0x0001) || (distance > 0xFA00))
                    throw new IllegalArgumentException("The distance of the workout must be between 0x0001 and 0xFA00!");
                break;
            case STROKES:
                // When unit = STROKES this value is the number of strokes and valid values are
                // 0x0001 to 0x1388.
                if ((distance < 0x0001) || (distance > 0x1388))
                    throw new IllegalArgumentException("The distance of the workout must be between 0x0001 and 0x1388!");
                break;
            case SECONDS:
                // When unit = SECONDS this value is in seconds. Valid values are 0x0001 to 0x4650. This value is limited
                // to 5 Hours, which is 18,000 seconds.
                if ((distance < 0x0001) || (distance > 0x4650))
                    throw new IllegalArgumentException("The duration of the workout must be between 0x0001 and 0x4650!");
                break;
        }
        return distance;
    }


    /**
     * The rest interval in seconds.
     *
     * Can be 0 if no rest interval is specified. Usually this is the case if this is the first
     * interval of an interval workout.
     *
     * @return The rest interval.
     */
    public int getRestInterval() {
        return restInterval;
    }


    /**
     * Returns the distance or duration of the workout interval.
     *
     * @return The distance or duration.
     */
    public int getDistance() {
        return distance;
    }


    /**
     * Returns the unit of the workout distance/duration.
     *
     * @return The unit of the workout distance/duration.
     */
    public WorkoutUnit getUnit() {
        return unit;
    }


    @Override
    public String toString() {
        return toStringHelper(this)
                .add("restInterval", restInterval)
                .add("distance", distance)
                .add("unit", unit)
                .toString();
    }

}
