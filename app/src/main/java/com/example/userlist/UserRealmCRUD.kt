package com.example.userlist

import io.realm.Realm
import java.lang.Exception

class UserRealmCRUD {

companion object {

    fun createUser(user: User, realm: Realm) : Boolean {
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

    fun readUserById(id: Long, realm: Realm) : User? {
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

    fun readAllContacts(realm: Realm) : ArrayList<User> {
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


    fun updateUser(user: User, realm: Realm) : Boolean {
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

    fun deleteUser(id: Long, realm: Realm) : Boolean {

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

    fun getNewId(realm: Realm) : Int {
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

    fun initData(realm: Realm, defaulUserList: ArrayList<User>): Boolean {
        try {
               for(user in defaulUserList) {
                   createUser(user, realm)
               }
        } catch (ex: Exception) {
            print(ex)
            return false
        }
        return true
    }
}
}