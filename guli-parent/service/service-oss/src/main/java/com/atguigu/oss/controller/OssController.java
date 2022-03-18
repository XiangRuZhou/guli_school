package com.atguigu.oss.controller;

import com.atguigu.commonutils.ResultMsg;
import com.atguigu.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("文件管理")
@RestController
@RequestMapping("/ossService/file")
@CrossOrigin
public class OssController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/fileUpload")
    public ResultMsg fileUpload(@ApiParam(name = "file",value = "file",required = true)
                                @RequestBody MultipartFile file){
        String fileUrl = fileService.fileUpload(file);
        return ResultMsg.ok().data("url",fileUrl);
    }



}
