package com.codemao.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author hekang
 * @des
 * @date 2021/3/11 16:31
 */
public class TActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        HashMap<String, String> map = new HashMap<>();
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();

//        ArrayList<String> arrayList2=new ArrayList<String>();
//        arrayList2.add("1");//编译通过
//        arrayList2.add(1);//编译通过

        MString[] mArr = new MString[5];
    }

    class MObject<T> {
        T t;
    }

    class MString extends MObject<String> {

    }
}
