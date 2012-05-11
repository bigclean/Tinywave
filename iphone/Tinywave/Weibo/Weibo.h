//
//  Weibo.h
//  Tinywave
//
//  Created by Bigclean Cheng on 12/04/19.
//

#import <Foundation/Foundation.h>
#import "WeiboOAuth.h"
#import "WeiboTweetStream.h"

#define kFriendsTimeline @"friendsTimeline"
#define kSelfTimeline    @"selfTimeline"

@interface Weibo : NSObject
{
    OAConsumer *weiboConsumer;
    OAToken    *weiboToken;
}

@property (nonatomic, retain) OAConsumer *weiboConsumer;
@property (nonatomic, retain) OAToken    *weiboToken;

#pragma mark -
#pragma mark Inital and dealloc
- (id)initWithToken:(NSString *)token secret:(NSString *)tokenSecret;
- (id)initWithKey:(NSString *)consumerKey secret:(NSString *)consumerSecret token:(NSString *)token secret:(NSString *)tokenSceret;
- (void)dealloc;

#pragma mark Timleine of Friends
- (void)retrieveFriendsTimeline:(OAMutableURLRequest *)request;
- (void)friendsTimelineTicket:(OAServiceTicket *)ticket didFinishWithData:(NSData *)data;
- (void)friendsTimelineTicket:(OAServiceTicket *)ticket didFailWithError:(NSError *)error;

#pragma mark Timleine of Self
- (void)retrieveSelfTimeline:(OAMutableURLRequest *)request;
- (void)selfTimelineTicket:(OAServiceTicket *)ticket didFinishWithData:(NSData *)data;
- (void)selfTimelineTicket:(OAServiceTicket *)ticket didFailWithError:(NSError *)error;

- (void)sendPostRequest:(NSString *)requestURL
                  param:(NSDictionary *)paramDict
     withFinishSelector:(SEL)finishSelector
       withFailSelector:(SEL)failSelector;

- (void)sendGetRequest:(NSString *)requestURL
                 param:(NSDictionary *)paramDict
    withFinishSelector:(SEL)finishSelector
      withFailSelector:(SEL)failSelector;

- (void)sendRequest:(NSString *)requestURL
              param:(NSDictionary *)paramDict
             method:(NSString *)requestMethod
 withFinishSelector:(SEL)finishSelector
   withFailSelector:(SEL)failSelector;

- (void)sendRequest:(OAMutableURLRequest *)request
 withFinishSelector:(SEL)finishSelector
   withFailSelector:(SEL)failSelector;

@end