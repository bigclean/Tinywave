//
//  WeiboTweetUser.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/04/19.
//

#import "WeiboTweetUser.h"

@implementation WeiboTweetUser

@synthesize uid, screenName, name;
@synthesize province, city, location;
@synthesize description, weiboURL, profileImageUrl, domain;
@synthesize gender, followersCount, friendsCount, statusesCount, favouritesCount;
@synthesize createdAt, following, verified, geoEnabled, allowAllActMsg;

- (id)initWithWeiboTweetUser:(NSDictionary *)singleWeiboTweetUser {
    if (self = [super init]) {
        self.uid = [[singleWeiboTweetUser objectForKey:@"id"] intValue];
        self.screenName = [[singleWeiboTweetUser objectForKey:@"screen_name"] retain];
        self.name = [[singleWeiboTweetUser objectForKey:@"name"] retain];

        self.province = [[singleWeiboTweetUser objectForKey:@"province"] intValue];
        self.city = [[singleWeiboTweetUser objectForKey:@"city"] intValue];
        self.location = [[singleWeiboTweetUser objectForKey:@"location"] retain];

        self.description = [[singleWeiboTweetUser objectForKey:@"description"] retain];
        self.weiboURL = [[singleWeiboTweetUser objectForKey:@"url"] retain];
        self.profileImageUrl = [[singleWeiboTweetUser objectForKey:@"profile_image_url"] retain];
        self.domain = [[singleWeiboTweetUser objectForKey:@"domain"] retain];

        self.gender = [[singleWeiboTweetUser objectForKey:@"gender"] retain];
        self.followersCount = [[singleWeiboTweetUser objectForKey:@"followers_count"] intValue];
        self.friendsCount = [[singleWeiboTweetUser objectForKey:@"friends_count"] intValue];
        self.statusesCount = [[singleWeiboTweetUser objectForKey:@"statuses_count"] intValue];
        self.favouritesCount = [[singleWeiboTweetUser objectForKey:@"favourites_count"] intValue];

        self.createdAt = [[singleWeiboTweetUser objectForKey:@"created_at"] retain];
        self.following = [[singleWeiboTweetUser objectForKey:@"following"] boolValue];
        self.verified = [[singleWeiboTweetUser objectForKey:@"verified"] boolValue];
        self.geoEnabled = [[singleWeiboTweetUser objectForKey:@"geo_enabled"] boolValue];
        self.allowAllActMsg = [[singleWeiboTweetUser objectForKey:@"allow_all_act_msg"] boolValue];
    }
    return self;
}

- (void)dealloc
{
    [screenName release];
    [name release];

    [location release];

    [description release];
    [weiboURL release];
    [profileImageUrl release];
    [domain release];

    [gender release];

    [createdAt release];

    [super dealloc];
}

- (NSString *)description
{
    NSMutableString *desc = [[NSMutableString alloc] init];
    [desc appendString:@"------------ Weibo User ------------\n"];

    [desc appendFormat:@"User name: %@ \nFollowers Count: %d\n",
     self.screenName, self.followersCount];

    [desc appendString:@"------------ Weibo User fin ------------\n"];

    return desc;
}

@end
