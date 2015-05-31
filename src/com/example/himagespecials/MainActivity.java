package com.example.himagespecials;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.view.Display;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {
private ImageView iv_src;
private ImageView iv_copy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	//加载原图，这个事只读的	
		Bitmap bmSrc = fileImageScale("bbbsmall.jpg");
	//1.创建一个没有任何内容的bitmap对象，宽高与原图一致,就想一张白纸
		Bitmap bmCopy=Bitmap.createBitmap(bmSrc.getWidth(), bmSrc.getHeight(),bmSrc.getConfig());
	//2.创建画笔
		Paint paint=new Paint();
		//3.创建一个画板，把画纸铺在画板上
	Canvas canvas=new Canvas(bmCopy);
	//4.开始作画
	Matrix mt=new Matrix();
	//旋转效果
	//mt.setRotate(45, bmCopy.getWidth()/2, bmCopy.getHeight()/2);
	//平移效果
	//mt.setTranslate(50, 50);
	//缩放效果
	//mt.setScale(0.5f, 0.5f);
	//镜面效果,参数就是乘以宽高的多少倍
	//mt.setScale(-1f, 1f);
	//如果要设置多种效果，一种效果之后就要用post
	//mt.postTranslate(bmCopy.getWidth(), 0);
	
	//倒影效果
	mt.setScale(1f, -1f);
	//如果要设置多种效果，一种效果之后就要用post
	mt.postTranslate(0, bmCopy.getHeight());
	
	
	canvas.drawBitmap(bmSrc,mt, paint);
	iv_copy=(ImageView) findViewById(R.id.iv_copy);
	iv_src=(ImageView) findViewById(R.id.iv_src);
	iv_copy.setImageBitmap(bmCopy);
	iv_src.setImageBitmap(bmSrc);
	
	}
	
	
	public Bitmap fileImageScale(String picName){
		File sdFile=Environment.getExternalStorageDirectory();
		File file=new File(sdFile,picName);
		System.out.println(file.toString());
		Options opt = new Options();

		// 允许调用者去查询图片的属性,但是不会为图片的像素分配内存
		opt.inJustDecodeBounds = true;

		int imageWidth = opt.outWidth;
		int imageHeight = opt.outHeight;

		Display display = getWindowManager().getDefaultDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		// 缩放比例
		int scaleWidth = imageWidth / screenWidth;
		int scaleHeight = imageHeight / screenHeight;
		int scale = 1;
		// 如果宽高比例不一致,>0是因为如果图片小于屏幕，就不用缩放了。
		if (scaleWidth >= scaleHeight && scaleWidth > 0) {
			scale = scaleWidth;
		} else if (scaleWidth < scaleHeight && scaleHeight > 0) {
			scale = scaleHeight;
		}
		opt.inSampleSize = scale;
		opt.inJustDecodeBounds = false;
		
		Bitmap bm = BitmapFactory.decodeFile(file.toString());
		return bm;
	}

}
