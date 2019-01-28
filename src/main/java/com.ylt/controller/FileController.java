package com.ylt.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ylt.common.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Api(tags = "文件")
@Controller
public class FileController {

//    Gson gson=new GsonBuilder().enableComplexMapKeySerialization().create();

    @ApiOperation("上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult upload(@RequestParam MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String path = request.getServletContext().getRealPath("/WEB-INF/file/");
            String filename = file.getOriginalFilename();
            String type = filename.substring(filename.lastIndexOf('.'));
            File targetFile = new File(path, UUID.randomUUID().toString() + type);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
                return JsonResult.success("/file/"+targetFile.getName(),"操作成功");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return JsonResult.failed("上传失败");
    }

    @ApiOperation("批量上传")
    @RequestMapping(value = "/multiUpload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult multiUpload(@RequestParam MultipartFile[] files, HttpServletRequest request) {
        if (files != null && files.length > 0) {
            String path = request.getServletContext().getRealPath("/WEB-INF/file/");
            List<String> fileList = new ArrayList<>();

            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                String type = filename.substring(filename.lastIndexOf('.'));
                File targetFile = new File(path, UUID.randomUUID().toString() + type);
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
                    fileList.add(targetFile.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return JsonResult.success(fileList);
        }
        return JsonResult.failed("上传失败");
    }

    @ApiOperation("下载")
    @RequestMapping(value = "/downLoad/{fileName}", method = RequestMethod.GET)
    public void downLoad(HttpServletRequest request, HttpServletResponse response,@PathVariable(value = "fileName") String fileName) {
        String path = request.getServletContext().getRealPath("/WEB-INF/file/") + fileName;
        File file = new File(path);
        if (!file.exists()) {
            try {
                response.sendError(404, "您要下载的文件没找到");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            BufferedInputStream bufIn = new BufferedInputStream(new FileInputStream(file));
            response.reset();
            URL u = new URL("file:///" + path);
            response.setContentType(u.openConnection().getContentType());
            response.setHeader("Content-Disposition",
                    "inline;filename=" + fileName);
            OutputStream out = response.getOutputStream();
            IOUtils.copy(bufIn, out);
            bufIn.close();
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
