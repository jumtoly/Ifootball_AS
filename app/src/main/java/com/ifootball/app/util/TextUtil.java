package com.ifootball.app.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

/**
 * This class is used to apply style for string and this will reduce use of multiple views to render proper info.
 * 
 * and it will be used in two scenes: 
 * 1) format string,such as "商品名称：{0}，商品价格：{1}。"
 * 	@see TextUtil.applyFormatStyleSpannable(String src,LinkedList<String> args,LinkedList<TextStyleWrapper> styleWrapperList)
 * 	@see TextUtil.applyFormatStyleAllParamSections(String fromatString,LinkedList<String> args,LinkedList<TextStyleWrapper> styleWrapperList)
 * 
 * 2) common string such as "买就送100积分。"
 * 	@see TextUtil.applyFormatStyleSpannable(LinkedHashMap<String, TextStyleWrapper> textStyleMap)
 * 	@see TextUtil.applyFormatStyleSpannable(String text,TextStyleWrapper textStyleWrapper)
 * 
 */
public class TextUtil {
	
	/**
	 * Use HTML utils to format string sections 
	 * and the styled string should be loaded by WebView control.
	 * 
	 * @param textStyleMap
	 * 			the text according with its text style linked map
	 * @return 
	 * 			the styled HTML format string
	 */
	public static String applyFormatStyleByHTMLParser(LinkedHashMap<String, TextStyleWrapper> textStyleMap){
		
		StringBuilder stringBuilder = new StringBuilder();
		
		if (textStyleMap != null && !textStyleMap.isEmpty()) {	
			 Iterator<Entry<String, TextStyleWrapper>> iterator = textStyleMap.entrySet().iterator(); 
			 while (iterator.hasNext()) {
				 Entry<String, TextStyleWrapper> entry = iterator.next();
				 stringBuilder.append(applyFormatStyleByHTMLParser(entry.getKey(),entry.getValue()));
			}
		}
		
		return (String) stringBuilder.subSequence(0, stringBuilder.length());
	}

	/**
	 * Use HTML utils to format string sections 
	 * and the styled string should be loaded by WebView control.
	 * 
	 * @param textStyleWrapper
	 * 			@see class TextUtil.TextStyleWrapper
	 * 			the text according with its text style
	 * @return 
	 * 			the styled HTML format string
	 */
	public static String applyFormatStyleByHTMLParser(String text,TextStyleWrapper textStyleWrapper){

		if (text == null) {
			return null;
		}
		
		if (textStyleWrapper == null) {
			textStyleWrapper = new TextStyleWrapper();
		}
		
		String styledString = "";
		
		styledString += " <font color=\"" + getRealHTMLFontColor(textStyleWrapper.getTextColor()) + "\"";
		styledString += " size=\"" + getFontSize(textStyleWrapper.getTextSize())+ "\">" + text + "</font> ";
	
		if (textStyleWrapper.isTextBold()) {
			styledString = " <strong>" + styledString + "</strong> ";
		}

		return styledString;
	}
	
	private static String getRealHTMLFontColor(Object textColor){
		
		return textColor.equals(Color.BLACK) ? "#000000" : (String)textColor;
	}
	
	/**
	 * Apply style for format string
	 * and this styled string should be loaded by WebView control.
	 * 
	 * @param fromatString
	 * 		like format "商品价格:{0},商品评分： {1}."
	 * @param args
	 * 		format string placeholder params according to format string.
	 * @param styleWrapperList
	 * 		need 2 textstyles for format string and format placeholder param string.
	 * @return
	 * 
	 * @throws Exception
	 * 		The count of args is not equal with params count in format string.
	 */
	public static String applyFormatStyleByHTMLParser(String fromatString,LinkedList<String> args,LinkedList<TextStyleWrapper> styleWrapperList) throws Exception{
		
			if (fromatString == null) {
				
				return null;
			}
		
	        String[] stringSectionArray = fromatString.split("\\{(\\d+)\\}");
	        if (stringSectionArray == null || stringSectionArray.length == 0 || args == null || args.size() == 0) {
				
	        	return fromatString;
			}
	        
	        List<String> stringSectionList = new ArrayList<String>();
	       
	        for (int i = 0; i < stringSectionArray.length; i++) {
	        	stringSectionList.add(stringSectionArray[i]);
			}
	        
	        if (args.size() > stringSectionList.size()) {
				throw new Exception("count of args passed in is not equal with that of in format string.");
			}
	       
	       return TextUtil.applyFormatStyleByHTMLParser(getTextStyleMap(fromatString, args, styleWrapperList));
	}
	
