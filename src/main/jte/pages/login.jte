@param String appTitle

@template.layout.main(fileId = -1, isUserLogged = false, appName = appTitle,content = @`
    <div class="overflow-hidden relative flex flex-column align-center justify-center w-100 h-100" hx-ext="response-targets">
        <div id="auth-card">
            <ul class="tabs">
                <li class="active"><a href="/sign-in" class="btn btn-primary">Inicia sessió</a></li>
                <li><a class="btn" href="/sign-up">Registra't</a></li>
            </ul>
            <h1>Benvingut a SOS - ETSEIB</h1>
            <h3 id="sub-title" class="text-center font-normal">Inicia sessió per accedir a centernars de recursos acadèmics totalment gratuits</h3>
            <form class="flex flex-column gap-2 w-100 items-center" hx-post="/api/v1/auth/sign-in" hx-trigger="submit" hx-target="body" hx-replace-url="true" hx-swap="outerHTML" hx-target-error="#sub-title" method="post">
                <div class="search-div">
                    <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="22px" fill="#757575"><path d="M180.31-212Q154-212 135-231q-19-19-19-45.31v-407.38Q116-710 135-729q19-19 45.31-19h599.38q27.31 0 45.81 19 18.5 19 18.5 45.31v407.38Q844-250 825.5-231q-18.5 19-45.81 19H180.31ZM480-449.69l312-183.62-6.15-62.69L480-517 174.15-696 168-633.31l312 183.62Z"/></svg>
                    <input class="search-input" type="email" id="email" name="email"
                           placeholder="nom.cognom1.cognom2@estudiantat.upc.edu" required autocomplete="username" />
                </div>
                <div class="search-div">
                    <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="#757575"><path d="M276.03-116q-26.55 0-45.29-18.89T212-180.31v-359.38q0-26.53 18.89-45.42T276.31-604H308v-96q0-71.6 50.27-121.8Q408.53-872 480.23-872q71.69 0 121.73 50.2Q652-771.6 652-700v96h31.69q26.53 0 45.42 18.89T748-539.69v359.38q0 26.53-18.9 45.42Q710.19-116 683.65-116H276.03Zm204.18-182q25.94 0 43.87-18.14Q542-334.27 542-360.21t-18.14-43.87Q505.73-422 479.79-422t-43.87 18.14Q418-385.73 418-359.79t18.14 43.87Q454.27-298 480.21-298ZM360-604h240v-96q0-50-35-85t-85-35q-50 0-85 35t-35 85v96Z"/></svg>                    <input class="search-input" type="password" id="password" name="password"
                           placeholder="La teva contrassenya" required autocomplete="new-password" />
                </div>
                <button type="submit" class="btn btn-primary" >
                    Inicia Sessió
                </button>
            </form>
        </div>
    </div>
`)