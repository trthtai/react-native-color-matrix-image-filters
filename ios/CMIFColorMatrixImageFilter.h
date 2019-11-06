#import <React/UIView+React.h>
#import <React/RCTView.h>
#import <React/RCTComponent.h>

@interface CMIFColorMatrixImageFilter : RCTView

@property (nonatomic, strong) NSArray<NSNumber *> *matrix;
@property (nonatomic, copy) RCTBubblingEventBlock onDone;

@end
