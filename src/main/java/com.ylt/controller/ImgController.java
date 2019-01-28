package com.ylt.controller;

import com.ylt.common.JsonResult;
import com.ylt.config.Constants;
import com.ylt.dao.Imgdao;
import com.ylt.entity.Img;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Api(tags = "图像模块")
@Controller
public class ImgController {
    @Autowired
    Imgdao imgdao;

    @ApiOperation("添加图像")
    @RequestMapping(value = "/addImg", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addImg(@RequestParam String path,@RequestParam String hostId, HttpServletRequest request) {
        String userId = (String)request.getSession().getAttribute(Constants.USERID);
        if (StringUtils.isBlank(userId)) {
            return JsonResult.failed("未登录");
        }
        if(StringUtils.isBlank(path)||StringUtils.isBlank(hostId)){
            return JsonResult.failed("参数不全");
        }
        Img img = new Img();
        img.setHostId(hostId);
        img.setUserId(userId);
        img.setPath(path);
        img.setType(path.substring(path.lastIndexOf('.')));
        img.setUploadTime(DateFormatUtils.format(new Date(), Constants.DATE_PATTERN));
        img.setImgId(UUID.randomUUID().toString());
        imgdao.addImg(img);
        return JsonResult.success("操作成功");

    }
}
