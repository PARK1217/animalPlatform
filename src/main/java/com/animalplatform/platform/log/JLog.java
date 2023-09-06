package com.animalplatform.platform.log;

import com.animalplatform.platform.session.constants.SessionConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 이용시 반드시 아래부분을 해 주시기 바랍니다.<br/>
 * 이 Class는 Apache log4j를 이용한 class입니다. 따라서 해당 Library를 반드시 추가시켜 주어야 합니다.<br/>
 * <br/>
 * Apache log4j의 lib와 설정 파일이 있는 folder와 file은 다음과 같습니다.<br/>
 * <b>Path : project/libs-log4j<br/>
 * Files : log4j-1.2.17.jar, log4j.properties<br/>
 * </b> <br/>
 * 위 파일을 각각 다음에 해당하는 Folder에 추가시켜 주시기 바랍니다. <br/>
 * [log4j-1.2.17.jar]<br/>
 * <b>-> project/WebContent/WEB-INF/lib<br/>
 * </b> [log4j.properties]<br/>
 * <b>-> project/src<br/>
 * </b>
 *
 */
public class JLog {
    private static final String TAG = "Log";

    private static Logger mLog = LoggerFactory.getLogger("debug_log");
    private static Logger mInfoLog = LoggerFactory.getLogger("info_log");
    private static Logger mWarnLog = LoggerFactory.getLogger("warn_log");
    private static Logger mErrorLog = LoggerFactory.getLogger("error_log");

    // private static FileAppender mFileAppender = null;

    protected interface LogLevel {
        public static int ERROR = 1;
        public static int WARN = ERROR + 1;
        public static int INFO = WARN + 1;
        public static int DEBUG = INFO + 1;
    }

    // private static final Logger mRootLogger = Logger.getRootLogger();
    // private static String mFilePath = "/";

    private static boolean DEBUG = true;
    // public static final boolean NET_DEBUG = false;
    // public static final boolean NET_LOG_DEBUG = false;
    // public static final boolean DB_LOG_DEBUG = false;
    public static final boolean FILE_DEBUG = true;

    // public final static boolean UI_DEVELOPMENT_MODE = false;

    private static void log(String msg) {
        System.out.println(msg);
    }

    public static void setDebugMode(boolean isDebug) {
        DEBUG = isDebug;
    }
    private static void filelog(String classname, int logLevel, String msg) {
        if (FILE_DEBUG == false) {
            return;
        }

        try {
            // Logger log = Logger.getLogger(classname);
            // log.info(classname);
            // Logger log = mRootLogger;
            // if (log != null) {
            // if (mFileAppender == null) {
            // mFileAppender = new FileAppender();
            // }
            //
            // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // String today = dateFormat.format(new Date());
            //
            // String filename = mFilePath + TAG + "-" + today + ".log";
            //
            // log.info(filename);
            // mFileAppender.setFile(filename);
            // }
            //
            // log.addAppender(mFileAppender);

            switch (logLevel) {
                case LogLevel.DEBUG:
                    if (mLog != null) {
                        mLog.debug(msg);
                    }
                    break;
                case LogLevel.INFO:
                    if (mInfoLog != null) {
                        mInfoLog.info(msg);
                    }
                    break;
                case LogLevel.WARN:
                    if (mWarnLog != null) {
                        mWarnLog.warn(msg);
                    }
                    break;
                case LogLevel.ERROR:
                    if (mErrorLog != null) {
                        mErrorLog.error(msg);
                    }
                    break;
            }
        } catch (Exception e) {

        }
    }

    protected static void filelog(int logLevel, String msg) {
        if (FILE_DEBUG == false) {
            return;
        }

        try {
            switch (logLevel) {
                case LogLevel.DEBUG:
                    if (mLog != null) {
                        mLog.debug(msg);
                    }
                    break;
                case LogLevel.INFO:
                    if (mInfoLog != null) {
                        mInfoLog.info(msg);
                    }
                    break;
                case LogLevel.WARN:
                    if (mWarnLog != null) {
                        mWarnLog.warn(msg);
                    }
                    break;
                case LogLevel.ERROR:
                    if (mErrorLog != null) {
                        mErrorLog.error(msg);
                    }
                    break;
            }
        } catch (Exception e) {

        }
    }

