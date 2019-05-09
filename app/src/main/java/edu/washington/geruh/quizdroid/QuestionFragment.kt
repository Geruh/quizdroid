package edu.washington.geruh.quizdroid


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import java.lang.ClassCastException
import java.lang.RuntimeException

class QuestionFragment : Fragment() {
    companion object {
        fun newInstance(quizTopic: String, index: Int, correct: Int): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle().apply {
                putString("TOPIC", quizTopic)
                putInt("CORRECT", correct)
                putInt("INDEX", index)
            }

            fragment.arguments = args
            return fragment
        }

    }
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
            "What is Newtons third law",
            "5",
            "Don't repeat yourself",
            "I forgot",
            "don't lie",
            "For each action there is a equal and opposite reaction"
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


    var listener: QuestionFragmentListener? = null
    var questions = marvel
    var selected: String = ""
    var questionIndex = 0
    var scoreC = 0
    val questionAmount = 5

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        val quizTopic = arguments!!.getString("TOPIC") as String
        questionIndex = arguments!!.getInt("INDEX", 0)
        scoreC = arguments!!.getInt("CORRECT", 0)
        val title = view.findViewById<TextView>(R.id.quizName)
        title.setText(quizTopic)
        addQuestion(quizTopic, view)
        val multipleC = view.findViewById<RadioGroup>(R.id.choices)
        multipleC.setOnCheckedChangeListener { _, checkedId ->
            val select = view.findViewById<RadioButton>(checkedId)
            selected = select.text.toString()
        }

        val button = view.findViewById<Button>(R.id.submit)
        button.setOnClickListener {
            submit(selected, quizTopic, questionIndex)
        }
        return view
    }

    fun addQuestion(topic: String, view: View) {
        if (topic == "Math") {
            questions = math
        } else if (topic == "Physics") {
            questions = physics
        }
        val question: Any = questions.get(questionIndex)
        val questionText: TextView = view.findViewById(R.id.quizName)
        questionText.setText(questions.get(questionIndex).get(0).toString())
        createResponses(question as Array<String>, view)
    }

    fun createResponses(questions: Array<String>, view: View) {
        view.findViewById<RadioButton>(R.id.answer1).setText(questions.get(2))
        view.findViewById<RadioButton>(R.id.answer2).setText(questions.get(3))
        view.findViewById<RadioButton>(R.id.answer3).setText(questions.get(4))
        view.findViewById<RadioButton>(R.id.answer4).setText(questions.get(5))
    }

    interface QuestionFragmentListener {
        fun submit(
            selected: String, answer: String,
            questionIndex: Int, numCorrect: Int, totalQuestions: Int,
            quizTopic: String, feedback: String
        )
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as QuestionFragmentListener
        } catch (e: ClassCastException) {
            throw RuntimeException(context.toString())
        }
    }

    fun submit(selected: String, quizTopic: String, index: Int) {
        if (selected != "") {
            val answer = questions.get(questionIndex).get(1)
            var feedback = ""
            if (questions.get(index).get(answer.toInt()) == selected) {
                feedback = "Correct :)"
                scoreC = scoreC + 1
            } else {
                feedback = "Incorrect :("
            }
            listener?.submit(
                selected,
                questions.get(index).get(answer.toInt()),
                index,
                scoreC,
                questionAmount,
                quizTopic,
                feedback
            )
        }
    }

}
