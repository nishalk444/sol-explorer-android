package com.nisha.solexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Chapter(val title: String, val standard: String, val visual: String, val summary: String, val quiz: List<Question>)
data class Subject(val title: String, val emoji: String, val color: Color, val chapters: List<Chapter>)
data class Question(val prompt: String, val options: List<String>, val answer: Int, val explanation: String)

private val subjects = listOf(
    Subject("Mathematics", "🔢", Color(0xFFE6F0FF), listOf(
        Chapter("Place Value & Number Sense", "2023 5.NS.1–5.NS.3", "🧱  4 | 3 | 2 | 1", "Read, compare, round, and compose whole numbers and decimals.", listOf(Question("Which is greatest?", listOf("0.45", "0.405", "0.5", "0.055"), 2, "0.5 has five tenths, more than the other numbers."))),
        Chapter("Operations with Whole Numbers", "2023 5.CE.1–5.CE.3", "✖️  134 × 34", "Use algorithms, estimation, and equations to solve multi-step problems.", listOf(Question("134 × 34 = ?", listOf("4,256", "4,556", "3,556", "5,256"), 1, "134 × 30 = 4,020 and 134 × 4 = 536; total 4,556."))),
        Chapter("Fractions & Decimals", "2023 5.NS.4–5.NS.7", "🍕  3/4 = 0.75", "Add, subtract, multiply, and divide fractions and decimals.", listOf(Question("Which decimal equals 3/4?", listOf("0.25", "0.34", "0.75", "1.25"), 2, "Three out of four equal 0.75."))),
        Chapter("Measurement & Geometry", "2023 5.MG.1–5.MG.5", "📐  A = l × w", "Convert units and find perimeter, area, volume, and angle measures.", listOf(Question("Area of a 6 by 4 rectangle?", listOf("10 sq units", "20 sq units", "24 sq units", "28 sq units"), 2, "Area = length × width = 6 × 4 = 24."))),
        Chapter("Data, Probability & Patterns", "2023 5.PS.1–5.PS.4", "📊  2, 4, 6, __", "Represent data, identify patterns, and reason about probability.", listOf(Question("What comes next: 2, 4, 6, __?", listOf("7", "8", "9", "10"), 1, "Add 2 each time, so the next number is 8.")))
    )),
    Subject("English", "📚", Color(0xFFFFE7D6), listOf(
        Chapter("Reading: Main Idea & Evidence", "2024 5.RL.1–5.RI.3", "🔎  idea + evidence", "Find central ideas, summarize, and support answers with text evidence.", listOf(Question("The main idea is…", listOf("a tiny detail", "what the text is mostly about", "the title only", "a question"), 1, "The main idea tells what a text is mostly about."))),
        Chapter("Vocabulary in Context", "2024 5.RV.1–5.RV.3", "🧩  context clues", "Use roots, affixes, reference tools, and context to determine meaning.", listOf(Question("In 'The arid desert was dry,' arid means…", listOf("wet", "cold", "dry", "crowded"), 2, "The sentence gives a synonym: arid means dry."))),
        Chapter("Writing & Organization", "2024 5.W.1–5.W.5", "📝  claim → reasons → evidence", "Plan, draft, revise, edit, and publish opinion, informative, and narrative writing.", listOf(Question("A strong opinion paragraph needs…", listOf("a claim and reasons", "only a title", "random facts", "no conclusion"), 0, "A claim is supported by clear reasons and evidence."))),
        Chapter("Grammar & Conventions", "2024 5.C.1–5.C.3", "✅  subject + verb", "Use complete sentences, verb tense, punctuation, and grammar conventions.", listOf(Question("Choose the complete sentence.", listOf("Because she ran.", "Running quickly.", "Maya ran quickly.", "After school."), 2, "Maya ran quickly has a subject and a predicate.")))
    )),
    Subject("Science", "🔬", Color(0xFFE4F7EA), listOf(
        Chapter("Scientific Investigation", "2018 5.1", "👀 → ❓ → 🧪 → 📈", "Ask questions, plan investigations, collect data, and communicate evidence.", listOf(Question("A testable question can be…", listOf("What is my favorite color?", "Does sunlight affect plant growth?", "Why is science fun?", "Who is tallest?"), 1, "A testable question can be investigated and measured."))),
        Chapter("Matter & Energy", "2018 5.4", "⚖️  solid → liquid → gas", "Describe properties of matter and how energy changes matter.", listOf(Question("Water changing to ice is…", listOf("melting", "freezing", "evaporating", "condensing"), 1, "Freezing changes liquid water into solid ice."))),
        Chapter("Sound & Light", "2018 5.2–5.3", "🔊  vibration | 💡 reflection", "Explain sound as vibration and investigate visible light, reflection, and refraction.", listOf(Question("Sound travels through…", listOf("vibrations", "shadows", "stillness", "color"), 0, "Sound energy moves through vibrating particles."))),
        Chapter("Earth, Moon & Sun", "2018 5.6", "☀️ → 🌎 → 🌙", "Model the relationships among the Sun, Earth, Moon, seasons, and day/night.", listOf(Question("Day and night are caused by Earth’s…", listOf("revolution", "rotation", "weather", "gravity only"), 1, "Earth rotates on its axis, creating day and night."))),
        Chapter("Ecosystems & Resources", "2018 5.5", "🌱 → 🐛 → 🐦", "Analyze food webs, adaptations, and how people use and protect resources.", listOf(Question("A producer is usually a…", listOf("plant", "lion", "mushroom", "rock"), 0, "Plants make their own food using sunlight.")))
    )),
    Subject("History & Social Science", "🏛️", Color(0xFFF2E8FF), listOf(
        Chapter("Virginia Geography", "VS.1–VS.2", "🗺️  mountains • piedmont • tidewater", "Use maps and geographic tools to locate Virginia regions and resources.", listOf(Question("Which is a Virginia geographic region?", listOf("Tidewater", "Sahara", "Alps", "Amazon"), 0, "Tidewater is one of Virginia’s geographic regions."))),
        Chapter("Indigenous Peoples & Exploration", "VS.3–VS.4", "🛶  🧭  🌎", "Describe Indigenous cultures and European exploration and settlement.", listOf(Question("The Powhatan lived in…", listOf("Virginia", "Alaska", "Hawaii", "Oregon"), 0, "The Powhatan people lived in the Tidewater region of Virginia."))),
        Chapter("Colonial Virginia", "VS.5–VS.6", "🏘️  Jamestown 1607", "Explain Jamestown, colonial government, economy, and daily life.", listOf(Question("Jamestown was founded in…", listOf("1492", "1607", "1776", "1865"), 1, "Jamestown was established in 1607."))),
        Chapter("Revolution & Independence", "VS.7–VS.8", "🗣️  rights → declaration", "Connect Virginia to the American Revolution and the Declaration of Independence.", listOf(Question("The Declaration of Independence announced…", listOf("independence from Britain", "a new holiday", "a map", "a trade route"), 0, "It announced that the colonies were independent from Britain."))),
        Chapter("Virginia Government & Civics", "VS.9–VS.10", "⚖️  rights + responsibilities", "Understand Virginia’s role in the nation and the responsibilities of citizens.", listOf(Question("A responsibility of citizens is to…", listOf("ignore laws", "participate and follow laws", "avoid voting forever", "damage resources"), 1, "Citizens help communities by following laws and participating.")))
    ))
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { SolExplorerApp() } }
}

