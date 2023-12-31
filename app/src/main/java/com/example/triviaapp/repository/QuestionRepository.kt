package com.example.triviaapp.repository

import android.util.Log
import com.example.triviaapp.data.DataOrException
import com.example.triviaapp.model.QuestionItem
import com.example.triviaapp.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    private val listOfQuestions = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()


    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception>{
        try {
            listOfQuestions.isLoading = true
            listOfQuestions.data = api.getAllQuestions()
            if(listOfQuestions.data.toString().isNotEmpty()) listOfQuestions.isLoading = false

        }catch (exception: Exception) {
            listOfQuestions.e = exception
            Log.d("MyException", "getAllQuestions: ${listOfQuestions.e!!.localizedMessage}")
        }
        return listOfQuestions
    }
}