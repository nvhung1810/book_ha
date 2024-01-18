package book.be.gau.book_ha.utils;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
  @Autowired

  private MessageSourceAccessor regexs;

  public boolean isValidPassword(String login_password) {
    String regexPassword = regexs.getMessage("check.password");
    return Pattern.compile(regexPassword).matcher(login_password).matches();
  }
}
