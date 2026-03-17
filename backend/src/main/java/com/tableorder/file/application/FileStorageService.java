package com.tableorder.file.application;

import com.tableorder.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("업로드 디렉토리를 생성할 수 없습니다", e);
        }
    }

    public Mono<String> store(FilePart file) {
        String filename = file.filename();
        String ext = getExtension(filename).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            return Mono.error(new BusinessException("허용되지 않는 파일 형식입니다. JPG, PNG만 가능합니다."));
        }

        String storedName = UUID.randomUUID() + "." + ext;
        Path target = uploadPath.resolve(storedName);

        return file.transferTo(target)
                .then(Mono.just("/uploads/" + storedName));
    }

    private String getExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        return dot > 0 ? filename.substring(dot + 1) : "";
    }
}
