<template>
  <div id="outer">
    <header class="header order-last" id="tm-header">
      <nav class="navbar">
        <div class="collapse navbar-collapse single-page-nav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <a class="nav-link" href="#section-1"><span class="icn"><i class="fas fa-2x fa-air-freshener"></i></span> Our Hotel</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#section-2"><span class="icn"><i class="fab fa-2x fa-battle-net"></i></span> Our Special</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#section-3"><span class="icn"><i class="far fa-2x fa-images"></i></span> Functions</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#section-4"><span class="icn"><i class="far fa-2x fa-comments"></i></span> Go!</a>
            </li>
          </ul>
        </div>
      </nav>
    </header>

    <button class="navbar-button collapsed" type="button">
            <span class="menu_icon">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </span>
    </button>

    <main id="content-box" class="order-first">
      <div class="banner-section section parallax-window" data-parallax="scroll" id="section-1">
        <div class="container">
          <div class="item">
            <div class="bg-blue-transparent logo-fa"><span><i class="fas fa-2x fa-atom"></i></span> 酒店名称</div>
            <div class="bg-blue-transparent simple"><p>选择我们，你将永远不会后悔！<br/>You will never regret choosing us!</p></div>
          </div>
        </div>
      </div>

      <section class="work-section section" id="section-2">
        <div class="container">
          <div class="row">
            <div class="item col-md-4">
              <div class="tm-work-item-inner">
                <div class="icn"><i class="fas fa-2x fa-icons"></i></div>
                <h3>.01 数字化运营</h3>
                <p>脱离传统的方式，利用各种数据、智能工具来进行管理，通过新技术与数据能力重塑本企业的各个环节，升级体验</p>
              </div>
            </div>
            <div class="item col-md-4 one">
              <div class="tm-work-item-inner">
                <div class="icn"><i class="fas fa-2x fa-tools"></i></div>
                <h3>.02 数字化营销</h3>
                <p>利用数字化的媒体、工具和目标人群进行互动，传递精准的信息，提高营销效率</p>
              </div>
            </div>
            <div class="item col-md-4 two">
              <div class="tm-work-item-inner">
                <div class="icn"><i class="fab fa-2x fa-phoenix-framework"></i></div>
                <h3>.03 数字化协同</h3>
                <p>在数字化环境下,通过IT(信息技术)工具协作完成工作，全方位提升管理效率</p>
              </div>
            </div>
          </div>
          <div class="title">
            <h2>Our Special</h2>
          </div>
        </div>
      </section>

      <section class="gallery-section section parallax-window" data-parallax="scroll" data-image-src="../assets/section-3-bg.jpg" id="section-3">
        <div class="container">
          <div class="title text-right">
            <h2>Functions</h2>
          </div>
          <div class="mx-auto gallery-slider">
            <figure class="effect-julia item">
              <img src="@/assets/gallery-img-01.jpg" alt="Image">
            </figure>
            <figure class="effect-julia item">
              <img src="@/assets/gallery-img-02.jpg" alt="Image">
            </figure>
            <figure class="effect-julia item">
              <img src="@/assets/gallery-img-03.jpg" alt="Image">
            </figure>
            <figure class="effect-julia item">
              <img src="@/assets/gallery-img-04.jpg" alt="Image">
            </figure>
          </div>
        </div>
      </section>

      <section class="contact-section section" id="section-4">
        <section class="forms-section">
          <h1 class="section-title">登录</h1>
          <div class="forms">
            <div class="form-wrapper is-active">
              <button type="button" class="switcher switcher-login">
                用户端
                <span class="underline"></span>
              </button>
              <form class="form form-login" :model = "user">
                <fieldset>
                  <legend>请输入你的电话和密码来进行登录.</legend>
                  <div class="input-block">
                    <label for="login-email">电话</label>
                    <input id="login-email" type="email" v-model="user.phone" required>
                  </div>
                  <div class="input-block">
                    <label for="login-password">密码</label>
                    <input id="login-password" type="password" v-model="user.password" required>
                  </div>
                </fieldset>
                <button type="submit" class="btn-login" @click="loginuser()">Login</button>
              </form>
            </div>
            <div class="form-wrapper">
              <button type="button" class="switcher switcher-signup">
                管理端
                <span class="underline"></span>
              </button>
              <form class="form form-signup" :model = "adminn">
                <fieldset>
                  <legend>请输入你的电话和密码来进行登录.</legend>
                  <div class="input-block">
                    <label for="signup-email">电话</label>
                    <input id="signup-email" type="email" v-model = "adminn.phone" required>
                  </div>
                  <div class="input-block">
                    <label for="signup-password">密码</label>
                    <input id="signup-password" type="password" v-model = "adminn.password" required>
                  </div>
                </fieldset>
                <button type="submit" class="btn-signup" @click="loginadmin()">Login</button>
              </form>
            </div>
          </div>
        </section>
        <div style="width: 450px; height: 130px; background-color: #F2F6FC; border-radius: 20px; margin: 0px auto;">
          <h2 class="center" style="padding-top: 20px"><span>没有账户?</span></h2>
          <el-button type="" style="width: 300px; margin-left: 75px" @click="inRegister">创建您的账户</el-button>
          <dialog-component v-if="Visiable" ref="dialog"></dialog-component>
        </div>
      </section>
    </main>
  </div>

