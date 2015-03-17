package com.datang.miou.datastructure;

import java.util.ArrayList;

import com.datang.miou.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class Chart extends View{

	private int mHorizontalAxeNum = 5;
	private int mVerticalAxeNum = 10;
	private Paint axisPaint;
	private ArrayList<Point> mPoints;
	private ArrayList<Point> mCurrentPoints;
	//private int[] xValue = new int[mVerticalAxeNum + 1];
	private Paint pointPaint;
	private int measuredWidth;
	private int measuredHeight;
	private float mRate;
	private int mStartIndex = -1;
	private int mEndIndex = -1;
	private Point mLastPoint;
	private int mMaxStartIndex;
	private int mMaxEndIndex;
	
	private int mAxisColor = R.color.menu_border_gray;
	private int mPointColor = R.color.title_blue;
	private int mSelectedColor = R.color.black;
	
	
	public void setAxisColor(int axisColor) {
		this.mAxisColor = axisColor;
	}

	public void setPointColor(int pointColor) {
		this.mPointColor = pointColor;
	}

	public void setSelectedColor(int selectedColor) {
		Resources r = this.getResources();
		this.mSelectedColor = selectedColor;
		selectPaint.setColor(r.getColor(selectedColor));
	}

	public int getHorizontalAxeNum() {
		return mHorizontalAxeNum;
	}

	public void setHorizontalAxeNum(int horizontalAxeNum) {
		this.mHorizontalAxeNum = horizontalAxeNum;
	}

	public int getVerticalAxeNum() {
		return mVerticalAxeNum;
	}

	public void setVerticalAxeNum(int verticalAxeNum) {
		this.mVerticalAxeNum = verticalAxeNum;
	}

	public interface mCallback {
		public void onClickOnPoint(Chart.Point p);
	}
	private mCallback cb;
	private Paint selectPaint;
	
	public void setCb(mCallback cb) {
		this.cb = cb;
	}
	
	public class Point {
		int px;
		int py;
		public int data;
		boolean selected = false;
		
		public Point(int px, int py, int data) {
			this.px = px;
			this.py = py;
			this.data = data;
		}
	}
	
	public Chart(Context context) {
		super(context);
		// TODO �Զ���ɵĹ��캯����
		cb = (mCallback) context;
		initChart();
	}

	public Chart(Context context, AttributeSet attrs) {
		super(context, attrs);
		//cb = (mCallback) context;
		initChart();
	}
	
	public Chart(Context context, AttributeSet attrs, int defaultStyle) {
		super(context, attrs, defaultStyle);
		cb = (mCallback) context;
		initChart();
	}
	
	private void initChart() {
		// TODO �Զ���ɵķ������
		Resources r = this.getResources();
		
		axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		axisPaint.setColor(r.getColor(mAxisColor));
		axisPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		
		pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pointPaint.setColor(r.getColor(mPointColor));
		pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		pointPaint.setStrokeWidth((float) 5.0);
		
		selectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		selectPaint.setColor(r.getColor(mSelectedColor));
		selectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		selectPaint.setStrokeWidth((float) 5.0);
		
		mPoints = new ArrayList<Point>();
		mCurrentPoints = new ArrayList<Point>();
	}

	private int[] transformDataToPoints(int[] data) {
		// TODO �Զ���ɵķ������
		int result[] = new int[data.length];
		mRate = (getMaxInArray(data) - getMinInArray(data)) / (float) measuredHeight;
		for (int i = 0; i < data.length; i++) {
			result[i] = measuredHeight - (int) (data[i] / mRate);
		}
		return result;
	}
	
	private int getMinInArray(int[] data) {
		// TODO �Զ���ɵķ������
		int min;
		min = data[0];
		for (int i : data) {
			if (i < min) {
				min = i;
			}
		}
		return min;
	}

	private int getMaxInArray(int[] data) {
		// TODO �Զ���ɵķ������
		int max;
		max = data[0];
		for (int i : data) {
			if (i > max) {
				max = i;
			}
		}
		return max;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO �Զ���ɵķ������
		int measuredWidth = measure(widthMeasureSpec);
		int measuredHeight = measure(heightMeasureSpec);
		
		setMeasuredDimension(measuredWidth, measuredHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		measuredWidth = getMeasuredWidth();
		measuredHeight = getMeasuredHeight();
		
		for (int i = 0; i < mVerticalAxeNum + 1; i++) {
			int x = (measuredWidth / mVerticalAxeNum) * i;
			//xValue[i] = x;
			for (int j = 0; j < mHorizontalAxeNum + 1; j++) {
				int y = (measuredHeight / mHorizontalAxeNum) * j;
				//mPoints[i][j] = new Point(x, y);
			}
		}
		
		/*
		 * ����
		 */
		for (int i = 1; i < mHorizontalAxeNum; i++) {
			int startY = (measuredHeight / mHorizontalAxeNum) * i;
			int stopY = (measuredHeight / mHorizontalAxeNum) * i;
			int startX = 0;
			int stopX = measuredWidth;
			canvas.drawLine(startX, startY, stopX, stopY, axisPaint);
		}
		canvas.save();
		
		/*
		 * ����
		 */
		for (int i = 1; i < mVerticalAxeNum; i++) {
			int startX = (measuredWidth / mVerticalAxeNum) * i;
			int stopX = (measuredWidth / mVerticalAxeNum) * i;
			int startY = 0;
			int stopY = measuredWidth;
			canvas.drawLine(startX, startY, stopX, stopY, axisPaint);
		}
		canvas.save();
		
		int[] data = {100, 600, 300, 400, 500, 1000, 600, 700, 1200};
		int[] result = new int[data.length];
		result = transformDataToPoints(data);
		
		/*
		for (int i = 0; i < result.length; i++) {
			Point point = new Point(xValue[i], result[i]);
			mPoints.add(point);
			mCurrentPoints.add(point);
		}
		*/
		
		/*
		 * ����
		 */
		for (Point p : mCurrentPoints) {
			if (p.selected) {
				canvas.drawCircle(p.px, p.py, 5, selectPaint);
			} else {
				canvas.drawCircle(p.px, p.py, 5, pointPaint);
			}
		}
		canvas.save();
		
		/*
		 * ����
		 */
		for (int i = 0; i < mCurrentPoints.size() - 1; i++) {
			Point start = mCurrentPoints.get(i);
			Point end = mCurrentPoints.get(i + 1);
			canvas.drawLine(start.px, start.py, end.px, end.py, pointPaint);
		}
		canvas.save();
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO �Զ���ɵķ������
		Point current = new Point((int) event.getX(), (int) event.getY(), 0);
		switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int bias = getDragDistance(current);
				if (bias != 0) {
					adjustCurrentPoints(bias);			
				} else {
					Point point = getNearbyPoint(current);
					if (point != null) {
						for (Point p : mCurrentPoints) {
							p.selected = false;
						}
						point.selected = true;
						cb.onClickOnPoint(point);
					}
				}
				invalidate();
				break;
			case MotionEvent.ACTION_DOWN:
				mLastPoint = new Point((int) event.getX(), (int) event.getY(), 0);
				break;
			default:
				break;
		}
		return true;
	}

	private void adjustCurrentPoints(int bias) {
		// TODO �Զ���ɵķ������
		if (bias < 0) {
			for (int i = 0; i < Math.abs(bias); i++) {
				if (mStartIndex > 0) {
					mCurrentPoints.remove(mVerticalAxeNum);
					mStartIndex--;
					mEndIndex--;
					mCurrentPoints.add(0, mPoints.get(mStartIndex));		
				}
			}
		} else {
			for (int i = 0; i < bias; i++) {
				if (mEndIndex < mPoints.size() - 1) {
					mCurrentPoints.remove(0);
					mCurrentPoints.add(mPoints.get(mEndIndex + 1));
					mEndIndex++;
					mStartIndex++;
				}		
			}
		}
		for (int i = 0; i < mCurrentPoints.size(); i++) {
			mCurrentPoints.get(i).px = i * (measuredWidth / mVerticalAxeNum);
		}
	}

	private int getDragDistance(Point current) {
		// TODO �Զ���ɵķ������
		return (int) ((double) (mLastPoint.px - current.px) / (measuredWidth / 10));
	}

	private Point getNearbyPoint(Point current) {
		// TODO �Զ���ɵķ������
		for (Point p : mCurrentPoints) {
			double distance = (Math.pow((double) (current.px - p.px), 2) + Math.pow((double) (current.py - p.py), 2));
			double toleration = 1000;
			if (distance < toleration) {
				return p;
			}
		}
		return null;
	}

	public void addPoint(int data) {
		int nowX;
		
		mStartIndex = mMaxStartIndex;
		mEndIndex = mMaxEndIndex;
		
		if (mPoints.size() > mVerticalAxeNum) {
			nowX = (mVerticalAxeNum + 1) * (measuredWidth / mVerticalAxeNum);
			mCurrentPoints.remove(0);
			
		} else {
			nowX = mPoints.size() * (measuredWidth / mVerticalAxeNum);
		}
		Point point = new Point(nowX, measuredHeight - (int) (data / mRate), data);
		mPoints.add(point);
		mCurrentPoints.add(point);
		
		mEndIndex++;
		if (mCurrentPoints.size() > mVerticalAxeNum) {
			mStartIndex++;
		}
		
		mMaxStartIndex = mStartIndex;
		mMaxEndIndex = mEndIndex;
		
		if (mPoints.size() > mVerticalAxeNum) {
			mCurrentPoints = new ArrayList<Point>();
			for (int i = mPoints.size() - mVerticalAxeNum - 1; i < mPoints.size(); i++) {
				mCurrentPoints.add(mPoints.get(i));
			}
		}
		
		for (int i = 0; i < mCurrentPoints.size(); i++) {
			mCurrentPoints.get(i).px = i * (measuredWidth / mVerticalAxeNum);
		}
		
		this.invalidate();
	}
	
	private int measure(int measureSpec) {
		// TODO �Զ���ɵķ������
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		
		if (specMode == MeasureSpec.UNSPECIFIED) {
			result = 200;
		} else {
			result = specSize;
		}
		
		return result;
	}	
}
