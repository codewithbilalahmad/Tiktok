package com.muhammad.data.domain.model

import com.muhammad.core.formattedCount

data class Video(
    val id: String,
    val authorDetails: User,
    val thumbnail: String,
    val videoStats: VideoStats,
    val videoLink: String,
    val description: String,
    val currentViewInteraction: ViewerInteraction = ViewerInteraction(),
    val createdAt: String,
    val hasTag: List<HashTag> = listOf(),
){
    data class User(
        val userId : String,
        val username : String,
        val profileImage : String
    )
    data class VideoStats(
        var like: Long,
        var comment: Long,
        var share: Long,
        var favourite: Long,
        var views: Long = (like.plus(500)..like.plus(100000)).random()
    ) {
        var formattedLikeCount: String = ""
        var formattedCommentCount: String = ""
        var formattedShareCount: String = ""
        var formattedFavouriteCount: String = ""
        var formattedViewsCount: String = ""

        init {
            formattedLikeCount = like.formattedCount()
            formattedCommentCount = comment.formattedCount()
            formattedShareCount = share.formattedCount()
            formattedFavouriteCount = favourite.formattedCount()
            formattedViewsCount = views.formattedCount()
        }
    }

    data class HashTag(
        val id: Long,
        val title: String
    )

    data class ViewerInteraction(
        var isLikedByYou: Boolean = false,
        var isAddedToFavourite: Boolean = false
    )
}