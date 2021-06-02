package com.usantatecla.ustumlserver.infrastructure.api.dtos;

public enum ErrorMessage {

    COMMAND_NOT_FOUND("The command %s does not exists"),
    KEY_NOT_FOUND("%s is required"),
    INVALID_VALUE("The value of %s is not valid"),
    INVALID_ARRAY_VALUE("The value of a member is not valid"),
    MEMBER_TYPE_NOT_FOUND("The member type of %s does not exists"),
    INVALID_NAME("%s is not a valid name"),
    INVALID_CLASS_MODIFIERS("%s is not valid modifiers"),
    INVALID_CLASS_MEMBER("%s is not a valid member"),
    MEMBER_ALREADY_EXISTS("The member %s already exists in this package"),
    FILE_NOT_FOUND("The file %s wasn't found"),
    MEMBER_NOT_FOUND("The member %s does not exists in this package"),
    MEMBER_NOT_ALLOWED("You can't add %s in this scope"),
    OPEN_NOT_ALLOWED("This member cannot be open"),
    CLOSE_NOT_ALLOWED("This member cannot be closed"),
    IMPORT_NOT_ALLOWED("This member cannot import a project"),
    USER_NOT_FOUND("The user %s does not exist"),
    EMAIL_ALREADY_EXISTS("There is another user with this email %s"),
    SESSION_NOT_FOUND("There session %s does not exists"),
    UNABLE_DELETE_FILE("Failed to delete file: %s"),
    CLONE_ERROR("There was an error cloning the repository: %s"),
    DIRECTORY_NOT_FOUND("Couldn't find directory: %s"),
    INVALID_FILE_EXTENSION("The file %s doesn't have the correct extension"),
    NULL("");

    private String detail;

    ErrorMessage(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return this.detail;
    }

    public boolean isNull() {
        return this == ErrorMessage.NULL;
    }

}
