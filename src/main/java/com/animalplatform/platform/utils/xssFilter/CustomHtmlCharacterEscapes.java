package com.animalplatform.platform.utils.xssFilter;

import com.animalplatform.platform.utils.HtmlCharacterEscapes;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CustomHtmlCharacterEscapes extends HtmlCharacterEscapes {

    private static final String EMOJI_PREFIX = "\\u";
    private static final String EMOJI_UNICODE_FORMAT = "%04x";

    @Override
    public SerializableString getEscapeSequence(int ch) {
        char charAt = (char) ch;

        if (isEmojiSurrogate(charAt)) {
            StringBuffer sb = new StringBuffer()
                    .append(EMOJI_PREFIX)
                    .append(String.format(EMOJI_UNICODE_FORMAT, ch));
            return new SerializedString(sb.toString());
        } else {
            return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString(charAt)));
        }

    }

    /**
     * 이모지 유니코드 검사
     * @param charAt
     * @return
     */
    private static boolean isEmojiSurrogate(char charAt) {
        return Character.isHighSurrogate(charAt) || Character.isLowSurrogate(charAt);
    }
}