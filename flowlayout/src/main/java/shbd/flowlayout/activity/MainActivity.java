package shbd.flowlayout.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import shbd.flowlayout.view.FlowLayout;

public class MainActivity extends AppCompatActivity {
    private FlowLayout mFLowLayout;

        private List<String> mBooks = Arrays.asList("沙滩搁浅我们的旧时光", "女人天生高贵", "海是彩色的灰尘", "迷乱", "十年之前 十年之后", "向来缘浅 奈何情深",
                "生不对 死不起", "属于我们的那片天空", "幸福还在么", "支离破碎");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFLowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        mFLowLayout.setDatas(mBooks);
    }
}
