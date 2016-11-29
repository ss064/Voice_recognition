package android_example.voice_recognition;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView1);
        Button button = (Button)findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //エミュレータを使用するためtry～catch文を追加しました。
                try{
                    //インテント作成
                    Intent intent = new Intent(
                            RecognizerIntent.ACTION_RECOGNIZE_SPEECH);//認識された音声を文字列として取得する
                    intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); //WEB_SEARCHにするとWEB検索モードで起動できる
                    intent.putExtra(
                            RecognizerIntent.EXTRA_PROMPT,
                            "VoiceRecognitionTest");//ダイアログのタイトルを設定
                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);//返却される音声認識結果の数を変える(この行は設定する必要性は薄い)
                    //インテント発行
                    startActivityForResult(intent, REQUEST_CODE);//RecognizerIntentを実行
                }catch(ActivityNotFoundException e){
                    //このインテントに応答できるアクティビティがインストールされていない場合
                    Toast.makeText(MainActivity.this,
                            "ActivityNotFoundException", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // アクティビティ終了時に呼び出される
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 自分が投げたインテントであれば応答する
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String resultsString = "";

            // 結果文字列リスト
            ArrayList<String> results;
                    results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            //RecognizerIntentの音声認識結果はIntent#getStringArrayListExtra()メソッドを利用して受けとることが出来るようになっています。
            //また、音声認識の結果は1つで返却されるのではなく、複数の文字列候補を優先度(入力された音声と思われる)順に返却します
            //実装次第では、多少入力音声が乱れたり、音声解析結果がうまくいかなかった場合もあります
            // "もしかして候補"の文字列を利用することで入力精度をある程度向上させることもできるようになっています

            /*
            for (int i = 0; i< results.size(); i++) {
                // ここでは、文字列が複数あった場合に結合しています
                resultsString = results.get(i)+String.valueOf(results.size());
            }
            */
            resultsString = results.get(0);

            // トーストを使って結果を表示
            Toast.makeText(this, resultsString, Toast.LENGTH_LONG).show();
            tv.setText(resultsString);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
