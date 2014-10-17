package net.telesync.web.page;

import net.telesync.service.MailConfigService;
import net.telesync.web.page.model.SettingsForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

import static org.springframework.util.StringUtils.isEmpty;

@Controller
@RequestMapping("/")
public class PagesController {

    @Resource
    private MailConfigService mailConfigService;

    @RequestMapping(method = RequestMethod.GET)
    public String showHomePage() {
        return "index";
    }

    @ModelAttribute("isEmailSet")
    public boolean isEmailSet() {
        return !isEmpty(mailConfigService.getMailAddress());
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String showSettingsPage(Model model) {
        String mailAddress = mailConfigService.getMailAddress();

        if (mailAddress != null && !mailAddress.isEmpty()) {
            model.addAttribute("settings", new SettingsForm(mailAddress));
        } else {
            model.addAttribute("settings", new SettingsForm());
        }

        return "settings";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String updateMailAddress(@Valid @ModelAttribute("settings") SettingsForm settingsForm,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            return "settings";
        }

        mailConfigService.updateMailAddress(settingsForm.getEmailAddress());

        return "redirect:/";
    }
}
