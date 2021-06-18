package com.mao.whitewine;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.mao.whitewine.dao.UserDao;
import com.mao.whitewine.pojo.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mao.whitewine", appContext.getPackageName());
        UserDao userDao = new UserDao(appContext);
        User user = userDao.queryUserByName("one");
        if(user != null){
            System.out.println("成功");
        }else {
            System.out.println("失败");
        }
    }
}
