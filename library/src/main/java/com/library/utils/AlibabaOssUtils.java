//package com.library.utils;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//
//import com.alibaba.sdk.android.oss.ClientException;
//import com.alibaba.sdk.android.oss.OSS;
//import com.alibaba.sdk.android.oss.OSSClient;
//import com.alibaba.sdk.android.oss.ServiceException;
//import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
//import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
//import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
//import com.alibaba.sdk.android.oss.model.PutObjectRequest;
//import com.alibaba.sdk.android.oss.model.PutObjectResult;
//import com.library.http.JsonResult;
//import com.library.http.RequestHeaderInterceptor;
//import com.library.http.RequestLogInterceptor;
//import com.orhanobut.hawk.Hawk;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.http.POST;
//
///**
// * Author : zhouyx
// * Date   : 2017/9/21
// * Description : 阿里云存储工具
// */
//public final class AlibabaOssUtils {
//
//    private static final String TAG = AlibabaOssUtils.class.getSimpleName();
//    private static final AlibabaOssUtils ourInstance = new AlibabaOssUtils();
//    private static final String API_URL = "http://119.29.157.217:8353/api/api/";
//
//    public static AlibabaOssUtils getInstance() {
//        return ourInstance;
//    }
//
//    private AlibabaOssUtils() {
//    }
//
//    private Handler handler = new Handler(Looper.getMainLooper());
//    private String bucketName;
//    private String objectKey;
//    private String resourceUrl;
//    private OSS oss;
//
//    /**
//     * 初始化
//     *
//     * @param context
//     */
//    public void init(final Context context) {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(new RequestLogInterceptor())
//                .addInterceptor(new RequestHeaderInterceptor())
//                .build();
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(client)
//                .baseUrl(API_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        requestOssMessage(context, retrofit);
//    }
//
//    /**
//     * 获取Oss信息并初始化
//     *
//     * @param context
//     * @param retrofit
//     */
//    private void requestOssMessage(final Context context, final Retrofit retrofit) {
//        Log.d(TAG, "requestOssMessage");
//        retrofit.create(AlibabaApi.class).getUploads().enqueue(new CallBack<AlibabaBean>() {
//            @Override
//            public void success(AlibabaBean response) {
//                OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(response.getAccessKeyId(),
//                        response.getAccessKeySecret());
//                String endpoint = response.getEndpoint().replace(response.getBucketName() + ".", "");
//                oss = new OSSClient(context.getApplicationContext(), endpoint, credentialProvider);
//                bucketName = response.getBucketName();
//                if (response.getFiledir().endsWith(File.separator)) {
//                    objectKey = response.getFiledir();
//                } else {
//                    objectKey = response.getFiledir() + File.separator;
//                }
//                if (response.getEndpoint().endsWith(File.separator)) {
//                    resourceUrl = response.getEndpoint();
//                } else {
//                    resourceUrl = response.getEndpoint() + File.separator;
//                }
//            }
//
//            @Override
//            public void fail(int code, String message) {
//                //如请求失败1s后重连请求
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        requestOssMessage(context, retrofit);
//                    }
//                }, 1000);
//            }
//        });
//    }
//
//    /**
//     * 上传单个文件
//     *
//     * @param param
//     * @param filePath
//     * @param callback
//     */
//    public void uploadPath(String param, String filePath, UploadCallback callback) {
//        uploadFile(param, new File(filePath), callback);
//    }
//
//    /**
//     * 上传单个文件
//     *
//     * @param param
//     * @param file
//     * @param callback
//     */
//    public void uploadFile(String param, File file, UploadCallback callback) {
//        if (!file.exists()) {
//            Log.e(TAG, "File is nonexistent");
//            callback.complete(null);
//            return;
//        }
//        List<String> keyData = new ArrayList<>();
//        List<File> fileData = new ArrayList<>();
//        fileData.add(file);
//        uploadRequest(param, keyData, fileData, callback);
//    }
//
//    /**
//     * 上传多个文件
//     *
//     * @param param
//     * @param filePaths
//     * @param callback
//     */
//    public void uploadPaths(String param, List<String> filePaths, UploadCallback callback) {
//        if (filePaths == null) {
//            Log.e(TAG, "filePaths is null ");
//            callback.complete(null);
//            return;
//        }
//        List<File> files = new ArrayList<>();
//        for (String filepath : filePaths) {
//            File file = new File(filepath);
//            if (!file.exists()) {
//                Log.e(TAG, "File is nonexistent");
//                callback.complete(null);
//                return;
//            }
//            files.add(file);
//        }
//        uploadFiles(param, files, callback);
//    }
//
//    /**
//     * 上传多个文件
//     *
//     * @param param
//     * @param files
//     * @param callback
//     */
//    public void uploadFiles(String param, List<File> files, UploadCallback callback) {
//        if (files == null) {
//            callback.complete(null);
//            Log.e(TAG, "files is null");
//            return;
//        }
//        List<String> keyData = new ArrayList<>();
//        uploadRequest(param, keyData, files, callback);
//    }
//
//    /**
//     * 网络请求
//     *
//     * @param param
//     * @param keyData
//     * @param fileData
//     * @param callback
//     */
//    private void uploadRequest(final String param, final List<String> keyData, final List<File> fileData, final UploadCallback callback) {
//        if (oss == null) {
//            Log.e(TAG, "Oss hasn't init !");
//            return;
//        }
//        if (keyData.size() == fileData.size()) {
//            callback.complete(keyData);
//            return;
//        }
//        int index = keyData.size();
//
//        String filePath = fileData.get(index).getAbsolutePath();
//        File file = new File(filePath);
//        // 文件后缀
//        String fileSuffix = "";
//        if (file.isFile()) {
//            // 获取文件后缀名
//            fileSuffix = file.getName().substring(file.getName().lastIndexOf("."));
//        }
//        final String fileName = "userId_" + Hawk.get(HawkContants.UID, 0) + "_" + System.currentTimeMillis()+ fileSuffix;
//        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest(bucketName, objectKey + fileName, filePath);
//        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, final PutObjectResult result) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d(TAG, "PutObject : UploadSuccess");
//                        Log.d(TAG, "ETag : " + result.getETag());
//                        Log.d(TAG, "RequestId : " + result.getRequestId());
//                        try {
//                            String url = oss.presignPublicObjectURL(bucketName, objectKey + fileName);
//                            keyData.add(url);
//                            uploadRequest(param, keyData, fileData, callback);
//                        } catch (Exception e) {
//                            callback.complete(null);
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, final ClientException clientExcepion, final ServiceException serviceException) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 请求异常
//                        if (clientExcepion != null) {
//                            // 本地异常如网络异常等
//                            clientExcepion.printStackTrace();
//                        }
//                        if (serviceException != null) {
//                            // 服务异常
//                            Log.e(TAG, "ErrorCode : " + serviceException.getErrorCode());
//                            Log.e(TAG, "RequestId : " + serviceException.getRequestId());
//                            Log.e(TAG, "HostId : " + serviceException.getHostId());
//                            Log.e(TAG, "RawMessage : " + serviceException.getRawMessage());
//                        }
//                        callback.complete(null);
//                    }
//                });
//            }
//        });
//    }
//
//    public interface UploadCallback {
//        void complete(List<String> key);
//    }
//
//    private interface AlibabaApi {
//        /**
//         * 获取阿里云信息
//         */
//        @POST("common/getOSSParam.json")
//        Call<JsonResult<AlibabaBean>> getUploads();
//    }
//
//    private class AlibabaBean {
//        private String filedir;
//        private String accessKeyId;
//        private String bucketName;
//        private String endpoint;
//        private String accessKeySecret;
//
//        public String getFiledir() {
//            return filedir;
//        }
//
//        public void setFiledir(String filedir) {
//            this.filedir = filedir;
//        }
//
//        public String getAccessKeyId() {
//            return accessKeyId;
//        }
//
//        public void setAccessKeyId(String accessKeyId) {
//            this.accessKeyId = accessKeyId;
//        }
//
//        public String getBucketName() {
//            return bucketName;
//        }
//
//        public void setBucketName(String bucketName) {
//            this.bucketName = bucketName;
//        }
//
//        public String getEndpoint() {
//            return endpoint;
//        }
//
//        public void setEndpoint(String endpoint) {
//            this.endpoint = endpoint;
//        }
//
//        public String getAccessKeySecret() {
//            return accessKeySecret;
//        }
//
//        public void setAccessKeySecret(String accessKeySecret) {
//            this.accessKeySecret = accessKeySecret;
//        }
//    }
//    public abstract class CallBack<T> implements Callback<JsonResult<T>> {
//        @Override
//        public void onResponse(Call<JsonResult<T>> call, Response<JsonResult<T>> response) {
//            JsonResult<T> result = response.body();
//            try {
//                if (result == null) {
//                    fail(5000000, "没有数据");
//                } else {
//                    if (result.isOk()) {
//                        success(result.body);
//                    } else {
//                        fail(result.errCode, result.msg);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<JsonResult<T>> call, Throwable t) {
//            try {
//                fail(5000001, "网络错误");
//                t.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        public abstract void success(T response);
//
//        public abstract void fail(int code, String message);
//
//    }
//
//}
