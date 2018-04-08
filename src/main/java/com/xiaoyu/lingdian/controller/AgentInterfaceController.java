package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.constant.BaseConstant;
import com.xiaoyu.lingdian.entity.BusiAgent;
import com.xiaoyu.lingdian.entity.BusiAgentPay;
import com.xiaoyu.lingdian.entity.CoreAttachment;
import com.xiaoyu.lingdian.entity.CoreSystemSet;
import com.xiaoyu.lingdian.service.BusiAgentPayService;
import com.xiaoyu.lingdian.service.BusiAgentService;
import com.xiaoyu.lingdian.service.CoreAttachmentService;
import com.xiaoyu.lingdian.service.CoreSystemSetService;
import com.xiaoyu.lingdian.tool.IOUtil;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * 代理商对外第三方接口
 */
@Controller
@RequestMapping(value = "/agent")
@Api(value = "agent", description = "代理商对外第三方接口")
public class AgentInterfaceController extends BaseController {

    /**
     * 代理商打款表
     */
    @Autowired
    private BusiAgentPayService busiAgentPayService;

    /**
     * 代理商表
     */
    @Autowired
    private BusiAgentService busiAgentService;

    /**
     * 业务附件表
     */
    @Autowired
    private CoreAttachmentService coreAttachmentService;

    /**
     * 系统设置表
     */
    @Autowired
    private CoreSystemSetService coreSystemSetService;

    /**
     * 代理商申请打款( 状态:1申请中2已驳回3已确认)
     *
     * @param fee     打款金额
     * @param agentid   代理商标识
     * @param agentsecret 代理商密钥
     * @param ordercode   线下汇款单号
     * @param picdata     线下汇款单照片base64流
     * @param financename 操作财务姓名
     * @return
     */
    @ApiOperation(value = "代理商申请打款", httpMethod = "POST", notes = "代理商申请打款")
    @RequestMapping(value = "/apply/pay", method = RequestMethod.POST)
    public void applyPay(
            @ApiParam(value = "打款金额", required = true) @RequestParam(value = "fee", required = true) Double fee,
            @ApiParam(value = "代理商标识", required = true) @RequestParam(value = "agentid", required = true) String agentid,
            @ApiParam(value = "代理商密钥", required = true) @RequestParam(value = "agentsecret", required = true) String agentsecret,
            @ApiParam(value = "线下汇款单号", required = true) @RequestParam(value = "ordercode", required = true) String ordercode,
            @ApiParam(value = "线下汇款单照片base64流", required = true) @RequestParam(value = "picdata", required = true) String picdata,
            @ApiParam(value = "操作财务姓名", required = true) @RequestParam(value = "financename", required = true) String financename,
            HttpServletResponse response) {
        logger.info("[AgentInterfaceController]:begin applyPay");
        //判断金额必须为正数
        if(fee <= 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "打款金额必须为正数!"), response);
            logger.info("[AgentInterfaceController]:end applyPay");
            return;
        }
        //判断代理商平台是否存在
        BusiAgent busiAgent = new BusiAgent();
        busiAgent.setBsaetUuid(agentid);
        busiAgent = this.busiAgentService.getBusiAgent(busiAgent);
        if(null == busiAgent || StringUtil.isEmpty(busiAgent.getBsaetPwd())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "代理商不存在!"), response);
            logger.info("[AgentInterfaceController]:end applyPay");
            return;
        }
        if(!agentsecret.equals(busiAgent.getBsaetPwd())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "代理商密钥错误!"), response);
            logger.info("[AgentInterfaceController]:end applyPay");
            return;
        }
        //转换图片
        String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
        //获取附件目录
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
            baseDir = coreSystemSet.getCrsstAttachmentDir();
        }

        String picuuid = RandomUtil.generateString(16);
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            String[] strList = picdata.split(";");
            if (strList.length == 2) {
                picdata = strList[1].substring(7, strList[1].length());
            }
            byte[] b = decoder.decodeBuffer(picdata); // Base64解码
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) { // 调整异常数据
                    b[i] += 256;
                }
            }
            String name = RandomUtil.generateString(20) + ".png"; //带随机数的新文件名
            String path = baseDir + "2/" + name;
            IOUtil.createFile(path);
            out = new FileOutputStream(path); // 对字节数组字符串进行Base64解码
            out.write(b);
            out.flush();
            out.close();

            //插入数据库中
            CoreAttachment coreAttachment = new CoreAttachment();
            coreAttachment.setCratmUuid(picuuid);
            coreAttachment.setCratmCdate(new Date());
            coreAttachment.setCratmFileName(name);
            coreAttachment.setCratmDir("2");
            coreAttachment.setCratmExtension(".png");
            coreAttachment.setCratmType(2);
            coreAttachmentService.insertCoreAttachment(coreAttachment);
        } catch (IOException e) {
            logger.info(e.getMessage());
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "图片流解析失败！"), response);
            logger.info("[AgentInterfaceController]:end applyPay");
            return;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "图片流解析失败！"), response);
                logger.info("[AgentInterfaceController]:end applyPay");
                return;
            }
        }
        BusiAgentPay busiAgentPay = new BusiAgentPay();
        String uuid = RandomUtil.generateString(16);
        busiAgentPay.setBsapyUuid(uuid);
        busiAgentPay.setBsapyFee(fee);
        busiAgentPay.setBsapyAgent(agentid);
        busiAgentPay.setBsapyOrder(ordercode);
        busiAgentPay.setBsapyPic(picuuid);
        busiAgentPay.setBsapyFinance(financename);
        busiAgentPay.setBsapyStatus(1);
        Date date = new Date();
        busiAgentPay.setBsapyCdate(date);
        busiAgentPay.setBsapyUdate(date);
        busiAgentPayService.insertBusiAgentPay(busiAgentPay);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "提交成功!"), response);
        logger.info("[AgentInterfaceController]:end applyPay");
    }

}