@Composable fun SolExplorerApp() {
    var selectedSubject by remember { mutableStateOf<Subject?>(null) }
    var selectedChapter by remember { mutableStateOf<Chapter?>(null) }
    var testMode by remember { mutableStateOf(false) }
    MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF315EFB))) {
        Surface(Modifier.fillMaxSize(), color = Color(0xFFF7F9FC)) {
            when { testMode -> TestScreen(subjects.flatMap { it.chapters }.flatMap { it.quiz }, onClose = { testMode = false })
                selectedChapter != null -> LessonScreen(selectedChapter!!, onBack = { selectedChapter = null })
                selectedSubject != null -> SubjectScreen(selectedSubject!!, onBack = { selectedSubject = null }, onChapter = { selectedChapter = it })
                else -> HomeScreen(onSubject = { selectedSubject = it }, onTest = { testMode = true }) }
        }
    }
}

@Composable private fun Header(title: String, onBack: (() -> Unit)? = null) { Row(Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically) { if (onBack != null) Text("‹", fontSize = 32.sp, modifier = Modifier.clickable { onBack() }.padding(end = 12.dp)); Text(title, fontSize = 25.sp, fontWeight = FontWeight.Bold) } }
@Composable private fun HomeScreen(onSubject: (Subject) -> Unit, onTest: () -> Unit) { LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) { item { Text("SOL Explorer", fontSize = 32.sp, fontWeight = FontWeight.Bold); Text("Grade 5 • Virginia Standards of Learning", color = Color.DarkGray); Spacer(Modifier.height(8.dp)); Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF315EFB)), shape = RoundedCornerShape(20.dp)) { Column(Modifier.padding(20.dp)) { Text("Learn a little every day", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold); Text("Visual lessons • practice • explanations", color = Color.White); Button(onClick = onTest, modifier = Modifier.padding(top = 12.dp)) { Text("Take a sample test") } } } ; Text("Subjects", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp)) }; items(subjects) { s -> Card(Modifier.fillMaxWidth().clickable { onSubject(s) }, colors = CardDefaults.cardColors(containerColor = s.color), shape = RoundedCornerShape(18.dp)) { Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) { Text(s.emoji, fontSize = 34.sp); Spacer(Modifier.width(16.dp)); Column { Text(s.title, fontWeight = FontWeight.Bold, fontSize = 19.sp); Text("${s.chapters.size} chapters • visual lessons") } } } }; item { Text("Standards note: content is a starter map for Grade 5 Virginia SOL. Review with your child’s teacher as VDOE standards and assessment timelines evolve.", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp)) } } }
@Composable private fun SubjectScreen(subject: Subject, onBack: () -> Unit, onChapter: (Chapter) -> Unit) { LazyColumn(contentPadding = PaddingValues(20.dp)) { item { Header("${subject.emoji} ${subject.title}", onBack); Text("Choose a chapter", color = Color.Gray, modifier = Modifier.padding(bottom = 14.dp)) }; items(subject.chapters) { c -> Card(Modifier.fillMaxWidth().padding(bottom = 12.dp).clickable { onChapter(c) }, colors = CardDefaults.cardColors(containerColor = subject.color)) { Column(Modifier.padding(18.dp)) { Text(c.visual, fontSize = 22.sp); Text(c.title, fontSize = 19.sp, fontWeight = FontWeight.Bold); Text(c.standard, fontSize = 12.sp, color = Color.DarkGray); Text(c.summary, modifier = Modifier.padding(top = 8.dp)) } } } } }
@Composable private fun LessonScreen(chapter: Chapter, onBack: () -> Unit) { var revealed by remember { mutableStateOf(false) }; LazyColumn(contentPadding = PaddingValues(20.dp)) { item { Header(chapter.title, onBack); Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF0FF)), shape = RoundedCornerShape(20.dp)) { Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) { Text(chapter.visual, fontSize = 38.sp); Text(chapter.summary, fontSize = 18.sp, modifier = Modifier.padding(top = 12.dp)); Text("Standard: ${chapter.standard}", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 12.dp)) } }; Text("Try it", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)); val q = chapter.quiz.first(); Text(q.prompt, fontSize = 18.sp, fontWeight = FontWeight.SemiBold); q.options.forEachIndexed { i, option -> OutlinedButton(onClick = { revealed = true }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) { Text("${('A'.code + i).toChar()}. $option") } }; if (revealed) { Text("Explanation: ${q.explanation}", color = Color(0xFF176B3A), modifier = Modifier.padding(top = 12.dp)); Text("Answer key: ${('A'.code + q.answer).toChar()}", fontWeight = FontWeight.Bold) } } } }
@Composable private fun TestScreen(questions: List<Question>, onClose: () -> Unit) { var index by remember { mutableIntStateOf(0) }; var score by remember { mutableIntStateOf(0) }; var answered by remember { mutableStateOf(false) }; if (index >= questions.size) { Column(Modifier.fillMaxSize().padding(28.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) { Text("Test complete!", fontSize = 30.sp, fontWeight = FontWeight.Bold); Text("Score: $score / ${questions.size}", fontSize = 22.sp, modifier = Modifier.padding(16.dp)); Button(onClick = onClose) { Text("Back to subjects") } } } else { val q = questions[index]; LazyColumn(contentPadding = PaddingValues(20.dp)) { item { Header("Sample Test", onClose); LinearProgressIndicator(progress = { (index + 1f) / questions.size }, modifier = Modifier.fillMaxWidth()); Text("Question ${index + 1} of ${questions.size}", color = Color.Gray, modifier = Modifier.padding(top = 12.dp)); Text(q.prompt, fontSize = 21.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 18.dp)); q.options.forEachIndexed { i, option -> Button(onClick = { if (!answered) { if (i == q.answer) score++; answered = true } }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) { Text(option) } }; if (answered) { Text(q.explanation, color = Color(0xFF176B3A), modifier = Modifier.padding(vertical = 12.dp)); Button(onClick = { index++; answered = false }) { Text(if (index == questions.lastIndex) "Finish" else "Next") } } } } } }
