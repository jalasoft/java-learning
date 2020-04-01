
document.querySelector("#logout").addEventListener("click", onLogout);

function onLogout() {

    let tokenMeta = document.querySelector("meta[name='_csrf']");
    let token = tokenMeta.content;

    let headerMeta = document.querySelector("meta[name='_csrf_header']");
    let header = headerMeta.content;

    let allHeaders = {};
    allHeaders[header] = token;
   // allHeaders['origin'] = 'http://localhost:8090';

    console.log(document.origin);

/*
    fetch('/logout', {
        method: 'post',
        headers: allHeaders,
        redirect: 'manual'
    }).then(function(response) {
        if (response.status == 302) {
            let location = response.headers.get("Location");
            console.log(location);
        }

    }).catch(function(error) {
        console.log("neco se pokazilo:");
        console.log(error);
    });*/

    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/logout', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            let u = xhr.response;
            window.location.href="/welcome";
        }
    };

    xhr.setRequestHeader(header, token);

    xhr.send();
}