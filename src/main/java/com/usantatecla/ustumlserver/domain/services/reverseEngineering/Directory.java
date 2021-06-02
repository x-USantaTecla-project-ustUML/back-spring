package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.IOException;

public class Directory {

    private File file;

    public Directory(String path) {
        this.file = new File(path);
        // this(new File(path));
    }

    public Directory(File file) {
        this.file = file;
        if (!this.file.exists() || !this.file.isDirectory()) {
            throw new ServiceException(ErrorMessage.DIRECTORY_NOT_FOUND, this.getPath());
        }
    }

    public void delete() {
        if (this.file.exists()) {
            try {
                FileUtils.deleteDirectory(this.file);
            } catch (IOException e) {
                throw new ServiceException(ErrorMessage.UNABLE_DELETE_FILE, e.getMessage());
            }
        }
    }

    public String getName() {
        return this.file.getName();
    }

    public String getPath() {
        return this.file.getPath();
    }

    public File[] listFiles() {
        if(this.file.listFiles() == null) {
            throw new ServiceException(ErrorMessage.DIRECTORY_NOT_FOUND, this.getPath());
        }
        return this.file.listFiles();
    }

    public File getFile() {
        return this.file;
    }
}
