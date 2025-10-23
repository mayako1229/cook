
# 🍳 Cook Memo

**数日分の献立をまとめて登録・確認できる JSP/Servlet アプリケーション**

## 🥗 概要

このアプリケーションは、**数日間分の献立をまとめて登録・管理したい方向け**に作られた Web アプリです。  
登録した献立は **カレンダー形式で表示**され、メニューの重複を避けたり、買い物計画を立てやすくします。

### 主な機能
- 献立（メニュータイトル・詳細・日付）の登録
- 献立の一覧表示（カレンダー形式）
- 献立の編集・削除
- JavaScript による動的なUI表示

---

## ⚙️ 開発環境

| 項目 | 使用技術 |
|------|------------|
| 言語 | Java 21 |
| フレームワーク | JSP / Servlet |
| IDE | Eclipse |
| Web サーバ | Apache Tomcat |
| データベース | MySQL |
| フロントエンド | HTML / CSS / JavaScript |

---

## 📁 プロジェクト構成

```

cook/
├── src/
│   ├── main/java/
│   │   ├── dao/
│   │   │   ├── CookDAO.java         # DBアクセス（CRUD処理）
│   │   │   └── DBManager.java       # DB接続管理クラス
│   │   ├── logic/
│   │   │   └── CookLogic.java       # ビジネスロジック層
│   │   ├── model/
│   │   │   └── Cook.java            # 献立データモデル
│   │   └── servlet/
│   │       ├── AddCookServlet.java      # 献立追加処理
│   │       ├── DeleteCookServlet.java   # 献立削除処理
│   │       ├── ModifyCookServlet.java   # 献立変更処理
│   │       └── GetCookDataServlet.java  # 献立一覧取得
│   └── main/webapp/
│       ├── index.jsp               # トップページ（カレンダー表示）
│       ├── cook_form.jsp           # 献立登録・編集フォーム
│       ├── css/style.css           # スタイル定義
│       ├── js/app.js               # カレンダー処理
│       ├── js/add.js               # 登録用スクリプト
│       ├── images/                 # ロゴ・アイコン
│       └── WEB-INF/
│           ├── lib/（JARライブラリ群）
│           └── web.xml             # サーブレット設定
└── build/                          # コンパイル済みクラス

````

---

## 💾 データベース設定

使用するデータベースは **MySQL** です。  
以下の SQL を実行してデータベースとテーブルを作成してください。

```sql
CREATE DATABASE cook_memo;
USE cook_memo;

CREATE TABLE cook (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cook_date DATE NOT NULL,
    menu_title VARCHAR(255) NOT NULL,
    detail TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
````

DB接続情報は `DBManager.java` 内で設定します。

```java
private static final String URL = "jdbc:mysql://localhost:3306/cook_memo";
private static final String USER = "root";
private static final String PASSWORD = "（あなたのパスワード）";
```

---

## 🚀 起動方法

1. プロジェクトを Eclipse にインポート
   （File → Import → Existing Projects into Workspace）
2. Tomcat サーバにデプロイ
3. MySQL サーバを起動し、上記 SQL を実行
4. ブラウザで以下にアクセス：

```
http://localhost:8080/cook
```

---

## 🖥️ 画面構成

| 画面                       | 説明                   |
| ------------------------ | -------------------- |
| **トップページ**               | カレンダー形式で登録済みの献立を一覧表示 |
| **登録ページ（cook_form.jsp）** | 新しい献立を追加             |
| **編集ページ**                | 既存の献立を変更または削除        |

---

## 📦 使用ライブラリ

* **jakarta.servlet.jsp.jstl-3.0.1.jar**
* **jakarta.servlet.jsp.jstl-api-3.0.0.jar**
* **mysql-connector-java-8.0.23.jar**
* **json-20250517.jar**

---

## 🧠 今後の改善アイデア

* 材料リストの自動生成機能
* 献立のコピー登録機能
* 栄養バランス自動チェック
* モバイル対応UIの強化

---

## 👩‍💻 作者情報

**Author:** mayako1229
**Language:** Japanese / 日本語


---

## 📝 ライセンス

本プロジェクトは学習・個人利用目的で自由に利用できます。
商用利用の場合は作者への連絡を推奨します。


