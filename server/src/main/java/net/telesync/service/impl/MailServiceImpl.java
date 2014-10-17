package net.telesync.service.impl;

import net.telesync.exception.MailAddressIsNotSetException;
import net.telesync.model.DeviceInfo;
import net.telesync.service.MailConfigService;
import net.telesync.service.MailService;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    private static final String API_URL = "https://api.mailgun.net/v2/";
    private static final String API_SECRET_KEY = "key-734af18022a74d11570c75e9ce970fab";
    private static final String MAIL_DOMAIN = "sandbox6f5677b336d3404a9a909cadb04d324b.mailgun.org";
    private static final String USERNAME = "postmaster@sandbox6f5677b336d3404a9a909cadb04d324b.mailgun.org";
    private static final String PASSWORD = "70638d1dc7abf1839ec7fc0b267a0651";
    private static final String MESSAGE_TEMPLATE = "Devices %s are not responding";
    private final Configuration config;
    private final Form postData;

    @Resource
    private MailConfigService mailConfigService;

    public MailServiceImpl() {
        HttpAuthenticationFeature httpAuth = HttpAuthenticationFeature.basic("api", API_SECRET_KEY);

        config = new ClientConfig(JacksonFeature.class, httpAuth);

        postData = new Form();
        postData.param("from", USERNAME);
        postData.param("subject", "Device alert");
    }

    @Override
    public Response sendAlertMessage(List<DeviceInfo> devices) {
        String mailAddress = mailConfigService.getMailAddress();
        if (mailAddress == null || mailAddress.isEmpty()) {
            throw new MailAddressIsNotSetException();
        }

        StringBuilder messageText = new StringBuilder();
        for (int i = 0; i < devices.size(); i++) {
            if (i != devices.size() - 1) {
                messageText.append(String.format("%d, ", devices.get(i).getId()));
            } else {
                messageText.append(devices.get(i).getId());
            }
        }
        postData.param("to", mailAddress);
        postData.param("text", String.format(MESSAGE_TEMPLATE, messageText.toString()));

        return ClientBuilder.newClient(config)
                .target(API_URL)
                .path(MAIL_DOMAIN)
                .path("messages")
                .request()
                .post(Entity.entity(postData, MediaType.APPLICATION_FORM_URLENCODED));
    }
}
