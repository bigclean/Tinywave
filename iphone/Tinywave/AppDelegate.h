//
//  AppDelegate.h
//  Tinywave
//
//  Created by Bigclean Cheng on 12/05/07.
//

#import <UIKit/UIKit.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate>
{
    IBOutlet UIWindow *window;
    IBOutlet UITabBarController *rootViewController;
}

@property (nonatomic, retain) UIWindow *window;
@property (nonatomic, retain) UITabBarController *rootViewController;

@end