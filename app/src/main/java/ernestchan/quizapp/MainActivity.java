package ernestchan.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CHEAT = 0;

    private TextView mTextView;

    private LinearLayout mTrueFalseContainer;
    private LinearLayout mFillTheBlankContainer;
    private LinearLayout mMultipleChoiceContainer;

    private EditText mEditText;

    TextView score_view;
    // Button true_button;

    int score = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;
    private Button mHelpButton;
    private Button mCheckButton;
    private Button mAButton;
    private Button mBButton;
    private Button mCButton;
    private Button mDButton;


    private Question[] mQuestions;
    private int mIndex;


    private Button mCheatButton;
    private boolean mCheated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mHelpButton = (Button) findViewById(R.id.hintTextResId);
        mCheckButton = (Button) findViewById(R.id.check_button);
        mAButton = (Button) findViewById(R.id.a_button);
        mBButton = (Button) findViewById(R.id.b_button);
        mCButton = (Button) findViewById(R.id.c_button);
        mDButton = (Button) findViewById(R.id.d_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);


        mTrueFalseContainer = (LinearLayout) findViewById(R.id.true_false_container);
        mFillTheBlankContainer = (LinearLayout) findViewById(R.id.fill_the_blank_container);
        mMultipleChoiceContainer = (LinearLayout) findViewById(R.id.multiple_choice_container);

        mEditText = (EditText) findViewById(R.id.edit_text);

        score_view = (TextView) findViewById(R.id.score_view);
        score_view.setText("Score: " + score);

        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mHelpButton.setOnClickListener(this);
        mCheckButton.setOnClickListener(this);
        mAButton.setOnClickListener(this);
        mBButton.setOnClickListener(this);
        mCButton.setOnClickListener(this);
        mDButton.setOnClickListener(this);
        mCheatButton.setOnClickListener(this);

