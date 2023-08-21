package com.f33dir.API.User

import org.mindrot.jbcrypt.BCrypt
import java.sql.Connection
import java.sql.Statement

class UserRepo(val connection: Connection) {
    companion object {
     private const val INSERT_USER = "INSERT INTO \"Users\" (\"Login\", \"Password\") VALUES (?, ?);"
     private const val SELECT_USER = "SELECT * FROM \"Users\" WHERE \"Login\"=? "
    }
    suspend fun createUser(user: User): Int{
        val statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1,user.login)
        statement.setString(2, BCrypt.hashpw(user.password,BCrypt.gensalt()))
        statement.executeUpdate()
        val generatedKeys = statement.generatedKeys;
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1)
        } else {
            throw Exception("Failed to retrieve id after inserting into User")
        }
    }
    suspend fun getUserByLogin(user: User) : User{
        val statement = connection.prepareStatement(SELECT_USER, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1,user.login)
        val results = statement.executeQuery()
        return if(results.next()){
            User(results.getInt("Id"),results.getString("login"),results.getString("password"),results.getBoolean("isAdmin"))
        } else {
            User(null,"","",false);
        }
    }
}
