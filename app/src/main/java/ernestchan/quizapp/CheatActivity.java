package ernestchan.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestionTextView;
    private TextView mAnswerTextView;
    private TextView mShowAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswerButton = (TextView) findViewById(R.id.show_answer_button);

        mShowAnswerButton.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {

    }

    public static Intent newIntent(Context ctx){
        Intent ret = new Intent(ctx, CheatActivity.class);
        return ret;
    }

}
