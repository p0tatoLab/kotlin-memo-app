package com.example.memoapp.model

import java.util.Date

data class Memo(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) {
    /**
     * 更新用のコピーを作成
     */
    fun update(title: String? = null, content: String? = null): Memo {
        return copy(
            title = title ?: this.title,
            content = content ?: this.content,
            updatedAt = Date()
        )
    }

    /**
     * プレビュー用のコンテンツ（最初の50文字）
     */
    fun getPreview(): String {
        return if (content.length > 50) {
            content.take(50) + "..."
        } else {
            content
        }
    }

    /**
     * 空のメモかどうか
     */
    fun isEmpty(): Boolean {
        return title.isBlank() && content.isBlank()
    }
}