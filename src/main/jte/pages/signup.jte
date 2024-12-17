@param String appTitle
@param boolean isUserLogged

@template.layout.main(isUserLogged = isUserLogged, appName = appTitle,content = @`
    <div id="auth-card" class="flex flex-column align-center justify-center gap-2">
        <div class="flex flex-row align-center justify-center">
            <ul>
                <li class="is-active"><a href="/sign-in">Inicia sessió</a></li>
                <li><a href="/sign-up">Registra't</a></li>
            </ul>
        </div>
        <div class="is-full-mobile is-half-tablet">
            <h1 class="title">Inicia sessió</h1>
            <h2 class="subtitle">Accedeix a centenars de recursos acadèmics totalment gratuits</h2>
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
                               placeholder="La teva contrasenya" required>
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

    </div>
`)