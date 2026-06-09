package com.example.dynamicloginpage.data.repository

import com.example.dynamicloginpage.data.local.dao.UserDao
import com.example.dynamicloginpage.data.local.entity.UserEntity

class UserRepository(private val userDao: UserDao) {

    suspend fun login(username: String, password: String): UserEntity? {
        return userDao.login(username, password)
    }

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun isUsernameExists(username: String): Boolean {
        return userDao.isUsernameExists(username) > 0
    }

    suspend fun getUserCount(): Int {
        return userDao.getUserCount()
    }
}
