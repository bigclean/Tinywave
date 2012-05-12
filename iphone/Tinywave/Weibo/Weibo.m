//
//  Weibo.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/04/19.
//  Last updated on 12/05/12.
//

#import "Weibo.h"

@implementation Weibo

@synthesize weiboConsumer, weiboToken;

#pragma mark -
#pragma mark Inital and dealloc

- (id)init
{
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];

    NSString *accessTokenKey    = [defaults objectForKey:kAccessTokenKey];
    NSString *accessTokenSecret = [defaults objectForKey:kAccessTokenSecret];
    
    if (self  = [self initWithToken:accessTokenKey secret:accessTokenSecret]) {
    }
    return self;
}

- (id)initWithToken:(NSString *)token secret:(NSString *)tokenSecret
{
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];

    NSString *consumerKey    = [defaults objectForKey:kConsumerKey];
    NSString *consumerSecret = [defaults objectForKey:kConsumerSecret];
    
    if (self = [self initWithKey:consumerKey secret:consumerSecret token:token secret:tokenSecret]) {
    }
    return self;
}

- (id)initWithKey:(NSString *)consumerKey secret:(NSString *)consumerSecret
{
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    
    NSString *accessTokenKey    = [defaults objectForKey:kAccessTokenKey];
    NSString *accessTokenSecret = [defaults objectForKey:kAccessTokenSecret];
    
    if (self = [self initWithKey:consumerKey secret:consumerSecret token:accessTokenKey secret:accessTokenSecret]) {
    }
    return self;
}

- (id)initWithKey:(NSString *)consumerKey secret:(NSString *)consumerSecret token:(NSString *)token secret:(NSString *)tokenSceret
{
    if (self = [super init]) {
        self.weiboConsumer = [[OAConsumer alloc] initWithKey:consumerKey
                                                      secret:consumerSecret];
        self.weiboToken = [[OAToken alloc] initWithKey:token secret:tokenSceret];
    }
    return self;
}

- (void)dealloc
{
    [weiboConsumer release];
    [weiboToken    release];
    [super dealloc];
}

#pragma mark -
#pragma mark Timleine of Friends

- (void)retrieveFriendsTimeline:(OAMutableURLRequest *)request
{
    [self sendRequest:request
   withFinishSelector:@selector(friendsTimelineTicket:didFinishWithData:)
     withFailSelector:@selector(friendsTimelineTicket:didFailWithError:)];
}

- (void)friendsTimelineTicket:(OAServiceTicket *)ticket didFinishWithData:(NSData *)data
{
    if (ticket.didSucceed) {
        NSString *responseBody = [[NSString alloc] initWithData:data
                                                       encoding:NSUTF8StringEncoding];

        // todo: better solution to transfer data via in its space
        NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
        [defaults setObject:responseBody forKey:kFriendsTimeline];
        [defaults synchronize];

        [responseBody release];
    }
}

- (void)friendsTimelineTicket:(OAServiceTicket *)ticket didFailWithError:(NSError *)error
{
    NSLog(@"Failed to retrieve friends timeline.");
    NSLog(@"%@", error);
}

#pragma mark -
#pragma mark Timleine of Self

- (void)retrieveSelfTimeline:(OAMutableURLRequest *)request
{
    [self sendRequest:request
   withFinishSelector:@selector(selfTimelineTicket:didFinishWithData:)
     withFailSelector:@selector(selfTimelineTicket:didFailWithError:)];
}

- (void)selfTimelineTicket:(OAServiceTicket *)ticket didFinishWithData:(NSData *)data
{
    if (ticket.didSucceed) {
        NSString *responseBody = [[NSString alloc] initWithData:data
                                                       encoding:NSUTF8StringEncoding];
        // todo: better solution to transfer data via in its space
        NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
        [defaults setObject:responseBody forKey:kSelfTimeline];
        [defaults synchronize];

        [responseBody release];
    }
}

- (void)selfTimelineTicket:(OAServiceTicket *)ticket didFailWithError:(NSError *)error
{
    NSLog(@"Failed to retrieve self timeline.");
    NSLog(@"%@", error);
}

#pragma mark -
#pragma mark Request Literal

- (void)sendPostRequest:(NSString *)requestURL
                  param:(NSDictionary *)paramDict
     withFinishSelector:(SEL)finishSelector
       withFailSelector:(SEL)failSelector
{
    [self sendRequest:requestURL param:paramDict method:@"POST" withFinishSelector:finishSelector withFailSelector:failSelector];
}

- (void)sendGetRequest:(NSString *)requestURL
                 param:(NSDictionary *)paramDict
    withFinishSelector:(SEL)finishSelector
      withFailSelector:(SEL)failSelector
{
    [self sendRequest:requestURL param:paramDict method:@"GET" withFinishSelector:finishSelector withFailSelector:failSelector];
}

- (void)sendRequest:(NSString *)requestURL
              param:(NSDictionary *)paramDict
             method:(NSString *)requestMethod
 withFinishSelector:(SEL)finishSelector
   withFailSelector:(SEL)failSelector
{
    OAMutableURLRequest *request = [WeiboOAuth constructRequestWithURL:requestURL
                                                                consumer:self.weiboConsumer
                                                                   token:self.weiboToken
                                                                   param:paramDict
                                                                  method:requestMethod];
    [self sendRequest:request withFinishSelector:finishSelector withFailSelector:failSelector];
}

- (void)sendRequest:(OAMutableURLRequest *)request
 withFinishSelector:(SEL)finishSelector
   withFailSelector:(SEL)failSelector
{
    OADataFetcher *fetcher = [[OADataFetcher alloc] init];
    [fetcher fetchDataWithRequest:request
                         delegate:self
                didFinishSelector:finishSelector
                  didFailSelector:failSelector];
}

@end