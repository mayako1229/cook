
	document.addEventListener('DOMContentLoaded', function() {
		const form = document.querySelector('.menu-form');
		const addMenuBtn = document.getElementById('add-menu-btn');
		// 複製元として使用する最初のメニューセット（DOM上に表示されている要素）
		const template = document.getElementById('menu-set-template');
		// 新しいメニューセットを挿入する場所（ボタンコンテナの直前）
		const insertionPoint = document.querySelector('.btn-container');

		// 日付を YYYY-MM-DD 形式でフォーマットするヘルパー関数
		function formatDate(date) {
			const y = date.getFullYear();
			// getMonth() は 0 から始まるため + 1
			const m = String(date.getMonth() + 1).padStart(2, '0');
			const d = String(date.getDate()).padStart(2, '0');
			return `${y}-${m}-${d}`;
		}

		// 1. 初期表示の日付を設定（システム日付）
		const today = new Date();
		const initialDateInput = template.querySelector('input[name="cook_date"]');
		console.log( initialDateInput );
		
		console.log( today );
		initialDateInput.value = formatDate(today);
		
		// 最初のメニューセットのIDを削除し、表示用のメニューセットとして確定
		template.removeAttribute('id');
		
		// 2. 複製と翌日日付の設定を行う関数
		function cloneMenuSet() {
			// 現在フォーム内にある全ての cook_date 入力フィールド（削除されたものを除く）を取得
			const allDateInputs = form.querySelectorAll('input[name="cook_date"]');
			
			// 最後の cook_date の値を取得（現在のフォームの最後のメニューの日付）
			let lastDateValue = allDateInputs.length > 0 ? allDateInputs[allDateInputs.length - 1].value : formatDate(new Date());
			
			// 最後のメニューの日付に基づいて翌日を計算
			// YYYY-MM-DD 形式から Date オブジェクトを作成
			const lastDate = new Date(lastDateValue);
			// Date.getDate() + 1 で翌日を計算
			lastDate.setDate(lastDate.getDate() + 1);
			const nextDayFormatted = formatDate(lastDate);

			// テンプレート（最初のメニューセット）を複製
			const newMenuSet = template.cloneNode(true);
			
			// 複製されたメニューセットの cook_date フィールドに翌日の日付を設定
			const newDateInput = newMenuSet.querySelector('input[name="cook_date"]');
			newDateInput.value = nextDayFormatted;
			
			// 複製された要素から値とエラー状態をクリア
			newMenuSet.querySelectorAll('input, textarea').forEach(input => {
				// 日付は設定済みなので、それ以外をクリア
				if (input.name !== 'cook_date') {
					input.value = '';
				}
			});

			// 3. 削除ボタンを追加
			const deleteBtn = document.createElement('button');
			deleteBtn.type = 'button';

			deleteBtn.className = 'btn-delete'; 
			deleteBtn.innerHTML = '<i class="fa-solid fa-minus"></i>';
			deleteBtn.addEventListener('click', function() {
				// ボタンの親要素であるメニューセット全体を削除
				newMenuSet.remove();
			});
			
			// 削除ボタン用のpタグを作成し、メニューセットの最後に追加
			const deleteP = document.createElement('p');
			deleteP.className = 'delete-btn-container'; // スタイル調整用のクラス
			deleteP.appendChild(deleteBtn);
			newMenuSet.appendChild(deleteP);

			return newMenuSet;
		}

		// 4. 「ふやす」ボタンのイベントリスナー
		addMenuBtn.addEventListener('click', function() {
			const newSet = cloneMenuSet();
			// 複製したメニューセットをボタンコンテナの直前に挿入
			form.insertBefore(newSet, insertionPoint);
		});

	});
