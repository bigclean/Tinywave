//
//  OAToken.m
//  OAuthConsumer
//
//  Created by Jon Crosby on 10/19/07.
//  Copyright 2007 Kaboomerang LLC. All rights reserved.
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy
//  of this software and associated documentation files (the "Software"), to deal
//  in the Software without restriction, including without limitation the rights
//  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//  copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//  THE SOFTWARE.


#import "OAToken.h"


@implementation OAToken

@synthesize key, secret;

#pragma mark init

- (id)init {
    [super init];
    self.key = @"";
    self.secret = @"";
    return self;
}

- (id) initWithCoder: (NSCoder *)coder {
	if (self = [super init]) {
		[self setKey:[coder decodeObjectForKey:@"key"]];
		[self setSecret:[coder decodeObjectForKey:@"secret"]];
	}
    return self;
}

- (void) encodeWithCoder: (NSCoder *)coder {
	[coder encodeObject:key forKey:@"key"];
	[coder encodeObject:secret forKey:@"secret"];
}

- (id)initWithKey:(NSString *)aKey secret:(NSString *)aSecret {
	[super init];
	self.key = aKey;
	self.secret = aSecret;
	return self;
}

- (id)initWithHTTPResponseBody:(NSString *)body {
    [super init];
    NSArray *pairs = [body componentsSeparatedByString:@"&"];
    
    for (NSString *pair in pairs) {
        NSArray *elements = [pair componentsSeparatedByString:@"="];
        if ([[elements objectAtIndex:0] isEqualToString:@"oauth_token"]) {
            self.key = [elements objectAtIndex:1];
        } else if ([[elements objectAtIndex:0] isEqualToString:@"oauth_token_secret"]) {
            self.secret = [elements objectAtIndex:1];
        }
    }
    
    return self;
}


- (id)initWithUserDefaultsUsingServiceProviderName:(NSString *)provider prefix:(NSString *)prefix
{
[super init];
NSString *theKey = [[NSUserDefaults standardUserDefaults] stringForKey:[NSString stringWithFormat:@"OAUTH_%@_%@_KEY", prefix, provider]];
NSString *theSecret = [[NSUserDefaults standardUserDefaults] stringForKey:[NSString stringWithFormat:@"OAUTH_%@_%@_SECRET", prefix, provider]];
if (theKey == NULL || theSecret == NULL)
	return(nil);
self.key = theKey;
self.secret = theSecret;
return(self);
}


- (int)storeInUserDefaultsWithServiceProviderName:(NSString *)provider prefix:(NSString *)prefix
{
[[NSUserDefaults standardUserDefaults] setObject:self.key forKey:[NSString stringWithFormat:@"OAUTH_%@_%@_KEY", prefix, provider]];
[[NSUserDefaults standardUserDefaults] setObject:self.secret forKey:[NSString stringWithFormat:@"OAUTH_%@_%@_SECRET", prefix, provider]];
[[NSUserDefaults standardUserDefaults] synchronize];
return(0);
}

@end
