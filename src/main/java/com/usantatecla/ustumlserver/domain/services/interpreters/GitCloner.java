package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;

public class GitCloner {

    private static final String PATH = "src/main/resources/projects/";

    public File clone(String url, String email) {
        String[] splitUrl = url.split("/");
        String projectName = splitUrl[splitUrl.length - 1];
        String path = GitCloner.PATH + email + "/" + projectName + "/";
        File file = new File(path);
        if(file.exists()) { // TODO Refactor. ¿Fachada File?
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                throw new ServiceException(ErrorMessage.CLONE_ERROR, e.getMessage());
            }
        }
        try {
            Git git = Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(new File(path))
                    .call();
            File directory = git.getRepository().getDirectory().getParentFile();
            git.getRepository().close();
            return directory;
        } catch(Exception e) {
            throw new ServiceException(ErrorMessage.CLONE_ERROR, e.getMessage());
        }

    }

}
