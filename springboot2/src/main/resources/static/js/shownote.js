$(document).ready(function(){
	var noteId = null;
	var noteType = window.location.href.split('?')[1].split('=')[0];
	if (noteType=="noteid") {
		noteId = window.location.href.split('?')[1].split('=')[1];
		start(noteId);
	}else{
		var noteAddress = window.location.href.split('?')[1].split('=')[1];
		$.ajax({
			type:'get',
			url: "/sharedNotes/"+noteAddress,
			statusCode:{
				200: function(data){ 
					noteId = data.note.noteId;
					start(noteId);
				}
			}
		});
	}
	
	
});
function start(noteId) {
	//作者
	$.ajax({
        type: 'get', 
        url: "/notes/" + noteId +"/userInfo",   
        statusCode: {    
            200: function(data){
            	var author = data.author;
   				$(".user-image").attr("src","images/"+author.avatar);
			   	$(".user-name-m").html(author.userName);
            },   
        }
    });
    
	//是否赞过此文章
	$.ajax({
        type: 'get', 
        url: "/notes/" + noteId +"/islike",   
        statusCode: {    
            200: function(data){
            	var info = data.info;
   				if (info=="true") {
   					$("#like-bt").css({
   						"background-color": "#ea6f5a",
    					"color": "#fff",
   					});
   					$("#like-bt").children("img").attr("src","images/praise.w.svg");
   				}
            },   
        }
    });

	//喜欢此文章
	$("#like-bt").click(function(){
		if ($(this).css("background-color")!="#ea6f5a") {
			$.ajax({
		        type: 'get', 
		        url: "/notes/" + noteId +"?like=1",   
		        statusCode: {    
		            200: function(data){
	   					$("#like-bt").css({
	   						"background-color": "#ea6f5a",
	    					"color": "#fff",
	   					});
	   					$("#like-bt").children("img").attr("src","images/praise.w.svg");
		            },   
		        }
		    });
		}
	});

	//文章
	$.ajax({
        type: 'get', 
        url: "/notes/"+noteId,   
        statusCode: {    
            200: function(data){ 
   				var note = data.note;
   				$(".title-h").html(note.noteName); 
			    $(".title-m-b").html(note.noteName);    
			    
			    $(".other-in").html(note.gmtCreate);
			    if (note.type=="markdown") {
					var editor = new tui.Editor.factory({
					    el: document.querySelector('#note-c'),
					    viewer: true,
					    height: '500px',
					    initialValue: note.content,
					});
			    } else {
			    	$("#note-c").append(note.content);
			    }
            },   
        }
    });
    


    // 推荐
	$.ajax({
        type: 'get', 
        url: "/recommend/notes/"+noteId,   
        statusCode: {    
            200: function(data){
				$.each(data.notes, function(i, item){
					$("#recomend-ul").append("<li class=\"recomend-s-list\"><a href=\"shownote?noteid="+
						item.noteId+"\"><span class=\"recomend-list-title\">"+
						item.noteName+" </span><span class=\"recomend-list-text\">"+
						item.content+"</span></a></li>");
				});
            },   
        }
    });
    
  	


    //收藏
	$("#collect-bt").click(function(){
		$.ajax({
			type:'post',
			url:"/user/collect/notes/"+noteId+'?_method=PUT',
			statusCode: {    
	            200: function(data){ 
	   				alert("收藏成功");
	            },   
        	}
		});
	});

    //评论
    $.ajax({
        type: 'get', 
        url: "/notes/"+noteId+"/comments/",   
        statusCode: {    
            200: function(data){ 
            	$("#comments-number").html(data.comments.length+"条评论");
				$.each(data.comments, function(i, item){  
					if (item.parId.substring(0,4)=="note") {
						$("#comments-ul").append("<li class=\"comments-list\"><div class=\"comments-info\"><img class=\"user-image\" src=\"images/" +
							 item.avatar + "\"><span class=\"comments-info\">" + 
							 item.username + "<br>2018-4-14 8:35</span></div><div class=\"comments-text\">" + 
							 item.content+ "</div><div class=\"comments-event\"><div class=\"praise event-block\" commentid=\"" +
							 item.commentId + "\"><img class=\"event-icon\" src=\"images/praise.svg\"><span class=\"event-text\">" +
							 item.like +"</span></div><div class=\"reply event-block\" commentid=\"" +
							 item.commentId + "\"><img class=\"event-icon\" src=\"images/reply.svg\">	<span class=\"event-text\">回复</span></div></div><div class=\"comments-child-event\" commentid=\"" +
							 item.commentId + "\"><span class=\"com-child-t\">查看回复</span><img class=\"com-child-i\" src=\"images/down.svg\"></div><div class=\"comments-child-block\"><ul class=\"comments-child-ul\"></ul></div></li>");
					}   					
				});
            },   
        }
    });
    //子评论
	$("#comments-ul").on("click",".comments-child-event",function(){
		var _this = $(this);
		var commentId = _this.attr("commentid");
		if(_this.children(".com-child-t").html()=="查看回复"){
			_this.children(".com-child-t").html("隐藏回复");
			_this.children(".com-child-i").attr("src","images/up.svg");
			$.ajax({
		        type: 'get', 
		        url: "/notes/" + noteId + "/comments/" + commentId + "/child_comments",   
		        statusCode: {    
		            200: function(data){ 
		            	$.each(data.comments, function(i, item){
							_this.next().children(".comments-child-ul").append("<li class=\"comments-child\"><span class=\"comments-child-name\">"+
							item.username +"：</span><span class=\"comments-child-text\">"+
							item.content +"</span><div class=\"comments-child-info\"><span class=\"comments-child-time\">" +
							item.gmtCreate +"</span><div class=\"event-block reply-child\"><img class=\"event-icon\" src=\"images/reply.svg\"><span class=\"event-text\">回复</span></div></div></li>");
						});
						_this.next().children(".comments-child-ul").append("<div class=\"addcom\" commentid=\""+
							commentId +"\"><img class=\"event-icon\" src=\"images/reply.svg\"><span class=\"event-text\">添加评论</span></div>");
		            },   
		        }
		    });
		    
			
		}else {
			_this.children(".com-child-t").html("查看回复");
			_this.children(".com-child-i").attr("src","images/down.svg");
			_this.next().children(".comments-child-ul").empty();
		}
	});
	$("#comments-ul").on("click",".reply",function(){
		var _this = $(this);
		if (_this.parent().next().children(".com-child-t").html()=="查看回复") {
			_this.parent().next().click();
			_this.parent().siblings(".comments-child-block").children(".comments-child-ul").children(".addcom").click();
		}
	});
	$("#comments-ul").on("click",".praise",function(){
		var _this = $(this);//#ea6f5a
		var commentId = _this.attr("commentid");
		if(_this.children(".event-icon").attr("src")!="images/praised.svg"){
			$.ajax({
		        type: 'get', 
		        url: "/notes/" + noteId + "/comments/" + commentId + "?like=1",   
		        statusCode: {    
		            200: function(data){ 
		            	_this.children(".event-icon").attr("src","images/praised.svg");
		            	_this.children(".event-text").html(parseInt(_this.children(".event-text").html())+1);
		            },   
		        }
		    });
		}	
	});

	$("#comments-ul").on("click",".reply-child",function(){
		var _this = $(this);
		_this.parent().parent().siblings(".addcom").click();
		
	});
	
	$("#comments-ul").on("click",".addcom",function(){
		var _this = $(this);
		var commentId = _this.attr("commentid");
		if (_this.next().length==0) {
			_this.after("<div class=\"comments-child-input-block\"><textarea type=\"text\" name=\"\" class=\"comments-child-input\"></textarea><div class=\"bt-a comment-child-bt\" commentid=\""+commentId+"\">发送</div></div>");
		}
	});

	$("#comments-s").on("click","#comment-add",function(){
		var content = $.trim($(".comments-input").val());
		if (content=="") {
			alert("评论不能为空！");
		}else {
			$.ajax({
	        type: 'post', 
	        url: "/notes/" + noteId + "/comments/",   
	        data: "content="+content,
	        statusCode: {    
	            200: function(data){ 
	            	var item = data.comment;
					$("#comments-ul").prepend("<li class=\"comments-list\"><div class=\"comments-info\"><img class=\"user-image\" src=\"images/" +
						 item.avatar + "\"><span class=\"comments-info\">" + 
						 item.username + "<br>2018-4-14 8:35</span></div><div class=\"comments-text\">" + 
						 item.content+ "</div><div class=\"comments-event\"><div class=\"praise event-block\" commentid=\"" +
						 item.commentId + "\"><img class=\"event-icon\" src=\"images/praise.svg\"><span class=\"event-text\">" +
						 item.like +"</span></div><div class=\"reply event-block\" commentid=\"" +
						 item.commentId + "\"><img class=\"event-icon\" src=\"images/reply.svg\">	<span class=\"event-text\">回复</span></div></div><div class=\"comments-child-event\" commentid=\"" +
						 item.commentId + "\"><span class=\"com-child-t\">查看回复</span><img class=\"com-child-i\" src=\"images/down.svg\"></div><div class=\"comments-child-block\"><ul class=\"comments-child-ul\"></ul></div></li>");
					$("#comments-number").html((parseInt($("#comments-number").html())+1)+"条评论");
					$(".comments-input").val("")
					alert("发表成功！");
	            },   
	        }
	    });
		}
		
	    
		
	});

	$("#comments-ul").on("click",".comment-child-bt",function(){
		var _this = $(this);
		var commentId = _this.attr("commentid");
		var content = $.trim(_this.siblings(".comments-child-input").val());
		if (content=="") {
			alert("评论不能为空！");
		}else {
			$.ajax({
		        type: 'post', 
		        url: "/notes/"+ noteId +"/comments/" + commentId + "/child_comments/",   
		        data: "content="+content,
		        statusCode: {    
		            200: function(data){ 
		            	var item = data.comment;
						_this.parent().siblings(".addcom").before("<li class=\"comments-child\"><span class=\"comments-child-name\">"+
							item.username +"：</span><span class=\"comments-child-text\">"+
							item.content +"</span><div class=\"comments-child-info\"><span class=\"comments-child-time\">" +
							item.gmtCreate +"</span><div class=\"event-block reply-child\"><img class=\"event-icon\" src=\"images/reply.svg\"><span class=\"event-text\">回复</span></div></div></li>");
						_this.siblings(".comments-child-input").val("");
						alert("发表成功！");
		            },  
		            403: function(data){ 
		            	alert("输入不合法");
		            }, 
		        }
		    });
	    }
		
	});
}