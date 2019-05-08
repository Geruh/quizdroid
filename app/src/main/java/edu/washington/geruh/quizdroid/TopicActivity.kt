package edu.washington.geruh.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_topic.*

class TopicActivity : AppCompatActivity() {

    val quizDesc = listOf(
        "This will be a series of questions testing your skills in mathematics goodluck",
        "Do you know your Physics? Let's find out!",
        "Do you think you know your marvel superheroes well here is your chance to prove it"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val topicView = findViewById<TextView>(R.id.quizTopic)
        val topicDesc = findViewById<TextView>(R.id.topicDesc)
        val numberOfQuestions = findViewById<TextView>(R.id.questions)
        val quizTopic = intent.getStringExtra("quizTopic")
        topicView.setText(quizTopic)
        setDesc(quizTopic)
        numberOfQuestions.setText("4")
        startQuiz.setOnClickListener{
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("quizTopic", quizTopic)
            startActivity(intent)
        }
    }

    fun setDesc(topic: String) {
        if (topic == "Math") {
            topicDesc.setText(quizDesc[0])
        }
        if (topic == "Physics") {
            topicDesc.setText(quizDesc[1])
        }
        else {
            topicDesc.setText(quizDesc[2])
        }
     }
}
