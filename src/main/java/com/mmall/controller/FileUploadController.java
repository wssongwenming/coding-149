package com.mmall.controller;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.mmall.common.JsonData;
import com.mmall.service.ResourceService;
import com.mmall.service.SysRoleService;
import com.mmall.service.SysTreeService;
import com.mmall.service.SysUserService;
import com.mmall.util.ResponseFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/sys/file")
@Slf4j
public class FileUploadController {

    @Resource
    private ResourceService resourceService;

    @RequestMapping(path="/upload",method = RequestMethod.POST, consumes = { "multipart/form-data","multipart/mixed" })
    @ResponseBody
    public JsonData UploadFile(@RequestPart("txt_file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String fileName=file.getOriginalFilename();//获取文件名加后缀
        File targetFile=null;
        if(fileName!=null&&fileName!=""){
            //String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";//存储路径
            //String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
            //String path="d:/upload/";
            String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName=new Date().getTime()+"_"+new Random().nextInt(10000)+suffix;//新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String fileAddDate = sdf.format(new Date());

            //获取文件夹路径
            File file1 =new File("d:/upload/photo"+"/"+fileAddDate);
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdirs();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {

                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);

                System.out.println("path="+targetFile.getPath());
                System.out.println("abpath="+targetFile.getAbsolutePath());

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        String photoPath=targetFile.getPath();
        photoPath=photoPath.replace("\\upload\\photo", "");
        photoPath="\\sys\\file\\"+photoPath;
        photoPath=photoPath.replace(".","\\");
        return JsonData.success(photoPath);
    }


    @RequestMapping(value = "/{driver}/{year}/{month}/{day}/{imagename}/{suffix}")
    public void downloadFile(HttpServletResponse response,@PathVariable("driver") String driver,@PathVariable("year") String year,@PathVariable("month") String month,@PathVariable("day") String day, @PathVariable("imagename") String imagename,@PathVariable("suffix") String suffix) throws IOException {

        String path=driver+"/upload/photo/"+year+"/"+month+"/"+day+"/"+imagename+"."+suffix;
        System.out.print(path);
        File file = resourceService.findFile(path);
        if (file == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            ResponseFileUtil.response(file, response);
        }
    }


 /*   @RequestMapping(path="/upload",method = RequestMethod.POST, consumes = { "multipart/form-data","multipart/mixed" })
    @ResponseBody*/
   /* public JsonData UploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");

        Map<String, Object> json = new HashMap<String, Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        *//** 页面控件的文件流* *//*
        MultipartFile multipartFile = null;
        Map map =multipartRequest.getFileMap();
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            Object obj = i.next();
            multipartFile=(MultipartFile) map.get(obj);
        }
        *//** 获取文件的后缀* *//*
        String filename = multipartFile.getOriginalFilename();
        InputStream inputStream;
        String path = "";
        String newVersionName = "";
        String fileMd5 = "";
        try {

            inputStream = multipartFile.getInputStream();
            File tmpFile = File.createTempFile(filename,
                    filename.substring(filename.lastIndexOf(".")));
            fileMd5 = Files.hash(tmpFile, Hashing.md5()).toString();
            FileUtils.copyInputStreamToFile(inputStream, tmpFile);

            tmpFile.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("newVersionName", newVersionName);
        json.put("fileMd5", fileMd5);
        json.put("message", "应用上传成功");
        json.put("status", true);
        json.put("filePath", path);

        return JsonData.success(json);
    }*/

}
