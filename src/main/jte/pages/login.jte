@param String appTitle
@param boolean isUserLogged

@template.layout.main(isUserLogged = isUserLogged, appName = appTitle,content = @`
    <div class="overflow-hidden relative flex flex-row w-100 h-100">
        <div class="floating-image" style="min-width: 40%; background-image: url('https://d129a85b0t6mus.cloudfront.net/public/DigitalAsset/a8465952-7959-436d-a4fa-306f473c2ba4/e20086448dd8152b44cd6048319b0391/full/large/0/default.jpg');"></div>
        <div id="auth-card" class="auth-card w-100 flex flex-column align-center justify-center gap-2" style="height: 100%">
            <ul class="tabs">
                <li class="active"><a href="/sign-in">Inicia sessió</a></li>
                <li><a href="/sign-up">Registra't</a></li>
            </ul>
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