	/**
	 * Apply style for format string
	 * and this styled string should be loaded by WebView control.
	 * 
	 * @param fromatString
	 * 		like format "商品名称：{0}，商品价格:{1},商品评分： {2}."
	 * @param args
	 * 		format string placeholder params according to format string.
	 * @param styleWrapperList
	 * 		need all string  sections textstyles for format string and format placeholder param string.
	 * @return
	 * 
	 * 		style of the main format string  is same,
	 *		and param holder style is different.
	 *	
	 * 		"商品名称：" style is YYY
	 * 		 "，商品价格:" style is also YYY
	 * 		 ",商品评分： " style is also YYY
	 * 		"{0}" style is ZZZ1
	 * 		"{1}" style is ZZZ2
	 * 		.....
	 * @throws Exception 
	 * 		The count of args is not equal with params count in format string.
	 */
	public static String applyFormatStyleAllParamSectionsByHTMLParser(String fromatString,LinkedList<String> args,LinkedList<TextStyleWrapper> styleWrapperList) throws Exception{
		
			if (fromatString == null) {
				
				return null;
			}
		
	        String[] stringSectionArray = fromatString.split("\\{(\\d+)\\}");
	        if (stringSectionArray == null || stringSectionArray.length == 0 || args == null || args.size() == 0) {
				
	        	return fromatString;
			}
	        
	        List<String> stringSectionList = new ArrayList<String>();
	        for (int i = 0; i < stringSectionArray.length; i++) {
	        	stringSectionList.add(stringSectionArray[i]);
			}
	        
	        if (args.size() > stringSectionList.size()) {
				throw new Exception("count of args passed in is not equal with that of in format string.");
			}

	       return TextUtil.applyFormatStyleByHTMLParser(getAllParamsTextStyleMap(fromatString, args, styleWrapperList));
	}
	
	/**
	 * Get approximate font size
	 * 
	 * @param textSize
	 * @return
	 * 		the HTML styled font size
	 */
	private static int getFontSize(int textSize){
		
		int fontSize = 2;
		
		if(textSize <= 10){
			fontSize = 1;
		}
		else if (textSize > 10 && textSize <= 13) {
			fontSize = 2;
		}
		else if (textSize > 13 && textSize <= 16) {
			fontSize = 3;
		}
		else if (textSize > 16 && textSize <= 19) {
			fontSize = 4;
		}
		else if (textSize > 19 && textSize <= 22) {
			fontSize = 5;
		}
		else if (textSize > 22 && textSize <= 25) {
			fontSize = 6;
		}
		else if (textSize > 25) {
			fontSize = 7;
		}
		
		return fontSize;
	}
	
