package computer.networks.configuration;

import computer.networks.service.ProfanityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class ProfanityConfig {

    private static Set<String> predefinedProfanities = new HashSet<>(Arrays.asList("fuck", "shit", "ass", "asshole"));

    @Bean
    public ProfanityFilter profanityFilter() {
        final ProfanityFilter checker = new ProfanityFilter();
        checker.setBlockedWords(predefinedProfanities);

        return checker;
    }
}