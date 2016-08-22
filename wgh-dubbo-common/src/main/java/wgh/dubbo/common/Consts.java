package wgh.dubbo.common;


public class Consts {

	/**状态：0否，1是*/
    public static final int STATE_FALSE = 0;
    public static final int STATE_TRUE = 1;
    
    /** 注册账号类型：1手机，2邮箱,3微信*/
    public static final int ACCOUNT_TYPE_MOBILE = 1;
    public static final int ACCOUNT_TYPE_EMAIL = 2;
    public static final int ACCOUNT_TYPE_WEIXIN = 3;
    
    /** API结果：0成功，-1失败,100用户没有登录*/
    public static final String API_CODE_SUCCESS = "0";
    public static final String API_CODE_ERROR = "-1";
    public static final String API_CODE_NOLGONIN = "100";
    
    /** 产品状态：-1删除，0待上架，1上架*/
    public static final int PRODUCT_STATE_DELETE = -1;
    public static final int PRODUCT_STATE_STAY = 0;
    public static final int PRODUCT_STATE_SHELVE = 1;
    
    /** 产品图片分类：1详情，2介绍*/
    public static final int PRODUCT_PIC_TYPE_DETAILS = 1;
    public static final int PRODUCT_PIC_TYPE_INTRODUCE = 2;
    
    /**订单状态：-1删除，0待付款，1已付款,2付款失败,3已完成*/
    public static final int ORDER_STATE_DELETE = -1;
    public static final int ORDER_STATE_NO_PAY = 0;
    public static final int ORDER_STATE_PAY = 1;
    public static final int ORDER_STATE_TAIL = 2;
    public static final int ORDER_STATE_FINISH = 3;
    
    /** 产品购买类型：1正常购买，2抢购*/
    public static final int PRODUCT_BUY_TYPE_NOMAL = 1;
    public static final int PRODUCT_BUY_TYPE_SECKILL = 2;
    
    /** 理财产品回报方式：1消费回报，2利息回报*/
    public static final int FINANCE_RETURN_TYPE_CONSUME = 1;
    public static final int FINANCE_RETURN_TYPE_INTEREST = 2;
    
    /** 产品栏目类型：1全球疯抢（抢购），2疯爆专场*/
    public static final int PRODUCT_COLUMN_TYPE_SECKILL = 1;
    public static final int PRODUCT_COLUMN_TYPE_STORM = 2;
    
    /** 用户登录状态：-1异常退出，0正常退出，1登录态*/
    public static final int USER_STATE_LOGOUT_ERROR = -1;
    public static final int USER_STATE_LOGOUT = 0;
    public static final int USER_STATE_LOGIN = 1;
    
    /** 验证码类型：1注册，2修改密码*/
    public static final int VERIFY_TYPE_RIGISTER = 1;
    public static final int VERIFY_TYPE_PASSWORD = 2;
    
    /** 优惠券类型：1现金红包，2满减红包*/
    public static final int COUPONS_TYPE_CASH = 1;
    public static final int COUPONS_TYPE_FULL_REDUCTION = 2;
    
    /** 优惠券查询类型：1我的优惠券，2订单可用优惠券*/
    public static final int COUPONS_SEARCH_TYPE_MINE = 1;
    public static final int COUPONS_SEARCH_TYPE_ORDER = 2;
    
    /** 优惠券领取类型：1生日赠送，2领取分享，3领取优惠码，4通过分享*/
    public static final int COUPONS_EXTRACT_TYPE_BIRTH = 1;
    public static final int COUPONS_EXTRACT_TYPE_EXTRACT_SHARE = 2;
    public static final int COUPONS_EXTRACT_TYPE_CODE = 3;
    public static final int COUPONS_EXTRACT_TYPE_SHARE = 4;
    
    /**代金券状态：0待激活，1有效，2已使用，3过期*/
    public static final int COUPONS_STATE_NOT_ACTIVE = 0;
    public static final int COUPONS_STATE_ACTIVE = 1;
    public static final int COUPONS_STATE_USED = 2;
    public static final int COUPONS_STATE_OVERDUE = 3;
    
    /**问题反馈状态：0待处理，1受理中，2处理完*/
    public static final int FEEDBACK_STATE_PENDING = 0;
    public static final int FEEDBACK_STATE_INHAND = 1;
    public static final int FEEDBACK_STATE_HANDLE = 2;
    
    /**banner类型：1产品详情，2分类产品，3广告链接，4专题产品，5疯神榜*/
    public static final int BANNER_TYPE_PRODUCT_DETAILS = 1;
    public static final int BANNER_TYPE_PRODUCT_TYPE = 2;
    public static final int BANNER_TYPE_AD = 3;
    public static final int BANNER_TYPE_TOPIC = 4;
    public static final int BANNER_TYPE_FMB_RANK = 5;
    
