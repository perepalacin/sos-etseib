@param String appTitle
@param java.util.List<entities.BreadcrumbItem> breadcrumbs
@param String tabName
@param boolean isUserLogged
@param java.util.List<entities.FileDao> files
!{String lastCrumb = breadcrumbs.getLast().getRoute();}
!{boolean hasFolders = files.stream().anyMatch(fileDao -> "folder".equals(fileDao.getType()));}
!{boolean hasFiles = files.stream().anyMatch(fileDao -> "file".equals(fileDao.getType()));}


@template.layout.main(isUserLogged = true, appName = tabName,content = @`
    <div class="w-100 flex flex-column gap-1 align-start" style="padding: 1rem 2rem;">
        <nav aria-label="breadcrumb" class="w-100">
            <ol class="breadcrumb w-100">
                @for(entities.BreadcrumbItem breadcrumb : breadcrumbs)
                    <li>
                        <a aria-current="${breadcrumb.getRoute().equals(breadcrumbs.getLast().getRoute())}" href="${breadcrumb.getRoute()}">${breadcrumb.getCrumb()}</a>
                    </li>
                    @if(!breadcrumb.getRoute().equals(breadcrumbs.getLast().getRoute()))
                        <p style="margin: 0rem;">></p>
                    @endif
                @endfor
            </ol>
        </nav>

        @if(hasFolders)
            <h4 class="semi-bold">Folders</h4>
            <nav class="w-100">
                <ul class="folders-list">
                    @for(entities.FileDao file : files)
                        @if("folder".equals(file.getType()))
                            <li>
                                <a href="${lastCrumb + "/" + file.getName()}" class="w-100 flex flex-row align-center justify-start gap-1">
                                    <i class="fa-solid fa-folder" style="color: #55585d"></i>
                                    <p>${file.getName()}</p>
                                </a>
                            </li>
                        @endif
                    @endfor
                </ul>
            </nav>
        @endif
        @if(hasFiles)
            <h4 class="semi-bold">Files</h4>
            <nav class="w-100">
                <table class="w-100">
                    <tr class="w-100">
                        <th style="width: auto; text-align: center; padding: 0rem 0.5rem;">
                            <i class="fa-solid fa-bars"></i>
                        </th>
                        <th class="text-start" style="width: 50%;">Name</th>
                        <th class="text-start"  style="width: auto;">Compartit per</th>
                        <th class="text-start"  style="width: auto;">Compartit el</th>
                        <th class="text-start"  style="width: auto;">Puntuació</th>
                    </tr>
                    @for(entities.FileDao file : files)
                        @if("file".equals(file.getType()))
                            <tr class="w-100">
                                <td>
                                    <a class="flex w-100 justify-center" href="${lastCrumb + '/' + file.getName()}">
                                    @if(file.getName().endsWith(".doc") || file.getName().endsWith(".docx"))
                                        <i class="fa-regular fa-file-word" style="color: #4472ee;"></i>
                                    @elseif(file.getName().endsWith(".pdf"))
                                        <i class="fa-regular fa-file-pdf" style="color: #d94932;"></i>
                                    @elseif(file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls") ||file.getName().endsWith(".xlsm") || file.getName().endsWith(".csv"))
                                        <i class="fa-regular fa-file-table" style="color: #469d4e;"></i>
                                    @elseif(file.getName().endsWith(".ppt") || file.getName().endsWith(".pptx"))
                                        <i class="fa-regular fa-file-powerpoint" style="color: #f3ba28;"></i>
                                    @elseif(file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".png") || file.getName().endsWith(".webp") || file.getName().endsWith(".gif"))
                                        <i class="fa-regular fa-image" style="color: #55585d;"></i>
                                    @else
                                        <i class="fa-regular fa-file-lines" style="color: #4472ee;"></i>
                                    @endif
                                    </a>
                                </td>
                                <td>
                                    <a class="py-0-5 flex w-100" href="${lastCrumb + "/" + file.getName()}">
                                        ${file.getName()}
                                    </a>
                                </td>
                                <td class="font-light">
                                    <a class="flex w-100" href="${lastCrumb + "/" + file.getName()}">
                                        ${file.getSharedBy()}
                                    </a>
                                </td>
                                <td class="font-light">
                                    <a class="flex w-100" href="${lastCrumb + "/" + file.getName()}">
                                        ${file.getCreatedAt().toString()}
                                    </a>
                                </td>
                                <td>
                                    <a class="flex w-100" href="${lastCrumb + "/" + file.getName()}">
                                        4.75/5
                                    </a>
                                </td>
                            </tr>
                        @endif
                    @endfor
                </table>
            </nav>
        @endif
        @if (files.isEmpty())
            <div class="flex flex-column gap-0-5 align-center w-100">
                <img width="300" height="300" alt="Drawing of an empty folder" src="/static/images/emptyFolder.webp" style="margin-bottom: 1rem"/>
                <h2>Ups...</h2>
                <p>Aquesta carpeta sembla estar buida...</p>
                <a href="${breadcrumbs.size() > 1 ? breadcrumbs.get(breadcrumbs.size()-2).getRoute() : "/files/root"}">
                    <button class="btn btn-primary">Torna enrere</button>
                </a>
            </div>
        @endif

    </div>

    </html>
`)