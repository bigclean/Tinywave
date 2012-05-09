//
//  WeiboTweetUser.h
//  Tinywave
//
//  Created by Bigclean Cheng on 12/04/19.
//

#import <Foundation/Foundation.h>

@interface WeiboTweetUser : NSObject
{
    int       uid;
    NSString *screenName;
    NSString *name;

    int       province;
    int       city;
    NSString *location;

    NSString *description;
    NSString *weiboURL;
    NSString *profileImageUrl;
    NSString *domain;

    NSString *gender;
    int       followersCount;
    int       friendsCount;
    int       statusesCount;
    int       favouritesCount;

    NSString *createdAt;
    bool      following;
    bool      verified;
    bool      geoEnabled;
    bool      allowAllActMsg;
}

@property (nonatomic, assign) int       uid;
@property (nonatomic, retain) NSString *screenName;
@property (nonatomic, retain) NSString *name;

@property (nonatomic, assign) int       province;
@property (nonatomic, assign) int       city;
@property (nonatomic, retain) NSString *location;

@property (nonatomic, retain) NSString *description;
@property (nonatomic, retain) NSString *weiboURL;
@property (nonatomic, retain) NSString *profileImageUrl;
@property (nonatomic, retain) NSString *domain;

@property (nonatomic, retain) NSString *gender;
@property (nonatomic, assign) int       followersCount;
@property (nonatomic, assign) int       friendsCount;
@property (nonatomic, assign) int       statusesCount;
@property (nonatomic, assign) int       favouritesCount;

@property (nonatomic, retain) NSString *createdAt;
@property (nonatomic, assign) bool      following;
@property (nonatomic, assign) bool      verified;
@property (nonatomic, assign) bool      geoEnabled;
@property (nonatomic, assign) bool      allowAllActMsg;

- (id)initWithWeiboTweetUser:(NSDictionary *)singleWeiboTweetUser;
- (void)dealloc;
- (NSString *)description;

@end