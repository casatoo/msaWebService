$(document).ready(function() {
    $('form[name="loginForm"]').on('submit', function(event) {
        event.preventDefault(); // 기본 폼 제출 동작 방지

        const formData = $(this).serialize();

        // 서버에 로그인 요청을 보내며, 서버가 쿠키에 JWT를 저장하도록 함
        $.post('/loginProc', formData, function(response) {
            // 서버가 쿠키를 설정하므로 클라이언트는 별도로 처리할 필요 없음
            window.location.href = '/main';
        }).fail(function(xhr, status, error) {
            console.error('로그인 실패:', error);
        });
    });

    // 기본 AJAX 요청 함수 정의
    function ajaxRequest(method, url, data, successCallback, errorCallback) {
        $.ajax({
            method: method,
            url: url,
            data: data,
            success: successCallback,
            error: errorCallback
        });
    }

    // 간편함을 위해 get, post 메서드를 추가로 정의
    $.get = function(url, data, successCallback, errorCallback) {
        return ajaxRequest('GET', url, data, successCallback, errorCallback);
    };

    $.post = function(url, data, successCallback, errorCallback) {
        return ajaxRequest('POST', url, data, successCallback, errorCallback);
    };

    function logout() {
        fetch('/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            credentials: 'include' // 쿠키를 포함하여 요청
        })
            .then(response => window.location.href = '/login?logout')
            .catch(error => console.error('Error during logout:', error));
    }
});