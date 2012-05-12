//
//  SelfTimelineViewController.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/05/07.
//  Last updated on 12/05/12.
//

#import <Foundation/NSJSONSerialization.h>
#import "TimelineViewController.h"

@interface SelfTimelineViewController : TimelineViewController
<TimelineViewControllerDelegate>
@end

@implementation SelfTimelineViewController

- (void)viewDidLoad
{
    [self parseTimelineTweets];
    [super viewDidLoad];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (void)parseTimelineTweets
{
    // retrieve self timeline tweets
    Weibo *weiboClient = [[Weibo alloc] init];
    [weiboClient retrieveSelfTimeline:[Timeline getSelfTimeline]];
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *selfTimelineTweets = [defaults objectForKey:kSelfTimeline];
    NSData *jsonSelfTimelineTweets = [selfTimelineTweets dataUsingEncoding:NSUTF8StringEncoding];

    NSError *error = nil;
    NSArray *weiboTweetsArray = [NSJSONSerialization JSONObjectWithData:jsonSelfTimelineTweets
                                                                options:NSJSONReadingMutableContainers
                                                                  error:&error];
    if (weiboTweetsArray == nil) {
        NSLog(@"Error parsing JSON: %@", error);
    }
    WeiboTweetStream *stream = [[WeiboTweetStream alloc] initWithTweetStream:weiboTweetsArray];

    self.weiboTweets = stream.tweets;
    [weiboClient release];
}

@end