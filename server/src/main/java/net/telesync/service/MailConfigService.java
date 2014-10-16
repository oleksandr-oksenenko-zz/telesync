package net.telesync.service;

public interface MailConfigService {

    String getMailAddress();

    void saveNewMailAddress(String newMailAddress);
}
