package io.github.biezhi.wechat.api.constant;

import java.util.Properties;

import static io.github.biezhi.wechat.api.constant.Constant.*;

/**
 * 微信API配置
 *
 * @author biezhi
 * @date 2018/1/18
 */
public class Config {

    /**
     * 资源存储的文件夹，包括图片、视频、音频
     */
    private static final String CONF_ASSETS_DIR         = "wechat.asstes-path";
    private static final String CONF_ASSETS_DIR_DEFAULT = "assets";

    /**
     * 是否输出二维码到终端
     */
    private static final String CONF_PRINT_TERMINAL         = "wechat.print-terminal";
    private static final String CONF_PRINT_TERMINAL_DEFAULT = "false";

    /**
     * 自动回复消息，测试时用
     */
    private static final String CONF_AUTO_REPLY         = "wechat.autoreply";
    private static final String CONF_AUTO_REPLY_DEFAULT = "false";

    /**
     * 自动登录
     */
    private static final String CONF_AUTO_LOGIN         = "wechat.autologin";
    private static final String CONF_AUTO_LOGIN_DEFAULT = "false";

    private Properties props = new Properties();

    public static Config me() {
        return new Config();
    }

    public String assetsDir() {
        return props.getProperty(CONF_ASSETS_DIR, CONF_ASSETS_DIR_DEFAULT);
    }

    public Config assetsDir(String dir) {
        props.setProperty(CONF_ASSETS_DIR, dir);
        return this;
    }

    public boolean showTerminal() {
        return Boolean.valueOf(props.getProperty(CONF_PRINT_TERMINAL, CONF_PRINT_TERMINAL_DEFAULT));
    }

    public Config showTerminal(boolean show) {
        props.setProperty(CONF_PRINT_TERMINAL, String.valueOf(show));
        return this;
    }

    public boolean autoReply() {
        return Boolean.valueOf(props.getProperty(CONF_AUTO_REPLY, CONF_AUTO_REPLY_DEFAULT));
    }

    public Config autoReply(boolean autoReply) {
        props.setProperty(CONF_AUTO_REPLY, String.valueOf(autoReply));
        return this;
    }

    public Config autoLogin(boolean autoLogin) {
        props.setProperty(CONF_AUTO_LOGIN, String.valueOf(autoLogin));
        return this;
    }

    public boolean autoLogin() {
        return Boolean.valueOf(props.getProperty(CONF_AUTO_LOGIN, CONF_AUTO_LOGIN_DEFAULT));
    }

}
