package com.swufe.czj.imitateqq;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.swufe.czj.imitateqq.client.Client;
import com.swufe.czj.imitateqq.client.ClientOutputThread;
import com.swufe.czj.imitateqq.common.bean.User;
import com.swufe.czj.imitateqq.common.transportBean.TranObject;
import com.swufe.czj.imitateqq.common.transportBean.TranObjectType;
import com.swufe.czj.imitateqq.common.util.Constants;
import com.swufe.czj.imitateqq.common.util.DialogFactory;

import java.io.IOException;
import java.util.List;

public class LoginPage extends MyActivity implements Runnable {

    private EditText id;
    private EditText pwd;
    private CheckBox savePwd;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        id = (EditText) findViewById(R.id.Login_id);
        pwd = (EditText) findViewById(R.id.Login_pwd);
    }

    /**
     * 处理点击事件
     */
    public void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.Regist_btn:
                goRegisterActivity();
                break;
            case R.id.Login_btn:
                run();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到注册页面
     */
    public void goRegisterActivity() {
        Log.i("test", "成功");
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }

    /**
     * 提交账号密码信息到服务器进行验证
     */

    private void submit() throws Exception {
        String idStr = id.getText().toString();
        String pwdSrt = pwd.getText().toString();
        if (idStr.length() == 0 || pwdSrt.length() == 0) {
            DialogFactory.nomalDialog(this, "QQ登录", "帐号或密码不能为空哦");
        } else {
            if (application.isClientStart()) {
                Client client = application.getClient();
                ClientOutputThread out = client.getClientOutputThread();
                TranObject<User> o = new TranObject<User>(TranObjectType.LOGIN);
                User u = new User();
                u.setId(Integer.parseInt(idStr));
                u.setPassword(pwdSrt);//Encode.getEncode("MD5", pwdSrt)
                o.setObject(u);
                out.setMsg(o);
            } else {
                DialogFactory.nomalDialog(this, "QQ登录", "服务器暂未开放哦");
            }
        }
    }

    @Override
    // 依据自己需求处理父类广播接收者收取到的消息
    public void getMessage(TranObject msg) {
        if (msg != null) {
            // System.out.println("Login:" + msg);
            switch (msg.getType()) {
                case LOGIN:// LoginActivity只处理登录的消息
                    List<User> list = (List<User>) msg.getObject();
                    if (list.size() > 0) {
                        Intent i = new Intent(this, MainActivity.class);
                        i.putExtra(Constants.MSGKEY, msg);
                        startActivity(i);
                        finish();
                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogFactory.nomalDialog(this, "登陆失败", "帐号或密码错误");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void run() {
        try {
            submit();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}