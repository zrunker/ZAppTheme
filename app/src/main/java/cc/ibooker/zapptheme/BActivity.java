package cc.ibooker.zapptheme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 邹峰立 on 2017/10/20 0020.
 */
public class BActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.zoomout,R.anim.zoomin);
    }
}
