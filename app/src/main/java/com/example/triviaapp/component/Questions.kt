package com.example.triviaapp.component

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaapp.screens.QuestionViewModel

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