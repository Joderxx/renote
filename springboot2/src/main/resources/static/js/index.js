$(document).ready(function(){
    $(".note-title-text").val("");
    var doc = $(document), sidebar = $("#sidebar"), notes = $("#notes"),noteCon = $("#note-container"),bt = $("#but"),rec = $("#recommend-notes");
    var sum = sidebar.width() + notes.width() + noteCon.width();
    var hdLeft = parseInt($("#notes-handler").css("left"));
    var notesWidth = notes.width();
    var editor = null;
    var notesViewPage = 1;
    var notesViewNtype = null;
    var notesViewNbookId = null;

    //用户登录信息
    $.ajax({
        type: 'get', 
        url: "/user/info",   
        statusCode: {    
            200: function(data){       
                var imgurl = data.user.avatar;
                $("#user").attr("src","images/"+imgurl);
                $(".user-image").attr("src","images/"+imgurl);
                $(".user-name").html(data.user.username);
                $(".user-email").html(data.user.email);
            },   
        }
    });

    //获取用户通知条数
    $.ajax({
        type: 'get', 
        url: "/user/advice/count",   
        statusCode: {    
            200: function(data){       
                var count = data.count;
                if (count>0) {
                    $(".advice-point").css("display","block");
                    $(".advice-count").html(count);
                    $(".advice-count").css("display","block");
                }
            },   
        }
    });
    
    //左边的动画效果
    $("#sidebar-handler").mousedown (function (e) {
        var me = $(this);
        var deltaX = e.clientX - (parseInt(me.css("left")) || parseInt(me.prop("clientLeft")));
        doc.mousemove(function (e){
            var lt = e.clientX - deltaX; 
            lt = (lt <= 80) ? 80 : lt;
            lt = (lt >= 270) ? 270 : lt;
            //lt = lt >= sum - me.width() ? sum - me.width() : lt;4
            $("#rec-icon").css("left", (lt+notes.width()-40) + "px");
            me.css ("left", (lt-me.width()) + "px");
            sidebar.width(lt);
            bt.css ("left", lt + "px");
            notes.css ("left", lt + "px");
            notes.css ("right", (sum-notes.width()-lt) + "px");
            noteCon.css ("left", (lt+notes.width()) + "px");
            rec.css ("left", (lt+notes.width()) + "px");
            
            if (lt==80) {
                //改变成图标的形式展示
                $("#tree-layout").css("display","none");
                $("#icon-layout").css("display","block");
            }else{
                $("#icon-layout").css("display","none");
                $("#tree-layout").css("display","block");
                //回到之前文档树的形式展示
                
            }
        });
    });
    //中间的动画效果
    $("#notes-handler").mousedown(function(e){
        var me = $(this);
        var deltaX = e.clientX;
        var hdLeftNow = parseInt($("#notes-handler").css("left"));
        var notesWidthNow = notes.width();
        doc.mousemove(function(e){
            var lt = e.clientX - deltaX;
            var noteslt = notesWidthNow + lt;
            noteslt = noteslt <= notesWidth - 100 ?notesWidth - 100:noteslt;
            noteslt = noteslt >= notesWidth + 50 ?notesWidth + 50:noteslt;
            notes.width(noteslt + "px");

            var melt = hdLeftNow+lt;
            melt = melt <= hdLeft - 100 ?hdLeft - 100:melt;
            melt = melt >= hdLeft + 50 ?hdLeft + 50:melt;
            me.css("left", melt +"px");
            rec.css("left", (parseInt(notes.css("left"))+notes.width()) +"px");
            noteCon.css("left", (parseInt(notes.css("left"))+notes.width()) +"px");
            $("#rec-icon").css("left", (parseInt(notes.css("left"))+notes.width()-40) +"px");
            //noteCon.css ("left", (deltaX + lt -299) + "px");
        })
    });

    
    doc.mouseup (function(){
        doc.unbind("mousemove");
    });   

    //缩进按钮动画效果
    $("#but").click(function() {
        //$("#sidebar").css("left","-200px");
        var sideWidth= $("#sidebar").width();
        var notesWidth = $("#notes").width();
        console.log(sideWidth+notesWidth+parseInt(sideWidth)+parseInt(notesWidth));
        if ($(this).css("left")!="0px") {
            sidebar.animate({left:'-'+sideWidth});
            $("#sidebar-handler").animate({left:'-'+sideWidth});
            $(this).animate({left:'0'});
            notes.animate({left:'0'});
            rec.animate({left:(parseInt(notesWidth)+1)+'px'});
            noteCon.animate({left:(parseInt(notesWidth)+1)+'px'});
            $("#rec-icon").animate({left:(parseInt(notesWidth)-39)+'px'});
        }else{
            console.log(sidebar.css("background-color"));
            sidebar.animate({left:'0px'});
            $("#sidebar-handler").animate({left:(parseInt(sideWidth)-5)+'px'});
            $(this).animate({left:sideWidth});
            notes.animate({left:sideWidth});
            rec.animate({left:(parseInt(sideWidth)+parseInt(notesWidth)+1)+'px'});
            noteCon.animate({left:(parseInt(sideWidth)+parseInt(notesWidth)+1)+'px'});
            $("#rec-icon").animate({left:(parseInt(sideWidth)+parseInt(notesWidth)-39)+'px'});
        }
    });


    //推荐笔记展示事件

    var reicon =true;
    var reover = function(){
        $("#rec-icon").unbind("mouseover");
        if (reicon&&parseInt($(this).css("left"))<600) {
            $(this).animate({left:parseInt($(this).css("left"))+40+'px'});
            reicon = false;
        } 
    };

    $("#rec-icon").mouseover(reover);

    var reout = function(){
        setTimeout(function(){
            $("#rec-icon").mouseover(reover);
        },500);
        $("#rec-icon").unbind("mouseout");
        if (!reicon&&!$("#rec-icon").is(":animated")) {
            $(this).animate({left:parseInt($(this).css("left"))-40+'px'});
            reicon = true;
        } 
        setTimeout(function(){
            $("#rec-icon").mouseout(reout);
        },500);
    };
    $("#rec-icon").mouseout(reout);
   
    $("#rec-icon").click(function(){
        reicon =true;
        var ntLeft = parseInt($("#notes-handler").css("left"))+parseInt(notes.css("left"))+parseInt($("#notes-handler").width());
        var recWidth = parseInt(rec.width());
        if (rec.css("display")!="block") {
            rec.css("left", parseInt($("#notes-handler").css("left"))+parseInt($("#but").css("left"))-recWidth+'px');
            rec.css("display","block");
            rec.animate({left:ntLeft+'px'});
            //noteCon.animate({left:(ntLeft+recWidth)+'px'});
            $("#rec-icon").animate({left:(ntLeft+recWidth)+'px'});
            $("#recnotes-view").empty();
            $.ajax({
                type: 'get',
                url: "/recommend/user/recent",
                statusCode: {
                    200: function (data) {
                        $.each(data.notes, function(i, item){
                            $("#recnotes-view").append("<li class=\"file-item recommend-notes\" ntype=\"recommend\" noteid=\"" + 
                                item.noteId + "\"><div class=\"file-title\"><img class=\"file-icon\" src=\"images/note.svg\"><span class=\"file-name\">"+
                                item.noteName+"</span></div><div class=\"note-abstract\">"+
                                item.content+"</div><span class=\"note-time\">"+
                                item.gmtCreate+"</span></li>"); 
                        });
                    }
                }
            });  
        } else {    
            rec.animate({left:(ntLeft-recWidth)+'px'});
             setTimeout(function () {
                 rec.css("display","none");
             },300);   
             $("#rec-icon").animate({left:ntLeft-40+'px'});
             $("#recnotes-view").empty();
             //noteCon.animate({left:ntLeft+'px'});
        }
    });
    
    //json处理
    //收藏笔记
    $("#notes-collect").click(function (){
        $(this).css({
            "background-color":"#f7f7f7",
            "border-left": "5px solid #ea6f5a",
        });
        $(this).siblings().css({
            "background-color":"",
            "border-left": "",
        });
        $("#notes-dropdown").children().css({
            "background-color":"",
            "border-left": "",
        });
        $("#notes-view").empty();
        notesViewPage = 1;
        notesViewNtype = "collect";
        notesViewNbookId = null;
        notesViewAjax(notesViewNtype,notesViewNbookId);
    });
    //公开笔记
    $("#notes-public").click(function (){
        $(this).css({
            "background-color":"#f7f7f7",
            "border-left": "5px solid #ea6f5a",
        });
        $(this).siblings().css({
            "background-color":"",
            "border-left": "",
        });
        $("#notes-dropdown").children().css({
            "background-color":"",
            "border-left": "",
        });
        $("#notes-view").empty();
        notesViewPage = 1;
        notesViewNtype = "public";
        notesViewNbookId = null;
        notesViewAjax(notesViewNtype,notesViewNbookId);
    });
    //分享笔记
    $("#notes-share").click(function (){
        $(this).css({
            "background-color":"#f7f7f7",
            "border-left": "5px solid #ea6f5a",
        });
        $(this).siblings().css({
            "background-color":"",
            "border-left": "",
        });
        $("#notes-dropdown").children().css({
            "background-color":"",
            "border-left": "",
        });
        $("#notes-view").empty();
        notesViewPage = 1;
        notesViewNtype = "shared";
        notesViewNbookId = null;
        notesViewAjax(notesViewNtype,notesViewNbookId);
    });
    //回收站
    $("#recycle").click(function (){
        $(this).css({
            "background-color":"#f7f7f7",
            "border-left": "5px solid #ea6f5a",
        });
        $(this).siblings().css({
            "background-color":"",
            "border-left": "",
        });
        $("#notes-dropdown").children().css({
            "background-color":"",
            "border-left": "",
        });
        $("#notes-view").empty();
        notesViewPage = 1;
        notesViewNtype = "recycle";
        notesViewNbookId = null;
        notesViewAjax(notesViewNtype,notesViewNbookId);
       
    });
    //私有笔记
    $("#notes-private").click(function () {
        //测试
        $(this).siblings().css({
            "background-color":"",
            "border-left": "",
        });

        $("#notes-view").empty();//这里要改进成显示笔记本
        noteCon.css("display","none");

        var state = $("#notes-dropdown").css("display") == "block" ? "none" : "block";
        //此时的state是现在需要的
        if (state == "none") {
            $("#notes-dropdown").empty();
        } else {
            $.ajax({
                type: 'get',
                url: "/user/private",
                statusCode: {
                    200: function(data){
                        $.each(data.notebooks, function(i, item){
                            $("#notes-dropdown").append("<li class=\"dropdown-item\" ntype=\"private\" nbookid=\""+item.nbookId+"\"><i class=\"icon-myfolder\"></i><div>"+item.nbookName+"</div></li>");
                        });
                        $("#notes-dropdown li:first").click();
                    },
                }
            });

        }
        $("#notes-dropdown").css("display",state);

    });

    //私有笔记展示
    $("#notes-dropdown").on("click",".dropdown-item",function() {
        $(this).css({
            "background-color":"#f7f7f7",
            "border-left": "5px solid #ea6f5a",
        });
        $(this).siblings().css({
            "background-color":"",
            "border-left": "",
        });
        $("#notes-private").siblings().css({
            "background-color":"",
            "border-left": "",
        });
        $("#notes-view").empty();
        var nbookId = $(this).attr("nbookid");
        notesViewPage = 1;
        notesViewNtype = "private";
        notesViewNbookId = nbookId;
        notesViewAjax(notesViewNtype,notesViewNbookId);
    });
    
    doc.on("click",".recommend-notes",function(e){
       e.stopPropagation();
    });

    //笔记编辑器展示
    doc.on("click",".file-item",function(){
        $(".file-item").css({
            "background-color":"",
        });
        
        $(this).css({
            "background-color":"#f1f1f1",
        });

        var noteId = $(this).attr("noteid");
        var nbookId = $(this).attr("nbookid");
        var type = $(this).attr("ntype");
        // /user/{type:^public$|^collect$|^shared$}/notes/{noteId:\d{1,18}}
        $("#note-container").attr("noteid",noteId);
        $("#note-container").attr("nbookid",nbookId);
        if (type=="recycle" || type=="recommend") {
            return;
        }else{
            $("#editor").empty();
            $("#wangeditor1").empty();
            $("#wangeditor2").empty();
            noteCon.css("display","block");
            editor = null;
            var url = null;
            if (type=="private") {
                url = "/user/notebooks/"+nbookId+"/notes/"+noteId;
                $(".saveNote").off("click");
                $(".saveNote").on("click",save);
                $(".saveNote").css("display","block");
            }else{
                url = "/user/"+type+"/notes/"+noteId;
                $(".saveNote").off("click");
                $(".saveNote").css("display","none");
            }
            $.ajax({
                type: 'get',
                url: url,
                statusCode: {
                    200: function(data){
                        var note = data.note;
                        if (note.type == "markdown") {
                            $("#note-editor").empty();
                            $("#note-editor").append("<div id=\"editor\"></div> ");
                            var content = note.content;
                            editor = new tui.Editor({
                                el: document.querySelector('#editor'),
                                previewStyle: 'vertical',
                                height: '100%',
                                initialEditType: 'markdown',
                                useCommandShortcut: true,
                                initialValue: content,
                                exts: ['colorSyntax', 'uml', 'chart', 'mark', 'table','scrollSync',  'taskCounter']
                            });
                        } else {
                            $("#note-editor").empty();
                            $("#note-editor").append("<div id=\"wangeditor1\" class=\"toolbar\"></div><div id=\"wangeditor2\" class=\"text_editor\"></div> ");
                            var E = window.wangEditor
                            editor = new E('#wangeditor1','#wangeditor2')
                            // 或者 var editor = new E( document.getElementById('editor') )
                            //document.getElementById('editor').style.display = "none";
                            editor.create();
                            editor.txt.html(note.content);
                            //console.log(editor.txt.html())
                        }
                        $(".note-title-text").val(note.noteName);
                    },
                }
            });
        }
        
        
    });
        




    // 以下是右键菜单
    var getOffset = {
        top: function (obj) {
            return obj.offsetTop + (obj.offsetParent ? arguments.callee(obj.offsetParent) : 0)
        },
        left: function (obj) {
            return obj.offsetLeft + (obj.offsetParent ? arguments.callee(obj.offsetParent) : 0)
        }
    };
    function RightClickMenu(e,objId,evenId) {    
        
        e.preventDefault();
        var oMenu = document.getElementById(evenId);
        var aUl = oMenu.getElementsByTagName("ul");
        var aLi = oMenu.getElementsByTagName("li");
        for (var i = aLi.length - 1; i >= 0; i--) {
            aLi[i].setAttribute("ntype",objId[0].getAttribute("ntype"));
            aLi[i].setAttribute("nbookid",objId[0].getAttribute("nbookid"));
            aLi[i].setAttribute("noteid",objId[0].getAttribute("noteid"));
        }
        var showTimer = hideTimer = null;
        var i = 0;
        var maxWidth = maxHeight = 0;
        var aDoc = [document.documentElement.offsetWidth, document.documentElement.offsetHeight];
        oMenu.style.display = "none";

        for (i = 0; i < aLi.length; i++) {
            //为含有子菜单的li加上箭头
            aLi[i].getElementsByTagName("ul")[0] && (aLi[i].className = "sub");

            //鼠标移入
            aLi[i].onmouseover = function () {
                var oThis = this;
                var oUl = oThis.getElementsByTagName("ul");

                //鼠标移入样式
                oThis.className += " active";

                //显示子菜单
                if (oUl[0]) {
                    clearTimeout(hideTimer);
                    showTimer = setTimeout(function () {
                        for (i = 0; i < oThis.parentNode.children.length; i++) {
                            oThis.parentNode.children[i].getElementsByTagName("ul")[0] &&
                                (oThis.parentNode.children[i].getElementsByTagName("ul")[0].style.display = "none");
                        }
                        oUl[0].style.display = "block";
                        oUl[0].style.top = oThis.offsetTop + "px";
                        oUl[0].style.left = oThis.offsetWidth + "px";
                        setWidth(oUl[0]);

                        //最大显示范围                    
                        maxWidth = aDoc[0] - oUl[0].offsetWidth;
                        maxHeight = aDoc[1] - oUl[0].offsetHeight;

                        //防止溢出
                        maxWidth < getOffset.left(oUl[0]) && (oUl[0].style.left = -oUl[0].clientWidth + "px");
                        maxHeight < getOffset.top(oUl[0]) && (oUl[0].style.top = -oUl[0].clientHeight + oThis.offsetTop + oThis.clientHeight + "px")
                    }, 300);
                }
            };

            //鼠标移出  
            aLi[i].onmouseout = function () {
                var oThis = this;
                var oUl = oThis.getElementsByTagName("ul");
                //鼠标移出样式
                oThis.className = oThis.className.replace(/\s?active/, "");

                clearTimeout(showTimer);
                hideTimer = setTimeout(function () {
                    for (i = 0; i < oThis.parentNode.children.length; i++) {
                        oThis.parentNode.children[i].getElementsByTagName("ul")[0] &&
                            (oThis.parentNode.children[i].getElementsByTagName("ul")[0].style.display = "none");
                    }
                }, 300);
            };
        }



        var e = e || window.e;
        oMenu.style.display = "block";
        oMenu.style.top = e.clientY + "px";
        oMenu.style.left = e.clientX + "px";
        setWidth(aUl[0]);

        //最大显示范围
        maxWidth = aDoc[0] - oMenu.offsetWidth;
        maxHeight = aDoc[1] - oMenu.offsetHeight;
        console.log(aDoc[1] + " " + maxHeight + " " + oMenu.offsetHeight);
        //防止菜单溢出
        oMenu.offsetTop > maxHeight && (oMenu.style.top = maxHeight + "px");
        oMenu.offsetLeft > maxWidth && (oMenu.style.left = maxWidth + "px");
        return false;

        //取li中最大的宽度, 并赋给同级所有li  
        function setWidth(obj) {
            maxWidth = 0;
            for (i = 0; i < obj.children.length; i++) {
                var oLi = obj.children[i];
                var iWidth = oLi.clientWidth - parseInt(oLi.currentStyle ? oLi.currentStyle["paddingLeft"] : getComputedStyle(oLi, null)["paddingLeft"]) * 2
                if (iWidth > maxWidth) maxWidth = iWidth;
            }
            for (i = 0; i < obj.children.length; i++) obj.children[i].style.width = maxWidth + "px";
        }
    };


    //右键
    var rightMenu = ["rightMenu-notes","rightMenu-public","rightMenu-collect","rightMenu-nbook","rightMenu-open","rightMenu-private","rightMenu-shared","rightMenu-recycle"];
    
    function closeRightMenu() {
        for (var i = 0; i < rightMenu.length; i++) {
            document.getElementById(rightMenu[i]).style.display = "none";
        }
    }


    $("#notes-dropdown").on("contextmenu",".dropdown-item",function(e) {
        addRightClickMenu(e,$(this),"rightMenu-nbook");
    });
    $("#notes-view").on("contextmenu",".private-notes",function(e) {
        addRightClickMenu(e,$(this),"rightMenu-notes");
    }).on("contextmenu",".public-notes",function(e) {
        addRightClickMenu(e,$(this),"rightMenu-public");
    }).on("contextmenu",".collect-notes",function(e) {
        addRightClickMenu(e,$(this),"rightMenu-collect");
    }).on("contextmenu",".shared-notes",function(e) {
        addRightClickMenu(e,$(this),"rightMenu-shared");
    }).on("contextmenu",".recycle-notes",function(e) {
        addRightClickMenu(e,$(this),"rightMenu-recycle");
    });
    $("#notes-private").on("contextmenu",function(e) {
        addRightClickMenu(e,$(this),"rightMenu-private");
    });
    $("#notes-share").on("contextmenu",function (e) {
        addRightClickMenu(e,$(this),"rightMenu-open");
    });
    $("#notes-public").on("contextmenu",function (e) {
        addRightClickMenu(e,$(this),"rightMenu-open");
    });
    $("#notes-collect").on("contextmenu",function (e) {
        addRightClickMenu(e,$(this),"rightMenu-open");
    });
    $("#recycle").on("contextmenu",function (e) {
        addRightClickMenu(e,$(this),"rightMenu-open");
    });

    function addRightClickMenu(e, objId,rightMenuId) {
        closeRightMenu();
        RightClickMenu(e,objId,rightMenuId);
    };

    $("#createNbook").click(function () {    
        $("#notes-dropdown").css("display")=="none" && $("#notes-private").click();

        $.ajax({
            type: 'post',
            url: "/user/notebooks/",
            data: 'name='+"新建笔记本",
            statusCode: {
                200: function(data){
                    var nbookId = data.notebook.nbookId;
                    $("#notes-dropdown").append("<li class=\"dropdown-item\" ntype=\"private\" nbookId=\""+nbookId+"\"><i class=\"icon-myfolder\"></i><div>新建笔记本</div></li>");
                },
                500: function(data){
                    pop("tip","出现错误");
                }
            }
        });
    });

    

    $(".recover").click(function () { 
        var _this = $(this);
        var url = null;
        if (_this.attr("noteid")=="null") {
            url = "/user/recycle/notebook/" + _this.attr("nbookid")+ "/recovery";
        }else{
            url = "/user/recycle/note/" + _this.attr("noteid") + "/recovery";
        }
        $.ajax({
            type: 'post',
            url: url+"?_method=PUT",
            statusCode: {
                200: function(data){
                    if (_this.attr("noteid")=="null") {
                        $("#notes-view [nbookid=\""+_this.attr("nbookid")+"\"]").remove();
                    }else{
                         $("#notes-view [noteid=\""+_this.attr("noteid")+"\"]").remove();
                    }
                    pop("tip","恢复成功！");
                },
                500: function(data){
                    pop("tip","出现错误");
                }
            }
        });
    });

    $(".open").click(function () { 
        var _this = $(this);
        if (_this.attr("ntype")=="collect") {
            $("#notes-collect").click();
        } else if(_this.attr("ntype")=="public"){
            $("#notes-public").click();
        } else if(_this.attr("ntype")=="share"){
            $("#notes-share").click();
        } else if(_this.attr("ntype")=="recycle"){
            $("#recycle").click();
        } else if(_this.attr("ntype")=="private"){
            if ($("#notes-dropdown").css("display")=="none") {
                $("#notes-private").click();
            }
        } 
    });

    
    $(".delete").click(function () { 
        var _this = $(this);
        var noteId = _this.attr("noteid");
        var nbookId = _this.attr("nbookid");
        var type = _this.attr("ntype");
        if (noteId=="null") {
            if(confirm('确定删除整本笔记？')){
                $.ajax({
                    type: 'post',
                    url: "/user/notebooks/"+nbookId+"?_method=DELETE",
                    statusCode: {
                        200: function(data){
                            $("#notes-dropdown [nbookid=\""+nbookId+"\"]").remove();
                            $("#notes-view [nbookid=\""+nbookId+"\"]").remove();
                        },
                        500: function(data){
                            pop("tip","出现错误");
                        }
                    }
                });


                $("#notes-dropdown [nbookid=\""+nbookId+"\"]").remove();  
                $("#notes-view [nbookid=\""+nbookId+"\"]").remove();  

                $("#notes-dropdown li:first").click();
                if ( $("#notes-dropdown li:first").length == 0) {
                    noteCon.css("display","none");
                    $("#notes-view").empty();
                    $("#editor").empty();
                    $("#wangeditor1").empty();
                    $("#wangeditor2").empty();
                    editor = null;
                }
            }
        } else {
            if (type!="private") {
                var type_cn =null;
                var url = null;
                if (type == "collect") {type_cn="收藏";url="/user/collect/notes/"+noteId;}
                else if(type == "public"){type_cn="公开";url="/user/public/notes/"+noteId;}
                else if(type == "shared"){type_cn="分享";url="/user/notes/" + noteId + "/share";}
                if(confirm('确定取消' + type_cn + '吗？')){
                    $.ajax({
                        type: 'post',
                        url: url+"?_method=DELETE",
                        statusCode: {
                            200: function(data){
                                if($("#notes-view [noteid=\""+noteId+"\"]").css("background-color")=="rgb(234, 111, 90)"){
                                    $("#notes-view [noteid=\""+noteId+"\"]").remove();
                                    clickFirstNote();
                                }else{
                                    $("#notes-view [noteid=\""+noteId+"\"]").remove();
                                }
                            },
                            500: function(data){
                                pop("tip","出现错误");
                            }
                        }
                    });
                    if($("#notes-view [noteid=\""+noteId+"\"]").css("background-color")=="rgb(234, 111, 90)"){
                        $("#notes-view [noteid=\""+noteId+"\"]").remove();
                        clickFirstNote();
                    }else{
                        $("#notes-view [noteid=\""+noteId+"\"]").remove();
                    }
                    pop("tip","删除成功！");
                }
            } else {
                if(confirm('确定删除笔记吗？')){
                    $.ajax({
                        type: 'post',
                        url: "/user/notebooks/"+nbookId+"/notes/"+noteId+"?_method=DELETE",
                        statusCode: {
                            200: function(data){
                                if($("#notes-view [noteid=\""+noteId+"\"]").css("background-color")=="rgb(234, 111, 90)"){
                                    $("#notes-view [noteid=\""+noteId+"\"]").remove();
                                    clickFirstNote();
                                }else{
                                    $("#notes-view [noteid=\""+noteId+"\"]").remove();
                                }
                            },
                            500: function(data){
                                pop("tip","出现错误");
                            }
                        }
                    });
                    if($("#notes-view [noteid=\""+noteId+"\"]").css("background-color")=="rgb(234, 111, 90)"){
                        $("#notes-view [noteid=\""+noteId+"\"]").remove();
                        clickFirstNote();
                    }else{
                        $("#notes-view [noteid=\""+noteId+"\"]").remove();
                    }
                    pop("tip","删除成功！");
                }
            }
            
        }
    });
    $(".share").click(function () { 
        var _this = $(this);
        var noteId = _this.attr("noteid");
        var nbookId = _this.attr("nbookid");
        if(confirm('确定分享吗？')){
            $.ajax({
                type:'post',
                url:"/user/notes/"+noteId+"/share?_method=PUT",
                statusCode: {
                    200:function (data) {
                        pop("findAddress","快复制下面的链接分享给你的朋友吧","https://www.renote.top/shownote?address="+data.address.noteAddress);
                    }
                }
            });
        }



    });
    $(".public").click(function () { 
        var _this = $(this);
        var noteId = _this.attr("noteid");
        var nbookId = _this.attr("nbookid");
        if(confirm('确定公开吗？')){
            $.ajax({
                type:'post',
                url:"/user/notebooks/"+ nbookId +"/notes/"+noteId+"/public?_method=PUT",
                statusCode: {
                    200:function (data) {
                        pop("tip","公开成功！");
                    }
                }
            });
        }

    

    });
    //双击改名
    $("#notes-dropdown").on("dblclick","div",function(){
        var element = $(this);
        var nbookId = element.parent().attr("nbookid");
        var oldhtml = element.html();
        //如果已经双击过，内容已经存在input，不做任何操作
        if (oldhtml.indexOf('type="text"') > 0) {
            return;
        }
        //创建新的input元素
        var newobj = document.createElement('input');
        //为新增元素添加类型
        newobj.type = 'text';
        newobj.className = 'nameInput';
        //为新增元素添加value值
        newobj.value = oldhtml;
        //为新增元素添加光标离开事件
        newobj.onblur = function () {
            var newName = this.value == oldhtml ? oldhtml : this.value;
            if (newName!=oldhtml) {
                $.ajax({
                    type : 'post',
                    url : "/user/notebooks/" + nbookId ,
                    data: 'name='+newName+"&_method=PUT",
                    statusCode: {
                        200: function(data){

                        },
                        500: function(data){
                            pop("tip","出现错误");
                        }
                    }
                });
            }
        element.html(newName); 
            //当触发时判断新增元素值是否为空，为空则不修改，并返回原有值 
        }
        //设置该标签的子节点为空
        element.html("");
        //添加该标签的子节点，input对象
        element.append(newobj);
        //设置选择文本的内容或设置光标位置（两个参数：start,end；start为开始位置，end为结束位置；如果开始位置和结束位置相同则就是光标位置）
        newobj.setSelectionRange(0, oldhtml.length);
        //设置获得光标
        newobj.focus();
    });
    
    $(".reName").click(function (){
        var _this = $(this);
        var nbookId = _this.attr("nbookid");
        console.log("#notes-dropdown div[nbookid=\""+nbookId+"\"]");
        $("#notes-dropdown li[nbookid=\""+nbookId+"\"] div").dblclick();
    });





    $("#createNote").click(function () { 
        var _this = $(this);
        var nbookId = _this.attr("nbookid");
        $.ajax({
            type: 'post',
            url: "/user/notebooks/"+nbookId+"/notes/",
            data: 'type=text&name=新建笔记',
            statusCode: {
                200: function(data){
                    var note = data.note;
                    $("#notes-view").append("<li class=\"file-item private-notes\" ntype=\"private\" nbookid=\""+
                        nbookId+"\" noteid=\"" +
                        note.noteId + "\"><div class=\"file-title\"><span class=\"file-name\">新建笔记</span></div><div class=\"note-abstract\">"+
                        note.content+"</div></li>");
                    clickFirstNote();
                },
                500: function(data){
                    pop("tip","出现错误");
                }
            }
        });
    });
    $("#createMarkdown").click(function () {    
        var _this = $(this);
        var nbookId = _this.attr("nbookid");
        $.ajax({
            type: 'post',
            url: "/user/notebooks/"+nbookId+"/notes/",
            data: 'type=markdown&name=新建Markdown',
            statusCode: {
                200: function(data){
                    var note = data.note;
                    $("#notes-view").append("<li class=\"file-item private-notes\" ntype=\"private\" nbookid=\""+
                        nbookId+"\" noteid=\"" +
                        note.noteId + "\"><div class=\"file-title\"><span class=\"file-name\">新建Markdown</span></div><div class=\"note-abstract\">"+
                        note.content+"</div></li>");
                    clickFirstNote();
                },
                500: function(data){
                    pop("tip","出现错误");
                }
            }
        });
    });
    




    function clickFirstNote() {
        $("#notes-view li:first").click();
        if ( $("#notes-view li:first").length == 0) {
            noteCon.css("display","none");
            $("#editor").empty();
            $("#wangeditor1").empty();
            $("#wangeditor2").empty();
            editor = null;
        }
    }



    $(window).keydown(function(e) {
        //console.log(e.keyCode);//打印event，观察各属性，代替查阅文档，调试用。
        if (e.keyCode == 83 && e.ctrlKey) {
            e.preventDefault();
            $(".saveNote").click();
            
        }
    });
    var save = function () {
        $(".saveNote").off("click");
        var note = $("#note-container");
            if(note.css("display")!="none"){
                var nbookId = note.attr("nbookid"),
                noteId = note.attr("noteid"),
                name = $(".note-title-text").val(),
                content = null;
                var b = new Base64(); 
                if ('function'== typeof editor.getValue) {
                    content = b.encode(editor.getValue());
                    content = content.replace(/\s/g,"+");
                } else {
                    content = b.encode(editor.txt.html());
                    content = content.replace(/\s/g,"+");
                }

                $.ajax({
                    type : 'post',
                    url : "/user/notebooks/"+nbookId+"/notes/"+noteId,
                    data : 'name='+name+'&content='+content+"&_method=PUT",
                    statusCode: {
                        200: function(data){
                            $(".private-notes").each(function(){
                                var _this = $(this);
                                if(_this.css("background-color")=="rgb(241, 241, 241)"){
                                    _this.children(".file-title").children(".file-name").html(data.note.noteName);
                                    _this.children(".note-abstract").html(data.note.content.substring(0,100));
                                }
                            });
                            saveSuccess();
                        },
                        500: function(data){
                            pop("tip","出现错误");
                        }
                    }
                });
            }
        setTimeout(function() {
            $(".saveNote").on("click",save);
        }, 2000);
        
    }
    $(".saveNote").on("click",save);
    //$("#note-container").on("click",".saveNote",save);

    function saveSuccess() {
        $("body").append("<span class=\"saveSuccess\">保存成功</span>");
        $(".saveSuccess").css({
            "position": "absolute",  
            "border": "1px solid #ddd", 
            "background-color": "white", 
            "color":"#555",
            //"box-shadow": "1px 1px 5px 1px #444",
            "padding":"5px 15px",
            "top": "10%", 
            "left":"45%",
            //"height":"50px",
            //"width":"100px",
            "border-radius":"4px",
            "display":"block",
            "z-index":"2000",
        });
        setTimeout(function() {
            $(".saveSuccess").remove();
        },1000);
    }









    $("#notes-view").scroll(function () {
        var _this = $(this);

        if ((_this.scrollTop()+_this.height()) >= $("#notes-view")[0].scrollHeight) {
           notesViewAjax(notesViewNtype,notesViewNbookId);
        }
    });
    function notesViewAjax(ntype,nbookId) {
        var url = null;
        if (ntype=="recommend") {return;}
        if (ntype=="private") {
            url = "/user/notebooks/"+nbookId+"/notes?page="+(notesViewPage++);
        } else {
            url = "/user/"+ntype + "?page="+(notesViewPage++);
        }
        //console.log("没有数据了"+notesViewPage);
        if (ntype=="recycle") {

            $.ajax({
                type: 'get',
                url: "/user/recycle?page="+(notesViewPage++),
                statusCode: {
                    200: function(data){   
                        if (data.notebooks!=null || data.notes!=null) {
                            $.each(data.notebooks, function(i, item){
                                $("#notes-view").append("<li class=\"file-item recycle-notes\" ntype=\"recycle\" nbookid=\""+
                                    item.nbookId+"\"><div class=\"file-title\"><img class=\"file-icon\" src=\"images/nbook.svg\"><span class=\"file-name\">"+
                                    item.nbookName+"</span></div><span class=\"note-time\">"+
                                    item.gmtCreate+"</span></li>");
                            });
                            $.each(data.notes, function(i, item){
                                $("#notes-view").append("<li class=\"file-item recycle-notes\" ntype=\"recycle\" nbookid=\""+
                                    item.nbookId+"\" noteid=\"" +
                                    item.noteId + "\"><div class=\"file-title\"><img class=\"file-icon\" src=\"images/note.svg\"><span class=\"file-name\">"+
                                    item.noteName+"</span></div><div class=\"note-abstract\">"+
                                    item.content+"</div><span class=\"note-time\">"+
                                    item.gmtCreate+"</span></li>");
                            });
                            if((notesViewPage-1)==1)clickFirstNote();
                        } else {
                            console.log("没有数据了");
                        }

                    },
                }
            });

        } else {
            $.ajax({
                type: 'get',
                url: url,
                statusCode: {
                    200: function(data){
                        if (data!=null) {
                            $.each(data.notes, function(i, item){
                                $("#notes-view").append("<li class=\"file-item " + ntype +"-notes\" ntype=\"" + ntype + "\" nbookid=\""+
                                    nbookId+"\" noteid=\"" + 
                                    item.noteId + "\"><div class=\"file-title\"><img class=\"file-icon\" src=\"images/note.svg\"><span class=\"file-name\">"+
                                    item.noteName+"</span></div><div class=\"note-abstract\">"+
                                    item.content+"</div><span class=\"note-time\">"+
                                    item.gmtCreate+"</span></li>"); 
                            });
                            if((notesViewPage-1)==1)clickFirstNote();
                        } else {
                            console.log("没有数据了");
                        }

                    },
                }
            });
        }
        clickFirstNote();   
    }
    //展示url
    $(".findAddress").click(function (e) { 
        e.stopPropagation();
        var _this = $(this);
        var type = _this.attr("ntype");
        var noteId = _this.attr("noteid");
        $("#rightMenu-"+type).css("display","none");
        if (type=="shared") {
            $.ajax({
                type:'get',
                url:"/user/notes/"+noteId+"/share",
                statusCode: {
                    200:function (data) {
                        pop("findAddress","快复制下面的链接分享给你的朋友吧","https://www.renote.top/shownote?address="+data.address.noteAddress);
                    }
                }
            });
        }else if(type=="public"){
            pop("findAddress","以下是此条笔记的连接","https://www.renote.top/shownote?noteid="+noteId);
        }
    });
    //弹框函数
   
    function pop(type,info,address) {
        $(".pop-up-main").empty();
        if (type=="tip") {
            $(".pop-up-main").append("<span class=\"pop-text\">"+
                info+"</span>");
            $(".pop-up").css({
                "top": "30%", 
                "left": "40%",
                "width":"10%",
                "min-width": "150px",
                "min-height": "90px",
                "display":"block",
            });
        }else if(type=="findAddress"){
            $(".pop-up-main").append("<span class=\"pop-text\">"+
                info+"</span> <input id=\"address-text\" type=\"text\" name=\"\" value=\""+
                address+"\"> <div class=\"copy\" onclick=\"$('#address-text').select();document.execCommand('Copy');$('.copy-success').css('display','inline-block')\">复制</div> <span class=\"copy-success\" style=\"display: none;\">复制成功！</span>");
            $(".pop-up").css({
                "top": "30%", 
                "left": "35%",
                "width":"30%",
                "min-width": "420px",
                "min-height": "170px",
                "display":"block",
            });
        }
    }
    //关闭弹框
    $(".user-messege").on("click",".close",function(e){
        $(".user-messege").css("display","none");
    });
    $(".pop-up").on("click",".close",function(e){
        e.stopPropagation();
        $(".pop-up").css("display","none");
    });

    //信息通知版块
    $(".user-messege").on("click",".messege-title",function(e){
        e.stopPropagation();
        $(this).siblings().removeClass('selected');
        $(this).addClass('selected');
    });
    $("#user").click(function(e) {
        e.stopPropagation();
        if ($("#user-info").css("display")=="none") {
            $("#user-info").css("display","block");
        }else{
            $("#user-info").css("display","none");
        }
    });
    $("#user-info").on("click",".logout",function(e){
        e.stopPropagation();
        $.ajax({
            type: 'get',
            url: "/logout",
            statusCode: {
                200: function (data) {
                    window.open("login.html","_self");
                }
            }
        });
    });
    $("#user-info").on("click",".messege",function(e){
        e.stopPropagation();
        $(".advice-point").css("display","none");
        $(".advice-count").css("display","none");
        $("#user-info").css("display","none");
        $("#messege-advice").click();
        $(".user-messege").css("display","block");
    });


    //通知
    $("#messege-advice").click(function(){
        if (!$(this).hasClass("selected")) {
            $(".messege-ul").empty();
           $.ajax({
                type: 'get', 
                url: "/user/advice",   
                statusCode: {    
                    200: function(data){       
                       $.each(data.advice, function(i, advice){
                            if (advice.type=="NOTE" || advice.type=="COMMENT") {
                                $(".messege-ul").append("<li><div class=\"messege-info\"><img class=\"messege-image\" src=\"images/"+
                                advice.avatar+"\"><span class=\"messege-name\">"+
                                advice.username+"&nbsp;在笔记<a href=\"https://www.renote.top/shownote?noteid="+
                                advice.noteId+"\">《"+
                                advice.noteName+"》</a>的评论中提到了你</span><span class=\"messege-time\">"+
                                advice.gmtCreate+"</span> </div><p class=\"messege-text\">"+
                                advice.c+"</p> </li>");
                            }else if(advice.type=="LIKENOTE"){
                                $(".messege-ul").append("<li><div class=\"messege-info\"><img class=\"messege-image\" src=\"images/"+
                                advice.avatar+"\"><span class=\"messege-name\">"+
                                advice.username+"&nbsp;赞了你的笔记<a href=\"https://www.renote.top/shownote?noteid="+
                                advice.noteId+"\">《"+
                                advice.noteName+"》</a></span><span class=\"messege-time\">"+
                                advice.gmtCreate+"</span> </div></li>");
                            }else if(advice.type=="LIKECOMMENT"){
                                $(".messege-ul").append("<li><div class=\"messege-info\"><img class=\"messege-image\" src=\"images/"+
                                advice.avatar+"\"><span class=\"messege-name\">"+
                                advice.username+"&nbsp;赞了你的笔记<a href=\"https://www.renote.top/shownote?noteid="+
                                advice.noteId+"\">《"+
                                advice.noteName+"》</a>中的评论</span><span class=\"messege-time\">"+
                                advice.gmtCreate+"</span> </div></li>");
                            }
                        });
                    },   
                }
            });
        }
    });
    $("#messege-comments").click(function(){
        if (!$(this).hasClass("selected")) {
            $(".messege-ul").empty();
            $.ajax({
                type: 'get', 
                url: "/user/comments",   
                statusCode: {    
                    200: function(data){       
                        $.each(data.comments, function(i, comments){
                            $(".messege-ul").append("<li><div class=\"messege-info\"><img class=\"messege-image\" src=\"images/"+
                            comments.avatar+"\"><span class=\"messege-name\">"+
                            comments.username+"&nbsp;在笔记<a href=\"https://www.renote.top/shownote?noteid="+
                            comments.noteId+"\">《"+
                            comments.noteName+"》</a>发表了评论</span><span class=\"messege-time\">"+
                            comments.gmtCreate+"</span> </div><p class=\"messege-text\">"+
                            comments.content+"</p> </li>");
                        });
                        
                    },   
                }
            });
        }
    });
    $("#user-info").click(function(e) {e.stopPropagation();});
    $(".user-messege").click(function(e) {e.stopPropagation();});
    $(".pop-up").click(function(e) {e.stopPropagation();});
    window.onclick = function (e) {
        //用户触发click事件就可以关闭了，因为绑定在window上，按事件冒泡处理，不会影响菜单的功能
        closeRightMenu();
        if(rec.css("display")=="block"&& !$("#rec-icon").is(":animated")){    
            rec.animate({left:(parseInt($("#notes-handler").css("left"))+parseInt(notes.css("left"))+parseInt($("#notes-handler").width())-rec.width())+'px'});
             setTimeout(function () {
                 rec.css("display","none");
             },300);   
             $("#rec-icon").animate({left:parseInt($("#notes-handler").css("left"))+parseInt(notes.css("left"))+parseInt($("#notes-handler").width())-40+'px'});
             //noteCon.animate({left:ntLeft+'px'});
        }
        if ($(".pop-up").css("display")=="block" || $(".user-messege").css("display")=="block") {
            $(".close").click();
        }
        if ($("#user-info").css("display")=="block") {
            $("#user-info").css("display","none");
        }
    };

    doc.on("dblclick",".recommend-notes",function(){
        var _this = $(this);
        var noteId = _this.attr("noteid");
        window.open("shownote?noteid="+noteId,"_blank");
    });
    $("#notes-private").click();


});




