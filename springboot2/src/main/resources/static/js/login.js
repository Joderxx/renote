/**
 * Created by Joder_X on 2018/3/12.
 */
function validateAccount() {
    var s =  $("input[name='account']").val();
    s = s.trim()
    $("#username_tips").css('display','none')
    if(s.length<5||s.length>20){
        $("#username_tips").css('display','block')
        $("#username_tips").attr('title',title='账号长度为5-20个字符')
        return false
    }
    return true
}
function validatePassword() {
    var s = $("input[name='password']").val();
    s = s.trim()
    $("#password_tips").css('display','none')
    if(s.length<6||s.length>20){
        $("#password_tips").css('display','block')
        $("#password_tips").attr('title',title='账号长度为5-20个字符')
        return false
    }
    return true
}
$(document).ready(function () {
    $("input[name='account']").keyup(function () {
        $(this).blur(function(){
            validateAccount()
        });
    });
    $("input[name='password']").keyup(function () {
        $(this).blur(function(){
            validatePassword()
        });
    });
    $("#login-btn").click(function () {
        if(!(validatePassword()&&validateAccount()))return ;
        $("#username_tips").css('display','none')
        var account = $("input[name='account']").val().trim();
        var password = $("input[name='password']").val().trim();
        $.ajax({
            type : 'post',
            url: "/doLogin",
            data : 'account='+account+'&password='+password,
            statusCode:{
                200:function(data){
                    window.location.href='http://192.168.56.101:8080/user'
                },
                403:function(data){
                    var json =  JSON.parse(JSON.stringify(data));
                    var json = $.parseJSON(json.responseText)
                    var s=''
                    $.each(json.error, function (n,value) {
                        s += value+"\n"
                    })
                    $("#username_tips").css('display','block')
                    $("#username_tips").attr('title',title=s)
                }
            }
        });
    })
});