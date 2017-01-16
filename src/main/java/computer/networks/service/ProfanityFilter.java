package computer.networks.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ProfanityFilter {
    private Set<String> blockedWords = new HashSet<>();

    /**
     * Checks to see if the current message contains any profanities
     *
     * @param message Message to be ch
     * @return true when profanity count is higher than 0
     */
    public Boolean checkMessage(String message) {
        long profanityCount = Arrays.stream(message.split(" "))//
                .filter(word -> blockedWords.contains(word)) //
                .count();
        return profanityCount > 0;
    }


    public Set<String> getBlockedWords() {
        return blockedWords;
    }

    public void setBlockedWords(Set<String> blockedWords) {
        this.blockedWords = blockedWords;
    }
}
