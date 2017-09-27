package de.tbressler.waterrower.subscriptions;

import de.tbressler.waterrower.io.msg.in.DataMemoryMessage;
import de.tbressler.waterrower.model.WorkoutFlags;

import static de.tbressler.waterrower.io.msg.Memory.SINGLE_MEMORY;
import static de.tbressler.waterrower.model.MemoryLocation.FEXTENDED;

/**
 * Subscription for working and workout control flags (FEXTENDED).
 *
 * The received message contains the following flags:
 * 0 = fzone_hr fextended; working in heartrate zone
 * 1 = fzone_int fextended; working in intensity zone
 * 2 = fzone_sr fextended; working in strokerate zone
 * 3 = fprognostics fextended; prognostics active.
 * 4 = fworkout_dis fextended; workout distance mode
 * 5 = fworkout_dur fextended; workout duration mode
 * 6 = fworkout_dis_i fextended; workout distance interval mode
 * 7 = fworkout_dur_i fextended; workout duration interval mode
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public abstract class WorkoutModeSubscription extends AbstractMemorySubscription {

    /* The last received workout flags. */
    private WorkoutFlags lastWorkoutFlags;


    /**
     * Subscription for working and workout control flags (FEXTENDED).
     */
    public WorkoutModeSubscription() {
        super(SINGLE_MEMORY, FEXTENDED);
    }


    @Override
    public final void handle(DataMemoryMessage msg) {

        WorkoutFlags flags = new WorkoutFlags(msg.getValue1());

        // If the received workout flags are the same as before,
        // don't send an update.
        if (flags.equals(lastWorkoutFlags))
            return;

        lastWorkoutFlags = flags;

        onWorkoutModeUpdated(flags);
    }


    /**
     * Is called, when an update of the workout mode flag (FEXTENDED) was received.
     *
     * @param flags The flags of the workout mode, never null.
     */
    abstract void onWorkoutModeUpdated(WorkoutFlags flags);

}
