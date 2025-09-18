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
                "📍 Position words help us describe where things are located!\n\n🔼 ABOVE means higher up or on top\n🔽 BELOW means lower down or underneath\n📦 INSIDE means within or contained in something\n🌍 OUTSIDE means not within, or external to something\n\n✨ These special words are like directions that help us navigate our world!",
                "🎯 Practice Examples:\n• 🐦 The bird is ABOVE the tree\n• 🐱 The cat is BELOW the table\n• 🧸 The toy is INSIDE the box\n• ⚽ The ball is OUTSIDE the house\n• 🌙 The moon is ABOVE the clouds\n• 🐠 Fish swim BELOW the water surface"
            )
            2 -> Pair(
                "⏰ BEFORE and AFTER help us understand the order of events!\n\n⬅️ BEFORE means something happens first or earlier\n➡️ AFTER means something happens later or next\n🔄 This helps us put events in the correct sequence\n\n📅 Time order is very important in our daily lives!",
                "🌟 Daily Examples:\n• 🧼 We wash hands BEFORE eating\n• 🦷 We brush teeth AFTER eating\n• 📅 Monday comes BEFORE Tuesday\n• 🌙 Night comes AFTER day\n• 👕 We get dressed BEFORE going outside\n• 🍽️ We clean up AFTER playing"
            )
            3 -> Pair(
                "🗂️ Sorting means organizing things into groups based on what they have in common!\n\n🎨 Group by COLOR: red with red, blue with blue\n📏 Group by SIZE: big things together, small things together\n🔴 Group by SHAPE: circles with circles, squares with squares\n\n🧩 Sorting helps us organize and find things easily!",
                "🎪 Fun Sorting Examples:\n• 🔴 Red toys: fire truck, apple, strawberry\n• 🟦 Blue toys: ocean ball, blueberry, sky picture\n• 📏 Big things: elephant, house, tree\n• 🤏 Small things: ant, button, coin\n• ⭕ Round things: ball, wheel, pizza\n• ⬜ Square things: book, window, gift box"
            )
            4 -> Pair(
                "🔘 Objects come in different shapes that help us describe them!\n\n⭕ ROUND objects are circular like a wheel\n📏 LONG objects are stretched out like a pencil\n⬜ SQUARE objects have four equal sides\n📐 TRIANGLE objects have three sides\n\n🎯 Recognizing shapes helps us understand our world better!",
                "🌈 Shape Examples:\n• ⭕ ROUND: 🏀 basketball, 🍪 cookie, 🌕 full moon\n• 📏 LONG: ✏️ pencil, 🐍 snake, 🥖 baguette\n• ⬜ SQUARE: 📺 TV screen, 🧇 waffle, 🎁 gift box\n• 📐 TRIANGLE: 🏔️ mountain, 🍕 pizza slice, ⛵ sail\n• 🔶 OVAL: 🥚 egg, 🏈 football, 👁️ eye"
            )
            5 -> Pair(
                "🎢 Objects move in different ways depending on their shape!\n\n🎳 ROLLING objects turn over and over as they move\n🛷 SLIDING objects move smoothly across surfaces\n⭕ Round objects usually roll because of their shape\n⬜ Flat objects usually slide because they're smooth\n\n🎯 Understanding movement helps us predict how things will behave!",
                "🏃 Movement Examples:\n• 🎳 ROLLING: ⚽ soccer ball, 🎾 tennis ball, 🥎 baseball\n• 🛷 SLIDING: 📚 book, 📦 box, 💳 card\n• 🎪 BOTH: 🪙 coin (can roll and slide!)\n• 🧊 Ice makes everything slide easily\n• 🛹 Wheels help things roll smoothly\n• 🏔️ Things slide down hills"
            )
            6 -> Pair(
                "🔢 Let's learn to count from 1 to 9 - the foundation of all math!\n\n1️⃣ One - The beginning number\n2️⃣ Two - A pair\n3️⃣ Three - A small group\n4️⃣ Four - Like a square's sides\n5️⃣ Five - Fingers on one hand\n6️⃣ Six - Half a dozen\n7️⃣ Seven - Lucky number\n8️⃣ Eight - Like a spider's legs\n9️⃣ Nine - Almost ten!\n\n✨ Counting helps us understand how many things we have!",
                "🎈 Counting Fun:\n• 1️⃣ 🍎 One apple\n• 2️⃣ 👀 Two eyes\n• 3️⃣ 🚲 Three wheels on a tricycle\n• 4️⃣ 🪑 Four legs on a chair\n• 5️⃣ ✋ Five fingers on one hand\n• 6️⃣ 🦴 Six legs on an insect\n• 7️⃣ 🌈 Seven colors in a rainbow\n• 8️⃣ 🕷️ Eight legs on a spider\n• 9️⃣ ⚾ Nine players on a baseball team"
            )
            7 -> Pair(
                "⚖️ Comparing quantities helps us understand 'how much'!\n\n➕ MORE means a bigger amount or quantity\n➖ LESS means a smaller amount or quantity\n🟰 SAME/EQUAL means the amounts are identical\n\n🎯 Comparing helps us make decisions and understand differences!",
                "🎪 Comparison Examples:\n• 🍪 5 cookies is MORE than 3 cookies\n• 🧸 2 toys is LESS than 6 toys\n• 🍎 4 apples is the SAME as 4 oranges\n• 🎈 Which has more: 7 balloons or 4 balloons?\n• 🐱 3 cats is LESS than 8 cats\n• ⭐ 5 stars equals 5 stars - they're the SAME!"
            )
            8 -> Pair(
                "📊 Numbers have a special order that never changes!\n\n🔢 The number line: 1, 2, 3, 4, 5, 6, 7, 8, 9\n⬆️ Each number is one MORE than the number before it\n⬇️ Each number is one LESS than the number after it\n🔄 We can count forward (up) or backward (down)\n\n🎯 Number order helps us organize and compare amounts!",
                "🎮 Number Order Games:\n• ❓ What comes AFTER 3? Answer: 4️⃣\n• ❓ What comes BEFORE 7? Answer: 6️⃣\n• 🎯 Fill the gap: 1, 2, 3, ❓, 5 Answer: 4️⃣\n• 🔙 Count backward: 5, 4, 3, ❓, 1 Answer: 2️⃣\n• 🏃 Skip count: 2, 4, 6, ❓ Answer: 8️⃣\n• 🎪 Between what numbers is 5? Answer: 4️⃣ and 6️⃣"
            )
            9 -> Pair(
                "🔟 Teen numbers are special numbers from 10 to 19!\n\n🔟 Ten - Two digits start here!\n1️⃣1️⃣ Eleven - Sounds different\n1️⃣2️⃣ Twelve - Also sounds different\n1️⃣3️⃣ Thirteen - Thir-TEEN (like 3 + teen)\n1️⃣4️⃣ Fourteen - Four-TEEN\n1️⃣5️⃣ Fifteen - Fif-TEEN\n1️⃣6️⃣ Sixteen - Six-TEEN\n1️⃣7️⃣ Seventeen - Seven-TEEN\n1️⃣8️⃣ Eighteen - Eigh-TEEN\n1️⃣9️⃣ Nineteen - Nine-TEEN\n2️⃣0️⃣ Twenty - Two-ty!\n\n✨ These numbers bridge single digits to bigger numbers!",
                "🎊 Teen Number Fun:\n• 🎂 10 candles make double digits!\n• 🏀 11 players in a soccer team\n• 📅 12 months in a year\n• 🍀 13 is considered lucky or unlucky\n• 💕 14 is Valentine's Day\n• 🎂 Sweet 16 birthday\n• 🎯 All teens end in 'teen' except 10, 11, 12, 20\n• 🔢 They all start with '1' except 20"
            )
            10 -> Pair(
                "✏️ Writing numbers correctly takes practice and patience!\n\n🎯 Start at the TOP of each number\n➡️ Follow the correct direction and strokes\n🔄 Practice makes perfect - keep trying!\n📏 Some numbers have curves, others have straight lines\n✨ Good handwriting helps others read your numbers!\n\n🎨 Each number has its own special way to be written!",
                "🎪 Number Writing Guide:\n• 1️⃣ Start at top, draw straight line DOWN\n• 2️⃣ Curve RIGHT, then curve LEFT and line ACROSS\n• 3️⃣ Two curves going to the RIGHT\n• 4️⃣ Line DOWN, line ACROSS, then line DOWN\n• 5️⃣ Line DOWN, ACROSS top, curve for bottom\n• 6️⃣ Big curve starting from top\n• 7️⃣ Line ACROSS top, then diagonal DOWN\n• 8️⃣ Make an 'S' then close it up\n• 9️⃣ Circle at top, then line DOWN"
            )
            11 -> Pair(
                "➕ Addition means putting numbers together to get more!\n\n🎯 When we ADD, we combine amounts\n📈 The answer gets BIGGER (usually)\n➕ The '+' sign means 'plus' or 'add'\n🟰 The '=' sign means 'equals' or 'the same as'\n✨ Addition is like collecting things together!\n\n🎪 Adding makes groups bigger and helps us count totals!",
                "🎈 Addition Adventures:\n• 🍎 2 apples + 1 apple = 3 apples total\n• 🐱 1 cat + 2 cats = 3 cats altogether\n• ⭐ 3 stars + 2 stars = 5 stars in total\n• 🎁 4 gifts + 1 gift = 5 gifts to open\n• 🏀 2 balls + 3 balls = 5 balls to play with\n• 🌸 1 flower + 4 flowers = 5 beautiful flowers"
            )
            12 -> Pair(
                "🎯 Addition practice helps us get faster and more confident!\n\n🏃 Start with small numbers first\n🧠 Try to remember common addition facts\n✋ Use your fingers if you need to count\n🎲 Practice with toys, blocks, or objects you can see\n📈 The more you practice, the easier it becomes!\n\n⭐ Regular practice makes addition automatic!",
                "🎮 Practice Problems:\n• 🎈 1 + 1 = ❓ (Answer: 2)\n• 🍪 2 + 2 = ❓ (Answer: 4)\n• 🌟 3 + 1 = ❓ (Answer: 4)\n• 🎁 2 + 3 = ❓ (Answer: 5)\n• 🐰 4 + 1 = ❓ (Answer: 5)\n• 🏆 3 + 3 = ❓ (Answer: 6)\n• 🎪 Remember: Start with the bigger number!"
            )
            13 -> Pair(
                "➖ Subtraction means taking away or removing some amount!\n\n🎯 When we SUBTRACT, we remove things from a group\n📉 The answer gets SMALLER\n➖ The '-' sign means 'minus' or 'take away'\n🟰 The '=' sign still means 'equals'\n✨ Subtraction is like eating cookies from a jar!\n\n🎪 Taking away makes groups smaller!",
                "🍪 Subtraction Stories:\n• 🍎 5 apples - 2 apples = 3 apples left\n• 🎈 4 balloons - 1 balloon = 3 balloons remaining\n• 🐱 6 cats - 2 cats = 4 cats still here\n• 🌟 7 stars - 3 stars = 4 stars left\n• 🎁 8 gifts - 2 gifts = 6 gifts remaining\n• 🏀 9 balls - 4 balls = 5 balls left to play"
            )
            14 -> Pair(
                "🎯 Subtraction practice makes us subtraction superstars!\n\n🏃 Start with small numbers you can visualize\n🧠 Remember: subtraction is the opposite of addition\n✋ Count backwards using your fingers\n🎲 Use real objects to 'take away' and count what's left\n📉 Practice helps you subtract quickly!\n\n⭐ The more you practice, the easier taking away becomes!",
                "🎮 Subtraction Practice:\n• 🎈 3 - 1 = ❓ (Answer: 2)\n• 🍪 4 - 2 = ❓ (Answer: 2)\n• 🌟 5 - 1 = ❓ (Answer: 4)\n• 🎁 5 - 3 = ❓ (Answer: 2)\n• 🐰 6 - 2 = ❓ (Answer: 4)\n• 🏆 7 - 3 = ❓ (Answer: 4)\n• 🎪 Tip: Count what's left after taking away!"
            )
            15 -> Pair(
                "📏 Comparing length helps us understand how long or short things are!\n\n📏 LONG objects stretch out far\n🤏 SHORT objects don't stretch out much\n📐 We can compare by putting objects side by side\n👁️ Our eyes help us see which is longer\n✨ Length is how far something reaches from end to end!\n\n🎯 Comparing lengths helps us understand sizes!",
                "🎪 Length Comparisons:\n• 🐍 A snake is LONGER than a worm\n• ✏️ A pencil is SHORTER than a baseball bat\n• 🚗 A car is LONGER than a bicycle\n• 🎋 A tree is LONGER than a flower\n• 📱 A phone is SHORTER than a ruler\n• 🏊 A swimming pool is LONGER than a bathtub\n• 🎯 Line up objects to compare easily!"
            )
            16 -> Pair(
                "📏 Comparing height helps us understand how tall or short things are!\n\n🏔️ TALL objects reach high up\n🐭 SHORT objects stay close to the ground\n📐 We can compare by standing objects next to each other\n👁️ Look up and down to see which is taller\n✨ Height is how far something reaches from bottom to top!\n\n🎯 Comparing heights helps us understand vertical sizes!",
                "🎪 Height Comparisons:\n• 🏔️ A mountain is TALLER than a hill\n• 🌳 A tree is TALLER than a bush\n• 🦒 A giraffe is TALLER than a horse\n• 🏠 A house is TALLER than a car\n• 🗼 A tower is TALLER than a person\n• 🌻 A sunflower is TALLER than a daisy\n• 🎯 Stand things up to compare their heights!"
            )
            17 -> Pair(
                "⚖️ Comparing weight helps us understand how heavy or light things are!\n\n🏋️ HEAVY objects are hard to lift\n🪶 LIGHT objects are easy to lift\n⚖️ We can feel weight by holding objects\n💪 Heavy things need more strength to move\n✨ Weight is how much something presses down!\n\n🎯 Understanding weight helps us know what we can carry!",
                "🎪 Weight Comparisons:\n• 🐘 An elephant is HEAVIER than a mouse\n• 🪶 A feather is LIGHTER than a rock\n• 📚 A book is HEAVIER than a paper\n• 🎈 A balloon is LIGHTER than a ball\n• 🏋️ A dumbbell is HEAVIER than a toy\n• ☁️ A cloud looks light but contains heavy water!\n• 🎯 Try lifting different objects to feel their weight!"
            )
            18 -> Pair(
                "🔢 Let's learn to count all the way to 50 - that's a big number!\n\n📈 After 9 comes 10, then 11, 12... up to 19\n2️⃣0️⃣ Then we reach 20, 21, 22... up to 29\n3️⃣0️⃣ Then 30, 31, 32... up to 39\n4️⃣0️⃣ Then 40, 41, 42... up to 49\n5️⃣0️⃣ Finally we reach 50!\n\n✨ Counting to 50 opens up a whole new world of numbers!",
                "🎊 Big Number Adventures:\n• 🎂 Count 20 birthday candles\n• 📅 30 days in some months\n• 🏫 40 students in a big class\n• ⭐ 50 stars on a flag\n• 🎈 Count balloons at a party\n• 🍭 Count candy in a jar\n• 🎯 Practice counting by 10s: 10, 20, 30, 40, 50!\n• 🚗 Count cars in a parking lot"
            )
            19 -> Pair(
                "🔢 Number patterns are like number puzzles that follow rules!\n\n👫 Skip counting by 2s: 2, 4, 6, 8, 10...\n🏃 Skip counting by 5s: 5, 10, 15, 20, 25...\n🔟 Skip counting by 10s: 10, 20, 30, 40, 50...\n🎯 Even numbers: 2, 4, 6, 8, 10...\n🎪 Odd numbers: 1, 3, 5, 7, 9...\n\n✨ Patterns help us predict what number comes next!",
                "🎮 Pattern Games:\n• 👥 By 2s: 2, 4, 6, ❓, 10 (Answer: 8)\n• ✋ By 5s: 5, 10, ❓, 20, 25 (Answer: 15)\n• 🔟 By 10s: 10, ❓, 30, 40 (Answer: 20)\n• 🎈 Even: 2, 4, ❓, 8 (Answer: 6)\n• 🎪 Odd: 1, 3, 5, ❓ (Answer: 7)\n• 🎯 Find the pattern and fill in the missing number!"
            )
            20 -> Pair(
                "🌈 Color patterns are beautiful sequences that repeat!\n\n🔴 Colors can follow a pattern: red, blue, red, blue...\n🎨 Patterns can have 2 colors or more\n🔄 The pattern repeats over and over\n👁️ We can see what color comes next\n✨ Color patterns are everywhere around us!\n\n🎯 Recognizing color patterns helps us predict and create!",
                "🎪 Colorful Pattern Fun:\n• 🔴🔵🔴🔵❓ (Answer: 🔴 Red)\n• 🟢🟡🟢🟡❓ (Answer: 🟢 Green)\n• 🔴🔴🔵🔴🔴❓ (Answer: 🔵 Blue)\n• 🌈 Rainbow pattern: Red, Orange, Yellow, Green...\n• 🎈 Party balloons in patterns\n• 🧱 Brick patterns on buildings\n• 🎯 Look for patterns in clothes, tiles, and decorations!"
            )
            21 -> Pair(
                "🔷 Shape patterns are sequences of shapes that repeat!\n\n⭕ Shapes can follow patterns: circle, square, circle, square...\n🔺 Patterns can use triangles, circles, squares, and more\n🔄 The pattern repeats in the same order\n👁️ We can predict which shape comes next\n✨ Shape patterns are found in art, buildings, and nature!\n\n🎯 Understanding shape patterns helps us see order in the world!",
                "🎪 Shape Pattern Adventures:\n• ⭕⬜⭕⬜❓ (Answer: ⭕ Circle)\n• 🔺🔺⭕🔺🔺❓ (Answer: ⭕ Circle)\n• ⬜🔺⭕⬜🔺❓ (Answer: ⭕ Circle)\n• 🏠 House patterns on a street\n• 🧱 Tile patterns on floors\n• 🌸 Flower patterns in gardens\n• 🎯 Create your own shape patterns!"
            )
            22 -> Pair(
                "🪙 Money helps us buy things we need and want!\n\n🪙 COINS are round pieces of metal money\n💵 BILLS are paper money\n💰 Different coins and bills have different values\n🛒 We use money to trade for things in stores\n✨ Money represents value and helps us get what we need!\n\n🎯 Understanding money helps us in everyday life!",
                "💰 Money Examples:\n• 🪙 Penny = 1 cent (¢)\n• 🪙 Nickel = 5 cents\n• 🪙 Dime = 10 cents\n• 🪙 Quarter = 25 cents\n• 💵 Dollar bill = 100 cents\n• 🛒 Use money to buy toys, food, clothes\n• 🏪 Stores accept money for their items\n• 🎯 Count your coins to see how much you have!"
            )
            23 -> Pair(
                "🛒 When we buy things, we trade our money for items we want!\n\n💰 We give money to the store\n🎁 The store gives us the item\n🧾 Sometimes we get a receipt (paper showing what we bought)\n💵 We need enough money to buy what we want\n✨ Buying things is a trade - money for goods!\n\n🎯 Learning to buy things teaches us about value and exchange!",
                "🛍️ Shopping Adventures:\n• 🍎 Buy an apple for 50 cents\n• 🧸 Buy a toy for 2 dollars\n• 📚 Buy a book for 5 dollars\n• 🍪 Buy cookies for 1 dollar\n• 🎈 Buy a balloon for 25 cents\n• 🥤 Buy juice for 75 cents\n• 🎯 Count your money before you buy!\n• 💡 Make sure you have enough money!"
            )
            24 -> Pair(
                "👥 Making groups means organizing things into equal sets!\n\n🎯 EQUAL groups have the same amount in each group\n📊 We can divide objects into fair groups\n👫 Like sharing toys equally with friends\n🍕 Like cutting a pizza into equal slices\n✨ Equal groups help us share fairly!\n\n🎪 Making equal groups teaches us about fairness and division!",
                "🎲 Equal Groups Fun:\n• 🍪 6 cookies ÷ 2 groups = 3 cookies in each group\n• 🎈 8 balloons ÷ 4 groups = 2 balloons in each group\n• 🧸 9 toys ÷ 3 groups = 3 toys in each group\n• 🍎 10 apples ÷ 5 groups = 2 apples in each group\n• 👥 Share equally with friends\n• 🎁 Equal groups for party favors\n• 🎯 Everyone gets the same amount!"
            )
            25 -> Pair(
                "➕ Repeated addition means adding the same number over and over!\n\n🔄 Instead of 2+2+2, we can say 'three 2s'\n📈 It's like addition, but faster for equal groups\n🎯 3 groups of 4 = 4+4+4 = 12\n⭐ It helps us count groups quickly\n✨ Repeated addition is the beginning of multiplication!\n\n🎪 This makes counting large amounts much easier!",
                "🎮 Repeated Addition Games:\n• 🍎 3 groups of 2 apples = 2+2+2 = 6 apples\n• 🎈 4 groups of 3 balloons = 3+3+3+3 = 12 balloons\n• 🧸 2 groups of 5 toys = 5+5 = 10 toys\n• 🍪 5 groups of 2 cookies = 2+2+2+2+2 = 10 cookies\n• 🌟 6 groups of 1 star = 1+1+1+1+1+1 = 6 stars\n• 🎯 Count by adding the same number!"
            )
            26 -> Pair(
                "🌅 Day and night help us understand time and daily cycles!\n\n☀️ DAY is when the sun shines and it's bright\n🌙 NIGHT is when it's dark and we see stars\n🔄 Day and night take turns every 24 hours\n😴 We usually sleep at night and are awake during the day\n✨ This cycle helps us organize our daily activities!\n\n🎯 Understanding day and night helps us plan our time!",
                "🌟 Day and Night Activities:\n• ☀️ DAY time: playing, eating breakfast, school\n• 🌙 NIGHT time: sleeping, dinner, bedtime stories\n• 🌅 Morning starts the day\n• 🌆 Evening ends the day\n• ⏰ Clocks help us tell day and night time\n• 🦉 Some animals are active at night\n• 🐦 Some animals are active during the day\n• 🎯 What do you do during the day vs. night?"
            )
            27 -> Pair(
                "📅 Yesterday, today, and tomorrow help us understand time sequence!\n\n⬅️ YESTERDAY was the day before today (it already happened)\n📍 TODAY is right now (it's happening)\n➡️ TOMORROW is the day after today (it hasn't happened yet)\n🔄 These words help us talk about when things happen\n✨ Understanding time sequence helps us plan and remember!\n\n🎯 These time words organize our experiences!",
                "⏰ Time Sequence Examples:\n• 📖 YESTERDAY we read a story\n• 🎮 TODAY we are learning\n• 🎪 TOMORROW we will play at the park\n• 🍎 What did you eat YESTERDAY?\n• 🎈 What are you doing TODAY?\n• 🎁 What will you do TOMORROW?\n• 📅 Yesterday ← Today ← Tomorrow\n• 🎯 Use these words to tell time stories!"
            )
            28 -> Pair(
                "📊 Counting objects helps us organize information and understand amounts!\n\n🔢 Count how many of each type you have\n📋 Make lists or charts to organize your counting\n📊 Compare different amounts you've counted\n🎯 Organize objects before counting them\n✨ Counting and organizing helps us understand data!\n\n🎪 This skill helps us make sense of the world around us!",
                "📈 Counting and Organizing Fun:\n• 🍎 Count red apples vs green apples\n• 🚗 Count different colored cars\n• 🦆 Count animals at the pond\n• 📚 Count books on different shelves\n• 🌸 Count different types of flowers\n• 🧸 Count toys by type: dolls, cars, blocks\n• 📊 Make simple charts of what you count\n• 🎯 Organize first, then count for accuracy!"
            )
            29 -> Pair(
                "⚖️ Comparing groups helps us understand 'more,' 'less,' and 'equal'!\n\n📊 Look at different groups and compare their amounts\n➕ MORE means one group has a bigger amount\n➖ LESS means one group has a smaller amount\n🟰 EQUAL means both groups have the same amount\n✨ Comparing helps us make decisions and understand differences!\n\n🎯 This is the foundation for understanding math relationships!",
                "🎪 Group Comparison Adventures:\n• 🍎 5 red apples vs 3 green apples (red has MORE)\n• 🐱 2 cats vs 7 dogs (cats have LESS)\n• 🎈 4 blue balloons vs 4 red balloons (EQUAL amounts)\n• 🌟 Which group has more stars? ⭐⭐⭐ vs ⭐⭐⭐⭐⭐\n• 🧸 Compare toy collections with friends\n• 🍪 Who has more cookies?\n• 🎯 Line up objects to compare easily!\n• 🏆 Congratulations! You've completed all 29 topics!"
            )
            else -> Pair(
                "🎉 Congratulations! You've reached an advanced topic!\n\n🚀 This topic will challenge you to use everything you've learned\n🧠 Remember to take your time and think carefully\n💪 You're becoming a math superstar!\n✨ Keep practicing and exploring new ideas!\n\n🎯 Every topic builds on what you learned before!",
                "🌟 Keep Learning:\n• 📚 Review previous topics if you need help\n• 🎯 Practice makes perfect\n• 🤝 Ask for help when you need it\n• 🎪 Learning is an adventure\n• 🏆 You're doing great!\n• 🚀 Ready for new challenges\n• 💫 Keep exploring and discovering!"
            )
        }
    }
}