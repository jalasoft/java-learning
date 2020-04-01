let accountsUl = document.querySelector("#accounts");

let message = document.querySelector("#message");

document.querySelector("#accountsBtn").addEventListener("click", (e) => {
    fetch("/api/accounts")
        .then(r => {
            if (r.status != 200) {
                throw new Error("Selhalo nacteni: " + r.status + ": '" + r.statusText + "'.");
            }
            return r.json()
        })
        .then(j => {
            j.forEach(e => {
                let li = document.createElement("li");
                let text = document.createTextNode(e);
                li.appendChild(text);
                accountsUl.appendChild(li);
            });
        })
        .catch(e => {
            let node = document.createTextNode(e.message);
            message.appendChild(node);
        });
});

document.querySelector("#logoutBtn").addEventListener("click", e => {
    //fetch("/logout");
    window.location.href = "/logout";
});