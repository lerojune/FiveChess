package com.app.yunrich.fivechess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Render extends View {

    Mesh mesh;
    int who = 0;//0-black, 1-white
    Rule rule = new Rule();
    boolean isPause = false;

    public Render(Context context) {
        super(context);
    }

    public Render(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Render(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMap(canvas);
    }

    public void reset() {
        if (mesh != null) mesh.init();
        who = 0;
        isPause = false;
        postInvalidate();
    }

    protected void drawMap(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.parseColor("#000000"));
        if (mesh == null)
            return;

        int pad = 100;
        int padx = (getWidth() - (mesh.width - 1) * pad) / 2;
        int pady = (getHeight() - (mesh.height - 1) * pad) / 2;

        //hang
        float startx = padx, endx = padx + (mesh.width - 1) * pad;
        for (int index = 0; index < mesh.height; index++) {
            float y = padx + pad * index;
            canvas.drawLine(startx, y, endx, y, paint);
        }

        //lie
        float starty = pady, endy = pady + (mesh.height - 1) * pad;
        for (int index = 0; index < mesh.width; index++) {
            float x = pady + pad * index;
            canvas.drawLine(x, starty, x, endy, paint);
        }

        for (int index = 0; index < mesh.width; index++) {
            for (int indexh = 0; indexh < mesh.height; indexh++) {
                int v = mesh.getPoint(index, indexh);
                int x = padx + index * pad;
                int y = pady + indexh * pad;
                if (v == 1) {
                    paint.setColor(Color.parseColor("#333333"));
                    canvas.drawCircle(x, y, 30, paint);
                } else if (v == 2) {
                    paint.setColor(Color.parseColor("#aaaaaa"));
                    canvas.drawCircle(x, y, 30, paint);
                }
            }
        }
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
        postInvalidate();
    }

    protected Point getPoint(float x, float y) {
        Point point = null;

        int pad = 100;
        int padx = (getWidth() - (mesh.width - 1) * pad) / 2;
        int pady = (getHeight() - (mesh.height - 1) * pad) / 2;

        int nx = Math.round((x - padx) / pad);
        int ny = Math.round((y - pady) / pad);
        if (ny >= 0 && ny <= mesh.height && nx <= mesh.width && nx >= 0) {
            point = new Point(nx, ny);
        }
        return point;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isPause)
            return super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            if (mesh == null)
                return super.onTouchEvent(event);

            Point p = getPoint(x, y);
            if (p == null)
                return super.onTouchEvent(event);
            if (who == 0) {
                who = 1;
                mesh.add(p.x, p.y, 1);
            } else {
                who = 0;
                mesh.add(p.x, p.y, 2);
            }
            int tm = rule.check(mesh);
            if (tm == 1){//黑胜利
                Toast.makeText(getContext(),"黑胜利",Toast.LENGTH_LONG).show();
                isPause = true;
            }else if (tm == 2){//白胜利
                Toast.makeText(getContext(),"白胜利",Toast.LENGTH_LONG).show();
                isPause = true;
            }
            postInvalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }
}
