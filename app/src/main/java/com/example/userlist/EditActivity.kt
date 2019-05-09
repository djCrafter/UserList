package com.example.userlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    lateinit var realm : Realm
    private var editingMode = false
    lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        Realm.init(this)
        realm = Realm.getDefaultInstance()


        if(intent.hasExtra("edit")) {
           val id = intent.getLongExtra("id", 0)
            user = UserRealmCRUD.readUserById(id, realm)!!
            editingMode = true
            setFields()
        }
    }

    private fun setFields() {
        nameField.setText(user.name)
        companyField.setText(user.company)
        phoneField.setText(user.phone)
        aboutField.setText(user.about)
    }


    
}
