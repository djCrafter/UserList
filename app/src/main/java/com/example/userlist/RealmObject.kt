package com.example.userlist

import android.content.Context
import io.realm.Realm
import java.lang.Exception

object RealmObject {

    lateinit var realm : Realm

    fun initBase(context: Context) {
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }

    fun createUser(user: User) : Boolean {
      try {
          realm.executeTransaction {
              it.copyToRealm(user)
          }
          return true
      }
      catch (ex: Exception){
          println(ex)
          return false
      }
    }

    fun readUserById(id: Long) : User? {
          var user = User()
          try {
              realm.executeTransaction{
                  user = it.where(User :: class.java).equalTo("id", id).findFirst()!!
              }
          }
          catch (ex: Exception){
              println(ex)
          }
        return user
    }

    fun readAllContacts() : ArrayList<User> {
        var userList = ArrayList<User>()
        try{
            realm.executeTransaction{
               val result = it.where(User::class.java).findAll()
                result.forEach{ user ->
                    userList.add(user)
                }
            }
        }
        catch (ex:Exception) {
            println(ex)
        }
        return userList
    }


    fun updateUser(user: User) : Boolean {
        try{
            realm.executeTransaction {
                it.copyToRealmOrUpdate(user)
            }
            return true
        }
        catch (e: Exception) {
            println(e)
            return false
        }
    }

    fun deleteUser(id: Long) : Boolean {
         try{
            realm.executeTransaction{
                it.where(User :: class.java).equalTo("id", id).findFirst()!!.deleteFromRealm()
            }
             return true
         }
         catch (ex: Exception){
             println(ex)
             return false
         }
    }

    fun getNewId() : Int {
        var id = 0
        try {
            realm.executeTransaction {
                id = it.where(User::class.java).max("id")!!.toInt() + 1
            }
        }
        catch (ex: Exception) {
            println(ex)
        }
        return id
    }

    fun initData(defaulUserList: ArrayList<User>): Boolean {
        try {
               for(user in defaulUserList) {
                   createUser(user)
               }
        } catch (ex: Exception) {
            print(ex)
            return false
        }
        return true
    }

    fun isEmpty() : Boolean {
       return realm.isEmpty
    }
}