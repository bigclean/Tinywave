//
//  SelfTimelineViewController.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/05/07.
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
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];

    NSString *accessTokenKey    = [defaults objectForKey:kAccessTokenKey];
    NSString *accessTokenSecret = [defaults objectForKey:kAccessTokenSecret];

    Weibo *weiboClient = [[Weibo alloc] initWithToken:accessTokenKey secret:accessTokenSecret];
    [weiboClient retrieveSelfTimeline:[Timeline getSelfTimeline]];

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
    //[weiboTweetsArray release]; // why can't release it?
}

@end