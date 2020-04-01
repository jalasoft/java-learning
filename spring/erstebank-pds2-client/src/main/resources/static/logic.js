
let messageElm = document.querySelector("#message");

document.querySelector("#accountsBtn").addEventListener("click", (e) => {
    fetch("/accounts")
        .then(r => {
            if (r.status != 200) {
                throw new Error("Selhalo nacteni: " + r.status + ": '" + r.statusText + "'.");
            }
            return r.text();
        })
        .then(text => {
            let textNode = document.createTextNode(text);
            messageElm.appendChild(textNode);
        })
        .catch(e => {
            let node = document.createTextNode(e.message);
            message.appendChild(node);
        });
});