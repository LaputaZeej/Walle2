package com.bugull.android.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import org.jetbrains.annotations.NotNull;

/**
 * Author by xpl, Date on 2021/4/30.
 */
public class HDSettings {
    /**
     * 设置
     */
    public static boolean gotoSetting(@NotNull Context context) {
        return gotoIntents(context, ctx -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        });
    }

    /**
     * App信息
     */
    public static boolean gotoAppInfo(@NotNull Context context) {
        return gotoIntents(context, ctx -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            if (!isIntentAvailable(context, intent)) throw new RuntimeException("请检查intent");
            ctx.startActivity(intent);
        });
    }

    public static boolean gotoDeviceInfo(@NotNull Context context) {
        return gotoIntents(context, ctx -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_DEVICE_INFO_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        });
    }

    private static boolean isIntentAvailable(@NotNull Context context, @NotNull Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    /**
     * 开发者选项
     */
    public static boolean gotoDevelopment(@NotNull Context context) {
        return gotoIntents(context, ctx -> {
            try {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                gotoDevelopmentCompat(context);
            }
        });
    }

    /**
     * 开发者选项
     */
    private static void gotoDevelopmentCompat(@NotNull Context context)  {
        try {
            ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(componentName);
            intent.setAction("android.intent.action.View");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoDevelopmentCompatForXIAOMI(context);
        }
    }

    private static void gotoDevelopmentCompatForXIAOMI(@NotNull Context context)  {
        try {
            //部分小米手机采用这种方式跳转
            Intent intent = new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 本地语言
     */
    public static boolean gotoLocal(@NotNull Context context) {
        return gotoIntents(context, cxt -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCALE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            cxt.startActivity(intent);
        });
    }

    /**
     * 蓝牙
     */
    public static boolean gotoBluetooth(@NotNull Context context) {
        return gotoIntents(context, cxt -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            cxt.startActivity(intent);
        });
    }

    private static boolean gotoIntents(@NotNull Context context, Action action) {
        boolean result = false;
        try {
            action.work(context);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private interface Action {
        void work(@NotNull Context context);
    }


}
