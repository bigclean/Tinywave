//
//  FriendsTimelineViewController.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/05/07.
//  Last updated on 12/05/12.
//

#import <Foundation/NSJSONSerialization.h>
#import "TimelineViewController.h"

@interface FriendsTimelineViewController : TimelineViewController
<TimelineViewControllerDelegate>
@end

@implementation FriendsTimelineViewController

- (void)viewDidLoad
{
    [self parseTimelineTweets];
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (void)parseTimelineTweets
{
    // retrieve friends timeline tweets
    Weibo *weiboClient = [[Weibo alloc] init];
    [weiboClient retrieveFriendsTimeline:[Timeline getFriendsTimeline]];

    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *friendsTimelineTweets = [defaults objectForKey:kFriendsTimeline];
    NSData *jsonFriendsTimelineTweets = [friendsTimelineTweets dataUsingEncoding:NSUTF8StringEncoding];
    
    // using `NSNSJSONSerialization instead of terrbile `SBJSON
    // see also: http://blog.devtang.com/blog/2012/05/05/do-not-use-sbjson/
    NSError *error = nil;
    NSArray *weiboTweetsArray = [NSJSONSerialization JSONObjectWithData:jsonFriendsTimelineTweets
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