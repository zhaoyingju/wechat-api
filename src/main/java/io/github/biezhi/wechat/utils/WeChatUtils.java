package io.github.biezhi.wechat.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vdurmont.emoji.EmojiParser;
import io.github.biezhi.wechat.exception.WeChatException;

import java.io.*;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信公共静态方法
 *
 * @author biezhi
 * @date 2018/1/19
 */
public class WeChatUtils {

    private static final Gson GSON        = new Gson();
    private static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 正则匹配
     *
     * @param p
     * @param str
     * @return
     */
    public static String match(String reg, String text) {
        Pattern pattern = Pattern.compile(reg);
        Matcher m       = pattern.matcher(text);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public static Matcher matcher(String reg, String text) {
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(text);
    }

    /**
     * 格式化微信消息
     * <p>
     * 处理emoji表情、HTML转义符
     *
     * @param msg
     * @return
     */
    public static String formatMsg(String msg) {
        msg = msg.replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("<br/>", "\n");
        return emojiParse(msg);
    }

    /**
     * 获取文件 MimeType
     *
     * @param fileUrl
     * @return
     */
    public static String getMimeType(String fileUrl) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String      type        = fileNameMap.getContentTypeFor(fileUrl);
        return type;
    }

    public static String emojiParse(String text) {
        Matcher       m         = matcher("<span class=\"emoji emoji(.{1,10})\"></span>", text);
        StringBuilder sb        = new StringBuilder();
        int           lastStart = 0;
        while (m.find()) {
            String str = m.group(1);
            if (str.length() == 6) {

            } else if (str.length() == 10) {

            } else {
                str = "&#x" + str + ";";
                String tmp = text.substring(lastStart, m.start());
                sb.append(tmp + str);
                lastStart = m.end();
            }
        }
        if (lastStart < text.length()) {
            sb.append(text.substring(lastStart));
        }
        if (sb.length() > 0) {
            return EmojiParser.parseToUnicode(sb.toString());
        }
        return text;
    }

    public static String toPrettyJson(Object bean) {
        return PRETTY_GSON.toJson(bean);
    }

    public static String toJson(Object bean) {
        return GSON.toJson(bean);
    }

    public static <T> T fromJson(String json, Class<T> from) {
        return GSON.fromJson(json, from);
    }

    public static <T> T fromJson(String json, Type from) {
        return GSON.fromJson(json, from);
    }

    public static <T> T fromJson(FileReader fileReader, Type from) {
        return GSON.fromJson(fileReader, from);
    }

    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        return GSON.fromJson(json, typeToken.getType());
    }

    public static File saveFile(InputStream inputStream, String dirPath, String id) {
        return saveFileByDay(inputStream, dirPath, id, false);
    }

    public static File saveFileByDay(InputStream inputStream, String dirPath, String id, boolean byDay) {
        FileOutputStream fileOutputStream = null;
        try {
            if (byDay) {
                dirPath = dirPath + "/" + DateUtils.getDateString();
            }
            File dir = new File(dirPath);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
            File path = new File(dir, id);
            fileOutputStream = new FileOutputStream(path);
            byte[] buffer = new byte[2048];
            int    len    = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
            return path;
        } catch (Exception e) {
            throw new WeChatException(e);
        } finally {
            try {
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public static <T> void writeJson(String file, T data) {
        try {
            FileWriter writer = new FileWriter(file);
            GSON.toJson(data, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