/*

// deps for viewer.
require('tui-editor/dist/tui-editor-contents.css'); // editor content
require('highlight.js/styles/github.css'); // code block highlight

var Viewer = require('tui-editor/dist/tui-editor-Viewer');
...
var editor = new Viewer({
    el: document.querySelector('#viewerSection'),
    height: '500px',
    initialValue: '# content to be rendered'
});
...

var Editor = require('tui-editor');
...
var editor = Editor.factory({
    el: document.querySelector('#viewerSection'),
    viewer: true,
    height: '500px',
    initialValue: '# content to be rendered'
});*/
function Base64() {  
   
    // private property  
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";  
   
    // public method for encoding  
    this.encode = function (input) {  
        var output = "";  
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;  
        var i = 0;  
        input = _utf8_encode(input);  
        while (i < input.length) {  
            chr1 = input.charCodeAt(i++);  
            chr2 = input.charCodeAt(i++);  
            chr3 = input.charCodeAt(i++);  
            enc1 = chr1 >> 2;  
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);  
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);  
            enc4 = chr3 & 63;  
            if (isNaN(chr2)) {  
                enc3 = enc4 = 64;  
            } else if (isNaN(chr3)) {  
                enc4 = 64;  
            }  
            output = output +  
            _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +  
            _keyStr.charAt(enc3) + _keyStr.charAt(enc4);  
        }  
        return output;  
    }  
   
    // public method for decoding  
    this.decode = function (input) {  
        var output = "";  
        var chr1, chr2, chr3;  
        var enc1, enc2, enc3, enc4;  
        var i = 0;  
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");  
        while (i < input.length) {  
            enc1 = _keyStr.indexOf(input.charAt(i++));  
            enc2 = _keyStr.indexOf(input.charAt(i++));  
            enc3 = _keyStr.indexOf(input.charAt(i++));  
            enc4 = _keyStr.indexOf(input.charAt(i++));  
            chr1 = (enc1 << 2) | (enc2 >> 4);  
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);  
            chr3 = ((enc3 & 3) << 6) | enc4;  
            output = output + String.fromCharCode(chr1);  
            if (enc3 != 64) {  
                output = output + String.fromCharCode(chr2);  
            }  
            if (enc4 != 64) {  
                output = output + String.fromCharCode(chr3);  
            }  
        }  
        output = _utf8_decode(output);  
        return output;  
    }  
   
    // private method for UTF-8 encoding  
    _utf8_encode = function (string) {  
        string = string.replace(/\r\n/g,"\n");  
        var utftext = "";  
        for (var n = 0; n < string.length; n++) {  
            var c = string.charCodeAt(n);  
            if (c < 128) {  
                utftext += String.fromCharCode(c);  
            } else if((c > 127) && (c < 2048)) {  
                utftext += String.fromCharCode((c >> 6) | 192);  
                utftext += String.fromCharCode((c & 63) | 128);  
            } else {  
                utftext += String.fromCharCode((c >> 12) | 224);  
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);  
                utftext += String.fromCharCode((c & 63) | 128);  
            }  
   
        }  
        return utftext;  
    }  
   
    // private method for UTF-8 decoding  
    _utf8_decode = function (utftext) {  
        var string = "";  
        var i = 0;  
        var c = c1 = c2 = 0;  
        while ( i < utftext.length ) {  
            c = utftext.charCodeAt(i);  
            if (c < 128) {  
                string += String.fromCharCode(c);  
                i++;  
            } else if((c > 191) && (c < 224)) {  
                c2 = utftext.charCodeAt(i+1);  
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));  
                i += 2;  
            } else {  
                c2 = utftext.charCodeAt(i+1);  
                c3 = utftext.charCodeAt(i+2);  
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));  
                i += 3;  
            }  
        }  
        return string;  
    }  
}