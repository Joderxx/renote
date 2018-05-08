<style type="text/css">
    *{
        margin: 0;
        padding: 0;
    }
    a:hover{
        color: #ea6f5a;
    }
    a{
        cursor: pointer;
        text-decoration : none;
        color: inherit;
    }
    html,body {
        width: 100%;
        height: 100%;
        min-height: 550px;
    }
    .e-top{
        height: 50%;
        width: 100%;
        min-height: 275px;
        top: 0;
        background: linear-gradient(to right, #fe6f45, #fcb677);
        position: fixed;
        z-index: 2;
    }
    .e-bottom{
        background: #f0f3f5;
        z-index: 1;
        width: 100%;
        height: 100%;
        min-height: 550px;
        box-sizing: border-box;
        text-align: center;
    }
    .e-title{
        z-index: 3;
        position: fixed;
        display: block;
        top: 8%;
        left: 25%;
        color: #fff;
        font-weight: bold;
        font-size: 60px;

    }
    .e-middle{
        z-index: 3;
        position: fixed;
        display: block;
        top: 25%;
        left: 25%;
        background-color: #fff;
        text-align: center;
        height: 350px;
        width: 700px;
        box-shadow: 1px 1px 1px 0px #aaa;
        min-height: 350px;
        min-width: 600px;
    }
    .e-1,.e-2,.e-3{
        display: inline-block;
        margin: 25px 0;
    }
    .e-1{
        margin-top: 40px;
        width: 100%;
        font-size: 40px;
        color: #333333;
    }
    .e-2{
        color: #fff;
        background-color: #ea6f5a;
        padding: 25px 80px;
        font-size: 25px;
        border-radius: 2px;
    }
    .e-3{
        width: 100%;
        color: #99a6bf;
        font-size: 14px;
    }
    .e-b{
        position: fixed;
        font-size: 14px;
        color: #a499b0;
        width: 300px;
        bottom: 10px;
        z-index: 2;
        left:40%;
    }
</style>
<div class="e-top"></div>
<div class="e-bottom"></div>
<span class="e-title">Renote</span>
<div class="e-middle">
    <span class="e-1">欢迎使用Renote</span>
    <a href="${url}"><div class="e-2">验证邮箱→</div></a>
    <span class="e-3">请于1小时以内验证邮箱，点击以上的按钮即可完成验证。</span>
</div>
<div class="e-b">版权 &copy; Renote Team. 保留所有权利。</div>
</body>
</html>