//
//  AppDelegate.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/05/07.
//

#import "AppDelegate.h"

#import "TinywaveViewController.h"
#import "WeiboOAuth.h"

@implementation AppDelegate

@synthesize window = _window;
@synthesize rootViewController = __viewController;

- (void)dealloc
{
    [_window release];
    [__viewController release];
    [super dealloc];
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window.backgroundColor = [UIColor whiteColor];
    [self.window addSubview:self.rootViewController.view];
    [self.window makeKeyAndVisible];

    return YES;
}

- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation {
    TinywaveViewController *waveView = [self.rootViewController.viewControllers objectAtIndex:2];

    if ([waveView.mWeiboOAuth respondsToSelector:@selector(obtainAccessToken:)]) {
        [waveView.mWeiboOAuth obtainAccessToken:[WeiboOAuth getVerifierFromURL:url]];
    }

    return YES;
}

// todo needs to be tested
+ (AppDelegate *)App
{
    return (AppDelegate *)[[UIApplication sharedApplication] delegate];
}

@end