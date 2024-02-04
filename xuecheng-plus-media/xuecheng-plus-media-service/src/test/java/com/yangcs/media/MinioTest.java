package com.yangcs.media;


import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

// 测试minio的SDK

public class MinioTest {

    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

    @Test
    public void testUpLoad() throws Exception {
        // 根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".mp4");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }

        // 上传文件的参数信息
        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                .bucket("testbucket") // 桶
                .filename("C:\\Users\\yangcsky\\Videos\\test.mp4") // 指定本地文件路径
                .object("test.mp4") // 对象名
                .contentType(mimeType) // 设置媒体文件类型
                .build();

        // 上传文件
        minioClient.uploadObject(uploadObjectArgs);
    }

    @Test
    public void testDelete() throws Exception {
        try {
            // 删除文件
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket("testbucket")
                            .object("test.mp4").build());
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除失败");
        }
    }

    @Test
    public void testGetFile() throws Exception{
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().
                bucket("testbucket").object("test.mp4").build();
        // 输入流，查询远程服务获取到一个流对象
        FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
        // 指定输出流
        FileOutputStream outputStream = new FileOutputStream(new File("C:\\Users\\yangcsky\\Videos\\Captures\\res.mp4"));
        IOUtils.copy(inputStream, outputStream); // 下载文件

        // 校验文件的完整性 对文件的内容进行md5，对比源文件和本地文件的md5
        String source_md5 = DigestUtils.md5Hex(new FileInputStream(new File("C:\\Users\\yangcsky\\Videos\\test.mp4")));
        String local_md5 = DigestUtils.md5Hex(new FileInputStream(new File("C:\\Users\\yangcsky\\Videos\\Captures\\res.mp4")));
        if (source_md5.equals(local_md5)) {
            System.out.println("download success");
        } else {
            System.out.println("download failure");
        }
    }

}
