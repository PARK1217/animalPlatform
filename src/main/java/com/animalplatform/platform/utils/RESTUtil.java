package com.animalplatform.platform.utils;

import com.animalplatform.platform.log.JLog;
import com.squareup.okhttp.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RESTUtil {

    public String get(String strUrl, String key)   throws IOException{
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return doGET(strUrl, key, con);
    }

    public String get(String strUrl, String jsonString, String authKey, String token) throws IOException{
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("X-AUTH-TOKEN", token);
        return doGET(strUrl, authKey, con);
    }

    private static String doGET(String strUrl, String key, HttpURLConnection con) throws IOException {
        con.setConnectTimeout(30000); //서버에 연결되는 Timeout 시간 설정
        con.setReadTimeout(30000); // InputStream 읽어 오는 Timeout 시간 설정
        if(key !=null) con.addRequestProperty("Authorization", "Bearer "+ key); //key값 설정

        con.setRequestMethod("GET");

        //URLConnection에 대한 doOutput 필드값을 지정된 값으로 설정한다. URL 연결은 입출력에 사용될 수 있다. URL 연결을 출력용으로 사용하려는 경우 DoOutput 플래그를 true로 설정하고, 그렇지 않은 경우는 false로 설정해야 한다. 기본값은 false이다.
        con.setDoOutput(false);

        StringBuilder sb = new StringBuilder();
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            //Stream을 처리해줘야 하는 귀찮음이 있음.
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("");
            }
            br.close();
            JLog.logd("" + sb.toString());
            return sb.toString();
        } else {
            JLog.loge(con.getResponseCode()+" : "+ con.getResponseMessage() +" ["+ strUrl +"] ["+ key +"] ");
        }

        return null;
    }

    public String post(String strUrl, String jsonMessage, String key) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonMessage);
        Request request = null;
        if(key==null||key.equals("")){
            request = new Request.Builder()
                    .url(strUrl)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
        }else{
            request = null; new Request.Builder()
                    .url(strUrl)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+ key)
                    .build();
        }

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String post(String strUrl, String jsonMessage, String key, String token) throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(30000); //서버에 연결되는 Timeout 시간 설정
        con.setReadTimeout(30000); // InputStream 읽어 오는 Timeout 시간 설정
        if(key!=null)con.addRequestProperty("Authorization", "Bearer "+ key); //key값 설정

        con.setRequestMethod("POST");
        con.setRequestProperty("X-AUTH-TOKEN", token);

        //json으로 message를 전달하고자 할 때
        return doRest(strUrl, jsonMessage, key, con);
    }


    private static String doRest(String strUrl, String jsonMessage, String key, HttpURLConnection con) throws IOException {
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoInput(true);
        con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정
        con.setUseCaches(false);
        con.setDefaultUseCaches(false);

        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        if(jsonMessage !=null)wr.write(jsonMessage); //json 형식의 message 전달
        wr.flush();

        StringBuilder sb = new StringBuilder();
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK
                || con.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
            //Stream을 처리해줘야 하는 귀찮음이 있음.
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("");
            }
            br.close();
            //JLog.logd("" + sb.toString());
            return sb.toString();
        } else {
            JLog.loge(con.getResponseCode()+" : "+ con.getResponseMessage() +" ["+ strUrl +"] ["+ key +"] ::: " + jsonMessage);
        }
        return null;
    }

    public String toPatch(String usrStr, String jsonMessage, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonMessage);
        Request request = new Request.Builder()
                .url(usrStr)
                .patch(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-AUTH-TOKEN", token)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String put(String strUrl, String jsonMessage, String key) throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(30000); //서버에 연결되는 Timeout 시간 설정
        con.setReadTimeout(30000); // InputStream 읽어 오는 Timeout 시간 설정
        if(key!=null)con.addRequestProperty("Authorization", "Bearer "+ key); //key값 설정

        con.setRequestMethod("PUT");

        //json으로 message를 전달하고자 할 때
        return doRest(strUrl, jsonMessage, key, con);
    }

    public String delete(String strUrl,String reqBody, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, reqBody);
        Request request = new Request.Builder()
                .url(strUrl)
                .delete(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-AUTH-TOKEN", token)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public static void main(String[] args) throws Exception {
        RESTUtil ru = new RESTUtil();
        String strUrl="https://webexapis.com/v1/meetings";
        String jsonMessage ="{" +
                "\"title\":\"TEST_03\"," +
                "\"start\":\"2021-08-26 19:10:52\"," +
                "\"end\":\"2021-08-26 19:28:52\"," +
                "\"timezone\": \"Asia/Seoul\"," +
                "\"enabledAutoRecordMeeting\": false," +
                "  \"enabledJoinBeforeHost\":false," +
                "\"enableConnectAudioBeforeHost\" :false," +
                "\"AllowFirstUserToBeCoHost\":false," +
                " \"registration\" :  { " +
                "        \"requireFirstName\" :  \"true\" , " +
                "        \"requireLastName\" :  \"true\" ," +
                "        \"requireEmail\" :  \"true\" , " +
                "        \"requireCompanyName\":  \"true\" , " +
                "        \"requireCountryRegion\" :  \"true\" , " +
                "        \"requireWorkPhone\" :  \"true\" ," +
                " \"autoAcceptRequest\":\"true\"" +
                "    } " +
                "}";
        String access_token ="NDMyYjlkZTItM2FjNC00ODhhLWFhZWEtNzQwNzAxYjBlYTIxMjRlOTI4ZmUtYWVj_P0A1_cb5a5b29-3fc8-41df-9e13-7f1e41bb9760";
        String result = ru.post(strUrl, jsonMessage, access_token);
        System.out.println(result);
    }


}
