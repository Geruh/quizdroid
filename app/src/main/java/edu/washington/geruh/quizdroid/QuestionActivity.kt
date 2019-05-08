package edu.washington.geruh.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
//import android.widget.RadioButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_topic.*


class QuestionActivity : AppCompatActivity() {
    val math = arrayOf(
        arrayOf(
            "What is 2 * 2?", "3", "13", "4", "0", "5"
        ),
        arrayOf(
            "What is 8 / 4?", "2", "2", "12", "8", "33"
        ),
        arrayOf(
            "What is 3 * 10?", "5", "2", "3", "8", "30"
        ),
        arrayOf(
            "What is 5 * 4?", "4", "12", "34", "20", "456"
        ),
        arrayOf(
            "What is 300 * 1?", "2", "300", "300.3", "30", "2"
        )
    )
    val physics = arrayOf(
        arrayOf(
            "What is Force equal to", "3", "Mass", "Mass x Acceleration", "Pounds", "Pounds * Acceleration"
        ),
        arrayOf(
            "What is the unit of mesurement of mass", "2", "kilos", "pounds", "newtons", "force"
        ),
        arrayOf(
            "What is Newtons third law", "5", "Don't repeat yourself", "I forgot", "don't lie", "For each action there is a equal and opposite reaction"
        ),
        arrayOf(
            "objects in ___ stay in motion", "4", "laws", "life", "motion", "orbit"
        ),
        arrayOf(
            "what type of force allows you to walk", "2", "friction", "air resistance", "drag", "springs"
        )
    )

    val marvel = arrayOf(
        arrayOf(
            "what superhero has a hammer?", "3", "Iron Man", "Thor", "Spiderman", "Captain America"
        ),
        arrayOf(
            "What color is the hulk?", "2", "green", "red", "purple", "blue"
        ),
        arrayOf(
            "is thanos correct?", "5", "no", "no", "no", "yes"
        ),
        arrayOf(
            "what does groot say", "4", "meow", "hey", "I am groot", "whats up"
        ),
        arrayOf(
            "who can turn very small", "2", "ant-man", "spider man", "thanos", "you"
        )
    )
    var questions = marvel
    var selected: String = ""
    var questionIndex = 0
    var scoreC = 0
    val questionAmount = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        questionIndex = intent.getIntExtra("index", 0)
        scoreC = intent.getIntExtra("correct", 0)
        val quizTopic = intent.getStringExtra("quizTopic")
        val title: TextView = findViewById(R.id.quizName)
        title.setText(quizTopic)
        addQuestion(quizTopic)
        val multipleC: RadioGroup = findViewById(R.id.choices)

       multipleC.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
           val select: RadioButton = findViewById(checkedId)
           selected = select.text.toString()
       })

        submit.setOnClickListener {
            handleSubmit()
        }
    }

    fun addQuestion(topic: String) {
        if (topic == "Math") {
            questions = math
        } else if (topic == "Physics") {
            questions = physics
        }
        val question: Any = questions.get(questionIndex)
        val questionText: TextView = findViewById(R.id.quizName)
        questionText.setText(questions.get(questionIndex).get(0).toString())
        createResponses(question as Array<String>)
    }

    fun createResponses(questions: Array<String>) {
        val answer1: RadioButton = findViewById(R.id.answer1)
        val answer2: RadioButton = findViewById(R.id.answer2)
        val answer3: RadioButton = findViewById(R.id.answer3)
        val answer4: RadioButton = findViewById(R.id.answer4)
        answer1.setText(questions.get(2))
        answer2.setText(questions.get(3))
        answer3.setText(questions.get(4))
        answer4.setText(questions.get(5))
    }

    fun handleSubmit() {
        val quizTopic = intent.getStringExtra("quizTopic")
        if (selected != "") {
            val answer = questions.get(questionIndex).get(1)
            var correct = false
            if (questions.get(questionIndex).get(answer.toInt()) == selected) {
                scoreC = scoreC + 1
                correct = true
            }
            val intent = Intent(this, AnswerActivity::class.java)
            intent.putExtra("index", questionIndex)
            intent.putExtra("correct", scoreC)
            if (correct) {
                intent.putExtra("results", "Correct! :)")
            } else {
                intent.putExtra("results", "Wrong Answer! :(")
            }
            intent.putExtra("quizTopic", quizTopic)
            intent.putExtra("questions", questionAmount)
            intent.putExtra("solution", questions.get(questionIndex).get(answer.toInt()))
            intent.putExtra("response", selected)
            startActivity(intent)
        }
    }
}
