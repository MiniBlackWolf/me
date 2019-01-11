package study.kotin.my.find.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (callback != null)
            return callback.onGenericMotionEvent(event);
        return super.onGenericMotionEvent(event);
    }
    //定义一个接口，把滚动事件传递出去
    public interface GenericMotionCallback {
        boolean onGenericMotionEvent(MotionEvent event);
    }

    GenericMotionCallback callback;

    public void setCallback(GenericMotionCallback callback) {
        this.callback = callback;
    }


}