    protected static boolean checkEnableLog() {
        return (DEBUG /* || NET_DEBUG || NET_LOG_DEBUG */);
    }

    /**
     * <b>private static {@link StackTraceElement}
     * getCurrentStackTraceElement</b><br>
     * <br>
     * Returns StackTraceElement instance of calling
     * marblelotus.lotus.Lotus.logi().<br>
     * <br>
     * <br>
     * <b>[Stack table of current thread]</b><br>
     * [0]dalvik.system.VMStack.getThreadStackTrace<br>
     * [1]java.lang.Thread.getStackTrace<br>
     * [2]marblelotus.lotus.Lotus.getCurrentStackTraceElement<br>
     * [3]marblelotus.lotus.Lotus.logi<br>
     * [4] The method calling marblelotus.lotus.Lotus.logi()
     *
     * @return StackTraceElement instance of calling
     *         marblelotus.lotus.Lotus.logi()
     */
    protected static StackTraceElement getCurrentStackTraceElement() {
        StackTraceElement retStackTraceElement = null;
        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        // for(int i=0; i<stackTraceElements.length; i++) {
        // log(TAG, "[" + i + "]" + stackTraceElements[i].getClassName() + "."
        // + stackTraceElements[i].getMethodName() + " : "
        // + stackTraceElements[i].getLineNumber());
        // }

        retStackTraceElement = stackTraceElements[3];

        return retStackTraceElement;
    }

    private static String makeLogMsg(StackTraceElement stackTraceElement,
                                     String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append(stackTraceElement.getClassName()).append(".");
        sb.append(stackTraceElement.getMethodName()).append("()[")
                .append(Integer.toString(stackTraceElement.getLineNumber()))
                .append("]");

        if (msg != null) {
            sb.append(" ").append(msg);
        }

        return sb.toString();
    }

    private static String makeFileLogMsg(StackTraceElement stackTraceElement,
                                         String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append(stackTraceElement.getClassName()).append(".");
        sb.append(stackTraceElement.getMethodName()).append("()[")
                .append(Integer.toString(stackTraceElement.getLineNumber()))
                .append("]");

        if (msg != null) {
            sb.append(" ").append(msg);
        }

        return sb.toString();
    }

    // private static String makeLogMsg(StackTraceElement stackTraceElement) {
    // StringBuffer sb = new StringBuffer();
    // sb.append(stackTraceElement.getMethodName())
    // .append("()[")
    // .append(Integer.toString(stackTraceElement.getLineNumber()))
    // .append("]");
    //
    // return sb.toString();
    // }

    public static boolean isLoggable() {
        return checkEnableLog();
    }

