package book.be.gau.book_ha.configs.common;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {
  @Bean
  public ResourceBundleMessageSource message() {
    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasename("messgaes/messages");
    source.setDefaultEncoding("UTF-8");
    return source;
  }

  @Bean
  public ResourceBundleMessageSource regexPatternsSource() {
    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasename("regex-patterns");
    source.setDefaultEncoding("UTF-8");
    return source;
  }

  @Bean
  @Primary
  @SuppressWarnings("null")
  public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
    return new MessageSourceAccessor(messageSource);
  }

  @Bean
  @SuppressWarnings("null")
  public MessageSourceAccessor regexPatternsAccessor(
      @Qualifier("regexPatternsSource") MessageSource regexPatternsSource) {
    return new MessageSourceAccessor(regexPatternsSource);
  }
}
