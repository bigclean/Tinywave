//
//  WeiboTweetStream.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/04/19.
//

#import "WeiboTweetStream.h"

@implementation WeiboTweetStream

@synthesize tweets;

/**
 @li weiboTweetsArray Array of element which is a dictionary
 */
- (id)initWithTweetStream:(NSArray *)weiboTweetsArray
{
    if (self = [super init]) {
        NSMutableArray *mutableTweets = [[NSMutableArray alloc] init];
        for(int i = 0; i < [weiboTweetsArray count]; i++) {
            WeiboTweet *tweet = [[WeiboTweet alloc] initWithWeiboTweet:[[weiboTweetsArray objectAtIndex:i] retain]];
            [mutableTweets addObject:tweet];
            [tweet release];
        }
        self.tweets = [[NSArray alloc] initWithArray:mutableTweets];
        [mutableTweets release];
    }
    return self;
}

- (void)dealloc
{
    [tweets release];
    [super dealloc];
}

- (NSString *)description
{
    return [super description];
}

@end