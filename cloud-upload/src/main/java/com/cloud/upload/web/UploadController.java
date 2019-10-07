package com.cloud.upload.web;

import com.cloud.upload.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zf
 * @date 2019-09-30-11:10
 */
@RestController
@RequestMapping("upload")
@Api("文件上传接口")
public class UploadController {

    @Autowired
    UploadService uploadService;


    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("image")
    @ApiOperation(value = "图片上传接口,成功后返回图片地址", httpMethod = "POST", notes = "图片上传")
    public ResponseEntity<String> uploadImage(
                                        @RequestParam("file")MultipartFile file
    ){
        return ResponseEntity.ok(uploadService.uploadImage(file));
    }

}
