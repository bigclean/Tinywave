//
//  TimelineViewController.m
//  Tinywave
//
//  Created by Bigclean Cheng on 12/05/07.
//

#import "TimelineViewController.h"

@implementation TimelineViewController

@synthesize weiboTweets;
@synthesize timelineTweetViewCell;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];

    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;

    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)dealloc
{
    [weiboTweets release];
    [timelineTweetViewCell release];
    [super dealloc];
}

#pragma mark -
#pragma mark Table view data source

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.weiboTweets count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView
         cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell;
    UILabel *userNameLabel, *tweetTextLabel;

    static NSString *WeiboTweetsTableIdentifier = @"WeiboTweetsTableIdentifier";
    cell = [tableView dequeueReusableCellWithIdentifier:WeiboTweetsTableIdentifier];

    if (cell == nil) {
        cell = [[[UITableViewCell alloc]
                initWithStyle:UITableViewCellStyleSubtitle
                 reuseIdentifier:WeiboTweetsTableIdentifier] autorelease];
    }

    WeiboTweet *currentTweet = [weiboTweets objectAtIndex:[indexPath row]];

    userNameLabel = (UILabel *)[cell textLabel];
    userNameLabel.text = [[currentTweet weiboUser] screenName];

    tweetTextLabel = (UILabel *)[cell detailTextLabel];
    // appearancne settings
    tweetTextLabel.backgroundColor = [UIColor clearColor];
    tweetTextLabel.textColor = [UIColor darkGrayColor];

    tweetTextLabel.minimumFontSize = FONT_SIZE;
    tweetTextLabel.Font = [UIFont systemFontOfSize:FONT_SIZE];
    tweetTextLabel.lineBreakMode = UILineBreakModeWordWrap;
    tweetTextLabel.numberOfLines = 0;

    if ([currentTweet retweetedTweet] != nil) {
        NSString *originalText  = [[currentTweet retweetedTweet] weiboText];
        NSString *assembledText = [NSString stringWithFormat:@"%@ %@", @"转发：", originalText];
        tweetTextLabel.text     = assembledText;
    } else {
        tweetTextLabel.text = [currentTweet weiboText];
    }

    // todo: User profile image should be saved in local as cache.
    //       It would be CoreData or SQLite database.
    UIImageView *userProfileImageView = (UIImageView *)[cell imageView];
    [[UIApplication sharedApplication] setNetworkActivityIndicatorVisible:YES];
    NSURL *userProfileImageUrl = [NSURL URLWithString:[[tweet weiboUser] profileImageUrl]];
    NSData *userProfileImage = [[NSData alloc] initWithContentsOfURL:userProfileImageUrl];
    userProfileImageView.image = [[UIImage alloc] initWithData:userProfileImage];
    [[UIApplication sharedApplication] setNetworkActivityIndicatorVisible:NO];

    return cell;
}

/*!
 @seealso: http://www.cimgf.com/2009/09/23/uitableviewcell-dynamic-height/
 */
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath;
{
    NSString *text = [[weiboTweets objectAtIndex:[indexPath row]] weiboText];

    CGSize constraint = CGSizeMake(CELL_CONTENT_WIDTH - (CELL_CONTENT_MARGIN * 2), 20000.0f);
    CGSize size = [text sizeWithFont:[UIFont systemFontOfSize:FONT_SIZE] constrainedToSize:constraint lineBreakMode:UILineBreakModeWordWrap];
    CGFloat height = MAX(size.height, 44.0f);

    return height + (CELL_CONTENT_MARGIN * 2);
}

@end
