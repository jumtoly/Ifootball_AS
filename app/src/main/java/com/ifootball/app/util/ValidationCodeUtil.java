package com.ifootball.app.util;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.ifootball.app.entity.ValidationCodeData;

public class ValidationCodeUtil {
	private static final char[] CHARS = { '0', '1', '2', '3', '4', '5', '6',  
        '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',  
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',  
        'x', 'y', 'z'}; 
	private static ValidationCodeUtil validationCodeUtil;
	
	Random random = new Random();
	int lineNumber=3;
	int codeLength=5;
	int width = 140;
	int height = 60;
	int base_padding_left = 0;
	int range_padding_left = 10;
	int padding_left;
	int padding_top=40;
	
	public static ValidationCodeUtil getInstance() {
		if (validationCodeUtil == null) {
			validationCodeUtil = new ValidationCodeUtil();
		}
		return validationCodeUtil;
	}
	
	public ValidationCodeData getValidationCodeData(){
		String code=createCode();
		ValidationCodeData data=new ValidationCodeData();
		data.setCode(code);
		data.setBitmap(createValidationCodeBitmap(code));
		
		return data;
	}
	
	private Bitmap createValidationCodeBitmap(String code) {
		Bitmap bitmap=null;
		if (code!=null&&!"".equals(code.trim())) {
			code=code.toUpperCase();
			padding_left = 0;
			base_padding_left = width/ code.trim().length();
			bitmap=Bitmap.createBitmap(width, height, Config.ARGB_8888);
			Canvas canvas=new Canvas(bitmap);
			canvas.drawColor(Color.WHITE);
			Paint paint=new Paint();
			paint.setTextSize(30);
//			paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
			paint.setColor(Color.BLUE);
			
			for (int i = 0; i < code.length(); i++) {
				randomTextStyle(paint);
				randomPadding(i);
				canvas.drawText(String.valueOf(code.charAt(i)), padding_left, padding_top, paint);
			}
			for (int i = 0; i < lineNumber; i++) {
				drawLine(canvas, paint);//画线
			}
			
//			for (int i = 0; i < 255; i++) {
//				drawPoints(canvas, paint);//画点
//			}
		}
		
		return bitmap;
	}
	
	private String createCode() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < codeLength; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}
	
	private void randomTextStyle(Paint paint) {
		int color = randomColor();
		paint.setColor(color);
		paint.setFakeBoldText(random.nextBoolean());
		float skewX = random.nextInt(11) / 10;
		skewX = random.nextBoolean() ? skewX : -skewX;
		paint.setTextSkewX(skewX);
	}
	
	private void randomPadding(int i) {
		padding_left = base_padding_left * i+ random.nextInt(range_padding_left);
	}
	
	 /**
	  * 画线
	  * @param canvas
	  * @param paint
	  */
	private void drawLine(Canvas canvas, Paint paint) {
		int color = randomColor();
		int startX = random.nextInt(width);
		int startY = random.nextInt(height);
		int stopX = random.nextInt(width);
		int stopY = random.nextInt(height);
		paint.setStrokeWidth(4);
		paint.setColor(color);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}
	
	/**
	 * 画点
	 * @param canvas
	 * @param paint
	 */
	private void drawPoints(Canvas canvas, Paint paint){
		int color = randomColor();
		int stopX = random.nextInt(width);
		int stopY = random.nextInt(height);
		paint.setStrokeWidth(1);
		paint.setColor(color);
		canvas.drawPoint(stopX, stopY, paint);
	}
	
	private int randomColor(int rate) {
//		int red = random.nextInt(256) / rate;
//		int green = random.nextInt(256) / rate;
//		int blue = random.nextInt(256) / rate;
//		return Color.rgb(red, green, blue);
		
		return Color.BLUE;
	}

	private int randomColor() {
		return randomColor(1);
	}
}