    /** 记账类型：1支出，2收入,3投资理财*/
    public static final int BILL_TYPE_EXPENSE = 1;
    public static final int BILL_TYPE_INCOME = 2;
    public static final int BILL_TYPE_INVEST = 3;

    /** 消息类型：1系统消息，2交易消息 */
    public static final int PUSH_MSG_SYSTEM = 1;
    public static final int PUSH_MSG_CHARGE = 2;

    /** 消息推送方式：1广播，2个性化（默认）*/
    public static final int PUSH_BROADCAST = 1;         // 广播
    public static final int PUSH_CUSTOMIZEDCAST = 2;    // 个性化播

    /** 消息状态：0未读，1已查看，2删除 */
    public static final int PUSH_MSG_NOT_READ = 0;
    public static final int PUSH_MSG_READED = 1;
    public static final int PUSH_MSG_DELETED = 2;
    
    /**支付方式：1支付宝，2微信，3易宝支付*/
    public static final int PAY_TYPE_ALIPAY = 1;
    public static final int PAY_TYPE_WEIXIN = 2;
    public static final int PAY_TYPE_YIBAO = 3;
    
    /**记账余额类型：1不可转余额（没到连续天数），2可转余额，3已转余额，4红包清零（没有连续记账）*/
    public static final int BILL_BALANCE_NO_TRANSTER = 1;
    public static final int BILL_BALANCE_TYPE_CAN_TRANSTER = 2;
    public static final int BILL_BALANCE_TYPE_FINISH_TRANSTER = 3;
    public static final int BILL_BALANCE_TYPE_TO_CLEAR = 4;
    
    /** 余额记录类型：1充值，2订单消费，3（21天）记账红包，4记账获息*/
    public static final int BALANCE_TYPE_DEPOSIT = 1;
    public static final int BALANCE_TYPE_SONSUME = 2;
    public static final int BALANCE_TYPE_BILL_21 = 3;
    public static final int BALANCE_TYPE_BILL_INTEREST = 4;
    
    /** 账单获息来源：1发布，2点赞*/
    public static final int BILL_INTEREST_ISSUE = 1;
    public static final int BILL_INTEREST_PRAISE = 2;
    
    /**订单产品状态：0待付款，1已付款，2到期*/
    public static final int ORDER_PRODUCT_NO_PAY = 0;
    public static final int ORDER_PRODUCT_STATE_PAY = 1;
    public static final int ORDER_PRODUCT_STATE_DUE = 2;

    /** 排序方式：1向前排序，2向后排序 **/
    public static final int SORT_WAY_FRONT = 1;
    public static final int SORT_WAY_BACK = 2;
    
    /** 匿名类别：1昵称，2头像*/
    public static final int NAMELESS_TYPE_NAME = 1;
    public static final int NAMELESS_TYPE_HEADPIC = 2;
    
    /** 关注类型：1互相关注，2我的关注，3关注我的*/
    public static final int NOTICE_ZERO = 0;
    public static final int NOTICE_BOTH = 1;
    public static final int NOTICE_MINE = 2;
    public static final int NOTICE_OTHER = 3;
    
    /**投保单状态：0待出单，1出单成功，2投保失败*/
    public static final int FINANCE_POLICY_STAY = 0;
    public static final int FINANCE_POLICY_SUCCEED = 1;
    public static final int FINANCE_POLICY_TAIL = 2;
    
    public static final String MARK_IMAGE_HEIGHT = "imageHeight";
    public static final String MARK_IMAGE_URL = "imageUrl";
    public static final String MARK_IMAGE_WIDTH = "imageWidth";
    public static final String INSURE_ORDER_CACHE_KEY = "insureCacheKey_";
    
    public static final String DEFAULT_BILL_PHOTO = "http://7xoor9.com1.z0.glb.clouddn.com/default_bill_photo.png";
    
    /**是否已认证：0没认证，1已认证*/
    public static final String USER_AUTH_NOT = "0";
    public static final String USER_AUTH_OK = "1";
    
    /**保险单状态：0待处理，1领取成功，2领取失败，3待支付，4支付成功，5支付失败，6待出单，7出单成功，8出单失败，9其他*/
    public static final int INSURE_PENDING = 0;
	public static final int INSURE_GET_SUCCEED = 1;
    public static final int INSURE_GET_TAIL = 2;
    public static final int INSURE_NOT_PAY = 3;
    public static final int INSURE_PAY_SUCCEED = 4;
    public static final int INSURE_PAY_TAIL = 5;
    public static final int INSURE_ISSUING_STAY = 6;
    public static final int INSURE_ISSUING_SUCCEED = 7;
    public static final int INSURE_ISSUING_TAIL = 8;
    public static final int INSURE_RESTS = 9;
    