//when next button is clicked next button runs

        mTextView = (TextView) findViewById(R.id.text_view);

        mQuestions = new Question[7];
        mIndex = 0;

        mQuestions[0] = new TrueFalseQuestion(R.string.question_text1, R.string.question_text1_hint, true);
        mQuestions[1] = new TrueFalseQuestion(R.string.question_text2, R.string.question_text2_hint, false);
        mQuestions[2] = new TrueFalseQuestion(R.string.question_text3, R.string.question_text3_hint, true);
        mQuestions[3] = new TrueFalseQuestion(R.string.question_text4, R.string.question_text4_hint, true);
        mQuestions[4] = new TrueFalseQuestion(R.string.question_text5, R.string.question_text5_hint, false);

        String[] q6Answers = getResources().getStringArray(R.array.question_6_answers);
        mQuestions[5] = new FillTheBlankQuestion(R.string.question_text6, R.string.question_text6_hint, q6Answers);

        mQuestions[6] = new MultipleChoiceQuestion(R.string.question_text7, R.string.question_text7_hint, R.array.question_7_answers, 2);

        setupQuestion();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData){
        if(resultCode != RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT && resultData != null){
            mCheated = CheatActivity.didCheat(resultData);
        }
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.true_button) {
            checkAnswer(true);
            score_view.setText("Score: " + score);

        } else if (view.getId() == R.id.false_button) {
            checkAnswer(false);
            score_view.setText("Score: " + score);

        } else if (view.getId() == R.id.next_button) {
            if (mIndex < 6) {
                mIndex++;
                setupQuestion();
            }
        } else if (view.getId() == R.id.check_button) {
            if (mQuestions[mIndex].checkAnswer(mEditText.getText().toString())) {
                checkAnswer(mEditText.getText().toString());
                score_view.setText("Score: " + score);
            }
             else
            {
                checkAnswer(mEditText.getText().toString());
                score_view.setText("Score: " + score);
            }
        } else if (view.getId() == R.id.back_button) {
            if (mIndex > 0) {
                mIndex--;
                setupQuestion();
        } else if (view.getId() == R.id.a_button) {
            checkAnswer(0);
            score_view.setText("Score: " + score);}
        } else if (view.getId() == R.id.b_button) {
            checkAnswer(1);
            score_view.setText("Score: " + score);
        } else if (view.getId() == R.id.c_button) {
            checkAnswer(2);
            score_view.setText("Score: " + score);
        } else if (view.getId() == R.id.d_button) {
            checkAnswer(3);
            score_view.setText("Score: " + score);
        } else if (view.getId() == R.id.cheat_button) {
            Intent cheatIntent = CheatActivity.newIntent(this, mQuestions[mIndex]);
            startActivityForResult (cheatIntent, REQUEST_CODE_CHEAT);
        }
        if (view.getId() == R.id.hintTextResId) {

            // USING INDEX
            Toast myToast = Toast.makeText(this, mQuestions[mIndex].getHintTextResId(), Toast.LENGTH_LONG);
            myToast.show();
        }
    }


    public void setupQuestion() {
        //Setup the first question.
        mTextView.setText(mQuestions[mIndex].getTextResId());

        if (mQuestions[mIndex].isTrueFalseQuestion()) {
            mTrueFalseContainer.setVisibility(View.VISIBLE);
            mFillTheBlankContainer.setVisibility(View.GONE);
            mMultipleChoiceContainer.setVisibility(View.GONE);
        } else if (mQuestions[mIndex].isFillTheBlankQuestion()) {
            mTrueFalseContainer.setVisibility(View.GONE);
            mFillTheBlankContainer.setVisibility(View.VISIBLE);
            mMultipleChoiceContainer.setVisibility(View.GONE);
        } else if (mQuestions[mIndex].isMultipleChoiceQuestion()) {
            int optionsResId = ((MultipleChoiceQuestion) mQuestions[mIndex]).getmOptionsResId();
            String[] options = getResources().getStringArray(optionsResId);
            mTrueFalseContainer.setVisibility(View.GONE);
            mFillTheBlankContainer.setVisibility(View.GONE);
            mMultipleChoiceContainer.setVisibility(View.VISIBLE);
            mAButton.setText(options[0]);
            mBButton.setText(options[1]);
            mCButton.setText(options[2]);
            mDButton.setText(options[3]);
        }
    }

    public boolean checkAnswer(boolean userInput) {
        if (mCheated){
            Toast.makeText(this,R.string.cheat_shame, Toast.LENGTH_LONG).show();
            return false;
        } else if (mQuestions[mIndex].checkAnswer(userInput)) {
            Toast myToast = Toast.makeText(this, "You are correct", Toast.LENGTH_SHORT);
            myToast.show();
            score++;
            return true;
        } else {
            Toast myToast = Toast.makeText(this, "You are incorrect", Toast.LENGTH_SHORT);
            myToast.show();
            score--;
            return false;
        }
    }

    public boolean checkAnswer(String userInput) {
        if (mCheated){
            Toast.makeText(this,R.string.cheat_shame, Toast.LENGTH_LONG).show();
            return false;
        } else if (mQuestions[mIndex].checkAnswer(userInput)) {
            Toast myToast = Toast.makeText(this, "You are correct", Toast.LENGTH_SHORT);
            myToast.show();
            score++;
            return true;
        } else {
            Toast myToast = Toast.makeText(this, "You are incorrect", Toast.LENGTH_SHORT);
            myToast.show();
            score--;
            return false;
        }

    }

    public boolean checkAnswer(int userInput) {
        if (mCheated){
            Toast.makeText(this,R.string.cheat_shame, Toast.LENGTH_LONG).show();
            return false;
        } else if (mQuestions[mIndex].checkAnswer(userInput)) {
            Toast myToast = Toast.makeText(this, "You are correct", Toast.LENGTH_SHORT);
            myToast.show();
            score++;
            return true;
        } else {
            Toast myToast = Toast.makeText(this, "You are incorrect", Toast.LENGTH_SHORT);
            myToast.show();
            score--;
            return false;
        }

    }
}