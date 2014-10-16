package net.telesync;

import net.telesync.config.ServiceConfig;
import net.telesync.service.MailConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(classes = ServiceConfig.class)
public class MailServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private MailConfigService mailConfigService;

    public void testMailService() {
        final String mailAddress = mailConfigService.getMailAddress();
        System.out.println(mailAddress);
    }

}
