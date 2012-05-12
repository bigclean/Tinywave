//
//  WeiboOAuth.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/04/19.
//  Last updated on 12/05/12
//

#import "WeiboOAuth.h"

@implementation WeiboOAuth

@synthesize mConsumerKey, mConsumerSecret;
@synthesize weiboConsumer, weiboToken, userID;

- (id)init
{
    if (self = [self initWithKey:CONSUMER_KEY secret:CONSUMER_SECRET]) {
    }
    return self;
}

- (id)initWithKey:(NSString *)consumerKey secret:(NSString *)consumerSecret
{
    self.mConsumerKey    = consumerKey;
    self.mConsumerSecret = consumerSecret;

    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:self.mConsumerKey    forKey:kConsumerKey];
    [defaults setObject:self.mConsumerSecret forKey:kConsumerSecret];
    [defaults synchronize];

    if (self = [super init]) {
        self.weiboConsumer = [[OAConsumer alloc] initWithKey:self.mConsumerKey
                                                      secret:self.mConsumerSecret];
        self.weiboToken = nil;
    }
    return self;
}

- (void)dealloc
{
    [mConsumerKey    release];
    [mConsumerSecret release];

    [weiboConsumer   release];
    [weiboToken      release];
    [userID          release];

    [super dealloc];
}

#pragma mark -
#pragma mark Request Token
- (OAMutableURLRequest *)obtainRequestToken
{
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    NSString *callbackURL = [NSString stringWithFormat:@"%@%@%@", OAUTH_CALLBACK_SCHEME, @"://", OAUTH_CALLBACK_HOST];
    [WeiboOAuth addParameterInDict:dict key:@"oauth_callback" value:callbackURL];
    OAMutableURLRequest *request = [self constructWeiboRequestWithURL:REQUEST_URL
                                                                param:dict
                                                               method:@"GET"];

    OADataFetcher *fetcher = [[OADataFetcher alloc] init];
    NSLog(@"Request unauthorized token url is %@", request.URL);

    [fetcher fetchDataWithRequest:request
                         delegate:self
                didFinishSelector:@selector(requestTokenTicket:didFinishWithData:)
                  didFailSelector:@selector(requestTokenTicket:didFailWithError:)];

    [callbackURL release];
    [dict release];

    // XXX: better solution to separate it from 'obtainRequestToken' function
    @try {
        dict = [[NSMutableDictionary alloc] init];
        [WeiboOAuth addParameterInDict:dict key:@"oauth_token" value:self.weiboToken.key];
        request = [self constructWeiboRequestWithURL:AUTHENTICATE_URL
                                               param:dict
                                              method:@"GET"];
        NSLog(@"Request token authorized by user url is %@", request.URL);

        [dict release];
        return request;
    }
    @catch (NSException *exception) {
        // todo
    }
}

- (void)requestTokenTicket:(OAServiceTicket *)ticket didFinishWithData:(NSData *)data
{
    if (ticket.didSucceed) {
        NSString *responseBody = [[NSString alloc] initWithData:data
                                                       encoding:NSUTF8StringEncoding];
        if (self.weiboToken != nil) {
            [self.weiboToken release];
            self.weiboToken = nil;
        }

        self.weiboToken = [[OAToken alloc] initWithHTTPResponseBody:responseBody];
        [responseBody release];
    }
}

- (void)requestTokenTicket:(OAServiceTicket *)ticket didFailWithError:(NSError *)error
{
    NSLog(@"not ok");
    NSLog(@"%@", error);
}

#pragma mark -
#pragma mark Access Token
- (void)obtainAccessToken:(NSString *)verifier
{
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    [WeiboOAuth addParameterInDict:dict key:@"oauth_token"    value:self.weiboToken.key];
    [WeiboOAuth addParameterInDict:dict key:@"oauth_verifier" value:verifier];
    OAMutableURLRequest *request = [self constructWeiboRequestWithURL:ACCESS_TOKEN_URL
                                                                param:dict
                                                               method:@"POST"];

    NSLog(@"Request access token request is %@", request.URL);

    OADataFetcher *fetcher = [[OADataFetcher alloc] init];
    [fetcher fetchDataWithRequest:request
                         delegate:self
                didFinishSelector:@selector(accessTokenTicket:didFinishWithData:)
                  didFailSelector:@selector(accessTokenTicket:didFailWithError:)];

    [dict release];
    NSLog(@"access key: %@, access key secret: %@", self.weiboToken.key, self.weiboToken.secret);
}

