$(document).ready(function() {
    // 기본 AJAX 요청 함수 정의
    function ajaxRequest(method, url, data, successCallback, errorCallback) {
        $.ajax({
            method: method,
            url: url,
            data: data,
            success: successCallback,
            error: errorCallback,
            // credentials: 'include' 를 설정하여 쿠키를 요청에 포함시킴
            xhrFields: {
                withCredentials: true
            }
        });
    }

    // 간편함을 위해 get, post 메서드를 추가로 정의
    $.get = function(url, data, successCallback, errorCallback) {
        return ajaxRequest('GET', url, data, successCallback, errorCallback);
    };

    $.post = function(url, data, successCallback, errorCallback) {
        return ajaxRequest('POST', url, data, successCallback, errorCallback);
    };
});