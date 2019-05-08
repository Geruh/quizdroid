package edu.washington.geruh.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_answer.*

class AnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)
        val quizTopic = getIntent().getStringExtra("quizTopic")
        val quizScore = getIntent().getIntExtra("correct", 0)
        val questionCount = getIntent().getIntExtra("questions", 0)
        val index = getIntent().getIntExtra("index", 0)
        val results = getIntent().getStringExtra("results")
        val response = getIntent().getStringExtra("response")
        val solution = getIntent().getStringExtra("solution")

        result.setText(results)
        givenAns.setText("your answer was ${response}, and the correct answer was ${solution}")
        score.setText("You have answered ${quizScore} out of ${questionCount} corectly")

        if (index == (questionCount - 1)) {
            next.setText("Finish")
        }
        next.setOnClickListener {
            if (index < questionCount - 1) {
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("quizTopic", quizTopic)
                intent.putExtra("correct", quizScore)
                intent.putExtra("index", index + 1)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
