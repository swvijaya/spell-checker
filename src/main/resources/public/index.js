
let textBox = document.getElementById("inp-data");
let resultList = document.getElementById("opt-data");
let timeout; 

textBox.onkeydown = (e) => {
    const key = e.key;
    const keyCode = e.keyCode || e.charCode;
    if (key == " " ||  keyCode == 8 || keyCode == 46) {
        //Debounce 
        if(timeout) {
            clearTimeout(timeout);
        }
        timeout = setTimeout(()=> {
            checkSpelling(textBox.value);
            timeout = null
        }, 500);
    }
};

let checkSpelling = (text) => {
    let words = text.split(" ").filter((word) => { return word });
//    jQuery.post('/spellcheck', {words: words}, processSpellCheckResult, 'json')
     jQuery.ajax({
        type:'post',
        url: '/spellcheck',
        contentType: 'application/json',
        data: JSON.stringify(words),
        success: function (data) {
            processSpellCheckResult(data)
        },
        error: function (textStatus, errorThrown) {
            console.log(`ERROR ${textStatus}  ${errorThrown}`);
        }
    });

}

let processSpellCheckResult = (result) => {
    console.log("Response form Server " + JSON.stringify(result));
    resultList.innerHTML = '';
    result.forEach( word => {
        var node = document.createElement("LI"); 
        var textnode = document.createTextNode(word); 
        node.appendChild(textnode);
        resultList.appendChild(node);
    });
}