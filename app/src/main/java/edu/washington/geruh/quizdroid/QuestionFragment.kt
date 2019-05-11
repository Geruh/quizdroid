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

    var listener: QuestionFragmentListener? = null
    companion object {
        fun newInstance(topic: Topic, index: Int, correct: Int): QuestionFragment =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("TOPIC", topic)
                    putInt("CORRECT", correct)
                    putInt("INDEX", index)
                }
            }

    }

    var selected: String = ""
    var questionIndex = 0
    var scoreC = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val quizTopic = arguments!!.getSerializable("TOPIC") as Topic


        var questions = quizTopic.questions
        var questionAmount = questions.size

        questionIndex = arguments!!.getInt("INDEX", 0)

        scoreC = arguments!!.getInt("CORRECT", 0)

        val title = view.findViewById<TextView>(R.id.quizName)

        title.setText(quizTopic.questions.get(questionIndex).question)

        var possible: ArrayList<String> = questions.get(questionIndex).options

        view.findViewById<RadioButton>(R.id.answer1).setText(possible.get(0))
        view.findViewById<RadioButton>(R.id.answer2).setText(possible.get(1))
        view.findViewById<RadioButton>(R.id.answer3).setText(possible.get(2))
        view.findViewById<RadioButton>(R.id.answer4).setText(possible.get(3))

        val multipleC = view.findViewById<RadioGroup>(R.id.choices)
        multipleC.setOnCheckedChangeListener { _, checkedId ->
            val select = view.findViewById<RadioButton>(checkedId)
            selected = select.text.toString()
        }

        val button = view.findViewById<Button>(R.id.submit)
        button.setOnClickListener {
            submit(selected, quizTopic, questionIndex, questions)
        }
        return view
    }

    interface QuestionFragmentListener {
        fun submit(
            selected: String, answer: String,
            questionIndex: Int, numCorrect: Int, totalQuestions: Int,
            quizTopic: Topic, feedback: String
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

    fun submit(selected: String, quizTopic: Topic, index: Int, questions: ArrayList<Quiz>) {
        if (selected != "") {
            val questionAmount = questions.size
           val oneQuestion = questions.get(questionIndex)
            val answer = oneQuestion.correctIndex
            val options = oneQuestion.options
            var feedback = ""
            if (options.get(answer) == selected) {
                feedback = "Correct :)"
                scoreC = scoreC + 1
            } else {
                feedback = "Incorrect :("
            }
            listener?.submit(
                selected,
                options.get(answer),
                index,
                scoreC,
                questionAmount,
                quizTopic,
                feedback
            )
        }
    }

}
