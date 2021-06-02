package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.eclipse.jgit.api.Git;

public class GitCloner {

    private static final String PATH = "src/main/resources/projects/";

    public Directory clone(String url, String email) {
        String[] splitUrl = url.split("/");
        String projectName = splitUrl[splitUrl.length - 1];
        Directory directory = new Directory(GitCloner.PATH + email + "/" + projectName);
        directory.delete();
        try {
            Git git = Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(directory.getFile())
                    .call();
            git.getRepository().close();
            return directory;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessage.CLONE_ERROR, e.getMessage());
        }
    }

}
