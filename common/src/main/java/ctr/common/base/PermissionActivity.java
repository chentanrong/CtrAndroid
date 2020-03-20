package ctr.common.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;



import io.reactivex.functions.Consumer;

/**
 * @User chentanrong
 * @Date 2019/11/19
 * @Desc
 **/
public class PermissionActivity extends BaseActivity {
    RxPermissions rxPermissions =null;


    public String[] getPermissionList() {
        return new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxPermissions=new RxPermissions(this);
        rxPermissions.request(getPermissionList()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    onPermissionSuccess();
                } else {
                   finish();
                }
            }
        });
    }

    public void onPermissionSuccess() {

    }
}
