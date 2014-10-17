package net.telesync.service;

public interface MailConfigService {

    String getMailAddress();

    void updateMailAddress(String newMailAddress);
}
