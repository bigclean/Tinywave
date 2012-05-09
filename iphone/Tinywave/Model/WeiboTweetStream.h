//
//  WeiboTweetStream.h
//  Tinywave
//
//  Created by Bigclean Cheng on 12/04/19.
//

#import "WeiboTweet.h"

@interface WeiboTweetStream : NSObject
{
    NSArray *tweets;
}

@property (nonatomic, retain) NSArray *tweets;

- (id)initWithTweetStream:(NSArray *)weiboTweetsArray;
- (void)dealloc;
- (NSString *)description;

@end