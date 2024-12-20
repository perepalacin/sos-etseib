@param String appTitle

@template.layout.main(isUserLogged = false, appName = appTitle,content = @`
    <div class="overflow-hidden relative flex flex-column align-center justify-center w-100 h-100" hx-ext="response-targets">
        <div id="auth-card">
            <ul class="tabs">
                <li class="active"><a href="/sign-in" class="btn btn-primary">Inicia sessió</a></li>
                <li><a class="btn" href="/sign-up">Registra't</a></li>
            </ul>
            <h1>Benvingut a SOS - ETSEIB</h1>
            <h3 id="sub-title" class="text-center font-normal">Inicia sessió per accedir a centernars de recursos acadèmics totalment gratuits</h3>
            <form class="flex flex-column gap-2 w-100 items-center" hx-post="/api/v1/auth/sign-in" hx-trigger="submit" hx-target="#auth-card" hx-swap="outerHTML" hx-target-error="#sub-title" method="post">
                <div class="search-div">
                    <i class="absolute fa-solid fa-envelope" style="color: #454746;" ></i>
                    <input class="search-input" type="email" id="email" name="email"
                           placeholder="nom.cognom1.cognom2@estudiantat.upc.edu" required autocomplete="username" />
                </div>
                <div class="search-div">
                    <i class="absolute fa-solid fa-lock" style="color: #454746;" ></i>
                    <input class="search-input" type="password" id="password" name="password"
                           placeholder="La teva contrassenya" required autocomplete="new-password" />
                </div>
                <button type="submit" class="btn btn-primary" >
                    Inicia Sessió
                </button>
            </form>
        </div>
    </div>
`)