	/**
	 * Apply style for format string
	 * 
	 * @param fromatString
	 * 		like format "商品价格:{0},商品评分： {1}."
	 * @param args
	 * 		format string placeholder params according to format string.
	 * @param styleWrapperList
	 * 		need 2 textstyles for format string and format placeholder param string.
	 * @return
	 * 
	 * @throws Exception
	 * 		The count of args is not equal with params count in format string.
	 */
	public static SpannableString applyFormatStyleSpannable(String fromatString,LinkedList<String> args,LinkedList<TextStyleWrapper> styleWrapperList) throws Exception{
		
			if (fromatString == null) {
				
				return null;
			}
		
	        String[] stringSectionArray = fromatString.split("\\{(\\d+)\\}");
	        if (stringSectionArray == null || stringSectionArray.length == 0 || args == null || args.size() == 0) {
				
	        	return new SpannableString(fromatString);
			}
	        
	        List<String> stringSectionList = new ArrayList<String>();
	       
	        for (int i = 0; i < stringSectionArray.length; i++) {
	        	stringSectionList.add(stringSectionArray[i]);
			}
	        
	        if (args.size() > stringSectionList.size()) {
				throw new Exception("count of args passed in is not equal with that of in format string.");
			}
	       

	       return TextUtil.applyFormatStyleSpannable(getTextStyleMap(fromatString, args, styleWrapperList));
	}
	
	/**
	 * Apply style for format string
	 * 
	 * @param fromatString
	 * 		like format "商品名称：{0}，商品价格:{1},商品评分： {2}."
	 * @param args
	 * 		format string placeholder params according to format string.
	 * @param styleWrapperList
	 * 		need all string  sections textstyles for format string and format placeholder param string.
	 * @return
	 * 
	 * 		style of the main format string  is same,
	 *		and param holder style is different.
	 *	
	 * 		"商品名称：" style is style1
	 * 		 "，商品价格:" style is also style1
	 * 		 ",商品评分： " style is also style1
	 * 		"{0}" style is style2
	 * 		"{1}" style is style3
	 * 		.....
	 * @throws Exception 
	 * 		The count of args is not equal with params count in format string.
	 */
	public static SpannableString applyFormatStyleAllParamSections(String fromatString,LinkedList<String> args,LinkedList<TextStyleWrapper> styleWrapperList) throws Exception{
		
			if (fromatString == null) {
				
				return null;
			}
		
	        String[] stringSectionArray = fromatString.split("\\{(\\d+)\\}");
	        if (stringSectionArray == null || stringSectionArray.length == 0 || args == null || args.size() == 0) {
				
	        	return new SpannableString(fromatString);
			}
	        
	        List<String> stringSectionList = new ArrayList<String>();
	        for (int i = 0; i < stringSectionArray.length; i++) {
	        	stringSectionList.add(stringSectionArray[i]);
			}
	        
	        if (args.size() > stringSectionList.size()) {
				throw new Exception("count of args passed in is not equal with that of in format string.");
			}
	  
	       return TextUtil.applyFormatStyleSpannable(getAllParamsTextStyleMap(fromatString, args, styleWrapperList));
	}
	
	
	/**
	 * Use SpannableString utils to apply Format Style
	 * @param textStyleMap
	 * 			the text according with its text style linked map
	 * @return 
	 * 			the styled Spannable format string
	 */
	public static SpannableString applyFormatStyleSpannable(LinkedHashMap<String, TextStyleWrapper> textStyleMap){
		
		SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
		
		if (textStyleMap != null && !textStyleMap.isEmpty()) {	
			 Iterator<Entry<String, TextStyleWrapper>> iterator = textStyleMap.entrySet().iterator(); 
			 while (iterator.hasNext()) {
				 Entry<String, TextStyleWrapper> entry = iterator.next();
				 stringBuilder.append(applyFormatStyleSpannable(entry.getKey(),entry.getValue()));
			}
		}
		
		return new SpannableString(stringBuilder.subSequence(0, stringBuilder.length()));
	}
	
