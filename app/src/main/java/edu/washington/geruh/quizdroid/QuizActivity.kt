package edu.washington.geruh.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment

class QuizActivity : AppCompatActivity(), TopicActivityFragment.TopicactivityFragmentListener,
    QuestionFragment.QuestionFragmentListener, AnswerFragment.AnswerFragmentListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val topic = intent.getStringExtra("quizTopic")
        val topicActivity = TopicActivityFragment.newInstance(topic)
        replaceFragment(topicActivity)
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }


    override fun startQuiz(quizTopic: String) {
        val quiz = QuestionFragment.newInstance(quizTopic, 0, 0)
        replaceFragment(quiz)
    }


    override fun submit(
        selected: String, solution: String,
        index: Int, correct: Int, questionCount: Int,
        quizTopic: String, feedback: String
    ) {
        var answer = AnswerFragment.newInstance(
            selected,
            solution, index, correct, questionCount, quizTopic, feedback
        )
        replaceFragment(answer)
    }

    override fun nextQuestion(quizTopic: String, quizScore: Int, questionIndex: Int, questionCount: Int) {
        if (questionIndex < questionCount) {
            val questionFragment = QuestionFragment.newInstance(
                quizTopic, questionIndex,
                quizScore
            )
            replaceFragment(questionFragment)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
