

//function for submit form language in header (reload page after select new language).
function submitLanguageForm(idForm){
    let domForm = document.getElementById(idForm);
    if(!domForm){
        console.log('form not found with id ['+idForm+'].');
        return;
    }
    domForm.submit();
}


//function for click an element found by id.
function clickId(id){
    let domElements = document.querySelectorAll('[id*="'+id+'"]');
    if(domElements.length < 1){
        console.log('element HTML not found id ['+id+'].');
        return null;
    }
    if(domElements.length > 1){
        console.log('many elements HTML found id ['+id+'].');
        return null;
    }
    domElements[0].click();
}

//function for click element next.
function clickNext(target, blockProcess=false){
    if(!blockProcess)
        target.nextSibling.click();
}

//function for click element next, but ask confirmation to user before execute.
function clickNextConfirm(target, messageClass, blockProcess=false){
    let getDomMessage = document.getElementsByClassName(messageClass);
    if(getDomMessage.length !== 1)
        return;
    if(confirm(getDomMessage[0].innerHTML))
        clickNext(target, blockProcess);
}


//function for research in table before manny character.
function researchTable(target){
    let valueInput = target.value;
    if(valueInput.length < 3 && valueInput.length > 0)
        return;
    clickNext(target);
}


//function to apply class to word research table css.
//how to use :
// - need metadata event before table xhtml (calling TableFilter method).
// - need a columnTableListResearchWord class in column need to be affected.
// ! only one table list by page xhtml for no cross sibling.
function applyClassWordResearch(wordResearch){
    if(wordResearch.length < 3)
        return;
    let allTdTxt = document.getElementsByClassName('columnTableListResearchWord'); //get all column with this class.;
    let regexResearch = new RegExp(wordResearch, 'gi'); //set regex word.
    for(let i=0; i<allTdTxt.length; i++){ //loop on all td find.
        if(allTdTxt[i].tagName === 'TH')
            continue;
        allTdTxt[i].innerHTML = allTdTxt[i].innerHTML.replace( //replace all word in all td...
            regexResearch, //... find by regex...
            '<span class="wordResearch">'+wordResearch+'</span>'); //... replaced by same word with span class. (for css).
    }
}

//alert with i18n get in dom.
function alertI18NMessage(messageClass, reloadPage=false){
    let getDomMessage = document.getElementsByClassName(messageClass);
    if(getDomMessage.length !== 1)
        return;
    alert(getDomMessage[0].innerHTML);
    if(reloadPage){
        let buttonReloadResearchForm = document.getElementsByClassName('reloadResearchForm');
        if(buttonReloadResearchForm.length !== 1)
            return;
        buttonReloadResearchForm[0].click();
    }
}

//alert add basket i18n in dom.
function alertI18NAddBasket(messageClass, namePricePlatformToAddBasket){
    let getDomMessage = document.getElementsByClassName(messageClass);
    if(getDomMessage.length !== 1)
        return;
    setTimeout(function(){
        alert(
            getDomMessage[0].innerHTML+'\n'+
            'version : '+namePricePlatformToAddBasket
        );
    }, 60);
}

function clickNextIfNotDisabled(target){
    console.log(target);
    if(!target.classList.contains('disabledButtonIcon'))
        target.nextSibling.click();
}