	/**
	 * Use SpannableString utils to apply Format Style
	 * 
	 * @param textStyleWrapper
	 * 			@see class TextUtil.TextStyleWrapper
	 * 			the text according with its text style
	 * @return 
	 * 			the styled Spannable format string
	 */
	public static SpannableString applyFormatStyleSpannable(String text,TextStyleWrapper textStyleWrapper){
		
		
		if (text == null) {
			
			return null;
		}
		
		if (textStyleWrapper == null) {
			textStyleWrapper = new TextStyleWrapper();
		}
		
		SpannableString styledString = new SpannableString(text);
		styledString.setSpan(new ForegroundColorSpan(getRealFontColor(textStyleWrapper.getTextColor())), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		styledString.setSpan(new AbsoluteSizeSpan(textStyleWrapper.getTextSize(),false), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (textStyleWrapper.isTextBold()) {
			styledString.setSpan(new StyleSpan(Typeface.BOLD), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		
		return styledString;
	}
	
	private static LinkedHashMap<String, TextStyleWrapper> getAllParamsTextStyleMap(String fromatString,LinkedList<String> args,LinkedList<TextStyleWrapper> styleWrapperList) throws Exception{
		
        String[] stringSectionArray = fromatString.split("\\{(\\d+)\\}");
      
        List<String> stringSectionList = new ArrayList<String>();
        for (int i = 0; i < stringSectionArray.length; i++) {
        	stringSectionList.add(stringSectionArray[i]);
		}
     
        LinkedHashMap<String, TextStyleWrapper> map = new LinkedHashMap<String, TextStyleWrapper>();
        
        if (styleWrapperList == null || styleWrapperList.size() == 0) {
        	
        	LinkedList<TextStyleWrapper> defaultTextStyleWrappers = new LinkedList<TextStyleWrapper>();
        	for (int i = 0; i < 1 + args.size(); i++) {
        		defaultTextStyleWrappers.add(new TextStyleWrapper());
			}
        	styleWrapperList = defaultTextStyleWrappers;
		}else if (styleWrapperList != null && styleWrapperList.size() >= 1 && styleWrapperList.size() < 1 + args.size()) {
			
			int styleCount = styleWrapperList.size();
			for (int i = 0; i < 1 + args.size() - styleCount; i++) {
				styleWrapperList.add(new TextStyleWrapper());
			}
		}
        
        int k = 0;
        for (int i = 0; i < stringSectionList.size(); i++) {	
			map.put(stringSectionList.get(i), styleWrapperList.get(0));	
			for (int j = k; j < args.size();) {
				map.put(args.get(j), styleWrapperList.get(j + 1));
				k++;
				break;
			}	
		}

		return map;
	}
	
	private static LinkedHashMap<String, TextStyleWrapper> getTextStyleMap(String fromatString,LinkedList<String> args,LinkedList<TextStyleWrapper> styleWrapperList) throws Exception{
		
        String[] stringSectionArray = fromatString.split("\\{(\\d+)\\}");
   
        List<String> stringSectionList = new ArrayList<String>();
        for (int i = 0; i < stringSectionArray.length; i++) {
        	stringSectionList.add(stringSectionArray[i]);
		}
    
        LinkedHashMap<String, TextStyleWrapper> map = new LinkedHashMap<String, TextStyleWrapper>();

        if (styleWrapperList == null || styleWrapperList.size() == 0) {
        	
        	LinkedList<TextStyleWrapper> defaulTextStyleWrappers = new LinkedList<TextStyleWrapper>();
        	for (int i = 0; i < 2; i++) {
        		defaulTextStyleWrappers.add(new TextStyleWrapper());
			}
        	
        	styleWrapperList = defaulTextStyleWrappers;
		}
        else if(styleWrapperList != null && styleWrapperList.size() == 1){
        	
        	styleWrapperList.add(new TextStyleWrapper());
        }

        int k = 0;
        for (int i = 0; i < stringSectionList.size(); i++) {	
			map.put(stringSectionList.get(i), styleWrapperList.get(0));	
			for (int j = k; j < args.size();) {
				map.put(args.get(j), styleWrapperList.get(1));
				k++;
				break;
			}	
		}

		return map;
	}
	
	private static int getRealFontColor(Object textColor){
		
		return (Integer)(textColor instanceof Integer ? textColor : Color.parseColor((String)textColor));
	}
	
	public static final class TextStyleWrapper{
		
		static final int TEXT_COLOR_DEFAULT = Color.BLACK;
		static final int TEXT_SIZE_DEFAULT = 14;
		static final boolean TEXT_BOLD_DEFAULT = false;

		private static char[] HexChars = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		
		static final String COLOR_PREFIX = "#";
		
		private Object mTextColor;
		private int mTextSize;
		private boolean mTextBold;
		
		public TextStyleWrapper(){
			
			this(TEXT_COLOR_DEFAULT, TEXT_SIZE_DEFAULT, TEXT_BOLD_DEFAULT);
		}
		
		/**
		 * Text style wrapper,
		 * the text bold is false default.
		 * 
		 * @param mTextColor
		 * 	if you use WebView load data, then should pass in color string like format "#123456" ,
		 *  else you should pass in color integer like Color.parseColor("#123456") or other color integer convertions.
		 * 	
		 * @param mTextSize
		 */
		public TextStyleWrapper(Object mTextColor,int mTextSize){
			
			this(mTextColor, mTextSize, TEXT_BOLD_DEFAULT);
		}
		
		/**
		 * Text style wrapper,
		 *
		 * @param mTextColor
		 * 	if you use WebView load data, then should pass in color string like format "#123456" ,
		 *  else you should pass in color integer like Color.parseColor("#123456") or other color integer convertions.
		 * @param mTextSize
		 * @param mTextBold
		 */
		public TextStyleWrapper(Object mTextColor,int mTextSize,boolean mTextBold){
			
			this.mTextColor = mTextColor;
			this.mTextSize = mTextSize;
			this.mTextBold = mTextBold;
			
			//convertRealTextColor();
		}

		@SuppressWarnings("unused")
		private void convertRealTextColor(){
			
			if (mTextColor != null && mTextColor instanceof String) {
				
				if(!((String) mTextColor).startsWith(COLOR_PREFIX)){
					this.mTextColor = TEXT_COLOR_DEFAULT;
				}else{
					if (!isTextColorValid((String)mTextColor)) {
						this.mTextColor = TEXT_COLOR_DEFAULT;
					}else{
						this.mTextColor = getRealTextColor((String)mTextColor);
					}
				}
			}
			else if(mTextColor == null){
				this.mTextColor = TEXT_COLOR_DEFAULT;
			}
		}
		
		private String getRealTextColor(String textColor){
			
			String colorString = new String(COLOR_PREFIX);
			
			if (textColor.length() == 4) {
				for (int i = 1; i < textColor.length(); i++) {
					
					for (int j = 0; j < 2; j++) {
						colorString += String.valueOf(textColor.charAt(i));
					}	
				}
			}
			else if (textColor.length() == 7) {
				colorString = textColor;
			}
			else if (textColor.length() == 9) {
				colorString += textColor.substring(3);
			}
			
			return colorString;
		}
		
		private boolean isTextColorValid(String textColor){

			if (textColor != null && !StringUtil.isEmpty(textColor)) {
				
				for (int i = 0; i < textColor.toUpperCase().toCharArray().length; i++) {
					if(!isInHexChars(textColor.toUpperCase().toCharArray()[i])){
						return false;
					}
					continue;
				}
				return true;	
			}
			
			return false;
		}
		
		private boolean isInHexChars(char c){

			for (int i = 0; i < HexChars.length; i++) {
				if (c == HexChars[i]) {
					return true;
				}
				continue;
			}
			return false;
		}
		
		public Object getTextColor() {
			return mTextColor;
		}

		public void setTextColor(Object mTextColor) {
			this.mTextColor = mTextColor;
			//convertRealTextColor();
		}

		public int getTextSize() {
			return mTextSize;
		}

		public void setTextSize(int mTextSize) {
			this.mTextSize = mTextSize;
		}

		public boolean isTextBold() {
			return mTextBold;
		}

		public void setTextBold(boolean mTextBold) {
			this.mTextBold = mTextBold;
		}
	}
}
