package com.lxs.popupwindow;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdjustScreenBrightnessListener, PopupWindowOnClickListener {

    private MyPopupWindow myPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("CESHI", "getAdjustScreenBrightness():" + getAdjustScreenBrightness());
    }

    public void showAtLocation(View view) {
        myPopupWindow = new MyPopupWindow.Builder(this, R.layout.popupwindow, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                .setReferenceView(view)
                .setPopupWindowOnClickListener(this)
                .setAdjustScreenBrightnessListener(this)
                .setDismissAlpha(0.4f)
                .setAlpha(getAdjustScreenBrightness())
                .build();
        myPopupWindow.showAtLocation();
    }

    public void showAsDropDown(View view) {
        myPopupWindow = new MyPopupWindow.Builder(this, R.layout.popupwindow)
                .setReferenceView(view)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setHeight(WindowManager.LayoutParams.WRAP_CONTENT)
                .setPopupWindowOnClickListener(this)
                .setAdjustScreenBrightnessListener(this)
                .setDismissAlpha(0.4f)
                .setAlpha(getAdjustScreenBrightness())
                .build();
        myPopupWindow.showAsDropDown();
    }

    private ListPopupWindow listPopupWindow;

    public void ListPopupWindow(View view) {
//        ListPopupWindow可以用在下拉列表、查询结果显示、历史记录、提示输入等
        String[] array = new String[]{"添加", "编辑", "删除"};
        listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAnchorView(view);
        listPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT); //如果不设置宽度的话，默认取AnchorView的宽度，一般不是我们想要的结果
        listPopupWindow.setModal(true); //是否为模态，影响到对back按钮的处理
        listPopupWindow.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, android.R.id.text1, array));
        listPopupWindow.show();
    }

    @Override
    public void setAdjustScreenBrightness(@FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = alpha;
        getWindow().setAttributes(layoutParams);
    }

    public float getAdjustScreenBrightness() {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        return layoutParams.alpha;
    }

    @Override
    public void popupWindowOnClick(View view) {
        final TextView tv1 = (TextView) view.findViewById(R.id.queding);
        final TextView tv2 = (TextView) view.findViewById(R.id.quxiao);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), tv1.getText().toString(), Toast.LENGTH_SHORT).show();
                if (myPopupWindow.isShowing()) {
                    myPopupWindow.dismiss();
                }
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), tv2.getText().toString(), Toast.LENGTH_SHORT).show();
                if (myPopupWindow.isShowing()) {
                    myPopupWindow.dismiss();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myPopupWindow.isShowing()) {
            myPopupWindow.dismiss();
        }
        myPopupWindow.popupWindowDismiss();
        myPopupWindow = null;
    }
}
