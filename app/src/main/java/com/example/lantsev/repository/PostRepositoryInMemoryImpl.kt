package com.example.lantsev.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lantsev.dto.Post

class PostRepositoryInMemoryImpl : PostRepository{
    private var nextId=1
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "ГБПОУ ВО \"БТПИТ\"",
            content = "ЛИДЕР ДВИЖЕНИЯ ПЕРВЫХ\n" +
                    "29 марта студент 2 курса Бараненко Максим Борисоглебского техникума промышленных и информационных технологий совместно с советником директора по воспитанию Алехиной Светланой приняли участие в территориальном этапе регионального конкурса «Лидер Движения Первых Воронежской области», который проходил на базе МОУ Новохоперской СОШ N2.\n" +
                    "Ребята проходили испытания по следующим темам: «Эрудит», «Оратор», «Работа в команде».",
            published = "18 сентября в 10:12",
            likedByMe = false,
            likes = 489,
            share = 23,
            shareByMe =false
        ),
        Post(
            id = nextId++,
            author = "ГБПОУ ВО \"БТПИТ\"",
            content = "ЛИДЕР ДВИЖЕНИЯ ПЕРВЫХ\n" +
                    "29 марта студент 2 курса Бараненко Максим Борисоглебского техникума промышленных и информационных технологий совместно с советником директора по воспитанию Алехиной Светланой приняли участие в территориальном этапе регионального конкурса «Лидер Движения Первых Воронежской области», который проходил на базе МОУ Новохоперской СОШ N2.\n" +
                    "Ребята проходили испытания по следующим темам: «Эрудит», «Оратор», «Работа в команде».",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 999999,
            share = 999,
            shareByMe=false
        )
    ).reversed()
    private val data = MutableLiveData(posts)
    override fun getAll(): LiveData<List<Post>> = data
    override fun save(post: Post) {
        if(post.id==0){
            posts = listOf(post.copy(
                id = nextId++,
                author = "Me",
                likedByMe = false,
                published = "now",
                shareByMe = false
            )
            ) + posts
            data.value = posts
            return
        }
        posts = posts.map{
            if (it.id != post.id) it else it.copy (content = post.content, likes = post.likes, share = post.share)
        }
        data.value = posts
    }
    override fun likeById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else
                it.copy(likedByMe = !it.likedByMe, likes = if (!it.likedByMe) it.likes+1 else it.likes-1)
        }
        data.value = posts
    }
    override fun shareById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else
                it.copy(shareByMe = !it.shareByMe, share = it.share+1)
        }
        data.value = posts
    }

    override fun removeById(id: Int) {
        posts = posts.filter { it.id!=id }
        data.value = posts
    }


}


