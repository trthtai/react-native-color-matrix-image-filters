#import <React/RCTBridge.h>
#import <React/RCTUIManager.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTBridgeModule.h>

#import "CMIFColorMatrixImageFilterManager.h"
#import "CMIFColorMatrixImageFilter.h"

@implementation CMIFColorMatrixImageFilterManager

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

- (UIView *)view
{
  return [[CMIFColorMatrixImageFilter alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(matrix, NSArray<NSNumber *>);
RCT_EXPORT_VIEW_PROPERTY(onDone, RCTBubblingEventBlock);

@end
