//
//  TinywaveViewController.h
//  Tinywave
//
//  Created by Bigclean Cheng on 12/05/07.
//

#import <UIKit/UIKit.h>

#import "Weibo.h"

@interface TinywaveViewController : UIViewController
{
    WeiboOAuth *mWeiboOAuth;

    IBOutlet UIButton *authenticateButton;
    IBOutlet UIButton *tweetButton;
    IBOutlet UIButton *aboutButton;
}

@property (nonatomic, retain) WeiboOAuth *mWeiboOAuth;

@property (nonatomic, retain) UIButton *authenticateButton;
@property (nonatomic, retain) UIButton *tweetButton;
@property (nonatomic, retain) UIButton *aboutButton;

- (IBAction)authenticateButtonAction:(id)sender;
- (IBAction)aboutButtonAction:(id)sender;

- (void)dealloc;

@end