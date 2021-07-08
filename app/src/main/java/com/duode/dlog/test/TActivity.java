package com.duode.dlog.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.duode.dlog.MainActivity;
import com.duode.dlog.R;
import com.duode.dlog.test.bean.MObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

/**
 * @author hekang
 * @des
 * @date 2021/3/11 16:31
 */
public class TActivity extends Activity {

    public static Intent getCallIntent(Context context) {
        return new Intent(context, TActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        HashMap<String, String> map = new HashMap<String, String>();
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();

//        ArrayList<String> arrayList2=new ArrayList<String>();
//        arrayList2.add("1");//编译通过
//        arrayList2.add(1);//编译通过

//        MString[] mArr = new MString[5];
//        mArr[0] = new MString(new Test(1, "1"));
//
//        LogUtils.INSTANCE.e("TActivity", 0, "mArr[0]:" + mArr[0].t.toString());
//
//        Object[] objArray = new Object[20];
//
//        String[] strArray = (String[]) objArray;
//
//        strArray[0] = "new S(1)";
//        LogUtils.INSTANCE.e("TActivity", 0, "objArray[0]:" + objArray[0] + ";" + "strArray[0]:" + strArray[0]);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    if (isFinishing() || isDestroyed()) {
//                        break;
//                    }
                    if (MainActivity.Companion.getNeedDestroy()) {
                        break;
                    }
                    if (v == null) {
                        v = new Vector(10);
                        for (int i = 1; i < 100; i++) {
                            Object o = new Object();
                            v.add(o);
                            o = null;
                        }
                    }
                }
            }
        }, "TEST_MEMORY").start();

    }

    Vector v = null;

    public void inner() {
        //局部内部类
        class MyInner extends MObject<String> {
            public MyInner(String test) {
                t = test;
            }
        }
        MyInner inner = new MyInner("布局内部类");
    }

    class MString extends MObject<Test> {
        public MString(Test test) {
            t = test;
        }

    }

    class Test {
        int num;
        String name;

        public Test(int num, String name) {
            this.num = num;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "num=" + num +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
