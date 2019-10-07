package com.cloud.upload.service;

import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author zf
 * @date 2019-09-30-11:11
 */
@Service
@Slf4j
public class UploadService {


    public String uploadImage(MultipartFile file) {
        File dest = new File("/Users/mac/Downloads/upload", file.getOriginalFilename());
        try {
            BufferedImage read = ImageIO.read(file.getInputStream());
            if(null == read){
                throw new CloudException(ExceptionEnum.FILE_TYPE_ERROR);
            }
            file.transferTo(dest);
            return "http://localhost:8081/"+file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("文件上传失败 => {}", e.getMessage());
            throw new CloudException(ExceptionEnum.FILE_UPLOAD_ERROR);
        }
    }
}
