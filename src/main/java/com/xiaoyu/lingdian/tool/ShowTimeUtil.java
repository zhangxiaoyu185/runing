package com.xiaoyu.lingdian.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowTimeUtil {
    
    private static final long second = 1L;
    private static final long minute = 60 * second;
    private static final long hour = 60 * minute;
    private static final long day = 24 * hour;
    private static final long week = 7 * day;
    private static final long fourWeek = 4 * week;
    
    /**
     * processShowTime
     * 
     * @param startTime 起始日期 （较小日期）
     * @return
     */
    public static String processShowTime(Date startTime) {
        return processShowTime(new Date(), startTime);
    }
    
    /**
     * processShowTime
     * 
     * @param endTime 截止日期（较大日期）
     * @param startTime 起始日期（较小日期）
     * @return
     */
    public static String processShowTime(Date endTime, Date startTime) {
        String showTime = "";
        //根据endTime（有效时间）与 startTime（发布时间）差
        Long timeDiff = Math.abs(endTime.getTime() - startTime.getTime());
        if (minute > timeDiff) {
            //1分钟以内，显示“刚刚”
            showTime = "刚刚";
        } else if (minute <= timeDiff && timeDiff < hour) {
            //大于等于1分钟且小于1小时，显示“**分钟前”
            showTime = timeDiff / minute + "分钟前";
        } else if (hour <= timeDiff && timeDiff < day) {
            //大于等于1小时且小于1天，显示“**小时前”
            showTime = timeDiff / hour + "小时前";
        } else if (day <= timeDiff && timeDiff < week) {
            //大于等于1天且小于1周，显示“**天前”
            showTime = timeDiff / day + "天前";
        } else if (week <= timeDiff && timeDiff <= fourWeek) {
            //大于等于1周且小于等于4周，显示“**周前”
            showTime = timeDiff / week + "周前";
        } else if (fourWeek < timeDiff) {
            //大于4周，显示发布日期
            showTime = new SimpleDateFormat("yyyy-MM-dd").format(startTime);
        }
        
        return showTime;
    }
    
    /**
     * 来源和时间展示转换
     * @param nickName
     * @param publishTime
     * @return
     */
    public static String appProcessSourceAndTime(String nickName, Date publishTime) {
    	if(StringUtil.isEmpty(nickName)) {
    		nickName = "未命名用户";
    	}
    	if(nickName.length() > 5) {
    		nickName = nickName.substring(0, 5) + "...";
    	}
        return "由" + nickName + "于" + processShowTime(publishTime) + "发布";
    }
    
    /**
     * calcRemainTime:(计算剩余有效时间[endTime-now]).
     * 
     * @param endTime 有效日期（截止日期）
     * @return
     */
    public static String calcRemainTime(Date endTime) {
        Long remainTime = endTime.getTime() - new Date().getTime();
        if (remainTime < 0) {
            //如果有效日期<当前日期，则返回“已过期”
            return "已过期";
        } else {
            int dayCount = 0;
            int hourCount = 0;
            int minuteCount = 0;
            do {
                if (remainTime / day >= 1) {
                    dayCount = (int) (remainTime / day);
                    remainTime = remainTime % day;
                } else if (remainTime / hour >= 1) {
                    hourCount = (int) (remainTime / hour);
                    remainTime = remainTime % hour;
                } else if (remainTime / minute >= 1) {
                    minuteCount = (int) (remainTime / minute);
                    remainTime = remainTime % minute;
                }
            } while (remainTime >= minute);
            StringBuilder showRemain = new StringBuilder("");
            if (dayCount >= 1) {
                showRemain.append(dayCount).append("天");
            }
            if (hourCount >= 1) {
                showRemain.append(hourCount).append("小时");
            }
            if (minuteCount >= 1) {
                showRemain.append(minuteCount).append("分");
            }
            return showRemain.toString();
        }
    }
    
    /**
     * calcSimpleRemainTime:(计算剩余有效时间[endTime-now]，以简单时间格式输出：“*天|*小时|*分钟”).
     * 
     * @param endTime 有效日期（截止日期）
     * @return
     */
    public static String calcRawRemainTime(Date endTime) {
        Long remainTime = endTime.getTime() - new Date().getTime();
        if (remainTime < 0) {
            //如果有效日期<当前日期，则返回“已过期”
            return "已过期";
        } else {
            int dayCount = 0;
            int hourCount = 0;
            int minuteCount = 0;
            if (remainTime / day >= 1) {
                //1天以外（含1天）显示：**天
                dayCount = (int) (remainTime / day);
                return dayCount + "天";
            } else if (remainTime / hour >= 1) {
                //24小时以内（不含24小时）显示：**小时
                hourCount = (int) (remainTime / hour);
                return hourCount + "小时";
            } else {
                //一小时以内（不含一小时）显示：**分钟；若不足一分钟，则默认显示1分钟
                minuteCount = (int) (remainTime / minute);
                return (minuteCount >=1 ? minuteCount : 1) + "分钟";
            }
        }
    }

}
