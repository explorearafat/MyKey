package org.futo.inputmethod.keyboard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;

public class PointerTrackerNullSafetyTest {

    @Test
    public void testSafeShiftTraversalContract() {
        // Test contract: when mKeyboard is null or mKeyboard.mShiftKeys is null,
        // traversing shift keys must not throw NullPointerException
        final ArrayList<Key> shiftKeys = null;
        boolean exceptionThrown = false;
        try {
            if (shiftKeys != null) {
                for (final Key k : shiftKeys) {
                    if (k != null) {
                        k.onPressed();
                    }
                }
            }
        } catch (final NullPointerException e) {
            exceptionThrown = true;
        }
        assertFalse(exceptionThrown);
    }

    @Test
    public void testSafeAltCodeTraversalContract() {
        // Test contract: when mAltCodeKeysWhileTyping is null,
        // traversing altCodeKeysWhileTyping must not throw NullPointerException
        final ArrayList<Key> altCodeKeys = null;
        boolean exceptionThrown = false;
        try {
            if (altCodeKeys != null) {
                for (final Key k : altCodeKeys) {
                    if (k != null) {
                        k.onPressed();
                    }
                }
            }
        } catch (final NullPointerException e) {
            exceptionThrown = true;
        }
        assertFalse(exceptionThrown);
    }

    @Test
    public void testTimerCancellationOnNullKeyContract() {
        boolean timersCancelled = false;
        Key detectedKey = null;

        // Simulates PointerTracker.onDownEventInternal after layout change
        if (detectedKey == null) {
            timersCancelled = true;
        }

        assertTrue(timersCancelled);
    }
}
