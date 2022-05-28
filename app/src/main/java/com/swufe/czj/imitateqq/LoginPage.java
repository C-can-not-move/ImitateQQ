package com.swufe.czj.imitateqq;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.swufe.czj.imitateqq.client.ClientInputThread;
import com.swufe.czj.imitateqq.client.ClientOutputThread;
import com.swufe.czj.imitateqq.common.bean.User;
import com.swufe.czj.imitateqq.common.transportBean.TranObject;
import com.swufe.czj.imitateqq.common.transportBean.TranObjectType;
import com.swufe.czj.imitateqq.common.util.Constants;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class LoginPage extends AppCompatActivity implements Runnable  {

    private EditText id ;
    private EditText pwd;
    private CheckBox savePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        id = (EditText)findViewById(R.id.Login_id);
        pwd = (EditText)findViewById(R.id.Login_pwd);
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
        Log.i("test","成功");
        Intent intent = new Intent(this,RegisterPage.class);
        startActivity(intent);
    }
    /**
     * 提交账号密码信息到服务器进行验证
     */

    private void submit() throws Exception {
        String idStr = id.getText().toString();
        String pwdStr = pwd.getText().toString();
        if (idStr.length() == 0 || pwdStr.length() == 0) {
            DialogFactory.nomalDialog(this,"登陆失败", "帐号或密码不能为空");
        } else {
            ProgressDialog progressDialog=DialogFactory.creatRequestDialog(this,"正在登陆");

            Log.i("LoginPage","开始发送消息");
            Socket socket = new Socket(Constants.SERVER_IP,Constants.SERVER_PORT);
            ClientOutputThread out = new ClientOutputThread(socket);
            TranObject<User> o = new TranObject<User>(TranObjectType.LOGIN);
            User u = new User();
            u.setId(Integer.parseInt(idStr));
            u.setPassword(pwdStr);
            o.setObject(u);
            out.setMsg(o);
            out.start();
            Log.i("LoginPage","发送消息结束");
            //接收消息
            Log.i("LoginPage","开始接收消息");
            ClientInputThread in = new ClientInputThread(socket);
            progressMessage(in.call());
            Log.i("LoginPage","接收消息结束");
            progressDialog.dismiss();
        }
    }

    public void progressMessage(TranObject msg) {
        Log.i("LoginPage",msg.toString());
        if (msg != null) {
            Log.i("LoginPage", "收到了登陆请求的反馈信息！！！");
            switch (msg.getType()) {
                case LOGIN:
                    List<User> list = (List<User>) msg.getObject();
                    if (list != null && list.size() > 0) {
                        Log.i("LoginPage", "登陆成功！！！");
                        Intent intent = new Intent(this,MainActivity.class);
                        startActivity(intent);
                    } else {
                        DialogFactory.nomalDialog(this,"登陆失败", "帐号或密码错误！！！");
                        Log.i("LoginPage", "登陆失败");
                    }
                    break;
                default:
                    break;
            }

        }
        else
        {
            Log.i("LoginPage", "收到了错误反馈信息！！！");
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