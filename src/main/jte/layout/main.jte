@param gg.jte.Content content
@param String appName
@param boolean isUserLogged
<!DOCTYPE html>
<html lang="cat">
<head>
    <meta charset="UTF-8" name="htmx-config" content='{"responseHandling": [{"code":".*", "swap": true}]}'>
    <link rel="icon" href="data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><text y=%22.9em%22 font-size=%2290%22>🎓</text></svg>">
    <title>SOS - ETSEIB</title>
    <script src="https://unpkg.com/htmx.org@2.0.3"
            integrity="sha384-0895/pl2MU10Hqc6jd4RvrthNlDiE9U1tWmX7WRESftEDRosgxNsQG/Ze9YMRzHq"
            crossorigin="anonymous"></script>
    <script src="https://unpkg.com/htmx-ext-response-targets@2.0.0/response-targets.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
    <link href="/static/styles.css" rel="stylesheet">
</head>
<body>
<header>
    <nav class="w-100">
        <div class="w-100 flex flex-row align-center justify-between">
            <a href="/" class="logo">
                SOS - ETSEIB
            </a>
            @if(isUserLogged)
                <form class="search-div w-40" >
                    <i class="absolute fa-solid fa-magnifying-glass" style="color: #454746;" ></i>
                    <input 
                        class="search-input"
                        name="search"
                        id="search-input"
                        placeholder="Cerca un arxiu o carpeta"
                    />
                </form>
                <button hx-post="/api/v1/auth/log-out" hx-target="body" hx-push-url="true" class="btn btn-primary">
                    <i class="fa-solid fa-arrow-right-from-bracket"></i>
                    <p>Tanca Sessió</p>
                </button>
            @else
                <div class="flex flex-row gap-1">
                    <button class="btn btn-secondary">
                        Registra't
                    </button>
                    <button class="btn btn-primary">
                        Inicia Sessió
                    </button>
                </div>
            @endif

        </div>
    </nav>
</header>
<div class="content">
    ${content}
</div>
<footer>
    <p>
        <strong>SOS - ETSEIB</strong> cap dret restringit per a qualsevol dubte o consulta, envieu un correu a
        <a href="https://jgthms.com">info@sos-etseib.com</a>.
        El contingut de la web esta llicenciat
        <a href="https://creativecommons.org/licenses/by-nc-sa/4.0//">CC BY NC SA 4.0</a>.
    </p>
</footer>
<script src="https://kit.fontawesome.com/07d92569e3.js" crossorigin="anonymous"></script>
<script src="/static/scripts.js"></script>

</body>

</html>
</html>