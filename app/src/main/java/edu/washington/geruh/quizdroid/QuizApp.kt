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
        topicRepository = TopicRepository()
    }
}

// topic class
class Topic(val title: String, val shortDescr: String, val longDescr: String, val questions: Array<Quiz>): Serializable {}

//quiz class
class Quiz(val question: String, val correctIndex: Int, val options: Array<String>): Serializable{}

class TopicRepository {

    private val mapOfTopics: Map<String, Topic>
    companion object {
        const val JSON_FILE_PATH = "data/questions.json"

    }


    constructor(context: Context) {
        val jsonArray = constructJSON(context)
        mapOfTopics = initializeData(jsonArray)
    }

    fun getTopics(): List<String> {
        val topicNames = listOf<String>(
            mapOfTopics.getValue("math")!!.title,
            mapOfTopics.getValue("physics")!!.title,
            mapOfTopics.getValue("marvel")!!.title
        )
        return topicNames
    }

    fun getTopicInfo(name: String): Topic {
        return mapOfTopics.getValue(name)
    }

    private fun initializeData(jsonArray: JSONArray): Map<String, Topic> {
        for (i in 0 until jsonArray.length()) {

        }
    }

        private fun constructJSON(context: Context): JSONArray {
            var json: String? = null
            json = try {
                val sharedPreferences = context.getSharedPreferences("USER_PREFERENCES_KEY", Context.MODE_PRIVATE)
                val fileName = "questions.json"
                val path = "data/".plus(sharedPreferences.getString("data_path", fileName))
                val inputStream = context.assets.open(path)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                String(buffer, Charsets.UTF_8)
            }
            catch (e:Exception) {
                null
            }
            return JSONArray(json)
        }

}


//        val math = arrayOf(
//            Quiz(
//                "What is 2 * 2?", 1, arrayOf("13", "4", "0", "5")
//            ),
//            Quiz(
//                "What is 8 / 4?", 0, arrayOf("2", "12", "8", "33")
//            ),
//            Quiz(
//                "What is 3 * 10?", 3, arrayOf("2", "3", "8", "30")
//            ),
//            Quiz(
//                "What is 5 * 4?", 2, arrayOf("12", "34", "20", "456")
//            ),
//            Quiz(
//                "What is 300 * 1?", 0, arrayOf("300", "300.3", "30", "2")
//            )
//        )
//        val physics = arrayOf(
//            Quiz(
//                "What is Force equal to", 1, arrayOf("Mass", "Mass x Acceleration", "Pounds", "Pounds * Acceleration")
//            ),
//            Quiz(
//                "What is the unit of mesurement of mass", 0, arrayOf("kilos", "pounds", "newtons", "force")
//            ),
//            Quiz(
//                "What is Newtons third law",
//                3,
//                arrayOf(
//                    "Don't repeat yourself",
//                    "I forgot",
//                    "don't lie",
//                    "For each action there is a equal and opposite reaction"
//                )
//            ),
//            Quiz(
//                "objects in ___ stay in motion", 2, arrayOf("laws", "life", "motion", "orbit")
//            ),
//            Quiz(
//                "what type of force allows you to walk", 0, arrayOf("friction", "air resistance", "drag", "springs")
//            )
//        )
//        val marvel = arrayOf(
//            Quiz(
//                "what superhero has a hammer?", 1, arrayOf("Iron Man", "Thor", "Spiderman", "Captain America")
//            ),
//            Quiz(
//                "What color is the hulk?", 0, arrayOf("green", "red", "purple", "blue")
//            ),
//            Quiz(
//                "is thanos correct?", 3, arrayOf("no", "no", "no", "yes")
//            ),
//            Quiz(
//                "what does groot say", 2, arrayOf("meow", "hey", "I am groot", "whats up")
//            ),
//            Quiz(
//                "who can turn very small", 0, arrayOf("ant-man", "spider man", "thanos", "you")
//            )
//        )
//
//        val mathTopic: Topic =
//            Topic("Math", "This will be a series of questions testing your skills in mathematics goodluck", "Topics in mathematics that every educated person needs to know to process, evaluate, and understand the numerical information in our society.", math)
//        val physicsTopic: Topic = Topic("Physics", "Do you know your Physics? Let's find out!", "the branch of science concerned with the nature and properties of matter and energy. The subject matter of physics, distinguished from that of chemistry and biology, includes mechanics, heat, light and other radiation, sound, electricity, magnetism, and the structure of atoms.", physics)
//        val marvelTopic: Topic = Topic(
//            "Marvel",
//            "Do you think you know your marvel superheroes well here is your chance to prove it",
//            "Super-teams such as the Avengers, the X-Men, the Fantastic Four, the Guardians of the Galaxy, the Defenders, the Inhumans, the New Warriors, the Nova Corps and other Marvel superheroes live in this quiz",
//            marvel
//        )
//
//        return mapOf(
//            "math" to mathTopic,
//            "physics" to physicsTopic,
//            "marvel" to marvelTopic
//        )