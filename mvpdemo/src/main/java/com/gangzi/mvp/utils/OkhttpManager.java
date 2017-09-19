package com.gangzi.mvp.utils;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dan on 2017/8/8.
 */

public class OkhttpManager {

    private static OkhttpManager mOkhttpManager;
    private static OkHttpClient mOkHttpClient;
    private static final int READ_TIMEOUT=10;
    private static final int WRITE_TIMEOUT=10;
    private static final int CONNECT_TIMEOUT=10;

    private static final int POST=1;
    private static final int GET=0;



    private static final MediaType DEFAULT = MediaType.parse("application/x-www-form-urlencoded");

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

   private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case POST:
                    String postResult=msg.obj.toString();
                    mListener.onRequestSuccess(postResult);
                    break;
                case GET:
                    String getResult=msg.obj.toString();
                    mListener.onRequestSuccess(getResult);
                    break;
            }
        }
    };


    public OkhttpManager() {
        mOkHttpClient=new OkHttpClient().newBuilder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS).build();//设置连接超时时间
    }
    public static OkhttpManager getInstance(){
        if (mOkhttpManager==null){
            synchronized (OkhttpManager.class){
                if (mOkhttpManager==null){
                    mOkhttpManager=new OkhttpManager();
                }
            }
        }
        return mOkhttpManager;
    }

    private static String getParamUrl(final Map<String,Object> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }

        final StringBuilder sb = new StringBuilder();
        for (String key : params.keySet())
            sb.append(key).append('=').append(params.get(key)).append('&');
//		params.forEach((key, value) -> sb.append(key).append('=').append(value).append('&'));
        return sb.substring(0, sb.length() - 1);
    }

    public  String get(final String url) {

        return get(url, null);
    }

    /**
     * 同步get请求
     * @param url
     * @param params
     * @return
     */
    public String get(final String url, final Map<String, Object> params) {
        final String paramUrl = getParamUrl(params);
        final String newUrl = TextUtils.isEmpty(paramUrl) ? url : url + "?" + paramUrl;
        Request.Builder requestBuilder = new Request.Builder().url(newUrl);
//		if (headers != null) {
//			headers.forEach((key, value) -> requestBuilder.addHeader(Utils.parseString(key), Utils.parseString(value)));
//		}
       // 添加请求头
        //okhttp3添加请求头，需要在Request.Builder()使用.header(String key,String value)或者.addHeader(String key,String value);
        //使用.header(String key,String value)，如果key已经存在，将会移除该key对应的value，然后将新value添加进来，即替换掉原来的value；
       // 使用.addHeader(String key,String value)，即使当前的可以已经存在值了，只会添加新value的值，并不会移除/替换原来的值。
        Request request = requestBuilder.build();

        Response response;
        try {
            response = mOkHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 异步get请求
     * @param url
     * @param params
     */
    public  void doGet(final String url, final Map<String, Object> params, final NetworkRequestListener mListeners) {
        final String paramUrl = getParamUrl(params);
        final String newUrl = TextUtils.isEmpty(paramUrl) ? url : url + "?" + paramUrl;
        Request.Builder requestBuilder = new Request.Builder().url(newUrl);
//		if (headers != null) {
//			headers.forEach((key, value) -> requestBuilder.addHeader(Utils.parseString(key), Utils.parseString(value)));
//		}
        // 添加请求头
        //okhttp3添加请求头，需要在Request.Builder()使用.header(String key,String value)或者.addHeader(String key,String value);
        //使用.header(String key,String value)，如果key已经存在，将会移除该key对应的value，然后将新value添加进来，即替换掉原来的value；
        // 使用.addHeader(String key,String value)，即使当前的可以已经存在值了，只会添加新value的值，并不会移除/替换原来的值。
        final Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                mListeners.onRequestFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String result=response.body().string();
                    mListener=mListeners;
                    Message message=Message.obtain();
                    message.what=GET;
                    message.obj=result;
                    mHandler.sendMessage(message);
                  //  mListeners.onRequestSuccess(result);
                }
            }
        });
    }

    /**
     *  同步post请求
     * @param url
     * @param params
     * @return
     */
    public String sendPost(final String url, final Map<String, Object> params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                String value =String.valueOf(params.get(key));
                if(value == null)
                    value = "";
                //   LogUtil.d(String.format("%s:%s", key, value), DBG);
                formBodyBuilder.add(key, value);
            }
//			params.forEach((key, value) -> formBodyBuilder.add(key, value));
        }
        RequestBody body = formBodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
//		if (headers != null) {
//			headers.forEach((key, value) -> requestBuilder.addHeader(key, value));
//		}
        // 添加请求头
        //okhttp3添加请求头，需要在Request.Builder()使用.header(String key,String value)或者.addHeader(String key,String value);
        //使用.header(String key,String value)，如果key已经存在，将会移除该key对应的value，然后将新value添加进来，即替换掉原来的value；
        // 使用.addHeader(String key,String value)，即使当前的可以已经存在值了，只会添加新value的值，并不会移除/替换原来的值。
        Request request = requestBuilder.build();

        Response response;
        try {
            response = mOkHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseData = response.body().string();
            //LogUtil.d("responseData:" + responseData, DBG);
            return responseData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 异步post请求
     * @param url
     * @param params
     */
    public void doPost(final String url, final Map<String, Object> params,final NetworkRequestListener mListeners) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                String value =String.valueOf(params.get(key));
                if(value == null)
                    value = "";
                //   LogUtil.d(String.format("%s:%s", key, value), DBG);
                formBodyBuilder.add(key, value);
            }
