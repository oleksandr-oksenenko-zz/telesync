package net.telesync.service.impl;

import net.telesync.exception.InvalidUpdateException;
import net.telesync.service.MailConfigService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;

@Service
@Transactional(readOnly = true)
public class MailConfigServiceImpl implements MailConfigService {

    private static final String GET_MAIL_ADDRESS_SQL = "select value from mail_config where key = 'mail_address'";

    private static final String UPDATE_MAIL_ADDRESS_SQL = "update mail_config set value = ? where key = 'mail_address'";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getMailAddress() {
        return jdbcTemplate.queryForObject(GET_MAIL_ADDRESS_SQL, String.class);
    }

    @Override
    @Transactional
    public void updateMailAddress(String newMailAddress) {
        int rowsUpdated = jdbcTemplate.update(UPDATE_MAIL_ADDRESS_SQL, HtmlUtils.htmlEscape(newMailAddress));
        if (rowsUpdated != 1) {
            throw new InvalidUpdateException("Update failed with rowsUpdated = " + rowsUpdated);
        }
    }
}
