package cn.user0308.scutkicking.activity;

import android.app.Activity;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Yuan Qiang on 2017/5/13.
 * 用于管理活动
 */

public class ScreenManager {
    public  Stack<Activity> activityStack;
    private static ScreenManager instance;
    private ScreenManager(){}

    public static ScreenManager getScreenManager() {
        if(instance == null){
            instance = new ScreenManager();
        }
        return instance;
    }

    public  void popActivity(){
        Activity activity = activityStack.lastElement();
        if(activity != null){
            activity.finish();
            activity = null;
        }
    }

    public  void popActivity(Activity activity){
        if(activity != null){
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }


    public  Activity currentActivity(){
        Activity activity;
        if (activityStack.size() != 0) {
            activity = activityStack.lastElement();
        }else {
            activity = null;
        }
        return activity;
    }

    public  void pushActivity(Activity activity){
        if (activityStack == null){
            activityStack = new Stack<>();
        }
        activityStack.push(activity);
    }

    public  void popAllActivityExceptOne(Class cls){
        while (true){
            Activity activity = currentActivity();
            if(activity == null){
                break;
            }
            if(activity.getClass().equals(cls)){
                break;
            }
            popActivity(activity);
        }
    }

    public  void quit(){
        while (true){
            Activity activity = currentActivity();
            if (activity == null){
                break;
            }
            popActivity(activity);
        }
    }
}
