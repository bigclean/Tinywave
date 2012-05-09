//
//  FriendsTimelineViewController.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/05/07.
//

#import "TimelineViewController.h"

#import "SBJson.h"

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
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];

    NSString *accessTokenKey    = [defaults objectForKey:kAccessTokenKey];
    NSString *accessTokenSecret = [defaults objectForKey:kAccessTokenSecret];

    Weibo *weiboClient = [[Weibo alloc] initWithToken:accessTokenKey secret:accessTokenSecret];
    [weiboClient retrieveFriendsTimeline:[Timeline getFriendsTimeline]];

    NSString *friendsTimelineTweets = [defaults objectForKey:kFriendsTimeline];

    SBJsonParser *timelineParser = [[SBJsonParser alloc] init];
    NSArray *weiboTweetsArray = [timelineParser objectWithString:friendsTimelineTweets error:nil];
    WeiboTweetStream *stream = [[WeiboTweetStream alloc] initWithTweetStream:weiboTweetsArray];

    self.weiboTweets = stream.tweets;
    //[weiboTweetsArray release];
}

@end