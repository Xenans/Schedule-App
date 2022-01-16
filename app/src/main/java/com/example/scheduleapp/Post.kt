package com.example.scheduleapp

class Post {
    var postTitle: String? = null
    var postDesc: String? = null
    var postRecurring: Boolean? = null

    constructor() : super() {}

    constructor(PostTitle: String, PostDesc: String, PostRecurring: Boolean) : super() {
        this.postTitle = PostTitle
        this.postDesc = PostDesc
        this.postRecurring = PostRecurring
    }


}