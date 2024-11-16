package com.project.template.service;

import com.project.template.dto.FileInfoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileService {

    FileInfoDTO upload(MultipartFile file, String bucket) throws IOException;

    File getFile(FileInfoDTO fileInfoDTO, Integer width, Integer height) throws IOException;
}
