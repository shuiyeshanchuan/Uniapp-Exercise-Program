package com.project.template.controller;

import cn.hutool.core.io.FileUtil;
import com.project.template.common.Result;
import com.project.template.dto.FileInfoDTO;
import com.project.template.exception.CustomException;
import com.project.template.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/file")
@Slf4j
public class FilesController {

    @Resource
    private FileService fileService;


    @PostMapping("/upload/{bucket}")
    public Result uploads(@RequestParam MultipartFile file, @PathVariable String bucket) throws IOException {
        return new Result().success(fileService.upload(file, bucket));
    }

    @GetMapping("/{bucket}/**")
    public ResponseEntity<Object> get(@PathVariable("bucket") String bucket,
                                      @RequestParam(value = "width", required = false) Integer width,
                                      @RequestParam(value = "height", required = false) Integer height,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        String key = extractKeyFromRequest(request, bucket);
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
        fileInfoDTO.setKey(key);
        fileInfoDTO.setBucket(bucket);

        try {
            File file = fileService.getFile(fileInfoDTO, width, height);
            return handleRangeRequest(request, response, file);
        } catch (FileNotFoundException e) {
            handleFileNotFound(response, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String extractKeyFromRequest(HttpServletRequest request, String bucket) {
        return request.getRequestURI().replaceFirst("/file/" + bucket + "/", "/");
    }


    private ResponseEntity<Object> handleRangeRequest(HttpServletRequest request, HttpServletResponse response, File file) throws IOException {
        String range = request.getHeader(HttpHeaders.RANGE);
        if (range == null) {
            setResponseHeaders(response, file);
            writeContentToResponse(response, file);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        long fileLength = file.length();
        long start = 0;
        long end = fileLength - 1;

        Pattern pattern = Pattern.compile("bytes=(\\d+)-(\\d*)");
        Matcher matcher = pattern.matcher(range);
        if (matcher.find()) {
            start = Long.parseLong(matcher.group(1));
            if (matcher.group(2) != null && !matcher.group(2).isEmpty()) {
                end = Long.parseLong(matcher.group(2));
            }
        }
        if (end > fileLength - 1) {
            end = fileLength - 1;
        }
        long contentLength = end - start + 1;

        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
        response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileLength);
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            randomAccessFile.seek(start);
            byte[] buffer = new byte[8192];
            int bytesRead;
            ServletOutputStream outputStream = response.getOutputStream();
            while ((bytesRead = randomAccessFile.read(buffer)) != -1 && contentLength > 0) {
                outputStream.write(buffer, 0, (int) Math.min(bytesRead, contentLength));
                contentLength -= bytesRead;
            }
            outputStream.flush();
        }

        return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
    }

    private void setResponseHeaders(HttpServletResponse response, File file) {
        // response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName());
        response.setContentType(getMimeType(file));
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
    }

    private String getMimeType(File file) {
        return new MimetypesFileTypeMap().getContentType(file);
    }

    private void writeContentToResponse(HttpServletResponse response, File file) throws IOException {
        try (ServletOutputStream os = response.getOutputStream()) {
            os.write(FileUtil.readBytes(file));
            os.flush();
        } catch (IOException e) {
            throw new CustomException("Error writing file to response output stream");
        }
    }

    private void handleFileNotFound(HttpServletResponse response, FileNotFoundException e) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
        log.error("File download failed, file not found：{}", e.getMessage());
    }
}