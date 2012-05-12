//
//  Timeline.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/04/19.
//  Last updated on 12/05/12.
//

#import "Timeline.h"

@implementation Timeline

+ (OAMutableURLRequest *)getSelfTimeline
{
    return [self getSelfTimelineAtomic:0 withMaximumId:0 startingAtPage:0];
}

+ (OAMutableURLRequest *)getSelfTimelineAtomic:(long)sinceId
                                 withMaximumId:(long)maxId
                                startingAtPage:(int)page;
{
    return [self getTimeline:STATUSES_SELF_TIMELINE_URL withMinimumId:sinceId withMaximumId:maxId startingAtPage:page];
}

+ (OAMutableURLRequest *)getFriendsTimeline
{
    return [self getFriendsTimelineAtomic:0 withMaximumId:0 startingAtPage:0];
}

+ (OAMutableURLRequest *)getFriendsTimelineAtomic:(long)sinceId
                                    withMaximumId:(long)maxId
                                   startingAtPage:(int)page;
{
    return [self getTimeline:STATUSES_FRIENDS_TIMELINE_URL withMinimumId:sinceId withMaximumId:maxId startingAtPage:page];
}


+ (OAMutableURLRequest *)getTimeline:(NSString *)timelineURL
                       withMinimumId:(long)sinceId
                       withMaximumId:(long)maxId
                      startingAtPage:(int)page
{
    // XXX: better way to use memeber variables of `Weibo
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *consumerKey    = [defaults objectForKey:kConsumerKey];
    NSString *consumerSecret = [defaults objectForKey:kConsumerSecret];

    NSString *accessTokenKey    = [defaults objectForKey:kAccessTokenKey];
    NSString *accessTokenSecret = [defaults objectForKey:kAccessTokenSecret];
    
    OAConsumer *consumer = [[OAConsumer alloc] initWithKey:consumerKey secret:consumerSecret];
    OAToken    *token    = [[OAToken alloc] initWithKey:accessTokenKey secret:accessTokenSecret];

    [consumerKey       release];
    [consumerSecret    release];
    [accessTokenKey    release];
    [accessTokenSecret release];

    // FIXME: how to release 'consumer' and 'token'?
    return [self getTimeline:timelineURL
               withMinimumId:sinceId
               withMaximumId:maxId
              startingAtPage:page
                    consumer:consumer
                       token:token];
}

+ (OAMutableURLRequest *)getTimeline:(NSString *)timelineURL
                       withMinimumId:(long)sinceId
                       withMaximumId:(long)maxId
                      startingAtPage:(int)page
                            consumer:(OAConsumer *)consumer
                               token:(OAToken *)token
{
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];

    if (sinceId != 0) {
        [WeiboOAuth addParameterInDict:dict key:@"since_id" value:[NSNumber numberWithLong:sinceId]];
    }
    if (maxId != 0) {
        [WeiboOAuth addParameterInDict:dict key:@"max_id" value:[NSNumber numberWithLong:maxId]];
    }
    if (page != 0) {
        [WeiboOAuth addParameterInDict:dict key:@"page" value:[NSNumber numberWithInt:page]];
    }

    OAMutableURLRequest *request = [WeiboOAuth constructRequestWithURL:timelineURL
                                                              consumer:consumer
                                                                 token:token
                                                                 param:dict
                                                                method:nil];
    [request setHTTPMethod:@"GET"];

    [dict     release];
    return request;
}

@end