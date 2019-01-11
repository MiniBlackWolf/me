package study.kotin.my.find.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.tencent.smtt.sdk.WebView;

public class mywebview extends WebView {
    public mywebview(Context context) {
        super(context);
    }

    public mywebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public mywebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public HitTestResult getHitTestResult() {
        return super.getHitTestResult();
    }

    /**
     * 取得横向滚动宽度
     */
    public int getHorizontalScrollWidth() {
        return computeHorizontalScrollRange();
    }

    /**
     * 取得横向滚动高度
     */
    public int getVerticalScrollHeight() {
        return computeVerticalScrollRange();
    }

    public interface ITouch {
        void onTouchPointerSingle();
        void onTouchPointerMult();
    }

    private ITouch touch;
    public void setITouch(ITouch touch) {
        this.touch = touch;
    }

    float x1 = 0;
    float y1 = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() >= 2) { //多点触控
            if (touch != null) {
                touch.onTouchPointerMult();
            }
        } else {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    y1 = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    x1 = 0;
                    y1 = 0;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x2 = event.getX();
                    float y2 = event.getY();
                    if (x2 == x1 || Math.abs(y2 - y1) > Math.abs(x2 - x1)) { //竖直方向移动
                        if (touch != null) {
                            touch.onTouchPointerSingle();
                        }
                    } else {
                        if (touch != null) {
                            touch.onTouchPointerMult();
                        }
                    }
                    break;
            }

        }
        return super.onTouchEvent(event);
    }

    //是否拦截触摸事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

}
