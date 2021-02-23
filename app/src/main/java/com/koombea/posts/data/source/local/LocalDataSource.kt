package com.koombea.posts.data.source.local

import com.couchbase.lite.*
import com.koombea.posts.domain.model.Post
import com.koombea.posts.domain.model.User
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class LocalDataSource(private val database: Database): iLocalDataSource {

    override suspend fun isEmpty(): Boolean {
        database.name
        val query: Query = QueryBuilder
            .select(
                SelectResult.all()
            )
            .from(DataSource.database(database))
            .orderBy(Ordering.expression(Meta.id))
        try {
            val rs = query.execute()
            for (result in rs)
                return result.toList().size <= 0
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
            return true
        }
        return true
    }

    override suspend fun savePost(listUser: List<User>?): Boolean {
        try {
            database.delete()
            database.inBatch {
                for (i in listUser!!) {

                    var listUrlString : MutableArray = MutableArray()
                    for (j in listUser[i].post.pics){
                        listUrlString.addString(listUser[i].post.pics[j])
                    }

                    val user = MutableDocument(listUser[i].uid)
                    user.setValue("uid", listUser[i].uid)
                    user.setValue("name", listUser[i].name)
                    user.setValue("email", listUser[i].email)
                    user.setValue("profile_pic", listUser[i].profile_pic)
                    user.setValue("id", listUser[i].post.id)
                    user.setValue("date", listUser[i].post.date)
                    user.setValue("profile_pic", listUser[i].profile_pic)
                    user.setArray("pics", listUrlString)
                    try {
                       database.save(user)
                    } catch (e: CouchbaseLiteException) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
            return false
        }finally {
            return true
        }
    }

    override suspend fun getAllPost(): List<User> {
        var listUser : ArrayList<User> = ArrayList()
        val query: Query = QueryBuilder
            .select(
                SelectResult.all()
            )
            .from(DataSource.database(database))
            .orderBy(Ordering.expression(Meta.id))
        try {
            val rs = query.execute()
            var obj: HashMap<String, Any>
            for (result in rs) {
                obj= result.toList()[0] as HashMap<String, Any>
                var id = obj["id"] as Long
                var post: Post = Post(
                    id.toInt(),
                    obj["date"] as String,
                    obj["pics"] as List<String>)
                var user: User = User(
                    obj["uid"] as String,
                    obj["name"] as String,
                    obj["email"] as String,
                    obj["profile_pic"]as String,
                    post)
                listUser.add(user)
            }
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
        return listUser
    }

    private operator fun <E> List<E>.get(i: E): E {
        return i
    }

}



