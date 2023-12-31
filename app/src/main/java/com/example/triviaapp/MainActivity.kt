package com.example.triviaapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaapp.screens.QuestionViewModel
import com.example.triviaapp.ui.theme.TriviaAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TriviaAppTheme {
//                TriviaHome()
                Questions(viewModel = hiltViewModel())
            }
        }
    }
}

//@Composable
//fun TriviaHome(viewModel: QuestionViewModel = hiltViewModel()) {
//    Questions(viewModel)
//}

@Composable
fun Questions(viewModel: QuestionViewModel) {
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TriviaAppTheme {

    }
}