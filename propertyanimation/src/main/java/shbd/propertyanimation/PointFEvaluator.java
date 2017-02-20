package shbd.propertyanimation;


import android.graphics.PointF;

import com.nineoldandroids.animation.TypeEvaluator;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/2/16 11:02
 * 修改人：yh
 * 修改时间：2017/2/16 11:02
 * 修改备注：
 */
public class PointFEvaluator implements TypeEvaluator<PointF> {
    @Override
    public PointF evaluate(float fraction, PointF start, PointF end) {
        PointF evaluatePoint = new PointF();
        evaluatePoint.x = start.x+fraction * (end.x - start.x);
        evaluatePoint.y = start.y+fraction * (end.y - start.y);

        return evaluatePoint;
    }
}
