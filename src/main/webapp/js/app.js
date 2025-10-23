(() => {
	// 曜日の配列を月曜始まりに変更（以前の修正を保持）
	const weekdaysJP = ['月', '火', '水', '木', '金', '土', '日'];
	const weekdaysEl = document.getElementById('weekdays');
	const daysEl = document.getElementById('days');
	const monthTitle = document.getElementById('monthTitle');
	const prevBtn = document.getElementById('prevBtn');
	const nextBtn = document.getElementById('nextBtn');
	const modalBg = document.getElementById('modalBg');
	const modalTitle = document.getElementById('modalTitle');
	const modalDate = document.getElementById('modalDate');
	const modalDetail = document.getElementById('modalDetail');
	const closeModal = document.getElementById('closeModal');
	const modifyBtn = document.getElementById('modifyBtn');
	const deleteBtn = document.getElementById('deleteBtn');
	const editBtn = document.getElementById('editBtn');

	weekdaysJP.forEach((w, i) => {
		const el = document.createElement('div');
		el.className = 'weekday ' + (i === 6 ? 'sun' : (i === 5 ? 'sat' : ''));
		el.textContent = w;
		weekdaysEl.appendChild(el);
	});


	let events = {};

	// カレンダー描画
	function loadCalendar(year, month) {
		fetch(`getCookData?year=${year}&month=${month}`)
			.then(res => res.json())
			.then(data => {
				events = data;      // ←取得したデータをセット
				console.log(events);
				render(ref);        // ←再描画
			})
			.catch(err => console.error(err));
	}

	let ref = new Date();

	function render(date) {
		daysEl.innerHTML = '';
		const y = date.getFullYear();
		const m = date.getMonth();
		monthTitle.textContent = y + '年 ' + (m + 1) + '月';
		const first = new Date(y, m, 1);

		// 月曜始まりのインデックスに調整
		const startWeek = (first.getDay() + 6) % 7;

		const last = new Date(y, m + 1, 0);
		const daysInMonth = last.getDate();
		const prevLast = new Date(y, m, 0).getDate();
		const totalCells = 42;
		const today = new Date();

		for (let i = 0; i < totalCells; i++) {
			const cell = document.createElement('a');
			cell.href = '#';
			cell.className = 'day';

			const dayNum = i - startWeek + 1;
			let displayNum, isOut = false, cellDate;
			if (dayNum <= 0) {
				displayNum = prevLast + dayNum;
				isOut = true;
				cellDate = new Date(y, m - 1, displayNum);
			} else if (dayNum > daysInMonth) {
				displayNum = dayNum - daysInMonth;
				isOut = true;
				cellDate = new Date(y, m + 1, displayNum);
			} else {
				displayNum = dayNum;
				cellDate = new Date(y, m, displayNum);
			}

			const w = cellDate.getDay();

			if (w === 0) cell.classList.add('sun');
			if (w === 6) cell.classList.add('sat');

			if (isOut) cell.classList.add('out');
			if (cellDate.toDateString() === today.toDateString()) cell.classList.add('today');

			const num = document.createElement('div');
			num.className = 'num';
			num.textContent = displayNum;
			cell.appendChild(num);

			const note = document.createElement('div');
			note.className = 'note';
			const key = `${cellDate.getFullYear()}-${String(cellDate.getMonth() + 1).padStart(2, '0')}-${String(cellDate.getDate()).padStart(2, '0')}`;

			if (events[key]) {
				note.textContent = events[key].title;
				// 💡 修正点: クリック時にイベントオブジェクト全体を渡す (IDが含まれている想定)
				cell.addEventListener('click', e => {
					e.preventDefault();
					showModal(key, events[key]);
				});
			}
			cell.appendChild(note);

			daysEl.appendChild(cell);
		}
	}

	// ボタンにイベントリスナーを設定する
	function showModal(dateKey, event) {
		// イベントIDを取得 (eventオブジェクトにidプロパティがあることを仮定)
		const eventId = event.id;

		//modalTitle.textContent = event.title;
		modalTitle.value = event.title;
		modalDate.textContent = dateKey.replace(/-/g, '/');

		//const detailText = event.detail.replace(/\r?\n/g, '<br>');

		modalDetail.innerHTML = event.detail;
		modalBg.style.display = 'flex';


		// 削除機能:confirmで確認後、fetchを実行
		deleteBtn.onclick = () => {
			if (confirm(`${event.title} のメモを削除しますか？`)) {
				// モーダルを閉じる
				disabledForm();
				modalBg.style.display = 'none';

				// サーバーへ削除リクエストを送信
				fetch(`delete?id=${eventId}&year=${ref.getFullYear()}&month=${ref.getMonth() + 1}`)
					// 削除後、サーバーが最新のeventsデータを返す
					.then(res => res.json())
					.then(data => {
						events = data;
						//console.log(events);
						render(ref);
					})
					.catch(err => console.error(err));
			}
		};

		// 変更機能
		editBtn.onclick = () => {
			modalTitle.removeAttribute("disabled");
			modalDetail.removeAttribute("disabled");
			modalTitle.style.border = "1px #ccc solid";
			modalDetail.style.border = "1px #ccc solid";
			modifyBtn.style.display = "block";
		};

		//変更ボタン	
		modifyBtn.addEventListener('click', async () => {
			// モーダルを閉じる
			disabledForm();
			modalBg.style.display = 'none';
			try {
				const res = await fetch('/cook/modify', {
					method: 'POST',
					headers: { 'Content-Type': 'application/json;charset=utf-8' },
					body: JSON.stringify({
						id: eventId,
						title: modalTitle.value,
						detail: modalDetail.value,
						year: ref.getFullYear(),
						month: ref.getMonth()+1
					})
				});

				if (!res.ok) throw new Error('サーバーエラー');
				const data = await res.json();
				if (res.ok) {
					events = data;
					//console.log(events);
					render(ref);
				} 
			} catch (err) {
				console.error('保存失敗', err);

			}

		});
	}

	function disabledForm(){
		modalBg.style.display = 'none'
		modalTitle.setAttribute("disabled", true);
		modalDetail.setAttribute("disabled", true);
		modalTitle.style.border = "none";
		modalDetail.style.border = "none";
		modifyBtn.style.display = "none";
	}

	closeModal.addEventListener('click', () => {disabledForm()});
	modalBg.addEventListener('click', e => { if (e.target === modalBg) modalBg.style.display = 'none' });

	prevBtn.addEventListener('click', () => { ref = new Date(ref.getFullYear(), ref.getMonth() - 1, 1); render(ref) });
	nextBtn.addEventListener('click', () => { ref = new Date(ref.getFullYear(), ref.getMonth() + 1, 1); render(ref) });
	// 初回のみデータを取得して描画
	loadCalendar(ref.getFullYear(), ref.getMonth() + 1);
})();