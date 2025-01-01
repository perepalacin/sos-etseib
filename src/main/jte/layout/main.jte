@param gg.jte.Content content
@param String appName
@param boolean isUserLogged
@param int fileId

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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/default.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
</head>
<body>
<header>
    <nav class="w-100">
        <div class="w-100 flex flex-row align-center justify-between">
            <a href="/" class="logo">
                <i class="fa-solid fa-graduation-cap"></i>
                SOS - ETSEIB
            </a>
            @if(isUserLogged)
                @if (fileId == -1)
                    <form class="search-div w-40" >
                        <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#757575"><path d="M781.69-136.92 530.46-388.16q-30 24.77-69 38.77-39 14-80.69 14-102.55 0-173.58-71.01-71.03-71.01-71.03-173.54 0-102.52 71.01-173.6 71.01-71.07 173.54-71.07 102.52 0 173.6 71.03 71.07 71.03 71.07 173.58 0 42.85-14.38 81.85-14.39 39-38.39 67.84l251.23 251.23-42.15 42.16ZM380.77-395.38q77.31 0 130.96-53.66 53.66-53.65 53.66-130.96t-53.66-130.96q-53.65-53.66-130.96-53.66t-130.96 53.66Q196.15-657.31 196.15-580t53.66 130.96q53.65 53.66 130.96 53.66Z"/></svg>
                        <input class="search-input" name="search" id="search-input" placeholder="Cerca un arxiu o carpeta"/>
                    </form>
                @endif
                <div class="flex flex-row gap-1 align-center">
                    @if(fileId > -1)
                        <div id="likes-dislikes-buttons" class="flex flex-row gap-1 align-center">
                            <div class="flex flex-row align-center" style="gap: 0.1rem">
                                <button class="btn-icon" hx-post="/api/v1/feedback?action=like&fileId=${fileId}" hx-swap="outerHTML" hx-target="#likes-dislikes-buttons">
                                    <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#000"><path d="M709.23-140H288.46v-480l265.39-263.84L587.69-850q6.23 6.23 10.35 16.5 4.11 10.27 4.11 19.35V-804l-42.46 184h268q28.54 0 50.42 21.89Q900-576.23 900-547.69v64.61q0 6.23-1.62 13.46-1.61 7.23-3.61 13.47L780.15-185.69q-8.61 19.23-28.84 32.46T709.23-140Zm-360.77-60h360.77q4.23 0 8.65-2.31 4.43-2.31 6.74-7.69L840-480v-67.69q0-5.39-3.46-8.85t-8.85-3.46H483.85L534-779.23 348.46-594.46V-200Zm0-394.46V-200v-394.46Zm-60-25.54v60H160v360h128.46v60H100v-480h188.46Z"/></svg>
                                </button>
                                <p>67</p>
                            </div>
                            <div class="flex flex-row align-center" style="gap: 0.1rem">
                                <button class="btn-icon" hx-post="/api/v1/feedback?action=dislike&fileId=${fileId}" hx-swap="outerHTML" hx-target="#likes-dislikes-buttons">
                                    <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#000"><path d="M250.77-803.84h420.77v479.99L406.15-60l-33.84-33.85q-6.23-6.23-10.35-16.5-4.11-10.27-4.11-19.34v-10.16l42.46-184h-268q-28.54 0-50.42-21.88Q60-367.62 60-396.15v-64.62q0-6.23 1.62-13.46 1.61-7.23 3.61-13.46l114.62-270.46q8.61-19.23 28.84-32.46t42.08-13.23Zm360.77 60H250.77q-4.23 0-8.65 2.3-4.43 2.31-6.74 7.7L120-463.84v67.69q0 5.38 3.46 8.84 3.46 3.47 8.85 3.47h343.84L426-164.61l185.54-184.77v-394.46Zm0 394.46v-394.46 394.46Zm60 25.53v-59.99H800v-360H671.54v-60H860v479.99H671.54Z"/></svg>
                                </button>
                                <p>2</p>
                            </div>
                            <div class="flex flex-row align-center" style="gap: 0.1rem">
                                <button class="btn-icon" id="comments-toggle" onclick="toggleComments()" hx-get="/api/v1/feedback?fileId=${fileId}" hx-trigger="click once" hx-target="#comments-container" hx-swap="innerHTML">
                                    <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#000"><path d="M852.31-133.85 710.77-275.39H320q-29.19 0-49.98-20.79-20.79-20.78-20.79-49.98v-24.61h435.39q29.82 0 51.06-21.24 21.24-21.24 21.24-51.07V-720h24.62q29.19 0 49.98 20.79 20.79 20.79 20.79 49.98v515.38Zm-684.62-320.3 56.62-56.62h380.31q5.38 0 8.84-3.46t3.46-8.85v-264.61q0-5.39-3.46-8.85t-8.84-3.46H180q-5.38 0-8.85 3.46-3.46 3.46-3.46 8.85v333.54Zm-60 144.92v-478.46q0-29.83 21.24-51.07Q150.17-860 180-860h424.62q29.82 0 51.06 21.24 21.24 21.24 21.24 51.07v264.61q0 29.83-21.24 51.07-21.24 21.24-51.06 21.24H249.23L107.69-309.23Zm60-213.85V-800v276.92Z"/></svg>
                                </button>
                                <p>12</p>
                            </div>
                        </div>
                    @endif
                    <button hx-post="/api/v1/auth/log-out" hx-target="body" hx-push-url="true" class="btn btn-primary">
                        <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="#fff"><path d="M228.31-164q-27.01 0-45.66-18.65Q164-201.3 164-228.31v-503.38q0-27.01 18.65-45.66Q201.3-796 228.31-796h252.07v52H228.31q-4.62 0-8.46 3.85-3.85 3.84-3.85 8.46v503.38q0 4.62 3.85 8.46 3.84 3.85 8.46 3.85h252.07v52H228.31Zm428.92-177.23-37.54-36.39L696.08-454H387.85v-52h308.23l-76.39-76.38 37.54-36.39L796-480 657.23-341.23Z"/></svg>
                        <p>Tanca Sessió</p>
                    </button>
                </div>
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
<div class="w-100 flex flex-row h-100 max-height-85">
    <div class="content">
        ${content}
    </div>
    <div id="comments-container" class="community-content">
    </div>
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