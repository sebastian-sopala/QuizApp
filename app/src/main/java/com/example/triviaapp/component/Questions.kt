package com.example.triviaapp.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.triviaapp.screens.QuestionViewModel
import com.example.triviaapp.util.AppColors

//@Composable
//fun TriviaHome(viewModel: QuestionViewModel = hiltViewModel()) {
//    Questions(viewModel)
//}

@Composable
fun Questions(viewModel: QuestionViewModel = hiltViewModel()) {
    val questions = viewModel.data.value.data?.toMutableList()

    if (viewModel.data.value.isLoading == true) {
        Log.d("22Test", "List size: ${questions?.size} coz its loading")

        CircularProgressIndicator()
    } else {
        Log.d("22Test", "List size: ${questions?.size}, loaded")

        questions?.forEachIndexed { index, questionItem ->
            Log.d("22Test", "$index, ${questionItem.question}")
        }
    }
}

//@Preview
@Composable
fun QuestionCard() {
    Surface(modifier = Modifier.fillMaxSize(), color = AppColors.mDarkPurple) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
        }
    }
}

@Preview
@Composable
fun QuestionTracker(questionCount: Int = 10, allQuestions: Int = 100) {
    Text(
        modifier = Modifier.padding(20.dp),
        text = buildAnnotatedString {
            withStyle(ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(
                    SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
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
        }
    )
}

