package com.example.activitytest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends BaseActivity {

    // 反向传值
    ActivityResultLauncher resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Log.d("TAG", "onActivityResult: " + result.getData().getStringExtra("data_return"));
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        if (savedInstanceState != null) {
            String out_string = savedInstanceState.getString("data_key");
            Log.d("TAG", "onCreate: " + out_string);
        }
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(FirstActivity.this, "you click button", Toast.LENGTH_SHORT).show();
//                finish();

                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                // 正向传值
                intent.putExtra("activityId", "100");
//                startActivity(intent);

                // 反向传值
//                startactivity
                resultLauncher.launch(intent);

                // 启动模式
                /*
                androidManifest.xml中修改 android:launchMode = "singleTop"
                standard: 默认模式，每次都会新建activity
                singleTop: 在栈顶不会新建，不在栈顶会新建
                singleTask: 每次启动活动时，会在返回栈中检查是否存在该活动的实例，如果发现存在则使用该实例，
                并把这个活动之上的所有活动统统出栈，如果没有发现则会创建新的。
                singleInstance：返回一个新的返回栈管理这个活动，解决共享活动实例的问题。
                 */
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Log.d("FirstActivity", "onOptionsItemSelected: ",item.getItemId());
        System.out.println(item.getItemId());
        switch (item.getItemId()) {
            case R.id.add_item:
                System.out.println("click add");
                Toast.makeText(FirstActivity.this, "add_item click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                System.out.println("click remove");
                Toast.makeText(FirstActivity.this,"remove_item click",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    // 临时保存数据

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        String outString = "out string";
        outState.putString("data_key", outString);
    }
}