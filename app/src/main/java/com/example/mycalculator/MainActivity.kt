package com.example.mycalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.mycalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric= false
    var stateError=false
    var lastDot=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onoperatorclick(view: View) {
        if(!stateError && lastNumeric){
            binding.text1.append((view as Button).text)
            lastDot=false
            lastNumeric=false
            onEqual()
        }
    }
    fun onallclearclick(view: View) {
        binding.text1.text=""
        binding.answer.text=""
        stateError=false
        lastNumeric=false
        binding.answer.visibility=View.GONE

    }
    fun onequalclick(view: View) {
        onEqual()
        binding.text1.text=binding.answer.text.toString().drop(1)
    }
    fun ondigitclick(view: View) {
        if(stateError){
            binding.text1.text=(view as Button).text
            stateError=false
        }else{
            binding.text1.append((view as Button).text)
        }
        lastNumeric=true
        onEqual()
    }
    fun onclearclick(view: View) {
        binding.text1.text=""
        binding.answer.text=""
        stateError=false
        lastDot=false
        lastNumeric=false
        binding.answer.visibility=View.GONE
    }
    fun onbackclick(view: View) {
        binding.text1.text=binding.text1.text.toString().dropLast(1)
        try {
            val lastChar=binding.text1.text.toString().last()
            if (lastChar.isDigit()){
                onEqual()
            }
        }catch (e: Exception){
            binding.answer.text=""
            binding.answer.visibility=View.GONE
//            Log.e("last char error".e.toString())
            Log.e("last char error",e.toString())
        }
    }
    fun onEqual(){
        if (lastNumeric && !stateError){
            val txt =binding.text1.text.toString()
            var expression = ExpressionBuilder(txt).build()
            try {
                val result= expression.evaluate()
                binding.answer.visibility=View.VISIBLE
                binding.answer.text="="+result.toString()
            }catch (
                ex: ArithmeticException
            ){
                Log.e("evaluate error",ex.toString())
                binding.answer.text= "error"
                stateError=true
                lastNumeric=false


            }
        }

    }
    fun showMessage(context:Context,message: String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

}