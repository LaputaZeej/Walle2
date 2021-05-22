package com.bugull.android.util;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HDSettingsTest {
    //@Rule
    //public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    @Test
    public void gotoSetting() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        boolean success = HDSettings.gotoSetting(appContext);
        assertTrue(success);
    }

    @Test
    public void gotoAppInfo() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        boolean success = HDSettings.gotoAppInfo(appContext);
        assertTrue(success);
    }

    @Test
    public void gotoDevelopment() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        boolean success = HDSettings.gotoDevelopment(appContext);
        assertTrue(success);
    }

    @Test
    public void gotoLocal() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        boolean success = HDSettings.gotoLocal(appContext);
        assertTrue(success);
    }

    @Test
    public void gotoBluetooth() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        boolean success = HDSettings.gotoBluetooth(appContext);
        assertTrue(success);
    }
}