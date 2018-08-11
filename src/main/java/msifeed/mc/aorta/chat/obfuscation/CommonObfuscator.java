package msifeed.mc.aorta.chat.obfuscation;

import msifeed.mc.aorta.chat.parser.SpeechPart;

import java.util.List;
import java.util.stream.Collectors;

public class CommonObfuscator implements LangObfuscator {
    @Override
    public String obfuscate(List<SpeechPart> parts) {
        // TODO
        return parts.stream()
                .map(part -> part.text)
                .collect(Collectors.joining());

//        final String letters = joinLetters(parts);
//        final Random random = ObfuscationUtils.stringSeededRandom(letters);
//
//        final int wordCount = (int) parts.stream().filter(SpeechPart::isWord).count();
//        final int wordLength = Math.floorDiv(letters.length(), wordCount);
//
//        final LinkedList<String> words = new LinkedList<>();
//        for (int offset = 0; offset < letters.length(); ) {
//            final int maxLen = Math.min(8, wordLength);
//            final int minLen = Math.max(3, wordLength);
//            final int randLen = random.nextInt(maxLen - minLen) + minLen;
//            final int len = Math.max(randLen, letters.length() - offset);
//            final String sub = letters.substring(offset, len);
//            words.add(sub);
//            offset += len;
//        }
//
//        return parts.stream()
//                .map(part -> part.isWord() ? words.removeFirst() : part.text)
//                .collect(Collectors.joining());
    }

    private static String joinLetters(List<SpeechPart> parts) {
        return parts.stream()
                .filter(SpeechPart::isWord)
                .map(part -> part.text)
                .collect(Collectors.joining());
    }
}