    /**
     * 帖子状态：
     * -1审核不通过（只有发帖人在自己个人中心可以看到） ，后台管理功能
     * 0用户删除（所有地方不可见），前端功能
     * 1正常（正常推的地方都可以看到 比如首页、分类聚合页），帖子默认状态
     * 2隐藏（只在她的个人中心展示 自己和别人来个人中心可以看到），后台管理功能
     */
    public static final int BILL_NO_PASS_AUDIT = -1;
    public static final int BILL_USER_DELETE = 0;
    public static final int BILL_NORMAL = 1;
    public static final int BILL_CONCEAL = 2;
    
    /**支付密码前缀*/
    public static final String USER_PAY_PASSWORD_PRE = "./,;@0fmb";
    
    /**
     * 查看帖子权限帖子
     */
    public static final int BILL_PERMISSIOM_NOENTRY=-1;//未加入
    public static final int BILL_PERMISSIOM_VIEWER=0;//访问者
    public static final int BILL_PERMISSIOM_MANAGER=1;//圈主
    public static final int BILL_PERMISSIOM_OWNER=2;//自己
    
    /**
     * 帖子状态，-1-屏蔽，1-正常，2-隐藏
     */
    public static final int BILL_STATE_FORBID=-1;//屏蔽
    public static final int BILL_STATE_NORMAL=1;//正常
    public static final int BILL_STATE_HIDDEN=2;//隐藏
    
    /**
     * 关注类型，1圈子，2活动，3其他
     */
    public static final int RELATION_CIRCLE=1;//圈子
    public static final int RELATION_ACTIVITY=2;//活动
    public static final int RELATION_OTHER=3;//其他
    
    /**
     * 用户与圈子关系：0普通成员，1管理员，2关注
     */
    public static final String USER_RELATION_MEMBER="0";//普通成员
    public static final String USER_RELATION_MANAGER="1";//管理员
    public static final String USER_RELATION_FOCUS="2";//关注
    
    /**
     *活动状态：
     * 0未开始
     * 1报名中
     * 2进行中
     * 3结束
     */
    public static final int activity_state_not_start=0;
    public static final int activity_state_applying=1;
    public static final int activity_state_underway=2;
    public static final int activity_state_over=3;
    
    
    public static final String PUSH_MSG_TITLE="疯蜜";
    /**
     * 极光推送msgType（消息类型）
     * 消息分类：0-h5打开，1系统消息，2交易消息，
     * 3-新评论,4-新赞,5-关注用户,6-由管理后台发送给用户的圈子通知,
     * 7-由管理后台发送给用户的活动通知,8-由管理后台发送给用户的屏蔽通知
     * 9-加入圈子,10-退出圈子,11-圈子加入结果,12-帖子处理,13-活动报名通知
     */
    public static final int JPUSH_MSG_TYPE_H5_OPEN=0;//h5打开网页
    public static final int JPUSH_MSG_TYPE_SYSTEM=1;//由管理后台发送给用户的其他通知

    public static final int JPUSH_MSG_TYPE_TRADE=2;//交易消息
    
    public static final int JPUSH_MSG_TYPE_NEW_COMMENT=3;//新评论
    public static final int JPUSH_MSG_TYPE_NEW_PRIASE=4;//新赞
    public static final int JPUSH_MSG_TYPE_FOCUS_USER=5;//关注用户
    public static final int JPUSH_MSG_TYPE_SYSTEM_CIRCLE_MSG=6;//由管理后台发送给用户的圈子通知

    public static final int JPUSH_MSG_TYPE_SYSTEM_ACTIVITY_MSG=7;//由管理后台发送给用户的活动通知

    public static final int JPUSH_MSG_TYPE_SYSTEM_SHIELD_USER=8;//由管理后台发送给用户的屏蔽通知

    
    public static final int JPUSH_MSG_TYPE_CIRCLE_ENTRY=9;//加入圈子
    public static final int JPUSH_MSG_TYPE_CIRCLE_EXITS=10;//退出圈子
 
    
    public static final int JPUSH_MSG_TYPE_CIRCLE_ENTRY_RESULT=11;//圈子加入结果
    public static final int JPUSH_MSG_TYPE_BILL_DEAL=12;//帖子处理
    public static final int JPUSH_MSG_TYPE_ACTIVITY_NOTICE=13;//活动报名通知
   
}
