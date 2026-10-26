package org.futo.inputmethod.keyboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MoreKeysKeyboardParamsTest {

    private MoreKeysKeyboard.MoreKeysKeyboardParams mParams;

    @Before
    public void setUp() {
        mParams = new MoreKeysKeyboard.MoreKeysKeyboardParams();
    }

    @Test
    public void testSetParametersWithZeroKeyWidthDoesNotThrow() {
        // Zero key width should be guarded with safeKeyWidth = Math.max(1, keyWidth)
        mParams.setParameters(5, 5, 0, 100, 200, 1080, false, false, 0);
        assertTrue(mParams.mDefaultKeyWidth >= 1);
        assertTrue(mParams.mNumColumns >= 1);
        assertTrue(mParams.mNumRows >= 1);
    }

    @Test
    public void testSetParametersWithSmallParentWidthDoesNotThrow() {
        // Parent keyboard width is smaller than keyWidth * numKeys
        // In previous versions this threw IllegalArgumentException: "Keyboard is too small to hold more keys"
        mParams.setParameters(10, 5, 100, 100, 50, 80, false, false, 0);
        assertTrue(mParams.mNumColumns >= 1);
        assertTrue(mParams.mNumRows >= 1);
        assertTrue(mParams.mLeftKeys >= 0);
        assertTrue(mParams.mRightKeys >= 0);
    }

    @Test
    public void testSetParametersBoundaryCoords() {
        // Parent key at left edge (coordXInParent = 0)
        mParams.setParameters(6, 3, 100, 100, 0, 1080, false, false, 0);
        assertTrue(mParams.mLeftKeys >= 0);
        assertTrue(mParams.mRightKeys >= 0);
        assertEquals(mParams.mNumColumns, mParams.mLeftKeys + mParams.mRightKeys);

        // Parent key at right edge (coordXInParent = parentKeyboardWidth)
        mParams.setParameters(6, 3, 100, 100, 1080, 1080, false, false, 0);
        assertTrue(mParams.mLeftKeys >= 0);
        assertTrue(mParams.mRightKeys >= 0);
        assertEquals(mParams.mNumColumns, mParams.mLeftKeys + mParams.mRightKeys);
    }

    @Test
    public void testSetParametersNormalSingleMoreKey() {
        // Single more key popup (e.g. "-" having "+")
        mParams.setParameters(1, 1, 100, 120, 500, 1080, true, false, 0);
        assertEquals(1, mParams.mNumColumns);
        assertEquals(1, mParams.mNumRows);
        assertEquals(100, mParams.mDefaultKeyWidth);
        assertEquals(120, mParams.mDefaultRowHeight);
        assertEquals(0, mParams.mLeftKeys);
        assertEquals(1, mParams.mRightKeys);
    }

    @Test
    public void testSetParametersSymbolCluster() {
        // 10 symbol more-keys with 5 columns
        mParams.setParameters(10, 5, 100, 100, 500, 1080, false, false, 2);
        assertTrue(mParams.mNumColumns >= 1);
        assertTrue(mParams.mNumRows >= 2);
        assertTrue(mParams.mOccupiedWidth > 0);
        assertTrue(mParams.mOccupiedHeight > 0);
    }
}
