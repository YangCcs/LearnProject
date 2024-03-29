package com.yangcs.media.service;

import com.yangcs.base.model.PageParams;
import com.yangcs.base.model.PageResult;
import com.yangcs.base.model.RestResponse;
import com.yangcs.media.model.dto.QueryMediaParamsDto;
import com.yangcs.media.model.dto.UploadFileParamsDto;
import com.yangcs.media.model.dto.UploadFileResultDto;
import com.yangcs.media.model.po.MediaFiles;

import java.io.File;

/**
 * @version 1.0
 * @description 媒资文件管理业务类
 */
public interface MediaFileService {

    //根据媒资id查询文件信息
    MediaFiles getFileById(String mediaId);

    /**
     * @description 媒资文件查询方法
     * @param pageParams 分页参数
     * @param queryMediaParamsDto 查询条件
     * @return com.yangcs.base.model.PageResult<com.yangcs.media.model.po.MediaFiles>
     */

    /**
     * @param pageParams          分页参数
     * @param queryMediaParamsDto 查询条件
     * @return com.yangcs.base.model.PageResult<com.yangcs.media.model.po.MediaFiles>
     * @description 媒资文件查询方法
     */
    public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);


    // 本地的文件路径要传进来
    UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);

    // 将文件信息保存到数据库
    public MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto,
                                        String bucket, String objectName);

    public boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName);

    /**
     * @description 检查文件是否存在
     * @param fileMd5 文件的md5
     * @return com.yangcs.base.model.RestResponse<java.lang.Boolean> false不存在，true存在
     */


    /**
     * @param fileMd5 文件的md5
     * @return com.yangcs.base.model.RestResponse<java.lang.Boolean> false不存在，true存在
     * @description 检查文件是否存在
     */

    public RestResponse<Boolean> checkFile(String fileMd5);


    /**
     * @param fileMd5    文件的md5
     * @param chunkIndex 分块序号
     * @return com.xuecheng.base.model.RestResponse<java.lang.Boolean> false不存在，true存在
     * @description 检查分块是否存在
     */
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);


    /**
     * @param fileMd5 文件md5
     * @param chunk   分块序号
     * @param localChunkFilePath   文件字节
     * @return com.xuecheng.base.model.RestResponse
     * @description 上传分块
     */
    public RestResponse uploadChunk(String fileMd5, int chunk, String localChunkFilePath);


    /**
     * @param companyId           机构id
     * @param fileMd5             文件md5
     * @param chunkTotal          分块总和
     * @param uploadFileParamsDto 文件信息
     * @return com.xuecheng.base.model.RestResponse
     * @description 合并分块
     */
    public RestResponse mergechunks(Long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto);

    public File downloadFileFromMinIO(String bucket, String objectName);

}
