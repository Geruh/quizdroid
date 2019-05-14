package edu.washington.geruh.quizdroid

import android.app.Application
import android.content.Context
import java.io.Serializable
import org.json.JSONArray
import org.json.JSONObject


class QuizApp : Application() {
    companion object {
        lateinit var sharedInstance: QuizApp
    }

    lateinit var topicRepository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        sharedInstance = this
        topicRepository = TopicRepository(applicationContext)
    }
}

// topic class
class Topic(val title: String, val shortDescr: String, val longDescr: String, val questions: ArrayList<Quiz>) :
    Serializable {}

//quiz class
class Quiz(val question: String, val correctIndex: Int, val options: ArrayList<String>) : Serializable {}


class TopicRepository {

    private val mapOfTopics: MutableMap<String, Topic>

    constructor(context: Context) {
        val jsonArray = constructJSON(context)
        mapOfTopics = initializeData(jsonArray)
    }

    fun getTopics(): List<String> {
        val topicNames = mapOfTopics.keys
        return topicNames.toList()
    }

    fun getTopicInfo(name: String): Topic {
        return mapOfTopics.getValue(name)
    }

    private fun initializeData(jsonArray: JSONArray): MutableMap<String, Topic> {
        val map = mutableMapOf<String, Topic>()
        for (i in 0 until jsonArray.length()) {
            var topic = jsonArray.get(i) as JSONObject
            var topicName = topic.get("title") as String
            var topicDescription =  topic.get("desc") as String
            var questions = constructQuiz(topic.get("questions") as JSONArray)
            val topicObj = Topic(topicName, topicDescription, topicDescription, questions)
            map.put(topicName, topicObj)
        }
        return map
    }

    private fun constructQuiz(jsonArray: JSONArray): ArrayList<Quiz> {
        val data = arrayListOf<Quiz>()

        for (i in 0 until jsonArray.length()) {
            val question = jsonArray.get(i) as JSONObject
            val prompt = question.get("text") as String
            val solution = question.get("answer") as String
            val answers = question.get("answers") as JSONArray
            val options = constructOptions(answers)

            data.add(Quiz(prompt, solution.toInt() - 1, options))
        }
        return data
    }

    private fun constructOptions(jsonArray: JSONArray): ArrayList<String> {
        var possibleAnswers = arrayListOf<String>()
        for (i in 0 until jsonArray.length()) {
            possibleAnswers.add(jsonArray.get(i) as String)
        }
        return possibleAnswers
    }

    //    https://www.youtube.com/watch?v=3LIXkNxUdhw
    private fun constructJSON(context: Context): JSONArray {
        var json: String? = null
        try {
            val inputStream = context.assets.open("questions.json")
            json = inputStream.bufferedReader().use{it.readText()}
        } catch (e: Exception) {
            null
        }
        return JSONArray(json)
    }

}