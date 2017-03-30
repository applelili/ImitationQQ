package com.gfd.qq;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String strings[] = {"开通会员", "QQ钱包", "个性装扮", "我的收藏", "我的相册", "我的文件", "我的日程", "我的名片夹"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setState();
        setContentView(R.layout.activity_main);
        ListView mListLeft = (ListView) findViewById(R.id.list_left);
        mListLeft.setDividerHeight(0);
        mListLeft.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,strings));
        ImageView mImageLeft = (ImageView) findViewById(R.id.img_left_head);
        float currentY = mImageLeft.getTranslationY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageLeft, "translationY", currentY, -100, -40, currentY);
        animator.setDuration(5000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.start();
    }

    @TargetApi(20)
    public void setState(){
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        getWindow().setAttributes(layoutParams);
    }
}
