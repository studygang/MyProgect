package com.gangzi.onedaybest;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouwei.library.CustomPopWindow;
import com.gangzi.onedaybest.utils.GetImagePath;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

   // private TakePhoto takePhoto;
   // private InvokeParam invokeParam;
    private Button bt_camera,bt_selectImage;
    private CircleImageView avator;

    private CustomPopWindow mPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initPermissions();
        bt_camera= (Button) findViewById(R.id.bt_camera);
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bt_selectImage= (Button) findViewById(R.id.bt_select_img);
        bt_selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        avator= (CircleImageView) findViewById(R.id.icon_image);
        avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showView();
            }
        });
    }

    private void initPermissions() {
        RxPermissions rxPermissions=new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // 在android 6.0之前会默认返回true
                        // 已经获取权限
                        Toast.makeText(this,"已经获取权限",Toast.LENGTH_SHORT).show();
                    } else {
                        // 未获取权限
                        Toast.makeText(this,"部分权限未获取",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showView() {
        View contentView= LayoutInflater.from(this).inflate(R.layout.avator_popwindow,null);
        View mainView = LayoutInflater.from(TestActivity.this).inflate(
                R.layout.activity_test, null);
        mPopWindow=new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()
                .showAtLocation(mainView, Gravity.BOTTOM,0,0);
        dealChildViewEvent(contentView);
    }

    private void dealChildViewEvent(View view) {
        TextView btnTakePhoto,btnChooseFromAlbum,btnCancle;
        btnTakePhoto= (TextView) view.findViewById(R.id.btnTakePhoto);
        btnChooseFromAlbum= (TextView) view.findViewById(R.id.btnChooseFromAlbum);
        btnCancle= (TextView) view.findViewById(R.id.btnCancle);
        btnCancle.setOnClickListener(this);
        btnChooseFromAlbum.setOnClickListener(this);
        btnTakePhoto.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
       // getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private File mCameraFile ;
    private File mCropFile;
    private File mGalleryFile;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTakePhoto:
                mCameraFile =new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!mCameraFile .getParentFile().exists())
                    mCameraFile .getParentFile().mkdirs();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Android7.0以上URI
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                {
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    //通过FileProvider创建一个content类型的Uri
                    Uri uri = FileProvider.getUriForFile(this, "com.gangzi.myprogect.onedaybest.fileprovider", mCameraFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                } else
                {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCameraFile));
                }
                startActivityForResult(intent,1006);
                break;
            case R.id.btnChooseFromAlbum:
                mGalleryFile =new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!mGalleryFile .getParentFile().exists())
                    mGalleryFile .getParentFile().mkdirs();
                Intent intentAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                intentAlbum.addCategory(Intent.CATEGORY_OPENABLE);
                intentAlbum.setType("image/*");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider
                    Uri uriForFile = FileProvider.getUriForFile(this, "com.gangzi.myprogect.onedaybest.fileprovider", mGalleryFile);
                    intentAlbum.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                    intentAlbum.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intentAlbum.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intentAlbum, 1007);
                } else {
                    startActivityForResult(intentAlbum, 1009);
                }
                break;
            case R.id.btnCancle:
                mPopWindow.dissmiss();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            mPopWindow.dissmiss();
            if (requestCode==1006){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri inputUri = FileProvider.getUriForFile(this, "com.gangzi.myprogect.onedaybest.fileprovider", mCameraFile);//通过FileProvider创建一个content类型的Uri
                    cropPicture(inputUri);//设置输入类型
                } else {
                    Uri inputUri = Uri.fromFile(mCameraFile);
                    cropPicture(inputUri);
                }
            }else if (requestCode==1007){//版本>= 7.0
                File imgUri = new File(GetImagePath.getPath(this, data.getData()));
                Uri dataUri = FileProvider.getUriForFile(this, "com.gangzi.myprogect.onedaybest.fileprovider", imgUri);
                cropPicture(dataUri);
            }else if (requestCode==1009){//版本<7.0  图库后返回
                cropPicture(data.getData());
            }else if (requestCode==1008){
                mPopWindow.dissmiss();
                Uri uriCrop=Uri.fromFile(mCropFile);
                //Uri uriCrop=data.getData();
                avator.setImageURI(uriCrop);
            }
        }
    }


    /**
     * 调用系统剪裁功能
     */
    public void cropPicture(Uri inputUri) {
        if (inputUri == null) {
            return;
        }
        mCropFile =new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!mCropFile .getParentFile().exists())
            mCropFile .getParentFile().mkdirs();
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri outPutUri = Uri.fromFile(mCropFile);
           // imageUri=FileProvider.getUriForFile(this, "com.gangzi.myprogect.onedaybest.fileprovider",fileimage);//通过FileProvider创建一个content类型的Uri
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //通过FileProvider创建一个content类型的Uri
            //imageUri = FileProvider.getUriForFile(this,"com.gangzi.myprogect.onedaybest.fileprovider", file);
            //outputUri = Uri.fromFile(new File(crop_image));
            //TODO:outputUri不需要ContentUri,否则失败
            //outputUri = FileProvider.getUriForFile(activity, "com.solux.furniture.fileprovider", new File(crop_image));
            intent.setDataAndType(inputUri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        } else {
            Uri outPutUri = Uri.fromFile(mCropFile);
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                String url = GetImagePath.getPath(this, inputUri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
            } else {
                intent.setDataAndType(inputUri, "image/*");
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        }

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 1008);//这里就将裁剪后的图片的Uri返回了
    }




    /**
     *  获取TakePhoto实例
     * @return
     */
   /* public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {

    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }*/



}
