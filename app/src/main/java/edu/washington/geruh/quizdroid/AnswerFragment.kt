package edu.washington.geruh.quizdroid

import android.content.Context

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
class AnswerFragment : Fragment() {

    companion object {
        fun newInstance(
            selected: String,
            solution: String, index: Int, correct: Int, questionCount: Int, quizTopic: String, feedback: String
        ): AnswerFragment {
            val fragment = AnswerFragment()
            val bundle = Bundle().apply {
                putString("TOPIC", quizTopic)
                putInt("CORRECT", correct)
                putInt("QUESTIONS", questionCount)
                putInt("INDEX", index)
                putString("FEEDBACK", feedback)
                putString("RESPONSE", selected)
                putString("SOLUTION", solution)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    var listener: AnswerFragmentListener? = null
    interface AnswerFragmentListener {
        fun nextQuestion(quizTopic: String, quizScore: Int, questionIndex: Int, questionCount: Int)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as AnswerFragmentListener
        } catch(e: ClassCastException) {
            throw ClassCastException(context.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_answer, container, false)
        val quizTopic = arguments!!.getString("TOPIC")
        val quizScore = arguments!!.getInt("CORRECT", 0)
        val questionCount = arguments!!.getInt("QUESTIONS", 0)
        val index = arguments!!.getInt("INDEX", 0)
        val results = arguments!!.getString("FEEDBACK")
        val response = arguments!!.getString("RESPONSE")
        val solution = arguments!!.getString("SOLUTION")

        val feedback = view.findViewById<TextView>(R.id.result)
        feedback.setText(results)
        val given = view.findViewById<TextView>(R.id.givenAns)
        given.setText("your answer was ${response}, and the correct answer was ${solution}")
        val score = view.findViewById<TextView>(R.id.score)
        score.setText("You have answered ${quizScore} out of ${questionCount} corectly")
        val next = view.findViewById<Button>(R.id.next)
        if (index == (questionCount-1)) {
            next.setText("Finish")
        }
        next.setOnClickListener {
            next(quizTopic, quizScore, index + 1, questionCount)
        }
        return view
    }

    fun next(quizTopic: String, quizScore: Int, index: Int, questionCount: Int) {
        listener?.nextQuestion(quizTopic, quizScore, index, questionCount)
    }

}
