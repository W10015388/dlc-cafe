package com.tableorder.file.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileStorageServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void store_invalidExtension_throwsError() {
        FileStorageService service = new FileStorageService();
        setField(service, "uploadDir", tempDir.toString());
        service.init();

        FilePart filePart = mock(FilePart.class);
        when(filePart.filename()).thenReturn("test.gif");

        StepVerifier.create(service.store(filePart))
                .expectError()
                .verify();
    }

    @Test
    void store_validFile_returnsUrl() {
        FileStorageService service = new FileStorageService();
        setField(service, "uploadDir", tempDir.toString());
        service.init();

        FilePart filePart = mock(FilePart.class);
        when(filePart.filename()).thenReturn("test.png");
        when(filePart.transferTo(any(Path.class))).thenReturn(Mono.empty());

        StepVerifier.create(service.store(filePart))
                .expectNextMatches(url -> url.startsWith("/uploads/") && url.endsWith(".png"))
                .verifyComplete();
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
