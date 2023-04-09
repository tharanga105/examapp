package com.example.examapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.lifecycleOwner = this
        dataBinding.vm = viewModel

        dataBinding.button.setOnClickListener {
            login()
        }



        private fun login() {

            val username = viewModel.username
            val password = viewModel.password

            when {
                username.isBlank() -> {
                    Toast.makeText(this, "Username is mandatory", Toast.LENGTH_SHORT).show()
                }

                password.isBlank() -> {
                    Toast.makeText(applicationContext, "password is mandatory", Toast.LENGTH_SHORT).show()
                }
                else -> {

                    val user = User(username, password)

                    SharedPref(this).saveUserToPref(user, object : UserPreferenceListener {

                        override fun onSuccess(userCount: Int) {
                            Log.d(TAG, userCount.toString())
                            viewModel.setItemCount(userCount)
                        }

                        override fun onError() {
                            Log.d(TAG, "Error")

                        }
                    })


                }
            }

        }

        override fun onResume() {
            super.onResume()
            viewModel.setItemCount(SharedPref(this).getUserCount())
        }

    }