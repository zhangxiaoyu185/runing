package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreShortMessage;

import java.util.List;

/**
 * 短信日志记录表
 */
public interface CoreShortMessageService {

    /**
     * 添加
     *
     * @param coreShortMessage
     * @return
     */
    public boolean insertCoreShortMessage(CoreShortMessage coreShortMessage);

    /**
     * 修改
     *
     * @param coreShortMessage
     * @return
     */
    public boolean updateCoreShortMessage(CoreShortMessage coreShortMessage);

    /**
     * 删除
     *
     * @param coreShortMessage
     * @return
     */
    public boolean deleteCoreShortMessage(CoreShortMessage coreShortMessage);

    /**
     * 批量删除
     *
     * @param list
     * @return boolean
     */
    public boolean deleteBatchByIds(List<String> list);

    /**
     * 查询
     *
     * @param coreShortMessage
     * @return
     */
    public CoreShortMessage getCoreShortMessage(CoreShortMessage coreShortMessage);

    
	/**
	* 根据手机号查询
	* @param crmceMobile
	* @return
	*/
	public CoreShortMessage getCoreShortMessageByMobile(String crmceMobile);

	/**
	* 根据手机号查询今日发送短信次数
	* @param crmceMobile
	* @return
	*/
	public int getCoreShortMessageCountByMobile(String crmceMobile);
	
	/**
	* 获取手机号的验证码list
	* @param crmceMobile
	* @return List
	*/
	public List<CoreShortMessage> findCoreShortMessageList(String crmceMobile);

    /**
     * 查询所有Page
     *
     * @return Page
     */
    public Page<CoreShortMessage> findCoreShortMessagePage(CoreShortMessage coreShortMessage, int pageNum, int pageSize);

}