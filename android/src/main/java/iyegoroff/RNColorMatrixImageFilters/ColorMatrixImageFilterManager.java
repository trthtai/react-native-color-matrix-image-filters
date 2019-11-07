package iyegoroff.RNColorMatrixImageFilters;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.Map;

@ReactModule(name = ColorMatrixImageFilterManager.REACT_CLASS)
public class ColorMatrixImageFilterManager extends ReactViewManager {

  static final String REACT_CLASS = "CMIFColorMatrixImageFilter";

  private static final String PROP_MATRIX = "matrix";
  private static final String PROP_URI = "uri";

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public ColorMatrixImageFilter createViewInstance(ThemedReactContext reactContext) {
    return new ColorMatrixImageFilter(reactContext);
  }

  @Override
  public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
            .put(
                "onDone",
                MapBuilder.of(
                    "phasedRegistrationNames",
                    MapBuilder.of("bubbled", "onDone")))
                    .build();
    }

  @ReactProp(name = PROP_MATRIX)
  public void setMatrix(ColorMatrixImageFilter view, ReadableArray matrix) {
    view.setMatrix(matrix);
  }

  @ReactProp(name = PROP_URI)
  public void setMatrix(ColorMatrixImageFilter view, String uri) {
    view.setUri(uri);
  }
}
