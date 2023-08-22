<div class="d-flex container flex-column align-items-center mt-100">
    <div class="container justify-content-center col-12">
        <h1 class="text-center">Курсы Валют</h1>
    </div>
    <div class="d-flex container-fluid flex-row">
        <div class="card col-sm-12 col-md-6">
            <div class="card-body">
                <h2>
                    Перевести из
                </h2>

                <select id="valute_from" onchange="valuteFromChange(this.value)" class="form-select form-select-lg col-md-3 m-1 mb-3 col-xs-12 col-sm-6 align-self-bottom shadow border-dark" aria-label="Выберите валюту">
                    <option value="-1" disabled selected hidden>Выбрать валюту</option>
                </select>
                <div style="display: none">
                    <label for="valute_from_input">Номинал</label>
                    <input type="number" value="0" id="valute_from_input" oninput="valuteFromChange()" class="form-control m-1 shadow border-dark" >
                </div>
            </div>
        </div>
        <div class="card col-sm-12 col-md-6 ">
            <div class="card-body">
                <h2>
                    Перевести в
                </h2>

                <select id="valute_to" onchange="valuteToChange(this.value)" class="form-select form-select-lg col-md-3 col-xs-12 col-sm-6 m-1 mb-3 align-self-b ottom shadow border-dark" aria-label="Выберите валюту">
                    <option value="-1" disabled selected hidden>Выбрать валюту</option>
                </select>
                <div style="display: none">

                    <label for="valute_to_input">Номинал</label>
                    <input type="number" value="0" id="valute_to_input" oninput="valuteToChange()" class="form-control m-1 shadow border-dark" >
                </div>
            </div>
        </div>
        </div>
</div>
<script>

    let quotations;
    let fromValute;
    let toValute;

    function valuteFromChange(item){
        let valute_from = document.getElementById("valute_from")
        let valute_to = document.getElementById("valute_to")
        let valute_from_input = document.getElementById("valute_from_input")
        let valute_to_input = document.getElementById("valute_to_input")
        if(item){
            let comp = (element,index,array) => {return element.CharCode === item}
            fromValute = quotations.find(comp)
        }
        if(valute_from.value!=="-1" && valute_to.value!=="-1"){
            $('#valute_from_input').parent().show()
            $('#valute_to_input').parent().show()
            valute_to_input.value = ((valute_from_input.value*fromValute.Value)/toValute.Value).toFixed(2)
        }

    }
    function valuteToChange(item){
        let valute_from = document.getElementById("valute_from")
        let valute_to = document.getElementById("valute_to")
        let valute_from_input = document.getElementById("valute_from_input")
        let valute_to_input = document.getElementById("valute_to_input")
        if(item){
            let comp = (element,index,array) => {return element.CharCode === item}
            toValute = quotations[quotations.findIndex(comp)]
        }
        if(valute_from.value!=="-1" && valute_to.value!=="-1"){
            $('#valute_from_input').parent().show()
            $('#valute_to_input').parent().show()
            valute_to_input.value = ((valute_from_input.value*fromValute.Value)/toValute.Value).toFixed(2)
        }
    }
    const update = function (){
        $.ajax({
            type: "GET",
            url: "/api/quotation",
            complete : function (jqXHR, textStatus){
                console.log(jqXHR)
                quotations = JSON.parse(jqXHR.responseText)
                quotations.forEach((quotation)=>{
                    let option  = document.createElement('option')
                    option.value = quotation.CharCode
                    option.innerText = quotation.Name
                    valute_to.appendChild(option)
                    option  = document.createElement('option')
                    option.value = quotation.CharCode
                    option.innerText = quotation.Name
                    valute_from.appendChild(option)
                })
            }
        })
    }
    window.onload = update
    setInterval(update,6000000)
</script>