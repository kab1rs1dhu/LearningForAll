package com.kabir.learningforall

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class TopicLearningActivity : AppCompatActivity() {

    private var currentTopicNumber = 1
    private lateinit var currentTopic: LearningTopic

    // All topics data for reference
    private val allTopics = listOf(
        LearningTopic(1, "Position Words", "Above, Below, Inside, Outside"),
        LearningTopic(2, "Before and After", "Understanding Sequence"),
        LearningTopic(3, "Sorting Things", "Grouping Similar Objects"),
        LearningTopic(4, "Round and Long", "Identifying Shapes"),
        LearningTopic(5, "Rolling and Sliding", "How Objects Move"),
        LearningTopic(6, "Counting 1-9", "Learning Numbers"),
        LearningTopic(7, "More and Less", "Comparing Quantities"),
        LearningTopic(8, "Number Order", "Arranging Numbers"),
        LearningTopic(9, "Teen Numbers", "Numbers 10-20"),
        LearningTopic(10, "Number Writing", "Writing Numbers Correctly"),
        LearningTopic(11, "Adding Numbers", "Putting Together"),
        LearningTopic(12, "Addition Practice", "Simple Addition"),
        LearningTopic(13, "Taking Away", "Simple Subtraction"),
        LearningTopic(14, "Subtraction Practice", "Practice Subtraction"),
        LearningTopic(15, "Long and Short", "Comparing Length"),
        LearningTopic(16, "Tall and Short", "Comparing Height"),
        LearningTopic(17, "Heavy and Light", "Comparing Weight"),
        LearningTopic(18, "Counting to 50", "Bigger Numbers"),
        LearningTopic(19, "Number Patterns", "Skip Counting"),
        LearningTopic(20, "Color Patterns", "Making Patterns"),
        LearningTopic(21, "Shape Patterns", "Pattern Recognition"),
        LearningTopic(22, "Coins and Money", "Understanding Money"),
        LearningTopic(23, "Buying Things", "Using Money"),
        LearningTopic(24, "Making Groups", "Equal Groups"),
        LearningTopic(25, "Repeated Addition", "Adding Same Numbers"),
        LearningTopic(26, "Day and Night", "Understanding Time"),
        LearningTopic(27, "Yesterday, Today, Tomorrow", "Time Sequence"),
        LearningTopic(28, "Counting Objects", "Organizing Information"),
        LearningTopic(29, "Comparing Groups", "More, Less, Equal")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_learning)

        currentTopicNumber = intent.getIntExtra("topic_number", 1)
        loadTopicContent()
        setupViews()
    }

    private fun loadTopicContent() {
        currentTopic = getTopicContent(currentTopicNumber)

        findViewById<TextView>(R.id.topicTitle).text = currentTopic.title
        findViewById<TextView>(R.id.topicDescription).text = currentTopic.description
        findViewById<TextView>(R.id.topicNumber).text = "Topic $currentTopicNumber/29"

        // Load topic-specific content
        val contentText = findViewById<TextView>(R.id.contentText)
        val examplesText = findViewById<TextView>(R.id.examplesText)

        val (content, examples) = getTopicDetails(currentTopicNumber)
        contentText.text = content
        examplesText.text = examples
    }

    private fun setupViews() {
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.nextButton).setOnClickListener {
            startQuiz()
        }
    }

    private fun startQuiz() {
        val intent = Intent(this, TopicQuizActivity::class.java)
        intent.putExtra("topic_number", currentTopicNumber)
        startActivity(intent)
        finish()
    }

    private fun getTopicContent(topicNumber: Int): LearningTopic {
        return allTopics[topicNumber - 1]
    }

    private fun getTopicDetails(topicNumber: Int): Pair<String, String> {
        return when(topicNumber) {
            1 -> Pair(
                "We use special words to tell where things are:\n\n• ABOVE means on top\n• BELOW means underneath\n• INSIDE means within something\n• OUTSIDE means not within something\n\nThese words help us describe where objects are located!",
                "Examples:\n• The bird is ABOVE the tree\n• The cat is BELOW the table\n• The toy is INSIDE the box\n• The ball is OUTSIDE the house"
            )
            2 -> Pair(
                "BEFORE and AFTER help us understand order:\n\n• BEFORE means something comes first\n• AFTER means something comes later\n\nThis helps us put things in the right order!",
                "Examples:\n• We wash hands BEFORE eating\n• We brush teeth AFTER eating\n• Monday comes BEFORE Tuesday\n• Night comes AFTER day"
            )
            3 -> Pair(
                "Sorting means putting similar things together:\n\n• Group things that are the same\n• Look for what makes things similar\n• Color, size, shape can help us sort\n\nSorting helps us organize our world!",
                "Examples:\n• Red toys together, blue toys together\n• Big blocks with big blocks\n• Round things in one group\n• Square things in another group"
            )
            4 -> Pair(
                "Objects have different shapes:\n\n• ROUND objects are circular\n• LONG objects are stretched out\n• Some objects roll, others don't\n\nShapes help us describe things!",
                "Examples:\n• ROUND: ball, wheel, coin\n• LONG: pencil, snake, rope\n• A ball is round and can roll\n• A book is long and flat"
            )
            5 -> Pair(
                "Objects move in different ways:\n\n• ROLLING objects move by turning\n• SLIDING objects move smoothly\n• Round objects usually roll\n• Flat objects usually slide",
                "Examples:\n• ROLLING: ball, wheel, log\n• SLIDING: book, box, paper\n• A coin can both roll and slide!\n• Ice makes things slide easily"
            )
            6 -> Pair(
                "Let's learn to count from 1 to 9:\n\n1 - One\n2 - Two\n3 - Three\n4 - Four\n5 - Five\n6 - Six\n7 - Seven\n8 - Eight\n9 - Nine\n\nCounting helps us know how many!",
                "Practice counting:\n• 1 apple\n• 2 eyes\n• 3 wheels on a tricycle\n• 4 legs on a chair\n• 5 fingers on one hand"
            )
            7 -> Pair(
                "Comparing quantities:\n\n• MORE means a bigger amount\n• LESS means a smaller amount\n• SAME means equal amounts\n\nComparing helps us understand amounts!",
                "Examples:\n• 5 cookies is MORE than 3 cookies\n• 2 toys is LESS than 4 toys\n• 3 apples is the SAME as 3 oranges\n• Which group has more?"
            )
            8 -> Pair(
                "Numbers have an order:\n\n1, 2, 3, 4, 5, 6, 7, 8, 9\n\n• Each number comes after the one before\n• We can count forward or backward\n• Order helps us organize numbers",
                "Practice:\n• What comes after 3? (4)\n• What comes before 7? (6)\n• Count: 1, 2, 3, ?, 5\n• Backward: 5, 4, 3, ?, 1"
            )
            9 -> Pair(
                "Teen numbers are special:\n\n10 - Ten\n11 - Eleven\n12 - Twelve\n13 - Thirteen\n14 - Fourteen\n15 - Fifteen\n16 - Sixteen\n17 - Seventeen\n18 - Eighteen\n19 - Nineteen\n20 - Twenty",
                "Teen numbers:\n• All have 'teen' except 10, 11, 12, 20\n• 13 sounds like 'three-teen'\n• 14 sounds like 'four-teen'\n• They come after single digits"
            )
            10 -> Pair(
                "Writing numbers correctly:\n\n• Start at the top\n• Follow the right direction\n• Practice makes perfect!\n• Some numbers have curves, others have lines",
                "Number writing tips:\n• 1: Start at top, straight line down\n• 2: Curve right, then left and across\n• 3: Two curves to the right\n• 4: Down, across, then down again"
            )
            else -> Pair(
                "This is topic $currentTopicNumber. Learn step by step and practice regularly!",
                "Keep practicing and you'll master this topic!"
            )
        }
    }
}