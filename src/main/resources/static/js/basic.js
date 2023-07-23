let host = "http://localhost:8080"
$(document).ready(function () {
    const auth = getToken();
    const auth2 = getRefreshToken();

    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });

    }

    if (auth2 !== undefined && auth2 !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('RefreshToken', auth2);
        });

    }

    //회원가입 /
    const signupButton = document.getElementById('signup-btn');

    if (signupButton) {
        signupButton.addEventListener('click', event => {

            fetch(`/user/signup`, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email: document.getElementById('email').value,
                    password: document.getElementById('password').value,
                    nickname: document.getElementById('nickname').value,
                })
            })
                .then(() => {

                    alert('회원가입 성공!');
                    location.replace('/user/login-page');


                });
        });
    }

    //인증코드 보내기
    const emailButton = document.getElementById('email-btn');

    if (emailButton) {
        emailButton.addEventListener('click', event => {

            fetch(`/user/mail`, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email: document.getElementById('email').value,
                })
            })
                .then((response) => {
                    if(response.ok){
                        $("#emailForm").hide();
                        $("#checkCodeForm").show();
                    } else {
                        alert('이미 등록된 Email 입니다.');
                        location.replace('/user/signup-page');
                    }

                });
        });
    }

    //인증코드 확인
    const checkCodeButton = document.getElementById('checkCode-btn');

    if (checkCodeButton) {
        checkCodeButton.addEventListener('click', event => {

            fetch(`/user/mailCode`, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email: document.getElementById('email').value,
                    code: document.getElementById('checkCode').value,
                })
            })
                .then((response) => {
                    if(response.ok){
                        $("#checkCodeForm").hide();
                        $("#submitForm").show();
                    } else {
                        alert('인증코드가 틀렸습니다.');
                        location.replace('/user/signup-page');
                    }

                });
        });
    }

    // umm 삭제 기능 /
    const deleteButton = document.getElementById('delete-btn');

    if (deleteButton) {
        deleteButton.addEventListener('click', event => {
            let params = new URLSearchParams(location.search);
            let id = params.get('umm_id');

            fetch(`/umm/${id}`, {
                method: 'DELETE'
            })
                .then(() => {
                    alert('삭제가 완료되었습니다.');
                    location.replace('/profile');
                });
        });
    }

    //비밀번호 확인
    const checkPasswordButton = document.getElementById('checkPassword-btn');

    if (checkPasswordButton) {
        checkPasswordButton.addEventListener('click', event => {

            fetch(`/password`, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    password: document.getElementById('checkPassword').value,
                })
            })
                .then((response) => {
                    if(response.ok){
                        $("#passwordForm").hide();
                        $("#newPasswordForm").show();
                    } else {
                        alert('비밀번호가 틀렸습니다.');
                        location.replace('/profile');
                    }

                });
        });
    }

    //비밀번호 변경
    const newPasswordButton = document.getElementById('newPassword-btn');

    if (newPasswordButton) {
        newPasswordButton.addEventListener('click', event => {

            fetch(`/password`, {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    new_password: document.getElementById('newPassword').value,
                })
            })
                .then(() => {
                    alert('비밀번호가 변경되었습니다.');
                    location.replace('/profile');
                });
        });
    }

    // 팔로우 기능/
    const followButton = document.getElementById('follow-btn');

    if (followButton) {
        followButton.addEventListener('click', event => {
            let id = document.getElementById('user-id').value;

            fetch(`/follow/${id}`, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                }
            })
                .then(() => {
                    alert('등록 완료되었습니다.');
                    location.replace(`/profile`);
                });
        });
    }


})

function getToken() {
    let auth = Cookies.get('Authorization');

    if(auth === undefined) {
        return '';
    }
    return auth;
}

function getRefreshToken() {
    let auth2 = Cookies.get('RefreshToken');

    if(auth2 === undefined) {
        return '';
    }
    return auth2;
}




