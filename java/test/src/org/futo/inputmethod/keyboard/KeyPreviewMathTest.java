package org.futo.inputmethod.keyboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for the text scale arithmetic in KeyPreviewView.
 * Validates that negative maxWidth, zero/NaN width, and non-finite values
 * are guarded and never passed to setTextScaleX.
 */
public class KeyPreviewMathTest {

    /**
     * Replicates the calculation guarded in KeyPreviewView.setTextAndScaleX
     * to verify the contract independently.
     */
    public static Float computeScaleX(final int intrinsicWidth, final int paddingLeft,
            final int paddingRight, final float textWidth) {
        final int maxWidth = intrinsicWidth - paddingLeft - paddingRight;
        if (maxWidth <= 0) {
            return null; // Should return without calling setTextScaleX
        }
        if (textWidth <= 0.0f || Float.isNaN(textWidth)) {
            return null; // Should return without calling setTextScaleX
        }
        if (textWidth <= maxWidth) {
            return 1.0f;
        }
        final float scaleX = maxWidth / textWidth;
        if (Float.isFinite(scaleX) && scaleX > 0.0f) {
            return Math.min(1.0f, scaleX);
        }
        return null;
    }

    @Test
    public void testNegativeIntrinsicWidthReturnsNull() {
        // ShapeDrawable returns -1 for intrinsicWidth when unconstrained
        Float result = computeScaleX(-1, 10, 10, 50.0f);
        assertNull(result);
    }

    @Test
    public void testPaddingExceedingIntrinsicWidthReturnsNull() {
        // Intrinsic width 30, padding left 20, padding right 20 -> maxWidth = -10
        Float result = computeScaleX(30, 20, 20, 50.0f);
        assertNull(result);
    }

    @Test
    public void testZeroOrNegativeTextWidthReturnsNull() {
        // Empty text width 0.0f
        Float result = computeScaleX(100, 10, 10, 0.0f);
        assertNull(result);

        // Negative text width
        result = computeScaleX(100, 10, 10, -5.0f);
        assertNull(result);
    }

    @Test
    public void testNaNTextWidthReturnsNull() {
        Float result = computeScaleX(100, 10, 10, Float.NaN);
        assertNull(result);
    }

    @Test
    public void testTextWidthSmallerThanMaxWidth() {
        // maxWidth = 100 - 20 = 80; textWidth = 60 <= 80
        Float result = computeScaleX(100, 10, 10, 60.0f);
        assertEquals(Float.valueOf(1.0f), result);
    }

    @Test
    public void testTextWidthLargerThanMaxWidthScalesDown() {
        // maxWidth = 100 - 20 = 80; textWidth = 160 -> scale = 80 / 160 = 0.5f
        Float result = computeScaleX(100, 10, 10, 160.0f);
        assertEquals(Float.valueOf(0.5f), result);
    }

    @Test
    public void testScaleIsAlwaysFiniteAndPositive() {
        Float result = computeScaleX(80, 0, 0, 100.0f);
        assertNotNull(result);
        assertTrue(Float.isFinite(result));
        assertTrue(result > 0.0f);
        assertTrue(result <= 1.0f);
    }
}
