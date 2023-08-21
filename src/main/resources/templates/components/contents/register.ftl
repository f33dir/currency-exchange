<div class="d-flex  justify-content-center">
    <div class="card col-md-3 shadow-lg">
        <div class="card-body ">
            <h5 class="card-title">
                Регистрация
            </h5>
            <div class="card-text">
                <label for="input_login"  >Имя пользователя</label>
                <input id="input_login" type="text" class="form-control my-3 "/>
                <label for="input_password"  >Пароль</label>
                <input id="input_password" type="password" class="form-control my-3 " required="true"/>
                <label for="input_repeat_password"  >Повторите пароль</label>
                <input id="input_repeat_password" type="password" class="form-control my-3 " required="true"/>
                <p id="ErrorText" class="text-danger"></p>
                <p id="InfoText" class="text-success"></p>
                <input class="btn btn-primary" type="button" onclick="registerUser()" value="Регистрация">

            </div>
        </div>
    </div>
</div>
<script>
    function registerUser(){
        document.getElementById("ErrorText").value = ""
        if(document.getElementById("input_password").value === document.getElementById("input_password").value){
            $.ajax({
                type: "POST",
                dataType:'text',
                contentType : "application/json; charset=utf-8",
                method : "post",
                url: "/API/register",
                data: JSON.stringify({
                    "login": document.getElementById("input_login").value,
                    "password": document.getElementById("input_password").value,
                    "id": 0,
                    "isAdmin":false
                })

                ,
                statusCode: {
                    409: (data)=>{
                        document.getElementById("ErrorText").innerText = "Не удалось зарегистрировать пользователя: "+data.responseText;
                    },
                    201: (data)=>{
                        document.getElementById("InfoText").innerText = "Успешно"
                        setTimeout(()=>{
                            window.location.replace("login")
                        },1500)
                    }
                }
            })
        }
    }
</script>