- (void)accessTokenTicket:(OAServiceTicket *)ticket didFinishWithData:(NSData *)data
{
    if (ticket.didSucceed) {
        NSString *responseBody = [[NSString alloc] initWithData:data
                                                       encoding:NSUTF8StringEncoding];
        if (self.weiboToken != nil) {
            [self.weiboToken release];
            self.weiboToken = nil;
        }
        self.weiboToken = [[OAToken alloc] initWithHTTPResponseBody:responseBody];
        self.userID     = [self getUserIDFromHTTPBody:responseBody];

        // store basic user specific information
        NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
        [defaults setObject:self.weiboToken.key    forKey:kAccessTokenKey];
        [defaults setObject:self.weiboToken.secret forKey:kAccessTokenSecret];
        [defaults setObject:self.userID            forKey:kUserID];
        [defaults synchronize];

        [responseBody release];
    }
}

- (void)accessTokenTicket:(OAServiceTicket *)ticket didFailWithError:(NSError *)error
{
    NSLog(@"%@", error);
}

/*!
 @li Response url holds such format:
            'tinywave://tinywave?oauth_token=b5be8e3ab580ee2cf07bbbfc1f73cbdf&oauth_verifier=655703'
 */
+ (NSString *)getVerifierFromURL:(NSURL *)url
{
    NSString  *queryParameter = [url query];
    NSUInteger verifierLiteralStartIndex  = [queryParameter rangeOfString:WeiboOAuthVerifierLiteral].location;
    NSUInteger verifierStartIndex = verifierLiteralStartIndex + [WeiboOAuthVerifierLiteral length];
    return [queryParameter substringFromIndex:verifierStartIndex];
}

/*!
 @li Response HTTP Body holds such format:
              'oauth_token=df71c43cf51a60cc4ccac9713fa83a3c&oauth_token_secret=fb279bafa2c8573fb2888407d1da0f51&user_id=1788726225'
 */
- (NSString *)getUserIDFromHTTPBody:(NSString *)body
{
    if (body == nil) {
        return nil;
    }

    // extract HTTP Body to arrays, each element holds the such format: 'user_id=1788726225'
    NSArray *queryParameterArray = [body componentsSeparatedByString:@"&"];
    if ([queryParameterArray count] < 1) {
        return nil;
    }

    for (NSString *queryParameter in queryParameterArray) {
        NSArray  *query = [queryParameter componentsSeparatedByString:@"="];
        NSString *key   = [query objectAtIndex:0];
        NSString *value = [query objectAtIndex:1];
        if ([key isEqualToString:@"user_id"]) {
            return value;
        }
    }
    return nil;
}

#pragma mark -
#pragma mark Utility
- (OAMutableURLRequest *)constructWeiboRequestWithURL:(NSString *)requestURL
                                                param:(NSDictionary *)paramDict
                                               method:(NSString *)requestMethod
{
    return [WeiboOAuth constructRequestWithURL:requestURL
                                      consumer:self.weiboConsumer
                                         token:self.weiboToken
                                         param:paramDict
                                        method:requestMethod];
}

+ (OAMutableURLRequest *)constructRequestWithURL:(NSString *)requestURL
                                        consumer:(OAConsumer *)consumer
                                           token:(OAToken *)token
                                           param:(NSDictionary *)paramDict
                                          method:(NSString *)requestMethod
{
    NSURL *url = [NSURL URLWithString:requestURL];
    OAMutableURLRequest *request = [[[OAMutableURLRequest alloc] initWithURL:url
                                                                    consumer:consumer
                                                                       token:token
                                                                       realm:nil
                                                           signatureProvider:nil] autorelease];
    if (requestMethod != nil) {
        [request setHTTPMethod:requestMethod];
    }

    NSMutableArray *weiboParams = [[NSMutableArray alloc] init];
    if (paramDict != nil) {
        for (NSString *key in [paramDict allKeys]) {
            OARequestParameter *extraParam = [[OARequestParameter alloc] initWithName:key
                                                                                value:[paramDict valueForKey:key]];
            [weiboParams addObject:extraParam];
            [extraParam release];
        }
    }
    [request setParameters:weiboParams];

    return request;
}

+ (void)addParameterInDict:(NSMutableDictionary *)paramDict
                       key:(NSString *)aKey
                     value:(id)aValue
{
    if (aValue != nil) {
        if ([aValue isKindOfClass:[NSString class]]) {
            [paramDict setObject:aValue forKey:aKey];
        } else {
            if ([aValue isKindOfClass:[NSNumber class]]) {
                [paramDict setObject:[aValue stringValue] forKey:aKey];
            }
        }
    }
}

@end