package com.library.libraryapi.Controller;

import com.library.libraryapi.DAO.FileRepository;
import com.library.libraryapi.Model.File;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class FileController {

    private final FileRepository fileRepository;
    private HttpHeaders headers;

    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.asMediaType(MediaType.IMAGE_JPEG));
    }

    @RequestMapping(name = "/file/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable (name = "id") Long id) {

        Optional<File> file;

        if (id == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Empty id"));
        }

        file = fileRepository.findById(id);

        File f = new File();
        f.setId(file.map(File::getId).orElse(null));
        f.setFile(file.map(File::getFile).orElse(null));

        if (f.getId() == 0 ) {
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }

        if (f.getFile() == null || f.getFile().length == 0) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Faile is broken"));
        }

        ByteArrayResource resource = new ByteArrayResource(f.getFile());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(f.getFile().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);

    }
}
