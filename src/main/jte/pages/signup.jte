@param String appTitle
@param boolean isUserLogged

@template.layout.main(isUserLogged = isUserLogged, appName = appTitle,content = @`
    <div class="overflow-hidden relative flex flex-column align-center justify-center w-100 h-100">
        <div class="floating-image">
            <img class="w-100 h-100" src="https://i.ytimg.com/vi/qfmMgtdz6TA/maxresdefault.jpg" alt="Foto del etseib" />
        </div>
        <div id="auth-card">
            <ul class="tabs">
                <li><a href="/sign-in">Inicia sessió</a></li>
                <li class="active"><a href="/sign-up">Registra't</a></li>
            </ul>
            <h1 class="title">Registra't</h1>
            <h4 class="subtitle">Per accedir a centenars<br/>de recursos acadèmics totalment gratuits</h4>
            <form hx-post="/api/v1/auth/sign-in" hx-trigger="submit" hx-target="#auth-card" method="post">
                <div class="field">
                    <p class="control has-icons-left has-icons-right">
                        <input class="input" type="email" id="email" name="email"
                               placeholder="nom.cognom1.cognom2@estudiantat.upc.edu" required>
                        <span class="icon is-small is-left">
                            <i class="fas fa-envelope"></i>
                        </span>
                    </p>
                </div>
                <div class="field">
                    <p class="control has-icons-left">
                        <input class="input" type="password" id="password" name="password"
                               placeholder="La teva contrasenya" required />
                        <span class="icon is-small is-left">
                            <i class="fas fa-lock"></i>
                        </span>
                    </p>
                </div>
                <div class="field">
                    <p class="control">
                        <button type="submit" class="button is-success">
                            Inicia Sessió
                        </button>
                    </p>
                </div>
            </form>
        </div>
    </div>
`)