package com.example.animalplatform.utils.xssFilter;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.compress.utils.IOUtils;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Map;
import java.util.regex.Pattern;

public class XSSFilterWrapper extends HttpServletRequestWrapper {

    private byte[] rawData;

    public XSSFilterWrapper(HttpServletRequest request) {
        super(request);
        try {
            InputStream inputStream = request.getInputStream();
            this.rawData = replaceXSS(IOUtils.toByteArray(inputStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // XSS replace
    private byte[] replaceXSS(byte[] data) {
        String strData = new String(data);
        strData = strData.replaceAll("\\<", "&lt;").replaceAll("\\>", "&gt;");

        return strData.getBytes();
    }

    private String replaceXSS(String value) {
        if(value != null) {
            value = value.replaceAll("\\<", "&lt;").replaceAll("\\>", "&gt;");
            value = value.replaceAll("\\'", "&apos;");
            value = value.replaceAll("\"", "&quot;");
            value = value.replaceAll("#", "&#35;");
            value = value.replaceAll("&", "&amp;");
            value = value.replaceAll("eval\\((.*)\\)", "");
            value = value.replaceAll("[\"\'][\\s]*javascript:(.*)[\"\']", "\"\"");
            value = value.replaceAll("script", "");
            value = value.replaceAll("vbscript", "");
            value = value.replaceAll("onreset", "");
            value = value.replaceAll("onmove", "");
            value = value.replaceAll("onstop", "");
            value = value.replaceAll("onrowsinserted", "");
            value = value.replaceAll("innerHTML", "");
            value = value.replaceAll("msgbox", "");
            value = value.replaceAll("onstart", "");
            value = value.replaceAll("onresize", "");
            value = value.replaceAll("onrowexit", "");
            value = value.replaceAll("onselect", "");
            value = value.replaceAll("onmousewheel", "");
            value = value.replaceAll("ondataavailable", "");
            value = value.replaceAll("onafterprint", "");
            value = value.replaceAll("onafterupdate", "");
            value = value.replaceAll("onmousedown", "");
            value = value.replaceAll("onbeforeactivate", "");
            value = value.replaceAll("ondatasetchanged", "");
            value = value.replaceAll("onbeforecopy", "");
            value = value.replaceAll("onbeforedeactivate", "");
            value = value.replaceAll("onbeforeeditfocus", "");
            value = value.replaceAll("onbeforepaste", "");
            value = value.replaceAll("onbeforeprint", "");
            value = value.replaceAll("onbeforeunload", "");
            value = value.replaceAll("onbeforeupdate", "");
            value = value.replaceAll("onpropertychange", "");
            value = value.replaceAll("ondatasetcomplete", "");
            value = value.replaceAll("oncellchange", "");
            value = value.replaceAll("onlayoutcomplete", "");
            value = value.replaceAll("onmousemove", "");
            value = value.replaceAll("oncontextmenu", "");
            value = value.replaceAll("oncontrolselect", "");
            value = value.replaceAll("onreadystatechange", "");
            value = value.replaceAll("onselectionchange", "");
            value = value.replaceAll("onactivate", "");
            value = value.replaceAll("oncopy", "");
            value = value.replaceAll("oncut", "");
            value = value.replaceAll("onclick", "");
            value = value.replaceAll("onchange", "");
            value = value.replaceAll("onbeforecut", "");
            value = value.replaceAll("ondblclick", "");
            value = value.replaceAll("ondeactivate", "");
            value = value.replaceAll("ondrag", "");
            value = value.replaceAll("ondragend", "");
            value = value.replaceAll("ondragenter", "");
            value = value.replaceAll("ondragleave", "");
            value = value.replaceAll("ondragover", "");
            value = value.replaceAll("ondragstart", "");
            value = value.replaceAll("ondrop", "");
            value = value.replaceAll("onerror", "");
            value = value.replaceAll("onerrorupdate", "");
            value = value.replaceAll("onfilterchange", "");
            value = value.replaceAll("onfinish", "");
            value = value.replaceAll("onfocus", "");
            value = value.replaceAll("onresizestart", "");
            value = value.replaceAll("onunload", "");
            value = value.replaceAll("onselectstart", "");
            value = value.replaceAll("onfocusin", "");
            value = value.replaceAll("onfocusout", "");
            value = value.replaceAll("onhelp", "");
            value = value.replaceAll("onkeydown", "");
            value = value.replaceAll("onkeypress", "");
            value = value.replaceAll("onkeyup", "");
            value = value.replaceAll("onrowsdelete", "");
            value = value.replaceAll("onload", "");
            value = value.replaceAll("onlosecapture", "");
            value = value.replaceAll("onbounce", "");
            value = value.replaceAll("onmouseenter", "");
            value = value.replaceAll("onmouseleave", "");
            value = value.replaceAll("onbefore", "");
            value = value.replaceAll("onmouseover", "");
            value = value.replaceAll("onmouseout", "");
            value = value.replaceAll("onmouseup", "");
            value = value.replaceAll("onresizeend", "");
            value = value.replaceAll("onabort", "");
            value = value.replaceAll("onmoveend", "");
            value = value.replaceAll("onmovestart", "");
            value = value.replaceAll("onrowenter", "");
            value = value.replaceAll("onsubmit", "");
            value = value.replaceAll("onblur", "");
        }
        return value;
    }

    //새로운 인풋스트림을 리턴하지 않으면 에러가 남
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if(this.rawData == null) {
            return super.getInputStream();
        }

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public boolean isReady() {
                return false;
            }


            @Override
            public boolean isFinished() {
                return false;
            }
        };
    }

    @Override
    public String getQueryString() {
        return replaceXSS(super.getQueryString());
    }


    @Override
    public String getParameter(String name) {
        return replaceXSS(super.getParameter(name));
    }


    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> params = super.getParameterMap();
        if(params != null) {
            params.forEach((key, value) -> {
                for(int i=0; i<value.length; i++) {
                    value[i] = replaceXSS(value[i]);
                }
            });
        }
        return params;
    }


    @Override
    public String[] getParameterValues(String name) {
        String[] params = super.getParameterValues(name);
        if(params != null) {
            for(int i=0; i<params.length; i++) {
                params[i] = replaceXSS(params[i]);
            }
        }
        return params;
    }


    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), "UTF-8"));
    }


    private static Pattern[] patterns = new Pattern[] {
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };

    private String stripXSS(String value) {
        if (value != null) {

            value = value.replaceAll("\0", "");

            for(Pattern scriptPattern : patterns){
                if(scriptPattern.matcher(value).matches()){
                    value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                }
            }
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("'","&apos;");
        }
        return value;
    }
}

