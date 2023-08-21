<div class="container-fluid g-0">
    <header class="shadow d-flex  justify-content-center bg-primary py-3 mb-4 w-100">
        <a href="/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-light text-decoration-none">
            <span class="fs-4 mx-3">Конвертер валют</span>
        </a>
        <#if logged>
            <div class="col-sm-4 text-end mx-3">
                    <button class="btn btn-outline-light  pr-2" onclick="logoutOnClick()" type="button" id="logoutBtn" aria-expanded="false">
                        Выход
                    </button>
            </div>
        <#else>
            <div class="col-sm-4 text-end mx-3">
                <button type="button" onclick="loginOnClick()" class="btn btn-outline-light me-2">Вход</button>
                <button type="button" onclick="registerOnClick()" class="btn btn-light">Регистрация</button>
            </div>
        </#if>
    </header>
</div>
<script>
    function registerOnClick() {
        window.location.replace("/register")
    }
    function loginOnClick(){
        window.location.replace("/login")
    }
    function logoutOnClick(){
        $.post("/API/logout",(data)=>{
            console.log(data);
            window.location.replace("/")
        })
    }
</script>