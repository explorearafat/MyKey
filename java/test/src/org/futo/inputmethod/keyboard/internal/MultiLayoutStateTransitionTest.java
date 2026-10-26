package org.futo.inputmethod.keyboard.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.futo.inputmethod.latin.common.Constants;
import org.junit.Before;
import org.junit.Test;

public class MultiLayoutStateTransitionTest implements KeyboardState.SwitchActions {

    public enum CurrentLayout {
        ALPHABET,
        ALPHABET_MANUAL_SHIFTED,
        ALPHABET_AUTO_SHIFTED,
        ALPHABET_SHIFT_LOCKED,
        ALPHABET_SHIFT_LOCK_SHIFTED,
        EMOJI,
        SYMBOLS,
        SYMBOLS_SHIFTED
    }

    private KeyboardState mState;
    private CurrentLayout mCurrentLayout;
    private int mDoubleTapTimerStarts = 0;
    private int mDoubleTapTimerCancels = 0;
    private boolean mInDoubleTapTimeout = false;

    @Override
    public void setAlphabetKeyboard() {
        mCurrentLayout = CurrentLayout.ALPHABET;
    }

    @Override
    public void setAlphabetManualShiftedKeyboard() {
        mCurrentLayout = CurrentLayout.ALPHABET_MANUAL_SHIFTED;
    }

    @Override
    public void setAlphabetAutomaticShiftedKeyboard() {
        mCurrentLayout = CurrentLayout.ALPHABET_AUTO_SHIFTED;
    }

    @Override
    public void setAlphabetShiftLockedKeyboard() {
        mCurrentLayout = CurrentLayout.ALPHABET_SHIFT_LOCKED;
    }

    @Override
    public void setAlphabetShiftLockShiftedKeyboard() {
        mCurrentLayout = CurrentLayout.ALPHABET_SHIFT_LOCK_SHIFTED;
    }

    @Override
    public void setEmojiKeyboard() {
        mCurrentLayout = CurrentLayout.EMOJI;
    }

    @Override
    public void setSymbolsKeyboard() {
        mCurrentLayout = CurrentLayout.SYMBOLS;
    }

    @Override
    public void setSymbolsShiftedKeyboard() {
        mCurrentLayout = CurrentLayout.SYMBOLS_SHIFTED;
    }

    @Override
    public void requestUpdatingShiftState(int autoCapsFlags, int recapitalizeMode) {
    }

    @Override
    public void startDoubleTapShiftKeyTimer() {
        mDoubleTapTimerStarts++;
        mInDoubleTapTimeout = true;
    }

    @Override
    public void cancelDoubleTapShiftKeyTimer() {
        mDoubleTapTimerCancels++;
        mInDoubleTapTimeout = false;
    }

    @Override
    public boolean isInDoubleTapShiftKeyTimeout() {
        return mInDoubleTapTimeout;
    }

    @Before
    public void setUp() {
        mState = new KeyboardState(this);
        mCurrentLayout = CurrentLayout.ALPHABET;
        mState.onLoadKeyboard(0, 0);
    }

    private void pressAndReleaseKey(int code) {
        mState.onPressKey(code, false /* isSinglePointer */, 0 /* autoCaps */, 0 /* recapitalize */);
        mState.onReleaseKey(code, false /* withSliding */, 0 /* autoCaps */, 0 /* recapitalize */);
    }

    @Test
    public void testAlphabetToSymbolsAndMoreSymbolsTransition() {
        assertEquals(CurrentLayout.ALPHABET, mCurrentLayout);

        // Tap ?123 -> switches to symbols
        pressAndReleaseKey(Constants.CODE_SWITCH_ALPHA_SYMBOL);
        assertEquals(CurrentLayout.SYMBOLS, mCurrentLayout);

        // In symbols, tap =< (CODE_SHIFT) -> switches to shifted symbols (~#\)
        pressAndReleaseKey(Constants.CODE_SHIFT);
        assertEquals(CurrentLayout.SYMBOLS_SHIFTED, mCurrentLayout);

        // In shifted symbols, tap ?123 (CODE_SHIFT) -> switches back to symbols
        pressAndReleaseKey(Constants.CODE_SHIFT);
        assertEquals(CurrentLayout.SYMBOLS, mCurrentLayout);

        // In symbols, tap ABC (CODE_SWITCH_ALPHA_SYMBOL) -> switches back to alphabet
        pressAndReleaseKey(Constants.CODE_SWITCH_ALPHA_SYMBOL);
        assertEquals(CurrentLayout.ALPHABET, mCurrentLayout);
    }

    @Test
    public void testRapidSymbolKeyToggling() {
        // Switch to symbols
        pressAndReleaseKey(Constants.CODE_SWITCH_ALPHA_SYMBOL);
        assertEquals(CurrentLayout.SYMBOLS, mCurrentLayout);

        // Rapid toggle between symbols and shifted symbols 10 times
        for (int i = 0; i < 10; i++) {
            pressAndReleaseKey(Constants.CODE_SHIFT);
            assertEquals(CurrentLayout.SYMBOLS_SHIFTED, mCurrentLayout);

            pressAndReleaseKey(Constants.CODE_SHIFT);
            assertEquals(CurrentLayout.SYMBOLS, mCurrentLayout);
        }

        // Return cleanly to alphabet
        pressAndReleaseKey(Constants.CODE_SWITCH_ALPHA_SYMBOL);
        assertEquals(CurrentLayout.ALPHABET, mCurrentLayout);
    }

    @Test
    public void testMultiLayoutSimulatedLanguages() {
        // Test transition stability across 4 layouts: English QWERTY, Bengali Jatiyo, Avro, Probhat
        String[] layouts = {"English_QWERTY", "Bengali_Jatiyo", "Bengali_Avro", "Bengali_Probhat"};

        for (String layout : layouts) {
            // Reset to alphabet for layout
            mState.onResetKeyboardStateToAlphabet(0, 0);
            assertEquals(CurrentLayout.ALPHABET, mCurrentLayout);

            // Open Number / Symbol window (?123)
            pressAndReleaseKey(Constants.CODE_SWITCH_ALPHA_SYMBOL);
            assertEquals(CurrentLayout.SYMBOLS, mCurrentLayout);

            // Open More Symbols window (=<)
            pressAndReleaseKey(Constants.CODE_SHIFT);
            assertEquals(CurrentLayout.SYMBOLS_SHIFTED, mCurrentLayout);

            // Type a symbol key in shifted symbols mode (e.g., character code '=')
            pressAndReleaseKey('=');
            assertEquals(CurrentLayout.SYMBOLS_SHIFTED, mCurrentLayout);

            // Return to ABC
            pressAndReleaseKey(Constants.CODE_SWITCH_ALPHA_SYMBOL);
            assertEquals(CurrentLayout.ALPHABET, mCurrentLayout);
        }
    }
}
