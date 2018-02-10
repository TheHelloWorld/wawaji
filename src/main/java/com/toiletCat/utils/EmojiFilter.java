package com.toiletCat.utils;

import org.apache.commons.lang.StringUtils;

public class EmojiFilter {

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    private static boolean containsEmoji(String source) {

        if (StringUtils.isBlank(source)) {

            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {

            char codePoint = source.charAt(i);

            if (!isNotEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    private static boolean isNotEmojiCharacter(char codePoint)
    {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {

        source = source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");

        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }

        //到这里铁定包含
        StringBuilder buf = new StringBuilder("");

        int len = source.length();

        for (int i = 0; i < len; i++) {

            char codePoint = source.charAt(i);

            if (isNotEmojiCharacter(codePoint)) {

                buf.append(codePoint);
            }
        }

        if (StringUtils.isBlank(buf.toString())) {

            // 如果没有找到 emoji表情，则返回源字符串
            return source;

        } else {

            // 这里的意义在于尽可能少的toString，因为会重新生成字符串
            if (buf.length() == len) {

                buf = null;

                return source;

            } else {

                return buf.toString();
            }
        }

    }
}