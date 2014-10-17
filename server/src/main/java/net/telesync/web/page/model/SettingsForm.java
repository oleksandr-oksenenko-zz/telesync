package net.telesync.web.page.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SettingsForm {

    @NotNull(message = "Mail address cannot be empty.")
    @Pattern(regexp = ".*@.*", message = "Email format is not correct.")
    private String emailAddress;

    public SettingsForm() {
    }

    public SettingsForm(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
