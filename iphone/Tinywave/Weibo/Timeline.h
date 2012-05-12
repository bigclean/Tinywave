//
//  Timeline.h
//  Tinywave
//
//  Created by Bigclean Cheng on 11/04/19.
//  Last updated on 12/05/12.
//

#import <Foundation/Foundation.h>
#import "WeiboOAuth.h"

#define STATUSES_BASE_TIMELINE_URL    @"http://api.t.sina.com.cn/statuses/"
#define STATUSES_SELF_TIMELINE_URL    @"http://api.t.sina.com.cn/statuses/user_timeline.json"
#define STATUSES_FRIENDS_TIMELINE_URL @"http://api.t.sina.com.cn/statuses/friends_timeline.json"

@interface Timeline : NSObject

+ (OAMutableURLRequest *)getSelfTimeline;
+ (OAMutableURLRequest *)getSelfTimelineAtomic:(long)sinceId
                                 withMaximumId:(long)maxId
                                startingAtPage:(int)page;

+ (OAMutableURLRequest *)getFriendsTimeline;
+ (OAMutableURLRequest *)getFriendsTimelineAtomic:(long)sinceId
                                    withMaximumId:(long)maxId
                                   startingAtPage:(int)page;

+ (OAMutableURLRequest *)getTimeline:(NSString *)timelineURL
                       withMinimumId:(long)sinceId
                       withMaximumId:(long)maxId
                      startingAtPage:(int)page;

+ (OAMutableURLRequest *)getTimeline:(NSString *)timelineURL
                       withMinimumId:(long)sinceId
                       withMaximumId:(long)maxId
                      startingAtPage:(int)page
                            consumer:(OAConsumer *)consumer
                               token:(OAToken *)token;

@end