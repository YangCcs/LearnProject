package com.yangcs.media.api;

import com.yangcs.base.model.PageParams;
import com.yangcs.base.model.PageResult;
import com.yangcs.media.model.dto.QueryMediaParamsDto;
import com.yangcs.media.model.dto.UploadFileParamsDto;
import com.yangcs.media.model.dto.UploadFileResultDto;
import com.yangcs.media.model.po.MediaFiles;
import com.yangcs.media.service.MediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @description 媒资文件管理接口
 * @version 1.0
 */
@Api(value = "媒资文件管理接口",tags = "媒资文件管理接口")
@RestController
public class MediaFilesController {


    @Autowired
    MediaFileService mediaFileService;


    @ApiOperation("媒资列表查询接口")
    @PostMapping("/files")
    public PageResult<MediaFiles> list(PageParams pageParams, @RequestBody QueryMediaParamsDto queryMediaParamsDto){

    Long companyId = 1232141425L;
    return mediaFileService.queryMediaFiels(companyId,pageParams,queryMediaParamsDto);
    }

    @ApiOperation("上传图片")
    @RequestMapping(value = "/upload/coursefile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResultDto upload(@RequestPart("filedata")MultipartFile filedata) throws IOException {
        // 准备上传文件的信息
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        // 原始文件名称
        uploadFileParamsDto.setFilename(filedata.getOriginalFilename());
        // 文件大小
        uploadFileParamsDto.setFileSize(filedata.getSize());
        // 文件类型
        uploadFileParamsDto.setFileType("001001");
        // 创建一个临时文件
        File tempFile = File.createTempFile("minio", ".temp");
        filedata.transferTo(tempFile);

        Long companyId = 1232141425L;
        // 文件路径
        String localFilePath = tempFile.getAbsolutePath();

        //调用service上传图片 Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath
        UploadFileResultDto uploadFileResultDto = mediaFileService.uploadFile(companyId, uploadFileParamsDto, localFilePath);

        return uploadFileResultDto;
    }
}
