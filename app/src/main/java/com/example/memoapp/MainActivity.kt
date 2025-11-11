package com.example.memoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.memoapp.data.MemoRepository
import com.example.memoapp.utils.DateUtils

class MainActivity : AppCompatActivity() {

    private lateinit var repository: MemoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Repositoryの初期化
        repository = MemoRepository(this)

        // テストデータの追加（初回のみ）
        if (repository.getMemosCount() == 0) {
            testDataModel()
        }

        // データの表示テスト
        displayAllMemos()
    }

    /**
     * データモデルのテスト
     */
    private fun testDataModel() {
        Log.d("MainActivity", "=== データモデルのテスト ===")

        // メモを追加
        val memo1 = repository.addMemo(
            "買い物リスト",
            "牛乳\nパン\n卵\nバナナ"
        )
        Log.d("MainActivity", "追加: ${memo1.title}")

        val memo2 = repository.addMemo(
            "会議メモ",
            "明日の会議の議題：\n1. プロジェクト進捗\n2. 予算確認"
        )
        Log.d("MainActivity", "追加: ${memo2.title}")

        val memo3 = repository.addMemo(
            "勉強メモ",
            "Kotlinの基礎\n- データクラス\n- コレクション操作"
        )
        Log.d("MainActivity", "追加: ${memo3.title}")

        Log.d("MainActivity", "総メモ数: ${repository.getMemosCount()}")
    }

    /**
     * すべてのメモを表示
     */
    private fun displayAllMemos() {
        Log.d("MainActivity", "=== メモ一覧 ===")

        val memos = repository.getMemosSortedByDate()
        memos.forEach { memo ->
            Log.d("MainActivity", "---")
            Log.d("MainActivity", "ID: ${memo.id}")
            Log.d("MainActivity", "タイトル: ${memo.title}")
            Log.d("MainActivity", "プレビュー: ${memo.getPreview()}")
            Log.d("MainActivity", "更新: ${DateUtils.getRelativeTimeString(memo.updatedAt)}")
        }

        Log.d("MainActivity", "---")
        Log.d("MainActivity", "総件数: ${memos.size}")
    }
}