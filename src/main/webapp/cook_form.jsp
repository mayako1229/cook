<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html lang="ja">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>料理メニュー追加</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
	integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="css/style.css?date=20251022">
<link rel="icon" href="images/favicon.ico">
</head>
<body>
	<header class="header">
		<h1>
			<img src="images/cook-memo-logo.png" alt="cook memo">
		</h1>
		<div>
			<a class="add-btn" href="./"><i class="fa-solid fa-calendar"></i></a>
		</div>
	</header>

	<main class="wrap">
	<div class="msg">${msg}</div>
		<form action="add" method="post" class="menu-form">
			<div id="menu-set-template">
				<p>
					<label>日付 <input type="date" name="cook_date" required></label>
				</p>
				<p>
					<label>メニュー名<input type="text" name="menu_title" required></label> 
				</p>
				<p>
					<label>詳細
					<textarea name="detail"></textarea></label>
				</p>
			</div>

			<div class="btn-container">
				<button type="button" class="add-btn" id="add-menu-btn"><i class="fa-solid fa-plus"></i></button>
			</div>
			<div class="btn-container">
				<a href="./" class="btn-back">戻る</a>
				<button type="submit" class="btn-save">保存</button>
			</div>
		</form>
	</main>

	<footer class="footer">
		<small>&copy;cook memo</small>
	</footer>

	<script src="js/add.js"></script>
</body>
</html>