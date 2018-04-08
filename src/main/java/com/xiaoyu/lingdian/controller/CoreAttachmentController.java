package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.constant.BaseConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

import com.xiaoyu.lingdian.entity.CoreAttachment;
import com.xiaoyu.lingdian.entity.CoreSystemSet;
import com.xiaoyu.lingdian.service.CoreAttachmentService;
import com.xiaoyu.lingdian.service.CoreSystemSetService;
import com.xiaoyu.lingdian.tool.IOUtil;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreAttachmentVO;

@SuppressWarnings("restriction")
@Controller
@RequestMapping(value = "/coreAttachment")
@Api(value = "coreAttachment", description = "附件相关操作")
public class CoreAttachmentController extends BaseController {

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
     * base64流上传图片,只支持单文件,代码里不绑定,返回UUID
     *
     * @param cratmType 类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片
     * @param cratmDir  目录,例a/b
     * @param imgData   base64流
     * @param response
     * @return
     */
    @ApiOperation(value = "(前台用)base64流上传图片,只支持单文件,代码里不绑定,返回UUID", httpMethod = "POST", notes = "(前台用)base64流上传图片,只支持单文件,代码里不绑定,返回UUID")
    @RequestMapping(value = "/stream/upload", method = RequestMethod.POST)
    public void uploadStream(
            @ApiParam(value = "类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片", required = true) @RequestParam(value = "cratmType", required = true) Integer cratmType,
            @ApiParam(value = "图片存储上级目录,例a/b", required = true) @RequestParam(value = "cratmDir", required = true) String cratmDir,
            @ApiParam(value = "图片base64流", required = true) @RequestParam(value = "imgData", required = true) String imgData,
            HttpServletResponse response) {
        logger.info("[CoreAttachmentController]:begin uploadStream.");
        if (0 == cratmType) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少上传的文件类型！"), response);
            logger.info("[CoreAttachmentController]:end uploadStream.");
            return;
        }

