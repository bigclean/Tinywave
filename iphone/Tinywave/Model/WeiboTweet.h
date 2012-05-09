//
//  WeiboTweet.h
//  Tinywave
//
//  Created by Bigclean Cheng on 12/04/19.
//

#import "WeiboTweetUser.h"

/*!
 @li Because data element `user and `text of Weibo Tweet conficts with NSObject
    internal member instances, so append 'weibo' prefix to original element, it
    now would be `weiboUser and `weiboText
 */
@interface WeiboTweet : NSObject
{
    NSString *createdAt;
    int       mid; // XXX: long type error occurs
    NSString *weiboText;

    NSString *source;
    bool      favorited;
    bool      truncated;

    NSString *inReplyToStatusId;
    NSString *inReplyToUserId;
    NSString *inReplyToScreenName;

    NSString *geo;
    WeiboTweetUser *weiboUser;

    WeiboTweet     *retweetedTweet;
}

@property (nonatomic, retain) NSString *createdAt;
@property (nonatomic, assign) int       mid;
@property (nonatomic, retain) NSString *weiboText;

@property (nonatomic, retain) NSString *source;
@property (nonatomic, assign) bool      favorited;
@property (nonatomic, assign) bool      truncated;

@property (nonatomic, retain) NSString *inReplyToStatusId;
@property (nonatomic, retain) NSString *inReplyToUserId;
@property (nonatomic, retain) NSString *inReplyToScreenName;

@property (nonatomic, retain) NSString *geo;
@property (nonatomic, retain) WeiboTweetUser *weiboUser;

@property (nonatomic, retain) WeiboTweet     *retweetedTweet;

- (id)initWithWeiboTweet:(NSDictionary *)singleWeiboTweet;
- (void)dealloc;
- (NSString *)description;

@end