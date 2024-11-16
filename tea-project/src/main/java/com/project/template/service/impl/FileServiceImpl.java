package com.project.template.service.impl;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlPath;
import com.project.template.dto.FileInfoDTO;
import com.project.template.exception.CustomException;
import com.project.template.service.FileService;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${server.ip}")
    private String serverIp;
    @Value("${server.port}")
    private int serverPort;
    @Value("${files.uploads.path}")
    private String basePath;

    @Value("${files.uploads.enableDateTimeFormat:false}")
    private boolean enableDateTimeFormat;


    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("/yyyy/MM/dd/");

    public FileInfoDTO upload(MultipartFile file, String bucket) throws IOException {
        checkBucket(bucket);
        //获取上传文件扩展名
        String fix = FilenameUtils.getExtension(file.getOriginalFilename());
        //生成文件完整名称
        if (StringUtils.isBlank(fix)) {
            throw new CustomException("文件扩展名不能为空");
        }
        checkFix(fix);
        String newFileName = UUID.randomUUID() + "." + fix;
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
        fileInfoDTO.setAlt(file.getOriginalFilename());
        File newFile = createFile(bucket, newFileName, fileInfoDTO);
        // 直接转移文件到指定路径
        file.transferTo(new File(newFile.getAbsolutePath()));
        return fileInfoDTO;
    }

    public File getFile(FileInfoDTO fileInfoDTO, Integer width, Integer height) throws IOException {
        File file = new File(fileInfoDTO.buildFilePath(basePath));
        if (width != null && height != null) {
            String fix = FilenameUtils.getExtension(file.getName());
            String resizeFileName = replaceLastOccurrence(file.getName(), "." + fix, "_" + width + "_" + height + "." + fix);
            File resizeFile = new File(file.getAbsolutePath() + resizeFileName);
            if (!resizeFile.exists()) {
                resizeImage(file, resizeFile, width, height);
            }
            return resizeFile;
        }
        return file;
    }


    /**
     * 根据指定的宽度和高度调整图片尺寸，并保存为新文件。
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @param width      目标宽度
     * @param height     目标高度
     * @throws IOException 处理图片时可能发生的I/O异常
     */
    public void resizeImage(File sourceFile, File targetFile, int width, int height) throws IOException {
        // 使用ByteArrayOutputStream保存调整后的图片数据到内存
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(sourceFile)
                .size(width, height)
                .keepAspectRatio(true) // 保持原图的宽高比
                .toOutputStream(outputStream);
        // 将内存中的字节数组写入到目标文件
        Files.write(targetFile.toPath(), outputStream.toByteArray(),
                StandardOpenOption.CREATE, // 如果文件不存在则创建
                StandardOpenOption.TRUNCATE_EXISTING, // 覆盖现有文件内容
                StandardOpenOption.WRITE); // 写入权限
    }

    private String getServer() {
        String buildUrl = UrlBuilder.create()
                .setScheme("http")
                .setHost(serverIp)
                .setPort(serverPort)
                .addPath("file")
                .build();
        return buildUrl;

    }

    private void checkBucket(String bucket) {
        //TODO 检测权限以及合法性
    }

    private void checkFix(String fix) {
        //TODO 检测权限以及合法性
    }

    private File createFile(String bucket, String fileName, FileInfoDTO fileInfoDTO) throws IOException {
        LocalDate currentDate = LocalDate.now();
        // 格式化日期，用于创建目录结构
        String dateTimeFormatterStr = "/";
        if (enableDateTimeFormat) {
            dateTimeFormatterStr = currentDate.format(dateTimeFormatter);
        }
        // 拼接完整的路径
        fileInfoDTO.setServer(getServer());
        fileInfoDTO.setBucket(bucket);
        fileInfoDTO.setKey(dateTimeFormatterStr + fileName);
        File file = new File(fileInfoDTO.buildFilePath(basePath));
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file;
    }

    /**
     * 替换字符串中最后一个匹配到的字符。
     *
     * @param originalStr 原始字符串
     * @param targetChar  要替换的目标字符
     * @param replacement 替换字符或字符串
     * @return 替换后的字符串
     */
    public static String replaceLastOccurrence(String originalStr, String targetChar, String replacement) {
        int index = originalStr.lastIndexOf(targetChar);
        if (index == -1) {
            return originalStr; // 如果没有找到目标字符，返回原字符串
        }
        return originalStr.substring(0, index) + replacement;
    }
}
