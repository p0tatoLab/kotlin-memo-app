# Kotlin Memo App

Androidの基礎を学習するためのシンプルなメモアプリケーション

## 📝 概要

Android開発の基本要素を習得するための学習プロジェクトです。
Activity、RecyclerView、SharedPreferencesなどの基本的な概念を実践的に学びます。

## ✨ 機能

### 基本機能
- ✅ メモの一覧表示（RecyclerView）
- ✅ メモの新規作成
- ✅ メモの編集
- ✅ メモの削除（確認ダイアログ付き）
- ✅ データの永続化（SharedPreferences + JSON）

### 検索・整理
- ✅ メモの検索（リアルタイム検索）
- ✅ 並び替え（更新日、タイトル）
- ✅ 相対時間表示（「2時間前」など）

### その他
- ✅ すべてのメモを一括削除
- ✅ Material Design UI
- ✅ リップル効果
- ✅ アニメーション

## 🛠 技術スタック

- **言語**: Kotlin
- **最小SDK**: API 24 (Android 7.0)
- **ターゲットSDK**: API 34
- **アーキテクチャ**: Repository パターン
- **UI**: XML レイアウト + ViewBinding
- **データ保存**: SharedPreferences + Gson
- **主要ライブラリ**:
    - AndroidX Core
    - Material Components
    - RecyclerView
    - Gson

## 📁 プロジェクト構成
```
app/src/main/java/com/example/memoapp/
├── MainActivity.kt              # メイン画面（メモ一覧）
├── model/
│   └── Memo.kt                  # メモデータクラス
├── data/
│   └── MemoRepository.kt        # データ管理・永続化
├── ui/
│   ├── DetailActivity.kt        # メモ詳細・編集画面
│   └── MemoAdapter.kt           # RecyclerView アダプター
└── utils/
    └── DateUtils.kt             # 日付フォーマットユーティリティ
```

## 🚀 実行方法

### 1. プロジェクトを開く
```bash
git clone https://github.com/YOUR_USERNAME/kotlin-memo-app.git
cd kotlin-memo-app
```

### 2. Android Studioで開く
1. Android Studioを起動
2. 「Open」から `kotlin-memo-app` フォルダを選択
3. Gradle Syncが完了するのを待つ

### 3. 実行
1. エミュレータまたは実機を接続
2. **Run → Run 'app'** でアプリを実行

## 📖 使い方

### メモ一覧画面
- 右下の**+ボタン**で新規メモ作成
- メモをタップで編集画面へ
- 右上の**検索アイコン**でメモを検索
- メニュー（⋮）から並び替えやすべて削除が可能

### メモ編集画面
- タイトルと内容を入力
- 右下の**保存ボタン**で保存
- 右上の**削除ボタン**で削除（編集時のみ）
- 左上の**戻るボタン**で一覧に戻る

## 🎓 学習した技術

このプロジェクトで以下のAndroid/Kotlin概念を習得：

### Activity & ライフサイクル
- ✅ Activity の作成と管理
- ✅ `onCreate`, `onResume` ライフサイクル
- ✅ Toolbar と ActionBar

### UI & レイアウト
- ✅ XML レイアウト（ConstraintLayout、LinearLayout、CoordinatorLayout）
- ✅ ViewBinding
- ✅ Material Design コンポーネント
- ✅ RecyclerView と Adapter パターン
- ✅ CardView
- ✅ FloatingActionButton (FAB)
- ✅ SearchView
- ✅ TextInputLayout

### データ管理
- ✅ データクラス（`data class`）
- ✅ Repository パターン
- ✅ SharedPreferences
- ✅ JSON シリアライゼーション（Gson）

### 画面遷移とデータ受け渡し
- ✅ Intent
- ✅ Bundle（`putExtra`, `getExtra`）
- ✅ 画面間のデータ受け渡し

### UI/UX
- ✅ AlertDialog（確認ダイアログ）
- ✅ Toast と Snackbar
- ✅ Menu と MenuItem
- ✅ リップル効果
- ✅ アニメーション

### Kotlin機能
- ✅ データクラス
- ✅ 拡張関数
- ✅ null安全性
- ✅ ラムダ式
- ✅ companion object
- ✅ when式

## 📸 スクリーンショット

（ここにスクリーンショットを追加予定）

## 🔄 開発履歴

### Week 1: 基礎実装
- [x] プロジェクトセットアップ
- [x] データモデル作成（Memo, MemoRepository）
- [x] メモ一覧画面実装（RecyclerView）

### Week 2: 詳細画面と画面遷移
- [x] メモ詳細・編集画面実装
- [x] Intent によるデータ受け渡し
- [x] CRUD操作の完成

### Week 3: 追加機能とUI改善
- [x] 検索機能
- [x] 並び替え機能
- [x] UI改善（色、アニメーション）
- [x] 最終調整

## 🚧 今後の拡張案

- [ ] カテゴリ・タグ機能
- [ ] 色分け機能
- [ ] 画像添付機能
- [ ] Room Database への移行
- [ ] ViewModel + LiveData の導入
- [ ] Jetpack Compose への移行
- [ ] データのエクスポート/インポート
- [ ] ダークモード対応

## 📝 次のフェーズ

このプロジェクトの次のステップ：
- **フェーズ3**: Jetpack Compose でのUI再実装
- **フェーズ4**: MVVM アーキテクチャ導入
- **フェーズ5**: Room Database 導入

## 📄 ライセンス

このプロジェクトは学習目的で作成されています。

## 👤 作成者

学習プロジェクトとして作成

## 🙏 謝辞

Androidの基礎からモダンな開発手法までを段階的に学習するために作成しました。