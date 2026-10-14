/* 사내 표준 JS - Vanilla만 사용 (jQuery 등 외부 라이브러리 금지) */
'use strict';

function checkLogin(form) {
  var id = form.username.value.trim();
  var pw = form.password.value;
  if (!id) { alert('아이디를 입력하세요.'); form.username.focus(); return false; }
  if (!pw) { alert('비밀번호를 입력하세요.'); form.password.focus(); return false; }
  return true;
}

function checkUserForm(form) {
  var id = form.userId.value.trim();
  var nm = form.userNm.value.trim();
  if (!id) { alert('사번을 입력하세요.'); form.userId.focus(); return false; }
  if (!nm) { alert('성명을 입력하세요.'); form.userNm.focus(); return false; }
  return true;
}

// fetch JSON 래퍼 (추후 AJAX 화면용 표준)
async function apiGet(url) {
  const res = await fetch(url, { headers: { 'Accept': 'application/json' } });
  if (!res.ok) throw new Error('요청 실패: ' + res.status);
  return res.json();
}

async function apiPost(url, data) {
  const res = await fetch(url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
    body: JSON.stringify(data)
  });
  if (!res.ok) throw new Error('요청 실패: ' + res.status);
  return res.json();
}
