package iyegoroff.RNColorMatrixImageFilters;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RootDrawable;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;

import java.io.ByteArrayOutputStream;

public class ColorMatrixImageFilter extends ReactViewGroup {

  private ColorMatrixColorFilter mFilter = new ColorMatrixColorFilter(new ColorMatrix());
  private String uri = new String();

  public ColorMatrixImageFilter(Context context) {
    super(context);
  }

  public void setMatrix(ReadableArray matrix) {
    float[] m = new float[matrix.size()];

    for (int i = 0; i < m.length; i++) {
      m[i] = (float) matrix.getDouble(i);
    }

    mFilter = new ColorMatrixColorFilter(m);

    invalidate();
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  @Override
  public void draw(Canvas canvas) {
    for (int i = 0; i < this.getChildCount(); i++) {
      View child = this.getChildAt(i);

      while (!(child instanceof ImageView) && (child instanceof ViewGroup)) {
        child = ((ViewGroup) child).getChildAt(0);
      }

      if (child instanceof ImageView) {
        ((ImageView) child).setColorFilter(mFilter);

        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(this.uri))
                .setProgressiveRenderingEnabled(true)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        final DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);

        dataSource.subscribe(new BaseBitmapDataSubscriber() {

          @Override
          public void onNewResultImpl(@Nullable Bitmap bitmap) {
            if (dataSource.isFinished() && bitmap != null){
              Bitmap bmp = Bitmap.createBitmap(bitmap);
              Paint paint = new Paint();
              paint.setColorFilter(mFilter);

              Canvas canvas = new Canvas(bmp);
              canvas.drawBitmap(bmp, 0, 0, paint);

              ByteArrayOutputStream bos = new ByteArrayOutputStream();
              bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
              byte[] bb = bos.toByteArray();
              String encodedString = Base64.encodeToString(bb, 0);

              WritableMap event = Arguments.createMap();
              event.putString("data", encodedString);
              ReactContext reactContext = (ReactContext)getContext();
              reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                      getId(),
                      "onDone",
                      event);

              dataSource.close();
            }
          }

          @Override
          public void onFailureImpl(DataSource dataSource) {
            if (dataSource != null) {
              dataSource.close();
            }
          }
        }, CallerThreadExecutor.getInstance());

        break;
      }
    }

    super.draw(canvas);
  }
}
