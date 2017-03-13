package Util;

/**
 * Created by Clanner on 2017/1/25.
 */
public class Constant {

    /**
     * 0x01:增成功
     * 0x02:增失败
     * 0x03:删成功
     * 0x04:删失败
     * 0x05:改成功
     * 0x06:改失败
     * 0x07:查成功
     * 0x08:查失败
     * 0x09:参数不能为空
     * 0x10:已添加过好友
     * 0x11:账号已被使用
     * 0x12:初始化失败
     * 0x13:账号或密码错误
     * 0x14;数据不存在
     * 0x15:已经删除好友
     * 0x16:密码错误
     * 0x17:排课不存在
     * 0x18：课程冲突
     * 0x19:数据已存在
     */

    public static final int SAVE_SUCCESS = 0x1;
    public static final int SAVE_FAILURE = 0x2;
    public static final int DELETE_SUCCESS = 0x3;
    public static final int DELETE_FAILURE = 0x4;
    public static final int UPDATE_SUCCESS = 0x5;
    public static final int UPDATE_FAILURE = 0x6;
    public static final int SELECT_SUCCESS = 0x7;
    public static final int SELECT_FAILURE = 0x8;
    public static final int PARAMS_NOT_MATCH = 0x9;
    public static final int HAS_ADD_FRIEND = 0x10;
    public static final int ACCOUNT_HAS_USED = 0x11;
    public static final int INIT_FAILURE = 0x12;
    public static final int ACCOUNT_OR_PASSWORD_WRONG = 0x13;
    public static final int NOT_EXIST = 0x14;
    public static final int HAS_DELETE_FRIEND = 0x15;
    public static final int PASSWORD_WRONG = 0x16;
    public static final int COURSE_NOT_EXIST = 0x17;
    public static final int COURSE_CONFLICT = 0x18;
    public static final int HAS_EXIST = 0x19;
}
