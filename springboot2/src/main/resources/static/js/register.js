/**
 * Created by Joder_X on 2018/3/12.
 */
function validateAccount() {
    var s =  $("input[name='account']").val();
    s = s.trim();
    $("#account_tips").css('display','none');
    if(s.length<5||s.length>20){
        $("#account_tips").css('display','block');
        $("#account_tips").attr('title',title='账号长度为5-20个字符');
        return false;
    }
    return true;
}
function validatePassword() {
    var s = $("input[name='password']").val();
    s = s.trim();
    $("#password_tips").css('display','none');
    if(s.length<6||s.length>20){
        $("#password_tips").css('display','block');
        $("#password_tips").attr('title',title='密码长度为6-20个字符');
        return false;
    }
    return true;
}
function validateRePassword() {
    var s = $("input[name='repassword']").val();
    s = s.trim();
    $("#repassword_tips").css('display','none')
    if(s.length<6||s.length>20){
        $("#repassword_tips").css('display','block')
        $("#repassword_tips").attr('title',title='密码长度为6-20个字符')
        return false;
    }else if (s!==$("input[name='password']").val()){
        $("#repassword_tips").css('display','block')
        $("#repassword_tips").attr('title',title="密码不一致")
        return false;
    }
    return true;
}
function validateEmail() {
    var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    var s = $("input[name='email']").val();
    s = s.trim();
    $("#email_tips").css('display','none');
    if(s.length==''){
        $("#email_tips").css('display','block');
        $("#email_tips").attr('title',title="密码不一致");
        return false;
    }else if(!reg.test(s)){ //正则验证不通过，格式不对
        $("#email_tips").css('display','block');
        $("#email_tips").attr('title',title="验证不通过!");
        return false;
    }
    return true;
}

function validateCode() {
    var s = $("input[name='verificateCode']").val();
    s =s.trim();
    $("#code_tips").css('display','none')
    if(s.length!=4){
        $("#code_tips").css('display','block')
        $("#code_tips").attr('title',title="验证码格式不对")
        return false
    }
    return true
}

$(document).ready(function () {
    $("input[name='account']").keyup(function () {
        $(this).blur(function() {
            validateAccount()
        });
    });
    $("input[name='password']").keyup(function () {
        $(this).blur(function(){
            validatePassword()
        });
    });
    $("input[name='repassword']").keyup(function () {
        $(this).blur(function(){
            validateRePassword()
        });
    });
    $("input[name='email']").keyup(function () {
        $(this).blur(function(){
            validateEmail()
        });
    });
    $("input[name='verificateCode']").keyup(function () {
        $(this).blur(function(){
            validateCode()
        });
    });
    $("#register-btn").click(function () {
        //if(!(validateCode()&&validateEmail()&&validateRePassword()&&validatePassword()&&validateAccount()))return ;
        $("#account_tips").css('display','none')
        var account = $("input[name='account']").val().trim();
        var email = $("input[name='email']").val().trim();
        var sex = $("input[name='sex']").val().trim();
        var password = $("input[name='password']").val().trim();
        var verificateCode = $("input[name='verificateCode']").val().trim();
        $.ajax({
            type : 'post',
            url: "/doRegister",
            data : 'account='+account+'&password='+password+'&email='+email+'&sex='+sex+'&verificateCode='+verificateCode,
            statusCode:{
                200:function(data){
                    alert("注册成功！");
                    window.open("login","_self");
                },
                403:function(data){
                    $("#imgCode").attr('src',src ='code?d='+new Date().getTime())
                    var json =  JSON.parse(JSON.stringify(data));
                    var json = jQuery.parseJSON(json.responseText)
                    var s = ''
                    $.each(json.error, function (n,value) {
                        s+=value+'\n'
                    })
                    $("#account_tips").css('display','block')
                    $("#account_tips").attr('title',title=s)
                }
            }
        });
    });
})