</template>

<script>
import dialogComponent from "./Register";
import request from "@/utils/request";
import Cookies from "js-cookie";
export default {
  name: "Login",
  data() {
    return {
      user: {},
      admin: {},
      adminn: {},
      admin_temp: {},
    }
  },
  mounted() {
    const switchers = [...document.querySelectorAll('.switcher')]

    switchers.forEach(item => {
      item.addEventListener('click', function() {
        switchers.forEach(item => item.parentElement.classList.remove('is-active'))
        this.parentElement.classList.add('is-active')
      })
    })
  },
  methods: {
    loginuser() {
      request.post('/user/the/login', this.user).then(res => {
        if (res.code === '200') {
          this.$notify.success("登录成功")
          if (res.data !== null) {
            //Cookies.set('user', obj)
            //   console.log(res.data);
            this.admin = res.data;
            Cookies.set('admin', JSON.stringify(res.data))
          }
          this.$router.push('/user')
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    loginadmin() {
      request.post('/admin/login', this.adminn).then(res => {
        if (res.code === '200') {
          this.$notify.success("登录成功")
          if (res.data !== null) {
            //Cookies.set('user', obj)
            this.temp_admin = res.data
            Cookies.set('admin', JSON.stringify(res.data))
          }
          this.$router.push('/')
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>
*,
*::before,
*::after {
  box-sizing: border-box;
}

body {
  margin: 0;
  font-family: Roboto, -apple-system, "Helvetica Neue", "Segoe UI", Arial, sans-serif;
  background: #3b4465;
}

.forms-section {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.section-title {
  font-size: 32px;
  letter-spacing: 1px;
  color: #fff;
}

.forms {
  display: flex;
  align-items: flex-start;
  margin-top: 30px;
}

.form-wrapper {
  animation: hideLayer 0.3s ease-out forwards;
}

.form-wrapper.is-active {
  animation: showLayer 0.3s ease-in forwards;
}

@keyframes showLayer {
  50% {
    z-index: 1;
  }
  100% {
    z-index: 1;
  }
}

@keyframes hideLayer {
  0% {
    z-index: 1;
  }
  49.999% {
    z-index: 1;
  }
}

.switcher {
  position: relative;
  cursor: pointer;
  display: block;
  margin-right: auto;
  margin-left: auto;
  padding: 0;
  text-transform: uppercase;
  font-family: inherit;
  font-size: 16px;
  letter-spacing: 0.5px;
  color: black;
  background-color: transparent;
  border: none;
  outline: none;
  transform: translateX(0);
  transition: all 0.3s ease-out;
}

.form-wrapper.is-active .switcher-login {
  color: #fff;
  transform: translateX(90px);
}

.form-wrapper.is-active .switcher-signup {
  color: #fff;
  transform: translateX(-90px);
}

.underline {
  position: absolute;
  bottom: -5px;
  left: 0;
  overflow: hidden;
  pointer-events: none;
  width: 100%;
  height: 2px;
}

.underline::before {
  content: "";
  position: absolute;
  top: 0;
  left: inherit;
  display: block;
  width: inherit;
  height: inherit;
  background-color: currentColor;
  transition: transform 0.2s ease-out;
}

.switcher-login .underline::before {
  transform: translateX(101%);
}

.switcher-signup .underline::before {
  transform: translateX(-101%);
}

.form-wrapper.is-active .underline::before {
  transform: translateX(0);
}

.form {
  overflow: hidden;
  min-width: 260px;
  margin-top: 50px;
  padding: 30px 25px;
  border-radius: 5px;
  transform-origin: top;
}

.form-login {
  animation: hideLogin 0.3s ease-out forwards;
}

.form-wrapper.is-active .form-login {
  animation: showLogin 0.3s ease-in forwards;
}

@keyframes showLogin {
  0% {
    background: #d7e7f1;
    transform: translate(40%, 10px);
  }
  50% {
    transform: translate(0, 0);
  }
  100% {
    background-color: #fff;
    transform: translate(35%, -20px);
  }
}

@keyframes hideLogin {
  0% {
    background-color: #fff;
    transform: translate(35%, -20px);
  }
  50% {
    transform: translate(0, 0);
  }
  100% {
    background: #d7e7f1;
    transform: translate(40%, 10px);
  }
}

.form-signup {
  animation: hideSignup 0.3s ease-out forwards;
}

.form-wrapper.is-active .form-signup {
  animation: showSignup 0.3s ease-in forwards;
}

@keyframes showSignup {
  0% {
    background: #d7e7f1;
    transform: translate(-40%, 10px) scaleY(0.8);
  }
  50% {
    transform: translate(0, 0) scaleY(0.8);
  }
  100% {
    background-color: #fff;
    transform: translate(-35%, -20px) scaleY(1);
  }
}

@keyframes hideSignup {
  0% {
    background-color: #fff;
    transform: translate(-35%, -20px) scaleY(1);
  }
  50% {
    transform: translate(0, 0) scaleY(0.8);
  }
  100% {
    background: #d7e7f1;
    transform: translate(-40%, 10px) scaleY(0.8);
  }
}

.form fieldset {
  position: relative;
  opacity: 0;
  margin: 0;
  padding: 0;
  border: 0;
  transition: all 0.3s ease-out;
}

.form-login fieldset {
  transform: translateX(-50%);
}

.form-signup fieldset {
  transform: translateX(50%);
}

.form-wrapper.is-active fieldset {
  opacity: 1;
  transform: translateX(0);
  transition: opacity 0.4s ease-in, transform 0.35s ease-in;
}

.form legend {
  position: absolute;
  overflow: hidden;
  width: 1px;
  height: 1px;
  clip: rect(0 0 0 0);
}

.input-block {
  margin-bottom: 20px;
}

.input-block label {
  font-size: 14px;
  color: #a1b4b4;
}

.input-block input {
  display: block;
  width: 100%;
  margin-top: 8px;
  padding-right: 15px;
  padding-left: 15px;
  font-size: 16px;
  line-height: 40px;
  color: #3b4465;
  background: #eef9fe;
  border: 1px solid #cddbef;
  border-radius: 2px;
}

.form [type="submit"] {
  opacity: 0;
  display: block;
  min-width: 120px;
  margin: 30px auto 10px;
  font-size: 18px;
  line-height: 40px;
  border-radius: 25px;
  border: none;
  transition: all 0.3s ease-out;
}

.form-wrapper.is-active .form [type="submit"] {
  opacity: 1;
  transform: translateX(0);
  transition: all 0.4s ease-in;
}

.btn-login {
  color: #fbfdff;
  background: #a7e245;
  transform: translateX(-30%);
}

.btn-signup {
  color: #a7e245;
  background: #fbfdff;
  box-shadow: inset 0 0 0 2px #a7e245;
  transform: translateX(30%);
}
@import url('D:\homework\vue\src\assets\bootstrap.min.css');
@import url('D:\homework\vue\src\assets\slick.css');
@import url('D:\homework\vue\src\assets\tooplate-simply-amazed.css');
</style>



