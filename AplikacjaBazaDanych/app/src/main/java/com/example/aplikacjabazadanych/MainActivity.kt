package com.example.aplikacjabazadanych

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listOfPhoneNumbers = mutableListOf<Person>()
        val database = getSharedPreferences("phoneNumbers", Context.MODE_PRIVATE)
        var savedPhoneNumbers = database.getString("kolegow", "")

        if(savedPhoneNumbers != ""){
            val turnsType = object : TypeToken<MutableList<Person>>() {}.type
            listOfPhoneNumbers = Gson().fromJson(savedPhoneNumbers, turnsType)
            showContacts(listOfPhoneNumbers)
        }


        addContactButton.setOnClickListener {
            var name = nameEditText.text.toString()
            var phoneNumber = phoneNumberEditText.text.toString()

            if (name.isNotEmpty()) {
                var kolega = Person(name, phoneNumber)

                listOfPhoneNumbers.add(kolega)
                showContacts(listOfPhoneNumbers)
                Toast.makeText(this, "Dodano pomyślnie", Toast.LENGTH_SHORT).show()
                val database = getSharedPreferences("koledzy", Context.MODE_PRIVATE)
                database.edit().putString("kolegow", Gson().toJson(listOfPhoneNumbers)).apply()
                Toast.makeText(this, "dodano kontakt", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun showContacts(list: MutableList<Person>){
        var tekst = ""
        list.forEach {
            tekst += it.name + " = " + it.phoneNumber + "\n"
        }
        dbShot.text = tekst
    }
}
