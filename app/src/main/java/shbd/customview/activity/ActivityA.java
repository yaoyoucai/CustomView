package shbd.customview.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import shbd.customview.R;
import shbd.customview.service.GitHubService;
import shbd.customview.service.Repo;
import shbd.customview.view.MyTestView;

public class ActivityA extends AppCompatActivity {
    private MyTestView mTestView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTestView = (MyTestView) findViewById(R.id.my_test_view);
    }

    public void startTranlate(View view) {
       /* TranslateAnimation animation = new TranslateAnimation(0,
                0.5f,
                0,
                0.5f);
        animation.setDuration(3000);*/
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTestView, "x", 0, 30);
        animator.setDuration(3000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("TAG", "trueX: " + mTestView.getX() + "left:" + mTestView.getLeft() + "tranlationX:" + mTestView.getTranslationX());
            }
        });
        animator.start();
    }

    public void startRequest(View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        GitHubService github = retrofit.create(GitHubService.class);
        Call<List<Repo>> user = github.listRepos("user");
        try {
            List<Repo> body = user.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
