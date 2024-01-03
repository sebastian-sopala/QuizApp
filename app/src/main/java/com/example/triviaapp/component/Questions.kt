package com.example.triviaapp.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaapp.model.QuestionItem
import com.example.triviaapp.screens.QuestionViewModel
import com.example.triviaapp.util.AppColors

//@Composable
//fun TriviaHome(viewModel: QuestionViewModel = hiltViewModel()) {
//    Questions(viewModel)
//}

@Composable
fun Questions(
    viewModel: QuestionViewModel = hiltViewModel(),
) {
    val questions = viewModel.data.value.data?.toMutableList()

    var questionIndex = remember {
        mutableStateOf(0)
    }

    if (viewModel.data.value.isLoading == true) {
        Log.d("22Test", "List size: ${questions?.size} coz its loading")

        CircularProgressIndicator()
    } else {
        Log.d("22Test", "List size: ${questions?.size}, loaded")

        questions?.forEachIndexed { index, questionItem ->
            Log.d("22Test", "$index, ${questionItem.question}")
        }

        val question = try {
            questions?.get(questionIndex.value)
        } catch (ex: Exception) {
            null
        }

        if (questions != null) {
            QuestionCard(
                question = question!!,
                questionIndex = questionIndex,
                allQuestionsCount = questions.size
            ) {
                questionIndex.value = questionIndex.value + 1
            }
        }
    }
}

//@Preview
@Composable
fun QuestionCard(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    allQuestionsCount: Int,
    onNextClicked: (Int) -> Unit,
) {

    val choicesState = remember(question) {
        question.choices.toMutableList()
    }

    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }

    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }

    val dashedLine = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    Surface(modifier = Modifier.fillMaxSize(), color = AppColors.mDarkPurple) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Score(questionIndex.value)
            QuestionTracker(questionCount = questionIndex.value, allQuestions = allQuestionsCount)
            Divider(lineStyle = dashedLine)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = question.question,
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .padding(12.dp),
                    color = AppColors.mOffWhite,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )

                choicesState.forEachIndexed { index, text ->
                    Row(
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxWidth()
                            .height(55.dp)
                            .border(
                                width = 2.dp, shape = RoundedCornerShape(15.dp), color = Color.White
                            )
                            .background(Color.Transparent), verticalAlignment = CenterVertically
                    ) {
                        RadioButton(
                            selected = (answerState.value == index),
                            onClick = { updateAnswer(index) },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(
//                                selectedColor = Color.Green.copy(0.2f)
                                selectedColor = if (correctAnswerState.value == true
                                // && index == answerState.value - ?? TODO
                                ) {
                                    Color.Green.copy(0.2f)
                                } else {
                                    Color.Red.copy(0.2f)
                                }
                            )
                        )

                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color = if (correctAnswerState.value == true && index == answerState.value) {
                                        Color.Green
                                    } else if (correctAnswerState.value == false && index == answerState.value) {
                                        Color.Red
                                    } else {
                                        AppColors.mOffWhite
                                    }
                                )
                            ) { append(text) }
                        }
                        Text(text = annotatedString)
                    }
                }
                Button(
                    onClick = { onNextClicked(questionIndex.value) },
                    modifier = Modifier
                        .height(50.dp)
                        .width(100.dp)
                        .fillMaxWidth(0.5f),
                ) {
                    Text(text = "NEXT")
                }
            }
        }
    }
}

@Preview
@Composable
fun Score(score: Int = 1) {

    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075), Color(0xFFBE6BE5)))
    val progressFactor = remember(score) {
        mutableStateOf(score * 0.005f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(
                width = 3.dp,
                shape = RoundedCornerShape(30.dp),
                brush = Brush.linearGradient(listOf(AppColors.mLightPurple, AppColors.mLightPurple))
            )
            .clip(RoundedCornerShape(30.dp)),
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(progressFactor.value)
                .height(50.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(gradient),
            enabled = false,
//            colors = TODO

        ) {
            Text(
                text = "$score",
                modifier = Modifier.clip(RoundedCornerShape(30.dp))
            )
        }
    }
}

//@Preview
@Composable
fun QuestionTracker(questionCount: Int = 10, allQuestions: Int = 100) {
    Text(modifier = Modifier.padding(20.dp), text = buildAnnotatedString {
        withStyle(ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                SpanStyle(
                    color = AppColors.mLightGray, fontWeight = FontWeight.Bold, fontSize = 24.sp
                )
            ) {
                append("Question $questionCount / ")
            }
            withStyle(
                SpanStyle(
                    color = AppColors.mLightGray,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp
                )
            ) {
                append("$allQuestions")
            }
        }
    })
}

@Composable
fun Divider(lineStyle: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
    ) {
        drawLine(
            color = AppColors.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = lineStyle
        )
    }
}

