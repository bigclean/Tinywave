//
//  TinywaveViewController.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/05/07.
//

#import "TinywaveViewController.h"

@implementation TinywaveViewController

@synthesize mWeiboOAuth;
@synthesize authenticateButton, tweetButton, aboutButton;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
    }
    return self;
}

- (void)viewDidLoad
{
    self.mWeiboOAuth = [[WeiboOAuth alloc] init];
    [super viewDidLoad];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (void)dealloc
{
    [mWeiboOAuth release];
    [authenticateButton release];
    [tweetButton release];
    [aboutButton release];

    [super dealloc];
}

- (IBAction)authenticateButtonAction:(id)sender;
{
    OAMutableURLRequest *request = [self.mWeiboOAuth obtainRequestToken];
    [[UIApplication sharedApplication] openURL:[request URL]];
}

- (IBAction)aboutButtonAction:(id)sender
{
}
@end