        String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
        //获取附件目录
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
            baseDir = coreSystemSet.getCrsstAttachmentDir();
        }

        String uuid = RandomUtil.generateString(16);
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            String[] strList = imgData.split(";");
            if (strList.length == 2) {
                imgData = strList[1].substring(7, strList[1].length());
            }
            byte[] b = decoder.decodeBuffer(imgData); // Base64解码
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) { // 调整异常数据
                    b[i] += 256;
                }
            }
            String name = RandomUtil.generateString(20) + ".png"; //带随机数的新文件名
            String path = baseDir + cratmDir + "/" + name;
            IOUtil.createFile(path);
            out = new FileOutputStream(path); // 对字节数组字符串进行Base64解码
            out.write(b);
            out.flush();
            out.close();

            //插入数据库中
            CoreAttachment coreAttachment = new CoreAttachment();
            coreAttachment.setCratmUuid(uuid);
            coreAttachment.setCratmCdate(new Date());
            coreAttachment.setCratmFileName(name);
            coreAttachment.setCratmDir(cratmDir);
            coreAttachment.setCratmExtension(getAfterLastPoint(name));
            coreAttachment.setCratmType(cratmType);
            coreAttachmentService.insertCoreAttachment(coreAttachment);
        } catch (IOException e) {
            logger.info(e.getMessage());
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "上传附件失败！"), response);
            logger.info("[CoreAttachmentController]:end uploadStream");
            return;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "上传附件失败！"), response);
                logger.info("[CoreAttachmentController]:end uploadStream");
                return;
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "上传附件成功！", uuid), response);
        logger.info("[CoreAttachmentController]:end uploadStream");
    }

    /**
     * base64流上传图片,只支持单文件,根据业务UUID代码里直接绑定,返回UUID
     *
     * @param cratmType    类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片
     * @param cratmDir     目录,例a/b
     * @param imgData      base64流
     * @param cratmBusUuid 业务实体UUID
     * @param response
     * @return
     */
    @ApiOperation(value = "base64流上传图片,只支持单文件,根据业务UUID代码里直接绑定,返回UUID", httpMethod = "POST", notes = "base64流上传图片,只支持单文件,根据业务UUID代码里直接绑定,返回UUID")
    @RequestMapping(value = "/stream/upload/by/busi", method = RequestMethod.POST)
    public void uploadStreamByBusi(
            @ApiParam(value = "类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片", required = true) @RequestParam(value = "cratmType", required = true) Integer cratmType,
            @ApiParam(value = "图片存储上级目录,例a/b", required = true) @RequestParam(value = "cratmDir", required = true) String cratmDir,
            @ApiParam(value = "图片base64流", required = true) @RequestParam(value = "imgData", required = true) String imgData,
            @ApiParam(value = "业务UUID", required = true) @RequestParam(value = "cratmBusUuid", required = true) String cratmBusUuid,
            HttpServletResponse response) {
        logger.info("[CoreAttachmentController]:begin uploadStreamByBusi.");
        if (0 == cratmType) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少上传的文件类型！"), response);
            logger.info("[CoreAttachmentController]:end uploadStreamByBusi.");
            return;
        }

        String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
        //获取附件目录
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
            baseDir = coreSystemSet.getCrsstAttachmentDir();
        }

        String uuid = RandomUtil.generateString(16);
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            String[] strList = imgData.split(";");
            if (strList.length == 2) {
                imgData = strList[1].substring(7, strList[1].length());
            }
            byte[] b = decoder.decodeBuffer(imgData); // Base64解码
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) { // 调整异常数据
                    b[i] += 256;
                }
            }
            String name = RandomUtil.generateString(20) + ".png"; //带随机数的新文件名
            String path = baseDir + cratmDir + "/" + name;
            IOUtil.createFile(path);
            out = new FileOutputStream(path); // 对字节数组字符串进行Base64解码
            out.write(b);
            out.flush();
            out.close();

            //插入数据库中
            CoreAttachment coreAttachment = new CoreAttachment();
            coreAttachment.setCratmUuid(uuid);
            coreAttachment.setCratmCdate(new Date());
            coreAttachment.setCratmFileName(name);
            coreAttachment.setCratmDir(cratmDir);
            coreAttachment.setCratmExtension(getAfterLastPoint(name));
            coreAttachment.setCratmType(cratmType);
            coreAttachment.setCratmBusUuid(cratmBusUuid);
            coreAttachmentService.insertCoreAttachment(coreAttachment);
        } catch (IOException e) {
            logger.info(e.getMessage());
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "上传附件失败！"), response);
            logger.info("[CoreAttachmentController]:end uploadStreamByBusi");
            return;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "上传附件失败！"), response);
                logger.info("[CoreAttachmentController]:end uploadStreamByBusi");
                return;
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "上传附件成功！", uuid), response);
        logger.info("[CoreAttachmentController]:end uploadStreamByBusi.");
    }

    /**
     * 上传附件（包括文件、图片）,支持多文件,代码里不绑定,返回UUID
     *
     * @param cratmType 类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片
     * @param cratmDir  目录,例a/b
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "上传附件（包括文件、图片）,支持多文件,代码里不绑定,返回UUID", httpMethod = "POST", notes = "上传附件（包括文件、图片）,支持多文件,代码里不绑定,返回UUID")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(
            @ApiParam(value = "类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片", required = true) @RequestParam(value = "cratmType", required = true) Integer cratmType,
            @ApiParam(value = "图片存储上级目录,例a/b", required = true) @RequestParam(value = "cratmDir", required = true) String cratmDir,
            HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("[CoreAttachmentController]:begin upload.");
        if (0 == cratmType) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少上传的文件类型！"), response);
            logger.info("[CoreAttachmentController]:end upload.");
            return;
        }

        String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
        //获取附件目录
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
            baseDir = coreSystemSet.getCrsstAttachmentDir();
        }

        String uuids = "";
        String error = "";
        // 解析器解析request的上下文/创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            // 再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    if (file.isEmpty()) {
                        error += "文件为空，不能上传";
                        break;
                    }
                    // 记录上传过程起始时的时间，用来计算上传时间
                    int pre = (int) System.currentTimeMillis();
                    String orignalFilename = file.getOriginalFilename(); //原文件名,带后缀
                    String fileName = delAfterLastPoint(orignalFilename) + RandomUtil.generateString(5) + getAfterLastPoint(orignalFilename); //带随机数的新文件名
                    String uuid = RandomUtil.generateString(16);

                    if (cratmType != 999) { //999非图片
                        Matcher m = BaseConstant.P.matcher(fileName);
                        if (!m.matches()) {
                            error += orignalFilename;
                            error += "上传失败：只支持JPG或JPEG、BMP、PNG格式的图片文件";
                            break;
                        }
                    }
                    if (cratmType != 999) { //999非图片
                        if (file.getSize() > 9437148) { //大小超过9M的不上传
                            error += orignalFilename;
                            error += "上传失败：图片大小请保持在9M以内";
                            break;
                        }
                        //获得上传图片的宽度
                        int width = getPicWidth(file);
                        int height = getPicHeight(file);
                        if (width == -1 || width == 0 || height == -1 || height == 0) {
                            error += orignalFilename;
                            error += "上传失败：图片损坏，不能上传";
                            break;
                        }
                    }
                    if (cratmType == 999) { //999非图片
                        if (file.getSize() > 31457280) { //大小超过30M的不上传
                            error += orignalFilename;
                            error += "上传失败：附件大小请保持在30M以内";
                            break;
                        }
                    }

                    String path = baseDir + cratmDir + "/" + fileName;

                    uuids += uuid;
                    uuids += "|";
                    // 写文件到本地
                    try {
                        IOUtil.createFile(path);
                        File localFile = new File(path);
                        file.transferTo(localFile);
                    } catch (IllegalStateException e) {
                        logger.info(e.getMessage());
                        error += orignalFilename;
                        error += "上传失败：文件错误，不能上传到服务器";
                        break;
                    } catch (IOException e) {
                        logger.info(e.getMessage());
                        error += orignalFilename;
                        error += "上传失败：文件错误，不能上传到服务器";
                        break;
                    }
                    // 记录上传该文件后的时间
                    int finaltime = (int) System.currentTimeMillis();
                    int min = finaltime - pre;
                    logger.info(fileName + "耗时 : " + min);
                    //插入数据库中
                    CoreAttachment coreAttachment = new CoreAttachment();
                    coreAttachment.setCratmUuid(uuid);
                    coreAttachment.setCratmCdate(new Date());
                    coreAttachment.setCratmFileName(fileName);
                    coreAttachment.setCratmDir(cratmDir);
                    coreAttachment.setCratmExtension(getAfterLastPoint(fileName));
                    coreAttachment.setCratmType(cratmType);
                    coreAttachmentService.insertCoreAttachment(coreAttachment);
                }
            }
        }
        if (!("").equals(error)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, error), response);
            logger.info("[CoreAttachmentController]:end upload.");
            return;
        }
        if(uuids.endsWith("|")){
            uuids = uuids.substring(0, uuids.length() - 1);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "上传附件成功", uuids), response);
        logger.info("[CoreAttachmentController]:end upload.");
    }

    /**
     * 上传附件（包括文件、图片）,支持多文件,根据业务UUID代码里直接绑定,返回UUID
     *
     * @param cratmType    类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片
     * @param cratmDir     目录,例a/b
     * @param cratmBusUuid 业务附件UUID
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "上传附件（包括文件、图片）,支持多文件,根据业务UUID代码里直接绑定,返回UUID", httpMethod = "POST", notes = "上传附件（包括文件、图片）,支持多文件,根据业务UUID代码里直接绑定,返回UUID")
    @RequestMapping(value = "/upload/by/busi", method = RequestMethod.POST)
    public void uploadByBusi(
            @ApiParam(value = "类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片", required = true) @RequestParam(value = "cratmType", required = true) Integer cratmType,
            @ApiParam(value = "图片存储上级目录,例a/b", required = true) @RequestParam(value = "cratmDir", required = true) String cratmDir,
            @ApiParam(value = "业务UUID", required = true) @RequestParam(value = "cratmBusUuid", required = true) String cratmBusUuid,
            HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("[CoreAttachmentController]:begin uploadByBusi.");
        if (0 == cratmType) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少上传的文件类型！"), response);
            logger.info("[CoreAttachmentController]:end uploadByBusi.");
            return;
        }

        String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
        //获取附件目录
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
            baseDir = coreSystemSet.getCrsstAttachmentDir();
        }

        String uuids = "";
        String error = "";
        // 解析器解析request的上下文/创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            // 再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    if (file.isEmpty()) {
                        error += "文件为空，不能上传";
                        break;
                    }
                    // 记录上传过程起始时的时间，用来计算上传时间
                    int pre = (int) System.currentTimeMillis();
                    String orignalFilename = file.getOriginalFilename(); //原文件名,带后缀
                    String fileName = delAfterLastPoint(orignalFilename) + RandomUtil.generateString(5) + getAfterLastPoint(orignalFilename); //带随机数的新文件名
                    String uuid = RandomUtil.generateString(16);

                    if (cratmType != 999) { //999非图片
                        Matcher m = BaseConstant.P.matcher(fileName);
                        if (!m.matches()) {
                            error += orignalFilename;
                            error += "上传失败：只支持JPG或JPEG、BMP、PNG格式的图片文件";
                            break;
                        }
                    }
                    if (cratmType != 999) { //999非图片
                        if (file.getSize() > 9437148) { //大小超过9M的不上传
                            error += orignalFilename;
                            error += "上传失败：图片大小请保持在9M以内";
                            break;
                        }
                        //获得上传图片的宽度
                        int width = getPicWidth(file);
                        int height = getPicHeight(file);
                        if (width == -1 || width == 0 || height == -1 || height == 0) {
                            error += orignalFilename;
                            error += "上传失败：图片损坏，不能上传";
                            break;
                        }
                    }
                    if (cratmType == 999) { //999非图片
                        if (file.getSize() > 31457280) { //大小超过30M的不上传
                            error += orignalFilename;
                            error += "上传失败：附件大小请保持在30M以内";
                            break;
                        }
                    }

                    String path = baseDir + cratmDir + "/" + fileName;
                    uuids += uuid;
                    uuids += "|";
                    // 写文件到本地
                    try {
                        IOUtil.createFile(path);
                        File localFile = new File(path);
                        file.transferTo(localFile);
                    } catch (IllegalStateException e) {
                        logger.info(e.getMessage());
                        error += orignalFilename;
                        error += "上传失败：文件错误，不能上传到服务器";
                        break;
                    } catch (IOException e) {
                        logger.info(e.getMessage());
                        error += orignalFilename;
                        error += "上传失败：文件错误，不能上传到服务器";
                        break;
                    }
                    // 记录上传该文件后的时间
                    int finaltime = (int) System.currentTimeMillis();
                    int min = finaltime - pre;
                    logger.info(fileName + "耗时 : " + min);
                    //插入数据库中
                    CoreAttachment coreAttachment = new CoreAttachment();
                    coreAttachment.setCratmUuid(uuid);
                    coreAttachment.setCratmCdate(new Date());
                    coreAttachment.setCratmFileName(fileName);
                    coreAttachment.setCratmDir(cratmDir);
                    coreAttachment.setCratmExtension(getAfterLastPoint(fileName));
                    coreAttachment.setCratmType(cratmType);
                    coreAttachment.setCratmBusUuid(cratmBusUuid);
                    coreAttachmentService.insertCoreAttachment(coreAttachment);
                }
            }
        }
        if (!("").equals(error)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, error), response);
            logger.info("[CoreAttachmentController]:end uploadByBusi.");
            return;
        }
        if(uuids.endsWith("|")){
            uuids = uuids.substring(0, uuids.length() - 1);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "上传附件成功", uuids), response);
        logger.info("[CoreAttachmentController]:end uploadByBusi.");
    }

    /**
     * 根据附件类型和业务UUID查询附件列表
     *
     * @param cratmType    类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片
     * @param cratmBusUuid
     * @param response
     */
    @ApiOperation(value = "根据附件类型和业务UUID查询附件列表", httpMethod = "POST", notes = "根据附件类型和业务UUID查询附件列表", response = CoreAttachmentVO.class)
    @RequestMapping(value = "/find/attachement", method = RequestMethod.POST)
    public void findAttachement(
            @ApiParam(value = "类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片", required = true) @RequestParam(value = "cratmType", required = true) Integer cratmType,
            @ApiParam(value = "业务UUID", required = true) @RequestParam(value = "cratmBusUuid", required = true) String cratmBusUuid,
            HttpServletResponse response) {
        logger.info("[CoreAttachmentController]:begin findAttachement.");
        if (0 == cratmType) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少文件类型！"), response);
            logger.info("[CoreAttachmentController]:end findAttachement.");
            return;
        }

        List<CoreAttachment> list = coreAttachmentService.findCoreAttachmentByCnd(cratmBusUuid, cratmType);
        List<CoreAttachmentVO> voList = new ArrayList<>();
        CoreAttachmentVO vo = new CoreAttachmentVO();
        for (CoreAttachment coreAttachment : list) {
            vo = new CoreAttachmentVO();
            vo.convertPOToVO(coreAttachment);
            voList.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "查询附件列表成功", voList), response);
        logger.info("[CoreAttachmentController]:end findAttachement.");
    }

    /**
     * 查看附件详情
     *
     * @param cratmUuid
     * @param response
     */
    @ApiOperation(value = "查看附件详情", httpMethod = "POST", notes = "查看附件详情", response = CoreAttachmentVO.class)
    @RequestMapping(value = "/find/attachment/info", method = RequestMethod.POST)
    public void findAttachmentInfo(
            @ApiParam(value = "附件标识UUID", required = true) @RequestParam(value = "cratmUuid", required = true) String cratmUuid,
            HttpServletResponse response) {
        logger.info("[CoreAttachmentController]:begin findAttachmentInfo");
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmUuid(cratmUuid);
        coreAttachment = coreAttachmentService.getCoreAttachment(coreAttachment);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "查询附件详细信息成功！", coreAttachment), response);
        logger.info("[CoreAttachmentController]:end findAttachmentInfo");
    }

    /**
     * 根据业务ID删除附件
     *
     * @param cratmBusUuid
     * @param response
     */
    @ApiOperation(value = "根据业务ID删除附件", httpMethod = "POST", notes = "根据业务ID删除附件")
    @RequestMapping(value = "/delete/by/busi", method = RequestMethod.POST)
    public void deleteByBusi(
            @ApiParam(value = "业务UUID", required = true) @RequestParam(value = "cratmBusUuid", required = true) String cratmBusUuid,
            HttpServletResponse response) {
        logger.info("[CoreAttachmentController]:begin deleteByBusi");
        List<CoreAttachment> list = coreAttachmentService.findCoreAttachmentByCnd(cratmBusUuid, null);
        //删附件数据
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmBusUuid(cratmBusUuid);
        coreAttachmentService.deleteCoreAttachmentByBusi(coreAttachment);

        String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
        //获取附件目录
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
            baseDir = coreSystemSet.getCrsstAttachmentDir();
        }

        //删附件文件
        for (CoreAttachment model : list) {
            if (model.getCratmFileName() != null && !("").equals(model.getCratmFileName())) {
                IOUtil.deleteFile(baseDir + model.getCratmDir() + "/" + model.getCratmFileName()); //删除服务器上文件
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除附件成功！"), response);
        logger.info("[CoreAttachmentController]:end deleteByBusi");
    }

    /**
     * 根据附件UUID删除附件
     *
     * @param cratmUuid
     * @param response
     * @return
     */
    @ApiOperation(value = "根据附件UUID删除附件", httpMethod = "POST", notes = "根据附件UUID删除附件")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(
            @ApiParam(value = "附件UUID", required = true) @RequestParam(value = "cratmUuid", required = true) String cratmUuid,
            HttpServletResponse response) {
        logger.info("[CoreAttachmentController]:begin delete");
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmUuid(cratmUuid);
        coreAttachment = coreAttachmentService.getCoreAttachment(coreAttachment);
        if (coreAttachment == null) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "该附件不存在！"), response);
            return;
        }
        coreAttachmentService.deleteCoreAttachment(coreAttachment);

        String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
        //获取附件目录
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
            baseDir = coreSystemSet.getCrsstAttachmentDir();
        }

        if (coreAttachment.getCratmFileName() != null && !("").equals(coreAttachment.getCratmFileName())) {
            IOUtil.deleteFile(baseDir + coreAttachment.getCratmDir() + "/" + coreAttachment.getCratmFileName()); //删除服务器上文件
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除附件成功！"), response);
        logger.info("[CoreAttachmentController]:end delete");
    }

    /**
     * 根据附件UUID获取原图
     *
     * @param cratmUuid
     * @return
     */
    @ApiOperation(value = "(前后台通用)根据附件UUID获取原图", httpMethod = "GET", notes = "(前后台通用)根据附件UUID获取原图")
    @RequestMapping(value = "/image/get/{cratmUuid}", method = RequestMethod.GET)
    public void getImage(
            @ApiParam(value = "附件UUID", required = true) @PathVariable(value = "cratmUuid") String cratmUuid,
            HttpServletResponse response) {
        logger.info("[CoreAttachmentController]:begin getImage");
        byte[] bytes = null;
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmUuid(cratmUuid);
        coreAttachment = coreAttachmentService.getCoreAttachment(coreAttachment);
        if (coreAttachment == null) {
            writePicStream(response, null);
            return;
        }

        String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
        //获取附件目录
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
            baseDir = coreSystemSet.getCrsstAttachmentDir();
        }

        String path = baseDir + coreAttachment.getCratmDir() + "/" + coreAttachment.getCratmFileName();
        InputStream in = null;
        try {
            in = new FileInputStream(new File(path));
            bytes = IOUtils.toByteArray(in); // 文件二进制码
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }

        writePicStream(response, bytes);
        logger.info("[CoreAttachmentController]:end getImage");
    }

    /**
     * 根据附件UUID获取原图
     *
     * @param cratmUuid
     * @return
     */
    @ApiOperation(value = "(前后台通用)根据附件UUID获取原图", httpMethod = "GET", notes = "(前后台通用)根据附件UUID获取原图")
    @RequestMapping(value = "/image/get/other/{cratmUuid}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getOtherImage(
            @ApiParam(value = "附件UUID", required = true) @PathVariable(value = "cratmUuid") String cratmUuid) {
        logger.info("[CoreAttachmentController]:begin getOtherImage");
        byte[] bytes = null;
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmUuid(cratmUuid);
        coreAttachment = coreAttachmentService.getCoreAttachment(coreAttachment);
        if (coreAttachment == null) {
            return null;
        }

        String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
        //获取附件目录
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
            baseDir = coreSystemSet.getCrsstAttachmentDir();
        }

        String path = baseDir + coreAttachment.getCratmDir() + "/" + coreAttachment.getCratmFileName();
        InputStream in = null;
        try {
            in = new FileInputStream(new File(path));
            bytes = IOUtils.toByteArray(in); // 文件二进制码
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }
        logger.info("[CoreAttachmentController]:end getOtherImage");
        return bytes;
    }

    /**
     * 根据附件UUID获取附件base64流
     *
     * @param cratmUuid
     * @param response
     * @return
     */
    @ApiOperation(value = "(前后台通用)根据附件UUID获取附件base64流", httpMethod = "GET", notes = "(前后台通用)根据附件UUID获取附件base64流")
    @RequestMapping(value = "/get/stream", method = RequestMethod.GET)
    public void getStream(
            @ApiParam(value = "附件UUID", required = true) @RequestParam(value = "cratmUuid", required = true) String cratmUuid,
            HttpServletResponse response) {
        logger.info("[CoreAttachmentController]:begin getStream");
        if (StringUtil.isEmpty(cratmUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "缺少附件UUID参数！"), response);
            logger.info("[CoreAttachmentController]:end getStream");
            return;
        }
        byte[] bytes = null;
        CoreAttachment coreAttachment = new CoreAttachment();
        coreAttachment.setCratmUuid(cratmUuid);
        coreAttachment = coreAttachmentService.getCoreAttachment(coreAttachment);
        if (coreAttachment == null) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "获取附件base64流失败！", ""), response);
            logger.info("[CoreAttachmentController]:end getStream");
            return;
        }

        String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
        //获取附件目录
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
            baseDir = coreSystemSet.getCrsstAttachmentDir();
        }

        String path = baseDir + coreAttachment.getCratmDir() + "/" + coreAttachment.getCratmFileName();
        String strBase64 = "";
        InputStream in = null;
        try {
            in = new FileInputStream(new File(path));
            bytes = IOUtils.toByteArray(in); // 文件二进制码
            strBase64 = new BASE64Encoder().encode(bytes); //将字节流数组转换为字符串
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取附件base64流成功！", strBase64), response);
        logger.info("[CoreAttachmentController]:end getStream");
    }

    /**
     * 获取一个文件名的后缀名（最后一个点后面的字符串）,带上点
     *
     * @param fileName
     * @return
     */
    public String getAfterLastPoint(String fileName) {
        int last = fileName.lastIndexOf(".");
        if (last <= 0) {
            return "";
        }
        return fileName.substring(last, fileName.length());
    }

    /**
     * 去除一个文件的后缀名（去除最后一个点后面的字符串）,获取文件名
     *
     * @param fileName
     * @return
     */
    public String delAfterLastPoint(String fileName) {
        int last = fileName.lastIndexOf(".");
        if (last <= 0) {
            return "";
        }
        return fileName.substring(0, last);
    }

    /**
     * 获得上传图片文件的高度
     *
     * @param file
     * @return
     */
    private int getPicHeight(MultipartFile file) {
        int picHeight = 0;
        try {
            Image img = ImageIO.read(file.getInputStream());
            picHeight = img.getHeight(null);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return picHeight;
    }

    /**
     * 获得上传图片文件的宽度
     *
     * @param file
     * @return
     */
    private int getPicWidth(MultipartFile file) {
        int picWidth = 0;
        try {
            Image img = ImageIO.read(file.getInputStream());
            picWidth = img.getWidth(null);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return picWidth;
    }

}