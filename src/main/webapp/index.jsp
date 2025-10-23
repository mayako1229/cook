<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html lang="ja">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>料理メモカレンダー</title>
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
			<a class="add-btn" href="add"><i class="fa-solid fa-plus"></i></a>
		</div>
	</header>
	<main class="wrap">

		<div class="calendar" id="calendar" aria-label="月カレンダー">
			<div class="cal-header">
				<div class="month-title" id="monthTitle">--</div>
				<div class="controls">
					<button class="btn" id="prevBtn">
						<i class="fa-solid fa-less-than"></i>
					</button>
					<button class="btn" id="nextBtn">
						<i class="fa-solid fa-greater-than"></i>
					</button>
				</div>
			</div>

			<div class="weekdays" id="weekdays"></div>
			<div class="days" id="days" role="grid"></div>
		</div>
	</main>

	<div class="modal-bg" id="modalBg">
		<div class="modal" id="modal">
			<form action="modify" method="post" class="menu-form">
				<div class="modal-actions">
					<p id="modalDate"></p>
					<div class="action-btns">
						<button class="delete-btn" id="deleteBtn" title="削除" type="button">
							<i class="fa-solid fa-trash"></i>
						</button>
						<button class="edit-btn" id="editBtn" title="変更" type="button">
							<i class="fa-solid fa-pen"></i>
						</button>
					</div>
				</div>
				<input id="modalTitle" type="text" name="menu_title" disabled>
				<textarea name="detail" id="modalDetail" disabled></textarea>
				<button class="modify-btn" id="modifyBtn" type="button">変更</button>
				<button class="modal-close" id="closeModal" type="button">閉じる</button>
			</form>
		</div>
	</div>
	<footer class="footer"><small>&copy;cook memo</small></footer>

	<script src="js/app.js"></script>
</body>
</html>