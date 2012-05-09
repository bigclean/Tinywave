//
//  WeiboTweet.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/04/19.
//

#import "WeiboTweet.h"

@implementation WeiboTweet

@synthesize createdAt, mid, weiboText;
@synthesize source, favorited, truncated;
@synthesize inReplyToStatusId, inReplyToUserId, inReplyToScreenName;
@synthesize geo, weiboUser;
@synthesize retweetedTweet;

- (id)initWithWeiboTweet:(NSDictionary *)singleWeiboTweet {
    if (self = [super init]) {
        self.createdAt = [[singleWeiboTweet objectForKey:@"created_at"] retain];
        self.mid = [[singleWeiboTweet objectForKey:@"mid"] intValue];
        self.weiboText = [[singleWeiboTweet objectForKey:@"text"] retain];

        self.source = [[singleWeiboTweet objectForKey:@"source"] retain];
        self.favorited = [[singleWeiboTweet objectForKey:@"favorited"] boolValue];
        self.truncated = [[singleWeiboTweet objectForKey:@"truncated"] boolValue];

        self.inReplyToStatusId = [[singleWeiboTweet objectForKey:@"in_reply_to_status_id"] retain];
        self.inReplyToUserId = [[singleWeiboTweet objectForKey:@"in_reply_to_user_id"] retain];
        self.inReplyToScreenName = [[singleWeiboTweet objectForKey:@"in_reply_to_screen_name"] retain];

        self.geo = [[singleWeiboTweet objectForKey:@"geo"] retain];
        self.weiboUser = [[WeiboTweetUser alloc] initWithWeiboTweetUser:[singleWeiboTweet objectForKey:@"user"]];

        if ([singleWeiboTweet objectForKey:@"retweeted_status"] != nil) {
            self.retweetedTweet = [self initWithWeiboTweet:[[singleWeiboTweet objectForKey:@"retweeted_status"] retain]];
        }
    }
    return self;
}

- (void)dealloc
{
    [createdAt release];
    [weiboText release];

    [source release];

    [inReplyToStatusId release];
    [inReplyToUserId release];
    [inReplyToScreenName release];

    [geo release];
    [weiboUser release];

    if (self.retweetedTweet != nil) {
        [retweetedTweet release];
    }

    [super dealloc];
}

- (NSString *)description
{
    NSMutableString *desc = [[NSMutableString alloc] init];
    [desc appendString:@"------------ Tweet ------------\n"];

    [desc appendFormat:@"Source is %@\ntext: %@\n", self.source, self.weiboText];
    [desc appendFormat:@"User : %@\n", self.weiboUser];

    if (self.retweetedTweet != nil) {
        // FIXME: failed to invoke its descroption in retweetTweet
        //[desc appendFormat:@"retweeted: \n %@",retweetedTweet];
        [desc appendFormat:@"retweeted: %@ \n", retweetedTweet.weiboText];
    }

    [desc appendString:@"------------ Tweet finished ------------\n"];

    return desc;
}

@end