    public static String getStackTraceElementString(
            StackTraceElement[] stackTraceElements) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < stackTraceElements.length; i++) {
            sb.append("[").append(Integer.toString(i)).append("] ")
                    .append(stackTraceElements[i].getClassName()).append(".")
                    .append(stackTraceElements[i].getMethodName())
                    .append("() : ")
                    .append(stackTraceElements[i].getLineNumber()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Shows information log.
     *
     * @param msg
     */
    public static void logi(String msg) {
        StackTraceElement stackTraceElement = getCurrentStackTraceElement();

        String logString = makeLogMsg(stackTraceElement, msg);

        //log(logString);

        filelog(stackTraceElement.getClassName(), LogLevel.INFO, logString);
    }

    /**
     * Shows information log.<br/>
     * But it just shows limited log like calling method.
     */
    public static void logi() {
        if (checkEnableLog()) {
            StackTraceElement stackTraceElement = getCurrentStackTraceElement();

            String logString = makeLogMsg(stackTraceElement, null);

            log(logString);

            filelog(LogLevel.INFO, logString);
        }
    }

    /**
     * Shows error log.
     *
     * @param msg
     */
    public static void loge(String msg) {
        StackTraceElement stackTraceElement = getCurrentStackTraceElement();

        String logString = makeLogMsg(stackTraceElement, msg);

        //log(logString);

        filelog(stackTraceElement.getClassName(), LogLevel.ERROR, logString);
    }

    /**
     * Shows error log.
     *
     * @param e
     */
    public static void loge(Exception e) {
        StackTraceElement stackTraceElement = getCurrentStackTraceElement();

        String logString = makeLogMsg(
                stackTraceElement,
                getStackTraceElementString(e.getStackTrace()) + "\n"
                        + e.getMessage());

        //log(logString);

        filelog(stackTraceElement.getClassName(), LogLevel.ERROR, logString);
    }

    /**
     * Exception 로그를 남긴다.
     * @param e
     */
    public static void loggingException(Exception e, HttpServletRequest request) {

        loge("========================================================");
        HttpSession session = request.getSession(false);

        if(session != null) {
            mErrorLog.error("request user.. = " + session.getAttribute(SessionConstants.SESSION_KEY));
        }

        StackTraceElement stackTraceElement = getCurrentStackTraceElement();

        String exceptionName = makeLogMsg(stackTraceElement, e.toString());

        mErrorLog.error(exceptionName);

        for (StackTraceElement stack : e.getStackTrace()) {
            loge(stack.toString());
        }

        loge("request.getRequestURI() : " + request.getRequestURI());
        loge("request.getRequestURL() : " + request.getRequestURL());
        loge("request.getHeader(\"REFERER\") : " +request.getHeader("REFERER"));
        loge("request.getRemoteHost() : " + request.getRemoteHost());
        loge("request.getQueryString() : " + request.getQueryString());
        loge("Error Message : " + e.getMessage() );
        loge("========================================================");
    }

    /**
     * Shows debug log.
     *
     * @param msg
     */
    public static void logd(String msg) {
        if (checkEnableLog()) {
            StackTraceElement stackTraceElement = getCurrentStackTraceElement();

            String logString = makeLogMsg(stackTraceElement, msg);

//            log(logString);

            filelog(stackTraceElement.getClassName(), LogLevel.DEBUG, logString);
        }
    }

    /**
     * Shows debug log.<br/>
     * But it just shows limited log like calling method.
     */
    public static void logd() {
        if (checkEnableLog()) {
            StackTraceElement stackTraceElement = getCurrentStackTraceElement();

            String logString = makeLogMsg(stackTraceElement, null);

//            log(logString);

            filelog(stackTraceElement.getClassName(), LogLevel.DEBUG, logString);
        }
    }

    /**
     * Shows warning log.
     *
     * @param msg
     */
    public static void logw(String msg) {
        StackTraceElement stackTraceElement = getCurrentStackTraceElement();

        String logString = makeLogMsg(stackTraceElement, msg);

//        log(logString);

        filelog(stackTraceElement.getClassName(), LogLevel.WARN, logString);
    }

    /**
     * Shows verbose log.
     *
     * @param msg
     */
    public static void logv(String msg) {
        if (checkEnableLog()) {
            StackTraceElement stackTraceElement = getCurrentStackTraceElement();

            String logString = makeLogMsg(stackTraceElement, msg);

//            log(logString);

            filelog(stackTraceElement.getClassName(), LogLevel.DEBUG, logString);
        }
    }

    /**
     * Shows verbose log.<br/>
     * But it just shows limited log like calling method.
     */
    public static void logv() {
        if (checkEnableLog()) {
            StackTraceElement stackTraceElement = getCurrentStackTraceElement();

            String logString = makeLogMsg(stackTraceElement, null);

//            log(logString);

            filelog(stackTraceElement.getClassName(), LogLevel.DEBUG, logString);
        }
    }
}


