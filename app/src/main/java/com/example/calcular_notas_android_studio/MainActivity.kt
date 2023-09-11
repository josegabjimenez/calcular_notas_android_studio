package com.example.calcular_notas_android_studio

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.calcular_notas_android_studio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    var name: String? = null
    var course: String? = null
    var grade1: Float? = null
    var grade2: Float? = null
    var isRepeatedCourse: Boolean = false
    var finalGrade: Float? = null
    var listOfStudents: MutableList<Student> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        listeners()
        calculateFinalGrade()
    }

    private fun listeners(){
        binding.etName.addTextChangedListener {
            val currentName = binding.etName.text.toString()
            if(currentName.isNotEmpty()){
                name = currentName
            } else {
                name = null
            }
            activateButton()
        }

        binding.etCourse.addTextChangedListener {
            val currentCourse = binding.etCourse.text.toString()
            if(currentCourse.isNotEmpty()){
                course = currentCourse
            } else {
                course = null
            }
            activateButton()
        }

        binding.etGrade1.addTextChangedListener {
            val currentGrade1 = binding.etGrade1.text.toString()

            if(currentGrade1.isNotEmpty()){
                val currentGrade1Number = currentGrade1.toFloat()
                grade1 = currentGrade1Number
            } else {
                grade1 = null
            }
            activateButton()
        }

        binding.etGrade2.addTextChangedListener {
            val currentGrade2 = binding.etGrade2.text.toString()

            if(currentGrade2.isNotEmpty()){
                val currentGrade2Number = currentGrade2.toFloat()
                grade2 = currentGrade2Number
            } else {
                grade2 = null
            }
            activateButton()
        }

        binding.cbRepeatedCourse.setOnCheckedChangeListener { _, isChecked ->
            isRepeatedCourse = isChecked
        }


    }

    private fun activateButton(){
        binding.btnCalculateGrade.isEnabled = !name.isNullOrBlank() && !course.isNullOrBlank() && grade1.toString() != "null" && grade2.toString() !== "null"
    }

    private fun calculateFinalGrade(){
        binding.btnCalculateGrade.setOnClickListener {
            var finalGrade = ((grade1 ?: 0f) + (grade2 ?: 0f)) / 2
            if (isRepeatedCourse) finalGrade -= 0.5f
            listOfStudents.add(Student(name?: "", course?: "", grade1?: 0f, grade2?: 0f, finalGrade))
            showStudents()
            resetParameters()
        }
    }

    private fun showStudents(){
        var message = ""
        for (student in listOfStudents){
            message += "Nombre: ${student.name}\n Curso: ${student.course}\n Nota1: ${student.grade1}\n Nota2: ${student.grade2}\n Nota Final: ${student.finalGrade}\n\n\n"
        }
        binding.tvResult.text = message
    }

    private fun resetParameters(){
        name = null
        course = null
        grade1 = null
        grade2 = null
        finalGrade = null
        isRepeatedCourse = false

        binding.etName.setText("")
        binding.etCourse.setText("")
        binding.etGrade1.setText("")
        binding.etGrade2.setText("")
        binding.cbRepeatedCourse.isChecked = false

    }
}

class Student (var name: String, var course: String, var grade1: Float, var grade2: Float, var finalGrade: Float)