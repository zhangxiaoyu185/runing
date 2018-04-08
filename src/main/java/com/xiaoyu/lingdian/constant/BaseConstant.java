package com.xiaoyu.lingdian.constant;

import java.util.regex.Pattern;

/**
 * 项目常量
 */
public class BaseConstant {

    /**
     * 验证图片
     */
    public static Pattern P = Pattern.compile("(?i).+?\\.(jpg|gif|bmp|png)$");

    /**
     * 附件默认文件夹
     */
    public static String ATTACHMENT_DIR_PATH = "C:\\file\\oilcard\\";

    /**
     * 默认附件文件夹
     */
    public static String DEFAULT_ATTACHMENT_DIR = "C:\\file\\";

    /**
     * 项目域名
     */
    public static String DEFAULT_DOMAIN_NAME = "https://oil.webaiyun.com/";

    /**
     * 音频软件默认目录
     */
    public static String DEFAULT_FFMPEG_DIR = "C:\\ffmpeg\\bin\\ffmpeg.exe";

    /**
     * 项目名第一个目录
     */
    public static String FIRST_DIR = "oilcard\\";

    /**
     * 小程序UUID
     */
    public final static String WE_CHAT_UUID =  "161126131423yqiZ";

    /**
     * 系统设置UUID
     */
    public final static String SYSTEM_SET_UUID =  "161126131423aaaZ";
}
