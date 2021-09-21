package com.example.coroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var text:TextView

 private val Result_1="Result #1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button=findViewById(R.id.btnClick)
        text=findViewById(R.id.text)
        button.setOnClickListener {

            CoroutineScope(IO).launch {
                fakeApiRequest()
            }

        }
    }


    //this function helps send data or response to main Thread
    private suspend fun setTextOnMainThread(input: String){
            withContext(Main) {
                setNewText(input)
            }
    }

    //this function helps in set value to text
    private fun setNewText(input:String){
        val newText=text.text.toString()+"\n $input"
        text.text=newText

    }

    // return asynchronously using suspend it make it use coroutine{maybe network request or room persistent lib. }
    private suspend fun getResult1FromApi():String{
        logThread("getResultFromApi")
        delay(1000)
        return Result_1
    }

   //log the thread when it is called
    //coroutines !=Thread
    private fun logThread(methodName:String){
        println("debug. ${methodName}:${Thread.currentThread().name}")
    }

    //putting result into a new variable
    private suspend fun fakeApiRequest(){
        val result1=getResult1FromApi()
        println("debug: $result1")
        setTextOnMainThread(result1)
    }

}
