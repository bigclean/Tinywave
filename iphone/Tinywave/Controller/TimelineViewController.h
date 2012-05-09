//
//  TimelineViewController.h
//  Tinywave
//
//  Created by Bigclean Cheng on 12/05/07.
//

#import <UIKit/UIKit.h>

#import "Timeline.h"
#import "Weibo.h"

#define FONT_SIZE 14.0f
#define CELL_CONTENT_WIDTH 320.0f
#define CELL_CONTENT_MARGIN 10.0f

@protocol TimelineViewControllerDelegate <NSObject>
- (void)parseTimelineTweets;
@end

@interface TimelineViewController : UITableViewController
{
    NSArray *weiboTweets;
    IBOutlet UITableViewCell *timelineTweetViewCell;
}

@property (nonatomic, retain) NSArray *weiboTweets;
@property (nonatomic, retain) UITableViewCell *timelineTweetViewCell;

- (void)dealloc;

@end