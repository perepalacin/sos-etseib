@param boolean liked    
@param boolean disliked    
@param int likes 
@param int dislikes    
@param int comments    
@param String likeApiRoute
@param String dislikeApiRoute


<div id="likes-dislikes-buttons" class="flex flex-row gap-1 align-center">
    <div class="flex flex-row align-center" style="gap: 0.1rem">
        <button class="btn-icon" hx-post="${likeApiRoute}" hx-swap="outerHTML" hx-target="#likes-dislikes-buttons">
            @if(liked)
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#000"><path d="M709.23-140H313.08v-480l265.38-263.84L612.3-850q6.24 6.23 10.35 16.5 4.12 10.27 4.12 19.35V-804L584.3-620h243.39q28.54 0 50.42 21.89Q900-576.23 900-547.69v64.61q0 6.23-1.31 13.46t-3.92 13.47L780.15-185.69q-8.61 19.23-28.84 32.46T709.23-140ZM253.08-620v480H100v-480h153.08Z"/></svg>
            @else
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#000"><path d="M709.23-140H288.46v-480l265.39-263.84L587.69-850q6.23 6.23 10.35 16.5 4.11 10.27 4.11 19.35V-804l-42.46 184h268q28.54 0 50.42 21.89Q900-576.23 900-547.69v64.61q0 6.23-1.62 13.46-1.61 7.23-3.61 13.47L780.15-185.69q-8.61 19.23-28.84 32.46T709.23-140Zm-360.77-60h360.77q4.23 0 8.65-2.31 4.43-2.31 6.74-7.69L840-480v-67.69q0-5.39-3.46-8.85t-8.85-3.46H483.85L534-779.23 348.46-594.46V-200Zm0-394.46V-200v-394.46Zm-60-25.54v60H160v360h128.46v60H100v-480h188.46Z"/></svg>
            @endif
        </button>
        <p>${likes}</p>
    </div>
    <div class="flex flex-row align-center" style="gap: 0.1rem">
        <button class="btn-icon" hx-post="${dislikeApiRoute}" hx-swap="outerHTML" hx-target="#likes-dislikes-buttons">
            @if(disliked)
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#000"><path d="M250.77-803.84h420.77v479.99L406.15-60l-33.84-33.85q-6.23-6.23-10.35-16.5-4.11-10.27-4.11-19.34v-10.16l42.46-184h-268q-28.54 0-50.42-21.88Q60-367.62 60-396.15v-64.62q0-6.23 1.62-13.46 1.61-7.23 3.61-13.46l114.62-270.46q8.61-19.23 28.84-32.46t42.08-13.23Zm360.77 60H250.77q-4.23 0-8.65 2.3-4.43 2.31-6.74 7.7L120-463.84v67.69q0 5.38 3.46 8.84 3.46 3.47 8.85 3.47h343.84L426-164.61l185.54-184.77v-394.46Zm0 394.46v-394.46 394.46Zm60 25.53v-59.99H800v-360H671.54v-60H860v479.99H671.54Z"/></svg>
            @else
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#000"><path d="M250.77-803.84h420.77v479.99L406.15-60l-33.84-33.85q-6.23-6.23-10.35-16.5-4.11-10.27-4.11-19.34v-10.16l42.46-184h-268q-28.54 0-50.42-21.88Q60-367.62 60-396.15v-64.62q0-6.23 1.62-13.46 1.61-7.23 3.61-13.46l114.62-270.46q8.61-19.23 28.84-32.46t42.08-13.23Zm360.77 60H250.77q-4.23 0-8.65 2.3-4.43 2.31-6.74 7.7L120-463.84v67.69q0 5.38 3.46 8.84 3.46 3.47 8.85 3.47h343.84L426-164.61l185.54-184.77v-394.46Zm0 394.46v-394.46 394.46Zm60 25.53v-59.99H800v-360H671.54v-60H860v479.99H671.54Z"/></svg>
            @endif
        </button>
        <p>${dislikes}</p>
    </div>
    <div class="flex flex-row align-center" style="gap: 0.1rem">
        <button class="btn-icon" id="comments-toggle" onclick="toggleComments()">
            <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#000"><path d="M852.31-133.85 710.77-275.39H320q-29.19 0-49.98-20.79-20.79-20.78-20.79-49.98v-24.61h435.39q29.82 0 51.06-21.24 21.24-21.24 21.24-51.07V-720h24.62q29.19 0 49.98 20.79 20.79 20.79 20.79 49.98v515.38Zm-684.62-320.3 56.62-56.62h380.31q5.38 0 8.84-3.46t3.46-8.85v-264.61q0-5.39-3.46-8.85t-8.84-3.46H180q-5.38 0-8.85 3.46-3.46 3.46-3.46 8.85v333.54Zm-60 144.92v-478.46q0-29.83 21.24-51.07Q150.17-860 180-860h424.62q29.82 0 51.06 21.24 21.24 21.24 21.24 51.07v264.61q0 29.83-21.24 51.07-21.24 21.24-51.06 21.24H249.23L107.69-309.23Zm60-213.85V-800v276.92Z"/></svg>
        </button>
        <p>12</p>
    </div>
</div>