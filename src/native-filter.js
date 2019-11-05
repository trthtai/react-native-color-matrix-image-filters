import React from "react";
import {
  requireNativeComponent,
  findNodeHandle,
  UIManager
} from "react-native";
import { defaultStyle, checkStyle } from "./style";

const ColorMatrixImageFilter = requireNativeComponent(
  "CMIFColorMatrixImageFilter"
);

export const NativeFilter = ({ style, ...restProps }) => {
  checkStyle(style);

  getImageData = callback => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.imageFilter),
      UIManager.getViewManagerConfig("CMIFColorMatrixImageFilter").Commands
        .getImageData,
      [callback]
    );
  };

  return (
    <ColorMatrixImageFilter
      ref={e => {
        this.imageFilter = e;
      }}
      style={[defaultStyle.container, style]}
      {...restProps}
    />
  );
};
