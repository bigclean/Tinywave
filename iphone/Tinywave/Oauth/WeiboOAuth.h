//
//  WeiboOAuth.h
//  Tinywave
//
//  Created by Bigclean Cheng on 12/04/19.
//  Last updated on 12/05/12.
//

#import <Foundation/Foundation.h>

#import "WeiboOAuthKeys.h"
#import "WeiboTweetStream.h"

// see also: https://github.com/lgalabru/oauth-objc/
#import "OAuthConsumer.h"

#define kConsumerKey       @"consumerKey"
#define kConsumerSecret    @"consumerSecret"
#define kAccessTokenKey    @"accessToken"
#define kAccessTokenSecret @"accessTokenSecret"
#define kUserID            @"userID"

#define REQUEST_URL        @"http://api.t.sina.com.cn/oauth/request_token"
#define ACCESS_TOKEN_URL   @"http://api.t.sina.com.cn/oauth/access_token"
#define AUTHENTICATE_URL   @"http://api.t.sina.com.cn/oauth/authorize"

#define OAUTH_CALLBACK_SCHEME     @"tinywave"
#define OAUTH_CALLBACK_HOST       @"Tinywave"
#define WeiboOAuthVerifierLiteral @"oauth_verifier="

@interface WeiboOAuth : NSObject
{
    NSString   *mConsumerKey;
    NSString   *mConsumerSecret;

    OAConsumer *weiboConsumer;
    OAToken    *weiboToken;
    NSString   *userID;
}

@property (nonatomic, retain) NSString   *mConsumerKey;
@property (nonatomic, retain) NSString   *mConsumerSecret;

@property (nonatomic, retain) OAConsumer *weiboConsumer;
@property (nonatomic, retain) OAToken    *weiboToken;
@property (nonatomic, retain) NSString   *userID;

- (id)init;
- (id)initWithKey:(NSString *)consumerKey secret:(NSString *)consumerSecret;
- (void)dealloc;

#pragma mark -
#pragma mark Request Token
/*!
 * @function obtaninRequestToken
 * @brief Get unauthorized request token
 * @return Request URL would be authorized by user for request token
 * @li In standard OAuth-1.0 version, parameter 'oauth_callback' should be placeed
       as querying parameter in first step when consumer request unauthoroized request
       token, but Sina api points that it should be placed in second second when consumer
       request user to authorize.
       But what puzzles me is even place 'oauth_callback' in first step not follow
       Sina api, it just works.
 * @li More puzzled things is that no matter GET or POST you use, it always just works
       even don't do as API.
 * @seealso http://open.weibo.com/wiki/Oauth/request_token/
 */
- (OAMutableURLRequest *)obtainRequestToken;
- (void)requestTokenTicket:(OAServiceTicket *)ticket didFinishWithData:(NSData *)data;
- (void)requestTokenTicket:(OAServiceTicket *)ticket didFailWithError:(NSError *)error;

- (void)obtainAccessToken:(NSString *)verifier;
- (void)accessTokenTicket:(OAServiceTicket *)ticket didFinishWithData:(NSData *)data;
- (void)accessTokenTicket:(OAServiceTicket *)ticket didFailWithError:(NSError *)error;
+ (NSString *)getVerifierFromURL:(NSURL *)url;
- (NSString *)getUserIDFromHTTPBody:(NSString *)body;

- (OAMutableURLRequest *)constructWeiboRequestWithURL:(NSString *)requestURL
                                                param:(NSDictionary *)paramDict
                                               method:(NSString *)requestMethod;

+ (OAMutableURLRequest *)constructRequestWithURL:(NSString *)requestURL
                                        consumer:(OAConsumer *)consumer
                                           token:(OAToken *)token
                                           param:(NSDictionary *)paramDict
                                          method:(NSString *)requestMethod;

+ (void)addParameterInDict:(NSMutableDictionary *)paramDict
                       key:(NSString *)aKey
                     value:(id)aValue;

@end