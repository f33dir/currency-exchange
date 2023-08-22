<div class="d-flex  justify-content-center">
    <div class="card col-md-3 shadow-lg">
        <div class="card-body ">
            <h5 class="card-title">
                Вход
            </h5>
            <div class="card-text">
                <form>
                    <label for="input_login"  >Имя пользователя</label>
                    <input id="input_login" type="text" class="form-control my-3 "/>
                    <label for="input_password"  >Пароль</label>
                    <input id="input_password"type="password" class="form-control my-3 " required="true"/>
                    <input class="btn btn-primary" type="button" onclick="login()" value="Вход">
                    <p id="ErrorText" class="text-danger"></p>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function login() {
        $.ajax({
            type: "POST",
            dataType:'text',
            contentType : "application/json; charset=utf-8",
            method : "post",
            url: "/api/login",
            data: JSON.stringify({
                "login": document.getElementById("input_login").value,
                "password": document.getElementById("input_password").value,
                "id": 0,
                "isAdmin":false
            })

            ,
            statusCode: {
                401: (data)=>{
                    document.getElementById("ErrorText").innerText = "Не удалось войти: "+data.responseText;
                },
                200: (data)=>{
                    window.location.replace("/")
                }
            }
        })
    }
</script>