//			params.forEach((key, value) -> formBodyBuilder.add(key, value));
        }
        RequestBody body = formBodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
//		if (headers != null) {
//			headers.forEach((key, value) -> requestBuilder.addHeader(key, value));
//		}
        // 添加请求头
        //okhttp3添加请求头，需要在Request.Builder()使用.header(String key,String value)或者.addHeader(String key,String value);
        //使用.header(String key,String value)，如果key已经存在，将会移除该key对应的value，然后将新value添加进来，即替换掉原来的value；
        // 使用.addHeader(String key,String value)，即使当前的可以已经存在值了，只会添加新value的值，并不会移除/替换原来的值。
        Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mListeners.onRequestFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String result=response.body().string();
                    mListener=mListeners;
                    Message message= Message.obtain();
                    message.what=GET;
                    message.obj=result;
                    mHandler.sendMessage(message);
                   // mListeners.onRequestSuccess(result);
                }
            }
        });
    }
    /**
     * post请求提交json数据到服务器
     * @param url
     * @param params 请求参数
     * @return
     */
    public void sendJsonDataPost(final String url, final String params,final NetworkRequestListener mListeners) {
        RequestBody body = RequestBody.create(JSON, params);
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mListeners.onRequestFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String result=response.body().string();
                    //mListener=mListeners;
                    mListeners.onRequestSuccess(result);
                }
            }
        });
    }

    /**
     * 上传文件
     *
     * @param url
     * @param pathName
     * @param fileName
     * @param callback
     */
    public void doFile(String url, String pathName, String fileName, Callback callback) {
        //判断文件类型
        MediaType MEDIA_TYPE = MediaType.parse(judgeType(pathName));
        //创建文件参数
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(MEDIA_TYPE.type(), fileName,
                        RequestBody.create(MEDIA_TYPE, new File(pathName)));
        //发出请求参数
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "9199fdef135c122")
                .url(url)
                .post(builder.build())
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 根据文件路径判断MediaType
     *
     * @param path
     * @return
     */
    private static String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 异步的单文件上传 携带参数
     * @param url
     * @param file
     * @param maps
     */
    public void doPostAsynFile(String url, File file, Map<String, String> maps, final OnUploadListener uploadListener){

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (maps!=null){
            for (String key:maps.keySet()){
               // builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\""+fileName+"\""));
                builder.addFormDataPart(key,maps.get(key));
            }
        }

        if (file!=null){
            RequestBody fileBody = null;
            String fileName = file.getName();
            fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
            builder.addPart( Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\""+fileName+"\""),
                    fileBody);
        }
        RequestBody requestBody = builder.build();
        Request.Builder requestBuilder=new Request.Builder().url(url).post(requestBody);
        Request request=requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                uploadListener.onUploadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    uploadListener.onUploadSuccess();
                }
            }
        });
    }

    /**
     * 异步多文件上传
     * @param url
     * @param files
     * @param fileKeys
     * @param maps
     */
    public void doPostAsynFile(String url, File[] files, String[] fileKeys,Map<String, String> maps, final OnUploadListener uploadListener){

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (maps!=null){
            for (String key:maps.keySet()){
                // builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\""+fileName+"\""));
                builder.addFormDataPart(key,maps.get(key));
            }
        }
        if (files != null)
        {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }
        RequestBody requestBody = builder.build();
        Request.Builder requestBuilder=new Request.Builder().url(url).post(requestBody);
        Request request=requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                uploadListener.onUploadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    uploadListener.onUploadSuccess();
                }
            }
        });

    }

    /**
     * 根据文件路径判断MediaType
     * @param path
     * @return
     */
    private String guessMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 下载文件
     * @param url
     * @param fileDir 文件的保存路径
     */
    public  void downFile(final String url,final String fileName, final String fileDir, final OnDownloadListener downloadListener) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                downloadListener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                final long fileLength = response.body().contentLength();
                int len = 0;
                long readLength = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(fileDir,fileName);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        readLength += len;
                        int curProgress = (int) (((float) readLength / fileLength) * 100);
                        downloadListener.onDownloading(curProgress,readLength, fileLength, 0);
                    }
                    fos.flush();
                    downloadListener.onDownloadSuccess();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
        });
    }

    /**
     * 根据path得到文件名
     * @param path
     * @return
     */
    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }


    private static NetworkRequestListener mListener;

    public void setListener(NetworkRequestListener listener) {
        mListener = listener;
    }

   public interface NetworkRequestListener{
        /**
         * 网络请求前回调,进行加载框的展示
         */
        //void onRequestBefore();

        /**
         * 网络请求成功后回调，进行数据的处理和展示
         * @param result
         */
        void onRequestSuccess(String result);

        /**
         * 网络请求失败后回调，提示请求失败信息
         * @param msg
         */
        void onRequestFail(String msg);

        /**
         * 网络请求完成后回调，关闭加载框
         */
       // void onRequestComplete();
   }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress
         * 下载进度
         */
       // void onDownloading(int progress);
        void onDownloading(int progress, long readLenght, long total, int id);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

    public interface OnUploadListener {
        /**
         * 上传成功
         */
        void onUploadSuccess();

        /**
         * @param progress
         * 上传进度
         */
        // void onDownloading(int progress);
        //void onDownloading(int progress,long readLenght,long total , int id);

        /**
         * 上传失败
         */
        void onUploadFailed();
    }

}
